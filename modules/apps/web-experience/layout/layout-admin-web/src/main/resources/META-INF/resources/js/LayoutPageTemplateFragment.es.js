import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './LayoutPageTemplateFragment.soy';

/**
 * LayoutPageTemplateFragment
 */
class LayoutPageTemplateFragment extends Component {
	/**
	 * Callback executed when the fragment remove button is clicked.
	 * It emits a 'fragmentRemoveButtonClick' event with the fragment index.
	 * @private
	 */
	_handleFragmentRemoveButtonClick() {
		this.emit('fragmentRemoveButtonClick', {
			fragmentIndex: this.index,
		});
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
LayoutPageTemplateFragment.STATE = {
	/**
	 * Fragment index
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @type {!number}
	 */
	index: Config.number().required(),

	/**
	 * Fragment name
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @type {!string}
	 */
	name: Config.string().required(),

	/**
	 * Fragment spritemap
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @type {!string}
	 */
	spritemap: Config.string().required(),
};

Soy.register(LayoutPageTemplateFragment, templates);

export {LayoutPageTemplateFragment};
export default LayoutPageTemplateFragment;
