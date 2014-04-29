AUI.add(
	'liferay-dockbar',
	function(A) {
		var AObject = A.Object;

		var Lang = A.Lang;

		var Util = Liferay.Util;

		var ADD_PANEL_COMPONENTS = ['addApplication', 'addContent', 'addPage'];

		var BODY = A.getBody();

		var CSS_ADD_CONTENT = 'lfr-has-add-content';

		var CSS_EDIT_LAYOUT_CONTENT = 'lfr-has-edit-layout';

		var CSS_DOCKBAR_ITEM = 'dockbar-item';

		var CSS_PREVIEW_CONTENT = 'lfr-has-device-preview';

		var EVENT_CLICK = 'click';

		var EVENT_MOUSEDOWN_OUTSIDE = 'mousedownoutside';

		var SELECTOR_NAV_ACCOUNT_CONTROLS = '.nav-account-controls';

		var SELECTOR_NAV_ADD_CONTROLS = '.nav-add-controls';

		var STR_ACTIVE = 'active';

		var STR_ADD_PANEL = 'addPanel';

		var STR_EDIT_LAYOUT_PANEL = 'editLayoutPanel';

		var STR_OPEN = 'open';

		var STR_PREVIEW_PANEL = 'previewPanel';

		var TPL_ADD_CONTENT = '<div class="lfr-add-panel lfr-admin-panel" id="{0}" />';

		var TPL_EDIT_LAYOUT_PANEL = '<div class="lfr-admin-panel lfr-edit-layout-panel" id="{0}" />';

		var TPL_PREVIEW_PANEL = '<div class="lfr-admin-panel lfr-device-preview-panel" id="{0}" />';

		var TPL_LOADING = '<div class="loading-animation" />';

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

			getPanelNode: function(panelId) {
				var instance = this;

				var panelNode = null;

				var panel = DOCKBAR_PANELS[panelId];

				if (panel) {
					panelNode = panel.node;

					if (!panelNode) {
						var namespace = instance._namespace;

						var panelSidebarId = namespace + panelId + 'Sidebar';

						panelNode = A.one('#' + panelSidebarId);

						if (!panelNode) {
							panelNode = A.Node.create(Lang.sub(panel.tpl, [namespace]));

							panelNode.plug(A.Plugin.ParseContent);

							BODY.prepend(panelNode);

							panelNode.set('id', panelSidebarId);

							panel.node = panelNode;
						}
					}
				}

				return panelNode;
			},

			toggleAddPanel: function() {
				var instance = this;

				Dockbar._togglePanel(STR_ADD_PANEL);
			},

			toggleEditLayoutPanel: function() {
				var instance = this;

				Dockbar._togglePanel(STR_EDIT_LAYOUT_PANEL);
			},

			togglePreviewPanel: function() {
				var instance = this;

				Dockbar._togglePanel(STR_PREVIEW_PANEL);
			},

			_registerPanels: function() {
				var instance = this;

				var namespace = instance._namespace;

				AObject.each(
					DOCKBAR_PANELS,
					function(item, index) {
						var panelId = item.id;

						var panelTrigger = A.one('#' + namespace + panelId);

						if (panelTrigger) {
							panelTrigger.on(
								'gesturemovestart',
								function(event) {
									event.currentTarget.once(
										'gesturemoveend',
										function(event) {
											event.halt();

											instance._togglePanel(panelId);
										}
									);
								}
							);
						}
					}
				);
			},

			_setLoadingAnimation: function(panel) {
				var instance = this;

				instance.getPanelNode(panel).html(TPL_LOADING);
			},

			_toggleAppShortcut: function(item, force) {
				var instance = this;

				item.toggleClass('lfr-portlet-used', force);

				instance._addContentNode.focusManager.refresh();
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
			},

			_togglePanel: function(panelId) {
				var instance = this;

				AObject.each(
					DOCKBAR_PANELS,
					function(item, index) {
						if (item.id !== panelId) {
							BODY.removeClass(item.css);

							if (item.node) {
								item.node.hide();

								BODY.detach('layoutControlsEsc|key');
							}
						}
					}
				);

				var panel = DOCKBAR_PANELS[panelId];

				var namespace = instance._namespace;

				if (panel) {
					var panelNode = panel.node;

					if (!panelNode) {
						panelNode = instance.getPanelNode(panel.id);
					}

					BODY.toggleClass(panel.css);

					var panelDisplayEvent = 'dockbarHidePanel';
					var panelVisible = false;

					if (panelNode && BODY.hasClass(panel.css)) {
						panel.showFn(panelId);

						panelDisplayEvent = 'dockbarShowPanel';
						panelVisible = true;

						BODY.on(
							'layoutControlsEsc|key',
							function(event) {
								if (panelId !== STR_PREVIEW_PANEL) {
									instance._togglePanel(panelId);
								}

								var navAddControls = A.one('#' + namespace + 'navAddControls');

								if (navAddControls) {
									var layoutControl;

									if (panelId == STR_ADD_PANEL) {
										layoutControl = navAddControls.one('.site-add-controls > a');
									}
									else if (panelId == STR_EDIT_LAYOUT_PANEL) {
										layoutControl = navAddControls.one('.page-edit-controls > a');
									}
									else if (panelId == STR_PREVIEW_PANEL) {
										layoutControl = navAddControls.one('.page-preview-controls > a');
									}

									if (layoutControl) {
										layoutControl.focus();
									}
								}
							},
							'down:27'
						);
					}

					Liferay.fire(
						'dockbaraddpage:previewPageTitle',
						{
							data: {
								hidden: true
							}
						}
					);

					Liferay.fire(
						panelDisplayEvent,
						{
							id: panelId
						}
					);

					if (!panelVisible) {
						BODY.detach('layoutControlsEsc|key');

						if (panelId === STR_ADD_PANEL) {
							A.Array.each(
								ADD_PANEL_COMPONENTS,
								function(item, index) {
									var componentName = Liferay.Util.ns(namespace, item);

									var component = Liferay.component(componentName);

									if (component) {
										component.destroy();
									}
								}
							);
						}
					}

					panelNode.toggle(panelVisible);
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

				instance._registerPanels();

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

		Liferay.provide(
			Dockbar,
			'_showPanel',
			function(panelId) {
				var instance = this;

				instance._setLoadingAnimation(panelId);

				var panel = A.one('#' + instance._namespace + panelId);

				if (panel) {
					var uri = panel.ancestor().attr('data-panelURL');

					A.io.request(
						uri,
						{
							after: {
								success: function(event, id, obj) {
									var response = this.get('responseData');

									var panelNode = instance.getPanelNode(panelId);

									panelNode.plug(A.Plugin.ParseContent);

									panelNode.setContent(response);
								}
							}
						}
					);
				}
			},
			['aui-io-request', 'aui-parse-content', 'event-outside']
		);

		var showPanelFn = A.bind('_showPanel', Dockbar);

		var DOCKBAR_PANELS = {
			'addPanel': {
				css: CSS_ADD_CONTENT,
				id: STR_ADD_PANEL,
				node: null,
				showFn: showPanelFn,
				tpl: TPL_ADD_CONTENT
			},
			'editLayoutPanel': {
				css: CSS_EDIT_LAYOUT_CONTENT,
				id: STR_EDIT_LAYOUT_PANEL,
				node: null,
				showFn: showPanelFn,
				tpl: TPL_EDIT_LAYOUT_PANEL
			},
			'previewPanel': {
				css: CSS_PREVIEW_CONTENT,
				id: STR_PREVIEW_PANEL,
				node: null,
				showFn: showPanelFn,
				tpl: TPL_PREVIEW_PANEL
			}
		};

		Liferay.Dockbar = Dockbar;

		Liferay.Dockbar.ADD_PANEL = STR_ADD_PANEL;

		Liferay.Dockbar.PREVIEW_PANEL = STR_PREVIEW_PANEL;
	},
	'',
	{
		requires: ['aui-node', 'aui-overlay-mask-deprecated', 'event-move', 'event-touch', 'liferay-menu-toggle']
	}
);