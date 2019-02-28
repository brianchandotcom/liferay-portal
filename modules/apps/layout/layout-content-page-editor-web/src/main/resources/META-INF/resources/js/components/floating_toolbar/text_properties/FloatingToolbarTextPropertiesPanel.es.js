import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import '../common/FloatingToolbarColorPicker.es';
import './FloatingToolbarTextPropertiesPanelDelegateTemplate.soy';
import {TEXT_ALIGNMENT_OPTIONS, TEXT_STYLES, EDITABLE_FIELD_CONFIG_KEYS} from '../../../utils/constants';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import templates from './FloatingToolbarTextPropertiesPanel.soy';
import {UPDATE_CONFIG_ATTRIBUTES, UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS, UPDATE_TRANSLATION_STATUS} from '../../../actions/actions.es';

/**
 * FloatingToolbarTextPropertiesPanel
 */
class FloatingToolbarTextPropertiesPanel extends Component {

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
				UPDATE_CONFIG_ATTRIBUTES,
				{
					config,
					editableId: this.itemId,
					fragmentEntryLinkId: this.item.fragmentEntryLinkId
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

	/**
	 * Handle text alignment option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleTextAlignmentOptionChange(event) {
		this._updateSectionConfig(
			{
				[EDITABLE_FIELD_CONFIG_KEYS.textAlignment]: event.delegateTarget.value
			}
		);
	}

	/**
	 * Handle text color option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleTextColorButtonClick(event) {
		this._updateSectionConfig(
			{
				[EDITABLE_FIELD_CONFIG_KEYS.textColor]: event.color
			}
		);
	}

	/**
	 * Handle text style option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleTextStyleOptionChange(event) {
		this._updateSectionConfig(
			{
				[EDITABLE_FIELD_CONFIG_KEYS.textStyle]: event.delegateTarget.value
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
FloatingToolbarTextPropertiesPanel.STATE = {

	/**
	 * @default TEXT_ALIGNMENT_OPTIONS
	 * @memberOf FloatingToolbarTextPropertiesPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_textAlignmentOptions: Config
		.array()
		.internal()
		.value(TEXT_ALIGNMENT_OPTIONS),

	/**
	 * @default TEXT_STYLES
	 * @memberOf FloatingToolbarTextPropertiesPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_textStyles: Config
		.array()
		.internal()
		.value(TEXT_STYLES),

	item: Config.object(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarTextPropertiesPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config
		.string()
		.required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarTextPropertiesPanel
	 * @review
	 * @type {object}
	 */
	store: Config
		.object()
		.value(null)
};

const ConnectedFloatingToolbarTextPropertiesPanel = getConnectedComponent(
	FloatingToolbarTextPropertiesPanel,
	['themeColorsCssClasses']
);

Soy.register(ConnectedFloatingToolbarTextPropertiesPanel, templates);

export {ConnectedFloatingToolbarTextPropertiesPanel, FloatingToolbarTextPropertiesPanel};
export default FloatingToolbarTextPropertiesPanel;