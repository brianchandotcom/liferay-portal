/* eslint-disable radix */
/* eslint-disable @liferay/portal/no-global-fetch */
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

const requests = [];
const userId = parseInt(
	document.getElementById('user-id-container').textContent
);

const fundsLimit = 1000;
const serviceHoursLimit = 40;
const fundsRequestsByUserId = [];
const totalFundsRequestedById = [];
const serviceHoursRequestsByUserId = [];
const totalHoursRequestedByUserId = [];
const availableFunds = [];
const availableServiceHours = [];
const userInformation = [];

const getRequests = async () => {
	const response = await fetch(`/o/c/evprequests`, {
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'GET',
	});

	const data = await response.json();
	requests.push(data);
};

const getFundsRequestByUserId = async () => {
	await getRequests();

	const filteredRequests = requests[0].items.filter(
		(item) =>
			item.creator.id === userId && item.requestStatus.key !== 'rejected'
	);

	filteredRequests.map((item) =>
		fundsRequestsByUserId.push(item.grantAmount)
	);

	totalFundsRequestedById.push(
		fundsRequestsByUserId.reduce((total, quantity) => total + quantity, 0)
	);
};

const getServiceHoursByUserId = async () => {
	await getRequests();

	const filteredRequests = requests[0].items.filter(
		(item) =>
			item.creator.id === userId && item.requestStatus.key !== 'rejected'
	);

	filteredRequests.map((item) =>
		serviceHoursRequestsByUserId.push(item.totalHoursRequested)
	);

	totalHoursRequestedByUserId.push(
		serviceHoursRequestsByUserId.reduce(
			(total, quantity) => total + quantity,
			0
		)
	);
};

getFundsRequestByUserId();
getServiceHoursByUserId();

const getAvailableFunds = async () => {
	await Promise.all([getFundsRequestByUserId()]);

	availableFunds.push(fundsLimit - totalFundsRequestedById[0]);
};

const getAvailableHours = async () => {
	await Promise.all([getServiceHoursByUserId()]);

	availableServiceHours.push(
		serviceHoursLimit - totalHoursRequestedByUserId[0]
	);
};

const displayAvailableValues = async () => {
	await Promise.all([getAvailableFunds(), getAvailableHours()]);

	document.querySelector('#available-funds').innerHTML =
		' R$ ' + availableFunds[0];

	document.querySelector('#available-hours').innerHTML =
		availableServiceHours[0] + 'h';
};

displayAvailableValues();

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

	if (!labelName?.innerText.includes('*')) {
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

const getUser = async () => {
	const response = await fetch(
		`/o/headless-admin-user/v1.0/my-user-account`,
		{
			headers: {
				'content-type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
			method: 'GET',
		}
	);

	const data = await response.json();
	userInformation.push(data);
};

const setDefaultUserInfo = async () => {
	await getUser();

	const fullNameInput = document.querySelector('[name="fullName"]');
	fullNameInput.readOnly = true;
	fullNameInput.value = userInformation[0]?.name;
	fullNameInput.style.cursor = 'not-allowed';
	fullNameInput.style.color = '#b1b2b8';

	const emailAddressInput = document.querySelector('[name="emailAddress"]');
	emailAddressInput.readOnly = true;
	emailAddressInput.style.cursor = 'not-allowed';
	emailAddressInput.style.color = '#b1b2b8';
	emailAddressInput.value = userInformation[0]?.emailAddress;

	const managerInformation = userInformation[0].customFields.find(
		({name}) => name === 'Manager'
	).customValue.data;

	const managerEmailAddress = document.querySelector(
		'[name="managerEmailAddress"]'
	);

	if (managerInformation) {
		managerEmailAddress.readOnly = true;
		managerEmailAddress.style.cursor = 'not-allowed';
		managerEmailAddress.style.color = '#b1b2b8';
		managerEmailAddress.value = managerInformation;
	}
	else {
		managerEmailAddress.parentElement.style.display = 'none';
	}

	const phoneNumber =
		userInformation[0]?.userAccountContactInformation?.telephones[0]
			?.phoneNumber;
	const phoneInput = document.querySelector('[name="phoneNumber"]');

	if (phoneNumber) {
		phoneInput.readOnly = true;
		phoneInput.value = phoneNumber;
		phoneInput.style.cursor = 'not-allowed';
		phoneInput.style.color = '#b1b2b8';
	}
};

getUser();
setDefaultUserInfo();

const grantInput = document.querySelector('input[name="grantAmount"]');
const hoursInput = document.querySelector('input[name="totalHoursRequested"]');
const grantInputDiv = grantInput.parentNode;
const hoursInputDiv = hoursInput.parentNode;
const newParagraph = document.createElement('p');
const message = document.createTextNode('Text');
grantInputDiv.style.position = 'relative';
newParagraph.setAttribute('class', 'error-msg');
newParagraph.setAttribute('style', 'color:#a90f0f');
newParagraph.setAttribute('style', 'display:none');
newParagraph.appendChild(message);

const compareGrants = async () => {
	const grantInputValue = grantInput.value;

	try {
		await getAvailableFunds();

		if (grantInputValue > availableFunds[0]) {
			grantInputDiv.appendChild(newParagraph);
			newParagraph.style.position = 'absolute';

			const errorMsg = document.querySelector('.error-msg');
			if (errorMsg) {
				errorMsg.innerText = 'No funds available.';
				errorMsg.style.color = '#a90f0f';
				errorMsg.style.display = 'block';
			}
			document.querySelector('button[type="submit"]').disabled = true;
		}
		else {
			const errorMsg = document.querySelector('.error-msg');
			if (errorMsg) {
				errorMsg.style.display = 'none';
			}
			document.querySelector('button[type="submit"]').disabled = false;
		}
	}
	catch (error) {
		console.error(error);
	}
};

grantInput.addEventListener('change', compareGrants);

const compareHours = async () => {
	const hoursInputValue = hoursInput.value;

	await getAvailableHours();

	if (hoursInputValue > availableServiceHours[0]) {
		hoursInputDiv.appendChild(newParagraph);

		const errorMsg = document.querySelector('.error-msg');
		if (errorMsg) {
			errorMsg.innerText = 'No service hours available.';
			errorMsg.style.color = '#a90f0f';
			errorMsg.style.display = 'block';
			errorMsg.style.position = 'absolute';
		}
		document.querySelector('button[type="submit"]').disabled = true;
	}
	else {
		const errorMsg = document.querySelector('.error-msg');
		if (errorMsg) {
			errorMsg.style.display = 'none';
		}
		document.querySelector('button[type="submit"]').disabled = false;
	}
};

hoursInput.addEventListener('change', compareHours);
