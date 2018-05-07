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
	 *   subtype: {
	 *   	id: !string,
	 *   	label: !string
	 *   },
	 *   type: {
	 *   	id: !string,
	 *   	label: !string
	 *   }
	 * }}
	 */

	selectedMappingTypes: Config
		.shapeOf(
			{
				subtype: Config.shapeOf(
					{
						id: Config.string().required(),
						label: Config.string().required()
					}
				),
				type: Config.shapeOf(
					{
						id: Config.string().required(),
						label: Config.string().required()
					}
				)
			}
		)
		.value({})

};

Soy.register(SidebarMapping, templates);

export {SidebarMapping};
export default SidebarMapping;