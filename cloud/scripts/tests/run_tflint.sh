set -o errexit
set -o nounset
set -o pipefail

function main {
	local requested_module="${1:-}"

	local terraform_modules=(
		aws/dependencies
		aws/ecr
		aws/eks
		aws/gitops/platform
		aws/gitops/resources
		gcp/gitops/platform
		gcp/gitops/resources
		gcp/gke
	)

	if [[ -n "${requested_module}" ]]
	then
		terraform_modules=("${requested_module}")
	fi

	local script_dir
	script_dir=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

	local terraform_dir
	terraform_dir=$(cd "${script_dir}/../../terraform" && pwd)

	for terraform_module in "${terraform_modules[@]}"
	do
		local cloud="${terraform_module%%/*}"

		local config="${terraform_dir}/${cloud}/.tflint.hcl"

		cd "${terraform_dir}/${terraform_module}"

		terraform init -backend=false -input=false

		tflint --config="${config}" --init

		tflint --config="${config}"
	done
}

main "${@}"