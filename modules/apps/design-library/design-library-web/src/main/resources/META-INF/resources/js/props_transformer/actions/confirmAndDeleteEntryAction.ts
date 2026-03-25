/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, openToast} from 'frontend-js-components-web';

import {FDS_EVENT_UPDATE_DISPLAY} from '../../constants';
import DesignLibraryService from '../../services/DesignLibraryService';

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
				onClick: async ({processClose}: {processClose: () => void}) => {
					processClose();

					try {
						await DesignLibraryService.remove(deleteAction);

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
					}
					catch (error: any) {
						openToast({
							message: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
							type: 'danger',
						});
					}
				},
			},
		],
		role: 'alert',
		status: 'danger',
		title,
	});
}
