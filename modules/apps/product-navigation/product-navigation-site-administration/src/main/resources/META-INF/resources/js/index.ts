/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate, openSelectionModal} from 'frontend-js-web';

// @ts-ignore

export function mySitesOpener(jsOnClickConfig) {
	const {selectEventName, title, url} = jsOnClickConfig;

	openSelectionModal({
		id: selectEventName,
		onSelect(selectedItem) {

			// @ts-ignore

			navigate(selectedItem.url);
		},
		selectEventName,
		title,
		url,
	});
}
