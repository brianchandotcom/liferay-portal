import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarSpacingPanelDelegateTemplate.soy';
import {CONTAINER_TYPES, ITEM_CONFIG_KEYS} from '../../../utils/constants';
import {UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS, UPDATE_SECTION_CONFIG, UPDATE_TRANSLATION_STATUS} from '../../../actions/actions.es';
import templates from './FloatingToolbarSpacingPanel.soy';

/**
 * FloatingToolbarSpacingPanel
 */
class FloatingToolbarSpacingPanel extends Component {

	/**
	 * Handle container option change
	 * @param {Event} event
	 */
	_handleContainerOptionChange(event) {
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
					config: {
						[ITEM_CONFIG_KEYS.containerType]: event.delegateTarget.value
					},
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
FloatingToolbarSpacingPanel.STATE = {

	/**
	 * @default undefined
	 * @memberof FloatingToolbarSpacingPanel
	 * @review
	 * @type {object}
	 */
	store: Config
		.object()
		.value(null),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarSpacingPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config
		.string()
		.required(),

	/**
	 * @default CONTAINER_TYPES
	 * @memberOf FloatingToolbarSpacingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_containerTypes: Config
		.array()
		.internal()
		.value(Object.values(CONTAINER_TYPES))
};

Soy.register(FloatingToolbarSpacingPanel, templates);

export {FloatingToolbarSpacingPanel};
export default FloatingToolbarSpacingPanel;