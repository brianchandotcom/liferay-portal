import {DRAG_POSITIONS} from '../reducers/placeholders.es';

/**
 * Inserts an element in the given position of a given array and returns
 * a copy of the array
 * @param {!Array} array
 * @param {*} element
 * @param {!number} position
 */
function add(array, element, position) {
	const newArray = [...array];

	newArray.splice(position, 0, element);

	return newArray;
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
			section => section.columns.find(
				_column => _column.columnId === columnId
			)
		)
		.filter(column => column)
		.find(column => column);
}

/**
 * Returns the position in the structure of the given section
 * @param {object} structure
 * @param {number} targetSectionId
 * @param {string} targetBorder
 * @return {number}
 */
function getDropSectionPosition(
	structure,
	targetSectionId,
	targetBorder
) {
	let position = structure.length;

	const targetPosition = structure.findIndex(
		section => section.rowId === targetSectionId
	);

	if (targetPosition > -1 && targetBorder) {
		if (targetBorder === DRAG_POSITIONS.top) {
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
			section => section.columns.find(
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
		row => {
			return row.columns.find(
				column => {
					return column.fragmentEntryLinkIds.find(
						_fragmentEntryLinkId => (
							_fragmentEntryLinkId === fragmentEntryLinkId
						)
					);
				}
			);
		}
	);
}

/**
 * Removes from the given array the element in the given position and
 * returns a new array
 * @param {!Array} array
 * @param {!number} position
 */
function remove(array, position) {
	const newArray = [...array];

	newArray.splice(position, 1);

	return newArray;
}

/**
 * Recursively inserts a value inside an object creating
 * a copy of the original target. It the object (or any in the path),
 * it's an Array, it will generate new Arrays, preserving the same structure.
 * @param {!Array|!Object} object Original object that will be copied
 * @param {!Array<string>} keyPath Array of strings used for reaching the deep property
 * @param {*} value Value to be inserted
 * @return {Array|Object} Copy of the original object with the new value
 * @review
 */
function setIn(object, keyPath, value) {
	return updateIn(
		object,
		keyPath,
		() => value
	);
}

/**
 * Recursively inserts the value returned from updater inside an object creating
 * a copy of the original target. It the object (or any in the path),
 * it's an Array, it will generate new Arrays, preserving the same structure.
 * Updater receives the previous value or defaultValue and returns a new value.
 * @param {!Array|Object} object Original object that will be copied
 * @param {!Array<string>} keyPath Array of strings used for reaching the deep property
 * @param {!function} updater
 * @param {*} defaultValue
 */
function updateIn(object, keyPath, updater, defaultValue) {
	const nextKey = keyPath[0];
	const target = object instanceof Array ?
		[...object] :
		Object.assign({}, object);

	if (keyPath.length > 1) {
		target[nextKey] = updateIn(
			object[nextKey] || {},
			keyPath.slice(1),
			updater,
			defaultValue
		);
	}
	else {
		let nextValue = target[nextKey];

		if (typeof nextValue === 'undefined') {
			nextValue = defaultValue;
		}

		target[nextKey] = updater(nextValue);
	}

	return target;
}

/**
 * Update layoutData on backend
 * @param {!string} updateLayoutPageTemplateDataURL
 * @param {!string} portletNamespace
 * @param {!string} classNameId
 * @param {!string} classPK
 * @param {!object} data
 * @return {Promise}
 * @review
 */
function updateLayoutData(
	updateLayoutPageTemplateDataURL,
	portletNamespace,
	classNameId,
	classPK,
	data
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}classNameId`, classNameId);
	formData.append(`${portletNamespace}classPK`, classPK);

	formData.append(
		`${portletNamespace}data`,
		JSON.stringify(data)
	);

	return fetch(
		updateLayoutPageTemplateDataURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	);
}

export {
	add,
	getColumn,
	getDropSectionPosition,
	getFragmentColumn,
	getFragmentRowIndex,
	remove,
	setIn,
	updateIn,
	updateLayoutData
};