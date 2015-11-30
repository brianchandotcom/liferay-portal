'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/metal/src/component/EventsCollector", ['exports', 'metal/src/core', 'metal/src/component/ComponentCollector', 'metal/src/disposable/Disposable', 'metal/src/events/EventHandler'], function (exports, _core, _ComponentCollector, _Disposable2, _EventHandler) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	var _ComponentCollector2 = _interopRequireDefault(_ComponentCollector);

	var _Disposable3 = _interopRequireDefault(_Disposable2);

	var _EventHandler2 = _interopRequireDefault(_EventHandler);

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

	var EventsCollector = (function (_Disposable) {
		_inherits(EventsCollector, _Disposable);

		function EventsCollector(component) {
			_classCallCheck(this, EventsCollector);

			var _this = _possibleConstructorReturn(this, _Disposable.call(this));

			if (!component) {
				throw new Error('The component instance is mandatory');
			}

			_this.component_ = component;
			_this.eventHandles_ = {};
			_this.groupHasListener_ = {};
			return _this;
		}

		EventsCollector.prototype.attachListener_ = function attachListener_(eventType, fnNamesString, groupName) {
			var selector = '[data-on' + eventType + '="' + fnNamesString + '"]';
			this.groupHasListener_[groupName][selector] = true;

			if (!this.eventHandles_[selector]) {
				this.eventHandles_[selector] = new _EventHandler2.default();
				var fnNames = fnNamesString.split(',');

				for (var i = 0; i < fnNames.length; i++) {
					var fn = this.getListenerFn(fnNames[i]);

					if (fn) {
						this.eventHandles_[selector].add(this.component_.delegate(eventType, selector, this.onEvent_.bind(this, fn)));
					}
				}
			}
		};

		EventsCollector.prototype.attachListeners = function attachListeners(content, groupName) {
			this.groupHasListener_[groupName] = {};
			this.attachListenersFromHtml_(content, groupName);
		};

		EventsCollector.prototype.attachListenersFromHtml_ = function attachListenersFromHtml_(content, groupName) {
			if (content.indexOf('data-on') === -1) {
				return;
			}

			var regex = /data-on([a-z]+)=['"]([^'"]+)['"]/g;
			var match = regex.exec(content);

			while (match) {
				this.attachListener_(match[1], match[2], groupName);
				match = regex.exec(content);
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
				if (this.eventHandles_[selector]) {
					var unused = true;

					for (var groupName in this.groupHasListener_) {
						if (this.groupHasListener_[groupName][selector]) {
							unused = false;
							break;
						}
					}

					if (unused) {
						this.eventHandles_[selector].removeAllListeners();
						this.eventHandles_[selector] = null;
					}
				}
			}
		};

		EventsCollector.prototype.disposeInternal = function disposeInternal() {
			this.detachAllListeners();
			this.component_ = null;
		};

		EventsCollector.prototype.getListenerFn = function getListenerFn(fnName) {
			var fnComponent;
			var split = fnName.split(':');

			if (split.length === 2) {
				fnName = split[1];
				fnComponent = _ComponentCollector2.default.components[split[0]];

				if (!fnComponent) {
					console.error('No component with the id "' + split[0] + '" has been collected' + 'yet. Make sure that you specify an id for an existing component when ' + 'adding inline listeners.');
				}
			}

			fnComponent = fnComponent || this.component_;

			if (_core2.default.isFunction(fnComponent[fnName])) {
				return fnComponent[fnName].bind(fnComponent);
			} else {
				console.error('No function named "' + fnName + '" was found in the component with id "' + fnComponent.id + '". Make sure that you specify valid function names when adding ' + 'inline listeners.');
			}
		};

		EventsCollector.prototype.hasAttachedForGroup = function hasAttachedForGroup(group) {
			return !!this.groupHasListener_.hasOwnProperty(group);
		};

		EventsCollector.prototype.onEvent_ = function onEvent_(fn, event) {
			var eventComp = event.handledByComponent;

			if (!eventComp || eventComp === this.component_ || event.delegateTarget.contains(eventComp.element)) {
				event.handledByComponent = this.component_;
				return fn(event);
			}
		};

		return EventsCollector;
	})(_Disposable3.default);

	exports.default = EventsCollector;
});
//# sourceMappingURL=EventsCollector.js.map