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
import {unmountComponentAtNode} from 'react-dom';
import React from 'react';

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

/**
 * Function that implements the SimpleInputModal pattern, which allows
 * manipulating small amounts of data with a form shown inside a modal.
 *
 * @review
 */

function openSimpleInputModal(
	data,
	containerId,
	renderData = DEFAULT_RENDER_DATA
) {
	const container =
		document.getElementById(containerId) || getDefaultModalContainer();

	unmountComponentAtNode(container);

	const closeModal = () => {
		unmountComponentAtNode(container);
	};

	const SimpleInputModalComponent = () => (
		<SimpleInputModal
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

	render(
		SimpleInputModalComponent,
		Object.assign(data, renderData),
		container
	);
}

export {openSimpleInputModal};
