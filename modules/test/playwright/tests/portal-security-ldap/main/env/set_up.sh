#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

echo CURRENT_DIR_NAME=${CURRENT_DIR_NAME}

source ${CURRENT_DIR_NAME}/../../../../env/common.sh

function execute_command {
	"${@}"

	if [ $? -ne 0 ]
	then
		echo "Command \"${@}\" failed with exit code $?."
	fi
}

function ldap_set_up {
	execute_command `/usr/local/libexec/slapd -F /usr/local/etc/slapd.d`

	local dependencies_dir_name=${CURRENT_DIR_NAME}/../dependencies

	execute_command ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${dependencies_dir_name}/exampleCompany.ldif -w "secret"

	execute_command ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${dependencies_dir_name}/admin.ldif -w "secret"

	execute_command ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${dependencies_dir_name}/addUsers.ldif -w "secret"

	execute_command ldapadd -cx -D "cn=admin,dc=example,dc=com" -f ${dependencies_dir_name}/addGroups.ldif -w "secret"
}

function main {
	default_set_up

	ldap_set_up
}

main "${@}"