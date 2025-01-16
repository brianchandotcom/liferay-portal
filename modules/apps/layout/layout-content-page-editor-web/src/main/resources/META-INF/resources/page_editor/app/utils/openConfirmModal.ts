/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

type Props = {
	buttonLabel: string;
	center?: boolean;
	onCancel?: () => void;
	onConfirm?: () => void;
	status: 'info' | 'warning';
	text: string;
	title: string;
};

export function openConfirmModal({
	buttonLabel,
	center,
	onCancel,
	onConfirm,
	status,
	text,
	title,
}: Props) {
	openModal({
		bodyHTML: text,
		buttons: [
			{
				autoFocus: true,
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				onClick: ({processClose} = {processClose: () => {}}) => {
					processClose();

					if (onCancel) {
						onCancel();
					}
				},
				type: 'cancel',
			},
			{
				displayType: status,
				label: buttonLabel,
				onClick: ({processClose} = {processClose: () => {}}) => {
					processClose();

					if (onConfirm) {
						onConfirm();
					}
				},
			},
		],
		center,
		status,
		title,
	});
}
