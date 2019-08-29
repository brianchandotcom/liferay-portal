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

import {Config} from 'metal-state';
import fetch from './../../util/fetch.es';
import {isString} from 'metal';
import Soy from 'metal-soy';

import '../../compat/modal/Modal.es';
import PortletBase from '../../PortletBase.es';
import templates from './SimpleInputModal.soy';

/**
 * Manipulates small amounts of data with a form shown inside a modal.
 */
class SimpleInputModal extends PortletBase {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.addListener(
			'formSubmit',
			this._defaultFormSubmit.bind(this),
			true
		);
	}

	/**
	 * @inheritDoc
	 */
	rendered() {
		requestAnimationFrame(() => {
			this.refs.modal.refs.mainField.focus();
		});
	}

	/**
	 * The default event listener for form submission.
	 *
	 * @param {Event} event The event to listen for.
	 * @private
	 */
	_defaultFormSubmit(event) {
		fetch(this.formSubmitURL, {
			body: new FormData(event.form),
			method: 'POST'
		})
			.then(response => response.json())
			.then(responseContent => {
				if (responseContent.error) {
					this._loadingResponse = false;
					this._handleFormError(responseContent);
				} else {
					this._handleFormSuccess(responseContent);
				}
			})
			.catch(response => {
				this._handleFormError(response);
			});

		this._loadingResponse = true;
	}

	/**
	 * Callback executed when the <code>SimpleInputModal</code> Cancel button
	 * has been clicked.
	 *
	 * @private
	 */
	_handleCancelButtonClick() {
		this.emit('cancelButtonClicked');
	}

	/**
	 * Callback executed when the <code>SimpleInputModal</code> form has been
	 * submitted and it receives a server error as a response. It emits a form
	 * error event with the error message received.
	 *
	 * @param {{error: string}} responseContent The error response as a string.
	 * @private
	 */
	_handleFormError(responseContent) {
		this._errorMessage = responseContent.error || '';

		this.emit('formError', {
			errorMessage: this._errorMessage
		});
	}

	/**
	 * Callback executed when the <code>SimpleInputModal</code> form has been
	 * submitted. It prevents the default behavior and sends this form using a
	 * fetch request.
	 *
	 * @param {Event} event The default behavior for the submission event to
	 *        prevent.
	 * @private
	 */
	_handleFormSubmit(event) {
		event.preventDefault();

		this.emit('formSubmit', {
			form: this.refs.modal.refs.form
		});
	}

	/**
	 * Callback executed when the <code>SimpleInputModal</code> form has been
	 * submitted successfully. It emits a form success event with the redirect
	 * URL received.
	 *
	 * @param {{redirectURL: string}} responseContent The redirect URL as a
	 *        string.
	 * @private
	 */
	_handleFormSuccess(responseContent) {
		this.emit('formSuccess', {
			...responseContent,
			redirectURL: responseContent.redirectURL || ''
		});
	}

	/**
	 * Callback executed when the modal visibility property changes.
	 *
	 * @private
	 */
	_handleModalVisibleChanged() {
		this.emit('dialogHidden');
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
SimpleInputModal.STATE = {
	/**
	 * Form error message returned by the server.
	 *
	 * @default ''
	 * @instance
	 * @memberOf SimpleInputModal
	 * @private
	 * @type {!string}
	 */
	_errorMessage: Config.string()
		.internal()
		.value(''),

	/**
	 * Flag that checks whether a server response must be detected after a form
	 * submission.
	 *
	 * @default false
	 * @instance
	 * @memberOf SimpleInputModal
	 * @private
	 * @type {boolean}
	 */
	_loadingResponse: Config.bool()
		.internal()
		.value(false),

	/**
	 * Optional ClayAlert in SimpleInputModal
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {!object}
	 */
	alert: Config.shapeOf({
		message: Config.string(),
		style: Config.string(),
		title: Config.string()
	}),

	/**
	 * Label for the optional checkbox.
	 *
	 * @default ''
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {string}
	 */
	checkboxFieldLabel: Config.setter(checkboxFieldLabel => {
		return isString(checkboxFieldLabel) && checkboxFieldLabel
			? Soy.toIncDom(checkboxFieldLabel)
			: '';
	})
		.string()
		.value(''),

	/**
	 * Name for the optional checkbox.
	 *
	 * @default ''
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {string}
	 */
	checkboxFieldName: Config.string().value(''),

	/**
	 * Initial value for the optional checkbox.
	 *
	 * @default false
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {boolean}
	 */
	checkboxFieldValue: Config.bool().value(false),

	/**
	 * Modal window title.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {!string}
	 */
	dialogTitle: Config.string().required(),

	/**
	 * Adds a hidden bogus input when necessary to work around some form data +
	 * Ajax formatting issues. See {@link
	 * https://issues.liferay.com/browse/LPS-86960|LPS-86960} for more details.
	 *
	 * @default false
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {!string}
	 */
	fixFormData: Config.bool().value(false),

	/**
	 * URL where the form will be submitted.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {!string}
	 */
	formSubmitURL: Config.string().required(),

	/**
	 * Autogenerated ID provided by templates.
	 *
	 * @default ''
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {string}
	 */
	id: Config.string().value(''),

	/**
	 * Name for the hidden ID field.
	 *
	 * @default ''
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {string}
	 */
	idFieldName: Config.string().value(''),

	/**
	 * Value for the hidden ID field.
	 *
	 * @default ''
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {string}
	 */
	idFieldValue: Config.string().value(''),

	/**
	 * Label for the main field.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {!string}
	 */
	mainFieldLabel: Config.setter(mainFieldLabel =>
		Soy.toIncDom(mainFieldLabel)
	)
		.string()
		.required(),

	/**
	 * Name for the main field.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {!string}
	 */
	mainFieldName: Config.string().required(),

	/**
	 * Placeholder for the main field.
	 *
	 * @default ''
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {string}
	 */
	mainFieldPlaceholder: Config.string().value(''),

	/**
	 * Initial value for the main field.
	 *
	 * @default ''
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {string}
	 */
	mainFieldValue: Config.string().value(''),

	/**
	 * Namespace that will be prepended to field names.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {!string}
	 */
	namespace: Config.string().required(),

	/**
	 * URL for the portal icons being used.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SimpleInputModal
	 * @type {!string}
	 */
	spritemap: Config.string().required()
};

Soy.register(SimpleInputModal, templates);

export {SimpleInputModal};
export default SimpleInputModal;
