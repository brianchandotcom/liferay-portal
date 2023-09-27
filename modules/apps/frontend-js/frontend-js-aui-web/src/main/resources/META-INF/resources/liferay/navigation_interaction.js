/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-navigation-interaction',
	(A) => {
		const ACTIVE_DESCENDANT = 'activeDescendant';

		const DIRECTION_LEFT = 'left';

		const DIRECTION_RIGHT = 'right';

		const NAME = 'liferaynavigationinteraction';

		const NavigationInteraction = A.Component.create({
			EXTENDS: A.Plugin.Base,

			NAME,

			NS: NAME,

			prototype: {
				_handleExit() {
					const instance = this;

					const focusManager = instance._focusManager;

					if (focusManager.get(ACTIVE_DESCENDANT)) {
						focusManager.set(ACTIVE_DESCENDANT, 0);

						focusManager.blur();
					}

					instance._hideMenu();

					if (instance._isTriggerVisible()) {
						Liferay.fire('exitNavigation', {
							navigation: instance.get('host'),
						});
					}
				},

				_handleKey(event, direction) {
					const instance = this;

					if (!instance._isTriggerVisible()) {
						let item;

						const target = event.target;

						const parent = target
							.ancestors(instance._directChildLi)
							.item(0);

						let fallbackFirst = true;

						if (direction === DIRECTION_LEFT) {
							item = parent.previous();

							fallbackFirst = false;
						}
						else {
							item = parent.next();
						}

						if (!item) {
							const siblings = parent.siblings();

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
						Liferay.fire('exitNavigation', {
							direction,
							navigation: instance.get('host'),
						});
					}
				},

				_handleKeyDown(event) {
					const instance = this;

					let handler;

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

				_handleLeft(event) {
					const instance = this;

					instance._handleKey(event, DIRECTION_LEFT);
				},

				_handleRight(event) {
					const instance = this;

					instance._handleKey(event, DIRECTION_RIGHT);
				},

				_handleShowNavigationMenu(menuNew, menuOld, event) {
					const instance = this;

					if (
						!(
							instance._lastShownMenu &&
							event.type.indexOf('focusedChange') > -1
						)
					) {
						const mapHover = instance.MAP_HOVER;

						const menuOldDistinct = menuOld && menuOld !== menuNew;

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
							let exitDirection;

							const descendants = instance._focusManager.get(
								'descendants'
							);

							const first = descendants.first();

							const last = descendants.last();

							const oldMenuLink = menuOld.one('a');

							const newMenuLink = menuNew.one('a');

							if (oldMenuLink === last && newMenuLink === first) {
								exitDirection = 'down';
							}
							else if (
								oldMenuLink === first &&
								newMenuLink === last
							) {
								exitDirection = 'up';
							}

							if (exitDirection) {
								Liferay.fire('exitNavigation', {
									direction: exitDirection,
									navigation: instance.get('host'),
								});
							}
						}
					}
				},

				_hideMenu() {
					const instance = this;

					const mapHover = instance.MAP_HOVER;

					if (mapHover.menu) {
						Liferay.fire('hideNavigationMenu', mapHover);

						instance.MAP_HOVER = {};
					}
				},

				_initChildMenuHandlers(navigation) {
					const instance = this;

					if (navigation) {
						navigation.delegate(
							['mouseenter', 'mouseleave'],
							instance._onMouseToggle,
							'> li',
							instance
						);

						navigation.delegate(
							'keydown',
							instance._handleKeyDown,
							'a',
							instance
						);
					}
				},

				_initNodeFocusManager() {
					const instance = this;

					const host = instance.get('host');

					host.plug(A.Plugin.NodeFocusManager, {
						descendants: 'a',
						focusClass: 'active',
						keys: {
							next: 'down:40',
							previous: 'down:38',
						},
					});

					const focusManager = host.focusManager;

					focusManager.after(
						['activeDescendantChange', 'focusedChange'],
						instance._showMenu,
						instance
					);

					Liferay.once(
						'startNavigate',
						focusManager.destroy,
						focusManager
					);

					instance._focusManager = focusManager;
				},

				_isTriggerVisible() {
					const instance = this;

					return !!(
						instance._triggerNode &&
						instance._triggerNode.test(':visible')
					);
				},

				_onMouseToggle(event) {
					const instance = this;

					const mapHover = instance.MAP_HOVER;

					let eventType = 'hideNavigationMenu';

					if (event.type === 'mouseenter') {
						eventType = 'showNavigationMenu';
					}

					mapHover.menu = event.currentTarget;

					Liferay.fire(eventType, mapHover);
				},

				_showMenu(event) {
					const instance = this;

					event.halt();

					const mapHover = instance.MAP_HOVER;

					const menuOld = mapHover.menu;

					const newMenuIndex = event.newVal;

					const handleMenuToggle = newMenuIndex || newMenuIndex === 0;

					if (handleMenuToggle) {
						const focusManager = instance._focusManager;

						const activeDescendant = focusManager.get(
							ACTIVE_DESCENDANT
						);
						const descendants = focusManager.get('descendants');

						const menuLink = descendants.item(activeDescendant);

						const menuNew = menuLink.ancestor(
							instance._directChildLi
						);

						instance._handleShowNavigationMenu(
							menuNew,
							menuOld,
							event
						);
					}
					else if (menuOld) {
						Liferay.fire('hideNavigationMenu', mapHover);

						instance.MAP_HOVER = {};
					}
				},

				MAP_HOVER: {},

				initializer() {
					const instance = this;

					const host = instance.get('host');

					const navInteractionSelector =
						Liferay.Data.NAV_INTERACTION_LIST_SELECTOR || 'ul';

					const navigation = host.one(navInteractionSelector);

					const hostULId = '#' + navigation.guid();

					instance._directChildLi =
						Liferay.Data.NAV_INTERACTION_ITEM_SELECTOR ||
						hostULId + '> li';

					instance._hostULId = hostULId;

					instance._triggerNode = A.one('.nav-navigation-btn');

					Liferay.on(
						['hideNavigationMenu', 'showNavigationMenu'],
						(event) => {
							const menu = event.menu;

							if (menu) {
								instance._lastShownMenu = null;

								const showMenu =
									event.type === 'showNavigationMenu' &&
									menu.hasClass('dropdown');

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
			},
		});

		Liferay.NavigationInteraction = NavigationInteraction;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-component',
			'event-mouseenter',
			'node-focusmanager',
			'plugin',
		],
	}
);
