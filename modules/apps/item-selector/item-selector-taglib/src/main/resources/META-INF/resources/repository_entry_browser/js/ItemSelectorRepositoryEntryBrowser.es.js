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

import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import {Config} from 'metal-state';
import {PortletBase} from 'frontend-js-web';

const sub = (str, obj) => str.replace(/\{([^}]+)\}/g, (_, m) => obj[m]);

/**
 * Handles the events in the Repository Entry Browser taglib.
 *
 * @abstract
 * @extends {PortletBase}
 */
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
		AUI().use(
			'liferay-item-selector-uploader',
			'liferay-item-viewer',
			A => {
				this._itemViewer = new A.LiferayItemViewer({
					btnCloseCaption: this.closeCaption,
					editItemURL: this.editItemURL,
					links: '', //TODO
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
			}
		);
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

	/**
	 * Bind events
	 *
	 * @private
	 */
	_bindEvents() {
		this._eventHandler.add(
			dom.delegate(
				this.rootNode,
				'click',
				'.item-preview',
				event => this._onItemSelected(event.delegateTarget)
			)
		);

		const inputFileNode = this.one('input[type="file"]');

		if (inputFileNode) {
			this._eventHandler.add(
				inputFileNode.addEventListener('change', event => {
					this._validateFile(event.target.files[0]);
				})
			);
		}
	}

	/**
	 * Converts a String to a Number.
	 *
	 * @param  {Number | String}
	 * @private
	 * @return {Number}
	 */
	_convertMaxFileSize(maxFileSize) {
		return parseInt(maxFileSize);
	}

	/**
	 * Send the selected item.
	 *
	 * @param {Object} item
	 * @private
	 */
	_onItemSelected(item) {
		this.emit('selectedItem', {
			data: {
				returnType: item.getAttribute('data-returntype'),
				value: item.getAttribute('data-value')
			}
		});
	}

	/**
	 * Renders the item viewer's components
	 *
	 * @private
	 */
	_renderUI() {
		this._itemViewer.render(rootNode);
		this._uploadItemViewer.render(this.rootNode);
	}

	/**
	 * Shows an error message
	 *
	 * TODO
	 * @param {String} message
	 * @private
	 */
	_showError(message) {
		console.log(message);
	}

	/**
	 * Validates file's extension and size.
	 *
	 * @param {File} file The selected file
	 * @private
	 */
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
	/**
	 * Text to show near the close icon in the Item Viewer
	 *
	 * @instance
	 * @memberof ItemSelectorRepositoryEntryBrowser
	 * @type {String}
	 */
	closeCaption: Config.string(),

	/**
	 * Url to edit the item.
	 *
	 * @instance
	 * @memberof ItemSelectorRepositoryEntryBrowser
	 * @type {String}
	 */
	editItemURL: Config.string(),

	/**
	 * Maximum allowed file size to drop in the item selector.
	 *
	 * @instance
	 * @memberof ItemSelectorRepositoryEntryBrowser
	 * @type {Number | String}
	 */
	maxFileSize: Config.oneOfType([Config.number(), Config.string()])
		.setter('_convertMaxFileSize')
		.value(Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE),

	/**
	 * The return type for the uploaded item.
	 *
	 * @instance
	 * @memberof ItemSelectorRepositoryEntryBrowser
	 * @type {String}
	 */
	uploadItemReturnType: Config.string(),

	/**
	 * URL to upload an item.
	 *
	 * @instance
	 * @memberof ItemSelectorRepositoryEntryBrowser
	 * @type {String}
	 */
	uploadItemURL: Config.string(),

	/**
	 * Valid extensions for files uploaded to the Item Selector.
	 *
	 * @instance
	 * @memberof ItemSelectorRepositoryEntryBrowser
	 * @type {String}
	 */
	validExtensions: Config.string().value('*')
};

export default ItemSelectorRepositoryEntryBrowser;
