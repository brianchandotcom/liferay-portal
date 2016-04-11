define("frontend-js-metal-web@1.0.6/metal-select/src/Select.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.Select = undefined;

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

    // This file was automatically generated from Select.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace Select.
     * @public
     */

    goog.module('Select.incrementaldom');

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

    var $templateAlias1 = _Soy2.default.getTemplate('Dropdown.incrementaldom', 'render');

    /**
     * @param {{
     *    arrowClass: (?),
     *    buttonClass: (?),
     *    elementClasses: (?),
     *    handleDropdownStateSynced_: (?),
     *    handleItemClick_: (?),
     *    hiddenInputName: (?),
     *    items: (?),
     *    values: (?),
     *    selectedIndex: (?),
     *    label: (?soydata.SanitizedHtml|string|undefined)
     * }} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render(opt_data, opt_ignored, opt_ijData) {
      var $$temp;
      soy.asserts.assertType(opt_data.label == null || opt_data.label instanceof Function || opt_data.label instanceof soydata.UnsanitizedText || goog.isString(opt_data.label), 'label', opt_data.label, '?soydata.SanitizedHtml|string|undefined');
      var label = /** @type {?soydata.SanitizedHtml|string|undefined} */opt_data.label;
      ie_open('div', null, null, 'class', 'select' + (opt_data.elementClasses ? ' ' + opt_data.elementClasses : ''), 'data-onkeydown', 'handleKeyDown_');
      var currSelectedIndex__soy6 = opt_data.selectedIndex != null ? opt_data.selectedIndex : label || opt_data.items.length == 0 ? -1 : 0;
      ie_open('input', null, null, 'type', 'hidden', 'name', opt_data.hiddenInputName ? opt_data.hiddenInputName : '', 'value', currSelectedIndex__soy6 == -1 ? '' : opt_data.values ? opt_data.values[currSelectedIndex__soy6] : '');
      ie_close('input');
      var param12 = function param12() {
        var itemList21 = opt_data.items;
        var itemListLen21 = itemList21.length;
        for (var itemIndex21 = 0; itemIndex21 < itemListLen21; itemIndex21++) {
          var itemData21 = itemList21[itemIndex21];
          ie_open('li', null, null, 'data-onclick', ($$temp = opt_data.handleItemClick_) == null ? '' : $$temp, 'class', 'select-option' + (currSelectedIndex__soy6 == itemIndex21 ? ' selected' : ''));
          ie_open('a', null, null, 'href', 'javascript:;');
          $renderAsHtml_({ value: itemData21 }, null, opt_ijData);
          ie_close('a');
          ie_close('li');
        }
      };
      var param24 = function param24() {
        ie_open('button', null, null, 'class', (opt_data.buttonClass ? opt_data.buttonClass : '') + ' dropdown-select', 'type', 'button', 'data-onclick', 'toggle');
        if (currSelectedIndex__soy6 == -1) {
          if (label) {
            label();
          }
        } else {
          $renderAsHtml_({ value: opt_data.items[currSelectedIndex__soy6] }, null, opt_ijData);
        }
        itext(' ');
        ie_void('span', null, null, 'class', opt_data.arrowClass ? opt_data.arrowClass : 'caret');
        ie_close('button');
      };
      $templateAlias1({ body: param12, events: { stateSynced: opt_data.handleDropdownStateSynced_ }, header: param24, key: 'dropdown' }, null, opt_ijData);
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'Select.render';
    }

    /**
     * @param {{
     *    value: (!soydata.SanitizedHtml|string)
     * }} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $renderAsHtml_(opt_data, opt_ignored, opt_ijData) {
      soy.asserts.assertType(opt_data.value instanceof Function || opt_data.value instanceof soydata.UnsanitizedText || goog.isString(opt_data.value), 'value', opt_data.value, 'Function');
      var value = /** @type {Function} */opt_data.value;
      value();
    }
    exports.renderAsHtml_ = $renderAsHtml_;
    if (goog.DEBUG) {
      $renderAsHtml_.soyTemplateName = 'Select.renderAsHtml_';
    }

    exports.render.params = ["label", "arrowClass", "buttonClass", "elementClasses", "handleDropdownStateSynced_", "handleItemClick_", "hiddenInputName", "items", "values", "selectedIndex"];
    exports.renderAsHtml_.params = ["value"];
    exports.templates = templates = exports;
    return exports;
  });

  var Select = function (_Component) {
    _inherits(Select, _Component);

    function Select() {
      _classCallCheck(this, Select);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return Select;
  }(_Component3.default);

  _Soy2.default.register(Select, templates);
  exports.default = templates;
  exports.Select = Select;
  exports.templates = templates;
});
//# sourceMappingURL=Select.soy.js.map