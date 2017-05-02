// This file was automatically generated from form_entry_add_body.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace form.
 * @public
 */

if (typeof form == 'undefined') { var form = {}; }


form.field = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml((opt_data.field != null) ? '<p style="color: #9aa2a6; font-size: 18px; margin: 32px 0 16px 0;">' + soy.$$escapeHtml(opt_data.field.label) + '</p><p style="font-size: 16px; margin: 0;">' + soy.$$escapeHtml(opt_data.field.value) + '</p>' : '');
};
if (goog.DEBUG) {
  form.field.soyTemplateName = 'form.field';
}


form.fields = function(opt_data, opt_ignored) {
  var output = '';
  var fieldList332 = opt_data.fields;
  var fieldListLen332 = fieldList332.length;
  for (var fieldIndex332 = 0; fieldIndex332 < fieldListLen332; fieldIndex332++) {
    var fieldData332 = fieldList332[fieldIndex332];
    output += form.field(soy.$$augmentMap(opt_data, {field: fieldData332}));
  }
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
};
if (goog.DEBUG) {
  form.fields.soyTemplateName = 'form.fields';
}


form.form_entry = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<!DOCTYPE html><html><head><meta charset="UTF-8" /><title>' + soy.$$escapeHtmlRcdata(opt_data.formName) + '</title></head><body style="margin: 0; overflow: visible; padding: 0;"><div id="container" style="font-family: helvetica, \'open sans\', arial; margin: 0 auto 40px auto; width: 660px;"><table style="background-color: #e4e9ec; padding: 40px;"><tr><td><div id="introduction" style="background-color: #fff; border-radius: 4px; margin: 0 auto 24px auto; padding: 40px;"><h1 align="center" style="margin-bottom: 24px; margin-top: 0;">' + soy.$$escapeHtml(opt_data.siteName) + '</h1><h2 align="center" style="margin-bottom: 24px; margin-top: 0;">Hi, ' + soy.$$escapeHtml(opt_data.authorName) + '</h2><p style="line-height: 30px; margin-bottom: 24px; margin-top: 0;"><b>' + soy.$$escapeHtml(opt_data.userName) + '</b> submitted an entry for the ' + soy.$$escapeHtml(opt_data.formName) + ' form in the ' + soy.$$escapeHtml(opt_data.siteName) + ' site. View all the form\'s entries by clicking <a href="' + soy.$$escapeHtmlAttribute(soy.$$filterNormalizeUri(opt_data.viewFormEntriesURL)) + '" style="color: #7bc4f4; text-decoration: none;" target="_blank">here</a>.</p><a href="' + soy.$$escapeHtmlAttribute(soy.$$filterNormalizeUri(opt_data.viewFormURL)) + '" style="background: #65b4f1; border-radius: 4px; color: #fff; display: block; padding: 18px; text-align: center; text-decoration: none;" target="_blank">Click here to access the form</a></div></td></tr><tr><td><h3 style="color: #9aa2a6; font-weight: 300; margin: 8px 0 8px; text-align: center;">Here\'s what <b>' + soy.$$escapeHtml(opt_data.userName) + '</b> entered into the form:</h3>' + form.pages(opt_data) + '<a href="' + soy.$$escapeHtmlAttribute(soy.$$filterNormalizeUri(opt_data.viewFormURL)) + '" style="background: #65b4f1; border-radius: 4px; color: #fff; display: block; padding: 18px; text-align: center; text-decoration: none;" target="_blank">Click here to access the form</a></td></tr></table></div></body></html>');
};
if (goog.DEBUG) {
  form.form_entry.soyTemplateName = 'form.form_entry';
}


form.pages = function(opt_data, opt_ignored) {
  var output = '';
  var pageList366 = opt_data.pages;
  var pageListLen366 = pageList366.length;
  for (var pageIndex366 = 0; pageIndex366 < pageListLen366; pageIndex366++) {
    var pageData366 = pageList366[pageIndex366];
    output += '<div style="background-color: #fff; border-radius: 4px; margin: 0 auto 24px auto; padding: 40px;"><h4 style="color: #9aa2a6; font-size: 21px; font-weight: 500; margin: 0;">' + soy.$$escapeHtml(pageData366.title) + '</h4>' + form.fields(soy.$$augmentMap(opt_data, {fields: pageData366.fields})) + '</div>';
  }
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
};
if (goog.DEBUG) {
  form.pages.soyTemplateName = 'form.pages';
}
