const React = AlloyEditor.React;

/**
 * The Spacing class provides functionality for changing line spacing in a
 * document using Bootstrap margin classes..
 *
 * @uses ButtonStateClasses
 * @uses ButtonCfgProps
 *
 * @class Spacing
 */
const Spacing = React.createClass({
	mixins: [ AlloyEditor.ButtonStateClasses, AlloyEditor.ButtonCfgProps],

	// Allows validating props being passed to the component.
	propTypes: {
		/**
		 * The editor instance where the component is being used.
		 *
		 * @instance
		 * @memberof Spacing
		 * @property {Object} editor
		 */
		editor: React.PropTypes.object.isRequired,

		/**
		 * Indicates whether the styles list is expanded or not.
		 *
		 * @instance
		 * @memberof Spacing
		 * @property {Boolean} expanded
		 */
		expanded: React.PropTypes.bool,

		/**
		 * The label that should be used for accessibility purposes.
		 *
		 * @instance
		 * @memberof Spacing
		 * @property {String} label
		 */
		label: React.PropTypes.string,

		/**
		 * Indicates whether the remove styles item should appear in the styles list.
		 *
		 * @instance
		 * @memberof Spacing
		 * @property {Boolean} showRemoveStylesItem
		 */
		showRemoveStylesItem: React.PropTypes.bool,

		/**
		 * List of the styles the button is able to handle.
		 *
		 * @instance
		 * @memberof Spacing
		 * @property {Array} styles
		 */
		styles: React.PropTypes.arrayOf(React.PropTypes.object),

		/**
		 * The tabIndex of the button in its toolbar current state. A value other than -1
		 * means that the button has focus and is the active element.
		 *
		 * @instance
		 * @memberof Spacing
		 * @property {Number} tabIndex
		 */
		tabIndex: React.PropTypes.number,

		/**
		 * Callback provided by the button host to notify when the styles list has been expanded.
		 *
		 * @instance
		 * @memberof Spacing
		 * @property {Function} toggleDropdown
		 */
		toggleDropdown: React.PropTypes.func
	},

	// Lifecycle. Provides static properties to the widget.
	statics: {
		key: 'spacing'
	},

	/**
	 * Lifecycle. Renders the UI of the button.
	 *
	 * @method render
	 * @return {Object} The content which should be rendered.
	 */
	render: function() {
		var activeSpacing = "1.0x";

		var spacings = this._getSpacings();

		spacings.forEach(function (item) {
			if (this._checkActive(item.style)) {
				activeSpacing = item.name;
			}
		}.bind(this));

		var buttonIconPath =
			themeDisplay.getPathThemeImages() + '/lexicon/icons.svg#separator';

		var buttonStylesProps = {
			activeStyle: activeSpacing,
			editor: this.props.editor,
			onDismiss: this.props.toggleDropdown,
			showRemoveStylesItem: false,
			styles: spacings
		};

		return (
			<div className="ae-container ae-container-dropdown-small ae-has-dropdown">
				<button
					aria-expanded={this.props.expanded}
					className="ae-toolbar-element"
					onClick={this.props.toggleDropdown}
					role="combobox"
					tabIndex={this.props.tabIndex}
				>
					<span>
						<svg className="lexicon-icon">
							<use href={buttonIconPath}/>
						</svg>
						&nbsp;
						{activeSpacing}
					</span>
				</button>
				{this.props.expanded &&
				 <AlloyEditor.ButtonStylesList {...buttonStylesProps} />
				}
			</div>
		);
	},

	_applyStyle: function(className) {
		var editor = this.props.editor.get('nativeEditor');

		var styleConfig = {
			element: 'div',
			attributes: {
				class: className
			}
		};

		var style = new CKEDITOR.style(styleConfig);

		editor.getSelection().lock();

		this._getSpacings().forEach(
			function(item) {
				if (this._checkActive(item.style)) {
					editor.removeStyle(new CKEDITOR.style(item.style));
				}
			}.bind(this)
		);

		editor.applyStyle(style);

		editor.getSelection().unlock();

		editor.fire('actionPerformed', this);
	},

	/**
	 * Checks if the given spacing definition is applied to the current selection in the editor.
	 *
	 * @instance
	 * @memberof Spacing
	 * @method _checkActive
	 * @param {Object} styleConfig Spacing definition as per http://docs.ckeditor.com/#!/api/CKEDITOR.style.
	 * @protected
	 * @return {Boolean} Returns true if the spacing is applied to the selection, false otherwise.
	 */
	_checkActive: function(styleConfig) {
		var nativeEditor = this.props.editor.get('nativeEditor');

		var active = true;

		var elementPath = nativeEditor.elementPath();

		if (elementPath && elementPath.lastElement) {
			styleConfig.attributes.class.split(' ').forEach(
				function(className) {
					active = active && elementPath.lastElement.hasClass(className);
				}
			)
		}
		else {
			active = false;
		}

		return active;
	},

	/**
	 * Returns an array of spacings. Each spacing consists from three properties:
	 * - name - the style name, for example "default"
	 * - style - an object with one property, called `element` which value
	 * represents the style which have to be applied to the element.
	 * - styleFn - a function which applies selected style to the editor selection
	 *
	 * @instance
	 * @memberof Spacing
	 * @method _getSpacings
	 * @protected
	 * @return {Array<object>} An array of objects containing the spacings.
	 */
	_getSpacings: function() {
		return this.props.styles || [{
			name: '1.0x',
			style: {
				element: 'div',
				attributes: {
					class: ''
				},
				type: 1
			},
			styleFn: this._applyStyle.bind(this, ''),
		}, {
			name: '1.5x',
			style: {
				element: 'div',
				attributes: {
					class: 'mt-1 mb-1'
				},
				type: 1
			},
			styleFn: this._applyStyle.bind(this, 'mt-1 mb-1')
		}, {
			name: '2.0x',
			style: {
				element: 'div',
				attributes: {
					class: 'mt-2 mb-2'
				},
				type: 1
			},
			styleFn: this._applyStyle.bind(this, 'mt-2 mb-2')
		}, {
			name: '3.0x',
			style: {
				element: 'div',
				attributes: {
					class: 'mt-3 mb-3'
				},
				type: 1
			},
			styleFn: this._applyStyle.bind(this, 'mt-3 mb-3')
		}, {
			name: '4.0x',
			style: {
				element: 'div',
				attributes: {
					class: 'mt-4 mb-4'
				},
				type: 1
			},
			styleFn: this._applyStyle.bind(this, 'mt-4 mb-4')
		}, {
			name: '5.0x',
			style: {
				element: 'div',
				attributes: {
					class: 'mt-5 mb-5'
				},
				type: 1
			},
			styleFn: this._applyStyle.bind(this, 'mt-5 mb-5')
		}];
	}

});

export default Spacing;
export {Spacing};