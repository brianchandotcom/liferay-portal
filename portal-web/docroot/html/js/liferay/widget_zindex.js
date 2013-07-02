AUI.add(
	'liferay-widget-zindex',
	function(A) {
		var Lang = A.Lang;

		var STR_HOST = 'host';

		var WidgetZIndex = A.Component.create(
			{
				EXTENDS: A.Plugin.Base,

				NAME: 'widgetzindex',

				NS: 'zindex',

				prototype: {
					initializer: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						if (!host.get('rendered') && host.get('visible')) {
							instance._setHostZIndex();
						}

						instance.onHostEvent(
							'visibleChange',
							function(event) {
								if (event.newVal) {
									instance._setHostZIndex();
								}
							}
						);
					},

					_setHostZIndex: function() {
						var instance = this;

						instance.get(STR_HOST).set('zIndex', ++Liferay.zIndex.WINDOW);
					}
				}
			}
		);

		Liferay.WidgetZIndex = WidgetZIndex;
	},
	'',
	{
		requires: ['aui-modal', 'plugin']
	}
);