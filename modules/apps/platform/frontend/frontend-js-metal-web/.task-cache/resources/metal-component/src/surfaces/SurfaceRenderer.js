define("frontend-js-metal-web@1.0.0/metal-component/src/surfaces/SurfaceRenderer", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-html/src/html', '../Component', '../ComponentCollector', '../ComponentRenderer', '../EventsCollector', './SurfaceCollector'], function (exports, _metal, _dom, _html, _Component, _ComponentCollector, _ComponentRenderer2, _EventsCollector, _SurfaceCollector) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _html2 = _interopRequireDefault(_html);

	var _Component2 = _interopRequireDefault(_Component);

	var _ComponentCollector2 = _interopRequireDefault(_ComponentCollector);

	var _ComponentRenderer3 = _interopRequireDefault(_ComponentRenderer2);

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

	var SurfaceRenderer = function (_ComponentRenderer) {
		_inherits(SurfaceRenderer, _ComponentRenderer);

		/**
   * @inheritDoc
   */

		function SurfaceRenderer(component) {
			_classCallCheck(this, SurfaceRenderer);

			var _this = _possibleConstructorReturn(this, _ComponentRenderer.call(this, component));

			/**
    * Holds data about all surfaces that were collected through the
    * `replaceSurfacePlaceholders_` method.
    * @type {!Array}
    * @protected
    */
			_this.collectedSurfaces_ = [];

			/**
    * Holds the number of generated ids for each surface's contents.
    * @type {!Object}
    * @protected
    */
			_this.generatedIdCount_ = {};

			/**
    * The element ids of all surfaces that were removed on a repaint.
    * @type {!Array<string>}
    * @protected
    */
			_this.removedSurfaces_ = [];

			/**
    * The ids of the surfaces registered for this renderer's component.
    * @type {!Object<string, boolean>}
    * @protected
    */
			_this.surfaceIds_ = {};

			/**
    * Collects inline events from html contents.
    * @type {!EventsCollector}
    * @protected
    */
			_this.eventsCollector_ = new _EventsCollector2.default(_this.component_);

			_metal.core.mergeSuperClassesProperty(_this.component_.constructor, 'SURFACE_TAG_NAME', _metal.array.firstDefinedValue);
			_this.component_.constructor.SURFACE_TAG_NAME_MERGED = _this.component_.constructor.SURFACE_TAG_NAME_MERGED || 'div';

			_this.setShouldUseFacade(true);
			_this.addSurfacesFromStaticHint_();
			_this.addSurface(_this.component_.id, {
				componentName: _this.component_.getName()
			});

			_this.component_.once('attached', _this.handleComponentAttachedOnce_.bind(_this));
			_this.component_.on('detached', _this.handleComponentDetached_.bind(_this));
			_this.on('renderSurface', _this.defaultRenderSurfaceFn_, true);
			return _this;
		}

		/**
   * Adds a simple attribute with the given name to the component, if it doesn't
   * exist yet.
   * @param {string} attrName
   * @param {Object=} opt_initialValue Optional initial value for the new attr.
   * @protected
   */


		SurfaceRenderer.prototype.addMissingAttr_ = function addMissingAttr_(attrName, opt_initialValue) {
			if (!this.component_.getAttrConfig(attrName)) {
				this.component_.addAttr(attrName, {}, opt_initialValue);
			}
		};

		SurfaceRenderer.prototype.addSurface = function addSurface(surfaceId, opt_surfaceConfig) {
			var config = opt_surfaceConfig || {};
			var surfaceElementId = this.getSurfaceElementId(surfaceId, config);
			if (this.surfaceIds_[surfaceElementId]) {
				SurfaceRenderer.surfacesCollector.updateSurface(surfaceElementId, config);
			} else {
				this.surfaceIds_[surfaceElementId] = true;
				config.cacheState = config.cacheState || SurfaceRenderer.Cache.NOT_INITIALIZED;
				SurfaceRenderer.surfacesCollector.addSurface(surfaceElementId, config);
				if (config.componentName && surfaceId !== this.component_.id) {
					this.addSubComponent(config.componentName, surfaceElementId);
				}
			}
			this.cacheSurfaceRenderAttrs_(surfaceElementId, config.renderAttrs);

			return this;
		};

		SurfaceRenderer.prototype.addSurfaces = function addSurfaces(configs) {
			for (var surfaceId in configs) {
				this.addSurface(surfaceId, configs[surfaceId]);
			}
			return this;
		};

		SurfaceRenderer.prototype.addSurfacesFromStaticHint_ = function addSurfacesFromStaticHint_() {
			_metal.core.mergeSuperClassesProperty(this.component_.constructor, 'SURFACES', this.mergeObjects_);
			this.surfacesRenderAttrs_ = {};

			var configs = this.component_.constructor.SURFACES_MERGED;
			for (var surfaceId in configs) {
				this.addSurface(surfaceId, _metal.object.mixin({}, configs[surfaceId]));
			}
		};

		SurfaceRenderer.prototype.addToRemovedSurfaces_ = function addToRemovedSurfaces_(surfaceElementIds) {
			for (var i = 0; i < surfaceElementIds.length; i++) {
				var surface = this.getSurface(surfaceElementIds[i]);
				this.removedSurfaces_.push(surface);
				surface.parent = null;
			}
		};

		SurfaceRenderer.prototype.buildElement = function buildElement() {
			var compId = this.component_.id;
			var element = this.findElementInContent_(compId, this.getElementContent_(true) || '');
			if (!element) {
				element = this.findElementInContent_(compId, this.getComponentHtml(''));
			}
			_dom.dom.removeChildren(element);
			_dom.dom.exitDocument(element);
			return element;
		};

		SurfaceRenderer.prototype.buildFragment_ = function buildFragment_(content) {
			var frag = _dom.dom.buildFragment(content);
			if (content.indexOf('<script') !== -1) {
				_dom.globalEval.runScriptsInElement(frag);
			}
			return frag;
		};

		SurfaceRenderer.prototype.buildPlaceholder = function buildPlaceholder(surfaceElementId, opt_data) {
			if (surfaceElementId && opt_data) {
				opt_data.surfaceElementId = surfaceElementId;
				this.addSurface(surfaceElementId, opt_data);
			}
			return '%%%%~s' + (surfaceElementId ? '-' + surfaceElementId : '') + '~%%%%';
		};

		SurfaceRenderer.prototype.cacheSurfaceContent = function cacheSurfaceContent(surfaceElementId, content) {
			var cacheState = this.computeSurfaceCacheState_(content);
			var surface = this.getSurfaceFromElementId(surfaceElementId);
			surface.cacheState = cacheState;
		};

		SurfaceRenderer.prototype.cacheSurfaceRenderAttrs_ = function cacheSurfaceRenderAttrs_(surfaceElementId, renderAttrs) {
			var attrs = renderAttrs || [];
			for (var i = 0; i < attrs.length; i++) {
				if (!this.surfacesRenderAttrs_[attrs[i]]) {
					this.surfacesRenderAttrs_[attrs[i]] = {};
					this.addMissingAttr_(attrs[i], this.component_.getInitialConfig()[attrs[i]]);
				}
				this.surfacesRenderAttrs_[attrs[i]][surfaceElementId] = true;
			}
		};

		SurfaceRenderer.prototype.checkHasElementTag_ = function checkHasElementTag_(content, id) {
			return content.indexOf(' id="' + id + '"') !== -1;
		};

		SurfaceRenderer.prototype.clearSurfaceCache = function clearSurfaceCache(surfaceId) {
			this.getSurface(surfaceId).cacheState = SurfaceRenderer.Cache.NOT_INITIALIZED;
		};

		SurfaceRenderer.prototype.compareCacheStates_ = function compareCacheStates_(currentCacheState, previousCacheState) {
			return currentCacheState !== SurfaceRenderer.Cache.NOT_INITIALIZED && currentCacheState === previousCacheState;
		};

		SurfaceRenderer.prototype.computeSurfaceCacheState_ = function computeSurfaceCacheState_(value) {
			value = value || '';
			if (_dom.features.checkAttrOrderChange()) {
				value = this.convertHtmlToBrowserFormat_(value);
			}
			return _metal.string.hashCode(value);
		};

		SurfaceRenderer.prototype.convertHtmlToBrowserFormat_ = function convertHtmlToBrowserFormat_(htmlString) {
			var element = document.createElement('div');
			_dom.dom.append(element, htmlString);
			return element.innerHTML;
		};

		SurfaceRenderer.prototype.createPlaceholderSurface_ = function createPlaceholderSurface_(parentSurfaceElementId, opt_surfaceElementId) {
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

		SurfaceRenderer.prototype.addSubComponent = function addSubComponent(componentName, componentId) {
			return this.component_.addSubComponent(componentName, componentId, this.getSurfaceFromElementId(componentId).componentData);
		};

		SurfaceRenderer.prototype.createSurfaceElement_ = function createSurfaceElement_(surfaceElementId) {
			var el = document.createElement(this.component_.constructor.SURFACE_TAG_NAME_MERGED);
			el.id = surfaceElementId;
			return el;
		};

		SurfaceRenderer.prototype.defaultRenderSurfaceFn_ = function defaultRenderSurfaceFn_(data) {
			var surfaceElementId = data.surfaceElementId;
			var surface = this.getSurfaceFromElementId(surfaceElementId);
			if (surface.componentName && surfaceElementId !== this.component_.id) {
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
				this.eventsCollector_.attachListenersFromHtml(cacheContent, surfaceElementId);
				this.replaceSurfaceContent_(surfaceElementId, surface, content);
			}
		};

		SurfaceRenderer.prototype.disposeInternal = function disposeInternal() {
			var _this2 = this;

			_ComponentRenderer.prototype.disposeInternal.call(this);

			this.eventsCollector_.dispose();
			this.eventsCollector_ = null;

			this.surfacesRenderAttrs_ = null;

			Object.keys(this.surfaceIds_).forEach(function (surfaceId) {
				return _this2.removeSurface(surfaceId, true);
			});
			this.surfaceIds_ = null;
		};

		SurfaceRenderer.prototype.emitRenderSurfaceEvent_ = function emitRenderSurfaceEvent_(surfaceElementId, opt_content, opt_cacheContent, opt_renderAttrs) {
			this.emit('renderSurface', {
				cacheContent: opt_cacheContent,
				content: opt_content,
				renderAttrs: opt_renderAttrs || [],
				surfaceElementId: surfaceElementId,
				surfaceId: this.getSurfaceId(this.getSurfaceFromElementId(surfaceElementId))
			});
		};

		SurfaceRenderer.prototype.findElementInContent_ = function findElementInContent_(id, content) {
			content = _metal.core.isString(content) ? _dom.dom.buildFragment(content) : content;
			var firstChild = content.childNodes[0];
			if (firstChild && firstChild.id === id) {
				return firstChild;
			}
		};

		SurfaceRenderer.prototype.generateSurfaceElementId = function generateSurfaceElementId(parentSurfaceElementId) {
			this.generatedIdCount_[parentSurfaceElementId] = (this.generatedIdCount_[parentSurfaceElementId] || 0) + 1;
			return parentSurfaceElementId + '-s' + this.generatedIdCount_[parentSurfaceElementId];
		};

		SurfaceRenderer.prototype.getComponentHtml = function getComponentHtml(content) {
			return this.wrapContentIfNecessary(content, this.component_.id, this.component_.constructor.ELEMENT_TAG_NAME_MERGED);
		};

		SurfaceRenderer.prototype.getElementContent_ = function getElementContent_(opt_skipContents) {
			return this.getSurfaceContent(this.getSurface(this.component_.id), opt_skipContents);
		};

		SurfaceRenderer.prototype.getElementExtendedContent = function getElementExtendedContent() {
			var content = this.getElementContent_() || '';
			this.eventsCollector_.attachListenersFromHtml(content, this.component_.id);
			this.cacheSurfaceContent(this.component_.id, content);
			return this.replaceSurfacePlaceholders_(content, this.component_.id, this.getSurface(this.component_.id));
		};

		SurfaceRenderer.prototype.getModifiedSurfacesFromChanges_ = function getModifiedSurfacesFromChanges_(changes) {
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

		SurfaceRenderer.prototype.getNonComponentSurfaceHtml = function getNonComponentSurfaceHtml(surfaceElementId, content) {
			return this.wrapContentIfNecessary(content, surfaceElementId, this.component_.constructor.SURFACE_TAG_NAME_MERGED);
		};

		SurfaceRenderer.prototype.getSurface = function getSurface(surfaceId) {
			var surface = this.getSurfaceFromElementId(this.getSurfaceElementId(surfaceId));
			return surface ? surface : this.getSurfaceFromElementId(surfaceId);
		};

		SurfaceRenderer.prototype.getSurfaceContent = function getSurfaceContent() {
			_metal.core.abstractMethod();
		};

		SurfaceRenderer.prototype.getSurfaceContent_ = function getSurfaceContent_(surfaceElementId) {
			var surface = this.getSurfaceFromElementId(surfaceElementId);
			if (surface.componentName && surfaceElementId !== this.component_.id) {
				var component = _ComponentCollector2.default.components[surfaceElementId];
				if (component.wasRendered) {
					return '';
				} else {
					return component.getRenderer().getElementExtendedContent();
				}
			} else {
				return this.getSurfaceContent(surface) || '';
			}
		};

		SurfaceRenderer.prototype.getSurfaceElement = function getSurfaceElement(surfaceId, opt_surface) {
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
					surface.element = this.component_.findElementById(surfaceElementId) || this.createSurfaceElement_(surfaceElementId);
				}
			}
			return surface.element;
		};

		SurfaceRenderer.prototype.getSurfaceElementId = function getSurfaceElementId(surfaceId, opt_surface) {
			var surface = opt_surface || {};
			if (surface.surfaceElementId) {
				return surface.surfaceElementId;
			} else if (surface.componentName || this.hasComponentPrefix_(surfaceId)) {
				return surfaceId;
			} else {
				return this.prefixSurfaceId(surfaceId);
			}
		};

		SurfaceRenderer.prototype.getSurfaceFromElementId = function getSurfaceFromElementId(surfaceElementId) {
			return SurfaceRenderer.surfacesCollector.getSurface(surfaceElementId);
		};

		SurfaceRenderer.prototype.getSurfaceHtml_ = function getSurfaceHtml_(surface, content) {
			var surfaceElementId = surface.surfaceElementId;
			if (surface.componentName) {
				var component = _ComponentCollector2.default.components[surfaceElementId];
				return component.getRenderer().getComponentHtml(content);
			} else {
				return this.getNonComponentSurfaceHtml(surfaceElementId, content);
			}
		};

		SurfaceRenderer.prototype.getSurfaceId = function getSurfaceId(surface) {
			if (surface.componentName || !this.hasComponentPrefix_(surface.surfaceElementId)) {
				return surface.surfaceElementId;
			} else {
				return surface.surfaceElementId.substr(this.component_.id.length + 1);
			}
		};

		SurfaceRenderer.prototype.getSurfaces = function getSurfaces() {
			var surfaces = {};
			Object.keys(this.surfaceIds_).forEach(function (surfaceElementId) {
				var surface = this.getSurfaceFromElementId(surfaceElementId);
				surfaces[this.getSurfaceId(surface)] = surface;
			}.bind(this));
			return surfaces;
		};

		SurfaceRenderer.prototype.handleComponentAttachedOnce_ = function handleComponentAttachedOnce_() {
			this.updatePlaceholderSurfaces_();
		};

		SurfaceRenderer.prototype.handleComponentDetached_ = function handleComponentDetached_() {
			this.eventsCollector_.detachAllListeners();
		};

		SurfaceRenderer.prototype.hasComponentPrefix_ = function hasComponentPrefix_(surfaceId) {
			var compId = this.component_.id;
			return surfaceId.substr(0, compId.length) === compId && (surfaceId.length === compId.length || surfaceId[compId.length] === '-');
		};

		SurfaceRenderer.prototype.mergeObjects_ = function mergeObjects_(values) {
			return _metal.object.mixin.apply(null, [{}].concat(values.reverse()));
		};

		SurfaceRenderer.prototype.prefixSurfaceId = function prefixSurfaceId(surfaceId) {
			return this.component_.id + '-' + surfaceId;
		};

		SurfaceRenderer.prototype.removeSurface = function removeSurface(surfaceId, opt_skipDomRemoval) {
			if (!opt_skipDomRemoval) {
				var el = this.getSurfaceElement(surfaceId);
				if (el && el.parentNode) {
					el.parentNode.removeChild(el);
				}
			}
			var surfaceElementId = this.getSurfaceElementId(surfaceId, this.getSurface(surfaceId));
			SurfaceRenderer.surfacesCollector.removeSurface(surfaceElementId);
			this.surfaceIds_[surfaceElementId] = false;
			return this;
		};

		SurfaceRenderer.prototype.removeUnusedSurfaces_ = function removeUnusedSurfaces_() {
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
			this.component_.disposeSubComponents(compIds);
		};

		SurfaceRenderer.prototype.render = function render(data) {
			var id = this.component_.id;
			if (data.decorating) {
				var extendedContent = this.getElementExtendedContent();
				var extendedCacheState = this.computeSurfaceCacheState_(extendedContent);
				var originalContent = _html2.default.compress(this.component_.element.outerHTML);
				var htmlCacheState = this.computeSurfaceCacheState_(originalContent);
				if (!this.compareCacheStates_(htmlCacheState, extendedCacheState)) {
					this.replaceElementContent(extendedContent);
				}
			} else {
				this.emitRenderSurfaceEvent_(id);
			}
		};

		SurfaceRenderer.prototype.renderComponentSurface_ = function renderComponentSurface_(surfaceElementId, opt_content) {
			var component = _ComponentCollector2.default.components[surfaceElementId];
			if (component.wasRendered) {
				var surface = this.getSurfaceFromElementId(surfaceElementId);
				_Component2.default.componentsCollector.updateComponent(surfaceElementId, surface.componentData);
			} else {
				if (opt_content && _dom.dom.isEmpty(component.element)) {
					// If we have the rendered content for this component, but it hasn't
					// been rendered in its element yet, we render it manually here. That
					// can happen if the subcomponent's element is set before the parent
					// element renders its content, making originally rendered content be
					// set on the wrong place.
					component.getRenderer().replaceElementContent(opt_content);
				}
				component.renderAsSubComponent();
			}
		};

		SurfaceRenderer.prototype.renderPlaceholderSurfaceContents_ = function renderPlaceholderSurfaceContents_(content, surfaceElementId) {
			var instance = this;
			content.replace(SurfaceRenderer.SURFACE_REGEX, function (match, id) {
				var surface = instance.createPlaceholderSurface_(surfaceElementId, id);
				instance.emitRenderSurfaceEvent_(surface.surfaceElementId);
				return match;
			});
		};

		SurfaceRenderer.prototype.renderSurfacesContent_ = function renderSurfacesContent_(surfaces) {
			this.generatedIdCount_ = {};
			this.removedSurfaces_ = [];

			var compId = this.component_.id;
			var surfaceElementIds = Object.keys(surfaces);
			var idIndex = surfaceElementIds.indexOf(compId);
			if (idIndex !== -1) {
				// Always render the main content surface first, for performance reasons.
				surfaceElementIds.splice(idIndex, 1);
				surfaceElementIds = [compId].concat(surfaceElementIds);
			}

			for (var i = 0; i < surfaceElementIds.length; i++) {
				var surface = this.getSurfaceFromElementId(surfaceElementIds[i]);
				if (!surface.handled && (surface.parent || surfaceElementIds[i] === compId)) {
					this.emitRenderSurfaceEvent_(surfaceElementIds[i], null, null, surfaces[surfaceElementIds[i]]);
				}
			}
			this.updatePlaceholderSurfaces_();
			this.eventsCollector_.detachUnusedListeners();
			this.removeUnusedSurfaces_();
		};

		SurfaceRenderer.prototype.replaceElementContent = function replaceElementContent(content) {
			var element = this.component_.element;
			var newContent = this.buildFragment_(content);
			var newElement = this.findElementInContent_(this.component_.id, newContent);
			if (newElement) {
				this.updateElementAttributes_(element, newElement);
				newContent = newElement.childNodes;
			}
			_dom.dom.removeChildren(element);
			_dom.dom.append(element, newContent);
		};

		SurfaceRenderer.prototype.replaceSurfaceContent_ = function replaceSurfaceContent_(surfaceElementId, surface, content) {
			content = this.replaceSurfacePlaceholders_(content, surfaceElementId, surface);
			if (surfaceElementId === this.component_.id) {
				this.replaceElementContent(content);
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

		SurfaceRenderer.prototype.replaceSurfacePlaceholders_ = function replaceSurfacePlaceholders_(content, surfaceElementId, surface) {
			if (!surface.componentName || surfaceElementId === this.component_.id) {
				this.addToRemovedSurfaces_(surface.children || []);
				surface.children = [];
			}

			var instance = this;
			return content.replace(SurfaceRenderer.SURFACE_REGEX, function (match, id) {
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

		SurfaceRenderer.prototype.update = function update(data) {
			this.renderSurfacesContent_(this.getModifiedSurfacesFromChanges_(data.changes));
		};

		SurfaceRenderer.prototype.updateElementAttributes_ = function updateElementAttributes_(element, newElement) {
			var attrs = newElement.attributes;
			for (var i = 0; i < attrs.length; i++) {
				// The "id" and "class" html attributes are already synced via the "id"
				// and "elementClasses" component attributes, respectively.
				if (attrs[i].name !== 'id' && attrs[i].name !== 'class') {
					element.setAttribute(attrs[i].name, attrs[i].value);
				}
			}

			if (element.tagName !== newElement.tagName) {
				console.error('The component named "' + this.component_.getName() + '" tried to change the component ' + 'element\'s tag name, which is not allowed. Make sure to always return the same tag ' + 'name for the component element on the renderer\'s getSurfaceContent. This may also ' + 'have been caused by passing an element to this component with a different tag name ' + 'from the one it uses.');
			}
		};

		SurfaceRenderer.prototype.updatePlaceholderSurface_ = function updatePlaceholderSurface_(collectedData) {
			var surface = collectedData.surface;
			var surfaceElementId = surface.surfaceElementId;
			if (surface.componentName) {
				// Elements of component surfaces are unchangeable, so we need to replace the
				// rendered element with the component's.
				_dom.dom.replace(this.component_.findElementById(surfaceElementId), this.getSurfaceElement(surfaceElementId, surface));

				// Component surfaces need to be handled in case some internal details have changed.
				this.emitRenderSurfaceEvent_(surfaceElementId, collectedData.content, collectedData.cacheContent);
			} else {
				// This surface's element has either changed or never been created yet. Let's just
				// reset it to null, so it can be fetched from the dom again when necessary. Also,
				// since there's no need to do cache checks or rerender, let's just attach its
				// listeners and cache its content manually.
				surface.element = null;
				this.cacheSurfaceContent(surfaceElementId, collectedData.cacheContent);
				this.eventsCollector_.attachListenersFromHtml(collectedData.cacheContent, surfaceElementId);
			}
		};

		SurfaceRenderer.prototype.updatePlaceholderSurfaces_ = function updatePlaceholderSurfaces_() {
			for (var i = this.collectedSurfaces_.length - 1; i >= 0; i--) {
				this.updatePlaceholderSurface_(this.collectedSurfaces_[i]);
				this.collectedSurfaces_[i].surface.handled = false;
			}
			this.collectedSurfaces_ = [];
		};

		SurfaceRenderer.prototype.wrapContentIfNecessary = function wrapContentIfNecessary(content, id, tag) {
			if (!this.checkHasElementTag_(content, id)) {
				content = '<' + tag + ' id="' + id + '">' + content + '</' + tag + '>';
			}
			return content;
		};

		return SurfaceRenderer;
	}(_ComponentRenderer3.default);

	SurfaceRenderer.prototype.registerMetalComponent && SurfaceRenderer.prototype.registerMetalComponent(SurfaceRenderer, 'SurfaceRenderer')


	/**
  * Cache states for the surfaces.
  * @enum {string}
  */
	SurfaceRenderer.Cache = {
		/**
   * Cache not initialized.
   */
		NOT_INITIALIZED: -2
	};

	/**
  * The regex used to search for surface placeholders.
  * @type {RegExp}
  * @static
  */
	SurfaceRenderer.SURFACE_REGEX = /\%\%\%\%~s(?:-([^~:]+))?~\%\%\%\%/g;

	/**
  * Helper responsible for temporarily holding surface data.
  * @type {!SurfaceCollector}
  * @protected
  * @static
  */
	SurfaceRenderer.surfacesCollector = new _SurfaceCollector2.default();

	exports.default = SurfaceRenderer;
});
//# sourceMappingURL=SurfaceRenderer.js.map