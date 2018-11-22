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
	getColumn,
	getFragmentColumn,
	getFragmentRowIndex,
	setIn,
	updateIn,
	updateLayoutData
};