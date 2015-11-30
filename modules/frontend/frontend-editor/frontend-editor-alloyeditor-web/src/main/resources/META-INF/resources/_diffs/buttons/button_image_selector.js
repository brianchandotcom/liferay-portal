/* global React, AlloyEditor */

(function() {
	'use strict';

	var STR_FILE_ENTRY_RETURN_TYPE = 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType';

	var STR_UPLOADABLE_FILE_RETURN_TYPE = 'com.liferay.item.selector.criteria.UploadableFileReturnType';

	var Util = Liferay.Util;

	var React = AlloyEditor.React;

	var ButtonImage = React.createClass(
		{
			displayName: 'ButtonImage',

			propTypes: {
				editor: React.PropTypes.object.isRequired,
				imageTPL: React.PropTypes.string
			},

			getDefaultProps: function() {
				return {
					imageTPL: new CKEDITOR.template('<img src="{src}" />')
				};
			},

			statics: {
				key: 'image'
			},

			render: function() {
				return React.createElement(
					'button',
					{
						className: 'ae-button',
						'data-type': 'button-image',
						onClick: this._handleClick,
						tabIndex: this.props.tabIndex
					},
					React.createElement(
						'span',
						{
							className: 'ae-icon-image'
						}
					)
				);
			},

			_destroyItemSelectorDialog: function() {
				var instance = this;

				if (instance._itemSelectorDialog) {
					setTimeout(
						function() {
							instance._itemSelectorDialog.destroy();
						},
						0
					);
				}
			},

			_handleClick: function() {
				var instance = this;

				var editor = this.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectItem';

				if (instance._itemSelectorDialog) {
					instance._itemSelectorDialog.open();
				}
				else {
					AUI().use(
						'liferay-item-selector-dialog',
						function(A) {
							var itemSelectorDialog = new A.LiferayItemSelectorDialog(
								{
									after: {
										selectedItemChange: A.bind('_onSelectedItemChange', instance)
									},
									eventName: eventName,
									url: editor.config.filebrowserImageBrowseUrl
								}
							);

							itemSelectorDialog.open();

							instance._itemSelectorDialog = itemSelectorDialog;
						}
					);
				}
			},

			_onSelectedItemChange: function(event) {
				var instance = this;

				var editor = instance.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectItem';

				var selectedItem = event.newVal;

				if (selectedItem) {
					Util.getWindow(eventName).onceAfter(
						'destroy',
						function() {
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
								var el = CKEDITOR.dom.element.createFromHtml(
									instance.props.imageTPL.output(
										{
											src: imageSrc
										}
									)
								);

								editor.insertElement(el);
							}
						}
					);
				}

				instance._destroyItemSelectorDialog();
			}
		}
	);

	AlloyEditor.Buttons[ButtonImage.key] = AlloyEditor.ButtonImage = ButtonImage;
}());