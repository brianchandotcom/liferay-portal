import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './LayoutColumn.soy';

/**
 * LayoutColumn
 */
class LayoutColumn extends Component {}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
LayoutColumn.STATE = {
	/**
	 * List of layouts in the current column
	 * @default undefined
	 * @instance
	 * @memberof LayoutColumn
	 * @type {!Array}
	 */
	layoutColumn: Config.arrayOf(
		Config.shapeOf({
			actionURLs: Config.object().required(),
			active: Config.bool().required(),
			hasChild: Config.bool().required(),
			plid: Config.string().required(),
			url: Config.string().required(),
			title: Config.string().required(),
		})
	).required(),

	/**
	 * URL for using icons
	 * @default undefined
	 * @instance
	 * @memberof LayoutColumn
	 * @type {!string}
	 */
	pathThemeImages: Config.string().required(),

	/**
	 * Namespace of portlet to prefix parameters names
	 * @default undefined
	 * @instance
	 * @memberof LayoutColumn
	 * @type {!string}
	 */
	portletNamespace: Config.string().required(),
};

Soy.register(LayoutColumn, templates);

export {LayoutColumn};
export default LayoutColumn;
