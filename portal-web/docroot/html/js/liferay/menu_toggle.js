AUI.add(
	'liferay-menu-toggle',
	function(A) {
		var Lang = A.Lang;

		var	NAME = 'menutoggle';

		var MenuToggle = A.Component.create(
			{
				ATTRS: {
					content: {
						validator: '_validateContent'
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
						validator: Lang.isString
					}
				},

				NAME: NAME,

				NS: NAME,

				prototype: {
					initializer: function(config) {
						var instance = this;

						var triggerId = instance.get('trigger');

						instance._handleId = triggerId + 'Handle';

						instance._triggerNode = A.one(triggerId);

						instance._content = A.all(instance.get('content'));

						A.Event.defineOutside('touchend');

						instance._bindUI();
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

					_getEventOutside: function(event) {
						var eventOutside = event._event.type;

						eventOutside = eventOutside.toLowerCase();

						if (eventOutside.indexOf('pointerup') > -1) {
							eventOutside = 'mouseup';
						}
						else if (eventOutside.indexOf('touchend') > -1) {
							eventOutside = 'mouseover';
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
						return (event._event.type === 'touchend' && Liferay.Util.isTablet());
					},

					_toggleContent: function(force) {
						var instance = this;

						instance._content.toggleClass('open', force);
					},

					_toggleMenu: function(event, target) {
						var instance = this;

						var toggle = instance.get('toggle');
						var toggleTouch = instance.get('toggleTouch');

						var handleId = instance._handleId;

						instance._toggleContent();

						var menuOpen = instance._content.item(0).hasClass('open');

						if (!toggle) {
							var handle = Liferay.Data[handleId];

							if (menuOpen && !handle) {
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

							data[handleId] = menuOpen ? 'open' : 'closed';

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
		requires: ['aui-node', 'event-outside', 'event-tap', 'liferay-store']
	}
);