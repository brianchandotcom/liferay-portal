#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

echo CURRENT_DIR_NAME=${CURRENT_DIR_NAME}

source ${CURRENT_DIR_NAME}/../../../env/common.sh

function main {
	default_tear_down

	ldap_tear_down
}

function ldap_tear_down {
	# Remove Groups and Users
	local deleteGroupsAndUsersLdif="${CURRENT_DIR_NAME}/deleteGroupsAndUsers.ldif"

	ldapdelete -cx -D "cn=admin,dc=example,dc=com" -w "secret" -f ${deleteGroupsAndUsersLdif}

	# Stop slapd service
	kill -INT `cat /usr/local/var/run/slapd.pid`
}

main "${@}"