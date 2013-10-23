AUI.add(
	'liferay-toggler-key-access',
	function(A) {
		var STR_CHILDREN = 'children';

		var STR_CONTAINER = 'container';

		var STR_DESCENDANTS = 'descendants';

		var STR_HEADER = 'header';

		var STR_HOST = 'host';

		var STR_KEYS = 'keys';

		var STR_TOGGLER = 'toggler';

		var NAME = 'togglerKeyAccess';

		var TogglerKeyAccess = A.Component.create(
			{
				EXTENDS: Liferay.TogglerKeyFilter,

				NAME: NAME,

				NS: NAME,

				ATTRS: {
					parents: {
						validator: A.Lang.isString
					},

					children: {
						validator: A.Lang.isString
					},

					descendants: {
						getter: function() {
							var instance = this;

							var children = instance.get(STR_CHILDREN);

							return instance.get(STR_HOST).get(STR_HEADER) + (children ? (', ' + children + ':visible') : '');
						}
					},

					keys: {
						validator: A.Lang.isObject,
						value: {
							collapse: 'down:37',
							next: 'down:40',
							previous: 'down:38'
						}
					}
				},

				prototype: {
					initializer: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						var container = host.get(STR_CONTAINER);

						container.plug(
							A.Plugin.NodeFocusManager,
							{
								descendants: instance.get(STR_DESCENDANTS),
								keys: instance.get(STR_KEYS)
							}
						);

						container.delegate('key', instance._childrenEventHandler, instance.get(STR_KEYS).collapse, instance.get(STR_CHILDREN), instance);

						instance._focusManager = container.focusManager;
					},

					_childrenEventHandler: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var target = event.currentTarget;

						var header = target.ancestor(instance.get('parents')).one(host.get(STR_HEADER));

						var toggler = header.getData(STR_TOGGLER);

						if (!toggler) {
							host.createAll();

							toggler = header.getData(STR_TOGGLER);
						}

						toggler.collapse();

						header.focus();

						instance._focusManager.set('activeDescendant', header);
					},

					_headerEventHandler: function(event) {
						var instance = this;

						instance._focusManager.refresh();

						return TogglerKeyAccess.superclass._headerEventHandler.call(instance, event);
					}
				}
			}
		);

		Liferay.TogglerKeyAccess = TogglerKeyAccess;
	},
	'',
	{
		requires: ['key-event', 'liferay-toggler-key-filter', 'node-focusmanager']
	}
);