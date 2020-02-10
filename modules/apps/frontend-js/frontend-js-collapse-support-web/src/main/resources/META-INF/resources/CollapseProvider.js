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
	COLLAPSE: 'collapse',
	COLLAPSED: 'collapsed',
	COLLAPSING: 'collapsing',
	SHOW: 'show'
};

const Dimension = {
	HEIGHT: 'height',
	WIDTH: 'width'
};

const Selector = {
	TRIGGER: '[data-toggle="liferay-collapse"]'
};

class CollapseProvider {
	EVENT_HIDDEN = 'liferay.collapse.hidden';
	EVENT_HIDE = 'liferay.collapse.hide';
	EVENT_SHOW = 'liferay.collapse.show';
	EVENT_SHOWN = 'liferay.collapse.shown';

	constructor() {
		if (Liferay.CollapseProvider) {
			return Liferay.CollapseProvider;
		}

		this._setTransitionEndEvent();

		dom.delegate(
			document.body,
			'click',
			Selector.TRIGGER,
			this._onTriggerClick
		);

		Liferay.CollapseProvider = this;
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

		trigger.classList.add(CssClass.COLLAPSED);
		trigger.setAttribute('aria-expanded', false);

		const dimension = this._getDimension(panel);

		panel.style[dimension] = `${
			panel.getBoundingClientRect()[dimension]
		}px`;

		// Reflow to make sure dimention is set, without this transition may
		// not trigger.
		panel.getBoundingClientRect();

		panel.classList.remove(CssClass.COLLAPSE);

		this._transitioning = true;

		dom.once(panel, this._transitionEndEvent, () => {
			panel.classList.remove(CssClass.COLLAPSING);
			panel.classList.remove(CssClass.SHOW);
			panel.classList.add(CssClass.COLLAPSE);

			this._transitioning = false;

			Liferay.fire(this.EVENT_HIDDEN, {panel, trigger});
		});

		panel.classList.add(CssClass.COLLAPSING);
		panel.style[dimension] = 0;
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

		const parentId = trigger.dataset.parent;

		if (parentId) {
			const parent = document.querySelector(parentId);

			if (parent) {
				const expandedTriggers = parent.querySelectorAll(
					Selector.TRIGGER + ':not(.' + CssClass.COLLAPSED + ')'
				);

				expandedTriggers.forEach(expandedTrigger => {
					if (
						expandedTrigger !== trigger &&
						expandedTrigger.dataset.parent === parentId
					) {
						this.hide({trigger: expandedTrigger});
					}
				});
			}
		}

		Liferay.fire(this.EVENT_SHOW, {panel, trigger});

		trigger.classList.remove(CssClass.COLLAPSED);
		trigger.setAttribute('aria-expanded', true);

		const dimension = this._getDimension(panel);

		panel.classList.remove(CssClass.COLLAPSE);
		panel.classList.add(CssClass.COLLAPSING);
		panel.style[dimension] = 0;

		this._transitioning = true;

		dom.once(panel, this._transitionEndEvent, () => {
			panel.classList.remove(CssClass.COLLAPSING);
			panel.classList.add(CssClass.COLLAPSE);
			panel.classList.add(CssClass.SHOW);
			panel.style[dimension] = '';

			this._transitioning = false;

			Liferay.fire(this.EVENT_SHOWN, {panel, trigger});
		});

		const capitalizedDimension =
			dimension[0].toUpperCase() + dimension.slice(1);
		const scrollSize = `scroll${capitalizedDimension}`;

		panel.style[dimension] = `${panel[scrollSize]}px`;
	};

	_getDimension(panel) {
		const hasWidth = panel.classList.contains(Dimension.WIDTH);

		return hasWidth ? Dimension.WIDTH : Dimension.HEIGHT;
	}

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

export default CollapseProvider;
