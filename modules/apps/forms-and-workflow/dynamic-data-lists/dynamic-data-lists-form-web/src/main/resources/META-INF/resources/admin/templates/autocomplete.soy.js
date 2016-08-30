// This file was automatically generated from autocomplete.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddl.autocomplete.
 * @public
 */

if (typeof ddl == 'undefined') { var ddl = {}; }
if (typeof ddl.autocomplete == 'undefined') { ddl.autocomplete = {}; }


ddl.autocomplete.button = function(opt_data, opt_ignored) {
  return '<button class="autocomplete-button btn btn-block btn-primary" type="button">Autocomplete</button>';
};
if (goog.DEBUG) {
  ddl.autocomplete.button.soyTemplateName = 'ddl.autocomplete.button';
}


ddl.autocomplete.container = function(opt_data, opt_ignored) {
  return '<div class="autocomplete-container"><header class="header-toolbar"><div class="toolbar-group"><div class="toolbar-group-content"><a class="autocomplete-header-back" href="javascript:;">' + soy.$$filterNoAutoescape(opt_data.backButton) + '</a></div></div><div class="toolbar-group-expand-text"><span title="Autocomplete">Autocomplete</span></div></header><div class="autocomplete-body"></div></div>';
};
if (goog.DEBUG) {
  ddl.autocomplete.container.soyTemplateName = 'ddl.autocomplete.container';
}
