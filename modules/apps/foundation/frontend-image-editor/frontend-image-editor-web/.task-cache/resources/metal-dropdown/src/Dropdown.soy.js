define("frontend-image-editor-web@1.0.2/metal-dropdown/src/Dropdown.soy", ['exports', 'metal-component/src/all/component', 'metal-soy/src/Soy'], function (exports, _component, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.Dropdown = undefined;

  var _component2 = _interopRequireDefault(_component);

  var _Soy2 = _interopRequireDefault(_Soy);

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

  var templates;
  goog.loadModule(function (exports) {

    // This file was automatically generated from Dropdown.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace Dropdown.
     * @public
     */

    goog.module('Dropdown.incrementaldom');

    /** @suppress {extraRequire} */
    var soy = goog.require('soy');
    /** @suppress {extraRequire} */
    var soydata = goog.require('soydata');
    /** @suppress {extraRequire} */
    goog.require('goog.asserts');
    /** @suppress {extraRequire} */
    goog.require('soy.asserts');
    /** @suppress {extraRequire} */
    goog.require('goog.i18n.bidi');
    var IncrementalDom = goog.require('incrementaldom');
    var ie_open = IncrementalDom.elementOpen;
    var ie_close = IncrementalDom.elementClose;
    var ie_void = IncrementalDom.elementVoid;
    var ie_open_start = IncrementalDom.elementOpenStart;
    var ie_open_end = IncrementalDom.elementOpenEnd;
    var itext = IncrementalDom.text;
    var iattr = IncrementalDom.attr;

    /**
     * @param {{
     *    alignedPosition: (?),
     *    classMap: (?),
     *    elementClasses: (?),
     *    expanded: (?),
     *    position: (?),
     *    positionClassOnMenu: (?),
     *    body: (?soydata.SanitizedHtml|string|undefined),
     *    header: (?soydata.SanitizedHtml|string|undefined)
     * }} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render(opt_data, opt_ignored, opt_ijData) {
      opt_data = opt_data || {};
      soy.asserts.assertType(opt_data.body == null || opt_data.body instanceof Function || opt_data.body instanceof soydata.UnsanitizedText || goog.isString(opt_data.body), 'body', opt_data.body, '?soydata.SanitizedHtml|string|undefined');
      var body = /** @type {?soydata.SanitizedHtml|string|undefined} */opt_data.body;
      soy.asserts.assertType(opt_data.header == null || opt_data.header instanceof Function || opt_data.header instanceof soydata.UnsanitizedText || goog.isString(opt_data.header), 'header', opt_data.header, '?soydata.SanitizedHtml|string|undefined');
      var header = /** @type {?soydata.SanitizedHtml|string|undefined} */opt_data.header;
      var classes__soy3 = opt_data.classMap ? opt_data.classMap : ['dropup', 'dropup', 'dropright', 'dropdown', 'dropdown', 'dropdown', 'dropleft', 'dropup'];
      var currentPosition__soy4 = opt_data.alignedPosition != null ? opt_data.alignedPosition : opt_data.position;
      var positionClass__soy5 = currentPosition__soy4 != null ? classes__soy3[currentPosition__soy4] : 'dropdown';
      ie_open('div', null, null, 'class', (opt_data.positionClassOnMenu ? 'dropdown' : positionClass__soy5) + (opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + (opt_data.expanded ? ' open' : ''));
      if (header) {
        header();
      }
      ie_open('ul', null, null, 'class', 'dropdown-menu' + (opt_data.positionClassOnMenu ? ' ' + positionClass__soy5 : ''));
      if (body) {
        body();
      }
      ie_close('ul');
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'Dropdown.render';
    }

    exports.render.params = ["body", "header", "alignedPosition", "classMap", "elementClasses", "expanded", "position", "positionClassOnMenu"];
    exports.render.types = { "body": "html", "header": "html", "alignedPosition": "any", "classMap": "any", "elementClasses": "any", "expanded": "any", "position": "any", "positionClassOnMenu": "any" };
    exports.templates = templates = exports;
    return exports;
  });

  var Dropdown = function (_Component) {
    _inherits(Dropdown, _Component);

    function Dropdown() {
      _classCallCheck(this, Dropdown);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return Dropdown;
  }(_component2.default);

  _Soy2.default.register(Dropdown, templates);
  exports.Dropdown = Dropdown;
  exports.templates = templates;
  exports.default = templates;
});
//# sourceMappingURL=Dropdown.soy.js.map