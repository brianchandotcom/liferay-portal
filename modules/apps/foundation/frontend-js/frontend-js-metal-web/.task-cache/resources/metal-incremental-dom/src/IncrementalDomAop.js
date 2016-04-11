define("frontend-js-metal-web@1.0.6/metal-incremental-dom/src/IncrementalDomAop", ['exports', 'metal/src/metal', './incremental-dom'], function (exports, _metal) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var IncrementalDomAop = function () {
		function IncrementalDomAop() {
			_classCallCheck(this, IncrementalDomAop);
		}

		IncrementalDomAop.getOriginalFns = function getOriginalFns() {
			return fnStack[0];
		};

		IncrementalDomAop.startInterception = function startInterception(openFn, closeFn, attributesFn) {
			openFn = openFn.bind(null, fnStack[0].elementOpen);
			closeFn = closeFn.bind(null, fnStack[0].elementClose);
			fnStack.push({
				attr: fnAttr,
				attributes: attributesFn.bind(null, fnStack[0].attributes),
				elementClose: closeFn,
				elementOpen: openFn,
				elementOpenEnd: function elementOpenEnd() {
					return openFn.apply(null, collectedArgs);
				},
				elementOpenStart: fnOpenStart,
				elementVoid: function elementVoid(tag) {
					var node = openFn.apply(null, arguments);
					closeFn(tag);
					return node;
				}
			});
		};

		IncrementalDomAop.stopInterception = function stopInterception() {
			if (fnStack.length > 1) {
				fnStack.pop();
			}
		};

		return IncrementalDomAop;
	}();

	var fnStack = [{
		attr: IncrementalDOM.attr,
		attributes: IncrementalDOM.attributes[IncrementalDOM.symbols.default],
		elementClose: IncrementalDOM.elementClose,
		elementOpen: IncrementalDOM.elementOpen,
		elementOpenEnd: IncrementalDOM.elementOpenEnd,
		elementOpenStart: IncrementalDOM.elementOpenStart,
		elementVoid: IncrementalDOM.elementVoid
	}];

	var collectedArgs = [];

	function fnAttr(name, value) {
		collectedArgs.push(name, value);
	}

	function fnOpenStart(tag, key, statics) {
		collectedArgs = [tag, key, statics];
	}

	function handleCall(name) {
		var fn = fnStack[fnStack.length - 1][name];
		fn.apply(null, _metal.array.slice(arguments, 1));
	}

	IncrementalDOM.attr = handleCall.bind(null, 'attr');
	IncrementalDOM.elementClose = handleCall.bind(null, 'elementClose');
	IncrementalDOM.elementOpen = handleCall.bind(null, 'elementOpen');
	IncrementalDOM.elementOpenEnd = handleCall.bind(null, 'elementOpenEnd');
	IncrementalDOM.elementOpenStart = handleCall.bind(null, 'elementOpenStart');
	IncrementalDOM.elementVoid = handleCall.bind(null, 'elementVoid');

	IncrementalDOM.attributes[IncrementalDOM.symbols.default] = handleCall.bind(null, 'attributes');

	exports.default = IncrementalDomAop;
});
//# sourceMappingURL=IncrementalDomAop.js.map