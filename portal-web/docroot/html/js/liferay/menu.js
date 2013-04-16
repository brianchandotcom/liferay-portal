AUI.add(
	'liferay-menu',
	function(A) {
		var Lang = A.Lang;

		var trim = Lang.trim;

		var ATTR_CLASS_NAME = 'className';

		var AUTO = 'auto';

		var ARIA_ATTR_ROLE = 'role';

		var CSS_STATE_ACTIVE = 'aui-state-active';

		var CSS_EXTENDED = 'lfr-extended';

		var DEFAULT_ALIGN_POINTS = ['tl', 'bl'];

		var EVENT_CLICK = 'click';

		var STR_B = 'b';

		var STR_L = 'l';

		var STR_R = 'r';

		var STR_T = 't';

		var MAP_ALIGN_HORIZONTAL_OVERLAY = {
			right: STR_L,
			left: STR_R
		};

		var MAP_ALIGN_HORIZONTAL_TRIGGER = {
			right: STR_R,
			left: STR_L
		};

		var MAP_ALIGN_VERTICAL_OVERLAY = {
			down: STR_T,
			up: STR_B
		};

		var MAP_ALIGN_VERTICAL_TRIGGER = {
			down: STR_B,
			up: STR_T
		};

		var MAP_LIVE_SEARCH = {};

		var REGEX_DIRECTION = /\bdirection-(down|left|right|up)\b/;

		var REGEX_MAX_DISPLAY_ITEMS = /max-display-items-(\d+)/;

		var SELECTOR_ANCHOR = 'a';

		var SELECTOR_TEXT = 'a, span';

		var SELECTOR_LIST_ITEM = 'li';

		var SELECTOR_SEARCH_CONTAINER = '.lfr-menu-list-search-container';

		var SELECTOR_SEARCH_INPUT = '.lfr-menu-list-search';

		var STR_BLANK = '';

		var TPL_MENU = '<div class="lfr-component lfr-menu-list" />';

		var TPL_SEARCH_BOX = '<div class="lfr-menu-list-search-container">' +
				'<input autocomplete="off" aria-autocomplete="list" aria-expanded="true" aria-labelledby="{searchLabeledBy}" aria-owns="{searchOwns}" class="lfr-menu-list-search" id="{searchId}" role="combobox">' +
			'</div>';

		var Menu = function() {
			var instance = this;

			instance._handles = [];

			if (!Menu._INSTANCE) {
				Menu._INSTANCE = instance;
			}
		};

		Menu.prototype = {
			_closeActiveMenu: function() {
				var instance = this;

				var menu = instance._activeMenu;

				if (menu) {
					var handles = instance._handles;

					A.Array.invoke(handles, 'detach');

					handles.length = 0;

					instance._overlay.hide();

					var trigger = instance._activeTrigger;

					instance._activeMenu = null;
					instance._activeTrigger = null;

					if (trigger.hasClass(CSS_EXTENDED)) {
						trigger.removeClass(CSS_STATE_ACTIVE);
					}
				}
			},

			_getAlignPoints: A.cached(
				function(cssClass) {
					var instance = this;

					var alignPoints = DEFAULT_ALIGN_POINTS;

					if (cssClass.indexOf(AUTO) == -1) {
						var directionMatch = cssClass.match(REGEX_DIRECTION);

						var direction = (directionMatch && directionMatch[1]) || AUTO;

						var overlayHorizontal = MAP_ALIGN_HORIZONTAL_OVERLAY[direction] || STR_L;
						var overlayVertical = MAP_ALIGN_VERTICAL_OVERLAY[direction] || STR_T;

						var triggerHorizontal = MAP_ALIGN_HORIZONTAL_TRIGGER[direction] || STR_L;
						var triggerVertical = MAP_ALIGN_VERTICAL_TRIGGER[direction] || STR_T;

						alignPoints = [overlayVertical + overlayHorizontal, triggerVertical + triggerHorizontal];
					}

					return alignPoints;
				}
			),

			_getMenu: function(trigger) {
				var instance = this;

				var overlay = instance._overlay;

				if (!overlay) {
					overlay = new A.OverlayBase(
						{
							align: {
								node: trigger,
								points: DEFAULT_ALIGN_POINTS
							},
							constrain: true,
							cssClass: 'lfr-menu-list',
							hideClass: false,
							preventOverlap: true,
							zIndex: Liferay.zIndex.MENU
						}
					).render();

					var boundingBox = overlay.get('boundingBox');

					boundingBox.addClass('lfr-component');

					instance._overlay = overlay;
				}
				else {
					overlay.set('align.node', trigger);
				}

				var listContainer = trigger.getData('menuListContainer');
				var menu = trigger.getData('menu');
				var menuHeight = trigger.getData('menuHeight');

				var liveSearch = menu && MAP_LIVE_SEARCH[menu.guid()];

				if (liveSearch) {
					liveSearch.search(STR_BLANK);
				}

				var listItems;

				if (!menu || !listContainer) {
					listContainer = trigger.one('ul');

					listItems = listContainer.all(SELECTOR_LIST_ITEM);

					menu = A.Node.create(TPL_MENU);

					listContainer.placeBefore(menu);

					listItems.last().addClass('last');

					menu.append(listContainer);

					trigger.setData('menuListContainer', listContainer);
					trigger.setData('menu', menu);

					instance._setARIARoles(trigger, menu, listContainer);

					Liferay.Util.createFlyouts(
						{
							container: listContainer.getDOM()
						}
					);
				}

				overlay.setStdModContent(A.WidgetStdMod.BODY, menu);

				if (!menuHeight) {
					menuHeight = instance._getMenuHeight(trigger, menu, listItems || listContainer.all(SELECTOR_LIST_ITEM));

					trigger.setData('menuHeight', menuHeight);

					listContainer.addClass('lfr-menu-list-overflow');

					if (menuHeight != AUTO) {
						listContainer.setStyle('maxHeight', menuHeight);
					}
				}

				instance._getFocusManager();

				return menu;
			},

			_getMenuHeight: function(trigger, menu, listItems) {
				var instance = this;

				var cssClass = trigger.attr(ATTR_CLASS_NAME);

				var height = AUTO;

				if (cssClass.indexOf('lfr-menu-expanded') == -1) {
					var params = REGEX_MAX_DISPLAY_ITEMS.exec(cssClass);

					var maxDisplayItems = params && parseInt(params[1], 10);

					if (maxDisplayItems && listItems.size() > maxDisplayItems) {
						instance._getLiveSearch(trigger, trigger.getData('menu'));

						height = 0;

						var heights = listItems.slice(0, maxDisplayItems).get('offsetHeight');

						for (var i = heights.length - 1; i >= 0; i--) {
							height += heights[i];
						}
					}
				}

				return height;
			},

			_positionActiveMenu: function() {
				var instance = this;

				var menu = instance._activeMenu;
				var trigger = instance._activeTrigger;

				if (menu) {
					var cssClass = trigger.attr(ATTR_CLASS_NAME);

					var overlay = instance._overlay;

					var align = overlay.get('align');

					align.points = instance._getAlignPoints(cssClass);

					overlay.show();

					overlay.set('align', align);

					if (Liferay.Browser.isIe() && Liferay.Browser.getMajorVersion() <= 7) {
						var searchContainer = menu.one(SELECTOR_SEARCH_CONTAINER);

						if (searchContainer) {
							searchContainer.width(menu.innerWidth());

							menu.one(SELECTOR_SEARCH_INPUT).width('100%');
						}
					}

					if (cssClass.indexOf(CSS_EXTENDED) > -1) {
						trigger.addClass(CSS_STATE_ACTIVE);
					}

					var focusManager = overlay.bodyNode.focusManager;

					if (focusManager) {
						focusManager.focus(0);
					}
				}
			},

			_setARIARoles: function(trigger, menu, listContainer) {
				var links = menu.all(SELECTOR_ANCHOR);

				var searchContainer = menu.one(SELECTOR_SEARCH_CONTAINER);

				var listNode = menu.one('ul');

				var ariaListNodeAttr = 'menu';
				var ariaLinksAttr = 'menuitem';

				if (searchContainer) {
					ariaListNodeAttr = 'listbox';
					ariaListNodeAttr = 'option';
				}

				listNode.setAttribute(ARIA_ATTR_ROLE, ariaListNodeAttr);
				links.set(ARIA_ATTR_ROLE, ariaLinksAttr);

				var anchor = trigger.one(SELECTOR_ANCHOR);

				if (anchor) {
					anchor.attr(
						{
							'aria-haspopup': true,
							role: 'button'
						}
					);

					listNode.setAttribute('aria-labelledby', anchor.guid());
				}
			}
		};

		Menu.handleFocus = function(id) {
			var node = A.one(id);

			if (node) {
				node.delegate('mouseenter', A.rbind(Menu._targetLink, node, 'focus'), SELECTOR_LIST_ITEM);
				node.delegate('mouseleave', A.rbind(Menu._targetLink, node, 'blur'), SELECTOR_LIST_ITEM);
			}
		};

		var buffer = [];

		Menu.register = function(id) {
			var menuNode = document.getElementById(id);

			if (!Menu._INSTANCE) {
				new Menu();
			}

			buffer.push(menuNode);

			Menu._registerTask();
		};

		Menu._registerTask = A.debounce(
			function() {
				var instance = Menu._INSTANCE;

				if (buffer.length) {
					var nodes = A.all(buffer);

					buffer.length = 0;
				}
			},
			100
		);

		Menu._targetLink = function(event, action) {
			var anchor = event.currentTarget.one(SELECTOR_ANCHOR);

			if (anchor) {
				anchor[action]();
			}
		};

		Liferay.provide(
			Menu,
			'_getFocusManager',
			function() {
				var instance = Menu._INSTANCE;

				var focusManager = instance._focusManager;

				if (!focusManager) {
					var bodyNode = instance._overlay.bodyNode;

					bodyNode.plug(
						A.Plugin.NodeFocusManager,
						{
							circular: true,
							descendants: 'li:not(.aui-helper-hidden) a,input',
							focusClass: 'aui-focus',
							keys: {
								next: 'down:40',
								previous: 'down:38'
							}
						}
					);

					bodyNode.on(
						'key',
						function(event) {
							var activeTrigger = instance._activeTrigger;

							if (activeTrigger) {
								var anchor = activeTrigger.one(SELECTOR_ANCHOR);

								instance._closeActiveMenu();

								anchor.focus();
							}
						},
						'down:27,9'
					);

					focusManager = bodyNode.focusManager;

					bodyNode.delegate(
						'mouseenter',
						function(event) {
							if (focusManager.get('focused')) {
								focusManager.focus(event.currentTarget.one(SELECTOR_ANCHOR));
							}
						},
						SELECTOR_LIST_ITEM
					);

					focusManager.after(
						'activeDescendantChange',
						function(event) {
							var descendants = focusManager.get('descendants');

							var selectedItem = descendants.item(event.newVal);

							bodyNode.one('ul').setAttribute('aria-activedescendant', selectedItem.guid());
						}
					);

					instance._focusManager = focusManager;
				}

				focusManager.refresh();
			},
			['node-focusmanager'],
			true
		);

		Liferay.provide(
			Menu,
			'_getLiveSearch',
			function(trigger, menu) {
				var instance = Menu._INSTANCE;

				var id = menu.guid();

				var liveSearch = MAP_LIVE_SEARCH[id];

				if (!liveSearch) {
					var searchId = A.guid();

					var listNode = menu.one('ul');

					var searchLabelNode = trigger.one(SELECTOR_ANCHOR) || trigger;

					var searchBoxContent = Lang.sub(
						TPL_SEARCH_BOX,
						{
							searchId: searchId,
							searchLabeledBy: searchLabelNode.guid(),
							searchOwns: listNode.guid()
						}
					);

					var inputSearch = A.Node.create(searchBoxContent);

					inputSearch.swallowEvent('click');

					menu.prepend(inputSearch);

					var options = {
						data: function(node) {
							return trim(node.one(SELECTOR_TEXT).text());
						},
						input: '#' + searchId,
						nodes: '#' + listNode.guid() + ' > li'
					};

					liveSearch = new A.LiveSearch(options);

					var bodyNode = instance._overlay.bodyNode;

					liveSearch.after(
						'search',
						function(event) {
							var focusManager = bodyNode.focusManager;

							if (focusManager) {
								focusManager.refresh();
							}
						}
					);

					MAP_LIVE_SEARCH[id] = liveSearch;
				}
			},
			['aui-live-search'],
			true
		);

		Liferay.provide(
			Menu,
			'_registerMenu',
			function(event) {
				var instance = Menu._INSTANCE;

				var handles = instance._handles;

				var trigger = event.currentTarget;

				var activeTrigger = instance._activeTrigger;

				if (activeTrigger && (activeTrigger != trigger)) {
					activeTrigger.removeClass(CSS_STATE_ACTIVE);
				}

				if (!trigger.hasClass('disabled')) {
					var menu = instance._getMenu(trigger);

					instance._activeMenu = menu;
					instance._activeTrigger = trigger;

					if (!handles.length) {
						handles.push(
							A.getWin().on('resize', A.debounce(instance._positionActiveMenu, 200, instance)),
							A.getDoc().on(EVENT_CLICK, instance._closeActiveMenu, instance)
						);

						var DDM = A.DD && A.DD.DDM;

						if (DDM) {
							handles.push(DDM.on('ddm:start', instance._closeActiveMenu, instance));
						}
					}

					instance._positionActiveMenu();

					event.halt();
				}
			},
			['aui-overlay']
		);

		Liferay.Menu = Menu;
	},
	'',
	{
		requires: ['aui-debounce', 'aui-node']
	}
);