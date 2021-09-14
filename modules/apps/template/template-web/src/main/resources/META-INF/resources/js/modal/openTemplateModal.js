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
import ReactDOM from 'react-dom';

import TemplateModal from './TemplateModal';

const DEFAULT_MODAL_CONTAINER_ID = 'templateModal';

export default function openTemplateModal({
	addTemplateEntryURL,
	itemTypes,
	namespace,
}) {
	dispose();

	const container = document.createElement('div');
	container.id = DEFAULT_MODAL_CONTAINER_ID;
	document.body.appendChild(container);

	render(
		<TemplateModal
			addTemplateEntryURL={addTemplateEntryURL}
			itemTypes={itemTypes}
			namespace={namespace}
			onModalClose={dispose}
		/>,
		{},
		container
	);
}

function dispose() {
	const container = document.getElementById(DEFAULT_MODAL_CONTAINER_ID);

	if (container) {
		ReactDOM.unmountComponentAtNode(container);

		document.body.removeChild(container);
	}
}
