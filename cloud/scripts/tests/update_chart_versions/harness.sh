#!/usr/bin/env bash

set -o nounset
set -o pipefail

_TEST_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

_SCRIPT_PATH="${_TEST_DIR}/../../update_chart_versions.sh"

_FAILED=0

function _fail {
	echo "FAIL: ${*}" >&2

	_FAILED=1
}

function _pass {
	echo "PASS: ${*}"
}

function _setup_workdir {
	WORK_DIR=$(mktemp -d)

	export TEST_LOG="${WORK_DIR}/test.log"

	: > "${TEST_LOG}"

	export PATH="${_TEST_DIR}/stubs:${PATH}"
}

function _teardown_workdir {
	rm -rf "${WORK_DIR}"
}

function test_bug1_no_gnu_only_flags_in_script {
	echo "--- Test 1: Bug 1 (GNU-only flags removed from script source) ---"

	if grep -n -- "--in-place" "${_SCRIPT_PATH}"
	then
		_fail "Bug 1: script still uses '--in-place'"

		return
	fi

	if grep -n -- "--delimiter=" "${_SCRIPT_PATH}"
	then
		_fail "Bug 1: script still uses '--delimiter='"

		return
	fi

	if grep -n -- "--fields=" "${_SCRIPT_PATH}"
	then
		_fail "Bug 1: script still uses '--fields='"

		return
	fi

	if grep -n -- "yq --inplace" "${_SCRIPT_PATH}"
	then
		_fail "Bug 1: script still uses 'yq --inplace'"

		return
	fi

	_pass "Bug 1: script source clean of GNU-only flags"
}

function test_bug1_update_chart_dependency_version_runs_without_gnu_flags {
	echo "--- Test 1b: Bug 1 (functional: _update_chart_dependency_version rewrites via mv) ---"

	_setup_workdir

	local subchart_dir="${WORK_DIR}/example"
	local parent_dir="${WORK_DIR}/parent"

	mkdir -p "${subchart_dir}" "${parent_dir}"

	cp "${_TEST_DIR}/fixtures/four-space-multiline.Chart.yaml" "${subchart_dir}/Chart.yaml"
	cp "${_TEST_DIR}/fixtures/parent.Chart.yaml" "${parent_dir}/Chart.yaml"

	export _NO_MAIN=1

	set +o errexit
	source "${_SCRIPT_PATH}"

	_ROOT_CLOUD_DIR="${WORK_DIR}"

	_update_chart_dependency_version "example" "${subchart_dir}/Chart.yaml" "1.2.4"
	local rc="${?}"
	set -o errexit

	unset _NO_MAIN

	if [[ "${rc}" -ne 0 ]]
	then
		_fail "Bug 1b: _update_chart_dependency_version exited non-zero: ${rc}"

		_teardown_workdir

		return
	fi

	if ! grep -q "version: 1.2.4" "${parent_dir}/Chart.yaml"
	then
		_fail "Bug 1b: parent Chart.yaml did not receive new version"

		_teardown_workdir

		return
	fi

	if ! grep -q "version: 5.5.5" "${parent_dir}/Chart.yaml"
	then
		_fail "Bug 1b: parent Chart.yaml lost unrelated dependency version"

		_teardown_workdir

		return
	fi

	_pass "Bug 1b: dependency version rewritten without GNU flags"

	_teardown_workdir
}

function test_bug2_yq_not_invoked_inplace_and_only_version_line_changes {
	echo "--- Test 2: Bug 2 (yq never invoked --inplace; only version: line rewritten) ---"

	_setup_workdir

	local chart_dir="${WORK_DIR}/example"

	mkdir -p "${chart_dir}"

	cp "${_TEST_DIR}/fixtures/four-space-multiline.Chart.yaml" "${chart_dir}/Chart.yaml"

	local before_file="${WORK_DIR}/before.yaml"

	cp "${chart_dir}/Chart.yaml" "${before_file}"

	export _NO_MAIN=1

	set +o errexit
	(
		source "${_SCRIPT_PATH}"

		_bump_chart_yaml_version "${chart_dir}/Chart.yaml"
	) > "${WORK_DIR}/bump.out"
	local rc="${?}"
	set -o errexit

	unset _NO_MAIN

	if [[ "${rc}" -ne 0 ]]
	then
		_fail "Bug 2: _bump_chart_yaml_version exited non-zero: ${rc}"

		_teardown_workdir

		return
	fi

	if grep -q "STUB-FAIL" "${TEST_LOG}"
	then
		_fail "Bug 2: yq stub recorded an in-place invocation"

		cat "${TEST_LOG}" >&2

		_teardown_workdir

		return
	fi

	local diff_output

	diff_output=$(diff "${before_file}" "${chart_dir}/Chart.yaml" || true)

	local changed_line_count

	changed_line_count=$(echo "${diff_output}" | grep -cE "^[<>] ")

	if [[ "${changed_line_count}" -ne 2 ]]
	then
		_fail "Bug 2: expected exactly 2 changed lines (one < / one >), got ${changed_line_count}"

		echo "${diff_output}" >&2

		_teardown_workdir

		return
	fi

	if ! echo "${diff_output}" | grep -qE "^< version: 1\.2\.3"
	then
		_fail "Bug 2: expected old version line in diff"

		echo "${diff_output}" >&2

		_teardown_workdir

		return
	fi

	if ! echo "${diff_output}" | grep -qE "^> version: 1\.2\.4"
	then
		_fail "Bug 2: expected new version line in diff"

		echo "${diff_output}" >&2

		_teardown_workdir

		return
	fi

	local before_tail

	before_tail=$(tail -c 1 "${before_file}" | xxd -p)

	local after_tail

	after_tail=$(tail -c 1 "${chart_dir}/Chart.yaml" | xxd -p)

	if [[ "${before_tail}" != "${after_tail}" ]]
	then
		_fail "Bug 2: trailing byte changed (before=${before_tail}, after=${after_tail})"

		_teardown_workdir

		return
	fi

	if ! grep -qE "^    - name: child" "${chart_dir}/Chart.yaml"
	then
		_fail "Bug 2: four-space indented dependency block was reformatted"

		_teardown_workdir

		return
	fi

	if ! grep -qE "^    that spans several lines" "${chart_dir}/Chart.yaml"
	then
		_fail "Bug 2: multi-line description block was reformatted"

		_teardown_workdir

		return
	fi

	_pass "Bug 2: only top-level version: line rewritten; indentation, multi-line, no-trailing-newline preserved"

	_teardown_workdir
}

function test_bug3_no_args_prints_usage_and_exits_1 {
	echo "--- Test 3a: Bug 3 (main with no args -> usage + exit 1) ---"

	_setup_workdir

	set +o errexit
	"${_SCRIPT_PATH}" > "${WORK_DIR}/stdout" 2> "${WORK_DIR}/stderr"
	local rc="${?}"
	set -o errexit

	if [[ "${rc}" -ne 1 ]]
	then
		_fail "Bug 3a: expected exit 1 with no args, got ${rc}"

		_teardown_workdir

		return
	fi

	if ! grep -q "Usage:" "${WORK_DIR}/stderr"
	then
		_fail "Bug 3a: expected 'Usage:' on stderr"

		cat "${WORK_DIR}/stderr" >&2

		_teardown_workdir

		return
	fi

	_pass "Bug 3a: no-args prints usage + exits 1"

	_teardown_workdir
}

function test_bug3_with_arg_only_processes_that_chart {
	echo "--- Test 3b: Bug 3 (main <chart-dir> processes only that chart) ---"

	_setup_workdir

	local chart_a="${WORK_DIR}/a"
	local chart_b="${WORK_DIR}/b"

	mkdir -p "${chart_a}" "${chart_b}"

	cp "${_TEST_DIR}/fixtures/four-space-multiline.Chart.yaml" "${chart_a}/Chart.yaml"
	cp "${_TEST_DIR}/fixtures/four-space-multiline.Chart.yaml" "${chart_b}/Chart.yaml"

	local before_b

	before_b=$(cat "${chart_b}/Chart.yaml")

	set +o errexit
	"${_SCRIPT_PATH}" "${chart_a}" > "${WORK_DIR}/stdout" 2> "${WORK_DIR}/stderr"
	local rc="${?}"
	set -o errexit

	if [[ "${rc}" -ne 0 ]]
	then
		_fail "Bug 3b: expected exit 0 with one chart arg, got ${rc}"

		cat "${WORK_DIR}/stderr" >&2

		_teardown_workdir

		return
	fi

	if ! grep -q "version: 1.2.4" "${chart_a}/Chart.yaml"
	then
		_fail "Bug 3b: chart_a was not bumped"

		_teardown_workdir

		return
	fi

	local after_b

	after_b=$(cat "${chart_b}/Chart.yaml")

	if [[ "${before_b}" != "${after_b}" ]]
	then
		_fail "Bug 3b: chart_b (not passed) was modified"

		_teardown_workdir

		return
	fi

	_pass "Bug 3b: only the chart passed as an arg was processed"

	_teardown_workdir
}

function test_bug4_caret_blame_sha_handled {
	echo "--- Test 4: Bug 4 (^sha from blame is normalized and unverifiable sha skips) ---"

	_setup_workdir

	local chart_dir="${WORK_DIR}/example"

	mkdir -p "${chart_dir}"

	cp "${_TEST_DIR}/fixtures/four-space-multiline.Chart.yaml" "${chart_dir}/Chart.yaml"

	local before_file="${WORK_DIR}/before.yaml"

	cp "${chart_dir}/Chart.yaml" "${before_file}"

	export STUB_GIT_BLAME_OUTPUT="^deadbeef (Someone 2025-01-01 00:00:00 +0000 1) version: 1.2.3"
	export STUB_GIT_REV_PARSE_FAIL=1

	set +o errexit
	"${_SCRIPT_PATH}" "${chart_dir}" > "${WORK_DIR}/stdout" 2> "${WORK_DIR}/stderr"
	local rc="${?}"
	set -o errexit

	unset STUB_GIT_BLAME_OUTPUT
	unset STUB_GIT_REV_PARSE_FAIL

	if [[ "${rc}" -ne 0 ]]
	then
		_fail "Bug 4: expected exit 0 (skip), got ${rc}"

		cat "${WORK_DIR}/stderr" >&2

		_teardown_workdir

		return
	fi

	if ! grep -q "Skipping" "${WORK_DIR}/stderr"
	then
		_fail "Bug 4: expected 'Skipping' message on stderr"

		cat "${WORK_DIR}/stderr" >&2

		_teardown_workdir

		return
	fi

	if ! diff -q "${before_file}" "${chart_dir}/Chart.yaml"
	then
		_fail "Bug 4: fixture was modified when it should have been skipped"

		_teardown_workdir

		return
	fi

	local stripped_sha_invocations

	stripped_sha_invocations=$(grep -E "git rev-parse --quiet --verify deadbeef" "${TEST_LOG}" || true)

	if [[ -z "${stripped_sha_invocations}" ]]
	then
		_fail "Bug 4: expected rev-parse to be called with stripped sha 'deadbeef' (no caret)"

		cat "${TEST_LOG}" >&2

		_teardown_workdir

		return
	fi

	_pass "Bug 4: caret-prefixed sha stripped; unverifiable boundary skips cleanly"

	_teardown_workdir
}

test_bug1_no_gnu_only_flags_in_script
test_bug1_update_chart_dependency_version_runs_without_gnu_flags
test_bug2_yq_not_invoked_inplace_and_only_version_line_changes
test_bug3_no_args_prints_usage_and_exits_1
test_bug3_with_arg_only_processes_that_chart
test_bug4_caret_blame_sha_handled

if [[ "${_FAILED}" -ne 0 ]]
then
	echo ""
	echo "HARNESS FAILED"

	exit 1
fi

echo ""
echo "HARNESS PASSED"