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

import {Align} from 'metal-position';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './FloatingToolbarDropdown.soy';
import {Config} from 'metal-state';

/**
 * FloatingToolbarDropdown
 */
class FloatingToolbarDropdown extends Component {
	/**
	 * @inheritdoc
	 */
	created() {
		this._handleButtonClick = this._handleButtonClick.bind(this);
		this._handleWindowMouseUp = this._handleWindowMouseUp.bind(this);
		this._handleScroll = this._handleScroll.bind(this);

		this._button = document.querySelector(`#${this.buttonId}`);
		this._fragmentEntryLinkListWrapper = document.querySelector(
			'.fragment-entry-link-list-wrapper'
		);

		this._button.addEventListener('click', this._handleButtonClick);

		this._fragmentEntryLinkListWrapper.addEventListener(
			'scroll',
			this._handleScroll
		);

		document.body.addEventListener('scroll', this._handleScroll);

		window.addEventListener('mouseup', this._handleWindowMouseUp);
	}

	/**
	 * @inheritdoc
	 */
	disposed() {
		window.removeEventListener('mouseup', this._handleWindowMouseUp);

		this._fragmentEntryLinkListWrapper.removeEventListener(
			'scroll',
			this._handleScroll
		);

		document.body.removeEventListener('scroll', this._handleScroll);
	}

	/**
	 * @inheritdoc
	 */
	rendered() {
		if (!this._dropdown) {
			this._dropdown = document.querySelector(`#${this.dropdownId}`);
		}
	}

	/**
	 * Align dropdown to button
	 * @private
	 * @review
	 */
	_alignDropdown() {
		Align.align(this._dropdown, this._button, Align.LeftCenter, false);
	}

	/**
	 * Handle button click
	 * @private
	 * @review
	 */
	_handleButtonClick() {
		this._dropdown.classList.add('show');

		this._alignDropdown();
	}

	/**
	 * Handle FragmentEntryLinkListWrapper scroll
	 * @private
	 * @review
	 */
	_handleScroll() {
		this._alignDropdown();
	}

	/**
	 * Handle window mouseup event
	 * @private
	 * @review
	 */
	_handleWindowMouseUp() {
		this._dropdown.classList.remove('show');
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarDropdown.STATE = {
	/**
	 * @default undefined
	 * @memberof FloatingToolbarDropdown
	 * @review
	 * @type {object}
	 */
	_button: Config.object().internal(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarDropdown
	 * @review
	 * @type {object}
	 */
	_dropdown: Config.object().internal(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarDropdown
	 * @review
	 * @type {string}
	 */
	buttonId: Config.string(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarDropdown
	 * @review
	 * @type {string}
	 */
	dropdownId: Config.string()
};

Soy.register(FloatingToolbarDropdown, templates);

export {FloatingToolbarDropdown};
export default FloatingToolbarDropdown;
