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

ckEditor.on('dialogShow', event => {
	var A = AUI();

	var MODIFIED = 'modified';

	var SELECTOR_HBOX_FIRST = '.cke_dialog_ui_hbox_first';

	var dialog = event.data.definition.dialog;

	if (dialog.getName() == 'image') {
		var lockButton = A.one('.cke_btn_locked');

		if (lockButton) {
			var imageProperties = lockButton.ancestor(SELECTOR_HBOX_FIRST);

			if (imageProperties) {
				imageProperties.hide();
			}
		}

		var imagePreviewBox = A.one('.ImagePreviewBox');

		if (imagePreviewBox) {
			imagePreviewBox.setStyle('width', 410);
		}
	} else if (dialog.getName() == 'cellProperties') {
		var containerNode = A.one('#' + dialog.getElement('cellType').$.id);

		if (!containerNode.getData(MODIFIED)) {
			containerNode.one(SELECTOR_HBOX_FIRST).hide();

			containerNode.one('.cke_dialog_ui_hbox_child').hide();

			var cellTypeWrapper = containerNode.one('.cke_dialog_ui_hbox_last');

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
