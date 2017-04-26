// This file was automatically generated from rule_builder.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddl.
 * @public
 */

if (typeof ddl == 'undefined') { var ddl = {}; }


ddl.rule_builder = function(opt_data, opt_ignored) {
  return '<div class="form-builder-rule-builder-container"><h1 class="form-builder-section-title text-default">' + soy.$$escapeHtml(opt_data.strings.ruleBuilder) + '</h1><div class="liferay-ddl-form-rule-rules-list-container"></div><div class="form-builder-rule-builder-add-rule-container"><div class="btn-action-secondary btn-bottom-right dropdown form-builder-rule-builder-add-rule-button"><button class="btn btn-primary form-builder-rule-builder-add-rule-button-icon" type="button">' + soy.$$filterNoAutoescape(opt_data.plusIcon) + '</button></div></div></div>';
};
if (goog.DEBUG) {
  ddl.rule_builder.soyTemplateName = 'ddl.rule_builder';
}


ddl.rule_list = function(opt_data, opt_ignored) {
  var output = '';
  if (opt_data.rules.length > 0) {
    output += '<ul class="ddl-form-body-content form-builder-rule-builder-rules-list tabular-list-group">';
    var ruleList227 = opt_data.rules;
    var ruleListLen227 = ruleList227.length;
    for (var ruleIndex227 = 0; ruleIndex227 < ruleListLen227; ruleIndex227++) {
      var ruleData227 = ruleList227[ruleIndex227];
      output += '<li class="list-group-item"><div class="clamp-horizontal list-group-item-content"><p class="form-builder-rule-builder-rule-description text-default"><b>If </b>';
      var conditionList205 = ruleData227.conditions;
      var conditionListLen205 = conditionList205.length;
      for (var conditionIndex205 = 0; conditionIndex205 < conditionListLen205; conditionIndex205++) {
        var conditionData205 = conditionList205[conditionIndex205];
        output += ddl.condition({operandType: conditionData205.operands[0].type, operandValue: conditionData205.operands[0].label}) + '<b class="text-lowercase"><em> ' + soy.$$escapeHtml(opt_data.strings[conditionData205.operator]) + ' </em></b>' + ((conditionData205.operands[1]) ? ddl.condition({operandType: conditionData205.operands[1].type, operandValue: conditionData205.operands[1].label != null ? conditionData205.operands[1].label : conditionData205.operands[1].value}) : '') + ((! (conditionIndex205 == conditionListLen205 - 1)) ? '<br /><b> ' + soy.$$escapeHtml(ruleData227.logicOperator) + ' </b>' : '');
      }
      output += '<br />';
      var actionList213 = ruleData227.actions;
      var actionListLen213 = actionList213.length;
      for (var actionIndex213 = 0; actionIndex213 < actionListLen213; actionIndex213++) {
        var actionData213 = actionList213[actionIndex213];
        output += ddl.action({action: actionData213}) + ((! (actionIndex213 == actionListLen213 - 1)) ? ', <br /><b> and </b>' : '');
      }
      output += '</p></div><div class="list-group-item-field"><div class="card-col-field"><div class="dropdown"><a class="dropdown-toggle icon-monospaced" data-toggle="dropdown" href="#1">' + soy.$$filterNoAutoescape(opt_data.kebab) + '</a><ul class="dropdown-menu dropdown-menu-right"><li class="rule-card-edit" data-card-id="' + soy.$$escapeHtmlAttribute(ruleIndex227) + '"><a href="javascript:;">' + soy.$$escapeHtml(opt_data.strings.edit) + '</a></li><li class="rule-card-delete" data-card-id="' + soy.$$escapeHtmlAttribute(ruleIndex227) + '"><a href="javascript:;">' + soy.$$escapeHtml(opt_data.strings['delete']) + '</a></li></ul></div></div></div></li>';
    }
    output += '</ul>';
  } else {
    output += ddl.empty_list({message: opt_data.strings.emptyListText});
  }
  return output;
};
if (goog.DEBUG) {
  ddl.rule_list.soyTemplateName = 'ddl.rule_list';
}


ddl.empty_list = function(opt_data, opt_ignored) {
  opt_data = opt_data || {};
  return '<div class="main-content-body"><div class="card main-content-card taglib-empty-result-message"><div class="card-row card-row-padded"><div class="taglib-empty-result-message-header-has-plus-btn"></div>' + ((opt_data.message) ? '<div class="text-center text-muted"><p class="text-default">' + soy.$$escapeHtml(opt_data.message) + '</p></div>' : '') + '</div></div></div>';
};
if (goog.DEBUG) {
  ddl.empty_list.soyTemplateName = 'ddl.empty_list';
}


ddl.rule_types = function(opt_data, opt_ignored) {
  return '<ul class="dropdown-menu"><li><a data-rule-type="visibility" href="javascript:;">' + soy.$$escapeHtml(opt_data.strings.showHide) + '</a><a data-rule-type="readonly" href="javascript:;">' + soy.$$escapeHtml(opt_data.strings.enableDisable) + '</a><a data-rule-type="require" href="javascript:;">' + soy.$$escapeHtml(opt_data.strings.require) + '</a></li></ul>';
};
if (goog.DEBUG) {
  ddl.rule_types.soyTemplateName = 'ddl.rule_types';
}


ddl.badge = function(opt_data, opt_ignored) {
  opt_data = opt_data || {};
  return '<span class="badge badge-default badge-sm">' + soy.$$escapeHtml(opt_data.content) + '</span>';
};
if (goog.DEBUG) {
  ddl.badge.soyTemplateName = 'ddl.badge';
}


ddl.condition = function(opt_data, opt_ignored) {
  return '' + ((opt_data.operandType != 'user' && opt_data.operandType != 'list') ? '<span>' + soy.$$escapeHtml(opt_data.operandType) + ' </span>' : '') + ddl.badge({content: opt_data.operandValue});
};
if (goog.DEBUG) {
  ddl.condition.soyTemplateName = 'ddl.condition';
}


ddl.action = function(opt_data, opt_ignored) {
  return '<b>' + soy.$$filterNoAutoescape(opt_data.action) + '</b>';
};
if (goog.DEBUG) {
  ddl.action.soyTemplateName = 'ddl.action';
}
