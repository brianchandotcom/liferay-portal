var React = AlloyEditor.React;

var ItemSelectorVideo = React.createClass(
	{
		mixins: [AlloyEditor.ButtonCommand],

		displayName: 'ItemSelectorVideo',

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
			return (
				<button className="ae-button" data-type="button-video" onClick={this._handleClick} tabIndex={this.props.tabIndex}>
					<span className="ae-icon icon-film"></span>
				</button>
			);
		},

		_handleClick: function() {
			this.execCommand(null);
		}
	}
);

export default ItemSelectorVideo;
export {ItemSelectorVideo};