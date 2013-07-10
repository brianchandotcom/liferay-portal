AUI.add(
	'liferay-dockbar',
	function(A) {
		var AObject = A.Object;

		var Lang = A.Lang;

		var Portlet = Liferay.Portlet;

		var Util = Liferay.Util;

		var BODY = A.getBody();

		var CSS_ADD_CONTENT = 'lfr-has-add-content';

		var CSS_PREVIEW_CONTENT = 'lfr-has-device-preview';

		var BODY_CONTENT = 'bodyContent';

		var BOUNDING_BOX = 'boundingBox';

		var CONTENT_BOX = 'contentBox';

		var EVENT_CLICK = 'click';

		var STR_ADD_PANEL = 'addPanel';

		var STR_PREVIEW_PANEL = 'previewPanel';

		var TPL_ADD_CONTENT = '<div class="lfr-add-panel" id="{0}" />';

		var TPL_PREVIEW_PANEL = '<div class="lfr-device-preview-panel loading-animation" id="{0}" />';

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

						var panelSidebarId = namespace + panel + 'Sidebar';

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

			_createCustomizationMask: function(column) {
				var instance = this;

				var columnId = column.attr('id');

				var customizable = !!column.one('.portlet-column-content.customizable');

				var cssClass = 'customizable-layout-column';

				var overlayMask = new A.OverlayMask(
					{
						cssClass: cssClass,
						target: column,
						zIndex: 10

					}
				).render();

				if (customizable) {
					overlayMask.get(BOUNDING_BOX).addClass('customizable');
				}

				var columnControls = instance._controls.clone();

				var input = columnControls.one('.layout-customizable-checkbox');
				var label = columnControls.one('label');

				var oldName = input.attr('name');
				var newName = oldName.replace('[COLUMN_ID]', columnId);

				input.attr(
					{
						checked: customizable,
						id: newName,
						name: newName
					}
				);

				label.attr('for', newName);

				overlayMask.get(BOUNDING_BOX).prepend(columnControls);

				columnControls.show();

				input.setData('customizationControls', overlayMask);
				column.setData('customizationControls', overlayMask);

				return overlayMask;
			},

			_openWindow: function(config, item) {
				if (item) {
					A.mix(
						config,
						{
							id: item.guid(),
							title: item.attr('title'),
							uri: item.attr('href')
						}
					);
				}

				Util.openWindow(config);
			},

			_setLoadingAnimation: function() {
				var instance = this;

				instance.getPanelNode(STR_ADD_PANEL).html(TPL_LOADING);
			},

			_toggleAppShortcut: function(item, force) {
				var instance = this;

				item.toggleClass('lfr-portlet-used', force);

				instance._addContentNode.focusManager.refresh();
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

					if (panelNode && BODY.hasClass(panel.css)) {
						panel.showFn();

						panelNode.show();
					}
					else {
						panelNode.hide();
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
				var namespace = instance._namespace;

				Liferay.Util.toggleControls(dockBar);

				instance._toolbarItems = {};

				Liferay.fire('initLayout');
				Liferay.fire('initNavigation');

				var addPanel = A.one('#' + namespace + 'addPanel');

				if (addPanel) {
					addPanel.on(
						EVENT_CLICK,
						function(event) {
							event.halt();

							instance._togglePanel(STR_ADD_PANEL);
						}
					);
				}

				var previewContent = A.one('#' + namespace + 'previewContent');

				var previewPanel = A.one('#' + namespace + 'previewPanel');

				if (previewPanel) {
					previewPanel.on(
						EVENT_CLICK,
						function(event) {
							event.halt();

							instance._togglePanel(STR_PREVIEW_PANEL);
						}
					);
				}

				var manageContent = A.one('#' + namespace + 'manageContent');

				if (manageContent) {
					manageContent.delegate(
						EVENT_CLICK,
						function(event) {
							event.preventDefault();

							manageContent.removeClass('open');

							instance._openWindow(
								{
									id: '#' + namespace + 'manageContentDialog'
								},
								event.currentTarget
							);
						},
						'.use-dialog a'
					);
				}

				var manageCustomization = A.one('#' + namespace + 'manageCustomization');

				if (manageCustomization) {
					if (!manageCustomization.hasClass('disabled')) {
						instance._controls = dockBar.one('.layout-customizable-controls');

						var columns = A.all('.portlet-column');

						var customizationsHandle;

						manageCustomization.on(
							EVENT_CLICK,
							function(event) {
								event.halt();

								if (!customizationsHandle) {
									customizationsHandle = BODY.delegate(EVENT_CLICK, instance._onChangeCustomization, '.layout-customizable-checkbox', instance);
								}
								else {
									customizationsHandle.detach();

									customizationsHandle = null;
								}

								manageContent.removeClass('open');

								columns.each(
									function(item, index, collection) {
										var overlayMask = item.getData('customizationControls');

										if (!overlayMask) {
											overlayMask = instance._createCustomizationMask(item);
										}

										overlayMask.toggle();
									}
								);
							}
						);

						Liferay.publish(
							'updatedLayout',
							{
								defaultFn: function(event) {
									columns.each(
										function(item, index, collection) {
											var overlayMask = item.getData('customizationControls');

											if (overlayMask) {
												item.setData('customizationControls', null);
											}
										}
									);
								}
							}
						);
					}
				}

				var userAvatar = A.one('#' + namespace + 'userAvatar');

				if (userAvatar) {
					userAvatar.delegate(
						EVENT_CLICK,
						function(event) {
							event.preventDefault();

							instance._openWindow(
								{},
								event.currentTarget
							);
						},
						'a.use-dialog'
					);
				}

				Liferay.fire('dockbarLoaded');
			},
			['aui-io-request', 'liferay-store', 'node-focusmanager']
		);

		Liferay.provide(
			Dockbar,
			'_addPanel',
			function() {
				var instance = this;

				instance._setLoadingAnimation();

				var addPanel = A.one('#' + instance._namespace + 'addPanel');

				if (addPanel) {
					var uri = addPanel.ancestor().attr('data-addURL');

					A.io.request(
						uri,
						{
							after: {
								success: function(event, id, obj) {
									var response = this.get('responseData');

									var panelNode = instance.getPanelNode(STR_ADD_PANEL);

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

		Liferay.provide(
			Dockbar,
			'_previewPanel',
			function() {
				var instance = this;

				var previewPanel = A.one('#' + instance._namespace + 'previewPanel');

				if (previewPanel) {
					var uri = previewPanel.attr('href');

					A.io.request(
						uri,
						{
							after: {
								success: function(event, id, obj) {
									var response = this.get('responseData');

									var panelNode = instance.getPanelNode(STR_PREVIEW_PANEL);

									panelNode.removeClass('loading-animation');

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

		Liferay.provide(
			Dockbar,
			'_onChangeCustomization',
			function(event) {
				var instance = this;

				var checkbox = event.currentTarget;

				var overlayMask = checkbox.getData('customizationControls');

				var boundingBox = overlayMask.get(BOUNDING_BOX);
				var column = overlayMask.get('target');

				boundingBox.toggleClass('customizable');
				column.toggleClass('customizable');

				var data = {
					cmd: 'update_type_settings',
					doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
					p_auth: Liferay.authToken,
					p_l_id: themeDisplay.getPlid(),
					p_v_l_s_g_id: themeDisplay.getSiteGroupId()
				};

				var checkboxName = checkbox.attr('name');

				checkboxName = checkboxName.replace('Checkbox', '');

				data[checkboxName] = checkbox.attr('checked');

				A.io.request(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						data: data
					}
				);
			},
			['aui-io-request']
		);

		var DOCKBAR_PANELS = {
			'addPanel': {
				css: CSS_ADD_CONTENT,
				id: STR_ADD_PANEL,
				node: null,
				showFn: Dockbar._addPanel,
				tpl: TPL_ADD_CONTENT
			},
			'previewPanel': {
				css: CSS_PREVIEW_CONTENT,
				id: STR_PREVIEW_PANEL,
				node: null,
				showFn: Dockbar._previewPanel,
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