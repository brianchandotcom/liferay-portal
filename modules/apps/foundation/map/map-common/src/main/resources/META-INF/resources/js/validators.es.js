import core from 'metal';

/**
 * Checks if the given node is an instance of HTMLInputElement.
 * @param {*} node Node to be tested
 * @return {boolean}
 * @review
 */
function isInputNode(node) {
	return node instanceof HTMLInputElement;
}

/**
 * Checks if the given set is a subset of the specified superset.
 * @param {Array[]} superset Group of valid elements.
 * @return {boolean}
 * @review
 */
function isSubsetOf(superset) {
	return subset => {
		for (let element of subset) {
			if (superset.indexOf(element) === -1) {
				return false;
			}
		}

		return true;
	};
}

export {isInputNode, isSubsetOf};