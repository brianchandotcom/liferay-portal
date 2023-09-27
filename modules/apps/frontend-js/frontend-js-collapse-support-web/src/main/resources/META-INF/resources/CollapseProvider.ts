/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate, isReducedMotion} from 'frontend-js-web';

const CssClass = {
	COLLAPSE: 'collapse',
	COLLAPSED: 'collapsed',
	COLLAPSING: 'collapsing',
	SHOW: 'show',
};

const Dimension = {
	HEIGHT: 'height',
	WIDTH: 'width',
};

const Selector = {
	TRIGGER: '[data-toggle="liferay-collapse"]',
};

class CollapseProvider {
	_transitioning?: boolean;
	_transitionEndEvent?: any;

	EVENT_HIDDEN = 'liferay.collapse.hidden';
	EVENT_HIDE = 'liferay.collapse.hide';
	EVENT_SHOW = 'liferay.collapse.show';
	EVENT_SHOWN = 'liferay.collapse.shown';

	constructor() {

		// @ts-ignore

		if (Liferay.CollapseProvider) {

			// @ts-ignore

			return Liferay.CollapseProvider;
		}

		this._setTransitionEndEvent();

		delegate(
			document.body,
			'click',
			Selector.TRIGGER,
			this._onTriggerClick
		);

		// @ts-ignore

		Liferay.CollapseProvider = this;
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

		const onHidden = () => {
			panel.classList.remove(CssClass.COLLAPSING);
			panel.classList.remove(CssClass.SHOW);
			panel.classList.add(CssClass.COLLAPSE);

			this._transitioning = false;

			Liferay.fire(this.EVENT_HIDDEN, {panel, trigger});
		};

		if (isReducedMotion()) {
			onHidden();
		}
		else {
			panel.addEventListener(this._transitionEndEvent, onHidden, {
				once: true,
			});

			panel.classList.add(CssClass.COLLAPSING);
		}

		panel.style.removeProperty(dimension);
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

		const parentId = trigger.dataset.parent;

		if (parentId) {
			const parent = document.querySelector(parentId);

			if (parent) {
				const expandedTriggers = parent.querySelectorAll(
					Selector.TRIGGER + ':not(.' + CssClass.COLLAPSED + ')'
				);

				expandedTriggers.forEach((expandedTrigger: any) => {
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

		const onShown = () => {
			panel.classList.remove(CssClass.COLLAPSING);
			panel.classList.add(CssClass.COLLAPSE);
			panel.classList.add(CssClass.SHOW);
			panel.style[dimension] = '';

			this._transitioning = false;

			Liferay.fire(this.EVENT_SHOWN, {panel, trigger});
		};

		if (isReducedMotion()) {
			onShown();
		}
		else {
			panel.addEventListener(this._transitionEndEvent, onShown, {
				once: true,
			});

			const capitalizedDimension =
				dimension[0].toUpperCase() + dimension.slice(1);
			const scrollSize = `scroll${capitalizedDimension}`;

			panel.style[dimension] = `${panel[scrollSize]}px`;
		}
	};

	_getDimension(panel: any) {
		const hasWidth = panel.classList.contains(Dimension.WIDTH);

		return hasWidth ? Dimension.WIDTH : Dimension.HEIGHT;
	}

	_getPanel(trigger: any) {
		return document.querySelector(
			trigger.getAttribute('href') || trigger.dataset.target
		);
	}

	_getTrigger(panel: any) {
		return document.querySelector(`[href="#${panel.getAttribute('id')}"]`);
	}

	_onTriggerClick = (event: any) => {
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
			transition: 'transitionend',
		} as const;

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

export default CollapseProvider;
