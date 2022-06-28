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

ckEditor.on('dialogShow', (event) => {
	const A = AUI();

	const MODIFIED = 'modified';

	const SELECTOR_HBOX_FIRST = '.cke_dialog_ui_hbox_first';

	const dialog = event.data.definition.dialog;

	if (dialog.getName() === 'image') {
		const lockButton = document.querySelector('.cke_btn_locked');

		if (lockButton) {
			const imageProperties = lockButton.closest(SELECTOR_HBOX_FIRST);

			if (imageProperties) {
				imageProperties.style.display = 'none';
			}
		}

		const imagePreviewBox = document.querySelector('.ImagePreviewBox');

		if (imagePreviewBox) {
			imagePreviewBox.style.width = 410;
		}
	}
	else if (dialog.getName() === 'cellProperties') {
		// eslint-disable-next-line @liferay/aui/no-one
		const containerNode = A.one('#' + dialog.getElement('cellType').$.id);

		if (!containerNode.getData(MODIFIED)) {
			containerNode.one(SELECTOR_HBOX_FIRST).hide();

			containerNode.one('.cke_dialog_ui_hbox_child').hide();

			const cellTypeWrapper = containerNode.one(
				'.cke_dialog_ui_hbox_last'
			);

			cellTypeWrapper.replaceClass(
				'cke_dialog_ui_hbox_last',
				'cke_dialog_ui_hbox_first'
			);

			cellTypeWrapper.setStyle('width', '100%');

			cellTypeWrapper.all('tr').each((item, index) => {
				if (index > 0) {
					item.hide();
				}
			});

			containerNode.setData(MODIFIED, true);
		}
	}
});
