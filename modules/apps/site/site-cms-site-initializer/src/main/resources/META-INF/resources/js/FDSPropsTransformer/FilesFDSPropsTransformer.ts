/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import createFolderAction from './actions/createFolderAction';

export default function FilesFDSPropsTransformer({...props}) {
	return {
		...props,
		onCreationActionClick: ({action}: {action: string}) => {
			if (action === 'createFolder') {
				createFolderAction();
			}
		},
	};
}
