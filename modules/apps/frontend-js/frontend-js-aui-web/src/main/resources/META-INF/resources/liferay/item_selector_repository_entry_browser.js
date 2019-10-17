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

AUI.add(
	'liferay-item-selector-repository-entry-browser',
	A => {
		var AArray = A.Array;
		var Lang = A.Lang;

		var CSS_DROP_ACTIVE = 'drop-active';

		var STATUS_CODE = Liferay.STATUS_CODE;

		var STR_DRAG_LEAVE = 'dragleave';

		var STR_DRAG_OVER = 'dragover';

		var STR_DROP = 'drop';

		var STR_ITEM_SELECTED = '_onItemSelected';

		var STR_ITEM_UPLOAD_ERROR = '_onItemUploadError';

		var STR_LINKS = 'links';

		var STR_SELECTED_ITEM = 'selectedItem';

		var STR_VISIBLE_CHANGE = 'visibleChange';

		var UPLOAD_ITEM_LINK_TPL =
			'<a data-returnType="{returnType}" data-value="{value}" href="{preview}" title="{title}"></a>';

		var ItemSelectorRepositoryEntryBrowser = A.Component.create({
			ATTRS: {
				closeCaption: {
					validator: Lang.isString,
					value: ''
				},
				editItemURL: {
					validator: Lang.isString,
					value: ''
				},
				maxFileSize: {
					setter: Lang.toInt,
					value:
						Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE
				},
				uploadItemReturnType: {
					validator: Lang.isString,
					value: ''
				},
				uploadItemURL: {
					validator: Lang.isString,
					value: ''
				},
				validExtensions: {
					validator: Lang.isString,
					value: '*'
				}
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'itemselectorrepositoryentrybrowser',

			prototype: {
				_afterVisibleChange(event) {
					var instance = this;

					if (!event.newVal) {
						instance.fire(STR_SELECTED_ITEM);
					}
				},

				_bindUI() {
					var instance = this;

					var itemViewer = instance._itemViewer;

					var uploadItemViewer = instance._uploadItemViewer;

					var itemSelectorUploader = instance._itemSelectorUploader;

					var rootNode = instance.rootNode;

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
						)
					];

					var uploadItemURL = instance.get('uploadItemURL');

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

					var inputFileNode = instance.one('input[type="file"]');

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
					var instance = this;

					var dataTransfer = event._event.dataTransfer;

					if (dataTransfer && dataTransfer.types) {
						var dataTransferTypes = dataTransfer.types || [];

						if (
							AArray.indexOf(dataTransferTypes, 'Files') > -1 &&
							AArray.indexOf(dataTransferTypes, 'text/html') ===
								-1
						) {
							event.halt();

							var type = event.type;

							var eventDrop = type === STR_DROP;

							var rootNode = instance.rootNode;

							if (type === STR_DRAG_OVER) {
								rootNode.addClass(CSS_DROP_ACTIVE);
							} else if (type === STR_DRAG_LEAVE || eventDrop) {
								rootNode.removeClass(CSS_DROP_ACTIVE);

								if (eventDrop) {
									var file = dataTransfer.files[0];

									instance._validateFile(file);
								}
							}
						}
					}
				},

				_getUploadErrorMessage(error) {
					var instance = this;

					var message = Liferay.Language.get(
						'an-unexpected-error-occurred-while-uploading-your-file'
					);

					if (error && error.errorType) {
						var errorType = error.errorType;

						if (
							errorType ===
							STATUS_CODE.SC_FILE_ANTIVIRUS_EXCEPTION
						) {
							if (error.message) {
								message = error.message;
							}
						} else if (
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
							} else {
								message = Lang.sub(
									Liferay.Language.get(
										'please-enter-a-file-with-a-valid-file-type'
									)
								);
							}
						} else if (
							errorType === STATUS_CODE.SC_FILE_NAME_EXCEPTION
						) {
							message = Liferay.Language.get(
								'please-enter-a-file-with-a-valid-file-name'
							);
						} else if (
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
									)
								]
							);
						} else if (
							errorType ===
							STATUS_CODE.SC_UPLOAD_REQUEST_SIZE_EXCEPTION
						) {
							var maxUploadRequestSize =
								Liferay.PropsValues
									.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE;

							message = Lang.sub(
								Liferay.Language.get(
									'request-is-larger-than-x-and-could-not-be-processed'
								),
								[
									Liferay.Util.formatStorage(
										maxUploadRequestSize
									)
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
										value: file.type
									},
									{
										key: Liferay.Language.get('size'),
										value: Liferay.Util.formatStorage(
											file.size
										)
									},
									{
										key: Liferay.Language.get('name'),
										value: file.name
									}
								],
								title: Liferay.Language.get('file-info')
							}
						]
					};
				},

				_onInputFileChanged(event) {
					var instance = this;

					var file = event.currentTarget.getDOMNode().files[0];

					instance._validateFile(file);
				},

				_onItemSelected(itemViewer) {
					var instance = this;

					var link = itemViewer
						.get(STR_LINKS)
						.item(itemViewer.get('currentIndex'));

					instance.fire(STR_SELECTED_ITEM, {
						data: {
							returnType: link.getData('returntype'),
							value: link.getData('value')
						}
					});
				},

				_onItemUploadCancel() {
					var instance = this;

					instance._uploadItemViewer.hide();
				},

				_onItemUploadComplete(itemData) {
					var instance = this;

					var uploadItemViewer = instance._uploadItemViewer;

					uploadItemViewer.updateCurrentImage(itemData);

					instance._onItemSelected(uploadItemViewer);
				},

				_onItemUploadError(event) {
					var instance = this;

					instance._uploadItemViewer.hide();

					var errorMessage = instance._getUploadErrorMessage(
						event.error
					);

					instance._showError(errorMessage);
				},

				_previewFile(file) {
					var instance = this;

					if (A.config.win.FileReader) {
						var reader = new FileReader();

						reader.addEventListener('loadend', event => {
							instance._showFile(file, event.target.result);
						});

						reader.readAsDataURL(file);
					}
				},

				_renderUI() {
					var instance = this;

					var rootNode = instance.rootNode;

					instance._itemViewer.render(rootNode);
					instance._uploadItemViewer.render(rootNode);
				},

				_showError(message) {
					var instance = this;

					new Liferay.Alert({
						closeable: true,
						delay: {
							hide: 5000,
							show: 0
						},
						duration: 250,
						icon: 'exclamation-full',
						message,
						type: 'danger'
					}).render(instance.rootNode);
				},

				_showFile(file, preview) {
					var instance = this;

					var returnType = instance.get('uploadItemReturnType');

					if (!file.type.match(/image.*/)) {
						preview =
							Liferay.ThemeDisplay.getPathThemeImages() +
							'/file_system/large/default.png';
					}

					var linkNode = A.Node.create(
						Lang.sub(UPLOAD_ITEM_LINK_TPL, {
							preview,
							returnType,
							title: file.name,
							value: preview
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
					var instance = this;

					var errorMessage = '';

					var fileExtension = file.name
						.split('.')
						.pop()
						.toLowerCase();

					var validExtensions = instance.get('validExtensions');

					if (
						validExtensions === '*' ||
						validExtensions.indexOf(fileExtension) != -1
					) {
						var maxFileSize = instance.get('maxFileSize');

						if (file.size <= maxFileSize) {
							instance._previewFile(file);
						} else {
							errorMessage = Lang.sub(
								Liferay.Language.get(
									'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
								),
								[
									Liferay.Util.formatStorage(
										instance.get('maxFileSize')
									)
								]
							);
						}
					} else {
						errorMessage = Lang.sub(
							Liferay.Language.get(
								'please-enter-a-file-with-a-valid-extension-x'
							),
							[validExtensions]
						);
					}

					if (errorMessage) {
						var inputTypeFile = instance.one('input[type="file"]');

						if (inputTypeFile) {
							inputTypeFile.val('');
						}

						instance._showError(errorMessage);
					}
				},

				destructor() {
					var instance = this;

					instance._itemViewer.destroy();
					instance._uploadItemViewer.destroy();
					instance._itemSelectorUploader.destroy();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					instance._itemViewer = new A.LiferayItemViewer({
						btnCloseCaption: instance.get('closeCaption'),
						editItemURL: instance.get('editItemURL'),
						links: instance.all('.item-preview'),
						uploadItemURL: instance.get('uploadItemURL')
					});

					instance._uploadItemViewer = new A.LiferayItemViewer({
						btnCloseCaption: instance.get('closeCaption'),
						links: '',
						uploadItemURL: instance.get('uploadItemURL')
					});

					instance._itemSelectorUploader = new A.LiferayItemSelectorUploader(
						{
							rootNode: instance.rootNode
						}
					);

					instance._bindUI();
					instance._renderUI();
				}
			}
		});

		Liferay.ItemSelectorRepositoryEntryBrowser = ItemSelectorRepositoryEntryBrowser;
	},
	'',
	{
		requires: [
			'liferay-alert',
			'liferay-item-selector-uploader',
			'liferay-item-viewer',
			'liferay-portlet-base'
		]
	}
);
