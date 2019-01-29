import 'clay-checkbox';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarSpacingPanelDelegateTemplate.soy';
import {CONTAINER_TYPES, ITEM_CONFIG_KEYS} from '../../../utils/constants';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbarSpacingPanel.soy';
import {UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS, UPDATE_SECTION_CONFIG, UPDATE_TRANSLATION_STATUS} from '../../../actions/actions.es';

/**
 * @type {string}
 */
const DEFAULT_PADDING_SIZE = '3';

/**
 * @type {number}
 */
const PADDING_SIZES = 6;

/**
 * FloatingToolbarSpacingPanel
 */
class FloatingToolbarSpacingPanel extends Component {

	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		const config = (state.item && state.item.config) || {};
		let nextState = state;
		const selectedPaddingSizes = {
			horizontal: DEFAULT_PADDING_SIZE,
			vertical: DEFAULT_PADDING_SIZE
		};

		if (config) {
			selectedPaddingSizes.horizontal = config.paddingHorizontal || DEFAULT_PADDING_SIZE;
			selectedPaddingSizes.vertical = config.paddingVertical || DEFAULT_PADDING_SIZE;
		}

		nextState = setIn(
			nextState,
			['_paddingSizes'],
			Array
				.from({length: PADDING_SIZES})
				.fill(0)
				.map((_, i) => i.toString())
		);

		nextState = setIn(
			nextState,
			['_selectedPaddingSizes'],
			selectedPaddingSizes
		);

		return nextState;
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

	/**
	 * Handle container option change
	 * @param {Event} event
	 */
	_handleColumnSpacingOptionChange(event) {
		this._updateSectionConfig(
			{
				[ITEM_CONFIG_KEYS.columnSpacing]: event.target.checked
			}
		);
	}

	/**
	 * Handle container option change
	 * @param {Event} event
	 */
	_handleContainerPaddingOptionChange(event) {
		const {delegateTarget} = event;
		const {paddingDirectionId} = delegateTarget.dataset;
		const {value} = delegateTarget;

		this._updateSectionConfig(
			{
				[`${ITEM_CONFIG_KEYS.padding}${paddingDirectionId}`]: value
			}
		);
	}

	/**
	 * Handle container option change
	 * @param {Event} event
	 */
	_handleContainerTypeOptionChange(event) {
		this._updateSectionConfig(
			{
				[ITEM_CONFIG_KEYS.containerType]: event.delegateTarget.value
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
		.value(CONTAINER_TYPES)
};

Soy.register(FloatingToolbarSpacingPanel, templates);

export {FloatingToolbarSpacingPanel};
export default FloatingToolbarSpacingPanel;