var React = AlloyEditor.React;
var ReactDOM = AlloyEditor.ReactDOM;

/**
 * The AlignImageCenter class provides functionality for aligning an image in the center.
 *
 * @class AlignImageCenter
 * @uses ButtonCommand
 * @uses ButtonCommandActive
 * @uses ButtonStateClasses
 */
var AlignImageCenter = React.createClass({
	mixins: [AlloyEditor.ButtonStateClasses, AlloyEditor.ButtonCommand, AlloyEditor.ButtonCommandActive],

	// Allows validating props being passed to the component.
	propTypes: {
		/**
		 * The editor instance where the component is being used.
		 *
		 * @instance
		 * @memberof AlignImageCenter
		 * @property {Object} editor
		 */
		editor: React.PropTypes.object.isRequired,

		/**
		 * The label that should be used for accessibility purposes.
		 *
		 * @instance
		 * @memberof AlignImageCenter
		 * @property {String} label
		 */
		label: React.PropTypes.string,

		/**
		 * The tabIndex of the button in its toolbar current state. A value other than -1
		 * means that the button has focus and is the active element.
		 *
		 * @instance
		 * @memberof AlignImageCenter
		 * @property {Number} tabIndex
		 */
		tabIndex: React.PropTypes.number
	},

	// Lifecycle. Provides static properties to the widget.
	statics: {
		/**
		 * The name which will be used as an alias of the button in the configuration.
		 *
		 * @default imageCenter
		 * @memberof AlignImageCenter
		 * @property {String} key
		 * @static
		 */
		key: 'imageCenter'
	},

	/**
	 * Lifecycle. Returns the default values of the properties used in the widget.
	 *
	 * @instance
	 * @memberof AlignImageCenter
	 * @method getDefaultProps
	 * @return {Object} The default properties.
	 */
	getDefaultProps: function() {
		return {
			command: 'justifycenter'
		};
	},

	/**
	 * Lifecycle. Renders the UI of the button.
	 *
	 * @instance
	 * @memberof AlignImageCenter
	 * @method render
	 * @return {Object} The content which should be rendered.
	 */
	render: function() {
		var cssClass = 'ae-button ' + this.getStateClasses();

		return (
			<button aria-label={AlloyEditor.Strings.alignCenter} aria-pressed={cssClass.indexOf('pressed') !== -1} className={cssClass} data-type="button-image-align-center" onClick={this.execCommand} tabIndex={this.props.tabIndex} title={AlloyEditor.Strings.alignCenter}>
				<svg className="lexicon-icon lexicon-icon-align-image-center" focusable="false" role="image">
					<use data-href={themeDisplay.getPathThemeImages() + '/lexicon/icons.svg#align-image-center'} />
					<title>align-image-center</title>
				</svg>
			</button>
		);
	}
});

AlloyEditor.Buttons[AlignImageCenter.key] = AlloyEditor.AlignImageCenter = AlignImageCenter;

export default AlignImageCenter;
export {AlignImageCenter};