AUI.add(
	'liferay-control-menu',
	function(A) {
		var AObject = A.Object;

		var Lang = A.Lang;

		var ADD_PANEL_COMPONENTS = ['addApplication', 'addContent', 'addPage'];

		var BODY = A.getBody();

		var CSS_ADD_CONTENT = 'lfr-has-add-content';

		var CSS_PREVIEW_CONTENT = 'lfr-has-device-preview';

		var STR_ADD_PANEL = 'addPanel';

		var STR_EDIT_LAYOUT_PANEL = 'editLayoutPanel';

		var STR_PREVIEW_PANEL = 'previewPanel';

		var TPL_ADD_CONTENT = '<div class="lfr-add-panel lfr-admin-panel" id="{0}" />';

		var TPL_LOADING = '<div class="loading-animation" />';

		var TPL_PREVIEW_PANEL = '<div class="lfr-admin-panel lfr-device-preview-panel" id="{0}" />';

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

				var panel = CONTROL_MENU_PANELS[panelId];

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

			toggleAddPanel: function() {
				var instance = this;

				ControlMenu._togglePanel(STR_ADD_PANEL);
			},

			toggleEditLayoutPanel: function() {
				var instance = this;

				ControlMenu._togglePanel(STR_EDIT_LAYOUT_PANEL);
			},

			togglePreviewPanel: function() {
				var instance = this;

				ControlMenu._togglePanel(STR_PREVIEW_PANEL);
			},

			_registerPanels: function() {
				var instance = this;

				var namespace = instance._namespace;

				AObject.each(
					CONTROL_MENU_PANELS,
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

			_togglePanel: function(panelId) {
				var instance = this;

				AObject.each(
					CONTROL_MENU_PANELS,
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

				var panel = CONTROL_MENU_PANELS[panelId];

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
							ADD_PANEL_COMPONENTS.forEach(
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
			ControlMenu,
			'_showPanel',
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

		var showPanelFn = A.bind('_showPanel', ControlMenu);

		var CONTROL_MENU_PANELS = {
			'addPanel': {
				css: CSS_ADD_CONTENT,
				id: STR_ADD_PANEL,
				node: null,
				showFn: showPanelFn,
				tpl: TPL_ADD_CONTENT
			},
			'previewPanel': {
				css: CSS_PREVIEW_CONTENT,
				id: STR_PREVIEW_PANEL,
				node: null,
				showFn: showPanelFn,
				tpl: TPL_PREVIEW_PANEL
			}
		};

		Liferay.ControlMenu = ControlMenu;

		Liferay.ControlMenu.ADD_PANEL = STR_ADD_PANEL;

		Liferay.ControlMenu.PREVIEW_PANEL = STR_PREVIEW_PANEL;
	},
	'',
	{
		requires: ['aui-node', 'aui-overlay-mask-deprecated', 'event-move', 'event-touch', 'liferay-menu-toggle']
	}
);