/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {render} from '@liferay/frontend-js-react-web';
import React from 'react';
import {unmountComponentAtNode} from 'react-dom';

import AddBlueprintModal from './AddBlueprintModal';

/**
 * A slightly modified version of frontend-js-web module's OpenSimpleInputModal
 * file.
 */

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

function openAddBlueprintModal({
	alert,
	contextPath,
	defaultLocale,
	dialogTitle,
	formSubmitURL,
	idFieldName,
	idFieldValue,
	namespace,
	onFormSuccess,
	searchableAssetTypesString,
	type,
}) {
	dispose();

	render(
		<AddBlueprintModal
			alert={alert}
			closeModal={dispose}
			contextPath={contextPath}
			defaultLocale={defaultLocale}
			dialogTitle={dialogTitle}
			formSubmitURL={formSubmitURL}
			idFieldName={idFieldName}
			idFieldValue={idFieldValue}
			initialVisible
			namespace={namespace}
			onFormSuccess={onFormSuccess}
			searchableAssetTypesString={searchableAssetTypesString}
			type={type}
		/>,
		DEFAULT_RENDER_DATA,
		getDefaultModalContainer()
	);
}

export default openAddBlueprintModal;
