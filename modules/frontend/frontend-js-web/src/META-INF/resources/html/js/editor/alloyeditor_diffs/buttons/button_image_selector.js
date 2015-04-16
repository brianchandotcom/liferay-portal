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
					imageTPL: CKEDITOR.template('<img src="{src}" />')
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
					this._onDocumentSelectedFn
				);
			},

			_onDocumentSelected: function(event) {
				var instance = this;

				var image = CKEDITOR.dom.element.createFromHtml(
					this.props.imageTPL.output(
						{
							src: event.url
						}
					)
				);

				this.props.editor.get('nativeEditor').insertElement(image);
			}
		}
	);

	AlloyEditor.Buttons[ButtonImage.key] = AlloyEditor.ButtonImage = ButtonImage;
}());