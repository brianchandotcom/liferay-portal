// This file was automatically generated from text_localizable.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

goog.provide('ddm');

goog.require('soy');


/**
 * @param {Object<string, *>=} opt_data
 * @param {soy.IjData|Object<string, *>=} opt_ijData
 * @param {soy.IjData|Object<string, *>=} opt_ijData_deprecated
 * @return {string}
 * @suppress {checkTypes}
 */
ddm.__deltemplate__ddm_field_text_localizable = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = /** @type {!soy.IjData} */ (opt_ijData_deprecated || opt_ijData);
  opt_data = opt_data || {};
  return '' + (ddm.text_localizable(/** @type {?} */ (opt_data), opt_ijData));
};
if (goog.DEBUG) {
  ddm.__deltemplate__ddm_field_text_localizable.soyTemplateName = 'ddm.__deltemplate__ddm_field_text_localizable';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'text_localizable', 0, ddm.__deltemplate__ddm_field_text_localizable);


/**
 * @param {Object<string, *>=} opt_data
 * @param {soy.IjData|Object<string, *>=} opt_ijData
 * @param {soy.IjData|Object<string, *>=} opt_ijData_deprecated
 * @return {string}
 * @suppress {checkTypes}
 */
ddm.text_localizable = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = /** @type {!soy.IjData} */ (opt_ijData_deprecated || opt_ijData);
  var output = '';
  var displayValue__soy88 = opt_data.value ? opt_data.value : opt_data.predefinedValue ? opt_data.predefinedValue : '';
  output += '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-text-localizable ' + soy.$$escapeHtmlAttribute(opt_data.tip ? 'liferay-ddm-form-field-has-tip' : '') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"' + (goog.DEBUG && soy.$$debugSoyTemplateInfo ? ' data-debug-soy="ddm.text_localizable resources/com/liferay/portal/tools/soy/builder/commands/dependencies/build_soy/text_localizable.soy:31"' : '') + '><label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + (opt_data.required ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + (opt_data.tip ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') + '<div class="input-group-container ' + (opt_data.tooltip ? 'input-group-default' : '') + ' input-localized">' + (soy.$$equals(opt_data.displayStyle, 'multiline') ? '<textarea aria-describedby="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_desc" class="field form-control language-value" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.placeholder) + '"' + (opt_data.readOnly ? ' readonly' : '') + '>' + soy.$$escapeHtmlRcdata(displayValue__soy88) + '</textarea>' : '<input aria-describedby="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_desc" class="field form-control language-value" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.placeholder) + '"' + (opt_data.readOnly ? ' readonly' : '') + ' type="text" value="' + soy.$$escapeHtmlAttribute(displayValue__soy88) + '">') + '<div class="input-localized-content hidden" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + 'ContentBox" role="menu">' + (opt_data.availableLocalesMetadata ? ddm.available_locales(soy.$$assignDefaults({availableLocalesMetadata: opt_data.availableLocalesMetadata}, opt_data), opt_ijData) : '') + '</div>' + (opt_data.tooltip ? '<span class="input-group-addon"><span class="input-group-addon-content"><a class="help-icon help-icon-default icon-monospaced icon-question" data-original-title="' + soy.$$escapeHtmlAttribute(opt_data.tooltip) + '" href="javascript:void(0);" title="' + soy.$$escapeHtmlAttribute(opt_data.tooltip) + '"></a></span></span>' : '') + '</div>' + (opt_data.childElementsHTML ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div>';
  return output;
};
if (goog.DEBUG) {
  ddm.text_localizable.soyTemplateName = 'ddm.text_localizable';
}


/**
 * @param {Object<string, *>=} opt_data
 * @param {soy.IjData|Object<string, *>=} opt_ijData
 * @param {soy.IjData|Object<string, *>=} opt_ijData_deprecated
 * @return {string}
 * @suppress {checkTypes}
 */
ddm.available_locales = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = /** @type {!soy.IjData} */ (opt_ijData_deprecated || opt_ijData);
  var output = '<div class="palette-container"' + (goog.DEBUG && soy.$$debugSoyTemplateInfo ? ' data-debug-soy="ddm.available_locales resources/com/liferay/portal/tools/soy/builder/commands/dependencies/build_soy/text_localizable.soy:82"' : '') + '><ul class="palette-items-container">';
  var localeMetadata179List = opt_data.availableLocalesMetadata;
  var localeMetadata179ListLen = localeMetadata179List.length;
  for (var localeMetadata179Index = 0; localeMetadata179Index < localeMetadata179ListLen; localeMetadata179Index++) {
    var localeMetadata179Data = localeMetadata179List[localeMetadata179Index];
    output += ddm.flag(soy.$$assignDefaults({icon: localeMetadata179Data.icon, index: localeMetadata179Index, label: localeMetadata179Data.label, languageId: localeMetadata179Data.languageId}, opt_data), opt_ijData);
  }
  output += '</ul></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.available_locales.soyTemplateName = 'ddm.available_locales';
}


/**
 * @param {Object<string, *>=} opt_data
 * @param {soy.IjData|Object<string, *>=} opt_ijData
 * @param {soy.IjData|Object<string, *>=} opt_ijData_deprecated
 * @return {string}
 * @suppress {checkTypes}
 */
ddm.flag = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = /** @type {!soy.IjData} */ (opt_ijData_deprecated || opt_ijData);
  return '<li class="palette-item ' + (soy.$$equals(opt_data.index, 0) ? 'palette-item-selected lfr-input-localized-default' : '') + '" data-index="' + soy.$$escapeHtmlAttribute(opt_data.index) + '" data-title="' + soy.$$escapeHtmlAttribute(opt_data.label) + '" data-value="' + soy.$$escapeHtmlAttribute(opt_data.languageId) + '" role="menuitem" style="display: inline-block;"' + (goog.DEBUG && soy.$$debugSoyTemplateInfo ? ' data-debug-soy="ddm.flag resources/com/liferay/portal/tools/soy/builder/commands/dependencies/build_soy/text_localizable.soy:105"' : '') + '><a class="palette-item-inner" data-languageid="' + soy.$$escapeHtmlAttribute(opt_data.languageId) + '" href="javascript:void(0);"><span class="lfr-input-localized-flag"><svg class="lexicon-icon"><use xlink:href="' + soy.$$escapeHtmlAttribute(soy.$$filterNormalizeUri(opt_data.icon)) + '"/></svg></span><div class="lfr-input-localized-state"></div></a></li>';
};
if (goog.DEBUG) {
  ddm.flag.soyTemplateName = 'ddm.flag';
}
