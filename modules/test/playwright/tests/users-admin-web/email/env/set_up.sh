#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

source ${CURRENT_DIR_NAME}/../../../../env/common.sh
source ${CURRENT_DIR_NAME}/../../../../env/mockmock.sh

function main {
	default_set_up

	mockmock_set_up
}

main "${@}"