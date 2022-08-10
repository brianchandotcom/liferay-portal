/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
export default function hideProductMenuIfPresent({onHide}) {
	const sideNavigation = Liferay.SideNavigation?.instance(
		document.querySelector('.product-menu-toggle')
	);

	if (sideNavigation?.visible()) {
		let removeListener = () => {};

		const listener = sideNavigation.on('closed.lexicon.sidenav', () => {
			onHide();

			removeListener();
		});

		removeListener = () => listener.removeListener();

		sideNavigation.hide();
	}
	else {
		onHide();
	}
}
