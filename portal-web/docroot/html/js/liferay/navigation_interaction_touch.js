AUI.add(
	'liferay-navigation-interaction-touch',
	function(A) {
		A.mix(
			Liferay.NavigationInteraction.prototype,
			{
				_initChildMenuHandlers: function(navigation) {
					var instance = this;

					if (navigation) {
						navigation.delegate('click', instance._onTouchClick, '> li > a', instance);
					}
				},

				_initNodeFocusManager: A.Lang.emptyFn,

				_onTouchClick: function(event) {
					var instance = this;

					var menuNew = event.currentTarget.ancestor(instance._directChildLi);

					if (menuNew.one('.child-menu') && !menuNew.hasClass('hover')) {
						event.preventDefault();
					}

					instance._handleShowNavigationMenu(menuNew, instance.MAP_HOVER.menu);
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