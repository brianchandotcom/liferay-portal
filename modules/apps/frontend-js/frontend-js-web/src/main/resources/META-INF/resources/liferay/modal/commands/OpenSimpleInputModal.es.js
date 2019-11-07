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

const getDefaultModalContainer = () => {
	let container = document.getElementById(DEFAULT_MODAL_CONTAINER_ID);

	if (!container) {
		container = document.createElement('div');
		container.id = DEFAULT_MODAL_CONTAINER_ID;
		document.body.appendChild(container);
	}

	return container;
};

function openSimpleInputModalImplementation(data) {
	const container = getDefaultModalContainer();

	unmountComponentAtNode(container);

	const closeModal = () => {
		unmountComponentAtNode(container);
	};

	const SimpleInputModalComponent = () => (
		<SimpleInputModal
			alert={data.alert}
			checkboxFieldLabel={data.checkboxFieldLabel}
			checkboxFieldName={data.checkboxFieldName}
			checkboxFieldValue={data.checkboxFieldValue}
			closeModal={closeModal}
			dialogTitle={data.dialogTitle}
			formSubmitURL={data.formSubmitURL}
			idFieldName={data.idFieldName}
			idFieldValue={data.idFieldValue}
			initialVisible="true"
			mainFieldLabel={data.mainFieldLabel}
			mainFieldName={data.mainFieldName}
			namespace={data.namespace}
			placeholder={data.placeholder}
		/>
	);

	const renderData = DEFAULT_RENDER_DATA;

	render(SimpleInputModalComponent, {...data, ...renderData}, container);
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
