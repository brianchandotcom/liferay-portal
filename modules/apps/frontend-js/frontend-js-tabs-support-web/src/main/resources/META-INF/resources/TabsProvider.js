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

import dom from 'metal-dom';

const CssClass = {
	ACTIVE: 'active',
	SHOW: 'show'
};

const Selector = {
	TRIGGER: '[data-toggle="liferay-tab"]'
};

class TabsProvider {
	EVENT_HIDDEN = 'liferay.tabs.hidden';
	EVENT_HIDE = 'liferay.tabs.hide';
	EVENT_SHOW = 'liferay.tabs.show';
	EVENT_SHOWN = 'liferay.tabs.shown';

	constructor() {
		if (Liferay.TabsProvider) {
			return Liferay.TabsProvider;
		}

		this._setTransitionEndEvent();

		dom.delegate(
			document.body,
			'click',
			Selector.TRIGGER,
			this._onTriggerClick
		);

		Liferay.TabsProvider = this;
	}

	hide = ({panel, trigger}) => {
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

		dom.once(panel, this._transitionEndEvent, () => {
			panel.classList.remove(CssClass.ACTIVE);

			this._transitioning = false;

			Liferay.fire(this.EVENT_HIDDEN, {panel, trigger});
		});
	};

	show = ({panel, trigger}) => {
		if (panel && !trigger) {
			trigger = this._getTrigger(panel);
		}

		if (!panel) {
			panel = this._getPanel(trigger);
		}

		if (this._transitioning || panel.classList.contains(CssClass.SHOW)) {
			return;
		}

		const activePanel = panel.parentElement.querySelector(
			`.${CssClass.SHOW}`
		);

		if (activePanel) {
			Liferay.on(this.EVENT_HIDDEN, event => {
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

	_getPanel(trigger) {
		return document.querySelector(trigger.getAttribute('href'));
	}

	_getTrigger(panel) {
		return document.querySelector(`[href="#${panel.getAttribute('id')}"]`);
	}

	_onTriggerClick = event => {
		const trigger = event.delegateTarget;

		if (trigger.tagName === 'A') {
			event.preventDefault();
		}

		const panel = this._getPanel(trigger);

		if (panel) {
			if (panel.classList.contains(CssClass.SHOW)) {
				this.hide({panel, trigger});
			}
			else {
				this.show({panel, trigger});
			}
		}
	};

	_setTransitionEndEvent() {
		const sampleElement = document.body;

		const transitionEndEvents = {
			MozTransition: 'transitionend',
			OTransition: 'oTransitionEnd otransitionend',
			WebkitTransition: 'webkitTransitionEnd',
			transition: 'transitionend'
		};

		let eventName = false;

		Object.keys(transitionEndEvents).some(name => {
			if (sampleElement.style[name] !== undefined) {
				eventName = transitionEndEvents[name];

				return true;
			}
		});

		this._transitionEndEvent = eventName;
	}
}

export default TabsProvider;
