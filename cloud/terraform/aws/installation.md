## Liferay Cloud Native AWS Installation Guide

### Prerequisites

1. Install [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html) and configure with [IAM credentials](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-quickstart.html).
2. Install [Terraform CLI](https://developer.hashicorp.com/terraform/tutorials/aws-get-started/install-cli).
3. Install [Helm CLI](https://helm.sh/docs/intro/install/).
4. Install [kubectl CLI](https://kubernetes.io/docs/tasks/tools/).

### AWS

1. Login to AWS cli.

```shell
# Export your profile as default for other tools that use the AWS SDK
export AWS_PROFILE=<profile>

aws sso login
```

### Installation

1. Clone the repository <TBD>

Once the repository has been cloned there are two different scenarios to choose from:

##### Bring your own AWS Account

If you have an AWS Account and wish to create an entirely new EKS Cluster complete with VPC and networking. Continue from [EKS Cluster Bootstrap](#eks-cluster-bootstrap).

##### Bring your own EKS Cluster

If you have an existing EKS cluster, continue from [Liferay Infrastructures Bootstrap](#liferay-infrastructures-bootstrap)

### EKS Cluster Bootstrap

1. Navigate to the `eks` directory
2. Customize your infrastructure configuration by editing the `terraform.tfvars` file with your desired parameters. Variables are defined in `variables.tf` file.
   If left unchanged, the system will deploy an EKS cluster in the US West (Oregon) region (us-west-2) spanning two availability zones.
3. Run the following commands:

```shell
terraform init
```

```shell
terraform apply
# note that you will be prompted to apply the changes
```

4. Append the outputs of `terraform output` to the `../dependencies/terraform.tfvars` file in the `dependencies` directory by executing the following command:

```shell
terraform output >> ../dependencies/terraform.tfvars
```

### Liferay Infrastructures Bootstrap

1. Navigate to the `dependencies` directory.
2. Update the `terraform.tfvars` file with your required configuration values. Variables are defined in `variables.tf` file.
   > _**Note** that if you followed [EKS Cluster Bootstrap](#eks-cluster-bootstrap) this file should already be populated._
3. Run the following commands:

```shell
terraform init
```

```shell
terraform apply
# note that you will be prompted to apply the changes
```

### Helm Setup

In order to use Helm we need to setup `kubectl`. Use the `aws` cli to get the EKS context setup.

1. Navigate to the `dependencies` directory.
2. Run the following commands:

```shell
aws eks --region $(terraform output -raw region) update-kubeconfig --name $(terraform output -raw cluster_name)
```

3. Test that `kubectl cluster-info` works.

### Helm Chart Deployment

The chart expects a Kubernetes Secret called `managed-service-details` to exist in the deployment namespace and contains the following data:

```yaml
apiVersion: v1
kind: Secret
metadata:
    name: managed-service-details
data:
    OPENSEARCH_ENDPOINT: ""
    OPENSEARCH_PASSWORD: ""
    OPENSEARCH_USERNAME: ""
    DATABASE_ENDPOINT: ""
    DATABASE_PASSWORD: ""
    DATABASE_PORT: ""
    DATABASE_USERNAME: ""
    S3_BUCKET_ID: ""
    S3_BUCKET_REGION: ""
```

> **Note:** This secret is created automatically from [Liferay Infrastructures Bootstrap](#liferay-infrastructures-bootstrap). But if you've skipped that then it must be provided manually.

1. Navigate to the `dependencies` directory.
2. Run the following command:

```shell
helm upgrade -i \
  liferay \
  --create-namespace \
  -f ../helm/values.yaml \
  --namespace $(terraform output -raw deployment_namespace) \
  --set "awsServiceAccountArn=$(terraform output -raw liferay_sa_role)" \
  oci://<aws_chart>
```

**Note:** If you have an externally created service account use:

```patch
-  --set "awsServiceAccountArn=$(terraform output -raw liferay_sa_role)" \
+  --set "serviceAccount.create=false" \
+  --set "serviceAccount.name=${SERVICE_ACCOUNT_NAME}" \
```
