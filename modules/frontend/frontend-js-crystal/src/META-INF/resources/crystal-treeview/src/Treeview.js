define(['exports', 'module', 'metal/src/component/ComponentRegistry', 'metal/src/soy/SoyComponent', 'metal/src/dom/dom', 'crystal-treeview/src/Treeview.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, module, _metalSrcComponentComponentRegistry, _metalSrcSoySoyComponent, _metalSrcDomDom, _crystalTreeviewSrcTreeviewSoy, _metalJqueryAdapterSrcJQueryAdapter) {
	'use strict';

	var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

	var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

	function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) subClass.__proto__ = superClass; }

	var _ComponentRegistry = _interopRequireDefault(_metalSrcComponentComponentRegistry);

	var _SoyComponent2 = _interopRequireDefault(_metalSrcSoySoyComponent);

	var _dom = _interopRequireDefault(_metalSrcDomDom);

	var _JQueryAdapter = _interopRequireDefault(_metalJqueryAdapterSrcJQueryAdapter);

	/**
  * Treeview component.
  */

	var Treeview = (function (_SoyComponent) {
		_inherits(Treeview, _SoyComponent);

		function Treeview(opt_config) {
			_classCallCheck(this, Treeview);

			_get(Object.getPrototypeOf(Treeview.prototype), 'constructor', this).call(this, opt_config);
		}

		_createClass(Treeview, [{
			key: 'attached',

			/**
    * Called after this component has been attached to the dom.
    */
			value: function attached() {
				this.on('nodesChanged', this.onNodesChanged_.bind(this));
			}
		}, {
			key: 'getNodeObj',

			/**
    * Gets the node object from the nodes attribute that is located at the given
    * index path.
    * @param {!Array<number>} path An array of indexes indicating where the searched
    *   node is located inside the nodes attribute.
    * @return {!Object}
    */
			value: function getNodeObj(path) {
				var obj = this.nodes[path[0]];
				for (var i = 1; i < path.length; i++) {
					obj = obj.children[path[i]];
				}
				return obj;
			}
		}, {
			key: 'getNodeObjFromId_',

			/**
    * Gets the node object that the given element id represents from the nodes
    * attribute
    * @param {string} id
    * @return {!Object}
    */
			value: function getNodeObjFromId_(id) {
				var path = id.substr(this.id.length + 1).split('-');
				return this.getNodeObj(path);
			}
		}, {
			key: 'getSurfaceContent_',

			/**
    * Overrides SoyComponent's original method, skipping it when the flag for
    * ignoring surface updates is set.
    * @param {string} surfaceId The surface id.
    * @return {Object|string} The content to be rendered.
    * @protected
    * @override
    */
			value: function getSurfaceContent_(surfaceId) {
				if (!this.ignoreSurfaceUpdate_) {
					return _get(Object.getPrototypeOf(Treeview.prototype), 'getSurfaceContent_', this).call(this, surfaceId);
				}
				this.ignoreSurfaceUpdate_ = false;
			}
		}, {
			key: 'handleNodeClicked_',

			/**
    * This is called when one of this tree view's nodes is clicked.
    * @param {Event} event
    * @protected
    */
			value: function handleNodeClicked_(event) {
				var node = event.delegateTarget.parentNode;
				var nodeObj = this.getNodeObjFromId_(node.parentNode.id);
				nodeObj.expanded = !nodeObj.expanded;
				if (nodeObj.expanded) {
					_dom['default'].addClasses(node, 'expanded');
				} else {
					_dom['default'].removeClasses(node, 'expanded');
				}

				this.nodes = this.nodes;
				this.ignoreSurfaceUpdate_ = true;
			}
		}, {
			key: 'onNodesChanged_',

			/**
    * Fired when the `nodes` attribute changes. Make sure that any other
    * updates to the `nodes` attribute made after ignoreSurfaceUpdate_ is
    * set to true, cause surfaces to update again.
    * @return {[type]} [description]
    */
			value: function onNodesChanged_() {
				this.ignoreSurfaceUpdate_ = false;
			}
		}]);

		return Treeview;
	})(_SoyComponent2['default']);

	/**
  * Default tree view elementClasses.
  * @default treeView
  * @type {string}
  * @static
  */
	Treeview.ELEMENT_CLASSES = 'treeview';

	/**
  * Treeview attributes definition.
  * @type {Object}
  * @static
  */
	Treeview.ATTRS = {
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

	_ComponentRegistry['default'].register('Treeview', Treeview);

	module.exports = Treeview;
	_JQueryAdapter['default'].register('treeview', Treeview);
});