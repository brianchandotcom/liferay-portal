AUI.add(
	'liferay-surface-app',
	function(A) {
		var Surface = Liferay.Surface;

		A.ready(
			function() {
				Surface.app = new A.SurfaceApp(
					{
						basePath: Surface.getBasePath(),
						linkSelector: 'a:not(.portlet-icon-back):not([data-navigation]):not([data-resource-href]):not([target="_blank"])',
						on: {
							endNavigate: function(event) {
								var instance = this;

								var error = event.error;

								if (!error && Surface.isActionURL(event.path)) {
									var redirect = Surface.getRedirect(event.path);

									Surface.sendRedirect(redirect, instance.get('title'));
								}

								Liferay.fire(
									'surfaceEndNavigate',
									{
										app: Surface.app,
										error: error,
										path: event.path
									}
								);

								A.getBody().removeClass('lfr-surface-loading');
							},

							startNavigate: function(event) {
								var instance = this;

								if (Surface.isActionURL(event.path)) {
									event.path = Surface.isolatePortletURLRedirect(event.path);
								}

								Liferay.fire(
									'surfaceStartNavigate',
									{
										app: Surface.app,
										path: event.path
									}
								);

								A.getBody().addClass('lfr-surface-loading');
							}
						}
					}
				);

				Surface.app.addScreenRoutes(
					[
						{
							path: function(url) {
								return url.search(Surface.getPatternPortletURL(Liferay.PortletURL.ACTION_PHASE)) > -1;
							},
							screen: Surface.ActionURLScreen
						},
						{
							path: function(url) {
								return url.search(Surface.getPatternFriendlyURL(url)) > -1;
							},
							screen: Surface.RenderURLScreen
						},
						{
							path: function(url) {
								return url.search(Surface.getPatternPortletURL(Liferay.PortletURL.RENDER_PHASE)) > -1;
							},
							screen: Surface.RenderURLScreen
						}
					]
				);

				Surface.app.addSurfaces(Surface.getSurfaceIds());

				Liferay.on(
					'closePortlet',
					function(event) {
						var portletId = event.portletId;

						var surfaceId = Surface.getPortletBoundaryId(portletId);

						delete Surface.app.surfaces[surfaceId];
					}
				);

				Liferay.on(
					'portletReady',
					function(event) {
						var portletId = event.portletId;

						var surfaceId = Surface.getPortletBoundaryId(portletId);

						var surface = Surface.app.surfaces[surfaceId];

						if (surface && surface.activeChild && !surface.activeChild.inDoc()) {
							surface = null;
						}

						if (!surface && Surface.isPortletSurface(portletId)) {
							Surface.app.addSurfaces(surfaceId);
						}
					}
				);

				Liferay.on(
					'surfaceScreenLoad',
					function(event) {
						Surface.resetAllPortlets();
					}
				);

				Liferay.on(
					'surfaceEndNavigate',
					function(event) {
						var activeScreen = Surface.app.activeScreen;

						var dataChannel = activeScreen.get('dataChannel');

						if (dataChannel.clearScreensCache) {
							A.each(
								Surface.app.screens,
								function(value, key) {
									if (value !== activeScreen) {
										value.clearCache();
									}
								}
							);
						}
					}
				);
			}
		);
	},
	'',
	{
		requires: ['liferay-surface']
	}
);