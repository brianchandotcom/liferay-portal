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
import {delegate, on} from 'metal-dom';
import {Align} from 'metal-position';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './DisabledAreaPopover.soy';

/**
 * Mapping from metal-position to popover positions
 * @review
 * @type {!object}
 */
const POPOVER_POSITIONS = {
	[Align.Bottom]: 'bottom',
	[Align.BottomLeft]: 'bottom',
	[Align.BottomRight]: 'bottom',
	[Align.Left]: 'left',
	[Align.Right]: 'right',
	[Align.Top]: 'top',
	[Align.TopLeft]: 'top',
	[Align.TopRight]: 'top'
};

/**
 * DisabledAreaPopover
 * @review
 */
class DisabledAreaPopover extends Component {
	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this._attachListener();

		this._documentListeners = [
			on(document.body, 'click', this._handleDocumentClick.bind(this)),

			on(
				'.fragment-entry-link-list-wrapper',
				'scroll',
				this._handleFragmentEntryLinkListScroll.bind(this)
			)
		];
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this._detachListener();

		this._documentListeners.forEach(documentListener =>
			documentListener.removeListener()
		);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	rendered() {
		if (this._position) {
			requestAnimationFrame(() => {
				let popoverPosition = Align.TopCenter;

				const suggestedAlign = Align.suggestAlignBestRegion(
					this.refs.popover,
					this._clickedElement,
					popoverPosition
				);

				if (suggestedAlign.position !== popoverPosition) {
					popoverPosition = Align.BottomCenter;
				}

				Align.align(
					this.refs.popover,
					this._clickedElement,
					popoverPosition,
					false
				);

				this._setPosition(popoverPosition);
			});
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	syncSelector() {
		this._detachListener();
		this._attachListener();
	}

	/**
	 * Attachs a click listener for elements defined in selector
	 * property and calls _detachListener for old selectors if necessary.
	 * @private
	 * @review
	 */
	_attachListener() {
		if (this._elementListener) {
			this._detachListener();
		}

		if (this.selector) {
			this._elementListener = delegate(
				document.body,
				'click',
				this.selector,
				this._handleElementClick.bind(this)
			);
		}
	}

	/**
	 * Dettachs a click listener (if any).
	 * @private
	 * @review
	 */
	_detachListener() {
		if (this._elementListener) {
			this._elementListener.removeListener();

			this._elementListener = null;
		}
	}

	/**
	 * Handles a click over DOM (but not over a defined element)
	 * updating the _clickedElement property and setting visibility to false.
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDocumentClick() {
		this._hidePopover();
	}

	/**
	 * Handles a click over any element defined in selector property by
	 * updating the _clickedElement property and setting visibility to true.
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleElementClick(event) {
		event.stopImmediatePropagation();

		this._clickedElement = event.delegateTarget;

		this._setPosition(Align.TopCenter);
	}

	/**
	 * Handles a click over DOM (but not over a defined element)
	 * updating the _clickedElement property and setting visibility to false.
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleFragmentEntryLinkListScroll() {
		this._hidePopover();
	}

	/**
	 * Hides the popover
	 * @private
	 * @review
	 */
	_hidePopover() {
		this._clickedElement = null;
		this._position = null;
	}

	/**
	 * Sets the popover position from the position given by metal-position
	 * align method.
	 * @param {number} alignPosition
	 * @private
	 * @review
	 */
	_setPosition(alignPosition) {
		if (this._position !== POPOVER_POSITIONS[alignPosition]) {
			this._position = POPOVER_POSITIONS[alignPosition];
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
DisabledAreaPopover.STATE = {
	/**
	 * Clicked element
	 * @default null
	 * @instance
	 * @memberOf DisabledAreaPopover
	 * @private
	 * @review
	 * @type {!object}
	 */
	_clickedElement: Config.object().value(null),

	/**
	 * Listeners attached to document
	 * @default []
	 * @instance
	 * @memberOf DisabledAreaPopover
	 * @private
	 * @review
	 * @type {!Array<object>}
	 */
	_documentListeners: Config.arrayOf(Config.object()).value([]),

	/**
	 * Click listener attached to DOM
	 * @default null
	 * @instance
	 * @memberOf DisabledAreaPopover
	 * @private
	 * @review
	 * @type {!object}
	 */
	_elementListener: Config.object().value(null),

	/**
	 * Popover position
	 * @default null
	 * @instance
	 * @memberOf DisabledAreaPopover
	 * @private
	 * @review
	 * @type {!string}
	 */
	_position: Config.string().value(null),

	/**
	 * Selector for elements where this popover should be shown
	 * @default undefined
	 * @instance
	 * @memberOf DisabledAreaPopover
	 * @review
	 * @type {!string}
	 */
	selector: Config.string().required()
};

Soy.register(DisabledAreaPopover, templates);

export {DisabledAreaPopover};
export default DisabledAreaPopover;
