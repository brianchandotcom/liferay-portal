AUI.add(
	'liferay-hudcrumbs',
	function(A) {
		var Lang = A.Lang,

			getClassName = A.ClassNameManager.getClassName,

			NAME = 'hudcrumbs';

		var Hudcrumbs = A.Component.create(
			{
				ATTRS: {
					clone: {
						value: null
					},
					hostMidpoint: {
						value: 0
					},
					top: {
						value: 0
					},
					width: {
						value: 0
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: NAME,

				NS: NAME,

				prototype: {
					initializer: function() {
						var instance = this;

						var breadcrumbs = instance.get('host');
						var hudcrumbs = breadcrumbs.clone();
						var region = breadcrumbs.get('region');

						hudcrumbs.resetId();

						var win = A.getWin();
						var body = A.getBody();

						instance._win = win;
						instance._body = body;
						instance._dockbar = Liferay.Dockbar && Liferay.Dockbar.dockBar;

						hudcrumbs.hide();

						hudcrumbs.addClass('lfr-hudcrumbs');

						instance.set('clone', hudcrumbs);

						instance._calculateDimensions();

						win.on('scroll', instance._onScroll, instance);
						win.on('resize', instance._calculateDimensions, instance);

						body.append(hudcrumbs);

						Liferay.on('dockbar:pinned', instance._calculateDimensions, instance);
					},

					_calculateDimensions: function(event) {
						var instance = this;

						var region = instance.get('host').get('region');
						var midpoint = region.top + (region.height / 2);
						var top = 0;

						var dockbar = instance._dockbar;
						var body = instance._body;

						if (dockbar) {
							var dockbarHeight = dockbar.get('offsetHeight');

							top = dockbarHeight;
							midpoint -= dockbarHeight;
						}

						instance.get('clone').setStyles(
							{
								left: region.left + 'px',
								top: top + 'px',
								width: region.width + 'px'
							}
						);

						instance.set('hostMidpoint', midpoint);
					},

					_onScroll: function(event) {
						var instance = this;

						var scrollTop = event.currentTarget.get('scrollTop');
						var hudcrumbs = instance.get('clone');

						var action = 'hide';

						if (scrollTop >= instance.get('hostMidpoint')) {
							action = 'show';
						}

						if (instance.lastAction != action) {
							hudcrumbs[action]();
						}

						instance.lastAction = action;
					}
				}
			}
		);

		A.Hudcrumbs = Hudcrumbs;
	},
	'',
	{
		requires: ['aui-base', 'aui-component', 'plugin']
	}
);