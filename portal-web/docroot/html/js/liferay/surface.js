AUI.add(
	'liferay-surface',
	function(A) {
		var AArray = A.Array;

		A.Surface.DEFAULT = 'defaultScreen';

		var Surface = {
			app: null,

			blacklist: {
				cache: {},
				route: {},
				surface: {}
			},

			clearCache: function() {
				var instance = this;

				if (instance.app) {
					A.each(
						instance.app.screens,
						function(item, index) {
							item.clearCache();
						}
					);
				}
			},

			getBasePath: function() {
				var instance = this;

				var layoutRelativeURL = themeDisplay.getLayoutRelativeURL();

				var pos = layoutRelativeURL.lastIndexOf('?');

				if (pos > -1) {
					layoutRelativeURL = layoutRelativeURL.substr(0, pos);
				}

				return layoutRelativeURL;
			},

			getNamespace: function(portletURL) {
				var instance = this;

				var url = new A.Url(portletURL);

				return Liferay.Util.getPortletNamespace(url.getParameter('p_p_id'));
			},

			getPatternFriendlyURL: function(url) {
				var instance = this;

				if (!themeDisplay.isControlPanel()) {
					var friendlyURLMaximized = (url.indexOf('/maximized') > -1);

					if (themeDisplay.isStateMaximized() && !friendlyURLMaximized) {
						return null;
					}

					if (!themeDisplay.isStateMaximized() && friendlyURLMaximized) {
						return null;
					}
				}

				return /\/-\//;
			},

			getPatternPortletURL: function(lifecycle) {
				var instance = this;

				var routeablePortletIds = instance.getRouteablePortletIds();

				var windowState = 'NORMAL';

				if (themeDisplay.isStateExclusive()) {
					windowState = 'EXCLUSIVE';
				}
				else if (themeDisplay.isStatePopUp()) {
					windowState = 'POP_UP';
				}
				else if (themeDisplay.isStateMaximized()) {
					windowState = 'MAXIMIZED';
				}

				return new RegExp('p_p_id=(' + routeablePortletIds.join('|') + ')&p_p_lifecycle=' + lifecycle + '&p_p_state=' + windowState.toLowerCase());
			},

			getPortletBoundaryId: function(portletId) {
				var instance = this;

				return 'p_p_id_' + portletId + '_';
			},

			getPortletBoundaryIds: function(portletIds) {
				var instance = this;

				return portletIds.map(
					function(portletId) {
						return instance.getPortletBoundaryId(portletId);
					}
				);
			},

			getRedirect: function(portletURL) {
				var instance = this;

				var url = new A.Url(portletURL);
				var namespace = instance.getNamespace(portletURL);

				return url.getParameter(namespace + 'redirect');
			},

			getRouteablePortletIds: function() {
				var instance = this;

				return AArray.filter(
					Liferay.Portlet.list,
					function(portletId) {
						return instance.isPortletRouteable(portletId);
					}
				);
			},

			getSurfaceIds: function() {
				var instance = this;

				var surfaces = instance.getPortletBoundaryIds(instance.getSurfacePortletIds());

				surfaces.push('bottomJS');
				surfaces.push('breadcrumbs');

				return surfaces;
			},

			getSurfacePortletIds: function() {
				var instance = this;

				return AArray.filter(
					Liferay.Portlet.list,
					function(portletId) {
						return instance.isPortletSurface(portletId);
					}
				);
			},

			isActionURL: function(url) {
				var instance = this;

				if (url.indexOf('p_p_lifecycle=1') > -1) {
					return true;
				}

				return false;
			},

			isolatePortletURLRedirect: function(portletURL) {
				var instance = this;

				var url = new A.Url(portletURL);

				var namespace = instance.getNamespace(url.toString());

				var redirect = new A.Url(instance.getRedirect(url.toString()));

				redirect.setParameter('p_p_ajax', false);
				redirect.setParameter('p_p_isolated', true);

				url.setParameter(namespace + 'redirect', redirect.toString());

				return url.toString();
			},

			isPortletCacheable: function(portletId) {
				var instance = this;

				return !instance.blacklist.cache[instance.maybeExtractPortletId(portletId)];
			},

			isPortletRouteable: function(portletId) {
				var instance = this;

				return !instance.blacklist.route[instance.maybeExtractPortletId(portletId)];
			},

			isPortletSurface: function(portletId) {
				var instance = this;

				return !instance.blacklist.surface[instance.maybeExtractPortletId(portletId)];
			},

			maybeExtractPortletId: function(portletId) {
				var lastIndexOf = String(portletId).lastIndexOf('_INSTANCE_');

				if (lastIndexOf > 0) {
					portletId = portletId.substr(0, lastIndexOf);
				}

				return portletId;
			},

			resetAllPortlets: function() {
				var instance = this;

				AArray.each(
					instance.getPortletBoundaryIds(Liferay.Portlet.list),
					function(value, index, collection) {
						var portlet = A.one('#' + value);

						if (portlet) {
							Liferay.Portlet.destroy(portlet);

							portlet.portletProcessed = false;
						}
					}
				);

				Liferay.Portlet.readyCounter = 0;
			},

			sendRedirect: function(redirect, title) {
				var instance = this;

				if (redirect) {
					var url = new A.Url(redirect);

					url.removeParameter('p_p_ajax');
					url.removeParameter('p_p_isolated');

					A.config.win.history.replaceState(null, title, url.toString());
				}
			}
		};

		Surface.EventScreen = A.Component.create(
			{
				ATTRS: {
					dataChannel: {
						validator: A.Lang.isObject,
						value: {}
					}
				},

				EXTENDS: A.HTMLScreen,

				NAME: 'baseScreen',

				prototype: {
					activate: function() {
						var instance = this;

						Surface.EventScreen.superclass.activate.apply(instance, arguments);

						Liferay.fire(
							'surfaceScreenActivate',
							{
								app: Surface.app,
								screen: instance
							}
						);
					},

					deactivate: function() {
						var instance = this;

						Surface.EventScreen.superclass.deactivate.apply(instance, arguments);

						Liferay.fire(
							'surfaceScreenDeactivate',
							{
								app: Surface.app,
								screen: instance
							}
						);

						instance.set('dataChannel', {});
					},

					destructor: function() {
						var instance = this;

						Surface.EventScreen.superclass.destructor(instance, arguments);

						Liferay.fire(
							'surfaceScreenDestructor',
							{
								app: Surface.app,
								screen: instance
							}
						);
					},

					flip: function() {
						var instance = this;

						return Surface.EventScreen.superclass.flip.apply(instance, arguments).then(
							function(data) {
								Liferay.fire(
									'surfaceScreenFlip',
									{
										app: Surface.app,
										screen: instance
									}
								);

								return data;
							}
						);
					},

					load: function(path) {
						var instance = this;

						var url = new A.Url(path);

						var portletId = url.getParameter('p_p_id');

						if (!Surface.isPortletCacheable(portletId)) {
							instance.clearCache();
						}

						return Surface.EventScreen.superclass.load.apply(instance, arguments).then(
							function(data) {
								var dataChannel = data.one('script[type="text/surface-data-channel"]');

								if (dataChannel) {
									dataChannel.remove();
									instance.set('dataChannel', A.JSON.parse(dataChannel.get('text')));
								}

								Liferay.fire(
									'surfaceScreenLoad',
									{
										app: Surface.app,
										content: data,
										screen: instance
									}
								);

								return data;
							}
						);
					}
				}
			}
		);

		Surface.ActionURLScreen = A.Component.create(
			{
				ATTRS: {
					cacheable: {
						value: false
					},
					method: {
						value: 'POST'
					}
				},

				EXTENDS: Surface.EventScreen,

				NAME: 'actionURLScreen'
			}
		);

		Surface.RenderURLScreen = A.Component.create(
			{
				ATTRS: {
					cacheable: {
						value: true
					},
					urlParams: {
						value: {
							p_p_ajax: false,
							p_p_isolated: true
						}
					}
				},

				EXTENDS: Surface.EventScreen,

				NAME: 'renderURLScreen'
			}
		);

		Liferay.Surface = Surface;
	},
	'',
	{
		requires: ['aui-surface-app', 'aui-surface-base', 'aui-surface-screen-html', 'json', 'liferay-portlet-url']
	}
);