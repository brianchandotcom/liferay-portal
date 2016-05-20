define(['exports', '../IncrementalDomAop'], function (exports, _IncrementalDomAop) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _IncrementalDomAop2 = _interopRequireDefault(_IncrementalDomAop);

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

	var IncrementalDomChildren = function () {
		function IncrementalDomChildren() {
			_classCallCheck(this, IncrementalDomChildren);
		}

		IncrementalDomChildren.capture = function capture(callback) {
			callback_ = callback;
			tree_ = {
				children: []
			};
			currentParent_ = tree_;
			_IncrementalDomAop2.default.startInterception({
				elementClose: handleInterceptedCloseCall_,
				elementOpen: handleInterceptedOpenCall_,
				text: handleInterceptedTextCall_
			});
		};

		IncrementalDomChildren.render = function render(tree, opt_skipNode) {
			if (opt_skipNode && opt_skipNode(tree)) {
				return;
			}

			if (tree.isText) {
				IncrementalDOM.text.apply(null, tree.args);
			} else {
				if (tree.args) {
					IncrementalDOM.elementOpen.apply(null, tree.args);
				}
				if (tree.children) {
					for (var i = 0; i < tree.children.length; i++) {
						IncrementalDomChildren.render(tree.children[i], opt_skipNode);
					}
				}
				if (tree.args) {
					IncrementalDOM.elementClose(tree.args[0]);
				}
			}
		};

		return IncrementalDomChildren;
	}();

	var callback_;
	var currentParent_;
	var tree_;

	/**
  * Adds a child element to the tree.
  * @param {!Array} args The arguments passed to the incremental dom call.
  * @param {boolean=} opt_isText Optional flag indicating if the child is a
  *     text element.
  * @protected
  */
	function addChildToTree_(args, opt_isText) {
		var child = {
			args: args,
			children: [],
			isText: opt_isText,
			parent: currentParent_
		};
		currentParent_.children.push(child);
		return child;
	}

	/**
  * Handles an intercepted call to the `elementClose` function from incremental
  * dom.
  * @protected
  */
	function handleInterceptedCloseCall_() {
		if (currentParent_ === tree_) {
			_IncrementalDomAop2.default.stopInterception();
			callback_(tree_);
			tree_ = null;
			callback_ = null;
			currentParent_ = null;
		} else {
			currentParent_ = currentParent_.parent;
		}
	}

	/**
  * Handles an intercepted call to the `elementOpen` function from incremental
  * dom.
  * @param {!function()} originalFn The original function before interception.
  * @protected
  */
	function handleInterceptedOpenCall_(originalFn) {
		for (var _len = arguments.length, args = Array(_len > 1 ? _len - 1 : 0), _key = 1; _key < _len; _key++) {
			args[_key - 1] = arguments[_key];
		}

		currentParent_ = addChildToTree_(args);
	}

	/**
  * Handles an intercepted call to the `text` function from incremental dom.
  * @param {!function()} originalFn The original function before interception.
  * @protected
  */
	function handleInterceptedTextCall_(originalFn) {
		for (var _len2 = arguments.length, args = Array(_len2 > 1 ? _len2 - 1 : 0), _key2 = 1; _key2 < _len2; _key2++) {
			args[_key2 - 1] = arguments[_key2];
		}

		addChildToTree_(args, true);
	}

	exports.default = IncrementalDomChildren;
});
//# sourceMappingURL=IncrementalDomChildren.js.map