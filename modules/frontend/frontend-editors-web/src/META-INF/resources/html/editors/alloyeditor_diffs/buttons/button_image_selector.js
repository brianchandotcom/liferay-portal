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

			getDefaultProps: function() {
				return {
					imageTPL: new CKEDITOR.template('<img src="{src}" />')
				};
			},

			statics: {
				key: 'image'
			},

			render: function() {
				return (
					React.createElement(
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
					)
				);
			},

			_closeDialog: function(dialogId) {
				var instance = this;

				var dialog = Util.getWindow(dialogId);

				dialog.hide();

				instance._selectedItem = null;
			},

			_handleClick: function() {
				var instance = this;

				var editor = this.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectDocument';

				Util.selectEntity(
					{
						dialog: {
							constrain: true,
							destroyOnHide: true,
							modal: true,
							'toolbars.footer': [
								{
									cssClass: 'btn-primary',
									disabled: true,
									id: 'addButton',
									label: Liferay.Language.get('add'),
									on: {
										click: function() {
											instance._onDocumentSelected(instance._selectedItem);

											instance._closeDialog(eventName);
										}
									}
								},
								{
									id: 'cancelButton',
									label: Liferay.Language.get('cancel'),
									on: {
										click: function() {
											instance._closeDialog(eventName);
										}
									}
								}
							]
						},
						eventName: eventName,
						id: eventName,
						title: Liferay.Language.get('select-image'),
						uri: editor.config.filebrowserImageBrowseUrl
					},
					instance._onDocumentPicked
				);
			},

			_onDocumentPicked: function(event) {
				var instance = this;

				instance._selectedItem = event.value;

				var editor = this.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectDocument';

				var dialog = Util.getWindow(eventName);

				var addButton = dialog.getToolbar('footer').get('boundingBox').one('#addButton');

				Util.toggleDisabled(addButton, !instance._selectedItem);
			},

			_onDocumentSelected: function(itemSrc) {
				var instance = this;

				var editor = instance.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectDocument';

				Util.getWindow(eventName).onceAfter(
					'visibleChange',
					function() {
						var image = CKEDITOR.dom.element.createFromHtml(
							instance.props.imageTPL.output(
								{
									src: itemSrc
								}
							)
						);

						editor.insertElement(image);
					}
				);
			}
		}
	);

	AlloyEditor.Buttons[ButtonImage.key] = AlloyEditor.ButtonImage = ButtonImage;
}());