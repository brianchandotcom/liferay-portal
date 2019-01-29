import Component from 'metal-component';
import Soy from 'metal-soy';

import 'frontend-js-web/liferay/compat/modal/Modal.es';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {OPEN_ASSET_TYPE_DIALOG, UPDATE_HIGHLIGHT_MAPPING_STATUS} from '../../../actions/actions.es';
import templates from './SidebarMappingPanel.soy';

/**
 * SidebarMappingPanel
 */
class SidebarMappingPanel extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this.store.dispatchAction(
			UPDATE_HIGHLIGHT_MAPPING_STATUS,
			{
				highlightMapping: false
			}
		);
	}

	/**
	 * Callback executed on highlight mapping checkbox click
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleHighlightMappingCheckboxChange(event) {
		this.store.dispatchAction(
			UPDATE_HIGHLIGHT_MAPPING_STATUS,
			{
				highlightMapping: Boolean(event.delegateTarget.checked)
			}
		);
	}

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
		'highlightMapping',
		'selectedMappingTypes',
		'spritemap'
	]
);

Soy.register(ConnectedSidebarMappingPanel, templates);

export {ConnectedSidebarMappingPanel, SidebarMappingPanel};
export default SidebarMappingPanel;