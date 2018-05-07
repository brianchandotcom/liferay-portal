import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import AceEditor from './AceEditor.es';
import templates from './SourceEditorToolbar.soy';

/**
 * Component that creates an instance of Source Editor toolbar.
 * @review
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
	 * Toggles toolbar visibility.
	 * @private
	 * @review
	 */

	_handleToggleIconClick() {
		this.hidden = !this.hidden;
	}

	/**
	 * Updates _syntaxLabel attribute mapping the given syntax
	 * @param {!string} syntax
	 * @private
	 */

	_updateSyntaxLabel(syntax) {
		this._syntaxLabel = SourceEditorToolbar.SYNTAX_LABEL[syntax] || syntax;
	}
}

/**
 * Labels associated to the editor syntax that will be shown
 * to the user.
 */

SourceEditorToolbar.SYNTAX_LABEL = {
	[AceEditor.SYNTAX.css]: 'CSS',
	[AceEditor.SYNTAX.html]: 'HTML',
	[AceEditor.SYNTAX.javascript]: 'JavaScript'
};

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

SourceEditorToolbar.STATE = {

	/**
	 * Is toolbar hidden?
	 * @default false
	 * @instance
	 * @memberOf SourceEditorToolbar
	 * @review
	 * @type {!boolean}
	 */

	hidden: Config.bool().value(false),

	/**
	 * Toolbar items
	 * @default undefined
	 * @instance
	 * @memberOf SourceEditorToolbar
	 * @review
	 * @type {!Array<{
	 *   icon: string,
	 *   title: string,
	 *   handler: function
	 * }>}
	 */

	items: Config.arrayOf(
		Config.shapeOf(
			{
				handler: Config.func(),
				icon: Config.string(),
				title: Config.string()
			}
		)
	).required(),

	/**
	 * Path to images.
	 * @default undefined
	 * @instance
	 * @memberOf SourceEditorToolbar
	 * @review
	 * @type {!string}
	 */

	spritemap: Config.string().required(),

	/**
	 * Syntax used for the editor toolbar.
	 * @default undefined
	 * @instance
	 * @memberOf SourceEditorToolbar
	 * @review
	 * @type {!string}
	 */

	syntax: Config.oneOf(Object.values(AceEditor.SYNTAX)).required(),

	/**
	 * Syntax label shown in the toolbar markup.
	 * @default ''
	 * @instance
	 * @memberOf SourceEditorToolbar
	 * @private
	 * @review
	 * @type {string}
	 */

	_syntaxLabel: Config.string()
		.internal()
		.value('')
};

Soy.register(SourceEditorToolbar, templates);

export {SourceEditorToolbar};
export default SourceEditorToolbar;