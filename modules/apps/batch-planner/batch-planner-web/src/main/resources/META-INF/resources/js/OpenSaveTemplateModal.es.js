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

import {render} from '@liferay/frontend-js-react-web';
import React from 'react';
import {unmountComponentAtNode} from 'react-dom';

import SaveTemplateModal from './SaveTemplateModal.es';

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

function openSaveTemplateModalImplementation({
	alert,
	dialogTitle,
	formDataQuerySelector,
	formSubmitURL,
	modalFieldLabel,
	modalFieldName,
	modalFieldPlaceholder,
	namespace,
	onFormSuccess,
}) {
	dispose();

	render(
		<SaveTemplateModal
			alert={alert}
			closeModal={dispose}
			dialogTitle={dialogTitle}
			formDataQuerySelector={formDataQuerySelector}
			formSubmitURL={formSubmitURL}
			initialVisible="true"
			modalFieldLabel={modalFieldLabel}
			modalFieldName={modalFieldName}
			modalFieldPlaceholder={modalFieldPlaceholder}
			namespace={namespace}
			onFormSuccess={onFormSuccess}
		/>,
		DEFAULT_RENDER_DATA,
		getDefaultModalContainer()
	);
}

export default openSaveTemplateModalImplementation;
