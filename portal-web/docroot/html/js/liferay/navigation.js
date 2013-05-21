AUI.add(
	'liferay-navigation',
	function(A) {
		var Dockbar = Liferay.Dockbar;
		var Util = Liferay.Util;
		var Lang = A.Lang;

		var TPL_LIST_ITEM = '<li class="add-page"></li>';

		var TPL_TAB_LINK = '<a href="{url}"><span>{pageTitle}</span></a>';

		/**
		 * OPTIONS
		 *
		 * Required
		 * layoutIds {array}: The displayable layout ids.
		 * layoutSetBranchId {String}: The id of the layout set branch (when branching is enabled).
		 * navBlock {string|object}: A selector or DOM element of the navigation.
		 */

		var Navigation = A.Component.create(
			{
				ATTRS: {
					hasAddLayoutPermission: {
						value: false
					},

					isAddable: {
						getter: function(value) {
							var instance = this;

							return instance.get('hasAddLayoutPermission') && instance.get('navBlock').hasClass('modify-pages');
						},
						value: false
					},

					isModifiable: {
						getter: function(value) {
							var instance = this;

							return instance.get('navBlock').hasClass('modify-pages');
						},
						value: false
					},

					isSortable: {
						getter: function(value) {
							var instance = this;

							return instance.get('navBlock').hasClass('sort-pages');
						},
						value: false
					},

					layoutIds: {
						value: []
					},

					layoutSetBranchId: {
						value: 0
					},

					navBlock: {
						lazyAdd: false,
						setter: function(value) {
							var instance = this;

							value = A.one(value);

							if (!value) {
								value = A.Attribute.INVALID_VALUE;
							}

							return value;
						},
						value: null
					}
				},

				EXTENDS: A.Base,

				NAME: 'navigation',

				prototype: {
					TPL_DELETE_BUTTON: '<i class="icon-remove-sign delete-tab hide"></i>',

					initializer: function(config) {
						var instance = this;

						var navBlock = instance.get('navBlock');

						if (navBlock) {
							instance._updateURL = themeDisplay.getPathMain() + '/layouts_admin/update_page?p_auth=' + Liferay.authToken;

							var navItemSelector = Liferay.Data.NAV_ITEM_SELECTOR || 'ul > li';

							var items = navBlock.all(navItemSelector);

							var layoutIds = instance.get('layoutIds');

							var cssClassBuffer = [];

							items.each(
								function(item, index, collection) {
									var layoutConfig = layoutIds[index];

									if (layoutConfig) {
										item._LFR_layoutId = layoutConfig.id;

										if (layoutConfig.deletable) {
											cssClassBuffer.push('lfr-nav-deletable');
										}

										if (layoutConfig.sortable) {
											cssClassBuffer.push('lfr-nav-sortable');
										}

										if (layoutConfig.updateable) {
											cssClassBuffer.push('lfr-nav-updateable');
										}

										if (cssClassBuffer.length) {
											item.addClass(cssClassBuffer.join(' '));

											cssClassBuffer.length = 0;
										}
									}
								}
							);

							instance._navItemSelector = navItemSelector;

							instance._makeAddable();
							instance._makeDeletable();
							instance._makeSortable();
							instance._makeEditable();

							instance.on('savePage', A.bind('_savePage', instance));
							instance.on('cancelPage', instance._cancelPage);

							navBlock.delegate('keypress', A.bind('_onKeypress', instance), 'input');
						}
					},

					_addPage: function(event) {
						var instance = this;

						if (!event.shiftKey) {
							Dockbar.UnderlayManager.hideAll();
						}

						var navBlock = instance.get('navBlock');
						var addBlock = A.Node.create(TPL_LIST_ITEM);

						navBlock.show();

						navBlock.one('ul').append(addBlock);

						instance._createEditor(
							addBlock,
							{
								prevVal: ''
							}
						);
					},

					_cancelPage: function(event) {
						var instance = this;

						var actionNode = event.actionNode;
						var toolbar = event.toolbar;
						var field = event.field;
						var listItem = event.listItem;

						var navBlock = instance.get('navBlock');

						if (actionNode) {
							currentLink = actionNode.one('a');
							currentLink.show();

							field.val(instance.prevVal);
						}
						else {
							listItem.remove(true);
						}

						toolbar.destroy();

						if (!navBlock.one('li')) {
							navBlock.hide();
						}
					},

					_createDeleteButton: function(obj) {
						var instance = this;

						obj.append(instance.TPL_DELETE_BUTTON);
					},

					_deleteButton: function(obj) {
						var instance = this;

						if (!A.instanceOf(obj, A.NodeList)) {
							obj = A.all(obj);
						}

						obj.each(
							function(item, index, collection) {
								if (item.hasClass('lfr-nav-deletable')) {
									instance._createDeleteButton(item);
								}
							}
						);
					},

					_handleKeyDown: function(event) {
						var instance = this;

						if (event.isKey('DELETE') && !event.currentTarget.ancestor('li.selected')) {
							instance._removePage(event);
						}
					},

					_makeAddable: function() {
						var instance = this;

						if (instance.get('isAddable')) {
							var prototypeMenuNode = A.one('#layoutPrototypeTemplate');

							if (prototypeMenuNode) {
								instance._prototypeMenuTemplate = prototypeMenuNode.html();
							}

							if (instance.get('hasAddLayoutPermission')) {
								var addPageButton = A.one('#' + Dockbar._namespace + 'addPage');

								if (addPageButton) {
									addPageButton.on('click', instance._addPage, instance);
								}
							}
						}
					},

					_makeDeletable: function() {
						var instance = this;

						if (instance.get('isModifiable')) {
							var navBlock = instance.get('navBlock');

							var navItemSelector = instance._navItemSelector;

							var navItems = navBlock.all(navItemSelector).filter(
								function(item, index, collection) {
									return !item.hasClass('selected');
								}
							);

							navBlock.delegate(
								['click', 'touchstart'],
								A.bind('_removePage', instance),
								'.delete-tab'
							);

							navBlock.delegate(
								'keydown',
								A.bind('_handleKeyDown', instance),
								navItemSelector
							);

							navBlock.delegate(
								'mouseenter',
								A.rbind('_toggleDeleteButton', instance, 'show'),
								'li'
							);

							navBlock.delegate(
								'mouseleave',
								A.rbind('_toggleDeleteButton', instance, 'hide'),
								'li'
							);

							instance._deleteButton(navItems);
						}
					},

					_makeEditable: function() {
						var instance = this;

						if (instance.get('isModifiable')) {
							var currentItem = instance.get('navBlock').one('li.selected.lfr-nav-updateable');

							if (currentItem) {
								var currentLink = currentItem.one('a');

								if (currentLink) {
									currentLink.on(
										'click',
										function(event) {
											if (event.shiftKey) {
												event.halt();
											}
										}
									);

									currentLink.on(
										'mouseenter',
										function(event) {
											if (!themeDisplay.isStateMaximized() || event.shiftKey) {
												currentLink.setStyle('cursor', 'text');
											}
										}
									);

									currentLink.on('mouseleave', A.bind('setStyle', currentLink, 'cursor', 'pointer'));

									currentLink.on(
										'click',
										function(event) {
											if (themeDisplay.isStateMaximized() && !event.shiftKey) {
												return;
											}

											event.halt();

											var textNode = event.currentTarget;

											var actionNode = textNode.get('parentNode');
											var currentText = textNode.text();

											instance._createEditor(
												currentItem,
												{
													actionNode: actionNode,
													currentLink: currentLink,
													prevVal: currentText,
													textNode: textNode
												}
											);
										}
									);
								}
							}
						}
					},

					_onKeypress: function(event) {
						var instance = this;

						if (event.isKeyInSet('ENTER', 'ESC')) {
							var listItem = event.currentTarget.ancestor('li');
							var eventType = 'savePage';

							if (event.isKey('ESC')) {
								eventType = 'cancelPage';
							}

							var toolbar = listItem._toolbar;

							toolbar.fire(eventType);
						}
					},

					_toggleDeleteButton: function(event, action) {
						var instance = this;

						var deleteTab = event.currentTarget.one('.delete-tab');

						if (deleteTab) {
							deleteTab[action](true);
						}
					},

					_optionsOpen: true,
					_updateURL: ''
				}
			}
		);

		Liferay.provide(
			Navigation,
			'_createEditor',
			function(listItem, options) {
				var instance = this;

				var prototypeTemplate = instance._prototypeMenuTemplate || '';

				var prevVal = options.prevVal;
				instance.prevVal = prevVal;

				if (options.actionNode) {
					options.currentLink.hide();
				}

				var docClick;

				var relayEvent = function(event) {
					docClick.detach();

					var eventName = event.type.split(':');

					eventName = eventName[1] || eventName[0];

					instance.fire(eventName, options);
				};

				var icons = [
					{
						icon: 'icon-ok',
						id: 'Save',
						title: 'Save',
						on: {
							click: function(event) {
								toolbar.fire('savePage', options);
							}
						}
					}
				];

				if (prototypeTemplate && !prevVal) {
					icons.unshift(
						{
							icon: 'icon-cog',
							id: 'Options',
							title: 'Options',
						}
					);
				}

				var optionsPopover = instance._popover;

				if (!optionsPopover) {
					optionsPopover = new A.Popover(
						{
							bodyContent: prototypeTemplate,
							align: {
								node: optionItem,
								points: ['tc', 'bc']
							},
							on: {
								visibleChange: function(event) {
									var instance = this;

									if (event.newVal) {
										if (!instance.get('rendered')) {
											instance.set('align.node', optionItem);
											instance.setStdModContent(A.WidgetStdMod.BODY, prototypeTemplate);
											instance.render();
										}
									}
								}
							},
							zIndex: 200
						}
					);
					instance._popover = optionsPopover;
				}

				var toolbar = new A.Toolbar(
					{
						after: {
							destroy: function(event) {
								var instance = this;
								instance.fire('stopEditing');
							},
							render: function(event) {
								var instance = this;

								docClick = A.getDoc().on('click',
									function(event) {
										docClick.detach();

										instance.fire('cancelPage', options);
									}
								);

								instance.fire('startEditing');
							}
						},
						boundingBox: A.Node.create('<div class="input-append" />').prependTo(listItem),
						children: icons,
						on: {
							destroy: function(event) {
								var instance = this;

								if (optionsPopover.get('rendered')) {
									optionsPopover.hide();

									optionsPopover.setStdModContent(A.WidgetStdMod.BODY, '');
								}
							}
						}
					}
				).render();

				var navInput = A.Node.create('<input type="text" class="span2">');
				navInput.val(prevVal);
				var toolbarBoundingBox = A.one(toolbar.get('boundingBox'));
				toolbarBoundingBox.prepend(navInput);

				if (prototypeTemplate && instance._optionsOpen && !prevVal) {
					var optionItem = toolbar.item(1)._host;

					optionItem.toggleClass('active', true);
					optionsPopover.show();
				}

				var toolbarField = toolbarBoundingBox.one('input');

				var toolbarContentBox = toolbar.get('contentBox');

				var popoverBoundingBox = optionsPopover.get('boundingBox');
				var popoverContentBox = optionsPopover.get('contentBox');

				options.listItem = listItem;
				options.toolbar = toolbar;
				options.field = toolbarField;
				options.optionsPopover = optionsPopover;

				toolbar.on('savePage', relayEvent);
				toolbar.on('cancelPage', relayEvent);

				toolbar._optionsPopover = optionsPopover;

				listItem._toolbar = toolbar;

				popoverContentBox.addClass('lfr-menu-list lfr-page-templates');

				toolbarContentBox.swallowEvent('click');
				popoverContentBox.swallowEvent('click');

				Util.focusFormField(toolbarField);

				var realign = A.bind('fire', optionsPopover, 'align');

				optionsPopover.on('visibleChange', realign);

				instance.on('stopEditing', realign);
				instance.on('startEditing', realign);

				if (prevVal) {
					instance.fire('editPage');
				}
			},
			['aui-toolbar', 'aui-popover'],
			true
		);

		Liferay.provide(
			Navigation,
			'_makeSortable',
			function() {
				var instance = this;

				if (instance.get('isSortable')) {
					var navBlock = instance.get('navBlock');

					var sortable = new A.Sortable(
						{
							container: navBlock,
							moveType: 'move',
							nodes: '.lfr-nav-sortable',
							opacity: '.5',
							opacityNode: 'currentNode'
						}
					);

					sortable.delegate.on(
						'drag:end',
						function(event) {
							var dragNode = event.target.get('node');

							instance._saveSortables(dragNode);

							Liferay.fire(
								'navigation',
								{
									item: dragNode.getDOM(),
									type: 'sort'
								}
							);
						}
					);

					sortable.delegate.on(
						'drag:start',
						function(event) {
							var dragNode = event.target.get('dragNode');

							dragNode.addClass('lfr-navigation-proxy');
						}
					);

					sortable.delegate.dd.removeInvalid('a');
				}
			},
			['dd-constrain', 'sortable'],
			true
		);

		Liferay.provide(
			Navigation,
			'_removePage',
			function(event) {
				var instance = this;

				var navBlock = instance.get('navBlock');

				var tab = event.currentTarget.ancestor('li');

				if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-page'))) {
					var data = {
						cmd: 'delete',
						doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
						groupId: themeDisplay.getSiteGroupId(),
						layoutId: tab._LFR_layoutId,
						layoutSetBranchId: instance.get('layoutSetBranchId'),
						p_auth: Liferay.authToken,
						privateLayout: themeDisplay.isPrivateLayout()
					};

					A.io.request(
						instance._updateURL,
						{
							data: data,
							on: {
								success: function() {
									Liferay.fire(
										'navigation',
										{
											item: tab,
											type: 'delete'
										}
									);

									tab.remove(true);

									if (!navBlock.one('ul li')) {
										navBlock.hide();
									}
								}
							}
						}
					);
				}
			},
			['aui-io-request'],
			true
		);

		Liferay.provide(
			Navigation,
			'_savePage',
			function(event, obj, oldName) {
				var instance = this;

				var actionNode = event.actionNode;
				var toolbar = event.toolbar;
				var field = event.field;
				var listItem = event.listItem;
				var textNode = event.textNode;

				var pageTitle = field.get('value');

				pageTitle = Lang.trim(pageTitle);
				prevVal = textNode ? Lang.trim(textNode.text()) : '';

				var data = null;
				var onSuccess = null;

				if (pageTitle) {
					if (actionNode) {
						if (!pageTitle || pageTitle != prevVal) {
							data = {
								cmd: 'name',
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								groupId: themeDisplay.getSiteGroupId(),
								languageId: themeDisplay.getLanguageId(),
								layoutId: themeDisplay.getLayoutId(),
								name: pageTitle,
								p_auth: Liferay.authToken,
								privateLayout: themeDisplay.isPrivateLayout()
							};

							onSuccess = function(event, id, obj) {
								var doc = A.getDoc();

								textNode.text(pageTitle);
								currentLink = actionNode.one('a');
								currentLink.show();

								toolbar.destroy();

								var oldTitle = doc.get('title');

								var regex = new RegExp(prevVal, 'g');

								newTitle = oldTitle.replace(regex, pageTitle);

								doc.set('title', newTitle);
							};
						}
						else {
							// The new name is the same as the old one

							toolbar.fire('cancelPage');
						}
					}
					else {
						var popoverBoundingBox = toolbar._optionsPopover.get('boundingBox');
						var selectedInput = popoverBoundingBox.one('input:checked');
						if (selectedInput) {
							var layoutPrototypeId = selectedInput.val();
						}

						data = {
							cmd: 'add',
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							explicitCreation: true,
							groupId: themeDisplay.getSiteGroupId(),
							layoutPrototypeId: layoutPrototypeId,
							mainPath: themeDisplay.getPathMain(),
							name: pageTitle,
							p_auth: Liferay.authToken,
							parentLayoutId: themeDisplay.getParentLayoutId(),
							privateLayout: themeDisplay.isPrivateLayout()
						};

						onSuccess = function(event, id, obj) {
							var data = this.get('responseData');

							var tabHtml = Lang.sub(
								TPL_TAB_LINK,
								{
									url: data.url,
									pageTitle: Lang.String.escapeHTML(pageTitle)
								}
							);

							var newTab = A.Node.create(tabHtml);

							newTab.setStyle('cursor', 'move');

							listItem._LFR_layoutId = data.layoutId;

							listItem.append(newTab);

							toolbar.destroy();

							if (data.sortable) {
								listItem.addClass('sortable-item lfr-nav-sortable');
							}

							if (data.updateable) {
								listItem.addClass('lfr-nav-updateable');
							}

							if (data.deletable) {
								instance._createDeleteButton(listItem);
							}

							Liferay.fire(
								'navigation',
								{
									item: listItem,
									type: 'add'
								}
							);
						};
					}

					if (data) {
						A.io.request(
							instance._updateURL,
							{
								data: data,
								dataType: 'json',
								on: {
									success: onSuccess
								}
							}
						);
					}
				}
			},
			['aui-io-request'],
			true
		);

		Liferay.provide(
			Navigation,
			'_saveSortables',
			function(node) {
				var instance = this;

				var navItems = instance.get('navBlock').all('li');

				var priority = -1;

				navItems.some(
					function(item, index, collection) {
						if (!item.ancestor().hasClass('child-menu')) {
							priority++;
						}

						return item == node;
					}
				);

				var data = {
					cmd: 'priority',
					doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
					groupId: themeDisplay.getSiteGroupId(),
					layoutId: node._LFR_layoutId,
					p_auth: Liferay.authToken,
					priority: priority,
					privateLayout: themeDisplay.isPrivateLayout()
				};

				A.io.request(
					instance._updateURL,
					{
						data: data
					}
				);
			},
			['aui-io-request'],
			true
		);

		Liferay.Navigation = Navigation;
	},
	'',
	{
		requires: ['aui-component', 'transition']
	}
);