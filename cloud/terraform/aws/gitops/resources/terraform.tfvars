git_token_property="git_access_token"
git_username_property="git_machine_user_id"
remote_secret_key="argocd/pat/gitops-source-of-truth"
secret_store_provider_yaml_spec=<<-EOT
aws:
    auth:
        jwt:
            serviceAccountRef:
                name: argocd-secrets-sa 
                namespace: argocd
    region: us-east-1
    service: SecretsManager
EOT
