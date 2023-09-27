/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate} from 'frontend-js-web';

const CssClass = {
	ACTIVE: 'active',
	SHOW: 'show',
};

const Selector = {
	TRIGGER: '[data-toggle="liferay-tab"]',
};

class TabsProvider {
	_transitioning?: boolean;
	_transitionEndEvent?: any;

	EVENT_HIDDEN = 'liferay.tabs.hidden';
	EVENT_HIDE = 'liferay.tabs.hide';
	EVENT_SHOW = 'liferay.tabs.show';
	EVENT_SHOWN = 'liferay.tabs.shown';

	constructor() {
		if (Liferay.TabsProvider) {

			// @ts-ignore

			return Liferay.TabsProvider;
		}

		this._setTransitionEndEvent();

		delegate(
			document.body,
			'click',
			Selector.TRIGGER,
			this._onTriggerClick
		);

		Liferay.TabsProvider = this;
	}

	hide = ({panel, trigger}: {panel?: any; trigger?: any}) => {
		if (panel && !trigger) {
			trigger = this._getTrigger(panel);
		}

		if (!panel) {
			panel = this._getPanel(trigger);
		}

		if (this._transitioning || !panel.classList.contains(CssClass.SHOW)) {
			return;
		}

		Liferay.fire(this.EVENT_HIDE, {panel, trigger});

		trigger.classList.remove(CssClass.ACTIVE);
		trigger.setAttribute('aria-selected', false);

		panel.classList.remove(CssClass.SHOW);

		this._transitioning = true;

		if (this._prefersReducedMotion()) {
			this._onFadeEnd(panel, trigger);
		}
		else {
			panel.addEventListener(
				this._transitionEndEvent,
				() => this._onFadeEnd(panel, trigger),
				{once: true}
			);
		}
	};

	show = ({panel, trigger}: {panel?: any; trigger?: any}) => {
		if (panel && !trigger) {
			trigger = this._getTrigger(panel);
		}

		if (!panel) {
			panel = this._getPanel(trigger);
		}

		if (this._transitioning || panel.classList.contains(CssClass.SHOW)) {
			return;
		}

		const panels = Array.from(panel.parentElement.children);

		const activePanels = panels.filter((item: any) => {
			return item.classList.contains(CssClass.SHOW);
		});

		if (activePanels.length) {
			const activePanel = activePanels[0];

			Liferay.once(this.EVENT_HIDDEN, (event) => {
				if (event.panel === activePanel) {
					this.show({panel, trigger});
				}
			});

			this.hide({panel: activePanel});
		}
		else {
			Liferay.fire(this.EVENT_SHOW, {panel, trigger});

			trigger.classList.add(CssClass.ACTIVE);
			trigger.setAttribute('aria-selected', true);

			panel.classList.add(CssClass.ACTIVE);
			panel.classList.add(CssClass.SHOW);

			Liferay.fire(this.EVENT_SHOWN, {panel, trigger});
		}
	};

	_getPanel(trigger: any) {
		return document.querySelector(trigger.getAttribute('href'));
	}

	_getTrigger(panel: any) {
		return document.querySelector(`[href="#${panel.getAttribute('id')}"]`);
	}

	_onFadeEnd = (panel: any, trigger: any) => {
		panel.classList.remove(CssClass.ACTIVE);

		this._transitioning = false;

		Liferay.fire(this.EVENT_HIDDEN, {panel, trigger});
	};

	_onTriggerClick = (event: any) => {
		const trigger = event.delegateTarget;

		if (trigger.tagName === 'A') {
			event.preventDefault();
		}

		const panel = this._getPanel(trigger);

		if (panel && !panel.classList.contains(CssClass.SHOW)) {
			this.show({panel, trigger});
		}
	};

	_prefersReducedMotion() {
		return window.matchMedia('(prefers-reduced-motion: reduce)').matches;
	}

	_setTransitionEndEvent() {
		const sampleElement = document.body;

		const transitionEndEvents = {
			MozTransition: 'transitionend',
			OTransition: 'oTransitionEnd otransitionend',
			WebkitTransition: 'webkitTransitionEnd',
			transition: 'transitionend',
		};

		let eventName = false;

		Object.keys(transitionEndEvents).some((name) => {

			// @ts-ignore

			if (sampleElement.style[name] !== undefined) {

				// @ts-ignore

				eventName = transitionEndEvents[name];

				return true;
			}
		});

		this._transitionEndEvent = eventName;
	}
}

export default TabsProvider;
