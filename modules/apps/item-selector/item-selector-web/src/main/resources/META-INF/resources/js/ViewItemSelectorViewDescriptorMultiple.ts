/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getOpener} from 'frontend-js-web';

export interface Props {
	itemSelectorReturnType: string;
	itemSelectorSelectedEvent: string;
	namespace: string;
}

export default function ({
	itemSelectorReturnType,
	itemSelectorSelectedEvent,
	namespace,
}: Props) {

	// @ts-ignore

	const searchContainer = Liferay.SearchContainer.get(`${namespace}entries`);

	const searchContainerOnHandler = searchContainer.on('rowToggled', () => {
		const searchContainerItems: HTMLElement[] = searchContainer.select
			.getAllSelectedElements()
			.getDOMNodes();

		getOpener().Liferay.fire(itemSelectorSelectedEvent, {
			data: {
				returnType: itemSelectorReturnType,
				value: searchContainerItems
					.map((item: HTMLElement) => item.closest('li, tr, dd'))
					.filter((domElement): domElement is HTMLElement =>
						Boolean(domElement)
					)
					.map((domElement) => domElement.dataset.value),
			},
		});
	});

	return {
		dispose() {
			searchContainerOnHandler.detach();
		},
	};
}
