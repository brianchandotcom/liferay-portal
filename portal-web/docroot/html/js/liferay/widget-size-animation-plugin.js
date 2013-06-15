AUI.add(
    'liferay-widget-size-animation-plugin',
    function (A) {
        var Lang = A.Lang;
    
        var STR_HOST = 'host';

        var STR_SIZE_ANIM = 'sizeanim';

        var SizeAnim = A.Component.create(
            {
                EXTENDS: A.Plugin.Base,

                NAME: STR_SIZE_ANIM,

                NS: STR_SIZE_ANIM,

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
                    }
                },

                prototype: {
                    initializer: function(config) {
                        var instance = this;

                        var host = this.get(STR_HOST);

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

                        instance._anim.after('tween', instance._alignWidget, instance);
                    },

                    _alignWidget: function() {
                        var instance = this;

                        var host = this.get(STR_HOST);

                        if (instance.get('align')) {
                            host.align();
                        }
                    },

                    _animWidgetSize: function(size) {
                        var instance = this;

                        var host = this.get(STR_HOST);

                        instance._anim.set(
                            'to',
                            { 
                                width: size.width,
                                height: size.height
                            }
                        );

                        instance._anim.run();
                    }
                }
            }
        );

        A.namespace("Plugin").SizeAnim = SizeAnim;
    }, 
    '',
    {
        requires: ["anim-base", "anim-easing", "plugin", "widget"]
    }
);