define(['exports', 'module', 'metal/src/core', 'metal/src/component/Component', 'metal/src/component/ComponentRegistry', 'metal/src/soy/SoyComponentAop'], function (exports, module, _metalSrcCore, _metalSrcComponentComponent, _metalSrcComponentComponentRegistry, _metalSrcSoySoyComponentAop) {
	'use strict';

	var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

	var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

	function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) subClass.__proto__ = superClass; }

	var _core = _interopRequireDefault(_metalSrcCore);

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

			this.addSurfacesFromTemplates_(opt_config);

			/**
    * Indicates which surface is currently being rendered, or null if none is.
    * @type {boolean}
    * @protected
    */
			this.surfaceBeingRendered_ = null;

			/**
    * Flags indicating which surface names have already been found on this component's content.
    * @type {!Object<string, boolean>}
    * @protected
    */
			this.firstSurfaceFound_ = {};

			/**
    * Holds the data that should be passed to the next template call for a surface,
    * mapped by surface id.
    * @type {!Object<string, Object>}
    * @protected
    */
			this.nextSurfaceCallData_ = {};
		}

		_createClass(SoyComponent, [{
			key: 'addSurfacesFromTemplates_',

			/**
    * Adds surfaces for each registered template that is not named `element`.
    * @param {Object=} opt_config Optional component configuration.
    * @protected
    */
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
					}
				}
			}
		}, {
			key: 'buildComponentConfigData_',

			/**
    * Builds the config data for a component from the data that was passed to its
    * soy template function.
    * @param {string} id The id of the component.
    * @param {!Object} templateData
    * @return {!Object} The component's config data.
    * @protected
    */
			value: function buildComponentConfigData_(id, templateData) {
				var config = {
					id: id
				};
				for (var key in templateData) {
					config[key] = templateData[key];
				}
				return config;
			}
		}, {
			key: 'buildPlaceholderSurfaceData_',

			/**
    * Adds the template name to the creation data for placeholder surfaces.
    * @param {string} type The surface type (either "s" or "c").
    * @param {string} extra String with extra information about the surface.
    * @return {!Object}
    * @protected
    */
			value: function buildPlaceholderSurfaceData_(type, extra) {
				var data = _get(Object.getPrototypeOf(SoyComponent.prototype), 'buildPlaceholderSurfaceData_', this).call(this, type, extra);
				if (type === _Component2['default'].SurfaceType.NORMAL) {
					var split = extra.split('.');
					data.templateComponentName = split[0];
					data.templateName = split[1];
				}
				return data;
			}
		}, {
			key: 'buildTemplateData_',

			/**
    * Builds the data object that should be passed to a template from this component.
    * @return {!Object}
    * @protected
    */
			value: function buildTemplateData_() {
				var names = this.getAttrNames().filter(function (name) {
					// Get all attribute values except for "element", since it helps performance and this
					// attribute shouldn't be referenced inside a soy template anyway.
					return name !== 'element';
				});
				return this.getAttrs(names);
			}
		}, {
			key: 'generateSoySurfaceId_',

			/**
    * Generates the id for a surface that was found by a soy template call.
    * @param {string} templateComponentName
    * @param {string} templateName
    * @return {string}
    */
			value: function generateSoySurfaceId_(templateComponentName, templateName) {
				if (!this.surfaceBeingRendered_ && !this.firstSurfaceFound_[templateName] && templateComponentName === this.constructor.NAME) {
					this.firstSurfaceFound_[templateName] = true;
					return templateName;
				} else {
					return this.generateSurfaceId_(_Component2['default'].SurfaceType.NORMAL, this.surfaceBeingRendered_);
				}
			}
		}, {
			key: 'getElementContent',

			/**
    * Gets the content that should be rendered in the component's main element by
    * rendering the `content` soy template.
    * @return {?string} The template's result content, or undefined if the
    *   template doesn't exist.
    */
			value: function getElementContent() {
				this.surfaceBeingRendered_ = null;
				return this.renderTemplateByName_(this.constructor.NAME, 'content');
			}
		}, {
			key: 'getSurfaceContent',

			/**
    * Makes the default behavior of rendering surfaces automatically render the
    * appropriate soy template when one exists.
    * @param {string} surfaceId The surface id.
    * @return {Object|string} The content to be rendered.
    * @override
    */
			value: function getSurfaceContent(surfaceId) {
				var surface = this.getSurface(surfaceId);
				var data = this.nextSurfaceCallData_[surfaceId];
				this.nextSurfaceCallData_[surfaceId] = null;
				this.surfaceBeingRendered_ = surfaceId;
				return this.renderTemplateByName_(surface.templateComponentName, surface.templateName, data);
			}
		}, {
			key: 'handleComponentCall_',

			/**
    * Handles a call to the SoyComponent component template.
    * @param {string} componentName The component's name.
    * @param {Object} data The data the template was called with.
    * @return {string} A placeholder to be rendered instead of the content the template
    *   function would have returned.
    * @protected
    */
			value: function handleComponentCall_(componentName, data) {
				var id = (data || {}).id || this.generateSurfaceId_(_Component2['default'].SurfaceType.COMPONENT, this.surfaceBeingRendered_);
				_Component2['default'].componentsCollector.setNextComponentData(id, this.buildComponentConfigData_(id, data));
				return '%%%%~c-' + id + ':' + componentName + '~%%%%';
			}
		}, {
			key: 'handleInterceptedCall_',

			/**
    * Handles a call to the soy function for getting delegate functions.
    * @param {string} templateComponentName The name of the component that this template was belongs to.
    * @param {string} templateName The name of this template.
    * @param {!Object} data The data the template was called with.
    * @return {string}
    * @protected
    */
			value: function handleInterceptedCall_(templateComponentName, templateName, data) {
				if (templateName === 'content') {
					return this.handleComponentCall_.call(this, templateComponentName, data);
				} else {
					return this.handleSurfaceCall_.call(this, templateComponentName, templateName, data);
				}
			}
		}, {
			key: 'handleSurfaceCall_',

			/**
    * Handles a call to the SoyComponent surface template.
    * @param {string} templateComponentName The name of the component that this template was belongs to.
    * @param {string} templateName The name of this template.
    * @param {!Object} data The data the template was called with.
    * @return {string} A placeholder to be rendered instead of the content the template
    *   function would have returned.
    * @protected
    */
			value: function handleSurfaceCall_(templateComponentName, templateName, data) {
				var surfaceId = data.surfaceId;
				if (!_core['default'].isDefAndNotNull(surfaceId)) {
					surfaceId = this.generateSoySurfaceId_(templateComponentName, templateName);
				}
				this.nextSurfaceCallData_[surfaceId] = data;
				return '%%%%~s-' + surfaceId + ':' + templateComponentName + '.' + templateName + '~%%%%';
			}
		}, {
			key: 'isSurfaceTemplate_',

			/**
    * Checks if a template is a surface template.
    * @param {string} templateName
    * @param {!function()} templateFn
    * @return {boolean}
    * @protected
    */
			value: function isSurfaceTemplate_(templateName, templateFn) {
				return templateName !== 'content' && templateName.substr(0, 13) !== '__deltemplate' && templateFn.params;
			}
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
		}, {
			key: 'renderTemplateByName_',

			/**
    * Renders the template with the specified name.
    * @param {string} templateComponentName
    * @param {string} templateName
    * @param {Object=} opt_data
    * @return {string} The template's result content.
    */
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
		}], [{
			key: 'createComponentFromTemplate',

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
			value: function createComponentFromTemplate(templateFn, opt_element, opt_data) {
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
						return _SoyComponentAop['default'].getOriginalFn(templateFn)(opt_data || {}, opt_ignored, opt_ijData);
					}
				};
				_SoyComponentAop['default'].registerTemplates(name);
				return new TemplateComponent({
					element: opt_element
				});
			}
		}, {
			key: 'decorateFromTemplate',

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
			value: function decorateFromTemplate(templateFn, opt_element, opt_data) {
				return SoyComponent.createComponentFromTemplate(templateFn, opt_element, opt_data).decorate();
			}
		}, {
			key: 'renderFromTemplate',

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
			value: function renderFromTemplate(templateFn, opt_element, opt_data) {
				return SoyComponent.createComponentFromTemplate(templateFn, opt_element, opt_data).render();
			}
		}, {
			key: 'sanitizeHtml',

			/**
    * Sanitizes the given html string, so it can skip escaping when passed to a
    * soy template.
    * @param {string} html
    * @return {soydata.SanitizedHtml}
    * @static
    */
			value: function sanitizeHtml(html) {
				return soydata.VERY_UNSAFE.ordainSanitizedHtml(html);
			}
		}, {
			key: 'setInjectedData',

			/**
    * Sets the injected data object that should be passed to templates.
    * @param {Object} data
    * @static
    */
			value: function setInjectedData(data) {
				ijData = data || {};
			}
		}]);

		return SoyComponent;
	})(_Component2['default']);

	module.exports = SoyComponent;
});