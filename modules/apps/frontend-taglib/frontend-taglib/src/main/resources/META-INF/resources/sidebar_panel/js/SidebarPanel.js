/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {debounce, fetch, runScriptsInElement} from 'frontend-js-web';

export default function ({resourceURL, searchContainerId, targetNodeId}) {
	let searchContainer;

	const detachSearchContainerRegisterHandle = () => {
		if (searchContainerRegisterHandle) {
			Liferay.detach(searchContainerRegisterHandle);
		}
	};

	const getSidebarContent = debounce(() => {
		fetch(resourceURL, {
			body: new FormData(searchContainer.getForm().getDOM()),
			method: 'POST',
		})
			.then((response) => response.text())
			.then((response) => {
				const searchContainerSidebar =
					document.getElementById(targetNodeId);
				searchContainerSidebar.innerHTML = response;

				runScriptsInElement(searchContainerSidebar);
			});
	}, 50);

	const onSearchContainerRegistered = (event) => {
		const searchContainerRegistered = event.searchContainer;

		if (searchContainerRegistered.get('id') === searchContainerId) {
			searchContainer = searchContainerRegistered;

			detachSearchContainerRegisterHandle();

			searchContainer.on('rowToggled', getSidebarContent);
		}
	};

	const searchContainerRegisterHandle = Liferay.on(
		'search-container:registered',
		onSearchContainerRegistered
	);

	return {
		dispose() {
			Liferay.destroyComponent(searchContainerRegisterHandle);
		},
	};
}
