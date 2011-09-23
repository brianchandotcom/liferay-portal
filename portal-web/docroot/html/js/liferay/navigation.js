AUI().add(
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
		 * hasPermission {boolean}: Whether the current user has permission to modify the navigation
		 * layoutIds {array}: The displayable layout ids.
		 * layoutSetBranchId {String}: The id of the layout set branch (when branching is enabled).
		 * navBlock {string|object}: A selector or DOM element of the navigation.
		 */

		var Navigation = A.Component.create(
			{
				ATTRS: {
					hasPermission: {
						value: false
					},

					isModifiable: {
						getter: function(value) {
							var instance = this;

							return instance.get('hasPermission') &&
									instance.get('navBlock').hasClass('modify-pages');
						},
						value: false
					},

					isSortable: {
						getter: function(value) {
							var instance = this;

							return instance.get('hasPermission') &&
									instance.get('navBlock').hasClass('sort-pages');
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
					TPL_DELETE_BUTTON: '<span class="delete-tab aui-helper-hidden">X</span>',

					initializer: function(config) {
						var instance = this;

						var navBlock = instance.get('navBlock');

						if (navBlock) {
							instance._updateURL = themeDisplay.getPathMain() + '/layouts_admin/update_page';

							var items = navBlock.all('> ul > li');
							var layoutIds = instance.get('layoutIds');

							items.each(
								function(item, index, collection) {
									item._LFR_layoutId = layoutIds[index];
								}
							);

							instance._makeAddable();
							instance._makeDeletable();
							instance._makeSortable();
							instance._makeEditable();

							instance.on('savePage', instance._savePage);
							instance.on('cancelPage', instance._cancelPage);

							navBlock.delegate('keypress', A.bind(instance._onKeypress, instance), 'input');
						}
					},

					_addPage: function(event) {
						var instance = this;

						if (!event.shiftKey) {
							Dockbar.MenuManager.hideAll();
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

					_createEditor: function(listItem, options) {
						var instance = this;

						var prototypeTemplate = instance._prototypeMenuTemplate || '';

						prototypeTemplate = prototypeTemplate.replace(/name=\"template\"/g, 'name="' + A.guid() + '_template"');

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
								id: 'save'
							}
						];

						if (prototypeTemplate && !prevVal) {
							icons.unshift(
								{
									activeState: true,
									handler: function(event) {
										var toolItem = this;

										event.halt();

										var action = 'show';

										if (toolItem.StateInteraction.get('active')) {
											action = 'hide';
										}

										comboBox._optionsOverlay[action]();
									},
									icon: 'gear',
									id: 'options'
								}
							);
						}

						var optionsOverlay = new A.Overlay(
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
						).render(listItem);

						if (prototypeTemplate && instance._optionsOpen && !prevVal) {
							var optionItem = comboBox.icons.item('options');

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
						overlayContentBox.addClass('lfr-menu-list lfr-component lfr-page-templates');

						comboContentBox.swallowEvent('click');
						overlayContentBox.swallowEvent('click');

						Util.focusFormField(comboField.get('node'));

						var realign = A.bind(optionsOverlay.fire, optionsOverlay, 'align');

						optionsOverlay.on('visibleChange', realign);

						instance.on('stopEditing', realign);
						instance.on('startEditing', realign);

						if (prevVal) {
							instance.fire('editPage');
						}
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

					_deleteButton: function(obj) {
						var instance = this;

						obj.append(instance.TPL_DELETE_BUTTON);
					},

					_handleKeyDown: function(event) {
						var instance = this;

						if (event.isKey('DELETE')) {
							instance._removePage(event);
						}
					},

					_makeAddable: function() {
						var instance = this;

						if (instance.get('isModifiable')) {
							var navList = instance.get('navBlock').one('ul');

							var themeImages = themeDisplay.getPathThemeImages();

							var prototypeMenuNode = A.one('#layoutPrototypeTemplate');

							if (prototypeMenuNode) {
								instance._prototypeMenuTemplate = prototypeMenuNode.html();
							}

							if (instance.get('hasPermission')) {
								var addPageButton = A.one('#addPage');

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

							var navItems = navBlock.all('> ul > li').filter(':not(.selected)');

							navBlock.delegate(
								['click', 'touchstart'],
								A.bind(instance._removePage, instance),
								'.delete-tab'
							);

							navBlock.delegate(
								'keydown',
								A.bind(instance._handleKeyDown, instance),
								'> ul > li:not(.selected) a'
							);

							navBlock.delegate(
								'mouseenter',
								A.rbind(instance._toggleDeleteButton, instance, 'removeClass'),
								'li'
							);

							navBlock.delegate(
								'mouseleave',
								A.rbind(instance._toggleDeleteButton, instance, 'addClass'),
								'li'
							);

							instance._deleteButton(navItems);
						}
					},

					_makeEditable: function() {
						var instance = this;

						if (instance.get('isModifiable')) {
							var currentItem = instance.get('navBlock').one('li.selected');

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

										currentLink.on(
											'mouseenter',
											function(event) {
												if (!themeDisplay.isStateMaximized() || event.shiftKey) {
													currentSpan.setStyle('cursor', 'text');
												}
											}
										);

										currentLink.on('mouseleave', A.bind(currentSpan.setStyle, currentSpan, 'cursor', 'pointer'));

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

					_makeSortable: function() {
						var instance = this;

						if (instance.get('isSortable')) {
							var navBlock = instance.get('navBlock');

							var sortable = new A.Sortable(
								{
									container: navBlock,
									moveType: 'move',
									nodes: 'li',
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

					_removePage: function(event) {
						var instance = this;

						var navBlock = instance.get('navBlock');

						var tab = event.currentTarget.ancestor('li');

						if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-page'))) {
							var data = {
								cmd: 'delete',
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								groupId: themeDisplay.getParentGroupId(),
								layoutId: tab._LFR_layoutId,
								layoutSetBranchId: instance.get('layoutSetBranchId'),
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

					_savePage: function(event, obj, oldName) {
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
										groupId: themeDisplay.getParentGroupId(),
										languageId: themeDisplay.getLanguageId(),
										layoutId: themeDisplay.getLayoutId(),
										name: pageTitle,
										privateLayout: themeDisplay.isPrivateLayout()
									};

									onSuccess = function(event, id, obj) {
										var data = this.get('responseData');

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
									groupId: themeDisplay.getParentGroupId(),
									layoutPrototypeId: layoutPrototypeId,
									mainPath: themeDisplay.getPathMain(),
									name: pageTitle,
									parentLayoutId: themeDisplay.getParentLayoutId(),
									privateLayout: themeDisplay.isPrivateLayout()
								};

								onSuccess = function(event, id, obj) {
									var data = this.get('responseData');

									var tabHtml = Lang.sub(
										TPL_TAB_LINK,
										{
											url: data.url,
											pageTitle: pageTitle
										}
									);

									var newTab = A.Node.create(tabHtml);

									newTab.setStyle('cursor', 'move');

									listItem._LFR_layoutId = data.layoutId;

									listItem.append(newTab);

									comboBox.destroy();

									listItem.addClass('sortable-item');

									instance._deleteButton(listItem);

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

					_saveSortables: function(node) {
						var instance = this;

						var priority = instance.get('navBlock').all('li').indexOf(node);

						var data = {
							cmd: 'priority',
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							groupId: themeDisplay.getParentGroupId(),
							layoutId: node._LFR_layoutId,
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

					_toggleDeleteButton: function(event, action) {
						var instance = this;

						var deleteTab = event.currentTarget.one('.delete-tab');

						if (deleteTab) {
							deleteTab[action]('aui-helper-hidden');
						}
					},

					_optionsOpen: true,
					_updateURL: ''
				}
			}
		);

		Liferay.Navigation = Navigation;
	},
	'',
	{
		requires: ['aui-form-combobox', 'aui-io-request', 'dd-constrain', 'event-touch', 'json-parse', 'node-event-simulate', 'overlay', 'selector-css3', 'sortable']
	}
);