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

import {render} from '@liferay/frontend-js-react-web';
import {ClayAlert} from 'clay-alert';
import {EventHandler, PortletBase, delegate} from 'frontend-js-web';
import {Config} from 'metal-state';
import ReactDOM from 'react-dom';

import ItemSelectorPreview from '../../item_selector_preview/js/ItemSelectorPreview.es';

const STR_DRAG_LEAVE = 'dragleave';
const STR_DRAG_OVER = 'dragover';
const STR_DROP = 'drop';
const statusCode = Liferay.STATUS_CODE;

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
		AUI().use('liferay-item-selector-uploader', (A) => {
			this._itemSelectorUploader = new A.LiferayItemSelectorUploader({
				rootNode: this.rootNode,
			});

			this._bindEvents();
		});

		this.attachItemSelectorPreviewComponent();
	}

	attachItemSelectorPreviewComponent() {
		const itemsNodes = Array.from(this.all('.item-preview'));

		const items = itemsNodes.map((node) => node.dataset);

		const clicableItems = Array.from(this.all('.icon-view'));

		if (items.length === clicableItems.length) {
			clicableItems.forEach((clicableItem, index) => {
				clicableItem.addEventListener('click', (e) => {
					e.preventDefault();
					e.stopPropagation();

					this.openItemSelectorPreview(items, index);
				});
			});
		}

		this._itemSelectorPreviewContainer = this.one(
			'.item-selector-preview-container'
		);
	}

	openItemSelectorPreview(items, index) {
		const container = this._itemSelectorPreviewContainer;

		const data = {
			container,
			currentIndex: index,
			editImageURL: this.editImageURL,
			handleSelectedItem: this._onItemSelected.bind(this),
			headerTitle: this.closeCaption,
			itemReturnType: this.uploadItemReturnType,
			items,
		};

		render(ItemSelectorPreview, data, container);
	}

	closeItemSelectorPreview() {
		ReactDOM.unmountComponentAtNode(this._itemSelectorPreviewContainer);
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();

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
			delegate(this.rootNode, 'click', '.item-preview', (event) =>
				this._onItemSelected(event.delegateTarget.dataset)
			)
		);

		const inputFileNode = this.one('input[type="file"]');

		if (inputFileNode) {
			this._eventHandler.add(
				inputFileNode.addEventListener('change', (event) => {
					this._validateFile(event.target.files[0]);
				})
			);
		}

		if (this.uploadItemURL) {
			const itemSelectorUploader = this._itemSelectorUploader;
			const rootNode = this.rootNode;

			this._eventHandler.add(
				itemSelectorUploader.after('itemUploadCancel', () => {
					this.closeItemSelectorPreview();
				}),
				itemSelectorUploader.after('itemUploadComplete', (itemData) => {
					const itemFile = itemData.file;
					const itemFileUrl = itemFile.url;
					let itemFileValue = itemFile.resolvedValue;

					if (!itemFileValue) {
						const imageValue = {
							fileEntryId: itemFile.fileEntryId,
							groupId: itemFile.groupId,
							title: itemFile.title,
							type: itemFile.type,
							url: itemFileUrl,
							uuid: itemFile.uuid,
						};

						itemFileValue = JSON.stringify(imageValue);
					}

					Liferay.componentReady('ItemSelectorPreview').then(() => {
						Liferay.fire('updateCurrentItem', {
							...itemFile,
							value: itemFileValue,
						});
					});
				}),
				itemSelectorUploader.after('itemUploadError', (event) => {
					this._onItemUploadError(event);
				}),
				rootNode.addEventListener(STR_DRAG_OVER, (event) =>
					this._ddEventHandler(event)
				),
				rootNode.addEventListener(STR_DRAG_LEAVE, (event) =>
					this._ddEventHandler(event)
				),
				rootNode.addEventListener(STR_DROP, (event) =>
					this._ddEventHandler(event)
				)
			);
		}
	}

	/**
	 * @private
	 */
	_closeAlert() {
		if (this._alert && !this._alert.isDisposed()) {
			this._alert.emit('hide');
			this._alert = null;
		}

		clearTimeout(this._hideTimeout);
	}

	/**
	 * Converts a String to a Number.
	 *
	 * @param  {Number | String}
	 * @private
	 * @return {Number}
	 */
	_convertMaxFileSize(maxFileSize) {
		return parseInt(maxFileSize, 10);
	}

	/**
	 * Handles drag and drop events.
	 *
	 * @param  {!Event}
	 * @private
	 */
	_ddEventHandler(event) {
		const dataTransfer = event.dataTransfer;

		if (dataTransfer && dataTransfer.types) {
			const dataTransferTypes = dataTransfer.types || [];

			if (
				dataTransferTypes.indexOf('Files') > -1 &&
				dataTransferTypes.indexOf('text/html') === -1
			) {
				event.preventDefault();

				const type = event.type;

				const eventDrop = type === STR_DROP;

				const rootNode = this.rootNode;

				if (type === STR_DRAG_OVER) {
					rootNode.classList.add('drop-active');
				}
				else if (type === STR_DRAG_LEAVE || eventDrop) {
					rootNode.classList.remove('drop-active');

					if (eventDrop) {
						this._validateFile(dataTransfer.files[0]);
					}
				}
			}
		}
	}

	/**
	 * Returns the message to show when the upload fails.
	 *
	 * @param  {Object} error
	 * @private
	 * @return {String} error message
	 */
	_getUploadErrorMessage(error) {
		let message = Liferay.Language.get(
			'an-unexpected-error-occurred-while-uploading-your-file'
		);

		if (error && error.errorType) {
			const errorType = error.errorType;

			switch (errorType) {
				case statusCode.SC_FILE_ANTIVIRUS_EXCEPTION:
					if (error.message) {
						message = error.message;
					}

					break;
				case statusCode.SC_FILE_EXTENSION_EXCEPTION:
					if (error.message) {
						message = Liferay.Util.sub(
							Liferay.Language.get(
								'please-enter-a-file-with-a-valid-extension-x'
							),
							[error.message]
						);
					}
					else {
						message = Liferay.Language.get(
							'please-enter-a-file-with-a-valid-file-type'
						);
					}

					break;
				case statusCode.SC_FILE_NAME_EXCEPTION:
					message = Liferay.Language.get(
						'please-enter-a-file-with-a-valid-file-name'
					);

					break;
				case statusCode.SC_FILE_SIZE_EXCEPTION:
				case statusCode.SC_UPLOAD_REQUEST_CONTENT_LENGTH_EXCEPTION:
					message = Liferay.Util.sub(
						Liferay.Language.get(
							'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
						),
						[Liferay.Util.formatStorage(this.maxFileSize)]
					);

					break;
				case statusCode.SC_UPLOAD_REQUEST_SIZE_EXCEPTION: {
					const maxUploadRequestSize =
						Liferay.PropsValues
							.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE;

					message = Liferay.Util.sub(
						Liferay.Language.get(
							'request-is-larger-than-x-and-could-not-be-processed'
						),
						[Liferay.Util.formatStorage(maxUploadRequestSize)]
					);

					break;
				}
				default: {
					message = Liferay.Language.get(
						'an-unexpected-error-occurred-while-uploading-your-file'
					);
				}
			}
		}

		return message;
	}

	/**
	 * Generates the JSON that the item viewer
	 * need to show in the info panel.
	 *
	 * @param {File}
	 * @return {JSON}
	 */
	_getUploadFileMetadata(file) {
		return {
			groups: [
				{
					data: [
						{
							key: Liferay.Language.get('format'),
							value: file.type,
						},
						{
							key: Liferay.Language.get('size'),
							value: Liferay.Util.formatStorage(file.size),
						},
						{
							key: Liferay.Language.get('name'),
							value: file.name,
						},
					],
					title: Liferay.Language.get('file-info'),
				},
			],
		};
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
				returnType: item.returntype,
				value: item.value,
			},
		});
	}

	/**
	 *
	 * Handles the error during the item upload.
	 *
	 * @param {!Event}
	 * @private
	 */
	_onItemUploadError(event) {
		this.closeItemSelectorPreview();

		this._showError(this._getUploadErrorMessage(event.error));
	}

	/**
	 * Reads the file.
	 *
	 * @param  {File}
	 * @private
	 */
	_previewFile(file) {
		if (window.FileReader) {
			const reader = new FileReader();

			reader.addEventListener('loadend', (event) => {
				this._showFile(file, event.target.result);
			});

			reader.readAsDataURL(file);
		}
	}

	/**
	 * Shows an error message
	 *
	 * @param {String} message
	 * @private
	 */
	_showError(message) {
		this._alert = new ClayAlert(
			{
				closeable: true,
				destroyOnHide: true,
				message,
				spritemap:
					Liferay.ThemeDisplay.getPathThemeImages() +
					'/clay/icons.svg',
				style: 'danger',
				title: '',
				visible: true,
			},
			this.one('.message-container')
		);

		this._hideTimeout = setTimeout(
			() => this._closeAlert(),
			this.hideAlertDelay
		);
	}

	/**
	 * Shows the selected item in the Item Viewer and
	 * uploads to the server.
	 *
	 * @param  {File} file
	 * @param  {String} Preview of the item in Base64 code
	 */
	_showFile(file, preview) {
		if (!file.type.match(/image.*/)) {
			preview =
				Liferay.ThemeDisplay.getPathThemeImages() +
				'/file_system/large/default.png';
		}

		const item = {
			base64: preview,
			metadata: JSON.stringify(this._getUploadFileMetadata(file)),
			returntype: this.uploadItemReturnType,
			title: file.name,
			value: preview,
		};

		this.openItemSelectorPreview([item], 0);

		this._itemSelectorUploader.startUpload(file, this.uploadItemURL);
	}

	/**
	 * Validates file's extension and size.
	 *
	 * @param {File} file The selected file
	 * @private
	 */
	_validateFile(file) {
		let errorMessage = '';

		const fileExtension = file.name.split('.').pop().toLowerCase();

		const validExtensions = this.validExtensions;

		if (
			validExtensions === '*' ||
			validExtensions.indexOf(fileExtension) != -1
		) {
			const maxFileSize = this.maxFileSize;

			if (maxFileSize === 0 || file.size <= maxFileSize) {
				this._previewFile(file);
			}
			else {
				errorMessage = Liferay.Util.sub(
					Liferay.Language.get(
						'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
					),
					[Liferay.Util.formatStorage(maxFileSize)]
				);
			}
		}
		else {
			errorMessage = Liferay.Util.sub(
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
	 * Endpoint to send the image edited in the Image Editor
	 *
	 * @instance
	 * @memberof ItemSelectorRepositoryEntryBrowser
	 * @type {String}
	 */
	editImageURL: Config.string(),

	/**
	 * Time to hide the alert messages.
	 *
	 * @type {Number} milliseconds
	 */
	hideAlertDelay: Config.number().value(5000).internal(),

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
	validExtensions: Config.string().value('*'),
};

export default ItemSelectorRepositoryEntryBrowser;
