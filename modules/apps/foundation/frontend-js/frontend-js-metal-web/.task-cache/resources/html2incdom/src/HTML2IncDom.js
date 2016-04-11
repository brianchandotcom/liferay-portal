define("frontend-js-metal-web@1.0.6/html2incdom/src/HTML2IncDom", ['exports', './unescape'], function (exports, _unescape) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _unescape2 = _interopRequireDefault(_unescape);

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

	var parser_;

	var HTML2IncDom = function () {
		function HTML2IncDom() {
			_classCallCheck(this, HTML2IncDom);
		}

		HTML2IncDom.buildFn = function buildFn(html) {
			return function () {
				return HTML2IncDom.run(html);
			};
		};

		HTML2IncDom.getParser = function getParser() {
			return parser_ || window.HTMLParser;
		};

		HTML2IncDom.run = function run(html) {
			HTML2IncDom.getParser()(html, {
				start: function start(tag, attrs, unary) {
					var fn = unary ? IncrementalDOM.elementVoid : IncrementalDOM.elementOpen;
					var args = [tag, null, []];
					for (var i = 0; i < attrs.length; i++) {
						args.push(attrs[i].name, attrs[i].value);
					}
					fn.apply(null, args);
				},

				end: function end(tag) {
					IncrementalDOM.elementClose(tag);
				},

				chars: function chars(text) {
					IncrementalDOM.text(text, _unescape2.default);
				}
			});
		};

		HTML2IncDom.setParser = function setParser(newParser) {
			parser_ = newParser;
		};

		return HTML2IncDom;
	}();

	exports.default = HTML2IncDom;
});
//# sourceMappingURL=HTML2IncDom.js.map