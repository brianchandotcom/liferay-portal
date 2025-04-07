/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from '@liferay/layout-js-components-web';

type Props = {
	onContinue: () => Promise<void>;
};

export function openFormConversionModal({onContinue}: Props) {
	openConfirmModal({
		buttonLabel: Liferay.Language.get('continue'),
		onConfirm: onContinue,
		status: 'info',
		text: Liferay.Language.get(
			'adding-a-stepper-fragment-inside-a-simple-form-will-turn-it-into-a-multistep-form'
		),
		title: Liferay.Language.get('convert-to-multistep-form'),
	});
}
