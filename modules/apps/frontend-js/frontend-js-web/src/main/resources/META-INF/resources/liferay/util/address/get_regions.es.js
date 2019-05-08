import {
	isDef,
	isString
} from 'metal';

/**
 * Returns a list of regions by country
 * @callback callback
 * @param {!string} selectKey The selected region ID
 * @return {array} Array of regions by country
 */
export default function getRegions(callback, selectKey) {
	if (isDef(callback) && isString(selectKey)) {
		Liferay.Service(
			'/region/get-regions',
			{
				active: true,
				countryId: parseInt(selectKey, 10)
			},
			callback
		);
	}
}