AUI.add(
	'liferay-toggler-key-filter',
	function(A) {
		var KeyMap = A.Event.KeyMap;

		var NAME = 'togglerkeyfilter';

		var TogglerKeyFilter = A.Component.create(
			{
				ATTRS: {
					filter: {
						validator: Array.isArray,
						value: [
							KeyMap.ESC,
							KeyMap.LEFT,
							KeyMap.NUM_MINUS,
							KeyMap.NUM_PLUS,
							KeyMap.RIGHT,
							KeyMap.SPACE
						]
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: NAME,

				NS: NAME,

				prototype: {
					initializer: function() {
						var instance = this;

						instance._toggleEvent = instance.get('host').get('toggleEvent');

						instance.beforeHostMethod('headerEventHandler', instance._headerEventHandler, instance);
					},

					_headerEventHandler: function(event) {
						var instance = this;

						var validAction = event.type === instance._toggleEvent;

						if (!validAction) {
							validAction = instance.get('filter').indexOf(event.keyCode) > -1;
						}

						if (!validAction) {
							return new A.Do.Prevent();
						}
					}
				}
			}
		);

		Liferay.TogglerKeyFilter = TogglerKeyFilter;
	},
	'',
	{
		requires: ['aui-event-base']
	}
);