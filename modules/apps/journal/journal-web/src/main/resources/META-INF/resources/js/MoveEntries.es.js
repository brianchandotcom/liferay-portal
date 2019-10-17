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

import {ItemSelectorDialog, PortletBase} from 'frontend-js-web';
import {Config} from 'metal-state';

/**
 * @class MoveEntries
 * It adds a listener to a #selectFolderButton DOM element
 * and allows selecting a directory with itemSelector
 * @review
 */
class MoveEntries extends PortletBase {
	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		const selectFolderButton = document.getElementById(
			this.ns('selectFolderButton')
		);

		if (selectFolderButton) {
			this._handleSelectFolderButtonClick = this._handleSelectFolderButtonClick.bind(
				this
			);

			selectFolderButton.addEventListener(
				'click',
				this._handleSelectFolderButtonClick
			);
		}
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		const selectFolderButton = document.getElementById(
			this.ns('selectFolderButton')
		);

		if (selectFolderButton) {
			selectFolderButton.removeEventListener(
				'click',
				this._handleSelectFolderButtonClick
			);
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleSelectFolderButtonClick() {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('done'),
			eventName: this.ns('selectFolder'),
			title: Liferay.Language.get('select-folder'),
			url: this.selectFolderURL
		});

		itemSelectorDialog.on('selectedItemChange', event => {
			const selectedItem = event.selectedItem;

			if (selectedItem) {
				var folderData = {
					idString: 'newFolderId',
					idValue: selectedItem.folderId,
					nameString: 'folderName',
					nameValue: selectedItem.folderName
				};

				Liferay.Util.selectFolder(
					folderData,
					this.namespace || this.portletNamespace
				);
			}
		});

		itemSelectorDialog.open();
	}
}

/**
 * @memberof MoveEntries
 * @review
 * @static
 */
MoveEntries.STATE = {
	/**
	 * @default undefined
	 * @memberof MoveEntries
	 * @required
	 * @review
	 * @type {string}
	 */
	selectFolderURL: Config.string().required()
};

export default MoveEntries;
