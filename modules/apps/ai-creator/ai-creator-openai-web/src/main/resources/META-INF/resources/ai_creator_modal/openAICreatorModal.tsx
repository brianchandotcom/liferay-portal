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

import {AICreatorModal} from './AICreatorModal';

interface Props {
	namespace: string;
}

const DEFAULT_RENDER_DATA = {
	portletId: 'UNKNOWN_PORTLET_ID',
};

export default function openAICreatorModal({namespace}: Props) {
	if (!Liferay.FeatureFlags['LPS-179483']) {
		return;
	}

	const modalId = `${namespace}aiModal`;

	const disposeModal = () => {
		const existingModalContainer = document.getElementById(modalId);

		if (existingModalContainer) {
			unmountComponentAtNode(existingModalContainer);
			document.body.removeChild(existingModalContainer);
		}
	};

	const openModalHandler = Liferay.on(`${namespace}openAIModal`, () => {
		disposeModal();

		const modalContainer = document.createElement('div');
		modalContainer.id = modalId;
		modalContainer.classList.add('cadmin');

		document.body.appendChild(modalContainer);

		render(
			<AICreatorModal namespace={namespace} onClose={disposeModal} />,
			DEFAULT_RENDER_DATA,
			modalContainer
		);
	});

	return {
		dispose() {
			((openModalHandler as unknown) as {detach: () => void}).detach();
			disposeModal();
		},
	};
}
