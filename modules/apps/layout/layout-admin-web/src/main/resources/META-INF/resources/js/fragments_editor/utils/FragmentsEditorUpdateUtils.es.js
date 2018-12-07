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
 * @param {!array} fragmentEntryLinkIds
 * @return {Promise}
 * @review
 */
function updateLayoutData(
	updateLayoutPageTemplateDataURL,
	portletNamespace,
	classNameId,
	classPK,
	data,
	fragmentEntryLinkIds
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}classNameId`, classNameId);
	formData.append(`${portletNamespace}classPK`, classPK);

	formData.append(
		`${portletNamespace}data`,
		JSON.stringify(data)
	);

	if (fragmentEntryLinkIds) {
		formData.append(
			`${portletNamespace}fragmentEntryLinkIds`,
			JSON.stringify(fragmentEntryLinkIds)
		);
	}

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
	remove,
	setIn,
	updateIn,
	updateLayoutData
};