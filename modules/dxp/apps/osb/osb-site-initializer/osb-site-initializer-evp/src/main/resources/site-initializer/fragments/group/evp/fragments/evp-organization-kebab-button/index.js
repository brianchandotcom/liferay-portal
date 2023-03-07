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

const updateStatus = async (key, name) => {
	const organizationID = fragmentElement.querySelector('.organizationID')
		.value;

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	await fetch(`/o/c/evporganizations/${organizationID}`, {
		body: `{
		"organizationStatus":{
		   "key":"${key}",
		   "name":"${name}"
		}
	 }`,
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'PATCH',
	});

	location.reload();
};

const openModal = () => {
	const organizationName = fragmentElement.querySelector('.organizationName')
		.innerHTML;

	Liferay.Util.openModal({
		buttons: [
			{
				displayType: 'danger',
				label: 'Reject',
				async onClick() {
					await updateStatus('rejected', 'Rejected');
				},
				type: 'submit',
			},
			{
				displayType: 'success',
				label: 'Approve',
				async onClick() {
					await updateStatus(
						'awaitingFinanceApproval',
						'Awaiting Finance Approval'
					);
				},
				type: 'submit',
			},
		],
		center: true,
		headerHTML: `<p class="headerTextModal">Approve or Reject the organization ${organizationName} </p>`,
		size: 'md',
	});
};

const btnOpenModal = fragmentElement.querySelector('.btnOpenModal');

if (btnOpenModal) {
	btnOpenModal.onclick = openModal;
}
