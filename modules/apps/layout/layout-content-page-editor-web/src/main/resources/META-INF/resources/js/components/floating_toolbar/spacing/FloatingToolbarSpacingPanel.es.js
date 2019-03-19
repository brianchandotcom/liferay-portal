import 'clay-checkbox';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarSpacingPanelDelegateTemplate.soy';
import {CONTAINER_TYPES, ITEM_CONFIG_KEYS, NUMBER_OF_COLUMNS_OPTIONS} from '../../../utils/constants';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbarSpacingPanel.soy';
import {UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS, UPDATE_SECTION_COLUMNS, UPDATE_SECTION_CONFIG, UPDATE_TRANSLATION_STATUS} from '../../../actions/actions.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';

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

		nextState = setIn(
			nextState,
			['_sectionColumnsCount'],
			this.item.columns.length
		);

		return nextState;
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
	 * Handle container spacing checkbox mousedown
	 * @param {Event} event
	 */
	_handleColumnSpacingOptionMousedown(event) {
		event.preventDefault();
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

	/**
	 * Handle number of columns option change
	 * @param {Event} event
	 */
	_handleNumberOfColumnsOptionChange(event) {
		const newValue = event.delegateTarget.value;
		const prevValue = this.item.columns.length;

		let updateSectionColumns = true;

		if (newValue < prevValue) {
			let columnsToRemove = this.item.columns.slice(newValue - prevValue);
			let showConfirmation;

			for (let i = 0; i < columnsToRemove.length; i++) {
				if (columnsToRemove[i].fragmentEntryLinkIds.length > 0) {
					showConfirmation = true;
					break;
				}
			}

			if (showConfirmation && !confirm(Liferay.Language.get('reducing-the-number-of-columns-will-lose-the-content-added-to-the-deleted-columns-are-you-sure-you-want-to-proceed'))) {
				event.preventDefault();
				event.delegateTarget.querySelector(`option[value="${prevValue}"]`).selected = true;
				updateSectionColumns = false;
			}
		}

		if (updateSectionColumns) {
			this._updateSection(
				UPDATE_SECTION_COLUMNS,
				{
					numberOfColumns: event.delegateTarget.value,
					sectionId: this.itemId
				}
			);
		}
	}

	/**
	 * Updates section
	 * @param {string} updateAction Update action name
	 * @param {object} payload Section payload
	 * @private
	 * @review
	 */
	_updateSection(updateAction, payload) {
		this.store
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: true
				}
			)
			.dispatchAction(
				updateAction,
				payload
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
	 * Updates section configuration
	 * @param {object} config Section configuration
	 * @private
	 * @review
	 */
	_updateSectionConfig(config) {
		this._updateSection(
			UPDATE_SECTION_CONFIG,
			{
				config,
				sectionId: this.itemId
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
	 * @default CONTAINER_TYPES
	 * @memberOf FloatingToolbarSpacingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_containerTypes: Config
		.array()
		.internal()
		.value(CONTAINER_TYPES),

	/**
	 * @default CONTAINER_TYPES
	 * @memberOf FloatingToolbarSpacingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_numberOfColumnsOptions: Config
		.array()
		.internal()
		.value(NUMBER_OF_COLUMNS_OPTIONS),

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
	 * @default undefined
	 * @memberof FloatingToolbarSpacingPanel
	 * @review
	 * @type {object}
	 */
	store: Config
		.object()
		.value(null)
};

const ConnectedFloatingToolbarSpacingPanel = getConnectedComponent(
	FloatingToolbarSpacingPanel,
	[
		'layoutData',
		'spritemap'
	]
);

Soy.register(ConnectedFloatingToolbarSpacingPanel, templates);

export {FloatingToolbarSpacingPanel};
export default FloatingToolbarSpacingPanel;