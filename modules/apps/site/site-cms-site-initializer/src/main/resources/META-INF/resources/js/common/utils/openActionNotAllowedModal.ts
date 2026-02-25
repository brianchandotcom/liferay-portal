/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openCMSModal} from './openCMSModal';

export function openActionNotAllowedModal() {
	return openCMSModal({
		bodyHTML: Liferay.Language.get(
			'this-action-is-not-available-for-the-item-you-have-selected'
		),
		buttons: [
			{
				autoFocus: true,
				displayType: 'warning',
				label: Liferay.Language.get('ok'),
				type: 'cancel',
			},
		],
		center: true,
		status: 'warning',
		title: Liferay.Language.get('action-not-allowed'),
	});
}
