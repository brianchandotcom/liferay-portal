/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'document-library-upload-component',
	(A) => {
		const AArray = A.Array;
		const ANode = A.Node;
		const Lang = A.Lang;
		const LString = Lang.String;
		const UploaderQueue = A.Uploader.Queue;

		const isNumber = Lang.isNumber;
		const isString = Lang.isString;

		const sub = Lang.sub;

		const CSS_ACTIVE_AREA = 'active-area';

		const CSS_DISPLAY_DESCRIPTIVE = 'display-descriptive';

		const CSS_DISPLAY_ICON = 'display-icon';

		const CSS_ENTRIES_EMPTY = 'entries-empty';

		const CSS_ENTRY_DISPLAY_STYLE = 'entry-display-style';

		const CSS_ENTRY_LINK = CSS_ENTRY_DISPLAY_STYLE + ' a';

		const CSS_ENTRY_SELECTOR = 'entry-selector';

		const CSS_ICON = 'icon';

		const CSS_SEARCHCONTAINER = 'searchcontainer';

		const DOC = A.config.doc;

		const REGEX_AUDIO = /\.(aac|auif|bwf|flac|mp3|mp4|m4a|wav|wma)$/i;

		const REGEX_COMPRESSED = /\.(dmg|gz|tar|tgz|zip)$/i;

		const REGEX_IMAGE = /\.(bmp|gif|jpeg|jpg|png|tiff)$/i;

		const REGEX_VIDEO = /\.(avi|flv|mpe|mpg|mpeg|mov|m4v|ogg|wmv)$/i;

		const SELECTOR_DATA_FOLDER = '[data-folder="true"]';

		const SELECTOR_DATA_FOLDER_DATA_TITLE =
			'[data-folder="true"][data-title]';

		const STR_DOT = '.';

		const SELECTOR_DISPLAY_DESCRIPTIVE = STR_DOT + CSS_DISPLAY_DESCRIPTIVE;

		const SELECTOR_DISPLAY_ICON = STR_DOT + CSS_DISPLAY_ICON;

		const SELECTOR_ENTRIES_EMPTY = STR_DOT + CSS_ENTRIES_EMPTY;

		const SELECTOR_ENTRY_DISPLAY_STYLE = STR_DOT + CSS_ENTRY_DISPLAY_STYLE;

		const SELECTOR_ENTRY_LINK = STR_DOT + CSS_ENTRY_LINK;

		const SELECTOR_SEARCH_CONTAINER = STR_DOT + CSS_SEARCHCONTAINER;

		const STR_BLANK = '';

		const STR_BOUNDING_BOX = 'boundingBox';

		const STR_CONTENT_BOX = 'contentBox';

		const STR_EXTENSION_PDF = '.pdf';

		const STR_FIRST = 'first';

		const STR_FOLDER_ID = 'folderId';

		const STR_LABEL = 'label';

		const STR_LIST = 'list';

		const STR_NAME = 'name';

		const STR_NAVIGATION_OVERLAY_BACKGROUND = '#FFF';

		const STR_SIZE = 'size';

		const STR_ICON_DEFAULT = 'document-default';

		const STR_ICON_PDF = 'document-vector';

		const STR_ICON_IMAGE = 'document-image';

		const STR_ICON_COMPRESSED = 'document-compressed';

		const STR_ICON_MULTIMEDIA = 'document-multimedia';

		const TPL_ENTRIES_CONTAINER = '<dl class="{cssClass}"></dl>';

		const TPL_ENTRY_ROW_TITLE = `<div class="table-title">
			<span class="sticker sticker-rounded sticker-document sticker-secondary file-icon-color-0">
				<span class="sticker-overlay">
					${Liferay.Util.getLexiconIconTpl(STR_ICON_DEFAULT)}
				</span>
			</span>
			<a>{0}</a>
		</div>`;

		const TPL_ENTRY_WRAPPER =
			'<dd class="card-page-item card-page-item-asset" data-title="{title}"></dd>';

		const TPL_ERROR_NOTIFICATION = new A.Template(
			'{title}',

			'<tpl if="invalidFiles.length < 3">',

			'<ul class="mb-0 mt-2 pl-3">',
			'<tpl for="invalidFiles">',
			'<li><b>{name}</b>: {errorMessage}</li>',
			'</tpl>',
			'</ul>',

			'</tpl>'
		);

		const TPL_HIDDEN_CHECK_BOX =
			'<input class="hide ' +
			CSS_ENTRY_SELECTOR +
			'" name="{0}" type="checkbox" value="">';

		const TPL_IMAGE_THUMBNAIL =
			themeDisplay.getPathContext() + '/documents/{0}/{1}/{2}';

		const DocumentLibraryUpload = A.Component.create({
			ATTRS: {
				appViewEntryTemplates: {
					// eslint-disable-next-line @liferay/aui/no-one
					validator: A.one,
					value: {},
				},

				columnNames: {
					setter(val) {
						val.push(STR_BLANK);
						val.unshift(STR_BLANK);

						return val;
					},
					validator: Array.isArray,
					value: [],
				},

				displayStyle: {
					validator: isString,
					value: STR_BLANK,
				},

				documentLibraryNamespace: {
					validator: isString,
					value: STR_BLANK,
				},

				entriesContainer: {
					// eslint-disable-next-line @liferay/aui/no-one
					validator: A.one,
					value: {},
				},

				folderId: {
					readonly: true,
					setter: Lang.toInt,
					value: null,
				},

				maxFileSize: {
					validator(val) {
						return isNumber(val) && val > 0;
					},
					value:
						Liferay.PropsValues
							.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
				},

				redirect: {
					validator: isString,
					value: STR_BLANK,
				},

				scopeGroupId: {
					setter: Lang.toInt,
					value: null,
				},

				uploadURL: {
					setter: '_decodeURI',
					validator: isString,
					value: STR_BLANK,
				},

				viewFileEntryURL: {
					setter: '_decodeURI',
					validator: isString,
					value: STR_BLANK,
				},
			},
			EXTENDS: A.Base,

			NAME: 'documentlibraryupload',

			NS: 'upload',

			prototype: {
				_addFilesToQueueBottom(files) {
					const instance = this;

					const queue = instance._getUploader().queue;

					files.forEach((item) => {
						queue.addToQueueBottom(item);
					});
				},

				_attachSubscriptions(data) {
					const instance = this;

					const handles = instance._handles;

					const displayStyle = instance._getDisplayStyle();
					const uploader = instance._getUploader();

					instance._detachSubscriptions();

					if (data.folder) {
						handles.push(
							uploader.on(
								'alluploadscomplete',
								instance._showFolderUploadComplete,
								instance,
								data
							),
							uploader.on(
								'totaluploadprogress',
								instance._showFolderUploadProgress,
								instance,
								data
							),
							uploader.on(
								'uploadcomplete',
								instance._detectUploadError,
								instance,
								data
							),
							uploader.on(
								'uploadstart',
								instance._showFolderUploadStarting,
								instance,
								data
							)
						);
					}
					else {
						handles.push(
							uploader.after(
								'fileuploadstart',
								instance._showFileUploadStarting,
								instance
							),
							uploader.on(
								'uploadcomplete',
								instance._showFileUploadComplete,
								instance,
								displayStyle
							),
							uploader.on(
								'uploadprogress',
								instance._showFileUploadProgress,
								instance
							)
						);
					}
				},

				_bindDragDropUI() {
					const instance = this;
					// eslint-disable-next-line @liferay/aui/no-one
					const docElement = A.one(DOC.documentElement);

					const entriesContainer = instance._entriesContainer;

					A.getWin()._node.onbeforeunload = A.bind(
						'_confirmUnload',
						instance
					);

					const removeCssClassTask = A.debounce(() => {
						docElement.removeClass('upload-drop-intent');
						docElement.removeClass('upload-drop-active');
					}, 500);

					const onDragOverHandle = docElement.on(
						'dragover',
						(event) => {
							const dataTransfer = event._event.dataTransfer;

							if (dataTransfer && dataTransfer.types) {
								const dataTransferTypes =
									dataTransfer.types || [];

								if (
									AArray.indexOf(dataTransferTypes, 'Files') >
										-1 &&
									AArray.indexOf(
										dataTransferTypes,
										'text/html'
									) === -1
								) {
									event.halt();

									dataTransfer.dropEffect = 'copy';

									docElement.addClass('upload-drop-intent');

									const target = event.target;

									docElement.toggleClass(
										'upload-drop-active',
										target.compareTo(entriesContainer) ||
											entriesContainer.contains(target)
									);

									removeCssClassTask();
								}
							}
						}
					);

					const onDropHandle = docElement.delegate(
						'drop',
						(event) => {
							const dataTransfer = event._event.dataTransfer;

							if (dataTransfer) {
								const dataTransferTypes =
									dataTransfer.types || [];

								if (
									AArray.indexOf(dataTransferTypes, 'Files') >
										-1 &&
									AArray.indexOf(
										dataTransferTypes,
										'text/html'
									) === -1
								) {
									event.halt();

									const dragDropFiles = AArray(
										dataTransfer.files
									);

									event.fileList = dragDropFiles.map(
										(item) => {
											return new A.FileHTML5(item);
										}
									);

									const uploader = instance._getUploader();

									uploader.fire('fileselect', event);
								}
							}
						},
						'body, .document-container, .overlaymask, .progressbar, [data-folder="true"]'
					);

					const entriesDragDelegateHandle = entriesContainer.delegate(
						['dragleave', 'dragover'],
						(event) => {
							const dataTransfer = event._event.dataTransfer;

							const dataTransferTypes = dataTransfer.types;

							if (
								AArray.indexOf(dataTransferTypes, 'Files') >
									-1 &&
								AArray.indexOf(
									dataTransferTypes,
									'text/html'
								) === -1
							) {
								let parentElement = event.target.ancestor(
									'[data-folder="true"]'
								);

								if (!parentElement) {
									parentElement = event.target;
								}

								parentElement.toggleClass(
									CSS_ACTIVE_AREA,
									event.type === 'dragover'
								);
							}
						},
						SELECTOR_DATA_FOLDER
					);

					instance._eventHandles = [
						onDragOverHandle,
						onDropHandle,
						entriesDragDelegateHandle,
					];
				},

				_combineFileLists(fileList, queuedFiles) {
					queuedFiles.forEach((item) => {
						fileList.push(item);
					});
				},

				_confirmUnload() {
					const instance = this;

					if (instance._isUploading()) {
						return Liferay.Language.get(
							'uploads-are-in-progress-confirmation'
						);
					}
				},

				_createEntriesContainer(searchContainer, displayStyle) {
					let containerClasses =
						'list-group list-group-notification show-quick-actions-on-hover';

					if (displayStyle === CSS_ICON) {
						containerClasses = 'card-page card-page-equal-height';
					}

					const entriesContainer = ANode.create(
						Lang.sub(TPL_ENTRIES_CONTAINER, {
							cssClass: containerClasses,
						})
					);

					searchContainer
						.one('.searchcontainer-content')
						.append(entriesContainer);

					return entriesContainer;
				},

				_createEntryNode(name, size, displayStyle) {
					const instance = this;

					let entryNode;

					let entriesContainer = instance._entriesContainer;

					const searchContainer = entriesContainer.one(
						SELECTOR_SEARCH_CONTAINER
					);

					if (displayStyle === STR_LIST) {
						entriesContainer = searchContainer.one('tbody');

						entryNode = instance._createEntryRow(name, size);
					}
					else {
						let entriesContainerSelector =
							'dl.list-group:last-of-type';

						if (displayStyle === CSS_ICON) {
							entriesContainerSelector =
								'dl.card-page:last-of-type';

							if (
								entriesContainer
									.one(entriesContainerSelector)
									?.one('.card-type-directory')
							) {
								entriesContainerSelector = null;
							}
						}

						entriesContainer =
							entriesContainer.one(entriesContainerSelector) ||
							instance._createEntriesContainer(
								entriesContainer,
								displayStyle
							);

						let invisibleEntry =
							instance._invisibleDescriptiveEntry;

						const hiddenCheckbox = sub(TPL_HIDDEN_CHECK_BOX, [
							`${instance._documentLibraryNamespace}rowIdsFileEntry`,
						]);

						if (displayStyle === CSS_ICON) {
							invisibleEntry = instance._invisibleIconEntry;
						}

						entryNode = invisibleEntry.clone();

						entryNode.append(hiddenCheckbox);

						const entryLink = entryNode.one('a');

						const entryTitle = entryLink;

						entryLink.attr('title', name);

						entryTitle.setContent(name);

						instance._hideEmptyResultsMessage(searchContainer);

						const searchContainerWrapper = instance._entriesContainer
							.one('div.lfr-search-container-wrapper')
							.getDOMNode();

						if (searchContainerWrapper) {
							searchContainerWrapper.style.display = 'block';

							searchContainerWrapper.classList.remove('hide');
						}
					}

					entryNode.attr({
						'data-title': name,
						'id': A.guid(),
					});

					if (displayStyle === CSS_ICON) {
						const entryNodeWrapper = ANode.create(
							Lang.sub(TPL_ENTRY_WRAPPER, {
								title: name,
							})
						);

						entryNodeWrapper.append(entryNode);

						entryNode = entryNodeWrapper;
					}

					entriesContainer.append(entryNode);

					entryNode.show().scrollIntoView();

					return entryNode;
				},

				_createEntryRow(name, size) {
					const instance = this;

					const searchContainer = instance._getSearchContainer();

					const columnValues = instance._columnNames.map(
						(item, index) => {
							let value = STR_BLANK;

							if (item === STR_NAME) {
								value = sub(TPL_ENTRY_ROW_TITLE, [name]);
							}
							else if (item === STR_SIZE) {
								value = Liferay.Util.formatStorage(size);
							}
							else if (item === 'downloads') {
								value = '0';
							}
							else if (index === 0) {
								value = sub(TPL_HIDDEN_CHECK_BOX, [
									`${instance._documentLibraryNamespace}rowIdsFileEntry`,
								]);
							}

							return value;
						}
					);

					const rowid = A.guid();
					const row = searchContainer.addRow(columnValues, rowid);

					row.attr('data-draggable', true);
					row.attr('data-rowid', rowid);

					return row;
				},

				_createOverlay(target, background) {
					const instance = this;

					const displayStyle = instance._getDisplayStyle();
					const overlay = new A.OverlayMask({
						background: background || null,
						target:
							displayStyle !== CSS_ICON
								? target
								: target.one('.card'),
					}).render();

					overlay
						.get(STR_BOUNDING_BOX)
						.addClass('portlet-document-library-upload-mask');

					return overlay;
				},

				_createProgressBar(target) {
					return new A.ProgressBar({
						height: 16,
						on: {
							complete() {
								this.set(STR_LABEL, 'complete!');
							},
							valueChange(event) {
								this.set(STR_LABEL, event.newVal + '%');
							},
						},
						width: target.width() * 0.8,
					});
				},

				_createUploadStatus(target, file) {
					const instance = this;

					const overlay = instance._createOverlay(target);
					const progressBar = instance._createProgressBar(target);

					overlay.show();

					if (file) {
						file.overlay = overlay;
						file.progressBar = progressBar;
						file.target = target;
					}
					else {
						target.overlay = overlay;
						target.progressBar = progressBar;
					}
				},

				_decodeURI(val) {
					return decodeURI(val);
				},

				_destroyEntry() {
					const instance = this;

					const currentUploadData = instance._getCurrentUploadData();

					const fileList = currentUploadData.fileList;

					if (!currentUploadData.folder) {
						fileList.forEach((item) => {
							item.overlay.destroy();

							item.progressBar.destroy();
						});
					}

					AArray.invoke(fileList, 'destroy');
				},

				_detachSubscriptions() {
					const instance = this;

					const handles = instance._handles;

					AArray.invoke(handles, 'detach');

					handles.length = 0;
				},

				_detectUploadError(event, data, response) {
					const instance = this;

					data = data || instance._getCurrentUploadData();
					response =
						response || instance._getUploadResponse(event.data);

					if (response.error) {
						const file = event.file;

						const invalidFileIndex = data.fileList.findIndex(
							({name}) => file.name === name
						);

						if (invalidFileIndex !== -1) {
							const invalidFile = data.fileList[invalidFileIndex];
							invalidFile.errorMessage = response.message;

							data.fileList.splice(invalidFileIndex, 1);

							data.invalidFiles.push(invalidFile);
						}
					}
				},

				_getCurrentUploadData() {
					const instance = this;

					const dataSet = instance._getDataSet();

					return dataSet.get(STR_FIRST);
				},

				_getDataSet() {
					const instance = this;

					let dataSet = instance._dataSet;

					if (!dataSet) {
						dataSet = new A.DataSet();

						instance._dataSet = dataSet;
					}

					return dataSet;
				},

				_getDisplayStyle(style) {
					const instance = this;

					let displayStyle = instance._displayStyle;

					if (style) {
						displayStyle = style === displayStyle;
					}

					return displayStyle;
				},

				_getEmptyMessage() {
					const instance = this;

					let emptyMessage = instance._emptyMessage;

					if (!emptyMessage) {
						emptyMessage = instance._entriesContainer.one(
							SELECTOR_ENTRIES_EMPTY
						);

						instance._emptyMessage = emptyMessage;
					}

					return emptyMessage;
				},

				_getFolderEntryNode(target) {
					let folderEntry;

					const overlayContentBox = target.hasClass(
						'overlay-content'
					);

					if (overlayContentBox) {
						const overlay = A.Widget.getByNode(target);

						folderEntry = overlay._originalConfig.target;
					}
					else {
						if (target.attr('data-folder') === 'true') {
							folderEntry = target;
						}

						if (!folderEntry) {
							folderEntry = target.ancestor(
								SELECTOR_ENTRY_LINK + SELECTOR_DATA_FOLDER
							);
						}

						if (!folderEntry) {
							folderEntry = target.ancestor(
								SELECTOR_DATA_FOLDER_DATA_TITLE
							);
						}
					}

					return folderEntry;
				},

				_getImageThumbnail(fileName) {
					const instance = this;

					return sub(TPL_IMAGE_THUMBNAIL, [
						instance._scopeGroupId,
						instance.get(STR_FOLDER_ID),
						fileName,
					]);
				},

				_getMediaIcon(fileName) {
					let iconName = STR_ICON_DEFAULT;

					if (REGEX_IMAGE.test(fileName)) {
						iconName = STR_ICON_IMAGE;
					}
					else if (
						LString.endsWith(
							fileName.toLowerCase(),
							STR_EXTENSION_PDF
						)
					) {
						iconName = STR_ICON_PDF;
					}
					else if (
						REGEX_AUDIO.test(fileName) ||
						REGEX_VIDEO.test(fileName)
					) {
						iconName = STR_ICON_MULTIMEDIA;
					}
					else if (REGEX_COMPRESSED.test(fileName)) {
						iconName = STR_ICON_COMPRESSED;
					}

					return iconName;
				},

				_getNavigationOverlays() {
					const instance = this;

					let navigationOverlays = instance._navigationOverlays;

					if (!navigationOverlays) {
						navigationOverlays = [];

						const createNavigationOverlay = function (target) {
							if (target) {
								const overlay = instance._createOverlay(
									target,
									STR_NAVIGATION_OVERLAY_BACKGROUND
								);

								navigationOverlays.push(overlay);
							}
						};

						const entriesContainer = instance._entriesContainer;

						createNavigationOverlay(
							entriesContainer.one(
								'.app-view-taglib.lfr-header-row'
							)
						);
						createNavigationOverlay(
							instance.get('.searchcontainer')
						);

						instance._navigationOverlays = navigationOverlays;
					}

					return navigationOverlays;
				},

				_getSearchContainer() {
					const instance = this;

					const searchContainerNode = instance._entriesContainer.one(
						SELECTOR_SEARCH_CONTAINER
					);

					return Liferay.SearchContainer.get(
						searchContainerNode.attr('id')
					);
				},

				_getTargetFolderId(target) {
					const instance = this;

					const folderEntry = instance._getFolderEntryNode(target);

					return (
						(folderEntry &&
							Lang.toInt(folderEntry.attr('data-folder-id'))) ||
						instance.get(STR_FOLDER_ID)
					);
				},

				_getUploadResponse(responseData) {
					const instance = this;

					let error;
					let message;

					try {
						responseData = JSON.parse(responseData);
					}
					catch (error) {}

					if (Lang.isObject(responseData)) {
						error = Boolean(
							responseData.status === 0 ||
								(responseData.status &&
									responseData.status >= 490 &&
									responseData.status < 500)
						);

						if (error) {
							message =
								responseData.message ||
								Liferay.Language.get('unexpected-error');
						}
						else {
							message = `${instance._documentLibraryNamespace}fileEntryId=${responseData.fileEntryId}`;
						}
					}

					return {
						error,
						message,
					};
				},

				_getUploadStatus(key) {
					const instance = this;

					const dataSet = instance._getDataSet();

					return dataSet.item(String(key));
				},

				_getUploadURL(folderId) {
					const instance = this;

					if (!instance._uploadURL) {
						instance._uploadURL = instance._decodeURI(
							Liferay.Util.addParams(
								{
									redirect: instance.get('redirect'),
									ts: Date.now(),
								},
								instance.get('uploadURL')
							)
						);
					}

					return sub(instance._uploadURL, {
						folderId,
					});
				},

				_getUploader() {
					const instance = this;

					let uploader = instance._uploader;

					if (!uploader) {
						uploader = new A.Uploader({
							appendNewFiles: false,
							fileFieldName: 'file',
							multipleFiles: true,
							simLimit: 1,
						});

						const navigationOverlays = instance._getNavigationOverlays();

						uploader.on('uploadstart', () => {
							AArray.invoke(navigationOverlays, 'show');
						});

						uploader.after(
							'alluploadscomplete',
							instance._onAllUploadsComplete,
							instance
						);

						uploader.get(STR_BOUNDING_BOX).hide();

						uploader.render();

						uploader.after(
							'alluploadscomplete',
							instance._startNextUpload,
							instance
						);
						uploader.after(
							'fileselect',
							instance._onFileSelect,
							instance
						);

						instance._uploader = uploader;
					}

					return uploader;
				},

				_hideEmptyResultsMessage(searchContainer) {
					const id = searchContainer.getAttribute('id');

					const emptyResultsMessage = document.getElementById(
						`${id}EmptyResultsMessage`
					);

					if (emptyResultsMessage) {
						emptyResultsMessage.style.display = 'none';

						emptyResultsMessage.classList.remove('hide');
					}
				},

				_isUploading() {
					const instance = this;

					const uploader = instance._uploader;

					const queue = uploader && uploader.queue;

					return (
						!!queue &&
						(!!queue.queuedFiles.length ||
							queue.numberOfUploads > 0 ||
							// eslint-disable-next-line @liferay/aui/no-object
							!A.Object.isEmpty(queue.currentFiles)) &&
						queue._currentState === UploaderQueue.UPLOADING
					);
				},

				_onAllUploadsComplete() {
					const instance = this;
					const navigationOverlays = instance._getNavigationOverlays();

					AArray.invoke(navigationOverlays, 'hide');

					const currentUploadData = instance._getCurrentUploadData();

					const invalidFilesLength =
						currentUploadData.invalidFiles.length;
					const validFilesLength = currentUploadData.fileList.length;

					const searchContainer = instance._getSearchContainer();

					const emptyMessage = instance._getEmptyMessage();

					if (
						emptyMessage &&
						!emptyMessage.hasClass('hide') &&
						!validFilesLength
					) {
						emptyMessage.hide(true);
					}

					if (validFilesLength) {
						let messageText = null;

						if (validFilesLength === 1) {
							messageText = Liferay.Util.sub(
								instance._strings.singleFileUploaded,
								currentUploadData.fileList[0].name,
								instance._strings.pageRefreshNeeded
							);
						}
						else {
							messageText = Liferay.Util.sub(
								instance._strings.multipleFilesUploaded,
								validFilesLength,
								instance._strings.pageRefreshNeeded
							);
						}

						const openToastSuccessProps = {
							message: messageText,
							toastProps: {
								className: 'alert-full',
							},
							type: 'success',
						};

						if (!currentUploadData.folder && !invalidFilesLength) {
							const reloadButtonClassName = 'dl-reload-button';

							openToastSuccessProps.autoClose = 10000;

							openToastSuccessProps.message =
								openToastSuccessProps.message +
								`<div class="alert-footer">
										<div class="btn-group" role="group">
											<button class="btn btn-sm btn-primary alert-btn ${reloadButtonClassName}">${instance._strings.reloadButton}</button>
										</div>
								</div>`;

							openToastSuccessProps.onClick = ({
								event,
								onClose: closeToast,
							}) => {
								if (
									event.target.classList.contains(
										reloadButtonClassName
									)
								) {
									Liferay.Portlet.refresh(
										`#p_p_id${instance._documentLibraryNamespace}`
									);
									closeToast();
								}
							};
						}

						Liferay.Util.openToast(openToastSuccessProps);
					}

					if (invalidFilesLength) {
						if (!currentUploadData.folder) {
							const displayStyle = instance._getDisplayStyle();

							currentUploadData.invalidFiles.forEach(
								(invalidFile) => {
									if (invalidFile.target) {
										if (displayStyle !== STR_LIST) {
											invalidFile.target.remove();
											invalidFile.target.destroy();
										}
										else {
											searchContainer.deleteRow(
												invalidFile.target,
												invalidFile.target.getAttribute(
													'data-rowid'
												)
											);
										}
									}

									invalidFile.progressBar?.destroy();
									invalidFile.overlay?.destroy();
								}
							);

							if (!validFilesLength) {
								instance._showEmptyResultsMessage();
							}
						}
						else {
							instance._showFolderUploadComplete(
								undefined,
								currentUploadData
							);
						}

						const openToastErrorProps = {
							message: TPL_ERROR_NOTIFICATION.parse({
								invalidFiles: currentUploadData.invalidFiles,
								title: Liferay.Util.sub(
									instance._strings.xInvalidFilesUploaded,
									invalidFilesLength
								),
							}),
							toastProps: {
								className: 'alert-full',
							},
							type: 'danger',
						};

						if (invalidFilesLength < 3) {
							openToastErrorProps.autoClose = false;
						}

						Liferay.Util.openToast(openToastErrorProps);
					}
				},

				_onFileSelect(event) {
					const instance = this;
					const target = event.details[0].target;

					// LPS-159994

					if (
						!instance._entriesContainer.contains(
							event.details[0].currentTarget
						)
					) {
						instance
							._getFolderEntryNode(target)
							?.removeClass(CSS_ACTIVE_AREA);

						return;
					}

					const filesPartition = instance._validateFiles(
						event.fileList
					);

					instance._updateStatusUI(target, filesPartition);

					instance._queueSelectedFiles(target, filesPartition);
				},

				_positionProgressBar(overlay, progressBar) {
					const progressBarBoundingBox = progressBar.get(
						STR_BOUNDING_BOX
					);

					progressBar.render(overlay.get(STR_BOUNDING_BOX));

					progressBarBoundingBox.center(overlay.get(STR_CONTENT_BOX));
				},

				_queueSelectedFiles(target, filesPartition) {
					const instance = this;

					const key = instance._getTargetFolderId(target);

					const keyData = instance._getUploadStatus(key);

					const validFiles = filesPartition.matches;

					if (keyData) {
						instance._updateDataSetEntry(key, keyData, validFiles);
					}
					else {
						const dataSet = instance._getDataSet();

						let folderNode = null;

						const folder = key !== instance.get(STR_FOLDER_ID);

						if (folder) {
							folderNode = instance._getFolderEntryNode(target);
						}

						dataSet.add(key, {
							fileList: validFiles,
							folder,
							folderId: key,
							invalidFiles: filesPartition.rejects,
							target: folderNode,
						});
					}

					if (!instance._isUploading()) {
						instance._startUpload();
					}
				},

				_showEmptyResultsMessage() {
					const instance = this;

					const searchContainer = instance._getSearchContainer();
					const entriesContainer = instance._entriesContainer;

					if (
						searchContainer.getSize() ||
						entriesContainer
							.all(SELECTOR_ENTRY_DISPLAY_STYLE)
							.size()
					) {
						return;
					}

					const id = entriesContainer
						.one(SELECTOR_SEARCH_CONTAINER)
						.getAttribute('id');

					const emptyResultsMessage = document.getElementById(
						`${id}EmptyResultsMessage`
					);

					if (emptyResultsMessage) {
						emptyResultsMessage.style.display = 'block';

						emptyResultsMessage.classList.remove('hide');
					}
				},

				_showFileUploadComplete(event, displayStyle) {
					const instance = this;

					const file = event.file;

					const fileNode = file.target;

					const response = instance._getUploadResponse(event.data);

					if (response) {
						const hasErrors = !!response.error;

						if (hasErrors) {
							instance._detectUploadError(event, null, response);
						}
						else {
							const fileEntryId = JSON.parse(event.data)
								.fileEntryId;

							instance._updateEntryUI(
								fileNode,
								file.name,
								displayStyle
							);

							instance._updateFileLink(
								fileNode,
								response.message,
								displayStyle
							);

							instance._updateFileHiddenInput(
								fileNode,
								fileEntryId
							);
						}
					}

					file.overlay.hide();
				},

				_showFileUploadProgress(event) {
					const instance = this;

					instance._updateProgress(
						event.file.progressBar,
						event.percentLoaded
					);
				},

				_showFileUploadStarting(event) {
					const instance = this;

					const file = event.file;

					instance._positionProgressBar(
						file.overlay,
						file.progressBar
					);
				},

				_showFolderUploadComplete(_event, uploadData) {
					const folderEntry = uploadData.target;

					folderEntry.overlay.hide();
				},

				_showFolderUploadProgress(event, uploadData) {
					const instance = this;

					instance._updateProgress(
						uploadData.target.progressBar,
						event.percentLoaded
					);
				},

				_showFolderUploadStarting(_event, uploadData) {
					const instance = this;

					const target = uploadData.target;

					instance._positionProgressBar(
						target.overlay,
						target.progressBar
					);
				},

				_startNextUpload() {
					const instance = this;

					instance._destroyEntry();

					const dataSet = instance._getDataSet();

					dataSet.removeAt(0);

					if (dataSet.length) {
						instance._startUpload();
					}
				},

				_startUpload() {
					const instance = this;

					const uploadData = instance._getCurrentUploadData();

					const fileList = uploadData.fileList;

					const uploader = instance._getUploader();

					if (fileList.length) {
						const uploadURL = instance._getUploadURL(
							uploadData.folderId
						);

						instance._attachSubscriptions(uploadData);

						uploader.uploadThese(fileList, uploadURL);
					}
					else {
						uploader.fire('alluploadscomplete');
					}
				},

				_updateDataSetEntry(key, data, unmergedData) {
					const instance = this;

					const currentUploadData = instance._getCurrentUploadData();

					if (currentUploadData.folderId === key) {
						instance._addFilesToQueueBottom(unmergedData);
					}
					else {
						instance._combineFileLists(data.fileList, unmergedData);

						const dataSet = instance._getDataSet();

						dataSet.replace(key, data);
					}
				},

				_updateEntryIcon(node, fileName) {
					const instance = this;

					const stickerNode = node.one('.sticker-overlay');
					const mediaIcon = instance._getMediaIcon(fileName);

					stickerNode.html(Liferay.Util.getLexiconIconTpl(mediaIcon));
				},

				_updateEntryUI(node, fileName, displayStyle) {
					const instance = this;

					const displayStyleList = displayStyle === STR_LIST;

					instance._updateEntryIcon(node, fileName);

					if (!displayStyleList && REGEX_IMAGE.test(fileName)) {
						instance._updateThumbnail(node, fileName, displayStyle);
					}
				},

				_updateFileHiddenInput(node, id) {
					const inputNode = node.one('input');

					if (inputNode) {
						inputNode.val(id);
					}
				},

				_updateFileLink(node, id, displayStyle) {
					const instance = this;

					let selector = 'a';

					if (displayStyle === CSS_ICON) {
						selector = SELECTOR_ENTRY_LINK;
					}

					const link = node.all(selector);

					if (link.size()) {
						link.attr(
							'href',
							Liferay.Util.addParams(
								id,
								instance.get('viewFileEntryURL')
							)
						);
					}
				},

				_updateProgress(progressBar, value) {
					progressBar.set('value', Math.ceil(value));
				},

				_updateStatusUI(target, filesPartition) {
					const instance = this;

					const folderId = instance._getTargetFolderId(target);

					const folder = folderId !== instance.get(STR_FOLDER_ID);

					if (folder) {
						const folderEntryNode = instance._getFolderEntryNode(
							target
						);

						const folderEntryNodeOverlay = folderEntryNode.overlay;

						if (folderEntryNodeOverlay) {
							instance._updateProgress(
								folderEntryNode.progressBar,
								0
							);

							folderEntryNodeOverlay.show();
						}
						else {
							instance._createUploadStatus(folderEntryNode);
						}

						folderEntryNode.removeClass(CSS_ACTIVE_AREA);
					}
					else {
						const displayStyle = instance._getDisplayStyle();

						filesPartition.matches.map((file) => {
							const entryNode = instance._createEntryNode(
								file.name,
								file.size,
								displayStyle
							);

							instance._createUploadStatus(entryNode, file);
						});
					}
				},

				_updateThumbnail(node, fileName, displayStyle) {
					const instance = this;

					let imageNode = node.one('img');
					const thumbnailPath = instance._getImageThumbnail(fileName);

					if (!imageNode) {
						let targetNodeSelector = '.sticker-overlay svg';
						let imageClassName = 'sticker-img';

						if (displayStyle === CSS_ICON) {
							targetNodeSelector = '.card-type-asset-icon';
							imageClassName =
								'aspect-ratio-item-center-middle aspect-ratio-item-fluid';
						}

						imageNode = A.Node.create(
							`<img alt="" class="${imageClassName}" src="${thumbnailPath}" />`
						);

						const targetNode = node.one(targetNodeSelector);

						targetNode
							.get('parentNode')
							.replaceChild(imageNode, targetNode);
					}
					else {
						imageNode.attr('src', thumbnailPath);
					}
				},

				_validateFiles(data) {
					const instance = this;

					const maxFileSize = instance._maxFileSize;

					return AArray.partition(data, (item) => {
						let errorMessage;

						const size = item.get(STR_SIZE) || 0;

						const strings = instance._strings;

						if (maxFileSize !== 0 && size > maxFileSize) {
							errorMessage = sub(strings.invalidFileSize, [
								Liferay.Util.formatStorage(
									instance._maxFileSize
								),
							]);
						}
						else if (size === 0) {
							errorMessage = strings.zeroByteFile;
						}

						item.errorMessage = errorMessage;
						item.size = size;
						item.name = item.get(STR_NAME);

						return !errorMessage;
					});
				},

				destructor() {
					const instance = this;

					if (instance._dataSet) {
						instance._dataSet.destroy();
					}

					if (instance._navigationOverlays) {
						AArray.invoke(instance._navigationOverlays, 'destroy');
					}

					if (instance._uploader) {
						instance._uploader.destroy();
					}

					instance._detachSubscriptions();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					const appViewEntryTemplates = A.one(
						instance.get('appViewEntryTemplates')
					);

					instance._columnNames = instance.get('columnNames');
					instance._displayStyle = instance.get('displayStyle');
					instance._documentLibraryNamespace = instance.get(
						'documentLibraryNamespace'
					);
					instance._entriesContainer = A.one(
						instance.get('entriesContainer')
					);
					instance._maxFileSize = instance.get('maxFileSize');
					instance._scopeGroupId = instance.get('scopeGroupId');

					instance._handles = [];

					instance._invisibleDescriptiveEntry = appViewEntryTemplates.one(
						SELECTOR_ENTRY_DISPLAY_STYLE +
							SELECTOR_DISPLAY_DESCRIPTIVE
					);
					instance._invisibleIconEntry = appViewEntryTemplates.one(
						SELECTOR_ENTRY_DISPLAY_STYLE + SELECTOR_DISPLAY_ICON
					);

					instance._strings = {
						invalidFileSize: Liferay.Language.get(
							'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
						),
						multipleFilesUploaded: Liferay.Language.get(
							'x-files-were-uploaded'
						),
						pageRefreshNeeded: Liferay.Language.get(
							'page-refresh-needed'
						),
						reloadButton: Liferay.Language.get('reload'),
						singleFileUploaded: Liferay.Language.get(
							'x-was-uploaded'
						),
						xInvalidFilesUploaded: Liferay.Language.get(
							'x-files-could-not-be-uploaded'
						),
						zeroByteFile: Liferay.Language.get(
							'the-file-contains-no-data-and-cannot-be-uploaded.-please-use-the-classic-uploader'
						),
					};

					instance._bindDragDropUI();
				},
			},
		});

		Liferay.DocumentLibraryUploadComponent = DocumentLibraryUpload;
	},
	'',
	{
		requires: [
			'aui-component',
			'aui-data-set-deprecated',
			'aui-overlay-manager-deprecated',
			'aui-overlay-mask-deprecated',
			'aui-parse-content',
			'aui-progressbar',
			'aui-template-deprecated',
			'liferay-search-container',
			'querystring-parse-simple',
			'uploader',
		],
	}
);
