import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './LayoutBreadcrumbs.soy';

/**
 * LayoutBreadcrumbs
 */

class LayoutBreadcrumbs extends Component {}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

LayoutBreadcrumbs.STATE = {

	/**
	 * Breadcrumb entries
	 * @default undefined
	 * @instance
	 * @memberof LayoutBreadcrumbs
	 * @type {!Array}
	 */

	breadcrumbEntries: Config.arrayOf(
		Config.shapeOf(
			{
				title: Config.string().required(),
				url: Config.string().required()
			}
		)
	).required()
};

Soy.register(LayoutBreadcrumbs, templates);

export {LayoutBreadcrumbs};
export default LayoutBreadcrumbs;