/* global React, AlloyEditor */

(function() {
	'use strict';

	var React = AlloyEditor.React;

	var ButtonImageAlt = React.createClass(
		{
			mixins: [AlloyEditor.ButtonStateClasses, AlloyEditor.ButtonCfgProps],

			displayName: 'ButtonImageAlt',

			propTypes: {
				editor: React.PropTypes.object.isRequired
			},

			statics: {
				key: 'imageAlt'
			},

			/**
			 * Lifecycle. Invoked once before the component is mounted.
			 * The return value will be used as the initial value of this.state.
			 *
			 * @method getInitialState
			 */
			getInitialState: function() {
				var image = this.props.editor.get('nativeEditor').getSelection().getSelectedElement();

				return {
					altImage: image.getAttribute('alt'),
					element: image
				};
			},

			/**
			 * Lifecycle. Renders the UI of the button.
			 * Rendering alt button or alt form to update the alt´s image
			 * depend on renderExclusive property
			 *
			 * @method render
			 * @return {Object} The content which should be rendered.
			 */
			render: function() {
				var cssClass = 'ae-button ' + this.getStateClasses();

				var element;

				if (this.props.renderExclusive) {

					element = React.createElement(
						'div',
						{
							className: 'ae-container-edit-link'
						},
						React.createElement(
							'div',
							{
								className: 'ae-container-input xxl'
							},
							React.createElement(
								'input',
								{
									ariaLabel: 'Alt',
									className: 'ae-input',
									onChange: this._handleAltChange,
									onKeyDown: this._handleKeyDown,
									placeholder: 'Alt',
									ref: 'refAltInput',
									title: 'Alt',
									value: this.state.altImage
								}
							)
						),
						React.createElement(
							'button',
							{
								className: 'ae-button',
								onClick: this._updateAltImage
							},
							React.createElement(
								'span',
								{
									className: 'ae-icon-ok'
								}
							)
						)
					);
				}
				else {
					element = React.createElement(
						'button',
						{
							className: cssClass,
							onClick: this._requestExclusive
						},
						React.createElement(
							'small',
							{
								className: 'ae-icon small'
							},
							'Alt'
						)
					);
				}

				return element;
			},

			/**
			 * Focuses the user cursor on the widget's input.
			 *
			 * @protected
			 * @method _focusAltInput
			 */
			_focusAltInput: function() {
				var instance = this;

				var focusLinkEl = function() {
					AlloyEditor.ReactDOM.findDOMNode(instance.refs.refAltInput).focus();
				};

				if (window.requestAnimationFrame) {
					window.requestAnimationFrame(focusLinkEl);
				}
				else {
					setTimeout(focusLinkEl, 0);
				}

			},

			/**
			 * Event attached to alt input that fires when its value is changed
			 *
			 * @protected
			 * @param {MouseEvent} event
			 */
			_handleAltChange: function(event) {
				this.setState({ altImage: event.target.value });

				this._focusAltInput();
			},

			/**
			 * Event attached to al tinput that fires when key is down
			 * This method check that enter key is pushed to update the component´s state
			 *
			 * @protected
			 * @param {MouseEvent} event
			 */
			_handleKeyDown: function(event) {
				if (event.keyCode === 13) {
					event.preventDefault();
					this._updateAltImage();
				}
			},

			/**
			 * Requests the link button to be rendered in exclusive mode to allow the creation of a link.
			 *
			 * @protected
			 * @method _requestExclusive
			 */
			_requestExclusive: function() {
				this.props.requestExclusive(ButtonImageAlt.key);
			},

			/**
			 * Method called by clicking ok button or pushing key enter to update altImage state and to update alt property from the image that is selected
			 * This method calls cancelExclusive to show the previous toolbar before enter to edit alt property
			 *
			 * @protected
			 */
			_updateAltImage: function() {
				var editor = this.props.editor.get('nativeEditor');

				var newValue = this.refs.refAltInput.value;

				this.setState(
					{
						altImage: newValue
					}
				);

				this.state.element.setAttribute('alt', newValue);

				editor.fire('actionPerformed', this);

				// We need to cancelExclusive with the bound parameters in case the button is used
				// inside another in exclusive mode (such is the case of the alt button)
				this.props.cancelExclusive();

			}
		}
	);

	AlloyEditor.Buttons[ButtonImageAlt.key] = AlloyEditor.ButtonImageAlt = ButtonImageAlt;
}());