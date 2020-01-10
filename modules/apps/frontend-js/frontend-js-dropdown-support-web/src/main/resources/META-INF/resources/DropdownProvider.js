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

import domAlign from 'dom-align';
import dom from 'metal-dom';

const CssClass = {
	SHOW: 'show'
};

const Selector = {
	TRIGGER: '[data-toggle="liferay-dropdown"]'
};

class DropdownProvider {
	EVENT_HIDDEN = 'liferay.dropdown.hidden';
	EVENT_HIDE = 'liferay.dropdown.hide';
	EVENT_SHOW = 'liferay.dropdown.show';
	EVENT_SHOWN = 'liferay.dropdown.shown';

	constructor() {
		if (Liferay.DropdownProvider) {
			return Liferay.DropdownProvider;
		}

		dom.delegate(
			document.body,
			'click',
			Selector.TRIGGER,
			this._onTriggerClick
		);

		Liferay.DropdownProvider = this;
	}

	hide = ({menu, trigger}) => {
		if (menu && !trigger) {
			trigger = this._getTrigger(menu);
		}

		if (!menu) {
			menu = this._getMenu(trigger);
		}

		if (!menu.classList.contains(CssClass.SHOW)) {
			return;
		}

		Liferay.fire(this.EVENT_HIDE, {menu, trigger});

		trigger.parentElement.classList.remove(CssClass.SHOW);
		trigger.setAttribute('aria-expanded', false);

		menu.classList.remove(CssClass.SHOW);

		Liferay.fire(this.EVENT_HIDDEN, {menu, trigger});
	};

	show = ({menu, trigger}) => {
		if (menu && !trigger) {
			trigger = this._getTrigger(menu);
		}

		if (!menu) {
			menu = this._getMenu(trigger);
		}

		if (menu.classList.contains(CssClass.SHOW)) {
			return;
		}

		Liferay.fire(this.EVENT_SHOW, {menu, trigger});

		trigger.parentElement.classList.add(CssClass.SHOW);
		trigger.setAttribute('aria-expanded', true);

		menu.classList.add(CssClass.SHOW);

		domAlign(menu, trigger, {
			overflow: {
				adjustX: true,
				adjustY: true
			},
			points: ['tl', 'bl']
		});

		Liferay.fire(this.EVENT_SHOWN, {menu, trigger});
	};

	_getMenu(trigger) {
		return trigger.parentElement.querySelector('.dropdown-menu');
	}

	_getTrigger(menu) {
		return menu.parentElement.querySelector('.dropdown-toggle');
	}

	_onTriggerClick = event => {
		const trigger = event.delegateTarget;

		if (trigger.tagName === 'A') {
			event.preventDefault();
		}

		const menu = this._getMenu(trigger);

		if (menu) {
			if (menu.classList.contains(CssClass.SHOW)) {
				this.hide({menu, trigger});
			} else {
				this.show({menu, trigger});
			}
		}
	};
}

export default DropdownProvider;
