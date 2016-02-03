AUI.add(
	'liferay-navigation',
	function(A) {
		var ANode = A.Node;
		var Lang = A.Lang;

		var STATUS_CODE = Liferay.STATUS_CODE;

		var STR_EMPTY = '';

		var STR_LAYOUT_ID = 'layoutId';

		var TPL_LINK = '<a href="{url}">{pageTitle}</a>';

		var TPL_LIST_ITEM = '<li class="add-page"></li>';

		var TPL_TAB_LINK = '<a href="{url}">' +
				'<span>{pageTitle}</span>' +
			'</a>';

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
							instance._updateURL = themeDisplay.getPathMain() + '/portal/edit_layout?p_auth=' + Liferay.authToken;

							var navListSelector = Liferay.Data.NAV_LIST_SELECTOR || '> ul';

							var navItemSelector = Liferay.Data.NAV_ITEM_SELECTOR || navListSelector + '> li';

							var navItemChildToggleSelector = Liferay.Data.NAV_ITEM_CHILD_TOGGLE_SELECTOR || '> span';

							var navList = navBlock.one(navListSelector);

							var items = navBlock.all(navItemSelector);

							var layoutIds = instance.get('layoutIds');

							var cssClassBuffer = [];

							items.each(
								function(item, index) {
									var layoutConfig = layoutIds[index];

									if (layoutConfig) {
										item.setData(STR_LAYOUT_ID, layoutConfig.id);

										if (layoutConfig.deletable) {
											cssClassBuffer.push('lfr-nav-deletable');
										}

										if (layoutConfig.sortable) {
											cssClassBuffer.push('lfr-nav-sortable');
										}

										if (cssClassBuffer.length) {
											item.addClass(cssClassBuffer.join(' '));

											cssClassBuffer.length = 0;
										}
									}
								}
							);

							instance._navItemChildToggleSelector = navItemChildToggleSelector;
							instance._navItemSelector = navItemSelector;
							instance._navListSelector = navListSelector;

							instance._navList = navList;

							instance._makeDeletable();
							instance._makeSortable();

							instance._tempTab = instance._createTempTab(TPL_TAB_LINK);
							instance._tempChildTab = instance._createTempTab(TPL_LINK);
						}
					},

					_afterMakeSortable: function(sortable) {
						var instance = this;

						sortable.delegate.dd.removeInvalid('a');
					},

					_cancelPage: function(event) {
						var instance = this;

						var actionNode = event.actionNode;
						var field = event.field;
						var listItem = event.listItem;
						var toolbar = event.toolbar;

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

					_clearTempPage: function() {
						var instance = this;

						instance._tempTab.remove();

						instance._tempChildTab.remove();
					},

					_createDeleteButton: function(obj) {
						var instance = this;

						obj.append(instance.TPL_DELETE_BUTTON);
					},

					_createTempTab: function(tpl) {
						var instance = this;

						var tempLink = Lang.sub(
							tpl,
							{
								pageTitle: STR_EMPTY,
								url: '#'
							}
						);

						var tempTab = ANode.create('<li>');

						tempTab.append(tempLink);

						return tempTab;
					},

					_deleteButton: function(obj) {
						var instance = this;

						if (!A.instanceOf(obj, A.NodeList)) {
							obj = A.all(obj);
						}

						obj.each(
							function(item, index) {
								if (item.hasClass('lfr-nav-deletable')) {
									instance._createDeleteButton(item);
								}
							}
						);
					},

					_displayNotice: function(message, type, timeout, useAnimation) {
						new Liferay.Notice(
							{
								closeText: false,
								content: message + '<button class="close" type="button">&times;</button>',
								noticeClass: 'hide',
								timeout: timeout || 10000,
								toggleText: false,
								type: type || 'warning',
								useAnimation: Lang.isValue(useAnimation) ? useAnimation : true
							}
						).show();
					},

					_handleKeyDown: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						if (event.isKey('DELETE') && !currentTarget.hasClass('selected') && !currentTarget.ancestor('li.selected')) {
							instance._removePage(event);
						}
					},

					_hoverNavItem: function(event) {
						var instance = this;

						event.currentTarget.toggleClass('lfr-nav-hover', event.type == 'mouseenter');
					},

					_makeDeletable: function() {
						var instance = this;

						if (instance.get('isModifiable')) {
							var navBlock = instance.get('navBlock');

							var navItemSelector = instance._navItemSelector;

							var navItems = navBlock.all(navItemSelector).filter(
								function(item, index) {
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

							navBlock.delegate(['mouseenter', 'mouseleave'], instance._hoverNavItem, '.lfr-nav-deletable', instance);

							instance._deleteButton(navItems);
						}
					},

					_onAddPage: function(event) {
						var instance = this;

						var data = event.data;

						instance._clearTempPage();

						var navBlock = instance.get('navBlock');

						navBlock.show();

						var tabTPL = data.parentLayoutId ? TPL_LINK : TPL_TAB_LINK;

						var tabHtml = Lang.sub(
							tabTPL,
							{
								pageTitle: data.title,
								url: data.url
							}
						);

						var newTab = ANode.create(tabHtml);

						var listItem = data.parentLayoutId ? ANode.create('<li>') : ANode.create(TPL_LIST_ITEM);

						listItem.setData(STR_LAYOUT_ID, data.layoutId);

						listItem.append(newTab);

						if (data.parentLayoutId) {
							var parentItem = navBlock.one('#layout_' + data.parentLayoutId);

							if (parentItem) {
								var parentListItem = parentItem.one('ul');

								if (parentListItem) {
									parentListItem.append(listItem);
								}
							}
						}
						else {
							listItem.addClass('lfr-nav-deletable lfr-nav-sortable sortable-item');

							instance._createDeleteButton(listItem);

							navBlock.one('ul').append(listItem);
						}

						Liferay.fire(
							'navigation',
							{
								item: listItem,
								type: 'add'
							}
						);
					},

					_onPreviewPageTitle: function(event) {
						var instance = this;

						var data = event.data;

						if (data.name === STR_EMPTY || data.hidden) {
							instance._clearTempPage();
						}
						else {
							var navBlock = instance.get('navBlock');

							if (!data.parentLayoutId) {
								instance._tempTab.one('span').text(data.name);

								navBlock.one('ul').append(instance._tempTab);
							}
							else {
								var parentItem = navBlock.one('#layout_' + data.parentLayoutId);

								if (parentItem) {
									var parentListItem = parentItem.one('ul');

									if (parentListItem) {
										instance._tempChildTab.one('a').text(data.name);

										parentListItem.append(instance._tempChildTab);
									}
								}
							}
						}
					},

					_optionsOpen: true,
					_updateURL: STR_EMPTY
				}
			}
		);

		Liferay.provide(
			Navigation,
			'_makeSortable',
			function() {
				var instance = this;

				if (instance.get('isSortable') && instance._navList) {
					var sortable = new A.Sortable(
						{
							container: instance._navList,
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
						}
					);

					sortable.delegate.on(
						'drag:start',
						function(event) {
							var target = event.target;

							var dragNode = target.get('dragNode');

							dragNode.addClass('lfr-navigation-proxy');

							instance._nextPageNode = target.get('node').next();
						}
					);

					instance._afterMakeSortable(sortable);
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
					var processRemovePageFailure = function(result) {
						instance._displayNotice(result.message);
					};

					var processRemovePageSuccess = function(result) {
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
					};

					var data = {
						cmd: 'delete',
						doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
						groupId: themeDisplay.getSiteGroupId(),
						layoutId: tab.getData(STR_LAYOUT_ID),
						layoutSetBranchId: instance.get('layoutSetBranchId'),
						p_auth: Liferay.authToken,
						privateLayout: themeDisplay.isPrivateLayout()
					};

					A.io.request(
						instance._updateURL,
						{
							data: data,
							dataType: 'JSON',
							on: {
								failure: function() {
									processRemovePageFailure(
										{
											message: Liferay.Language.get('your-request-failed-to-complete'),
											status: STATUS_CODE.BAD_REQUEST
										}
									);
								},
								success: function(event, id, obj) {
									var result = this.get('responseData');

									var removePageFn = processRemovePageFailure;

									if (result.status === STATUS_CODE.OK) {
										removePageFn = processRemovePageSuccess;
									}

									removePageFn(result);
								}
							}
						}
					);
				}
			},
			['aui-io-request', 'liferay-notice'],
			true
		);

		Liferay.provide(
			Navigation,
			'_saveSortables',
			function(node) {
				var instance = this;

				var nextLayoutId = -1;

				var nextNode = node.next();

				if (nextNode) {
					nextLayoutId = nextNode.getData(STR_LAYOUT_ID);
				}

				var previousLayoutId = -1;

				var previousNode = node.previous();

				if (previousNode) {
					previousLayoutId = previousNode.getData(STR_LAYOUT_ID);
				}

				var data = {
					cmd: 'priority',
					doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
					groupId: themeDisplay.getSiteGroupId(),
					layoutId: node.getData(STR_LAYOUT_ID),
					nextLayoutId: nextLayoutId,
					p_auth: Liferay.authToken,
					previousLayoutId: previousLayoutId,
					privateLayout: themeDisplay.isPrivateLayout()
				};

				var processMovePageFailure = function(result) {
					instance._displayNotice(result.message);

					node.ancestor().insertBefore(node, instance._nextPageNode);
				};

				var processMovePageSuccess = function(result) {
					Liferay.fire(
						'navigation',
						{
							item: node.getDOM(),
							type: 'sort'
						}
					);
				};

				A.io.request(
					instance._updateURL,
					{
						data: data,
						dataType: 'JSON',
						on: {
							failure: function() {
								processMovePageFailure(
									{
										message: Liferay.Language.get('your-request-failed-to-complete'),
										status: STATUS_CODE.BAD_REQUEST
									}
								);
							},
							success: function(event, id, obj) {
								var result = this.get('responseData');

								var movePageFn = processMovePageFailure;

								if (result.status === STATUS_CODE.OK) {
									movePageFn = processMovePageSuccess;
								}

								movePageFn(result);
							}
						}
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
		requires: ['aui-component', 'event-mouseenter']
	}
);