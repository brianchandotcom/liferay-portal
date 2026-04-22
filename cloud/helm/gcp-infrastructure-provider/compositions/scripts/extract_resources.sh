#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
RESOURCE_NAME="${1:-arz-dev2025-infra}"

refs=$(kubectl get LiferayInfrastructure "${RESOURCE_NAME}" -o json | jq -c '.spec.crossplane.resourceRefs[]')
output="["
first=true
for ref in $refs; do
  kind=$(echo $ref | jq -r '.kind')
  name=$(echo $ref | jq -r '.name')
  apiversion=$(echo $ref | jq -r '.apiVersion')
  
  group=$(echo $apiversion | cut -d'/' -f1)
  lower_kind=$(echo $kind | tr '[:upper:]' '[:lower:]')
  
  # Some kinds might need pluralization or specific names but <kind>.<group> usually works for kubectl
  resource_type="${lower_kind}.${group}"
  
  full_resource=$(kubectl get "$resource_type" "$name" -o json)
  
  anno_name=$(echo "$full_resource" | jq -r '.metadata.annotations["crossplane.io/composition-resource-name"] // empty')
  
  if [ -z "$anno_name" ]; then
    comp_name=$name
  else
    comp_name=$anno_name
  fi
  
  spec=$(echo "$full_resource" | jq -c '.spec // {}')
  metadata=$(echo "$full_resource" | jq -c '{labels: .metadata.labels, annotations: .metadata.annotations}')
  
  if [ "$first" = true ]; then
    first=false
  else
    output="$output,"
  fi
  
  output="$output{\"apiVersion\":\"$apiversion\",\"kind\":\"$kind\",\"name\":\"$comp_name\",\"spec\":$spec,\"metadata\":$metadata}"
done
output="$output]"
echo "$output" | jq . > "${SCRIPT_DIR}/../tests/cluster_extracted_resources.json"
