/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal, selectFolder} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps,
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			const {selectFolderURL} = additionalProps;

			openSelectionModal({
				onSelect: (event) => {
					if (event) {
						const folderData = {
							idString: 'rootFolderId',
							idValue: event.entityid,
							nameString: 'rootFolderName',
							nameValue: event.entityname,
						};

						selectFolder(folderData, portletNamespace);
					}
				},
				selectEventName: `${portletNamespace}selectFolder`,
				title: Liferay.Language.get('select-folder'),
				url: selectFolderURL,
			});
		},
	};
}
