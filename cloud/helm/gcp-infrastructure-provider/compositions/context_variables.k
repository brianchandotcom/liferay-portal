import crypto

params = option("params") or {}
ctx = params?.ctx or {}
obs = params?.obs or {
    "resources": {}
}
oxr = params?.oxr or {}

env = ctx?["apiextensions.crossplane.io/environment"] or {}
metadata = oxr?.metadata or {}
spec = oxr?.spec or {}

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
restorePhase = spec?.restorePhase or "none"
storage_spec = spec?.storage or {}
targetActiveDataPlane = spec?.targetActiveDataPlane or "blue"

targetInactiveDataPlane = "green" if targetActiveDataPlane == "blue" else "blue"
activeDataPlane = targetInactiveDataPlane if restorePhase == "provisioning" else targetActiveDataPlane
dataPlanes = ["blue", "green"] if restorePhase in ["promoting", "provisioning"] else [targetActiveDataPlane]

baseName = metadata?.name or ""
projectIdFull = "{}-{}".format(projectId, environmentId)
uidHash = crypto.sha256("{}-{}".format(env?.projectNumber or "", projectIdFull))[:6:]
hashedName = "{}-{}".format(projectIdFull[:18:], uidHash)

backupBucketName = "{}-vault".format(hashedName)
cmkKeyName = "projects/{}/locations/{}/keyRings/{}-keyring/cryptoKeys/{}-key".format(env?.projectId or "", region, hashedName, hashedName)
databaseIamUser = "{}-sa@{}.iam".format(baseName, env?.projectId or "")
databaseName = databaseSpec?.name or "lportal"
databasePort = "5432"
gsaEmail = "{}-sa@{}.iam.gserviceaccount.com".format(baseName, env?.projectId or "")
gsaName = "{}-sa".format(baseName)
managedServiceDetailsExternalName = "{}-managed-service-details-secret".format(hashedName)
overlayBucketName = "{}-overlay".format(hashedName)
searchName = "{}-es".format(baseName)
searchNamespace = "{}-elasticsearch".format(namespace)
searchSecretName = "{}-es-elastic-user".format(searchName)

labels = {
    "app": "liferay"
    "deployment-name": env?.deploymentName or ""
    "environment-id": environmentId
    "project-id": projectId
} | (spec?.commonTags or {})
backupTargetLabels = labels | {"should-back-up": "true"}
managementPolicies = ["Create", "Observe", "Update"] if orphan else ["*"]
managementPolicy = "ObserveCreateUpdate" if orphan else "Default"
managementPoliciesOnce = ["Create", "Observe", "Delete"]
databaseInstanceManagementPolicies = ["*"] if backup?.enabled else managementPolicies
