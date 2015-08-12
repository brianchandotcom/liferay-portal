AUI.add(
	'liferay-dockbar-keyboard-interaction',
	function(A) {
		var AObject = A.Object;

		var ACTIVE_DESCENDANT = 'activeDescendant';

		var CSS_DROPDOWN = 'dropdown';

		var CSS_OPEN = 'open';

		var CSS_SITE_NAVIGATION = 'site-navigation';

		var EVENT_KEY = 'key';

		var MENU_ITEM_CSS_CLASSES = [CSS_DROPDOWN, CSS_SITE_NAVIGATION];

		var NAME = 'liferaydockbarkeyboardinteraction';

		var SELECTOR_DOCKBAR_ITEM = '.dockbar-item:visible, a.nav-navigation-btn:visible';

		var SELECTOR_DOCKBAR_ITEM_FIRST_LINK = '.dockbar-item:visible > a[role=menuitem], a.nav-navigation-btn:visible';

		var SELECTOR_DOCKBAR_ITEM_LINK = '.dockbar-item:visible a[role=menuitem], a.nav-navigation-btn:visible';

		var STR_MENU_ITEM = 'menuItem';

		var DockbarKeyboardInteraction = A.Component.create(
			{
				EXTENDS: A.Plugin.Base,

				NAME: NAME,

				NS: NAME,

				prototype: {
					initializer: function() {
						var instance = this;

						instance._host = instance.get('host');

						instance._initHostFocusManager();
						instance._initMenuItems();
						instance._initMenuItemHandlers();
					},

					_handleDownKeyPress: function(event) {
						var instance = this;

						event.preventDefault();

						instance._menuItems.removeClass(CSS_OPEN);

						var target = event.currentTarget;

						var menuItem = target.ancestor(SELECTOR_DOCKBAR_ITEM) || target.getData(STR_MENU_ITEM);

						MENU_ITEM_CSS_CLASSES.some(
							function(item, index) {
								if (menuItem.hasClass(item)) {
									menuItem.addClass(CSS_OPEN);

									return true;
								}
							}
						);

						if (menuItem.hasClass('site-navigation')) {
							A.soon(
								function() {
									menuItem.one('li a').focus();
								}
							);
						}
					},

					_handleExitNavigation: function(event) {
						var instance = this;

						var focusItem;

						var focusManager = instance._host.focusManager;

						var direction = event.direction;

						if (direction === 'down' || direction === 'right') {
							var active = focusManager.get(ACTIVE_DESCENDANT);

							focusItem = focusManager.get('descendants').item(active + 1);
						}
						else if (direction === 'up' || direction === 'left') {
							focusItem = instance._menuItems.last();
						}

						if (focusItem) {
							focusManager.focus(focusItem);
						}

						event.navigation.removeClass(CSS_OPEN);
					},

					_handleLeftRightKeyPress: function(event) {
						var instance = this;

						instance._menuItems.removeClass(CSS_OPEN);

						var menuItems = instance._host.all(SELECTOR_DOCKBAR_ITEM);

						var lastItemIndex = menuItems.size() - 1;

						var increment = 1;

						if (event.isKey('LEFT')) {
							increment = -1;
						}

						var currentTarget = event.currentTarget;

						var currentMenuItem = currentTarget.ancestor(SELECTOR_DOCKBAR_ITEM) || currentTarget;

						var nextMenuItemPos = menuItems.indexOf(currentMenuItem) + increment;

						if (nextMenuItemPos < 0) {
							nextMenuItemPos = lastItemIndex;
						}
						else if (nextMenuItemPos > lastItemIndex) {
							nextMenuItemPos = 0;
						}

						var focusTarget = menuItems.item(nextMenuItemPos).one('a') || menuItems.item(nextMenuItemPos);

						instance.hostFocusManager.focus(focusTarget);
					},

					_handleTabKeyPress: function(event) {
						event.currentTarget.all(SELECTOR_DOCKBAR_ITEM).removeClass(CSS_OPEN);
					},

					_handleUpDownKeyPress: function(event) {
						var instance = this;

						var method = '_handleDownKeyPress';

						if (event.isKey('UP')) {
							method = '_handleUpKeyPress';
						}

						instance[method](event);
					},

					_handleUpKeyPress: function(event) {
						var instance = this;

						event.preventDefault();

						instance._menuItems.removeClass(CSS_OPEN);

						var hostFocusManager = instance.hostFocusManager;

						var focusedCurrent = hostFocusManager.get(ACTIVE_DESCENDANT) - 1;

						if (focusedCurrent < 0) {
							focusedCurrent = hostFocusManager._lastNodeIndex;
						}

						AObject.some(
							hostFocusManager._descendantsMap,
							function(item, index) {
								if (item === focusedCurrent) {
									var menuItemLink = A.one('#' + index);

									var menuItem = menuItemLink.ancestor(SELECTOR_DOCKBAR_ITEM) || menuItemLink.getData(STR_MENU_ITEM);

									MENU_ITEM_CSS_CLASSES.some(
										function(item, index) {
											if (menuItem.hasClass(item)) {
												menuItem.addClass(CSS_OPEN);

												return true;
											}
										}
									);

									if (menuItem.hasClass('site-navigation')) {
										A.soon(
											function() {
												var descendants = menuItem.focusManager.get('descendants');

												menuItem.focusManager.set('activeDescendant', descendants.last());

												menuItem.all('li a').last().focus();
											}
										);
									}

									return true;
								}
							}
						);
					},

					_initHostFocusManager: function() {
						var instance = this;

						var host = instance._host;

						host.plug(
							A.Plugin.NodeFocusManager,
							{
								descendants: SELECTOR_DOCKBAR_ITEM_LINK,
								keys: {
									next: 'down:40',
									previous: 'down:38'
								}
							}
						);

						host.focusManager.after(
							'focusedChange',
							function(event) {
								var instance = this;

								instance.refresh();

								if (!event.newVal) {
									instance.set(ACTIVE_DESCENDANT, 0);
								}
							}
						);

						instance.hostFocusManager = host.focusManager;
					},

					_initMenuItemHandlers: function() {
						var instance = this;

						var host = instance._host;

						host.delegate(EVENT_KEY, instance._handleUpDownKeyPress, 'down:38,40', SELECTOR_DOCKBAR_ITEM_FIRST_LINK, instance);
						host.delegate(EVENT_KEY, instance._handleLeftRightKeyPress, 'down:37,39', SELECTOR_DOCKBAR_ITEM_LINK, instance);

						host.delegate(EVENT_KEY, instance._handleTabKeyPress, 'down:9');

						Liferay.after('exitNavigation', instance._handleExitNavigation, instance);
					},

					_initMenuItems: function() {
						var instance = this;

						var menuItems = [];

						instance._host.all(SELECTOR_DOCKBAR_ITEM).each(
							function(item, index) {
								item = item.getData('menuItem') || item;

								menuItems.push(item);
							}
						);

						instance._menuItems = new A.NodeList(menuItems);
					}
				}
			}
		);

		Liferay.DockbarKeyboardInteraction = DockbarKeyboardInteraction;
	},
	'',
	{
		requires: ['node-focusmanager', 'plugin']
	}
);