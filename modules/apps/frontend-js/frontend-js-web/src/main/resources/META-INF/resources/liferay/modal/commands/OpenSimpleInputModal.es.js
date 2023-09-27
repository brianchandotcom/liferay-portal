/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';
import React from 'react';
import {unmountComponentAtNode} from 'react-dom';

import SimpleInputModal from '../components/SimpleInputModal.es';

const DEFAULT_MODAL_CONTAINER_ID = 'modalContainer';

const DEFAULT_RENDER_DATA = {
	portletId: 'UNKNOWN_PORTLET_ID',
};

function getDefaultModalContainer() {
	let container = document.getElementById(DEFAULT_MODAL_CONTAINER_ID);

	if (!container) {
		container = document.createElement('div');
		container.id = DEFAULT_MODAL_CONTAINER_ID;
		document.body.appendChild(container);
	}

	return container;
}

function dispose() {
	unmountComponentAtNode(getDefaultModalContainer());
}

function openSimpleInputModalImplementation({
	alert,
	checkboxFieldLabel,
	checkboxFieldName,
	checkboxFieldValue,
	dialogTitle,
	formSubmitURL,
	idFieldName,
	idFieldValue,
	mainFieldLabel,
	mainFieldName,
	mainFieldValue,
	method,
	namespace,
	onFormSuccess,
	placeholder,
}) {
	dispose();

	render(
		<SimpleInputModal
			alert={alert}
			checkboxFieldLabel={checkboxFieldLabel}
			checkboxFieldName={checkboxFieldName}
			checkboxFieldValue={checkboxFieldValue}
			closeModal={dispose}
			dialogTitle={dialogTitle}
			formSubmitURL={formSubmitURL}
			idFieldName={idFieldName}
			idFieldValue={idFieldValue}
			initialVisible="true"
			mainFieldLabel={mainFieldLabel}
			mainFieldName={mainFieldName}
			mainFieldValue={mainFieldValue}
			method={method}
			namespace={namespace}
			onFormSuccess={onFormSuccess}
			placeholder={placeholder}
		/>,
		DEFAULT_RENDER_DATA,
		getDefaultModalContainer()
	);
}

let didEmitDeprecationWarning = false;

/**
 * Function that implements the SimpleInputModal pattern, which allows
 * manipulating small amounts of data with a form shown inside a modal.
 *
 * @deprecated As of Athanasius (7.3.x), replaced by the default export
 */
export function openSimpleInputModal(data) {
	if (process.env.NODE_ENV === 'development' && !didEmitDeprecationWarning) {
		console.warn(
			'The named "openSimpleInputModal" export is deprecated: use the default export instead'
		);

		didEmitDeprecationWarning = true;
	}

	return openSimpleInputModalImplementation.call(null, data);
}

export default openSimpleInputModalImplementation;
