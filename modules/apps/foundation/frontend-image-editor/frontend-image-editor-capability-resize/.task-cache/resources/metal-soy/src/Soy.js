define(['exports', 'metal/src/metal', 'metal-component/src/all/component', 'html2incdom/src/withParser', 'metal-incremental-dom/src/IncrementalDomRenderer', './SoyAop', 'metal-soy-bundle/build/bundle'], function (exports, _metal, _component, _withParser, _IncrementalDomRenderer, _SoyAop) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.SoyAop = exports.Soy = undefined;

	var _withParser2 = _interopRequireDefault(_withParser);

	var _IncrementalDomRenderer2 = _interopRequireDefault(_IncrementalDomRenderer);

	var _SoyAop2 = _interopRequireDefault(_SoyAop);

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

	var Soy = function (_IncrementalDomRender) {
		_inherits(Soy, _IncrementalDomRender);

		/**
   * @inheritDoc
   */

		function Soy(comp) {
			_classCallCheck(this, Soy);

			var _this = _possibleConstructorReturn(this, _IncrementalDomRender.call(this, comp));

			_this.addMissingStateKeys_();
			return _this;
		}
		/**
   * Adds the template params to the component's state, if they don't exist yet.
   * @protected
   */


		Soy.prototype.addMissingStateKeys_ = function addMissingStateKeys_() {
			var elementTemplate = this.component_.constructor.TEMPLATE;
			if (!_metal.core.isFunction(elementTemplate)) {
				return;
			}

			elementTemplate = _SoyAop2.default.getOriginalFn(elementTemplate);
			this.soyParamTypes_ = elementTemplate.types || {};

			var keys = elementTemplate.params || [];
			var component = this.component_;
			for (var i = 0; i < keys.length; i++) {
				if (!component.getStateKeyConfig(keys[i]) && !component[keys[i]]) {
					component.addToState(keys[i], {}, component.getInitialConfig()[keys[i]]);
				}
			}
		};

		Soy.prototype.buildTemplateData_ = function buildTemplateData_(params) {
			var _this2 = this;

			var component = this.component_;
			var data = _metal.object.mixin({}, component.config);
			component.getStateKeys().forEach(function (key) {
				// Get all state values except "element", since it helps performance
				// and the element shouldn't be referenced inside a soy template anyway.
				if (key === 'element') {
					return;
				}

				var value = component[key];
				if (_this2.isHtmlParam_(key)) {
					value = Soy.toIncDom(value);
				}
				data[key] = value;
			});
			for (var i = 0; i < params.length; i++) {
				if (!data[params[i]] && _metal.core.isFunction(component[params[i]])) {
					data[params[i]] = component[params[i]].bind(component);
				}
			}
			return data;
		};

		Soy.getTemplate = function getTemplate(namespace, templateName) {
			return function (opt_data, opt_ignored, opt_ijData) {
				if (!goog.loadedModules_[namespace]) {
					throw new Error('No template with namespace "' + namespace + '" has been loaded yet.');
				}
				return goog.loadedModules_[namespace][templateName](opt_data, opt_ignored, opt_ijData);
			};
		};

		Soy.handleInterceptedCall_ = function handleInterceptedCall_(originalFn) {
			var opt_data = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

			var args = [originalFn.componentCtor, null, []];
			for (var key in opt_data) {
				args.push(key, opt_data[key]);
			}
			IncrementalDOM.elementVoid.apply(null, args);
		};

		Soy.prototype.isHtmlParam_ = function isHtmlParam_(name) {
			if (this.component_.getStateKeyConfig(name).isHtml) {
				return true;
			}
			var type = this.soyParamTypes_[name] || '';
			return type.split('|').indexOf('html') !== -1;
		};

		Soy.register = function register(componentCtor, templates) {
			var mainTemplate = arguments.length <= 2 || arguments[2] === undefined ? 'render' : arguments[2];

			componentCtor.RENDERER = Soy;
			componentCtor.TEMPLATE = _SoyAop2.default.getOriginalFn(templates[mainTemplate]);
			componentCtor.TEMPLATE.componentCtor = componentCtor;
			_SoyAop2.default.registerForInterception(templates, mainTemplate);
			_component.ComponentRegistry.register(componentCtor);
		};

		Soy.prototype.renderIncDom = function renderIncDom() {
			var elementTemplate = this.component_.constructor.TEMPLATE;
			if (_metal.core.isFunction(elementTemplate) && !this.component_.render) {
				elementTemplate = _SoyAop2.default.getOriginalFn(elementTemplate);
				_SoyAop2.default.startInterception(Soy.handleInterceptedCall_);
				elementTemplate(this.buildTemplateData_(elementTemplate.params || []), null, ijData);
				_SoyAop2.default.stopInterception();
			} else {
				_IncrementalDomRender.prototype.renderIncDom.call(this);
			}
		};

		Soy.setInjectedData = function setInjectedData(data) {
			ijData = data || {};
		};

		Soy.prototype.shouldUpdate = function shouldUpdate(changes) {
			var should = _IncrementalDomRender.prototype.shouldUpdate.call(this, changes);
			if (!should || this.component_.shouldUpdate) {
				return should;
			}

			var fn = this.component_.constructor.TEMPLATE;
			var params = fn ? _SoyAop2.default.getOriginalFn(fn).params : [];
			for (var i = 0; i < params.length; i++) {
				if (changes[params[i]]) {
					return true;
				}
			}
			return false;
		};

		Soy.toHtmlString = function toHtmlString(incDomFn) {
			var element = document.createElement('div');
			IncrementalDOM.patch(element, incDomFn);
			return element.innerHTML;
		};

		Soy.toIncDom = function toIncDom(value) {
			if (_metal.core.isObject(value) && _metal.core.isString(value.content) && value.contentKind === 'HTML') {
				value = value.content;
			}
			if (_metal.core.isString(value)) {
				value = _withParser2.default.buildFn(value);
			}
			return value;
		};

		return Soy;
	}(_IncrementalDomRenderer2.default);

	exports.default = Soy;
	exports.Soy = Soy;
	exports.SoyAop = _SoyAop2.default;
});
//# sourceMappingURL=Soy.js.map