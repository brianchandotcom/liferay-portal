import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import 'frontend-js-web/liferay/compat/modal/Modal.es';
import templates from './SidebarMapping.soy';

/**
 * SidebarMapping
 */

class SidebarMapping extends Component {

	/**
	 * Open asset type selection dialog
	 * @private
	 * @review
	 */

	_handleSelectAssetTypeButtonClick() {
		this.emit('selectAssetTypeButtonClick');
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

SidebarMapping.STATE = {

	/**
	 * Selected mapping type label
	 * @default {}
	 * @instance
	 * @memberOf SidebarMapping
	 * @review
	 * @type {{
	 *   subtype: string,
	 *   type: string
	 * }}
	 */

	selectedMappingTypeLabel: Config
		.shapeOf(
			{
				subtype: Config.string().value(''),
				type: Config.string().value('')
			}
		)
		.value({})

};

Soy.register(SidebarMapping, templates);

export {SidebarMapping};
export default SidebarMapping;