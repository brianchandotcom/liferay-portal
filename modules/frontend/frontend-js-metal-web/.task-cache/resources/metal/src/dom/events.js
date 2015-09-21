define(
    "frontend-js-metal-web@1.0.0/metal/src/dom/events",
    ['exports', 'metal/src/dom/dom', 'metal/src/dom/features'],
    function (exports, _metalSrcDomDom, _metalSrcDomFeatures) {
        'use strict';

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        var _dom = _interopRequireDefault(_metalSrcDomDom);

        var _features = _interopRequireDefault(_metalSrcDomFeatures);

        var mouseEventMap = {
            mouseenter: 'mouseover',
            mouseleave: 'mouseout',
            pointerenter: 'pointerover',
            pointerleave: 'pointerout'
        };
        Object.keys(mouseEventMap).forEach(function (eventName) {
            _dom['default'].registerCustomEvent(eventName, {
                delegate: true,
                handler: function handler(callback, event) {
                    var related = event.relatedTarget;
                    var target = event.delegateTarget;
                    if (!related || related !== target && !target.contains(related)) {
                        event.customType = eventName;
                        return callback(event);
                    }
                },
                originalEvent: mouseEventMap[eventName]
            });
        });

        var animationEventMap = {
            animation: 'animationend',
            transition: 'transitionend'
        };
        Object.keys(animationEventMap).forEach(function (eventType) {
            var eventName = animationEventMap[eventType];
            _dom['default'].registerCustomEvent(eventName, {
                event: true,
                delegate: true,
                handler: function handler(callback, event) {
                    event.customType = eventName;
                    return callback(event);
                },
                originalEvent: _features['default'].checkAnimationEventName()[eventType]
            });
        });
    }
);