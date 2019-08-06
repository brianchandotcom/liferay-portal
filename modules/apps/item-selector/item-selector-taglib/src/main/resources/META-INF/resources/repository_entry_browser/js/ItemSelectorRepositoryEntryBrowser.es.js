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

import {EventHandler} from 'metal-events';
import {Config} from 'metal-state';
import {PortletBase} from 'frontend-js-web';

const sub = (str, obj) => str.replace(/\{([^}]+)\}/g, (_, m) => obj[m]);

class ItemSelectorRepositoryEntryBrowser extends PortletBase {
	/**
	 * @inheritDoc
	 */
	created() {
		this._eventHandler = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		AUI().use('liferay-item-selector-uploader,liferay-item-viewer', A => {
			this._itemViewer = new A.LiferayItemViewer({
				btnCloseCaption: this.closeCaption,
				editItemURL: this.editItemURL,
				links: '', //this.all('.item-preview'), TODO
				uploadItemURL: this.uploadItemURL
			});

			this._uploadItemViewer = new A.LiferayItemViewer({
				btnCloseCaption: this.closeCaption,
				links: '',
				uploadItemURL: this.uploadItemURL
			});

			this._itemSelectorUploader = new A.LiferayItemSelectorUploader({
				rootNode: this.rootNode
			});

			this._bindEvents();
			this._renderUI();
		});
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();

		this._itemViewer.destroy();
		this._uploadItemViewer.destroy();
		this._itemSelectorUploader.destroy();

		this._eventHandler.removeAllListeners();
	}

	_bindEvents() {
		const inputFileNode = this.one('input[type="file"]');

		if (inputFileNode) {
			this._eventHandler.add(
				inputFileNode.addEventListener('change', event => {
					this._validateFile(event.target.files[0])
				})
			);
		}
	}

	_convertMaxFileSize(maxFileSize) {
		return parseInt(maxFileSize);
	}

	_renderUI() {
		const rootNode = this.rootNode;

		this._itemViewer.render(rootNode);
		this._uploadItemViewer.render(rootNode);
	}

	_showError(message) {
		console.log(message);
		//TODO
	}

	_validateFile(file) {
		let errorMessage = '';

		const fileExtension = file.name
			.split('.')
			.pop()
			.toLowerCase();

		const validExtensions = this.validExtensions;

		if (
			validExtensions === '*' ||
			validExtensions.indexOf(fileExtension) != -1
		) {
			const maxFileSize = this.maxFileSize;

			if (file.size <= maxFileSize) {
				console.log('file OK, go to preview');
				//this._previewFile(file); TODO
			} else {
				errorMessage = sub(
					Liferay.Language.get(
						'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
					),
					[maxFileSize] //TODO format file size
				);
			}
		} else {
			errorMessage = sub(
				Liferay.Language.get(
					'please-enter-a-file-with-a-valid-extension-x'
				),
				[validExtensions]
			);
		}

		if (errorMessage) {
			var inputTypeFile = this.one('input[type="file"]');

			if (inputTypeFile) {
				inputTypeFile.value = '';
			}

			this._showError(errorMessage);
		}
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
ItemSelectorRepositoryEntryBrowser.STATE = {
	closeCaption: Config.string(),

	editItemURL: Config.string(),

	maxFileSize: Config.oneOfType([Config.number(), Config.string()])
		.setter('_convertMaxFileSize')
		.value(Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE),

	uploadItemReturnType: Config.string(),

	uploadItemURL: Config.string(),

	validExtensions: Config.string().value('*')
};

export default ItemSelectorRepositoryEntryBrowser;
