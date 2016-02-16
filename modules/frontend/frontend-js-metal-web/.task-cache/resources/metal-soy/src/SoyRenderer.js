define("frontend-js-metal-web@1.0.0/metal-soy/src/SoyRenderer", ['exports', 'soyutils', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-component/src/all/component', './SoyAop', './SoyTemplates'], function (exports, soyutils, _metal, _dom, _component, _SoyAop, _SoyTemplates) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	var _SoyAop2 = _interopRequireDefault(_SoyAop);

	var _SoyTemplates2 = _interopRequireDefault(_SoyTemplates);

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

	// The injected data that will be passed to soy templates.
	var ijData = {};

	/**
  * A `ComponentRenderer` that enables components to be rendered via soy templates. It
  * automatically creates surfaces named after each template and uses template params
  * as render attributes. That means that when an attribute value changes, the templates
  * that have a parameter with the same name will be automatically rendered again.
  * @extends {ComponentRenderer}
  */

	var SoyRenderer = function (_ComponentRenderer) {
		_inherits(SoyRenderer, _ComponentRenderer);

		function SoyRenderer() {
			_classCallCheck(this, SoyRenderer);

			return _possibleConstructorReturn(this, _ComponentRenderer.apply(this, arguments));
		}

		SoyRenderer.addSurfacesFromTemplates_ = function addSurfacesFromTemplates_(component) {
			var name = component.getName();
			var templates = _SoyTemplates2.default.get(name);
			var templateNames = Object.keys(templates);
			for (var i = 0; i < templateNames.length; i++) {
				var templateName = templateNames[i];
				var templateFn = _SoyAop2.default.getOriginalFn(templates[templateName]);
				if (SoyRenderer.isSurfaceTemplate_(templateName, templateFn)) {
					var surfaceId = templateName === 'render' ? component.id : templateName;
					component.addSurface(surfaceId, {
						renderAttrs: templateFn.params,
						templateComponentName: name,
						templateName: templateName
					});
				}
			}
		};

		SoyRenderer.buildComponentConfigData_ = function buildComponentConfigData_(id, templateData) {
			var config = {
				id: id
			};
			for (var key in templateData) {
				config[key] = templateData[key];
			}
			return config;
		};

		SoyRenderer.buildTemplateData_ = function buildTemplateData_(component) {
			var names = component.getAttrNames().filter(function (name) {
				// Get all attribute values except for "element", since it helps performance and this
				// attribute shouldn't be referenced inside a soy template anyway.
				return name !== 'element';
			});
			var surface = component.getSurface(component.id);
			var data = surface && surface.componentData ? surface.componentData : {};
			var attrs = _metal.object.map(component.getAttrs(names), function (key, value) {
				if (component.getAttrConfig(key).isHtml && _metal.core.isString(value)) {
					return SoyRenderer.sanitizeHtml(value);
				} else {
					return value;
				}
			});
			return _metal.object.mixin(data, attrs);
		};

		SoyRenderer.createComponentFromTemplate = function createComponentFromTemplate(templateFn, opt_element, opt_data) {
			var element = opt_element ? _dom2.default.toElement(opt_element) : null;
			var data = _metal.object.mixin({
				id: element ? element.id : null
			}, opt_data, {
				element: element
			});

			var name = 'TemplateComponent' + _metal.core.getUid();

			var TemplateComponent = function (_Component) {
				_inherits(TemplateComponent, _Component);

				function TemplateComponent() {
					_classCallCheck(this, TemplateComponent);

					return _possibleConstructorReturn(this, _Component.apply(this, arguments));
				}

				return TemplateComponent;
			}(_component.Component);

			TemplateComponent.prototype.registerMetalComponent && TemplateComponent.prototype.registerMetalComponent(TemplateComponent, 'TemplateComponent')

			TemplateComponent.RENDERER = SoyRenderer;
			_component.ComponentRegistry.register(TemplateComponent, name);
			_SoyTemplates2.default.set(name, {
				render: function render(opt_attrs, opt_ignored, opt_ijData) {
					return _SoyAop2.default.getOriginalFn(templateFn)(data, opt_ignored, opt_ijData);
				}
			});
			_SoyAop2.default.registerTemplates(name);
			return new TemplateComponent(data);
		};

		SoyRenderer.decorateFromTemplate = function decorateFromTemplate(templateFn, opt_element, opt_data) {
			return SoyRenderer.createComponentFromTemplate(templateFn, opt_element, opt_data).decorate();
		};

		SoyRenderer.generateSurfaceElementId = function generateSurfaceElementId(component, parentSurfaceId, data) {
			if (data.templateName && parentSurfaceId === component.id && !SoyRenderer.firstSurfaceFound_[data.templateName]) {
				SoyRenderer.firstSurfaceFound_[data.templateName] = true;
				return component.prefixSurfaceId(data.templateName);
			} else {
				return component.generateSurfaceElementId(parentSurfaceId);
			}
		};

		SoyRenderer.getSurfaceContent = function getSurfaceContent(surface, component, opt_skipContents) {
			if (surface.surfaceElementId === component.id) {
				if (!surface.renderAttrs) {
					this.addSurfacesFromTemplates_(component);
				}
				SoyRenderer.firstSurfaceFound_ = {};
			}

			SoyRenderer.surfaceBeingRendered_ = surface.surfaceElementId;
			SoyRenderer.skipInnerCalls_ = SoyRenderer.skipInnerCalls_ || opt_skipContents;

			var data = surface.templateData;
			surface.templateData = null;
			var content = SoyRenderer.renderTemplateByName_(component, surface.templateComponentName, surface.templateName, data);

			SoyRenderer.surfaceBeingRendered_ = null;
			SoyRenderer.skipInnerCalls_ = false;
			return content;
		};

		SoyRenderer.handleComponentCall_ = function handleComponentCall_(component, componentName, data) {
			var surfaceData = {
				componentName: componentName
			};
			var id = (data || {}).id;
			if (!id) {
				id = SoyRenderer.generateSurfaceElementId(component, SoyRenderer.surfaceBeingRendered_, surfaceData);
			}
			surfaceData.componentData = SoyRenderer.buildComponentConfigData_(id, data);
			return component.buildPlaceholder(id, surfaceData);
		};

		SoyRenderer.handleInterceptedCall_ = function handleInterceptedCall_(component, templateComponentName, templateName, originalFn, data, opt_ignored, opt_ijData) {
			if (SoyRenderer.skipInnerCalls_) {
				return '';
			} else if (templateName === 'render') {
				return this.handleComponentCall_.call(this, component, templateComponentName, data);
			} else {
				return this.handleSurfaceCall_.call(this, component, templateComponentName, templateName, originalFn, data, opt_ignored, opt_ijData);
			}
		};

		SoyRenderer.handleSurfaceCall_ = function handleSurfaceCall_(component, templateComponentName, templateName, originalFn, data, opt_ignored, opt_ijData) {
			var surfaceData = {
				static: originalFn.static,
				templateComponentName: templateComponentName,
				templateData: data,
				templateName: templateName
			};
			var surfaceElementId;
			if (_metal.core.isDefAndNotNull(data.surfaceElementId)) {
				surfaceElementId = data.surfaceElementId;
			} else if (_metal.core.isDefAndNotNull(data.surfaceId)) {
				surfaceElementId = component.getSurfaceElementId(data.surfaceId.toString());
			} else {
				if (originalFn.private) {
					return originalFn.call(null, data, opt_ignored, opt_ijData);
				}
				surfaceElementId = SoyRenderer.generateSurfaceElementId(component, SoyRenderer.surfaceBeingRendered_, surfaceData);
			}
			return component.buildPlaceholder(surfaceElementId, surfaceData);
		};

		SoyRenderer.isSurfaceTemplate_ = function isSurfaceTemplate_(templateName, templateFn) {
			return templateName.substr(0, 13) !== '__deltemplate' && !templateFn.private;
		};

		SoyRenderer.renderFromTemplate = function renderFromTemplate(templateFn, opt_element, opt_data) {
			return SoyRenderer.createComponentFromTemplate(templateFn, opt_element, opt_data).render();
		};

		SoyRenderer.renderTemplate_ = function renderTemplate_(component, templateFn, opt_data) {
			_SoyAop2.default.startInterception(SoyRenderer.handleInterceptedCall_.bind(SoyRenderer, component));
			templateFn = _SoyAop2.default.getOriginalFn(templateFn);
			var content = templateFn(opt_data || SoyRenderer.buildTemplateData_(component), null, ijData).content;
			_SoyAop2.default.stopInterception();
			return content;
		};

		SoyRenderer.renderTemplateByName_ = function renderTemplateByName_(component, templateComponentName, templateName, opt_data) {
			var elementTemplate = _SoyTemplates2.default.get(templateComponentName, templateName);
			if (_metal.core.isFunction(elementTemplate)) {
				return SoyRenderer.renderTemplate_(component, elementTemplate, opt_data);
			}
		};

		SoyRenderer.sanitizeHtml = function sanitizeHtml(html) {
			return soydata.VERY_UNSAFE.ordainSanitizedHtml(html);
		};

		SoyRenderer.setInjectedData = function setInjectedData(data) {
			ijData = data || {};
		};

		return SoyRenderer;
	}(_component.ComponentRenderer);

	SoyRenderer.prototype.registerMetalComponent && SoyRenderer.prototype.registerMetalComponent(SoyRenderer, 'SoyRenderer')


	var originalSanitizedHtmlFromFn = soydata.SanitizedHtml.from;
	soydata.SanitizedHtml.from = function (value) {
		if (value && value.contentKind === 'HTML') {
			value = SoyRenderer.sanitizeHtml(value.content);
		}
		return originalSanitizedHtmlFromFn(value);
	};

	exports.default = SoyRenderer;
});
//# sourceMappingURL=SoyRenderer.js.map