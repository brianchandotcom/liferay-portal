#!/bin/sh

set -o errexit
set -o nounset

function main {
	local data_plane_key

	data_plane_key="{{ "{{" }}inputs.parameters.data-plane-key}}"

	kubectl get liferayinfrastructure \
		--output json \
		| jq \
			--arg data_plane_key "${data_plane_key}" \
			--compact-output \
			'.items[0] | ([7, ([35, (.spec.backup.retentionDays // 30)] | min)] | max) as $retention_days | {($data_plane_key): ((now + ($retention_days * 86400)) | floor | todate)}' \
		> /tmp/retained-data-plane.txt

	echo "The demoted ${data_plane_key} data plane stays restorable until $(jq --raw-output ".[]" /tmp/retained-data-plane.txt)."
}

main