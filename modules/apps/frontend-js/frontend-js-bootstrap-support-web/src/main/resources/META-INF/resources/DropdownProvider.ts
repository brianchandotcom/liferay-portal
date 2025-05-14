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
	ITEM: '[class="dropdown-item"]',
	TRIGGER: '[data-toggle="liferay-dropdown"]',
};

const KEYCODES = {
	ARROW_DOWN: 40,
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
		return trigger.parentElement.querySelector('.dropdown-menu');
	}

	_getTrigger(menu: any) {
		return menu.parentElement.querySelector('.dropdown-toggle');
	}

	_onItemKeyDown = (event: any) => {
		if (
			event.keyCode === KEYCODES.TAB ||
			event.keyCode === KEYCODES.ESCAPE
		) {
			this._onTriggerClick(event);
		}
	};

	_onTriggerKeyDown = (event: any) => {
		if (
			event.keyCode === KEYCODES.ARROW_DOWN ||
			event.keyCode === KEYCODES.ESCAPE ||
			(event.shiftKey && event.keyCode === KEYCODES.TAB) ||
			(event.keyCode === KEYCODES.SPACE &&
				event.delegateTarget.tagName === 'A')
		) {
			this._onTriggerClick(event);
		}
	};

	_onTriggerClick = (event: any) => {
		if (!(event.shiftKey && event.keyCode === KEYCODES.TAB)) {
			event.preventDefault();
		}

		const tagName = event.delegateTarget.tagName;

		if (tagName === 'BUTTON') {
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
					if (
						event.keyCode !== KEYCODES.ESCAPE &&
						!(event.shiftKey && event.keyCode === KEYCODES.TAB)
					) {
						this.show({menu, trigger});
					}
				}
			}
		}
		else {
			if (!(event.shiftKey && event.keyCode === KEYCODES.TAB)) {
				const menu = event.delegateTarget.parentElement.parentElement;

				const trigger = menu.previousElementSibling;

				if (menu) {
					if (menu.classList.contains(CssClass.SHOW)) {
						this.hide({menu, trigger});
					}
					else {
						this.show({menu, trigger});
					}
				}
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
