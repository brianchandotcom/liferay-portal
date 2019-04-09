import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import '../common/FloatingToolbarColorPicker.es';
import './FloatingToolbarBackgroundColorPanelDelegateTemplate.soy';
import {CONFIG_KEYS} from '../../../utils/rowConstants';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import templates from './FloatingToolbarBackgroundColorPanel.soy';
import {UPDATE_LAST_SAVE_DATE, UPDATE_ROW_CONFIG, UPDATE_SAVING_CHANGES_STATUS, UPDATE_TRANSLATION_STATUS} from '../../../actions/actions.es';

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
		this._updateRowConfig(
			{
				[CONFIG_KEYS.backgroundColorCssClass]: ''
			}
		);
	}

	/**
	 * Handle BackgroundColor button click
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleBackgroundColorButtonClick(event) {
		this._updateRowConfig(
			{
				[CONFIG_KEYS.backgroundColorCssClass]: event.color
			}
		);
	}

	/**
	 * Updates row configuration
	 * @param {object} config Row configuration
	 * @private
	 * @review
	 */
	_updateRowConfig(config) {
		this.store
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: true
				}
			)
			.dispatchAction(
				UPDATE_ROW_CONFIG,
				{
					config,
					rowId: this.itemId
				}
			)
			.dispatchAction(
				UPDATE_TRANSLATION_STATUS
			)
			.dispatchAction(
				UPDATE_LAST_SAVE_DATE,
				{
					lastSaveDate: new Date()
				}
			)
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: false
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
FloatingToolbarBackgroundColorPanel.STATE = {

	/**
	 * @default undefined
	 * @memberof FloatingToolbarBackgroundColorPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config
		.string()
		.required()
};

const ConnectedFloatingToolbarBackgroundColorPanel = getConnectedComponent(
	FloatingToolbarBackgroundColorPanel,
	['themeColorsCssClasses']
);

Soy.register(ConnectedFloatingToolbarBackgroundColorPanel, templates);

export {ConnectedFloatingToolbarBackgroundColorPanel, FloatingToolbarBackgroundColorPanel};
export default ConnectedFloatingToolbarBackgroundColorPanel;