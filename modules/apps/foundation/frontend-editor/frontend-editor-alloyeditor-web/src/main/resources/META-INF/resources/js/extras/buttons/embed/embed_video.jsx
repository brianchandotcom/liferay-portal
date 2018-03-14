var React = AlloyEditor.React;

/**
 * The EmbedVideo class provides functionality for creating and editing a table in a document. EmbedVideo
 * renders in two different modes:
 *
 * - Normal: Just a button that allows to switch to the edition mode
 * - Exclusive: The EmbedVideoEdit UI with all the table edition controls.
 *
 * @class EmbedVideo
 */
var EmbedVideo = React.createClass({
	mixins: [AlloyEditor.ButtonCfgProps],

	// Allows validating props being passed to the component.
	propTypes: {
		/**
		 * The editor instance where the component is being used.
		 *
		 * @instance
		 * @memberof EmbedVideo
		 * @property {Object} editor
		 */
		editor: React.PropTypes.object.isRequired,

		/**
		 * The label that should be used for accessibility purposes.
		 *
		 * @instance
		 * @memberof EmbedVideo
		 * @property {String} label
		 */
		label: React.PropTypes.string,

		/**
		 * The tabIndex of the button in its toolbar current state. A value other than -1
		 * means that the button has focus and is the active element.
		 *
		 * @instance
		 * @memberof EmbedVideo
		 * @property {Number} tabIndex
		 */
		tabIndex: React.PropTypes.number
	},

	// Lifecycle. Provides static properties to the widget.
	statics: {
		/**
		 * The name which will be used as an alias of the button in the configuration.
		 *
		 * @default table
		 * @memberof EmbedVideo
		 * @property {String} key
		 * @static
		 */
		key: 'embedVideo'
	},

	/**
	 * Lifecycle. Renders the UI of the button.
	 *
	 * @instance
	 * @memberof EmbedVideo
	 * @method render
	 * @return {Object} The content which should be rendered.
	 */
	render: function() {
		if (this.props.renderExclusive) {
			return (
				<AlloyEditor.EmbedVideoEdit {...this.props} />
			);
		} else {
			var svg = Liferay.Util.getLexiconIconTpl('video');

			return (
				<button className="ae-button" data-type="button-embed-video" onClick={this.props.requestExclusive} tabIndex={this.props.tabIndex} dangerouslySetInnerHTML={{__html: svg}} />
			);
		}
	}
});

export default EmbedVideo;
export {EmbedVideo};