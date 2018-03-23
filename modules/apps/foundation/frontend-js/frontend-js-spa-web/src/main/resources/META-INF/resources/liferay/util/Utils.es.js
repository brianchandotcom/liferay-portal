'use strict';

const MAX_TIMEOUT = Math.pow(2, 31) - 1;

/**
 * Utils
 *
 * A collection of utilities used by this module
 */

class Utils {

	/**
	 * Returns the maximum number allowed by the `setTimeout` function
	 * @return {!Number} The number
	 */

	static getMaxTimeout() {
		return MAX_TIMEOUT;
	}

	/**
	 * Given a portletId, returns the ID of the portlet's boundary DOM element
	 * @param  {!String} portletId The portlet ID
	 * @return {!String} The portlet boundary ID
	 */

	static getPortletBoundaryId(portletId) {
		return 'p_p_id_' + portletId + '_';
	}

	/**
	 * Given an array of portlet IDs, returns an array of portlet boundary IDs
	 * @param  {!Array} The collection of portletIds
	 * @return {!Array} The collection of portlet boundary IDs
	 */

	static getPortletBoundaryIds(portletIds) {
		return portletIds.map(
			function(portletId) {
				return Utils.getPortletBoundaryId(portletId);
			}
		);
	}

	/**
	 * Destroys all rendered portlets on the page
	 */

	static resetAllPortlets() {
		Utils.getPortletBoundaryIds(Liferay.Portlet.list).forEach(
			function(value, index, collection) {
				let portlet = document.querySelector('#' + value);

				if (portlet) {
					Liferay.Portlet.destroy(portlet);

					portlet.portletProcessed = false;
				}
			}
		);

		Liferay.Portlet.readyCounter = 0;
	}
}

export default Utils;