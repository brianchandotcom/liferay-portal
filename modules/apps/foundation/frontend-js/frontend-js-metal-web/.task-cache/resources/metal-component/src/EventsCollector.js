define("frontend-js-metal-web@1.0.6/metal-component/src/EventsCollector", ['exports', 'metal/src/metal', 'metal-events/src/events'], function (exports, _metal, _events) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

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

	var EventsCollector = function (_Disposable) {
		_inherits(EventsCollector, _Disposable);

		function EventsCollector(component) {
			_classCallCheck(this, EventsCollector);

			var _this = _possibleConstructorReturn(this, _Disposable.call(this));

			if (!component) {
				throw new Error('The component instance is mandatory');
			}

			/**
    * Holds the component intance.
    * @type {!Component}
    * @protected
    */
			_this.component_ = component;

			/**
    * Holds the attached delegate event handles, indexed by the css selector.
    * @type {!Object<string, EventHandler>}
    * @protected
    */
			_this.eventHandles_ = {};

			/**
    * Holds flags indicating which selectors have listeners.
    * @type {!Object<string, boolean>}
    * @protected
    */
			_this.hasListener_ = {};
			return _this;
		}

		/**
   * Attaches the listener described by the given params, unless it has already
   * been attached.
   * @param {string} eventType
   * @param {string} fnNamesString
   */


		EventsCollector.prototype.attachListener = function attachListener(eventType, fnNamesString) {
			var selector = '[data-on' + eventType + '="' + fnNamesString + '"]';

			this.hasListener_[selector] = true;

			if (!this.eventHandles_[selector]) {
				this.eventHandles_[selector] = new _events.EventHandler();
				var fnNames = fnNamesString.split(',');
				for (var i = 0; i < fnNames.length; i++) {
					var fn = this.component_.getListenerFn(fnNames[i]);
					if (fn) {
						this.eventHandles_[selector].add(this.component_.delegate(eventType, selector, this.onEvent_.bind(this, fn)));
					}
				}
			}
		};

		EventsCollector.prototype.detachAllListeners = function detachAllListeners() {
			for (var selector in this.eventHandles_) {
				if (this.eventHandles_[selector]) {
					this.eventHandles_[selector].removeAllListeners();
				}
			}
			this.eventHandles_ = {};
			this.listenerCounts_ = {};
		};

		EventsCollector.prototype.detachUnusedListeners = function detachUnusedListeners() {
			for (var selector in this.eventHandles_) {
				if (this.eventHandles_[selector] && !this.hasListener_[selector]) {
					this.eventHandles_[selector].removeAllListeners();
					this.eventHandles_[selector] = null;
				}
			}
		};

		EventsCollector.prototype.disposeInternal = function disposeInternal() {
			this.detachAllListeners();
			this.component_ = null;
		};

		EventsCollector.prototype.onEvent_ = function onEvent_(fn, event) {
			// This check prevents parent components from handling their child inline listeners.
			var eventComp = event.handledByComponent;
			if (!eventComp || eventComp === this.component_ || event.delegateTarget.contains(eventComp.element)) {
				event.handledByComponent = this.component_;
				return fn(event);
			}
		};

		EventsCollector.prototype.startCollecting = function startCollecting() {
			this.hasListener_ = {};
		};

		return EventsCollector;
	}(_metal.Disposable);

	exports.default = EventsCollector;
});
//# sourceMappingURL=EventsCollector.js.map