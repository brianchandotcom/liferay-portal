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
			.dispatch(
				{
					savingChanges: true,
					type: UPDATE_SAVING_CHANGES_STATUS
				}
			)
			.dispatch(
				{
					config,
					rowId: this.itemId,
					type: UPDATE_ROW_CONFIG
				}
			)
			.dispatch(
				{
					type: UPDATE_TRANSLATION_STATUS
				}
			)
			.dispatch(
				{
					lastSaveDate: new Date(),
					type: UPDATE_LAST_SAVE_DATE
				}
			)
			.dispatch(
				{
					savingChanges: false,
					type: UPDATE_SAVING_CHANGES_STATUS
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