AUI.add(
    'liferay-widget-size-animation-plugin',
    function (A) {
        var Lang = A.Lang;
    
        var STR_HOST = 'host';

        var NAME = 'sizeanim';

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
                            'size',
                            {
                                setter: A.bind(instance._animWidgetSize, instance)
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
							instance._anim.after('tween', instance._alignWidget, instance),
							instance._anim.after('end', A.bind(instance.fire, instance, 'end')),
							instance._anim.after('start', A.bind(instance.fire, instance, 'start'))
						];

						instance._eventHandles = eventHandles;
					},

					destructor: function() {
						var instance = this;

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

						if (!instance.get('preventTransition')) {
	                        instance._anim.set(
	                            'to',
	                            { 
	                                width: size.width,
	                                height: size.height
	                            }
	                        );

	                        instance._anim.run();
	                    }
	                    else {
							instance.fire('start');

							host.set('height', size.height);
							host.set('width', size.width);
							instance._alignWidget();

							instance.fire('end');
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