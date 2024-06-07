/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getSelectedOptionValues} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...props}) {
	return {
		...props,
		onClick() {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				const currentFolderColumns = document.getElementById(
					`${portletNamespace}currentFolderColumns`
				);
				const folderColumns = document.getElementById(
					`${portletNamespace}folderColumns`
				);

				if (currentFolderColumns && folderColumns) {
					folderColumns.value =
						getSelectedOptionValues(currentFolderColumns);
				}

				const currentEntryColumns = document.getElementById(
					`${portletNamespace}currentEntryColumns`
				);
				const entryColumns = document.getElementById(
					`${portletNamespace}entryColumns`
				);

				if (currentEntryColumns && entryColumns) {
					entryColumns.value =
						getSelectedOptionValues(currentEntryColumns);
				}

				submitForm(form);
			}
		},
	};
}
