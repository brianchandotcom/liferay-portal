AUI.add(
	'liferay-navigation-interaction-touch',
	function(A) {
		var ANDROID = A.UA.android;

		var ANDROID_LEGACY = (ANDROID && ANDROID < 4.4);

		var STR_OPEN = 'open';

		A.mix(
			Liferay.NavigationInteraction.prototype,
			{
				_handleShowNavigationMenu: function(menuNew, menuOld, event) {
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

						handle = menuNew.on(
							outsideEvents,
							function() {
								Liferay.fire(
									'hideNavigationMenu',
									{
										menu: menuNew
									}
								);

								Liferay.Data[handleId] = null;

								handle.detach();
							}
						);
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

				_initChildMenuHandlers: function(navigation) {
					var instance = this;

					if (navigation) {
						A.Event.defineOutside('touchstart');

						navigation.delegate('tap', instance._onTouchClick, '.lfr-nav-child-toggle', instance);

						if (ANDROID_LEGACY) {
							navigation.delegate(
								'click',
								function(event) {
									event.preventDefault();
								},
								'.lfr-nav-child-toggle'
							);
						}
					}
				},

				_initNodeFocusManager: A.Lang.emptyFn,

				_onTouchClick: function(event) {
					var instance = this;

					var menuNew = event.currentTarget.ancestor(instance._directChildLi);

					if (menuNew.one('.child-menu')) {
						event.preventDefault();

						instance._handleShowNavigationMenu(menuNew, instance.MAP_HOVER.menu, event);
					}
				}
			},
			true
		);
	},
	'',
	{
		requires: ['event-tap', 'event-touch', 'liferay-navigation-interaction']
	}
);