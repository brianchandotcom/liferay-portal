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
	'liferay-navigation-interaction-touch',
	A => {
		var ANDROID = A.UA.android;

		var ANDROID_LEGACY = ANDROID && ANDROID < 4.4;

		var STR_OPEN = 'open';

		A.mix(
			Liferay.NavigationInteraction.prototype,
			{
				_handleShowNavigationMenu(menuNew) {
					var instance = this;

					var mapHover = instance.MAP_HOVER;

					mapHover.menu = menuNew;

					var menuOpen = menuNew.hasClass(STR_OPEN);

					var handleId = menuNew.attr('id') + 'Handle';

					var handle = Liferay.Data[handleId];

					if (!menuOpen) {
						Liferay.fire('showNavigationMenu', mapHover);

						var outsideEvents = ['clickoutside', 'touchendoutside'];

						if (ANDROID_LEGACY) {
							outsideEvents = outsideEvents[0];
						}

						handle = menuNew.on(outsideEvents, () => {
							Liferay.fire('hideNavigationMenu', {
								menu: menuNew
							});

							Liferay.Data[handleId] = null;

							handle.detach();
						});
					} else {
						Liferay.fire('hideNavigationMenu', mapHover);

						if (handle) {
							handle.detach();

							handle = null;
						}
					}

					Liferay.Data[handleId] = handle;
				},

				_initChildMenuHandlers(navigation) {
					var instance = this;

					if (navigation) {
						A.Event.defineOutside('touchend');

						navigation.delegate(
							'tap',
							instance._onTouchClick,
							'.lfr-nav-child-toggle',
							instance
						);

						if (ANDROID_LEGACY) {
							navigation.delegate(
								'click',
								event => {
									event.preventDefault();
								},
								'.lfr-nav-child-toggle'
							);
						}

						if (!A.UA.mobile) {
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
					}
				},

				_initNodeFocusManager: A.Lang.emptyFn,

				_onTouchClick(event) {
					var instance = this;

					var menuNew = event.currentTarget.ancestor(
						instance._directChildLi
					);

					if (menuNew.one('.child-menu')) {
						event.preventDefault();

						instance._handleShowNavigationMenu(
							menuNew,
							instance.MAP_HOVER.menu,
							event
						);
					}
				}
			},
			true
		);
	},
	'',
	{
		requires: [
			'event-outside',
			'event-tap',
			'event-touch',
			'liferay-navigation-interaction'
		]
	}
);
