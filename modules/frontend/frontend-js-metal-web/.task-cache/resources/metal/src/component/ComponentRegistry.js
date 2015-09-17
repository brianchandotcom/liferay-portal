define(
    "frontend-js-metal-web@1.0.0/metal/src/component/ComponentRegistry",
    ['exports', 'module'],
    function (exports, module) {
        'use strict';

        /**
      * The component registry is used to register components, so they can
      * be accessible by name.
      * @type {Object}
      */

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        var ComponentRegistry = (function () {
            function ComponentRegistry() {
                _classCallCheck(this, ComponentRegistry);
            }

            /**
       * Holds all registered components, indexed by their names.
       * @type {!Object<string, function()>}
       * @protected
       * @static
       */

            _createClass(ComponentRegistry, null, [{
                key: 'getConstructor',

                /**
        * Gets the constructor function for the given component name, or
        * undefined if it hasn't been registered yet.
        * @param {string} name The component's name.
        * @return {?function}
        * @static
        */
                value: function getConstructor(name) {
                    var constructorFn = ComponentRegistry.components_[name];
                    if (!constructorFn) {
                        console.error('There\'s no constructor registered for the component ' + 'named ' + name + '. Components need to be registered via ' + 'ComponentRegistry.register.');
                    }
                    return constructorFn;
                }

                /**
        * Registers a component.
        * @param {string} name The component's name.
        * @param {string} constructorFn The component's constructor function.
        * @static
        */
            }, {
                key: 'register',
                value: function register(name, constructorFn) {
                    ComponentRegistry.components_[name] = constructorFn;
                    constructorFn.NAME = name;
                    constructorFn.TEMPLATES = ComponentRegistry.Templates[name];
                }
            }]);

            return ComponentRegistry;
        })();

        ComponentRegistry.components_ = {};

        /**
      * Holds all registered component templates, indexed by component names.
      * Soy files automatically add their templates to this object when imported.
      * @type {!Object<string, !Object<string, !function()>>}
      * @static
      */
        ComponentRegistry.Templates = {};

        module.exports = ComponentRegistry;
    }
);