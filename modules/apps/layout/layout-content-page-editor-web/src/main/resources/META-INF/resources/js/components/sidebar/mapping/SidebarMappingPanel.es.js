import Component from 'metal-component';
import Soy from 'metal-soy';

import 'frontend-js-web/liferay/compat/modal/Modal.es';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {OPEN_ASSET_TYPE_DIALOG} from '../../../actions/actions.es';
import templates from './SidebarMappingPanel.soy';

/**
 * SidebarMappingPanel
 */
class SidebarMappingPanel extends Component {

	/**
	 * Open asset type selection dialog
	 * @private
	 * @review
	 */
	_handleSelectAssetTypeButtonClick() {
		this.store.dispatchAction(OPEN_ASSET_TYPE_DIALOG);
	}

}

const ConnectedSidebarMappingPanel = getConnectedComponent(
	SidebarMappingPanel,
	[
		'selectedMappingTypes',
		'spritemap'
	]
);

Soy.register(ConnectedSidebarMappingPanel, templates);

export {ConnectedSidebarMappingPanel, SidebarMappingPanel};
export default SidebarMappingPanel;