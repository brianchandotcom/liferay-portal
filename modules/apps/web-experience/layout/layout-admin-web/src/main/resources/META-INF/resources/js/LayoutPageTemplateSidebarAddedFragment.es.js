import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './LayoutPageTemplateSidebarAddedFragment.soy';

/**
 * LayoutPageTemplateSidebarAddedFragment
 */

class LayoutPageTemplateSidebarAddedFragment extends Component {

	/**
	 * Callback executed when the fragment remove button is clicked.
	 * It emits a 'fragmentRemoveButtonClick' event with the fragment index.
	 * @private
	 */

	_handleFragmentRemoveButtonClick() {
		this.emit(
			'fragmentRemoveButtonClick',
			{
				fragmentIndex: this.index
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

LayoutPageTemplateSidebarAddedFragment.STATE = {

	/**
	 * Fragment index
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateSidebarAddedFragment
	 * @type {!number}
	 */

	index: Config.number().required(),

	/**
	 * Fragment name
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateSidebarAddedFragment
	 * @type {!string}
	 */

	name: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateSidebarAddedFragment
	 * @type {!string}
	 */

	spritemap: Config.string().required()
};

Soy.register(LayoutPageTemplateSidebarAddedFragment, templates);

export {LayoutPageTemplateSidebarAddedFragment};
export default LayoutPageTemplateSidebarAddedFragment;