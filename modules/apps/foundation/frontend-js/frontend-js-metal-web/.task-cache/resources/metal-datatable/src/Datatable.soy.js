define("frontend-js-metal-web@1.0.6/metal-datatable/src/Datatable.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.Datatable = undefined;

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

    // This file was automatically generated from Datatable.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace Datatable.
     * @public
     */

    goog.module('Datatable.incrementaldom');

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
      ie_open('div', null, null, 'class', 'datatable' + (opt_data.elementClasses ? ' ' + opt_data.elementClasses : ''));
      $render_(soy.$$augmentMap(opt_data.data, { displayColumnsType: opt_data.displayColumnsType, tableClasses: opt_data.tableClasses }), null, opt_ijData);
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'Datatable.render';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render_(opt_data, opt_ignored, opt_ijData) {
      var $$temp;
      switch (goog.isObject($$temp = opt_data.type) ? $$temp.toString() : $$temp) {
        case 'array':
          if (opt_data.columns) {
            $renderArrayOfObjects_(opt_data, null, opt_ijData);
          } else {
            $renderArray_(opt_data, null, opt_ijData);
          }
          break;
        case 'boolean':
          $renderBoolean_(opt_data, null, opt_ijData);
          break;
        case 'null':
          $renderNull_(opt_data, null, opt_ijData);
          break;
        case 'number':
          $renderNumber_(opt_data, null, opt_ijData);
          break;
        case 'object':
          $renderObject_(opt_data, null, opt_ijData);
          break;
        case 'string':
          $renderString_(opt_data, null, opt_ijData);
          break;
        case 'undefined':
          $renderUndefined_(opt_data, null, opt_ijData);
          break;
      }
    }
    exports.render_ = $render_;
    if (goog.DEBUG) {
      $render_.soyTemplateName = 'Datatable.render_';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $renderArray_(opt_data, opt_ignored, opt_ijData) {
      ie_open('span', null, null, 'class', 'datatable-array');
      ie_open('span', null, null, 'class', 'datatable-label collapsed', 'data-onclick', 'toggleTableContents');
      itext('Array, ');
      itext((goog.asserts.assert(opt_data.value.length != null), opt_data.value.length));
      itext(' items');
      ie_close('span');
      ie_open('table', null, null, 'class', (opt_data.tableClasses ? opt_data.tableClasses + '' : '') + ' hidden');
      ie_open('tbody');
      var itemValueList41 = opt_data.value;
      var itemValueListLen41 = itemValueList41.length;
      for (var itemValueIndex41 = 0; itemValueIndex41 < itemValueListLen41; itemValueIndex41++) {
        var itemValueData41 = itemValueList41[itemValueIndex41];
        ie_open('tr');
        ie_open('td');
        $render_(soy.$$augmentMap(itemValueData41, { tableClasses: opt_data.tableClasses, displayColumnsType: opt_data.displayColumnsType }), null, opt_ijData);
        ie_close('td');
        ie_close('tr');
      }
      ie_close('tbody');
      ie_close('table');
      ie_close('span');
    }
    exports.renderArray_ = $renderArray_;
    if (goog.DEBUG) {
      $renderArray_.soyTemplateName = 'Datatable.renderArray_';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $renderArrayOfObjects_(opt_data, opt_ignored, opt_ijData) {
      ie_open('span', null, null, 'class', 'datatable-array-object');
      ie_open('table', null, null, 'class', opt_data.tableClasses ? opt_data.tableClasses : '');
      ie_open('thead');
      ie_open('tr');
      var columnList56 = opt_data.columns;
      var columnListLen56 = columnList56.length;
      for (var columnIndex56 = 0; columnIndex56 < columnListLen56; columnIndex56++) {
        var columnData56 = columnList56[columnIndex56];
        ie_open('th');
        itext((goog.asserts.assert(columnData56 != null), columnData56));
        if (opt_data.displayColumnsType && opt_data.columnsType) {
          ie_open('span', null, null, 'class', 'datatable-type');
          itext((goog.asserts.assert(opt_data.columnsType[columnData56] != null), opt_data.columnsType[columnData56]));
          ie_close('span');
        }
        ie_close('th');
      }
      ie_close('tr');
      ie_close('thead');
      ie_open('tbody');
      var itemValueList68 = opt_data.value;
      var itemValueListLen68 = itemValueList68.length;
      for (var itemValueIndex68 = 0; itemValueIndex68 < itemValueListLen68; itemValueIndex68++) {
        var itemValueData68 = itemValueList68[itemValueIndex68];
        ie_open('tr');
        var columnList65 = opt_data.columns;
        var columnListLen65 = columnList65.length;
        for (var columnIndex65 = 0; columnIndex65 < columnListLen65; columnIndex65++) {
          var columnData65 = columnList65[columnIndex65];
          ie_open('td');
          $render_(soy.$$augmentMap(itemValueData68.value[columnData65], { displayColumnsType: opt_data.displayColumnsType, tableClasses: opt_data.tableClasses }), null, opt_ijData);
          ie_close('td');
        }
        ie_close('tr');
      }
      ie_close('tbody');
      ie_close('table');
      ie_close('span');
    }
    exports.renderArrayOfObjects_ = $renderArrayOfObjects_;
    if (goog.DEBUG) {
      $renderArrayOfObjects_.soyTemplateName = 'Datatable.renderArrayOfObjects_';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $renderBoolean_(opt_data, opt_ignored, opt_ijData) {
      ie_open('span', null, null, 'class', 'datatable-boolean');
      itext((goog.asserts.assert(opt_data.value != null), opt_data.value));
      ie_close('span');
    }
    exports.renderBoolean_ = $renderBoolean_;
    if (goog.DEBUG) {
      $renderBoolean_.soyTemplateName = 'Datatable.renderBoolean_';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $renderNull_(opt_data, opt_ignored, opt_ijData) {
      ie_open('span', null, null, 'class', 'datatable-null');
      itext('null');
      ie_close('span');
    }
    exports.renderNull_ = $renderNull_;
    if (goog.DEBUG) {
      $renderNull_.soyTemplateName = 'Datatable.renderNull_';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $renderNumber_(opt_data, opt_ignored, opt_ijData) {
      ie_open('span', null, null, 'class', 'datatable-number');
      itext((goog.asserts.assert(opt_data.value != null), opt_data.value));
      ie_close('span');
    }
    exports.renderNumber_ = $renderNumber_;
    if (goog.DEBUG) {
      $renderNumber_.soyTemplateName = 'Datatable.renderNumber_';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $renderObject_(opt_data, opt_ignored, opt_ijData) {
      ie_open('span', null, null, 'class', 'datatable-object');
      ie_open('span', null, null, 'class', 'datatable-label collapsed', 'data-onclick', 'toggleTableContents');
      itext('Object, ');
      itext((goog.asserts.assert(soy.$$getMapKeys(opt_data.value).length != null), soy.$$getMapKeys(opt_data.value).length));
      itext(' items');
      ie_close('span');
      ie_open('table', null, null, 'class', (opt_data.tableClasses ? opt_data.tableClasses : '') + ' hidden');
      ie_open('thead');
      ie_open('tr');
      var columnList95 = opt_data.columns;
      var columnListLen95 = columnList95.length;
      for (var columnIndex95 = 0; columnIndex95 < columnListLen95; columnIndex95++) {
        var columnData95 = columnList95[columnIndex95];
        ie_open('th');
        itext((goog.asserts.assert(columnData95 != null), columnData95));
        if (opt_data.displayColumnsType && opt_data.columnsType) {
          ie_open('span', null, null, 'class', 'datatable-type');
          itext((goog.asserts.assert(opt_data.columnsType[columnData95] != null), opt_data.columnsType[columnData95]));
          ie_close('span');
        }
        ie_close('th');
      }
      ie_close('tr');
      ie_close('thead');
      ie_open('tbody');
      ie_open('tr');
      var columnList103 = opt_data.columns;
      var columnListLen103 = columnList103.length;
      for (var columnIndex103 = 0; columnIndex103 < columnListLen103; columnIndex103++) {
        var columnData103 = columnList103[columnIndex103];
        ie_open('td');
        $render_(soy.$$augmentMap(opt_data.value[columnData103], { displayColumnsType: opt_data.displayColumnsType, tableClasses: opt_data.tableClasses }), null, opt_ijData);
        ie_close('td');
      }
      ie_close('tr');
      ie_close('tbody');
      ie_close('table');
      ie_close('span');
    }
    exports.renderObject_ = $renderObject_;
    if (goog.DEBUG) {
      $renderObject_.soyTemplateName = 'Datatable.renderObject_';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $renderUndefined_(opt_data, opt_ignored, opt_ijData) {
      ie_open('span', null, null, 'class', 'datatable-undefined');
      itext('undefined');
      ie_close('span');
    }
    exports.renderUndefined_ = $renderUndefined_;
    if (goog.DEBUG) {
      $renderUndefined_.soyTemplateName = 'Datatable.renderUndefined_';
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
    function $renderString_(opt_data, opt_ignored, opt_ijData) {
      soy.asserts.assertType(opt_data.value instanceof Function || opt_data.value instanceof soydata.UnsanitizedText || goog.isString(opt_data.value), 'value', opt_data.value, 'Function');
      var value = /** @type {Function} */opt_data.value;
      ie_open('span', null, null, 'class', 'datatable-string');
      value();
      ie_close('span');
    }
    exports.renderString_ = $renderString_;
    if (goog.DEBUG) {
      $renderString_.soyTemplateName = 'Datatable.renderString_';
    }

    exports.render.params = ["data", "displayColumnsType", "elementClasses", "tableClasses"];
    exports.render_.params = ["type", "columns"];
    exports.renderArray_.params = ["value", "displayColumnsType", "tableClasses"];
    exports.renderArrayOfObjects_.params = ["columns", "value", "columnsType", "displayColumnsType", "tableClasses"];
    exports.renderBoolean_.params = ["value"];
    exports.renderNull_.params = [];
    exports.renderNumber_.params = ["value"];
    exports.renderObject_.params = ["columns", "value", "columnsType", "displayColumnsType", "tableClasses"];
    exports.renderUndefined_.params = [];
    exports.renderString_.params = ["value"];
    exports.templates = templates = exports;
    return exports;
  });

  var Datatable = function (_Component) {
    _inherits(Datatable, _Component);

    function Datatable() {
      _classCallCheck(this, Datatable);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return Datatable;
  }(_Component3.default);

  _Soy2.default.register(Datatable, templates);
  exports.default = templates;
  exports.Datatable = Datatable;
  exports.templates = templates;
});
//# sourceMappingURL=Datatable.soy.js.map