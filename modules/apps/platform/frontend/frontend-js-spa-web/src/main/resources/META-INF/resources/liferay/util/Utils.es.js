'use strict';

class Utils {
	static getPortletBoundaryId(portletId) {
		return 'p_p_id_' + portletId + '_';
	}

	static getPortletBoundaryIds(portletIds) {
		return portletIds.map(
			function(portletId) {
				return Utils.getPortletBoundaryId(portletId);
			}
		);
	}

	static resetAllPortlets() {
		Utils.getPortletBoundaryIds(Liferay.Portlet.list).forEach(
			function(value, index, collection) {
				var portlet = document.querySelector('#' + value);

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