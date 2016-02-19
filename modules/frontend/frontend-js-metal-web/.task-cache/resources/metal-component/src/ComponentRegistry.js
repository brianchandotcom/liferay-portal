define("frontend-js-metal-web@1.0.0/metal-component/src/ComponentRegistry", ['exports', 'metal/src/metal'], function (exports, _metal) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var ComponentRegistry = function () {
		function ComponentRegistry() {
			_classCallCheck(this, ComponentRegistry);
		}

		ComponentRegistry.getConstructor = function getConstructor(name) {
			var constructorFn = ComponentRegistry.components_[name];
			if (!constructorFn) {
				console.error('There\'s no constructor registered for the component ' + 'named ' + name + '. Components need to be registered via ' + 'ComponentRegistry.register.');
			}
			return constructorFn;
		};

		ComponentRegistry.register = function register(constructorFn, opt_name) {
			var name = opt_name;
			if (!name) {
				if (constructorFn.hasOwnProperty('NAME')) {
					name = constructorFn.NAME;
				} else {
					name = _metal.core.getFunctionName(constructorFn);
				}
			}
			constructorFn.NAME = name;
			ComponentRegistry.components_[name] = constructorFn;
		};

		return ComponentRegistry;
	}();

	/**
  * Holds all registered components, indexed by their names.
  * @type {!Object<string, function()>}
  * @protected
  * @static
  */
	ComponentRegistry.components_ = {};

	exports.default = ComponentRegistry;
});
//# sourceMappingURL=ComponentRegistry.js.map