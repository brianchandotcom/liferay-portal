AUI.add(
	'liferay-widget-size-animation-plugin',
	function(A) {
		var Lang = A.Lang;

		var NAME = 'sizeanim';

		var STR_END = 'end';

		var STR_HOST = 'host';

		var STR_SIZE = 'size';

		var STR_START = 'start';

		var SizeAnim = A.Component.create(
			{
				EXTENDS: A.Plugin.Base,

				NAME: NAME,

				NS: NAME,

				ATTRS: {
					align: {
						validator: Lang.isBoolean
					},
					duration: {
						validator: Lang.isNumber,
						value: 0.3
					},
					easing: {
						validator: Lang.isString,
						value: 'easeBoth'
					},
					preventTransition: {
						validator: Lang.isBoolean
					}
				},

				prototype: {
					initializer: function(config) {
						var instance = this;

						var host = instance.get(STR_HOST);

						host.addAttr(
							STR_SIZE,
							{
								setter: A.bind('_animWidgetSize', instance)
							}
						);

						instance._anim = new A.Anim(
							{
								duration: instance.get('duration'),
								easing: instance.get('easing'),
								node: host
							}
						);

						var eventHandles = [
							instance._anim.after(STR_END, A.bind('fire', instance, STR_END)),
							instance._anim.after(STR_START, A.bind('fire', instance, STR_START)),
							instance._anim.after('tween', instance._alignWidget, instance)
						];

						instance._eventHandles = eventHandles;
					},

					destructor: function() {
						var instance = this;

						instance.get(STR_HOST).removeAttr(STR_SIZE);

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_alignWidget: function() {
						var instance = this;

						if (instance.get('align')) {
							instance.get(STR_HOST).align();
						}
					},

					_animWidgetSize: function(size) {
						var instance = this;

						var host = instance.get(STR_HOST);

						instance._anim.stop();

						var attrs = {
							height: size.height,
							width: size.width
						};

						if (!instance.get('preventTransition')) {
							instance._anim.set('to', attrs);

							instance._anim.run();
						}
						else {
							instance.fire(STR_START);

							host.setAttrs(attrs);

							instance._alignWidget();

							instance.fire(STR_END);
						}
					}
				}
			}
		);

		A.Plugin.SizeAnim = SizeAnim;
	},
	'',
	{
		requires: ['anim-easing', 'plugin', 'widget']
	}
);