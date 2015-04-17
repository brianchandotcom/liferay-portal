/* global React, AlloyEditor */

(function() {
	'use strict';

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
				var editor = this.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectDocument';

				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							destroyOnHide: true,
							modal: true
						},
						eventName: eventName,
						id: eventName,
						title: Liferay.Language.get('select-image'),
						uri: editor.config.filebrowserImageBrowseUrl
					},
					this._onDocumentSelected
				);
			},

			_onDocumentSelected: function(event) {
				var instance = this;

				var editor = instance.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectDocument';

				Liferay.Util.getWindow(eventName).onceAfter(
					'visibleChange',
					function() {
						var image = CKEDITOR.dom.element.createFromHtml(
							instance.props.imageTPL.output(
								{
									src: event.url
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