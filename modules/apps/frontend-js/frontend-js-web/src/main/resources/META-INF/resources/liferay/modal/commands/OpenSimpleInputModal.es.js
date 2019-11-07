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

import {render} from 'frontend-js-react-web';
import React from 'react';
import {unmountComponentAtNode} from 'react-dom';

import SimpleInputModal from '../components/SimpleInputModal.es';

const DEFAULT_MODAL_CONTAINER_ID = 'modalContainer';

const DEFAULT_RENDER_DATA = {
	portletId: 'UNKNOWN_PORTLET_ID'
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

function SimpleInputModalComponent({
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
	namespace,
	onFormSuccess,
	placeholder
}) {
	return (
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
			namespace={namespace}
			onFormSuccess={onFormSuccess}
			placeholder={placeholder}
		/>
	);
}

function openSimpleInputModalImplementation(data) {
	dispose();

	const renderData = DEFAULT_RENDER_DATA;

	render(
		SimpleInputModalComponent,
		{...data, ...renderData},
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
