/* global React, AlloyEditor */

(function() {
	'use strict';

	var Util = Liferay.Util;

	var ButtonImage = React.createClass(
		{
			displayName: 'ButtonImage',

			propTypes: {
				editor: React.PropTypes.object.isRequired,
				imageTPL: React.PropTypes.string
			},

			componentWillUnmount: function() {
				var instance = this;

				if (instance._itemSelectorDialog) {
					instance._itemSelectorDialog.destroy();
				}
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
						className: 'alloy-editor-button',
						'data-type': 'button-image',
						onClick: this._handleClick,
						tabIndex: this.props.tabIndex
					},
					React.createElement(
						'span',
						{
							className: 'alloy-editor-icon-image'
						}
					)
				);
			},

			_createEl: function(imageSrc) {
				var instance = this;

				var editor = instance.props.editor.get('nativeEditor');

				var el = CKEDITOR.dom.element.createFromHtml(
					instance.props.imageTPL.output(
						{
							src: imageSrc
						}
					)
				);

				editor.insertElement(el);

				return el;
			},

			_handleClick: function() {
				var instance = this;

				var editor = this.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectDocument';

				if (instance._itemSelectorDialog) {
					instance._itemSelectorDialog.open();
				}
				else {
					AUI().use(
						'liferay-item-selector-dialog',
						'liferay-file-uploader',
						function(A) {
							var itemSelectorDialog = new A.LiferayItemSelectorDialog(
								{
									after: {
										selectedItemChange: A.bind('_onSelectedItemChange', instance),
										selectedItemUploadStart: A.bind('_onSelectedItemUploadStart', instance)
									},
									eventName: eventName,
									plugins: [A.Plugin.LiferayFileUploader],
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

				var eventName = editor.name + 'selectDocument';

				var selectedItem = event.newVal;

				if (selectedItem) {
					Util.getWindow(eventName).onceAfter(
						'visibleChange',
						function() {
							instance._createEl(selectedItem.value);
						}
					);
				}
			},

			_onSelectedItemUploadStart: function(event) {
				var instance = this;

				var editor = instance.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectDocument';

				var uploadableItem = event.data;

				var uploadableItemValue = uploadableItem.value;

				Util.getWindow(eventName).onceAfter(
					'visibleChange',
					function() {
						var el = instance._createEl(uploadableItemValue.base64);

						editor.fire(
							'imagedrop',
							{
								el: el,
								file: uploadableItemValue.file,
								randomId: uploadableItemValue.id,
								uploader: uploadableItem.uploader
							}
						);
					}
				);
			}
		}
	);

	AlloyEditor.Buttons[ButtonImage.key] = AlloyEditor.ButtonImage = ButtonImage;
}());