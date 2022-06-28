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

/**
 * @deprecated As of Athanasius (7.3.x), replaced by ItemSelectorRepositoryEntryBrowser.es.js
 * @module liferay-item-selector-repository-entry-browser
 */

AUI.add(
	'liferay-item-selector-repository-entry-browser',
	(A) => {
		const AArray = A.Array;
		const Lang = A.Lang;

		const CSS_DROP_ACTIVE = 'drop-active';

		const STATUS_CODE = Liferay.STATUS_CODE;

		const STR_DRAG_LEAVE = 'dragleave';

		const STR_DRAG_OVER = 'dragover';

		const STR_DROP = 'drop';

		const STR_ITEM_SELECTED = '_onItemSelected';

		const STR_ITEM_UPLOAD_ERROR = '_onItemUploadError';

		const STR_LINKS = 'links';

		const STR_SELECTED_ITEM = 'selectedItem';

		const STR_VISIBLE_CHANGE = 'visibleChange';

		const UPLOAD_ITEM_LINK_TPL =
			'<a data-returnType="{returnType}" data-value="{value}" href="{preview}" title="{title}"></a>';

		const ItemSelectorRepositoryEntryBrowser = A.Component.create({
			ATTRS: {
				closeCaption: {
					validator: Lang.isString,
					value: '',
				},
				editItemURL: {
					validator: Lang.isString,
					value: '',
				},
				maxFileSize: {
					setter: Lang.toInt,
					value:
						Liferay.PropsValues
							.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
				},
				uploadItemReturnType: {
					validator: Lang.isString,
					value: '',
				},
				uploadItemURL: {
					validator: Lang.isString,
					value: '',
				},
				validExtensions: {
					validator: Lang.isString,
					value: '*',
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'itemselectorrepositoryentrybrowser',

			prototype: {
				_afterVisibleChange(event) {
					const instance = this;

					if (!event.newVal) {
						instance.fire(STR_SELECTED_ITEM);
					}
				},

				_bindUI() {
					const instance = this;

					const itemViewer = instance._itemViewer;

					const uploadItemViewer = instance._uploadItemViewer;

					const itemSelectorUploader = instance._itemSelectorUploader;

					const rootNode = instance.rootNode;

					instance._eventHandles = [
						itemViewer
							.get(STR_LINKS)
							.on(
								'click',
								A.bind(STR_ITEM_SELECTED, instance, itemViewer)
							),
						itemViewer.after(
							'currentIndexChange',
							A.bind(STR_ITEM_SELECTED, instance, itemViewer)
						),
						itemViewer.after(
							STR_VISIBLE_CHANGE,
							instance._afterVisibleChange,
							instance
						),
					];

					const uploadItemURL = instance.get('uploadItemURL');

					if (uploadItemURL) {
						instance._eventHandles.push(
							uploadItemViewer.after(
								STR_VISIBLE_CHANGE,
								instance._afterVisibleChange,
								instance
							),
							itemSelectorUploader.after(
								'itemUploadCancel',
								instance._onItemUploadCancel,
								instance
							),
							itemSelectorUploader.after(
								'itemUploadComplete',
								instance._onItemUploadComplete,
								instance
							),
							itemSelectorUploader.after(
								'itemUploadError',
								A.bind(STR_ITEM_UPLOAD_ERROR, instance)
							),
							rootNode.on(
								STR_DRAG_OVER,
								instance._ddEventHandler,
								instance
							),
							rootNode.on(
								STR_DRAG_LEAVE,
								instance._ddEventHandler,
								instance
							),
							rootNode.on(
								STR_DROP,
								instance._ddEventHandler,
								instance
							)
						);
					}

					const inputFileNode = instance.one('input[type="file"]');

					if (inputFileNode) {
						instance._eventHandles.push(
							inputFileNode.on(
								'change',
								A.bind(instance._onInputFileChanged, instance)
							)
						);
					}
				},

				_ddEventHandler(event) {
					const instance = this;

					const dataTransfer = event._event.dataTransfer;

					if (dataTransfer && dataTransfer.types) {
						const dataTransferTypes = dataTransfer.types || [];

						if (
							AArray.indexOf(dataTransferTypes, 'Files') > -1 &&
							AArray.indexOf(dataTransferTypes, 'text/html') ===
								-1
						) {
							event.halt();

							const type = event.type;

							const eventDrop = type === STR_DROP;

							const rootNode = instance.rootNode;

							if (type === STR_DRAG_OVER) {
								rootNode.addClass(CSS_DROP_ACTIVE);
							}
							else if (type === STR_DRAG_LEAVE || eventDrop) {
								rootNode.removeClass(CSS_DROP_ACTIVE);

								if (eventDrop) {
									const file = dataTransfer.files[0];

									instance._validateFile(file);
								}
							}
						}
					}
				},

				_getUploadErrorMessage(error) {
					const instance = this;

					let message = Liferay.Language.get(
						'an-unexpected-error-occurred-while-uploading-your-file'
					);

					if (error && error.errorType) {
						const errorType = error.errorType;

						if (
							errorType ===
							STATUS_CODE.SC_FILE_ANTIVIRUS_EXCEPTION
						) {
							if (error.message) {
								message = error.message;
							}
						}
						else if (
							errorType ===
							STATUS_CODE.SC_FILE_EXTENSION_EXCEPTION
						) {
							if (error.message) {
								message = Lang.sub(
									Liferay.Language.get(
										'please-enter-a-file-with-a-valid-extension-x'
									),
									[error.message]
								);
							}
							else {
								message = Lang.sub(
									Liferay.Language.get(
										'please-enter-a-file-with-a-valid-file-type'
									)
								);
							}
						}
						else if (
							errorType === STATUS_CODE.SC_FILE_NAME_EXCEPTION
						) {
							message = Liferay.Language.get(
								'please-enter-a-file-with-a-valid-file-name'
							);
						}
						else if (
							errorType === STATUS_CODE.SC_FILE_SIZE_EXCEPTION ||
							errorType ===
								STATUS_CODE.SC_UPLOAD_REQUEST_CONTENT_LENGTH_EXCEPTION
						) {
							message = Lang.sub(
								Liferay.Language.get(
									'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
								),
								[
									Liferay.Util.formatStorage(
										instance.get('maxFileSize')
									),
								]
							);
						}
						else if (
							errorType ===
							STATUS_CODE.SC_UPLOAD_REQUEST_SIZE_EXCEPTION
						) {
							const maxUploadRequestSize =
								Liferay.PropsValues
									.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE;

							message = Lang.sub(
								Liferay.Language.get(
									'request-is-larger-than-x-and-could-not-be-processed'
								),
								[
									Liferay.Util.formatStorage(
										maxUploadRequestSize
									),
								]
							);
						}
					}

					return message;
				},

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
										value: Liferay.Util.formatStorage(
											file.size
										),
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
				},

				_onInputFileChanged(event) {
					const instance = this;

					const file = event.currentTarget.getDOMNode().files[0];

					instance._validateFile(file);
				},

				_onItemSelected(itemViewer) {
					const instance = this;

					const link = itemViewer
						.get(STR_LINKS)
						.item(itemViewer.get('currentIndex'));

					instance.fire(STR_SELECTED_ITEM, {
						data: {
							returnType: link.getData('returntype'),
							value: link.getData('value'),
						},
					});
				},

				_onItemUploadCancel() {
					const instance = this;

					instance._uploadItemViewer.hide();
				},

				_onItemUploadComplete(itemData) {
					const instance = this;

					const uploadItemViewer = instance._uploadItemViewer;

					uploadItemViewer.updateCurrentImage(itemData);

					instance._onItemSelected(uploadItemViewer);
				},

				_onItemUploadError(event) {
					const instance = this;

					instance._uploadItemViewer.hide();

					const errorMessage = instance._getUploadErrorMessage(
						event.error
					);

					instance._showError(errorMessage);
				},

				_previewFile(file) {
					const instance = this;

					if (A.config.win.FileReader) {
						const reader = new FileReader();

						reader.addEventListener('loadend', (event) => {
							instance._showFile(file, event.target.result);
						});

						reader.readAsDataURL(file);
					}
				},

				_renderUI() {
					const instance = this;

					const rootNode = instance.rootNode;

					instance._itemViewer.render(rootNode);
					instance._uploadItemViewer.render(rootNode);
				},

				_showError(message) {
					const instance = this;

					Liferay.Util.openToast({
						container: instance.rootNode,
						message,
						type: 'danger',
					});
				},

				_showFile(file, preview) {
					const instance = this;

					const returnType = instance.get('uploadItemReturnType');

					if (!file.type.match(/image.*/)) {
						preview =
							Liferay.ThemeDisplay.getPathThemeImages() +
							'/file_system/large/default.png';
					}

					const linkNode = A.Node.create(
						Lang.sub(UPLOAD_ITEM_LINK_TPL, {
							preview,
							returnType,
							title: file.name,
							value: preview,
						})
					);

					linkNode.setData(
						'metadata',
						JSON.stringify(instance._getUploadFileMetadata(file))
					);

					instance._uploadItemViewer.set(
						STR_LINKS,
						new A.NodeList(linkNode)
					);
					instance._uploadItemViewer.show();

					instance._itemSelectorUploader.startUpload(
						file,
						instance.get('uploadItemURL')
					);
				},

				_validateFile(file) {
					const instance = this;

					let errorMessage = '';

					const fileExtension = file.name
						.split('.')
						.pop()
						.toLowerCase();

					const validExtensions = instance.get('validExtensions');

					if (
						validExtensions === '*' ||
						validExtensions.indexOf(fileExtension) !== -1
					) {
						const maxFileSize = instance.get('maxFileSize');

						if (file.size <= maxFileSize) {
							instance._previewFile(file);
						}
						else {
							errorMessage = Lang.sub(
								Liferay.Language.get(
									'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
								),
								[
									Liferay.Util.formatStorage(
										instance.get('maxFileSize')
									),
								]
							);
						}
					}
					else {
						errorMessage = Lang.sub(
							Liferay.Language.get(
								'please-enter-a-file-with-a-valid-extension-x'
							),
							[validExtensions]
						);
					}

					if (errorMessage) {
						const inputTypeFile = instance.one(
							'input[type="file"]'
						);

						if (inputTypeFile) {
							inputTypeFile.val('');
						}

						instance._showError(errorMessage);
					}
				},

				destructor() {
					const instance = this;

					instance._itemViewer.destroy();
					instance._uploadItemViewer.destroy();
					instance._itemSelectorUploader.destroy();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					instance._itemViewer = new A.LiferayItemViewer({
						btnCloseCaption: instance.get('closeCaption'),
						editItemURL: instance.get('editItemURL'),
						links: instance.all('.item-preview'),
						uploadItemURL: instance.get('uploadItemURL'),
					});

					instance._uploadItemViewer = new A.LiferayItemViewer({
						btnCloseCaption: instance.get('closeCaption'),
						links: '',
						uploadItemURL: instance.get('uploadItemURL'),
					});

					instance._itemSelectorUploader = new A.LiferayItemSelectorUploader(
						{
							rootNode: instance.rootNode,
						}
					);

					instance._bindUI();
					instance._renderUI();
				},
			},
		});

		Liferay.ItemSelectorRepositoryEntryBrowser = ItemSelectorRepositoryEntryBrowser;
	},
	'',
	{
		requires: [
			'liferay-item-selector-uploader',
			'liferay-item-viewer',
			'liferay-portlet-base',
		],
	}
);
