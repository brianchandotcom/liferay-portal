AUI.add(
	'liferay-toggler-interaction',
	function(A) {
		var Lang = A.Lang;

		var STR_CHILDREN = 'children';

		var STR_CONTAINER = 'container';

		var STR_DESCENDANTS = 'descendants';

		var STR_HEADER = 'header';

		var STR_HOST = 'host';

		var STR_KEYS = 'keys';

		var STR_TOGGLER = 'toggler';

		var NAME = 'togglerinteraction';

		var TogglerInteraction = A.Component.create(
			{
				EXTENDS: Liferay.TogglerKeyFilter,

				NAME: NAME,

				NS: NAME,

				ATTRS: {
					children: {
						validator: Lang.isString
					},

					descendants: {
						getter: '_getDescendants'
					},

					keys: {
						validator: Lang.isObject,
						value: {
							collapse: 'down:37',
							next: 'down:40',
							previous: 'down:38'
						}
					},

					parents: {
						validator: Lang.isString
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

					_getDescendants: function() {
						var instance = this;

						var result = instance.get(STR_HOST).get(STR_HEADER);

						var children = instance.get(STR_CHILDREN);

						if (children) {
							result += ', ' + children + ':visible';
						}

						return result;
					},

					_headerEventHandler: function(event) {
						var instance = this;

						instance._focusManager.refresh();

						return TogglerInteraction.superclass._headerEventHandler.call(instance, event);
					}
				}
			}
		);

		Liferay.TogglerInteraction = TogglerInteraction;
	},
	'',
	{
		requires: ['key-event', 'liferay-toggler-key-filter', 'node-focusmanager']
	}
);