/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const ROLE = {
	EMPLOYEE: 'Employee',
	EVP_MANAGER: 'EVP Manager',
};

const FIELD = {
	BANKINGINFO: 'bankingInfo',
	TAXID: 'taxId',
};

const queryString = window.location.search;
const urlParams = queryString.split('=');
const organizationId = urlParams[1];
const liferayUrl = window.location.origin;

const searchParams = new URLSearchParams();

const userRoles = document.querySelector('.userRoles').value;

searchParams.set('filter', `id eq '${organizationId}'`);
searchParams.set(
	'fields',
	'bankingInfo,city,contactEmail,contactName,contactPhone,country,id,organizationName,organizationSiteSocialMediaLink,smallDescription,state,street,taxId,zip'
);

async function getEVPOrganizations() {
	const response = await fetch(
		`${liferayUrl}/o/c/evporganizations?${searchParams.toString()}`,
		{
			headers: {
				'Content-type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);

	const data = await response.json();

	return data?.items ?? [];
}

getEVPOrganizations().then((organizations) => {
	for (const organization of organizations) {
		for (const key in organization) {
			const inputName = document.querySelector(`[name='${key}']`);

			if (inputName) {
				const isEVPManager = userRoles === ROLE.EVP_MANAGER;
				const isBankingInfoOrTaxId =
					inputName.name === FIELD.BANKINGINFO ||
					inputName.name === FIELD.TAXID;

				if (isEVPManager && isBankingInfoOrTaxId) {
					inputName.setAttribute('disabled', 'disabled');
				}

				inputName.value = organization[key];
			}
		}
	}
});

const ignoreFields = [
	'classTypeId',
	'classNameId',
	'formItemId',
	'p_l_mode',
	'plid',
	'redirect',
	'organizationStatus',
	'organizationStatus-label',
];

function getOrganizationFormValues() {
	const evpOrganizationForm = document.querySelector(
		'.evp-organization-form'
	);

	if (!evpOrganizationForm) {
		return console.error('Evp Form not found');
	}

	const organizationForm = {};
	const formData = new FormData(evpOrganizationForm);

	for (const [key, value] of Array.from(formData.entries())) {
		if (!ignoreFields.includes(key)) {
			organizationForm[key] = value;
		}
	}

	return organizationForm;
}

const organizationUpdate = async () => {
	const organizationForm = getOrganizationFormValues();

	organizationForm.organizationStatus =
		userRoles === ROLE.EMPLOYEE
			? {
					key: 'awaitingApprovalOnEvp',
					name: 'Awaiting Approval On EVP',
			  }
			: {
					key: 'verified',
					name: 'Verified',
			  };

	await fetch(`${liferayUrl}/o/c/evporganizations/${organizationId}`, {
		body: JSON.stringify(organizationForm),
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'PATCH',
	});
	localStorage.setItem('success', 'Success');
};

const formInputName = document.querySelector('.submit-button');
const toolTip = document.querySelector('.tooltip');

function changeDisplayTooltip(display) {
	toolTip.style.display = display;
}

changeDisplayTooltip('none');

if (!formInputName) {
	return;
}

formInputName.addEventListener('click', (event) => {
	const inputValues = [];
	const organizationForm = getOrganizationFormValues();

	let error = 0;

	const formInputs = Object.entries(organizationForm);

	inputValues.push(formInputs);

	const firstInputValue = inputValues[0]?.find((item) => item[1] === '');
	if (firstInputValue !== undefined) {
		const inputName = document.querySelector(
			`[name='${firstInputValue[0]}']`
		);
		const div = document.createElement('div');

		div.setAttribute('class', firstInputValue[0]);

		changeDisplayTooltip('none');

		if (!firstInputValue[1]) {
			const inputParentDiv = inputName.parentNode;
			const divElement = document.createElement('div');

			divElement.appendChild(toolTip);
			inputParentDiv.appendChild(divElement);

			changeDisplayTooltip('block');

			inputName.addEventListener('load', () =>
				changeDisplayTooltip('block')
			);
			event.preventDefault();

			error++;
		}
	}

	if (error === 0) {
		organizationUpdate();
	}
});
