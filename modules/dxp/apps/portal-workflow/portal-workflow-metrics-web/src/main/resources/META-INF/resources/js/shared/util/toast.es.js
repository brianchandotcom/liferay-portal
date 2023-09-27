/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast as liferayToast} from 'frontend-js-web';

export function openErrorToast({message}) {
	openToast(message, Liferay.Language.get('error'), 'danger');
}

export function openSuccessToast(
	message,
	title = Liferay.Language.get('success')
) {
	openToast(message, title, 'success');
}

export function openToast(message, title, type) {
	liferayToast({message, title, type});
}
