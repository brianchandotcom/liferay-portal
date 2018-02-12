import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './SourceEditorToolbar.soy';

/**
 * Component that creates an instance of Source Editor toolbar.
 * @review
 */
class SourceEditorToolbar extends Component {
	/**
	 * Toggles toolbar visibility.
	 * @private
	 * @review
	 */
	_handleToggleIconClick() {
		this.hidden = !this.hidden;
	}
}

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
		Config.shapeOf({
			icon: Config.string(),
			title: Config.string(),
			handler: Config.func(),
		})
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
	syntax: Config.oneOf(['html', 'css', 'javascript']).required(),
};

Soy.register(SourceEditorToolbar, templates);

export {SourceEditorToolbar};
export default SourceEditorToolbar;