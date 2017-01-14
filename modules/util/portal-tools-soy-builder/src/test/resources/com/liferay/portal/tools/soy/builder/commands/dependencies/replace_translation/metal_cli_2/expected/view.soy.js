Liferay.Loader.define("hello-soy-web@1.0.4/View.soy", ['exports', 'metal-component/src/all/component', 'metal-soy/src/Soy'], function (exports, _component, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.View = undefined;

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

    // This file was automatically generated from View.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace View.
     * @public
     */

    goog.module('View.incrementaldom');

    /** @suppress {extraRequire} */
    var soy = goog.require('soy');
    /** @suppress {extraRequire} */
    var soydata = goog.require('soydata');
    /** @suppress {extraRequire} */
    goog.require('goog.i18n.bidi');
    /** @suppress {extraRequire} */
    goog.require('goog.asserts');
    /** @suppress {extraRequire} */
    goog.require('goog.string');
    var IncrementalDom = goog.require('incrementaldom');
    var ie_open = IncrementalDom.elementOpen;
    var ie_close = IncrementalDom.elementClose;
    var ie_void = IncrementalDom.elementVoid;
    var ie_open_start = IncrementalDom.elementOpenStart;
    var ie_open_end = IncrementalDom.elementOpenEnd;
    var itext = IncrementalDom.text;
    var iattr = IncrementalDom.attr;

    var $templateAlias2 = _Soy2.default.getTemplate('Footer.incrementaldom', 'render');

    var $templateAlias1 = _Soy2.default.getTemplate('Header.incrementaldom', 'render');

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render(opt_data, opt_ignored, opt_ijData) {
      ie_open('div', null, null, 'id', opt_data.id);
      $templateAlias1(opt_data, null, opt_ijData);
      ie_open('p');
      /** @desc  */
      var MSG_EXTERNAL_7241381614077301826 = Liferay.Language.get('here-you-will-find-how-easy-it-is-to-do-things-like');
MSG_EXTERNAL_7241381614077301826 = MSG_EXTERNAL_7241381614077301826.replace(/{(\d+)}/g, '\x01$1\x01')
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_7241381614077301826));
      ie_close('p');
      ie_open('h3');
      /** @desc  */
      var MSG_EXTERNAL_52703005167531630 = Liferay.Language.get('listing-pages');
MSG_EXTERNAL_52703005167531630 = MSG_EXTERNAL_52703005167531630.replace(/{(\d+)}/g, '\x01$1\x01')
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_52703005167531630));
      ie_close('h3');
      ie_open('div', null, null, 'class', 'list-group');
      ie_open('div', null, null, 'class', 'list-group-heading');
      /** @desc  */
      var MSG_EXTERNAL_7891216484594141556 = Liferay.Language.get('navigate-to');
MSG_EXTERNAL_7891216484594141556 = MSG_EXTERNAL_7891216484594141556.replace(/{(\d+)}/g, '\x01$1\x01')
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_7891216484594141556));
      ie_close('div');
      var layoutList60 = opt_data.themeDisplay.layouts;
      var layoutListLen60 = layoutList60.length;
      for (var layoutIndex60 = 0; layoutIndex60 < layoutListLen60; layoutIndex60++) {
        var layoutData60 = layoutList60[layoutIndex60];
        ie_open('a', null, null, 'class', 'list-group-item', 'href', layoutData60.friendlyURL);
        var dyn1 = layoutData60.nameCurrentValue;
        if (typeof dyn1 == 'function') dyn1();else if (dyn1 != null) itext(dyn1);
        ie_close('a');
      }
      ie_close('div');
      ie_open('h3');
      /** @desc  */
      var MSG_EXTERNAL_9050153343882765023 = Liferay.Language.get('navigating-between-views');
MSG_EXTERNAL_9050153343882765023 = MSG_EXTERNAL_9050153343882765023.replace(/{(\d+)}/g, '\x01$1\x01')
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_9050153343882765023));
      ie_close('h3');
      ie_open('a', null, null, 'href', opt_data.navigationURL);
      /** @desc  */
      var MSG_EXTERNAL_7202372830797071859 = Liferay.Language.get('click-here-to-navigate-to-another-view');
MSG_EXTERNAL_7202372830797071859 = MSG_EXTERNAL_7202372830797071859.replace(/{(\d+)}/g, '\x01$1\x01')
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_7202372830797071859));
      ie_close('a');
      $templateAlias2(opt_data, null, opt_ijData);
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'View.render';
    }

    exports.render.params = ["id", "navigationURL", "themeDisplay"];
    exports.render.types = { "id": "any", "navigationURL": "any", "themeDisplay": "any" };
    exports.templates = templates = exports;
    return exports;
  });

  var View = function (_Component) {
    _inherits(View, _Component);

    function View() {
      _classCallCheck(this, View);

      return _possibleConstructorReturn(this, (View.__proto__ || Object.getPrototypeOf(View)).apply(this, arguments));
    }

    return View;
  }(_component2.default);

  _Soy2.default.register(View, templates);
  exports.View = View;
  exports.templates = templates;
  exports.default = templates;
});
//# sourceMappingURL=View.soy.js.map