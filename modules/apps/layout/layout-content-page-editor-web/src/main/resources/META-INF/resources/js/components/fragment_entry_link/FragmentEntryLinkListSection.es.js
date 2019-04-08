import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import '../floating_toolbar/background_color/FloatingToolbarBackgroundColorPanel.es';
import '../floating_toolbar/background_image/FloatingToolbarBackgroundImagePanel.es';
import '../floating_toolbar/spacing/FloatingToolbarSpacingPanel.es';
import './ColumnOverlayGrid.es';
import './FragmentEntryLink.es';
import {MOVE_SECTION, REMOVE_SECTION, UPDATE_SECTION_COLUMNS} from '../../actions/actions.es';
import {FLOATING_TOOLBAR_BUTTONS, FRAGMENTS_EDITOR_ITEM_TYPES, FRAGMENTS_EDITOR_ROW_TYPES} from '../../utils/constants';
import {getItemMoveDirection, getItemPath, getSectionIndex, getTargetBorder, itemIsInPath} from '../../utils/FragmentsEditorGetUtils.es';
import {moveItem, removeItem, setIn, updateSection} from '../../utils/FragmentsEditorUpdateUtils.es';
import {shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';
import FloatingToolbar from '../floating_toolbar/FloatingToolbar.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './FragmentEntryLinkListSection.soy';

/**
 * Defines the list of available panels.
 * @type {object[]}
 */
const SECTION_FLOATING_TOOLBAR_BUTTONS = [
	FLOATING_TOOLBAR_BUTTONS.backgroundColor,
	FLOATING_TOOLBAR_BUTTONS.backgroundImage,
	FLOATING_TOOLBAR_BUTTONS.spacing
];

/**
 * Creates a Fragment Entry Link List Section component.
 */
class FragmentEntryLinkListSection extends Component {

	/**
	 * Checks if the given section should be highlighted
	 * @param {string} dropTargetItemId
	 * @param {string} dropTargetItemType
	 * @param {string} rowId
	 * @param {object} structure
	 * @private
	 * @return {boolean}
	 * @review
	 */
	static _isHighlighted(
		dropTargetItemId,
		dropTargetItemType,
		rowId,
		structure
	) {
		const dropTargetPath = getItemPath(
			dropTargetItemId,
			dropTargetItemType,
			structure
		);

		const sectionInDropTargetPath = itemIsInPath(
			dropTargetPath,
			rowId,
			FRAGMENTS_EDITOR_ITEM_TYPES.section
		);

		const sectionIsDropTarget = (dropTargetItemId === rowId &&
			dropTargetItemType === FRAGMENTS_EDITOR_ITEM_TYPES.section);

		return (sectionInDropTargetPath && !sectionIsDropTarget);
	}

	/**
	 * @inheritdoc
	 */
	created() {
		this._handleBodyMouseLeave = this._handleBodyMouseLeave.bind(this);
		this._handleBodyMouseMove = this._handleBodyMouseMove.bind(this);
		this._handleBodyMouseUp = this._handleBodyMouseUp.bind(this);

		document.body.addEventListener('mouseleave', this._handleBodyMouseLeave);
		document.body.addEventListener('mouseup', this._handleBodyMouseUp);
	}

	/**
	 * @inheritdoc
	 */
	disposed() {
		this._disposeFloatingToolbar();

		document.body.removeEventListener('mouseleave', this._handleBodyMouseLeave);
		document.body.removeEventListener('mousemove', this._handleBodyMouseMove);
		document.body.removeEventListener('mouseup', this._handleBodyMouseUp);
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

		nextState = setIn(
			nextState,
			['_columnResizerVisible'],
			columnResizerVisible
		);

		nextState = setIn(
			nextState,
			['_fragmentsEditorRowTypes'],
			FRAGMENTS_EDITOR_ROW_TYPES
		);

		nextState = setIn(
			nextState,
			['_highlighted'],
			FragmentEntryLinkListSection._isHighlighted(
				state.dropTargetItemId,
				state.dropTargetItemType,
				state.rowId,
				state.layoutData.structure
			)
		);

		if (nextState._resizing && nextState._resizeSectionColumns) {
			nextState = setIn(
				nextState,
				['columns'],
				state._resizeSectionColumns
			);
		}

		return nextState;
	}

	/**
	 * @inheritdoc
	 */
	rendered() {
		if (
			(this.rowId === this.activeItemId) &&
			(this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.section) &&
			!this._resizing &&
			this.row.type !== FRAGMENTS_EDITOR_ROW_TYPES.sectionRow
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
	 * Clears resizing properties.
	 * @private
	 */
	_clearResizing() {
		document.body.removeEventListener('mousemove', this._handleBodyMouseMove);

		this._resizeColumnIndex = 0;
		this._resizeHighlightedColumn = null;
		this._resizeInitialPosition = 0;
		this._resizeSectionColumns = null;
		this._resizing = false;
	}

	/**
	 * Creates a new instance of the floating toolbar.
	 * @private
	 */
	_createFloatingToolbar() {
		const config = {
			anchorElement: this.element,
			buttons: SECTION_FLOATING_TOOLBAR_BUTTONS,
			item: this.row,
			itemId: this.rowId,
			itemType: FRAGMENTS_EDITOR_ITEM_TYPES.section,
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
	 * Disposes of an existing floating toolbar instance.
	 * @private
	 */
	_disposeFloatingToolbar() {
		if (this._floatingToolbar) {
			this._floatingToolbar.dispose();

			this._floatingToolbar = null;
		}
	}

	/**
	 * Callback executed when a section loses focus.
	 * @private
	 * @review
	 */
	_handleBodyMouseLeave() {
		if (this._resizing) {
			this._clearResizing();
		}
	}

	/**
	 * Callback executed when a section is clicked.
	 * @param {Event} event
	 * @private
	 */
	_handleBodyMouseMove(event) {
		let columnElement = this.refs.resizeColumn;
		let columnIndex = this._resizeColumnIndex;
		let nextColumnElement = this.refs.resizeNextColumn;
		let nextColumnIndex = columnIndex + 1;

		const languageDirection = Liferay.Language.direction[
			Liferay.ThemeDisplay.getLanguageId()
		];

		if (languageDirection === 'rtl') {
			columnElement = this.refs.resizeNextColumn;
			columnIndex = nextColumnIndex;
			nextColumnElement = this.refs.resizeColumn;
			nextColumnIndex = this._resizeColumnIndex;
		}

		const nextColumnRect = nextColumnElement.getBoundingClientRect();

		const maxPosition = nextColumnRect.x + nextColumnRect.width;
		const minPosition = columnElement.getBoundingClientRect().x;
		const position = Math.max(Math.min(event.clientX, maxPosition), minPosition);

		const column = this._resizeSectionColumns[columnIndex];
		const nextColumn = this._resizeSectionColumns[nextColumnIndex];

		const maxColumns = (parseInt(column.size, 10) || 1) + (parseInt(nextColumn.size, 10) || 1) - 1;

		const columns = Math.max(
			Math.round(
				((position - minPosition) / (maxPosition - minPosition)) *
				(maxColumns)
			),
			1
		);

		this._resizeSectionColumns = setIn(
			this._resizeSectionColumns,
			[columnIndex, 'size'],
			columns.toString()
		);

		this._resizeSectionColumns = setIn(
			this._resizeSectionColumns,
			[nextColumnIndex, 'size'],
			(maxColumns - columns + 1).toString()
		);

		if (languageDirection === 'rtl') {
			this._resizeHighlightedColumn = maxColumns - (
				this._resizeSectionColumns
					.slice(columnIndex)
					.map(column => parseInt(column.size, 10) || 1)
					.reduce((size, columnSize) => size + columnSize, 0)
			);
		}
		else {
			this._resizeHighlightedColumn = this._resizeSectionColumns
				.slice(0, nextColumnIndex)
				.map(column => parseInt(column.size, 10) || 1)
				.reduce((size, columnSize) => size + columnSize, 0) - 1;
		}
	}

	/**
	 * Callback executed when the mouse hovers over a section.
	 * @param {Event} event
	 * @private
	 */
	_handleBodyMouseUp() {
		if (this._resizing) {
			this._updateSectionColumns(this._resizeSectionColumns);

			this._clearResizing();
		}
	}

	/**
	 * @private
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
	 * Callback executed when a key is pressed on the focused section.
	 * @private
	 * @param {Event} event
	 */
	_handleSectionKeyUp(event) {
		if (this.activeItemId === this.rowId &&
			this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.section) {

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

				moveItem(this.store, MOVE_SECTION, moveItemPayload);
			}
		}
	}

	/**
	 * Callback executed when the remove section button is clicked.
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
	 * Updates section columns.
	 * @param {array} columns The section columns to update.
	 * @private
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
 * @static
 * @type {!Object}
 */
FragmentEntryLinkListSection.STATE = {

	/**
	 * Floating toolbar instance for internal use.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkListSection
	 * @type {object|null}
	 */
	_floatingToolbar: Config.internal().value(null),

	/**
	 * Index of the column being resized.
	 * @default 0
	 * @instance
	 * @memberOf FragmentEntryLinkListSection
	 * @private
	 * @type {number}
	 */
	_resizeColumnIndex: Config.internal().number().value(0),

	/**
	 * Index of the column that should be highlighted when resized.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkListSection
	 * @private
	 * @type {number}
	 */
	_resizeHighlightedColumn: Config.internal().number().value(null),

	/**
	 * Mouse position when the resize is started.
	 * @default 0
	 * @instance
	 * @memberOf FragmentEntryLinkListSection
	 * @private
	 * @type {number}
	 */
	_resizeInitialPosition: Config.internal().number().value(0),

	/**
	 * Copy of section columns for resizing.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkListSection
	 * @type {array}
	 */
	_resizeSectionColumns: Config.internal().array().value(null),

	/**
	 * If <code>true</code>, the user is resizing a column.
	 * @default false
	 * @instance
	 * @memberOf FragmentEntryLinkListSection

	 * @type {boolean}
	 */
	_resizing: Config.internal().bool().value(false),

	/**
	 * Section row.
	 * @default undefined
	 * @instance
	 * @memberof FragmentEntryLinkListSection
	 * @type {object}
	 */
	row: Config.object()
		.required(),

	/**
	 * Section row ID.
	 * @default undefined
	 * @instance
	 * @memberof FragmentEntryLinkListSection
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
		'layoutData',
		'selectedMappingTypes',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentEntryLinkListSection, templates);

export {ConnectedFragmentEntryLinkListSection, FragmentEntryLinkListSection};
export default FragmentEntryLinkListSection;