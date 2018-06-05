var React = AlloyEditor.React;
var ReactDOM = AlloyEditor.ReactDOM;

/**
 * The AlignImageRight class provides functionality for aligning an image on the right.
 *
 * @class AlignImageRight
 * @uses ButtonCommand
 * @uses ButtonCommandActive
 * @uses ButtonStateClasses
 */
var AlignImageRight = React.createClass({
	mixins: [AlloyEditor.ButtonStateClasses, AlloyEditor.ButtonCommand, AlloyEditor.ButtonCommandActive],

	// Allows validating props being passed to the component.
	propTypes: {
		/**
		 * The editor instance where the component is being used.
		 *
		 * @instance
		 * @memberof AlignImageRight
		 * @property {Object} editor
		 */
		editor: React.PropTypes.object.isRequired,

		/**
		 * The label that should be used for accessibility purposes.
		 *
		 * @instance
		 * @memberof AlignImageRight
		 * @property {String} label
		 */
		label: React.PropTypes.string,

		/**
		 * The tabIndex of the button in its toolbar current state. A value other than -1
		 * means that the button has focus and is the active element.
		 *
		 * @instance
		 * @memberof AlignImageRight
		 * @property {Number} tabIndex
		 */
		tabIndex: React.PropTypes.number
	},

	// Lifecycle. Provides static properties to the widget.
	statics: {
		/**
		 * The name which will be used as an alias of the button in the configuration.
		 *
		 * @default imageRight
		 * @memberof AlignImageRight
		 * @property {String} key
		 * @static
		 */
		key: 'imageRight'
	},

	/**
	 * Lifecycle. Returns the default values of the properties used in the widget.
	 *
	 * @instance
	 * @memberof AlignImageRight
	 * @method getDefaultProps
	 * @return {Object} The default properties.
	 */
	getDefaultProps: function() {
		return {
			command: 'justifyright'
		};
	},

	/**
	 * Lifecycle. Renders the UI of the button.
	 *
	 * @instance
	 * @memberof AlignImageRight
	 * @method render
	 * @return {Object} The content which should be rendered.
	 */
	render: function() {
		var cssClass = 'ae-button ' + this.getStateClasses();

		return (
			<button aria-label={AlloyEditor.Strings.alignRight} aria-pressed={cssClass.indexOf('pressed') !== -1} className={cssClass} data-type="button-image-align-right" onClick={this.execCommand} tabIndex={this.props.tabIndex} title={AlloyEditor.Strings.alignRight}>
				<svg className="lexicon-icon lexicon-icon-align-image-right" focusable="false" role="image">
					<use data-href={themeDisplay.getPathThemeImages() + '/lexicon/icons.svg#align-image-right'} />
					<title>align-image-right</title>
				</svg>
			</button>
		);
	}
});

AlloyEditor.Buttons[AlignImageRight.key] = AlloyEditor.AlignImageRight = AlignImageRight;

export default AlignImageRight;
export {AlignImageRight};