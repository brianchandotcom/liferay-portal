/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

type Props = {
	onContinue: () => {};
};

export function openFormConversionModal({onContinue}: Props) {
	openModal({
		bodyHTML: Liferay.Language.get(
			'adding-a-stepper-fragment-inside-a-simple-form-will-turn-it-into-a-multistep-form'
		),

		buttons: [
			{
				autoFocus: true,
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				onClick: ({processClose} = {processClose: () => {}}) => {
					processClose();
				},
				type: 'cancel',
			},
			{
				displayType: 'info',
				label: Liferay.Language.get('continue'),
				onClick: ({processClose} = {processClose: () => {}}) => {
					processClose();

					onContinue();
				},
			},
		],
		status: 'info',
		title: Liferay.Language.get('convert-to-multistep-form'),
	});
}
