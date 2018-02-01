/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from rule.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMRule.
 * @public
 */

goog.module('DDMRule.incrementaldom');

/** @suppress {extraRequire} */
var soy = goog.require('soy');
/** @suppress {extraRequire} */
var soydata = goog.require('soydata');
/** @suppress {extraRequire} */
goog.require('goog.asserts');
/** @suppress {extraRequire} */
goog.require('soy.asserts');
/** @suppress {extraRequire} */
goog.require('goog.i18n.bidi');
/** @suppress {extraRequire} */
goog.require('goog.string');
var IncrementalDom = goog.require('incrementaldom');
var ie_open = IncrementalDom.elementOpen;
var ie_close = IncrementalDom.elementClose;
var ie_void = IncrementalDom.elementVoid;
var ie_open_start = IncrementalDom.elementOpenStart;
var ie_open_end = IncrementalDom.elementOpenEnd;
var itext = IncrementalDom.text;
var iattr = IncrementalDom.attr;


/**
 * @param {{
 *    actions: !Array<string>,
 *    conditions: !Array<string>,
 *    deleteIcon: (!soydata.SanitizedHtml|string),
 *    invalid: boolean,
 *    plusIcon: (!soydata.SanitizedHtml|string),
 *    strings: (?),
 *    logicalOperator: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  var actions = goog.asserts.assertArray(opt_data.actions, "expected parameter 'actions' of type list<string>.");
  var conditions = goog.asserts.assertArray(opt_data.conditions, "expected parameter 'conditions' of type list<string>.");
  soy.asserts.assertType((opt_data.deleteIcon instanceof Function) || (opt_data.deleteIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.deleteIcon), 'deleteIcon', opt_data.deleteIcon, 'Function');
  var deleteIcon = /** @type {Function} */ (opt_data.deleteIcon);
  soy.asserts.assertType(goog.isBoolean(opt_data.invalid) || opt_data.invalid === 1 || opt_data.invalid === 0, 'invalid', opt_data.invalid, 'boolean');
  var invalid = /** @type {boolean} */ (!!opt_data.invalid);
  soy.asserts.assertType((opt_data.plusIcon instanceof Function) || (opt_data.plusIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.plusIcon), 'plusIcon', opt_data.plusIcon, 'Function');
  var plusIcon = /** @type {Function} */ (opt_data.plusIcon);
  soy.asserts.assertType(opt_data.logicalOperator == null || (opt_data.logicalOperator instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.logicalOperator), 'logicalOperator', opt_data.logicalOperator, 'null|string|undefined');
  var logicalOperator = /** @type {null|string|undefined} */ (opt_data.logicalOperator);
  ie_open('div');
    ie_open('h2', null, null,
        'class', 'form-builder-section-title text-default');
      var dyn30 = opt_data.strings.title;
      if (typeof dyn30 == 'function') dyn30(); else if (dyn30 != null) itext(dyn30);
    ie_close('h2');
    ie_open('h4', null, null,
        'class', 'text-default');
      var dyn31 = opt_data.strings.description;
      if (typeof dyn31 == 'function') dyn31(); else if (dyn31 != null) itext(dyn31);
    ie_close('h4');
    ie_open('div', null, null,
        'class', 'ddm-form-body-content');
      ie_open('ul', null, null,
          'class', 'liferay-ddm-form-builder-rule-condition-list liferay-ddm-form-rule-builder-timeline timeline ' + (conditions.length > 1 ? 'can-remove-item' : ''));
        var showLogicalOperator__soy336 = conditions.length > 1 ? true : false;
        var param337 = function() {
          $logicalOperatorDropDown({logicalOperator: opt_data.strings[logicalOperator], strings: opt_data.strings}, null, opt_ijData);
        };
        $rulesHeader({extraContent: param337, logicalOperator: opt_data.strings[logicalOperator], title: opt_data.strings.condition}, null, opt_ijData);
        var conditionList354 = conditions;
        var conditionListLen354 = conditionList354.length;
        if (conditionListLen354 > 0) {
          for (var conditionIndex354 = 0; conditionIndex354 < conditionListLen354; conditionIndex354++) {
            var conditionData354 = conditionList354[conditionIndex354];
            $condition({deleteIcon: deleteIcon, if: opt_data.strings['if'], index: conditionIndex354, logicalOperator: opt_data.strings[logicalOperator]}, null, opt_ijData);
          }
        } else {
          $condition({deleteIcon: deleteIcon, if: opt_data.strings['if'], index: 0, logicalOperator: opt_data.strings[logicalOperator]}, null, opt_ijData);
        }
      ie_close('ul');
      $btnAddNewTimelineItem({cssClass: 'form-builder-rule-add-condition', plusIcon: plusIcon}, null, opt_ijData);
      ie_open('ul', null, null,
          'class', 'action-list liferay-ddm-form-builder-rule-action-list liferay-ddm-form-rule-builder-timeline timeline ' + (actions.length > 1 ? 'can-remove-item' : ''));
        $rulesHeader({logicalOperator: opt_data.strings[logicalOperator], title: opt_data.strings.actions}, null, opt_ijData);
        var actionList375 = actions;
        var actionListLen375 = actionList375.length;
        if (actionListLen375 > 0) {
          for (var actionIndex375 = 0; actionIndex375 < actionListLen375; actionIndex375++) {
            var actionData375 = actionList375[actionIndex375];
            $action({deleteIcon: deleteIcon, do: opt_data.strings['do'], index: actionIndex375}, null, opt_ijData);
          }
        } else {
          $action({deleteIcon: deleteIcon, do: opt_data.strings['do'], index: 0}, null, opt_ijData);
        }
      ie_close('ul');
      $btnAddNewTimelineItem({cssClass: 'form-builder-rule-add-action', plusIcon: plusIcon}, null, opt_ijData);
      ie_open('div', null, null,
          'class', 'liferay-ddm-form-rule-builder-footer');
        ie_open_start('button');
            iattr('class', 'btn btn-lg btn-primary ddm-button form-builder-rule-settings-save');
            if (invalid) {
              iattr('disabled', '');
            }
            iattr('type', 'button');
        ie_open_end();
          ie_open('span', null, null,
              'class', 'form-builder-rule-settings-save-label');
            var dyn32 = opt_data.strings.save;
            if (typeof dyn32 == 'function') dyn32(); else if (dyn32 != null) itext(dyn32);
          ie_close('span');
        ie_close('button');
        ie_open('button', null, null,
            'class', 'btn btn-lg btn-cancel btn-secondary form-builder-rule-settings-cancel',
            'type', 'button');
          ie_open('span', null, null,
              'class', 'lfr-btn-label');
            var dyn33 = opt_data.strings.cancel;
            if (typeof dyn33 == 'function') dyn33(); else if (dyn33 != null) itext(dyn33);
          ie_close('span');
        ie_close('button');
      ie_close('div');
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMRule.render';
}


/**
 * @param {{
 *    deleteIcon: (!soydata.SanitizedHtml|string),
 *    param$if: string,
 *    index: number,
 *    logicalOperator: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $condition(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.deleteIcon instanceof Function) || (opt_data.deleteIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.deleteIcon), 'deleteIcon', opt_data.deleteIcon, 'Function');
  var deleteIcon = /** @type {Function} */ (opt_data.deleteIcon);
  soy.asserts.assertType(goog.isString(opt_data['if']) || (opt_data['if'] instanceof goog.soy.data.SanitizedContent), 'if', opt_data['if'], 'string|goog.soy.data.SanitizedContent');
  var param$if = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data['if']);
  var index = goog.asserts.assertNumber(opt_data.index, "expected parameter 'index' of type int.");
  soy.asserts.assertType(opt_data.logicalOperator == null || (opt_data.logicalOperator instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.logicalOperator), 'logicalOperator', opt_data.logicalOperator, 'null|string|undefined');
  var logicalOperator = /** @type {null|string|undefined} */ (opt_data.logicalOperator);
  ie_open('li', null, null,
      'class', 'form-builder-rule-condition-container-' + index + ' timeline-item');
    ie_open('div', null, null,
        'class', 'panel panel-default');
      ie_open('div', null, null,
          'class', 'flex-container panel-body');
        ie_open('h4');
          var dyn34 = param$if;
          if (typeof dyn34 == 'function') dyn34(); else if (dyn34 != null) itext(dyn34);
        ie_close('h4');
        ie_void('div', null, null,
            'class', 'condition-if-' + index + ' form-group');
        ie_void('div', null, null,
            'class', 'condition-operator-' + index + ' form-group');
        ie_void('div', null, null,
            'class', 'condition-the-' + index + ' form-group');
        ie_void('div', null, null,
            'class', 'condition-type-value-' + index + ' form-group');
        ie_void('div', null, null,
            'class', 'condition-type-value-options-' + index + ' form-group');
        ie_open('div', null, null,
            'class', 'timeline-increment');
          ie_void('span', null, null,
              'class', 'timeline-icon');
        ie_close('div');
      ie_close('div');
    ie_close('div');
    ie_open('div', null, null,
        'class', 'operator panel panel-default panel-inline');
      ie_open('div', null, null,
          'class', 'panel-body text-uppercase');
        var dyn35 = logicalOperator;
        if (typeof dyn35 == 'function') dyn35(); else if (dyn35 != null) itext(dyn35);
      ie_close('div');
    ie_close('div');
    ie_open('div', null, null,
        'class', 'container-trash');
      ie_open('button', null, null,
          'class', 'btn btn-link condition-card-delete',
          'data-card-id', index,
          'href', 'javascript:;',
          'type', 'button');
        deleteIcon();
      ie_close('button');
    ie_close('div');
  ie_close('li');
}
exports.condition = $condition;
if (goog.DEBUG) {
  $condition.soyTemplateName = 'DDMRule.condition';
}


/**
 * @param {{
 *    plusIcon: (!soydata.SanitizedHtml|string),
 *    cssClass: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $btnAddNewTimelineItem(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.plusIcon instanceof Function) || (opt_data.plusIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.plusIcon), 'plusIcon', opt_data.plusIcon, 'Function');
  var plusIcon = /** @type {Function} */ (opt_data.plusIcon);
  soy.asserts.assertType(opt_data.cssClass == null || (opt_data.cssClass instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.cssClass), 'cssClass', opt_data.cssClass, 'null|string|undefined');
  var cssClass = /** @type {null|string|undefined} */ (opt_data.cssClass);
  ie_open('div', null, null,
      'class', 'addbutton-timeline-item');
    ie_open('div', null, null,
        'class', 'add-condition timeline-increment-icon');
      ie_open('a', null, null,
          'aria-role', 'button',
          'class', 'btn-primary rounded-circle sticker sticker-primary form-builder-timeline-add-item ' + (cssClass || ''),
          'href', 'javascript:;');
        plusIcon();
      ie_close('a');
    ie_close('div');
  ie_close('div');
}
exports.btnAddNewTimelineItem = $btnAddNewTimelineItem;
if (goog.DEBUG) {
  $btnAddNewTimelineItem.soyTemplateName = 'DDMRule.btnAddNewTimelineItem';
}


/**
 * @param {{
 *    title: string,
 *    extraContent: (?soydata.SanitizedHtml|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $rulesHeader(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.title) || (opt_data.title instanceof goog.soy.data.SanitizedContent), 'title', opt_data.title, 'string|goog.soy.data.SanitizedContent');
  var title = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.title);
  soy.asserts.assertType(opt_data.extraContent == null || (opt_data.extraContent instanceof Function) || (opt_data.extraContent instanceof soydata.UnsanitizedText) || goog.isString(opt_data.extraContent), 'extraContent', opt_data.extraContent, '?soydata.SanitizedHtml|string|undefined');
  var extraContent = /** @type {?soydata.SanitizedHtml|string|undefined} */ (opt_data.extraContent);
  ie_open('li', null, null,
      'class', 'timeline-item');
    ie_open('div', null, null,
        'class', 'panel panel-default');
      ie_open('div', null, null,
          'class', 'flex-container panel-body');
        ie_open('div', null, null,
            'class', 'h4 panel-title');
          var dyn36 = title;
          if (typeof dyn36 == 'function') dyn36(); else if (dyn36 != null) itext(dyn36);
        ie_close('div');
        if (extraContent) {
          extraContent();
        }
        ie_open('div', null, null,
            'class', 'timeline-increment');
          ie_void('span', null, null,
              'class', 'timeline-icon');
        ie_close('div');
      ie_close('div');
    ie_close('div');
  ie_close('li');
}
exports.rulesHeader = $rulesHeader;
if (goog.DEBUG) {
  $rulesHeader.soyTemplateName = 'DDMRule.rulesHeader';
}


/**
 * @param {{
 *    strings: (?),
 *    logicalOperator: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $logicalOperatorDropDown(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(opt_data.logicalOperator == null || (opt_data.logicalOperator instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.logicalOperator), 'logicalOperator', opt_data.logicalOperator, 'null|string|undefined');
  var logicalOperator = /** @type {null|string|undefined} */ (opt_data.logicalOperator);
  ie_open('div', null, null,
      'class', 'btn-group dropdown',
      'style', 'block');
    ie_open('button', null, null,
        'class', 'btn btn-default dropdown-toggle dropdown-toggle-operator text-uppercase',
        'data-toggle', 'dropdown',
        'type', 'button');
      ie_open('span', null, null,
          'class', 'dropdown-toggle-selected-value');
        var dyn37 = logicalOperator;
        if (typeof dyn37 == 'function') dyn37(); else if (dyn37 != null) itext(dyn37);
      ie_close('span');
      itext(' ');
      ie_void('span', null, null,
          'class', 'caret');
    ie_close('button');
    ie_open('ul', null, null,
        'class', 'dropdown-menu');
      ie_open('li', null, null,
          'class', 'logic-operator text-uppercase',
          'data-logical-operator-value', 'or');
        ie_open('a', null, null,
            'href', '#');
          var dyn38 = opt_data.strings['or'];
          if (typeof dyn38 == 'function') dyn38(); else if (dyn38 != null) itext(dyn38);
        ie_close('a');
      ie_close('li');
      ie_void('li', null, null,
          'class', 'divider');
      ie_open('li', null, null,
          'class', 'logic-operator text-uppercase',
          'data-logical-operator-value', 'and');
        ie_open('a', null, null,
            'href', '#');
          var dyn39 = opt_data.strings['and'];
          if (typeof dyn39 == 'function') dyn39(); else if (dyn39 != null) itext(dyn39);
        ie_close('a');
      ie_close('li');
    ie_close('ul');
  ie_close('div');
}
exports.logicalOperatorDropDown = $logicalOperatorDropDown;
if (goog.DEBUG) {
  $logicalOperatorDropDown.soyTemplateName = 'DDMRule.logicalOperatorDropDown';
}


/**
 * @param {{
 *    deleteIcon: (!soydata.SanitizedHtml|string),
 *    param$do: string,
 *    index: number
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $action(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.deleteIcon instanceof Function) || (opt_data.deleteIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.deleteIcon), 'deleteIcon', opt_data.deleteIcon, 'Function');
  var deleteIcon = /** @type {Function} */ (opt_data.deleteIcon);
  soy.asserts.assertType(goog.isString(opt_data['do']) || (opt_data['do'] instanceof goog.soy.data.SanitizedContent), 'do', opt_data['do'], 'string|goog.soy.data.SanitizedContent');
  var param$do = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data['do']);
  var index = goog.asserts.assertNumber(opt_data.index, "expected parameter 'index' of type int.");
  ie_open('li', null, null,
      'class', 'form-builder-rule-action-container-' + index + ' timeline-item');
    ie_open('div', null, null,
        'class', 'panel panel-default');
      ie_open('div', null, null,
          'class', 'panel-body');
        ie_open('div', null, null,
            'class', 'row');
          ie_open('div', null, null,
              'class', 'col-md-12 flex-container ');
            ie_open('h4');
              var dyn40 = param$do;
              if (typeof dyn40 == 'function') dyn40(); else if (dyn40 != null) itext(dyn40);
            ie_close('h4');
            ie_void('div', null, null,
                'class', 'action-' + index + ' form-group');
            ie_void('div', null, null,
                'class', 'container-target-action form-group target-' + index);
          ie_close('div');
        ie_close('div');
        ie_open('div', null, null,
            'class', 'action-rule-data-provider row');
          ie_open('div', null, null,
              'class', 'col-md-12 no-padding');
            ie_void('div', null, null,
                'class', 'additional-info-' + index);
          ie_close('div');
        ie_close('div');
        ie_open('div', null, null,
            'class', 'timeline-increment');
          ie_void('span', null, null,
              'class', 'timeline-icon');
        ie_close('div');
      ie_close('div');
    ie_close('div');
    ie_open('div', null, null,
        'class', 'container-trash');
      ie_open('button', null, null,
          'class', 'btn btn-link action-card-delete',
          'data-card-id', index,
          'href', 'javascript:;',
          'type', 'button');
        deleteIcon();
      ie_close('button');
    ie_close('div');
  ie_close('li');
}
exports.action = $action;
if (goog.DEBUG) {
  $action.soyTemplateName = 'DDMRule.action';
}

exports.render.params = ["actions","conditions","deleteIcon","invalid","plusIcon","strings","logicalOperator"];
exports.render.types = {"actions":"list<string>","conditions":"list<string>","deleteIcon":"html","invalid":"bool","plusIcon":"html","strings":"?","logicalOperator":"string"};
exports.condition.params = ["deleteIcon","if","index","logicalOperator"];
exports.condition.types = {"deleteIcon":"html","if":"string","index":"int","logicalOperator":"string"};
exports.btnAddNewTimelineItem.params = ["plusIcon","cssClass"];
exports.btnAddNewTimelineItem.types = {"plusIcon":"html","cssClass":"string"};
exports.rulesHeader.params = ["title","extraContent"];
exports.rulesHeader.types = {"title":"string","extraContent":"html"};
exports.logicalOperatorDropDown.params = ["strings","logicalOperator"];
exports.logicalOperatorDropDown.types = {"strings":"?","logicalOperator":"string"};
exports.action.params = ["deleteIcon","do","index"];
exports.action.types = {"deleteIcon":"html","do":"string","index":"int"};
templates = exports;
return exports;

});

class DDMRule extends Component {}
Soy.register(DDMRule, templates);
export { DDMRule, templates };
export default templates;
/* jshint ignore:end */
