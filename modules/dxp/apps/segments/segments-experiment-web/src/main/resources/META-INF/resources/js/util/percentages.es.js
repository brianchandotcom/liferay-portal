/**
 * Example: `0.22` => `22`
 */
export function indexToPercentageNumber(index) {
	return parseInt(index * 100, 10);
}

/**
 * Example: `0.22` => `"22%"`
 */
export function indexToPercentageString(index) {
	return indexToPercentageNumber(index) + '%';
}

/**
 * Example: `22` => `0.22`
 */
export function percentageNumberToIndex(percentageNumber) {
	const fixedIndexString = parseFloat(percentageNumber / 100).toFixed(2);

	return parseFloat(fixedIndexString);
}

export const MAX_CONFIDENCE_LEVEL = 99;
export const MIN_CONFIDENCE_LEVEL = 80;

export const INITIAL_CONFIDENCE_LEVEL = 95;
