import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import '../floating_toolbar/background_color/FloatingToolbarBackgroundColorPanel.es';
import '../floating_toolbar/background_image/FloatingToolbarBackgroundImagePanel.es';
import '../floating_toolbar/spacing/FloatingToolbarSpacingPanel.es';
import './ColumnOverlayGrid.es';
import './FragmentEntryLink.es';
import {CLEAR_ACTIVE_ITEM, MOVE_SECTION, REMOVE_SECTION, UPDATE_ACTIVE_ITEM, UPDATE_HOVERED_ITEM, UPDATE_SECTION_COLUMNS} from '../../actions/actions.es';
import {FLOATING_TOOLBAR_BUTTONS, FRAGMENTS_EDITOR_ITEM_TYPES} from '../../utils/constants';
import {getItemMoveDirection, getSectionIndex, getTargetBorder} from '../../utils/FragmentsEditorGetUtils.es';
import {moveItem, removeItem, setIn, shouldClearFocus, updateSection} from '../../utils/FragmentsEditorUpdateUtils.es';
import {shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';
import FloatingToolbar from '../floating_toolbar/FloatingToolbar.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './FragmentEntryLinkListSection.soy';

/**
 * List of available panels
 * @review
 * @type {object[]}
 */
const SECTION_FLOATING_TOOLBAR_BUTTONS = [
	FLOATING_TOOLBAR_BUTTONS.backgroundColor,
	FLOATING_TOOLBAR_BUTTONS.backgroundImage,
	FLOATING_TOOLBAR_BUTTONS.spacing
];

/**
 * FragmentEntryLinkListSection
 */
class FragmentEntryLinkListSection extends Component {

	created() {
		this._handleBodyMouseLeave = this._handleBodyMouseLeave.bind(this);
		this._handleBodyMouseMove = this._handleBodyMouseMove.bind(this);
		this._handleBodyMouseUp = this._handleBodyMouseUp.bind(this);

		document.body.addEventListener('mouseleave', this._handleBodyMouseLeave);
		document.body.addEventListener('mouseup', this._handleBodyMouseUp);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		this._disposeFloatingToolbar();
	}

	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		let columnResizerVisible;
		let nextState = state;

		if (this.rowId === this.activeItemId &&
			this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.section) {

			columnResizerVisible = true;
		}

		nextState = setIn(nextState, ['_columnResizerVisible'], columnResizerVisible);

		if (nextState._resizing && nextState._resizeSectionColumns) {
			nextState = setIn(nextState, ['columns'], state._resizeSectionColumns);
		}

		return nextState;
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	rendered() {
		if (
			(this.rowId === this.activeItemId) &&
			(this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.section) &&
			!this._resizing
		) {
			this._createFloatingToolbar();
		}
		else {
			this._disposeFloatingToolbar();
		}

		if (this._resizing) {
			this.element.focus();
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
	 * Clear resizing properties
	 * @private
	 * @review
	 */
	_clearResizing() {
		document.body.removeEventListener('mousemove', this._handleBodyMouseMove);

		this._resizeColumnIndex = 0;
		this._resizeInitialPosition = 0;
		this._resizeSectionColumns = null;
		this._resizing = false;
	}

	/**
	 * Creates a new instance of FloatingToolbar
	 * @private
	 * @review
	 */
	_createFloatingToolbar() {
		const config = {
			anchorElement: this.element,
			buttons: SECTION_FLOATING_TOOLBAR_BUTTONS,
			item: this.row,
			itemId: this.rowId,
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
	 * @private
	 * @review
	 */
	_handleBodyMouseLeave() {
		if (this._resizing) {
			this._clearResizing();
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleBodyMouseMove(event) {
	}

	/**
	 * @private
	 * @review
	 */
	_handleBodyMouseUp() {
		if (this._resizing) {
			this._updateSectionColumns(this._resizeSectionColumns);

			this._clearResizing();
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
	 * @private
	 * @review
	 */
	_handleResizerMouseDown(event) {
		this._resizeColumnIndex = this.row.columns.findIndex(
			column => column.columnId === event.delegateTarget.dataset.columnId
		);

		this._resizeInitialPosition = event.clientX;
		this._resizeSectionColumns = this.row.columns;
		this._resizing = true;

		document.body.addEventListener('mousemove', this._handleBodyMouseMove);
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

	/**
	 * Updates section columns
	 * @param {array} columns Section columns
	 * @private
	 * @review
	 */
	_updateSectionColumns(columns) {
		updateSection(
			this.store,
			UPDATE_SECTION_COLUMNS,
			{
				columns,
				sectionId: this.rowId
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
	_floatingToolbar: Config.internal().value(null),

	/**
	 * Index of the column being resized
	 * @default 0
	 * @instance
	 * @memberOf FragmentEntryLinkListSection
	 * @private
	 * @review
	 * @type {number}
	 */
	_resizeColumnIndex: Config.internal().number().value(0),

	/**
	 * Position of the mouse when the resize started
	 * @default 0
	 * @instance
	 * @memberOf FragmentEntryLinkListSection
	 * @private
	 * @review
	 * @type {number}
	 */
	_resizeInitialPosition: Config.internal().number().value(0),

	/**
	 * Copy of section columns for resizing
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkListSection
	 * @review
	 * @type {array}
	 */
	_resizeSectionColumns: Config.internal().array().value(null),

	/**
	 * True when user is resizing a column.
	 * @default false
	 * @instance
	 * @memberOf FragmentEntryLinkListSection
	 * @review
	 * @type {boolean}
	 */
	_resizing: Config.internal().bool().value(false),

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