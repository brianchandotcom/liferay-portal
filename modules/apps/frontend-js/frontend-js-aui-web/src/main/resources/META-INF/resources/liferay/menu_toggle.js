/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-menu-toggle',
	A => {
		var AEvent = A.Event;
		var Lang = A.Lang;

		var NAME = 'menutoggle';

		var MenuToggle = A.Component.create({
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

			NAME,

			NS: NAME,

			prototype: {
				_addMenuFilter() {
					var instance = this;

					var menuFilter = instance._menuFilter;

					if (!menuFilter) {
						var menu = instance._content.one('.dropdown-menu');

						if (menu) {
							var menuItems = menu.all('li');

							if (
								menuItems.size() >
								instance.get('maxDisplayItems')
							) {
								menuFilter = instance._createMenuFilter(
									menu,
									menuItems
								);

								instance._inputFilterNode = menuFilter.get(
									'inputNode'
								);
							}
						}
					} else {
						menuFilter.reset();
					}
				},

				_bindUI() {
					var instance = this;

					if (instance._triggerNode) {
						instance._triggerNode.on(['keyup', 'tap'], event => {
							if (
								event.type == 'tap' ||
								event.isKeyInSet('ENTER', 'SPACE')
							) {
								instance._toggleMenu(
									event,
									event.currentTarget
								);
							}
						});
					}
				},

				_createMenuFilter(menu, menuItems) {
					var instance = this;

					var results = [];

					menuItems.each(node => {
						results.push({
							name: node
								.one('.nav-item-label')
								.text()
								.trim(),
							node
						});
					});

					instance._menuFilter = new Liferay.MenuFilter({
						content: menu,
						minQueryLength: 0,
						queryDelay: 0,
						resultFilters: 'phraseMatch',
						resultTextLocator: 'name',
						source: results
					});

					return instance._menuFilter;
				},

				_getEventOutside(event) {
					var eventOutside = event._event.type;

					eventOutside = eventOutside.toLowerCase();

					if (eventOutside.indexOf('pointerup') > -1) {
						eventOutside = 'mouseup';
					} else if (eventOutside.indexOf('touchend') > -1) {
						eventOutside = 'mouseover';
					}

					eventOutside += 'outside';

					return eventOutside;
				},

				_isContent(target) {
					var instance = this;

					return instance._content.some(item => {
						return item.contains(target);
					});
				},

				_isTouchEvent(event) {
					var eventType = event._event.type;

					var touchEvent =
						eventType === 'touchend' || eventType === 'touchstart';

					return touchEvent && Liferay.Util.isTablet();
				},

				_toggleContent(force) {
					var instance = this;

					instance._content.toggleClass('open', force);

					instance.set('open', force);

					if (force) {
						instance._addMenuFilter();

						var inputFilterNode = instance._inputFilterNode;

						if (inputFilterNode) {
							setTimeout(() => {
								Liferay.Util.focusFormField(inputFilterNode);
							}, 0);
						}
					}
				},

				_toggleMenu(event, target) {
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
								event => {
									if (toggleTouch) {
										toggleTouch = instance._isTouchEvent(
											event
										);
									}

									if (
										!toggleTouch &&
										!instance._isContent(event.target)
									) {
										Liferay.Data[handleId] = null;

										handle.detach();

										instance._toggleContent(false);
									}
								}
							);
						} else if (handle) {
							handle.detach();

							handle = null;
						}

						Liferay.Data[handleId] = handle;
					} else {
						var data = {};

						data[handleId] = open ? 'open' : 'closed';

						Object.entries(data).forEach((key, value) => {
							Liferay.Util.Session.set(key, value);
						});
					}
				},

				_validateContent(value) {
					return (
						Lang.isString(value) ||
						Array.isArray(value) ||
						A.instanceOf(value, A.Node)
					);
				},

				initializer() {
					var instance = this;

					var trigger = instance.get('trigger');

					var triggerId = trigger.guid();

					instance._handleId = triggerId + 'Handle';

					instance._triggerNode = trigger;

					instance._content = A.all(instance.get('content'));

					AEvent.defineOutside('touchend');
					AEvent.defineOutside('touchstart');

					instance._bindUI();
				}
			}
		});

		Liferay.MenuToggle = MenuToggle;
	},
	'',
	{
		requires: [
			'aui-node',
			'event-outside',
			'event-tap',
			'liferay-menu-filter'
		]
	}
);
