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
	'liferay-document-library',
	A => {
		var Lang = A.Lang;

		var WIN = A.config.win;

		var HTML5_UPLOAD =
			WIN && WIN.File && WIN.FormData && WIN.XMLHttpRequest;

		var TPL_MOVE_FORM =
			'<form action="{actionUrl}" method="POST"><input name="{namespace}cmd" value="move"/>' +
			'<input name="{namespace}newFolderId" value="{newFolderId}"/>' +
			'<input name="{namespace}{parameterName}" value="{parameterValue}"/>' +
			'<input name="{namespace}redirect" value="{redirectUrl}"/>' +
			'</form>';

		var DocumentLibrary = A.Component.create({
			ATTRS: {
				downloadEntryUrl: {
					validator: Lang.isString
				},

				editEntryUrl: {
					validator: Lang.isString
				},

				form: {
					validator: Lang.isObject
				},

				openViewMoreFileEntryTypesURL: {
					validator: Lang.isString
				},

				searchContainerId: {
					validator: Lang.isString
				},

				selectFileEntryTypeURL: {
					validator: Lang.isString
				},

				selectFolderURL: {
					validator: Lang.isString
				},

				trashEnabled: {
					validator: Lang.isBoolean
				},

				viewFileEntryURL: {
					validator: Lang.isString
				}
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'documentlibrary',

			prototype: {
				_handleSearchContainerRowToggled(event) {
					var instance = this;

					var selectedElements = event.elements.allSelectedElements;

					if (selectedElements.size() > 0) {
						instance._selectedFileEntries = selectedElements.attr(
							'value'
						);
					} else {
						instance._selectedFileEntries = [];
					}

					var bulkSelection =
						instance._searchContainer.select &&
						instance._searchContainer.select.get('bulkSelection');

					var form = instance.get('form').node;

					form.get(instance.NS + 'selectAll').val(bulkSelection);
				},

				_moveCurrentSelection(newFolderId) {
					var instance = this;

					var form = instance.get('form').node;

					var actionUrl = instance.get('editEntryUrl');

					form.attr('action', actionUrl);
					form.attr('method', 'POST');
					form.attr('enctype', 'multipart/form-data');

					form.get(instance.NS + 'cmd').val('move');
					form.get(instance.NS + 'newFolderId').val(newFolderId);

					submitForm(form, actionUrl, false);
				},

				_moveSingleElement(newFolderId, parameterName, parameterValue) {
					var instance = this;

					var actionUrl = instance.get('editEntryUrl');
					var namespace = instance.NS;
					var originalForm = instance.get('form').node;
					var redirectUrl = originalForm
						.get(namespace + 'redirect')
						.val();

					var formNode = A.Node.create(
						A.Lang.sub(TPL_MOVE_FORM, {
							actionUrl,
							namespace,
							newFolderId,
							parameterName,
							parameterValue,
							redirectUrl
						})
					);

					submitForm(formNode, actionUrl, false);
				},

				_moveToFolder(obj) {
					var instance = this;

					var dropTarget = obj.targetItem;

					var selectedItems = obj.selectedItems;

					var folderId = dropTarget.attr('data-folder-id');

					if (folderId) {
						if (
							!instance._searchContainer.select ||
							selectedItems.indexOf(
								dropTarget.one('input[type=checkbox]')
							)
						) {
							instance._moveCurrentSelection(folderId);
						}
					}
				},

				_moveToTrash() {
					var instance = this;

					instance._processAction(
						'move_to_trash',
						instance.get('editEntryUrl')
					);
				},

				_openDocument(event) {
					var instance = this;

					Liferay.Util.openDocument(
						event.webDavUrl,
						null,
						exception => {
							var errorMessage = Lang.sub(
								Liferay.Language.get(
									'cannot-open-the-requested-document-due-to-the-following-reason'
								),
								[exception.message]
							);

							var openMSOfficeError = instance.ns(
								'openMSOfficeError'
							);

							if (openMSOfficeError) {
								openMSOfficeError.setHTML(errorMessage);

								openMSOfficeError.removeClass('hide');
							}
						}
					);
				},

				_openModalCategories() {
					var instance = this;

					Liferay.componentReady(
						instance.NS + 'EditCategoriesComponent'
					).then(editCategoriesComponent => {
						var bulkSelection =
							instance._searchContainer.select &&
							instance._searchContainer.select.get(
								'bulkSelection'
							);

						editCategoriesComponent.open(
							instance._selectedFileEntries,
							bulkSelection,
							instance.getFolderId()
						);
					});
				},

				_openModalMove() {
					var instance = this;

					var selectedItems = 0;

					if (instance._searchContainer.select) {
						selectedItems = instance._searchContainer.select
							.getAllSelectedElements()
							.filter(':enabled')
							.size();
					}

					this.showFolderDialog(selectedItems);
				},

				_openModalTags() {
					var instance = this;

					Liferay.componentReady(
						instance.NS + 'EditTagsComponent'
					).then(editTagsComponent => {
						var bulkSelection =
							instance._searchContainer.select &&
							instance._searchContainer.select.get(
								'bulkSelection'
							);

						editTagsComponent.open(
							instance._selectedFileEntries,
							bulkSelection,
							instance.getFolderId()
						);
					});
				},

				_plugUpload(event, config) {
					var instance = this;

					instance.plug(Liferay.DocumentLibraryUpload, {
						appViewEntryTemplates: config.appViewEntryTemplates,
						columnNames: config.columnNames,
						dimensions: config.folders.dimensions,
						displayStyle: config.displayStyle,
						entriesContainer: instance._entriesContainer,
						folderId: instance._folderId,
						maxFileSize: config.maxFileSize,
						redirect: config.redirect,
						scopeGroupId: config.scopeGroupId,
						uploadURL: config.uploadURL,
						viewFileEntryURL: config.viewFileEntryURL
					});
				},

				_processAction(action, url, redirectUrl) {
					var instance = this;

					var namespace = instance.NS;

					var form = instance.get('form').node;

					redirectUrl = redirectUrl || location.href;

					form.attr('method', instance.get('form').method);

					if (form.get(namespace + 'javax-portlet-action')) {
						form.get(namespace + 'javax-portlet-action').val(
							action
						);
					} else {
						form.get(namespace + 'cmd').val(action);
					}

					form.get(namespace + 'redirect').val(redirectUrl);

					submitForm(form, url, false);
				},

				destructor() {
					var instance = this;

					A.Array.invoke(instance._eventHandles, 'detach');

					instance._documentLibraryContainer.purge(true);
				},

				getFolderId() {
					var instance = this;

					return instance._folderId;
				},

				handleActionItemClicked(event) {
					var instance = this;

					var action = event.data.item.data.action;

					var namespace = instance.NS;

					var url = instance.get('editEntryUrl');

					if (action === 'editTags') {
						instance._openModalTags();

						action = null;
					} else if (action === 'editCategories') {
						instance._openModalCategories();

						action = null;
					} else if (action === 'move' || action === 'moveEntries') {
						instance._openModalMove();

						action = null;
					} else if (action === 'download') {
						url = instance.get('downloadEntryUrl');
					} else if (action === 'deleteEntries') {
						if (instance.get('trashEnabled')) {
							action = 'move_to_trash';
						} else if (
							confirm(
								Liferay.Language.get(
									'are-you-sure-you-want-to-delete-the-selected-entries'
								)
							)
						) {
							action = 'delete';
						} else {
							action = null;
						}
					} else if (action === 'checkin') {
						Liferay.DocumentLibraryCheckin.showDialog(
							namespace,
							(versionIncrease, changeLog) => {
								var form = instance.get('form').node;

								form.get(namespace + 'changeLog').val(
									changeLog
								);
								form.get(namespace + 'versionIncrease').val(
									versionIncrease
								);

								instance._processAction('checkin', url);
							}
						);
						action = null;
					}

					if (action) {
						instance._processAction(action, url);
					}
				},

				handleCreationMenuMoreButtonClicked(event) {
					event.preventDefault();

					var instance = this;

					Liferay.Util.openWindow({
						dialog: {
							destroyOnHide: true,
							modal: true
						},
						id: instance.ns('selectAddMenuItem'),
						title: Liferay.Language.get('more'),
						uri: instance.get('openViewMoreFileEntryTypesURL')
					});
				},

				handleFilterItemClicked(event) {
					var instance = this;

					var itemData = event.data.item.data;

					if (itemData.action === 'openDocumentTypesSelector') {
						Liferay.Loader.require(
							'frontend-js-web/liferay/ItemSelectorDialog.es',
							ItemSelectorDialog => {
								var itemSelectorDialog = new ItemSelectorDialog.default(
									{
										eventName: instance.ns(
											'selectFileEntryType'
										),
										singleSelect: true,
										title: Liferay.Language.get(
											'select-document-type'
										),
										url: instance.get(
											'selectFileEntryTypeURL'
										)
									}
								);

								itemSelectorDialog.open();

								itemSelectorDialog.on(
									'selectedItemChange',
									event => {
										var selectedItem = event.selectedItem;

										if (selectedItem) {
											var uri = instance.get(
												'viewFileEntryTypeURL'
											);

											uri = Liferay.Util.addParams(
												instance.ns(
													'fileEntryTypeId='
												) + selectedItem,
												uri
											);

											location.href = uri;
										}
									}
								);
							}
						);
					}
				},

				initializer(config) {
					var instance = this;

					var eventHandles = [];

					var documentLibraryContainer = instance.byId(
						'documentLibraryContainer'
					);

					instance._documentLibraryContainer = documentLibraryContainer;

					instance._eventDataRequest = instance.ns('dataRequest');
					instance._entriesContainer = instance.byId(
						'entriesContainer'
					);

					var namespace = instance.NS;

					var searchContainer = Liferay.SearchContainer.get(
						namespace + instance.get('searchContainerId')
					);

					searchContainer.registerAction(
						'move-to-folder',
						A.bind('_moveToFolder', instance)
					);
					searchContainer.registerAction(
						'move-to-trash',
						A.bind('_moveToTrash', instance)
					);
					eventHandles.push(
						searchContainer.on(
							'rowToggled',
							this._handleSearchContainerRowToggled,
							this
						)
					);

					instance._searchContainer = searchContainer;

					var foldersConfig = config.folders;

					instance._folderId = foldersConfig.defaultParentFolderId;

					instance._config = config;

					if (
						config.uploadable &&
						HTML5_UPLOAD &&
						themeDisplay.isSignedIn() &&
						instance._entriesContainer.inDoc()
					) {
						config.appViewEntryTemplates = instance.byId(
							'appViewEntryTemplates'
						);

						eventHandles.push(
							A.getDoc().once(
								'dragenter',
								instance._plugUpload,
								instance,
								config
							)
						);
					}

					instance._eventHandles = eventHandles;
				},

				showFolderDialog(selectedItems, parameterName, parameterValue) {
					var instance = this;

					var namespace = instance.NS;

					var dialogTitle = '';

					if (selectedItems == 1) {
						dialogTitle = Liferay.Language.get(
							'select-destination-folder-for-x-item'
						);
					} else {
						dialogTitle = Liferay.Language.get(
							'select-destination-folder-for-x-items'
						);
					}

					Liferay.Util.selectEntity(
						{
							dialog: {
								constrain: true,
								destroyOnHide: true,
								modal: true,
								width: 680
							},
							id: namespace + 'selectFolder',
							title: Lang.sub(dialogTitle, [selectedItems]),
							uri: instance.get('selectFolderURL')
						},
						event => {
							if (parameterName && parameterValue) {
								instance._moveSingleElement(
									event.folderid,
									parameterName,
									parameterValue
								);
							} else {
								instance._moveCurrentSelection(event.folderid);
							}
						}
					);
				}
			}
		});

		Liferay.Portlet.DocumentLibrary = DocumentLibrary;
	},
	'',
	{
		requires: [
			'document-library-upload',
			'liferay-message',
			'liferay-portlet-base'
		]
	}
);
