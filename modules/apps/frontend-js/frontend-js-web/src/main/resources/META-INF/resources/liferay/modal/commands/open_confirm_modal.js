/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {escapeHTML} from '../../util/html_util';
import {openModal} from '../Modal';

const openConfirmModal = ({message, onConfirm, status, title}) => {
	if (Liferay.CustomDialogs.enabled) {
		openModal({
			bodyHTML: escapeHTML(message),
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					autoFocus: true,
					displayType: status ? status : 'primary',
					label: Liferay.Language.get('ok'),
					onClick: ({processClose}) => {
						processClose();

						onConfirm(true);
					},
				},
			],
			disableHeader: !title,
			footerCssClass: 'border-0',
			headerCssClass: 'border-0',
			onClose: () => onConfirm(false),
			role: 'alertdialog',
			status,
			title,
		});
	}
	else {
		onConfirm(confirm(message));
	}
};

export default openConfirmModal;
