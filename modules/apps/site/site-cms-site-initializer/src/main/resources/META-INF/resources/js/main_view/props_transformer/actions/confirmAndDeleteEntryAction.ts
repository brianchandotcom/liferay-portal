/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDS_EVENT_UPDATE_DISPLAY} from '../../../common/utils/constants';
import {openCMSModal} from '../../../common/utils/openCMSModal';
import {executeAsyncItemAction} from '../utils/executeAsyncItemAction';

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
				onClick: ({processClose}: {processClose: () => void}) => {
					processClose();

					executeAsyncItemAction({
						method: deleteAction.method,
						refreshData: (responseData) => {
							loadData();

							Liferay.fire(FDS_EVENT_UPDATE_DISPLAY, {
								id: dataSetId,
							});
						},
						successMessage,
						url: deleteAction.href,
					});
				},
			},
		],
		role: 'alert',
		status: 'danger',
		title,
	});
}
