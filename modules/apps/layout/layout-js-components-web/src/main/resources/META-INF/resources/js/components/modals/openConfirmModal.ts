/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

type Props = {
	buttonLabel: string;
	center?: boolean;
	hideCancel?: boolean;
	onCancel?: () => void;
	onConfirm?: () => void;
	status: 'info' | 'warning';
	text?: string;
	title: string;
};

export default function openConfirmModal({
	buttonLabel,
	center,
	hideCancel,
	onCancel,
	onConfirm,
	status,
	text,
	title,
}: Props) {
	const buttons = [];

	if (!hideCancel) {
		buttons.push({
			autoFocus: true,
			displayType: 'secondary' as const,
			label: Liferay.Language.get('cancel'),
			onClick: ({processClose} = {processClose: () => {}}) => {
				processClose();

				if (onCancel) {
					onCancel();
				}
			},
			type: 'cancel' as const,
		});
	}

	buttons.push({
		displayType: status,
		label: buttonLabel,
		onClick: ({processClose} = {processClose: () => {}}) => {
			processClose();

			if (onConfirm) {
				onConfirm();
			}
		},
	});

	openModal({
		bodyHTML: text,
		buttons,
		center,
		status,
		title,
	});
}
