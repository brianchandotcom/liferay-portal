/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {escapeHTML} from '../../util/html_util';
import {openModal} from '../Modal';

const openAlertModal = ({message}) => {
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
};

export default openAlertModal;
