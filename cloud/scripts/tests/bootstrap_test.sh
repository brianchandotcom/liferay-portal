#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

_SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source "${_SCRIPT_DIR}/../bootstrap.sh"

function curl {
	local arg
	local capture=""
	local output=""
	local url=""

	for arg in "${@}"
	do
		if [[ "${capture}" = "yes" ]]
		then
			output="${arg}"
			capture=""

			continue
		fi

		if [[ "${arg}" = "--output" ]]
		then
			capture="yes"
		fi

		if [[ "${arg}" == http://* || "${arg}" == https://* ]]
		then
			url="${arg}"
		fi
	done

	if [[ "${url}" == https://storage.googleapis.com/storage/v1/* ]]
	then
		printf '%s' "${CURL_STUB_METADATA_JSON-}"

		return 0
	fi

	if [[ "${url}" == *.sha256 ]]
	then
		if [[ "${CURL_STUB_SIDECAR_FAIL-}" = "true" ]]
		then
			return 22
		fi

		printf '%s' "${CURL_STUB_SIDECAR_BODY-}" > "${output}"

		return 0
	fi

	printf '%s' "${CURL_STUB_TARBALL_BODY-}" > "${output}"
}
export -f curl

function main {
	_test_bad_format_fails
	_test_mismatch_fails
	_test_missing_sidecar_fails
	_test_success

	echo "All tests passed."
}

function tar {
	return 0
}
export -f tar

function _assert_fails_with {
	local expected="${2}"
	local label="${1}"

	shift 2

	local output

	if output=$("${@}" 2>&1)
	then
		echo "FAIL: ${label} (expected failure, got success)" >&2

		exit 1
	fi

	if [[ "${output}" == *"${expected}"* ]]
	then
		echo "PASS: ${label}"
	else
		echo "FAIL: ${label} (expected error containing '${expected}', got '${output}')" >&2

		exit 1
	fi
}

function _assert_succeeds {
	local label="${1}"

	shift

	if "${@}" > /dev/null
	then
		echo "PASS: ${label}"
	else
		echo "FAIL: ${label} (expected success, got exit ${?})" >&2

		exit 1
	fi
}

function _setup_test {
	_workdir=$(mktemp -d)

	cd "${_workdir}"

	export CURL_STUB_METADATA_JSON='{"items":[{"name":"bootstrap/liferay-aws-bootstrap/liferay-aws-bootstrap-1.2.3.tar.gz","updated":"2026-01-01T00:00:00Z"}]}'
	export CURL_STUB_TARBALL_BODY="tarball-bytes"

	local digest

	digest=$(printf '%s' "${CURL_STUB_TARBALL_BODY}" | _sha256 | awk '{print $1}')

	export CURL_STUB_SIDECAR_BODY="${digest}  liferay-aws-bootstrap-1.2.3.tar.gz"

	unset CURL_STUB_SIDECAR_FAIL
}

function _teardown_test {
	cd /

	find "${_workdir}" -depth -delete

	unset CURL_STUB_METADATA_JSON
	unset CURL_STUB_SIDECAR_BODY
	unset CURL_STUB_SIDECAR_FAIL
	unset CURL_STUB_TARBALL_BODY
}

function _test_bad_format_fails {
	_setup_test

	export CURL_STUB_SIDECAR_BODY="not-hex  whatever"

	_assert_fails_with "verify rejects non-hex checksum format" \
		"Invalid expected checksum format" \
		_download_and_extract_files "" aws latest

	_teardown_test
}

function _test_mismatch_fails {
	_setup_test

	export CURL_STUB_SIDECAR_BODY="0000000000000000000000000000000000000000000000000000000000000000  whatever"

	_assert_fails_with "verify fails on mismatched digest" \
		"Checksum verification failed" \
		_download_and_extract_files "" aws latest

	_teardown_test
}

function _test_missing_sidecar_fails {
	_setup_test

	export CURL_STUB_SIDECAR_FAIL=true

	_assert_fails_with "errors with context when sidecar download fails" \
		"Unable to download checksum" \
		_download_and_extract_files "" aws latest

	_teardown_test
}

function _test_success {
	_setup_test

	_assert_succeeds "verify succeeds with matching digest" \
		_download_and_extract_files "" aws latest

	_teardown_test
}

main