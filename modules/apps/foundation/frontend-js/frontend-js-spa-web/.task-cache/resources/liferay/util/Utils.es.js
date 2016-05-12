define("frontend-js-spa-web@1.0.6/liferay/util/Utils.es", ['exports'], function (exports) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var Utils = function () {
		function Utils() {
			_classCallCheck(this, Utils);
		}

		Utils.getPortletBoundaryId = function getPortletBoundaryId(portletId) {
			return 'p_p_id_' + portletId + '_';
		};

		Utils.getPortletBoundaryIds = function getPortletBoundaryIds(portletIds) {
			return portletIds.map(function (portletId) {
				return Utils.getPortletBoundaryId(portletId);
			});
		};

		Utils.resetAllPortlets = function resetAllPortlets() {
			Utils.getPortletBoundaryIds(Liferay.Portlet.list).forEach(function (value, index, collection) {
				var portlet = document.querySelector('#' + value);

				if (portlet) {
					Liferay.Portlet.destroy(portlet);

					portlet.portletProcessed = false;
				}
			});

			Liferay.Portlet.readyCounter = 0;
		};

		return Utils;
	}();

	exports.default = Utils;
});
//# sourceMappingURL=Utils.es.js.map