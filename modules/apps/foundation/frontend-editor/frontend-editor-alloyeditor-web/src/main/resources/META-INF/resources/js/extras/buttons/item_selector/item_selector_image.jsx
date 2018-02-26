var React = AlloyEditor.React;

var ItemSelectorImage = React.createClass(
	{
		mixins: [AlloyEditor.ButtonCommand],

		displayName: 'ItemSelectorImage',

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
			return (
				<button className="ae-button" data-type="button-image" onClick={this._handleClick} tabIndex={this.props.tabIndex}>
					<span className="ae-icon-image"></span>
				</button>
			);
		},

		_handleClick: function() {
			this.execCommand(null);
		}
	}
);

export default ItemSelectorImage;
export {ItemSelectorImage};