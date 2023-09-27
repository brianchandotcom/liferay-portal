/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-navigation-interaction-touch',
	(A) => {
		const ANDROID = A.UA.android;

		const ANDROID_LEGACY = ANDROID && ANDROID < 4.4;

		const STR_OPEN = 'open';

		A.mix(
			Liferay.NavigationInteraction.prototype,
			{
				_handleShowNavigationMenu(menuNew) {
					const instance = this;

					const mapHover = instance.MAP_HOVER;

					mapHover.menu = menuNew;

					const menuOpen = menuNew.hasClass(STR_OPEN);

					const handleId = menuNew.attr('id') + 'Handle';

					let handle = Liferay.Data[handleId];

					if (!menuOpen) {
						Liferay.fire('showNavigationMenu', mapHover);

						let outsideEvents = ['clickoutside', 'touchendoutside'];

						if (ANDROID_LEGACY) {
							outsideEvents = outsideEvents[0];
						}

						handle = menuNew.on(outsideEvents, () => {
							Liferay.fire('hideNavigationMenu', {
								menu: menuNew,
							});

							Liferay.Data[handleId] = null;

							handle.detach();
						});
					}
					else {
						Liferay.fire('hideNavigationMenu', mapHover);

						if (handle) {
							handle.detach();

							handle = null;
						}
					}

					Liferay.Data[handleId] = handle;
				},

				_initChildMenuHandlers(navigation) {
					const instance = this;

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
								(event) => {
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
					const instance = this;

					const menuNew = event.currentTarget.ancestor(
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
				},
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
			'liferay-navigation-interaction',
		],
	}
);
