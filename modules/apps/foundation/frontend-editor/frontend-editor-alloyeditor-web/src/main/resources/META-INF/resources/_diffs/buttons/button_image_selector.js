/* global React, AlloyEditor */

(function() {
	'use strict';

	var React = AlloyEditor.React;

	var ButtonImage = React.createClass(
		{
			mixins: [AlloyEditor.ButtonCommand],

			displayName: 'ButtonImage',

			propTypes: {
				editor: React.PropTypes.object.isRequired,
				imageTPL: React.PropTypes.string
			},

			getDefaultProps: function() {
				return {
					command: 'imageselector'
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

			_handleClick: function() {
				this.execCommand(null);
			}
		}
	);

	AlloyEditor.Buttons[ButtonImage.key] = AlloyEditor.ButtonImage = ButtonImage;
}());