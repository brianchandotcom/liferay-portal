#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

echo CURRENT_DIR_NAME=${CURRENT_DIR_NAME}

source ${CURRENT_DIR_NAME}/../../../env/common.sh

function ldap_set_up {
	echo "Starting slapd:"
	/usr/local/libexec/slapd -F /usr/local/etc/slapd.d

	if [ $? -ne 0 ]; then
		echo "Command failed with exit status $?"
	else
		echo "Command succeeded"
	fi

	ldapadd -cx -D "cn=admin,dc=example,dc=com" -w "secret" -f ${CURRENT_DIR_NAME}/exampleCompany.ldif

	if [ $? -ne 0 ]; then
		echo "Command failed with exit status $?"
	else
		echo "Command succeeded"
	fi

	ldapadd -cx -D "cn=admin,dc=example,dc=com" -w "secret" -f ${CURRENT_DIR_NAME}/admin.ldif

	if [ $? -ne 0 ]; then
		echo "Command failed with exit status $?"
	else
		echo "Command succeeded"
	fi

	ldapadd -cx -D "cn=admin,dc=example,dc=com" -w "secret" -f ${CURRENT_DIR_NAME}/addUsers.ldif

	if [ $? -ne 0 ]; then
		echo "Command failed with exit status $?"
	else
		echo "Command succeeded"
	fi

	ldapadd -cx -D "cn=admin,dc=example,dc=com" -w "secret" -f ${CURRENT_DIR_NAME}/addGroups.ldif

	if [ $? -ne 0 ]; then
		echo "Command failed with exit status $?"
	else
		echo "Command succeeded"
	fi
}

function main {
	default_set_up

	ldap_set_up
}

main "${@}"