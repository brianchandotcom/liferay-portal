AUI.add(
	'liferay-dockbar',
	function(A) {
		var Lang = A.Lang;

		var Portlet = Liferay.Portlet;
		var Util = Liferay.Util;

		var BODY = A.getBody();

		var CSS_ADD_CONTENT = 'lfr-has-add-content';

		var BODY_CONTENT = 'bodyContent';

		var BOUNDING_BOX = 'boundingBox';

		var CONTENT_BOX = 'contentBox';

		var EVENT_CLICK = 'click';

		var TPL_ADD_CONTENT = '<div class="span4" />';

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

			addItem: function(options) {
				var instance = this;

				if (options.url) {
					options.text = '<a href="' + options.url + '">' + options.text + '</a>';
				}

				var item = A.Node.create('<li class="' + (options.className || '') + '">' + options.text + '</li>');

				instance.dockBar.one('> ul').appendChild(item);

				instance._toolbarItems[options.name] = item;

				return item;
			},

			addMessage: function(message, messageId) {
				var instance = this;

				var messages = instance.messages;

				if (!instance.messageList) {
					instance.messageList = [];
					instance.messageIdList = [];
				}

				messages.show();

				if (!messageId) {
					messageId = A.guid();
				}

				instance.messageList.push(message);
				instance.messageIdList.push(messageId);

				var currentBody = messages.get(BODY_CONTENT);

				message = instance._createMessage(message, messageId);

				messages.setStdModContent('body', message, 'after');

				var messagesContainer = messages.get(BOUNDING_BOX);

				var action = 'removeClass';

				if (instance.messageList.length > 1) {
					action = 'addClass';
				}

				messagesContainer[action]('multiple-messages');

				return messageId;
			},

			clearMessages: function(event) {
				var instance = this;

				instance.messages.set(BODY_CONTENT, ' ');

				instance.messageList = [];
				instance.messageIdList = [];
			},

			getPanelNode: function() {
				var instance = this;

				return instance._getPanelNode();
			},

			loadPanel: function() {
				var instance = this;

				Dockbar._loadAddPanel();
			},

			setMessage: function(message, messageId) {
				var instance = this;

				var messages = instance.messages;

				if (!messageId) {
					messageId = A.guid();
				}

				instance.messageList = [message];
				instance.messageIdList = [messageId];

				messages.show();

				message = instance._createMessage(message, messageId);

				messages.set(BODY_CONTENT, message);

				var messagesContainer = messages.get(BOUNDING_BOX);

				messagesContainer.removeClass('multiple-messages');

				return messageId;
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

			_createMessage: function(message, messageId) {
				var instance = this;

				var cssClass = '';

				if (instance.messageList.length === 1) {
					cssClass = 'first';
				}

				return '<div class="dockbar-message ' + cssClass + '" id="' + messageId + '">' + message + '</div>';
			},

			_getPanelNode: function() {
				var instance = this;

				var addPanelNode = instance._addPanelNode;

				if (!addPanelNode) {
					addPanelNode = A.one('#' + instance._namespace + 'addPanelSidebar');

					if (!addPanelNode) {
						addPanelNode = A.Node.create(TPL_ADD_CONTENT);

						addPanelNode.plug(A.Plugin.ParseContent);

						A.one('#wrapper .row-fluid').prepend(addPanelNode);

						addPanelNode.set('id', instance._namespace + 'addPanelSidebar');

						instance._addPanelNode = addPanelNode;
					}
				}

				return addPanelNode;
			},

			_loadAddPanel: function() {
				var instance = this;

				BODY.toggleClass(CSS_ADD_CONTENT);

				var addPanelNode = instance._getPanelNode();

				var contentWrapper = A.one('#content-wrapper');

				if (BODY.hasClass(CSS_ADD_CONTENT)) {
					instance._addPanel();

					addPanelNode.show();

					contentWrapper.replaceClass('span12', 'span8');
				}
				else {
					addPanelNode.hide();

					contentWrapper.replaceClass('span8', 'span12');
				}
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

				instance._getPanelNode().html(TPL_LOADING);
			},

			_toggleAppShortcut: function(item, force) {
				var instance = this;

				item.toggleClass('lfr-portlet-used', force);

				instance._addContentNode.focusManager.refresh();
			}
		};

		Liferay.provide(
			Dockbar,
			'addUnderlay',
			function(options) {
				var instance = this;

				instance._addUnderlay(options);
			},
			['liferay-dockbar-underlay']
		);

		Liferay.provide(
			Dockbar,
			'_init',
			function() {
				var instance = this;

				var dockBar = instance.dockBar;
				var namespace = instance._namespace;

				Liferay.Util.toggleControls(dockBar);

				var UnderlayManager = new A.OverlayManager(
					{
						zIndexBase: 300
					}
				);

				Dockbar.UnderlayManager = UnderlayManager;

				instance._toolbarItems = {};

				var messages = instance._addUnderlay(
					{
						align: {
							node: instance.dockBar,
							points: ['tc', 'bc']
						},
						bodyContent: '',
						boundingBox: '#' + namespace + 'dockbarMessages',
						header: 'My messages',
						name: 'messages',
						visible: false
					}
				);

				messages.on(
					'visibleChange',
					function(event) {
						if (event.newVal) {
							BODY.addClass('showing-messages');
						}
						else {
							BODY.removeClass('showing-messages');
						}
					}
				);

				messages.closeTool.on(EVENT_CLICK, instance.clearMessages, instance);

				Liferay.fire('initLayout');
				Liferay.fire('initNavigation');

				var addContent = A.one('#' + namespace + 'addContent');

				var addPanel = A.one('#' + namespace + 'addPanel');

				if (addPanel) {
					addPanel.on(
						EVENT_CLICK,
						function(event) {
							event.halt();

							instance._loadAddPanel();

							addContent.removeClass('open');
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
			['aui-io-request', 'aui-overlay-context-deprecated', 'liferay-dockbar-underlay', 'liferay-store', 'node-focusmanager']
		);

		Liferay.provide(
			Dockbar,
			'_addPanel',
			function() {
				var instance = this;

				instance._setLoadingAnimation();

				var addPanel = A.one('#' + instance._namespace + 'addPanel');

				if (addPanel) {
					var uri = addPanel.attr('href');

					A.io.request(
						uri,
						{
							after: {
								success: function(event, id, obj) {
									var response = this.get('responseData');

									var panelNode = instance._getPanelNode();

									panelNode.plug(A.Plugin.ParseContent);

									panelNode.setContent(response);
								}
							}
						}
					);
				}
			},
			['aui-io-request']
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

		Liferay.Dockbar = Dockbar;
	},
	'',
	{
		requires: ['aui-node', 'aui-overlay-context-deprecated', 'aui-overlay-manager-deprecated', 'event-touch']
	}
);