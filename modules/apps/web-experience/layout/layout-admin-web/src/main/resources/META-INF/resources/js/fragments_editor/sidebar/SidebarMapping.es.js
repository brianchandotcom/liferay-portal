import Component from 'metal-component';
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

SidebarMapping.STATE = {};

Soy.register(SidebarMapping, templates);

export {SidebarMapping};
export default SidebarMapping;