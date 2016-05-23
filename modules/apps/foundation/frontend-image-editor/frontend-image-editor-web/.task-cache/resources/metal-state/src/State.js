define(['exports', 'metal/src/metal', 'metal-events/src/events'], function (exports, _metal, _events) {
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

	var State = function (_EventEmitter) {
		_inherits(State, _EventEmitter);

		function State(opt_config) {
			_classCallCheck(this, State);

			var _this = _possibleConstructorReturn(this, _EventEmitter.call(this));

			/**
    * Object with information about the batch event that is currently
    * scheduled, or null if none is.
    * @type {Object}
    * @protected
    */
			_this.scheduledBatchData_ = null;

			/**
    * Object that contains information about all this instance's state keys.
    * @type {!Object<string, !Object>}
    * @protected
    */
			_this.stateInfo_ = {};

			/**
    * Object with the most recent values that state properties were set to
    * through either the constructor or setState calls.
    * @type {!Object<string, *>}
    */
			_this.config = {};

			_this.updateConfig_(opt_config || {});
			_this.setShouldUseFacade(true);
			_this.mergeInvalidKeys_();
			_this.addToStateFromStaticHint_(opt_config);
			return _this;
		}

		/**
   * Adds the given key to the state.
   * @param {string} name The name of the new state key.
   * @param {Object.<string, *>=} config The configuration object for the new
   *     key. See `addToState` for supported settings.
   * @param {*} initialValue The initial value of the new key.
   */


		State.prototype.addKeyToState = function addKeyToState(name, config, initialValue) {
			this.buildKeyInfo_(name, config, initialValue);
			Object.defineProperty(this, name, this.buildKeyPropertyDef_(name));
		};

		State.prototype.addToState = function addToState(configsOrName, opt_initialValuesOrConfig, opt_contextOrInitialValue) {
			if (_metal.core.isString(configsOrName)) {
				return this.addKeyToState(configsOrName, opt_initialValuesOrConfig, opt_contextOrInitialValue);
			}

			var initialValues = opt_initialValuesOrConfig || {};
			var names = Object.keys(configsOrName);

			var props = {};
			for (var i = 0; i < names.length; i++) {
				var name = names[i];
				this.buildKeyInfo_(name, configsOrName[name], initialValues[name]);
				props[name] = this.buildKeyPropertyDef_(name);
			}

			if (opt_contextOrInitialValue !== false) {
				Object.defineProperties(opt_contextOrInitialValue || this, props);
			}
		};

		State.prototype.addToStateFromStaticHint_ = function addToStateFromStaticHint_(opt_config) {
			var ctor = this.constructor;
			var defineContext = false;
			if (State.mergeStateStatic(ctor)) {
				defineContext = ctor.prototype;
			}
			this.addToState(ctor.STATE_MERGED, opt_config, defineContext);
		};

		State.prototype.assertValidStateKeyName_ = function assertValidStateKeyName_(name) {
			if (this.constructor.INVALID_KEYS_MERGED[name]) {
				throw new Error('It\'s not allowed to create a state key with the name "' + name + '".');
			}
		};

		State.prototype.buildKeyInfo_ = function buildKeyInfo_(name, config, initialValue) {
			this.assertValidStateKeyName_(name);

			this.stateInfo_[name] = {
				config: config || {},
				initialValue: initialValue,
				state: State.KeyStates.UNINITIALIZED
			};
		};

		State.prototype.buildKeyPropertyDef_ = function buildKeyPropertyDef_(name) {
			return {
				configurable: true,
				enumerable: true,
				get: function get() {
					return this.getStateKeyValue_(name);
				},
				set: function set(val) {
					this.setStateKeyValue_(name, val);
				}
			};
		};

		State.prototype.callFunction_ = function callFunction_(fn, args) {
			if (_metal.core.isString(fn)) {
				return this[fn].apply(this, args);
			} else if (_metal.core.isFunction(fn)) {
				return fn.apply(this, args);
			}
		};

		State.prototype.callSetter_ = function callSetter_(name, value, currentValue) {
			var info = this.stateInfo_[name];
			var config = info.config;
			if (config.setter) {
				value = this.callFunction_(config.setter, [value, currentValue]);
			}
			return value;
		};

		State.prototype.callValidator_ = function callValidator_(name, value) {
			var info = this.stateInfo_[name];
			var config = info.config;
			if (config.validator) {
				var validatorReturn = this.callFunction_(config.validator, [value, name, this]);

				if (validatorReturn instanceof Error) {
					console.error('Warning: ' + validatorReturn);
				}
				return validatorReturn;
			}
			return true;
		};

		State.prototype.canSetState = function canSetState(name) {
			var info = this.stateInfo_[name];
			return !info.config.writeOnce || !info.written;
		};

		State.prototype.disposeInternal = function disposeInternal() {
			_EventEmitter.prototype.disposeInternal.call(this);
			this.stateInfo_ = null;
			this.scheduledBatchData_ = null;
		};

		State.prototype.emitBatchEvent_ = function emitBatchEvent_() {
			if (!this.isDisposed()) {
				var data = this.scheduledBatchData_;
				this.scheduledBatchData_ = null;
				this.emit('stateChanged', data);
			}
		};

		State.prototype.get = function get(name) {
			return this[name];
		};

		State.prototype.getState = function getState(opt_names) {
			var state = {};
			var names = opt_names || this.getStateKeys();

			for (var i = 0; i < names.length; i++) {
				state[names[i]] = this[names[i]];
			}

			return state;
		};

		State.prototype.getStateKeyConfig = function getStateKeyConfig(name) {
			return (this.stateInfo_[name] || {}).config;
		};

		State.prototype.getStateKeys = function getStateKeys() {
			return Object.keys(this.stateInfo_);
		};

		State.prototype.getStateKeyValue_ = function getStateKeyValue_(name) {
			this.initStateKey_(name);
			return this.stateInfo_[name].value;
		};

		State.prototype.hasBeenSet = function hasBeenSet(name) {
			var info = this.stateInfo_[name];
			return info.state === State.KeyStates.INITIALIZED || info.initialValue;
		};

		State.prototype.informChange_ = function informChange_(name, prevVal) {
			if (this.shouldInformChange_(name, prevVal)) {
				var data = {
					key: name,
					newVal: this[name],
					prevVal: prevVal
				};
				this.emit(name + 'Changed', data);
				this.emit('stateKeyChanged', data);
				this.scheduleBatchEvent_(data);
			}
		};

		State.prototype.initStateKey_ = function initStateKey_(name) {
			var info = this.stateInfo_[name];
			if (info.state !== State.KeyStates.UNINITIALIZED) {
				return;
			}

			info.state = State.KeyStates.INITIALIZING;
			this.setInitialValue_(name);
			if (!info.written) {
				info.state = State.KeyStates.INITIALIZING_DEFAULT;
				this.setDefaultValue_(name);
			}
			info.state = State.KeyStates.INITIALIZED;
		};

		State.mergeState_ = function mergeState_(values) {
			return _metal.object.mixin.apply(null, [{}].concat(values.reverse()));
		};

		State.mergeStateStatic = function mergeStateStatic(ctor) {
			return _metal.core.mergeSuperClassesProperty(ctor, 'STATE', State.mergeState_);
		};

		State.prototype.mergeInvalidKeys_ = function mergeInvalidKeys_() {
			_metal.core.mergeSuperClassesProperty(this.constructor, 'INVALID_KEYS', function (values) {
				return _metal.array.flatten(values).reduce(function (merged, val) {
					if (val) {
						merged[val] = true;
					}
					return merged;
				}, {});
			});
		};

		State.prototype.removeStateKey = function removeStateKey(name) {
			this.stateInfo_[name] = null;
			delete this[name];
		};

		State.prototype.scheduleBatchEvent_ = function scheduleBatchEvent_(changeData) {
			if (!this.scheduledBatchData_) {
				_metal.async.nextTick(this.emitBatchEvent_, this);
				this.scheduledBatchData_ = {
					changes: {}
				};
			}

			var name = changeData.key;
			var changes = this.scheduledBatchData_.changes;
			if (changes[name]) {
				changes[name].newVal = changeData.newVal;
			} else {
				changes[name] = changeData;
			}
		};

		State.prototype.set = function set(name, value) {
			this[name] = value;
		};

		State.prototype.setDefaultValue_ = function setDefaultValue_(name) {
			var config = this.stateInfo_[name].config;

			if (config.value !== undefined) {
				this[name] = config.value;
			} else {
				this[name] = this.callFunction_(config.valueFn);
			}
		};

		State.prototype.setInitialValue_ = function setInitialValue_(name) {
			var info = this.stateInfo_[name];
			if (info.initialValue !== undefined) {
				this[name] = info.initialValue;
				info.initialValue = undefined;
			}
		};

		State.prototype.setState = function setState(values, opt_callback) {
			this.updateConfig_(values);
			var names = Object.keys(values);
			for (var i = 0; i < names.length; i++) {
				this[names[i]] = values[names[i]];
			}
			if (opt_callback && this.scheduledBatchData_) {
				this.once('stateChanged', opt_callback);
			}
		};

		State.prototype.setStateKeyValue_ = function setStateKeyValue_(name, value) {
			if (!this.canSetState(name) || !this.validateKeyValue_(name, value)) {
				return;
			}

			var info = this.stateInfo_[name];
			if (info.initialValue === undefined && info.state === State.KeyStates.UNINITIALIZED) {
				info.state = State.KeyStates.INITIALIZED;
			}

			var prevVal = this[name];
			info.value = this.callSetter_(name, value, prevVal);
			info.written = true;
			this.informChange_(name, prevVal);
		};

		State.prototype.shouldInformChange_ = function shouldInformChange_(name, prevVal) {
			var info = this.stateInfo_[name];
			return info.state === State.KeyStates.INITIALIZED && (_metal.core.isObject(prevVal) || prevVal !== this[name]);
		};

		State.prototype.updateConfig_ = function updateConfig_(values) {
			var prevConfig = this.config;
			this.config = _metal.object.mixin({}, this.config, values);
			this.emit('configChanged', {
				newVal: this.config,
				prevVal: prevConfig
			});
		};

		State.prototype.validateKeyValue_ = function validateKeyValue_(name, value) {
			var info = this.stateInfo_[name];

			return info.state === State.KeyStates.INITIALIZING_DEFAULT || this.callValidator_(name, value);
		};

		return State;
	}(_events.EventEmitter);

	/**
  * A list with state key names that will automatically be rejected as invalid.
  * Subclasses can define their own invalid keys by setting this static on their
  * constructors, which will be merged together and handled automatically.
  * @type {!Array<string>}
  */
	State.INVALID_KEYS = ['config', 'state', 'stateKey'];

	/**
  * Constants that represent the states that an a state key can be in.
  * @type {!Object}
  */
	State.KeyStates = {
		UNINITIALIZED: 0,
		INITIALIZING: 1,
		INITIALIZING_DEFAULT: 2,
		INITIALIZED: 3
	};

	exports.default = State;
});
//# sourceMappingURL=State.js.map