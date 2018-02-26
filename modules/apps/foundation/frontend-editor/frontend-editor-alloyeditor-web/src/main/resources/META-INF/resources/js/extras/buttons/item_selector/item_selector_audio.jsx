var React = AlloyEditor.React;

var ItemSelectorAudio = React.createClass(
	{

		mixins: [AlloyEditor.ButtonCommand],

		displayName: 'ItemSelectorAudio',

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
			return (
				<button className="ae-button" data-type="button-audio" onClick={this._handleClick} tabIndex={this.props.tabIndex}>
					<span className="ae-icon icon-headphones">Alt</span>
				</button>
			);
		},

		_handleClick: function() {
			this.execCommand(null);

		}
	}
);

export default ItemSelectorAudio;
export {ItemSelectorAudio};