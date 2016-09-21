// This file was automatically generated from rule.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddl.rule.
 * @public
 */

if (typeof ddl == 'undefined') { var ddl = {}; }
if (typeof ddl.rule == 'undefined') { ddl.rule = {}; }


ddl.rule.settings = function(opt_data, opt_ignored) {
  var output = '<div class="top-informations"><h2 class="text-default">' + soy.$$escapeHtml(opt_data.strings.title) + '</h2><h4 class="text-default">' + soy.$$escapeHtml(opt_data.strings.description) + '</h4></div><ul class="liferay-ddl-form-rule-builder-timeline liferay-ddl-form-builder-rule-condition-list timeline">' + ddl.rule.rulesHeader({logicalOperator: opt_data.logicalOperator, title: 'Condition'});
  var conditionList60 = opt_data.conditions;
  var conditionListLen60 = conditionList60.length;
  if (conditionListLen60 > 0) {
    for (var conditionIndex60 = 0; conditionIndex60 < conditionListLen60; conditionIndex60++) {
      var conditionData60 = conditionList60[conditionIndex60];
      output += ddl.rule.condition({index: conditionIndex60, deleteIcon: opt_data.deleteIcon, logicOperator: opt_data.logicalOperator});
    }
  } else {
    output += ddl.rule.condition({index: 0, deleteIcon: opt_data.deleteIcon, logicOperator: opt_data.logicalOperator});
  }
  output += '</ul>' + ddl.rule.btnAddNewTimelineItem({plusIcon: opt_data.plusIcon, cssClass: 'form-builder-rule-add-condition'}) + '<ul class="action-list liferay-ddl-form-rule-builder-timeline liferay-ddl-form-builder-rule-action-list timeline">' + ddl.rule.rulesHeader({logicalOperator: opt_data.logicalOperator, title: 'Actions'});
  var actionList77 = opt_data.actions;
  var actionListLen77 = actionList77.length;
  if (actionListLen77 > 0) {
    for (var actionIndex77 = 0; actionIndex77 < actionListLen77; actionIndex77++) {
      var actionData77 = actionList77[actionIndex77];
      output += ddl.rule.action({index: actionIndex77, deleteIcon: opt_data.deleteIcon});
    }
  } else {
    output += ddl.rule.action({index: 0, deleteIcon: opt_data.deleteIcon});
  }
  output += '</ul>' + ddl.rule.btnAddNewTimelineItem({plusIcon: opt_data.plusIcon, cssClass: 'form-builder-rule-add-action'}) + '<div class="liferay-ddl-form-rule-builder-footer"><button class="btn btn-lg ddl-button btn-primary btn-default form-builder-rule-settings-save" type="button"><span class="form-builder-rule-settings-save-label">' + soy.$$escapeHtml(opt_data.strings.save) + '</span></button><button type="button" class="btn btn-lg btn-cancel btn-default btn-link form-builder-rule-settings-cancel"><span class="lfr-btn-label">' + soy.$$escapeHtml(opt_data.strings.cancel) + '</span></button></div>';
  return output;
};
if (goog.DEBUG) {
  ddl.rule.settings.soyTemplateName = 'ddl.rule.settings';
}


ddl.rule.condition = function(opt_data, opt_ignored) {
  return '<li class="form-builder-rule-condition-container-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' timeline-item"><div class="panel panel-default"><div class="panel-body flex-container"><h4>If</h4><div class="form-group condition-if-' + soy.$$escapeHtmlAttribute(opt_data.index) + '"></div><div class="form-group condition-operator-' + soy.$$escapeHtmlAttribute(opt_data.index) + '"></div><div class="form-group condition-the-' + soy.$$escapeHtmlAttribute(opt_data.index) + '"></div><div class="form-group condition-type-value-' + soy.$$escapeHtmlAttribute(opt_data.index) + '"></div><div class="form-group condition-type-value-options-' + soy.$$escapeHtmlAttribute(opt_data.index) + '"></div><div class="timeline-increment-icon"><span class="timeline-icon"></span></div></div></div><div class="operator panel panel-default panel-inline"><div class="panel-body text-uppercase">' + soy.$$escapeHtml(opt_data.logicOperator) + '</div></div><div class="container__trash"><button class="btn btn-link condition-card-delete icon-monospaced" data-card-id="' + soy.$$escapeHtmlAttribute(opt_data.index) + '" href="javascript:;" type="button">' + soy.$$filterNoAutoescape(opt_data.deleteIcon) + '</button></div></li>';
};
if (goog.DEBUG) {
  ddl.rule.condition.soyTemplateName = 'ddl.rule.condition';
}


ddl.rule.btnAddNewTimelineItem = function(opt_data, opt_ignored) {
  return '<div class="addbutton-timeline-item"><div class="timeline-increment-icon add-condition"><a href="javascript:;" aria-role="button" class="btn btn-xs btn-primary form-builder-timeline-add-item ' + soy.$$escapeHtmlAttribute(opt_data.cssClass || '') + '">' + soy.$$filterNoAutoescape(opt_data.plusIcon) + '</a></div></div>';
};
if (goog.DEBUG) {
  ddl.rule.btnAddNewTimelineItem.soyTemplateName = 'ddl.rule.btnAddNewTimelineItem';
}


ddl.rule.rulesHeader = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<li class="timeline-item"><div class="panel panel-default"><div class="panel-body flex-container"><div class="h4 panel-title">' + soy.$$escapeHtml(opt_data.title) + '</div><div class="btn-group dropdown" style="block"><button class="btn btn-default dropdown-toggle text-uppercase" data-toggle="dropdown" type="button">' + soy.$$escapeHtml(opt_data.logicalOperator) + ' <span class="caret"></span></button><ul class="dropdown-menu"><li class="logic-operator text-uppercase"><a>or</a></li><li class="divider"></li><li class="logic-operator text-uppercase"><a>and</a></li></ul></div><div class="timeline-increment-icon"><span class="timeline-icon"></span></div></div></div></li>');
};
if (goog.DEBUG) {
  ddl.rule.rulesHeader.soyTemplateName = 'ddl.rule.rulesHeader';
}


ddl.rule.action = function(opt_data, opt_ignored) {
  return '<li class="form-builder-rule-action-container-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' timeline-item"><div class="panel panel-default"><div class="panel-body flex-container"><h4>Do</h4><div class="action-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="target-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="timeline-increment-icon"><span class="timeline-icon"></span></div></div></div><div class="container__trash"><button class="btn btn-link action-card-delete icon-monospaced" data-card-id="' + soy.$$escapeHtmlAttribute(opt_data.index) + '" href="javascript:;" type="button">' + soy.$$filterNoAutoescape(opt_data.deleteIcon) + '</button></div></li>';
};
if (goog.DEBUG) {
  ddl.rule.action.soyTemplateName = 'ddl.rule.action';
}
