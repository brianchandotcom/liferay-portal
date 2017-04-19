// This file was automatically generated from text_localizable.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_055aadf4 = function(opt_data, opt_ignored) {
  return '' + ddm.text_localizable(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_055aadf4.soyTemplateName = 'ddm.__deltemplate_s2_055aadf4';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'text_localizable', 0, ddm.__deltemplate_s2_055aadf4);


ddm.text_localizable = function(opt_data, opt_ignored) {
  var output = '';
  var displayValue__soy5 = opt_data.value ? opt_data.value : opt_data.predefinedValue ? opt_data.predefinedValue : '';
  output += '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-text-localizable ' + soy.$$escapeHtmlAttribute(opt_data.tip ? 'liferay-ddm-form-field-has-tip' : '') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') + '<div class="input-group-container ' + ((opt_data.tooltip) ? 'input-group-default' : '') + ' input-localized">' + ((opt_data.displayStyle == 'multiline') ? '<textarea aria-describedby="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_desc" class="field form-control language-value" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.placeholder) + '" ' + ((opt_data.readOnly) ? 'readonly' : '') + ' >' + soy.$$escapeHtmlRcdata(displayValue__soy5) + '</textarea>' : '<input aria-describedby="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_desc" class="field form-control language-value" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.placeholder) + '" ' + ((opt_data.readOnly) ? 'readonly' : '') + ' type="text" value="' + soy.$$escapeHtmlAttribute(displayValue__soy5) + '">') + '<div class="input-localized-content hidden" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + 'ContentBox" role="menu">' + ((opt_data.availableLocalesMetadata) ? ddm.available_locales(opt_data) : '') + '</div>' + ((opt_data.tooltip) ? '<span class="input-group-addon"><span class="input-group-addon-content"><a class="help-icon help-icon-default icon-monospaced icon-question" data-original-title="' + soy.$$escapeHtmlAttribute(opt_data.tooltip) + '" data-toggle="popover" href="javascript:;" title="' + soy.$$escapeHtmlAttribute(opt_data.tooltip) + '"></a></span></span>' : '') + '</div>' + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div>';
  return output;
};
if (goog.DEBUG) {
  ddm.text_localizable.soyTemplateName = 'ddm.text_localizable';
}


ddm.available_locales = function(opt_data, opt_ignored) {
  var output = '<div class="palette-container"><ul class="palette-items-container">';
  var localeMetadataList95 = opt_data.availableLocalesMetadata;
  var localeMetadataListLen95 = localeMetadataList95.length;
  for (var localeMetadataIndex95 = 0; localeMetadataIndex95 < localeMetadataListLen95; localeMetadataIndex95++) {
    var localeMetadataData95 = localeMetadataList95[localeMetadataIndex95];
    output += ddm.flag(soy.$$augmentMap(opt_data, {icon: localeMetadataData95.icon, index: localeMetadataIndex95, label: localeMetadataData95.label, languageId: localeMetadataData95.languageId}));
  }
  output += '</ul></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.available_locales.soyTemplateName = 'ddm.available_locales';
}


ddm.flag = function(opt_data, opt_ignored) {
  return '<li class="palette-item ' + ((opt_data.index == 0) ? 'palette-item-selected lfr-input-localized-default' : '') + '" data-index="' + soy.$$escapeHtmlAttribute(opt_data.index) + '" data-title="' + soy.$$escapeHtmlAttribute(opt_data.label) + '" data-value="' + soy.$$escapeHtmlAttribute(opt_data.languageId) + '" role="menuitem" style="display: inline-block;"><a class="palette-item-inner" data-languageid="' + soy.$$escapeHtmlAttribute(opt_data.languageId) + '" href="javascript:;"><span class="lfr-input-localized-flag"><svg class="lexicon-icon"><use xlink:href="' + soy.$$escapeHtmlAttribute(soy.$$filterNormalizeUri(opt_data.icon)) + '" /></svg></span><div class="lfr-input-localized-state"></div></a></li>';
};
if (goog.DEBUG) {
  ddm.flag.soyTemplateName = 'ddm.flag';
}
