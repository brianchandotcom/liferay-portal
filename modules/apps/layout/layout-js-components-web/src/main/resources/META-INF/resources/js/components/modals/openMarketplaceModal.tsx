/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';
import React from 'react';

import MarketplaceModal from './MarketplaceModal';

const DEFAULT_MODAL_CONTAINER_ID = 'modalContainer';

interface Props {
	component: string;
	image: string;
	location: string;
	onCloseModal: () => void;
}
let root: any;

function getDefaultModalContainer() {
	let container = document.getElementById(DEFAULT_MODAL_CONTAINER_ID);

	if (!container) {
		container = document.createElement('div');
		container.id = DEFAULT_MODAL_CONTAINER_ID;
		document.body.appendChild(container);
	}

	return container;
}

export default function openMarketplaceModal(props: Props) {
	const cleanUp = () => {
		if (root) {
			root.unmount?.();
			root = null;
		}

		const container = document.getElementById(DEFAULT_MODAL_CONTAINER_ID);
		if (container) {
			container.remove();
		}
	};

	root = render(
		<MarketplaceModal {...props} onCloseModal={cleanUp} />,
		props as any,
		getDefaultModalContainer()
	);
}
