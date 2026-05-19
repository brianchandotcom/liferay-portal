<!-- argocd-aws-charts -->
## AWS Helm Charts

Charts for this PR are available at `${REGISTRY}`. To test on an AWS cluster, run:

```bash
ARGOCD_NS=$(kubectl get pods --all-namespaces --selector app.kubernetes.io/name=argocd-server --output jsonpath='{.items[0].metadata.namespace}')

kubectl patch appproject liferay-application --namespace "${ARGOCD_NS}" --type=json --patch='[{"op":"add","path":"/spec/sourceRepos/-","value":"${REGISTRY}"}]'
kubectl patch appproject liferay-infrastructure --namespace "${ARGOCD_NS}" --type=json --patch='[{"op":"add","path":"/spec/sourceRepos/-","value":"${REGISTRY}"}]'

kubectl patch application liferay-aws-infrastructure-provider --namespace "${ARGOCD_NS}" --type=json --patch='[
  {"op":"replace","path":"/spec/sources/0/repoURL","value":"${REGISTRY}"},
  {"op":"replace","path":"/spec/sources/0/targetRevision","value":"${aws_infra_provider_version}"}
]'
kubectl patch applicationset liferay-aws-applicationset --namespace "${ARGOCD_NS}" --type=json --patch='[
  {"op":"replace","path":"/spec/template/spec/sources/0/repoURL","value":"${REGISTRY}"},
  {"op":"replace","path":"/spec/template/spec/sources/0/targetRevision","value":"${aws_version}"}
]'
kubectl patch applicationset liferay-aws-infrastructure-applicationset --namespace "${ARGOCD_NS}" --type=json --patch='[
  {"op":"replace","path":"/spec/template/spec/sources/0/repoURL","value":"${REGISTRY}"},
  {"op":"replace","path":"/spec/template/spec/sources/0/targetRevision","value":"${aws_infra_version}"}
]'
```
