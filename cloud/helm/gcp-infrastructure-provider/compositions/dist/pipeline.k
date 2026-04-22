import crypto
import json
import k8s.api.batch.v1 as k8s_batch
import k8s.api.core.v1 as k8s_core
import k8s.api.rbac.v1 as k8s_rbac
import k8s.apimachinery.pkg.apis.meta.v1 as v1
import yaml

# --- MODELS ---

schema ProjectIAMMember:

    apiVersion: "cloudplatform.gcp.m.upbound.io/v1beta1" = "cloudplatform.gcp.m.upbound.io/v1beta1"

    kind: "ProjectIAMMember" = "ProjectIAMMember"

    metadata?: v1.ObjectMeta

    spec: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpec

    status?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberStatus

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpec:

    managementPolicies?: [str] = ["*"]

    forProvider: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProvider

    initProvider?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProvider

    providerConfigRef?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecProviderConfigRef

    writeConnectionSecretToRef?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecWriteConnectionSecretToRef

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProvider:

    member?: str

    project?: str

    role?: str

    condition?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProviderCondition

    projectRef?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProviderProjectRef

    projectSelector?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProviderProjectSelector

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProviderCondition:

    description?: str

    expression?: str

    title?: str

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProviderProjectRef:

    name: str

    namespace?: str

    policy?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProviderProjectRefPolicy

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProviderProjectRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProviderProjectSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProviderProjectSelectorPolicy

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecForProviderProjectSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProvider:

    member?: str

    project?: str

    role?: str

    condition?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProviderCondition

    projectRef?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProviderProjectRef

    projectSelector?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProviderProjectSelector

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProviderCondition:

    description?: str

    expression?: str

    title?: str

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProviderProjectRef:

    name: str

    namespace?: str

    policy?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProviderProjectRefPolicy

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProviderProjectRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProviderProjectSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProviderProjectSelectorPolicy

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecInitProviderProjectSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecProviderConfigRef:

    kind: str

    name: str

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberSpecWriteConnectionSecretToRef:

    name: str

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberStatus:

    conditions?: [CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberStatusAtProvider

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberStatusAtProvider:

    etag?: str

    id?: str

    member?: str

    project?: str

    role?: str

    condition?: CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberStatusAtProviderCondition

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberStatusAtProviderCondition:

    description?: str

    expression?: str

    title?: str

schema CloudplatformGcpmUpboundIoV1beta1ProjectIAMMemberStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

schema ServiceAccount:

    apiVersion: "cloudplatform.gcp.m.upbound.io/v1beta1" = "cloudplatform.gcp.m.upbound.io/v1beta1"

    kind: "ServiceAccount" = "ServiceAccount"

    metadata?: v1.ObjectMeta

    spec: CloudplatformGcpmUpboundIoV1beta1ServiceAccountSpec

    status?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountStatus

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountSpec:

    managementPolicies?: [str] = ["*"]

    forProvider: CloudplatformGcpmUpboundIoV1beta1ServiceAccountSpecForProvider

    initProvider?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountSpecInitProvider

    providerConfigRef?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountSpecProviderConfigRef

    writeConnectionSecretToRef?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountSpecWriteConnectionSecretToRef

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountSpecForProvider:

    createIgnoreAlreadyExists?: bool

    description?: str

    disabled?: bool

    displayName?: str

    project?: str

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountSpecInitProvider:

    createIgnoreAlreadyExists?: bool

    description?: str

    disabled?: bool

    displayName?: str

    project?: str

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountSpecProviderConfigRef:

    kind: str

    name: str

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountSpecWriteConnectionSecretToRef:

    name: str

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountStatus:

    conditions?: [CloudplatformGcpmUpboundIoV1beta1ServiceAccountStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountStatusAtProvider

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountStatusAtProvider:

    createIgnoreAlreadyExists?: bool

    description?: str

    disabled?: bool

    displayName?: str

    email?: str

    id?: str

    member?: str

    name?: str

    project?: str

    uniqueId?: str

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

schema ServiceAccountIAMMember:

    apiVersion: "cloudplatform.gcp.m.upbound.io/v1beta1" = "cloudplatform.gcp.m.upbound.io/v1beta1"

    kind: "ServiceAccountIAMMember" = "ServiceAccountIAMMember"

    metadata?: v1.ObjectMeta

    spec: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpec

    status?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberStatus

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpec:

    managementPolicies?: [str] = ["*"]

    forProvider: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProvider

    initProvider?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProvider

    providerConfigRef?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecProviderConfigRef

    writeConnectionSecretToRef?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecWriteConnectionSecretToRef

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProvider:

    member?: str

    role?: str

    serviceAccountId?: str

    condition?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProviderCondition

    serviceAccountIdRef?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProviderServiceAccountIDRef

    serviceAccountIdSelector?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProviderServiceAccountIDSelector

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProviderCondition:

    description?: str

    expression?: str

    title?: str

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProviderServiceAccountIDRef:

    name: str

    namespace?: str

    policy?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProviderServiceAccountIDRefPolicy

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProviderServiceAccountIDRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProviderServiceAccountIDSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProviderServiceAccountIDSelectorPolicy

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecForProviderServiceAccountIDSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProvider:

    member?: str

    role?: str

    serviceAccountId?: str

    condition?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProviderCondition

    serviceAccountIdRef?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProviderServiceAccountIDRef

    serviceAccountIdSelector?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProviderServiceAccountIDSelector

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProviderCondition:

    description?: str

    expression?: str

    title?: str

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProviderServiceAccountIDRef:

    name: str

    namespace?: str

    policy?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProviderServiceAccountIDRefPolicy

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProviderServiceAccountIDRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProviderServiceAccountIDSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProviderServiceAccountIDSelectorPolicy

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecInitProviderServiceAccountIDSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecProviderConfigRef:

    kind: str

    name: str

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberSpecWriteConnectionSecretToRef:

    name: str

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberStatus:

    conditions?: [CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberStatusAtProvider

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberStatusAtProvider:

    etag?: str

    id?: str

    member?: str

    role?: str

    serviceAccountId?: str

    condition?: CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberStatusAtProviderCondition

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberStatusAtProviderCondition:

    description?: str

    expression?: str

    title?: str

schema CloudplatformGcpmUpboundIoV1beta1ServiceAccountIAMMemberStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

schema CryptoKey:

    apiVersion: "kms.gcp.m.upbound.io/v1beta1" = "kms.gcp.m.upbound.io/v1beta1"

    kind: "CryptoKey" = "CryptoKey"

    metadata?: v1.ObjectMeta

    spec: KmsGcpmUpboundIoV1beta1CryptoKeySpec

    status?: KmsGcpmUpboundIoV1beta1CryptoKeyStatus

schema KmsGcpmUpboundIoV1beta1CryptoKeySpec:

    managementPolicies?: [str] = ["*"]

    forProvider: KmsGcpmUpboundIoV1beta1CryptoKeySpecForProvider

    initProvider?: KmsGcpmUpboundIoV1beta1CryptoKeySpecInitProvider

    providerConfigRef?: KmsGcpmUpboundIoV1beta1CryptoKeySpecProviderConfigRef

    writeConnectionSecretToRef?: KmsGcpmUpboundIoV1beta1CryptoKeySpecWriteConnectionSecretToRef

schema KmsGcpmUpboundIoV1beta1CryptoKeySpecForProvider:

    cryptoKeyBackend?: str

    destroyScheduledDuration?: str

    importOnly?: bool

    keyRing?: str

    labels?: {str:str}

    purpose?: str

    rotationPeriod?: str

    skipInitialVersionCreation?: bool

    keyRingRef?: KmsGcpmUpboundIoV1beta1CryptoKeySpecForProviderKeyRingRef

    keyRingSelector?: KmsGcpmUpboundIoV1beta1CryptoKeySpecForProviderKeyRingSelector

    versionTemplate?: KmsGcpmUpboundIoV1beta1CryptoKeySpecForProviderVersionTemplate

schema KmsGcpmUpboundIoV1beta1CryptoKeySpecForProviderKeyRingRef:

    name: str

    namespace?: str

    policy?: KmsGcpmUpboundIoV1beta1CryptoKeySpecForProviderKeyRingRefPolicy

schema KmsGcpmUpboundIoV1beta1CryptoKeySpecForProviderKeyRingRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema KmsGcpmUpboundIoV1beta1CryptoKeySpecForProviderKeyRingSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: KmsGcpmUpboundIoV1beta1CryptoKeySpecForProviderKeyRingSelectorPolicy

schema KmsGcpmUpboundIoV1beta1CryptoKeySpecForProviderKeyRingSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema KmsGcpmUpboundIoV1beta1CryptoKeySpecForProviderVersionTemplate:

    algorithm?: str

    protectionLevel?: str

schema KmsGcpmUpboundIoV1beta1CryptoKeySpecInitProvider:

    cryptoKeyBackend?: str

    destroyScheduledDuration?: str

    importOnly?: bool

    labels?: {str:str}

    purpose?: str

    rotationPeriod?: str

    skipInitialVersionCreation?: bool

    versionTemplate?: KmsGcpmUpboundIoV1beta1CryptoKeySpecInitProviderVersionTemplate

schema KmsGcpmUpboundIoV1beta1CryptoKeySpecInitProviderVersionTemplate:

    algorithm?: str

    protectionLevel?: str

schema KmsGcpmUpboundIoV1beta1CryptoKeySpecProviderConfigRef:

    kind: str

    name: str

schema KmsGcpmUpboundIoV1beta1CryptoKeySpecWriteConnectionSecretToRef:

    name: str

schema KmsGcpmUpboundIoV1beta1CryptoKeyStatus:

    conditions?: [KmsGcpmUpboundIoV1beta1CryptoKeyStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: KmsGcpmUpboundIoV1beta1CryptoKeyStatusAtProvider

schema KmsGcpmUpboundIoV1beta1CryptoKeyStatusAtProvider:

    cryptoKeyBackend?: str

    destroyScheduledDuration?: str

    effectiveLabels?: {str:str}

    id?: str

    importOnly?: bool

    keyRing?: str

    labels?: {str:str}

    primary?: [KmsGcpmUpboundIoV1beta1CryptoKeyStatusAtProviderPrimaryItems0]

    purpose?: str

    rotationPeriod?: str

    skipInitialVersionCreation?: bool

    terraformLabels?: {str:str}

    versionTemplate?: KmsGcpmUpboundIoV1beta1CryptoKeyStatusAtProviderVersionTemplate

schema KmsGcpmUpboundIoV1beta1CryptoKeyStatusAtProviderPrimaryItems0:

    name?: str

    state?: str

schema KmsGcpmUpboundIoV1beta1CryptoKeyStatusAtProviderVersionTemplate:

    algorithm?: str

    protectionLevel?: str

schema KmsGcpmUpboundIoV1beta1CryptoKeyStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

schema CryptoKeyIAMMember:

    apiVersion: "kms.gcp.m.upbound.io/v1beta1" = "kms.gcp.m.upbound.io/v1beta1"

    kind: "CryptoKeyIAMMember" = "CryptoKeyIAMMember"

    metadata?: v1.ObjectMeta

    spec: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpec

    status?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberStatus

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpec:

    managementPolicies?: [str] = ["*"]

    forProvider: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProvider

    initProvider?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProvider

    providerConfigRef?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecProviderConfigRef

    writeConnectionSecretToRef?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecWriteConnectionSecretToRef

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProvider:

    cryptoKeyId?: str

    member?: str

    role?: str

    condition?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProviderCondition

    cryptoKeyIdRef?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProviderCryptoKeyIDRef

    cryptoKeyIdSelector?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProviderCryptoKeyIDSelector

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProviderCondition:

    description?: str

    expression?: str

    title?: str

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProviderCryptoKeyIDRef:

    name: str

    namespace?: str

    policy?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProviderCryptoKeyIDRefPolicy

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProviderCryptoKeyIDRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProviderCryptoKeyIDSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProviderCryptoKeyIDSelectorPolicy

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecForProviderCryptoKeyIDSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProvider:

    cryptoKeyId?: str

    member?: str

    role?: str

    condition?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProviderCondition

    cryptoKeyIdRef?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProviderCryptoKeyIDRef

    cryptoKeyIdSelector?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProviderCryptoKeyIDSelector

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProviderCondition:

    description?: str

    expression?: str

    title?: str

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProviderCryptoKeyIDRef:

    name: str

    namespace?: str

    policy?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProviderCryptoKeyIDRefPolicy

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProviderCryptoKeyIDRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProviderCryptoKeyIDSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProviderCryptoKeyIDSelectorPolicy

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecInitProviderCryptoKeyIDSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecProviderConfigRef:

    kind: str

    name: str

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberSpecWriteConnectionSecretToRef:

    name: str

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberStatus:

    conditions?: [KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberStatusAtProvider

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberStatusAtProvider:

    cryptoKeyId?: str

    etag?: str

    id?: str

    member?: str

    role?: str

    condition?: KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberStatusAtProviderCondition

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberStatusAtProviderCondition:

    description?: str

    expression?: str

    title?: str

schema KmsGcpmUpboundIoV1beta1CryptoKeyIAMMemberStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

schema KeyRing:

    apiVersion: "kms.gcp.m.upbound.io/v1beta1" = "kms.gcp.m.upbound.io/v1beta1"

    kind: "KeyRing" = "KeyRing"

    metadata?: v1.ObjectMeta

    spec: KmsGcpmUpboundIoV1beta1KeyRingSpec

    status?: KmsGcpmUpboundIoV1beta1KeyRingStatus

schema KmsGcpmUpboundIoV1beta1KeyRingSpec:

    managementPolicies?: [str] = ["*"]

    forProvider: KmsGcpmUpboundIoV1beta1KeyRingSpecForProvider

    initProvider?: KmsGcpmUpboundIoV1beta1KeyRingSpecInitProvider

    providerConfigRef?: KmsGcpmUpboundIoV1beta1KeyRingSpecProviderConfigRef

    writeConnectionSecretToRef?: KmsGcpmUpboundIoV1beta1KeyRingSpecWriteConnectionSecretToRef

schema KmsGcpmUpboundIoV1beta1KeyRingSpecForProvider:

    location: str

    project?: str

schema KmsGcpmUpboundIoV1beta1KeyRingSpecInitProvider:

    project?: str

schema KmsGcpmUpboundIoV1beta1KeyRingSpecProviderConfigRef:

    kind: str

    name: str

schema KmsGcpmUpboundIoV1beta1KeyRingSpecWriteConnectionSecretToRef:

    name: str

schema KmsGcpmUpboundIoV1beta1KeyRingStatus:

    conditions?: [KmsGcpmUpboundIoV1beta1KeyRingStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: KmsGcpmUpboundIoV1beta1KeyRingStatusAtProvider

schema KmsGcpmUpboundIoV1beta1KeyRingStatusAtProvider:

    id?: str

    location?: str

    project?: str

schema KmsGcpmUpboundIoV1beta1KeyRingStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

schema Database:

    apiVersion: "sql.gcp.m.upbound.io/v1beta1" = "sql.gcp.m.upbound.io/v1beta1"

    kind: "Database" = "Database"

    metadata?: v1.ObjectMeta

    spec: SQLGcpmUpboundIoV1beta1DatabaseSpec

    status?: SQLGcpmUpboundIoV1beta1DatabaseStatus

schema SQLGcpmUpboundIoV1beta1DatabaseSpec:

    managementPolicies?: [str] = ["*"]

    forProvider: SQLGcpmUpboundIoV1beta1DatabaseSpecForProvider

    initProvider?: SQLGcpmUpboundIoV1beta1DatabaseSpecInitProvider

    providerConfigRef?: SQLGcpmUpboundIoV1beta1DatabaseSpecProviderConfigRef

    writeConnectionSecretToRef?: SQLGcpmUpboundIoV1beta1DatabaseSpecWriteConnectionSecretToRef

schema SQLGcpmUpboundIoV1beta1DatabaseSpecForProvider:

    charset?: str

    collation?: str

    deletionPolicy?: str

    instance?: str

    project?: str

    instanceRef?: SQLGcpmUpboundIoV1beta1DatabaseSpecForProviderInstanceRef

    instanceSelector?: SQLGcpmUpboundIoV1beta1DatabaseSpecForProviderInstanceSelector

schema SQLGcpmUpboundIoV1beta1DatabaseSpecForProviderInstanceRef:

    name: str

    namespace?: str

    policy?: SQLGcpmUpboundIoV1beta1DatabaseSpecForProviderInstanceRefPolicy

schema SQLGcpmUpboundIoV1beta1DatabaseSpecForProviderInstanceRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema SQLGcpmUpboundIoV1beta1DatabaseSpecForProviderInstanceSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: SQLGcpmUpboundIoV1beta1DatabaseSpecForProviderInstanceSelectorPolicy

schema SQLGcpmUpboundIoV1beta1DatabaseSpecForProviderInstanceSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema SQLGcpmUpboundIoV1beta1DatabaseSpecInitProvider:

    charset?: str

    collation?: str

    deletionPolicy?: str

    project?: str

schema SQLGcpmUpboundIoV1beta1DatabaseSpecProviderConfigRef:

    kind: str

    name: str

schema SQLGcpmUpboundIoV1beta1DatabaseSpecWriteConnectionSecretToRef:

    name: str

schema SQLGcpmUpboundIoV1beta1DatabaseStatus:

    conditions?: [SQLGcpmUpboundIoV1beta1DatabaseStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: SQLGcpmUpboundIoV1beta1DatabaseStatusAtProvider

schema SQLGcpmUpboundIoV1beta1DatabaseStatusAtProvider:

    charset?: str

    collation?: str

    deletionPolicy?: str

    id?: str

    instance?: str

    project?: str

    selfLink?: str

schema SQLGcpmUpboundIoV1beta1DatabaseStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

schema DatabaseInstance:

    apiVersion: "sql.gcp.m.upbound.io/v1beta1" = "sql.gcp.m.upbound.io/v1beta1"

    kind: "DatabaseInstance" = "DatabaseInstance"

    metadata?: v1.ObjectMeta

    spec: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpec

    status?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatus

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpec:

    managementPolicies?: [str] = ["*"]

    forProvider: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProvider

    initProvider?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProvider

    providerConfigRef?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecProviderConfigRef

    writeConnectionSecretToRef?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecWriteConnectionSecretToRef

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProvider:

    databaseVersion?: str

    deletionProtection?: bool

    encryptionKeyName?: str

    maintenanceVersion?: str

    masterInstanceName?: str

    project?: str

    region?: str

    replicaNames?: [str]

    clone?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderClone

    replicaConfiguration?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderReplicaConfiguration

    replicationCluster?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderReplicationCluster

    restoreBackupContext?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderRestoreBackupContext

    rootPasswordSecretRef?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderRootPasswordSecretRef

    settings?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettings

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderClone:

    allocatedIpRange?: str

    databaseNames?: [str]

    pointInTime?: str

    preferredZone?: str

    sourceInstanceName?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderReplicaConfiguration:

    caCertificate?: str

    cascadableReplica?: bool

    clientCertificate?: str

    clientKey?: str

    connectRetryInterval?: float

    dumpFilePath?: str

    failoverTarget?: bool

    masterHeartbeatPeriod?: float

    sslCipher?: str

    username?: str

    verifyServerCertificate?: bool

    passwordSecretRef?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderReplicaConfigurationPasswordSecretRef

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderReplicaConfigurationPasswordSecretRef:

    key: str

    name: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderReplicationCluster:

    failoverDrReplicaName?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderRestoreBackupContext:

    backupRunId?: float

    instanceId?: str

    project?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderRootPasswordSecretRef:

    key: str

    name: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettings:

    activationPolicy?: str

    availabilityType?: str

    collation?: str

    connectionPoolConfig?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsConnectionPoolConfigItems0]

    connectorEnforcement?: str

    databaseFlags?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsDatabaseFlagsItems0]

    deletionProtectionEnabled?: bool

    diskAutoresize?: bool

    diskAutoresizeLimit?: float

    diskSize?: float

    diskType?: str

    edition?: str

    enableDataplexIntegration?: bool

    enableGoogleMlIntegration?: bool

    pricingPlan?: str

    retainBackupsOnDelete?: bool

    tier?: str

    timeZone?: str

    userLabels?: {str:str}

    activeDirectoryConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsActiveDirectoryConfig

    advancedMachineFeatures?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsAdvancedMachineFeatures

    backupConfiguration?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsBackupConfiguration

    dataCacheConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsDataCacheConfig

    denyMaintenancePeriod?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsDenyMaintenancePeriod

    insightsConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsInsightsConfig

    ipConfiguration?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfiguration

    locationPreference?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsLocationPreference

    maintenanceWindow?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsMaintenanceWindow

    passwordValidationPolicy?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsPasswordValidationPolicy

    sqlServerAuditConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsSQLServerAuditConfig

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsActiveDirectoryConfig:

    domain?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsAdvancedMachineFeatures:

    threadsPerCore?: float

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsBackupConfiguration:

    binaryLogEnabled?: bool

    enabled?: bool

    location?: str

    pointInTimeRecoveryEnabled?: bool

    startTime?: str

    transactionLogRetentionDays?: float

    backupRetentionSettings?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsBackupConfigurationBackupRetentionSettings

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsBackupConfigurationBackupRetentionSettings:

    retainedBackups?: float

    retentionUnit?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsConnectionPoolConfigItems0:

    connectionPoolingEnabled?: bool

    flags?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsConnectionPoolConfigItems0FlagsItems0]

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsConnectionPoolConfigItems0FlagsItems0:

    name?: str

    value?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsDataCacheConfig:

    dataCacheEnabled?: bool

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsDatabaseFlagsItems0:

    name?: str

    value?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsDenyMaintenancePeriod:

    endDate?: str

    startDate?: str

    time?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfiguration:

    allocatedIpRange?: str

    authorizedNetworks?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationAuthorizedNetworksItems0]

    customSubjectAlternativeNames?: [str]

    enablePrivatePathForGoogleCloudServices?: bool

    ipv4Enabled?: bool

    privateNetwork?: str

    pscConfig?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPscConfigItems0]

    serverCaMode?: str

    serverCaPool?: str

    sslMode?: str

    privateNetworkRef?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPrivateNetworkRef

    privateNetworkSelector?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPrivateNetworkSelector

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationAuthorizedNetworksItems0:

    expirationTime?: str

    name?: str

    value?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPrivateNetworkRef:

    name: str

    namespace?: str

    policy?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPrivateNetworkRefPolicy

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPrivateNetworkRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPrivateNetworkSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPrivateNetworkSelectorPolicy

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPrivateNetworkSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPscConfigItems0:

    allowedConsumerProjects?: [str]

    pscAutoConnections?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPscConfigItems0PscAutoConnectionsItems0]

    pscEnabled?: bool

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsIPConfigurationPscConfigItems0PscAutoConnectionsItems0:

    consumerNetwork?: str

    consumerServiceProjectId?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsInsightsConfig:

    queryInsightsEnabled?: bool

    queryPlansPerMinute?: float

    queryStringLength?: float

    recordApplicationTags?: bool

    recordClientAddress?: bool

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsLocationPreference:

    followGaeApplication?: str

    secondaryZone?: str

    zone?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsMaintenanceWindow:

    day?: float

    hour?: float

    updateTrack?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsPasswordValidationPolicy:

    complexity?: str

    disallowUsernameSubstring?: bool

    enablePasswordPolicy?: bool

    minLength?: float

    passwordChangeInterval?: str

    reuseInterval?: float

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecForProviderSettingsSQLServerAuditConfig:

    bucket?: str

    retentionInterval?: str

    uploadInterval?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProvider:

    databaseVersion?: str

    deletionProtection?: bool

    encryptionKeyName?: str

    maintenanceVersion?: str

    masterInstanceName?: str

    project?: str

    region?: str

    replicaNames?: [str]

    clone?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderClone

    replicaConfiguration?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderReplicaConfiguration

    replicationCluster?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderReplicationCluster

    restoreBackupContext?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderRestoreBackupContext

    rootPasswordSecretRef?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderRootPasswordSecretRef

    settings?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettings

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderClone:

    allocatedIpRange?: str

    databaseNames?: [str]

    pointInTime?: str

    preferredZone?: str

    sourceInstanceName?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderReplicaConfiguration:

    caCertificate?: str

    cascadableReplica?: bool

    clientCertificate?: str

    clientKey?: str

    connectRetryInterval?: float

    dumpFilePath?: str

    failoverTarget?: bool

    masterHeartbeatPeriod?: float

    sslCipher?: str

    username?: str

    verifyServerCertificate?: bool

    passwordSecretRef?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderReplicaConfigurationPasswordSecretRef

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderReplicaConfigurationPasswordSecretRef:

    key: str

    name: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderReplicationCluster:

    failoverDrReplicaName?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderRestoreBackupContext:

    backupRunId?: float

    instanceId?: str

    project?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderRootPasswordSecretRef:

    key: str

    name: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettings:

    activationPolicy?: str

    availabilityType?: str

    collation?: str

    connectionPoolConfig?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsConnectionPoolConfigItems0]

    connectorEnforcement?: str

    databaseFlags?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsDatabaseFlagsItems0]

    deletionProtectionEnabled?: bool

    diskAutoresize?: bool

    diskAutoresizeLimit?: float

    diskSize?: float

    diskType?: str

    edition?: str

    enableDataplexIntegration?: bool

    enableGoogleMlIntegration?: bool

    pricingPlan?: str

    retainBackupsOnDelete?: bool

    tier?: str

    timeZone?: str

    userLabels?: {str:str}

    activeDirectoryConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsActiveDirectoryConfig

    advancedMachineFeatures?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsAdvancedMachineFeatures

    backupConfiguration?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsBackupConfiguration

    dataCacheConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsDataCacheConfig

    denyMaintenancePeriod?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsDenyMaintenancePeriod

    insightsConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsInsightsConfig

    ipConfiguration?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfiguration

    locationPreference?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsLocationPreference

    maintenanceWindow?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsMaintenanceWindow

    passwordValidationPolicy?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsPasswordValidationPolicy

    sqlServerAuditConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsSQLServerAuditConfig

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsActiveDirectoryConfig:

    domain?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsAdvancedMachineFeatures:

    threadsPerCore?: float

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsBackupConfiguration:

    binaryLogEnabled?: bool

    enabled?: bool

    location?: str

    pointInTimeRecoveryEnabled?: bool

    startTime?: str

    transactionLogRetentionDays?: float

    backupRetentionSettings?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsBackupConfigurationBackupRetentionSettings

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsBackupConfigurationBackupRetentionSettings:

    retainedBackups?: float

    retentionUnit?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsConnectionPoolConfigItems0:

    connectionPoolingEnabled?: bool

    flags?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsConnectionPoolConfigItems0FlagsItems0]

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsConnectionPoolConfigItems0FlagsItems0:

    name?: str

    value?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsDataCacheConfig:

    dataCacheEnabled?: bool

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsDatabaseFlagsItems0:

    name?: str

    value?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsDenyMaintenancePeriod:

    endDate?: str

    startDate?: str

    time?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfiguration:

    allocatedIpRange?: str

    authorizedNetworks?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationAuthorizedNetworksItems0]

    customSubjectAlternativeNames?: [str]

    enablePrivatePathForGoogleCloudServices?: bool

    ipv4Enabled?: bool

    privateNetwork?: str

    pscConfig?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPscConfigItems0]

    serverCaMode?: str

    serverCaPool?: str

    sslMode?: str

    privateNetworkRef?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPrivateNetworkRef

    privateNetworkSelector?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPrivateNetworkSelector

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationAuthorizedNetworksItems0:

    expirationTime?: str

    name?: str

    value?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPrivateNetworkRef:

    name: str

    namespace?: str

    policy?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPrivateNetworkRefPolicy

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPrivateNetworkRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPrivateNetworkSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPrivateNetworkSelectorPolicy

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPrivateNetworkSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPscConfigItems0:

    allowedConsumerProjects?: [str]

    pscAutoConnections?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPscConfigItems0PscAutoConnectionsItems0]

    pscEnabled?: bool

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsIPConfigurationPscConfigItems0PscAutoConnectionsItems0:

    consumerNetwork?: str

    consumerServiceProjectId?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsInsightsConfig:

    queryInsightsEnabled?: bool

    queryPlansPerMinute?: float

    queryStringLength?: float

    recordApplicationTags?: bool

    recordClientAddress?: bool

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsLocationPreference:

    followGaeApplication?: str

    secondaryZone?: str

    zone?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsMaintenanceWindow:

    day?: float

    hour?: float

    updateTrack?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsPasswordValidationPolicy:

    complexity?: str

    disallowUsernameSubstring?: bool

    enablePasswordPolicy?: bool

    minLength?: float

    passwordChangeInterval?: str

    reuseInterval?: float

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecInitProviderSettingsSQLServerAuditConfig:

    bucket?: str

    retentionInterval?: str

    uploadInterval?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecProviderConfigRef:

    kind: str

    name: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceSpecWriteConnectionSecretToRef:

    name: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatus:

    conditions?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProvider

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProvider:

    availableMaintenanceVersions?: [str]

    connectionName?: str

    databaseVersion?: str

    deletionProtection?: bool

    dnsName?: str

    dnsNames?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderDNSNamesItems0]

    encryptionKeyName?: str

    firstIpAddress?: str

    id?: str

    instanceType?: str

    ipAddress?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderIPAddressItems0]

    maintenanceVersion?: str

    masterInstanceName?: str

    privateIpAddress?: str

    project?: str

    pscServiceAttachmentLink?: str

    publicIpAddress?: str

    region?: str

    replicaNames?: [str]

    selfLink?: str

    serviceAccountEmailAddress?: str

    clone?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderClone

    replicaConfiguration?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderReplicaConfiguration

    replicationCluster?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderReplicationCluster

    restoreBackupContext?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderRestoreBackupContext

    settings?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettings

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderClone:

    allocatedIpRange?: str

    databaseNames?: [str]

    pointInTime?: str

    preferredZone?: str

    sourceInstanceName?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderDNSNamesItems0:

    connectionType?: str

    dnsScope?: str

    name?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderIPAddressItems0:

    ipAddress?: str

    timeToRetire?: str

    $type?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderReplicaConfiguration:

    caCertificate?: str

    cascadableReplica?: bool

    clientCertificate?: str

    clientKey?: str

    connectRetryInterval?: float

    dumpFilePath?: str

    failoverTarget?: bool

    masterHeartbeatPeriod?: float

    sslCipher?: str

    username?: str

    verifyServerCertificate?: bool

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderReplicationCluster:

    drReplica?: bool

    failoverDrReplicaName?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderRestoreBackupContext:

    backupRunId?: float

    instanceId?: str

    project?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettings:

    activationPolicy?: str

    availabilityType?: str

    collation?: str

    connectionPoolConfig?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsConnectionPoolConfigItems0]

    connectorEnforcement?: str

    databaseFlags?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsDatabaseFlagsItems0]

    deletionProtectionEnabled?: bool

    diskAutoresize?: bool

    diskAutoresizeLimit?: float

    diskSize?: float

    diskType?: str

    edition?: str

    enableDataplexIntegration?: bool

    enableGoogleMlIntegration?: bool

    pricingPlan?: str

    retainBackupsOnDelete?: bool

    tier?: str

    timeZone?: str

    userLabels?: {str:str}

    version?: float

    activeDirectoryConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsActiveDirectoryConfig

    advancedMachineFeatures?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsAdvancedMachineFeatures

    backupConfiguration?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsBackupConfiguration

    dataCacheConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsDataCacheConfig

    denyMaintenancePeriod?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsDenyMaintenancePeriod

    insightsConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsInsightsConfig

    ipConfiguration?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsIPConfiguration

    locationPreference?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsLocationPreference

    maintenanceWindow?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsMaintenanceWindow

    passwordValidationPolicy?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsPasswordValidationPolicy

    sqlServerAuditConfig?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsSQLServerAuditConfig

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsActiveDirectoryConfig:

    domain?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsAdvancedMachineFeatures:

    threadsPerCore?: float

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsBackupConfiguration:

    binaryLogEnabled?: bool

    enabled?: bool

    location?: str

    pointInTimeRecoveryEnabled?: bool

    startTime?: str

    transactionLogRetentionDays?: float

    backupRetentionSettings?: SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsBackupConfigurationBackupRetentionSettings

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsBackupConfigurationBackupRetentionSettings:

    retainedBackups?: float

    retentionUnit?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsConnectionPoolConfigItems0:

    connectionPoolingEnabled?: bool

    flags?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsConnectionPoolConfigItems0FlagsItems0]

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsConnectionPoolConfigItems0FlagsItems0:

    name?: str

    value?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsDataCacheConfig:

    dataCacheEnabled?: bool

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsDatabaseFlagsItems0:

    name?: str

    value?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsDenyMaintenancePeriod:

    endDate?: str

    startDate?: str

    time?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsIPConfiguration:

    allocatedIpRange?: str

    authorizedNetworks?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsIPConfigurationAuthorizedNetworksItems0]

    customSubjectAlternativeNames?: [str]

    enablePrivatePathForGoogleCloudServices?: bool

    ipv4Enabled?: bool

    privateNetwork?: str

    pscConfig?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsIPConfigurationPscConfigItems0]

    serverCaMode?: str

    serverCaPool?: str

    sslMode?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsIPConfigurationAuthorizedNetworksItems0:

    expirationTime?: str

    name?: str

    value?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsIPConfigurationPscConfigItems0:

    allowedConsumerProjects?: [str]

    pscAutoConnections?: [SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsIPConfigurationPscConfigItems0PscAutoConnectionsItems0]

    pscEnabled?: bool

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsIPConfigurationPscConfigItems0PscAutoConnectionsItems0:

    consumerNetwork?: str

    consumerServiceProjectId?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsInsightsConfig:

    queryInsightsEnabled?: bool

    queryPlansPerMinute?: float

    queryStringLength?: float

    recordApplicationTags?: bool

    recordClientAddress?: bool

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsLocationPreference:

    followGaeApplication?: str

    secondaryZone?: str

    zone?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsMaintenanceWindow:

    day?: float

    hour?: float

    updateTrack?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsPasswordValidationPolicy:

    complexity?: str

    disallowUsernameSubstring?: bool

    enablePasswordPolicy?: bool

    minLength?: float

    passwordChangeInterval?: str

    reuseInterval?: float

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusAtProviderSettingsSQLServerAuditConfig:

    bucket?: str

    retentionInterval?: str

    uploadInterval?: str

schema SQLGcpmUpboundIoV1beta1DatabaseInstanceStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

schema User:

    apiVersion: "sql.gcp.m.upbound.io/v1beta1" = "sql.gcp.m.upbound.io/v1beta1"

    kind: "User" = "User"

    metadata?: v1.ObjectMeta

    spec: SQLGcpmUpboundIoV1beta1UserSpec

    status?: SQLGcpmUpboundIoV1beta1UserStatus

schema SQLGcpmUpboundIoV1beta1UserSpec:

    managementPolicies?: [str] = ["*"]

    forProvider: SQLGcpmUpboundIoV1beta1UserSpecForProvider

    initProvider?: SQLGcpmUpboundIoV1beta1UserSpecInitProvider

    providerConfigRef?: SQLGcpmUpboundIoV1beta1UserSpecProviderConfigRef

    writeConnectionSecretToRef?: SQLGcpmUpboundIoV1beta1UserSpecWriteConnectionSecretToRef

schema SQLGcpmUpboundIoV1beta1UserSpecForProvider:

    deletionPolicy?: str

    host?: str

    instance?: str

    passwordWo?: str

    passwordWoVersion?: float

    project?: str

    $type?: str

    instanceRef?: SQLGcpmUpboundIoV1beta1UserSpecForProviderInstanceRef

    instanceSelector?: SQLGcpmUpboundIoV1beta1UserSpecForProviderInstanceSelector

    passwordPolicy?: SQLGcpmUpboundIoV1beta1UserSpecForProviderPasswordPolicy

    passwordSecretRef?: SQLGcpmUpboundIoV1beta1UserSpecForProviderPasswordSecretRef

schema SQLGcpmUpboundIoV1beta1UserSpecForProviderInstanceRef:

    name: str

    namespace?: str

    policy?: SQLGcpmUpboundIoV1beta1UserSpecForProviderInstanceRefPolicy

schema SQLGcpmUpboundIoV1beta1UserSpecForProviderInstanceRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema SQLGcpmUpboundIoV1beta1UserSpecForProviderInstanceSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: SQLGcpmUpboundIoV1beta1UserSpecForProviderInstanceSelectorPolicy

schema SQLGcpmUpboundIoV1beta1UserSpecForProviderInstanceSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema SQLGcpmUpboundIoV1beta1UserSpecForProviderPasswordPolicy:

    allowedFailedAttempts?: float

    enableFailedAttemptsCheck?: bool

    enablePasswordVerification?: bool

    passwordExpirationDuration?: str

schema SQLGcpmUpboundIoV1beta1UserSpecForProviderPasswordSecretRef:

    key: str

    name: str

schema SQLGcpmUpboundIoV1beta1UserSpecInitProvider:

    deletionPolicy?: str

    host?: str

    instance?: str

    passwordWo?: str

    passwordWoVersion?: float

    project?: str

    $type?: str

    instanceRef?: SQLGcpmUpboundIoV1beta1UserSpecInitProviderInstanceRef

    instanceSelector?: SQLGcpmUpboundIoV1beta1UserSpecInitProviderInstanceSelector

    passwordPolicy?: SQLGcpmUpboundIoV1beta1UserSpecInitProviderPasswordPolicy

    passwordSecretRef?: SQLGcpmUpboundIoV1beta1UserSpecInitProviderPasswordSecretRef

schema SQLGcpmUpboundIoV1beta1UserSpecInitProviderInstanceRef:

    name: str

    namespace?: str

    policy?: SQLGcpmUpboundIoV1beta1UserSpecInitProviderInstanceRefPolicy

schema SQLGcpmUpboundIoV1beta1UserSpecInitProviderInstanceRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema SQLGcpmUpboundIoV1beta1UserSpecInitProviderInstanceSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: SQLGcpmUpboundIoV1beta1UserSpecInitProviderInstanceSelectorPolicy

schema SQLGcpmUpboundIoV1beta1UserSpecInitProviderInstanceSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema SQLGcpmUpboundIoV1beta1UserSpecInitProviderPasswordPolicy:

    allowedFailedAttempts?: float

    enableFailedAttemptsCheck?: bool

    enablePasswordVerification?: bool

    passwordExpirationDuration?: str

schema SQLGcpmUpboundIoV1beta1UserSpecInitProviderPasswordSecretRef:

    key: str

    name: str

schema SQLGcpmUpboundIoV1beta1UserSpecProviderConfigRef:

    kind: str

    name: str

schema SQLGcpmUpboundIoV1beta1UserSpecWriteConnectionSecretToRef:

    name: str

schema SQLGcpmUpboundIoV1beta1UserStatus:

    conditions?: [SQLGcpmUpboundIoV1beta1UserStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: SQLGcpmUpboundIoV1beta1UserStatusAtProvider

schema SQLGcpmUpboundIoV1beta1UserStatusAtProvider:

    deletionPolicy?: str

    host?: str

    id?: str

    instance?: str

    passwordWo?: str

    passwordWoVersion?: float

    project?: str

    sqlServerUserDetails?: [SQLGcpmUpboundIoV1beta1UserStatusAtProviderSQLServerUserDetailsItems0]

    $type?: str

    passwordPolicy?: SQLGcpmUpboundIoV1beta1UserStatusAtProviderPasswordPolicy

schema SQLGcpmUpboundIoV1beta1UserStatusAtProviderPasswordPolicy:

    allowedFailedAttempts?: float

    enableFailedAttemptsCheck?: bool

    enablePasswordVerification?: bool

    passwordExpirationDuration?: str

    status?: [SQLGcpmUpboundIoV1beta1UserStatusAtProviderPasswordPolicyStatusItems0]

schema SQLGcpmUpboundIoV1beta1UserStatusAtProviderPasswordPolicyStatusItems0:

    locked?: bool

    passwordExpirationTime?: str

schema SQLGcpmUpboundIoV1beta1UserStatusAtProviderSQLServerUserDetailsItems0:

    disabled?: bool

    serverRoles?: [str]

schema SQLGcpmUpboundIoV1beta1UserStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

schema Bucket:

    apiVersion: "storage.gcp.m.upbound.io/v1beta1" = "storage.gcp.m.upbound.io/v1beta1"

    kind: "Bucket" = "Bucket"

    metadata?: v1.ObjectMeta

    spec: StorageGcpmUpboundIoV1beta1BucketSpec

    status?: StorageGcpmUpboundIoV1beta1BucketStatus

schema StorageGcpmUpboundIoV1beta1BucketSpec:

    managementPolicies?: [str] = ["*"]

    forProvider: StorageGcpmUpboundIoV1beta1BucketSpecForProvider

    initProvider?: StorageGcpmUpboundIoV1beta1BucketSpecInitProvider

    providerConfigRef?: StorageGcpmUpboundIoV1beta1BucketSpecProviderConfigRef

    writeConnectionSecretToRef?: StorageGcpmUpboundIoV1beta1BucketSpecWriteConnectionSecretToRef

schema StorageGcpmUpboundIoV1beta1BucketSpecForProvider:

    cors?: [StorageGcpmUpboundIoV1beta1BucketSpecForProviderCorsItems0]

    defaultEventBasedHold?: bool

    enableObjectRetention?: bool

    forceDestroy?: bool

    labels?: {str:str}

    lifecycleRule?: [StorageGcpmUpboundIoV1beta1BucketSpecForProviderLifecycleRuleItems0]

    location?: str

    project?: str

    publicAccessPrevention?: str

    requesterPays?: bool

    rpo?: str

    storageClass?: str

    uniformBucketLevelAccess?: bool

    autoclass?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderAutoclass

    customPlacementConfig?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderCustomPlacementConfig

    encryption?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderEncryption

    hierarchicalNamespace?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderHierarchicalNamespace

    ipFilter?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderIPFilter

    logging?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderLogging

    retentionPolicy?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderRetentionPolicy

    softDeletePolicy?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderSoftDeletePolicy

    versioning?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderVersioning

    website?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderWebsite

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderAutoclass:

    enabled?: bool

    terminalStorageClass?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderCorsItems0:

    maxAgeSeconds?: float

    method?: [str]

    origin?: [str]

    responseHeader?: [str]

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderCustomPlacementConfig:

    dataLocations?: [str]

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderEncryption:

    defaultKmsKeyName?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderHierarchicalNamespace:

    enabled?: bool

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderIPFilter:

    allowAllServiceAgentAccess?: bool

    allowCrossOrgVpcs?: bool

    mode?: str

    vpcNetworkSources?: [StorageGcpmUpboundIoV1beta1BucketSpecForProviderIPFilterVpcNetworkSourcesItems0]

    publicNetworkSource?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderIPFilterPublicNetworkSource

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderIPFilterPublicNetworkSource:

    allowedIpCidrRanges?: [str]

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderIPFilterVpcNetworkSourcesItems0:

    allowedIpCidrRanges?: [str]

    network?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderLifecycleRuleItems0:

    action?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderLifecycleRuleItems0Action

    condition?: StorageGcpmUpboundIoV1beta1BucketSpecForProviderLifecycleRuleItems0Condition

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderLifecycleRuleItems0Action:

    storageClass?: str

    $type?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderLifecycleRuleItems0Condition:

    age?: float

    createdBefore?: str

    customTimeBefore?: str

    daysSinceCustomTime?: float

    daysSinceNoncurrentTime?: float

    matchesPrefix?: [str]

    matchesStorageClass?: [str]

    matchesSuffix?: [str]

    noncurrentTimeBefore?: str

    numNewerVersions?: float

    sendAgeIfZero?: bool

    sendDaysSinceCustomTimeIfZero?: bool

    sendDaysSinceNoncurrentTimeIfZero?: bool

    sendNumNewerVersionsIfZero?: bool

    withState?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderLogging:

    logBucket?: str

    logObjectPrefix?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderRetentionPolicy:

    isLocked?: bool

    retentionPeriod?: float

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderSoftDeletePolicy:

    retentionDurationSeconds?: float

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderVersioning:

    enabled?: bool

schema StorageGcpmUpboundIoV1beta1BucketSpecForProviderWebsite:

    mainPageSuffix?: str

    notFoundPage?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProvider:

    cors?: [StorageGcpmUpboundIoV1beta1BucketSpecInitProviderCorsItems0]

    defaultEventBasedHold?: bool

    enableObjectRetention?: bool

    forceDestroy?: bool

    labels?: {str:str}

    lifecycleRule?: [StorageGcpmUpboundIoV1beta1BucketSpecInitProviderLifecycleRuleItems0]

    location?: str

    project?: str

    publicAccessPrevention?: str

    requesterPays?: bool

    rpo?: str

    storageClass?: str

    uniformBucketLevelAccess?: bool

    autoclass?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderAutoclass

    customPlacementConfig?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderCustomPlacementConfig

    encryption?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderEncryption

    hierarchicalNamespace?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderHierarchicalNamespace

    ipFilter?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderIPFilter

    logging?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderLogging

    retentionPolicy?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderRetentionPolicy

    softDeletePolicy?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderSoftDeletePolicy

    versioning?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderVersioning

    website?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderWebsite

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderAutoclass:

    enabled?: bool

    terminalStorageClass?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderCorsItems0:

    maxAgeSeconds?: float

    method?: [str]

    origin?: [str]

    responseHeader?: [str]

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderCustomPlacementConfig:

    dataLocations?: [str]

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderEncryption:

    defaultKmsKeyName?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderHierarchicalNamespace:

    enabled?: bool

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderIPFilter:

    allowAllServiceAgentAccess?: bool

    allowCrossOrgVpcs?: bool

    mode?: str

    vpcNetworkSources?: [StorageGcpmUpboundIoV1beta1BucketSpecInitProviderIPFilterVpcNetworkSourcesItems0]

    publicNetworkSource?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderIPFilterPublicNetworkSource

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderIPFilterPublicNetworkSource:

    allowedIpCidrRanges?: [str]

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderIPFilterVpcNetworkSourcesItems0:

    allowedIpCidrRanges?: [str]

    network?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderLifecycleRuleItems0:

    action?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderLifecycleRuleItems0Action

    condition?: StorageGcpmUpboundIoV1beta1BucketSpecInitProviderLifecycleRuleItems0Condition

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderLifecycleRuleItems0Action:

    storageClass?: str

    $type?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderLifecycleRuleItems0Condition:

    age?: float

    createdBefore?: str

    customTimeBefore?: str

    daysSinceCustomTime?: float

    daysSinceNoncurrentTime?: float

    matchesPrefix?: [str]

    matchesStorageClass?: [str]

    matchesSuffix?: [str]

    noncurrentTimeBefore?: str

    numNewerVersions?: float

    sendAgeIfZero?: bool

    sendDaysSinceCustomTimeIfZero?: bool

    sendDaysSinceNoncurrentTimeIfZero?: bool

    sendNumNewerVersionsIfZero?: bool

    withState?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderLogging:

    logBucket?: str

    logObjectPrefix?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderRetentionPolicy:

    isLocked?: bool

    retentionPeriod?: float

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderSoftDeletePolicy:

    retentionDurationSeconds?: float

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderVersioning:

    enabled?: bool

schema StorageGcpmUpboundIoV1beta1BucketSpecInitProviderWebsite:

    mainPageSuffix?: str

    notFoundPage?: str

schema StorageGcpmUpboundIoV1beta1BucketSpecProviderConfigRef:

    kind: str

    name: str

schema StorageGcpmUpboundIoV1beta1BucketSpecWriteConnectionSecretToRef:

    name: str

schema StorageGcpmUpboundIoV1beta1BucketStatus:

    conditions?: [StorageGcpmUpboundIoV1beta1BucketStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: StorageGcpmUpboundIoV1beta1BucketStatusAtProvider

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProvider:

    cors?: [StorageGcpmUpboundIoV1beta1BucketStatusAtProviderCorsItems0]

    defaultEventBasedHold?: bool

    effectiveLabels?: {str:str}

    enableObjectRetention?: bool

    forceDestroy?: bool

    id?: str

    labels?: {str:str}

    lifecycleRule?: [StorageGcpmUpboundIoV1beta1BucketStatusAtProviderLifecycleRuleItems0]

    location?: str

    project?: str

    projectNumber?: float

    publicAccessPrevention?: str

    requesterPays?: bool

    rpo?: str

    selfLink?: str

    storageClass?: str

    terraformLabels?: {str:str}

    timeCreated?: str

    uniformBucketLevelAccess?: bool

    updated?: str

    url?: str

    autoclass?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderAutoclass

    customPlacementConfig?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderCustomPlacementConfig

    encryption?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderEncryption

    hierarchicalNamespace?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderHierarchicalNamespace

    ipFilter?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderIPFilter

    logging?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderLogging

    retentionPolicy?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderRetentionPolicy

    softDeletePolicy?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderSoftDeletePolicy

    versioning?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderVersioning

    website?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderWebsite

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderAutoclass:

    enabled?: bool

    terminalStorageClass?: str

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderCorsItems0:

    maxAgeSeconds?: float

    method?: [str]

    origin?: [str]

    responseHeader?: [str]

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderCustomPlacementConfig:

    dataLocations?: [str]

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderEncryption:

    defaultKmsKeyName?: str

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderHierarchicalNamespace:

    enabled?: bool

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderIPFilter:

    allowAllServiceAgentAccess?: bool

    allowCrossOrgVpcs?: bool

    mode?: str

    vpcNetworkSources?: [StorageGcpmUpboundIoV1beta1BucketStatusAtProviderIPFilterVpcNetworkSourcesItems0]

    publicNetworkSource?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderIPFilterPublicNetworkSource

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderIPFilterPublicNetworkSource:

    allowedIpCidrRanges?: [str]

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderIPFilterVpcNetworkSourcesItems0:

    allowedIpCidrRanges?: [str]

    network?: str

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderLifecycleRuleItems0:

    action?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderLifecycleRuleItems0Action

    condition?: StorageGcpmUpboundIoV1beta1BucketStatusAtProviderLifecycleRuleItems0Condition

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderLifecycleRuleItems0Action:

    storageClass?: str

    $type?: str

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderLifecycleRuleItems0Condition:

    age?: float

    createdBefore?: str

    customTimeBefore?: str

    daysSinceCustomTime?: float

    daysSinceNoncurrentTime?: float

    matchesPrefix?: [str]

    matchesStorageClass?: [str]

    matchesSuffix?: [str]

    noncurrentTimeBefore?: str

    numNewerVersions?: float

    sendAgeIfZero?: bool

    sendDaysSinceCustomTimeIfZero?: bool

    sendDaysSinceNoncurrentTimeIfZero?: bool

    sendNumNewerVersionsIfZero?: bool

    withState?: str

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderLogging:

    logBucket?: str

    logObjectPrefix?: str

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderRetentionPolicy:

    isLocked?: bool

    retentionPeriod?: float

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderSoftDeletePolicy:

    effectiveTime?: str

    retentionDurationSeconds?: float

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderVersioning:

    enabled?: bool

schema StorageGcpmUpboundIoV1beta1BucketStatusAtProviderWebsite:

    mainPageSuffix?: str

    notFoundPage?: str

schema StorageGcpmUpboundIoV1beta1BucketStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

schema BucketIAMMember:

    apiVersion: "storage.gcp.m.upbound.io/v1beta1" = "storage.gcp.m.upbound.io/v1beta1"

    kind: "BucketIAMMember" = "BucketIAMMember"

    metadata?: v1.ObjectMeta

    spec: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpec

    status?: StorageGcpmUpboundIoV1beta1BucketIAMMemberStatus

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpec:

    managementPolicies?: [str] = ["*"]

    forProvider: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProvider

    initProvider?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProvider

    providerConfigRef?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecProviderConfigRef

    writeConnectionSecretToRef?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecWriteConnectionSecretToRef

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProvider:

    bucket?: str

    member?: str

    role?: str

    bucketRef?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProviderBucketRef

    bucketSelector?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProviderBucketSelector

    condition?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProviderCondition

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProviderBucketRef:

    name: str

    namespace?: str

    policy?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProviderBucketRefPolicy

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProviderBucketRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProviderBucketSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProviderBucketSelectorPolicy

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProviderBucketSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecForProviderCondition:

    description?: str

    expression?: str

    title?: str

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProvider:

    bucket?: str

    member?: str

    role?: str

    bucketRef?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProviderBucketRef

    bucketSelector?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProviderBucketSelector

    condition?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProviderCondition

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProviderBucketRef:

    name: str

    namespace?: str

    policy?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProviderBucketRefPolicy

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProviderBucketRefPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProviderBucketSelector:

    matchControllerRef?: bool

    matchLabels?: {str:str}

    namespace?: str

    policy?: StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProviderBucketSelectorPolicy

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProviderBucketSelectorPolicy:

    resolution?: "Required" | "Optional" = "Required"

    resolve?: "Always" | "IfNotPresent"

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecInitProviderCondition:

    description?: str

    expression?: str

    title?: str

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecProviderConfigRef:

    kind: str

    name: str

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberSpecWriteConnectionSecretToRef:

    name: str

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberStatus:

    conditions?: [StorageGcpmUpboundIoV1beta1BucketIAMMemberStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: StorageGcpmUpboundIoV1beta1BucketIAMMemberStatusAtProvider

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberStatusAtProvider:

    bucket?: str

    etag?: str

    id?: str

    member?: str

    role?: str

    condition?: StorageGcpmUpboundIoV1beta1BucketIAMMemberStatusAtProviderCondition

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberStatusAtProviderCondition:

    description?: str

    expression?: str

    title?: str

schema StorageGcpmUpboundIoV1beta1BucketIAMMemberStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

schema ManagedFieldsEntry:

    apiVersion?: str

    fieldsType?: str

    fieldsV1?: any

    manager?: str

    operation?: str

    time?: str

schema ObjectMeta:

    annotations?: {str:str}

    clusterName?: str

    creationTimestamp?: str

    deletionGracePeriodSeconds?: int

    deletionTimestamp?: str

    finalizers?: [str]

    generateName?: str

    generation?: int

    labels?: {str:str}

    managedFields?: [ManagedFieldsEntry]

    name?: str

    namespace?: str

    ownerReferences?: [OwnerReference]

    resourceVersion?: str

    selfLink?: str

    uid?: str

schema OwnerReference:

    apiVersion: str

    blockOwnerDeletion?: bool

    controller?: bool

    kind: str

    name: str

    uid: str

schema Object:

    apiVersion: "kubernetes.m.crossplane.io/v1alpha1" = "kubernetes.m.crossplane.io/v1alpha1"

    kind: "Object" = "Object"

    metadata?: v1.ObjectMeta

    spec: KubernetesmCrossplaneIoV1alpha1ObjectSpec

    status?: KubernetesmCrossplaneIoV1alpha1ObjectStatus

schema KubernetesmCrossplaneIoV1alpha1ObjectSpec:

    connectionDetails?: [KubernetesmCrossplaneIoV1alpha1ObjectSpecConnectionDetailsItems0]

    managementPolicies?: [str] = ["*"]

    references?: [KubernetesmCrossplaneIoV1alpha1ObjectSpecReferencesItems0]

    watch?: bool = False

    forProvider: KubernetesmCrossplaneIoV1alpha1ObjectSpecForProvider

    providerConfigRef?: KubernetesmCrossplaneIoV1alpha1ObjectSpecProviderConfigRef

    readiness?: KubernetesmCrossplaneIoV1alpha1ObjectSpecReadiness

    writeConnectionSecretToRef?: KubernetesmCrossplaneIoV1alpha1ObjectSpecWriteConnectionSecretToRef

schema KubernetesmCrossplaneIoV1alpha1ObjectSpecConnectionDetailsItems0:

    apiVersion?: str

    fieldPath?: str

    kind?: str

    name?: str

    namespace?: str

    resourceVersion?: str

    toConnectionSecretKey?: str

    uid?: str

schema KubernetesmCrossplaneIoV1alpha1ObjectSpecForProvider:

    manifest: any

schema KubernetesmCrossplaneIoV1alpha1ObjectSpecProviderConfigRef:

    kind: str

    name: str

schema KubernetesmCrossplaneIoV1alpha1ObjectSpecReadiness:

    celQuery?: str

    policy?: "SuccessfulCreate" | "DeriveFromObject" | "AllTrue" | "DeriveFromCelQuery" = "SuccessfulCreate"

schema KubernetesmCrossplaneIoV1alpha1ObjectSpecReferencesItems0:

    dependsOn?: KubernetesmCrossplaneIoV1alpha1ObjectSpecReferencesItems0DependsOn

    patchesFrom?: KubernetesmCrossplaneIoV1alpha1ObjectSpecReferencesItems0PatchesFrom

    toFieldPath?: str

schema KubernetesmCrossplaneIoV1alpha1ObjectSpecReferencesItems0DependsOn:

    apiVersion?: str = "kubernetes.m.crossplane.io/v1alpha1"

    kind?: str = "Object"

    name: str

    namespace?: str

schema KubernetesmCrossplaneIoV1alpha1ObjectSpecReferencesItems0PatchesFrom:

    apiVersion?: str = "kubernetes.m.crossplane.io/v1alpha1"

    fieldPath: str

    kind?: str = "Object"

    name: str

    namespace?: str

schema KubernetesmCrossplaneIoV1alpha1ObjectSpecWriteConnectionSecretToRef:

    name: str

schema KubernetesmCrossplaneIoV1alpha1ObjectStatus:

    conditions?: [KubernetesmCrossplaneIoV1alpha1ObjectStatusConditionsItems0]

    observedGeneration?: int

    atProvider?: KubernetesmCrossplaneIoV1alpha1ObjectStatusAtProvider

schema KubernetesmCrossplaneIoV1alpha1ObjectStatusAtProvider:

    manifest?: any

schema KubernetesmCrossplaneIoV1alpha1ObjectStatusConditionsItems0:

    lastTransitionTime: str

    message?: str

    observedGeneration?: int

    reason: str

    status: str

    $type: str

# --- CONTEXT ---

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

schema backup_layer:

    get_items = lambda obs: any, vars: {str:any} -> [any] {
        _backup = vars?.backup or {}
        _gsaEmail = str(vars?.gsaEmail or "")
        _labels = vars?.labels or {}
        _managementPolicies = vars?.managementPolicies or ["*"]
        _managementPolicy = vars?.managementPolicy or "Default"
        _meta_name = str(vars?.metadata?.name or "")
        _namespace = str(vars?.namespace or "")

        _get_iam_name = lambda role: str, bucket: str -> str {
            _hash = crypto.sha256(_meta_name + role + "serviceAccount:" + _gsaEmail + bucket)[:12:]
            "{}-{}".format(_meta_name, _hash)
        }

        _backup_items: [any] = []
        if _backup?.enabled:
            _backup_vault_labels = _labels | {"is-backup-vault": "true"}
            _bucket_name = str(vars?.backupBucketName or "")

            _backup_items += [Bucket {
                apiVersion = "storage.gcp.m.upbound.io/v1beta1"
                kind = "Bucket"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-gcs-backup"}
                    labels = _backup_vault_labels
                    name = _bucket_name
                }
                spec = {
                    forProvider = {
                        forceDestroy = bool(vars?.forceDestroy or False)
                        labels = _backup_vault_labels
                        lifecycleRule = [{
                            action = {$type = "Delete"}
                            condition = {age = int(_backup?.retentionDays or 30) + 2}
                        }]
                        location = str(_backup?.region or vars?.region or "")
                        storageClass = str(_backup?.storageClass or "NEARLINE")
                        uniformBucketLevelAccess = True
                    }
                    if vars?.cmkEnabled:
                        forProvider.encryption = {
                            defaultKmsKeyName = str(vars?.cmkKeyName or "")
                        }
                    managementPolicies = _managementPolicies
                }
            }]

            _backup_items += [BucketIAMMember {
                apiVersion = "storage.gcp.m.upbound.io/v1beta1"
                kind = "BucketIAMMember"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-gcs-backup-iam"}
                    labels = _labels
                    name = _get_iam_name("roles/storage.admin", _bucket_name)
                }
                spec = {
                    forProvider = {
                        bucketRef = {"name": _bucket_name}
                        member = "serviceAccount:{}".format(_gsaEmail)
                        role = "roles/storage.admin"
                    }
                    managementPolicies = _managementPolicies
                }
            }]

            # The ClusterRole 'backup-create-cluster-role' is provisioned out-of-band
            # (baseline RBAC chart); this composition only creates the per-namespace
            # RoleBinding + ServiceAccount that use it.
            _backup_sa = k8s_core.ServiceAccount {
                apiVersion = "v1"
                kind = "ServiceAccount"
                metadata = {
                    labels = _labels
                    name = "backup-create-service-account"
                    namespace = _namespace
                }
            }

            _backup_rb = k8s_rbac.RoleBinding {
                apiVersion = "rbac.authorization.k8s.io/v1"
                kind = "RoleBinding"
                metadata = {
                    labels = _labels
                    name = "backup-create-role-binding"
                    namespace = _namespace
                }
                roleRef = {
                    apiGroup = "rbac.authorization.k8s.io"
                    kind = "ClusterRole"
                    name = "backup-create-cluster-role"
                }
                subjects = [{
                    kind = "ServiceAccount"
                    name = "backup-create-service-account"
                    namespace = _namespace
                }]
            }

            _backup_items += [
                Object {
                    apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
                    kind = "Object"
                    metadata = {
                        annotations = {"crossplane.io/composition-resource-name": "backup-create-service-account"}
                        labels = _labels
                        name = "backup-create-service-account"
                    }
                    spec = {
                        forProvider = {"manifest": _backup_sa}
                        managementPolicies = _managementPolicies
                    }
                }
                Object {
                    apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
                    kind = "Object"
                    metadata = {
                        annotations = {"crossplane.io/composition-resource-name": "backup-create-role-binding"}
                        labels = _labels
                        name = "backup-create-role-binding"
                    }
                    spec = {
                        forProvider = {"manifest": _backup_rb}
                        managementPolicies = _managementPolicies
                    }
                }
            ]

        _backup_items
    }

    _vars = {
        "backup": backup
        "backupBucketName": backupBucketName
        "cmkEnabled": cmkEnabled
        "cmkKeyName": cmkKeyName
        "forceDestroy": forceDestroy
        "gsaEmail": gsaEmail
        "labels": labels
        "managementPolicies": managementPolicies
        "managementPolicy": managementPolicy
        "metadata": metadata
        "namespace": namespace
        "region": region
    }

    items: {str:any} = {"items": get_items(obs, _vars)}

_backup = backup_layer {}
items_backup = _backup.items

schema elasticsearch_layer:

    get_items = lambda obs: any, vars: {str:any} -> [any] {
        _res = obs?.resources or {}
        _es_status = _res?["elasticsearch-observer"]?.resource?.status?.atProvider?.manifest?.status or {}
        _sec_data = _res?["elasticsearch-secret-observer"]?.resource?.status?.atProvider?.manifest?.data or {}

        _health = _es_status?.health or ""
        _password = _sec_data?.elastic or ""
        _is_ready = _health in ["green", "yellow"] and _password

        _labels = vars?.labels or {}
        _name = str(vars?.metadata?.name or "")
        _namespace = str(vars?.namespace or "")
        _searchName = str(vars?.searchName or "")
        _searchNamespace = str(vars?.searchNamespace or "")
        _searchSecretName = str(vars?.searchSecretName or "")
        _managementPolicies = vars?.managementPolicies or ["Default"]

        _items = [
            Object {
                apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
                kind = "Object"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "elasticsearch-observer"}
                    name = "{}-es-observer".format(_name)
                }
                spec = {
                    forProvider = {
                        manifest = {
                            apiVersion = "elasticsearch.k8s.elastic.co/v1"
                            kind = "Elasticsearch"
                            metadata = {name = _searchName, namespace = _searchNamespace}
                        }
                    }
                    managementPolicies = ["Observe"]
                }
            }
            Object {
                apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
                kind = "Object"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "elasticsearch-secret-observer"}
                    name = "{}-es-es-elastic-user-observer".format(_name)
                }
                spec = {
                    forProvider = {
                        manifest = k8s_core.Secret {
                            apiVersion = "v1"
                            kind = "Secret"
                            metadata = {name = _searchSecretName, namespace = _searchNamespace}
                        }
                    }
                    managementPolicies = ["Observe"]
                }
            }
        ]

        if _is_ready:
            _items += [Object {
                apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
                kind = "Object"
                metadata = {
                    annotations = {
                        "crossplane.io/external-name": "elasticsearch-connection-details"
                        "crossplane.io/composition-resource-name": "elasticsearch-connection-details-secret"
                    }
                    labels = _labels
                    name = "{}-elasticsearch-connection-details-secret".format(_name)
                }
                spec = {
                    forProvider = {
                        manifest = k8s_core.Secret {
                            apiVersion = "v1"
                            kind = "Secret"
                            data = {ELASTICSEARCH_PASSWORD = _password}
                            metadata = {
                                labels = _labels
                                name = "elasticsearch-connection-details"
                                namespace = _namespace
                            }
                            stringData = {
                                ELASTICSEARCH_URL = "http://{}-es-http.{}.svc:9200".format(_searchName, _searchNamespace)
                                ELASTICSEARCH_USERNAME = "elastic"
                            }
                            $type = "Opaque"
                        }
                    }
                    managementPolicies = _managementPolicies
                }
            }]

        _items
    }

    _vars = {
        "labels": labels
        "managementPolicies": managementPolicies
        "metadata": metadata
        "namespace": namespace
        "searchName": searchName
        "searchNamespace": searchNamespace
        "searchSecretName": searchSecretName
    }

    items: {str:any} = {"items": get_items(obs, _vars)}

_elasticsearch = elasticsearch_layer {}
items_elasticsearch = _elasticsearch.items

schema init_layer:

    # Emits a composite-state-update item that function-kcl merges onto the XR's
    # status. The old go-template composition wrote the same shape via a separate
    # LiferayInfrastructure manifest. Consumers (liferay app) poll this flag.
    get_items = lambda obs: any, vars: {str:any} -> [any] {
        _grant_conditions = obs?.resources?["liferay-db-grant-job"]?.resource?.status?.conditions or []
        _is_ready = any c in _grant_conditions {c.type == "Ready" and c.status == "True"}
        [
            {
                apiVersion = "gcp.liferay.com/v1alpha1"
                kind = "LiferayInfrastructure"
                status = {managedServiceDetailsReady = _is_ready}
            }
        ]
    }

    _vars = {}

    items: {str:any} = {"items": get_items(obs, _vars)}

_init = init_layer {}
items_init = _init.items

schema k8s_resources_layer:

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
            Object {
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
            Object {
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
        "databaseIamUser": databaseIamUser
        "databaseName": databaseName
        "databasePort": databasePort
        "databaseSpec": databaseSpec
        "gsaEmail": gsaEmail
        "instanceConnectionName": instanceConnectionName
        "jobName": jobName
        "labels": labels
        "managementPolicies": managementPolicies
        "managementPoliciesOnce": managementPoliciesOnce
        "metadata": metadata
        "namespace": namespace
    }

    items: {str:any} = {"items": get_items(obs, _vars)}

_k8s_resources = k8s_resources_layer {}
items_k8s_resources = _k8s_resources.items

schema managed_service_details_layer:

    get_items = lambda obs: any, vars: {str:any} -> [any] {
        # Gate on the grant-job Object being Ready. Until the Job has succeeded
        # the DB user has no privileges, so any downstream consumer reading this
        # secret would hit a broken DSN.
        _res = obs?.resources or {}
        _grant_conditions = _res?["liferay-db-grant-job"]?.resource?.status?.conditions or []
        _is_ready = any c in _grant_conditions {c.type == "Ready" and c.status == "True"}

        _externalName = str(vars?.managedServiceDetailsExternalName or "")
        _labels = vars?.labels or {}
        _managementPolicies = vars?.managementPolicies or ["*"]
        _namespace = str(vars?.namespace or "")

        _items: [any] = []
        if _is_ready:
            _secret = k8s_core.Secret {
                apiVersion = "v1"
                kind = "Secret"
                metadata = {
                    labels = _labels
                    name = "managed-service-details"
                    namespace = _namespace
                }
                stringData = {
                    DATABASE_ENDPOINT = "localhost"
                    DATABASE_INSTANCE_NAME = str(vars?.instanceConnectionName or "")
                    DATABASE_PORT = str(vars?.databasePort or "5432")
                    DATABASE_USERNAME = str(vars?.databaseIamUser or "")
                    LIFERAY_GCS_BUCKET_NAME = str(vars?.bucketName or "")
                    LIFERAY_OVERLAY_BUCKET_NAME = str(vars?.overlayBucketName or "")
                }
                $type = "Opaque"
            }

            _items += [Object {
                apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
                kind = "Object"
                metadata = {
                    annotations = {
                        "crossplane.io/composition-resource-name": "managed-service-details-secret"
                        "crossplane.io/external-name": _externalName
                    }
                    labels = _labels
                    name = _externalName
                }
                spec = {
                    forProvider = {manifest = _secret}
                    managementPolicies = _managementPolicies
                }
            }]

        _items
    }

    _vars = {
        "bucketName": bucketName
        "databaseIamUser": databaseIamUser
        "databasePort": databasePort
        "instanceConnectionName": instanceConnectionName
        "labels": labels
        "managedServiceDetailsExternalName": managedServiceDetailsExternalName
        "managementPolicies": managementPolicies
        "namespace": namespace
        "overlayBucketName": overlayBucketName
    }

    items: {str:any} = {"items": get_items(obs, _vars)}

_managed_service_details = managed_service_details_layer {}
items_managed_service_details = _managed_service_details.items

schema overlay_layer:

    get_items = lambda obs: any, vars: {str:any} -> [any] {
        _labels = vars?.labels or {}
        _overlayBucketName = str(vars?.overlayBucketName or "")
        _gsaEmail = str(vars?.gsaEmail or "")
        _managementPolicies = vars?.managementPolicies or ["*"]

        _items: [any] = []

        if vars?.overlayEnabled:
            _items += [
                Bucket {
                    apiVersion = "storage.gcp.m.upbound.io/v1beta1"
                    kind = "Bucket"
                    metadata = {
                        annotations = {"crossplane.io/composition-resource-name": "liferay-overlay-bucket"}
                        labels = _labels
                        name = _overlayBucketName
                    }
                    spec = {
                        forProvider = {
                            forceDestroy = bool(vars?.forceDestroy or False)
                            labels = _labels
                            location = str(vars?.overlayRegion or vars?.region or "")
                            project = str(vars?.projectId or "")
                            storageClass = "STANDARD"
                            uniformBucketLevelAccess = True
                            versioning = {enabled = True}
                        }
                        if vars?.cmkEnabled:
                            forProvider.encryption = {
                                defaultKmsKeyName = str(vars?.cmkKeyName or "")
                            }
                        managementPolicies = _managementPolicies
                    }
                }
                BucketIAMMember {
                    apiVersion = "storage.gcp.m.upbound.io/v1beta1"
                    kind = "BucketIAMMember"
                    metadata = {
                        annotations = {"crossplane.io/composition-resource-name": "liferay-gsa-overlay-bucket-iam"}
                        labels = _labels
                    }
                    spec = {
                        forProvider = {
                            bucketRef = {name = _overlayBucketName}
                            member = "serviceAccount:{}".format(_gsaEmail)
                            role = "roles/storage.objectViewer"
                        }
                        managementPolicies = _managementPolicies
                    }
                }
            ]

        _items
    }

    _vars = {
        "cmkEnabled": cmkEnabled
        "cmkKeyName": cmkKeyName
        "forceDestroy": forceDestroy
        "gsaEmail": gsaEmail
        "labels": labels
        "managementPolicies": managementPolicies
        "overlayBucketName": overlayBucketName
        "overlayEnabled": overlayEnabled
        "overlayRegion": overlay?.region or ""
        "projectId": env?.projectId or ""
        "region": region
    }

    items: {str:any} = {"items": get_items(obs, _vars)}

_overlay = overlay_layer {}
items_overlay = _overlay.items

schema security_layer:

    get_items = lambda obs: any, vars: {str:any} -> [any] {
        _env = vars?.env or {}
        _gsaEmail = str(vars?.gsaEmail or "")
        _gsaName = str(vars?.gsaName or "")

        # KMS resources are global in GCP — use hashedName for uniqueness across
        # deployments that share a project.
        _kmsName = str(vars?.hashedName or "")
        _labels = vars?.labels or {}
        _managementPolicies = vars?.managementPolicies or ["*"]
        _namespace = str(vars?.namespace or "")
        _projectId = str(_env?.projectId or "")

        _security_items: [any] = [
            ServiceAccount {
                apiVersion = "cloudplatform.gcp.m.upbound.io/v1beta1"
                kind = "ServiceAccount"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-gsa"}
                    labels = _labels
                    name = _gsaName
                }
                spec = {
                    forProvider = {
                        displayName = "Liferay {} Service Account".format(_gsaName)
                    }
                    managementPolicies = _managementPolicies
                }
            }
            ServiceAccountIAMMember {
                apiVersion = "cloudplatform.gcp.m.upbound.io/v1beta1"
                kind = "ServiceAccountIAMMember"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-wi-binding"}
                    labels = _labels
                }
                spec = {
                    forProvider = {
                        member = "serviceAccount:{}.svc.id.goog[{}/liferay-default]".format(_projectId, _namespace)
                        role = "roles/iam.workloadIdentityUser"
                        serviceAccountIdRef = {name = _gsaName}
                    }
                    managementPolicies = _managementPolicies
                }
            }
            ProjectIAMMember {
                apiVersion = "cloudplatform.gcp.m.upbound.io/v1beta1"
                kind = "ProjectIAMMember"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-sql-instance-user-role"}
                    labels = _labels
                }
                spec = {
                    forProvider = {
                        member = "serviceAccount:{}".format(_gsaEmail)
                        project = _projectId
                        role = "roles/cloudsql.instanceUser"
                    }
                    managementPolicies = _managementPolicies
                }
            }
            ProjectIAMMember {
                apiVersion = "cloudplatform.gcp.m.upbound.io/v1beta1"
                kind = "ProjectIAMMember"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-sql-client-role"}
                    labels = _labels
                }
                spec = {
                    forProvider = {
                        member = "serviceAccount:{}".format(_gsaEmail)
                        project = _projectId
                        role = "roles/cloudsql.client"
                    }
                    managementPolicies = _managementPolicies
                }
            }
        ]

        if vars?.cmkEnabled:
            _security_items += [
                KeyRing {
                    apiVersion = "kms.gcp.m.upbound.io/v1beta1"
                    kind = "KeyRing"
                    metadata = {
                        annotations = {"crossplane.io/composition-resource-name": "liferay-keyring"}
                        labels = _labels
                        name = "{}-keyring".format(_kmsName)
                    }
                    spec = {
                        forProvider = {location = str(vars?.region or "")}
                        managementPolicies = _managementPolicies
                    }
                }
                CryptoKey {
                    apiVersion = "kms.gcp.m.upbound.io/v1beta1"
                    kind = "CryptoKey"
                    metadata = {
                        annotations = {"crossplane.io/composition-resource-name": "liferay-cryptokey"}
                        labels = _labels
                        name = "{}-key".format(_kmsName)
                    }
                    spec = {
                        forProvider = {
                            keyRingSelector = {matchControllerRef = True}
                            purpose = "ENCRYPT_DECRYPT"
                            rotationPeriod = "86400s"
                        }
                        managementPolicies = _managementPolicies
                    }
                }
            ]

            _kms_targets = [
                {"name": "sql", "svc": "gcp-sa-cloud-sql"}
                {"name": "storage", "svc": "gs-project-accounts"}
            ]
            _security_items += [CryptoKeyIAMMember {
                apiVersion = "kms.gcp.m.upbound.io/v1beta1"
                kind = "CryptoKeyIAMMember"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-{}-kms-binding".format(t.name)}
                    labels = _labels
                }
                spec = {
                    forProvider = {
                        cryptoKeyIdRef = {name = "{}-key".format(_kmsName)}
                        member = "serviceAccount:service-{}@{}.iam.gserviceaccount.com".format(str(_env?.projectNumber or ""), t.svc)
                        role = "roles/cloudkms.cryptoKeyEncrypterDecrypter"
                    }
                    managementPolicies = _managementPolicies
                }
            } for t in _kms_targets]

        _security_items
    }

    _vars = {
        "cmkEnabled": cmkEnabled
        "cmkKeyName": cmkKeyName
        "env": env
        "gsaEmail": gsaEmail
        "gsaName": gsaName
        "hashedName": hashedName
        "labels": labels
        "managementPolicies": managementPolicies
        "namespace": namespace
        "region": region
    }

    items: {str:any} = {"items": get_items(obs, _vars)}

_security = security_layer {}
items_security = _security.items

schema sql_layer:

    get_items = lambda obs: any, vars: {str:any} -> [any] {
        _baseName = str(vars?.baseName or "")
        _cmkEnabled = bool(vars?.cmkEnabled or False)
        _cmkKeyName = str(vars?.cmkKeyName or "")
        _db_spec = vars?.database or {}
        _dbObjectName = str(vars?.databaseObjectName or "{}-db".format(_baseName))
        _env = vars?.env or {}
        _instanceName = str(vars?.databaseInstanceName or "")
        _labels = vars?.labels or {}
        _managementPolicies = vars?.managementPolicies or ["*"]
        _namespace = str(vars?.namespace or "")
        _projectId = str(vars?.projectId or "")
        _region = str(vars?.region or "")
        _userObjectName = str(vars?.databaseUserObjectName or "{}-db-user".format(_baseName))

        _instance = DatabaseInstance {
            apiVersion = "sql.gcp.m.upbound.io/v1beta1"
            kind = "DatabaseInstance"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "database-primary"}
                labels = _labels
                name = _instanceName
            }
            spec = {
                forProvider = {
                    databaseVersion = str(_db_spec?.databaseVersion or "POSTGRES_18")
                    deletionProtection = bool(_db_spec?.deletionProtection or False)
                    region = _region
                    rootPasswordSecretRef = {
                        key = "password"
                        name = str(_db_spec?.rootPasswordSecretName or "placeholder")
                    }
                    settings = {
                        databaseFlags = [{name = "cloudsql.iam_authentication", value = "on"}]
                        diskSize = int(_db_spec?.storageGB or 10)
                        diskType = "PD_SSD"
                        edition = "ENTERPRISE"
                        insightsConfig = {
                            queryInsightsEnabled = bool(_db_spec?.queryInsightsEnabled if _db_spec?.queryInsightsEnabled != Undefined else True)
                        }
                        ipConfiguration = {
                            ipv4Enabled = False
                            privateNetwork = "projects/{}/global/networks/{}".format(_projectId, str(_env?.vpcName or ""))
                            sslMode = "TRUSTED_CLIENT_CERTIFICATE_REQUIRED"
                        }
                        tier = str(_db_spec?.instanceType or "db-custom-1-3840")
                        userLabels = _labels | {"active": "true", "should-back-up": "true"}
                    }
                }
                if _cmkEnabled:
                    forProvider.encryptionKeyName = _cmkKeyName
                managementPolicies = _managementPolicies
            }
        }

        [
            _instance
            Database {
                apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                kind = "Database"
                metadata = {
                    annotations = {
                        "crossplane.io/composition-resource-name": "liferay-db"
                        "crossplane.io/external-name": str(vars?.databaseName or "lportal")
                    }
                    labels = _labels
                    name = _dbObjectName
                }
                spec = {
                    forProvider = {
                        charset = "UTF8"
                        collation = "en_US.UTF8"
                        instanceSelector = {matchControllerRef = True}
                    }
                    managementPolicies = _managementPolicies
                }
            }
            User {
                apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                kind = "User"
                metadata = {
                    annotations = {
                        "crossplane.io/composition-resource-name": "liferay-iam-user"
                        "crossplane.io/external-name": str(vars?.databaseIamUser or "")
                    }
                    labels = _labels
                    name = _userObjectName
                }
                spec = {
                    forProvider = {
                        instanceSelector = {matchControllerRef = True}
                        $type = "CLOUD_IAM_SERVICE_ACCOUNT"
                    }
                    managementPolicies = _managementPolicies
                }
            }
            {
                apiVersion = "protection.crossplane.io/v1beta1"
                kind = "Usage"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "usage-instance-by-database"}
                    name = "{}-instance-by-database".format(_baseName)
                    namespace = _namespace
                }
                spec = {
                    of = {
                        apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                        kind = "DatabaseInstance"
                        resourceRef = {name = _instanceName}
                    }
                    by = {
                        apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                        kind = "Database"
                        resourceRef = {name = _dbObjectName}
                    }
                }
            }
            {
                apiVersion = "protection.crossplane.io/v1beta1"
                kind = "Usage"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "usage-instance-by-user"}
                    name = "{}-instance-by-user".format(_baseName)
                    namespace = _namespace
                }
                spec = {
                    of = {
                        apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                        kind = "DatabaseInstance"
                        resourceRef = {name = _instanceName}
                    }
                    by = {
                        apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                        kind = "User"
                        resourceRef = {name = _userObjectName}
                    }
                }
            }
        ]
    }

    _vars = {
        "baseName": baseName
        "cmkEnabled": cmkEnabled
        "cmkKeyName": cmkKeyName
        "database": databaseSpec
        "databaseIamUser": databaseIamUser
        "databaseInstanceName": databaseInstanceName
        "databaseName": databaseName
        "databaseObjectName": databaseObjectName
        "databaseUserObjectName": databaseUserObjectName
        "env": env
        "labels": labels
        "managementPolicies": managementPolicies
        "namespace": namespace
        "projectId": env?.projectId or ""
        "region": region
    }

    items: {str:any} = {"items": get_items(obs, _vars)}

_sql = sql_layer {}
items_sql = _sql.items

schema storage_layer:

    get_items = lambda obs: any, vars: {str:any} -> [any] {
        _storage_spec = vars?.storage_spec or {}
        _labels = vars?.labels or {}
        _bucket_name = str(vars?.bucketName or "")
        _managementPolicies = vars?.managementPolicies or ["*"]

        [
            Bucket {
                apiVersion = "storage.gcp.m.upbound.io/v1beta1"
                kind = "Bucket"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-gcs-bucket"}
                    labels = _labels
                    name = _bucket_name
                }
                spec = {
                    forProvider = {
                        forceDestroy = bool(vars?.forceDestroy or False)
                        labels = _labels | {"active": "true", "should-back-up": "true"}
                        location = str(_storage_spec?.region or vars?.region or "")
                        storageClass = str(_storage_spec?.storageClass or "STANDARD")
                        uniformBucketLevelAccess = True
                        versioning = {enabled = bool(_storage_spec?.enableVersioning if _storage_spec?.enableVersioning != Undefined else True)}
                    }
                    if vars?.cmkEnabled:
                        forProvider.encryption = {
                            defaultKmsKeyName = str(vars?.cmkKeyName or "")
                        }
                    managementPolicies = _managementPolicies
                }
            }
            BucketIAMMember {
                apiVersion = "storage.gcp.m.upbound.io/v1beta1"
                kind = "BucketIAMMember"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-gcs-iam"}
                    labels = _labels
                }
                spec = {
                    forProvider = {
                        member = "serviceAccount:{}".format(str(vars?.gsaEmail or ""))
                        role = "roles/storage.admin"
                        bucketRef = {name = _bucket_name}
                    }
                    managementPolicies = _managementPolicies
                }
            }
        ]
    }

    _vars = {
        "bucketName": bucketName
        "cmkEnabled": cmkEnabled
        "cmkKeyName": cmkKeyName
        "forceDestroy": forceDestroy
        "gsaEmail": gsaEmail
        "labels": labels
        "managementPolicies": managementPolicies
        "region": region
        "storage_spec": storage_spec
    }

    items: {str:any} = {"items": get_items(obs, _vars)}

_storage = storage_layer {}
items_storage = _storage.items

_layer_items = lambda layer {
    _items = []
    if typeof(layer) == "list":
        _items = layer
    elif layer and "items" in layer:
        _items = layer.items
    _items
}
items = {"items": [item for layer in [items_backup,items_elasticsearch,items_init,items_k8s_resources,items_managed_service_details,items_overlay,items_security,items_sql,items_storage] for item in _layer_items(layer)]}
