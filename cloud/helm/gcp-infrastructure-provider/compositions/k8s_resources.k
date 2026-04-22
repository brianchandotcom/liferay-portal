import context_variables as cv
import k8s.api.batch.v1 as k8s_batch
import k8s.api.core.v1 as k8s_core
import models.v1alpha1.kubernetesm_crossplane_io_v1alpha1_object as kubernetes

get_items = lambda obs: any, vars: {str:any} -> [any] {
    _db_spec = vars?.databaseSpec or {}
    _gsaEmail = str(vars?.gsaEmail or "")
    _jobName = str(vars?.jobName or "")
    _labels = vars?.labels or {}
    _managementPolicies = vars?.managementPolicies or ["*"]
    _managementPoliciesOnce = vars?.managementPoliciesOnce or ["Create", "Observe", "Delete"]
    _meta_name = str(vars?.metadata?.name or "")
    _namespace = str(vars?.namespace or "")

    _liferay_sa = k8s_core.ServiceAccount {
        apiVersion = "v1"
        kind = "ServiceAccount"
        metadata = {
            annotations = {"iam.gke.io/gcp-service-account": _gsaEmail}
            labels = _labels
            name = "liferay-default"
            namespace = _namespace
        }
    }

    _liferay_job = k8s_batch.Job {
        apiVersion = "batch/v1"
        kind = "Job"
        metadata = {
            labels = _labels
            name = _jobName
            namespace = _namespace
        }
        spec = {
            template = {
                spec = {
                    containers = [{
                        args = [
                            """\
set -e
until pg_isready -h 127.0.0.1 -p {port} -U postgres; do sleep 2; done
psql -h 127.0.0.1 -v "ON_ERROR_STOP=1" -U postgres -d {db} <<EOF
GRANT ALL PRIVILEGES ON DATABASE "{db}" TO "{user}";
GRANT ALL ON ALL TABLES IN SCHEMA public TO "{user}";
GRANT cloudsqlsuperuser TO "{user}";
EOF
""".format(port=str(vars?.databasePort or ""), db=str(vars?.databaseName or ""), user=str(vars?.databaseIamUser or ""))
                        ]
                        command = ["/bin/sh", "-c"]
                        env = [{
                            name = "PGPASSWORD"
                            valueFrom = {
                                secretKeyRef = {key = "password", name = str(_db_spec?.rootPasswordSecretName or "")}
                            }
                        }]
                        image = "postgres:18-alpine"
                        name = "sql-runner"
                        resources = {
                            limits = {cpu = "200m", memory = "256Mi"}
                            requests = {cpu = "100m", memory = "128Mi"}
                        }
                        securityContext = {
                            allowPrivilegeEscalation = False
                            capabilities = {drop = ["ALL"]}
                            runAsNonRoot = True
                            runAsUser = 1000
                            seccompProfile = {$type = "RuntimeDefault"}
                        }
                    }]
                    initContainers = [{
                        args = [
                            "--auto-iam-authn"
                            "--port={}".format(str(vars?.databasePort or ""))
                            "--private-ip"
                            "--structured-logs"
                            str(vars?.instanceConnectionName or "")
                        ]
                        image = "gcr.io/cloud-sql-connectors/cloud-sql-proxy:2.14.1"
                        name = "cloud-sql-proxy"
                        resources = {
                            limits = {cpu = "200m", memory = "256Mi"}
                            requests = {cpu = "100m", memory = "128Mi"}
                        }
                        restartPolicy = "Always"
                        securityContext = {
                            allowPrivilegeEscalation = False
                            capabilities = {drop = ["ALL"]}
                            runAsNonRoot = True
                            runAsUser = 65532
                            seccompProfile = {$type = "RuntimeDefault"}
                        }
                    }]
                    restartPolicy = "OnFailure"
                    securityContext = {
                        fsGroup = 1000
                        runAsNonRoot = True
                        seccompProfile = {$type = "RuntimeDefault"}
                    }
                    serviceAccountName = "liferay-default"
                    volumes = [{emptyDir = {}, name = "term-log"}]
                }
            }
        }
    }

    [
        kubernetes.Object {
            apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
            kind = "Object"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-ksa"}
                labels = _labels
                name = "{}-ksa".format(_meta_name)
            }
            spec = {
                forProvider = {manifest = _liferay_sa}
                managementPolicies = _managementPolicies
            }
        }
        kubernetes.Object {
            apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
            kind = "Object"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-db-grant-job"}
                labels = _labels
                name = _jobName
            }
            spec = {
                forProvider = {manifest = _liferay_job}
                managementPolicies = _managementPoliciesOnce
                # Mark the Object Ready once the wrapped Job has succeeded —
                # managed_service_details depends on this signal.
                readiness = {
                    celQuery = "object.status.succeeded == 1"
                    policy = "DeriveFromCelQuery"
                }
            }
        }
    ]
}

_vars = {
    "databaseIamUser": cv.databaseIamUser
    "databaseName": cv.databaseName
    "databasePort": cv.databasePort
    "databaseSpec": cv.databaseSpec
    "gsaEmail": cv.gsaEmail
    "instanceConnectionName": cv.instanceConnectionName
    "jobName": cv.jobName
    "labels": cv.labels
    "managementPolicies": cv.managementPolicies
    "managementPoliciesOnce": cv.managementPoliciesOnce
    "metadata": cv.metadata
    "namespace": cv.namespace
}

items = {"items": get_items(cv.obs, _vars)}
