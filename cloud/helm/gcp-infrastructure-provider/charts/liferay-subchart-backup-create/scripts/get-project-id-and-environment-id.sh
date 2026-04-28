#!/bin/sh

set -o errexit
set -o nounset

function main {
	local liferay_infrastructure_json

	liferay_infrastructure_json=$( \
		kubectl \
			get \
			liferayinfrastructure \
			--output json \
			| jq ".items[0]")

	echo "${liferay_infrastructure_json}" | jq --raw-output ".spec.environmentId" > /tmp/environment-id.txt
	echo "${liferay_infrastructure_json}" | jq --raw-output ".spec.projectId" > /tmp/project-id.txt
}

main