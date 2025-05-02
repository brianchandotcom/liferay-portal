# Liferay Cloud Native AWS Installation Guide

Use this specialized Liferay AWS Helm chart with these instructions:

`oci://us-central1-docker.pkg.dev/liferay-artifact-registry/liferay-helm-chart/liferay-aws`

## Install the Prerequisites

1. Install [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html) and configure with [IAM credentials](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-quickstart.html).

1. Install [Terraform CLI](https://developer.hashicorp.com/terraform/tutorials/aws-get-started/install-cli).

1. Install [Git CLI](https://git-scm.com/downloads).

1. Install [Helm CLI](https://helm.sh/docs/intro/install/).

1. Install [kubectl CLI](https://kubernetes.io/docs/tasks/tools/).

## Log Into AWS

1. Export your profile for AWS SDK and its tools.

   ```bash
   export AWS_PROFILE=[profile]
   ```

1. Log into AWS CLI.

   ```bash
   aws sso login
   ```

## Clone the Terraform Files

1. Clone the terraform files from the repository:

   ```bash
   git clone -n --depth=1 --filter=tree:0 https://github.com/liferay/liferay-portal.git liferay-aws-terraform
   cd liferay-aws-terraform
   git sparse-checkout set --no-cone /cloud/terraform/aws
   git checkout
   cd cloud/terraform/aws
   ```

Once the repository has been cloned, you have two choices:

1. Create a new EKS cluster. If you want to create a new EKS cluster complete with VPC and networking, follow [Create a new EKS cluster](#create-a-new-eks-cluster).

1. Use an existing EKS cluster. If you have an existing EKS cluster, follow [Create dependent services](#create-dependent-services).

## Create a New EKS Cluster

1. Navigate to the `eks` directory.

1. Edit `terraform.tfvars` to configure your infrastructure. Variables are defined in the `variables.tf` file. By default, the system deploys an EKS cluster in the US West (Oregon) region (us-west-2) spanning two availability zones.

1. Run the following commands:

   ```bash
   terraform init
   ```

   ```bash
   terraform apply
   ```

   You are prompted to apply the changes.

1. Write the result of `terraform output` to the `../dependencies/terraform.tfvars` file in the `dependencies` directory:

   ```bash
   terraform output > ../dependencies/terraform.tfvars
   ```

## Create the Dependent Services

1. Navigate to the `dependencies` directory.

1. Update the `terraform.tfvars` file to configure your infrastructure. Variables are defined in `variables.tf` file. If you followed [Create a new EKS cluster](#create-a-new-eks-cluster), this file is already populated.

1. Run the following commands:

   ```bash
   terraform init
   ```

   ```bash
   terraform apply
   ```

   You are prompted to apply the changes.

## Set Up Helm

To use Helm you must use the `aws` CLI to set up `kubectl`.

1. Navigate to the `dependencies` directory.

1. Run the command below:

   ```bash
   aws eks update-kubeconfig \
      --name $(terraform output -raw cluster_name) \
      --region $(terraform output -raw region)
   ```

1. Test that `kubectl cluster-info` works.

## Deploy the Helm Chart

The chart expects a Kubernetes secret called `managed-service-details` in the deployment namespace containing the following data:

```yaml
apiVersion: v1
kind: Secret
metadata:
    name: managed-service-details
data:
    DATABASE_ENDPOINT: ""
    DATABASE_PASSWORD: ""
    DATABASE_PORT: ""
    DATABASE_USERNAME: ""
    OPENSEARCH_ENDPOINT: ""
    OPENSEARCH_PASSWORD: ""
    OPENSEARCH_USERNAME: ""
    S3_BUCKET_ID: ""
    S3_BUCKET_REGION: ""
```

This secret is created automatically when you run the Terraform auto-configuration. If you have your own configuration, you must provide it manually.

1. Navigate to the `dependencies` directory.

1. Run the following command:

   ```bash
   helm upgrade liferay oci://us-central1-docker.pkg.dev/liferay-artifact-registry/liferay-helm-chart/liferay-aws \
       --create-namespace \
       --install \
       --namespace $(terraform output -raw deployment_namespace) \
       --set "liferay-default.serviceAccount.annotations.eks\.amazonaws\.com/role-arn=$(terraform output -raw liferay_sa_role)" \
       --values ../helm/values.yaml \
       --version ${LIFERAY_AWS_HELM_CHART_VERSION}
   ```