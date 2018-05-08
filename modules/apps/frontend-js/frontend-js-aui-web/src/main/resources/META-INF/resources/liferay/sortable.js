AUI.add(
	'liferay-sortable',
	function(A) {
		var Sortable = A.Sortable;

		var STR_CONT = 'container';

		var STR_ID = 'id';

		var STR_NODES = 'nodes';

		A.mix(
			Sortable.prototype,
			{
				initializer: function() {
					var instance = this;

					var id = 'sortable-' + A.guid();

					var delegateConfig = {
						container: instance.get(STR_CONT),
						dragConfig: {
							groups: [id]
						},
						invalid: instance.get('invalid'),
						nodes: instance.get(STR_NODES),
						target: true
					};

					if (instance.get('handles')) {
						delegateConfig.handles = instance.get('handles');
					}

					var delegate = new A.DD.Delegate(delegateConfig);

					instance.set(STR_ID, id);

					delegate.dd.plug(
						A.Plugin.DDProxy,
						{
							cloneNode: true,
							moveOnEnd: false
						}
					);

					instance.drop = new A.DD.Drop(
						{
							bubbleTarget: delegate,
							groups: delegate.dd.get('groups'),
							node: instance.get(STR_CONT)
						}
					);

					instance.drop.on('drop:enter', A.bind('_onDropEnter', instance));

					instance._setDragMethod();

					delegate.on(
						{
							'drag:drag': A.bind('_dragMethod', instance),
							'drag:end': A.bind('_onDragEnd', instance),
							'drag:over': A.bind('_onDragOver', instance),
							'drag:start': A.bind('_onDragStart', instance)
						}
					);

					instance.delegate = delegate;

					Sortable.reg(instance, id);
				},

				_onDragHorizontal: function(event) {
					var instance = this;

					var pageX = event.pageX;

					var x = instance._x;

					instance._up = pageX < x;

					instance._x = pageX;
				},

				_setDragMethod: function() {
					var instance = this;

					var dragMethod;

					var node = instance.get(STR_CONT).one(instance.get(STR_NODES));

					var floated = node ? node.getStyle('float') : 'none';

					if (floated === 'left' || floated === 'right') {
						dragMethod = '_onDragHorizontal';
					}
					else {
						dragMethod = '_onDrag';
					}

					instance._dragMethod = A.bind(dragMethod, instance);
				},

				_x: null
			},
			true
		);
	},
	'',
	{
		requires: ['liferay-dd-proxy', 'sortable']
	}
);