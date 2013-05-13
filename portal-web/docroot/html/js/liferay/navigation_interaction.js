AUI.add(
	'liferay-navigation-interaction',
	function(A) {
		var ACTIVE_DESCENDANT = 'activeDescendant';

		var DIRECTION_LEFT = 0;

		var DIRECTION_RIGHT = 1;

		var NAME = 'liferaynavigationinteraction';

		var NavigationInteraction = A.Component.create(
			{
				EXTENDS: A.Plugin.Base,

				NAME: NAME,

				NS: NAME,

				prototype: {
					MAP_HOVER: {},

					initializer: function(config) {
						var instance = this;

						var host = instance.get('host');

						var navigation = host.one('> ul');

						var hostULId = '#' + navigation.guid();

						instance._directChildLi = hostULId + '> li';
						instance._hostULId = hostULId;

						Liferay.on(
							['hideNavigationMenu', 'showNavigationMenu'],
							function(event) {
								var showMenu = (event.type == 'showNavigationMenu');

								var menu = event.menu;

								instance._lastShownMenu = null;

								if (showMenu) {
									instance._lastShownMenu = menu;
								}

								event.menu.toggleClass('hover', showMenu);
								event.menu.toggleClass('open', showMenu);
							}
						);

						instance._initChildMenuHandlers(navigation);

						instance._initNodeFocusManager();
					},

					_handleExit: function(event) {
						var instance = this;

						var focusManager = instance._focusManager;

						if (focusManager.get(ACTIVE_DESCENDANT)) {
							focusManager.set(ACTIVE_DESCENDANT, 0);

							focusManager.blur();
						}

						instance._hideMenu();
					},

					_handleKey: function(event, direction) {
						var instance = this;

						var item;

						var target = event.target;

						var parent = target.ancestors(instance._directChildLi).item(0);

						var fallbackFirst = true;

						if (direction == DIRECTION_LEFT) {
							item = parent.previous();

							fallbackFirst = false;
						}
						else {
							item = parent.next();
						}

						if (!item) {
							var siblings = parent.siblings();

							if (fallbackFirst) {
								item = siblings.first();
							}
							else {
								item = siblings.last();
							}
						}

						instance._focusManager.focus(item.one('a'));
					},

					_handleKeyDown: function(event) {
						var instance = this;

						var handler;

						if (event.isKey('LEFT')) {
							handler = '_handleLeft';
						}
						else if (event.isKey('RIGHT')) {
							handler = '_handleRight';
						}
						else if (event.isKey('TAB') || event.isKey('ESC')) {
							handler = '_handleExit';
						}

						if (handler) {
							instance[handler](event);
						}
					},

					_handleLeft: function(event) {
						var instance = this;

						instance._handleKey(event, DIRECTION_LEFT);
					},

					_handleRight: function(event) {
						var instance = this;

						instance._handleKey(event, DIRECTION_RIGHT);
					},

					_hideMenu: function() {
						var instance = this;

						var mapHover = instance.MAP_HOVER;

						if (mapHover.menu) {
							Liferay.fire('hideNavigationMenu', mapHover);

							instance.MAP_HOVER = {};
						}
					},

					_initChildMenuHandlers: function(navigation) {
						var instance = this;

						if (navigation) {
							navigation.delegate(['mouseenter', 'mouseleave'], instance._onMouseToggle, '> li', instance);

							navigation.delegate('keydown', instance._handleKeyDown, 'a', instance);
						}
					},

					_initNodeFocusManager: function() {
						var instance = this;

						var host = instance.get('host');

						host.plug(
							A.Plugin.NodeFocusManager,
							{
								descendants: 'a',
								focusClass: 'active',
								keys: {
									next: 'down:40',
									previous: 'down:38'
								}
							}
						);

						var focusManager = host.focusManager;

						focusManager.after(['activeDescendantChange', 'focusedChange'], instance._showMenu, instance);

						instance._focusManager = focusManager;
					},

					_onMouseToggle: function(event) {
						var instance = this;

						var mapHover = instance.MAP_HOVER;

						var eventType = 'hideNavigationMenu';

						if (event.type == 'mouseenter') {
							eventType = 'showNavigationMenu';
						}

						mapHover.menu = event.currentTarget;

						Liferay.fire(eventType, mapHover);
					},

					_showMenu: function(event) {
						var instance = this;

						event.halt();

						var mapHover = instance.MAP_HOVER;

						var menuOld = mapHover.menu;

						var newMenuIndex = event.newVal;

						var handleMenuToggle = (newMenuIndex || (newMenuIndex === 0));

						if (handleMenuToggle) {
							var focusManager = instance._focusManager;

							var activeDescendant = focusManager.get(ACTIVE_DESCENDANT);
							var descendants = focusManager.get('descendants');

							var menuLink = descendants.item(activeDescendant);

							var menuNew = menuLink.ancestor(instance._directChildLi);

							instance._handleShowNavigationMenu(menuNew, menuOld)
						}
						else if (menuOld) {
							Liferay.fire('hideNavigationMenu', mapHover);

							instance.MAP_HOVER = {};
						}
					},

					_handleShowNavigationMenu: function(menuNew, menuOld) {
						var instance = this;

						var mapHover = instance.MAP_HOVER;

						if (!(instance._lastShownMenu && (event.type.indexOf('focusedChange') !== -1))) {
							var updateMenu = (menuOld && (menuOld != menuNew));

							if (updateMenu) {
								Liferay.fire('hideNavigationMenu', mapHover);
							}

							if (!menuOld || updateMenu) {
								mapHover.menu = menuNew;

								Liferay.fire('showNavigationMenu', mapHover);
							}
						}
					}
				}
			}
		);

		Liferay.NavigationInteraction = NavigationInteraction;
	},
	'',
	{
		requires: ['node-focusmanager', 'plugin']
	}
);