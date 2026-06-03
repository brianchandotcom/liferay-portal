#!/bin/bash

TEST_SMTP_SERVER_DIR="/tmp"
TEST_SMTP_SERVER_URL="https://repository-cdn.liferay.com/nexus/service/local/repo_groups/public/content/com/liferay/com.mockmock/1.4.0/com.mockmock-1.4.0.jar"

function mockmock_set_up {
	_download_mockmock

	_start_mockmock_server
}

function _download_mockmock {
	local attempt=1
	local max_attempts=5
	local sleep_interval=20

	while ! curl --insecure --location --output "${TEST_SMTP_SERVER_DIR}/MockMock.jar" "${TEST_SMTP_SERVER_URL}"
	do
		if [ ${attempt} -ge ${max_attempts} ]
		then
			echo "Unable to download MockMock.jar after ${max_attempts} attempts."

			exit 1
		fi

		((++attempt))

		sleep ${sleep_interval}
	done

	echo "Downloaded MockMock."
}

function _start_mockmock_server {
	local sleep_duration=60
	local sleep_interval=2
	local total_duration=0

	java --add-opens java.base/java.lang=ALL-UNNAMED -jar ${TEST_SMTP_SERVER_DIR}/MockMock.jar -p 25000 &

	while ! curl --fail --head --output /dev/null --silent http://localhost:8282
	do
		if [ ${total_duration} -ge ${sleep_duration} ]
		then
			echo "Unable to start MockMock smtp server."

			exit 1
		fi

		sleep ${sleep_interval}

		total_duration=$((total_duration + sleep_interval))
	done

	echo "Started MockMock smtp server."
}