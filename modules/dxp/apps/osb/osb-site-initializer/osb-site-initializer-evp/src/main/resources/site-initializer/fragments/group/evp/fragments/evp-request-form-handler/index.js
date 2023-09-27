/* eslint-disable @liferay/portal/no-global-fetch */
/* eslint-disable radix */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const editPage = window.location.pathname.includes('edit-request-form');

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

function setRequestStatusHandler() {
	const requestStatusHandler = document.querySelector(
		'.requestStatusHandler div div div div div input'
	);
	requestStatusHandler.setAttribute('value', 'Awaiting Approval On Evp');

	const requestStatus = document.querySelector('[name="requestStatus"]');
	requestStatus.setAttribute('value', 'awaitingApprovalOnEvp');

	const requestStatusLabel = document.querySelector(
		'[name="requestStatus-label"]'
	);
	requestStatusLabel.setAttribute('value', 'Awaiting Approval On Evp');
}
setRequestStatusHandler();

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

if (!editPage) {
	document.addEventListener('click', main);
}

const getAccounts = async () => {
	const response = await fetch(`/o/headless-admin-user/v1.0/accounts`, {
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'GET',
	});

	const data = await response.json();

	return data;
};

const getUserAccounts = async () => {
	const response = await fetch(`/o/headless-admin-user/v1.0/user-accounts`, {
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'GET',
	});

	const data = await response.json();

	return data;
};

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

	return data;
};

const setRegionsInTheRequest = async () => {
	const [accounts, userCustomFields] = await Promise.all([
		getAccounts(),
		getUserAccounts(),
	]);

	const naRegions = ['Liferay Inc', 'Liferay Canada Inc'];
	const latamRegions = ['Liferay Latin America', 'Liferay Chile'];
	const emeaRegions = [
		'Liferay Africa S.A.R.L.',
		'Liferay France SAS',
		'Liferay Germany GmbH',
		'Liferay Hungary',
		'Liferay International',
		'Liferay Italy',
		'Liferay Middle East',
		'Liferay Nordic Oy',
		'Liferay Spain',
		'Liferay UK',
		'Liferay Benelux B.V.',
	];
	const apacRegions = [
		'Liferay Australia Pty',
		'Liferay Dailan Software',
		'Liferay India Pvt. Ltd.',
		'Liferay Japan KK',
		'Liferay Singapore Pte Ltd',
	];

	let accountId = '';
	let entityName = '';
	let region = '';
	let isEmployee = false;

	for (const entity of userCustomFields?.items) {
		entityName = entity?.customFields.find(
			(field) => field?.name === 'Entity'
		)?.customValue?.data;

		isEmployee = entity?.roleBriefs.some(
			(role) => role?.name === 'Employee'
		);
		if (entityName) {
			break;
		}
	}

	const regions = [
		{list: naRegions, prefix: 'Liferay NA'},
		{list: latamRegions, prefix: 'Liferay LATAM'},
		{list: emeaRegions, prefix: 'Liferay EMEA'},
		{list: apacRegions, prefix: 'Liferay APAC'},
	];

	for (const {list, prefix} of regions) {
		if (list.includes(entityName)) {
			region = isEmployee ? `${prefix} Employee` : prefix;
			break;
		}
	}

	accountId =
		accounts?.items.find((account) => account?.name === region)?.id || '';

	document.querySelector(
		'input[name="r_requestRegion_accountEntryId"]'
	).value = accountId;
};

setRegionsInTheRequest();

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

const setDefaultUserInfo = async () => {
	await getUser();

	const fullNameInput = document.querySelector('[name="fullName"]');
	fullNameInput.readOnly = true;
	fullNameInput.style.cursor = 'not-allowed';
	fullNameInput.style.color = '#b1b2b8';

	const emailAddressInput = document.querySelector('[name="emailAddress"]');
	emailAddressInput.readOnly = true;
	emailAddressInput.style.cursor = 'not-allowed';
	emailAddressInput.style.color = '#b1b2b8';

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
		managerEmailAddress.removeAttribute('required');
		managerEmailAddress.value = '';
		managerEmailAddress.disabled = true;
	}

	const phoneNumber =
		userInformation[0]?.userAccountContactInformation?.telephones[0]
			?.phoneNumber;
	const phoneInput = document.querySelector('[name="phoneNumber"]');

	if (!editPage) {
		fullNameInput.value = userInformation[0]?.name;
		emailAddressInput.value = userInformation[0]?.emailAddress;

		if (phoneNumber) {
			phoneInput.readOnly = true;
			phoneInput.style.cursor = 'not-allowed';
			phoneInput.style.color = '#b1b2b8';
			phoneInput.value = phoneNumber;
		}

		if (managerInformation) {
			managerEmailAddress.value = managerInformation;
		}
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

const evpRequestEditForm = document.querySelector('.evp-request-form');
async function getEVPRequest(requestId) {
	const searchParams = new URLSearchParams();
	searchParams.set(
		'fields',
		'r_organization_c_evpOrganizationId,requestType,endDate,fullName,managerEmailAddress,requestBehalf,totalHoursRequested,grantAmount,requestDescription,requestPurposes,emailAddress,phoneNumber,grantRequestType,startDate'
	);

	const response = await fetch(
		`/o/c/evprequests/${requestId}?${searchParams.toString()}`,
		{
			headers: {
				'Content-type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);

	const data = await response.json();

	return data;
}

async function getEVPRequestOrganization(organizationId) {
	const searchParams = new URLSearchParams();
	searchParams.set('fields', 'id,organizationName,taxId');

	const response = await fetch(
		`/o/c/evporganizations/${organizationId}?${searchParams.toString()}`,
		{
			headers: {
				'Content-type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);

	const data = await response.json();

	return data;
}

async function updateEVPRequest(requestId, requestEditForm) {
	await fetch(`/o/c/evprequests/${requestId}`, {
		body: JSON.stringify(requestEditForm),
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'PATCH',
	});
}

function getRequestEditFormValues() {
	const ignoreFields = [
		'backURL',
		'classNameId',
		'classTypeId',
		'formItemId',
		'groupId',
		'p_l_id',
		'p_l_mode',
		'plid',
		'grantRequestType-label',
		'r_organization_c_evpOrganizationId-label',
		'requestPurposes-label',
		'requestType-label',
		'requestBehalf-label',
		'segmentsExperienceId',
		'redirect',
	];

	if (!evpRequestEditForm) {
		return console.error('Evp Form not found');
	}

	const requestEditForm = {};
	const formData = new FormData(evpRequestEditForm);

	for (const [key, value] of Array.from(formData.entries())) {
		if (!ignoreFields.includes(key)) {
			requestEditForm[key] = value;
		}
	}

	return requestEditForm;
}

if (editPage) {
	const queryString = window.location.search;
	const requestId = queryString.split('=')[1];

	const FIELD = {
		ENDDATE: 'endDate',
		ORGANIZATIONID: 'r_organization_c_evpOrganizationId',
		REQUESTTYPE: 'requestType',
		STARTDATE: 'startDate',
	};

	getEVPRequest(requestId).then((request) => {
		for (const [key, keyValue] of Object.entries(request)) {
			const formInput = document.querySelector(`[name='${key}']`);
			const formInputLabel = document.querySelector(
				`[name='${key}-label']`
			);

			if (formInput) {
				if (formInputLabel) {
					if (formInput.name === FIELD.ORGANIZATIONID) {
						getEVPRequestOrganization(keyValue).then((response) => {
							const organizationInput = document.querySelector(
								'#selected-org'
							);

							organizationInput.value = `${response['id']} - ${response['organizationName']} - ${response['taxId']} `;
							organizationInput.setAttribute(
								'disabled',
								'disabled'
							);

							formInput.value = response['id'];
							formInputLabel.value = response['organizationName'];
						});

						continue;
					}

					formInput.value = keyValue.key;
					formInput.previousSibling.previousSibling.value =
						keyValue.name;
					formInputLabel.value = keyValue.name;

					if (formInput.name === FIELD.REQUESTTYPE) {
						formInput.previousSibling.previousSibling.setAttribute(
							'disabled',
							'disabled'
						);

						formInput.parentElement.parentElement
							.querySelector('span button')
							.setAttribute('disabled', true);

						main();
					}

					continue;
				}

				if (
					formInput.name === FIELD.ENDDATE ||
					formInput.name === FIELD.STARTDATE
				) {
					formInput.value = keyValue.split('T')[0];
					continue;
				}

				formInput.value = keyValue;
			}
		}
	});

	if (!evpRequestEditForm) {
		return;
	}

	evpRequestEditForm.addEventListener('submit', (event) => {
		event.preventDefault();

		const requestEditForm = getRequestEditFormValues();
		requestEditForm.requestStatus = {
			key: 'awaitingApprovalOnEvp',
			name: 'Awaiting Approval On EVP',
		};

		try {
			updateEVPRequest(requestId, requestEditForm).then(() => {
				const groupKey = Liferay.ThemeDisplay.getSiteAdminURL()
					.split('group')[1]
					.split('/')[1];

				const redirect = `${Liferay.ThemeDisplay.getPortalURL()}/web/${groupKey}/request-form-submit-page`;
				window.location.href = redirect;
			});
		}
		catch (error) {
			console.error(error);
		}
	});
}
