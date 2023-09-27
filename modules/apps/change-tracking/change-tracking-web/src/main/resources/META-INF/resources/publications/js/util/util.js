/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-web';

const showNotification = (message, error, afterFunc) => {
	const openToastParams = {message};

	if (error) {
		openToastParams.title = Liferay.Language.get('error');
		openToastParams.type = 'danger';
	}

	openToast(openToastParams);

	if (afterFunc !== undefined) {
		afterFunc();
	}
};

export {showNotification};
