/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal, selectFolder} from 'frontend-js-web';

export default function ({inputName, namespace, selectFolderURL}) {
	const handleSelectFolderButtonClick = () =>
		openSelectionModal({
			height: '70vh',
			iframeBodyCssClass: '',
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const folderData = {
						idString: inputName,
						idValue: selectedItem.folderId,
						nameString: 'folderName',
						nameValue: selectedItem.folderName,
					};

					selectFolder(folderData, namespace);
				}
			},
			selectEventName: `${namespace}selectFolder`,
			size: 'md',
			title: Liferay.Language.get('select-folder'),
			url: selectFolderURL,
		});

	const selectFolderButton = document.getElementById(
		`${namespace}selectFolderButton`
	);

	if (selectFolderButton) {
		selectFolderButton.addEventListener(
			'click',
			handleSelectFolderButtonClick
		);

		return {
			dispose() {
				selectFolderButton.removeEventListener(
					'click',
					handleSelectFolderButtonClick
				);
			},
		};
	}
}
