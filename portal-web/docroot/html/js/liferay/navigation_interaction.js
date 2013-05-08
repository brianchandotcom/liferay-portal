AUI.add(
	'liferay-navigation-interaction',
	function(A) {
		var ACTIVE_DESCENDANT = 'activeDescendant';

		var DIRECTION_LEFT = 0;

		var DIRECTION_RIGHT = 1;

		var MAP_HOVER = {};

		var NAME = 'liferaynavigationinteraction';

		var hideMenu = function() {
			Liferay.fire('hideNavigationMenu', MAP_HOVER);
		};

		var NavigationInteraction = A.Component.create(
			{
				EXTENDS: A.Plugin.Base,

				NAME: NAME,

				NS: NAME,

				prototype: {
					initializer: function(config) {
						var instance = this;

						var host = instance.get('host');
						var navigation = host.one('> ul');

						var hostULId = '#' + navigation.guid();

						instance._directLiChild = hostULId + '> li';

						instance._hostULId = hostULId;

						Liferay.on(
							['showNavigationMenu', 'hideNavigationMenu'],
							function(event) {
								var showMenu = event.type == 'showNavigationMenu';

								instance._lastShownMenu = null;

								if (showMenu) {
									instance._lastShownMenu = event.menu;
								}

								event.menu.toggleClass('hover', showMenu);
								event.menu.toggleClass('open', showMenu);
							}
						);

						if (navigation) {
							navigation.delegate('mouseenter', instance._onMouseToggle, '> li', instance);
							navigation.delegate('mouseleave', instance._onMouseToggle, '> li', instance);

							navigation.delegate('keydown', instance._handleKeyDown, 'a', instance);
						}

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

						focusManager.after('activeDescendantChange', instance._showMenu, instance);
						focusManager.after('focusedChange', instance._showMenu, instance);

						instance._focusManager = focusManager;
					},

					_handleExit: function(event) {
						var instance = this;

						var focusManager = instance._focusManager;

						if (focusManager.get(ACTIVE_DESCENDANT)) {
							focusManager.set(ACTIVE_DESCENDANT, 0);

							focusManager.blur();

							hideMenu();
						}
						else {
							setTimeout(hideMenu, 0);
						}
					},

					_handleKey: function(event, direction) {
						var instance = this;

						var target = event.target;

						var parent = target.ancestors(instance._directLiChild).item(0);

						var fallbackFirst = true;
						var item;

						if (direction === DIRECTION_LEFT) {
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

					_onMouseToggle: function(event) {
						var instance = this;

						var showMenu = event.type == 'mouseenter';
						var eventType = 'hideNavigationMenu';

						if (showMenu) {
							eventType = 'showNavigationMenu';
						}

						MAP_HOVER.menu = event.currentTarget;

						Liferay.fire(eventType, MAP_HOVER);
					},

					_showMenu: function(event) {
						var instance = this;

						event.halt();

						var focusManager = instance._focusManager;

						var activeDescendant = focusManager.get(ACTIVE_DESCENDANT);
						var descendants = focusManager.get('descendants');

						if (!(instance._lastShownMenu && (event.type.indexOf('focusedChange') !== -1))) {
							if (MAP_HOVER.menu) {
								Liferay.fire('hideNavigationMenu', MAP_HOVER);
							}

							MAP_HOVER.menu = descendants.item(activeDescendant).ancestors(instance._directLiChild);

							Liferay.fire('showNavigationMenu', MAP_HOVER);
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