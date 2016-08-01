define("frontend-image-editor-web@1.0.2/ImageEditor.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.ImageEditor = undefined;

  var _Component3 = _interopRequireDefault(_Component2);

  var _Soy2 = _interopRequireDefault(_Soy);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  function _possibleConstructorReturn(self, call) {
    if (!self) {
      throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
    }

    return call && (typeof call === "object" || typeof call === "function") ? call : self;
  }

  function _inherits(subClass, superClass) {
    if (typeof superClass !== "function" && superClass !== null) {
      throw new TypeError("Super expression must either be null or a function, not " + typeof superClass);
    }

    subClass.prototype = Object.create(superClass && superClass.prototype, {
      constructor: {
        value: subClass,
        enumerable: false,
        writable: true,
        configurable: true
      }
    });
    if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
  }

  var templates;
  goog.loadModule(function (exports) {

    // This file was automatically generated from ImageEditor.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace ImageEditor.
     * @hassoydeltemplate {ImageEditor.Controls.idom}
     * @hassoydelcall {ImageEditor.Controls.idom}
     * @public
     */

    goog.module('ImageEditor.incrementaldom');

    /** @suppress {extraRequire} */
    var soy = goog.require('soy');
    /** @suppress {extraRequire} */
    var soydata = goog.require('soydata');
    /** @suppress {extraRequire} */
    goog.require('goog.i18n.bidi');
    /** @suppress {extraRequire} */
    goog.require('goog.asserts');
    var IncrementalDom = goog.require('incrementaldom');
    var ie_open = IncrementalDom.elementOpen;
    var ie_close = IncrementalDom.elementClose;
    var ie_void = IncrementalDom.elementVoid;
    var ie_open_start = IncrementalDom.elementOpenStart;
    var ie_open_end = IncrementalDom.elementOpenEnd;
    var itext = IncrementalDom.text;
    var iattr = IncrementalDom.attr;

    var $templateAlias2 = _Soy2.default.getTemplate('Dropdown.incrementaldom', 'render');

    var $templateAlias1 = _Soy2.default.getTemplate('ImageEditorLoading.incrementaldom', 'render');

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render(opt_data, opt_ignored, opt_ijData) {
      ie_open('div', null, null, 'id', opt_data.id);
      ie_open('div', null, null, 'class', 'dialog-body');
      ie_open('div', null, null, 'class', 'lfr-image-editor-image-container', 'id', opt_data.id + 'ImageContainer');
      ie_open('img', null, null, 'class', 'hide img-responsive', 'src', opt_data.image);
      ie_close('img');
      ie_void('canvas', null, null, 'class', 'fade ' + (opt_data.imageEditorReady ? 'in' : '') + ' image-preview');
      $templateAlias1(soy.$$augmentMap(opt_data, { key: 'loading' }), null, opt_ijData);
      ie_close('div');
      ie_open('div', null, null, 'class', 'fade ' + (opt_data.imageEditorReady ? 'in' : '') + ' lfr-image-editor-tools-container');
      $tools(opt_data, null, opt_ijData);
      ie_close('div');
      ie_open('div', null, null, 'class', 'fade ' + (opt_data.imageEditorReady && !opt_data.selectedTool ? 'in' : '') + ' lfr-image-editor-history-container');
      ie_open('div', null, null, 'class', 'btn-group', 'role', 'group');
      ie_open('a', null, null, 'class', 'btn btn-link ' + (opt_data.history && opt_data.history.canUndo ? '' : 'disabled') + ' icon-monospaced', 'data-onclick', 'undo', 'href', 'javascript:;');
      ie_open('svg', null, null, 'class', 'lexicon-icon');
      ie_void('use', null, null, 'xlink:href', opt_data.pathThemeImages + '/lexicon/icons.svg#angle-left');
      ie_close('svg');
      ie_close('a');
      ie_open('a', null, null, 'class', 'btn btn-link ' + (opt_data.history && opt_data.history.canReset ? '' : 'disabled') + ' icon-monospaced', 'data-onclick', 'reset', 'href', 'javascript:;');
      ie_open('svg', null, null, 'class', 'lexicon-icon');
      ie_void('use', null, null, 'xlink:href', opt_data.pathThemeImages + '/lexicon/icons.svg#restore');
      ie_close('svg');
      ie_close('a');
      ie_open('a', null, null, 'class', 'btn btn-link ' + (opt_data.history && opt_data.history.canRedo ? '' : 'disabled') + ' icon-monospaced', 'data-onclick', 'redo', 'href', 'javascript:;');
      ie_open('svg', null, null, 'class', 'lexicon-icon');
      ie_void('use', null, null, 'xlink:href', opt_data.pathThemeImages + '/lexicon/icons.svg#angle-right');
      ie_close('svg');
      ie_close('a');
      ie_close('div');
      ie_close('div');
      ie_close('div');
      ie_open('div', null, null, 'class', 'button-holder dialog-footer');
      var hasChanges__soy37 = opt_data.history && opt_data.history.canUndo;
      var saveButtonAttributes__soy38 = function saveButtonAttributes__soy38() {
        iattr('class', 'btn btn-default btn-lg btn-primary ' + (hasChanges__soy37 ? '' : 'disabled'));
        iattr('data-onclick', 'save_');
        if (!hasChanges__soy37) {
          iattr('disabled', 'disabled');
        }
      };
      ie_open_start('button');
      saveButtonAttributes__soy38();
      ie_open_end();
      ie_open('span', null, null, 'class', 'lfr-btn-label');
      itext((goog.asserts.assert(opt_data.strings['save'] != null), opt_data.strings['save']));
      ie_close('span');
      ie_close('button');
      ie_open('button', null, null, 'class', 'btn btn-cancel btn-default btn-lg btn-link', 'data-onclick', 'close_');
      ie_open('span', null, null, 'class', 'lfr-btn-label');
      itext((goog.asserts.assert(opt_data.strings['cancel'] != null), opt_data.strings['cancel']));
      ie_close('span');
      ie_close('button');
      ie_close('div');
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'ImageEditor.render';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $tools(opt_data, opt_ignored, opt_ijData) {
      ie_open('div', null, null, 'class', 'controls text-center');
      ie_open('ul', null, null, 'class', 'list-inline');
      var toolList61 = opt_data.imageEditorCapabilities.tools;
      var toolListLen61 = toolList61.length;
      for (var toolIndex61 = 0; toolIndex61 < toolListLen61; toolIndex61++) {
        var toolData61 = toolList61[toolIndex61];
        ie_open('li', null, null, 'class', opt_data.selectedTool == 'tool-' + toolIndex61 ? 'open' : '');
        $tool(soy.$$augmentMap(opt_data, { tool: toolData61, toolIndex: toolIndex61 }), null, opt_ijData);
        ie_close('li');
      }
      ie_close('ul');
      ie_close('div');
    }
    exports.tools = $tools;
    if (goog.DEBUG) {
      $tools.soyTemplateName = 'ImageEditor.tools';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $tool(opt_data, opt_ignored, opt_ijData) {
      if (opt_data.tool.controls.length > 1) {
        $tool_dropdown(opt_data, null, opt_ijData);
      } else {
        var control__soy70 = opt_data.tool.controls[0];
        ie_open('a', null, null, 'class', 'btn', 'data-control', control__soy70.variant, 'data-onclick', 'requestImageEditorEdit', 'data-tool', 'tool-' + opt_data.toolIndex, 'href', 'javascript:;');
        ie_open('svg', null, null, 'class', 'icon-monospaced lexicon-icon');
        ie_void('use', null, null, 'xlink:href', opt_data.pathThemeImages + '/lexicon/icons.svg#' + opt_data.tool.icon);
        ie_close('svg');
        ie_close('a');
        $tool_control(soy.$$augmentMap(opt_data, { control: control__soy70 }), null, opt_ijData);
      }
    }
    exports.tool = $tool;
    if (goog.DEBUG) {
      $tool.soyTemplateName = 'ImageEditor.tool';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $tool_dropdown(opt_data, opt_ignored, opt_ijData) {
      var param83 = function param83() {
        ie_open('a', null, null, 'class', 'btn', 'data-onclick', 'toggle', 'href', 'javascript:;');
        ie_open('svg', null, null, 'class', 'icon-monospaced lexicon-icon', 'data-icon', opt_data.tool.icon);
        ie_void('use', null, null, 'xlink:href', opt_data.pathThemeImages + '/lexicon/icons.svg#' + opt_data.tool.icon);
        ie_close('svg');
        ie_close('a');
      };
      var param91 = function param91() {
        var controlList101 = opt_data.tool.controls;
        var controlListLen101 = controlList101.length;
        for (var controlIndex101 = 0; controlIndex101 < controlListLen101; controlIndex101++) {
          var controlData101 = controlList101[controlIndex101];
          ie_open('li', null, null, 'data-onclick', 'toggle');
          ie_open('a', null, null, 'data-control', controlData101.variant, 'data-onclick', opt_data.requestImageEditorEdit, 'data-tool', 'tool-' + opt_data.toolIndex);
          itext((goog.asserts.assert(controlData101.label != null), controlData101.label));
          ie_close('a');
          ie_close('li');
        }
      };
      $templateAlias2({ header: param83, body: param91, elementClasses: 'dropup', position: 0, positionClassOnMenu: true }, null, opt_ijData);
      var controlList109 = opt_data.tool.controls;
      var controlListLen109 = controlList109.length;
      for (var controlIndex109 = 0; controlIndex109 < controlListLen109; controlIndex109++) {
        var controlData109 = controlList109[controlIndex109];
        $tool_control(soy.$$augmentMap(opt_data, { control: controlData109 }), null, opt_ijData);
      }
    }
    exports.tool_dropdown = $tool_dropdown;
    if (goog.DEBUG) {
      $tool_dropdown.soyTemplateName = 'ImageEditor.tool_dropdown';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $tool_control(opt_data, opt_ignored, opt_ijData) {
      ie_open('div', null, null, 'class', 'controls-container ' + (opt_data.selectedControl && opt_data.selectedControl.label == opt_data.control.label ? 'open' : ''));
      ie_open('div', null, null, 'class', 'btn-space col-md-2 col-sm-2 col-xs-2');
      ie_open('button', null, null, 'class', 'btn btn-link', 'data-onclick', 'accept');
      itext((goog.asserts.assert(opt_data.strings['apply'] != null), opt_data.strings['apply']));
      ie_close('a');
      ie_close('div');
      ie_open('div', null, null, 'class', 'col-md-8 col-sm-8 col-xs-8 tool-controls');
      if (opt_data.selectedControl && opt_data.selectedControl.label == opt_data.control.label) {
        $active_controls(soy.$$augmentMap(opt_data, { modulePath: opt_data.control.modulePath, variant: opt_data.control.variant }), null, opt_ijData);
      }
      ie_close('div');
      ie_open('div', null, null, 'class', 'btn-space col-md-2 col-sm-2 col-xs-2');
      ie_open('button', null, null, 'class', 'btn btn-link', 'data-onclick', 'discard');
      itext((goog.asserts.assert(opt_data.strings['cancel'] != null), opt_data.strings['cancel']));
      ie_close('a');
      ie_close('div');
      ie_close('div');
    }
    exports.tool_control = $tool_control;
    if (goog.DEBUG) {
      $tool_control.soyTemplateName = 'ImageEditor.tool_control';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $active_controls(opt_data, opt_ignored, opt_ijData) {
      soy.$$getDelegateFn(soy.$$getDelTemplateId('ImageEditor.Controls.idom'), opt_data.variant, true)(soy.$$augmentMap(opt_data, { key: opt_data.id + '_selected_control_' + opt_data.variant }), null, opt_ijData);
    }
    exports.active_controls = $active_controls;
    if (goog.DEBUG) {
      $active_controls.soyTemplateName = 'ImageEditor.active_controls';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function __deltemplate_s128_266044fb(opt_data, opt_ignored, opt_ijData) {}
    exports.__deltemplate_s128_266044fb = __deltemplate_s128_266044fb;
    if (goog.DEBUG) {
      __deltemplate_s128_266044fb.soyTemplateName = 'ImageEditor.__deltemplate_s128_266044fb';
    }
    soy.$$registerDelegateFn(soy.$$getDelTemplateId('ImageEditor.Controls.idom'), '', 0, __deltemplate_s128_266044fb);

    exports.render.params = ["history", "id", "image", "imageEditorCapabilities", "imageEditorReady", "selectedControl", "selectedTool", "pathThemeImages", "strings", "getImageEditorCanvas", "getImageEditorImageData", "requestImageEditorEdit", "requestImageEditorPreview"];
    exports.render.types = { "history": "any", "id": "any", "image": "any", "imageEditorCapabilities": "any", "imageEditorReady": "any", "selectedControl": "any", "selectedTool": "any", "pathThemeImages": "any", "strings": "any", "getImageEditorCanvas": "any", "getImageEditorImageData": "any", "requestImageEditorEdit": "any", "requestImageEditorPreview": "any" };
    exports.tools.params = ["imageEditorCapabilities", "selectedTool"];
    exports.tools.types = { "imageEditorCapabilities": "any", "selectedTool": "any" };
    exports.tool.params = ["pathThemeImages", "tool", "toolIndex"];
    exports.tool.types = { "pathThemeImages": "any", "tool": "any", "toolIndex": "any" };
    exports.tool_dropdown.params = ["pathThemeImages", "requestImageEditorEdit", "tool", "toolIndex"];
    exports.tool_dropdown.types = { "pathThemeImages": "any", "requestImageEditorEdit": "any", "tool": "any", "toolIndex": "any" };
    exports.tool_control.params = ["control", "selectedControl", "strings"];
    exports.tool_control.types = { "control": "any", "selectedControl": "any", "strings": "any" };
    exports.active_controls.params = ["id", "variant"];
    exports.active_controls.types = { "id": "any", "variant": "any" };
    exports.templates = templates = exports;
    return exports;
  });

  var ImageEditor = function (_Component) {
    _inherits(ImageEditor, _Component);

    function ImageEditor() {
      _classCallCheck(this, ImageEditor);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return ImageEditor;
  }(_Component3.default);

  _Soy2.default.register(ImageEditor, templates);
  exports.default = templates;
  exports.ImageEditor = ImageEditor;
  exports.templates = templates;
});
//# sourceMappingURL=ImageEditor.soy.js.map