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

import {debounce} from 'frontend-js-web';
import Component from 'metal-component';
import dom from 'metal-dom';
import {Align} from 'metal-position';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import templates from './FragmentEditableFieldTooltip.soy';

/**
 * @type number
 */
const WINDOW_RESIZE_DEBOUNCE_DELAY = 100;

/**
 * Creates a Fragment Editable Field Tooltip component.
 */
class FragmentEditableFieldTooltip extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this._alignTooltip();
	}

	/**
	 * @inheritDoc
	 */
	created() {
		this._handleDocumentClick = this._handleDocumentClick.bind(this);
		this._handleFragmentEntryLinkListWrapperScroll = this._handleFragmentEntryLinkListWrapperScroll.bind(
			this
		);

		this._handleWindowResize = debounce(
			this._handleWindowResize.bind(this),
			WINDOW_RESIZE_DEBOUNCE_DELAY
		);

		this._windowResizeHandler = dom.on(
			window,
			'resize',
			this._handleWindowResize
		);

		this._documentClickHandler = dom.on(
			document.body,
			'click',
			this._handleDocumentClick
		);

		const fragmentEntryLinkListWrapper = document.querySelector(
			'.fragment-entry-link-list-wrapper'
		);

		if (fragmentEntryLinkListWrapper) {
			fragmentEntryLinkListWrapper.addEventListener(
				'scroll',
				this._handleFragmentEntryLinkListWrapperScroll
			);
		}
	}

	/**
	 * @inheritDoc
	 */
	disposed() {
		this._documentClickHandler.removeListener();
		this._windowResizeHandler.removeListener();

		const fragmentEntryLinkListWrapper = document.querySelector(
			'.fragment-entry-link-list-wrapper'
		);

		if (fragmentEntryLinkListWrapper) {
			fragmentEntryLinkListWrapper.removeEventListener(
				'scroll',
				this._handleFragmentEntryLinkListWrapperScroll
			);
		}
	}

	/**
	 * @inheritDoc
	 */
	rendered() {
		this._alignTooltip();
	}

	/**
	 * Aligns the tooltip position for editable fields.
	 *
	 * @private
	 */
	_alignTooltip() {
		if (this.refs.tooltip) {
			Align.align(this.refs.tooltip, this.alignElement, Align.Top, false);
		}
	}

	/**
	 * Handles a button click.
	 *
	 * @param {MouseEvent} event
	 */
	_handleButtonClick(event) {
		const button = event.delegateTarget;
		const buttonId = button.dataset.tooltipButtonId;

		this.emit('buttonClick', {
			buttonId
		});
	}

	/**
	 * Hides the tooltip when a document click occurs outside the tooltip.
	 *
	 * @param {MouseEvent} event The document click.
	 */
	_handleDocumentClick(event) {
		if (
			this.refs.tooltip &&
			!this.refs.tooltip.contains(event.target) &&
			!this.alignElement.contains(event.target)
		) {
			this.emit('outsideTooltipClick');
		}
	}

	/**
	 * Callback executed to align the tooltip when the window is resized.
	 *
	 * @private
	 */
	_handleWindowResize() {
		this._alignTooltip();
	}

	/**
	 * @private
	 */
	_handleFragmentEntryLinkListWrapperScroll() {
		this._alignTooltip();
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
FragmentEditableFieldTooltip.STATE = {
	/**
	 * Reference element the tooltip alignment is based on.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableFieldTooltip
	 * @type {HTMLElement}
	 */
	alignElement: Config.object().required(),

	/**
	 * List of buttons rendered inside the tooltip.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableFieldTooltip
	 * @type {!Array<{
	 *   id: !string,
	 *   label: !string
	 * }>}
	 */
	buttons: Config.arrayOf(
		Config.shapeOf({
			icon: Config.string().required(),
			id: Config.string().required(),
			label: Config.string().required()
		})
	)
};

const ConnectedFragmentEditableFieldTooltip = getConnectedComponent(
	FragmentEditableFieldTooltip,
	['spritemap']
);

Soy.register(ConnectedFragmentEditableFieldTooltip, templates);

export {ConnectedFragmentEditableFieldTooltip, FragmentEditableFieldTooltip};
export default ConnectedFragmentEditableFieldTooltip;
