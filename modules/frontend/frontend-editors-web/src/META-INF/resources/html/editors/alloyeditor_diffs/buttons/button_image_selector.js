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

			_handleClick: function() {
				var instance = this;

				var editor = this.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectDocument';

				AUI().use(
					'liferay-item-selector-dialog',
					function(A) {
						var dialog = new A.LiferayItemSelectorDialog(
							{
								url: editor.config.filebrowserImageBrowseUrl,
								eventName: eventName
							}
						);

						dialog.on('itemSelected', instance._onDocumentSelected);
					}
				);
			},

			_onDocumentSelected: function(item) {
				var instance = this;

				var editor = instance.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectDocument';

				Util.getWindow(eventName).onceAfter(
					'visibleChange',
					function() {
						var image = CKEDITOR.dom.element.createFromHtml(
							instance.props.imageTPL.output(
								{
									src: item.value
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