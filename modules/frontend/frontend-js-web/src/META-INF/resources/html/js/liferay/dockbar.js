AUI.add(
	'liferay-dockbar',
	function(A) {
		var BODY = A.getBody();

		var CSS_DOCKBAR_ITEM = 'dockbar-item';

		var SELECTOR_NAV_ACCOUNT_CONTROLS = '.nav-account-controls';

		var SELECTOR_NAV_ADD_CONTROLS = '.nav-add-controls';

		var Dockbar = {
			init: function(containerId) {
				var instance = this;

				var dockBar = A.one(containerId);

				if (dockBar) {
					instance.dockBar = dockBar;

					var namespace = dockBar.attr('data-namespace');

					instance._namespace = namespace;

					Liferay.once('initDockbar', instance._init, instance);

					var eventHandle = dockBar.on(
						['focus', 'mousemove', 'touchstart'],
						function(event) {
							var target = event.target;
							var type = event.type;

							Liferay.fire('initDockbar');

							eventHandle.detach();

							if (!A.UA.touch) {
								instance._initInteraction(target, type);
							}
						}
					);

					var btnNavigation = A.one('#' + namespace + 'navSiteNavigation');

					var navigation = A.one(Liferay.Data.NAV_SELECTOR);

					if (btnNavigation && navigation) {
						btnNavigation.setData('menuItem', navigation);

						new Liferay.MenuToggle(
							{
								content: [btnNavigation, navigation],
								toggleTouch: false,
								trigger: btnNavigation
							}
						);
					}

					BODY.addClass('dockbar-ready');

					Liferay.on(['noticeHide', 'noticeShow'], instance._toggleControlsOffset, instance);
				}
			},

			_toggleControlsOffset: function(event) {
				if (!event.useAnimation) {
					var instance = this;

					var force = false;

					if (event.type === 'noticeShow') {
						force = true;
					}

					var namespace = instance._namespace;

					var navAccountControls = A.one('#' + namespace + 'navAccountControls');

					if (navAccountControls) {
						navAccountControls.toggleClass('nav-account-controls-notice', force);
					}

					var navAddControls = A.one('#' + namespace + 'navAddControls');

					if (navAddControls) {
						navAddControls.toggleClass('nav-add-controls-notice', force);
					}
				}
			}
		};

		Liferay.provide(
			Dockbar,
			'_init',
			function() {
				var instance = this;

				var dockBar = instance.dockBar;

				Liferay.Util.toggleControls(dockBar);

				instance._toolbarItems = {};

				Liferay.fire('initLayout');
				Liferay.fire('initNavigation');
				Liferay.fire('dockbarLoaded');
			},
			['aui-io-request', 'liferay-node', 'liferay-store', 'node-focusmanager']
		);

		Liferay.provide(
			Dockbar,
			'_initInteraction',
			function(target, type) {
				var instance = this;

				var dockBar = instance.dockBar;

				var navAccountControls = dockBar.one(SELECTOR_NAV_ACCOUNT_CONTROLS);
				var navAddControls = dockBar.one(SELECTOR_NAV_ADD_CONTROLS);

				if (navAccountControls) {
					var stagingBar = navAccountControls.one('.staging-bar');

					if (stagingBar) {
						stagingBar.all('> li').addClass(CSS_DOCKBAR_ITEM);
					}

					navAccountControls.all('> li > a').get('parentNode').addClass(CSS_DOCKBAR_ITEM);
				}

				if (BODY.hasClass('dockbar-split')) {
					dockBar.plug(Liferay.DockbarKeyboardInteraction);

					if (themeDisplay.isSignedIn() && navAddControls) {
						navAddControls.plug(
							A.Plugin.NodeFocusManager,
							{
								circular: true,
								descendants: '.dropdown-menu li:visible a',
								keys: {
									next: 'down:39,40',
									previous: 'down:37,38'
								}
							}
						);

						navAddControls.focusManager.after(
							'focusedChange',
							function(event) {
								var instance = this;

								if (!event.newVal) {
									instance.set('activeDescendant', 0);
								}
							}
						);
					}
				}
				else if (themeDisplay.isSignedIn() && navAddControls) {
					var brand = dockBar.one('.navbar-brand');

					if (brand) {
						brand.all('a').get('parentNode').addClass(CSS_DOCKBAR_ITEM);
					}

					navAddControls.all('> li').addClass(CSS_DOCKBAR_ITEM);

					dockBar.plug(Liferay.DockbarKeyboardInteraction);
				}

				if (type === 'focus') {
					var navAccountControlsAncestor = target.ancestor(SELECTOR_NAV_ACCOUNT_CONTROLS);

					var navLink = target;

					if (navAccountControlsAncestor) {
						navLink = navAccountControlsAncestor.one('li a');
					}

					navLink.blur();
					navLink.focus();
				}
			},
			['liferay-dockbar-keyboard-interaction', 'node-focusmanager']
		);

		Liferay.Dockbar = Dockbar;
	},
	'',
	{
		requires: ['aui-node', 'aui-overlay-mask-deprecated', 'event-move', 'event-touch', 'liferay-menu-toggle']
	}
);