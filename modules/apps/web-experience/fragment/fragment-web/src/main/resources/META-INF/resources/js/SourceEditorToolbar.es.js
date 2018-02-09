import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './SourceEditorToolbar.soy';

/**
 * Component that creates an instance of Source Editor toolbar.
 */
class SourceEditorToolbar extends Component {

	/**
	 * Toggles toolbar visibility.
	 * @private
	 */
	_handleToggleIconClick() {
		this.hidden = !this.hidden;
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
SourceEditorToolbar.STATE = {

	/**
	 * Is toolbar hidden?
	 * @instance
	 * @memberOf SourceEditorToolbar
	 * @type {!bool}
	 */
	hidden: Config.bool(),

	/**
	 * Toolbar items
	 * @instance
	 * @memberOf SourceEditorToolbar
	 * @type {!Array}
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
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	spritemap: Config.string().required(),

	/**
	 * Syntax used for the editor toolbar.
	 * @instance
	 * @memberOf SourceEditorToolbar
	 * @type {!string}
	 */
	syntax: Config.oneOf(['html', 'css', 'javascript']).required(),
};

Soy.register(SourceEditorToolbar, templates);

export {SourceEditorToolbar};
export default SourceEditorToolbar;