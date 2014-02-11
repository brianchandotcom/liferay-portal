AUI.add(
	'liferay-menu-toggle',
	function(A) {
		var	NAME = 'menutoggle';

		var Lang = A.Lang;

		var MenuToggle = A.Component.create(
			{
				ATTRS: {
					toggle: {
						validator: Lang.isBoolean,
						value: false
					},

					toggleTouch: {
						validator: Lang.isBoolean,
						value: false
					}
				},

				NAME: NAME,

				NS: NAME,

				prototype: {
					initializer: function(config) {
						var instance = this;

						var triggerId = config.trigger;

						instance._handleId = triggerId + 'Handle';

						instance._triggerNode = A.one(triggerId);

						instance._content = A.all(config.content);

						A.Event.defineOutside('touchend');

						instance._bindUI();
					},

					_bindUI: function() {
						var instance = this;

						if (instance._triggerNode) {
							instance._triggerNode.on(
								['gesturemovestart', 'keypress'],
								function(event) {
									if ((event.type == "gesturemovestart") || event.isKeyInSet('ENTER', 'SPACE')) {
										instance._toggleMenu(event, event.currentTarget);
									}
								}
							);
						}
					},

					_getEventOutside: function(event) {
						var eventOutside = event._event.type;

						if (eventOutside == 'MSPointerUp') {
							eventOutside = 'mouseup';
						}

						eventOutside = eventOutside + 'outside';

						return eventOutside;
					},

					_isContent: function(target) {
						var instance = this;

						var isContent = false;

						A.some(
							instance._content,
							function(item, index, collection) {
								if (item.contains(target)) {
									isContent = true;

									return isContent;
								}
							}
						);

						return isContent;
					},

					_isTouch: function(event) {
						if ((event._event.type == 'touchend') && Liferay.Util.isTablet()) {
							return true;
						}
						else {
							return false;
						}
					},

					_toggleContent: function(force) {
						var instance = this;

						A.each(
							instance._content,
							function(item, index, collection) {
								if (item) {
									item.toggleClass('open', force);
								}
							}
						);
					},

					_toggleMenu: function(event, target) {
						var instance = this;

						var toggle = instance.get('toggle');
						var toggleTouch = instance.get('toggleTouch');

						instance._toggleContent();

						var menuOpen = instance._content.item(0).hasClass('open');

						if (!toggle) {
							var handle = Liferay.Data[instance._handleId];

							if (menuOpen && !handle) {
								handle = target.on(
									instance._getEventOutside(event),
									function(event) {
										if (toggleTouch) {
											toggleTouch = instance._isTouch(event);
										}

										if (!instance._isContent(event.target) && !toggleTouch) {
											Liferay.Data[instance._handleId] = null;

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

							Liferay.Data[instance._handleId] = handle;
						}
						else {
							var data = {};

							data[instance._handleId] = menuOpen ? 'open' : 'closed';

							Liferay.Store(data);
						}
					}
				}
			}
		);

		Liferay.MenuToggle = MenuToggle;
	},
	'',
	{
		requires: ['aui-node','event-move','event-outside','liferay-store']
	}
);