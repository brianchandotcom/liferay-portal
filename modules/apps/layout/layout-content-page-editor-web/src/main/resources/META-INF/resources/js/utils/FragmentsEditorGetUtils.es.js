import {FRAGMENTS_EDITOR_ITEM_BORDERS, FRAGMENTS_EDITOR_ITEM_TYPES} from '../utils/constants';

const ARROW_DOWN_KEYCODE = 40;

const ARROW_UP_KEYCODE = 38;

const MOVE_ITEM_DIRECTIONS = {
	DOWN: 1,
	UP: -1
};

/**
 * @param {object} objectToClone
 * @returns {object}
 */
function deepClone(objectToClone) {
	let cloned = objectToClone;

	if (typeof cloned == 'object' && cloned !== null) {
		if (Array.isArray(cloned)) {
			cloned = objectToClone.map(
				arrayItem => deepClone(arrayItem)
			);
		}
		else {
			cloned = Object.assign({}, cloned);

			for (let clonedKey in cloned) {
				cloned[clonedKey] = deepClone(cloned[clonedKey]);
			}
		}
	}

	return cloned;
}

/**
 * Returns the column with the given columnId
 * @param {Object} structure
 * @param {string} columnId
 * @return {Object}
 */
function getColumn(structure, columnId) {
	return structure
		.map(
			row => row.columns.find(
				_column => _column.columnId === columnId
			)
		)
		.filter(column => column)
		.find(column => column);
}

/**
 * Returns the position in the structure of the given row
 * @param {object} structure
 * @param {number} targetRowId
 * @param {string} targetBorder
 * @return {number}
 */
function getDropRowPosition(
	structure,
	targetRowId,
	targetBorder
) {
	let position = structure.length;

	const targetPosition = structure.findIndex(
		row => row.rowId === targetRowId
	);

	if (targetPosition > -1 && targetBorder) {
		if (targetBorder === FRAGMENTS_EDITOR_ITEM_BORDERS.top) {
			position = targetPosition;
		}
		else {
			position = targetPosition + 1;
		}
	}

	return position;
}

/**
 * Returns the column that contains the fragment
 * with the given fragmentEntryLinkId.
 *
 * @param {Array} structure
 * @param {string} fragmentEntryLinkId
 * @return {Object}
 */
function getFragmentColumn(structure, fragmentEntryLinkId) {
	return structure
		.map(
			row => row.columns.find(
				_column => _column.fragmentEntryLinkIds.find(
					fragmentId => fragmentId === fragmentEntryLinkId
				)
			)
		)
		.filter(column => column)
		.find(column => column);
}

/**
 * Returns the row index of a given fragmentEntryLinkId.
 * -1 if it is not present.
 *
 * @param {array} structure
 * @param {string} fragmentEntryLinkId
 * @return {number}
 */
function getFragmentRowIndex(structure, fragmentEntryLinkId) {
	return structure.findIndex(
		row => row.columns.find(
			column => column.fragmentEntryLinkIds.find(
				_fragmentEntryLinkId => (
					_fragmentEntryLinkId === fragmentEntryLinkId
				)
			)
		)
	);
}

/**
 * @param {string} keycode
 * @return {MOVE_ITEM_DIRECTIONS}
 * @review
 */
function getItemMoveDirection(keycode) {
	let direction = null;

	if (keycode === ARROW_UP_KEYCODE) {
		direction = MOVE_ITEM_DIRECTIONS.UP;
	}
	else if (keycode === ARROW_DOWN_KEYCODE) {
		direction = MOVE_ITEM_DIRECTIONS.DOWN;
	}

	return direction;
}

/**
 * For a given itemId and itemType, returns an array
 * with the whole path of active elements.
 * @param {string|null} itemId
 * @param {string|null} itemType
 * @param {object[]} structure
 * @return {{itemId: string, itemType}[]}
 */
function getItemPath(itemId, itemType, structure) {
	let itemPath = [];

	if (itemId && itemType && structure.length) {
		itemPath = [
			{
				itemId,
				itemType
			}
		];

		if (itemType === FRAGMENTS_EDITOR_ITEM_TYPES.editable) {
			const [fragmentEntryLinkId] = itemId.split('-');

			if (fragmentEntryLinkId) {
				itemPath = [
					...itemPath,
					...getItemPath(
						fragmentEntryLinkId,
						FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
						structure
					)
				];
			}
		}
		else if (itemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment) {
			const column = [].concat(
				...structure.map(row => row.columns)
			).find(
				_column => _column.fragmentEntryLinkIds.indexOf(itemId) !== -1
			);

			if (column) {
				itemPath = [
					...itemPath,
					...getItemPath(
						column.columnId,
						FRAGMENTS_EDITOR_ITEM_TYPES.column,
						structure
					)
				];
			}
		}
		else if (itemType === FRAGMENTS_EDITOR_ITEM_TYPES.column) {
			const row = structure.find(
				row => row.columns.find(
					column => column.columnId === itemId
				)
			);

			if (row) {
				itemPath = [
					...itemPath,
					...getItemPath(
						row.rowId,
						FRAGMENTS_EDITOR_ITEM_TYPES.row,
						structure
					)
				];
			}
		}
	}

	return itemPath;
}

/**
 * Get the fragmentEntryLinkIds of the fragments inside the given row
 * @param {array} row
 * @return {string[]}
 * @review
 */
function getRowFragmentEntryLinkIds(row) {
	let fragmentEntryLinkIds = [];

	row.columns.forEach(
		column => {
			fragmentEntryLinkIds = fragmentEntryLinkIds.concat(
				column.fragmentEntryLinkIds
			);
		}
	);

	return fragmentEntryLinkIds;
}

/**
 * Returns the index of the row with the given rowId
 * @param {array} structure
 * @param {string} rowId
 * @return {number}
 */
function getRowIndex(structure, rowId) {
	return structure.findIndex(
		row => (row.rowId === rowId)
	);
}

/**
 * Get target item border from the direction the item is moving in
 * @param {!string} direction
 * @return {FRAGMENTS_EDITOR_ITEM_BORDERS}
 * @review
 */
function getTargetBorder(direction) {
	let targetBorder = null;

	if (direction === MOVE_ITEM_DIRECTIONS.UP) {
		targetBorder = FRAGMENTS_EDITOR_ITEM_BORDERS.top;
	}
	else if (direction === MOVE_ITEM_DIRECTIONS.DOWN) {
		targetBorder = FRAGMENTS_EDITOR_ITEM_BORDERS.bottom;
	}

	return targetBorder;
}

/**
 * Get widget from the widgets tree by portletId
 * @param {!array} widgets
 * @param {!string} portletId
 * @return {object}
 * @review
 */
function getWidget(widgets, portletId) {
	let widget = null;

	for (const widgetCategory of widgets) {
		const {categories = [], portlets = []} = widgetCategory;
		const categoryPortlet = portlets.find(_portlet => _portlet.portletId === portletId);
		const subCategoryPortlet = getWidget(categories, portletId);

		if (categoryPortlet) {
			widget = categoryPortlet;
		}

		if (subCategoryPortlet) {
			widget = subCategoryPortlet;
		}
	}

	return widget;
}

/**
 * Get widget path from the widgets tree by portletId
 * @param {!array} widgets
 * @param {!string} portletId
 * @param {string[]} [_path=['widgets']]
 * @return {object}
 * @review
 */
function getWidgetPath(widgets, portletId, _path = ['widgets']) {
	let widgetPath = null;

	for (let categoryIndex = 0; categoryIndex < widgets.length; categoryIndex += 1) {
		const {categories = [], portlets = []} = widgets[categoryIndex];

		const categoryPortletIndex = portlets.findIndex(
			_portlet => _portlet.portletId === portletId
		);

		const subCategoryPortletPath = getWidgetPath(
			categories,
			portletId,
			[
				..._path,
				categoryIndex,
				'categories'
			]
		);

		if (categoryPortletIndex !== -1) {
			widgetPath = [
				..._path,
				categoryIndex,
				'portlets',
				categoryPortletIndex
			];
		}

		if (subCategoryPortletPath) {
			widgetPath = subCategoryPortletPath;
		}
	}

	return widgetPath;
}

/**
 * @param {object} path
 * @param {string} itemId
 * @param {string} itemType
 * @return {boolean} Item is in path
 * @review
 */
function itemIsInPath(path, itemId, itemType) {
	return path.some(
		pathItem => pathItem.itemId === itemId && pathItem.itemType === itemType
	);
}

export {
	deepClone,
	getColumn,
	getDropRowPosition,
	getItemPath,
	getFragmentColumn,
	getFragmentRowIndex,
	getItemMoveDirection,
	getRowFragmentEntryLinkIds,
	getRowIndex,
	getTargetBorder,
	getWidget,
	getWidgetPath,
	itemIsInPath
};