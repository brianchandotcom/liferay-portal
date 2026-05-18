#!/usr/bin/env bash

function _atomic_write {
	local file="${1}"

	cat > "${file}.tmp"
	mv "${file}.tmp" "${file}"
}

function _die {
	echo "${*}" >&2

	exit 1
}

function _find_app_server_parent_dir {
	local project_dir="${1}"
	local user_props="${project_dir}/app.server.${USER}.properties"
	local default_props="${project_dir}/app.server.properties"
	local raw=""

	if [[ -f "${user_props}" ]]
	then
		raw="$(grep -E '^[[:space:]]*app\.server\.parent\.dir=' "${user_props}" | tail -1 | sed 's/^[[:space:]]*app\.server\.parent\.dir=//')"
	fi

	if [[ -z "${raw}" && -f "${default_props}" ]]
	then
		raw="$(grep -E '^[[:space:]]*app\.server\.parent\.dir=' "${default_props}" | tail -1 | sed 's/^[[:space:]]*app\.server\.parent\.dir=//')"
	fi

	[[ -n "${raw}" ]] || return 1

	raw="${raw//\$\{project.dir\}/${project_dir}}"

	(cd "$(dirname "${raw}")" 2>/dev/null && printf '%s/%s\n' "$(pwd)" "$(basename "${raw}")") || return 1
}

function _find_tomcat_dir {
	local bundles_dir="${1}"
	local candidate latest=""

	for candidate in "${bundles_dir}"/tomcat-*
	do
		[[ -d "${candidate}" ]] || continue

		if [[ -z "${latest}" || "${candidate}" > "${latest}" ]]
		then
			latest="${candidate}"
		fi
	done

	[[ -n "${latest}" ]] || return 1

	echo "${latest}"
}