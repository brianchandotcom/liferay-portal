/**
 * Recursively inserts a value inside an object creating
 * a copy of the original target. It the object (or any in the path),
 * it's an Array, it will generate new Arrays, preserving the same structure.
 *
 * @param {!Array|!Object} Original object that will be copied
 * @param {!string[]} Array of strings used for reaching the deep property
 * @param {*} value Value to be inserted
 * @return {!Array|!Object} Copy of the original object with the new value
 * @review
 */

function setIn(object, keyPath, value) {
	const nextKey = keyPath[0];
	const target = object instanceof Array ?
		[...object] :
		Object.assign({}, object);

	let nextValue = value;

	if (keyPath.length > 1) {
		nextValue = setIn(
			object[nextKey] || {},
			keyPath.slice(1),
			value
		);
	}

	target[nextKey] = nextValue;

	return target;
}

export {setIn};