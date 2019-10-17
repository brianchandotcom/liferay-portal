/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import '../floating_toolbar/background_color/FloatingToolbarBackgroundColorPanel.es';

import '../floating_toolbar/layout_background_image/FloatingToolbarLayoutBackgroundImagePanel.es';

import '../floating_toolbar/spacing/FloatingToolbarSpacingPanel.es';

import './ColumnOverlayGrid.es';

import './FragmentEntryLink.es';
import {removeRowAction} from '../../actions/removeRow.es';
import {updateRowColumnsAction} from '../../actions/updateRowColumns.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';
import {getAssetFieldValue} from '../../utils/FragmentsEditorFetchUtils.es';
import {
	getItemMoveDirection,
	getItemPath,
	getRowIndex,
	itemIsInPath,
	editableIsMappedToAssetEntry
} from '../../utils/FragmentsEditorGetUtils.es';
import {
	moveRow,
	removeItem,
	setIn
} from '../../utils/FragmentsEditorUpdateUtils.es';
import {
	FLOATING_TOOLBAR_BUTTONS,
	FRAGMENTS_EDITOR_ITEM_TYPES,
	FRAGMENTS_EDITOR_ROW_TYPES
} from '../../utils/constants';
import FloatingToolbar from '../floating_toolbar/FloatingToolbar.es';
import templates from './FragmentEntryLinkListRow.soy';

/**
 * Creates a Fragment Entry Link List Row component.
 */
class FragmentEntryLinkListRow extends Component {
	/**
	 * @param {object} config
	 * @return {object[]} Floating toolbar buttons
	 */
	static _getFloatingToolbarButtons(config) {
		const buttons = [];

		buttons.push(FLOATING_TOOLBAR_BUTTONS.backgroundColor);

		const layoutBackgroundImageButton = {
			...FLOATING_TOOLBAR_BUTTONS.layoutBackgroundImage
		};

		if (
			config.backgroundImage &&
			(config.backgroundImage.mappedField ||
				config.backgroundImage.fieldId)
		) {
			layoutBackgroundImageButton.cssClass =
				'fragments-editor__floating-toolbar--mapped-field';
		}

		buttons.push(layoutBackgroundImageButton);

		buttons.push(FLOATING_TOOLBAR_BUTTONS.spacing);

		return buttons;
	}

	/**
	 * Checks if the given row should be highlighted
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
		let highlighted = false;

		if (dropTargetItemId) {
			const dropTargetPath = getItemPath(
				dropTargetItemId,
				dropTargetItemType,
				structure
			);

			const rowInDropTargetPath = itemIsInPath(
				dropTargetPath,
				rowId,
				FRAGMENTS_EDITOR_ITEM_TYPES.row
			);

			const row = structure.find(row => row.rowId === rowId);

			const rowIsDropTarget =
				dropTargetItemId === rowId &&
				dropTargetItemType === FRAGMENTS_EDITOR_ITEM_TYPES.row;

			highlighted =
				row.type !== FRAGMENTS_EDITOR_ROW_TYPES.sectionRow &&
				rowInDropTargetPath &&
				!rowIsDropTarget;
		}

		return highlighted;
	}

	/**
	 * @inheritdoc
	 */
	created() {
		this._handleBodyMouseLeave = this._handleBodyMouseLeave.bind(this);
		this._handleBodyMouseMove = this._handleBodyMouseMove.bind(this);
		this._handleBodyMouseUp = this._handleBodyMouseUp.bind(this);

		document.body.addEventListener(
			'mouseleave',
			this._handleBodyMouseLeave
		);
		document.body.addEventListener('mouseup', this._handleBodyMouseUp);
	}

	/**
	 * @inheritdoc
	 */
	disposed() {
		this._disposeFloatingToolbar();

		document.body.removeEventListener(
			'mouseleave',
			this._handleBodyMouseLeave
		);
		document.body.removeEventListener(
			'mousemove',
			this._handleBodyMouseMove
		);
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

		if (
			this.rowId === this.activeItemId &&
			this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.row
		) {
			columnResizerVisible = true;
		}

		nextState = setIn(
			nextState,
			['_backgroundImageValue'],
			this._getBackgroundImageValue()
		);

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
			FragmentEntryLinkListRow._isHighlighted(
				state.dropTargetItemId,
				state.dropTargetItemType,
				state.rowId,
				state.layoutData.structure
			)
		);

		if (nextState._resizing && nextState._resizeRowColumns) {
			nextState = setIn(nextState, ['columns'], state._resizeRowColumns);
		}

		return nextState;
	}

	/**
	 * @inheritdoc
	 */
	rendered() {
		if (
			this.rowId === this.activeItemId &&
			this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.row &&
			!this._resizing &&
			this.row.type !== FRAGMENTS_EDITOR_ROW_TYPES.sectionRow
		) {
			this._createFloatingToolbar();
		} else {
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
	 * Handle getAssetFieldValueURL changed
	 * @inheritDoc
	 * @review
	 */
	syncGetAssetFieldValueURL() {
		this._updateMappedBackgroundFieldValue();
	}

	/**
	 * Handle layoutData changed
	 * @inheritDoc
	 * @review
	 */
	syncLayoutData() {
		this._updateMappedBackgroundFieldValue();
	}

	/**
	 * Clears resizing properties.
	 * @private
	 */
	_clearResizing() {
		document.body.removeEventListener(
			'mousemove',
			this._handleBodyMouseMove
		);

		this._resizeColumnIndex = 0;
		this._resizeHighlightedColumn = null;
		this._resizeInitialPosition = 0;
		this._resizeRowColumns = null;
		this._resizing = false;
	}

	/**
	 * Creates a new instance of the floating toolbar.
	 * @private
	 */
	_createFloatingToolbar() {
		const config = {
			anchorElement: this.element,
			buttons: FragmentEntryLinkListRow._getFloatingToolbarButtons(
				this.row.config
			),
			item: this.row,
			itemId: this.rowId,
			itemType: FRAGMENTS_EDITOR_ITEM_TYPES.row,
			portalElement: document.body,
			store: this.store
		};

		if (this._floatingToolbar) {
			this._floatingToolbar.setState(config);
		} else {
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
	 * @private
	 */
	_getBackgroundImageValue() {
		if (this._mappedBackgroundFieldValue) {
			return this._mappedBackgroundFieldValue;
		}

		const {config} = this.row;

		if (!config) {
			return '';
		}

		if (typeof config.backgroundImage === 'string') {
			return config.backgroundImage;
		}

		if (typeof config.backgroundImage === 'object') {
			return config.backgroundImage.url;
		}

		return '';
	}

	/**
	 * Callback executed when a row loses focus.
	 * @private
	 * @review
	 */
	_handleBodyMouseLeave() {
		if (this._resizing) {
			this._clearResizing();
		}
	}

	/**
	 * Callback executed when a row is clicked.
	 * @param {Event} event
	 * @private
	 */
	_handleBodyMouseMove(event) {
		let columnElement = this.refs.resizeColumn;
		let columnIndex = this._resizeColumnIndex;
		let nextColumnElement = this.refs.resizeNextColumn;
		let nextColumnIndex = columnIndex + 1;

		const languageDirection =
			Liferay.Language.direction[Liferay.ThemeDisplay.getLanguageId()];

		if (languageDirection === 'rtl') {
			columnElement = this.refs.resizeNextColumn;
			columnIndex = nextColumnIndex;
			nextColumnElement = this.refs.resizeColumn;
			nextColumnIndex = this._resizeColumnIndex;
		}

		const nextColumnRect = nextColumnElement.getBoundingClientRect();

		const maxPosition = nextColumnRect.left + nextColumnRect.width;
		const minPosition = columnElement.getBoundingClientRect().left;
		const position = Math.max(
			Math.min(event.clientX, maxPosition),
			minPosition
		);

		const column = this._resizeRowColumns[columnIndex];
		const nextColumn = this._resizeRowColumns[nextColumnIndex];

		const maxColumns =
			(parseInt(column.size, 10) || 1) +
			(parseInt(nextColumn.size, 10) || 1) -
			1;

		const columns = Math.max(
			Math.round(
				((position - minPosition) / (maxPosition - minPosition)) *
					maxColumns
			),
			1
		);

		this._resizeRowColumns = setIn(
			this._resizeRowColumns,
			[columnIndex, 'size'],
			columns.toString()
		);

		this._resizeRowColumns = setIn(
			this._resizeRowColumns,
			[nextColumnIndex, 'size'],
			(maxColumns - columns + 1).toString()
		);

		if (languageDirection === 'rtl') {
			this._resizeHighlightedColumn =
				maxColumns -
				this._resizeRowColumns
					.slice(columnIndex)
					.map(column => parseInt(column.size, 10) || 1)
					.reduce((size, columnSize) => size + columnSize, 0);
		} else {
			this._resizeHighlightedColumn =
				this._resizeRowColumns
					.slice(0, nextColumnIndex)
					.map(column => parseInt(column.size, 10) || 1)
					.reduce((size, columnSize) => size + columnSize, 0) - 1;
		}
	}

	/**
	 * Callback executed when the mouse hovers over a row.
	 * @param {Event} event
	 * @private
	 */
	_handleBodyMouseUp() {
		if (this._resizing) {
			this._updateRowColumns(this._resizeRowColumns);

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
		this._resizeRowColumns = this.row.columns;
		this._resizing = true;

		document.body.addEventListener('mousemove', this._handleBodyMouseMove);
	}

	/**
	 * Callback executed when a key is pressed on the focused row.
	 * @private
	 * @param {KeyboardEvent} event
	 */
	_handleRowKeyUp(event) {
		if (
			this.activeItemId === this.rowId &&
			this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.row
		) {
			const direction = getItemMoveDirection(event.keyCode);

			if (direction) {
				moveRow(
					direction,
					getRowIndex(this.layoutData.structure, this.rowId),
					this.store,
					this.layoutData.structure
				);
			}
		}
	}

	/**
	 * Callback executed when the remove row button is clicked.
	 * @param {Event} event
	 * @private
	 */
	_handleRowRemoveButtonClick(event) {
		event.stopPropagation();

		removeItem(this.store, removeRowAction(this.hoveredItemId));
	}

	/**
	 * Updates mapped field value
	 * @private
	 * @review
	 */
	_updateMappedBackgroundFieldValue() {
		if (
			this.getAssetFieldValueURL &&
			this.row.config.backgroundImage &&
			editableIsMappedToAssetEntry(this.row.config.backgroundImage)
		) {
			getAssetFieldValue(
				this.row.config.backgroundImage.classNameId,
				this.row.config.backgroundImage.classPK,
				this.row.config.backgroundImage.fieldId
			).then(response => {
				const {fieldValue} = response;

				if (
					fieldValue &&
					fieldValue.url !== this._mappedBackgroundFieldValue
				) {
					this._mappedBackgroundFieldValue = fieldValue.url;
				}
			});
		} else {
			this._mappedBackgroundFieldValue = null;
		}
	}

	/**
	 * Updates row columns.
	 * @param {Array} columns The row columns to update.
	 * @private
	 */
	_updateRowColumns(columns) {
		this.store.dispatch(updateRowColumnsAction(columns, this.rowId));
	}
}

/**
 * State definition.
 * @static
 * @type {!Object}
 */
FragmentEntryLinkListRow.STATE = {
	/**
	 * Floating toolbar instance for internal use.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkListRow
	 * @type {object|null}
	 */
	_floatingToolbar: Config.internal().value(null),

	/**
	 * Mapped background field value
	 * @instance
	 * @memberOf FragmentEntryLinkListRow
	 * @private
	 * @review
	 * @type {string}
	 */
	_mappedBackgroundFieldValue: Config.internal().string(),

	/**
	 * Index of the column being resized.
	 * @default 0
	 * @instance
	 * @memberOf FragmentEntryLinkListRow
	 * @private
	 * @type {number}
	 */
	_resizeColumnIndex: Config.internal()
		.number()
		.value(0),

	/**
	 * Index of the column that should be highlighted when resized.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkListRow
	 * @private
	 * @type {number}
	 */
	_resizeHighlightedColumn: Config.internal()
		.number()
		.value(null),

	/**
	 * Mouse position when the resize is started.
	 * @default 0
	 * @instance
	 * @memberOf FragmentEntryLinkListRow
	 * @private
	 * @type {number}
	 */
	_resizeInitialPosition: Config.internal()
		.number()
		.value(0),

	/**
	 * Copy of row columns for resizing.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkListRow
	 * @type {Array}
	 */
	_resizeRowColumns: Config.internal()
		.array()
		.value(null),

	/**
	 * If <code>true</code>, the user is resizing a column.
	 * @default false
	 * @instance
	 * @memberOf FragmentEntryLinkListRow

	 * @type {boolean}
	 */
	_resizing: Config.internal()
		.bool()
		.value(false),

	/**
	 * Row.
	 * @default undefined
	 * @instance
	 * @memberof FragmentEntryLinkListRow
	 * @type {object}
	 */
	row: Config.object().required(),

	/**
	 * Row ID.
	 * @default undefined
	 * @instance
	 * @memberof FragmentEntryLinkListRow
	 * @type {string}
	 */
	rowId: Config.string().required()
};

const ConnectedFragmentEntryLinkListRow = getConnectedComponent(
	FragmentEntryLinkListRow,
	[
		'activeItemId',
		'activeItemType',
		'dropTargetBorder',
		'dropTargetItemId',
		'dropTargetItemType',
		'getAssetFieldValueURL',
		'hoveredItemId',
		'hoveredItemType',
		'layoutData',
		'mappingFieldsURL',
		'selectedMappingTypes',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentEntryLinkListRow, templates);

export {ConnectedFragmentEntryLinkListRow, FragmentEntryLinkListRow};
export default FragmentEntryLinkListRow;
