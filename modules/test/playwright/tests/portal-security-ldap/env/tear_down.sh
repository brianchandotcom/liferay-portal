#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

echo CURRENT_DIR_NAME=${CURRENT_DIR_NAME}

source ${CURRENT_DIR_NAME}/../../../env/common.sh

DEPENDENCIES_DIR_NAME=${CURRENT_DIR_NAME}/../dependencies

echo DEPENDENCIES_DIR_NAME=${DEPENDENCIES_DIR_NAME}

function main {
	default_tear_down

	ldap_tear_down
}

function ldap_tear_down {
	ldapdelete -cx -D "cn=admin,dc=example,dc=com" -f ${DEPENDENCIES_DIR_NAME}/removeGroups.ldif -w "secret"
	ldapdelete -cx -D "cn=admin,dc=example,dc=com" -f ${DEPENDENCIES_DIR_NAME}/removeUsers.ldif -w "secret"

	kill -INT `cat /usr/local/var/run/slapd.pid`
}

main "${@}"