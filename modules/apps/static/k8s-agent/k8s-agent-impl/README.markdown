# The K8s Agent (Native Kubernetes Integration for ConfigMaps)

The K8sAgent is an active component within DXP which integrates natively with
the Kubernetes API server to manage a particular type of Kubernetes resource.
This pattern is often called the Kubernetes Operator pattern and is a common in
Kubernetes. In this case the K8sAgent manages dynamic configurations in DXP
which originate from externally orchestrated operations.

## Enabling K8sAgent

K8sAgent requires an OSGi configuration to exist with the `pid` equal to `com.liferay.k8s.agent.configuration.v1.K8sAgentConfiguration`.

The configuration properties required to be present are:
* `namespace` - (required) The Kubernetes namespace in which DXP and all it's extension services run.
* `labelSelector` - (optional) A Kubernetes [label `selector`](https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/) used to match the labels of a subset of ConfigMap instances within the namespace. The default value is `dxp.liferay.com/configs=true`.

## Variables resulting from Kubernetes ConfigMap metadata

OSGi Configurations resulting from the K8sAgent contain several additional properties many of which are widely usable, others which are intended purely for coordination.

* `host.service.address` - Holds the primary public service address (with protocol) of the extension (service). (Origin `metadata.annotations.cloud.liferay.com/context-data.domains`)
* `host.service.domains` - A comma delimited string of all domains under which the extension (service) is provided. (Origin `metadata.annotations.cloud.liferay.com/context-data.domains`)
* `k8s.cloud.liferay.com.serviceId` - The Liferay Cloud identity of the extension (service). (Origin `metadata.labels.cloud.liferay.com/serviceId`)
* `k8s.dxp.liferay.com.configs` - A flag indicating the configuration originated from an extension (service). (Origin `metadata.labels.dxp.liferay.com/configs`)
* `k8s.projectCustomer` - A flag indicating if the project is a customer project. (Origin `metadata.labels.projectCustomer`)
* `k8s.projectId` - The Liferay Cloud project identity of the extension (service) that originated the configuration. (Origin `metadata.labels.projectId`)
* `k8s.projectSandbox` - Identity of the sandbox in which the project is running. Otherwise an empty string. (Origin `metadata.labels.projectSandbox`)
* `k8s.projectTrial` - Indicates if the project is a trial or not. (Origin `metadata.labels.projectTrial`)
* `k8s.projectType` - Indicates the project type. (Origin `metadata.labels.projectType`)
* `k8s.lxc.environment` - Identifier indicating the target LXC environment. (Origin `metadata.annotations.cloud.liferay.com/context-data.environment`)
* `.kubernetes.config.key` - The original name of the configuration in the K8s ConfigMap.
* `.kubernetes.config.uid` - The UID of the K8s ConfigMap instance which originated the configuration.
* `.kubernetes.config.resource.version` - The resource version of K8s ConfigMap instance which originated the configuration.