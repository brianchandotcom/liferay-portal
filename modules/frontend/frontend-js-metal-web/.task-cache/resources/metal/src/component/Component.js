'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/metal/src/component/Component", ['exports', 'metal/src/array/array', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/dom/features', 'metal/src/eval/globalEval', 'metal/src/html/html', 'metal/src/object/object', 'metal/src/string/string', 'metal/src/attribute/Attribute', 'metal/src/component/ComponentCollector', 'metal/src/component/ComponentRenderer', 'metal/src/events/EventEmitterProxy', 'metal/src/events/EventHandler', 'metal/src/component/EventsCollector', 'metal/src/component/SurfaceCollector'], function (exports, _array, _core, _dom, _features, _globalEval, _html, _object, _string, _Attribute2, _ComponentCollector, _ComponentRenderer, _EventEmitterProxy, _EventHandler, _EventsCollector, _SurfaceCollector) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _array2 = _interopRequireDefault(_array);

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _features2 = _interopRequireDefault(_features);

	var _globalEval2 = _interopRequireDefault(_globalEval);

	var _html2 = _interopRequireDefault(_html);

	var _object2 = _interopRequireDefault(_object);

	var _string2 = _interopRequireDefault(_string);

	var _Attribute3 = _interopRequireDefault(_Attribute2);

	var _ComponentCollector2 = _interopRequireDefault(_ComponentCollector);

	var _ComponentRenderer2 = _interopRequireDefault(_ComponentRenderer);

	var _EventEmitterProxy2 = _interopRequireDefault(_EventEmitterProxy);

	var _EventHandler2 = _interopRequireDefault(_EventHandler);

	var _EventsCollector2 = _interopRequireDefault(_EventsCollector);

	var _SurfaceCollector2 = _interopRequireDefault(_SurfaceCollector);

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

		return call && ((typeof call === 'undefined' ? 'undefined' : _typeof(call)) === "object" || typeof call === "function") ? call : self;
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

	var Component = (function (_Attribute) {
		_inherits(Component, _Attribute);

		function Component(opt_config) {
			_classCallCheck(this, Component);

			var _this = _possibleConstructorReturn(this, _Attribute.call(this, opt_config));

			_this.collectedSurfaces_ = [];
			_this.components = {};
			_this.decorating_ = false;
			_this.delegateEventHandler_ = null;
			_this.elementEventProxy_ = null;
			_this.eventsAttrHandler_ = new _EventHandler2.default();
			_this.eventsCollector_ = new _EventsCollector2.default(_this);
			_this.generatedIdCount_ = {};
			_this.inDocument = false;
			_this.initialConfig_ = opt_config || {};
			_this.removedSurfaces_ = [];
			_this.surfaceIds_ = {};
			_this.wasRendered = false;
			_this.DEFAULT_ELEMENT_PARENT = document.body;

			_core2.default.mergeSuperClassesProperty(_this.constructor, 'ELEMENT_CLASSES', _this.mergeElementClasses_);

			_core2.default.mergeSuperClassesProperty(_this.constructor, 'ELEMENT_TAG_NAME', _array2.default.firstDefinedValue);

			_core2.default.mergeSuperClassesProperty(_this.constructor, 'RENDERER', _array2.default.firstDefinedValue);

			_core2.default.mergeSuperClassesProperty(_this.constructor, 'SURFACE_TAG_NAME', _array2.default.firstDefinedValue);

			_this.addSurfacesFromStaticHint_();

			_this.delegateEventHandler_ = new _EventHandler2.default();

			_this.created_();

			return _this;
		}

		Component.prototype.addListenersFromObj_ = function addListenersFromObj_(events) {
			var eventNames = Object.keys(events || {});

			for (var i = 0; i < eventNames.length; i++) {
				var info = this.extractListenerInfo_(events[eventNames[i]]);

				if (info.fn) {
					var handler;

					if (info.selector) {
						handler = this.delegate(eventNames[i], info.selector, info.fn);
					} else {
						handler = this.on(eventNames[i], info.fn);
					}

					this.eventsAttrHandler_.add(handler);
				}
			}
		};

		Component.prototype.addMissingAttr_ = function addMissingAttr_(attrName, initialValue) {
			if (!this.getAttrConfig(attrName)) {
				this.addAttr(attrName, {}, initialValue);
			}
		};

		Component.prototype.addSingleListener_ = function addSingleListener_(event, listener, opt_origin) {
			if (!this.elementEventProxy_ && _dom2.default.supportsEvent(this.constructor.ELEMENT_TAG_NAME_MERGED, event)) {
				this.elementEventProxy_ = new _EventEmitterProxy2.default(this.element, this);
			}

			_Attribute.prototype.addSingleListener_.call(this, event, listener, opt_origin);
		};

		Component.prototype.addElementSurface_ = function addElementSurface_() {
			if (!this.surfaceIds_[this.id]) {
				this.addSurface(this.id, {
					componentName: this.getName()
				});
			}
		};

		Component.prototype.addSurface = function addSurface(surfaceId, opt_surfaceConfig) {
			var config = opt_surfaceConfig || {};
			var surfaceElementId = this.getSurfaceElementId(surfaceId, config);

			if (this.surfaceIds_[surfaceElementId]) {
				Component.surfacesCollector.updateSurface(surfaceElementId, config);
			} else {
				this.surfaceIds_[surfaceElementId] = true;
				config.cacheState = config.cacheState || Component.Cache.NOT_INITIALIZED;
				Component.surfacesCollector.addSurface(surfaceElementId, config);

				if (config.componentName && surfaceId !== this.id) {
					this.createSubComponent_(config.componentName, surfaceElementId);
				}
			}

			this.cacheSurfaceRenderAttrs_(surfaceElementId, config.renderAttrs);
			return this;
		};

		Component.prototype.addSurfaces = function addSurfaces(configs) {
			for (var surfaceId in configs) {
				this.addSurface(surfaceId, configs[surfaceId]);
			}

			return this;
		};

		Component.prototype.addSurfacesFromStaticHint_ = function addSurfacesFromStaticHint_() {
			_core2.default.mergeSuperClassesProperty(this.constructor, 'SURFACES', this.mergeObjects_);

			this.surfacesRenderAttrs_ = {};
			var configs = this.constructor.SURFACES_MERGED;

			for (var surfaceId in configs) {
				this.addSurface(surfaceId, _object2.default.mixin({}, configs[surfaceId]));
			}
		};

		Component.prototype.addToRemovedSurfaces_ = function addToRemovedSurfaces_(surfaceElementIds) {
			for (var i = 0; i < surfaceElementIds.length; i++) {
				var surface = this.getSurface(surfaceElementIds[i]);
				this.removedSurfaces_.push(surface);
				surface.parent = null;
			}
		};

		Component.prototype.attach = function attach(opt_parentElement, opt_siblingElement) {
			if (!this.inDocument) {
				this.renderElement_(opt_parentElement, opt_siblingElement);
				this.inDocument = true;

				if (!this.wasRendered) {
					this.updatePlaceholderSurfaces_();
				}

				this.attached();
			}

			return this;
		};

		Component.prototype.attached = function attached() {};

		Component.prototype.buildFragment_ = function buildFragment_(content) {
			var frag = _dom2.default.buildFragment(content);

			if (content.indexOf('<script') === -1) {
				return frag;
			}

			var scripts = frag.querySelectorAll('script');

			for (var i = 0; i < scripts.length; i++) {
				var script = scripts.item(i);

				if (!script.type || script.type === 'text/javascript') {
					_globalEval2.default.runScript(script);
				}
			}

			return frag;
		};

		Component.prototype.buildPlaceholder = function buildPlaceholder(surfaceElementId, opt_data) {
			if (surfaceElementId && opt_data) {
				opt_data.surfaceElementId = surfaceElementId;
				this.addSurface(surfaceElementId, opt_data);
			}

			return '%%%%~s' + (surfaceElementId ? '-' + surfaceElementId : '') + '~%%%%';
		};

		Component.prototype.cacheSurfaceContent = function cacheSurfaceContent(surfaceElementId, content) {
			var cacheState = this.computeSurfaceCacheState_(content);
			var surface = this.getSurfaceFromElementId(surfaceElementId);
			surface.cacheState = cacheState;
		};

		Component.prototype.cacheSurfaceRenderAttrs_ = function cacheSurfaceRenderAttrs_(surfaceElementId, renderAttrs) {
			var attrs = renderAttrs || [];

			for (var i = 0; i < attrs.length; i++) {
				if (!this.surfacesRenderAttrs_[attrs[i]]) {
					this.surfacesRenderAttrs_[attrs[i]] = {};
					this.addMissingAttr_(attrs[i], this.initialConfig_[attrs[i]]);
				}

				this.surfacesRenderAttrs_[attrs[i]][surfaceElementId] = true;
			}
		};

		Component.prototype.checkHasElementTag_ = function checkHasElementTag_(content, id) {
			return content.indexOf(' id="' + id + '"') !== -1;
		};

		Component.prototype.clearSurfaceCache = function clearSurfaceCache(surfaceId) {
			this.getSurface(surfaceId).cacheState = Component.Cache.NOT_INITIALIZED;
		};

		Component.prototype.compareCacheStates_ = function compareCacheStates_(currentCacheState, previousCacheState) {
			return currentCacheState !== Component.Cache.NOT_INITIALIZED && currentCacheState === previousCacheState;
		};

		Component.prototype.computeSurfaceCacheState_ = function computeSurfaceCacheState_(value) {
			value = value || '';

			if (_features2.default.checkAttrOrderChange()) {
				value = this.convertHtmlToBrowserFormat_(value);
			}

			return _string2.default.hashCode(value);
		};

		Component.prototype.convertHtmlToBrowserFormat_ = function convertHtmlToBrowserFormat_(htmlString) {
			var element = document.createElement('div');

			_dom2.default.append(element, htmlString);

			return element.innerHTML;
		};

		Component.prototype.createPlaceholderSurface_ = function createPlaceholderSurface_(parentSurfaceElementId, opt_surfaceElementId) {
			var surfaceElementId = opt_surfaceElementId;

			if (!_core2.default.isDefAndNotNull(surfaceElementId)) {
				surfaceElementId = this.generateSurfaceElementId(parentSurfaceElementId);
			}

			var surface = this.getSurfaceFromElementId(surfaceElementId);

			if (!surface) {
				surface = {
					surfaceElementId: surfaceElementId
				};
				this.addSurface(surfaceElementId, surface);
			}

			return surface;
		};

		Component.prototype.createSubComponent_ = function createSubComponent_(componentName, componentId) {
			this.components[componentId] = Component.componentsCollector.createComponent(componentName, componentId, this.getSurfaceFromElementId(componentId).componentData);
			return this.components[componentId];
		};

		Component.prototype.createSurfaceElement_ = function createSurfaceElement_(surfaceElementId) {
			var el = document.createElement(this.constructor.SURFACE_TAG_NAME_MERGED);
			el.id = surfaceElementId;
			return el;
		};

		Component.prototype.decorateAsSubComponent = function decorateAsSubComponent(opt_content) {
			this.decorating_ = true;
			this.renderAsSubComponent(opt_content);
			this.decorating_ = false;
		};

		Component.prototype.delegate = function delegate(eventName, selector, callback) {
			var handle = _dom2.default.delegate(this.element, eventName, selector, callback);

			this.delegateEventHandler_.add(handle);
			return handle;
		};

		Component.prototype.detach = function detach() {
			if (this.inDocument) {
				if (this.element.parentNode) {
					this.element.parentNode.removeChild(this.element);
				}

				this.inDocument = false;
				this.detached();
			}

			this.eventsCollector_.detachAllListeners();
			return this;
		};

		Component.prototype.detached = function detached() {};

		Component.prototype.created_ = function created_() {
			this.on('eventsChanged', this.onEventsChanged_);
			this.addListenersFromObj_(this.events);
			this.on('attrsChanged', this.handleAttributesChanges_);
			Component.componentsCollector.addComponent(this);
			this.on('renderSurface', this.defaultRenderSurfaceFn_, true);
		};

		Component.prototype.decorate = function decorate() {
			this.decorating_ = true;
			this.render();
			this.decorating_ = false;
			return this;
		};

		Component.prototype.defaultRenderSurfaceFn_ = function defaultRenderSurfaceFn_(data) {
			var surfaceElementId = data.surfaceElementId;
			var surface = this.getSurfaceFromElementId(surfaceElementId);

			if (surface.componentName && surfaceElementId !== this.id) {
				this.renderComponentSurface_(surfaceElementId, data.content);
				return;
			}

			var content = data.content || this.getSurfaceContent_(surfaceElementId);
			var cacheContent = data.cacheContent || content;
			var cacheHit = surface.static;

			if (!surface.static) {
				var previousCacheState = surface.cacheState;
				this.cacheSurfaceContent(surfaceElementId, cacheContent);
				cacheHit = this.compareCacheStates_(surface.cacheState, previousCacheState);
			}

			if (cacheHit) {
				this.renderPlaceholderSurfaceContents_(cacheContent, surfaceElementId);
			} else {
				this.eventsCollector_.attachListeners(cacheContent, surfaceElementId);
				this.replaceSurfaceContent_(surfaceElementId, surface, content);
			}
		};

		Component.prototype.disposeSubComponents_ = function disposeSubComponents_(ids) {
			for (var i = 0; i < ids.length; i++) {
				var component = this.components[ids[i]];

				if (!component.isDisposed()) {
					Component.componentsCollector.removeComponent(component);
					component.dispose();
					delete this.components[ids[i]];
				}
			}
		};

		Component.prototype.disposeInternal = function disposeInternal() {
			var _this2 = this;

			this.detach();

			if (this.elementEventProxy_) {
				this.elementEventProxy_.dispose();
				this.elementEventProxy_ = null;
			}

			this.delegateEventHandler_.removeAllListeners();
			this.delegateEventHandler_ = null;
			this.disposeSubComponents_(Object.keys(this.components));
			this.components = null;
			this.generatedIdCount_ = null;
			this.surfacesRenderAttrs_ = null;
			this.eventsCollector_.dispose();
			this.eventsCollector_ = null;
			Object.keys(this.surfaceIds_).forEach(function (surfaceId) {
				return _this2.removeSurface(surfaceId);
			});
			this.surfaceIds_ = null;

			_Attribute.prototype.disposeInternal.call(this);
		};

		Component.prototype.emitRenderSurfaceEvent_ = function emitRenderSurfaceEvent_(surfaceElementId, opt_content, opt_cacheContent, opt_renderAttrs) {
			this.emit('renderSurface', {
				cacheContent: opt_cacheContent,
				content: opt_content,
				renderAttrs: opt_renderAttrs || [],
				surfaceElementId: surfaceElementId,
				surfaceId: this.getSurfaceId(this.getSurfaceFromElementId(surfaceElementId))
			});
		};

		Component.prototype.extractListenerInfo_ = function extractListenerInfo_(value) {
			var info = {
				fn: value
			};

			if (_core2.default.isObject(value) && !_core2.default.isFunction(value)) {
				info.selector = value.selector;
				info.fn = value.fn;
			}

			if (_core2.default.isString(info.fn)) {
				info.fn = this.eventsCollector_.getListenerFn(info.fn);
			}

			return info;
		};

		Component.prototype.syncAttrs_ = function syncAttrs_() {
			var attrNames = this.getAttrNames();

			for (var i = 0; i < attrNames.length; i++) {
				this.fireAttrChange_(attrNames[i]);
			}
		};

		Component.prototype.syncAttrsFromChanges_ = function syncAttrsFromChanges_(changes) {
			for (var attr in changes) {
				this.fireAttrChange_(attr, changes[attr]);
			}
		};

		Component.prototype.findElementById_ = function findElementById_(id) {
			return document.getElementById(id) || this.element && this.element.querySelector('#' + id);
		};

		Component.prototype.findElementInContent_ = function findElementInContent_(id, content) {
			content = _core2.default.isString(content) ? _dom2.default.buildFragment(content) : content;
			var firstChild = content.childNodes[0];

			if (firstChild && firstChild.id === id) {
				return firstChild;
			}
		};

		Component.prototype.fireAttrChange_ = function fireAttrChange_(attr, opt_change) {
			var fn = this['sync' + attr.charAt(0).toUpperCase() + attr.slice(1)];

			if (_core2.default.isFunction(fn)) {
				if (!opt_change) {
					opt_change = {
						newVal: this[attr],
						prevVal: undefined
					};
				}

				fn.call(this, opt_change.newVal, opt_change.prevVal);
			}
		};

		Component.prototype.generateSurfaceElementId = function generateSurfaceElementId(parentSurfaceElementId) {
			this.generatedIdCount_[parentSurfaceElementId] = (this.generatedIdCount_[parentSurfaceElementId] || 0) + 1;
			return parentSurfaceElementId + '-s' + this.generatedIdCount_[parentSurfaceElementId];
		};

		Component.prototype.getComponentHtml = function getComponentHtml(content) {
			return this.wrapContentIfNecessary(content, this.id, this.constructor.ELEMENT_TAG_NAME_MERGED);
		};

		Component.prototype.getComponentsWithPrefix = function getComponentsWithPrefix(prefix) {
			var _this3 = this;

			var ids = Object.keys(this.components).filter(function (id) {
				return id.indexOf(prefix) === 0;
			});
			var map = {};
			ids.forEach(function (id) {
				return map[id] = _this3.components[id];
			});
			return map;
		};

		Component.prototype.getName = function getName() {
			return this.constructor.NAME || _core2.default.getFunctionName(this.constructor);
		};

		Component.prototype.getElementContent_ = function getElementContent_(opt_skipContents) {
			this.addElementSurface_();
			return this.getRenderer().getSurfaceContent(this.getSurface(this.id), this, opt_skipContents);
		};

		Component.prototype.getElementExtendedContent = function getElementExtendedContent() {
			var content = this.getElementContent_() || '';
			this.eventsCollector_.attachListeners(content, this.id);
			this.cacheSurfaceContent(this.id, content);
			return this.replaceSurfacePlaceholders_(content, this.id, this.getSurface(this.id));
		};

		Component.prototype.getModifiedSurfacesFromChanges_ = function getModifiedSurfacesFromChanges_(changes) {
			var surfaces = {};

			for (var attr in changes) {
				var surfaceNames = Object.keys(this.surfacesRenderAttrs_[attr] || {});

				for (var i = 0; i < surfaceNames.length; i++) {
					if (!surfaces[surfaceNames[i]]) {
						surfaces[surfaceNames[i]] = [];
					}

					surfaces[surfaceNames[i]].push(attr);
				}
			}

			return surfaces;
		};

		Component.prototype.getNonComponentSurfaceHtml = function getNonComponentSurfaceHtml(surfaceElementId, content) {
			return this.wrapContentIfNecessary(content, surfaceElementId, this.constructor.SURFACE_TAG_NAME_MERGED);
		};

		Component.prototype.getRenderer = function getRenderer() {
			return this.constructor.RENDERER_MERGED;
		};

		Component.prototype.getSurface = function getSurface(surfaceId) {
			var surface = this.getSurfaceFromElementId(this.getSurfaceElementId(surfaceId));
			return surface ? surface : this.getSurfaceFromElementId(surfaceId);
		};

		Component.prototype.getSurfaceContent_ = function getSurfaceContent_(surfaceElementId) {
			var surface = this.getSurfaceFromElementId(surfaceElementId);

			if (surface.componentName && surfaceElementId !== this.id) {
				var component = _ComponentCollector2.default.components[surfaceElementId];

				if (component.wasRendered) {
					return '';
				} else {
					return component.getElementExtendedContent();
				}
			} else {
				return this.getRenderer().getSurfaceContent(surface, this) || '';
			}
		};

		Component.prototype.getSurfaceElement = function getSurfaceElement(surfaceId, opt_surface) {
			var surface = opt_surface || this.getSurface(surfaceId);

			if (!surface) {
				return null;
			}

			if (!surface.element) {
				if (surface.componentName) {
					var component = _ComponentCollector2.default.components[surfaceId];

					if (component) {
						surface.element = component.element;
					}
				} else {
					var surfaceElementId = this.getSurfaceElementId(surfaceId, surface);
					surface.element = this.findElementById_(surfaceElementId) || this.createSurfaceElement_(surfaceElementId);
				}
			}

			return surface.element;
		};

		Component.prototype.getSurfaceElementId = function getSurfaceElementId(surfaceId, opt_surface) {
			var surface = opt_surface || {};

			if (surface.surfaceElementId) {
				return surface.surfaceElementId;
			} else if (surface.componentName || this.hasComponentPrefix_(surfaceId)) {
				return surfaceId;
			} else {
				return this.prefixSurfaceId(surfaceId);
			}
		};

		Component.prototype.getSurfaceFromElementId = function getSurfaceFromElementId(surfaceElementId) {
			return Component.surfacesCollector.getSurface(surfaceElementId);
		};

		Component.prototype.getSurfaceHtml_ = function getSurfaceHtml_(surface, content) {
			var surfaceElementId = surface.surfaceElementId;

			if (surface.componentName) {
				return _ComponentCollector2.default.components[surfaceElementId].getComponentHtml(content);
			} else {
				return this.getNonComponentSurfaceHtml(surfaceElementId, content);
			}
		};

		Component.prototype.getSurfaceId = function getSurfaceId(surface) {
			if (surface.componentName || !this.hasComponentPrefix_(surface.surfaceElementId)) {
				return surface.surfaceElementId;
			} else {
				return surface.surfaceElementId.substr(this.id.length + 1);
			}
		};

		Component.prototype.getSurfaces = function getSurfaces() {
			var surfaces = {};
			Object.keys(this.surfaceIds_).forEach((function (surfaceElementId) {
				var surface = this.getSurfaceFromElementId(surfaceElementId);
				surfaces[this.getSurfaceId(surface)] = surface;
			}).bind(this));
			return surfaces;
		};

		Component.prototype.handleAttributesChanges_ = function handleAttributesChanges_(event) {
			if (this.inDocument) {
				this.renderSurfacesContent_(this.getModifiedSurfacesFromChanges_(event.changes));
			}

			this.syncAttrsFromChanges_(event.changes);
			this.emit('attrsSynced', event);
		};

		Component.prototype.hasComponentPrefix_ = function hasComponentPrefix_(surfaceId) {
			return surfaceId.substr(0, this.id.length) === this.id && (surfaceId.length === this.id.length || surfaceId[this.id.length] === '-');
		};

		Component.prototype.onEventsChanged_ = function onEventsChanged_(event) {
			this.eventsAttrHandler_.removeAllListeners();
			this.addListenersFromObj_(event.newVal);
		};

		Component.prototype.makeId_ = function makeId_() {
			return 'metal_c_' + _core2.default.getUid(this);
		};

		Component.prototype.mergeElementClasses_ = function mergeElementClasses_(values) {
			var marked = {};
			return values.filter(function (val) {
				if (!val || marked[val]) {
					return false;
				} else {
					marked[val] = true;
					return true;
				}
			}).join(' ');
		};

		Component.prototype.mergeObjects_ = function mergeObjects_(values) {
			return _object2.default.mixin.apply(null, [{}].concat(values.reverse()));
		};

		Component.prototype.prefixSurfaceId = function prefixSurfaceId(surfaceId) {
			return this.id + '-' + surfaceId;
		};

		Component.prototype.removeSurface = function removeSurface(surfaceId) {
			var el = this.getSurfaceElement(surfaceId);

			if (el && el.parentNode) {
				el.parentNode.removeChild(el);
			}

			var surfaceElementId = this.getSurfaceElementId(surfaceId, this.getSurface(surfaceId));
			Component.surfacesCollector.removeSurface(surfaceElementId);
			this.surfaceIds_[surfaceElementId] = false;
			return this;
		};

		Component.prototype.removeUnusedSurfaces_ = function removeUnusedSurfaces_() {
			var compIds = [];

			for (var i = 0; i < this.removedSurfaces_.length; i++) {
				var surface = this.removedSurfaces_[i];

				if (!surface.parent) {
					this.removeSurface(surface.surfaceElementId);

					if (surface.componentName) {
						compIds.push(surface.surfaceElementId);
					}
				}
			}

			this.disposeSubComponents_(compIds);
		};

		Component.prototype.render = function render(opt_parentElement, opt_siblingElement) {
			if (this.wasRendered) {
				throw new Error(Component.Error.ALREADY_RENDERED);
			}

			this.addElementSurface_();
			this.renderContent_();
			this.syncAttrs_();
			this.emit('render');
			this.attach(opt_parentElement, opt_siblingElement);
			this.wasRendered = true;
			return this;
		};

		Component.prototype.renderAsSubComponent = function renderAsSubComponent(opt_content) {
			this.addElementSurface_();

			if (opt_content && _dom2.default.isEmpty(this.element)) {
				this.replaceElementContent_(opt_content);
			}

			this.syncAttrs_();
			this.attach();
			this.wasRendered = true;
		};

		Component.prototype.renderComponentSurface_ = function renderComponentSurface_(surfaceElementId, opt_content) {
			var component = _ComponentCollector2.default.components[surfaceElementId];

			if (component.wasRendered) {
				var surface = this.getSurfaceFromElementId(surfaceElementId);
				Component.componentsCollector.updateComponent(surfaceElementId, surface.componentData);
			} else if (this.decorating_) {
				component.decorateAsSubComponent(opt_content);
			} else {
				component.renderAsSubComponent(opt_content);
			}
		};

		Component.prototype.renderContent_ = function renderContent_() {
			var id = this.id;

			if (this.decorating_) {
				var extendedContent = this.getElementExtendedContent();
				var extendedCacheState = this.computeSurfaceCacheState_(extendedContent);
				var htmlCacheState = this.computeSurfaceCacheState_(_html2.default.compress(this.element.outerHTML));

				if (!this.compareCacheStates_(htmlCacheState, extendedCacheState)) {
					this.replaceElementContent_(extendedContent);
				}
			} else {
				this.emitRenderSurfaceEvent_(id);
			}
		};

		Component.prototype.renderElement_ = function renderElement_(opt_parentElement, opt_siblingElement) {
			var element = this.element;
			element.id = this.id;

			if (opt_siblingElement || !element.parentNode) {
				var parent = _dom2.default.toElement(opt_parentElement) || this.DEFAULT_ELEMENT_PARENT;
				parent.insertBefore(element, _dom2.default.toElement(opt_siblingElement));
			}
		};

		Component.prototype.renderPlaceholderSurfaceContents_ = function renderPlaceholderSurfaceContents_(content, surfaceElementId) {
			var instance = this;
			content.replace(Component.SURFACE_REGEX, function (match, id) {
				var surface = instance.createPlaceholderSurface_(surfaceElementId, id);
				instance.emitRenderSurfaceEvent_(surface.surfaceElementId);
				return match;
			});
		};

		Component.prototype.renderSurfacesContent_ = function renderSurfacesContent_(surfaces) {
			this.generatedIdCount_ = {};
			this.removedSurfaces_ = [];
			var surfaceElementIds = Object.keys(surfaces);
			var idIndex = surfaceElementIds.indexOf(this.id);

			if (idIndex !== -1) {
				surfaceElementIds.splice(idIndex, 1);
				surfaceElementIds = [this.id].concat(surfaceElementIds);
			}

			for (var i = 0; i < surfaceElementIds.length; i++) {
				if (!this.getSurfaceFromElementId(surfaceElementIds[i]).handled) {
					this.emitRenderSurfaceEvent_(surfaceElementIds[i], null, null, surfaces[surfaceElementIds[i]]);
				}
			}

			this.updatePlaceholderSurfaces_();
			this.eventsCollector_.detachUnusedListeners();
			this.removeUnusedSurfaces_();
		};

		Component.prototype.replaceElementContent_ = function replaceElementContent_(content) {
			var element = this.element;
			var newContent = this.buildFragment_(content);
			var newElement = this.findElementInContent_(this.id, newContent);

			if (newElement) {
				this.updateElementAttributes_(element, newElement);
				newContent = newElement.childNodes;
			}

			_dom2.default.removeChildren(element);

			_dom2.default.append(element, newContent);
		};

		Component.prototype.replaceSurfaceContent_ = function replaceSurfaceContent_(surfaceElementId, surface, content) {
			content = this.replaceSurfacePlaceholders_(content, surfaceElementId, surface);

			if (surfaceElementId === this.id) {
				this.replaceElementContent_(content);
				return;
			}

			var el = this.getSurfaceElement(surfaceElementId);
			var frag = this.buildFragment_(content);
			var element = this.findElementInContent_(surfaceElementId, frag);

			if (element) {
				surface.element = element;

				_dom2.default.replace(el, surface.element);
			} else {
				_dom2.default.removeChildren(el);

				_dom2.default.append(el, frag);
			}
		};

		Component.prototype.replaceSurfacePlaceholders_ = function replaceSurfacePlaceholders_(content, surfaceElementId, surface) {
			if (!surface.componentName || surfaceElementId === this.id) {
				this.addToRemovedSurfaces_(surface.children || []);
				surface.children = [];
			}

			var instance = this;
			return content.replace(Component.SURFACE_REGEX, function (match, id) {
				var placeholderSurface = instance.createPlaceholderSurface_(surfaceElementId, id);
				id = placeholderSurface.surfaceElementId;
				placeholderSurface.handled = true;
				placeholderSurface.parent = surfaceElementId;
				surface.children.push(id);
				var surfaceContent = instance.getSurfaceContent_(id);
				var surfaceHtml = instance.getSurfaceHtml_(placeholderSurface, surfaceContent);
				var expandedHtml = instance.replaceSurfacePlaceholders_(surfaceHtml, id, placeholderSurface);
				instance.collectedSurfaces_.push({
					cacheContent: surfaceContent,
					content: expandedHtml,
					surface: placeholderSurface
				});
				return expandedHtml;
			});
		};

		Component.prototype.setterElementFn_ = function setterElementFn_(val) {
			var element = _dom2.default.toElement(val);

			if (!element) {
				element = this.valueElementFn_();
			}

			return element;
		};

		Component.prototype.syncElementClasses = function syncElementClasses(newVal, prevVal) {
			var classesToAdd = this.constructor.ELEMENT_CLASSES_MERGED;

			if (newVal) {
				classesToAdd = classesToAdd + ' ' + newVal;
			}

			if (prevVal) {
				_dom2.default.removeClasses(this.element, prevVal);
			}

			_dom2.default.addClasses(this.element, classesToAdd);
		};

		Component.prototype.syncVisible = function syncVisible(newVal) {
			this.element.style.display = newVal ? '' : 'none';
		};

		Component.prototype.updateElementAttributes_ = function updateElementAttributes_(element, newElement) {
			var attrs = newElement.attributes;

			for (var i = 0; i < attrs.length; i++) {
				if (attrs[i].name !== 'id' && attrs[i].name !== 'class') {
					element.setAttribute(attrs[i].name, attrs[i].value);
				}
			}

			if (element.tagName !== newElement.tagName) {
				console.error('The component named "' + this.getName() + '" tried to change the component ' + 'element\'s tag name, which is not allowed. Make sure to always return the same tag ' + 'name for the component element on the renderer\'s getSurfaceContent. This may also ' + 'have been caused by passing an element to this component with a different tag name ' + 'from the one it uses.');
			}
		};

		Component.prototype.updatePlaceholderSurface_ = function updatePlaceholderSurface_(collectedData) {
			var surface = collectedData.surface;
			var surfaceElementId = surface.surfaceElementId;

			if (surface.componentName) {
				_dom2.default.replace(this.findElementById_(surfaceElementId), this.getSurfaceElement(surfaceElementId, surface));

				this.emitRenderSurfaceEvent_(surfaceElementId, collectedData.content, collectedData.cacheContent);
			} else {
				surface.element = null;
				this.cacheSurfaceContent(surfaceElementId, collectedData.cacheContent);
				this.eventsCollector_.attachListeners(collectedData.cacheContent, surfaceElementId);
			}
		};

		Component.prototype.updatePlaceholderSurfaces_ = function updatePlaceholderSurfaces_() {
			for (var i = this.collectedSurfaces_.length - 1; i >= 0; i--) {
				this.updatePlaceholderSurface_(this.collectedSurfaces_[i]);
				this.collectedSurfaces_[i].surface.handled = false;
			}

			this.collectedSurfaces_ = [];
		};

		Component.prototype.validatorElementFn_ = function validatorElementFn_(val) {
			return _core2.default.isElement(val) || _core2.default.isString(val);
		};

		Component.prototype.validatorElementClassesFn_ = function validatorElementClassesFn_(val) {
			return _core2.default.isString(val);
		};

		Component.prototype.validatorEventsFn_ = function validatorEventsFn_(val) {
			return !_core2.default.isDefAndNotNull(val) || _core2.default.isObject(val);
		};

		Component.prototype.validatorIdFn_ = function validatorIdFn_(val) {
			return _core2.default.isString(val);
		};

		Component.prototype.valueElementFn_ = function valueElementFn_() {
			if (!this.id) {
				this.id = this.makeId_();
			}

			var element = this.findElementInContent_(this.id, this.getElementContent_(true) || '');

			if (!element) {
				element = this.findElementInContent_(this.id, this.getComponentHtml(''));
			}

			_dom2.default.removeChildren(element);

			_dom2.default.exitDocument(element);

			return element;
		};

		Component.prototype.valueIdFn_ = function valueIdFn_() {
			var element = this.element;
			return element && element.id ? element.id : this.makeId_();
		};

		Component.prototype.wrapContentIfNecessary = function wrapContentIfNecessary(content, id, tag) {
			if (!this.checkHasElementTag_(content, id)) {
				content = '<' + tag + ' id="' + id + '">' + content + '</' + tag + '>';
			}

			return content;
		};

		return Component;
	})(_Attribute3.default);

	Component.componentsCollector = new _ComponentCollector2.default();
	Component.surfacesCollector = new _SurfaceCollector2.default();
	Component.ATTRS = {
		element: {
			setter: 'setterElementFn_',
			validator: 'validatorElementFn_',
			valueFn: 'valueElementFn_',
			writeOnce: true
		},
		elementClasses: {
			validator: 'validatorElementClassesFn_'
		},
		events: {
			validator: 'validatorEventsFn_',
			value: null
		},
		id: {
			validator: 'validatorIdFn_',
			valueFn: 'valueIdFn_',
			writeOnce: true
		},
		visible: {
			validator: _core2.default.isBoolean,
			value: true
		}
	};
	Component.ELEMENT_CLASSES = 'component';
	Component.ELEMENT_TAG_NAME = 'div';
	Component.RENDERER = _ComponentRenderer2.default;
	Component.SURFACE_REGEX = /\%\%\%\%~s(?:-([^~:]+))?~\%\%\%\%/g;
	Component.SURFACE_TAG_NAME = 'div';
	Component.Cache = {
		NOT_INITIALIZED: -2
	};
	Component.Error = {
		ALREADY_RENDERED: 'Component already rendered'
	};
	Component.INVALID_ATTRS = ['components', 'elementContent'];
	exports.default = Component;
});
//# sourceMappingURL=Component.js.map