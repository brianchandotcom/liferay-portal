define(['exports', 'metal/src/metal', 'metal-dom/src/all/dom', './ComponentRenderer', 'metal-events/src/events', 'metal-state/src/State'], function (exports, _metal, _dom, _ComponentRenderer, _events, _State2) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _ComponentRenderer2 = _interopRequireDefault(_ComponentRenderer);

	var _State3 = _interopRequireDefault(_State2);

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

	var Component = function (_State) {
		_inherits(Component, _State);

		/**
   * Constructor function for `Component`.
   * @param {Object=} opt_config An object with the initial values for this
   *     component's state.
   * @param {boolean|string|Element=} opt_parentElement The element where the
   *     component should be rendered. Can be given as a selector or an element.
   *     If `false` is passed, the component won't be rendered automatically
   *     after created.
   * @constructor
   */

		function Component(opt_config, opt_parentElement) {
			_classCallCheck(this, Component);

			var _this = _possibleConstructorReturn(this, _State.call(this, opt_config));

			/**
    * All listeners that were attached until the `DomEventEmitterProxy` instance
    * was created.
    * @type {!Object<string, bool>}
    * @protected
    */
			_this.attachedListeners_ = {};

			/**
    * Gets all nested components.
    * @type {!Array<!Component>}
    */
			_this.components = {};

			/**
    * Instance of `DomEventEmitterProxy` which proxies events from the component's
    * element to the component itself.
    * @type {DomEventEmitterProxy}
    * @protected
    */
			_this.elementEventProxy_ = null;

			/**
    * The `EventHandler` instance for events attached from the `events` state key.
    * @type {!EventHandler}
    * @protected
    */
			_this.eventsStateKeyHandler_ = new _events.EventHandler();

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
			_metal.core.mergeSuperClassesProperty(_this.constructor, 'SYNC_UPDATES', _metal.array.firstDefinedValue);

			_this.renderer_ = _this.createRenderer();
			_this.renderer_.on('rendered', _this.rendered.bind(_this));

			_this.on('stateChanged', _this.handleStateChanged_);
			_this.newListenerHandle_ = _this.on('newListener', _this.handleNewListener_);
			_this.on('eventsChanged', _this.onEventsChanged_);
			_this.addListenersFromObj_(_this.events);

			_this.created();
			if (opt_parentElement !== false) {
				_this.render_(opt_parentElement);
			}
			_this.on('elementChanged', _this.onElementChanged_);
			return _this;
		}

		/**
   * Adds the necessary classes to the component's element.
   */


		Component.prototype.addElementClasses = function addElementClasses() {
			var classesToAdd = this.constructor.ELEMENT_CLASSES_MERGED;
			if (this.elementClasses) {
				classesToAdd = classesToAdd + ' ' + this.elementClasses;
			}
			_dom.dom.addClasses(this.element, classesToAdd);
		};

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
					this.eventsStateKeyHandler_.add(handler);
				}
			}
		};

		Component.prototype.attach = function attach(opt_parentElement, opt_siblingElement) {
			if (!this.inDocument) {
				this.renderElement_(opt_parentElement, opt_siblingElement);
				this.inDocument = true;
				this.emit('attached', {
					parent: opt_parentElement,
					sibling: opt_siblingElement
				});
				this.attached();
			}
			return this;
		};

		Component.prototype.attached = function attached() {};

		Component.prototype.addSubComponent = function addSubComponent(ref, component) {
			this.components[ref] = component;
		};

		Component.prototype.created = function created() {};

		Component.prototype.createRenderer = function createRenderer() {
			_metal.core.mergeSuperClassesProperty(this.constructor, 'RENDERER', _metal.array.firstDefinedValue);
			return new this.constructor.RENDERER_MERGED(this);
		};

		Component.prototype.delegate = function delegate(eventName, selector, callback) {
			return this.on('delegate:' + eventName + ':' + selector, callback);
		};

		Component.prototype.detach = function detach() {
			if (this.inDocument) {
				if (this.element && this.element.parentNode) {
					this.element.parentNode.removeChild(this.element);
				}
				this.inDocument = false;
				this.detached();
			}
			this.emit('detached');
			return this;
		};

		Component.prototype.detached = function detached() {};

		Component.prototype.disposed = function disposed() {};

		Component.prototype.disposeInternal = function disposeInternal() {
			this.disposed();

			this.detach();

			if (this.elementEventProxy_) {
				this.elementEventProxy_.dispose();
				this.elementEventProxy_ = null;
			}

			this.disposeSubComponents(Object.keys(this.components));
			this.components = null;

			this.renderer_.dispose();
			this.renderer_ = null;

			_State.prototype.disposeInternal.call(this);
		};

		Component.prototype.disposeSubComponents = function disposeSubComponents(keys) {
			for (var i = 0; i < keys.length; i++) {
				var component = this.components[keys[i]];
				if (component && !component.isDisposed()) {
					component.element = null;
					component.dispose();
					delete this.components[keys[i]];
				}
			}
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
				info.fn = this.getListenerFn(info.fn);
			}
			return info;
		};

		Component.prototype.getInitialConfig = function getInitialConfig() {
			return this.initialConfig_;
		};

		Component.prototype.getListenerFn = function getListenerFn(fnName) {
			if (_metal.core.isFunction(this[fnName])) {
				return this[fnName].bind(this);
			} else {
				console.error('No function named "' + fnName + '" was found in the ' + 'component "' + _metal.core.getFunctionName(this.constructor) + '". Make ' + 'sure that you specify valid function names when adding inline listeners.');
			}
		};

		Component.prototype.fireStateKeyChange_ = function fireStateKeyChange_(key, opt_change) {
			var fn = this['sync' + key.charAt(0).toUpperCase() + key.slice(1)];
			if (_metal.core.isFunction(fn)) {
				if (!opt_change) {
					opt_change = {
						newVal: this[key],
						prevVal: undefined
					};
				}
				fn.call(this, opt_change.newVal, opt_change.prevVal);
			}
		};

		Component.prototype.getRenderer = function getRenderer() {
			return this.renderer_;
		};

		Component.prototype.handleStateChanged_ = function handleStateChanged_(event) {
			this.syncStateFromChanges_(event.changes);
			this.emit('stateSynced', event);
		};

		Component.prototype.handleNewListener_ = function handleNewListener_(event) {
			this.attachedListeners_[event] = true;
		};

		Component.isComponentCtor = function isComponentCtor(fn) {
			return fn.prototype && fn.prototype[Component.COMPONENT_FLAG];
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

		Component.prototype.onElementChanged_ = function onElementChanged_(event) {
			if (event.prevVal === event.newVal) {
				// The `elementChanged` event will be fired whenever the element is set,
				// even if its value hasn't actually changed, since that's how State
				// handles objects. We need to check manually here.
				return;
			}

			this.setUpProxy_();
			this.elementEventProxy_.setOriginEmitter(event.newVal);
			if (event.newVal) {
				this.addElementClasses();
				this.syncVisible(this.visible);
			}
		};

		Component.prototype.onEventsChanged_ = function onEventsChanged_(event) {
			this.eventsStateKeyHandler_.removeAllListeners();
			this.addListenersFromObj_(event.newVal);
		};

		Component.render = function render(Ctor, opt_configOrElement, opt_element) {
			var config = opt_configOrElement;
			var element = opt_element;
			if (_metal.core.isElement(opt_configOrElement)) {
				config = null;
				element = opt_configOrElement;
			}
			var instance = new Ctor(config, false);
			instance.render_(element);
			return instance;
		};

		Component.prototype.render_ = function render_(opt_parentElement, opt_skipRender) {
			if (!opt_skipRender) {
				this.emit('render');
			}
			this.setUpProxy_();
			this.syncState_();
			this.attach(opt_parentElement);
			this.wasRendered = true;
		};

		Component.prototype.renderAsSubComponent = function renderAsSubComponent() {
			this.render_(null, true);
		};

		Component.prototype.renderElement_ = function renderElement_(opt_parentElement, opt_siblingElement) {
			var element = this.element;
			if (element && (opt_siblingElement || !element.parentNode)) {
				var parent = _dom.dom.toElement(opt_parentElement) || this.DEFAULT_ELEMENT_PARENT;
				parent.insertBefore(element, _dom.dom.toElement(opt_siblingElement));
			}
		};

		Component.prototype.setterElementFn_ = function setterElementFn_(newVal, currentVal) {
			var element = newVal;
			if (element) {
				element = _dom.dom.toElement(newVal) || currentVal;
			}
			return element;
		};

		Component.prototype.setUpProxy_ = function setUpProxy_() {
			if (this.elementEventProxy_) {
				return;
			}

			var proxy = new _dom.DomEventEmitterProxy(this.element, this);
			this.elementEventProxy_ = proxy;

			_metal.object.map(this.attachedListeners_, proxy.proxyEvent.bind(proxy));
			this.attachedListeners_ = null;

			this.newListenerHandle_.removeListener();
			this.newListenerHandle_ = null;
		};

		Component.prototype.syncState_ = function syncState_() {
			var keys = this.getStateKeys();
			for (var i = 0; i < keys.length; i++) {
				this.fireStateKeyChange_(keys[i]);
			}
		};

		Component.prototype.syncStateFromChanges_ = function syncStateFromChanges_(changes) {
			for (var key in changes) {
				this.fireStateKeyChange_(key, changes[key]);
			}
		};

		Component.prototype.syncElementClasses = function syncElementClasses(newVal, prevVal) {
			if (this.element && prevVal) {
				_dom.dom.removeClasses(this.element, prevVal);
			}
			this.addElementClasses();
		};

		Component.prototype.syncVisible = function syncVisible(newVal) {
			if (this.element) {
				this.element.style.display = newVal ? '' : 'none';
			}
		};

		Component.prototype.rendered = function rendered() {};

		Component.prototype.validatorElementClassesFn_ = function validatorElementClassesFn_(val) {
			return _metal.core.isString(val);
		};

		Component.prototype.validatorElementFn_ = function validatorElementFn_(val) {
			return _metal.core.isElement(val) || _metal.core.isString(val) || !_metal.core.isDefAndNotNull(val);
		};

		Component.prototype.validatorEventsFn_ = function validatorEventsFn_(val) {
			return !_metal.core.isDefAndNotNull(val) || _metal.core.isObject(val);
		};

		return Component;
	}(_State3.default);

	/**
  * Component state definition.
  * @type {Object}
  * @static
  */
	Component.STATE = {
		/**
   * Component element bounding box.
   * @type {Element}
   * @writeOnce
   */
		element: {
			setter: 'setterElementFn_',
			validator: 'validatorElementFn_'
		},

		/**
   * CSS classes to be applied to the element.
   * @type {string}
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
   * Indicates if the component is visible or not.
   * @type {boolean}
   */
		visible: {
			validator: _metal.core.isBoolean,
			value: true
		}
	};

	Component.COMPONENT_FLAG = '__metal_component__';

	/**
  * CSS classes to be applied to the element.
  * @type {string}
  * @protected
  * @static
  */
	Component.ELEMENT_CLASSES = '';

	/**
  * The `ComponentRenderer` that should be used. Components need to set this
  * to a subclass of `ComponentRenderer` that has the rendering logic, like
  * `SoyRenderer`.
  * @type {!ComponentRenderer}
  * @static
  */
	Component.RENDERER = _ComponentRenderer2.default;

	/**
  * Flag indicating if component updates will happen synchronously. Updates are
  * done asynchronously by default, which allows changes to be batched and
  * applied together.
  * @type {boolean}
  */
	Component.SYNC_UPDATES = false;

	/**
  * A list with state key names that will automatically be rejected as invalid.
  * @type {!Array<string>}
  */
	Component.INVALID_KEYS = ['components', 'wasRendered'];

	/**
  * Sets a prototype flag to easily determine if a given constructor is for
  * a component or not.
  */
	Component.prototype[Component.COMPONENT_FLAG] = true;

	exports.default = Component;
});
//# sourceMappingURL=Component.js.map