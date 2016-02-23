define("frontend-js-metal-web@1.0.0/metal-autocomplete/src/Autocomplete.soy", ['exports', 'metal-component/src/all/component', 'metal-soy/src/soy'], function (exports, _component, _soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  var _component2 = _interopRequireDefault(_component);

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

  var Templates = _soy.SoyTemplates.get();
  // This file was automatically generated from Autocomplete.soy.
  // Please don't edit this file by hand.

  /**
   * @fileoverview Templates in namespace Templates.Autocomplete.
   */

  if (typeof Templates.Autocomplete == 'undefined') {
    Templates.Autocomplete = {};
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
  Templates.Autocomplete.render = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="autocomplete autocomplete-list component ' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '">' + soy.$$escapeHtml(Templates.List.render({ events: { itemSelected: opt_data.id + ':onListItemSelected_' }, id: opt_data.id + '-list' }, null, opt_ijData)) + '</div>');
  };
  if (goog.DEBUG) {
    Templates.Autocomplete.render.soyTemplateName = 'Templates.Autocomplete.render';
  }

  Templates.Autocomplete.render.params = ["id"];

  var Autocomplete = function (_Component) {
    _inherits(Autocomplete, _Component);

    function Autocomplete() {
      _classCallCheck(this, Autocomplete);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return Autocomplete;
  }(_component2.default);

  Autocomplete.prototype.registerMetalComponent && Autocomplete.prototype.registerMetalComponent(Autocomplete, 'Autocomplete')

  Autocomplete.RENDERER = _soy.SoyRenderer;
  _soy.SoyAop.registerTemplates('Autocomplete');
  exports.default = Autocomplete;
});
//# sourceMappingURL=Autocomplete.soy.js.map