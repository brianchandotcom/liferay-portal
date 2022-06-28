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

import {openSelectionModal} from 'frontend-js-web';

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

					Liferay.Util.selectFolder(folderData, namespace);
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
