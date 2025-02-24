/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {escapeHTML} from 'frontend-js-web';

import openModal from './openModal';

export interface OpenAlertModalProps {
	message: string;
}

export default function openAlertModal({message}: OpenAlertModalProps) {
	if (Liferay.CustomDialogs.enabled) {
		openModal({
			bodyHTML: escapeHTML(message),
			buttons: [
				{
					autoFocus: true,
					label: Liferay.Language.get('ok'),
					onClick: ({processClose}) => {
						processClose();
					},
				},
			],
			center: true,
			disableHeader: true,
		});
	}
	else {
		alert(message);
	}
}
