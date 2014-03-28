AUI.add(
	'liferay-navigation-interaction-touch',
	function(A) {
		var STR_OPEN = 'open';

		A.mix(
			Liferay.NavigationInteraction.prototype,
			{
				_handleShowNavigationMenu: function(menuNew, menuOld, event) {
					var instance = this;

					var mapHover = instance.MAP_HOVER;

					mapHover.menu = menuNew;

					var menuOpen = menuNew.hasClass(STR_OPEN);

					if (menuOpen) {
						Liferay.fire('hideNavigationMenu', mapHover);
					}
					else {
						Liferay.fire('showNavigationMenu', mapHover);

						if ((menuOld && menuOld.hasClass(STR_OPEN)) && (menuOld != menuNew)) {
							menuOld.removeClass(STR_OPEN);
							menuOld.removeClass('hover');
						}
					}
				},

				_initChildMenuHandlers: function(navigation) {
					var instance = this;

					if (navigation) {
						navigation.delegate(['click', 'touchstart'], instance._onTouchClick, '> li > a', instance);
					}
				},

				_initNodeFocusManager: A.Lang.emptyFn,

				_onTouchClick: function(event) {
					var instance = this;

					var target = event.target;

					var menuNew = event.currentTarget.ancestor(instance._directChildLi);

					if (menuNew.one('.child-menu') && target.ancestor('.lfr-nav-child-toggle', true, '.lfr-nav-item')) {
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
		requires: ['event-touch', 'liferay-navigation-interaction']
	}
);