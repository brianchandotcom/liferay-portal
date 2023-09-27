/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
