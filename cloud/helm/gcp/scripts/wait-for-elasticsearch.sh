#!/bin/bash

function main {
	echo "Waiting for Elasticsearch health to be \"green\" or \"yellow\" at \"${ELASTICSEARCH_URL}/_cluster/health\"."

	local auth_url="http://${ELASTICSEARCH_USERNAME}:${ELASTICSEARCH_PASSWORD}@${ELASTICSEARCH_URL#*//}/_cluster/health"

	until wget -qO- "${auth_url}" | grep -qE "\"status\":\"(green|yellow)\""
	do
		echo "Waiting for ES (current status: \"Red\" or \"Unreachable\")."

		sleep 5
	done

	echo "Elasticsearch is ready."
}

main