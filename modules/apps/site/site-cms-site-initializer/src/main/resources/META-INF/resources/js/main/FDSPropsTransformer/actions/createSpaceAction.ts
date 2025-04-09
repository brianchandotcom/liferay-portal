/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import CreateSpaceModalContent from '../../components/modal/CreateSpaceModalContent';

export type SpaceData = {
	action: 'createSpace';
	redirect: string;
	title: string;
};

export default function createSpaceAction(data: SpaceData) {
	openModal({
		center: true,
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			CreateSpaceModalContent({
				...data,
				closeModal,
			}),
		size: 'sm',
	});
}
