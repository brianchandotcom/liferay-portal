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
    var ruleList263 = opt_data.rules;
    var ruleListLen263 = ruleList263.length;
    for (var ruleIndex263 = 0; ruleIndex263 < ruleListLen263; ruleIndex263++) {
      var ruleData263 = ruleList263[ruleIndex263];
      output += '<li class="list-group-item"><div class="clamp-horizontal list-group-item-content"><p class="form-builder-rule-builder-rule-description text-default"><b>' + soy.$$escapeHtml(opt_data.strings['if']) + ' </b>';
      var conditionList239 = ruleData263.conditions;
      var conditionListLen239 = conditionList239.length;
      for (var conditionIndex239 = 0; conditionIndex239 < conditionListLen239; conditionIndex239++) {
        var conditionData239 = conditionList239[conditionIndex239];
        output += ddl.condition({operandType: conditionData239.operands[0].type, operandValue: conditionData239.operands[0].label, strings: opt_data.strings}) + '<b class="text-lowercase"><em> ' + soy.$$escapeHtml(opt_data.strings[conditionData239.operator]) + ' </em></b>' + ((conditionData239.operands[1]) ? ddl.condition({operandType: conditionData239.operands[1].type, operandValue: conditionData239.operands[1].label != null ? conditionData239.operands[1].label : conditionData239.operands[1].value, strings: opt_data.strings}) : '') + ((! (conditionIndex239 == conditionListLen239 - 1)) ? '<br /><b> ' + soy.$$escapeHtml(opt_data.strings[ruleData263.logicOperator]) + ' </b>' : '');
      }
      output += '<br />';
      var actionList249 = ruleData263.actions;
      var actionListLen249 = actionList249.length;
      for (var actionIndex249 = 0; actionIndex249 < actionListLen249; actionIndex249++) {
        var actionData249 = actionList249[actionIndex249];
        output += ddl.action({action: actionData249}) + ((! (actionIndex249 == actionListLen249 - 1)) ? ', <br /><b> ' + soy.$$escapeHtml(opt_data.strings.and) + ' </b>' : '');
      }
      output += '</p></div><div class="list-group-item-field"><div class="card-col-field"><div class="dropdown"><ul class="dropdown-menu dropdown-menu-right"><li class="rule-card-edit" data-card-id="' + soy.$$escapeHtmlAttribute(ruleIndex263) + '"><a href="javascript:;">' + soy.$$escapeHtml(opt_data.strings.edit) + '</a></li><li class="rule-card-delete" data-card-id="' + soy.$$escapeHtmlAttribute(ruleIndex263) + '"><a href="javascript:;">' + soy.$$escapeHtml(opt_data.strings['delete']) + '</a></li></ul><a class="dropdown-toggle icon-monospaced" data-toggle="dropdown" href="#1">' + soy.$$filterNoAutoescape(opt_data.kebab) + '</a></div></div></div></li>';
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
  return '' + ((opt_data.operandType != 'user' && opt_data.operandType != 'list') ? '<span>' + soy.$$escapeHtml(opt_data.strings[opt_data.operandType]) + ' </span>' : '') + ddl.badge({content: opt_data.operandValue});
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
