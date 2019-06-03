import {isFunction} from 'metal';

/**
 * Returns a list of countries
 * @callback callback
 * @return {array} Array of countries
 */
export default function getCountries(callback) {
	if (!isFunction(callback)) {
		throw new TypeError('Parameter callback must be a function');
	}

	Liferay.Service(
		'/country/get-countries',
		{
			active: true
		},
		callback
	);
}
