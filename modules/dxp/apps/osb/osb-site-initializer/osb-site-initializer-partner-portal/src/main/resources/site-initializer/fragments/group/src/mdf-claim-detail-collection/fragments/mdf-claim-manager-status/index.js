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

/* eslint-disable no-undef */
const findRequestIdUrl = (paramsUrl) => {
	const splitParamsUrl = paramsUrl.split('?');

	return splitParamsUrl[0];
};

const currentPath = Liferay.currentURL.split('/');
const mdfClaimId = findRequestIdUrl(currentPath.at(-1));

const updateStatusToApproved = fragmentElement.querySelector(
	'#status-approved'
);
const updateStatusToRequestMoreInfo = fragmentElement.querySelector(
	'#status-request'
);
const updateStatusToReject = fragmentElement.querySelector('#status-reject');

const updateInFinanceReview = fragmentElement.querySelector(
	'#status-in-finance-review'
);

const updateStatusPendingMarketingReview = fragmentElement.querySelector(
	'#pending-marketing-review'
);

const updateInDirectorReview = fragmentElement.querySelector(
	'#status-in-director-review'
);

const updateClaimPaid = fragmentElement.querySelector('#status-claim-paid');

const updateStatusToCanceled = fragmentElement.querySelector('#status-cancel');

const updateStatus = async (status) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const statusManagerResponse = await fetch(`/o/c/mdfclaims/${mdfClaimId}`, {
		body: `{"mdfClaimStatus": "${status}"}`,
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'PUT',
	});

	if (statusManagerResponse.ok) {
		location.reload();

		return;
	}

	Liferay.Util.openToast({
		message: 'The MDF Claim Status cannot be changed.',
		type: 'danger',
	});
};

if (updateStatusToApproved) {
	updateStatusToApproved.onclick = () =>
		Liferay.Util.openConfirmModal({
			message: 'Do you want to Approve this MDF?',
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateStatus('approved');
				}
			},
		});
}

if (updateStatusToRequestMoreInfo) {
	updateStatusToRequestMoreInfo.onclick = () =>
		Liferay.Util.openConfirmModal({
			message: 'Do you want to Request more info for this MDF?',
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateStatus('moreInfoRequested');
				}
			},
		});
}

if (updateStatusPendingMarketingReview) {
	updateStatusPendingMarketingReview.onclick = () =>
		Liferay.Util.openConfirmModal({
			message: 'Do you want to Pending Marketing Review for this MDF?',
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateStatus('pendingMarketingReview');
				}
			},
		});
}

if (updateStatusToReject) {
	updateStatusToReject.onclick = () =>
		Liferay.Util.openConfirmModal({
			message: 'Do you want to Reject this MDF?',
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateStatus('rejected');
				}
			},
		});
}

if (updateInFinanceReview) {
	updateInFinanceReview.onclick = () =>
		Liferay.Util.openConfirmModal({
			message: 'Do you want Finance Review this MDF?',
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateStatus('inFinanceReview');
				}
			},
		});
}

if (updateInDirectorReview) {
	updateInDirectorReview.onclick = () =>
		Liferay.Util.openConfirmModal({
			message: 'Do you want Director Review this MDF?',
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateStatus('inDirectorReview');
				}
			},
		});
}

if (updateClaimPaid) {
	updateClaimPaid.onclick = () =>
		Liferay.Util.openConfirmModal({
			message: 'Do you want Claim Paid this MDF?',
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateStatus('claimPaid');
				}
			},
		});
}

if (updateStatusToCanceled) {
	updateStatusToCanceled.onclick = () =>
		Liferay.Util.openConfirmModal({
			message: 'Do you want to Cancel this MDF?',
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateStatus('canceled');
				}
			},
		});
}

const getMDFClaimStatus = async () => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const statusResponse = await fetch(`/o/c/mdfclaims/${mdfClaimId}`, {
		headers: {
			'accept': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	if (statusResponse.ok) {
		const data = await statusResponse.json();

		fragmentElement.querySelector(
			'#mdf-claim-status-display'
		).innerHTML = `Status: ${Liferay.Util.escape(
			data.mdfClaimStatus.name
		)}`;

		updateButtons(data.mdfClaimStatus.key);

		return;
	}

	Liferay.Util.openToast({
		message: 'An unexpected error occured.',
		type: 'danger',
	});
};

const updateButtons = (mdfClaimStatusKey) => {
	if (mdfClaimStatusKey === 'pendingMarketingReview') {
		if (updateStatusToRequestMoreInfo) {
			updateStatusToRequestMoreInfo.classList.toggle('d-flex');
		}
		if (updateStatusToReject) {
			updateStatusToReject.classList.toggle('d-flex');
		}
		if (updateStatusToApproved) {
			updateStatusToApproved.classList.toggle('d-flex');
		}
		if (updateInFinanceReview) {
			updateInFinanceReview.classList.toggle('d-flex');
		}
	}
	if (mdfClaimStatusKey === 'approved') {
		if (updateInFinanceReview) {
			updateInFinanceReview.classList.toggle('d-flex');
		}
		if (updateStatusToCanceled) {
			updateStatusToCanceled.classList.toggle('d-flex');
		}
	}

	if (mdfClaimStatusKey === 'inFinanceReview') {
		if (updateStatusToApproved) {
			updateStatusToApproved.classList.toggle('d-flex');
		}
		if (updateClaimPaid) {
			updateClaimPaid.classList.toggle('d-flex');
		}
		if (updateStatusToRequestMoreInfo) {
			updateStatusToRequestMoreInfo.classList.toggle('d-flex');
		}
		if (updateStatusToReject) {
			updateStatusToReject.classList.toggle('d-flex');
		}
		if (updateInDirectorReview) {
			updateInDirectorReview.classList.toggle('d-flex');
		}
	}

	if (mdfClaimStatusKey === 'moreInfoRequested') {
		if (updateStatusPendingMarketingReview) {
			updateStatusPendingMarketingReview.classList.toggle('d-flex');
		}
		if (updateStatusToApproved) {
			updateStatusToApproved.classList.toggle('d-flex');
		}
		if (updateClaimPaid) {
			updateClaimPaid.classList.toggle('d-flex');
		}
		if (updateInFinanceReview) {
			updateInFinanceReview.classList.toggle('d-flex');
		}
		if (updateStatusToReject) {
			updateStatusToReject.classList.toggle('d-flex');
		}
	}
	if (mdfClaimStatusKey === 'rejected') {
		if (updateStatusPendingMarketingReview) {
			updateStatusPendingMarketingReview.classList.toggle('d-flex');
		}
	}
	if (mdfClaimStatusKey === 'draft') {
		if (updateStatusPendingMarketingReview) {
			updateStatusPendingMarketingReview.classList.toggle('d-flex');
		}
		if (updateStatusToCanceled) {
			updateStatusToCanceled.classList.toggle('d-flex');
		}
	}
	if (mdfClaimStatusKey === 'inDirectorReview') {
		if (updateStatusToApproved) {
			updateStatusToApproved.classList.toggle('d-flex');
		}
		if (updateInFinanceReview) {
			updateInFinanceReview.classList.toggle('d-flex');
		}
		if (updateStatusToReject) {
			updateStatusToReject.classList.toggle('d-flex');
		}
		if (updateStatusToRequestMoreInfo) {
			updateStatusToRequestMoreInfo.classList.toggle('d-flex');
		}
	}
	if (mdfClaimStatusKey === 'canceled') {
		if (updateStatusToApproved) {
			updateStatusToApproved.classList.toggle('d-flex');
		}
	}
};

if (layoutMode !== 'edit') {
	getMDFClaimStatus();
}
