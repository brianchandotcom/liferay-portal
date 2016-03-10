define("frontend-js-metal-web@1.0.0/metal-autocomplete/src/all/autocomplete", ['exports', '../Autocomplete', '../AutocompleteBase', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _Autocomplete, _AutocompleteBase, _JQueryAdapter) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.AutocompleteBase = exports.Autocomplete = undefined;

  var _Autocomplete2 = _interopRequireDefault(_Autocomplete);

  var _AutocompleteBase2 = _interopRequireDefault(_AutocompleteBase);

  var _JQueryAdapter2 = _interopRequireDefault(_JQueryAdapter);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  exports.default = _Autocomplete2.default;
  exports.Autocomplete = _Autocomplete2.default;
  exports.AutocompleteBase = _AutocompleteBase2.default;
  _JQueryAdapter2.default.register('autocomplete', autocomplete);
});
//# sourceMappingURL=autocomplete.js.map