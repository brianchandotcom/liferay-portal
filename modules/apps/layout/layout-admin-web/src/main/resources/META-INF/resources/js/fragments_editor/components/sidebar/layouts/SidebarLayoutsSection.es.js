import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './SidebarLayoutsSection.soy';

/**
 * SidebarLayoutsSection
 */

class SidebarLayoutsSection extends Component {}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

SidebarLayoutsSection.STATE = {

	/**
	 * List of layouts to be shown
	 * @default []
	 * @memberOf SidebarLayoutsSection
	 * @private
	 * @review
	 * @type {Array}
	 */

	_layouts: Config.arrayOf(
		Config.shapeOf(
			{
				columns: Config.arrayOf(Config.string())
			}
		)
	).value(
		[
			{columns: ['3', '6', '3']},
			{columns: ['6', '6']},
			{columns: ['4', '8']}
		]
	)
};

Soy.register(SidebarLayoutsSection, templates);

export {SidebarLayoutsSection};
export default SidebarLayoutsSection;