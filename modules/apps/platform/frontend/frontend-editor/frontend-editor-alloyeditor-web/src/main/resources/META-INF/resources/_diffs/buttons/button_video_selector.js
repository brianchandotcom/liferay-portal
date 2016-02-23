/* global React, AlloyEditor */

(function() {
	'use strict';

	var React = AlloyEditor.React;

	var ButtonVideo = React.createClass(
		{
			mixins: [AlloyEditor.ButtonCommand],

			displayName: 'ButtonVideo',

			propTypes: {
				editor: React.PropTypes.object.isRequired
			},

			getDefaultProps: function() {
				return {
					command: 'videoselector'
				};
			},

			statics: {
				key: 'video'
			},

			render: function() {
				return React.createElement(
					'button',
					{
						className: 'ae-button',
						'data-type': 'button-video',
						onClick: this._handleClick,
						tabIndex: this.props.tabIndex
					},
					React.createElement(
						'span',
						{
							className: 'icon-film'
						}
					)
				);
			},

			_handleClick: function() {
				this.execCommand(null);
			}
		}
	);

	AlloyEditor.Buttons[ButtonVideo.key] = AlloyEditor.ButtonVideo = ButtonVideo;
}());