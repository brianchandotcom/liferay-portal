import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import '../common/FloatingToolbarColorPicker.es';
import './FloatingToolbarBackgroundColorPanelDelegateTemplate.soy';
import {CONFIG_KEYS} from '../../../utils/rowConstants';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../../actions/saveChanges.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import templates from './FloatingToolbarBackgroundColorPanel.soy';
import {UPDATE_ROW_CONFIG} from '../../../actions/actions.es';

/**
 * FloatingToolbarBackgroundColorPanel
 */
class FloatingToolbarBackgroundColorPanel extends Component {
	/**
	 * Handle Clear button click
	 * @private
	 * @review
	 */
	_handleClearButtonClick() {
		this._updateRowConfig({
			[CONFIG_KEYS.backgroundColorCssClass]: ''
		});
	}

	/**
	 * Handle BackgroundColor button click
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleBackgroundColorButtonClick(event) {
		this._updateRowConfig({
			[CONFIG_KEYS.backgroundColorCssClass]: event.color
		});
	}

	/**
	 * Updates row configuration
	 * @param {object} config Row configuration
	 * @private
	 * @review
	 */
	_updateRowConfig(config) {
		this.store
			.dispatch(enableSavingChangesStatusAction())
			.dispatch({
				config,
				rowId: this.itemId,
				type: UPDATE_ROW_CONFIG
			})
			.dispatch(updateLastSaveDateAction())
			.dispatch(disableSavingChangesStatusAction());
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarBackgroundColorPanel.STATE = {
	/**
	 * @default undefined
	 * @memberof FloatingToolbarBackgroundColorPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required()
};

const ConnectedFloatingToolbarBackgroundColorPanel = getConnectedComponent(
	FloatingToolbarBackgroundColorPanel,
	['themeColorsCssClasses']
);

Soy.register(ConnectedFloatingToolbarBackgroundColorPanel, templates);

export {
	ConnectedFloatingToolbarBackgroundColorPanel,
	FloatingToolbarBackgroundColorPanel
};
export default ConnectedFloatingToolbarBackgroundColorPanel;
