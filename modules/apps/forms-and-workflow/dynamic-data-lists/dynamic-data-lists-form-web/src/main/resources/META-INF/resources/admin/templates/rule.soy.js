// This file was automatically generated from rule.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddl.rule.
 * @public
 */

if (typeof ddl == 'undefined') { var ddl = {}; }
if (typeof ddl.rule == 'undefined') { ddl.rule = {}; }


ddl.rule.settings = function(opt_data, opt_ignored) {
  var output = '<h2 class="form-builder-section-title text-default">' + soy.$$escapeHtml(opt_data.strings.title) + '</h2><h4 class="text-default">' + soy.$$escapeHtml(opt_data.strings.description) + '</h4><div class="ddl-form-body-content"><ul class="liferay-ddl-form-builder-rule-condition-list liferay-ddl-form-rule-builder-timeline timeline ' + soy.$$escapeHtmlAttribute(opt_data.conditions.length > 1 ? 'can-remove-item' : '') + '">' + ddl.rule.rulesHeader({logicalOperator: opt_data.logicalOperator, title: 'Condition', extraContent: soydata.VERY_UNSAFE.$$ordainSanitizedHtmlForInternalBlocks('' + ddl.rule.logicOperatorDropDown(opt_data))});
  var conditionList73 = opt_data.conditions;
  var conditionListLen73 = conditionList73.length;
  if (conditionListLen73 > 0) {
    for (var conditionIndex73 = 0; conditionIndex73 < conditionListLen73; conditionIndex73++) {
      var conditionData73 = conditionList73[conditionIndex73];
      output += ddl.rule.condition({index: conditionIndex73, deleteIcon: opt_data.deleteIcon, logicOperator: opt_data.logicalOperator});
    }
  } else {
    output += ddl.rule.condition({index: 0, deleteIcon: opt_data.deleteIcon, logicOperator: opt_data.logicalOperator});
  }
  output += '</ul>' + ddl.rule.btnAddNewTimelineItem({plusIcon: opt_data.plusIcon, cssClass: 'form-builder-rule-add-condition'}) + '<ul class="action-list liferay-ddl-form-builder-rule-action-list liferay-ddl-form-rule-builder-timeline timeline ' + soy.$$escapeHtmlAttribute(opt_data.actions.length > 1 ? 'can-remove-item' : '') + '">' + ddl.rule.rulesHeader({logicalOperator: opt_data.logicalOperator, title: 'Actions'});
  var actionList92 = opt_data.actions;
  var actionListLen92 = actionList92.length;
  if (actionListLen92 > 0) {
    for (var actionIndex92 = 0; actionIndex92 < actionListLen92; actionIndex92++) {
      var actionData92 = actionList92[actionIndex92];
      output += ddl.rule.action({index: actionIndex92, deleteIcon: opt_data.deleteIcon});
    }
  } else {
    output += ddl.rule.action({index: 0, deleteIcon: opt_data.deleteIcon});
  }
  output += '</ul>' + ddl.rule.btnAddNewTimelineItem({plusIcon: opt_data.plusIcon, cssClass: 'form-builder-rule-add-action'}) + '<div class="liferay-ddl-form-rule-builder-footer"><button class="btn btn-default btn-lg btn-primary ddl-button form-builder-rule-settings-save" type="button"><span class="form-builder-rule-settings-save-label">' + soy.$$escapeHtml(opt_data.strings.save) + '</span></button><button class="btn btn-cancel btn-default btn-lg btn-link form-builder-rule-settings-cancel" type="button"><span class="lfr-btn-label">' + soy.$$escapeHtml(opt_data.strings.cancel) + '</span></button></div></div>';
  return output;
};
if (goog.DEBUG) {
  ddl.rule.settings.soyTemplateName = 'ddl.rule.settings';
}


ddl.rule.condition = function(opt_data, opt_ignored) {
  return '<li class="form-builder-rule-condition-container-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' timeline-item"><div class="panel panel-default"><div class="flex-container panel-body"><h4>If</h4><div class="condition-if-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="condition-operator-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="condition-the-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="condition-type-value-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="condition-type-value-options-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="timeline-increment-icon"><span class="timeline-icon"></span></div></div></div><div class="operator panel panel-default panel-inline"><div class="panel-body text-uppercase">' + soy.$$escapeHtml(opt_data.logicOperator) + '</div></div><div class="container-trash"><button class="btn btn-link condition-card-delete icon-monospaced" data-card-id="' + soy.$$escapeHtmlAttribute(opt_data.index) + '" href="javascript:;" type="button">' + soy.$$filterNoAutoescape(opt_data.deleteIcon) + '</button></div></li>';
};
if (goog.DEBUG) {
  ddl.rule.condition.soyTemplateName = 'ddl.rule.condition';
}


ddl.rule.btnAddNewTimelineItem = function(opt_data, opt_ignored) {
  return '<div class="addbutton-timeline-item"><div class="add-condition timeline-increment-icon"><a aria-role="button" class="btn btn-primary btn-xs form-builder-timeline-add-item ' + soy.$$escapeHtmlAttribute(opt_data.cssClass || '') + '" href="javascript:;">' + soy.$$filterNoAutoescape(opt_data.plusIcon) + '</a></div></div>';
};
if (goog.DEBUG) {
  ddl.rule.btnAddNewTimelineItem.soyTemplateName = 'ddl.rule.btnAddNewTimelineItem';
}


ddl.rule.rulesHeader = function(opt_data, opt_ignored) {
  return '<li class="timeline-item"><div class="panel panel-default"><div class="flex-container panel-body"><div class="h4 panel-title">' + soy.$$escapeHtml(opt_data.title) + '</div>' + ((opt_data.extraContent) ? soy.$$filterNoAutoescape(opt_data.extraContent) : '') + '<div class="timeline-increment-icon"><span class="timeline-icon"></span></div></div></div></li>';
};
if (goog.DEBUG) {
  ddl.rule.rulesHeader.soyTemplateName = 'ddl.rule.rulesHeader';
}


ddl.rule.logicOperatorDropDown = function(opt_data, opt_ignored) {
  opt_data = opt_data || {};
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div class="btn-group dropdown" style="block"><button class="btn btn-default dropdown-toggle dropdown-toggle-operator text-uppercase" data-toggle="dropdown" type="button"><span class="dropdown-toggle-selected-value">' + soy.$$escapeHtml(opt_data.logicalOperator) + '</span> <span class="caret"></span></button><ul class="dropdown-menu"><li class="logic-operator text-uppercase"><a href="#">or</a></li><li class="divider"></li><li class="logic-operator text-uppercase"><a href="#">and</a></li></ul></div>');
};
if (goog.DEBUG) {
  ddl.rule.logicOperatorDropDown.soyTemplateName = 'ddl.rule.logicOperatorDropDown';
}


ddl.rule.action = function(opt_data, opt_ignored) {
  return '<li class="form-builder-rule-action-container-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' timeline-item"><div class="panel panel-default"><div class="panel-body"><div class="row"><div class="col-md-12 flex-container "><h4>Do</h4><div class="action-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="form-group target-' + soy.$$escapeHtmlAttribute(opt_data.index) + '"></div></div></div><div class="row"><div class="col-md-12"><div class="additional-info-' + soy.$$escapeHtmlAttribute(opt_data.index) + '"></div></div></div><div class="timeline-increment-icon"><span class="timeline-icon"></span></div></div></div><div class="container-trash"><button class="btn btn-link action-card-delete icon-monospaced" data-card-id="' + soy.$$escapeHtmlAttribute(opt_data.index) + '" href="javascript:;" type="button">' + soy.$$filterNoAutoescape(opt_data.deleteIcon) + '</button></div></li>';
};
if (goog.DEBUG) {
  ddl.rule.action.soyTemplateName = 'ddl.rule.action';
}
