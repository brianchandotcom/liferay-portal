import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './LayoutPageTemplateFragment.soy';

/**
 * LayoutPageTemplateFragment
 */
class LayoutPageTemplateFragment extends Component {}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
LayoutPageTemplateFragment.STATE = {
	/**
	 * Fragment name
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @type {!string}
	 */
	name: Config.string().required(),
};

Soy.register(LayoutPageTemplateFragment, templates);

export {LayoutPageTemplateFragment};
export default LayoutPageTemplateFragment;
