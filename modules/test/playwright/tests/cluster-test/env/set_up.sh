#!/bin/bash

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

source ${CURRENT_DIR_NAME}/../../../../../env/common.sh

function change_property {
	local portal_home="$1"
	local property_name="$2"
	local property_value="$3"

	for file in "${portal_home}"/tomcat-*/webapps/ROOT/WEB-INF/classes/*.properties
	do
		sed -i "s/${property_name}=.*/${property_name}=${property_value}/g" "${file}"
	done
}

function delete_property {
	local portal_home="$1"
	local property_name="$2"

	for file in "${portal_home}"/tomcat-*/webapps/ROOT/WEB-INF/classes/*.properties
	do
		sed -i "s/${property_name}=.*//g" "${file}"
	done
}

function main {
	echo Running default setup
	default_set_up

	echo Preparing cluster slave
	prepare_additional_bundles 1

	local slave_home="${LIFERAY_HOME}-1"

	# Set up cluster
	echo Tweaking Elastic Search
	cp "${CURRENT_DIR_NAME}/com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration.config" "${slave_home}/osgi/configs"
	sed -i "s/%LIFERAY_DOCKER_NETWORK_NAME%/${LIFERAY_DOCKER_NETWORK_NAME}/g" "${slave_home}/osgi/configs/com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration.config"

	echo "Tweaking data dir"
	rm -rf "${slave_home}/data"
	mkdir -p "${slave_home}/data"
	ln -s "${LIFERAY_HOME}/data/document_library" "${slave_home}/data"

	echo "Tweaking routes"
	for domain in "${slave_home}/routes/default/dxp/"*
	do
		sed -i 's/8080/9080/g' "${domain}"
	done

	echo "Removing unused folders"
	rm -rf "${slave_home}/elasticsearch-sidecar"
	rm -rf "${slave_home}/osgi/state"
	rm -rf "${slave_home}/osgi/tomcat/work"
	rm -rf "${slave_home}/osgi/work"

	echo "Removing configuration properties in conflict with cluster"
	for node_home in "${LIFERAY_HOME}" "${slave_home}"
	do
		delete_property "${node_home}" "web.server.http.port"
		delete_property "${node_home}" "web.server.https.port"
	done

	echo "Changing configuration properties in conflict with cluster"
	change_property "${slave_home}" "module.framework.properties.osgi.console" "localhost:11313"

	start_additional_bundles 1
}

main "${@}"