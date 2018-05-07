import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import AceEditor from './AceEditor.es';
import templates from './SourceEditor.soy';
import './SourceEditorToolbar.es';

/**
 * Component that creates an instance of Ace editor
 * to allow code editing.
 * @review
 */

class SourceEditor extends Component {

	/**
	 * Callback executed when the internal Ace editor has been
	 * modified. It simply propagates the event.
	 * @param {!Event} event
	 * @review
	 */

	_handleContentChanged(event) {
		this.emit(
			'contentChanged',
			{
				content: event.content,
				valid: event.valid
			}
		);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

SourceEditor.STATE = {

	/**
	 * Initial content sent to the editor
	 * @default undefined
	 * @instance
	 * @memberOf SourceEditor
	 * @review
	 * @type {!string}
	 */

	initialContent: Config.string().required(),

	/**
	 * Path to images.
	 * @default undefined
	 * @instance
	 * @memberOf SourceEditor
	 * @review
	 * @type {!string}
	 */

	spritemap: Config.string().required(),

	/**
	 * Syntax used for the editor.
	 * It will be used for Ace and rendered on the interface.
	 * @default undefined
	 * @instance
	 * @memberOf SourceEditor
	 * @review
	 * @see AceEditor.SYNTAX
	 * @type {!string}
	 */

	syntax: Config.oneOf(Object.values(AceEditor.SYNTAX)).required()
};

Soy.register(SourceEditor, templates);

export {SourceEditor};
export default SourceEditor;