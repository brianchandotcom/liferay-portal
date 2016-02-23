define("frontend-js-metal-web@1.0.0/metal-component/src/Component", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-html/src/html', 'metal-attribute/src/Attribute', './ComponentCollector', './ComponentRegistry', './ComponentRenderer', './EventsCollector', 'metal-events/src/events', './SurfaceCollector'], function (exports, _metal, _dom, _html, _Attribute2, _ComponentCollector, _ComponentRegistry, _ComponentRenderer, _EventsCollector, _events, _SurfaceCollector) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _html2 = _interopRequireDefault(_html);

	var _Attribute3 = _interopRequireDefault(_Attribute2);

	var _ComponentCollector2 = _interopRequireDefault(_ComponentCollector);

	var _ComponentRegistry2 = _interopRequireDefault(_ComponentRegistry);

	var _ComponentRenderer2 = _interopRequireDefault(_ComponentRenderer);

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

	var Component = function (_Attribute) {
		_inherits(Component, _Attribute);

		function Component(opt_config) {
			_classCallCheck(this, Component);

			var _this = _possibleConstructorReturn(this, _Attribute.call(this, opt_config));

			/**
    * Holds data about all surfaces that were collected through the
    * `replaceSurfacePlaceholders_` method.
    * @type {!Array}
    * @protected
    */
			_this.collectedSurfaces_ = [];

			/**
    * Gets all nested components.
    * @type {!Array<!Component>}
    */
			_this.components = {};

			/**
    * Whether the element is being decorated.
    * @type {boolean}
    * @protected
    */
			_this.decorating_ = false;

			/**
    * Holds events that were listened through the `delegate` Component function.
    * @type {EventHandler}
    * @protected
    */
			_this.delegateEventHandler_ = null;

			/**
    * Instance of `DomEventEmitterProxy` which proxies events from the component's
    * element to the component itself.
    * @type {DomEventEmitterProxy}
    * @protected
    */
			_this.elementEventProxy_ = null;

			/**
    * The `EventHandler` instance for events attached from the `events` attribute.
    * @type {!EventHandler}
    * @protected
    */
			_this.eventsAttrHandler_ = new _events.EventHandler();

			/**
    * Collects inline events from html contents.
    * @type {!EventsCollector}
    * @protected
    */
			_this.eventsCollector_ = new _EventsCollector2.default(_this);

			/**
    * Holds the number of generated ids for each surface's contents.
    * @type {!Object}
    * @protected
    */
			_this.generatedIdCount_ = {};

			/**
    * Whether the element is in document.
    * @type {boolean}
    */
			_this.inDocument = false;

			/**
    * The initial config option passed to this constructor.
    * @type {!Object}
    * @protected
    */
			_this.initialConfig_ = opt_config || {};

			/**
    * The element ids of all surfaces that were removed on a repaint.
    * @type {!Array<string>}
    * @protected
    */
			_this.removedSurfaces_ = [];

			/**
    * The ids of the surfaces registered by this component.
    * @type {!Object<string, boolean>}
    * @protected
    */
			_this.surfaceIds_ = {};

			/**
    * Whether the element was rendered.
    * @type {boolean}
    */
			_this.wasRendered = false;

			/**
    * The component's element will be appended to the element this variable is
    * set to, unless the user specifies another parent when calling `render` or
    * `attach`.
    * @type {!Element}
    */
			_this.DEFAULT_ELEMENT_PARENT = document.body;

			_metal.core.mergeSuperClassesProperty(_this.constructor, 'ELEMENT_CLASSES', _this.mergeElementClasses_);
			_metal.core.mergeSuperClassesProperty(_this.constructor, 'ELEMENT_TAG_NAME', _metal.array.firstDefinedValue);
			_metal.core.mergeSuperClassesProperty(_this.constructor, 'RENDERER', _metal.array.firstDefinedValue);
			_metal.core.mergeSuperClassesProperty(_this.constructor, 'SURFACE_TAG_NAME', _metal.array.firstDefinedValue);
			_this.addSurfacesFromStaticHint_();

			_this.delegateEventHandler_ = new _events.EventHandler();

			_this.created_();
			return _this;
		}

		/**
   * Adds the listeners specified in the given object.
   * @param {Object} events
   * @protected
   */


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
			if (!this.elementEventProxy_ && _dom.dom.supportsEvent(this.constructor.ELEMENT_TAG_NAME_MERGED, event)) {
				this.elementEventProxy_ = new _dom.DomEventEmitterProxy(this.element, this);
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
			_metal.core.mergeSuperClassesProperty(this.constructor, 'SURFACES', this.mergeObjects_);
			this.surfacesRenderAttrs_ = {};

			var configs = this.constructor.SURFACES_MERGED;
			for (var surfaceId in configs) {
				this.addSurface(surfaceId, _metal.object.mixin({}, configs[surfaceId]));
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
			var frag = _dom.dom.buildFragment(content);
			if (content.indexOf('<script') !== -1) {
				_dom.globalEval.runScriptsInElement(frag);
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
			if (_dom.features.checkAttrOrderChange()) {
				value = this.convertHtmlToBrowserFormat_(value);
			}
			return _metal.string.hashCode(value);
		};

		Component.prototype.convertHtmlToBrowserFormat_ = function convertHtmlToBrowserFormat_(htmlString) {
			var element = document.createElement('div');
			_dom.dom.append(element, htmlString);
			return element.innerHTML;
		};

		Component.prototype.createPlaceholderSurface_ = function createPlaceholderSurface_(parentSurfaceElementId, opt_surfaceElementId) {
			var surfaceElementId = opt_surfaceElementId;
			if (!_metal.core.isDefAndNotNull(surfaceElementId)) {
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

		Component.prototype.delegate = function delegate(eventName, selector, callback) {
			var handle = _dom.dom.delegate(this.element, eventName, selector, callback);
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
			if (_metal.core.isObject(value) && !_metal.core.isFunction(value)) {
				info.selector = value.selector;
				info.fn = value.fn;
			}
			if (_metal.core.isString(info.fn)) {
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
			content = _metal.core.isString(content) ? _dom.dom.buildFragment(content) : content;
			var firstChild = content.childNodes[0];
			if (firstChild && firstChild.id === id) {
				return firstChild;
			}
		};

		Component.prototype.fireAttrChange_ = function fireAttrChange_(attr, opt_change) {
			var fn = this['sync' + attr.charAt(0).toUpperCase() + attr.slice(1)];
			if (_metal.core.isFunction(fn)) {
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
			return this.constructor.NAME || _metal.core.getFunctionName(this.constructor);
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
			Object.keys(this.surfaceIds_).forEach(function (surfaceElementId) {
				var surface = this.getSurfaceFromElementId(surfaceElementId);
				surfaces[this.getSurfaceId(surface)] = surface;
			}.bind(this));
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
			return 'metal_c_' + _metal.core.getUid(this);
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
			return _metal.object.mixin.apply(null, [{}].concat(values.reverse()));
		};

		Component.prototype.prefixSurfaceId = function prefixSurfaceId(surfaceId) {
			return this.id + '-' + surfaceId;
		};

		Component.prototype.registerMetalComponent = function registerMetalComponent(constructorFn, opt_name) {
			_ComponentRegistry2.default.register(constructorFn, opt_name);
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
			if (opt_content && _dom.dom.isEmpty(this.element)) {
				// If we have the rendered content for this component, but it hasn't
				// been rendered in its element yet, we render it manually here. That
				// can happen if the subcomponent's element is set before the parent
				// element renders its content, making originally rendered content be
				// set on the wrong place.
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
				var parent = _dom.dom.toElement(opt_parentElement) || this.DEFAULT_ELEMENT_PARENT;
				parent.insertBefore(element, _dom.dom.toElement(opt_siblingElement));
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
				// Always render the main content surface first, for performance reasons.
				surfaceElementIds.splice(idIndex, 1);
				surfaceElementIds = [this.id].concat(surfaceElementIds);
			}

			for (var i = 0; i < surfaceElementIds.length; i++) {
				var surface = this.getSurfaceFromElementId(surfaceElementIds[i]);
				if (!surface.handled && (surface.parent || surfaceElementIds[i] === this.id)) {
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
			_dom.dom.removeChildren(element);
			_dom.dom.append(element, newContent);
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
				_dom.dom.replace(el, surface.element);
			} else {
				_dom.dom.removeChildren(el);
				_dom.dom.append(el, frag);
			}
		};

		Component.prototype.replaceSurfacePlaceholders_ = function replaceSurfacePlaceholders_(content, surfaceElementId, surface) {
			if (!surface.componentName || surfaceElementId === this.id) {
				this.addToRemovedSurfaces_(surface.children || []);
				surface.children = [];
			}

			var instance = this;
			return content.replace(Component.SURFACE_REGEX, function (match, id) {
				// Surfaces should already have been created before being rendered so they can be
				// accessed from their getSurfaceContent calls.
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
			var element = _dom.dom.toElement(val);
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
				_dom.dom.removeClasses(this.element, prevVal);
			}
			_dom.dom.addClasses(this.element, classesToAdd);
		};

		Component.prototype.syncVisible = function syncVisible(newVal) {
			this.element.style.display = newVal ? '' : 'none';
		};

		Component.prototype.updateElementAttributes_ = function updateElementAttributes_(element, newElement) {
			var attrs = newElement.attributes;
			for (var i = 0; i < attrs.length; i++) {
				// The "id" and "class" html attributes are already synced via the "id"
				// and "elementClasses" component attributes, respectively.
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
				// Elements of component surfaces are unchangeable, so we need to replace the
				// rendered element with the component's.
				_dom.dom.replace(this.findElementById_(surfaceElementId), this.getSurfaceElement(surfaceElementId, surface));

				// Component surfaces need to be handled in case some internal details have changed.
				this.emitRenderSurfaceEvent_(surfaceElementId, collectedData.content, collectedData.cacheContent);
			} else {
				// This surface's element has either changed or never been created yet. Let's just
				// reset it to null, so it can be fetched from the dom again when necessary. Also,
				// since there's no need to do cache checks or rerender, let's just attach its
				// listeners and cache its content manually.
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
			return _metal.core.isElement(val) || _metal.core.isString(val);
		};

		Component.prototype.validatorElementClassesFn_ = function validatorElementClassesFn_(val) {
			return _metal.core.isString(val);
		};

		Component.prototype.validatorEventsFn_ = function validatorEventsFn_(val) {
			return !_metal.core.isDefAndNotNull(val) || _metal.core.isObject(val);
		};

		Component.prototype.validatorIdFn_ = function validatorIdFn_(val) {
			return _metal.core.isString(val);
		};

		Component.prototype.valueElementFn_ = function valueElementFn_() {
			if (!this.id) {
				// This may happen because the default value of "id" depends on "element",
				// and the default value of "element" depends on "id".
				this.id = this.makeId_();
			}
			var element = this.findElementInContent_(this.id, this.getElementContent_(true) || '');
			if (!element) {
				element = this.findElementInContent_(this.id, this.getComponentHtml(''));
			}
			_dom.dom.removeChildren(element);
			_dom.dom.exitDocument(element);
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
	}(_Attribute3.default);

	Component.prototype.registerMetalComponent && Component.prototype.registerMetalComponent(Component, 'Component')


	/**
  * Helper responsible for extracting components from strings and config data.
  * @type {!ComponentCollector}
  * @protected
  * @static
  */
	Component.componentsCollector = new _ComponentCollector2.default();

	/**
  * Helper responsible for temporarily holding surface data.
  * @type {!SurfaceCollector}
  * @protected
  * @static
  */
	Component.surfacesCollector = new _SurfaceCollector2.default();

	/**
  * Component attributes definition.
  * @type {Object}
  * @static
  */
	Component.ATTRS = {
		/**
   * Component element bounding box.
   * @type {Element}
   * @writeOnce
   */
		element: {
			setter: 'setterElementFn_',
			validator: 'validatorElementFn_',
			valueFn: 'valueElementFn_',
			writeOnce: true
		},

		/**
   * CSS classes to be applied to the element.
   * @type {Array.<string>}
   */
		elementClasses: {
			validator: 'validatorElementClassesFn_'
		},

		/**
   * Listeners that should be attached to this component. Should be provided as an object,
   * where the keys are event names and the values are the listener functions (or function
   * names).
   * @type {Object<string, (function()|string|{selector: string, fn: function()|string})>}
   */
		events: {
			validator: 'validatorEventsFn_',
			value: null
		},

		/**
   * Component element id. If not specified will be generated.
   * @type {string}
   * @writeOnce
   */
		id: {
			validator: 'validatorIdFn_',
			valueFn: 'valueIdFn_',
			writeOnce: true
		},

		/**
   * Indicates if the component is visible or not.
   * @type {boolean}
   */
		visible: {
			validator: _metal.core.isBoolean,
			value: true
		}
	};

	/**
  * CSS classes to be applied to the element.
  * @type {string}
  * @protected
  * @static
  */
	Component.ELEMENT_CLASSES = 'component';

	/**
  * Element tag name is a string that specifies the type of element to be
  * created. The nodeName of the created element is initialized with the
  * value of tag name.
  * @type {string}
  * @default div
  * @protected
  * @static
  */
	Component.ELEMENT_TAG_NAME = 'div';

	/**
  * The `ComponentRenderer` that should be used. Components need to set this
  * to a subclass of `ComponentRenderer` that has the rendering logic, like
  * `SoyRenderer`.
  * @type {!ComponentRenderer}
  * @static
  */
	Component.RENDERER = _ComponentRenderer2.default;

	/**
  * The regex used to search for surface placeholders.
  * @type {RegExp}
  * @static
  */
	Component.SURFACE_REGEX = /\%\%\%\%~s(?:-([^~:]+))?~\%\%\%\%/g;

	/**
  * Surface tag name is a string that specifies the type of element to be
  * created for the surfaces. The nodeName of the created element is
  * initialized with the value of tag name.
  * @type {string}
  * @default div
  * @protected
  * @static
  */
	Component.SURFACE_TAG_NAME = 'div';

	/**
  * Cache states for the component.
  * @enum {string}
  */
	Component.Cache = {
		/**
   * Cache not initialized.
   */
		NOT_INITIALIZED: -2
	};

	/**
  * Errors thrown by the component.
  * @enum {string}
  */
	Component.Error = {
		/**
   * Error when the component is already rendered and another render attempt
   * is made.
   */
		ALREADY_RENDERED: 'Component already rendered'
	};

	/**
  * A list with attribute names that will automatically be rejected as invalid.
  * @type {!Array<string>}
  */
	Component.INVALID_ATTRS = ['components', 'elementContent'];

	exports.default = Component;
});
//# sourceMappingURL=Component.js.map