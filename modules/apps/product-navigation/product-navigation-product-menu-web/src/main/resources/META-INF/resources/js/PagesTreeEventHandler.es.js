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

import Component from 'metal-component';

class PagesTreeEventHandler extends Component {
	attached() {
		Liferay.on('liferay.dropdown.show', this._handleDropdownOpened);
	}

	disposed() {
		Liferay.detach('liferay.dropdown.show', this._handleDropdownOpened);
	}

	_handleDropdownOpened({menu, trigger}) {
		if (menu.closest('.pages-tree-dropdown')) {
			const handler = (event) => {
				if (!event.target.closest('.pages-tree-dropdown')) {
					Liferay.DropdownProvider.hide({menu, trigger});

					window.removeEventListener('click', handler);
				}
			};

			window.addEventListener('click', handler);
		}
	}
}

export default PagesTreeEventHandler;
