#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

echo CURRENT_DIR_NAME=${CURRENT_DIR_NAME}

source ${CURRENT_DIR_NAME}/../../../env/common.sh

DEPENDENCIES_DIR_NAME=${CURRENT_DIR_NAME}/../dependencies

function execute_command {
	$1

	if [ $? -ne 0 ]
	then
		echo "Command failed with exit status $?"
	fi
}

function ldap_set_up {
	execute_command `/usr/local/libexec/slapd -F /usr/local/etc/slapd.d`
	execute_command `ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${DEPENDENCIES_DIR_NAME}/exampleCompany.ldif -w "secret"`
	execute_command `ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${DEPENDENCIES_DIR_NAME}/admin.ldif -w "secret"`
	execute_command `ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${DEPENDENCIES_DIR_NAME}/addUsers.ldif -w "secret"`
	execute_command `ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${DEPENDENCIES_DIR_NAME}/addGroups.ldif -w "secret"`
}

function main {
	default_set_up

	ldap_set_up
}

main "${@}"