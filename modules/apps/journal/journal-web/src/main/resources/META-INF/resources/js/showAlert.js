/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-web';

import removeAlert from './removeAlert';

export default function showAlert(
	message,
	title = Liferay.Language.get('error'),
	type = 'danger'
) {
	removeAlert();

	const articleContentWrapper = document.querySelector(
		'.article-content-content'
	);

	const alertContainer = document.createElement('div');

	alertContainer.classList.add('journal-alert-container');
	articleContentWrapper.prepend(alertContainer);

	openToast({
		autoClose: false,
		container: alertContainer,
		message,
		onClose: () => alertContainer.remove(),
		title,
		type,
	});
}
