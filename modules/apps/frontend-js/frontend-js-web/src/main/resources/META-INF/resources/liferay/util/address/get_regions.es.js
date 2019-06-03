import {isFunction, isString} from 'metal';

/**
 * Returns a list of regions by country
 * @callback callback
 * @param {!string} selectKey The selected region ID
 * @return {array} Array of regions by country
 */
export default function getRegions(callback, selectKey) {
	if (!isFunction(callback)) {
		throw new TypeError('Parameter callback must be a function');
	}

	if (!isString(selectKey)) {
		throw new TypeError('Parameter selectKey must be a string');
	}

	Liferay.Service(
		'/region/get-regions',
		{
			active: true,
			countryId: parseInt(selectKey, 10)
		},
		callback
	);
}
