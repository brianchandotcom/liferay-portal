define("frontend-js-metal-web@1.0.6/metal-treeview/src/Treeview", ['exports', 'metal-component/src/all/component', 'metal-soy/src/Soy', './Treeview.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _component, _Soy, _Treeview, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _component2 = _interopRequireDefault(_component);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _Treeview2 = _interopRequireDefault(_Treeview);

	var _JQueryAdapter2 = _interopRequireDefault(_JQueryAdapter);

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

	function _possibleConstructorReturn(self, call) {
		if (!self) {
			throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
		}

		return call && (typeof call === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
		if (typeof superClass !== "function" && superClass !== null) {
			throw new TypeError("Super expression must either be null or a function, not " + typeof superClass);
		}

		subClass.prototype = Object.create(superClass && superClass.prototype, {
			constructor: {
				value: subClass,
				enumerable: false,
				writable: true,
				configurable: true
			}
		});
		if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var Treeview = function (_Component) {
		_inherits(Treeview, _Component);

		function Treeview() {
			_classCallCheck(this, Treeview);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		Treeview.prototype.getNodeObj = function getNodeObj(path) {
			var obj = this.nodes[path[0]];
			for (var i = 1; i < path.length; i++) {
				obj = obj.children[path[i]];
			}
			return obj;
		};

		Treeview.prototype.handleNodeClicked_ = function handleNodeClicked_(event) {
			this.toggleExpandedState_(event.delegateTarget.parentNode.parentNode);
		};

		Treeview.prototype.handleNodeKeyUp_ = function handleNodeKeyUp_(event) {
			if (event.keyCode === 13 || event.keyCode === 32) {
				this.toggleExpandedState_(event.delegateTarget.parentNode.parentNode);
			}
		};

		Treeview.prototype.toggleExpandedState_ = function toggleExpandedState_(node) {
			var path = node.getAttribute('data-treeview-path').split('-');
			var nodeObj = this.getNodeObj(path);
			nodeObj.expanded = !nodeObj.expanded;
			this.nodes = this.nodes;
		};

		return Treeview;
	}(_component2.default);

	_Soy2.default.register(Treeview, _Treeview2.default);

	/**
  * Treeview state definition.
  * @type {!Object}
  * @static
  */
	Treeview.STATE = {
		/**
   * This tree view's nodes. Each node should have a name, and can optionally
   * have nested children nodes. It should also indicate if its children are
   * expanded or not.
   * @type {Array<!{children: Array, expanded: boolean?, name: string}>}
   * @default []
   */
		nodes: {
			validator: Array.isArray,
			valueFn: function valueFn() {
				return [];
			}
		}
	};

	exports.default = Treeview;
	_JQueryAdapter2.default.register('treeview', Treeview);
});
//# sourceMappingURL=Treeview.js.map