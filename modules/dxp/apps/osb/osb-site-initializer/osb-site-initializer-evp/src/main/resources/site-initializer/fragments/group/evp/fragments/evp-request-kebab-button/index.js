/* eslint-disable @liferay/portal/no-global-fetch */
/* eslint-disable no-undef */
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

const ROLE = {
	FINANCE_USER: 'Finance User',
};

const REQUEST_STATUS = {
	SPONSORSHIP: 'sponsorship',
};

const userRoles = document.querySelector('.userRoles').value;

const updateStatus = async (key, name, message) => {
	const requestID = fragmentElement.querySelector('.requestID').value;

	const requestBody = {
		messageEVPManager: `${message}`,
		requestStatus: {
			key: `${key}`,
			name: `${name}`,
		},
	};

	await fetch(`/o/c/evprequests/${requestID}`, {
		body: JSON.stringify(requestBody),
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'PATCH',
	});
	location.reload();
};

const layerForDendingUpdateStatus = async (message, attribute, key, value) => {
	if (message === '') {
		return attribute.removeAttribute('hidden');
	}

	return await updateStatus(key, value, message);
};

const getMessage = () => document.querySelector('#messageDescribed').value;
const getAttributeHidden = () => document.querySelector('#messageDanger');

const openModal = () => {
	const grantRequestType = fragmentElement.querySelector('.grantRequestType')
		.value;

	const requestName = fragmentElement.querySelector('.requestName').value;

	Liferay.Util.openModal({
		bodyHTML:
			'<textarea id="messageDescribed" style="word-wrap: break-word;width:100%;height: 10em;resize: none; border-style: inset;border-width: 1px;border-radius: 5px;" placeholder="Describe here..."></textarea>' +
			'<div id="messageDanger" class="alert alert-danger" role="alert" hidden>This field is mandatory, please fill it in.</div>',
		buttons: [
			{
				displayType: 'secondary',
				label: 'Request more Info',
				async onClick() {
					await layerForDendingUpdateStatus(
						getMessage(),
						getAttributeHidden(),
						'awaitingMoreInfoFromEmployee',
						'Awaiting More Info From Employee'
					);
				},
				type: 'submit',
			},
			{
				displayType: 'secondary',
				label: 'Reject',
				async onClick() {
					await layerForDendingUpdateStatus(
						getMessage(),
						getAttributeHidden(),
						'rejected',
						'Rejected'
					);
				},
				type: 'submit',
			},
			{
				label: 'Approve',
				async onClick() {
					let status = '';
					if (userRoles === ROLE.FINANCE_USER) {
						if (grantRequestType === REQUEST_STATUS.SPONSORSHIP) {
							status = {
								key: 'awaitingEmployeeProofOfExpenses',
								value: 'Awaiting Employee Proof Of Expenses',
							};
						}
						else {
							status = {
								key: 'awaitingPaymentConfirmation',
								value: 'Awaiting Payment Confirmation',
							};
						}
					}
					else {
						status = {
							key: 'awaitingFinanceReview',
							value: 'Awaiting Finance Review',
						};
					}

					await layerForDendingUpdateStatus(
						getMessage(),
						getAttributeHidden(),
						status.key,
						status.value
					);
				},
				type: 'submit',
			},
		],
		center: true,
		headerHTML: `<p class="request-modal-header">Review Request:</p><p>${requestName}</p>`,
		size: 'md',
	});
};
const btnOpenModal = fragmentElement.querySelector('.btnOpenModal');
if (btnOpenModal) {
	btnOpenModal.onclick = () => openModal();
}
