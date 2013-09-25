AUI.add(
	'liferay-dockbar',
	function(A) {
		var AObject = A.Object;

		var Lang = A.Lang;

		var Util = Liferay.Util;

		var BODY = A.getBody();

		var CSS_ADD_CONTENT = 'lfr-has-add-content';

		var CSS_EDIT_LAYOUT_CONTENT = 'lfr-has-edit-layout';

		var CSS_PREVIEW_CONTENT = 'lfr-has-device-preview';

		var EVENT_CLICK = 'click';

		var STR_ADD_PANEL = 'addPanel';

		var STR_EDIT_LAYOUT_PANEL = 'editLayoutPanel';

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

					instance._namespace = dockBar.attr('data-namespace');

					Liferay.once('initDockbar', instance._init, instance);

					var eventHandle = dockBar.on(
						['focus', 'mousemove', 'touchstart'],
						function(event) {
							Liferay.fire('initDockbar');

							eventHandle.detach();
						}
					);

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

			togglePreviewPanel: function() {
				var instance = this;

				Dockbar._togglePanel(STR_PREVIEW_PANEL);
			},

			toggleAddPanel: function() {
				var instance = this;

				Dockbar._togglePanel(STR_ADD_PANEL);
			},

			toggleEditLayoutPanel: function() {
				var instance = this;

				Dockbar._togglePanel(STR_EDIT_LAYOUT_PANEL);
			},

			_registerPanels: function() {
				var instance = this;

				var namespace = instance._namespace;

				AObject.each(
					DOCKBAR_PANELS,
					function(item, index, collection) {
						var panelId = item.id;

						var panelTrigger = A.one('#' + namespace + panelId);

						if (panelTrigger) {
							panelTrigger.on(
								EVENT_CLICK,
								function(event) {
									event.halt();

									instance._togglePanel(panelId);
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

					navAccountControls.toggleClass('nav-account-controls-notice', force);

					var navAddControls = A.one('#' + namespace + 'navAddControls');

					navAddControls.toggleClass('nav-add-controls-notice', force);
				}
			},

			_togglePanel: function(panelId) {
				var instance = this;

				AObject.each(
					DOCKBAR_PANELS,
					function(item, index, collection) {
						if (item.id !== panelId) {
							BODY.removeClass(item.css);

							if (item.node) {
								item.node.hide();
							}
						}
					}
				);

				var panel = DOCKBAR_PANELS[panelId];

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
					}

					Liferay.fire(
						panelDisplayEvent,
						{
							id: panelId
						}
					);

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
				var namespace = instance._namespace;

				Liferay.Util.toggleControls(dockBar);

				instance._toolbarItems = {};

				Liferay.fire('initLayout');
				Liferay.fire('initNavigation');

				instance._registerPanels();

				var btnNavigation = A.oneNS(namespace, '#navSiteNavigationNavbarBtn');

				var navigation = A.one(Liferay.Data.NAV_SELECTOR);

				if (btnNavigation && navigation) {
					btnNavigation.on(
						EVENT_CLICK,
						function(event) {
							btnNavigation.toggleClass('open');
							navigation.toggleClass('open');
						}
					);
				}

				Liferay.fire('dockbarLoaded');
			},
			['aui-io-request', 'liferay-node', 'liferay-store', 'node-focusmanager']
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
		requires: ['aui-node', 'aui-overlay-mask-deprecated', 'event-touch']
	}
);