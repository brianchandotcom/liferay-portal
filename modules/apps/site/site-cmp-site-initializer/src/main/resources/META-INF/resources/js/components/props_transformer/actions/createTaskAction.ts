/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate} from 'frontend-js-web';

import {openCMPModal} from '../../../utils/openCMPModal';
import SelectProjectModalContent from '../../modal/SelectProjectModalContent';

export type Data = {
	addProjectURL?: string;
	addTaskURL?: string;
	projectObjectDefinitionId: string;
	redirect?: string;
};

export default function createTaskAction(data: Data) {
	if (!data.addProjectURL && !data.addTaskURL && data.redirect) {
		const url = new URL(data.redirect);

		navigate(url.pathname + url.search);

		return;
	}

	if (!data.addProjectURL || !data.addTaskURL) {
		throw new Error(
			'Both addProjectURL and addTaskURL are required to open the modal'
		);
	}

	openCMPModal({
		center: true,
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			SelectProjectModalContent({
				...data,
				addProjectURL: data.addProjectURL!,
				addTaskURL: data.addTaskURL!,
				closeModal,
				projectObjectDefinitionId: data.projectObjectDefinitionId,
			}),
		size: 'md',
	});
}
