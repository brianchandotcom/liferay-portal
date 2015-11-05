(function() {
	var pluginName = 'itemselector';

	var STR_FILE_ENTRY_RETURN_TYPE = 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType';

	var STR_UPLOADABLE_FILE_RETURN_TYPE = 'com.liferay.item.selector.criteria.UploadableFileReturnType';

	CKEDITOR.plugins.add(
		pluginName,
		{
			init: function(editor) {
				var instance = this;

				editor.addCommand(
					pluginName,
					{
						canUndo: false,
						exec: function(editor, callback) {
							var _onSelectedItemChange = function(event) {
								var selectedItem = event.newVal;

								if (selectedItem) {
									var imageSrc = selectedItem.value;

									if (selectedItem.returnType === STR_FILE_ENTRY_RETURN_TYPE ||
										selectedItem.returnType === STR_UPLOADABLE_FILE_RETURN_TYPE) {
										try {
											var itemValue = JSON.parse(selectedItem.value);

											imageSrc = editor.config.attachmentURLPrefix ? editor.config.attachmentURLPrefix + itemValue.title : itemValue.url;
										}
										catch (e) {
										}
									}

									if (imageSrc) {
										if (callback) {
											callback(imageSrc);
										}
										else {
											var el = CKEDITOR.dom.element.createFromHtml('<img src="' + imageSrc + '">');

											editor.insertElement(el);
										}
									}
								}
							};

							instance._getItemSelectorDialog(
								editor,
								function(itemSelectorDialog) {
									itemSelectorDialog.once('selectedItemChange', _onSelectedItemChange);
									itemSelectorDialog.open();
								}
							);
						}
					}
				);

				if (editor.ui.addButton) {
					editor.ui.addButton(
						'ImageSelector',
						{
							command: pluginName,
							icon: instance.path + 'assets/image.png',
							label: editor.lang.common.image
						}
					);
				}

				CKEDITOR.on(
					'dialogDefinition',
					function(event) {
						var dialogName = event.data.name;

						if (dialogName === 'image') {
							var dialogDefinition = event.data.definition;

							instance._bindBrowseButton(editor, dialogDefinition, 'info');
							instance._bindBrowseButton(editor, dialogDefinition, 'Link');
						}
					}
				);

				editor.once(
					'destroy',
					function() {
						if (instance._itemSelectorDialog) {
							instance._itemSelectorDialog.destroy();
						}
					}
				);
			},

			_bindBrowseButton: function(editor, dialogDefinition, tabName) {
				var tab = dialogDefinition.getContents(tabName);

				if (tab) {
					var browseButton = tab.get('browse');

					if (browseButton) {
						browseButton.onClick = function() {
							editor.execCommand(
								pluginName,
								function(newVal) {
									dialogDefinition.dialog.setValueOf(tabName, 'txtUrl', newVal);
								}
							);
						};
					}
				}
			},

			_getItemSelectorDialog: function(editor, callback) {
				var instance = this;

				var itemSelectorDialog = instance._itemSelectorDialog;

				if (itemSelectorDialog) {
					itemSelectorDialog.set('zIndex', CKEDITOR.getNextZIndex());

					callback(itemSelectorDialog);
				}
				else {
					AUI().use(
						'liferay-item-selector-dialog',
						function(A) {
							var eventName = editor.name + 'selectItem';

							itemSelectorDialog = new A.LiferayItemSelectorDialog(
								{
									eventName: eventName,
									url: editor.config.filebrowserImageBrowseUrl,
									zIndex: CKEDITOR.getNextZIndex()
								}
							);

							instance._itemSelectorDialog = itemSelectorDialog;

							callback(itemSelectorDialog);
						}
					);
				}
			}
		}
	);
})();