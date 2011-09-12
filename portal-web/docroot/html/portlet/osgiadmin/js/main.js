AUI().use(
	'aui-base',
	function(A) {
		Liferay.namespace('OSGiAdmin');

		Liferay.OSGiAdmin.Util = {
			ACTIVE: 		'ACTIVE',
			INSTALLED: 		'INSTALLED',
			RESOLVED: 		'RESOLVED',
			STARTING: 		'STARTING',
			STOPPING: 		'STOPPING',
			UNINSTALLED: 	'UNINSTALLED',

			setStartLevel: function(options) {
				var instance = this;

				if (!confirm(Liferay.Language.get(options.message))) {
					return;
				}

				Liferay.Service.OA.OSGi.setBundleStartLevel({bundleId: options.bundleId, startLevel: options.startLevel}, function() {});
			},

			start: function(options) {
				var instance = this;

				if (!confirm(Liferay.Language.get(options.message))) {
					return;
				}

				options.action = 'start';

				Liferay.Service.OA.OSGi.startBundle({bundleId: options.bundleId}, A.bind(instance._handleResponse, instance, options));
			},

			stop: function(options) {
				var instance = this;

				if (!confirm(Liferay.Language.get(options.message))) {
					return;
				}

				options.action = 'stop';

				Liferay.Service.OA.OSGi.stopBundle({bundleId: options.bundleId}, A.bind(instance._handleResponse, instance, options));
			},

			uninstall: function(options) {
				var instance = this;

				if (!confirm(Liferay.Language.get(options.message))) {
					return;
				}

				options.action = 'uninstall';

				Liferay.Service.OA.OSGi.uninstallBundle({bundleId: options.bundleId}, A.bind(instance._handleResponse, instance, options));
			},

			update: function(options) {
				var instance = this;

				if (!confirm(Liferay.Language.get(options.message))) {
					return;
				}

				options.action = 'update';

				Liferay.Service.OA.OSGi.updateBundle({bundleId: options.bundleId}, A.bind(instance._handleResponse, instance, options));
			},

			_handleResponse: function(options) {
				var instance = this;

				Liferay.Service.OA.OSGi.getState({bundleId: options.bundleId}, A.bind(instance._updateState, instance, options));
			},

			_updateState: function(options, result) {
				var instance = this;

				console.log("_updateState", options, result);

				var action = options.action;
				var bundleId = options.bundleId;
				var namespace = options.namespace;
				var newState = result ? result.toUpperCase() : instance.UNDEFINED;

				var stateContainer = A.one('.portlet-osgi-admin span.state-' + bundleId);
				var currentState = stateContainer.html();

				if ((action == 'uninstall') && (newState == instance.UNDEFINED)) {
					newState = instance.UNINSTALLED;
				}
				else if (currentState == instance.RESOLVED && action == 'start' && newState == instance.STARTING) {
					A.later(200, instance, A.bind(instance._handleResponse, instance, options));
				}

				if (newState == instance.ACTIVE) {
					A.one('.' + namespace + 'start_' + bundleId).hide();
					A.one('.' + namespace + 'stop_' + bundleId).show();
				}
				else if (newState == instance.RESOLVED) {
					A.one('.' + namespace + 'start_' + bundleId).show();
					A.one('.' + namespace + 'stop_' + bundleId).hide();
				}

				stateContainer.html(newState);
			}
		};
	}
);