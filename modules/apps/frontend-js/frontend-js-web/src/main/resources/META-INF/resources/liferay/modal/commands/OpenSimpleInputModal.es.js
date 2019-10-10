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

import navigate from '../../util/navigate.es';
import SimpleInputModal from '../components/SimpleInputModal.es';

/**
 * Function that implements the SimpleInputModal pattern, which allows
 * manipulating small amounts of data with a form shown inside a modal.
 *
 * @param {Object} alert An optional alert for the modal
 * @param {Object} options The list of options for the modal (see description)
 * @param {string} checkboxFieldLabel The label for the optional checkbox field, if it's included
 * @param {string} checkboxFieldName The name for the optional checkbox field, if it's included
 * @param {boolean} checkboxFieldValue The value for the optional checkbox field, if it's included
 * @param {!string} dialogTitle The modal window's title
 * @param {!string} formSubmitURL The URL where the form will be submitted
 * @param {string} idFieldName The ID of the optional hidden field, if it's included
 * @param {string} idFieldValue The value of the optional hidden field, if it's included
 * @param {!string} mainFieldLabel The main input field's label
 * @param {!string} mainFieldName The main input field's name
 * @param {string} mainFieldPlaceholder A placeholder value for main input field
 * @param {string} mainFieldValue The main input field's value
 * @param {!string} namespace The namespace to prepend to field names
 * @param {!string} spritemap The URL for the portal icons used in the modal
 *
 * @return {SimpleInputModal} SimpleInputModal component instance
 *
 * @see SimpleInputModal
 *
 * @description
 *
 * Execution flow
 * 	1. When the function is called, a delegated click event
 * 	   is bound to the specified elementSelector, and
 * 	   a SimpleInputModal component is created when fired.
 * 	2. When the form is submitted, a POST request is sent to
 * 	   the server and a formSubmit event is dispatched.
 * 	3. SimpleInputModal processes the response.
 * 	   3.1. If the response is successful, a formSuccess
 * 	        event is dispatched. Then, if there is a
 * 	        redirectURL, it performs a redirection.
 * 	        Finally, the SimpleInputModal is disposed and
 * 	        all event listeners are detached.
 * 	   3.2. If the response is an error, a formError event is
 * 	   		  dispatched. Then the given errorMessage is shown inside
 * 	        the modal and nothing happens until the user fixes the
 * 	        error and submits it again or the modal is closed.
 *
 * Optional fields
 * - Checkbox: SimpleInputModal supports an optional checkbox field that
 *   can be added to the form.
 * - Id field: if you need to edit an element, instead of creating new ones,
 *   a hidden ID field can be used. fieldValues can also be used to specify an
 *   initial value instead of an empty field.
 * - Redirect URL: by default, the form redirects to an existing URL when it's
 *   submitted. This URL is obtained from the request response as "redirectURL."
 *   If no URL is obtained, the SimpleInputModal is disposed.
 */

function openSimpleInputModal({
	alert,
	checkboxFieldLabel = '',
	checkboxFieldName = '',
	checkboxFieldValue = false,
	dialogTitle,
	formSubmitURL,
	idFieldName = '',
	idFieldValue = '',
	mainFieldLabel,
	mainFieldName,
	mainFieldPlaceholder = '',
	mainFieldValue = '',
	namespace,
	spritemap
}) {
	const fixFormData = Liferay.Browser.isIe();

	let simpleInputModal = null;

	/**
	 * Callback executed when the SimpleInputModal component
	 * is closed or the form cancel button is pressed.
	 */

	function handleSimpleInputModalDisposal() {
		if (simpleInputModal) {
			if (!simpleInputModal.isDisposed()) {
				simpleInputModal.dispose();
			}

			simpleInputModal = null;
		}
	}

	/**
	 * Callback executed when the SimpleInputModal form receives a successful
	 * response from the server.
	 * @param {{redirectURL: string}} serverResponseContent
	 */

	function handleSimpleInputModalSubmission(serverResponseContent) {
		if (serverResponseContent.redirectURL) {
			navigate(serverResponseContent.redirectURL, {
				beforeScreenFlip: handleSimpleInputModalDisposal.bind(this)
			});
		} else {
			handleSimpleInputModalDisposal();
		}
	}

	simpleInputModal = new SimpleInputModal({
		alert,
		checkboxFieldLabel,
		checkboxFieldName,
		checkboxFieldValue,
		dialogTitle,
		events: {
			cancelButtonClicked: handleSimpleInputModalDisposal,
			dialogHidden: handleSimpleInputModalDisposal,
			formSuccess: handleSimpleInputModalSubmission
		},
		fixFormData,
		formSubmitURL,
		idFieldName,
		idFieldValue,
		mainFieldLabel,
		mainFieldName,
		mainFieldPlaceholder,
		mainFieldValue,
		namespace,
		spritemap
	});

	return simpleInputModal;
}

export {openSimpleInputModal};
export default openSimpleInputModal;
