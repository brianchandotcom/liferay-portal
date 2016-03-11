define("frontend-js-metal-web@1.0.0/metal-soy/src/soy", ['exports', './SoyAop', './SoyRenderer', './SoyTemplates'], function (exports, _SoyAop, _SoyRenderer, _SoyTemplates) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.SoyTemplates = exports.SoyRenderer = exports.SoyAop = undefined;

  var _SoyAop2 = _interopRequireDefault(_SoyAop);

  var _SoyRenderer2 = _interopRequireDefault(_SoyRenderer);

  var _SoyTemplates2 = _interopRequireDefault(_SoyTemplates);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  exports.default = _SoyRenderer2.default;
  exports.SoyAop = _SoyAop2.default;
  exports.SoyRenderer = _SoyRenderer2.default;
  exports.SoyTemplates = _SoyTemplates2.default;
});
//# sourceMappingURL=soy.js.map