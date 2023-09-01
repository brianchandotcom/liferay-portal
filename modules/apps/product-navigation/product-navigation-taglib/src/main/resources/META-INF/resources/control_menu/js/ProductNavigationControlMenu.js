/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ProductNavigationControlMenu({namespace}) {
	const controlMenu = document.getElementById(`${namespace}controlMenu`);

	const eventHandler = () => {
		Liferay.fire('initLayout');
	};

	if (controlMenu) {
		Liferay.Util.toggleControls(controlMenu);

		['focus', 'mousemove', 'touchstart'].forEach((event) =>
			controlMenu.addEventListener(event, eventHandler)
		);

		['focus', 'mousemove', 'touchstart'].forEach((event) =>
			controlMenu.removeEventListener(event, eventHandler)
		);
	}

	const sidenavToggles = controlMenu.querySelectorAll(
		`[data-toggle="liferay-sidenav"]`
	);

	const sidenavInstances = Array.from(sidenavToggles)
		.map((toggle) => Liferay.SideNavigation.instance(toggle))
		.filter((instance) => instance);

	sidenavInstances.forEach((instance) => {
		instance.on('openStart.lexicon.sidenav', (source) => {
			sidenavInstances.forEach((sidenav) => {
				if (sidenav !== source) {
					sidenav.hide();
				}
			});
		});
	});
}
