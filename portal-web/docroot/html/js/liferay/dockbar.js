AUI.add(
	'liferay-dockbar',
	function(A) {
		var Lang = A.Lang;

		var LayoutConfiguration = Liferay.LayoutConfiguration;
		var Portlet = Liferay.Portlet;
		var Util = Liferay.Util;

		var BODY = A.getBody();

		var BODY_CONTENT = 'bodyContent';

		var BOUNDING_BOX = 'boundingBox';

		var CONTENT_BOX = 'contentBox';

		var EVENT_CLICK = 'click';

		var Dockbar = {
			init: function() {
				var instance = this;

				var dockBar = A.one('#dockbar');

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

			_addMenu: function(options) {
				var instance = this;

				var menu;
				var name = options.name;

				if (name && A.one(options.trigger)) {

					delete options.name;

					options.zIndex = instance.menuZIndex++;

					A.mix(
						options,
						{
							hideDelay: 500,
							hideOn: 'mouseleave',
							showOn: 'mouseover'
						}
					);

					var boundingBox = options.boundingBox;

					if (boundingBox && !(CONTENT_BOX in options)) {
						options.contentBox = boundingBox + '> .aui-menu-content';
					}

					menu = new A.OverlayContext(options);

					var contentBox = menu.get(CONTENT_BOX);

					contentBox.plug(
						A.Plugin.NodeFocusManager,
						{
							circular: false,
							descendants: 'a',
							focusClass: 'aui-focus',
							keys: {
								next: 'down:40',
								previous: 'down:38'
							}
						}
					);

					var focusManager = contentBox.focusManager;

					contentBox.all('li').addClass('aui-menu-item');

					contentBox.delegate(
						'mouseenter',
						function(event) {
							focusManager.focus(event.currentTarget.one('a'));
						},
						'.aui-menu-item'
					);

					contentBox.delegate(
						'mouseleave',
						function(event) {
							focusManager.blur(event.currentTarget.one('a'));
						},
						'.aui-menu-item'
					);

					var MenuManager = Dockbar.MenuManager;

					var dockBar = instance.dockBar;

					var trigger = menu.get('trigger').item(0);
					var button = trigger.one('a');

					MenuManager.register(menu);

					menu.on(
						'visibleChange',
						function(event) {
							var visible = event.newVal;

							if (visible) {
								MenuManager.hideAll();
							}

							trigger.toggleClass('menu-button-active', visible);
						}
					);

					button.on(
						'focus',
						function(event) {
							menu.show();
						}
					);

					button.on(
						'keydown',
						function(event) {
							if (event.isKey('DOWN')) {
								focusManager.focus(0);
							}
						}
					);

					menu.on(
						'keydown',
						function(event) {
							if (focusManager.get('activeDescendant') == -1) {
								button.focus();
							}
							else {
								instance._updateMenu(event.domEvent, button);
							}
						}
					);

					instance[name] = menu.render(instance.dockBar);
				}

				return menu;
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

				if (instance.messageList.length == 1) {
					cssClass = 'first';
				}

				return '<div class="dockbar-message ' + cssClass + '" id="' + messageId + '">' + message + '</div>';
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

			_toggleAppShortcut: function(item, force) {
				var instance = this;

				item.toggleClass('lfr-portlet-used', force);

				instance._addContentNode.focusManager.refresh();
			},

			_updateMenu: function(event, item) {
				var instance = this;

				var menuButtons = instance.dockBar._menuButtons;
				var lastButtonIndex = menuButtons.size();
				var index = menuButtons.indexOf(item);

				if (index > -1) {
					var button;

					if (event.isKey('LEFT') && index > 0) {
						button = menuButtons.item(--index);
					}
					else if (event.isKey('RIGHT') && (index < lastButtonIndex)) {
						button = menuButtons.item(++index);
					}

					if (button) {
						if (event.isKeyInRange('LEFT', 'DOWN')) {
							event.halt();
						}

						var MenuManager = Dockbar.MenuManager;

						MenuManager.hideAll();

						button.focus();
					}
				}
			}
		};

		Liferay.provide(
			Dockbar,
			'addMenu',
			function(options) {
				var instance = this;

				instance._addMenu(options);
			},
			['aui-overlay-context', 'node-focusmanager']
		);

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

				dockBar.one('.pin-dockbar').on(
					EVENT_CLICK,
					function(event) {
						event.halt();

						BODY.toggleClass('lfr-dockbar-pinned');

						var pinned = BODY.hasClass('lfr-dockbar-pinned');

						Liferay.Store('liferay_dockbar_pinned', pinned);

						Liferay.fire(
							'dockbar:pinned',
							{
								pinned: pinned
							}
						);
					}
				);

				Liferay.Util.toggleControls(dockBar);

				var MenuManager = new A.OverlayManager(
					{
						zIndexBase: 100000
					}
				);

				var UnderlayManager = new A.OverlayManager(
					{
						zIndexBase: 300
					}
				);

				Dockbar.MenuManager = MenuManager;
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

							MenuManager.hideAll();
						}
						else {
							BODY.removeClass('showing-messages');
						}
					}
				);

				messages.closeTool.on(EVENT_CLICK, instance.clearMessages, instance);

				var addContent = instance._addMenu(
					{
						boundingBox: '#' + namespace + 'addContentContainer',
						name: 'addContent',
						trigger: '#' + namespace + 'addContent'
					}
				);

				if (addContent) {
					addContent.on(
						'show',
						function() {
							Liferay.fire('initLayout');
							Liferay.fire('initNavigation');
						}
					);

					var addContentNode = addContent.get(CONTENT_BOX);

					instance._addContentNode = addContentNode;

					var commonItems = addContentNode.one('.common-items');

					if (commonItems) {
						commonItems.removeClass('aui-menu-item');
					}

					addContentNode.delegate(
						EVENT_CLICK,
						function(event) {
							event.halt();

							var item = event.currentTarget;

							if (item.hasClass('lfr-portlet-used')) {
								return;
							}

							var portletId = item.attr('data-portlet-id');

							if (!item.hasClass('lfr-instanceable')) {
								instance._toggleAppShortcut(item, true);
							}

							Portlet.add(
								{
									portletId: portletId
								}
							);

							if (!event.shiftKey) {
								MenuManager.hideAll();
							}
						},
						'.app-shortcut'
					);

					addContentNode.focusManager.set('descendants', 'a:not(.lfr-portlet-used)');

					Liferay.on(
						'closePortlet',
						function(event) {
							var item = addContentNode.one('.app-shortcut[data-portlet-id=' + event.portletId + ']');

							if (item) {
								instance._toggleAppShortcut(item, false);
							}
						}
					);
				}

				var manageContent = instance._addMenu(
					{
						boundingBox: '#' + namespace + 'manageContentContainer',
						name: 'manageContent',
						trigger: '#' + namespace + 'manageContent'
					}
				);

				instance._addMenu(
					{
						boundingBox: '#' + namespace + 'mySitesContainer',
						name: 'mySites',
						trigger: '#' + namespace + 'mySites'
					}
				);

				if (A.UA.ie === 6) {
					var mySitesMenu = Dockbar.mySites;

					if (mySitesMenu) {
						mySitesMenu.onceAfter(
							'visibleChange',
							function(event) {
								if (event.newVal) {
									var menuBoundingBox = mySitesMenu.get('boundingBox');

									if (menuBoundingBox.height() > 300) {
										menuBoundingBox.addClass('aui-menu-scroll');
									}
								}
							}
						);
					}
				}

				var userOptionsContainer = A.one('#' + namespace + 'userOptionsContainer');

				if (userOptionsContainer) {
					instance._addMenu(
						{
							boundingBox: userOptionsContainer,
							name: 'userOptions',
							trigger: '#' + namespace + 'userAvatar'
						}
					);
				}

				if (BODY.hasClass('staging') || BODY.hasClass('live-view')) {
					instance._addMenu(
						{
							boundingBox: '#' + namespace + 'stagingContainer',
							name: 'staging',
							trigger: '#' + namespace + 'staging'
						}
					);
				}

				var addApplicationLink = A.one('#' + namespace + 'addApplication');

				if (addApplicationLink) {
					addApplicationLink.on(
						EVENT_CLICK,
						function(event) {
							addContent.hide();

							var addApplication = Dockbar.addApplication;

							if (!addApplication) {
								var setAddApplicationUI = function(visible) {
									BODY.toggleClass('lfr-has-sidebar', visible);
								};

								addApplication = instance._addUnderlay(
									{
										after: {
											render: function(event) {
												setAddApplicationUI(true);
											}
										},
										className: 'add-application',
										io: {
											after: {
												success: Dockbar._loadAddApplications
											},
											data: {
												doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
												p_l_id: themeDisplay.getPlid(),
												p_p_id: 87,
												p_p_state: 'exclusive'
											},
											uri: themeDisplay.getPathMain() + '/portal/render_portlet'
										},
										name: 'addApplication',
										width: '255px'
									}
								);

								addApplication.after(
									'visibleChange',
									function(event) {
										if (event.newVal) {
											Util.focusFormField('#layout_configuration_content');
										}

										setAddApplicationUI(event.newVal);
									}
								);
							}
							else {
								addApplication.show();
							}

							addApplication.focus();
						}
					);
				}

				if (manageContent) {
					manageContent.get(BOUNDING_BOX).delegate(
						EVENT_CLICK,
						function(event) {
							event.preventDefault();

							var fullDialog = event.currentTarget.ancestor('li').hasClass('full-dialog');

							manageContent.hide();

							var width = 960;

							if (fullDialog) {
								width = '90%';
							}

							instance._openWindow(
								{
									dialog: {
										align: Util.Window.ALIGN_CENTER,
										modal: fullDialog,
										width: width
									},
									id: 'manageContentDialog'
								},
								event.currentTarget
							);
						},
						'.use-dialog a'
					);
				}

				var manageCustomizationLink = A.one('#' + namespace + 'manageCustomization');

				if (manageCustomizationLink) {
					if (!manageCustomizationLink.hasClass('disabled')) {
						instance._controls = dockBar.one('.layout-customizable-controls');

						var columns = A.all('.portlet-column');

						var customizationsHandle;

						manageCustomizationLink.on(
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

								manageContent.hide();

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

				var myAccount = A.one('#' + namespace + 'userAvatar .user-links');

				if (myAccount) {
					myAccount.delegate(
						EVENT_CLICK,
						function(event) {
							event.preventDefault();

							var currentTarget = event.currentTarget;

							var controlPanelCategory = Lang.trim(currentTarget.attr('data-controlPanelCategory'));

							var uri = currentTarget.attr('href');
							var title = currentTarget.attr('title');

							if (controlPanelCategory) {
								uri = Liferay.Util.addParams('controlPanelCategory=' + controlPanelCategory, uri) || uri;
							}

							instance._openWindow(
								{
									dialog: {
										align: Util.Window.ALIGN_CENTER,
										width: 960
									},
									title: title,
									uri: uri
								}
							);
						},
						'a.use-dialog'
					);
				}

				dockBar._menuButtons = dockBar.all('ul.aui-toolbar > li > a, .user-links a, .sign-out a');

				dockBar.delegate(
					'keydown',
					function(event) {
						instance._updateMenu(event, event.currentTarget);
					},
					'.aui-toolbar a'
				);

				Liferay.fire('dockbarLoaded');
			},
			['aui-io-request', 'aui-overlay-context', 'liferay-dockbar-underlay', 'liferay-store', 'node-focusmanager']
		);

		Liferay.provide(
			Dockbar,
			'_loadAddApplications',
			function(event, id, obj) {
				var contentBox = Dockbar.addApplication.get(CONTENT_BOX);

				LayoutConfiguration._dialogBody = contentBox;

				LayoutConfiguration._loadContent();
			},
			['liferay-layout-configuration']
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
					p_v_g_id: themeDisplay.getParentGroupId()
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
		requires: ['aui-node', 'event-touch']
	}
);