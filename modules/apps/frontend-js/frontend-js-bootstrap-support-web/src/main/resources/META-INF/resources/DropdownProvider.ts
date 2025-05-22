/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import domAlign from 'dom-align';
import {delegate} from 'frontend-js-web';

const CssClass = {
	SHOW: 'show',
};

const Selector = {
	ITEM: '.dropdown-item',
	MENU: '.dropdown-menu',
	TRIGGER: '[data-toggle="liferay-dropdown"]',
};

const KEYCODES = {
	ARROW_DOWN: 40,
	ARROW_UP: 38,
	ESCAPE: 27,
	SPACE: 32,
	TAB: 9,
};

class DropdownProvider {
	EVENT_HIDDEN = 'liferay.dropdown.hidden';
	EVENT_HIDE = 'liferay.dropdown.hide';
	EVENT_SHOW = 'liferay.dropdown.show';
	EVENT_SHOWN = 'liferay.dropdown.shown';

	constructor() {
		if (Liferay.DropdownProvider) {

			// @ts-ignore

			return Liferay.DropdownProvider;
		}

		delegate(
			document.body,
			'click',
			Selector.TRIGGER,
			this._onTriggerClick
		);

		delegate(
			document.body,
			'keydown',
			Selector.TRIGGER,
			this._onTriggerKeyDown
		);

		delegate(document.body, 'keydown', Selector.ITEM, this._onItemKeyDown);

		this._warnNotButtonTrigger();

		Liferay.DropdownProvider = this;
	}

	hide = ({menu, trigger}: {menu?: any; trigger?: any}) => {
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

	show = ({menu, trigger}: {menu?: any; trigger?: any}) => {
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

		const clickOutsideHandler = (event: any) => {
			if (
				!menu.contains(event.target) &&
				!trigger.contains(event.target)
			) {
				this.hide({menu, trigger});

				document.removeEventListener('mousedown', clickOutsideHandler);
				document.removeEventListener('touchstart', clickOutsideHandler);
			}
		};

		document.addEventListener('mousedown', clickOutsideHandler);
		document.addEventListener('touchstart', clickOutsideHandler);

		menu.classList.add(CssClass.SHOW);

		domAlign(menu, trigger, {
			overflow: {
				adjustX: true,
				adjustY: true,
			},
			points: ['tl', 'bl'],
		});

		Liferay.fire(this.EVENT_SHOWN, {menu, trigger});
	};

	_getMenu(trigger: any) {
		return trigger.parentElement.querySelector(Selector.MENU);
	}

	_getNextSibling({
		item,
		items,
	}: {
		item: HTMLElement;
		items: Array<HTMLElement>;
	}) {
		let nextItemIndex = items.indexOf(item) + 1;

		if (nextItemIndex === items.length) {
			nextItemIndex = 0;
		}

		return items[nextItemIndex] as HTMLElement;
	}

	_getPreviousSibling({
		item,
		items,
	}: {
		item: HTMLElement;
		items: Array<HTMLElement>;
	}) {
		let previousItemIndex = items.indexOf(item) - 1;

		if (previousItemIndex < 0) {
			previousItemIndex = items.length - 1;
		}

		return items[previousItemIndex];
	}

	_getTrigger(menu: any) {
		return menu.parentElement.querySelector('.dropdown-toggle');
	}

	_onItemKeyDown = (event: any) => {
		const {delegateTarget: item, keyCode} = event;

		const menu: HTMLElement = item.closest(Selector.MENU);

		const items: Array<HTMLElement> = Array.from(
			menu.querySelectorAll(Selector.ITEM)
		);

		const trigger = this._getTrigger(menu);

		if (keyCode === KEYCODES.ESCAPE) {
			this.hide({menu});

			trigger.focus();
		}
		else if (keyCode === KEYCODES.TAB) {
			this.hide({menu});
		}
		else if (keyCode === KEYCODES.ARROW_DOWN) {
			event.preventDefault();

			const nextSibling = this._getNextSibling({item, items});

			nextSibling.focus();
		}
		else if (keyCode === KEYCODES.ARROW_UP) {
			event.preventDefault();

			const previousSibling = this._getPreviousSibling({item, items});

			previousSibling.focus();
		}
	};

	_onTriggerKeyDown = (event: any) => {
		const {delegateTarget: trigger, keyCode} = event;

		const menu = this._getMenu(trigger);

		const menuOpen = menu.classList.contains(CssClass.SHOW);

		if (keyCode === KEYCODES.ESCAPE) {
			if (menuOpen) {
				this.hide({menu, trigger});
			}

			trigger.focus();
		}
		else if (keyCode === KEYCODES.TAB && menuOpen) {
			this.hide({menu, trigger});
		}
		else if (
			keyCode === KEYCODES.ARROW_DOWN ||
			keyCode === KEYCODES.SPACE
		) {
			event.preventDefault();

			if (!menuOpen) {
				this.show({menu, trigger});
			}

			const firstMenuItem = menu.querySelector(Selector.ITEM);

			if (firstMenuItem) {
				firstMenuItem.focus();
			}
		}
	};

	_onTriggerClick = (event: any) => {
		event.preventDefault();

		const trigger = event.delegateTarget;

		if (trigger.tagName === 'A') {
			event.preventDefault();
		}

		const menu = this._getMenu(trigger);

		if (menu) {
			if (menu.classList.contains(CssClass.SHOW)) {
				this.hide({menu, trigger});
			}
			else {
				this.show({menu, trigger});
			}
		}
	};

	_warnNotButtonTrigger() {
		const triggerElements = document.querySelectorAll(
			`:not(button)${Selector.TRIGGER}`
		);

		triggerElements.forEach((element) => {
			console.warn('This Dropdown Trigger should be a button');
			console.warn(element);
		});
	}
}

export default DropdownProvider;
