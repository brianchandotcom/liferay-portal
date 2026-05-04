#!/usr/bin/env bash

set -o errexit
set -o nounset

function main {
	local fail=0
	local pass=0
	local script

	script=$(cd "$(dirname "${0}")/.." && pwd)/overlay-sync.sh

	function rclone {
		echo "rclone ${*}"
	}

	export -f rclone

	_run_test _test_exits_with_error_when_argument_count_is_wrong
	_run_test _test_exits_with_error_when_no_bucket_env_var_is_set
	_run_test _test_passes_plain_path_to_rclone_without_include
	_run_test _test_splits_glob_pattern_into_path_and_include_filter
	_run_test _test_strips_wildcard_and_passes_include_for_wildcard_path
	_run_test _test_target_path_is_prefixed_with_temp
	_run_test _test_uses_liferay_overlay_bucket_name_when_set

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

function _test_exits_with_error_when_argument_count_is_wrong {
	local output

	output=$(bash "${script}" gcs /source 2>&1 || true)

	[[ "${output}" == *"Usage:"* ]]
}

function _test_exits_with_error_when_no_bucket_env_var_is_set {
	local output

	output=$(unset LIFERAY_OVERLAY_BUCKET_NAME; bash "${script}" gcs source/path dest/path 2>&1 || true)

	[[ "${output}" == *"Overlay bucket does not exist"* ]]
}

function _test_passes_plain_path_to_rclone_without_include {
	local output

	output=$(LIFERAY_OVERLAY_BUCKET_NAME="test-bucket" bash "${script}" gcs source/path dest/path 2>&1)

	[[ "${output}" == *"rclone copy :gcs:test-bucket/source/path"* ]] && [[ "${output}" != *"--include"* ]]
}

function _test_splits_glob_pattern_into_path_and_include_filter {
	local output

	output=$(LIFERAY_OVERLAY_BUCKET_NAME="test-bucket" bash "${script}" gcs "source/path/*.jar" dest/path 2>&1)

	[[ "${output}" == *"rclone copy :gcs:test-bucket/source/path"* ]] && [[ "${output}" == *"--include *.jar"* ]]
}

function _test_strips_wildcard_and_passes_include_for_wildcard_path {
	local output

	output=$(LIFERAY_OVERLAY_BUCKET_NAME="test-bucket" bash "${script}" gcs "source/path/*" dest/path 2>&1)

	[[ "${output}" == *"rclone copy :gcs:test-bucket/source/path"* ]] && [[ "${output}" == *"--include *"* ]]
}

function _test_target_path_is_prefixed_with_temp {
	local output

	output=$(LIFERAY_OVERLAY_BUCKET_NAME="test-bucket" bash "${script}" gcs source/path osgi/configs 2>&1)

	[[ "${output}" == *"/temp/osgi/configs"* ]]
}

function _test_uses_liferay_overlay_bucket_name_when_set {
	local output

	output=$(LIFERAY_OVERLAY_BUCKET_NAME="test-bucket" bash "${script}" gcs source/path dest/path 2>&1)

	[[ "${output}" == *":gcs:test-bucket/source/path"* ]]
}

main "${@}"