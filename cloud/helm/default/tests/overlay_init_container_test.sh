#!/usr/bin/env bash

set -o errexit
set -o nounset

pass=0
fail=0

function main {
	local chart_directory

	chart_directory="$(cd "$(dirname "${0}")/.." && pwd)"

	local disabled_output

	disabled_output=$(helm template test "${chart_directory}" --set overlay.enabled=false)

	if echo "${disabled_output}" | grep -q "name: liferay-overlay"
	then
		_fail "liferay-overlay init container rendered when overlay is disabled"
	else
		_pass "liferay-overlay init container absent when overlay is disabled"
	fi

	local enabled_output

	enabled_output=$(helm template test "${chart_directory}" \
		--set overlay.enabled=true \
		--set 'overlay.copy[0].into=/dest')

	if echo "${enabled_output}" | grep -q "name: liferay-overlay"
	then
		_pass "liferay-overlay init container present when overlay is enabled"
	else
		_fail "liferay-overlay init container not rendered when overlay is enabled"
	fi

	if echo "${enabled_output}" | grep -q "image: rclone/rclone:1.66"
	then
		_pass "liferay-overlay uses rclone image by default"
	else
		_fail "liferay-overlay does not use expected rclone image"
	fi

	local aws_output

	aws_output=$(helm template test "${chart_directory}" \
		--set overlay.enabled=true \
		--set 'overlay.copy[0].into=/dest' \
		--set overlay.image.repository=amazon/aws-cli \
		--set overlay.image.tag=2.27.63)

	if echo "${aws_output}" | grep -q "image: amazon/aws-cli:2.27.63"
	then
		_pass "liferay-overlay uses overridden aws-cli image"
	else
		_fail "liferay-overlay does not use overridden aws-cli image"
	fi

	local custom_name_output

	custom_name_output=$(helm template test "${chart_directory}" \
		--set overlay.enabled=true \
		--set 'overlay.copy[0].into=/dest' \
		--set overlay.initScriptsVolumeName=custom-init-scripts)

	if echo "${custom_name_output}" | grep -q "name: custom-init-scripts"
	then
		_pass "volumeMount name tracks overlay.initScriptsVolumeName"
	else
		_fail "volumeMount name does not reflect overlay.initScriptsVolumeName"
	fi

	local multi_copy_output

	multi_copy_output=$(helm template test "${chart_directory}" \
		--set overlay.enabled=true \
		--set 'overlay.copy[0].from=overlay-build-1/osgi/*' \
		--set 'overlay.copy[0].into=osgi/' \
		--set 'overlay.copy[1].from=overlay-build-1/configs/*.config' \
		--set 'overlay.copy[1].into=osgi/configs/')

	if echo "${multi_copy_output}" | grep -q '"osgi/"' \
		&& echo "${multi_copy_output}" | grep -q '"osgi/configs/"'
	then
		_pass "multiple copy blocks each generate a sync command"
	else
		_fail "multiple copy blocks do not each generate a sync command"
	fi

	echo ""
	echo "Results: ${pass} passed, ${fail} failed."

	if [ "${fail}" -gt 0 ]
	then
		exit 1
	fi
}

function _fail {
	echo "FAIL: ${1}."
	fail=$((fail + 1))
}

function _pass {
	echo "PASS: ${1}."
	pass=$((pass + 1))
}

main "${@}"