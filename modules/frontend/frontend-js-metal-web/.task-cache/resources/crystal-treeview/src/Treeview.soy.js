'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/crystal-treeview/src/Treeview.soy", ['exports', 'metal/src/component/Component', 'metal/src/soy/SoyAop', 'metal/src/soy/SoyRenderer', 'metal/src/soy/SoyTemplates'], function (exports, _Component2, _SoyAop, _SoyRenderer, _SoyTemplates) {
  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  var _Component3 = _interopRequireDefault(_Component2);

  var _SoyAop2 = _interopRequireDefault(_SoyAop);

  var _SoyRenderer2 = _interopRequireDefault(_SoyRenderer);

  var _SoyTemplates2 = _interopRequireDefault(_SoyTemplates);

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

    return call && ((typeof call === 'undefined' ? 'undefined' : _typeof(call)) === "object" || typeof call === "function") ? call : self;
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

  var Templates = _SoyTemplates2.default.get();

  if (typeof Templates.Treeview == 'undefined') {
    Templates.Treeview = {};
  }

  Templates.Treeview.content = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="treeview component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '" role="tree">' + Templates.Treeview.nodes(opt_data, null, opt_ijData) + '</div>');
  };

  if (goog.DEBUG) {
    Templates.Treeview.content.soyTemplateName = 'Templates.Treeview.content';
  }

  Templates.Treeview.nodes = function (opt_data, opt_ignored, opt_ijData) {
    var output = '';
    var elementId__soy11 = opt_data.id + '-' + (opt_data.surfaceId != null ? opt_data.surfaceId : 'nodes');
    output += '<ul id="' + soy.$$escapeHtmlAttribute(elementId__soy11) + '" class="treeview-nodes">';
    var nodeList15 = opt_data.nodes;
    var nodeListLen15 = nodeList15.length;

    for (var nodeIndex15 = 0; nodeIndex15 < nodeListLen15; nodeIndex15++) {
      var nodeData15 = nodeList15[nodeIndex15];
      var index__soy16 = nodeIndex15;
      output += Templates.Treeview.node({
        id: opt_data.id,
        node: nodeData15,
        surfaceId: opt_data.parentSurfaceId != null ? opt_data.parentSurfaceId + '-' + index__soy16 : index__soy16
      }, null, opt_ijData);
    }

    output += '</ul>';
    return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
  };

  if (goog.DEBUG) {
    Templates.Treeview.nodes.soyTemplateName = 'Templates.Treeview.nodes';
  }

  Templates.Treeview.node = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<li id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-' + soy.$$escapeHtmlAttribute(opt_data.surfaceId) + '" class="treeview-node">' + (opt_data.node ? '<div class="treeview-node-wrapper' + soy.$$escapeHtmlAttribute(opt_data.node.expanded ? ' expanded' : '') + '"><div class="treeview-node-main clearfix' + soy.$$escapeHtmlAttribute(opt_data.node.children ? ' hasChildren' : '') + '" data-onclick="handleNodeClicked_" data-onkeyup="handleNodeKeyUp_" aria-expanded="' + soy.$$escapeHtmlAttribute(opt_data.node.expanded ? 'true' : 'false') + '" role="treeitem" tabindex="0">' + (opt_data.node.children ? '<div class="treeview-node-toggler"></div>' : '') + '<span class="treeview-node-name">' + soy.$$escapeHtml(opt_data.node.name) + '</span></div>' + (opt_data.node.children ? Templates.Treeview.nodes({
      id: opt_data.id,
      nodes: opt_data.node.children,
      parentSurfaceId: opt_data.surfaceId,
      surfaceId: opt_data.surfaceId + '-nodes'
    }, null, opt_ijData) : '') + '</div>' : '') + '</li>');
  };

  if (goog.DEBUG) {
    Templates.Treeview.node.soyTemplateName = 'Templates.Treeview.node';
  }

  Templates.Treeview.content.params = ["id"];
  Templates.Treeview.nodes.params = ["id", "nodes", "parentSurfaceId", "surfaceId"];
  Templates.Treeview.node.private = true;

  var Treeview = (function (_Component) {
    _inherits(Treeview, _Component);

    function Treeview() {
      _classCallCheck(this, Treeview);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return Treeview;
  })(_Component3.default);

  Treeview.prototype.registerMetalComponent && Treeview.prototype.registerMetalComponent(Treeview, 'Treeview')
  Treeview.RENDERER = _SoyRenderer2.default;

  _SoyAop2.default.registerTemplates('Treeview');

  exports.default = Treeview;
});
//# sourceMappingURL=Treeview.soy.js.map