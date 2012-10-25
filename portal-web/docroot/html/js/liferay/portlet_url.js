AUI.add(
	'liferay-portlet-url',
	function(A) {
		var PortletURL = function(lifecycle, params) {
			var instance = this;

			instance.params = params || {};

			instance.options = {
				copyCurrentRenderParameters: null,
				doAsGroupId: null,
				doAsUserId: null,
				encrypt: null,
				escapeXML: null,
				lifecycle: lifecycle,
				name: null,
				p_l_id: themeDisplay.getPlid(),
				portletConfiguration: false,
				portletId: null,
				portletMode: null,
				resourceId: null,
				secure: null,
				windowState: null
			};

			instance._parameterMap = {
				javaClass: 'java.util.HashMap',
				map: {}
			};
		};

		PortletURL.prototype = {
			setCopyCurrentRenderParameters: function(copyCurrentRenderParameters) {
				var instance = this;

				instance.options.copyCurrentRenderParameters = copyCurrentRenderParameters;

				return instance;
			},

			setDoAsGroupId: function(doAsGroupId) {
				var instance = this;

				instance.options.doAsGroupId = doAsGroupId;

				return instance;
			},

			setDoAsUserId: function(doAsUserId) {
				var instance = this;

				instance.options.doAsUserId = doAsUserId;

				return instance;
			},

			setEncrypt: function(encrypt) {
				var instance = this;

				instance.options.encrypt = encrypt;

				return instance;
			},

			setEscapeXML: function(escapeXML) {
				var instance = this;

				instance.options.escapeXML = escapeXML;

				return instance;
			},

			setLifecycle: function(lifecycle) {
				var instance = this;

				instance.options.lifecycle = lifecycle;

				return instance;
			},

			setName: function(name) {
				var instance = this;

				instance.options.name = name;

				return instance;
			},

			setParameter: function(key, value) {
				var instance = this;

				instance.params[key] = value;

				return instance;
			},

			setPlid: function(plid) {
				var instance = this;

				instance.options.p_l_id = plid;

				return instance;
			},

			setPortletConfiguration: function(portletConfiguration) {
				var instance = this;

				instance.options.portletConfiguration = portletConfiguration;

				return instance;
			},

			setPortletId: function(portletId) {
				var instance = this;

				instance.options.portletId = portletId;

				return instance;
			},

			setPortletMode: function(portletMode) {
				var instance = this;

				instance.options.portletMode = portletMode;

				return instance;
			},

			setResourceId: function(resourceId) {
				var instance = this;

				instance.options.resourceId = resourceId;

				return instance;
			},

			setSecure: function(secure) {
				var instance = this;

				instance.options.secure = secure;

				return instance;
			},

			setWindowState: function(windowState) {
				var instance = this;

				instance.options.windowState = windowState;

				return instance;
			},

			toString: function() {
				var instance = this;

				instance._forceStringValues(instance.params);
				instance._forceStringValues(instance.options);

				instance._parameterMap.map = A.merge(
					instance._parameterMap.map,
					instance.params
				);

				var responseText = null;

				A.io.request(
					themeDisplay.getPathContext() + '/c/portal/portlet_url',
					{
						sync: true,
						data: instance._buildRequestData(),
						on: {
							complete: function(event, id, obj) {
								responseText = obj.responseText;
							}
						},
						type: 'GET'
					}
				);

				return responseText;
			},

			_buildRequestData: function() {
				var instance = this;

				var data = {};

				A.each(
					instance.options,
					function(value, key) {
						if (value !== null) {
							data[key] = [value].join('');
						}
					}
				);

				data.parameterMap = A.JSON.stringify(instance._parameterMap);

				return A.QueryString.stringify(data);
			},

			_forceStringValues: function(obj) {
				A.each(
					obj,
					function(value, key) {
						if (value !== null) {
							obj[key] = [value].join('');
						}
					}
				);

				return obj;
			}
		};

		A.mix(
			PortletURL,
			{
				createActionURL: function() {
					return new PortletURL('ACTION_PHASE');
				},

				createPermissionURL: function(portletResource, modelResource, modelResourceDescription, resourcePrimKey) {
					var redirect = location.href;

					var portletURL = PortletURL.createRenderURL();

					portletURL.setPortletId(86);

					portletURL.setWindowState('MAXIMIZED');

					portletURL.setDoAsGroupId(themeDisplay.getScopeGroupId());

					portletURL.setParameter('struts_action', '/portlet_configuration/edit_permissions');
					portletURL.setParameter('redirect', redirect);

					if (!themeDisplay.isStateMaximized()) {
						portletURL.setParameter('returnToFullPageURL', redirect);
					}

					portletURL.setParameter('portletResource', portletResource);
					portletURL.setParameter('modelResource', modelResource);
					portletURL.setParameter('modelResourceDescription', modelResourceDescription);
					portletURL.setParameter('resourcePrimKey', resourcePrimKey);

					return portletURL;
				},

				createRenderURL: function() {
					return new PortletURL('RENDER_PHASE');
				},

				createResourceURL: function() {
					return new PortletURL('RESOURCE_PHASE');
				}
			}
		);

		Liferay.PortletURL = PortletURL;
	},
	'',
	{
		requires: ['aui-base', 'aui-io-request', 'querystring-stringify-simple']
	}
);