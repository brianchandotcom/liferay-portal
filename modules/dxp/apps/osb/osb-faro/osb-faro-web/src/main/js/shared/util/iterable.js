/**
 * Similar to Array#some. Returns true if any item in
 * the Iterable satisfies the predicate.
 * @param {Iterable.<*>} Iterable - The Iterable.
 * @param {Function} fn - The predicate
 * @return {boolean}
 */
export function some(iterable, fn) {
	for (const item of iterable) {
		if (fn(item)) return true;
	}

	return false;
}
