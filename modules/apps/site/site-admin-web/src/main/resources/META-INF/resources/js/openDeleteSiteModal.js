/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, sub} from 'frontend-js-web';

export default function openDeleteSiteModal({multiple = false, onDelete}) {
	openModal({
		bodyHTML: Liferay.Language.get(
			'deleting-a-site-is-an-action-impossible-to-revert'
		),
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
				onClick: ({processClose}) => {
					processClose();

					onDelete();
				},
			},
		],
		role: 'alert',
		status: 'danger',
		title: sub(
			Liferay.Language.get('delete-x'),
			multiple
				? Liferay.Language.get('sites')
				: Liferay.Language.get('site')
		),
	});
}
