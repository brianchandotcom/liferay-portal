import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import '../floating_toolbar/background_color/FloatingToolbarBackgroundColorPanel.es';
import '../floating_toolbar/background_image/FloatingToolbarBackgroundImagePanel.es';
import '../floating_toolbar/spacing/FloatingToolbarSpacingPanel.es';
import './FragmentEntryLink.es';
import {CLEAR_ACTIVE_ITEM, MOVE_SECTION, REMOVE_SECTION, UPDATE_ACTIVE_ITEM, UPDATE_HOVERED_ITEM} from '../../actions/actions.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../utils/constants';
import {getItemMoveDirection, getSectionIndex, getTargetBorder} from '../../utils/FragmentsEditorGetUtils.es';
import {moveItem, removeItem, shouldClearFocus} from '../../utils/FragmentsEditorUpdateUtils.es';
import {shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';
import FloatingToolbar from '../floating_toolbar/FloatingToolbar.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './FragmentEntryLinkListSection.soy';

/**
 * List of available panels
 * @review
 * @type {object[]}
 */
const FLOATING_TOOLBAR_PANELS = [
	{
		icon: 'format',
		panelId: 'background_color',
		title: Liferay.Language.get('background-color')
	},
	{
		icon: 'table',
		panelId: 'spacing',
		title: Liferay.Language.get('spacing')
	},
	{
		icon: 'picture',
		panelId: 'background_image',
		title: Liferay.Language.get('background-image')
	}
];

/**
 * FragmentEntryLinkListSection
 */
class FragmentEntryLinkListSection extends Component {

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		this._disposeFloatingToolbar();
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	rendered() {
		if (
			(this.rowId === this.activeItemId) &&
			(this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.section)
		) {
			this._createFloatingToolbar();
		}
		else {
			this._disposeFloatingToolbar();
		}
	}

	/**
	 * @inheritdoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return shouldUpdatePureComponent(changes);
	}

	/**
	 * Creates a new instance of FloatingToolbar
	 * @private
	 * @review
	 */
	_createFloatingToolbar() {
		const config = {
			anchorElement: this.element,
			item: this.row,
			itemId: this.rowId,
			panels: FLOATING_TOOLBAR_PANELS,
			portalElement: document.body,
			store: this.store
		};

		if (this._floatingToolbar) {
			this._floatingToolbar.setState(config);
		}
		else {
			this._floatingToolbar = new FloatingToolbar(config);
		}
	}

	/**
	 * Disposes an existing instance of FloatingToolbar
	 * @private
	 * @review
	 */
	_disposeFloatingToolbar() {
		if (this._floatingToolbar) {
			this._floatingToolbar.dispose();

			this._floatingToolbar = null;
		}
	}

	/**
	 * Callback executed when a section lose the focus
	 * @private
	 */
	_handleSectionFocusOut() {
		requestAnimationFrame(
			() => {
				if (shouldClearFocus(this.element)) {
					this.store.dispatchAction(CLEAR_ACTIVE_ITEM);
				}
			}
		);
	}

	/**
	 * Callback executed when a section is clicked.
	 * @param {Event} event
	 * @private
	 */
	_handleSectionClick(event) {
		event.stopPropagation();

		this.store.dispatchAction(
			UPDATE_ACTIVE_ITEM,
			{
				activeItemId: event.delegateTarget.dataset.layoutSectionId,
				activeItemType: FRAGMENTS_EDITOR_ITEM_TYPES.section
			}
		);
	}

	/**
	 * Callback executed when a section starts being hovered.
	 * @param {Event} event
	 * @private
	 */
	_handleSectionHoverStart(event) {
		if (this.store) {
			this.store.dispatchAction(
				UPDATE_HOVERED_ITEM,
				{
					hoveredItemId: event.delegateTarget.dataset.layoutSectionId,
					hoveredItemType: FRAGMENTS_EDITOR_ITEM_TYPES.section
				}
			);
		}
	}

	/**
	 * Callback executed when a key is pressed on focused section
	 * @private
	 * @param {Event} event
	 */
	_handleSectionKeyUp(event) {
		const direction = getItemMoveDirection(event.which);
		const sectionId = document.activeElement.dataset.layoutSectionId;
		const sectionIndex = getSectionIndex(
			this.layoutData.structure,
			sectionId
		);
		const targetItem = this.layoutData.structure[
			sectionIndex + direction
		];

		if (direction && targetItem) {
			const moveItemPayload = {
				sectionId,
				targetBorder: getTargetBorder(direction),
				targetItemId: targetItem.rowId
			};

			this.store.dispatchAction(
				UPDATE_ACTIVE_ITEM,
				{
					activeItemId: sectionId,
					activeItemType: FRAGMENTS_EDITOR_ITEM_TYPES.section
				}
			);

			moveItem(this.store, MOVE_SECTION, moveItemPayload);
		}
	}

	/**
	 * Callback executed when the remove section button is clicked
	 * @param {Event} event
	 * @private
	 */
	_handleSectionRemoveButtonClick(event) {
		event.stopPropagation();

		removeItem(
			this.store,
			REMOVE_SECTION,
			{
				sectionId: this.hoveredItemId
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
FragmentEntryLinkListSection.STATE = {

	/**
	 * Internal FloatingToolbar instance.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkListSection
	 * @review
	 * @type {object|null}
	 */
	_floatingToolbar: Config.internal()
		.value(null),

	/**
	 * Section row
	 * @default undefined
	 * @instance
	 * @memberof FragmentEntryLinkListSection
	 * @review
	 * @type {object}
	 */
	row: Config.object()
		.required(),

	/**
	 * Section row ID
	 * @default undefined
	 * @instance
	 * @memberof FragmentEntryLinkListSection
	 * @review
	 * @type {string}
	 */
	rowId: Config.string()
		.required()
};

const ConnectedFragmentEntryLinkListSection = getConnectedComponent(
	FragmentEntryLinkListSection,
	[
		'activeItemId',
		'activeItemType',
		'dropTargetBorder',
		'dropTargetItemId',
		'dropTargetItemType',
		'hoveredItemId',
		'hoveredItemType',
		'selectedMappingTypes',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentEntryLinkListSection, templates);

export {ConnectedFragmentEntryLinkListSection, FragmentEntryLinkListSection};
export default FragmentEntryLinkListSection;