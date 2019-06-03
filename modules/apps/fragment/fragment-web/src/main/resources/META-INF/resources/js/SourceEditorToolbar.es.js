import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import AceEditor from './AceEditor.es';
import templates from './SourceEditorToolbar.soy';

/**
 * Creates a Source Editor Toolbar component.
 */

class SourceEditorToolbar extends Component {
	/**
	 * @inheritDoc
	 */
	created() {
		this._updateSyntaxLabel(this.syntax);
	}

	/**
	 * @inheritDoc
	 */
	willReceiveState(changes) {
		if (changes.syntax) {
			this._updateSyntaxLabel(changes.syntax);
		}
	}

	/**
	 * Updates the <code>_syntaxLabel</code> attribute mapping with the given
	 * syntax.
	 *
	 * @param {!string} syntax
	 * @private
	 */
	_updateSyntaxLabel(syntax) {
		this._syntaxLabel = SourceEditorToolbar.SYNTAX_LABEL[syntax] || syntax;
	}
}

/**
 * Editor labels to display to the user.
 */
SourceEditorToolbar.SYNTAX_LABEL = {
	[AceEditor.SYNTAX.css]: 'CSS',
	[AceEditor.SYNTAX.html]: 'HTML',
	[AceEditor.SYNTAX.javascript]: 'JavaScript'
};

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
SourceEditorToolbar.STATE = {
	/**
	 * Syntax used for the editor toolbar.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SourceEditorToolbar
	 * @type {!string}
	 */
	syntax: Config.oneOf(Object.values(AceEditor.SYNTAX)).required(),

	/**
	 * Syntax label shown in the toolbar markup.
	 *
	 * @default ''
	 * @instance
	 * @memberOf SourceEditorToolbar
	 * @private
	 * @type {string}
	 */
	_syntaxLabel: Config.string()
		.internal()
		.value('')
};

Soy.register(SourceEditorToolbar, templates);

export {SourceEditorToolbar};
export default SourceEditorToolbar;
