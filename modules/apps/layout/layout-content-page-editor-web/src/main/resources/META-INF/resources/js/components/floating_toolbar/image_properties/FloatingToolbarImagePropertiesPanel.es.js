import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarImagePropertiesPanelDelegateTemplate.soy';
import {EDITABLE_FIELD_CONFIG_KEYS, TARGET_TYPES} from '../../../utils/constants';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import templates from './FloatingToolbarImagePropertiesPanel.soy';
import {CLEAR_FRAGMENT_EDITOR, ENABLE_FRAGMENT_EDITOR, UPDATE_CONFIG_ATTRIBUTES, UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS, UPDATE_TRANSLATION_STATUS} from '../../../actions/actions.es';

/**
 * FloatingToolbarImagePropertiesPanel
 */
class FloatingToolbarImagePropertiesPanel extends Component {

	/**
	 * Updates fragment configuration
	 * @param {object} config Configuration
	 * @private
	 * @review
	 */
	_updateFragmentConfig(config) {
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
					editableId: this.item.editableId,
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
	 * Handle select image button change
	 * @private
	 * @review
	 */
	_handleClearImageButtonClick() {
		this.store.dispatchAction(
			CLEAR_FRAGMENT_EDITOR,
			{
				itemId: this.itemId
			}
		);
	}

	/**
	 * Handle image link change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleImageLinkInputChange(event) {
		this._updateFragmentConfig(
			{
				[EDITABLE_FIELD_CONFIG_KEYS.imageLink]: event.delegateTarget.value
			}
		);
	}

	/**
	 * Handle image target option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleImageTargetOptionChange(event) {
		this._updateFragmentConfig(
			{
				[EDITABLE_FIELD_CONFIG_KEYS.imageTarget]: event.delegateTarget.value
			}
		);
	}

	/**
	 * Handle select image button change
	 * @private
	 * @review
	 */
	_handleSelectImageButtonClick() {
		this.store.dispatchAction(
			ENABLE_FRAGMENT_EDITOR,
			{
				itemId: this.itemId
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
FloatingToolbarImagePropertiesPanel.STATE = {

	/**
	 * @default TARGET_TYPES
	 * @memberOf FloatingToolbarImagePropertiesPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_imageTargetOptions: Config
		.array()
		.internal()
		.value(TARGET_TYPES),

	/**
	 * @default undefined
	 * @memberOf FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {object}
	 */
	item: Config.object(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config
		.string()
		.required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {object}
	 */
	store: Config
		.object()
		.value(null)
};

const ConnectedFloatingToolbarImagePropertiesPanel = getConnectedComponent(
	FloatingToolbarImagePropertiesPanel,
	[
		'imageSelectorURL',
		'portletNamespace'
	]
);

Soy.register(ConnectedFloatingToolbarImagePropertiesPanel, templates);

export {ConnectedFloatingToolbarImagePropertiesPanel, FloatingToolbarImagePropertiesPanel};
export default FloatingToolbarImagePropertiesPanel;