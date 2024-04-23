#!/bin/bash

function combine_properties_files {
	local temp_properties_file=temp.properties

	echo "" > ${temp_properties_file}

	for properties_file in ${@}
	do
		if [ ! -f "${properties_file}" ]
		then
			continue
		fi

		echo -e "\n# From ${properties_file}\n" >> ${temp_properties_file}

		while IFS='=' read -r property_name property_value || [ -n "${property_name}" ]
		do
			if [[ ${property_name} =~ ^\ *# ]] || [[ ${property_name} =~ ^\ *\/\/ ]] || [[ -z "${property_name}" ]]
			then
				continue
			fi

			property_name=$(echo "${property_name}" | sed 's/^\ *//;s/\ *$//')
			property_value=$(echo "${property_value}" | sed 's/^\ *//;s/\ *$//')

			while [[ ${property_value} =~ \\$ ]]
			do
				read -r property_value_next

				property_value="${property_value%\\}${property_value_next}"
			done

			sed -i "/^${property_name}=/d" ${temp_properties_file}

			echo "${property_name}=${property_value}" >> ${temp_properties_file}
		done < "${properties_file}"
	done

	mv ${temp_properties_file} ${1}

	echo ""
	echo "##"
	echo "## ${1}"
	echo "##"
	echo ""

	cat ${1}
}

function default_set_up {
	update_portal_ext_properties

	start_app_server

	deploy_parent_project_osgi_modules

	deploy_project_osgi_modules

	deploy_parent_project_deploy_folder

	deploy_project_deploy_folder

	deploy_parent_project_osgi_configs

	deploy_project_osgi_configs

	deploy_parent_project_client_extensions

	deploy_project_client_extensions
}

function default_tear_down {
	stop_app_server
}

function deploy_client_extensions {
	if [[ -n ${1} ]]
	then
		for client_extension_name in ${@}
		do
			local client_extension_dir=$(find ${_PORTAL_PROJECT_DIR}/workspaces -type d -name "${client_extension_name}" | grep -v .releng | grep -v .npmscripts)

			if [[ -d ${client_extension_dir} ]]
			then
				echo "Deploy '${client_extension_dir}'"

				cd ${client_extension_dir}

				local gradlew=$(get_gradlew)

				${gradlew} deploy -Pliferay.workspace.home.dir=${LIFERAY_HOME}

				wait_for_portal_log_inactivity
			fi
		done
	fi
}

function deploy_deploy_folder {
	mkdir -p ${LIFERAY_HOME}/deploy

	local playwright_project_dir=${1}

	if [[ -d ${playwright_project_dir}/env/deploy ]]
	then
		cp -r ${playwright_project_dir}/env/deploy/ ${LIFERAY_HOME}/deploy
	fi
}

function deploy_osgi_configs {
	mkdir -p ${LIFERAY_HOME}/osgi/configs

	local playwright_project_dir=${1}

	if [[ -d ${playwright_project_dir}/env/osgi/configs ]]
	then
		cp -r ${playwright_project_dir}/env/osgi/configs/. ${LIFERAY_HOME}/osgi/configs
	fi
}

function deploy_osgi_modules {
	if [[ -n ${1} ]]
	then
		mkdir -p ${LIFERAY_HOME}/deploy

		for osgi_module_name in ${@}
		do
			local osgi_module_dir=$(find ${_PORTAL_PROJECT_DIR}/modules -type d -name "${osgi_module_name}" | grep -v .releng | grep -v .npmscripts)

			if [[ -f ${osgi_module_dir}/build.gradle ]]
			then
				echo "Deploying ${osgi_module_dir}"

				cd ${osgi_module_dir}

				local gradlew=$(get_gradlew)

				${gradlew} deploy

				wait_for_portal_log_inactivity
			fi
		done
	fi
}

function deploy_parent_project_client_extensions {
	for parent_playwright_project_dir in $(get_parent_playwright_project_dirs)
	do
		if [[ -f ${parent_playwright_project_dir}/env/client-extensions.list ]]
		then
			deploy_client_extensions $(cat ${parent_playwright_project_dir}/env/client-extensions.list)
		fi
	done
}

function deploy_parent_project_deploy_folder {
	mkdir -p ${LIFERAY_HOME}/deploy

	for parent_playwright_project_dir in $(get_parent_playwright_project_dirs)
	do
		deploy_deploy_folder ${parent_playwright_project_dir}
	done
}

function deploy_parent_project_osgi_configs {
	mkdir -p ${LIFERAY_HOME}/osgi/configs

	for parent_playwright_project_dir in $(get_parent_playwright_project_dirs)
	do
		deploy_osgi_configs ${parent_playwright_project_dir}
	done
}

function deploy_parent_project_osgi_modules {
	for parent_playwright_project_dir in $(get_parent_playwright_project_dirs)
	do
		if [[ -f ${parent_playwright_project_dir}/env/osgi-modules.list ]]
		then
			deploy_osgi_modules $(cat ${parent_playwright_project_dir}/env/osgi-modules.list)
		fi
	done
}

function deploy_project_client_extensions {
	local playwright_project_dir=$(get_playwright_project_dir)

	if [[ -f ${playwright_project_dir}/env/client-extensions.list ]]
	then
		deploy_client_extensions $(cat ${playwright_project_dir}/env/client-extensions.list)
	fi
}

function deploy_project_deploy_folder {
	deploy_deploy_folder $(get_playwright_project_dir)
}

function deploy_project_osgi_configs {
	deploy_osgi_configs $(get_playwright_project_dir)
}

function deploy_project_osgi_modules {
	local playwright_project_dir=$(get_playwright_project_dir)

	if [[ -f ${playwright_project_dir}/env/osgi-modules.list ]]
	then
		deploy_osgi_modules $(cat ${playwright_project_dir}/env/osgi-modules.list)
	fi
}

function get_absolute_dir {
	echo $(cd -- $(dirname -- $1) &> /dev/null && pwd)
}

function get_gradlew {
	if [[ -e ./gradlew ]]
	then
		echo "$(pwd)/gradlew"
	elif [[ $(pwd) == / ]]
	then
		echo "Unable to find gradlew."

		exit 1
	else
		echo $(cd .. ; get_gradlew)
	fi
}

function get_parent_playwright_project_dirs {
	local playwright_project_dir=${1}

	if [[ "${playwright_project_dir}" == "" ]]
	then
		playwright_project_dir=$(get_playwright_project_dir)
	fi

	current_playwright_project_dir=${playwright_project_dir}

	while [[ "${current_playwright_project_dir}" != "/" ]] && [[ "${current_playwright_project_dir}" != "${_PLAYWRIGHT_BASE_DIR}" ]]
	do
		current_playwright_project_dir=$(dirname "${current_playwright_project_dir}")

		echo ${current_playwright_project_dir}
	done
}

function get_parent_portal_ext_properties_files {
	for parent_playwright_project_dir in $(reverse $(get_parent_playwright_project_dirs))
	do
		if [[ -f ${parent_playwright_project_dir}/env/portal-ext.properties ]]
		then
			echo ${parent_playwright_project_dir}/env/portal-ext.properties
		fi
	done
}

function get_playwright_project_dir {
	find ${_PLAYWRIGHT_BASE_DIR} -name config.ts -type f -print | xargs grep "name: '${PLAYWRIGHT_PROJECT_NAME}'" | sed -n 's/\(.*\)\/config.ts.*/\1/p'
}

function get_portal_log_file_size {
	wc --lines --total=always ${LIFERAY_HOME}/logs/liferay.*.log | grep total | awk '{print $1}'
}

function get_tomcat_dir {
	find ${LIFERAY_HOME} -type d -name "tomcat*"
}

function get_tomcat_portal_ext_properties_file {
	find ${LIFERAY_HOME} -type f -name "portal-ext.properties"
}

function main {
	local playwright_env_dir=$(dirname ${BASH_SOURCE[0]})

	_PLAYWRIGHT_BASE_DIR=$(get_absolute_dir ${playwright_env_dir}/../..)
	_PORTAL_PROJECT_DIR=$(get_absolute_dir ${playwright_env_dir}/../../../../..)

	if [[ "${LIFERAY_HOME}" == "" ]]
	then
		echo "Set the environment variable LIFERAY_HOME."

		exit 1
	fi

	if [[ "${LIFERAY_PORTAL_URL}" == "" ]]
	then
		echo "Set the environment variable LIFERAY_PORTAL_URL."

		exit 1
	fi

	if [[ "${PLAYWRIGHT_PROJECT_NAME}" == "" ]]
	then
		echo "Set the environment variable PLAYWRIGHT_PROJECT_NAME."

		exit 1
	fi
}

function reverse {
	local array=(${@})

	for ((i = ${#array[@]} - 1; i >= 0; i--))
	do
	  echo "${array[$i]}"
	done
}

function start_analytics_cloud {
	cd ${_PORTAL_PROJECT_DIR}

	ant -f build-test-analytics-cloud.xml start-analytics-cloud
}

function start_app_server {
	cd $(get_tomcat_dir)/bin

	/bin/bash catalina.sh run &

	while ! curl --output /dev/null --silent --head --fail ${LIFERAY_PORTAL_URL}
	do
		sleep 5
	done

	wait_for_portal_log_inactivity

	echo "${LIFERAY_PORTAL_URL} is now available."
}

function start_client_extension_spring_boot_application {
	local client_extension_dir=${_PORTAL_PROJECT_DIR}/${1}

	if [[ -d ${client_extension_dir} ]]
	then
		local spring_boot_class_name=$(find ${client_extension_dir} -name "*SpringBootApplication.java" | grep "SpringBootApplication.java" | xargs basename | sed 's/.java//')

		echo "Starting ${spring_boot_class_name}."

		cd ${client_extension_dir}

		local portal_url_hostname=$(echo ${LIFERAY_PORTAL_URL} | awk -F:// '{print $2}')

		echo "${portal_url_hostname}" > ${LIFERAY_HOME}/routes/default/dxp/com.liferay.lxc.dxp.domains
		echo "${portal_url_hostname}" > ${LIFERAY_HOME}/routes/default/dxp/com.liferay.lxc.dxp.main.domain
		echo "${portal_url_hostname}" > ${LIFERAY_HOME}/routes/default/dxp/com.liferay.lxc.dxp.mainDomain

		local portal_url_scheme=$(echo ${LIFERAY_PORTAL_URL} | awk -F:// '{print $1}')

		echo "${portal_url_scheme}" > ${LIFERAY_HOME}/routes/default/dxp/com.liferay.lxc.dxp.server.protocol

		$(get_gradlew) bootRun -Pliferay.workspace.home.dir=${LIFERAY_HOME} &

		local sleep_duration=60
		local sleep_interval=5
		local total_duration=0

		while ! curl --output /dev/null --silent --head --fail http://localhost:58081/ready
		do
			if [ ${total_duration} -ge ${sleep_duration} ]; then
				echo "Unable to start ${spring_boot_class_name}."

				exit 1
			fi

			sleep ${sleep_interval}

			total_duration=$((total_duration + sleep_interval))
		done

		sleep ${sleep_interval}

		echo "Started ${spring_boot_class_name}."
	else
		echo "The directory ${client_extension_dir} does not exist."
	fi
}

function stop_analytics_cloud {
	cd ${_PORTAL_PROJECT_DIR}

	ant -f build-test-analytics-cloud.xml stop-analytics-cloud
}

function stop_app_server {
	cd $(get_tomcat_dir)/bin

	/bin/bash shutdown.sh &

	while curl --output /dev/null --silent --head --fail ${LIFERAY_PORTAL_URL}
	do
		sleep 5
	done

	echo "${LIFERAY_PORTAL_URL} is no longer available."
}

function stop_client_extension_spring_boot_application {
	local client_extension_dir=${_PORTAL_PROJECT_DIR}/${1}

	if [[ -d ${client_extension_dir} ]]
	then
		local spring_boot_class_name=$(find ${client_extension_dir} -name "*SpringBootApplication.java" | grep "SpringBootApplication.java" | xargs basename | sed 's/.java//')

		echo "Stopping ${spring_boot_class_name}."

		kill -SIGTERM $(jps | grep ${spring_boot_class_name} | awk '{print $1}')

		local sleep_duration=60
		local sleep_interval=5
		local total_duration=0

		while curl --output /dev/null --silent --head --fail http://localhost:58081/ready
		do
			if [ ${total_duration} -ge ${sleep_duration} ]; then
				echo "Unable to start ${spring_boot_class_name}."

				exit 1
			fi

			sleep ${sleep_interval}

			total_duration=$((total_duration + sleep_interval))
		done

		sleep ${sleep_interval}

		echo "Stopped ${spring_boot_class_name}."
	else
		echo "The directory ${client_extension_dir} does not exist."
	fi
}

function update_portal_ext_properties {
	combine_properties_files \
		$(get_tomcat_portal_ext_properties_file) \
		\
		$(get_parent_portal_ext_properties_files) \
		\
		$(get_playwright_project_dir)/env/portal-ext.properties
}

function wait_for_portal_log_inactivity {
	local portal_log_file_size=$(get_portal_log_file_size)

	local sleep_interval=15
	local sleep_duration=180
	local total_duration=0

	sleep ${sleep_interval}

	while [[ ${portal_log_file_size} != $(get_portal_log_file_size) ]]
	do
		portal_log_file_size=$(get_portal_log_file_size)

		if [[ ${total_duration} -ge ${sleep_duration} ]]
		then
			break
		fi

		sleep ${sleep_interval}

		total_duration=$((total_duration + sleep_interval))

		echo "Waiting for portal log inactivity"
	done

	echo "No portal activity in ${sleep_interval}s"
}

main "${@}"