var React = AlloyEditor.React;
var ReactDOM = AlloyEditor.ReactDOM;

var KEY_ENTER = 13;
var KEY_ESC = 27;

var inputPlaceholder = Liferay.Language.get('paste-video-link');

/**
 * The EmbedVideoEdit class provides functionality for embedding a social platform
 * video element in a document based on its original url.
 *
 * @class EmbedVideoEdit
 */
var EmbedVideoEdit = React.createClass({
	// Allows validating props being passed to the component.
	propTypes: {
		/**
		 * The editor instance where the component is being used.
		 *
		 * @instance
		 * @memberof EmbedVideoEdit
		 * @property {Object} editor
		 */
		editor: React.PropTypes.object.isRequired
	},

	// Lifecycle. Provides static properties to the widget.
	statics: {
		/**
		 * The name which will be used as an alias of the button in the configuration.
		 *
		 * @default embedEdit
		 * @memberof EmbedVideoEdit
		 * @property {String} key
		 * @static
		 */
		key: 'embedVideoEdit'
	},

	/**
	 * Lifecycle. Invoked once, only on the client, immediately after the initial rendering occurs.
	 *
	 * Focuses on the link input to immediately allow editing. This should only happen if the component
	 * is rendered in exclusive mode to prevent aggressive focus stealing.
	 *
	 * @instance
	 * @memberof EmbedVideoEdit
	 * @method componentDidMount
	 */
	componentDidMount: function() {
		if (this.props.renderExclusive || this.props.manualSelection) {
			// We need to wait for the next rendering cycle before focusing to avoid undesired
			// scrolls on the page
			if (window.requestAnimationFrame) {
				window.requestAnimationFrame(this._focusVideoUrlInput);
			} else {
				setTimeout(this._focusVideoUrlInput, 0);
			}
		}
	},

	/**
	 * Lifecycle. Invoked when a component is receiving new props.
	 * This method is not called for the initial render.
	 *
	 * @instance
	 * @memberof EmbedVideoEdit
	 * @method componentWillReceiveProps
	 */
	componentWillReceiveProps: function(nextProps) {
		this.replaceState(this.getInitialState());
	},

	/**
	 * Lifecycle. Invoked once before the component is mounted.
	 * The return value will be used as the initial value of this.state.
	 *
	 * @instance
	 * @memberof EmbedVideoEdit
	 * @method getInitialState
	 */
	getInitialState: function() {
		var editor = this.props.editor.get('nativeEditor');
		var embed;

		var selection = editor.getSelection();

		if (selection) {
			var selectedElement = selection.getSelectedElement();

			if (selectedElement) {
				embed = selectedElement.findOne('[data-widget="videoembed"]');
			}
		}

		var videoURL = embed ? embed.getAttribute('data-embed-video-url') : '';

		return {
			element: embed,
			initialEmbed: {
				videoURL: videoURL
			},
			videoURL: videoURL
		};
	},

	/**
	 * Lifecycle. Renders the UI of the button.
	 *
	 * @instance
	 * @memberof EmbedVideoEdit
	 * @method render
	 * @return {Object} The content which should be rendered.
	 */
	render: function() {
		var clearVideoURLStyle = {
			opacity: this.state.videoURL ? 1 : 0
		};

		return (
			<div className="ae-container-embed-video-edit">
				<div className="ae-container-input xxl">
					<input className="ae-input" onChange={this._handleVideoURLChange} onKeyDown={this._handleKeyDown} placeholder={inputPlaceholder} ref="linkInput" type="text" value={this.state.videoURL}></input>
					<button aria-label={AlloyEditor.Strings.clearInput} className="ae-button ae-icon-remove" onClick={this._clearLink} style={clearVideoURLStyle} title={AlloyEditor.Strings.clear}></button>
				</div>
				<button aria-label={AlloyEditor.Strings.confirm} className="ae-button" disabled={!this._isValidState()} onClick={this._embedVideoURL} title={AlloyEditor.Strings.confirm}>
					<span className="ae-icon-ok"></span>
				</button>
			</div>
		);
	},

	/**
	 * Clears the link input. This only changes the component internal state, but does not
	 * affect the link element of the editor. Only the _removeLink and _updateLink methods
	 * are translated to the editor element.
	 *
	 * @instance
	 * @memberof EmbedVideoEdit
	 * @method _clearLink
	 * @protected
	 */
	_clearLink: function() {
		this.setState({
			videoURL: ''
		});
	},

	/**
	 * Triggers the embedVideoUrl command to transform the link into an embed media object
	 *
	 * @instance
	 * @memberof EmbedVideoEdit
	 * @method _embedVideoURL
	 * @protected
	 */
	_embedVideoURL: function() {
		var nativeEditor = this.props.editor.get('nativeEditor');

		nativeEditor.execCommand('embedVideoUrl', {
			url: this.state.videoURL
		});

		// We need to cancelExclusive with the bound parameters in case the button is used
		// inside another in exclusive mode (such is the case of the link button)
		this.props.cancelExclusive();
	},

	/**
	 * Focuses the user cursor on the widget's input.
	 *
	 * @instance
	 * @memberof EmbedVideoEdit
	 * @method _focusVideoUrlInput
	 * @protected
	 */
	_focusVideoUrlInput: function() {
		ReactDOM.findDOMNode(this.refs.linkInput).focus();
	},

	/**
	 * Monitors key interaction inside the input element to respond to the keys:
	 * - Enter: Creates/updates the link.
	 * - Escape: Discards the changes.
	 *
	 * @instance
	 * @memberof EmbedVideoEdit
	 * @method _handleKeyDown
	 * @param {SyntheticEvent} event The keyboard event.
	 * @protected
	 */
	_handleKeyDown: function(event) {
		if (event.keyCode === KEY_ENTER || event.keyCode === KEY_ESC) {
			event.preventDefault();
		}

		if (event.keyCode === KEY_ENTER) {
			this._embedVideoURL();
		} else if (event.keyCode === KEY_ESC) {
			this.props.cancelExclusive();
		}
	},

	/**
	 * Updates the component state when the link input changes on user interaction.
	 *
	 * @instance
	 * @memberof EmbedVideoEdit
	 * @method _handleVideoURLChange
	 * @param {SyntheticEvent} event The change event.
	 * @protected
	 */
	_handleVideoURLChange: function(event) {
		this.setState({
			videoURL: event.target.value
		});
	},

	/**
	 * Verifies that the current link state is valid so the user can save the link. A valid state
	 * means that we have a non-empty videoURL that's different from the original one.
	 *
	 * @instance
	 * @memberof EmbedVideoEdit
	 * @method _isValidState
	 * @protected
	 * @return {Boolean} True if the state is valid, false otherwise
	 */
	_isValidState: function() {
		var validState =
			this.state.videoURL && (
				this.state.videoURL !== this.state.initialEmbed.videoURL
			);

		return validState;
	}
});

export default EmbedVideoEdit;
export {EmbedVideoEdit};