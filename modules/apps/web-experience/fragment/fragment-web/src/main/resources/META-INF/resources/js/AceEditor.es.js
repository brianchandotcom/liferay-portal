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
			'aui-ace-editor',
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
				this._editorDocument = editor.getSession().getDocument();

				this.refs.wrapper.style.height = '';
				this.refs.wrapper.style.width = '';

				this._editorDocument.on('change', this._handleDocumentChanged);

				if (this.initialContent) {
					this._editorDocument.setValue(this.initialContent);
				}
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

	/**
	 * Callback executed when the internal Ace editor has been
	 * modified. It simply propagates the event.
	 * @private
	 * @review
	 */

	_handleDocumentChanged() {
		this.emit(
			'contentChanged',
			{
				content: this._editorDocument.getValue()
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