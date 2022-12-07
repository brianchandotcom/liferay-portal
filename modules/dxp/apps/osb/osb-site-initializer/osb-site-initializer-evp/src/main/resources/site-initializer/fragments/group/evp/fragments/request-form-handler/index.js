/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

function main() {
	const requestType = document.querySelector('[name="requestType"]');

	handleDocumentClick(requestType);
}

document.addEventListener('click', main);

function updateValue(requestType) {
	const serviceForm = document.getElementsByClassName('.service-form');
	const grantForm = document.getElementsByClassName('.grant-form');
	const grantRequestType = document.querySelector(
		'[name="grantRequestType"]'
	);
	const grantAmount = document.querySelector('[name="grantAmount"]');
	const grantCombobox = document.querySelector(
		'.grant-combobox div div div div div input'
	);
	const managerEmailAddress = document.querySelector(
		'[name="managerEmailAddress"]'
	);
	const totalHoursRequested = document.querySelector(
		'[name="totalHoursRequested"]'
	);
	const startDate = document.querySelector('[name="startDate"]');
	const endDate = document.querySelector('[name="endDate"]');
	const labelName = document.querySelector(
		'.grant-combobox div div div div div label'
	);

	if (!labelName.innerText.includes('*')) {
		labelName.innerHTML =
			labelName.innerText +
			"<span style='color:#DA1414;margin-left:4px;font-size:15px;'>*</span>";
	}

	if (
		requestType.value === 'grant' &&
		grantForm[0].classList.contains('d-none')
	) {
		grantForm[0].classList.toggle('d-none');
		toggleGrantRequired(grantForm[0]);
		grantCombobox.setAttribute('required', true);

		if (!serviceForm[0].classList.contains('d-none')) {
			managerEmailAddress.value = '';
			totalHoursRequested.value = 0;
			startDate.value = '';
			endDate.value = '';

			serviceForm[0].classList.toggle('d-none');
			toggleServiceRequired(serviceForm[0]);
		}
	}
	if (
		requestType.value === 'service' &&
		serviceForm[0].classList.contains('d-none')
	) {
		serviceForm[0].classList.toggle('d-none');
		toggleServiceRequired(serviceForm[0]);
		grantCombobox.removeAttribute('required');

		if (!grantForm[0].classList.contains('d-none')) {
			grantRequestType.value = '';
			grantAmount.value = 0;

			grantForm[0].classList.toggle('d-none');
			toggleGrantRequired(grantForm[0]);
		}
	}
}

function toggleServiceRequired(service) {
	if (service.querySelector('[name="managerEmailAddress"]').required) {
		service.querySelector('[name="managerEmailAddress"]').required = false;
		service.querySelector('[name="totalHoursRequested"]').required = false;
		service.querySelector('[name="startDate"]').required = false;
		service.querySelector('[name="endDate"]').required = false;
	}
	else {
		service.querySelector('[name="managerEmailAddress"]').required = true;
		service.querySelector('[name="totalHoursRequested"]').required = true;
		service.querySelector('[name="startDate"]').required = true;
		service.querySelector('[name="endDate"]').required = true;
	}
}

function toggleGrantRequired(grant) {
	if (grant.querySelector('[name="grantAmount"]').required) {
		grant.querySelector('[name="grantAmount"]').required = false;
	}
	else {
		grant.querySelector('[name="grantAmount"]').required = true;
	}
}

function handleDocumentClick(requestType) {
	updateValue(requestType);
}
