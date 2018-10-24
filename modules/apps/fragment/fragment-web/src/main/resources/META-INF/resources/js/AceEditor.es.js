import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './AceEditor.soy';

/**
 * Component that creates an instance of Ace editor
 * to allow code editing.
 * @review
 */

class AceEditor extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */

	attached() {
		this._editorDocument = null;
		this._handleDocumentChanged = this._handleDocumentChanged.bind(this);

		AUI().use(
			'aui-ace-editor', 'aui-ace-autocomplete-plugin', 'aui-ace-autocomplete-templateprocessor',
			A => {
				const editor = new A.AceEditor(
					{
						boundingBox: this.refs.wrapper,
						highlightActiveLine: false,
						mode: this.syntax,
						tabSize: 2
					}
				);

				this._overrideSetAnnotations(editor.getSession());
				this._editorSession = editor.getSession();
				this._editorDocument = editor.getSession().getDocument();

				this.refs.wrapper.style.height = '';
				this.refs.wrapper.style.width = '';

				this._editorDocument.on('change', this._handleDocumentChanged);

				editor.getSession().on('changeAnnotation', this._handleDocumentChanged);

				if (this.initialContent) {
					this._editorDocument.setValue(this.initialContent);
				}

				this._initAutocomplete(A, editor);
			}
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	shouldUpdate() {
		return false;
	}

	_initAutocomplete(A, editor) {
		if (this.syntax !== 'html') {
			return;
		}

		const FragmentAutocompleteProcessor = function(options) {
			FragmentAutocompleteProcessor.superclass.constructor.apply(
				this, arguments);
		};

		const FragmentAutocompleteAttributeProcessor = function(options) {
			FragmentAutocompleteProcessor.superclass.constructor.apply(
				this, arguments);
		};

		const instance = this;

		A.extend(
			FragmentAutocompleteProcessor,
			A.AceEditor.TemplateProcessor, {
				getMatch: function(content) {
					let match, matchIndex;

					if ((matchIndex = content.lastIndexOf('<')) >= 0) {
						content = content.substring(matchIndex);

						if (/<lfr[\w]*[^<lfr]*$/.test(content)) {
							match = {
								content: content.substring(1),
								start: matchIndex,
								type: 0
							};
						}
					}

					return match;
				},

				getSuggestion: function(match, selectedSuggestion) {
					const selectedTag =
						FragmentAutocompleteProcessor.superclass.getSuggestion.apply(
							this, arguments);

					const attributes = instance.autocompleteTags.reduce(
						(array, tag) =>
							tag.name === selectedSuggestion ? tag.attributes : array,
						[]);

					return attributes.reduce(
						(selectedSuggestion, attribute) =>
							`${selectedSuggestion} ${attribute}=""`,
						selectedSuggestion);
				}
			});

		const autocompleteProcessor = new FragmentAutocompleteProcessor(
			{
				directives: this.autocompleteTags.map((tag) => tag.name)
			}
		);

		editor.plug(
			A.Plugin.AceAutoComplete,
			{
				processor: autocompleteProcessor,
				render: true,
				visible: false,
				zIndex: 10000
			}
		);
	}

	/**
	 * Callback executed when the internal Ace editor has been
	 * modified. It simply propagates the event.
	 * @private
	 * @review
	 */

	_handleDocumentChanged() {
		const valid = this._editorSession.getAnnotations().reduce(
			(acc, annotation) => {
				return (!acc || (annotation.type === 'error')) ?
					false : acc;
			},
			true
		);

		this.emit(
			'contentChanged',
			{
				content: this._editorDocument.getValue(),
				valid: valid
			}
		);
	}

	/**
	 * Override AceEditor's session setAnnotations method to avoid showing
	 * misleading messages.
	 * @param {Object} session AceEditor session
	 * @private
	 * @review
	 */

	_overrideSetAnnotations(session) {
		const setAnnotations = session.setAnnotations.bind(session);

		session.setAnnotations = annotations => {
			setAnnotations(
				annotations.filter(annotation => annotation.type !== 'info')
			);
		};
	}
}

/**
 * Available AceEditor syntax
 * @review
 * @static
 * @type {Object}
 */

AceEditor.SYNTAX = {
	css: 'css',
	html: 'html',
	javascript: 'javascript'
};

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

AceEditor.STATE = {

	/**
	 * List of tags to support custom autocomplete in the HTML editor
	 * @default []
	 * @instance
	 * @memberOf AceEditor
	 * @review
	 * @type Array
	 */

	autocompleteTags: Config.arrayOf(
		Config.shapeOf({
			name: Config.string(),
			attributes: Config.arrayOf(Config.string())
		})),

	/**
	 * Initial content sent to the editor
	 * @default ''
	 * @instance
	 * @memberOf AceEditor
	 * @review
	 * @type {string}
	 */

	initialContent: Config.string().value(''),

	/**
	 * Syntax used for the editor.
	 * It will be used for Ace and rendered on the interface.
	 * @default undefined
	 * @instance
	 * @memberOf AceEditor
	 * @review
	 * @see AceEditor.SYNTAX
	 * @type {!string}
	 */

	syntax: Config.oneOf(Object.values(AceEditor.SYNTAX)).required()
};

Soy.register(AceEditor, templates);

export {AceEditor};
export default AceEditor;