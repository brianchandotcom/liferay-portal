#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

_SCRIPTS_DIR=$(cd "$(dirname "${0}")/.." && pwd)

_AWS_SCRIPT="${_SCRIPTS_DIR}/setup_aws.sh"
_GCP_SCRIPT="${_SCRIPTS_DIR}/setup_gcp.sh"

function main {
	local fail=0
	local pass=0

	for script in "${_AWS_SCRIPT}" "${_GCP_SCRIPT}"
	do
		_run_test "${script}" _test_aborts_with_config_missing_variables_object
		_run_test "${script}" _test_aborts_with_malformed_config_json
		_run_test "${script}" _test_aborts_with_missing_config_file
		_run_test "${script}" _test_aborts_with_missing_required_utility
		_run_test "${script}" _test_aborts_with_missing_tfvars_file
		_run_test "${script}" _test_aborts_with_no_arguments
		_run_test "${script}" _test_aborts_with_old_terraform_version
	done

	echo ""
	echo "Results: ${pass} passed, ${fail} failed."

	if [ "${fail}" -eq 0 ]
	then
		return 0
	fi

	return 1
}

function _make_real_terraform_stub {
	local stub_dir="${1}"

	cat > "${stub_dir}/terraform" <<'EOF'
#!/usr/bin/env bash

if [ "${1:-}" = "--version" ]
then
	echo "Terraform v1.10.0"

	exit 0
fi

exit 0
EOF

	chmod +x "${stub_dir}/terraform"
}

function _make_stub_path {
	local stub_dir

	stub_dir=$(mktemp -d)

	for util in aws gcloud jq kubectl
	do
		local real_path

		real_path=$(command -v "${util}" 2>/dev/null || true)

		if [ -n "${real_path}" ]
		then
			ln -s "${real_path}" "${stub_dir}/${util}"
		else
			cat > "${stub_dir}/${util}" <<'EOF'
#!/usr/bin/env bash
exit 0
EOF
			chmod +x "${stub_dir}/${util}"
		fi
	done

	for util in awk basename bash dirname env head mktemp printf readarray sed sort tr
	do
		local real_path

		real_path=$(command -v "${util}" 2>/dev/null || true)

		if [ -n "${real_path}" ]
		then
			ln -s "${real_path}" "${stub_dir}/${util}"
		fi
	done

	echo "${stub_dir}"
}

function _run_test {
	local script="${1}"
	local test_function="${2}"

	local script_name

	script_name=$(basename "${script}")

	local description

	description=$(echo "${test_function}" | sed "s/^_test_//; s/_/ /g")

	local exit_code=0

	"${test_function}" "${script}" || exit_code="${?}"

	if [ "${exit_code}" -eq 0 ]
	then
		echo "PASS: [${script_name}] ${description}."

		pass=$((pass + 1))
	else
		echo "FAIL: [${script_name}] ${description}."

		fail=$((fail + 1))
	fi
}

function _test_aborts_with_config_missing_variables_object {
	local stub_dir

	stub_dir=$(_make_stub_path)

	_make_real_terraform_stub "${stub_dir}"

	local tmpdir

	tmpdir=$(mktemp -d)

	echo '{"options":{"provider":"aws"}}' > "${tmpdir}/config.json"

	touch "${tmpdir}/versions.tfvars"

	local output

	output=$(PATH="${stub_dir}" bash "${1}" "${tmpdir}/config.json" "${tmpdir}/versions.tfvars" 2>&1 || true)

	rm -rf "${stub_dir}" "${tmpdir}"

	if [[ "${output}" == *"must contain a root object named \"variables\""* ]]
	then
		return 0
	fi

	echo "Output was: ${output}" >&2

	return 1
}

function _test_aborts_with_malformed_config_json {
	local stub_dir

	stub_dir=$(_make_stub_path)

	_make_real_terraform_stub "${stub_dir}"

	local tmpdir

	tmpdir=$(mktemp -d)

	echo '{this is not valid json' > "${tmpdir}/config.json"

	touch "${tmpdir}/versions.tfvars"

	local output

	output=$(PATH="${stub_dir}" bash "${1}" "${tmpdir}/config.json" "${tmpdir}/versions.tfvars" 2>&1 || true)

	rm -rf "${stub_dir}" "${tmpdir}"

	if [[ "${output}" == *"is not valid JSON"* ]]
	then
		return 0
	fi

	echo "Output was: ${output}" >&2

	return 1
}

function _test_aborts_with_missing_config_file {
	local stub_dir

	stub_dir=$(_make_stub_path)

	_make_real_terraform_stub "${stub_dir}"

	local tmpdir

	tmpdir=$(mktemp -d)

	touch "${tmpdir}/versions.tfvars"

	local output

	output=$(PATH="${stub_dir}" bash "${1}" "${tmpdir}/does-not-exist.json" "${tmpdir}/versions.tfvars" 2>&1 || true)

	rm -rf "${stub_dir}" "${tmpdir}"

	if [[ "${output}" == *"Configuration JSON file"*"does not exist"* ]]
	then
		return 0
	fi

	echo "Output was: ${output}" >&2

	return 1
}

function _test_aborts_with_missing_required_utility {
	local stub_dir

	stub_dir=$(_make_stub_path)

	_make_real_terraform_stub "${stub_dir}"

	rm "${stub_dir}/jq"

	local tmpdir

	tmpdir=$(mktemp -d)

	echo '{"variables":{}}' > "${tmpdir}/config.json"

	touch "${tmpdir}/versions.tfvars"

	local output

	output=$(PATH="${stub_dir}" bash "${1}" "${tmpdir}/config.json" "${tmpdir}/versions.tfvars" 2>&1 || true)

	rm -rf "${stub_dir}" "${tmpdir}"

	if [[ "${output}" == *"utility jq is not installed"* ]]
	then
		return 0
	fi

	echo "Output was: ${output}" >&2

	return 1
}

function _test_aborts_with_missing_tfvars_file {
	local stub_dir

	stub_dir=$(_make_stub_path)

	_make_real_terraform_stub "${stub_dir}"

	local tmpdir

	tmpdir=$(mktemp -d)

	echo '{"variables":{}}' > "${tmpdir}/config.json"

	local output

	output=$(PATH="${stub_dir}" bash "${1}" "${tmpdir}/config.json" "${tmpdir}/does-not-exist.tfvars" 2>&1 || true)

	rm -rf "${stub_dir}" "${tmpdir}"

	if [[ "${output}" == *"Versions tfvars file"*"does not exist"* ]]
	then
		return 0
	fi

	echo "Output was: ${output}" >&2

	return 1
}

function _test_aborts_with_no_arguments {
	local output

	output=$(bash "${1}" 2>&1 || true)

	if [[ "${output}" == *"Usage:"* ]]
	then
		return 0
	fi

	echo "Output was: ${output}" >&2

	return 1
}

function _test_aborts_with_old_terraform_version {
	local stub_dir

	stub_dir=$(_make_stub_path)

	cat > "${stub_dir}/terraform" <<'EOF'
#!/usr/bin/env bash

if [ "${1:-}" = "--version" ]
then
	echo "Terraform v1.9.9"

	exit 0
fi

exit 0
EOF

	chmod +x "${stub_dir}/terraform"

	local tmpdir

	tmpdir=$(mktemp -d)

	echo '{"variables":{}}' > "${tmpdir}/config.json"

	touch "${tmpdir}/versions.tfvars"

	local output

	output=$(PATH="${stub_dir}" bash "${1}" "${tmpdir}/config.json" "${tmpdir}/versions.tfvars" 2>&1 || true)

	rm -rf "${stub_dir}" "${tmpdir}"

	if [[ "${output}" == *"below minimum version"* ]]
	then
		return 0
	fi

	echo "Output was: ${output}" >&2

	return 1
}

main "${@}"