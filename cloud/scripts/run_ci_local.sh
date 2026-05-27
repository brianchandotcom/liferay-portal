#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

_SCRIPTS_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

function main {
	"${_SCRIPTS_DIR}/run_ci_local_terraform.sh"
	"${_SCRIPTS_DIR}/run_ci_local_helm.sh"
}

main