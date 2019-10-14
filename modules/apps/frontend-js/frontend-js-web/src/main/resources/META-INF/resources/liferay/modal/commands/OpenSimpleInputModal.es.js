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

import SimpleInputModal from '../components/SimpleInputModal.es';
import React from 'react';
import {render} from 'frontend-js-react-web';

/**
 * Function that implements the SimpleInputModal pattern, which allows
 * manipulating small amounts of data with a form shown inside a modal.
 *
 * @review
 */

let container;

function openSimpleInputModal(data) {
	container = document.createElement('div');

	document.body.appendChild(container);

	render(renderComponent, data, container);
}

function renderComponent(data) {
	return (
		<SimpleInputModal
			dialogTitle={data.dialogTitle}
			formSubmitURL={data.formSubmitURL}
			idFieldName={data.idFieldName}
			idFieldValue={data.idFieldValue}
			initialVisible="true"
			mainFieldName={data.mainFieldName}
			mainFieldLabel={data.mainFieldLabel}
			namespace={data.namespace}
			placeholder={data.placeholder}
		/>
	);
}

export {openSimpleInputModal};
export default openSimpleInputModal;
