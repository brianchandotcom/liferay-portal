/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

export type ConnectSitesData = {
	action: 'connectSites';
	title: string;
};

export default function connectSitesAction(data: ConnectSitesData) {
	openModal({
		bodyHTML: `<p>${data.title}</p>`,
		center: true,
		containerProps: {},
		size: 'lg',
		title: data.title,
	});
}
