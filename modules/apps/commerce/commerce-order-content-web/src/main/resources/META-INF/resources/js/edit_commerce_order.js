/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

export default function ({commerceOrderImporterTypeKey, title, url}) {
	const commerceOrderImporterTypeKeySelector = document.querySelector(
		`.${commerceOrderImporterTypeKey}`
	);

	commerceOrderImporterTypeKeySelector.addEventListener('click', (event) => {
		event.preventDefault();

		openModal({
			id: commerceOrderImporterTypeKey,
			iframeBodyCssClass: '',
			title,
			url,
		});
	});
}
