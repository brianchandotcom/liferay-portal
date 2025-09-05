{{- define "liferayAwsBackupRestore.script.getDependenciesModuleOutputs" -}}
{{- $terraformOutputIsRestoring := "is_restoring" -}}
{{- $terraformOutputDbRestoreSnapshotIdentifier := "db_restore_snapshot_identifier" -}}
{{- include "liferayAwsBackupRestore.script.partial.shStart" . }}

function main {
    local db_restore_snapshot_identifier

	terraform init -input=false

    terraform plan -detailed-exitcode -input=false

    db_restore_snapshot_identifier=$( \
    	terraform \
			output \
			-raw \
			{{ $terraformOutputDbRestoreSnapshotIdentifier }} 2>/dev/null || echo "")

    if [ -n "${db_restore_snapshot_identifier}" ];
	then
        echo "Terraform output \"{{ $terraformOutputDbRestoreSnapshotIdentifier }}\" is not empty. A restore may already be in progress." >&2

        exit 1
    fi

    if [ $(terraform output -raw {{ $terraformOutputIsRestoring }}) = "true" ];
    then
        echo "Terraform output \"{{ $terraformOutputIsRestoring }}\" is set to \"true\". A restore may already be in progress." >&2

        exit 1
    fi

    terraform output -raw data_active > {{ include "liferayAwsBackupRestore.path.dataActive" . }}
	terraform output -raw data_inactive > {{ include "liferayAwsBackupRestore.path.dataInactive" . }}
	terraform output -raw s3_bucket_id_active > {{ include "liferayAwsBackupRestore.path.s3BucketIdActive" . }}
	terraform output -raw s3_bucket_id_inactive > {{ include "liferayAwsBackupRestore.path.s3BucketIdInactive" . }}
}

main
{{- end -}}

{{- define "liferayAwsBackupRestore.script.partial.shStart" -}}
#!/bin/sh
set -eu
{{- end }}