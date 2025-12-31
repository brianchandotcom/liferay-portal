/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, sub} from 'frontend-js-web';

import {openCMSModal} from './openCMSModal';
import {displayErrorToast, displayRequestSuccessToast} from './toastUtil';

const openGenericFDSDeleteConfirmationModal = (
	bodyHTML: string,
	deleteMethod: any,
	deleteURL: any,
	itemName: string,
	loadData: any,
	displayCustomSuccessToast?: () => void
) => {
	openCMSModal({
		bodyHTML,
		buttons: [
			{
				autoFocus: true,
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: 'danger',
				label: Liferay.Language.get('delete'),
				onClick: ({processClose}: {processClose: Function}) => {
					processClose();

					if (deleteMethod && deleteURL) {
						fetch(deleteURL, {
							headers: {
								'Accept': 'application/json',
								'Content-Type': 'application/json',
								'x-csrf-token': Liferay.authToken,
							},
							method: deleteMethod,
						})
							.then(() => {
								if (displayCustomSuccessToast) {
									displayCustomSuccessToast();
								}
								else {
									displayRequestSuccessToast();
								}

								loadData();
							})
							.catch(() => {
								displayErrorToast();
							});
					}
					else {
						displayErrorToast();
					}
				},
			},
		],
		status: 'danger',
		title: sub(Liferay.Language.get('delete-x'), '"' + itemName + '"'),
	});
};

export {openGenericFDSDeleteConfirmationModal};
