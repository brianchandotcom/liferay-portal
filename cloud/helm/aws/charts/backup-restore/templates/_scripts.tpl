{{- define "liferayAWSBackupRestore.script.getDependenciesModuleOutputs" -}}
{{- include "liferayAWSBackupRestore.script.partial.shStart" . }}

function main {
	terraform init -input=false

    terraform plan -detailed-exitcode -input=false

    local db_restore_snapshot_identifier

    db_restore_snapshot_identifier=$( \
    	terraform \
			output \
			-raw \
			db_restore_snapshot_identifier 2>/dev/null || echo "")

    if [ -n "${db_restore_snapshot_identifier}" ];
	then
        echo "Terraform output \"db_restore_snapshot_identifier\" is not empty. A restore may already be in progress." >&2

        exit 1
    fi

    if [ $(terraform output -raw is_restoring) = "true" ];
    then
        echo "Terraform output \"is_restoring\" is set to \"true\". A restore may already be in progress." >&2

        exit 1
    fi

    terraform output -raw data_active > /tmp/data-active.txt
	terraform output -raw data_inactive > /tmp/data-inactive.txt
	terraform output -raw s3_bucket_id_active > /tmp/s3-bucket-id-active.txt
	terraform output -raw s3_bucket_id_inactive > /tmp/s3-bucket-id-inactive.txt
}

main
{{- end -}}

{{- define "liferayAWSBackupRestore.script.partial.shStart" -}}
#!/bin/sh
set -eu
{{- end }}