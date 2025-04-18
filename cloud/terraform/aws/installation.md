# Liferay Cloud Native AWS Installation Guide

## Prerequisites

1. Install [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html) and configure with [IAM credentials](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-quickstart.html).

1. Install [Terraform CLI](https://developer.hashicorp.com/terraform/tutorials/aws-get-started/install-cli).

1. Install [Helm CLI](https://helm.sh/docs/intro/install/).

1. Install [kubectl CLI](https://kubernetes.io/docs/tasks/tools/).

## AWS

1. Export your profile for AWS SDK and its tools.

   ```bash
   export AWS_PROFILE=[profile]
   ```

1. Log into AWS CLI.

   ```bash
   aws sso login
   ```

## Installation

Clone the repository [TBD].

Once the repository has been cloned, you have two choices:

1. Bring your own AWS account. If you have an AWS account and wish to create a new EKS cluster complete with VPC and networking, follow [EKS Cluster Bootstrap](#eks-cluster-bootstrap).

1. Bring your own EKS cluster. If you have an existing EKS cluster, follow [Liferay Infrastructure Bootstrap](#liferay-infrastructure-bootstrap).

## EKS Cluster Bootstrap

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

1. Append the result of `terraform output` to the `../dependencies/terraform.tfvars` file in the `dependencies` directory:

   ```bash
   terraform output >> ../dependencies/terraform.tfvars
   ```

## Liferay Infrastructure Bootstrap

1. Navigate to the `dependencies` directory.

1. Update the `terraform.tfvars` file to configure your infrastructure. Variables are defined in `variables.tf` file. If you followed [EKS Cluster Bootstrap](#eks-cluster-bootstrap), this file is already populated.

1. Run the following commands:

   ```bash
   terraform init
   ```

   ```bash
   terraform apply
   ```

   You are prompted to apply the changes.

## Helm Setup

To use Helm you must use the `aws` CLI to set up `kubectl`.

1. Navigate to the `dependencies` directory.

1. Run the command below:

   ```bash
   aws eks --name $(terraform output -raw cluster_name) --region $(terraform output -raw region) update-kubeconfig
   ```

1. Test that `kubectl cluster-info` works.

## Helm Chart Deployment

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
   helm upgrade liferay oci://[aws_chart] \
     --create-namespace \
     --install \
     --namespace $(terraform output -raw deployment_namespace) \
     --set "awsServiceAccountArn=$(terraform output -raw liferay_sa_role)" \
     --values ../helm/values.yaml
   ```

   If you have an externally created service account, use

   ```bash
   helm upgrade liferay oci://[aws_chart] \
     --create-namespace \
     --install \
     --namespace $(terraform output -raw deployment_namespace) \
     --set "serviceAccount.create=false" \
     --set "serviceAccount.name=${SERVICE_ACCOUNT_NAME}" \
     --values ../helm/values.yaml
   ```