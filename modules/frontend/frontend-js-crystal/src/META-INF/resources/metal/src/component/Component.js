define(['exports', 'module', 'metal/src/array/array', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/dom/features', 'metal/src/html/html', 'metal/src/object/object', 'metal/src/string/string', 'metal/src/attribute/Attribute', 'metal/src/component/ComponentCollector', 'metal/src/events/EventEmitterProxy', 'metal/src/events/EventHandler', 'metal/src/component/EventsCollector'], function (exports, module, _metalSrcArrayArray, _metalSrcCore, _metalSrcDomDom, _metalSrcDomFeatures, _metalSrcHtmlHtml, _metalSrcObjectObject, _metalSrcStringString, _metalSrcAttributeAttribute, _metalSrcComponentComponentCollector, _metalSrcEventsEventEmitterProxy, _metalSrcEventsEventHandler, _metalSrcComponentEventsCollector) {
	'use strict';

	var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

	var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

	function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) subClass.__proto__ = superClass; }

	var _array = _interopRequireDefault(_metalSrcArrayArray);

	var _core = _interopRequireDefault(_metalSrcCore);

	var _dom = _interopRequireDefault(_metalSrcDomDom);

	var _features = _interopRequireDefault(_metalSrcDomFeatures);

	var _html = _interopRequireDefault(_metalSrcHtmlHtml);

	var _object = _interopRequireDefault(_metalSrcObjectObject);

	var _string = _interopRequireDefault(_metalSrcStringString);

	var _Attribute2 = _interopRequireDefault(_metalSrcAttributeAttribute);

	var _ComponentCollector = _interopRequireDefault(_metalSrcComponentComponentCollector);

	var _EventEmitterProxy = _interopRequireDefault(_metalSrcEventsEventEmitterProxy);

	var _EventHandler = _interopRequireDefault(_metalSrcEventsEventHandler);

	var _EventsCollector = _interopRequireDefault(_metalSrcComponentEventsCollector);

	/**
  * Component collects common behaviors to be followed by UI components, such
  * as Lifecycle, bounding box element creation, CSS classes management,
  * events encapsulation and surfaces support. Surfaces are an area of the
  * component that can have information rendered into it. A component
  * manages multiple surfaces. Surfaces are only rendered when its content is
  * modified, representing render performance gains. For each surface, render
  * attributes could be associated, when the render context of a surface gets
  * modified the component Lifecycle re-paints the modified surface
  * automatically.
  *
  * Example:
  *
  * <code>
  * class CustomComponent extends Component {
  *   constructor(config) {
  *     super(config);
  *   }
  *
  *   decorateInternal() {
  *   }
  *
  *   renderInternal() {
  *     this.element.appendChild(this.getSurfaceElement('header'));
  *     this.element.appendChild(this.getSurfaceElement('bottom'));
  *   }
  *
  *   getSurfaceContent(surfaceId) {
  *   }
  *
  *   attached() {
  *   }
  *
  *   detached() {
  *   }
  * }
  *
  * CustomComponent.ATTRS = {
  *   title: { value: 'Title' },
  *   fontSize: { value: '10px' }
  * };
  *
  * CustomComponent.SURFACES = {
  *   header: { renderAttrs: ['title', 'fontSize'] },
  *   bottom: { renderAttrs: ['fontSize'] }
  * };
  * </code>
  *
  * @param {!Object} opt_config An object with the initial values for this component's
  *   attributes.
  * @constructor
  * @extends {Attribute}
  */

	var Component = (function (_Attribute) {
		_inherits(Component, _Attribute);

		function Component(opt_config) {
			_classCallCheck(this, Component);

			_get(Object.getPrototypeOf(Component.prototype), 'constructor', this).call(this, opt_config);

			/**
    * Holds data about all surfaces that were collected through the
    * `replaceSurfacePlaceholders_` method.
    * @type {!Array}
    * @protected
    */
			this.collectedSurfaces_ = [];

			/**
    * Gets all nested components.
    * @type {!Array<!Component>}
    */
			this.components = {};

			/**
    * Whether the element is being decorated.
    * @type {boolean}
    * @protected
    */
			this.decorating_ = false;

			/**
    * Holds events that were listened through the `delegate` Component function.
    * @type {EventHandler}
    * @protected
    */
			this.delegateEventHandler_ = null;

			/**
    * Instance of `EventEmitterProxy` which proxies events from the component's
    * element to the component itself.
    * @type {EventEmitterProxy}
    * @protected
    */
			this.elementEventProxy_ = null;

			/**
    * The `EventHandler` instance for events attached from the `events` attribute.
    * @type {!EventHandler}
    * @protected
    */
			this.eventsAttrHandler_ = new _EventHandler['default']();

			/**
    * Collects inline events from html contents.
    * @type {!EventsCollector}
    * @protected
    */
			this.eventsCollector_ = new _EventsCollector['default'](this);

			/**
    * Holds the number of generated ids for each surface's contents.
    * @type {!Object}
    * @protected
    */
			this.generatedIdCount_ = {};

			/**
    * Whether the element is in document.
    * @type {boolean}
    */
			this.inDocument = false;

			/**
    * Maps that index the surfaces instances by the surface id.
    * @type {Object}
    * @default null
    * @protected
    */
			this.surfaces_ = null;

			/**
    * Whether the element was rendered.
    * @type {boolean}
    */
			this.wasRendered = false;

			/**
    * The component's element will be appended to the element this variable is
    * set to, unless the user specifies another parent when calling `render` or
    * `attach`.
    * @type {!Element}
    */
			this.DEFAULT_ELEMENT_PARENT = document.body;

			_core['default'].mergeSuperClassesProperty(this.constructor, 'ELEMENT_CLASSES', this.mergeElementClasses_);
			_core['default'].mergeSuperClassesProperty(this.constructor, 'ELEMENT_TAG_NAME', _array['default'].firstDefinedValue);
			_core['default'].mergeSuperClassesProperty(this.constructor, 'SURFACE_TAG_NAME', _array['default'].firstDefinedValue);
			this.addSurfacesFromStaticHint_(opt_config);

			this.delegateEventHandler_ = new _EventHandler['default']();

			this.created_();
		}

		_createClass(Component, [{
			key: 'addListenersFromObj_',

			/**
    * Adds the listeners specified in the given object.
    * @param {Object} events
    * @protected
    */
			value: function addListenersFromObj_(events) {
				var eventNames = Object.keys(events || {});
				for (var i = 0; i < eventNames.length; i++) {
					var fn = events[eventNames[i]];
					if (_core['default'].isString(fn)) {
						fn = this.eventsCollector_.getListenerFn(fn);
					}
					if (fn) {
						this.eventsAttrHandler_.add(this.on(eventNames[i], fn));
					}
				}
			}
		}, {
			key: 'addMissingAttr_',

			/**
    * Adds a simple attribute with the given name, if it doesn't exist yet.
    * @param {string} attrName
    * @param {Object=} opt_initialValue Optional initial value for the new attr.
    * @protected
    */
			value: function addMissingAttr_(attrName, initialValue) {
				if (!this.getAttrConfig(attrName)) {
					this.addAttr(attrName, {}, initialValue);
				}
			}
		}, {
			key: 'addSingleListener_',

			/**
    * Overrides `addSingleListener_` from `EventEmitter`, so we can create
    * the `EventEmitterProxy` instance only when it's needed for the first
    * time.
    * @param {string} event
    * @param {!Function} listener
    * @param {Function=} opt_origin The original function that was added as a
    *   listener, if there is any.
    * @protected
    * @override
    */
			value: function addSingleListener_(event, listener, opt_origin) {
				if (!this.elementEventProxy_ && _dom['default'].supportsEvent(this.constructor.ELEMENT_TAG_NAME_MERGED, event)) {
					this.elementEventProxy_ = new _EventEmitterProxy['default'](this.element, this);
				}
				_get(Object.getPrototypeOf(Component.prototype), 'addSingleListener_', this).call(this, event, listener, opt_origin);
			}
		}, {
			key: 'addSurface',

			/**
    * Registers a surface to the component. Surface elements are not
    * automatically appended to the component element.
    * @param {string} surfaceId The surface id to be registered.
    * @param {Object=} opt_surfaceConfig Optional surface configuration.
    * @param {Object=} opt_config Optional component configuration.
    * @chainable
    */
			value: function addSurface(surfaceId, opt_surfaceConfig, opt_config) {
				var config = opt_surfaceConfig || {};
				this.surfaces_[surfaceId] = config;
				if (config.componentName) {
					this.createSubComponent_(config.componentName, surfaceId);
				}
				this.cacheSurfaceRenderAttrs_(surfaceId, opt_config);
				return this;
			}
		}, {
			key: 'addSurfaces',

			/**
    * Registers surfaces to the component. Surface elements are not
    * automatically appended to the component element.
    * @param {!Object.<string, Object=>} configs An object that maps the names
    *     of all the surfaces to be added to their configuration objects.
    * @chainable
    */
			value: function addSurfaces(configs) {
				for (var surfaceId in configs) {
					this.addSurface(surfaceId, configs[surfaceId]);
				}
				return this;
			}
		}, {
			key: 'addSurfacesFromStaticHint_',

			/**
    * Adds surfaces from super classes static hint.
    * @param {Object=} opt_config This component's configuration object.
    * @protected
    */
			value: function addSurfacesFromStaticHint_(opt_config) {
				_core['default'].mergeSuperClassesProperty(this.constructor, 'SURFACES', this.mergeObjects_);
				this.surfaces_ = {};
				this.surfacesRenderAttrs_ = {};

				var configs = this.constructor.SURFACES_MERGED;
				for (var surfaceId in configs) {
					this.addSurface(surfaceId, _object['default'].mixin({}, configs[surfaceId]), opt_config);
				}
			}
		}, {
			key: 'attach',

			/**
    * Invokes the attached Lifecycle. When attached, the component element is
    * appended to the DOM and any other action to be performed must be
    * implemented in this method, such as, binding DOM events. A component can
    * be re-attached multiple times.
    * @param {(string|Element)=} opt_parentElement Optional parent element
    *     to render the component.
    * @param {(string|Element)=} opt_siblingElement Optional sibling element
    *     to render the component before it. Relevant when the component needs
    *     to be rendered before an existing element in the DOM, e.g.
    *     `component.render(null, existingElement)`.
    * @protected
    * @chainable
    */
			value: function attach(opt_parentElement, opt_siblingElement) {
				if (!this.inDocument) {
					this.renderElement_(opt_parentElement, opt_siblingElement);
					this.inDocument = true;
					if (!this.wasRendered) {
						this.updatePlaceholderSurfaces_();
					}
					this.attached();
				}
				return this;
			}
		}, {
			key: 'attachInlineListeners_',

			/**
    * Attaches inline listeners that are found on the element's string content.
    * @protected
    */
			value: function attachInlineListeners_() {
				this.eventsCollector_.attachListeners(this.getElementContent_(''));
				this.elementContent_ = null;
			}
		}, {
			key: 'attached',

			/**
    * Lifecycle. When attached, the component element is appended to the DOM
    * and any other action to be performed must be implemented in this method,
    * such as, binding DOM events. A component can be re-attached multiple
    * times, therefore the undo behavior for any action performed in this phase
    * must be implemented on the detach phase.
    */
			value: function attached() {}
		}, {
			key: 'buildPlaceholderSurfaceData_',

			/**
    * Builds the data that should be used to create a surface that was found via
    * a string placeholder.
    * @param {string} type The surface type (either "s" or "c").
    * @param {string} extra String with extra information about the surface.
    * @return {!Object}
    * @protected
    */
			value: function buildPlaceholderSurfaceData_(type, extra) {
				return {
					componentName: type === Component.SurfaceType.COMPONENT ? extra : null
				};
			}
		}, {
			key: 'cacheSurfaceContent',

			/**
    * Caches the given content for the surface with the requested id.
    * @param {string} surfaceId
    * @param {string} content
    */
			value: function cacheSurfaceContent(surfaceId, content) {
				var cacheState = this.computeSurfaceCacheState_(content);
				var surface = this.getSurface(surfaceId);
				surface.cacheState = cacheState;
			}
		}, {
			key: 'cacheSurfaceRenderAttrs_',

			/**
    * Caches surface render attributes into a O(k) flat map representation.
    * Relevant for performance to calculate the surfaces group that were
    * modified by attributes mutation.
    * @param {string} surfaceId The surface id to be cached into the flat map.
    * @param {Object=} opt_config Optional component configuration.
    * @protected
    */
			value: function cacheSurfaceRenderAttrs_(surfaceId, opt_config) {
				var attrs = this.getSurface(surfaceId).renderAttrs || [];
				for (var i = 0; i < attrs.length; i++) {
					if (!this.surfacesRenderAttrs_[attrs[i]]) {
						this.surfacesRenderAttrs_[attrs[i]] = {};
						this.addMissingAttr_(attrs[i], opt_config ? opt_config[attrs[i]] : null);
					}
					this.surfacesRenderAttrs_[attrs[i]][surfaceId] = true;
				}
			}
		}, {
			key: 'checkHasElementTag_',

			/**
    * Checks if the given content has an element tag with the given id.
    * @param {!Element|string} content
    * @param {string} id
    * @return {boolean}
    * @protected
    */
			value: function checkHasElementTag_(content, id) {
				return _core['default'].isString(content) ? content.indexOf(' id="' + id + '"') !== -1 : content.id === id;
			}
		}, {
			key: 'clearSurfacesCache_',

			/**
    * Clears the surfaces content cache.
    * @protected
    */
			value: function clearSurfacesCache_() {
				for (var surfaceId in this.surfaces_) {
					this.getSurface(surfaceId).cacheState = Component.Cache.NOT_INITIALIZED;
				}
			}
		}, {
			key: 'compareCacheStates_',

			/**
    * Compares cache states.
    * @param {number} currentCacheState
    * @param {number} previousCacheState
    * @return {boolean} True if there's a cache hit, or false for cache miss.
    */
			value: function compareCacheStates_(currentCacheState, previousCacheState) {
				return currentCacheState !== Component.Cache.NOT_INITIALIZED && currentCacheState !== Component.Cache.NOT_CACHEABLE && currentCacheState === previousCacheState;
			}
		}, {
			key: 'computeSurfaceCacheState_',

			/**
    * Computes the cache state for the surface content. If value is string, the
    * cache state is represented by its hashcode.
    * @param {Object} value The value to calculate the cache state.
    * @return {Object} The computed cache state.
    * @protected
    */
			value: function computeSurfaceCacheState_(value) {
				if (_core['default'].isString(value)) {
					if (_features['default'].checkAttrOrderChange()) {
						value = this.convertHtmlToBrowserFormat_(value);
					}
					return _string['default'].hashCode(value);
				}
				return Component.Cache.NOT_CACHEABLE;
			}
		}, {
			key: 'computeSurfacesCacheStateFromDom_',

			/**
    * Computes the cache state for the surface content based on the decorated
    * DOM element. The innerHTML of the surface element is read and compressed
    * in order to minimize mismatches caused by breaking spaces or HTML
    * formatting differences that does not affect the content structure.
    * @protected
    */
			value: function computeSurfacesCacheStateFromDom_() {
				for (var surfaceId in this.surfaces_) {
					if (!this.getSurface(surfaceId).componentName) {
						this.cacheSurfaceContent(surfaceId, _html['default'].compress(this.getSurfaceElement(surfaceId).outerHTML));
					}
				}
			}
		}, {
			key: 'convertHtmlToBrowserFormat_',

			/**
    * Converts the given html string to the format the current browser uses
    * when html is rendered. This is done by rendering the html in a temporary
    * element, and returning its resulting rendered html.
    * @param {string} htmlString The html to be converted.
    * @return {string}
    * @protected
    */
			value: function convertHtmlToBrowserFormat_(htmlString) {
				var element = document.createElement('div');
				_dom['default'].append(element, htmlString);
				return element.innerHTML;
			}
		}, {
			key: 'createPlaceholderSurface_',

			/**
    * Creates a surface that was found via a string placeholder.
    * @param {string?} surfaceId
    * @param {string} type The surface type (either "s" or "c").
    * @param {string?} extra String with extra information about the surface.
    * @param {string=} opt_parentSurfaceId The id of the surface that contains
    *   the surface to be created, or undefined if there is none.
    * @return {string} The id of the created surface.
    * @protected
    */
			value: function createPlaceholderSurface_(surfaceId, type, extra, opt_parentSurfaceId) {
				surfaceId = surfaceId || this.generateSurfaceId_(type, opt_parentSurfaceId);
				if (!this.getSurface(surfaceId)) {
					this.addSurface(surfaceId, this.buildPlaceholderSurfaceData_(type, extra));
				}
				return surfaceId;
			}
		}, {
			key: 'createSubComponent_',

			/**
    * Creates a sub component.
    * @param {string} componentName
    * @param {string} componentId
    * @return {!Component}
    * @protected
    */
			value: function createSubComponent_(componentName, componentId) {
				this.components[componentId] = Component.componentsCollector.createComponent(componentName, componentId);
				return this.components[componentId];
			}
		}, {
			key: 'createSurfaceElement_',

			/**
    * Creates the surface element with its id namespaced to the component id.
    * @param {string} surfaceElementId The id of the element for the surface to be
    *   created.
    * @return {Element} The surface element.
    * @protected
    */
			value: function createSurfaceElement_(surfaceElementId) {
				var el = document.createElement(this.constructor.SURFACE_TAG_NAME_MERGED);
				el.id = surfaceElementId;
				return el;
			}
		}, {
			key: 'decorateAsSubComponent',

			/**
    * Decorates this component as a subcomponent, meaning that no rendering is
    * needed since it was already rendered by the parent component. Handles the
    * same logics that `renderAsSubComponent`, but also makes sure that the
    * surfaces content is updated if the html is incorrect for the given data.
    */
			value: function decorateAsSubComponent() {
				this.decorating_ = true;
				this.computeSurfacesCacheStateFromDom_();
				this.renderAsSubComponent();
				this.decorating_ = false;
			}
		}, {
			key: 'decorateInternal',

			/**
    * Lifecycle. Internal implementation for decoration. Any extra operation
    * necessary to prepare the component DOM must be implemented in this phase.
    */
			value: function decorateInternal() {}
		}, {
			key: 'delegate',

			/**
    * Listens to a delegate event on the component's element.
    * @param {string} eventName The name of the event to listen to.
    * @param {string} selector The selector that matches the child elements that
    *   the event should be triggered for.
    * @param {!function(!Object)} callback Function to be called when the event is
    *   triggered. It will receive the normalized event object.
    * @return {!DomEventHandle} Can be used to remove the listener.
    */
			value: function delegate(eventName, selector, callback) {
				var handle = _dom['default'].delegate(this.element, eventName, selector, callback);
				this.delegateEventHandler_.add(handle);
				return handle;
			}
		}, {
			key: 'detach',

			/**
    * Invokes the detached Lifecycle. When detached, the component element is
    * removed from the DOM and any other action to be performed must be
    * implemented in this method, such as, unbinding DOM events. A component
    * can be detached multiple times.
    * @chainable
    */
			value: function detach() {
				if (this.inDocument) {
					this.element.parentNode.removeChild(this.element);
					this.inDocument = false;
					this.detached();
				}
				this.eventsCollector_.detachAllListeners();
				return this;
			}
		}, {
			key: 'detached',

			/**
    * Lifecycle. When detached, the component element is removed from the DOM
    * and any other action to be performed must be implemented in this method,
    * such as, unbinding DOM events. A component can be detached multiple
    * times, therefore the undo behavior for any action performed in this phase
    * must be implemented on the attach phase.
    */
			value: function detached() {}
		}, {
			key: 'created_',

			/**
    * Internal implementation for the creation phase of the component.
    * @protected
    */
			value: function created_() {
				this.on('eventsChanged', this.onEventsChanged_);
				this.addListenersFromObj_(this.events);

				this.on('attrsChanged', this.handleAttributesChanges_);
				Component.componentsCollector.addComponent(this);
			}
		}, {
			key: 'decorate',

			/**
    * Lifecycle. Creates the component using existing DOM elements. Often the
    * component can be created using existing elements in the DOM to leverage
    * progressive enhancement. Any extra operation necessary to prepare the
    * component DOM must be implemented in this phase. Decorate phase replaces
    * render phase.
    *
    * Decoration Lifecycle:
    *   decorate - Decorate is manually called.
    *   decorateInternal - Internal implementation for decoration happens.
    *   render surfaces - All surfaces content are rendered.
    *   attribute synchronization - All synchronization methods are called.
    *   attach - Attach Lifecycle is called.
    * @chainable
    */
			value: function decorate() {
				if (this.inDocument) {
					throw new Error(Component.Error.ALREADY_RENDERED);
				}
				this.decorating_ = true;

				this.decorateInternal();

				// Need to go through all surface placeholders on decorate to make sure they are
				// properly created for the first time.
				this.replaceSurfacePlaceholders_(this.getElementContent_());

				this.computeSurfacesCacheStateFromDom_(); // TODO(edu): This optimization seems worth it, analyze it.
				this.renderSurfacesContent_(this.surfaces_); // TODO(edu): Sync surfaces on decorate?

				this.attachInlineListeners_();
				this.syncAttrs_();

				this.attach();

				this.decorating_ = false;
				this.wasRendered = true;

				return this;
			}
		}, {
			key: 'disposeSubComponents_',

			/**
    * Calls `dispose` on all subcomponents.
    * @protected
    */
			value: function disposeSubComponents_() {
				var ids = Object.keys(this.components);
				for (var i = 0; i < ids.length; i++) {
					var component = this.components[ids[i]];
					if (!component.isDisposed()) {
						Component.componentsCollector.removeComponent(component);
						component.dispose();
					}
				}
				this.components = null;
			}
		}, {
			key: 'disposeInternal',

			/**
    * @inheritDoc
    */
			value: function disposeInternal() {
				this.detach();

				if (this.elementEventProxy_) {
					this.elementEventProxy_.dispose();
					this.elementEventProxy_ = null;
				}

				this.delegateEventHandler_.removeAllListeners();
				this.delegateEventHandler_ = null;

				this.disposeSubComponents_();
				this.generatedIdCount_ = null;
				this.surfaces_ = null;
				this.surfacesRenderAttrs_ = null;
				_get(Object.getPrototypeOf(Component.prototype), 'disposeInternal', this).call(this);
			}
		}, {
			key: 'syncAttrs_',

			/**
    * Fires attributes synchronization changes for attributes.
    * @protected
    */
			value: function syncAttrs_() {
				var attrNames = this.getAttrNames();
				for (var i = 0; i < attrNames.length; i++) {
					this.fireAttrChange_(attrNames[i]);
				}
			}
		}, {
			key: 'syncAttrsFromChanges_',

			/**
    * Fires attributes synchronization changes for attributes.
    * @param {Object.<string, Object>} changes Object containing the attribute
    *     name as key and an object with newVal and prevVal as value.
    * @protected
    */
			value: function syncAttrsFromChanges_(changes) {
				for (var attr in changes) {
					this.fireAttrChange_(attr, changes[attr]);
				}
			}
		}, {
			key: 'findElementById_',

			/**
    * Finds the element that matches the given id on this component. This searches
    * on the document first, for performance. If the element is not found, it's
    * searched in the component's element directly.
    * @param {string} id
    * @return {Element}
    * @protected
    */
			value: function findElementById_(id) {
				return document.getElementById(id) || this.element.querySelector('#' + id);
			}
		}, {
			key: 'fireAttrChange_',

			/**
    * Fires attribute synchronization change for the attribute.
    * @param {Object.<string, Object>} change Object containing newVal and
    *     prevVal keys.
    * @protected
    */
			value: function fireAttrChange_(attr, opt_change) {
				var fn = this['sync' + attr.charAt(0).toUpperCase() + attr.slice(1)];
				if (_core['default'].isFunction(fn)) {
					if (!opt_change) {
						opt_change = {
							newVal: this[attr],
							prevVal: undefined
						};
					}
					fn.call(this, opt_change.newVal, opt_change.prevVal);
				}
			}
		}, {
			key: 'generateSurfaceId_',

			/**
    * Generates an id for a surface that was found inside the contents of the main
    * element or of a parent surface.
    * @param {string} type Either "comp" or "surface", to indicate the surface's type.
    * @param {string=} opt_parentSurfaceId The id of the parent surface, or undefined
    *   if there is none.
    * @return {string} The generated id.
    */
			value: function generateSurfaceId_(type, opt_parentSurfaceId) {
				var parentSurfaceId = opt_parentSurfaceId || '';
				var parentSurfacePrefix = parentSurfaceId ? parentSurfaceId + '-' : '';
				this.generatedIdCount_[parentSurfaceId] = (this.generatedIdCount_[parentSurfaceId] || 0) + 1;
				var id = parentSurfacePrefix + type + this.generatedIdCount_[parentSurfaceId];
				return type === Component.SurfaceType.COMPONENT ? this.makeSurfaceId_(id) : id;
			}
		}, {
			key: 'getComponentHtml',

			/**
    * Gets the html that should be used to build this component's main element with
    * some content.
    * @param {string} content
    * @return {string}
    */
			value: function getComponentHtml(content) {
				return this.wrapContentIfNecessary(content, this.id, this.constructor.ELEMENT_TAG_NAME_MERGED);
			}
		}, {
			key: 'getElementContent',

			/**
    * Gets the content that should be rendered in the component's main element.
    * Should be implemented by subclasses.
    * @return {Object|string} The content to be rendered. If the content is a
    *   string, surfaces can be represented by placeholders in the format specified
    *   by Component.SURFACE_REGEX. Also, if the string content's main wrapper has
    *   the component's id, then it will be used to render the main element tag.
    */
			value: function getElementContent() {}
		}, {
			key: 'getElementContent_',

			/**
    * Internal function for getting the component element's content. Stores the
    * result in a variable so it can be accessed later without building it again.
    * @return {Object|string}
    * @protected
    */
			value: function getElementContent_() {
				this.elementContent_ = this.elementContent_ || this.getElementContent();
				return this.elementContent_;
			}
		}, {
			key: 'getElementExtendedContent',

			/**
    * Calls `getElementContent` and replaces all placeholders in the returned content.
    * @return {string} The content with all placeholders already replaced.
    */
			value: function getElementExtendedContent() {
				return this.replaceSurfacePlaceholders_(this.getElementContent_());
			}
		}, {
			key: 'getModifiedSurfacesFromChanges_',

			/**
    * Gets surfaces that got modified by the specified attributes changes.
    * @param {Object.<string, Object>} changes Object containing the attribute
    *     name as key and an object with newVal and prevVal as value.
    * @return {Object.<string, boolean>} Object containing modified surface ids
    *     as key and true as value.
    */
			value: function getModifiedSurfacesFromChanges_(changes) {
				var surfaces = [];
				for (var attr in changes) {
					if (this.surfacesRenderAttrs_[attr]) {
						surfaces.push(this.surfacesRenderAttrs_[attr]);
					}
				}
				return _object['default'].mixin.apply(null, surfaces);
			}
		}, {
			key: 'getNonComponentSurfaceHtml',

			/**
    * Same as `getSurfaceHtml`, but only called for non component surfaces.
    * @param {string} surfaceId
    * @param {string} content
    * @return {string}
    */
			value: function getNonComponentSurfaceHtml(surfaceId, content) {
				var surfaceElementId = this.makeSurfaceId_(surfaceId);
				return this.wrapContentIfNecessary(content, surfaceElementId, this.constructor.SURFACE_TAG_NAME_MERGED);
			}
		}, {
			key: 'getSurface',

			/**
    * Gets surface configuration object. If surface is not registered returns
    * null.
    * @param {string} surfaceId The surface id.
    * @return {?Object} The surface configuration object.
    */
			value: function getSurface(surfaceId) {
				return this.surfaces_[surfaceId] || null;
			}
		}, {
			key: 'getSurfaceContent',

			/**
    * Gets the content for the requested surface. Should be implemented by subclasses.
    * @param {string} surfaceId The surface id.
    * @return {Object|string} The content to be rendered. If the content is a
    *   string, surfaces can be represented by placeholders in the format specified
    *   by Component.SURFACE_REGEX.
    */
			value: function getSurfaceContent() {}
		}, {
			key: 'getSurfaceContent_',

			/**
    * Gets the content for the requested surface. By default this just calls
    * `getSurfaceContent`, but can be overriden to add more behavior (check
    * `SoyComponent` for an example).
    * @param {string} surfaceId The surface id.
    * @return {Object|string} The content to be rendered.
    * @protected
    */
			value: function getSurfaceContent_(surfaceId) {
				var surface = this.getSurface(surfaceId);
				if (surface.componentName) {
					if (this.components[surfaceId].wasRendered) {
						return '';
					} else {
						return this.components[surfaceId].getElementExtendedContent();
					}
				} else {
					return this.getSurfaceContent(surfaceId);
				}
			}
		}, {
			key: 'getSurfaceElement',

			/**
    * Queries from the document or creates an element for the surface. Surface
    * elements have its surface id namespaced to the component id, e.g. for a
    * component with id `gallery` and a surface with id `pictures` the surface
    * element will be represented by the id `gallery-pictures`. Surface
    * elements must also be appended to the component element.
    * @param {string} surfaceId The surface id.
    * @return {Element} The surface element or null if surface not registered.
    */
			value: function getSurfaceElement(surfaceId) {
				var surface = this.getSurface(surfaceId);
				if (!surface) {
					return null;
				}
				if (!surface.element) {
					if (surface.componentName) {
						surface.element = this.components[surfaceId].element;
					} else {
						var surfaceElementId = this.makeSurfaceId_(surfaceId);
						surface.element = this.findElementById_(surfaceElementId) || this.createSurfaceElement_(surfaceElementId);
					}
				}
				return surface.element;
			}
		}, {
			key: 'getSurfaceHtml',

			/**
    * Gets the html that should be used to build a surface's main element with its
    * content.
    * @param {string} surfaceId
    * @param {string} content
    * @return {string}
    */
			value: function getSurfaceHtml(surfaceId, content) {
				var surface = this.getSurface(surfaceId);
				if (surface.componentName) {
					return this.components[surfaceId].getComponentHtml(content);
				} else {
					return this.getNonComponentSurfaceHtml(surfaceId, content);
				}
			}
		}, {
			key: 'getSurfaces',

			/**
    * A map of surface ids to the respective surface object.
    * @return {!Object}
    */
			value: function getSurfaces() {
				return this.surfaces_;
			}
		}, {
			key: 'handleAttributesChanges_',

			/**
    * Handles attributes batch changes. Responsible for surface mutations and
    * attributes synchronization.
    * @param {Event} event
    * @protected
    */
			value: function handleAttributesChanges_(event) {
				if (this.inDocument) {
					this.renderSurfacesContent_(this.getModifiedSurfacesFromChanges_(event.changes));
				}
				this.syncAttrsFromChanges_(event.changes);
			}
		}, {
			key: 'onEventsChanged_',

			/**
    * Fired when the `events` attribute value is changed.
    * @param {!Object} event
    * @protected
    */
			value: function onEventsChanged_(event) {
				this.eventsAttrHandler_.removeAllListeners();
				this.addListenersFromObj_(event.newVal);
			}
		}, {
			key: 'makeId_',

			/**
    * Makes an unique id for the component.
    * @return {string} Unique id.
    * @protected
    */
			value: function makeId_() {
				return 'metal_c_' + _core['default'].getUid(this);
			}
		}, {
			key: 'makeSurfaceId_',

			/**
    * Makes the id for the surface scoped by the component.
    * @param {string} surfaceId The surface id.
    * @return {string}
    * @protected
    */
			value: function makeSurfaceId_(surfaceId) {
				return this.id + '-' + surfaceId;
			}
		}, {
			key: 'mergeElementClasses_',

			/**
    * Merges an array of values for the ELEMENT_CLASSES property into a single object.
    * @param {!Array.<string>} values The values to be merged.
    * @return {!string} The merged value.
    * @protected
    */
			value: function mergeElementClasses_(values) {
				var marked = {};
				return values.filter(function (val) {
					if (!val || marked[val]) {
						return false;
					} else {
						marked[val] = true;
						return true;
					}
				}).join(' ');
			}
		}, {
			key: 'mergeObjects_',

			/**
    * Merges an array of objects into a single object. Used by the SURFACES static
    * variable.
    * @param {!Array} values The values to be merged.
    * @return {!Object} The merged value.
    * @protected
    */
			value: function mergeObjects_(values) {
				return _object['default'].mixin.apply(null, [{}].concat(values.reverse()));
			}
		}, {
			key: 'removeSurface',

			/**
    * Unregisters a surface and removes its element from the DOM.
    * @param {string} surfaceId The surface id.
    * @chainable
    */
			value: function removeSurface(surfaceId) {
				var el = this.getSurfaceElement(surfaceId);
				if (el && el.parentNode) {
					el.parentNode.removeChild(el);
				}
				delete this.surfaces_[surfaceId];
				return this;
			}
		}, {
			key: 'render',

			/**
    * Lifecycle. Renders the component into the DOM. Render phase replaces
    * decorate phase, without progressive enhancement support.
    *
    * Render Lifecycle:
    *   render - Decorate is manually called.
    *   renderInternal - Internal implementation for rendering happens.
    *   render surfaces - All surfaces content are rendered.
    *   attribute synchronization - All synchronization methods are called.
    *   attach - Attach Lifecycle is called.
    *
    * @param {(string|Element)=} opt_parentElement Optional parent element
    *     to render the component.
    * @param {(string|Element)=} opt_siblingElement Optional sibling element
    *     to render the component before it. Relevant when the component needs
    *     to be rendered before an existing element in the DOM, e.g.
    *     `component.render(null, existingElement)`.
    * @chainable
    */
			value: function render(opt_parentElement, opt_siblingElement) {
				if (this.wasRendered) {
					throw new Error(Component.Error.ALREADY_RENDERED);
				}

				this.renderInternal();

				this.clearSurfacesCache_();
				this.renderSurfacesContent_(this.surfaces_);

				this.attachInlineListeners_();
				this.syncAttrs_();

				this.attach(opt_parentElement, opt_siblingElement);

				this.wasRendered = true;

				return this;
			}
		}, {
			key: 'renderAsSubComponent',

			/**
    * Renders this component as a subcomponent, meaning that no actual rendering is
    * needed since it was already rendered by the parent component. This just handles
    * other logics from the rendering lifecycle, like attaching event listeners.
    */
			value: function renderAsSubComponent() {
				this.attachInlineListeners_();
				this.syncAttrs_();
				this.attach();
				this.wasRendered = true;
			}
		}, {
			key: 'renderComponentSurface_',

			/**
    * Renders a surface that holds a component.
    * @param {string} surfaceId
    * @param {(Object|string)?} opt_content The content to be rendered.
    * @protected
    */
			value: function renderComponentSurface_(surfaceId, opt_content) {
				var component = this.components[surfaceId];
				if (component.wasRendered) {
					Component.componentsCollector.updateComponent(surfaceId);
				} else if (opt_content) {
					var element = component.element;
					if (_dom['default'].isEmpty(element)) {
						// If we have the rendered content for this component, but it hasn't
						// been rendered in its element yet, we render it manually here. That
						// can happen if the subcomponent's element is set before the parent
						// element renders its content.
						_dom['default'].append(element, opt_content);
					}
					if (this.decorating_) {
						component.decorateAsSubComponent();
					} else {
						component.renderAsSubComponent();
					}
				} else {
					component.render();
				}
			}
		}, {
			key: 'renderContent_',

			/**
    * Renders the given content in the component's element.
    * @param {string} content The content to be rendered.
    * @protected
    */
			value: function renderContent_(content) {
				var element = this.element;
				var newElement;

				if (_core['default'].isString(content)) {
					content = _dom['default'].buildFragment(content);
					if (content.childNodes[0].id === this.id) {
						newElement = content.childNodes[0];
					}
				} else if (content.id === this.id) {
					newElement = content;
				}

				if (newElement) {
					this.updateElementAttributes_(element, newElement);
					content = newElement.childNodes;
				}
				_dom['default'].append(element, content);
			}
		}, {
			key: 'renderElement_',

			/**
    * Renders the component element into the DOM.
    * @param {(string|Element)=} opt_parentElement Optional parent element
    *     to render the component.
    * @param {(string|Element)=} opt_siblingElement Optional sibling element
    *     to render the component before it. Relevant when the component needs
    *     to be rendered before an existing element in the DOM, e.g.
    *     `component.render(null, existingElement)`.
    * @protected
    */
			value: function renderElement_(opt_parentElement, opt_siblingElement) {
				var element = this.element;
				element.id = this.id;
				if (opt_siblingElement || !element.parentNode) {
					var parent = _dom['default'].toElement(opt_parentElement) || this.DEFAULT_ELEMENT_PARENT;
					parent.insertBefore(element, _dom['default'].toElement(opt_siblingElement));
				}
			}
		}, {
			key: 'renderInternal',

			/**
    * Lifecycle. Internal implementation for rendering. Any extra operation
    * necessary to prepare the component DOM must be implemented in this phase.
    */
			value: function renderInternal() {
				var content = this.getElementExtendedContent();
				if (content) {
					this.renderContent_(content);
				}
			}
		}, {
			key: 'renderPlaceholderSurfaceContents_',

			/**
    * Renders the contents of all the surface placeholders found in the given content.
    * @param {string} content
    * @param {string} surfaceId The id of surface the content is from.
    * @protected
    */
			value: function renderPlaceholderSurfaceContents_(content, surfaceId) {
				var instance = this;
				content.replace(Component.SURFACE_REGEX, function (match, type, id) {
					id = id || instance.generateSurfaceId_(type, surfaceId);
					instance.renderSurfaceContent(id);
					return match;
				});
			}
		}, {
			key: 'renderSurfaceContent',

			/**
    * Render content into a surface. If the specified content is the same of
    * the current surface content, nothing happens. If the surface cache state
    * is not initialized or the content is not eligible for cache or content is
    * different, the surfaces re-renders. It's not recommended to use this
    * method directly since surface content can be provided by
    * `getSurfaceContent(surfaceId)`.
    * @param {string} surfaceId The surface id.
    * @param {(Object|string)?} opt_content The content to be rendered.
    * @param {string?} opt_cacheContent The content that should be cached for
    *   this surface. If none is given, the rendered content will be used for this.
    */
			value: function renderSurfaceContent(surfaceId, opt_content, opt_cacheContent) {
				var surface = this.getSurface(surfaceId);
				if (surface.componentName) {
					this.renderComponentSurface_(surfaceId, opt_content);
					return;
				}

				var content = opt_content || this.getSurfaceContent_(surfaceId);
				if (_core['default'].isDefAndNotNull(content)) {
					var previousCacheState = surface.cacheState;
					var cacheContent = opt_cacheContent || content;

					// We cache the entire original content first when decorating so we can compare
					// with the full content we got from the dom. After comparing, we cache the correct
					// value so updates can work as expected for this surface.
					this.cacheSurfaceContent(surfaceId, this.decorating_ ? content : cacheContent);
					var cacheHit = this.compareCacheStates_(surface.cacheState, previousCacheState);
					if (this.decorating_) {
						this.cacheSurfaceContent(surfaceId, cacheContent);
					}

					if (cacheHit) {
						if (this.decorating_) {
							this.eventsCollector_.attachListeners(cacheContent, surfaceId);
						}
						this.renderPlaceholderSurfaceContents_(content, surfaceId);
					} else {
						this.eventsCollector_.attachListeners(cacheContent, surfaceId);
						this.replaceSurfaceContent_(surfaceId, content);
					}
				}
			}
		}, {
			key: 'renderSurfacesContent_',

			/**
    * Renders all surfaces contents ignoring the cache.
    * @param {Object.<string, Object=>} surfaces Object map where the key is
    *     the surface id and value the optional surface configuration.
    * @protected
    */
			value: function renderSurfacesContent_(surfaces) {
				this.generatedIdCount_ = {};
				for (var surfaceId in surfaces) {
					if (!this.getSurface(surfaceId).handled) {
						this.renderSurfaceContent(surfaceId);
					}
				}
				if (this.wasRendered) {
					this.updatePlaceholderSurfaces_();
					this.eventsCollector_.detachUnusedListeners();
				}
			}
		}, {
			key: 'replaceSurfaceContent_',

			/**
    * Replaces the content of a surface with a new one.
    * @param {string} surfaceId The surface id.
    * @param {Element|string} content The content to be rendered.
    * @protected
    */
			value: function replaceSurfaceContent_(surfaceId, content) {
				var elementId = this.makeSurfaceId_(surfaceId);
				var el = this.getSurfaceElement(surfaceId);
				content = this.replaceSurfacePlaceholders_(content);
				if (this.checkHasElementTag_(content, elementId)) {
					var surface = this.getSurface(surfaceId);
					surface.element = content;
					if (_core['default'].isString(content)) {
						surface.element = _dom['default'].buildFragment(content).childNodes[0];
					}
					if (el.parentNode) {
						_dom['default'].replace(el, surface.element);
					}
				} else {
					_dom['default'].removeChildren(el);
					_dom['default'].append(el, content);
				}
			}
		}, {
			key: 'replaceSurfacePlaceholders_',

			/**
    * Replaces the given content's surface placeholders with their real contents.
    * @param {string|Element} content
    * @param {string=} opt_surfaceId The id of the surface that contains the given
    *   content, or undefined if the content is from the main element.
    * @return {string} The final string with replaced placeholders.
    * @protected
    */
			value: function replaceSurfacePlaceholders_(content, opt_surfaceId) {
				if (!_core['default'].isString(content)) {
					return content;
				}

				var instance = this;
				return content.replace(Component.SURFACE_REGEX, function (match, type, id, extra) {
					// Surfaces should already have been created before being rendered so they can be
					// accessed from their getSurfaceContent calls.
					id = instance.createPlaceholderSurface_(id, type, extra, opt_surfaceId);
					instance.getSurface(id).handled = true;

					var surfaceContent = instance.getSurfaceContent_(id);
					var surfaceHtml = instance.getSurfaceHtml(id, surfaceContent);
					var expandedHtml = instance.replaceSurfacePlaceholders_(surfaceHtml, id);
					instance.collectedSurfaces_.push({
						cacheContent: surfaceContent,
						content: expandedHtml,
						surfaceId: id
					});

					return expandedHtml;
				});
			}
		}, {
			key: 'setterElementFn_',

			/**
    * Setter logic for element attribute.
    * @param {string|Element} val
    * @return {Element}
    * @protected
    */
			value: function setterElementFn_(val) {
				var element = _dom['default'].toElement(val);
				if (!element) {
					element = this.valueElementFn_();
				}
				return element;
			}
		}, {
			key: 'syncElementClasses',

			/**
    * Attribute synchronization logic for the `elementClasses` attribute.
    * @param {string} newVal
    * @param {string} prevVal
    */
			value: function syncElementClasses(newVal, prevVal) {
				var classesToAdd = this.constructor.ELEMENT_CLASSES_MERGED;
				if (newVal) {
					classesToAdd = classesToAdd + ' ' + newVal;
				}
				if (prevVal) {
					_dom['default'].removeClasses(this.element, prevVal);
				}
				_dom['default'].addClasses(this.element, classesToAdd);
			}
		}, {
			key: 'syncVisible',

			/**
    * Attribute synchronization logic for `visible` attribute.
    * Updates the element's display value according to its visibility.
    * @param {boolean} newVal
    */
			value: function syncVisible(newVal) {
				this.element.style.display = newVal ? '' : 'none';
			}
		}, {
			key: 'updateElementAttributes_',

			/**
    * Sets the attributes from the second element to the first element.
    * @param {!Element} element
    * @param {!Element} newElement
    * @protected
    */
			value: function updateElementAttributes_(element, newElement) {
				var attrs = newElement.attributes;
				for (var i = 0; i < attrs.length; i++) {
					// The "id" and "class" html attributes are already synced via the "id"
					// and "elementClasses" component attributes, respectively.
					if (attrs[i].name !== 'id' && attrs[i].name !== 'class') {
						element.setAttribute(attrs[i].name, attrs[i].value);
					}
				}

				if (element.tagName !== newElement.tagName) {
					console.error('Changing the component element\'s tag name is not allowed. Make sure ' + 'to always return the same tag name for the component element on getElementContent, ' + 'as well as to set the static variable ELEMENT_TAG_NAME to the chosen value.');
				}
			}
		}, {
			key: 'updatePlaceholderSurface_',

			/**
    * Updates a surface after it has been rendered through placeholders.
    * @param {!{content: string, cacheContent: string, surfaceId: string}} collectedData
    *   Data about the collected surface. Should have the surface's id, content and the
    *   content that should be cached for it.
    * @protected
    */
			value: function updatePlaceholderSurface_(collectedData) {
				var surfaceId = collectedData.surfaceId;
				var surface = this.getSurface(surfaceId);
				if (surface.componentName) {
					// Elements of component surfaces are unchangeable, so we need to replace the
					// rendered element with the component's.
					_dom['default'].replace(this.findElementById_(surfaceId), this.getSurfaceElement(surfaceId));
				}

				if (this.decorating_ || surface.componentName) {
					// Component surfaces need to be handled in case some internal details have changed.
					// Also, if this component is being decorated, it needs to go through the regular flow
					// to check if the cache matches.
					this.renderSurfaceContent(surfaceId, collectedData.content, collectedData.cacheContent);
				} else {
					// This surface's element has either changed or never been created yet. Let's just
					// reset it to null, so it can be fetched from the dom again when necessary. Also,
					// since there's no need to do cache checks or rerender, let's just attach its
					// listeners and cache its content manually.
					surface.element = null;
					this.cacheSurfaceContent(surfaceId, collectedData.cacheContent);
					this.eventsCollector_.attachListeners(collectedData.cacheContent, surfaceId);
				}
			}
		}, {
			key: 'updatePlaceholderSurfaces_',

			/**
    * Updates all collected surfaces.
    * @protected
    */
			value: function updatePlaceholderSurfaces_() {
				for (var i = this.collectedSurfaces_.length - 1; i >= 0; i--) {
					this.updatePlaceholderSurface_(this.collectedSurfaces_[i]);
					this.getSurface(this.collectedSurfaces_[i].surfaceId).handled = false;
				}
				this.collectedSurfaces_ = [];
			}
		}, {
			key: 'validatorElementFn_',

			/**
    * Validator logic for element attribute.
    * @param {string|Element} val
    * @return {boolean} True if val is a valid element.
    * @protected
    */
			value: function validatorElementFn_(val) {
				return _core['default'].isElement(val) || _core['default'].isString(val);
			}
		}, {
			key: 'validatorElementClassesFn_',

			/**
    * Validator logic for elementClasses attribute.
    * @param {string} val
    * @return {boolean} True if val is a valid element classes.
    * @protected
    */
			value: function validatorElementClassesFn_(val) {
				return _core['default'].isString(val);
			}
		}, {
			key: 'validatorEventsFn_',

			/**
    * Validator logic for the `events` attribute.
    * @param {Object} val
    * @return {boolean}
    * @protected
    */
			value: function validatorEventsFn_(val) {
				return !_core['default'].isDefAndNotNull(val) || _core['default'].isObject(val);
			}
		}, {
			key: 'validatorIdFn_',

			/**
    * Validator logic for the `id` attribute.
    * @param {string} val
    * @return {boolean} True if val is a valid id.
    * @protected
    */
			value: function validatorIdFn_(val) {
				return _core['default'].isString(val);
			}
		}, {
			key: 'valueElementFn_',

			/**
    * Provides the default value for element attribute.
    * @return {Element} The element.
    * @protected
    */
			value: function valueElementFn_() {
				return document.createElement(this.constructor.ELEMENT_TAG_NAME_MERGED);
			}
		}, {
			key: 'valueIdFn_',

			/**
    * Provides the default value for id attribute.
    * @return {string} The id.
    * @protected
    */
			value: function valueIdFn_() {
				var element = this.element;
				return element && element.id ? element.id : this.makeId_();
			}
		}, {
			key: 'wrapContentIfNecessary',

			/**
    * Wraps the content with the given tag, unless the content already has an element with the
    * correct id.
    * @param {string} content
    * @param {string} id
    * @param {string} tag
    * @return {string}
    * @protected
    */
			value: function wrapContentIfNecessary(content, id, tag) {
				if (!this.checkHasElementTag_(content, id)) {
					content = '<' + tag + ' id="' + id + '">' + content + '</' + tag + '>';
				}
				return content;
			}
		}]);

		return Component;
	})(_Attribute2['default']);

	/**
  * Helper responsible for extracting components from strings and config data.
  * @type {!ComponentCollector}
  * @protected
  * @static
  */
	Component.componentsCollector = new _ComponentCollector['default']();

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
   * @type {Object<string, (function()|string)>}
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
			validator: _core['default'].isBoolean,
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
  * The regex used to search for surface placeholders.
  * @type {RegExp}
  * @static
  */
	Component.SURFACE_REGEX = /\%\%\%\%~(s|c)(?:-([^~:]+))?(?::([^~]+))?~\%\%\%\%/g;

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
   * Cache is not allowed for this state.
   */
		NOT_CACHEABLE: -1,

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
  * Valid surface types that can be used for string placeholders.
  * @enum {string}
  */
	Component.SurfaceType = {
		COMPONENT: 'c',
		NORMAL: 's'
	};

	/**
  * A list with attribute names that will automatically be rejected as invalid.
  * @type {!Array<string>}
  */
	Component.INVALID_ATTRS = ['components', 'elementContent'];

	module.exports = Component;
});