#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

function main {
	local chart_directory
	local fail=0
	local pass=0

	chart_directory=$(cd "$(dirname "${0}")/.." && pwd)

	_run_test _test_init_container_absent_when_overlay_is_disabled
	_run_test _test_init_container_present_when_overlay_is_enabled
	_run_test _test_multiple_copy_blocks_each_generate_a_sync_command
	_run_test _test_uses_overridden_aws_cli_image
	_run_test _test_uses_rclone_image_by_default
	_run_test _test_volume_mount_name_tracks_overlay_init_scripts_volume_name

	echo ""
	echo "Results: ${pass} passed, ${fail} failed."

	if [ "${fail}" -eq 0 ]
	then
		return 0
	fi

	return 1
}

function _run_test {
	local description

	description=$(echo "${1}" | sed "s/^_test_//; s/_/ /g")

	if "${1}"
	then
		echo "PASS: ${description}."

		pass=$((pass + 1))
	else
		echo "FAIL: ${description}."

		fail=$((fail + 1))
	fi
}

function _test_init_container_absent_when_overlay_is_disabled {
	if ! helm template test "${chart_directory}" --set 'overlay.enabled=false' | grep --quiet "name: liferay-overlay"
	then
		return 0
	fi

	return 1
}

function _test_init_container_present_when_overlay_is_enabled {
	if helm template \
			test \
			"${chart_directory}" \
			--set 'overlay.copy[0].into=/dest' \
			--set 'overlay.enabled=true' \
			| grep --quiet "name: liferay-overlay"
	then
		return 0
	fi

	return 1
}

function _test_multiple_copy_blocks_each_generate_a_sync_command {
	local output

	output=$(helm template \
		test \
		"${chart_directory}" \
		--set 'overlay.copy[0].from=overlay-build-1/osgi/*' \
		--set 'overlay.copy[0].into=osgi/' \
		--set 'overlay.copy[1].from=overlay-build-1/configs/*.config' \
		--set 'overlay.copy[1].into=osgi/configs/' \
		--set 'overlay.enabled=true')

	if echo "${output}" | grep --quiet '"osgi/"' && echo "${output}" | grep --quiet '"osgi/configs/"'
	then
		return 0
	fi

	return 1
}

function _test_uses_overridden_aws_cli_image {
	if helm template \
			test \
			"${chart_directory}" \
			--set 'overlay.copy[0].into=/dest' \
			--set 'overlay.enabled=true' \
			--set 'overlay.image.repository=amazon/aws-cli' \
			--set 'overlay.image.tag=2.27.63' \
			| grep --quiet "image: amazon/aws-cli:2.27.63"
	then
		return 0
	fi

	return 1
}

function _test_uses_rclone_image_by_default {
	if helm template \
			test \
			"${chart_directory}" \
			--set 'overlay.copy[0].into=/dest' \
			--set 'overlay.enabled=true' \
			| grep --quiet "image: rclone/rclone:1.66"
	then
		return 0
	fi

	return 1
}

function _test_volume_mount_name_tracks_overlay_init_scripts_volume_name {
	if helm template \
			test \
			"${chart_directory}" \
			--set 'overlay.copy[0].into=/dest' \
			--set 'overlay.enabled=true' \
			--set 'overlay.initScriptsVolumeName=custom-init-scripts' \
			| grep --quiet "name: custom-init-scripts"
	then
		return 0
	fi

	return 1
}

main "${@}"