define(
    "frontend-js-metal-web@1.0.0/metal/src/dom/features",
    ['exports', 'module', 'metal/src/dom/dom', 'metal/src/string/string'],
    function (exports, module, _metalSrcDomDom, _metalSrcStringString) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        var _dom = _interopRequireDefault(_metalSrcDomDom);

        var _string = _interopRequireDefault(_metalSrcStringString);

        /**
      * Class with static methods responsible for doing browser feature checks.
      */

        var features = (function () {
            function features() {
                _classCallCheck(this, features);
            }

            _createClass(features, null, [{
                key: 'checkAnimationEventName',

                /**
        * Some browsers still supports prefixed animation events. This method can
        * be used to retrieve the current browser event name for both, animation
        * and transition.
        * @return {object}
        */
                value: function checkAnimationEventName() {
                    if (features.animationEventName_ === undefined) {
                        features.animationEventName_ = {
                            animation: features.checkAnimationEventName_('animation'),
                            transition: features.checkAnimationEventName_('transition')
                        };
                    }
                    return features.animationEventName_;
                }

                /**
        * @protected
        * @param {string} type Type to test: animation, transition.
        * @return {string} Browser event name.
        */
            }, {
                key: 'checkAnimationEventName_',
                value: function checkAnimationEventName_(type) {
                    var prefixes = ['Webkit', 'MS', 'O', ''];
                    var typeTitleCase = _string['default'].replaceInterval(type, 0, 1, type.substring(0, 1).toUpperCase());
                    var suffixes = [typeTitleCase + 'End', typeTitleCase + 'End', typeTitleCase + 'End', type + 'end'];
                    for (var i = 0; i < prefixes.length; i++) {
                        if (features.animationElement_.style[prefixes[i] + typeTitleCase] !== undefined) {
                            return prefixes[i].toLowerCase() + suffixes[i];
                        }
                    }
                    return type + 'end';
                }

                /**
        * Some browsers (like IE9) change the order of element attributes, when html
        * is rendered. This method can be used to check if this behavior happens on
        * the current browser.
        * @return {boolean}
        */
            }, {
                key: 'checkAttrOrderChange',
                value: function checkAttrOrderChange() {
                    if (features.attrOrderChange_ === undefined) {
                        var originalContent = '<div data-component="" data-ref=""></div>';
                        var element = document.createElement('div');
                        _dom['default'].append(element, originalContent);
                        features.attrOrderChange_ = originalContent !== element.innerHTML;
                    }
                    return features.attrOrderChange_;
                }
            }]);

            return features;
        })();

        features.animationElement_ = document.createElement('div');
        features.animationEventName_ = undefined;
        features.attrOrderChange_ = undefined;

        module.exports = features;
    }
);