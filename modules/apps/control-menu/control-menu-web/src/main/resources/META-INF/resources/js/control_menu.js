AUI.add(
	'liferay-control-menu',
	function(A) {
		var AArray = A.Array;

		var AObject = A.Object;

		var Lang = A.Lang;

		var BODY = A.getBody();

		var CSS_ADD_CONTENT = 'lfr-has-add-content';

		var STR_ADD_PANEL = 'addPanel';

		var TPL_ADD_CONTENT = '<div class="lfr-add-panel lfr-admin-panel" id="{0}" />';

		var TPL_LOADING = '<div class="loading-animation" />';

		var ControlMenu = {
			init: function(containerId) {
				var instance = this;

				var controlMenu = A.one(containerId);

				if (controlMenu) {
					var namespace = controlMenu.attr('data-namespace');

					instance._namespace = namespace;

					Liferay.Util.toggleControls(controlMenu);

					var eventHandle = controlMenu.on(
						['focus', 'mousemove', 'touchstart'],
						function(event) {
							Liferay.fire('initLayout');

							eventHandle.detach();
						}
					);
				}

				instance._registerPanels();
			},

			getPanelNode: function(panelId) {
				var instance = this;

				var panelNode = null;

				var panel = instance._panels[panelId];

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

							panelNode.attr('id', panelSidebarId);

							panel.node = panelNode;
						}
					}
				}

				return panelNode;
			},

			registerPanel: function(panel) {
				var instance = this;

				var namespace = instance._namespace;

				var panelTrigger = panel.trigger || A.one('#' + namespace + panel.id);

				if (panelTrigger) {
					panelTrigger.on(
						'gesturemovestart',
						function(event) {
							event.currentTarget.once(
								'gesturemoveend',
								function(event) {
									event.halt();

									instance.togglePanel(panel.id);
								}
							);
						}
					);
				}

				instance._panels[panel.id] = panel;
			},

			toggleAddPanel: function() {
				var instance = this;

				ControlMenu.togglePanel(STR_ADD_PANEL);
			},

			togglePanel: function(panelId) {
				var instance = this;

				AObject.each(
					instance._panels,
					function(item) {
						if (item.id !== panelId) {
							BODY.removeClass(item.css);

							if (item.node) {
								item.node.hide();

								BODY.detach('layoutControlsEsc|key');
							}
						}
					}
				);

				var panel = instance._panels[panelId];

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
						panel.showFn.call(this, panelId);

						panelDisplayEvent = 'dockbarShowPanel';
						panelVisible = true;

						BODY.on(
							'layoutControlsEsc|key',
							function(event) {
								if (panel.hideOnEscape) {
									instance.togglePanel(panelId);
								}

								var navAddControls = A.one('#' + namespace + 'navAddControls');

								if (navAddControls) {
									var layoutControl = navAddControls.one(item.layoutControl);

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

						if (panel.destroy) {
							panel.destroy.call(this);
						}
					}

					panelNode.toggle(panelVisible);
				}
			},

			_registerPanels: function() {
				var instance = this;

				instance._panels = {};

				AArray.each(
					DEFAULT_CONTROL_MENU_PANELS,
					A.bind('registerPanel', instance)
				);
			},

			_setLoadingAnimation: function(panel) {
				var instance = this;

				instance.getPanelNode(panel).html(TPL_LOADING);
			}
		};

		Liferay.provide(
			ControlMenu,
			'showPanel',
			function(panelId) {
				var instance = this;

				instance._setLoadingAnimation(panelId);

				var panel = A.one('#' + instance._namespace + panelId);

				if (panel) {
					var uri = panel.attr('data-panelurl');

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

		var DEFAULT_CONTROL_MENU_PANELS = [
			{
				css: CSS_ADD_CONTENT,
				destroy: function() {
					var namespace = this._namespace;

					AArray.forEach(
						['addApplication', 'addContent', 'addPage'],
						function(item) {
							var componentName = Liferay.Util.ns(namespace, item);

							var component = Liferay.component(componentName);

							if (component) {
								component.destroy();
							}
						}
					);
				},
				id: STR_ADD_PANEL,
				layoutControl: '.site-add-controls > a',
				node: null,
				showFn: A.bind('showPanel', ControlMenu),
				hideOnEscape: true,
				tpl: TPL_ADD_CONTENT
			}
		];

		Liferay.ControlMenu = ControlMenu;

		Liferay.ControlMenu.ADD_PANEL = STR_ADD_PANEL;
	},
	'',
	{
		requires: ['aui-node', 'aui-overlay-mask-deprecated', 'event-move', 'event-touch', 'liferay-menu-toggle']
	}
);