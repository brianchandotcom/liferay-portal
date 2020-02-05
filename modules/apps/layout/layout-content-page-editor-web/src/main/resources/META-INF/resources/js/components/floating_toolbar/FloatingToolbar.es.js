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
import {Align} from 'metal-position';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbar.soy';

/**
 * @type {object}
 */
const FIXED_PANEL_CLASS = 'fragments-editor__floating-toolbar-panel--fixed';

/**
 * @type {object}
 */
const ELEMENT_AVAILABLE_POSITIONS = {
	bottom: [
		Align.Bottom,
		Align.BottomCenter,
		Align.BottomLeft,
		Align.BottomRight
	],

	left: [Align.BottomLeft, Align.Left, Align.LeftCenter, Align.TopRight],
	right: [Align.BottomRight, Align.Right, Align.RightCenter, Align.TopRight],
	top: [Align.Top, Align.TopCenter, Align.TopLeft, Align.TopRight]
};

/**
 * @type {object}
 */
const ELEMENT_POSITION = {
	bottom: {
		left: Align.BottomLeft,
		right: Align.BottomRight
	},

	top: {
		left: Align.TopLeft,
		right: Align.TopRight
	}
};

/**
 * FloatingToolbar
 */
class FloatingToolbar extends Component {
	/**
	 * Gets a suggested align of an element to an anchor, following this logic:
	 * - Vertically, if the element fits at bottom, it's placed there, otherwise
	 *   it is placed at top.
	 * - Horizontally, if the element fits at right, it's placed there, otherwise
	 *   it is placed at left.
	 * @param {HTMLElement|null} element
	 * @param {HTMLElement|null} anchor
	 * @private
	 * @return {number} Selected align
	 * @review
	 */
	static _getElementAlign(element, anchor) {
		const alignFits = (align, availableAlign) =>
			availableAlign.includes(
				Align.suggestAlignBestRegion(element, anchor, align).position
			);

		const anchorRect = anchor.getBoundingClientRect();
		const elementRect = element.getBoundingClientRect();
		const horizontal =
			anchorRect.right - elementRect.width > 0 ? 'right' : 'left';

		const fallbackVertical = 'top';
		let vertical = 'bottom';

		if (
			!alignFits(
				ELEMENT_POSITION[vertical][horizontal],
				ELEMENT_AVAILABLE_POSITIONS[vertical]
			) &&
			alignFits(
				ELEMENT_POSITION[fallbackVertical][horizontal],
				ELEMENT_AVAILABLE_POSITIONS[fallbackVertical]
			)
		) {
			vertical = fallbackVertical;
		}

		return ELEMENT_POSITION[vertical][horizontal];
	}

	/**
	 * Gets the height of the element matching the selector
	 * Defaults to 0
	 * @param {string} selector
	 */
	static _getElementHeight(selector) {
		const element = document.querySelector(selector);

		if (element) {
			return element.offsetHeight;
		}

		return 0;
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		this._defaultButtonClicked = this._defaultButtonClicked.bind(this);
		this._handleWindowResize = this._handleWindowResize.bind(this);
		this._handleWrapperScroll = this._handleWrapperScroll.bind(this);

		this._lastSelectedPanelId = this.selectedPanelId;

		this._managementBarHeight = FloatingToolbar._getElementHeight(
			'.management-bar'
		);
		this._productMenuHeight = FloatingToolbar._getElementHeight(
			'.control-menu'
		);

		window.addEventListener('resize', this._handleWindowResize);

		this._wrapper = document.querySelector(
			'.fragment-entry-link-list-wrapper'
		);

		this._wrapper.addEventListener('scroll', this._handleWrapperScroll);
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		this.addListener('buttonClicked', this._defaultButtonClicked, true);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		window.removeEventListener('resize', this._handleWindowResize);

		this._wrapper.removeEventListener('scroll', this._handleWrapperScroll);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		nextState = setIn(
			nextState,
			['selectedPanelId'],
			this._isAnchorElementVisible() ? state.selectedPanelId : null
		);

		return nextState;
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	rendered() {
		this._setFixedPanelClass();

		requestAnimationFrame(() => {
			this._align();
		});
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	syncSelectedPanelId() {
		requestAnimationFrame(() => {
			if (this.refs.selectedPanel) {
				this.refs.selectedPanel.on('clearEditor', () =>
					this.emit('clearEditor')
				);

				this.refs.selectedPanel.on('createProcessor', () =>
					this.emit('createProcessor')
				);
			}
		});
	}

	/**
	 * Select or deselect panel. Default handler for button clicked event.
	 * @param {Event} event
	 * @param {Object} data
	 * @private
	 */
	_defaultButtonClicked(event, data) {
		const {panelId} = data;

		if (!event.defaultPrevented) {
			if (this.selectedPanelId === panelId) {
				this.selectedPanelId = null;
			}
			else {
				this.selectedPanelId = panelId;
				this._lastSelectedPanelId = panelId;
			}
		}
	}
	/**
	 * Controls the visibility of the panel.
	 * The panel will be shown only when the anchor element is visible
	 * @private
	 * @review
	 */
	_handlePanelVisibilityOnScroll() {
		if (!this._isAnchorElementVisible()) {
			this.selectedPanelId = null;
		}
		else {
			if (this._lastSelectedPanelId && !this.selectedPanelId) {
				this.selectedPanelId = this._lastSelectedPanelId;
			}
		}
	}

	/**
	 * Handle panel button click
	 * @param {MouseEvent} event Click event
	 */
	_handlePanelButtonClick(event) {
		const {panelId = null, type} = event.delegateTarget.dataset;

		this.emit('buttonClicked', event, {
			panelId,
			type
		});
	}

	/**
	 * @private
	 * @review
	 */
	_handleWindowResize() {
		this._align();
	}

	/**
	 * @private
	 * @review
	 */
	_handleWrapperScroll() {
		this._align();
		this._handlePanelVisibilityOnScroll();
	}

	/**
	 * Check whether the anchor element is visible or not
	 * @private
	 * @review
	 */
	_isAnchorElementVisible() {
		if (this.anchorElement) {
			const anchorElementRect = this.anchorElement.getBoundingClientRect();
			const anchorElementY = anchorElementRect.y || anchorElementRect.top;
			const anchorElementBottom =
				anchorElementY + anchorElementRect.height;

			return (
				anchorElementBottom >
				this._productMenuHeight + this._managementBarHeight
			);
		}
		else {
			return false;
		}
	}

	/**
	 * Aligns the FloatingToolbar to the anchorElement
	 * @private
	 * @review
	 */
	_align() {
		AUI().use('portal-available-languages', () => {
			if (this.refs.buttons && this.anchorElement) {
				const buttonsAlign = FloatingToolbar._getElementAlign(
					this.refs.panel || this.refs.buttons,
					this.anchorElement
				);

				Align.align(
					this.refs.buttons,
					this.anchorElement,
					buttonsAlign,
					false
				);

				requestAnimationFrame(() => {
					this._alignPanel();
				});
			}
			else if (this.anchorElement) {
				this._alignPanel();
			}
		});
	}

	/**
	 * Align FloatingToolbar panel to it's buttons or anchorElement
	 * @private
	 * @review
	 */
	_alignPanel() {
		if (this.refs.panel && this.anchorElement) {
			const panelAlign = FloatingToolbar._getElementAlign(
				this.refs.panel,
				this.refs.buttons || this.anchorElement
			);

			Align.align(
				this.refs.panel,
				this.refs.buttons || this.anchorElement,
				panelAlign,
				false
			);
		}
	}

	/**
	 * Add fixed CSS class to panel if buttons are not shown
	 * @private
	 * @review
	 */
	_setFixedPanelClass() {
		if (this.refs.panel && !this.refs.buttons) {
			this.refs.panel.classList.add(FIXED_PANEL_CLASS);
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbar.STATE = {
	/**
	 * Used for restoring the panel after hiding it
	 * @default null
	 * @instance
	 * @memberOf FloatingToolbar
	 * @private
	 * @review
	 * @type {string|null}
	 */
	_lastSelectedPanelId: Config.string()
		.internal()
		.value(null),

	/**
	 * @default 0
	 * @instance
	 * @memberOf FloatingToolbar
	 * @private
	 * @review
	 * @type {number}
	 */
	_managementBarHeight: Config.number()
		.internal()
		.value(0),

	/**
	 * @default 0
	 * @instance
	 * @memberOf FloatingToolbar
	 * @private
	 * @review
	 * @type {number}
	 */
	_productMenuHeight: Config.number()
		.internal()
		.value(0),

	/**
	 * @default null
	 * @instance
	 * @memberof FloatingToolbar
	 * @private
	 * @review
	 * @type {object}
	 */
	_wrapper: Config.object()
		.internal()
		.value(null),

	/**
	 * Element where the floating toolbar is positioned with
	 * @default undefined
	 * @instance
	 * @memberof FloatingToolbar
	 * @review
	 * @type {HTMLElement}
	 */
	anchorElement: Config.instanceOf(HTMLElement).required(),

	/**
	 * List of available buttons.
	 * @default undefined
	 * @instance
	 * @memberOf FloatingToolbar
	 * @review
	 * @type {object[]}
	 */
	buttons: Config.arrayOf(
		Config.shapeOf({
			cssClass: Config.string(),
			icon: Config.string(),
			id: Config.string(),
			panelId: Config.string(),
			title: Config.string(),
			type: Config.string()
		})
	).required(),

	/**
	 * If true, once a panel has been selected it cannot be changed
	 * until selectedPanelId is set manually to null.
	 * @default false
	 * @instance
	 * @memberof FloatingToolbar
	 * @review
	 * @type {boolean}
	 */
	fixSelectedPanel: Config.bool().value(false),

	/**
	 * Selected panel ID.
	 * @default null
	 * @instance
	 * @memberOf FloatingToolbar
	 * @private
	 * @review
	 * @type {string|null}
	 */
	selectedPanelId: Config.string()
		.internal()
		.value(null)
};

const ConnectedFloatingToolbar = getConnectedComponent(FloatingToolbar, [
	'spritemap'
]);

Soy.register(ConnectedFloatingToolbar, templates);

export {ConnectedFloatingToolbar, FloatingToolbar};
export default ConnectedFloatingToolbar;
