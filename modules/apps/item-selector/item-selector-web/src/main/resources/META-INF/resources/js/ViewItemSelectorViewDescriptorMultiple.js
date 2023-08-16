/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getOpener} from 'frontend-js-web';

export default function ({
	itemSelectorReturnType,
	itemSelectorSelectedEvent,
	namespace,
}) {
	const searchContainer = Liferay.SearchContainer.get(`${namespace}entries`);

	const searchContainerOnHandler = searchContainer.on('rowToggled', () => {
		getOpener().Liferay.fire(itemSelectorSelectedEvent, {
			data: {
				returnType: itemSelectorReturnType,
				value: searchContainer.select
					.getAllSelectedElements()
					.getDOMNodes()
					.map((item) => item.closest('li, tr, dd'))
					.filter((domElement) => domElement)
					.map((domElement) => domElement.dataset.value),
			},
		});
	});

	return {
		dispose() {
			searchContainerOnHandler.dispose();
		},
	};
}
