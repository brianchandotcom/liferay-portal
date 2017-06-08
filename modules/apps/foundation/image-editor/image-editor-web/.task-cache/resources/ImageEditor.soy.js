define("liferay-image-editor-web@1.0.0/ImageEditor.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
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

    var $templateAlias1 = _Soy2.default.getTemplate('Dropdown.incrementaldom', 'render');

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render(opt_data, opt_ignored, opt_ijData) {
      ie_open('div', null, null, 'id', opt_data.id);
      ie_open('div', null, null, 'class', 'lfr-image-editor-image-container', 'id', opt_data.id + 'ImageContainer');
      if (('' + opt_data.image).indexOf('data:image') != -1) {
        ie_open('img', null, null, 'class', 'img-responsive', 'src', soy.$$filterImageDataUri(opt_data.image));
        ie_close('img');
      } else {
        ie_open('img', null, null, 'class', 'img-responsive', 'src', opt_data.image);
        ie_close('img');
      }
      ie_void('canvas', null, null, 'class', 'image-preview ' + (opt_data.imagePreview ? '' : 'hide'));
      ie_close('div');
      ie_open('div', null, null, 'class', 'lfr-image-editor-tools-container');
      $tools(opt_data, null, opt_ijData);
      ie_close('div');
      ie_open('div', null, null, 'class', 'lfr-image-editor-history-container');
      if (!opt_data.selectedTool) {
        ie_open('div', null, null, 'class', 'history-controls history-left btn-group', 'role', 'group');
        ie_void('a', null, null, 'class', 'btn btn-link ' + (opt_data.history && opt_data.history.canUndo ? '' : 'disabled') + ' icon-reply icon-monospaced', 'data-onclick', 'undo', 'href', 'javascript:;');
        ie_void('a', null, null, 'class', 'btn btn-link ' + (opt_data.history && opt_data.history.canRedo ? '' : 'disabled') + ' icon-share-alt icon-monospaced', 'data-onclick', 'redo', 'href', 'javascript:;');
        ie_close('div');
        ie_open('div', null, null, 'class', 'history-controls history-right btn-group', 'role', 'group');
        ie_void('a', null, null, 'class', 'btn btn-link icon-undo ' + (opt_data.history && opt_data.history.canReset ? '' : 'disabled') + ' icon-monospaced', 'data-onclick', 'reset', 'href', 'javascript:;');
        ie_close('div');
      }
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
      var toolList46 = opt_data.capabilities.tools;
      var toolListLen46 = toolList46.length;
      for (var toolIndex46 = 0; toolIndex46 < toolListLen46; toolIndex46++) {
        var toolData46 = toolList46[toolIndex46];
        ie_open('li', null, null, 'class', opt_data.selectedTool == 'tool-' + toolIndex46 ? 'open' : '', 'style', 'display:inline');
        $tool(soy.$$augmentMap(opt_data, { tool: toolData46, toolIndex: toolIndex46 }), null, opt_ijData);
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
        var control__soy55 = opt_data.tool.controls[0];
        ie_open('a', null, null, 'class', 'btn', 'data-onclick', 'requestEditorEdit', 'data-control', control__soy55.variant, 'data-tool', 'tool-' + opt_data.toolIndex, 'href', 'javascript:;');
        ie_void('span', null, null, 'class', 'icon-' + opt_data.tool.icon + ' icon-monospaced');
        ie_close('a');
        $tool_control(soy.$$augmentMap(opt_data, { control: control__soy55 }), null, opt_ijData);
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
      var param66 = function param66() {
        ie_open('a', null, null, 'class', 'btn', 'data-onclick', 'toggle', 'href', 'javascript:;');
        ie_void('span', null, null, 'class', 'icon-' + opt_data.tool.icon + ' icon-monospaced');
        ie_close('a');
      };
      var param70 = function param70() {
        var controlList80 = opt_data.tool.controls;
        var controlListLen80 = controlList80.length;
        for (var controlIndex80 = 0; controlIndex80 < controlListLen80; controlIndex80++) {
          var controlData80 = controlList80[controlIndex80];
          ie_open('li');
          ie_open('a', null, null, 'data-onclick', opt_data.requestEditorEdit, 'data-control', controlData80.variant, 'data-tool', 'tool-' + opt_data.toolIndex);
          itext((goog.asserts.assert(controlData80.label != null), controlData80.label));
          ie_close('a');
          ie_close('li');
        }
      };
      $templateAlias1({ header: param66, body: param70 }, null, opt_ijData);
      var controlList85 = opt_data.tool.controls;
      var controlListLen85 = controlList85.length;
      for (var controlIndex85 = 0; controlIndex85 < controlListLen85; controlIndex85++) {
        var controlData85 = controlList85[controlIndex85];
        $tool_control(soy.$$augmentMap(opt_data, { control: controlData85 }), null, opt_ijData);
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
      ie_open('div', null, null, 'class', 'filters');
      ie_open('div', null, null, 'class', 'col-md-2 col-sm-2 col-xs-2 btn-space');
      ie_open('a', null, null, 'class', 'btn btn-link btn-primary modal-btn-icon icon-ok', 'data-onclick', 'accept', 'href', 'javascript:;');
      itext('Apply');
      ie_close('a');
      ie_close('div');
      ie_open('div', null, null, 'class', 'col-md-8 col-sm-8 col-xs-8 filters-list');
      if (opt_data.selectedControl && opt_data.selectedControl.label == opt_data.control.label) {
        $active_controls(soy.$$augmentMap(opt_data, { modulePath: opt_data.control.modulePath, variant: opt_data.control.variant }), null, opt_ijData);
      }
      ie_close('div');
      ie_open('div', null, null, 'class', 'col-md-2 col-sm-2 col-xs-2 btn-space');
      ie_open('a', null, null, 'class', 'btn btn-link close-modal modal-btn-icon icon-remove', 'data-onclick', 'discard', 'href', 'javascript:;');
      itext('Cancel');
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
    function __deltemplate_s98_266044fb(opt_data, opt_ignored, opt_ijData) {}
    exports.__deltemplate_s98_266044fb = __deltemplate_s98_266044fb;
    if (goog.DEBUG) {
      __deltemplate_s98_266044fb.soyTemplateName = 'ImageEditor.__deltemplate_s98_266044fb';
    }
    soy.$$registerDelegateFn(soy.$$getDelTemplateId('ImageEditor.Controls.idom'), '', 0, __deltemplate_s98_266044fb);

    exports.render.params = ["capabilities", "history", "id", "image", "imagePreview", "selectedControl", "selectedTool", "getEditorImage", "getEditorImageContainer", "requestEditorEdit", "requestEditorPreview"];
    exports.render.types = { "capabilities": "any", "history": "any", "id": "any", "image": "any", "imagePreview": "any", "selectedControl": "any", "selectedTool": "any", "getEditorImage": "any", "getEditorImageContainer": "any", "requestEditorEdit": "any", "requestEditorPreview": "any" };
    exports.tools.params = ["capabilities", "selectedTool"];
    exports.tools.types = { "capabilities": "any", "selectedTool": "any" };
    exports.tool.params = ["tool", "toolIndex"];
    exports.tool.types = { "tool": "any", "toolIndex": "any" };
    exports.tool_dropdown.params = ["requestEditorEdit", "tool", "toolIndex"];
    exports.tool_dropdown.types = { "requestEditorEdit": "any", "tool": "any", "toolIndex": "any" };
    exports.tool_control.params = ["control", "selectedControl"];
    exports.tool_control.types = { "control": "any", "selectedControl": "any" };
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