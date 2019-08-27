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
 * @param {Object} alert
 * @param {Object} options
 * @param {string} checkboxFieldLabel
 * @param {string} checkboxFieldName
 * @param {boolean} checkboxFieldValue
 * @param {!string} dialogTitle
 * @param {!string} formSubmitURL
 * @param {string} idFieldName
 * @param {string} idFieldValue
 * @param {!string} mainFieldLabel
 * @param {!string} mainFieldName
 * @param {string} mainFieldPlaceholder
 * @param {string} mainFieldValue
 * @param {!string} namespace
 * @param {!string} spritemap
 *
 * @return {SimpleInputModal} SimpleInputModal component instance
 * @review
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
 * 	        Finally the SimpleInputModal is disposed and
 * 	        every event listeners are detached.
 * 	   3.2. If the response is an error, a formError event
 * 	   		is dispatched. Then the given errorMessage is shown inside
 * 	        the modal and nothing happens until the user
 * 	        fixes submits it again or the modal is closed.
 *
 * Optional fields
 * - Checkbox: SimpleInputModal supports an optional checkbox field that
 *   can be added to the form.
 * - Id field: in case of editing elements instead of creating new ones,
 *   and hidden id field can be used. There are also fieldValues that can
 *   be used for having an initial value instead of an empty field.
 * - Redirect url: the common behaviour is redirecting to an existing url
 *   when the form is submitted. This URL is obtained from the request response
 *   as "redirectURL" and, if no URL is obtained, the SimpleInputModal is simply
 *   disposed.
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
	 * @review
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
	 * response from server.
	 * @param {{redirectURL: string}} serverResponseContent
	 * @review
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
