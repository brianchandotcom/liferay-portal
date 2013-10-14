AUI.add(
	'liferay-dockbar-keyboard-interaction',
	function(A) {
		var AObject = A.Object;

		var ACTIVE_DESCENDANT = 'activeDescendant';

		var CSS_DROPDOWN = 'dropdown';

		var CSS_OPEN = 'open';

		var EVENT_KEY = 'key';

		var KEY_PRESS_DOWN_ARROW = 'down:40';

		var KEY_PRESS_LEFT_ARROW = 'down:37';

		var KEY_PRESS_RIGHT_ARROW = 'down:39';

		var KEY_PRESS_TAB = 'down:9';

		var KEY_PRESS_UP_ARROW = 'down:38';

		var NAME = 'liferaydockbarkeyboardinteraction';

		var SELECTOR_A = 'a';

		var SELECTOR_DOCKBAR_ITEM = '.dockbar-item';

		var SELECTOR_DOCKBAR_ITEM_FIRST_LINK = '.dockbar-item > a';

		var SELECTOR_DOCKBAR_ITEM_LINK = '.dockbar-item a';

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

						instance._initDockbarMenuItemHandlers();
					},

					_handleDownKeyPress: function(event) {
						var instance = this;

						event.preventDefault();

						var currentTarget = event.currentTarget;

						var host = instance._host;

						var liDockbarMenuItem = currentTarget.ancestor(SELECTOR_DOCKBAR_ITEM);

						host.all(SELECTOR_DOCKBAR_ITEM).removeClass(CSS_OPEN);

						if (liDockbarMenuItem.hasClass(CSS_DROPDOWN)) {
							liDockbarMenuItem.addClass(CSS_OPEN);
						}
					},

					_handleLeftKeyPress: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var currentDockbarMenuItem = currentTarget.ancestor(SELECTOR_DOCKBAR_ITEM);

						var host = instance._host;

						var hostFocusManager = instance.hostFocusManager;

						var dockbarMenuItems = host.all(SELECTOR_DOCKBAR_ITEM);

						var dockbarMenuItemsPosition = dockbarMenuItems.indexOf(currentDockbarMenuItem);

						host.all(SELECTOR_DOCKBAR_ITEM).removeClass(CSS_OPEN);

						if (dockbarMenuItemsPosition <= 0) {
							var lastItemIndex = dockbarMenuItems.size() - 1;

							hostFocusManager.focus(dockbarMenuItems.item(lastItemIndex).one(SELECTOR_A));
						}
						else {
							hostFocusManager.focus(dockbarMenuItems.item(dockbarMenuItemsPosition - 1).one(SELECTOR_A));
						}
					},

					_handleRightKeyPress: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var currentDockbarMenuItem = currentTarget.ancestor(SELECTOR_DOCKBAR_ITEM);

						var host = instance._host;

						var hostFocusManager = instance.hostFocusManager;

						var dockbarMenuItems = host.all(SELECTOR_DOCKBAR_ITEM);

						var dockbarMenuItemsPosition = dockbarMenuItems.indexOf(currentDockbarMenuItem);

						var lastItemIndex = dockbarMenuItems.size() - 1;

						host.all(SELECTOR_DOCKBAR_ITEM).removeClass(CSS_OPEN);

						if (dockbarMenuItemsPosition >= lastItemIndex) {
							hostFocusManager.focus(dockbarMenuItems.item(0).one(SELECTOR_A));
						}
						else {
							hostFocusManager.focus(dockbarMenuItems.item(dockbarMenuItemsPosition + 1).one(SELECTOR_A));
						}
					},

					_handleTabKeyPress: function(event) {
						var currentTarget = event.currentTarget;

						currentTarget.all(SELECTOR_DOCKBAR_ITEM).removeClass(CSS_OPEN);
					},

					_handleUpKeyPress: function(event) {
						var instance = this;

						event.preventDefault();

						var host = instance._host;

						var hostFocusManager = instance.hostFocusManager;

						var descendantsMap = hostFocusManager._descendantsMap;

						var focusedCurrent = hostFocusManager.get(ACTIVE_DESCENDANT) - 1;

						host.all(SELECTOR_DOCKBAR_ITEM).removeClass(CSS_OPEN);

						if (focusedCurrent < 0) {
							focusedCurrent = hostFocusManager._lastNodeIndex;
						}

						AObject.some(
							descendantsMap,
							function(item, index, collection) {
								var descendant = index;

								if (descendantsMap[descendant] === focusedCurrent) {
									var liDockbarMenuItem = A.one('#' + descendant).ancestor(SELECTOR_DOCKBAR_ITEM);

									if (liDockbarMenuItem.hasClass(CSS_DROPDOWN)) {
										A.one('#' + descendant).ancestor(SELECTOR_DOCKBAR_ITEM).addClass(CSS_OPEN);
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
									next: KEY_PRESS_DOWN_ARROW,
									previous: KEY_PRESS_UP_ARROW
								}
							}
						);

						host.focusManager.after(
							'focusedChange',
							function (event) {
								var instance = this;

								if (!event.newVal) {
									instance.set(ACTIVE_DESCENDANT, 0);
								}
							}
						);

						instance.hostFocusManager = host.focusManager;
					},

					_initDockbarMenuItemHandlers: function() {
						var instance = this;

						var host = instance._host;

						host.delegate(EVENT_KEY, instance._handleDownKeyPress, KEY_PRESS_DOWN_ARROW, SELECTOR_DOCKBAR_ITEM_FIRST_LINK, instance);

						host.delegate(EVENT_KEY, instance._handleLeftKeyPress, KEY_PRESS_LEFT_ARROW, SELECTOR_DOCKBAR_ITEM_LINK, instance);

						host.delegate(EVENT_KEY, instance._handleRightKeyPress, KEY_PRESS_RIGHT_ARROW, SELECTOR_DOCKBAR_ITEM_LINK, instance);

						host.delegate(EVENT_KEY, instance._handleTabKeyPress, KEY_PRESS_TAB);

						host.delegate(EVENT_KEY, instance._handleUpKeyPress, KEY_PRESS_UP_ARROW, SELECTOR_DOCKBAR_ITEM_FIRST_LINK, instance);
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