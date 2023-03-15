import {createNewGroup} from './utils';
import {CustomValue} from 'shared/util/records';
import {fromJS, Map} from 'immutable';
import {isArray} from 'lodash';
import {TIME_PERIOD_OPTIONS} from './constants';

/**
 * Create the valueIMap for a custom input.
 * @param {Array} params - Array of root keys for the valueIMap.
 * @returns {CustomValue} The params array converted into a deeply immutable valueIMap.
 */
export const createCustomValueMap = (
	params: {key: string; value: any}[]
): CustomValue => {
	let valueIMap = Map();

	params.forEach(({key, value}) => {
		if (isArray(value)) {
			valueIMap = valueIMap.set(key, fromJS(createNewGroup(value)));
		} else {
			valueIMap = valueIMap.set(key, value);
		}
	});

	return new CustomValue(valueIMap);
};

/**
 * Get the filter Criterion Map at index.
 * @param {CustomValue} valueIMap - The Immutable Map representing the custom input value.
 * @param {number} index - The index of the Criterion Map.
 * @returns {Map} The Criterion Map.
 */
export const getFilterCriterionIMap = (valueIMap: CustomValue, index: number) =>
	valueIMap.getIn(['criterionGroup', 'items', index]);

/**
 * Get the index where the first matching property name is found in the criteria list.
 * This is useful if we don't know where the criterion is at in the list, but we know the property name.
 * @param {CustomValue} valueIMap - The Immutable Map representing the custom input value.
 * @returns {number} The index of the matching criterion or -1 if not found.
 */
export const getIndexFromPropertyName = (
	valueIMap: CustomValue,
	propertyName: string
): number =>
	valueIMap
		.getIn(['criterionGroup', 'items'])
		.findIndex(entry => entry.get('propertyName') === propertyName);

/**
 * Get the operator name from the criterion at the specified index in the valueIMap.
 * @param {CustomValue} valueIMap - The Immutable Map representing the custom input value.
 * @returns {string} The operator name of the criterion at the specified index.
 */
export const getOperator = (valueIMap: CustomValue, index: number): string =>
	getPropertyValue(valueIMap, 'operatorName', index);

/**
 * Get the time period value from the valueIMap.
 * @param {CustomValue} valueIMap - The Immutable Map representing the custom input value.
 * @returns {string} The time period value from the criterion with a 'completeDate' property name.
 */
export const getCompleteDate = (valueIMap: CustomValue): string => {
	const index = getIndexFromPropertyName(valueIMap, 'completeDate');

	return getPropertyValue(valueIMap, 'value', index);
};

/**
 * Get the value of the propertyName at the specified index.
 * @param {CustomValue} valueIMap - The Immutable Map representing the custom input value.
 * @param {string} propertyName - The propertyName string in the criterion to update.
 * @param {number} index - The index of the criterion in the items list.
 * @returns {*} The value of the criterion propertyName at the specified index in the items list.
 */
export const getPropertyValue = (
	valueIMap: CustomValue,
	propertyName: string,
	index: number
): any => valueIMap.getIn(['criterionGroup', 'items', index, propertyName]);

/**
 * Get the time period label from the time period value.
 * @param {string} timePeriod - The time period value.
 * @returns {string} The time period label that matches the timePeriodValue.
 */
export const getTimePeriodLabel = (value: string): string => {
	const timePeriod = TIME_PERIOD_OPTIONS.find(
		timePeriod => timePeriod.value === value
	);

	return timePeriod ? timePeriod.label : null;
};

/**
 * Remove entries in valueIMap by their index in the criteria list.
 * @param {CustomValue} valueIMap - The Immutable Map representing the custom input value.
 * @param {Array} indexArray - Array of indexes to remove from the criteria list in valueIMap.
 * @returns {CustomValue} The valueIMap with the items at the indexes in indexArray removed.
 */
export const removeItemsByIndex = (
	valueIMap: CustomValue,
	indexArray: number[]
): CustomValue =>
	valueIMap.updateIn(['criterionGroup', 'items'], iList =>
		iList.filterNot((_, i) => indexArray.includes(i))
	) as CustomValue;

/**
 * Set the operator name from the criterion at the specified index in the valueIMap.
 * @param {CustomValue} valueIMap - The Immutable Map representing the custom input value.
 * @param {number} index - The index of the criterion in the items list.
 * @param {*} value - The value to update in the valueIMap.
 * @returns {CustomValue} The updated valueIMap.
 */
export const setOperator = (
	valueIMap: CustomValue,
	index: number,
	value: any
): CustomValue => setPropertyValue(valueIMap, 'operatorName', index, value);

/**
 * Set the time period value in the valueIMap.
 * @param {CustomValue} valueIMap - The Immutable Map representing the custom input value.
 * @param {string} value - The value to update for the time period.
 * @returns {CustomValue} The updated valueIMap.
 */
export const setCompleteDate = (
	valueIMap: CustomValue,
	completeDate: string
): CustomValue => {
	const index = getIndexFromPropertyName(valueIMap, 'completeDate');

	return setPropertyValue(valueIMap, 'value', index, completeDate);
};

/**
 * Set the value of the propertyName at the specified index.
 * @param {CustomValue} valueIMap - The Immutable Map representing the custom input value.
 * @param {string} propertyName - The propertyName string in the criterion to update.
 * @param {number} index - The index of the criterion in the items list.
 * @param {*} value - The value to update in the valueIMap.
 * @returns {CustomValue} The updated valueIMap.
 */
export const setPropertyValue = (
	valueIMap: CustomValue,
	propertyName: string,
	index: number,
	value: any
): CustomValue =>
	valueIMap.setIn(
		['criterionGroup', 'items', index, propertyName],
		value
	) as CustomValue;
