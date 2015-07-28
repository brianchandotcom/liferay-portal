AUI.add(
	'liferay-navigation-interaction',
	function(A) {
		var ACTIVE_DESCENDANT = 'activeDescendant';

		var DIRECTION_LEFT = 'left';

		var DIRECTION_RIGHT = 'right';

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

						var navInteractionSelector = Liferay.Data.NAV_INTERACTION_LIST_SELECTOR || 'ul';

						var navigation = host.one(navInteractionSelector);

						var hostULId = '#' + navigation.guid();

						instance._directChildLi = Liferay.Data.NAV_INTERACTION_ITEM_SELECTOR || hostULId + '> li';

						instance._hostULId = hostULId;

						instance._triggerNode = A.one('.nav-navigation-btn');

						Liferay.on(
							['hideNavigationMenu', 'showNavigationMenu'],
							function(event) {
								var showMenu = (event.type == 'showNavigationMenu');

								var menu = event.menu;

								if (menu) {
									instance._lastShownMenu = null;

									if (showMenu) {
										instance._lastShownMenu = menu;
									}

									menu.toggleClass('hover', showMenu);
									menu.toggleClass('open', showMenu);
								}
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

						if (instance._isTriggerVisible()) {
							Liferay.fire(
								'exitNavigation',
								{
									navigation: instance.get('host')
								}
							);
						}
					},

					_handleKey: function(event, direction) {
						var instance = this;

						if (!instance._isTriggerVisible()) {
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
						}
						else {
							Liferay.fire(
								'exitNavigation',
								{
									direction: direction,
									navigation: instance.get('host')
								}
							);
						}
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

					_handleShowNavigationMenu: function(menuNew, menuOld, event) {
						var instance = this;

						if (!(instance._lastShownMenu &&
							event.type.indexOf('focusedChange') > -1)) {

							var mapHover = instance.MAP_HOVER;

							var menuOldDistinct = (menuOld && (menuOld != menuNew));

							if (menuOldDistinct) {
								Liferay.fire('hideNavigationMenu', mapHover);
							}

							if (!menuOld || menuOldDistinct) {
								mapHover.menu = menuNew;

								Liferay.fire('showNavigationMenu', mapHover);
							}
						}

						if (instance._isTriggerVisible()) {
							if (menuOld) {
								var exitDirection;

								var descendants = instance._focusManager.get('descendants');

								var first = descendants.first();

								var last = descendants.last();

								var oldMenuLink = menuOld.one('a');

								var newMenuLink = menuNew.one('a');

								if ((oldMenuLink === last) && (newMenuLink === first)) {
									exitDirection = 'down';
								}
								else if ((oldMenuLink === first) && (newMenuLink === last)) {
									exitDirection = 'up';
								}

								if (exitDirection) {
									Liferay.fire(
										'exitNavigation',
										{
											direction: exitDirection,
											navigation: instance.get('host')
										}
									);
								}
							}
						}
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

					_isTriggerVisible: function() {
						var instance = this;

						return !!(instance._triggerNode && instance._triggerNode.test(':visible'));
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

							instance._handleShowNavigationMenu(menuNew, menuOld, event);
						}
						else if (menuOld) {
							Liferay.fire('hideNavigationMenu', mapHover);

							instance.MAP_HOVER = {};
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