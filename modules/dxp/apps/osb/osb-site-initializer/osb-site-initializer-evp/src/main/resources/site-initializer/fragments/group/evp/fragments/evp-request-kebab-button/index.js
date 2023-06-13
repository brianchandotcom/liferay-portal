/* eslint-disable no-undef */
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

const MODAL_BTN_OPTION = {
	APPROVE: 'approve',
	REVIEW: 'review',
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

const openModal = (btn_option) => {
	const grantRequestType = fragmentElement.querySelector('.grantRequestType')
		.value;
	const requestName = fragmentElement.querySelector('.requestName').value;

	let modalConfigs = {};

	if (btn_option === MODAL_BTN_OPTION.REVIEW) {
		modalConfigs = {
			buttons: [
				{
					displayType: 'secondary',
					label: LABEL.REQUEST_MORE_INFO,
					async onClick() {
						await layerForDendingUpdateStatus(
							getMessage(),
							getAttributeHidden(),
							STATUS.AWAITING_MORE_INFO_FROM_EMPLOYEE.key,
							STATUS.AWAITING_MORE_INFO_FROM_EMPLOYEE.value
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
							STATUS.REJECTED.key,
							STATUS.REJECTED.value
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
							} else {
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
						} else {
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
			btn_option === MODAL_BTN_OPTION.APPROVE
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
										STATUS.AWAITING_APPROVAL_ON_EVP.key,
										STATUS.AWAITING_APPROVAL_ON_EVP.value
									);
								},
								type: 'submit',
							},
						],
						headerHTML: `<p class="request-modal-header">Approve Request:</p><p>${requestName}</p>`,
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
										STATUS.REJECTED.key,
										STATUS.REJECTED.value
									);
								},
								type: 'submit',
							},
						],
						headerHTML: `<p class="request-modal-header">Reject Request:</p><p>${requestName}</p>`,
				  };
	}

	Liferay.Util.openModal({
		bodyHTML:
			'<textarea id="messageDescribed" style="word-wrap: break-word;width:100%;height: 10em;resize: none; border-style: inset;border-width: 1px;border-radius: 5px;" placeholder="Describe here..."></textarea>' +
			'<div id="messageDanger" class="alert alert-danger" role="alert" hidden>This field is mandatory, please fill it in.</div>',
		buttons: modalConfigs?.buttons,
		center: true,
		headerHTML: modalConfigs?.headerHTML,
		size: 'md',
	});
};

const btnOpenModal = fragmentElement.querySelectorAll('.btnOpenModal');

if (btnOpenModal.length) {
	btnOpenModal.forEach((cur_btn_option) => {
		cur_btn_option.onclick = () => openModal(cur_btn_option.classList[1]);
	});
}
