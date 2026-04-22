import crypto

# ---- Raw input from the crossplane function invocation ----
params = option("params") or {}
ctx = params?.ctx or {}
obs = params?.obs or {
    "resources": {}
}
oxr = params?.oxr or {}

# ---- Top-level paths into the inputs ----
env = ctx?["apiextensions.crossplane.io/environment"] or {}
metadata = oxr?.metadata or {}
spec = oxr?.spec or {}

# ---- Spec-derived primitives ----
backup = spec?.backup or {}
cmkEnabled = spec?.cmk?.enabled or False
databaseSpec = spec?.database or {}
environmentId = spec?.environmentId or ""
forceDestroy = spec?.forceDestroy or False
namespace = metadata?.namespace or ""
orphan = spec?.orphanOnDelete or False
overlay = spec?.overlay or {}
overlayEnabled = overlay?.enabled or False
projectId = spec?.projectId or ""
region = databaseSpec?.region or spec?.region or ""
storage_spec = spec?.storage or {}

# ---- Dual naming scheme (matches the legacy go-template composition) ----
# - baseName = metadata.name — namespace-scoped crossplane/k8s resources
#   (ServiceAccount, Job, DatabaseInstance, Usage, ...).
# - hashedName = {projectIdFull[:18]}-{sha256(projectNumber+projectIdFull)[:6]}
#   — globally unique GCP resources (buckets, kms, the managed-service-details
#   external-name).
baseName = metadata?.name or ""
projectIdFull = "{}-{}".format(projectId, environmentId)
uidHash = crypto.sha256("{}-{}".format(env?.projectNumber or "", projectIdFull))[:6:]
hashedName = "{}-{}".format(projectIdFull[:18:], uidHash)

# ---- Derived names (alphabetical) ----
backupBucketName = "{}-vault".format(hashedName)
bucketName = "{}-dl".format(hashedName)
cmkKeyName = "projects/{}/locations/{}/keyRings/{}-keyring/cryptoKeys/{}-key".format(env?.projectId or "", region, hashedName, hashedName)
databaseIamUser = "{}-sa@{}.iam".format(baseName, env?.projectId or "")
databaseInstanceName = "{}-db-instance".format(baseName)
databaseName = databaseSpec?.name or "lportal"
databaseObjectName = "{}-db".format(baseName)
databasePort = "5432"
databaseUserObjectName = "{}-db-user".format(baseName)
gsaEmail = "{}-sa@{}.iam.gserviceaccount.com".format(baseName, env?.projectId or "")
gsaName = "{}-sa".format(baseName)
instanceConnectionName = "{}:{}:{}".format(env?.projectId or "", region, databaseInstanceName)
jobName = "{}-db-grant".format(baseName)
managedServiceDetailsExternalName = "{}-managed-service-details-secret".format(hashedName)
overlayBucketName = "{}-overlay".format(hashedName)
searchName = "{}-es".format(baseName)
searchNamespace = "{}-elasticsearch".format(namespace)
searchSecretName = "{}-es-elastic-user".format(searchName)

# ---- Labels & management policies ----
labels = {
    "app": "liferay"
    "deployment-name": env?.deploymentName or ""
    "environment-id": environmentId
    "project-id": projectId
} | (spec?.commonTags or {})
backupTargetLabels = labels | {"active": "true", "should-back-up": "true"}
managementPolicies = ["Create", "Observe", "Update"] if orphan else ["*"]
managementPolicy = "ObserveCreateUpdate" if orphan else "Default"
managementPoliciesNoUpdate = ["Create", "Observe"] if orphan else ["Create", "Delete", "Observe"]
managementPoliciesOnce = ["Create", "Observe", "Delete"]
