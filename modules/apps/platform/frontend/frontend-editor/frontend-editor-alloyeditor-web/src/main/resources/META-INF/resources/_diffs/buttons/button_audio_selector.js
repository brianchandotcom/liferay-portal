/* global React, AlloyEditor */

(function() {
	'use strict';

	var React = AlloyEditor.React;

	var ButtonAudio = React.createClass(
		{

			mixins: [AlloyEditor.ButtonCommand],

			displayName: 'ButtonAudio',

			propTypes: {
				editor: React.PropTypes.object.isRequired
			},
			getDefaultProps: function() {
				return {
					command: 'audioselector'
				};
			},

			statics: {
				key: 'audio'
			},

			render: function() {
				return React.createElement(
					'button',
					{
						className: 'ae-button',
						'data-type': 'button-audio',
						onClick: this._handleClick,
						tabIndex: this.props.tabIndex
					},
					React.createElement(
						'span',
						{
							className: 'icon-headphones'
						}
					)
				);
			},

			_handleClick: function() {
				this.execCommand(null);

			}
		}
	);

	AlloyEditor.Buttons[ButtonAudio.key] = AlloyEditor.ButtonAudio = ButtonAudio;
}());