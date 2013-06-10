AUI.add(
	'liferay-portlet-url',
	function(A) {
		var Util = Liferay.Util;

		var PortletURL = function(lifecycle, params, basePortletURL) {
			var instance = this;

			instance.params = {};

			instance.reservedParams = {
				controlPanelCategory: null,
				doAsGroupId: null,
				doAsUserId: null,
				doAsUserLanguageId: null,
				p_auth: null,
				p_auth_secret: null,
				p_f_id: null,
				p_j_a_id: null,
				p_l_id: null,
				p_l_reset: null,
				p_p_auth: null,
				p_p_cacheability: null,
				p_p_col_count: null,
				p_p_col_id: null,
				p_p_col_pos: null,
				p_p_i_id: null,
				p_p_id: null,
				p_p_isolated: null,
				p_p_lifecycle: null,
				p_p_mode: null,
				p_p_resource_id: null,
				p_p_state: null,
				p_p_state_rcv: null,
				p_p_static: null,
				p_p_url_type: null,
				p_p_width: null,
				p_t_lifecycle: null,
				p_v_l_s_g_id: null,
				refererGroupId: null,
				refererPlid: null,
				saveLastPath: null,
				scroll: null
			};

			instance.options = {
				basePortletURL: basePortletURL,
				escapeXML: null,
				secure: null
			};

			if (!basePortletURL) {
				instance.options.basePortletURL = themeDisplay.getPathContext() + themeDisplay.getPathMain() + '/portal/layout?p_l_id=' + themeDisplay.getPlid();
			}

			A.each(
				params,
				function(item, index, collection) {
					if (item) {
						if (instance._isReservedParam(index)) {
							instance.reservedParams[index] = item;
						}
						else {
							instance.params[index] = item;
						}
					}
				}
			);

			if (lifecycle) {
				instance.setLifecycle(lifecycle);
			}
		};

		PortletURL.prototype = {
			/*
			 * @deprecated
			 */
			setCopyCurrentRenderParameters: function() {
				var instance = this;

				return instance;
			},

			setDoAsGroupId: function(doAsGroupId) {
				var instance = this;

				instance.reservedParams.doAsGroupId = doAsGroupId;

				return instance;
			},

			setDoAsUserId: function(doAsUserId) {
				var instance = this;

				instance.reservedParams.doAsUserId = doAsUserId;

				return instance;
			},

			/*
			 * @deprecated
			 */
			setEncrypt: function() {
				var instance = this;

				return instance;
			},

			setEscapeXML: function(escapeXML) {
				var instance = this;

				instance.options.escapeXML = escapeXML;

				return instance;
			},

			setLifecycle: function(lifecycle) {
				var instance = this;

				var reservedParams = instance.reservedParams;

				if (lifecycle === PortletURL.ACTION_PHASE) {
					reservedParams.p_p_lifecycle = PortletURL.ACTION_PHASE;
				}
				else if (lifecycle === PortletURL.RENDER_PHASE) {
					reservedParams.p_p_lifecycle = PortletURL.RENDER_PHASE;
				}
				else if (lifecycle === PortletURL.RESOURCE_PHASE) {
					reservedParams.p_p_lifecycle = PortletURL.RESOURCE_PHASE;
					reservedParams.p_p_cacheability = 'cacheLevelPage';
				}

				return instance;
			},

			setName: function(name) {
				var instance = this;

				instance.setParameter('javax.portlet.action', name);

				return instance;
			},

			setParameter: function(key, value) {
				var instance = this;

				if (instance._isReservedParam(key)) {
					instance.reservedParams[key] = value;
				}
				else {
					instance.params[key] = value;
				}

				return instance;
			},

			setPlid: function(plid) {
				var instance = this;

				instance.reservedParams.p_l_id =  plid;

				return instance;
			},

			/*
			 * @deprecated
			 */
			setPortletConfiguration: function() {
				var instance = this;

				return instance;
			},

			setPortletId: function(portletId) {
				var instance = this;

				instance.reservedParams.p_p_id = portletId;

				return instance;
			},

			setPortletMode: function(portletMode) {
				var instance = this;

				instance.reservedParams.p_p_mode = portletMode;

				return instance;
			},

			setResourceId: function(resourceId) {
				var instance = this;

				instance.reservedParams.p_p_resource_id = resourceId;

				return instance;
			},

			setSecure: function(secure) {
				var instance = this;

				instance.options.secure = secure;

				return instance;
			},

			setWindowState: function(windowState) {
				var instance = this;

				instance.reservedParams.p_p_state = windowState;

				return instance;
			},

			toString: function() {
				var instance = this;

				var options = instance.options;

				var reservedParams = instance.reservedParams;

				var resultURL = new A.Url(options.basePortletURL);

				var portletId = reservedParams.p_p_id;

				if (!portletId) {
					portletId = resultURL.getParameter('p_p_id');
				}

				var namespacePrefix = Util.getPortletNamespace(portletId);

				A.each(
					reservedParams,
					function (item, index, collection) {
						if (item) {
							resultURL.setParameter(index, item);
						}
					}
				);

				A.each(
					instance.params,
					function(item, index, collection) {
						if (item) {
							resultURL.setParameter(namespacePrefix + index, item);
						}
					}
				);

				if (options.secure) {
					resultURL.setProtocol('https');
				}

				if (options.escapeXML) {
					return Util.escapeHTML(resultURL.toString());
				}
				else {
					return resultURL.toString();
				}
			},

			_isReservedParam: function(paramName) {
				var instance = this;

				var result = false;

				A.each(
					instance.reservedParams,
					function(item, index, collection) {
						if (index === paramName) {
							result = true;
						}
					}
				);

				return result;
			}
		};

		A.mix(
			PortletURL,
			{
				ACTION_PHASE: '1',

				RENDER_PHASE: '0',

				RESOURCE_PHASE: '2',

				createActionURL: function() {
					return new PortletURL(PortletURL.ACTION_PHASE);
				},

				createPermissionURL: function(portletResource, modelResource, modelResourceDescription, resourcePrimKey) {
					var redirect = location.href;

					var portletURL = PortletURL.createRenderURL();

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
					portletURL.setPortletId(86);
					portletURL.setWindowState('MAXIMIZED');

					return portletURL;
				},

				createRenderURL: function() {
					return new PortletURL(PortletURL.RENDER_PHASE);
				},

				createResourceURL: function() {
					return new PortletURL(PortletURL.RESOURCE_PHASE);
				},

				createURL: function(basePortletURL, params) {
					return new PortletURL(null, params, basePortletURL);
				}
			}
		);

		Liferay.PortletURL = PortletURL;
	},
	'',
	{
		requires: ['aui-base', 'aui-io-request', 'aui-url', 'querystring-stringify-simple']
	}
);