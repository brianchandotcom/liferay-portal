var React = AlloyEditor.React;
var ReactDOM = AlloyEditor.ReactDOM;

/**
 * The AlignImageLeft class provides functionality for aligning an image on the left.
 *
 * @class AlignImageLeft
 * @uses ButtonCommand
 * @uses ButtonCommandActive
 * @uses ButtonStateClasses
 */
var AlignImageLeft = React.createClass({
	mixins: [AlloyEditor.ButtonStateClasses, AlloyEditor.ButtonCommand, AlloyEditor.ButtonCommandActive],

	// Allows validating props being passed to the component.
	propTypes: {
		/**
		 * The editor instance where the component is being used.
		 *
		 * @instance
		 * @memberof AlignImageLeft
		 * @property {Object} editor
		 */
		editor: React.PropTypes.object.isRequired,

		/**
		 * The label that should be used for accessibility purposes.
		 *
		 * @instance
		 * @memberof AlignImageLeft
		 * @property {String} label
		 */
		label: React.PropTypes.string,

		/**
		 * The tabIndex of the button in its toolbar current state. A value other than -1
		 * means that the button has focus and is the active element.
		 *
		 * @instance
		 * @memberof AlignImageLeft
		 * @property {Number} tabIndex
		 */
		tabIndex: React.PropTypes.number
	},

	// Lifecycle. Provides static properties to the widget.
	statics: {
		/**
		 * The name which will be used as an alias of the button in the configuration.
		 *
		 * @default imageLeft
		 * @memberof AlignImageLeft
		 * @property {String} key
		 * @static
		 */
		key: 'imageLeft'
	},

	/**
	 * Lifecycle. Returns the default values of the properties used in the widget.
	 *
	 * @instance
	 * @memberof AlignImageLeft
	 * @method getDefaultProps
	 * @return {Object} The default properties.
	 */
	getDefaultProps: function() {
		return {
			command: 'justifyleft'
		};
	},

	/**
	 * Lifecycle. Renders the UI of the button.
	 *
	 * @instance
	 * @memberof AlignImageLeft
	 * @method render
	 * @return {Object} The content which should be rendered.
	 */
	render: function() {
		var cssClass = 'ae-button ' + this.getStateClasses();

		return (
			<button aria-label={AlloyEditor.Strings.alignLeft} aria-pressed={cssClass.indexOf('pressed') !== -1} className={cssClass} data-type="button-image-align-left" onClick={this.execCommand} tabIndex={this.props.tabIndex} title={AlloyEditor.Strings.alignLeft}>
				<svg className="lexicon-icon lexicon-icon-align-image-left" focusable="false" role="image">
					<use data-href={themeDisplay.getPathThemeImages() + '/lexicon/icons.svg#align-image-left'} />
					<title>align-image-left</title>
				</svg>
			</button>
		);
	}
});

AlloyEditor.Buttons[AlignImageLeft.key] = AlloyEditor.AlignImageLeft = AlignImageLeft;

export default AlignImageLeft;
export {AlignImageLeft};