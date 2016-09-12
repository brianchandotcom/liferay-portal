// This file was automatically generated from autocomplete.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddl.autocomplete.
 * @public
 */

if (typeof ddl == 'undefined') { var ddl = {}; }
if (typeof ddl.autocomplete == 'undefined') { ddl.autocomplete = {}; }


ddl.autocomplete.actionPanel = function(opt_data, opt_ignored) {
  return '<div class="row"><div class="col-md-12"><div class="autocomplete-action-panel cursor-pointer panel panel-default"><div class="panel-body">' + soy.$$escapeHtml(opt_data.label) + '<span class="pull-right">' + soy.$$filterNoAutoescape(opt_data.addAutoCompleteButton) + '</span></div></div></div></div>';
};
if (goog.DEBUG) {
  ddl.autocomplete.actionPanel.soyTemplateName = 'ddl.autocomplete.actionPanel';
}


ddl.autocomplete.container = function(opt_data, opt_ignored) {
  return '<div class="autocomplete-container"><header class="header-toolbar"><div class="toolbar-group"><div class="toolbar-group-content"><a class="autocomplete-header-back" href="javascript:;">' + soy.$$filterNoAutoescape(opt_data.backButton) + '</a></div></div><div class="toolbar-group-expand-text"><span title="Autocomplete">' + soy.$$escapeHtml(opt_data.label) + '</span></div></header><div class="autocomplete-body"></div></div>';
};
if (goog.DEBUG) {
  ddl.autocomplete.container.soyTemplateName = 'ddl.autocomplete.container';
}
