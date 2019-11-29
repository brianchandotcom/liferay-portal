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

import {PortletBase} from 'frontend-js-web';
import core from 'metal';
import dom from 'metal-dom';

/**
 * EditAdaptiveMediaConfig
 *
 * @abstract
 * @extends {PortletBase}
 */

class EditAdaptiveMediaConfig extends PortletBase {
	/**
	 * @inheritDoc
	 */
	created() {
		this.validInputKeyCodes_ = [
			8,
			9,
			13,
			38,
			40,
			48,
			49,
			50,
			51,
			52,
			53,
			54,
			55,
			56,
			57
		];
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		const idOptions = this.one('#idOptions');

		if (idOptions) {
			dom.delegate(idOptions, 'change', 'input[type="radio"]', event =>
				this.onChangeUuidOptions_(event.delegateTarget)
			);
		}

		const nameInput = this.one('#name');

		if (nameInput) {
			nameInput.addEventListener('input', () => this.updateUuid());
		}

		this.nameInput = nameInput;

		const maxHeightInput = this.one('#maxHeight');
		const maxWidthInput = this.one('#maxWidth');

		if (maxWidthInput) {
			maxWidthInput.addEventListener('keydown', event => {
				this.handleKeyDown_(event);
			});

			maxWidthInput.addEventListener('input', () =>
				this.validateDimensions_()
			);
		}

		if (maxHeightInput) {
			maxHeightInput.addEventListener('keydown', event =>
				this.handleKeyDown_(event)
			);

			maxHeightInput.addEventListener('input', () =>
				this.validateDimensions_()
			);
		}

		this.maxHeightInput = maxHeightInput;
		this.maxWidthInput = maxWidthInput;

		this.newUuidInput = this.one('#newUuid');
		this.newUuidLabel = this.one('label', this.newUuidInput.parentNode);

		const saveButton = this.one('button[type=submit]');

		saveButton.addEventListener('click', event =>
			this.onSubmitForm_(event)
		);
	}

	/**
	 * Updates the uuid identifier based on the "name" field
	 * if the "Automatic" option is selected
	 */
	updateUuid() {
		const newUuidInput = this.newUuidInput;

		const uuidEmpty = !newUuidInput.value;

		if (
			this.isAutomaticUuid_() &&
			(uuidEmpty || this._originalUuidChanged)
		) {
			newUuidInput.value = Liferay.Util.normalizeFriendlyURL(
				this.nameInput.value
			);
		}

		this._originalUuidChanged = true;
	}

	/**
	 * Prevents from introducing letters in the input field.
	 *
	 * @param {KeyboardEvent} event The keyboard event.
	 */
	handleKeyDown_(event) {
		const code = event.keyCode || event.charCode;

		if (this.validInputKeyCodes_.indexOf(code) == -1) {
			event.preventDefault();
		}
	}

	/**
	 * Returns if the "Automatic" check is selected
	 * @return {Boolean} whether the "Automatic" radiobutton is checked or not.
	 */
	isAutomaticUuid_() {
		return this.one('input:checked', '#idOptions').value === 'true';
	}

	/**
	 * Checks if the uuid identifier has a custom
	 * value or it has to be generated automatically.
	 *
	 * @protected
	 */
	onChangeUuidOptions_() {
		const newUuidInput = this.newUuidInput;
		const newUuidLabel = this.newUuidLabel;

		if (this.isAutomaticUuid_()) {
			this._lastCustomUuuid = newUuidInput.value;

			this.updateUuid(this.nameInput.value);

			newUuidInput.setAttribute('disabled', true);
			newUuidLabel.classList.add('disabled');
		}
		else {
			newUuidInput.value = this._lastCustomUuuid || newUuidInput.value;

			newUuidInput.removeAttribute('disabled');
			newUuidLabel.classList.remove('disabled');
		}
	}

	/**
	 * Checks if there are form errors before
	 * submitting the AMI.
	 *
	 * @param {Event} event The event that
	 * triggered the submit action.
	 * @protected
	 */
	onSubmitForm_(event) {
		this.validateDimensions_();

		const form = Liferay.Form.get(this.ns('fm'));

		form.formValidator.validate();

		if (form.formValidator.hasErrors()) {
			event.preventDefault();
		}
		else {
			submitForm(form.form);
		}
	}

	/**
	 * Checks if max-widht or max-height has a value.
	 *
	 * @protected
	 */
	validateDimensions_() {
		const form = Liferay.Form.get(this.ns('fm'));

		const nsMaxHeight = this.ns('maxHeight');
		const nsMaxWidth = this.ns('maxWidth');

		const inputErrorMessage = Liferay.Language.get(
			'at-least-one-value-is-required'
		);
		const STR_BLANK = ' ';

		if (this.maxWidthInput.value || this.maxHeightInput.value) {
			form.removeRule(nsMaxWidth, 'required');
			form.removeRule(nsMaxHeight, 'required');
		}
		else {
			form.addRule(nsMaxWidth, 'required', inputErrorMessage);
			form.addRule(nsMaxHeight, 'required', STR_BLANK);

			form.formValidator.validateField(nsMaxWidth);
			form.formValidator.validateField(nsMaxHeight);
		}
	}
}

/**
 * EditAdaptiveMediaConfig State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
EditAdaptiveMediaConfig.STATE = {
	/**
	 * Node where errors will be rendered.
	 * @instance
	 * @memberof EditAdaptiveMediaConfig
	 * @type {String}
	 */
	errorNode: {
		validator: core.isString,
		value: '.error-wrapper'
	}
};

export default EditAdaptiveMediaConfig;
