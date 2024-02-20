/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';
import {unmountComponentAtNode} from 'react-dom';

import CreationModal from './CreationModal';

const DEFAULT_MODAL_CONTAINER_ID = 'modalContainer';

interface Props {
	buttonLabel: string;
	descriptionInputValue: string;
	formSubmitURL: string;
	heading: string;
	nameInputValue: string;
	portletNamespace: string;
}

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

export default function openCreationModal(props: Props) {
	dispose();

	render(
		CreationModal,
		{...props, onCloseModal: dispose},
		getDefaultModalContainer()
	);
}
