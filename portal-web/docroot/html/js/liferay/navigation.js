AUI.add(
	'liferay-navigation',
	function(A) {
		var Dockbar = Liferay.Dockbar;
		var Util = Liferay.Util;
		var Lang = A.Lang;

		var TPL_EDITOR = '<div class="add-page-editor"><div class="input-append"></div></div>';

		var TPL_FIELD_INPUT = '<input class="add-page-editor-input" type="text" value="{0}" />';

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
					TPL_DELETE_BUTTON: '<span class="delete-tab">&times;</span>',

					initializer: function(config) {
						var instance = this;

						var navBlock = instance.get('navBlock');

						if (navBlock) {
							instance._updateURL = themeDisplay.getPathMain() + '/layouts_admin/update_page?p_auth=' + Liferay.authToken;

							var navItemSelector = Liferay.Data.NAV_ITEM_SELECTOR || '> ul > li';

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

						event.halt();

						if (!event.shiftKey) {
							Liferay.fire('dockbar:closeAddContentMenu');
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
							actionNode.show();

							field.val(event.prevVal);
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

					_hoverNavItem: function(event) {
						var instance = this;

						event.currentTarget.toggleClass('lfr-nav-hover', (event.type == 'mouseenter'));
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

							navBlock.delegate(['mouseenter', 'mouseleave'], instance._hoverNavItem, 'li', instance);

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
									var currentSpan = currentLink.one('span');

									if (currentSpan) {
										currentLink.on(
											'click',
											function(event) {
												if (event.shiftKey) {
													event.halt();
												}
											}
										);

										currentSpan.on(
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
														prevVal: currentText,
														textNode: textNode
													}
												);
											}
										);
									}
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

							listItem._toolbar.fire(eventType);
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

				var prevVal = Lang.trim(options.prevVal);

				if (options.actionNode) {
					options.actionNode.hide();
				}

				var relayEvent = function(event) {
					if (docClick) {
						docClick.detach();
					}

					var eventName = event.type.split(':');

					eventName = eventName[1] || eventName[0];

					instance.fire(eventName, options);
				};

				var icons = [
					{
						icon: 'icon-ok',
						on: {
							click: function(event) {
								toolbar.fire('savePage', options);
							}
						},
						title: Liferay.Language.get('save')
					}
				];

				if (prototypeTemplate && !prevVal) {
					icons.unshift(
						{
							icon: 'icon-cog',
							on: {
								click: function(event) {
									var button = event.currentTarget.get('boundingBox');

									var active = button.hasClass('active');

									optionsPopover.set('visible', !active);

									button.toggleClass('active');
								}
							},
							title: Liferay.Language.get('options')
						}
					);
				}

				var editorContainer = A.Node.create(TPL_EDITOR);

				var docClick = editorContainer.on(
					'clickoutside',
					function(event) {
						docClick.detach();

						instance.fire('cancelPage', options);
					}
				);

				var toolbarBoundingBox = editorContainer.one('.input-append');

				var toolbar = new A.Toolbar(
					{
						after: {
							destroy: function(event) {
								instance.fire('stopEditing');

								optionsPopover.destroy();

								editorContainer.remove(true);

								if (docClick) {
									docClick.detach();
								}
							},
							render: function(event) {
								instance.fire('startEditing');
							}
						},
						boundingBox: toolbarBoundingBox,
						children: icons
					}
				).render(editorContainer);

				toolbar.get('contentBox').swallowEvent('click');

				var optionItem;

				var optionsPopover = new A.Popover(
					{
						bodyContent: prototypeTemplate,
						align: {
							points: ['tc', 'bc']
						},
						on: {
							visibleChange: function(event) {
								var instance = this;

								if (event.newVal && !instance.get('rendered')) {
									instance.set('align.node', optionItem);

									instance.render(editorContainer);
								}
							}
						},
						position: 'bottom',
						zIndex: 200
					}
				);

				var popoverContentBox = optionsPopover.get('contentBox');

				popoverContentBox.addClass('lfr-menu-list lfr-page-templates');

				popoverContentBox.swallowEvent('click');

				var toolbarField = A.Node.create(Lang.sub(TPL_FIELD_INPUT, [prevVal]));

				toolbarBoundingBox.prepend(toolbarField);

				listItem.prepend(editorContainer);

				if (prototypeTemplate && instance._optionsOpen && !prevVal) {
					optionItem = toolbar.item(1).get('boundingBox');

					optionItem.addClass('active');

					optionsPopover.show();
				}

				options.listItem = listItem;
				options.optionsPopover = optionsPopover;
				options.toolbar = toolbar;

				options.field = editorContainer.one('input');

				toolbar.on(['cancelPage', 'savePage'], relayEvent);

				toolbar._optionsPopover = optionsPopover;

				listItem._toolbar = toolbar;

				Util.focusFormField(toolbarField);

				var realign = A.bind('fire', optionsPopover, 'align');

				optionsPopover.on('visibleChange', realign);

				instance.on(['startEditing', 'stopEditing'], realign);

				if (prevVal) {
					instance.fire('editPage');
				}
			},
			['aui-toolbar', 'aui-popover', 'event-outside'],
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
			function(event, obj) {
				var instance = this;

				var actionNode = event.actionNode;
				var toolbar = event.toolbar;
				var field = event.field;
				var listItem = event.listItem;
				var textNode = event.textNode;

				var pageTitle = field.get('value');

				var prevVal = Lang.trim(event.prevVal);

				pageTitle = Lang.trim(pageTitle);

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

								actionNode.show();

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

						var layoutPrototypeId;

						if (selectedInput) {
							layoutPrototypeId = selectedInput.val();
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

							listItem._LFR_layoutId = data.layoutId;

							listItem.append(newTab);

							toolbar.destroy();

							if (data.sortable) {
								listItem.addClass('lfr-nav-sortable sortable-item');
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
		requires: ['aui-component']
	}
);