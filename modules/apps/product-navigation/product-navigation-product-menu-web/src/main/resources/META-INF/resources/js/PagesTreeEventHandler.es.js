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
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import {Config} from 'metal-state';

class PagesTreeEventHandler extends Component {
	attached() {
		this._eventHandler = new EventHandler();

		this._eventHandler.add(
			dom.delegate(
				document,
				'click',
				'.layout-action',
				this._handleLayoutActionClick.bind(this)
			)
		);

		Liferay.on('liferay.dropdown.show', this._handleDropdownOpened);
	}

	disposed() {
		this._eventHandler.removeAllListeners();
	}

	addChildPage(plid) {
		const url = `${this.addLayoutURL}&${this.portletNamescape}selPlid=${plid}`;

		Liferay.Util.navigate(url);
	}

	configure(plid) {
		const url = `${this.configureLayoutURL}&${this.portletNamescape}selPlid=${plid}`;

		Liferay.Util.navigate(url);
	}

	_handleLayoutActionClick(event) {
		const {delegateTarget} = event;

		const {action, plid} = delegateTarget.dataset;

		this[action](plid);
	}

	_handleDropdownOpened({menu, trigger}) {
		if (dom.closest(menu, '.pages-tree-dropdown')) {
			const handler = (event) => {
				if (!dom.closest(event.target, '.pages-tree-dropdown')) {
					Liferay.DropdownProvider.hide({menu, trigger});

					window.removeEventListener('click', handler);
				}
			};

			window.addEventListener('click', handler);
		}
	}
}

PagesTreeEventHandler.STATE = {
	addLayoutURL: Config.string(),
	configureLayoutURL: Config.string(),
	deleteLayoutURL: Config.string(),
	duplicateLayoutURL: Config.string(),
	editLayoutURL: Config.string(),
	portletNamescape: Config.string(),
};

export default PagesTreeEventHandler;
