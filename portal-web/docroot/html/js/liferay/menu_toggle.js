AUI.add(
	'liferay-menu-toggle',
	function(A) {
		var Lang = A.Lang;
		var AArray = A.Array;
		var AEvent = A.Event;
		var Util = Liferay.Util;

		var CSS_HIDDEN = 'hidden';

		var NAME = 'menutoggle';

		var SELECTOR_NAV_ITEM_FILTER = '.nav-item-filter';

		var TPL_INPUT_FILTER = '<div class="btn-toolbar search-panel">' +
			'<div class="control-group">' +
				'<input class="field search-query span12 focus nav-item-filter" placeholder="{placeholder}" type="text">' +
			'</div>' +
		'</div>';

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

						var menu = instance._content.one('.dropdown-menu');

						if (!instance._menuFilter) {
							var menuItems = menu.all('li');

							var itemsSize = menuItems.size();

							if (itemsSize > instance.get('maxDisplayItems')) {
								menu.prepend(
									Lang.sub(
										TPL_INPUT_FILTER,
										{
											placeholder: instance.get('strings').placeholder
										}
									)
								);

								instance._createMenuFilter(menu, menuItems);

								instance._menuFilter.on('results', instance._filterMenu, instance, menuItems);
							}
						}

						setTimeout(
							function() {
								Util.focusFormField(menu.one(SELECTOR_NAV_ITEM_FILTER));
							},
							0
						);
					},

					_bindUI: function() {
						var instance = this;

						if (instance._triggerNode) {
							instance._triggerNode.on(
								['gesturemovestart', 'keyup'],
								function(event) {
									if ((event.type == 'gesturemovestart') || event.isKeyInSet('ENTER', 'SPACE')) {
										instance._toggleMenu(event, event.currentTarget);
									}
								}
							);
						}
					},

					_createMenuFilter: function(menu, menuItems) {
						var instance = this;

						instance._menuFilter = new MenuFilter(
							{
								inputNode: menu.one(SELECTOR_NAV_ITEM_FILTER),

								minQueryLength: 0,

								resultTextLocator: 'name',

								resultFilters: 'phraseMatch',

								source: function() {
									var results = [];

									menuItems.each(
										function(node) {
											results.push(
												{
													name: node.one('.nav-item-label').text(),
													node: node
												}
											);
										}
									);

									return results;
								}(),

								queryDelay: 0
							}
						);
					},

					_filterMenu: function(event, menuItems) {
						menuItems.addClass(CSS_HIDDEN);

						AArray.each(
							event.results,
							function(result) {
								result.raw.node.removeClass(CSS_HIDDEN);
							}
						);
					},

					_getEventOutside: function(event) {
						var eventOutside = event._event.type;

						if (eventOutside.toLowerCase().indexOf('pointerdown') !== -1) {
							eventOutside = 'mousedown';
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

		var MenuFilter = A.Component.create(
			{
				NAME: 'menufilter',

				EXTENDS: A.Base,

				AUGMENTS: A.AutoCompleteBase,

				prototype: {
					initializer: function () {
						var instance = this;

						instance._bindUIACBase();
						instance._syncUIACBase();
					}
				}
			}
		);

		Liferay.MenuToggle = MenuToggle;
	},
	'',
	{
		requires: ['aui-node', 'autocomplete-base', 'autocomplete-filters', 'event-move', 'event-outside', 'liferay-store']
	}
);