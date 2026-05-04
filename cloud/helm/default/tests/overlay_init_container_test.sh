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

	echo
	echo "Results: ${pass} passed, ${fail} failed."

	[ "${fail}" -eq 0 ]
}

function _run_test {
	local description="${1#_test_}"
	description="${description//_/ }"

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
	local output

	output=$(helm template test "${chart_directory}" \
		--set 'overlay.enabled=false')

	! echo "${output}" | grep --quiet "name: liferay-overlay"
}

function _test_init_container_present_when_overlay_is_enabled {
	helm template test "${chart_directory}" \
		--set 'overlay.copy[0].into=/dest' \
		--set 'overlay.enabled=true' \
		| grep --quiet "name: liferay-overlay"
}

function _test_multiple_copy_blocks_each_generate_a_sync_command {
	local output

	output=$(helm template test "${chart_directory}" \
		--set 'overlay.copy[0].from=overlay-build-1/osgi/*' \
		--set 'overlay.copy[0].into=osgi/' \
		--set 'overlay.copy[1].from=overlay-build-1/configs/*.config' \
		--set 'overlay.copy[1].into=osgi/configs/' \
		--set 'overlay.enabled=true')

	echo "${output}" | grep --quiet '"osgi/"' \
		&& echo "${output}" | grep --quiet '"osgi/configs/"'
}

function _test_uses_overridden_aws_cli_image {
	helm template test "${chart_directory}" \
		--set 'overlay.copy[0].into=/dest' \
		--set 'overlay.enabled=true' \
		--set 'overlay.image.repository=amazon/aws-cli' \
		--set 'overlay.image.tag=2.27.63' \
		| grep --quiet "image: amazon/aws-cli:2.27.63"
}

function _test_uses_rclone_image_by_default {
	helm template test "${chart_directory}" \
		--set 'overlay.copy[0].into=/dest' \
		--set 'overlay.enabled=true' \
		| grep --quiet "image: rclone/rclone:1.66"
}

function _test_volume_mount_name_tracks_overlay_init_scripts_volume_name {
	helm template test "${chart_directory}" \
		--set 'overlay.copy[0].into=/dest' \
		--set 'overlay.enabled=true' \
		--set 'overlay.initScriptsVolumeName=custom-init-scripts' \
		| grep --quiet "name: custom-init-scripts"
}

main "${@}"