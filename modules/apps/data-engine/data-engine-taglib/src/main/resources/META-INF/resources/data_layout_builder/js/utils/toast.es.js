/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-web';

export function errorToast(
	message = Liferay.Language.get('an-unexpected-error-occurred'),
	title = Liferay.Language.get('error')
) {
	openToast({
		message,
		title: `${title}`,
		type: 'danger',
	});
}

export function successToast(
	message = Liferay.Language.get('your-request-completed-successfully'),
	title = Liferay.Language.get('success')
) {
	openToast({
		message,
		title: `${title}`,
		type: 'success',
	});
}
