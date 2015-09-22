define(
    "frontend-js-metal-web@1.0.0/metal/src/soy/SoyComponent",
    ['exports', 'module', 'soyutils', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/object/object', 'metal/src/component/Component', 'metal/src/component/ComponentRegistry', 'metal/src/soy/SoyComponentAop'],
    function (exports, module, _soyutils, _metalSrcCore, _metalSrcDomDom, _metalSrcObjectObject, _metalSrcComponentComponent, _metalSrcComponentComponentRegistry, _metalSrcSoySoyComponentAop) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _soyutils2 = _interopRequireDefault(_soyutils);

        var _core = _interopRequireDefault(_metalSrcCore);

        var _dom = _interopRequireDefault(_metalSrcDomDom);

        var _object = _interopRequireDefault(_metalSrcObjectObject);

        var _Component2 = _interopRequireDefault(_metalSrcComponentComponent);

        var _ComponentRegistry = _interopRequireDefault(_metalSrcComponentComponentRegistry);

        var _SoyComponentAop = _interopRequireDefault(_metalSrcSoySoyComponentAop);

        // The injected data that will be passed to soy templates.
        var ijData = {};

        /**
      * Special Component class that handles a better integration between soy templates
      * and the components. It allows for automatic rendering of surfaces that have soy
      * templates defined with their names, skipping the call to `getSurfaceContent`.
      * @param {Object} opt_config An object with the initial values for this component's
      *   attributes.
      * @constructor
      * @extends {Component}
      */

        var SoyComponent = (function (_Component) {
            _inherits(SoyComponent, _Component);

            function SoyComponent(opt_config) {
                _classCallCheck(this, SoyComponent);

                _get(Object.getPrototypeOf(SoyComponent.prototype), 'constructor', this).call(this, opt_config);

                /**
        * The parameters defined in this component's "content" soy template.
        * @type {Array}
        * @protected
        */
                this.contentParams_ = null;

                /**
        * Flags indicating which surface names have already been found on this component's content.
        * @type {!Object<string, boolean>}
        * @protected
        */
                this.firstSurfaceFound_ = {};

                /**
        * Indicates which surface is currently being rendered, or null if none is.
        * @type {boolean}
        * @protected
        */
                this.surfaceBeingRendered_ = null;

                /**
        * Flag indicating if inner calls to templates should be skipped.
        * @type {boolean}
        * @protected
        */
                this.skipInnerCalls_ = false;

                this.addSurfacesFromTemplates_(opt_config);
                this.on('renderSurface', this.handleSoyComponentRenderSurface_);
            }

            /**
       * Adds surfaces for each registered template that is not named `element`.
       * @param {Object=} opt_config Optional component configuration.
       * @protected
       */

            _createClass(SoyComponent, [{
                key: 'addSurfacesFromTemplates_',
                value: function addSurfacesFromTemplates_(opt_config) {
                    var templates = _ComponentRegistry['default'].Templates[this.constructor.NAME] || {};
                    var templateNames = Object.keys(templates);
                    for (var i = 0; i < templateNames.length; i++) {
                        var templateName = templateNames[i];
                        var templateFn = _SoyComponentAop['default'].getOriginalFn(templates[templateName]);
                        if (this.isSurfaceTemplate_(templateName, templateFn)) {
                            var surface = this.getSurface(templateName);
                            if (!surface) {
                                this.addSurface(templateName, {
                                    renderAttrs: templateFn.params,
                                    templateComponentName: this.constructor.NAME,
                                    templateName: templateName
                                }, opt_config);
                            }
                        } else if (templateName === 'content') {
                            this.contentParams_ = templateFn.params;
                        }
                    }
                }

                /**
        * Builds the config data for a component from the data that was passed to its
        * soy template function.
        * @param {string} id The id of the component.
        * @param {!Object} templateData
        * @return {!Object} The component's config data.
        * @protected
        */
            }, {
                key: 'buildComponentConfigData_',
                value: function buildComponentConfigData_(id, templateData) {
                    var config = {
                        id: id
                    };
                    for (var key in templateData) {
                        config[key] = templateData[key];
                    }
                    return config;
                }

                /**
        * Overrides the original method from `Component` to include renderAttrs extracted
        * from the sou template.
        * @return {!Object}
        */
            }, {
                key: 'buildElementSurfaceData_',
                value: function buildElementSurfaceData_() {
                    var data = _get(Object.getPrototypeOf(SoyComponent.prototype), 'buildElementSurfaceData_', this).call(this);
                    data.renderAttrs = this.contentParams_;
                    this.contentParams_ = null;
                    return data;
                }

                /**
        * Builds the data object that should be passed to a template from this component.
        * @return {!Object}
        * @protected
        */
            }, {
                key: 'buildTemplateData_',
                value: function buildTemplateData_() {
                    var names = this.getAttrNames().filter(function (name) {
                        // Get all attribute values except for "element", since it helps performance and this
                        // attribute shouldn't be referenced inside a soy template anyway.
                        return name !== 'element';
                    });
                    var surface = this.getSurface(this.id);
                    var data = surface && surface.componentData ? surface.componentData : {};
                    return _object['default'].mixin(data, this.getAttrs(names));
                }

                /**
        * Creates and instantiates a component that has the given soy template function as its
        * main content template. All keys present in the config object, if one is given, will be
        * attributes of this component, and the object itself will be passed to the constructor.
        * @param {!function()} templateFn
        * @param {(Element|string)=} opt_element The element that should be decorated. If none is given,
        *   one will be created and appended to the document body.
        * @param {Object=} opt_data Data to be passed to the soy template when it's called.
        * @return {!SoyComponent}
        * @static
        */
            }, {
                key: 'generateSurfaceElementId_',

                /**
        * Generates the id for a surface that was found by a soy template call.
        * @param {string?} parentSurfaceId The id of the parent surface, or undefined
        *   if there is none.
        * @param {!Object} data The placeholder data registered for this surface.
        * @return {string} The generated id.
        * @override
        */
                value: function generateSurfaceElementId_(parentSurfaceId, data) {
                    if (data.templateName && !parentSurfaceId && !this.firstSurfaceFound_[data.templateName]) {
                        this.firstSurfaceFound_[data.templateName] = true;
                        return this.prefixSurfaceId_(data.templateName);
                    } else {
                        return _get(Object.getPrototypeOf(SoyComponent.prototype), 'generateSurfaceElementId_', this).call(this, parentSurfaceId);
                    }
                }

                /**
        * Gets the content that should be rendered in the component's main element by
        * rendering the `content` soy template.
        * @return {?string} The template's result content, or undefined if the
        *   template doesn't exist.
        */
            }, {
                key: 'getElementContent',
                value: function getElementContent() {
                    this.surfaceBeingRendered_ = null;
                    return this.renderTemplateByName_(this.constructor.NAME, 'content');
                }

                /**
        * Makes the default behavior of rendering surfaces automatically render the
        * appropriate soy template when one exists.
        * @param {string} surfaceId The surface id.
        * @param {string} surfaceElementId The surface element id.
        * @return {Object|string} The content to be rendered.
        * @override
        */
            }, {
                key: 'getSurfaceContent',
                value: function getSurfaceContent(surfaceId, surfaceElementId) {
                    var surface = this.getSurface(surfaceId);
                    var data = surface.templateData;
                    surface.templateData = null;
                    this.surfaceBeingRendered_ = surfaceElementId;
                    return this.renderTemplateByName_(surface.templateComponentName, surface.templateName, data);
                }

                /**
        * Handles a call to the SoyComponent component template.
        * @param {string} componentName The component's name.
        * @param {Object} data The data the template was called with.
        * @return {string} A placeholder to be rendered instead of the content the template
        *   function would have returned.
        * @protected
        */
            }, {
                key: 'handleComponentCall_',
                value: function handleComponentCall_(componentName, data) {
                    var surfaceData = {
                        componentName: componentName
                    };
                    var id = (data || {}).id || this.generateSurfaceElementId_(this.surfaceBeingRendered_, surfaceData);
                    surfaceData.componentData = this.buildComponentConfigData_(id, data);
                    return this.buildPlaceholder(id, surfaceData);
                }

                /**
        * Handles a call to the soy function for getting delegate functions.
        * @param {string} templateComponentName The name of the component that this template was belongs to.
        * @param {string} templateName The name of this template.
        * @param {!function()} originalFn The original template function that was intercepted.
        * @param {!Object} data The data the template was called with.
        * @param {*} opt_ignored
        * @param {Object} opt_ijData Template injected data object.
        * @return {string}
        * @protected
        */
            }, {
                key: 'handleInterceptedCall_',
                value: function handleInterceptedCall_(templateComponentName, templateName, originalFn, data, opt_ignored, opt_ijData) {
                    if (this.skipInnerCalls_) {
                        return '';
                    } else if (templateName === 'content') {
                        return this.handleComponentCall_.call(this, templateComponentName, data);
                    } else {
                        return this.handleSurfaceCall_.call(this, templateComponentName, templateName, originalFn, data, opt_ignored, opt_ijData);
                    }
                }

                /**
        * Handles a `renderSurface` event. Clears the `firstSurfaceFound_` variable so we can
        * find the anonymous surfaces that should be named after their template names again,
        * once the main content surface is to be rerendered.
        * @param {!Object} data
        * @protected
        */
            }, {
                key: 'handleSoyComponentRenderSurface_',
                value: function handleSoyComponentRenderSurface_(data) {
                    if (data.surfaceElementId === this.id) {
                        this.firstSurfaceFound_ = {};
                    }
                }

                /**
        * Handles a call to the SoyComponent surface template.
        * @param {string} templateComponentName The name of the component that this template was belongs to.
        * @param {string} templateName The name of this template.
        * @param {!function()} originalFn The original template function that was intercepted.
        * @param {!Object} data The data the template was called with.
        * @param {*} opt_ignored
        * @param {Object} opt_ijData Template injected data object.
        * @return {string} A placeholder to be rendered instead of the content the template
        *   function would have returned.
        * @protected
        */
            }, {
                key: 'handleSurfaceCall_',
                value: function handleSurfaceCall_(templateComponentName, templateName, originalFn, data, opt_ignored, opt_ijData) {
                    var surfaceData = {
                        templateComponentName: templateComponentName,
                        templateData: data,
                        templateName: templateName
                    };
                    var surfaceElementId;
                    if (_core['default'].isDefAndNotNull(data.surfaceId)) {
                        surfaceElementId = this.getSurfaceElementId_(data.surfaceId.toString());
                    } else {
                        if (originalFn['private']) {
                            return originalFn.call(null, data, opt_ignored, opt_ijData);
                        }
                        surfaceElementId = this.generateSurfaceElementId_(this.surfaceBeingRendered_, surfaceData);
                    }
                    return this.buildPlaceholder(surfaceElementId, surfaceData);
                }

                /**
        * Checks if a template is a surface template.
        * @param {string} templateName
        * @param {!function()} templateFn
        * @return {boolean}
        * @protected
        */
            }, {
                key: 'isSurfaceTemplate_',
                value: function isSurfaceTemplate_(templateName, templateFn) {
                    return templateName !== 'content' && templateName.substr(0, 13) !== '__deltemplate' && !templateFn['private'];
                }

                /**
        * Renders the given soy template function, instantiating any referenced components in it.
        * @param {!function()} templateFn
        * @param {(Element|string)=} opt_element The element that should be decorated. If none is given,
        *   one will be created and appended to the document body.
        * @param {Object=} opt_data Data to be passed to the soy template when it's called.
        * @return {!SoyComponent} The component that was created for this action. Contains
        *   references to components that were rendered by the given template function.
        * @static
        */
            }, {
                key: 'renderTemplate_',

                /**
        * Renders the specified template.
        * @param {!function()} templateFn
        * @param {Object=} opt_data
        * @return {string} The template's result content.
        */
                value: function renderTemplate_(templateFn, opt_data) {
                    _SoyComponentAop['default'].startInterception(this.handleInterceptedCall_.bind(this));
                    templateFn = _SoyComponentAop['default'].getOriginalFn(templateFn);
                    var content = templateFn(opt_data || this.buildTemplateData_(), null, ijData).content;
                    _SoyComponentAop['default'].stopInterception();
                    return content;
                }

                /**
        * Renders the template with the specified name.
        * @param {string} templateComponentName
        * @param {string} templateName
        * @param {Object=} opt_data
        * @return {string} The template's result content.
        */
            }, {
                key: 'renderTemplateByName_',
                value: function renderTemplateByName_(templateComponentName, templateName, opt_data) {
                    var elementTemplate;
                    var componentTemplates = _ComponentRegistry['default'].Templates[templateComponentName];
                    if (componentTemplates) {
                        elementTemplate = componentTemplates[templateName];
                    }

                    if (_core['default'].isFunction(elementTemplate)) {
                        return this.renderTemplate_(elementTemplate, opt_data);
                    }
                }

                /**
        * Sanitizes the given html string, so it can skip escaping when passed to a
        * soy template.
        * @param {string} html
        * @return {soydata.SanitizedHtml}
        * @static
        */
            }, {
                key: 'valueElementFn_',

                /**
        * Overrides the original method from `Component` so only the outer soy
        * template returns content, as we only need to render the parent tag here.
        * @return {!Element}
        * @protected
        * @override
        */
                value: function valueElementFn_() {
                    this.skipInnerCalls_ = true;
                    var element = _get(Object.getPrototypeOf(SoyComponent.prototype), 'valueElementFn_', this).call(this);
                    this.skipInnerCalls_ = false;
                    return element;
                }
            }], [{
                key: 'createComponentFromTemplate',
                value: function createComponentFromTemplate(templateFn, opt_element, opt_data) {
                    var element = opt_element ? _dom['default'].toElement(opt_element) : null;
                    var data = _object['default'].mixin({
                        id: element ? element.id : null
                    }, opt_data, {
                        element: element
                    });

                    var name = 'TemplateComponent' + _core['default'].getUid();

                    var TemplateComponent = (function (_SoyComponent) {
                        _inherits(TemplateComponent, _SoyComponent);

                        function TemplateComponent() {
                            _classCallCheck(this, TemplateComponent);

                            _get(Object.getPrototypeOf(TemplateComponent.prototype), 'constructor', this).apply(this, arguments);
                        }

                        return TemplateComponent;
                    })(SoyComponent);

                    _ComponentRegistry['default'].register(name, TemplateComponent);
                    _ComponentRegistry['default'].Templates[name] = {
                        content: function content(opt_attrs, opt_ignored, opt_ijData) {
                            return _SoyComponentAop['default'].getOriginalFn(templateFn)(data, opt_ignored, opt_ijData);
                        }
                    };
                    _SoyComponentAop['default'].registerTemplates(name);
                    return new TemplateComponent(data);
                }

                /**
        * Decorates html rendered by the given soy template function, instantiating any referenced
        * components in it.
        * @param {!function()} templateFn
        * @param {(Element|string)=} opt_element The element that should be decorated. If none is given,
        *   one will be created and appended to the document body.
        * @param {Object=} opt_data Data to be passed to the soy template when it's called.
        * @return {!SoyComponent} The component that was created for this action. Contains
        *   references to components that were rendered by the given template function.
        * @static
        */
            }, {
                key: 'decorateFromTemplate',
                value: function decorateFromTemplate(templateFn, opt_element, opt_data) {
                    return SoyComponent.createComponentFromTemplate(templateFn, opt_element, opt_data).decorate();
                }
            }, {
                key: 'renderFromTemplate',
                value: function renderFromTemplate(templateFn, opt_element, opt_data) {
                    return SoyComponent.createComponentFromTemplate(templateFn, opt_element, opt_data).render();
                }
            }, {
                key: 'sanitizeHtml',
                value: function sanitizeHtml(html) {
                    return soydata.VERY_UNSAFE.ordainSanitizedHtml(html);
                }

                /**
        * Sets the injected data object that should be passed to templates.
        * @param {Object} data
        * @static
        */
            }, {
                key: 'setInjectedData',
                value: function setInjectedData(data) {
                    ijData = data || {};
                }
            }]);

            return SoyComponent;
        })(_Component2['default']);

        var originalSanitizedHtmlFromFn = soydata.SanitizedHtml.from;
        soydata.SanitizedHtml.from = function (value) {
            if (value && value.contentKind === 'HTML') {
                value = SoyComponent.sanitizeHtml(value.content);
            }
            return originalSanitizedHtmlFromFn(value);
        };

        module.exports = SoyComponent;
    }
);