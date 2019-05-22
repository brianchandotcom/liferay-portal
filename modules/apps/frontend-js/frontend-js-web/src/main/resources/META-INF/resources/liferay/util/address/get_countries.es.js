import {isDef} from 'metal';

/**
 * Returns a list of countries
 * @callback callback
 * @return {array} Array of countries
 */
export default function getCountries(callback) {
	if (isDef(callback)) {
		Liferay.Service(
			'/country/get-countries',
			{
				active: true
			},
			callback
		);
	}
}
