define("frontend-js-metal-web@1.0.6/metal-list/src/ListItem.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.ListItem = undefined;

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

    // This file was automatically generated from ListItem.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace ListItem.
     * @public
     */

    goog.module('ListItem.incrementaldom');

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
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render(opt_data, opt_ignored, opt_ijData) {
      ie_open('li', null, null, 'class', 'listitem list-group-item ' + (opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + ' clearfix', 'data-index', opt_data.index);
      if (opt_data.item.avatar) {
        ie_open('span', null, null, 'class', 'list-image pull-left ' + opt_data.item.avatar['class']);
        $htmlContent({ content: opt_data.item.avatar.content }, null, opt_ijData);
        ie_close('span');
      }
      ie_open('div', null, null, 'class', 'list-main-content pull-left');
      ie_open('div', null, null, 'class', 'list-text-primary');
      $htmlContent({ content: opt_data.item.textPrimary }, null, opt_ijData);
      ie_close('div');
      if (opt_data.item.textSecondary) {
        ie_open('div', null, null, 'class', 'list-text-secondary');
        $htmlContent({ content: opt_data.item.textSecondary }, null, opt_ijData);
        ie_close('div');
      }
      ie_close('div');
      if (opt_data.item.icons) {
        var iconList48 = opt_data.item.icons;
        var iconListLen48 = iconList48.length;
        for (var iconIndex48 = 0; iconIndex48 < iconListLen48; iconIndex48++) {
          var iconData48 = iconList48[iconIndex48];
          ie_void('span', null, null, 'class', 'btn-icon ' + iconData48 + ' pull-right');
        }
      }
      if (opt_data.item.iconsHtml) {
        ie_open('div', null, null, 'class', 'pull-right');
        var iconHtmlList55 = opt_data.item.iconsHtml;
        var iconHtmlListLen55 = iconHtmlList55.length;
        for (var iconHtmlIndex55 = 0; iconHtmlIndex55 < iconHtmlListLen55; iconHtmlIndex55++) {
          var iconHtmlData55 = iconHtmlList55[iconHtmlIndex55];
          $htmlContent({ content: iconHtmlData55 }, null, opt_ijData);
        }
        ie_close('div');
      }
      if (opt_data.item.label) {
        ie_open('span', null, null, 'class', 'label list-label pull-right ' + opt_data.item.label['class']);
        itext((goog.asserts.assert(opt_data.item.label.content != null), opt_data.item.label.content));
        ie_close('span');
      }
      ie_close('li');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'ListItem.render';
    }

    /**
     * @param {{
     *    content: (?soydata.SanitizedHtml|string|undefined)
     * }} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $htmlContent(opt_data, opt_ignored, opt_ijData) {
      opt_data = opt_data || {};
      soy.asserts.assertType(opt_data.content == null || opt_data.content instanceof Function || opt_data.content instanceof soydata.UnsanitizedText || goog.isString(opt_data.content), 'content', opt_data.content, '?soydata.SanitizedHtml|string|undefined');
      var content = /** @type {?soydata.SanitizedHtml|string|undefined} */opt_data.content;
      if (content) {
        content();
      }
    }
    exports.htmlContent = $htmlContent;
    if (goog.DEBUG) {
      $htmlContent.soyTemplateName = 'ListItem.htmlContent';
    }

    exports.render.params = ["index", "item", "elementClasses"];
    exports.htmlContent.params = ["content"];
    exports.templates = templates = exports;
    return exports;
  });

  var ListItem = function (_Component) {
    _inherits(ListItem, _Component);

    function ListItem() {
      _classCallCheck(this, ListItem);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return ListItem;
  }(_Component3.default);

  _Soy2.default.register(ListItem, templates);
  exports.default = templates;
  exports.ListItem = ListItem;
  exports.templates = templates;
});
//# sourceMappingURL=ListItem.soy.js.map