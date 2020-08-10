#!/bin/bash

function date {
	export LC_ALL=en_US.UTF-8

	if [ -z ${1+x} ] || [ -z ${2+x} ]
	then
		if [ "$(uname)" == "Darwin" ]
		then
			echo $(/bin/date)
		elif [ -e /bin/date ]
		then
			echo $(/bin/date)
		else
			echo $(/usr/bin/date)
		fi
	else
		if [ "$(uname)" == "Darwin" ]
		then
			echo $(/bin/date -jf "%a %b %e %H:%M:%S %Z %Y" "${1}" "${2}")
		elif [ -e /bin/date ]
		then
			echo $(/bin/date -d "${1}" "${2}")
		else
			echo $(/usr/bin/date -d "${1}" "${2}")
		fi
	fi
}

function download {
	local file_name=${2##*/}

	mkdir -p downloads

	if [ ! -e downloads/${file_name} ]
	then
		curl -o downloads/${file_name} ${2}
	fi

	cp downloads/${file_name} "${1}"
}

function fix_tomcat_setenv {
	local jvm_opts="-Xms2560m -Xmx2560m -XX:MaxNewSize=1536m -XX:MaxMetaspaceSize=512m -XX:MetaspaceSize=512m -XX:NewSize=1536m -XX:SurvivorRatio=7"

	#
	# For 7.1.3
	#

	sed -i "s/-Xms1280m -Xmx1280m -XX:MaxNewSize=256m -XX:NewSize=256m -XX:MaxMetaspaceSize=512m -XX:MetaspaceSize=512m -XX:SurvivorRatio=7/${jvm_opts}/" ${1}/bin/setenv.bat
	sed -i "s/-Xms2560m -Xmx2560m -XX:MaxNewSize=1536m -XX:MaxMetaspaceSize=512m -XX:MetaspaceSize=512m -XX:NewSize=1536m -XX:SurvivorRatio=7/${jvm_opts}/" ${1}/bin/setenv.sh

	#
	# For 7.1.10.1
	#

	sed -i "s/-Xmx1024m -XX:MaxMetaspaceSize=512m/${jvm_opts}/" ${1}/bin/setenv.bat
	sed -i "s/-Xmx1024m -XX:MaxMetaspaceSize=512m/${jvm_opts}/" ${1}/bin/setenv.sh

	if $(! grep -q -e "${jvm_opts}" ${1}/bin/setenv.bat) || $(! grep -q -e "${jvm_opts}" ${1}/bin/setenv.sh)
	then
		echo "Unable to set JVM options."

		exit 1
	fi
}

function get_tomcat_version {
	if [ -e ${1}/tomcat-* ]
	then
		for temp_file_name in `ls ${1}`
		do
			if [[ ${temp_file_name} == tomcat-* ]]
			then
				local liferay_tomcat_version=${temp_file_name#*-}
			fi
		done
	fi

	if [ -z ${liferay_tomcat_version+x} ]
	then
		echo "Unable to determine Tomcat version."

		exit 1
	fi

	echo ${liferay_tomcat_version}
}

function install_fix_pack {
	local commerce_bundle_name=${1}
	local fix_pack_url=${2}
	local timestamp=${3}

	#
	# See https://gist.github.com/ethanbustad/600d232539824db320d2977d453115a6.
	#

	echo ""
	echo "Download Patching Tool."
	echo ""

	rm -fr ${timestamp}/${commerce_bundle_name}/patching-tool

	local patching_tool_name="patching-tool-$(curl --silent http://mirrors.lax.liferay.com/files.liferay.com/private/ee/fix-packs/patching-tool/LATEST-2.0.txt).zip"

	download ${patching_tool_name} http://mirrors.lax.liferay.com/files.liferay.com/private/ee/fix-packs/patching-tool/${patching_tool_name}

	unzip -q ${patching_tool_name} -d ${timestamp}/${commerce_bundle_name}

	rm ${patching_tool_name}

	chmod u+x ${timestamp}/${commerce_bundle_name}/patching-tool/*.sh

	echo ""
	echo "Install Patching Tool."
	echo ""

	local liferay_tomcat_version=$(get_tomcat_version ${timestamp}/${commerce_bundle_name})

	echo -e "global.lib.path=../tomcat-${liferay_tomcat_version}/lib/ext/\nliferay.home=../\npatching.mode=binary\nwar.path=../tomcat-${liferay_tomcat_version}/webapps/ROOT/" > ${timestamp}/${commerce_bundle_name}/patching-tool/default.properties

	${timestamp}/${commerce_bundle_name}/patching-tool/patching-tool.sh auto-discovery ..

	${timestamp}/${commerce_bundle_name}/patching-tool/patching-tool.sh revert

	rm -fr ${timestamp}/${commerce_bundle_name}/patching-tool/patches/*

	echo ""
	echo "Download fix pack."
	echo ""

	download ${timestamp}/${commerce_bundle_name}/patching-tool/patches/patch.zip http://mirrors.lax.liferay.com/${fix_pack_url}

	echo ""
	echo "Install fix pack."
	echo ""

	local patch_status=$(${timestamp}/${commerce_bundle_name}/patching-tool/patching-tool.sh info | grep "\[ x\]\|\[ D\]\|\[ o\]\|\[ s\]")

	if [[ ! -z ${patch_status} ]]
	then
		echo "Unable to patch: ${patch_status}."

		exit 1
	fi

	${timestamp}/${commerce_bundle_name}/patching-tool/patching-tool.sh install

	${timestamp}/${commerce_bundle_name}/patching-tool/patching-tool.sh update-plugins

	rm -fr ${timestamp}/${commerce_bundle_name}/osgi/state
}

function main {

	#
	# Make temporary directory.
	#

	local current_date=$(date)

	local timestamp=$(date "${current_date}" "+%Y%m%d%H%M")

	mkdir -p ${timestamp}

	#
	# Download and extract Portal.
	#

	local portal_bundle_url=http://mirrors.lax.liferay.com/${2}

	local portal_bundle_name=${portal_bundle_url##*/}

	download ${timestamp}/${portal_bundle_name} ${portal_bundle_url}

	if [[ ${portal_bundle_name} == *.7z ]]
	then
		7z x -O${timestamp} ${timestamp}/${portal_bundle_name}
	else
		unzip -q ${timestamp}/${portal_bundle_name} -d ${timestamp}
	fi

	rm ${timestamp}/${portal_bundle_name}

	#
	# Rename Portal to Commerce directory.
	#

	local commerce_lpkg_url=http://mirrors.lax.liferay.com/${1}

	local commerce_portal_variant=${commerce_lpkg_url: -10:-5}

	local commerce_version=${commerce_lpkg_url%/*}

	commerce_version=${commerce_version##*/}

	local commerce_bundle_name

	if [[ ${portal_bundle_name} == *-dxp-* ]]
	then
		commerce_bundle_name=liferay-commerce-enterprise-${commerce_version}-${commerce_portal_variant}
	else
		commerce_bundle_name=liferay-commerce-${commerce_version}-${commerce_portal_variant}
	fi

	mv ${timestamp}/liferay-* ${timestamp}/${commerce_bundle_name}

	#
	# Install fix pack.
	#

	if [[ ${portal_bundle_name} == *-dxp-* ]]
	then
		if [ ! -z ${3+x} ]
		then
			install_fix_pack ${commerce_bundle_name} ${3} ${timestamp}
		fi
	fi

	#
	# Download Commerce.
	#

	download "${timestamp}/${commerce_bundle_name}/osgi/marketplace/Liferay Commerce.lpkg" ${commerce_lpkg_url}

	#
	# Start Tomcat.
	#

	start_tomcat ${timestamp}

	#
	# Download licenses.
	#

	~/dev/projects/github/liferay-docker/download_trial_dxp_license.sh ${timestamp}/${commerce_bundle_name} $(date "${CURRENT_DATE}" "+%s%3N") ${commerce_bundle_name}

	#
	# Build bundle.
	#

	cd ${timestamp}

	7z a -md1024m ${commerce_bundle_name}-${timestamp}.7z ${commerce_bundle_name}

	cd ..
}

function start_tomcat {
	local timestamp=${1}

	rm -fr ${timestamp}/liferay-commerce-*/data/elasticsearch**

	#fix_tomcat_setenv ${timestamp}/${commerce_bundle_name}/tomcat-$(get_tomcat_version ${timestamp}/${commerce_bundle_name})

	cp ${timestamp}/liferay-commerce-*/tomcat-*/bin/setenv.sh setenv.sh.bak

	printf "\nexport LIFERAY_CLEAN_OSGI_STATE=true" >> ${timestamp}/liferay-commerce-*/tomcat-*/bin/setenv.sh

	./${timestamp}/liferay-commerce-*/tomcat-*/bin/catalina.sh start

	until $(curl --head --fail --output /dev/null --silent http://localhost:8080)
	do
		sleep 3
	done

	./${timestamp}/liferay-commerce-*/tomcat-*/bin/catalina.sh stop

	sleep 20

	rm -fr ${timestamp}/liferay-commerce-*/logs/*
	rm -fr ${timestamp}/liferay-commerce-*/tomcat-*/logs/*

	mv setenv.sh.bak ${timestamp}/liferay-commerce-*/tomcat-*/bin/setenv.sh
}

main ${1} ${2} ${3}