#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

echo CURRENT_DIR_NAME=${CURRENT_DIR_NAME}

source ${CURRENT_DIR_NAME}/../../../env/common.sh

DEPENDENCIES_DIR_NAME=${CURRENT_DIR_NAME}/../dependencies

echo DEPENDENCIES_DIR_NAME=${DEPENDENCIES_DIR_NAME}

function echo_command_output {
	if [ $? -ne 0 ]
	 then
		echo "Command failed with exit status $?"
	else
		echo "Command succeeded"
	fi
}

function ldap_set_up {
	echo "Starting slapd:"
	/usr/local/libexec/slapd -F /usr/local/etc/slapd.d

	echo_command_output

	ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${DEPENDENCIES_DIR_NAME}/exampleCompany.ldif -w "secret"

	echo_command_output

	ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${DEPENDENCIES_DIR_NAME}/admin.ldif -w "secret"

	echo_command_output

	ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${DEPENDENCIES_DIR_NAME}/addUsers.ldif -w "secret"

	echo_command_output

	ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${DEPENDENCIES_DIR_NAME}/addGroups.ldif -w "secret"

	echo_command_output
}

function main {
	default_set_up

	ldap_set_up
}

main "${@}"