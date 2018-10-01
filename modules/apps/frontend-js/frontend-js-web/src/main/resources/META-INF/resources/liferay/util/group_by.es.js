/**
 * Creates an object composed of keys generated from the results of running each element
 * of collection thru iteratee. The order of grouped values is determined by the order
 * they occur in collection. The corresponding value of each key is an array of elements
 * responsible for generating the key. The iteratee is invoked with one argument: (value).
 * Call: groupBy(collection, [iteratee=_.identity])
 * @param {array | object} The collection to iterate over.
 * @param {iteratee} The iteratee to transform keys.
 * @review
 */

export default function(collection, iteratee) {
	var result = {};
	var key;

	collection.map(
		elem => {
			if (typeof (iteratee) === 'string') {
				key = elem[iteratee];
			}
			else if (typeof (iteratee) === 'function') {
				key = iteratee(elem);
			}

			if (!result.hasOwnProperty(key)) {
				result[key] = [];
			}

			result[key].push(elem);
			return elem;
		}
	);

	return result;
}