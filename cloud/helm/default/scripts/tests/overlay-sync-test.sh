#!/usr/bin/env bash

set -o errexit
set -o nounset

SCRIPT="$(cd "$(dirname "${0}")/.." && pwd)/overlay-sync.sh"
pass=0
fail=0

function main {
	rclone() { echo "rclone ${*}"; }

	export -f rclone

	_run_test _test_exits_with_error_when_argument_count_is_wrong
	_run_test _test_exits_with_error_when_no_bucket_env_var_is_set
	_run_test _test_falls_back_to_liferay_gcs_bucket_name_when_primary_is_unset
	_run_test _test_falls_back_to_s3_bucket_id_when_gcs_vars_are_unset
	_run_test _test_passes_plain_path_to_rclone_without_include
	_run_test _test_splits_glob_pattern_into_path_and_include_filter
	_run_test _test_strips_wildcard_and_passes_include_for_wildcard_path
	_run_test _test_target_path_is_prefixed_with_temp
	_run_test _test_uses_liferay_overlay_bucket_name_when_set

	echo ""
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

	output=$(bash "${SCRIPT}" gcs /source 2>&1 || true)

	[[ "${output}" == *"Usage:"* ]]
}

function _test_exits_with_error_when_no_bucket_env_var_is_set {
	local output

	output=$(unset LIFERAY_OVERLAY_BUCKET_NAME LIFERAY_GCS_BUCKET_NAME S3_BUCKET_ID AZURE_CONTAINER_NAME OSS_BUCKET_NAME; bash "${SCRIPT}" gcs source/path dest/path 2>&1 || true)

	[[ "${output}" == *"No overlay bucket found"* ]]
}

function _test_falls_back_to_liferay_gcs_bucket_name_when_primary_is_unset {
	local output

	output=$(unset LIFERAY_OVERLAY_BUCKET_NAME; LIFERAY_GCS_BUCKET_NAME="gcs-fallback" bash "${SCRIPT}" gcs source/path dest/path 2>&1)

	[[ "${output}" == *":gcs:gcs-fallback/source/path"* ]]
}

function _test_falls_back_to_s3_bucket_id_when_gcs_vars_are_unset {
	local output

	output=$(unset LIFERAY_OVERLAY_BUCKET_NAME LIFERAY_GCS_BUCKET_NAME; S3_BUCKET_ID="s3-fallback" bash "${SCRIPT}" s3 source/path dest/path 2>&1)

	[[ "${output}" == *":s3:s3-fallback/source/path"* ]]
}

function _test_passes_plain_path_to_rclone_without_include {
	local output

	output=$(LIFERAY_OVERLAY_BUCKET_NAME="test-bucket" bash "${SCRIPT}" gcs source/path dest/path 2>&1)

	[[ "${output}" == *"rclone copy :gcs:test-bucket/source/path"* ]] && [[ "${output}" != *"--include"* ]]
}

function _test_splits_glob_pattern_into_path_and_include_filter {
	local output

	output=$(LIFERAY_OVERLAY_BUCKET_NAME="test-bucket" bash "${SCRIPT}" gcs "source/path/*.jar" dest/path 2>&1)

	[[ "${output}" == *"rclone copy :gcs:test-bucket/source/path"* ]] && [[ "${output}" == *"--include *.jar"* ]]
}

function _test_strips_wildcard_and_passes_include_for_wildcard_path {
	local output

	output=$(LIFERAY_OVERLAY_BUCKET_NAME="test-bucket" bash "${SCRIPT}" gcs "source/path/*" dest/path 2>&1)

	[[ "${output}" == *"rclone copy :gcs:test-bucket/source/path"* ]] && [[ "${output}" == *"--include *"* ]]
}

function _test_target_path_is_prefixed_with_temp {
	local output

	output=$(LIFERAY_OVERLAY_BUCKET_NAME="test-bucket" bash "${SCRIPT}" gcs source/path osgi/configs 2>&1)

	[[ "${output}" == *"/temp/osgi/configs"* ]]
}

function _test_uses_liferay_overlay_bucket_name_when_set {
	local output

	output=$(LIFERAY_OVERLAY_BUCKET_NAME="test-bucket" bash "${SCRIPT}" gcs source/path dest/path 2>&1)

	[[ "${output}" == *":gcs:test-bucket/source/path"* ]]
}

main "${@}"