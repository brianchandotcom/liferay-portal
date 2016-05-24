define(['exports', 'metal/src/metal'], function (exports, _metal) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	function _interopRequireDefault(obj) {
		return obj && obj.__esModule ? obj : {
			default: obj
		};
	}

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var IncrementalDomUtils = function () {
		function IncrementalDomUtils() {
			_classCallCheck(this, IncrementalDomUtils);
		}

		IncrementalDomUtils.buildConfigFromCall = function buildConfigFromCall(args) {
			var config = {};
			if (args[1]) {
				config.key = args[1];
			}
			var attrsArr = (args[2] || []).concat(args.slice(3));
			for (var i = 0; i < attrsArr.length; i += 2) {
				config[attrsArr[i]] = attrsArr[i + 1];
			}
			return config;
		};

		IncrementalDomUtils.buildCallFromConfig = function buildCallFromConfig(tag, config) {
			var call = [tag, config.key, []];
			var keys = Object.keys(config);
			for (var i = 0; i < keys.length; i++) {
				if (keys[i] !== 'children') {
					call.push(keys[i], config[keys[i]]);
				}
			}
			return call;
		};

		IncrementalDomUtils.isComponentTag = function isComponentTag(tag) {
			return !_metal2.default.isString(tag) || tag[0] === tag[0].toUpperCase();
		};

		return IncrementalDomUtils;
	}();

	exports.default = IncrementalDomUtils;
});
//# sourceMappingURL=IncrementalDomUtils.js.map