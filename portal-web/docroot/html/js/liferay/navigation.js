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
						var comboBox = event.comboBox;
						var field = event.field;
						var listItem = event.listItem;

						var navBlock = instance.get('navBlock');

						if (actionNode) {
							actionNode.show();

							field.resetValue();
						}
						else {
							listItem.remove(true);
						}

						comboBox.destroy();

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

							var comboBox = listItem._comboBox;

							comboBox.fire(eventType);
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

				var id = A.guid();

				var prototypeTemplate = instance._prototypeMenuTemplate || '';

				prototypeTemplate = prototypeTemplate.replace(/name=\"template\"/g, 'name="' + id + 'Template"');

				var prevVal = options.prevVal;

				if (options.actionNode) {
					options.actionNode.hide();
				}

				var docClick = A.getDoc().on(
					'click',
					function(event) {
						docClick.detach();

						instance.fire('cancelPage', options);
					}
				);

				var relayEvent = function(event) {
					docClick.detach();

					var eventName = event.type.split(':');

					eventName = eventName[1] || eventName[0];

					instance.fire(eventName, options);
				};

				var icons = [
					{
						handler: function(event) {
							comboBox.fire('savePage', options);
						},
						icon: 'check',
						id: id + 'Save'
					}
				];

				if (prototypeTemplate && !prevVal) {
					icons.unshift(
						{
							activeState: true,
							handler: function(event) {
								event.halt();

								comboBox._optionsOverlay.toggle(this.StateInteraction.get('active'));
							},
							icon: 'gear',
							id: id + 'Options'
						}
					);
				}

				var optionsOverlay = new A.OverlayBase(
					{
						bodyContent: prototypeTemplate,
						align: {
							node: listItem,
							points: ['tl', 'bl']
						},
						on: {
							visibleChange: function(event) {
								var instance = this;

								if (event.newVal) {
									if (!instance.get('rendered')) {
										instance.set('align.node', comboBox.get('contentBox'));

										instance.render();
									}
								}
							}
						},
						zIndex: 200
					}
				);

				var comboBox = new A.Combobox(
					{
						after: {
							destroy: function(event) {
								instance.fire('stopEditing');
							},
							render: function(event) {
								instance.fire('startEditing');
							}
						},
						boundingBox: A.Node.create('<div />').prependTo(listItem),
						field: {
							value: prevVal
						},
						icons: icons,
						on: {
							destroy: function(event) {
								var instance = this;

								if (optionsOverlay.get('rendered')) {
									optionsOverlay.destroy();
								}
							}
						}
					}
				).render();

				if (prototypeTemplate && instance._optionsOpen && !prevVal) {
					var optionItem = comboBox.icons.item(id + 'Options');

					optionItem.StateInteraction.set('active', true);
					optionsOverlay.show();
				}

				var comboField = comboBox._field;

				var comboContentBox = comboBox.get('contentBox');

				var overlayBoundingBox = optionsOverlay.get('boundingBox');
				var overlayContentBox = optionsOverlay.get('contentBox');

				options.listItem = listItem;
				options.comboBox = comboBox;
				options.field = comboField;
				options.optionsOverlay = optionsOverlay;

				comboBox.on('savePage', relayEvent);
				comboBox.on('cancelPage', relayEvent);

				comboBox._optionsOverlay = optionsOverlay;

				listItem._comboBox = comboBox;

				overlayBoundingBox.setStyle('minWidth', listItem.get('offsetWidth') + 'px');
				overlayContentBox.addClass('lfr-menu-list lfr-page-templates');

				comboContentBox.swallowEvent('click');
				overlayContentBox.swallowEvent('click');

				Util.focusFormField(comboField.get('node'));

				var realign = A.bind('fire', optionsOverlay, 'align');

				optionsOverlay.on('visibleChange', realign);

				instance.on('stopEditing', realign);
				instance.on('startEditing', realign);

				if (prevVal) {
					instance.fire('editPage');
				}
			},
			['aui-form-combobox-deprecated', 'aui-overlay-deprecated'],
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
				var comboBox = event.comboBox;
				var field = event.field;
				var listItem = event.listItem;
				var textNode = event.textNode;

				var pageTitle = field.get('value');

				pageTitle = Lang.trim(pageTitle);

				var data = null;
				var onSuccess = null;

				if (pageTitle) {
					if (actionNode) {
						if (field.isDirty()) {
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

								comboBox.destroy();

								var oldTitle = doc.get('title');

								var regex = new RegExp(field.get('prevVal'), 'g');

								newTitle = oldTitle.replace(regex, pageTitle);

								doc.set('title', newTitle);
							};
						}
						else {
							// The new name is the same as the old one

							comboBox.fire('cancelPage');
						}
					}
					else {
						var optionsOverlay = comboBox._optionsOverlay;
						var selectedInput = optionsOverlay.get('contentBox').one('input:checked');
						var layoutPrototypeId = selectedInput && selectedInput.val();

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

							comboBox.destroy();

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