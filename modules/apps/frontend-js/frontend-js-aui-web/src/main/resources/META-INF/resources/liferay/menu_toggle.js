/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-menu-toggle',
	(A) => {
		const AEvent = A.Event;
		const Lang = A.Lang;

		const NAME = 'menutoggle';

		const MenuToggle = A.Component.create({
			ATTRS: {
				content: {
					validator: '_validateContent',
				},

				maxDisplayItems: {
					validator: Lang.isNumber,
					value: 10,
				},

				open: {
					validator: Lang.isBoolean,
					value: false,
				},

				strings: {
					validator: Lang.isObject,
					value: {
						placeholder: 'Search',
					},
				},

				toggle: {
					validator: Lang.isBoolean,
					value: false,
				},

				toggleTouch: {
					validator: Lang.isBoolean,
					value: false,
				},

				trigger: {
					setter: A.one,
				},
			},

			NAME,

			NS: NAME,

			prototype: {
				_addMenuFilter() {
					const instance = this;

					let menuFilter = instance._menuFilter;

					if (!menuFilter) {
						const menu = instance._content.one('.dropdown-menu');

						if (menu) {
							const menuItems = menu.all('li');

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
					}
					else {
						menuFilter.reset();
					}
				},

				_bindUI() {
					const instance = this;

					if (instance._triggerNode) {
						instance._triggerNode.on(['keyup', 'tap'], (event) => {
							if (
								event.type === 'tap' ||
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
					const instance = this;

					const results = [];

					menuItems.each((node) => {
						results.push({
							name: node.one('.nav-item-label').text().trim(),
							node,
						});
					});

					instance._menuFilter = new Liferay.MenuFilter({
						content: menu,
						minQueryLength: 0,
						queryDelay: 0,
						resultFilters: 'phraseMatch',
						resultTextLocator: 'name',
						source: results,
					});

					return instance._menuFilter;
				},

				_getEventOutside(event) {
					let eventOutside = event._event.type;

					eventOutside = eventOutside.toLowerCase();

					if (eventOutside.indexOf('pointerup') > -1) {
						eventOutside = 'mouseup';
					}
					else if (eventOutside.indexOf('touchend') > -1) {
						eventOutside = 'mouseover';
					}

					eventOutside += 'outside';

					return eventOutside;
				},

				_isContent(target) {
					const instance = this;

					return instance._content.some((item) => {
						return item.contains(target);
					});
				},

				_isTouchEvent(event) {
					const eventType = event._event.type;

					const touchEvent =
						eventType === 'touchend' || eventType === 'touchstart';

					return touchEvent && Liferay.Util.isTablet();
				},

				_toggleContent(force) {
					const instance = this;

					instance._content.toggleClass('open', force);

					instance.set('open', force);

					if (force) {
						instance._addMenuFilter();

						const inputFilterNode = instance._inputFilterNode;

						if (inputFilterNode) {
							setTimeout(() => {
								Liferay.Util.focusFormField(inputFilterNode);
							}, 0);
						}
					}
				},

				_toggleMenu(event, target) {
					const instance = this;

					const open = !instance.get('open');
					const toggle = instance.get('toggle');
					let toggleTouch = instance.get('toggleTouch');

					const handleId = instance._handleId;

					instance._toggleContent(open);

					if (!toggle) {
						let handle = Liferay.Data[handleId];

						if (open && !handle) {
							handle = target.on(
								instance._getEventOutside(event),
								(event) => {
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
						}
						else if (handle) {
							handle.detach();

							handle = null;
						}

						Liferay.Data[handleId] = handle;
					}
					else {
						const data = {};

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
					const instance = this;

					const trigger = instance.get('trigger');

					const triggerId = trigger.guid();

					instance._handleId = triggerId + 'Handle';

					instance._triggerNode = trigger;

					instance._content = A.all(instance.get('content'));

					AEvent.defineOutside('touchend');
					AEvent.defineOutside('touchstart');

					instance._bindUI();
				},
			},
		});

		Liferay.MenuToggle = MenuToggle;
	},
	'',
	{
		requires: [
			'aui-node',
			'event-outside',
			'event-tap',
			'liferay-menu-filter',
		],
	}
);
