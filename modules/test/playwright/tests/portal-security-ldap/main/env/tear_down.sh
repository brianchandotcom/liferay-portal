#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

echo CURRENT_DIR_NAME=${CURRENT_DIR_NAME}

source ${CURRENT_DIR_NAME}/../../../../env/common.sh

function ldap_tear_down {
	local dependencies_dir_name=${CURRENT_DIR_NAME}/../dependencies

	ldapdelete -cx -D "cn=admin,dc=example,dc=com" -f ${dependencies_dir_name}/removeGroups.ldif -w "secret"
	ldapdelete -cx -D "cn=admin,dc=example,dc=com" -f ${dependencies_dir_name}/removeUsers.ldif -w "secret"

	kill -INT `cat /usr/local/var/run/slapd.pid`
}

function main {
	default_tear_down

	ldap_tear_down
}

main "${@}"