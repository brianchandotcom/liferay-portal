/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-web';

const titlesMap = {
	danger: Liferay.Language.get('danger'),
	success: Liferay.Language.get('success'),
	warning: Liferay.Language.get('warning'),
};

export function showNotification(
	message,
	type = 'success',
	closeable = true,
	duration = 500,
	title
) {
	openToast({
		closeable,
		delay: {
			hide: 5000,
			show: 0,
		},
		duration,
		message,
		title: title || titlesMap[type],
		type,
	});
}

export function showErrorNotification(
	error = Liferay.Language.get('unexpected-error')
) {
	showNotification(error, 'danger');
}
