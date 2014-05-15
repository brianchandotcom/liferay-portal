AUI.add(
	'liferay-menu-toggle',
	function(A) {
		var Lang = A.Lang;
		var AArray = A.Array;
		var AEvent = A.Event;
		var Util = Liferay.Util;

		var trim = Lang.trim;

		var NAME = 'menutoggle';

		var SELECTOR_NAV_ITEM_FILTER = '.nav-item-filter';

		var MenuToggle = A.Component.create(
			{
				ATTRS: {
					content: {
						validator: '_validateContent'
					},

					maxDisplayItems: {
						validator: Lang.isNumber,
						value: 10
					},

					open: {
						validator: Lang.isBoolean,
						value: false
					},

					strings: {
						validator: Lang.isObject,
						value: {
							placeholder: 'Search'
						}
					},

					toggle: {
						validator: Lang.isBoolean,
						value: false
					},

					toggleTouch: {
						validator: Lang.isBoolean,
						value: false
					},

					trigger: {
						setter: A.one
					}
				},

				NAME: NAME,

				NS: NAME,

				prototype: {
					initializer: function(config) {
						var instance = this;

						var trigger = instance.get('trigger');

						var triggerId = trigger.guid();

						instance._handleId = triggerId + 'Handle';

						instance._triggerNode = trigger;

						instance._content = A.all(instance.get('content'));

						AEvent.defineOutside('touchend');
						AEvent.defineOutside('touchstart');

						instance._bindUI();
					},

					_addMenuFilter: function() {
						var instance = this;

						var menuFilter = instance._menuFilter;

						if (!menuFilter) {
							var menu = instance._content.one('.dropdown-menu');

							if (menu) {
								var menuItems = menu.all('li');

								if (menuItems.size() > instance.get('maxDisplayItems')) {
									menuFilter = instance._createMenuFilter(menu, menuItems);

									instance._inputFilterNode = menuFilter.get('inputNode');
								}
							}
						}
						else {
							menuFilter.reset();
						}
					},

					_bindUI: function() {
						var instance = this;

						if (instance._triggerNode) {
							instance._triggerNode.on(
								['keyup', 'tap'],
								function(event) {
									if ((event.type == 'tap') || event.isKeyInSet('ENTER', 'SPACE')) {
										instance._toggleMenu(event, event.currentTarget);
									}
								}
							);
						}
					},

					_createMenuFilter: function(menu, menuItems) {
						var instance = this;

						var results = [];

						menuItems.each(
							function(node) {
								results.push(
									{
										name: trim(node.one('.nav-item-label').text()),
										node: node
									}
								);
							}
						);

						instance._menuFilter = new Liferay.MenuFilter(
							{
								content: menu,
								minQueryLength: 0,
								queryDelay: 0,
								resultFilters: 'phraseMatch',
								resultTextLocator: 'name',
								source: results
							}
						);

						return instance._menuFilter;
					},

					_getEventOutside: function(event) {
						var eventOutside = event._event.type;

						if (eventOutside.toLowerCase().indexOf('pointerup') !== -1) {
							eventOutside = 'mouseup';
						}

						eventOutside = eventOutside + 'outside';

						return eventOutside;
					},

					_isContent: function(target) {
						var instance = this;

						return instance._content.some(
							function(item, index, collection) {
								return item.contains(target);
							}
						);
					},

					_isTouchEvent: function(event) {
						var eventType = event._event.type;

						var touchEvent = ((eventType === 'touchend') || (eventType === 'touchstart'));

						return (touchEvent && Liferay.Util.isTablet());
					},

					_toggleContent: function(force) {
						var instance = this;

						instance._content.toggleClass('open', force);

						instance.set('open', force);

						if (force) {
							instance._addMenuFilter();

							var inputFilterNode = instance._inputFilterNode;

							if (inputFilterNode) {
								setTimeout(
									function() {
										Liferay.Util.focusFormField(inputFilterNode);
									},
									0
								);
							}
						}
					},

					_toggleMenu: function(event, target) {
						var instance = this;

						var open = !instance.get('open');
						var toggle = instance.get('toggle');
						var toggleTouch = instance.get('toggleTouch');

						var handleId = instance._handleId;

						instance._toggleContent(open);

						if (!toggle) {
							var handle = Liferay.Data[handleId];

							if (open && !handle) {
								handle = target.on(
									instance._getEventOutside(event),
									function(event) {
										if (toggleTouch) {
											toggleTouch = instance._isTouchEvent(event);
										}

										if (!toggleTouch && !instance._isContent(event.target)) {
											Liferay.Data[handleId] = null;

											handle.detach();

											instance._toggleContent(false);
										}
									}
								);
							}
							else if (handle) {
								handle.detach();

								handle = null;
							}

							Liferay.Data[handleId] = handle;
						}
						else {
							var data = {};

							data[handleId] = open ? 'open' : 'closed';

							Liferay.Store(data);
						}
					},

					_validateContent: function(value) {
						var instance = this;

						return Lang.isString(value) || Lang.isArray(value) || A.instanceOf(value, A.Node);
					}
				}
			}
		);

		Liferay.MenuToggle = MenuToggle;
	},
	'',
	{
		requires: ['aui-node', 'event-outside', 'event-tap', 'liferay-menu-filter', 'liferay-store']
	}
);