AUI.add(
	'module-admin',
	function(A) {
		var Lang = A.Lang;

		var ACTIVE = 'ACTIVE';
		var INSTALLED = 'INSTALLED';
		var RESOLVED = 'RESOLVED';
		var STARTING = 'STARTING';
		var STOPPING = 'STOPPING';
		var UNDEFINED = 'UNDEFINED';
		var UNINSTALLED = 'UNINSTALLED';

		var ModuleAdmin = {
			init: function(config) {
				var instance = this;

				instance._namespace = config.namespace;
				instance._redirectURL = config.redirectURL;
				instance._serviceURL = config.serviceURL;
			},

			setStartLevel: function(options) {
				var instance = this;

				_confirm(
					options.message,
					{
						ok: function() {
							instance._handleRequest(
								{
									data: {
										bundleId: options.bundleId,
										cmd: "setBundleStartLevel",
										startLevel: options.startLevel
									},
									success: function() {
										alert("setBundleStartLevel: success");
									}
								}
							);
						}
					}
				);
			},

			start: function(options) {
				var instance = this;

				_confirm(
					options.message,
					{
						ok: function() {
							options.action = 'start';

							instance._handleRequest(
								{
									data: {
										bundleId: options.bundleId,
										cmd: "startBundle"
									},
									success: A.bind(instance._updateState, instance, options)
								}
							);
						}
					}
				);
			},

			stop: function(options) {
				var instance = this;

				_confirm(
					options.message,
					{
						ok: function() {
							options.action = 'stop';

							instance._handleRequest(
								{
									data: {
										bundleId: options.bundleId,
										cmd: "stopBundle"
									},
									success: A.bind(instance._updateState, instance, options)
								}
							);
						}
					}
				);
			},

			uninstall: function(options) {
				var instance = this;

				_confirm(
					options.message,
					{
						ok: function() {
							options.action = 'uninstall';

							instance._handleRequest(
								{
									data: {
										bundleId: options.bundleId,
										cmd: "uninstallBundle"
									},
									success: function() {
										window.location.href = instance._redirectURL;
									}
								}
							);
						}
					}
				);
			},

			update: function(options) {
				var instance = this;

				_confirm(
					options.message,
					{
						ok: function() {
							options.action = 'update';

							instance._handleRequest(
								{
									data: {
										bundleId: options.bundleId,
										cmd: "updateBundle"
									},
									success: A.bind(instance._updateState, instance, options)
								}
							);
						}
					}
				);
			},

			_handlerError : function(event, id, obj) {
				var instance = this;

				// TODO
			},

			_handleRequest : function(options) {
				var instance = this;

				options = options || {};

				options.failure = options.failure || instance._handlerError;
				options.success = options.success || function(event, id, obj) {};

				var data = {};

				for (var property in options.data) {
					data[instance._namespace + property] = options.data[property];
				}

				A.io.request(
					instance._serviceURL,
					{
						data: data,
						dataType: 'json',
						on: {
							failure: function(event, id, obj) {
								options.failure(event, id, obj);
							},
							success: function(event, id, obj) {
								var response = this.get('responseData');

								options.success(event, response);
							}
						}
					}
				);
			},

			_updateState : function(options, event, response) {
				var instance = this;

				var action = options.action;
				var bundleId = options.bundleId;
				var namespace = instance._namespace;
				var newState = response.state ? response.state.toUpperCase() : UNDEFINED;

				var stateContainer = A.one('.module-admin-portlet span.state-' + bundleId);
				var currentState = stateContainer.html();

				if ((action == 'uninstall') && (newState == UNDEFINED)) {
					newState = UNINSTALLED;
				}
				else if ((currentState == RESOLVED) && (action == 'start') && (newState == STARTING)) {
					A.later(200, instance, A.bind(instance._handleResponse, instance, options));
				}

				if (newState == ACTIVE) {
					A.one('.' + namespace + 'start_' + bundleId).hide();
					A.one('.' + namespace + 'stop_' + bundleId).show();
				}
				else if (newState == RESOLVED) {
					A.one('.' + namespace + 'start_' + bundleId).show();
					A.one('.' + namespace + 'stop_' + bundleId).hide();
				}

				stateContainer.html(newState);
			}
		};

		var _confirm = function(msg, options) {
			var instance = this;

			options = options || {};

			instance._okCallback = function() {};
			instance._cancelCallback = function() {};

			var confirmDialog = instance._confirmDialog;

			if (!confirmDialog) {
				confirmDialog = new A.Dialog({
					align: Liferay.Util.Window.ALIGN_CENTER,
					modal: true,
					centered: true,
					buttons: [
						{
							label: 'OK',
							handler: function() {
								instance._okCallback();

								this.hide();
							}
						},
						{
							label: 'Cancel',
							handler: function() {
								instance._cancelCallback();

								this.hide();
							}
						}
					],
					render: true,
					width: 350,
					contentBox : A.Node.create('<div id="dialog" />')
				});
			}

			if (options.ok) {
				instance._okCallback = options.ok;
			}

			if (options.cancel) {
				instance._cancelCallback = options.cancel;
			}

			confirmDialog.set('bodyContent', msg).show();
		};

		Liferay.ModuleAdmin = ModuleAdmin;
	},
	'',
	{
		requires: ['aui-base', 'aui-dialog', 'aui-io-request']
	}
);