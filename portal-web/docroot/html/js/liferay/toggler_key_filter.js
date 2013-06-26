AUI.add(
	'liferay-toggler-key-filter',
	function(A) {
		var AArray = A.Array;

		var KeyMap = A.Event.KeyMap;

		var NAME = 'togglerkeyfilter';

		var TogglerKeyFilter = A.Component.create(
			{
				EXTENDS: A.Plugin.Base,

				NAME: NAME,

				NS: NAME,

				ATTRS: {
					filter: {
						validator: A.isArray,
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

				prototype: {
					initializer: function(){
						var instance = this;

						instance.beforeHostMethod('headerEventHandler', instance._headerEventHandler, instance);
					},

					_headerEventHandler: function(event) {
						var instance = this;

						var validAction = (event.type === 'click');

						if (!validAction) {
							var keyCodeIndex = AArray.indexOf(instance.get('filter'), event.keyCode);

							validAction = keyCodeIndex > -1;
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