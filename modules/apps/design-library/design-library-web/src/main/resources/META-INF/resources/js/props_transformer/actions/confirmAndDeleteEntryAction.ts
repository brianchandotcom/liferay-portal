/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, openToast} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';

const FDS_EVENT_UPDATE_DISPLAY = 'fds-update-display';

export default function confirmAndDeleteEntryAction({
	bodyHTML,
	dataSetId,
	deleteAction,
	loadData,
	successMessage,
	title,
}: {
	bodyHTML: string;
	dataSetId?: string;
	deleteAction: {href: string; method: string};
	loadData: () => void;
	successMessage: string;
	title: string;
}) {
	return openModal({
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
				onClick: ({processClose}: {processClose: () => void}) => {
					processClose();

					const DEFAULT_ERROR = Liferay.Language.get(
						'an-unexpected-error-occurred'
					);

					return fetch(deleteAction.href, {
						headers: {
							'Accept': 'application/json',
							'Accept-Language':
								Liferay.ThemeDisplay.getBCP47LanguageId(),
							'Content-Type': 'application/json',
						},
						method: deleteAction.method,
					})
						.then((response) => {
							if (!response.ok) {
								throw new Error(DEFAULT_ERROR);
							}

							return response.status === 204
								? ''
								: response.json();
						})
						.then(() => {
							openToast({
								message:
									successMessage ||
									Liferay.Language.get(
										'your-request-completed-successfully'
									),
								type: 'success',
							});

							loadData();

							if (dataSetId) {
								Liferay.fire(FDS_EVENT_UPDATE_DISPLAY, {
									id: dataSetId,
								});
							}
						})
						.catch(() => {
							openToast({
								message: DEFAULT_ERROR,
								type: 'danger',
							});
						});
				},
			},
		],
		containerProps: {
			className: '',
		},
		role: 'alert',
		status: 'danger',
		title,
	});
}
