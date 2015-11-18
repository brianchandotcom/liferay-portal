AUI.add(
	'liferay-dockbar-keyboard-interaction',
	function(A) {
		var AObject = A.Object;

		var ACTIVE_DESCENDANT = 'activeDescendant';

		var CSS_DROPDOWN = 'dropdown';

		var CSS_OPEN = 'open';

		var EVENT_KEY = 'key';

		var NAME = 'liferaydockbarkeyboardinteraction';

		var SELECTOR_DOCKBAR_ITEM = '.dockbar-item';

		var SELECTOR_DOCKBAR_ITEM_FIRST_LINK = '.dockbar-item > a[role=menuitem]';

		var SELECTOR_DOCKBAR_ITEM_LINK = '.dockbar-item a[role=menuitem]';

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
						instance._initMenuItemHandlers();
					},

					_handleDownKeyPress: function(event) {
						var instance = this;

						event.preventDefault();

						instance._host.all(SELECTOR_DOCKBAR_ITEM).removeClass(CSS_OPEN);

						var menuItem = event.currentTarget.ancestor(SELECTOR_DOCKBAR_ITEM);

						if (menuItem.hasClass(CSS_DROPDOWN)) {
							menuItem.addClass(CSS_OPEN);
						}
					},

					_handleLeftRightKeyPress: function(event) {
						var instance = this;

						var menuItems = instance._host.all(SELECTOR_DOCKBAR_ITEM);

						menuItems.removeClass(CSS_OPEN);

						var lastItemIndex = menuItems.size() - 1;

						var increment = 1;

						if (event.isKey('LEFT')) {
							increment = -1;
						}

						var currentMenuItem = event.currentTarget.ancestor(SELECTOR_DOCKBAR_ITEM);

						var nextMenuItemPos = menuItems.indexOf(currentMenuItem) + increment;

						if (nextMenuItemPos < 0) {
							nextMenuItemPos = lastItemIndex;
						}
						else if (nextMenuItemPos > lastItemIndex) {
							nextMenuItemPos = 0;
						}

						var focusTarget = menuItems.item(nextMenuItemPos).one('a');

						instance.hostFocusManager.focus(focusTarget);
					},

					_handleTabKeyPress: function(event) {
						event.currentTarget.all(SELECTOR_DOCKBAR_ITEM).removeClass(CSS_OPEN);
					},

					_handleUpKeyPress: function(event) {
						var instance = this;

						event.preventDefault();

						instance._host.all(SELECTOR_DOCKBAR_ITEM).removeClass(CSS_OPEN);

						var hostFocusManager = instance.hostFocusManager;

						var focusedCurrent = hostFocusManager.get(ACTIVE_DESCENDANT) - 1;

						if (focusedCurrent < 0) {
							focusedCurrent = hostFocusManager._lastNodeIndex;
						}

						AObject.some(
							hostFocusManager._descendantsMap,
							function(item, index, collection) {
								if (item === focusedCurrent) {
									var menuItem = A.one('#' + index).ancestor(SELECTOR_DOCKBAR_ITEM);

									if (menuItem.hasClass(CSS_DROPDOWN)) {
										menuItem.addClass(CSS_OPEN);
									}

									return true;
								}
							}
						);
					},

					_handleUpDownKeyPress: function(event) {
						var instance = this;

						var method = '_handleDownKeyPress';

						if (event.isKey('UP')) {
							method = '_handleUpKeyPress';
						}

						instance[method](event);
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