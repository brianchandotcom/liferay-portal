/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import CreationModalContent from '../../components/modal/CreationModalContent';

type AssetData = {
	action: 'createAsset';
	redirect: string;
	title: string;
};

export default function createAssetAction(data: AssetData) {
	openModal({
		center: true,
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			CreationModalContent({
				...data,
				closeModal,
			}),
		size: 'sm',
	});
}
