define("frontend-js-metal-web@1.0.6/metal-pagination/src/Pagination.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.Pagination = undefined;

  var _Component3 = _interopRequireDefault(_Component2);

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

    // This file was automatically generated from Pagination.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace Pagination.
     * @public
     */

    goog.module('Pagination.incrementaldom');

    /** @suppress {extraRequire} */
    var soy = goog.require('soy');
    /** @suppress {extraRequire} */
    var soydata = goog.require('soydata');
    /** @suppress {extraRequire} */
    goog.require('goog.i18n.bidi');
    /** @suppress {extraRequire} */
    goog.require('goog.asserts');
    var IncrementalDom = goog.require('incrementaldom');
    var ie_open = IncrementalDom.elementOpen;
    var ie_close = IncrementalDom.elementClose;
    var ie_void = IncrementalDom.elementVoid;
    var ie_open_start = IncrementalDom.elementOpenStart;
    var ie_open_end = IncrementalDom.elementOpenEnd;
    var itext = IncrementalDom.text;
    var iattr = IncrementalDom.attr;

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render(opt_data, opt_ignored, opt_ijData) {
      ie_open('ul', null, null, 'class', 'pagination' + (opt_data.elementClasses ? ' ' + opt_data.elementClasses : ''));
      if (opt_data.showControls == true) {
        var disabled__soy8 = !opt_data.circular && opt_data.page == 0 ? true : false;
        $renderControlElement_({ content: opt_data.strings.prev, disabled: disabled__soy8, index: 0 }, null, opt_ijData);
      }
      var iLimit13 = opt_data.total;
      for (var i13 = 0; i13 < iLimit13; i13++) {
        var active__soy14 = opt_data.page == i13 ? true : false;
        $renderElement_({ active: active__soy14, content: opt_data.offset + i13, index: i13 }, null, opt_ijData);
      }
      if (opt_data.showControls == true) {
        var disabled__soy21 = !opt_data.circular && opt_data.page == opt_data.total - 1 ? true : false;
        $renderControlElement_({ content: opt_data.strings.next, disabled: disabled__soy21, index: 1 }, null, opt_ijData);
      }
      ie_close('ul');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'Pagination.render';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $renderElement_(opt_data, opt_ignored, opt_ijData) {
      ie_open_start('li');
      iattr('class', 'pagination-item' + (opt_data.active ? ' active' : ''));
      if (!opt_data.active) {
        iattr('data-onclick', 'onClickItem');
      }
      iattr('data-index', opt_data.index);
      ie_open_end();
      ie_open('a', null, null, 'href', '#');
      itext((goog.asserts.assert(opt_data.content != null), opt_data.content));
      ie_close('a');
      ie_close('li');
    }
    exports.renderElement_ = $renderElement_;
    if (goog.DEBUG) {
      $renderElement_.soyTemplateName = 'Pagination.renderElement_';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $renderControlElement_(opt_data, opt_ignored, opt_ijData) {
      ie_open_start('li');
      iattr('class', 'pagination-control' + (opt_data.disabled ? ' disabled' : ''));
      if (!opt_data.disabled) {
        iattr('data-onclick', 'onClickControls');
      }
      iattr('data-control-index', opt_data.index);
      ie_open_end();
      ie_open('a', null, null, 'href', '#');
      itext((goog.asserts.assert(opt_data.content != null), opt_data.content));
      ie_close('a');
      ie_close('li');
    }
    exports.renderControlElement_ = $renderControlElement_;
    if (goog.DEBUG) {
      $renderControlElement_.soyTemplateName = 'Pagination.renderControlElement_';
    }

    exports.render.params = ["circular", "elementClasses", "offset", "page", "strings", "showControls", "total"];
    exports.render.types = { "circular": "any", "elementClasses": "any", "offset": "any", "page": "any", "strings": "any", "showControls": "any", "total": "any" };
    exports.renderElement_.params = ["active", "content", "index"];
    exports.renderElement_.types = { "active": "any", "content": "any", "index": "any" };
    exports.renderControlElement_.params = ["content", "disabled", "index"];
    exports.renderControlElement_.types = { "content": "any", "disabled": "any", "index": "any" };
    exports.templates = templates = exports;
    return exports;
  });

  var Pagination = function (_Component) {
    _inherits(Pagination, _Component);

    function Pagination() {
      _classCallCheck(this, Pagination);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return Pagination;
  }(_Component3.default);

  _Soy2.default.register(Pagination, templates);
  exports.Pagination = Pagination;
  exports.templates = templates;
  exports.default = templates;
});
//# sourceMappingURL=Pagination.soy.js.map