/* eslint-disable no-undef */
/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const MODAL_BTN_OPTION = {
	APPROVE: 'md-approve',
	REJECT: 'md-reject',
	REVIEW: 'md-review',
};

const ROLE = {
	FINANCE_USER: 'Finance User',
};

const REQUEST_STATUS = {
	AWAITING_APPROVAL_ON_EVP: {
		key: 'awaitingApprovalOnEvp',
		value: 'Awaiting Approval On EVP',
	},
	AWAITING_EMPLOYEE_PROOF_OF_EXPENSES: {
		key: 'awaitingEmployeeProofOfExpenses',
		value: 'Awaiting Employee Proof Of Expenses',
	},
	AWAITING_FINANCE_REVIEW: {
		key: 'awaitingFinanceReview',
		value: 'Awaiting Finance Review',
	},
	AWAITING_MORE_INFO_FROM_EMPLOYEE: {
		key: 'awaitingMoreInfoFromEmployee',
		value: 'Awaiting More Info From Employee',
	},
	AWAITING_PAYMENT_CONFIRMATION: {
		key: 'awaitingPaymentConfirmation',
		value: 'Awaiting Payment Confirmation',
	},
	REJECTED: {
		key: 'rejected',
		value: 'Rejected',
	},
	SPONSORSHIP: 'sponsorship',
};

const LABEL = {
	APPROVE: 'Approve',
	CANCEL: 'Cancel',
	REJECT: 'Reject',
	REQUEST_MORE_INFO: 'Request more Info',
};

const userRoles = document.querySelector('.userRoles').value;

const updateStatus = async (key, name, message) => {
	const requestID = fragmentElement.querySelector('.requestID').value;

	const requestBody = {
		messageEVPManager: message,
		requestStatus: {
			key,
			name,
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

const openModal = (optionBtn) => {
	const grantRequestType = fragmentElement.querySelector('.grantRequestType')
		.value;
	const requestName = fragmentElement.querySelector('.requestName').value;

	let modalConfigs = {};

	if (optionBtn === MODAL_BTN_OPTION.REVIEW) {
		modalConfigs = {
			buttons: [
				{
					displayType: 'secondary',
					label: LABEL.REQUEST_MORE_INFO,
					async onClick() {
						await layerForDendingUpdateStatus(
							getMessage(),
							getAttributeHidden(),
							REQUEST_STATUS.AWAITING_MORE_INFO_FROM_EMPLOYEE.key,
							REQUEST_STATUS.AWAITING_MORE_INFO_FROM_EMPLOYEE
								.value
						);
					},
					type: 'submit',
				},
				{
					displayType: 'secondary',
					label: LABEL.REJECT,
					async onClick() {
						await layerForDendingUpdateStatus(
							getMessage(),
							getAttributeHidden(),
							REQUEST_STATUS.REJECTED.key,
							REQUEST_STATUS.REJECTED.value
						);
					},
					type: 'submit',
				},
				{
					label: LABEL.APPROVE,
					async onClick() {
						let status = '';
						if (userRoles === ROLE.FINANCE_USER) {
							if (
								grantRequestType === REQUEST_STATUS.SPONSORSHIP
							) {
								status = {
									key:
										REQUEST_STATUS
											.AWAITING_EMPLOYEE_PROOF_OF_EXPENSES
											.key,
									value:
										REQUEST_STATUS
											.AWAITING_EMPLOYEE_PROOF_OF_EXPENSES
											.value,
								};
							}
							else {
								status = {
									key:
										REQUEST_STATUS
											.AWAITING_PAYMENT_CONFIRMATION.key,
									value:
										REQUEST_STATUS
											.AWAITING_PAYMENT_CONFIRMATION
											.value,
								};
							}
						}
						else {
							status = {
								key: REQUEST_STATUS.AWAITING_FINANCE_REVIEW.key,
								value:
									REQUEST_STATUS.AWAITING_FINANCE_REVIEW
										.value,
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
			headerHTML: `<p class="request-modal-header">Review Request:</p><p>${requestName}</p>`,
		};
	}
	else {
		modalConfigs =
			optionBtn === MODAL_BTN_OPTION.APPROVE
				? {
						buttons: [
							{
								displayType: 'secondary',
								label: LABEL.CANCEL,
								type: 'cancel',
							},
							{
								displayType: 'primary',
								label: LABEL.APPROVE,
								async onClick() {
									await layerForDendingUpdateStatus(
										getMessage(),
										getAttributeHidden(),
										REQUEST_STATUS.AWAITING_APPROVAL_ON_EVP
											.key,
										REQUEST_STATUS.AWAITING_APPROVAL_ON_EVP
											.value
									);
								},
								type: 'submit',
							},
						],
						headerHTML: `<p class="request-modal-header">Approve Request: <span>${requestName}</span></p>`,
				  }
				: {
						buttons: [
							{
								displayType: 'secondary',
								label: LABEL.CANCEL,
								type: 'cancel',
							},
							{
								displayType: 'primary',
								label: LABEL.REJECT,
								async onClick() {
									await layerForDendingUpdateStatus(
										getMessage(),
										getAttributeHidden(),
										REQUEST_STATUS.REJECTED.key,
										REQUEST_STATUS.REJECTED.value
									);
								},
								type: 'submit',
							},
						],
						headerHTML: `<p class="request-modal-header">Reject Request: <span>${requestName}</span></p>`,
				  };
	}

	Liferay.Util.openModal({
		bodyHTML:
			'<textarea id="messageDescribed" style="word-wrap: break-word;width:100%;height: 10em;resize: none; border-style: inset;border-width: 1px;border-radius: 5px;padding: 5px;" placeholder="Describe here..."></textarea>' +
			'<div id="messageDanger" class="alert alert-danger" role="alert" hidden>This field is mandatory, please fill it in.</div>',
		buttons: modalConfigs?.buttons,
		center: true,
		headerHTML: modalConfigs?.headerHTML,
		size: 'md',
	});
};

const btnOpenModal = fragmentElement.querySelectorAll('.btn-open-modal');

if (btnOpenModal.length) {
	btnOpenModal.forEach((cur_optionBtn) => {
		const type = Array.from(cur_optionBtn.classList).find((className) =>
			className.includes('md-')
		);
		cur_optionBtn.onclick = () => openModal(type);
	});
}
