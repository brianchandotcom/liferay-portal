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

import {ClayIconSpriteContext} from '@clayui/icon';
import React from 'react';
import ReactDOM from 'react-dom';

import DisplayPageModal from './DisplayPageModal.es';

let container;

/**
 * Opens a modal that will let the user choose the title
 * and the mapping types of a displaoy page.
 *
 * @param {string} param.displayPageName
 * @param {string} param.formSubmitURL
 * @param {array} param.mappingTypes
 * @param {string} param.namespace
 * @param {string} param.spritemap
 * @param {string} param.title
 */
export function openDisplayPageModal({
	displayPageName,
	formSubmitURL,
	mappingTypes,
	namespace,
	spritemap,
	title
}) {
	if (container) {
		cleanUp();
	}

	container = document.createElement('div');

	document.body.appendChild(container);

	// eslint-disable-next-line liferay-portal/no-react-dom-render
	ReactDOM.render(
		<ClayIconSpriteContext.Provider value={spritemap}>
			<DisplayPageModal
				displayPageName={displayPageName}
				formSubmitURL={formSubmitURL}
				mappingTypes={mappingTypes}
				namespace={namespace}
				onClose={cleanUp}
				title={title}
			/>
		</ClayIconSpriteContext.Provider>,
		container
	);

	Liferay.once('destroyPortlet', cleanUp);
}

function cleanUp() {
	if (container) {
		ReactDOM.unmountComponentAtNode(container);
		document.body.removeChild(container);

		container = null;
	}
}
