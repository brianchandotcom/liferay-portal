define("frontend-js-metal-web@1.0.0/metal-attribute/src/Attribute", ['exports', 'metal/src/metal', 'metal-events/src/events'], function (exports, _metal, _events) {
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

	var Attribute = function (_EventEmitter) {
		_inherits(Attribute, _EventEmitter);

		function Attribute(opt_config) {
			_classCallCheck(this, Attribute);

			var _this = _possibleConstructorReturn(this, _EventEmitter.call(this));

			/**
    * Object with information about the batch event that is currently
    * scheduled, or null if none is.
    * @type {Object}
    * @protected
    */
			_this.scheduledBatchData_ = null;

			/**
    * Object that contains information about all this instance's attributes.
    * @type {!Object<string, !Object>}
    * @protected
    */
			_this.attrsInfo_ = {};

			_this.setShouldUseFacade(true);
			_this.mergeInvalidAttrs_();
			_this.addAttrsFromStaticHint_(opt_config);
			return _this;
		}

		/**
   * Adds the given attribute.
   * @param {string} name The name of the new attribute.
   * @param {Object.<string, *>=} config The configuration object for the new attribute.
   *   This object can have the following keys:
   *   setter - Function for normalizing new attribute values. It receives the new value
   *   that was set, and returns the value that should be stored.
   *   validator - Function that validates new attribute values. When it returns false,
   *   the new value is ignored.
   *   value - The default value for this attribute. Note that setting this to an object
   *   will cause all attribute instances to use the same reference to the object. To
   *   have each attribute instance use a different reference, use the `valueFn` option
   *   instead.
   *   valueFn - A function that returns the default value for this attribute.
   *   writeOnce - Ignores writes to the attribute after it's been first written to. That is,
   *   allows writes only when setting the attribute for the first time.
   * @param {*} initialValue The initial value of the new attribute. This value has higher
   *   precedence than the default value specified in this attribute's configuration.
   */


		Attribute.prototype.addAttr = function addAttr(name, config, initialValue) {
			this.buildAttrInfo_(name, config, initialValue);
			Object.defineProperty(this, name, this.buildAttrPropertyDef_(name));
		};

		Attribute.prototype.addAttrs = function addAttrs(configs, initialValues, opt_defineContext) {
			initialValues = initialValues || {};
			var names = Object.keys(configs);

			var props = {};
			for (var i = 0; i < names.length; i++) {
				var name = names[i];
				this.buildAttrInfo_(name, configs[name], initialValues[name]);
				props[name] = this.buildAttrPropertyDef_(name);
			}

			if (opt_defineContext !== false) {
				Object.defineProperties(opt_defineContext || this, props);
			}
		};

		Attribute.prototype.addAttrsFromStaticHint_ = function addAttrsFromStaticHint_(config) {
			var ctor = this.constructor;
			var defineContext = false;
			if (Attribute.mergeAttrsStatic(ctor)) {
				defineContext = ctor.prototype;
			}
			this.addAttrs(ctor.ATTRS_MERGED, config, defineContext);
		};

		Attribute.prototype.assertValidAttrName_ = function assertValidAttrName_(name) {
			if (this.constructor.INVALID_ATTRS_MERGED[name]) {
				throw new Error('It\'s not allowed to create an attribute with the name "' + name + '".');
			}
		};

		Attribute.prototype.buildAttrInfo_ = function buildAttrInfo_(name, config, initialValue) {
			this.assertValidAttrName_(name);

			this.attrsInfo_[name] = {
				config: config || {},
				initialValue: initialValue,
				state: Attribute.States.UNINITIALIZED
			};
		};

		Attribute.prototype.buildAttrPropertyDef_ = function buildAttrPropertyDef_(name) {
			return {
				configurable: true,
				enumerable: true,
				get: function get() {
					return this.getAttrValue_(name);
				},
				set: function set(val) {
					this.setAttrValue_(name, val);
				}
			};
		};

		Attribute.prototype.callFunction_ = function callFunction_(fn, args) {
			if (_metal.core.isString(fn)) {
				return this[fn].apply(this, args);
			} else if (_metal.core.isFunction(fn)) {
				return fn.apply(this, args);
			}
		};

		Attribute.prototype.callSetter_ = function callSetter_(name, value, currentValue) {
			var info = this.attrsInfo_[name];
			var config = info.config;
			if (config.setter) {
				value = this.callFunction_(config.setter, [value, currentValue]);
			}
			return value;
		};

		Attribute.prototype.callValidator_ = function callValidator_(name, value) {
			var info = this.attrsInfo_[name];
			var config = info.config;
			if (config.validator) {
				return this.callFunction_(config.validator, [value]);
			}
			return true;
		};

		Attribute.prototype.canSetAttribute = function canSetAttribute(name) {
			var info = this.attrsInfo_[name];
			return !info.config.writeOnce || !info.written;
		};

		Attribute.prototype.disposeInternal = function disposeInternal() {
			_EventEmitter.prototype.disposeInternal.call(this);
			this.attrsInfo_ = null;
			this.scheduledBatchData_ = null;
		};

		Attribute.prototype.emitBatchEvent_ = function emitBatchEvent_() {
			if (!this.isDisposed()) {
				var data = this.scheduledBatchData_;
				this.scheduledBatchData_ = null;
				this.emit('attrsChanged', data);
			}
		};

		Attribute.prototype.get = function get(name) {
			return this[name];
		};

		Attribute.prototype.getAttrConfig = function getAttrConfig(name) {
			return (this.attrsInfo_[name] || {}).config;
		};

		Attribute.prototype.getAttrs = function getAttrs(opt_names) {
			var attrsMap = {};
			var names = opt_names || this.getAttrNames();

			for (var i = 0; i < names.length; i++) {
				attrsMap[names[i]] = this[names[i]];
			}

			return attrsMap;
		};

		Attribute.prototype.getAttrNames = function getAttrNames() {
			return Object.keys(this.attrsInfo_);
		};

		Attribute.prototype.getAttrValue_ = function getAttrValue_(name) {
			this.initAttr_(name);

			return this.attrsInfo_[name].value;
		};

		Attribute.prototype.hasBeenSet = function hasBeenSet(name) {
			var info = this.attrsInfo_[name];
			return info.state === Attribute.States.INITIALIZED || info.initialValue;
		};

		Attribute.prototype.informChange_ = function informChange_(name, prevVal) {
			if (this.shouldInformChange_(name, prevVal)) {
				var data = {
					attrName: name,
					newVal: this[name],
					prevVal: prevVal
				};
				this.emit(name + 'Changed', data);
				this.emit('attrChanged', data);
				this.scheduleBatchEvent_(data);
			}
		};

		Attribute.prototype.initAttr_ = function initAttr_(name) {
			var info = this.attrsInfo_[name];
			if (info.state !== Attribute.States.UNINITIALIZED) {
				return;
			}

			info.state = Attribute.States.INITIALIZING;
			this.setInitialValue_(name);
			if (!info.written) {
				info.state = Attribute.States.INITIALIZING_DEFAULT;
				this.setDefaultValue_(name);
			}
			info.state = Attribute.States.INITIALIZED;
		};

		Attribute.mergeAttrs_ = function mergeAttrs_(values) {
			return _metal.object.mixin.apply(null, [{}].concat(values.reverse()));
		};

		Attribute.mergeAttrsStatic = function mergeAttrsStatic(ctor) {
			return _metal.core.mergeSuperClassesProperty(ctor, 'ATTRS', Attribute.mergeAttrs_);
		};

		Attribute.prototype.mergeInvalidAttrs_ = function mergeInvalidAttrs_() {
			_metal.core.mergeSuperClassesProperty(this.constructor, 'INVALID_ATTRS', function (values) {
				return _metal.array.flatten(values).reduce(function (merged, val) {
					if (val) {
						merged[val] = true;
					}
					return merged;
				}, {});
			});
		};

		Attribute.prototype.removeAttr = function removeAttr(name) {
			this.attrsInfo_[name] = null;
			delete this[name];
		};

		Attribute.prototype.scheduleBatchEvent_ = function scheduleBatchEvent_(attrChangeData) {
			if (!this.scheduledBatchData_) {
				_metal.async.nextTick(this.emitBatchEvent_, this);
				this.scheduledBatchData_ = {
					changes: {}
				};
			}

			var name = attrChangeData.attrName;
			var changes = this.scheduledBatchData_.changes;
			if (changes[name]) {
				changes[name].newVal = attrChangeData.newVal;
			} else {
				changes[name] = attrChangeData;
			}
		};

		Attribute.prototype.set = function set(name, value) {
			this[name] = value;
		};

		Attribute.prototype.setAttrs = function setAttrs(values) {
			var names = Object.keys(values);

			for (var i = 0; i < names.length; i++) {
				this[names[i]] = values[names[i]];
			}
		};

		Attribute.prototype.setAttrValue_ = function setAttrValue_(name, value) {
			if (!this.canSetAttribute(name) || !this.validateAttrValue_(name, value)) {
				return;
			}

			var info = this.attrsInfo_[name];
			if (info.initialValue === undefined && info.state === Attribute.States.UNINITIALIZED) {
				info.state = Attribute.States.INITIALIZED;
			}

			var prevVal = this[name];
			info.value = this.callSetter_(name, value, prevVal);
			info.written = true;
			this.informChange_(name, prevVal);
		};

		Attribute.prototype.setDefaultValue_ = function setDefaultValue_(name) {
			var config = this.attrsInfo_[name].config;

			if (config.value !== undefined) {
				this[name] = config.value;
			} else {
				this[name] = this.callFunction_(config.valueFn);
			}
		};

		Attribute.prototype.setInitialValue_ = function setInitialValue_(name) {
			var info = this.attrsInfo_[name];
			if (info.initialValue !== undefined) {
				this[name] = info.initialValue;
				info.initialValue = undefined;
			}
		};

		Attribute.prototype.shouldInformChange_ = function shouldInformChange_(name, prevVal) {
			var info = this.attrsInfo_[name];
			return info.state === Attribute.States.INITIALIZED && (_metal.core.isObject(prevVal) || prevVal !== this[name]);
		};

		Attribute.prototype.validateAttrValue_ = function validateAttrValue_(name, value) {
			var info = this.attrsInfo_[name];

			return info.state === Attribute.States.INITIALIZING_DEFAULT || this.callValidator_(name, value);
		};

		return Attribute;
	}(_events.EventEmitter);

	Attribute.prototype.registerMetalComponent && Attribute.prototype.registerMetalComponent(Attribute, 'Attribute')


	/**
  * A list with attribute names that will automatically be rejected as invalid.
  * Subclasses can define their own invalid attributes by setting this static
  * on their constructors, which will be merged together and handled automatically.
  * @type {!Array<string>}
  */
	Attribute.INVALID_ATTRS = ['attr', 'attrs'];

	/**
  * Constants that represent the states that an attribute can be in.
  * @type {!Object}
  */
	Attribute.States = {
		UNINITIALIZED: 0,
		INITIALIZING: 1,
		INITIALIZING_DEFAULT: 2,
		INITIALIZED: 3
	};

	exports.default = Attribute;
});
//# sourceMappingURL=Attribute.js.map