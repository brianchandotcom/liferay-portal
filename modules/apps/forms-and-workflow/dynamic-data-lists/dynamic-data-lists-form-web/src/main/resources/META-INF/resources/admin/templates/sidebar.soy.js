// This file was automatically generated from sidebar.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddl.sidebar.
 * @public
 */

if (typeof ddl == 'undefined') { var ddl = {}; }
if (typeof ddl.sidebar == 'undefined') { ddl.sidebar = {}; }


ddl.sidebar.render = function(opt_data, opt_ignored) {
  return '' + ddl.sidebar.header(opt_data) + ddl.sidebar.body(opt_data);
};
if (goog.DEBUG) {
  ddl.sidebar.render.soyTemplateName = 'ddl.sidebar.render';
}


ddl.sidebar.body = function(opt_data, opt_ignored) {
  opt_data = opt_data || {};
  return '<div class="sidebar-body">' + soy.$$filterNoAutoescape(opt_data.bodyContent) + '</div>';
};
if (goog.DEBUG) {
  ddl.sidebar.body.soyTemplateName = 'ddl.sidebar.body';
}


ddl.sidebar.field_options_toolbar = function(opt_data, opt_ignored) {
  return '' + ddl.field_settings_toolbar(opt_data);
};
if (goog.DEBUG) {
  ddl.sidebar.field_options_toolbar.soyTemplateName = 'ddl.sidebar.field_options_toolbar';
}


ddl.sidebar.header = function(opt_data, opt_ignored) {
  return '<div class="sidebar-header"><ul class="sidebar-header-actions">' + ((opt_data.toolbarTemplateContext) ? '<li>' + ddl.sidebar.field_options_toolbar(opt_data) + '</li>' : '') + '<li><a class="form-builder-sidebar-close" href="javascript:;">' + soy.$$filterNoAutoescape(opt_data.closeButtonIcon) + '</a></li></ul><h4 class="form-builder-sidebar-title truncate-text" title="' + soy.$$escapeHtmlAttribute(opt_data.title) + '">' + soy.$$escapeHtml(opt_data.title || '') + '</h4><h5 class="form-builder-sidebar-description" title="' + soy.$$escapeHtmlAttribute(opt_data.description) + '">' + soy.$$escapeHtml(opt_data.description || '') + '</h5></div>';
};
if (goog.DEBUG) {
  ddl.sidebar.header.soyTemplateName = 'ddl.sidebar.header';
}
