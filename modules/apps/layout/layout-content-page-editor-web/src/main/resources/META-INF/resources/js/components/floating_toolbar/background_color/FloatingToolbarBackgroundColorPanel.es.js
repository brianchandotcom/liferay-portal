import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FloatingToolbarBackgroundColorPanelDelegateTemplate.soy';
import {ITEM_CONFIG_KEYS} from '../../../utils/constants';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import templates from './FloatingToolbarBackgroundColorPanel.soy';
import {UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS, UPDATE_SECTION_CONFIG, UPDATE_TRANSLATION_STATUS} from '../../../actions/actions.es';

/**
 * FloatingToolbarBackgroundColorPanel
 */
class FloatingToolbarBackgroundColorPanel extends Component {

	/**
	 * @inheritDoc
	 */
	disposed() {
		this._colorPalette.destroy();
	}

	/**
	 * @inheritDoc
	 */
	rendered() {
		AUI().use(
			'aui-color-palette',
			(A) => {
				this._colorPalette = new A.ColorPalette(
					{
						items: this.themeColorsCssClasses
					}
				);

				this._colorPalette.after(
					'select',
					this._handleColorSelect,
					this
				);

				this._colorPalette.render(
					'#floatingToolbarBackgroundColorPanelPalette'
				);
			}
		);
	}

	/**
	 * Handle Clear button click
	 * @private
	 * @review
	 */
	_handleClearButtonClick() {
		this._updateSectionConfig(
			{
				[ITEM_CONFIG_KEYS.backgroundColorCssClass]: ''
			}
		);
	}

	/**
	 * Handle color palette color select
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleColorSelect(event) {
		this._updateSectionConfig(
			{
				[ITEM_CONFIG_KEYS.backgroundColorCssClass]: event.value.value
			}
		);
	}

	/**
	 * Updates section configuration
	 * @param {object} config Section configuration
	 * @private
	 * @review
	 */
	_updateSectionConfig(config) {
		this.store
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: true
				}
			)
			.dispatchAction(
				UPDATE_SECTION_CONFIG,
				{
					config,
					sectionId: this.itemId
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
		.required(),

	/**
	 * Internal Color Palette instance
	 * @default null
	 * @memberof FloatingToolbarBackgroundColorPanel
	 * @review
	 * @type {object}
	 */
	_colorPalette: Config
		.internal()
		.value(null)
};

const ConnectedFloatingToolbarBackgroundColorPanel = getConnectedComponent(
	FloatingToolbarBackgroundColorPanel,
	['themeColorsCssClasses']
);

Soy.register(ConnectedFloatingToolbarBackgroundColorPanel, templates);

export {ConnectedFloatingToolbarBackgroundColorPanel, FloatingToolbarBackgroundColorPanel};
export default ConnectedFloatingToolbarBackgroundColorPanel;