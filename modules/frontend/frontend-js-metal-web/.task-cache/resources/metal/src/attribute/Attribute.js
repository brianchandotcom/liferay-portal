define(
    "frontend-js-metal-web@1.0.0/metal/src/attribute/Attribute",
    ['exports', 'module', 'metal/src/array/array', 'metal/src/core', 'metal/src/object/object', 'metal/src/events/EventEmitter', 'metal/src/async/async'],
    function (exports, module, _metalSrcArrayArray, _metalSrcCore, _metalSrcObjectObject, _metalSrcEventsEventEmitter, _metalSrcAsyncAsync) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _array = _interopRequireDefault(_metalSrcArrayArray);

        var _core = _interopRequireDefault(_metalSrcCore);

        var _object = _interopRequireDefault(_metalSrcObjectObject);

        var _EventEmitter2 = _interopRequireDefault(_metalSrcEventsEventEmitter);

        var _async = _interopRequireDefault(_metalSrcAsyncAsync);

        /**
      * Attribute adds support for having object properties that can be watched for
      * changes, as well as configured with validators, setters and other options.
      * See the `addAttr` method for a complete list of available attribute
      * configuration options.
      * @constructor
      * @extends {EventEmitter}
      */

        var Attribute = (function (_EventEmitter) {
            _inherits(Attribute, _EventEmitter);

            function Attribute(opt_config) {
                _classCallCheck(this, Attribute);

                _get(Object.getPrototypeOf(Attribute.prototype), 'constructor', this).call(this);

                /**
        * Object with information about the batch event that is currently
        * scheduled, or null if none is.
        * @type {Object}
        * @protected
        */
                this.scheduledBatchData_ = null;

                /**
        * Object that contains information about all this instance's attributes.
        * @type {!Object<string, !Object>}
        * @protected
        */
                this.attrsInfo_ = {};

                this.setShouldUseFacade(true);
                this.mergeInvalidAttrs_();
                this.addAttrsFromStaticHint_(opt_config);
            }

            /**
       * A list with attribute names that will automatically be rejected as invalid.
       * Subclasses can define their own invalid attributes by setting this static
       * on their constructors, which will be merged together and handled automatically.
       * @type {!Array<string>}
       */

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

            _createClass(Attribute, [{
                key: 'addAttr',
                value: function addAttr(name, config, initialValue) {
                    this.buildAttrInfo_(name, config, initialValue);
                    Object.defineProperty(this, name, this.buildAttrPropertyDef_(name));
                }

                /**
        * Adds the given attributes.
        * @param {!Object.<string, !Object>} configs An object that maps the names of all the
        *   attributes to be added to their configuration objects.
        * @param {!Object.<string, *>} initialValues An object that maps the names of
        *   attributes to their initial values. These values have higher precedence than the
        *   default values specified in the attribute configurations.
        * @param {boolean|Object=} opt_defineContext If value is false
        *     `Object.defineProperties` will not be called. If value is a valid
        *     context it will be used as definition context, otherwise `this`
        *     will be the context.
        */
            }, {
                key: 'addAttrs',
                value: function addAttrs(configs, initialValues, opt_defineContext) {
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
                }

                /**
        * Adds attributes from super classes static hint `MyClass.ATTRS = {};`.
        * @param {!Object.<string, !Object>} configs An object that maps the names
        *     of all the attributes to be added to their configuration objects.
        * @protected
        */
            }, {
                key: 'addAttrsFromStaticHint_',
                value: function addAttrsFromStaticHint_(config) {
                    var ctor = this.constructor;
                    var defineContext = false;
                    if (Attribute.mergeAttrsStatic(ctor)) {
                        defineContext = ctor.prototype;
                    }
                    this.addAttrs(ctor.ATTRS_MERGED, config, defineContext);
                }

                /**
        * Checks that the given name is a valid attribute name. If it's not, an error
        * will be thrown.
        * @param {string} name The name to be validated.
        * @throws {Error}
        */
            }, {
                key: 'assertValidAttrName_',
                value: function assertValidAttrName_(name) {
                    if (this.constructor.INVALID_ATTRS_MERGED[name]) {
                        throw new Error('It\'s not allowed to create an attribute with the name "' + name + '".');
                    }
                }

                /**
        * Builds the info object for the requested attribute.
        * @param {string} name The name of the attribute.
        * @param {Object} config The config object of the attribute.
        * @param {*} initialValue The initial value of the attribute.
        * @protected
        */
            }, {
                key: 'buildAttrInfo_',
                value: function buildAttrInfo_(name, config, initialValue) {
                    this.assertValidAttrName_(name);

                    this.attrsInfo_[name] = {
                        config: config || {},
                        initialValue: initialValue,
                        state: Attribute.States.UNINITIALIZED
                    };
                }

                /**
        * Builds the property definition object for the requested attribute.
        * @param {string} name The name of the attribute.
        * @return {!Object}
        * @protected
        */
            }, {
                key: 'buildAttrPropertyDef_',
                value: function buildAttrPropertyDef_(name) {
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
                }

                /**
        * Calls the requested function, running the appropriate code for when it's
        * passed as an actual function object or just the function's name.
        * @param {!Function|string} fn Function, or name of the function to run.
        * @param {!Array} An optional array of parameters to be passed to the
        *   function that will be called.
        * @return {*} The return value of the called function.
        * @protected
        */
            }, {
                key: 'callFunction_',
                value: function callFunction_(fn, args) {
                    if (_core['default'].isString(fn)) {
                        return this[fn].apply(this, args);
                    } else if (_core['default'].isFunction(fn)) {
                        return fn.apply(this, args);
                    }
                }

                /**
        * Calls the attribute's setter, if there is one.
        * @param {string} name The name of the attribute.
        * @param {*} value The value to be set.
        * @return {*} The final value to be set.
        */
            }, {
                key: 'callSetter_',
                value: function callSetter_(name, value) {
                    var info = this.attrsInfo_[name];
                    var config = info.config;
                    if (config.setter) {
                        value = this.callFunction_(config.setter, [value]);
                    }
                    return value;
                }

                /**
        * Calls the attribute's validator, if there is one.
        * @param {string} name The name of the attribute.
        * @param {*} value The value to be validated.
        * @return {boolean} Flag indicating if value is valid or not.
        */
            }, {
                key: 'callValidator_',
                value: function callValidator_(name, value) {
                    var info = this.attrsInfo_[name];
                    var config = info.config;
                    if (config.validator) {
                        return this.callFunction_(config.validator, [value]);
                    }
                    return true;
                }

                /**
        * Checks if the it's allowed to write on the requested attribute.
        * @param {string} name The name of the attribute.
        * @return {boolean}
        */
            }, {
                key: 'canSetAttribute',
                value: function canSetAttribute(name) {
                    var info = this.attrsInfo_[name];
                    return !info.config.writeOnce || !info.written;
                }

                /**
        * @inheritDoc
        */
            }, {
                key: 'disposeInternal',
                value: function disposeInternal() {
                    _get(Object.getPrototypeOf(Attribute.prototype), 'disposeInternal', this).call(this);
                    this.attrsInfo_ = null;
                    this.scheduledBatchData_ = null;
                }

                /**
        * Emits the attribute change batch event.
        * @protected
        */
            }, {
                key: 'emitBatchEvent_',
                value: function emitBatchEvent_() {
                    if (!this.isDisposed()) {
                        var data = this.scheduledBatchData_;
                        this.scheduledBatchData_ = null;
                        this.emit('attrsChanged', data);
                    }
                }

                /**
        * Returns the value of the requested attribute.
        * Note: this can and should be accomplished by accessing the attribute as a regular property.
        * This should only be used in cases where a function is actually needed.
        * @param {string} name
        * @return {*}
        */
            }, {
                key: 'get',
                value: function get(name) {
                    return this[name];
                }

                /**
        * Gets the config object for the requested attribute.
        * @param {string} name The attribute's name.
        * @return {Object}
        * @protected
        */
            }, {
                key: 'getAttrConfig',
                value: function getAttrConfig(name) {
                    return (this.attrsInfo_[name] || {}).config;
                }

                /**
        * Returns an object that maps attribute names to their values.
        * @param {Array<string>=} opt_names A list of names of the attributes that should be
        *   returned. If none is given, all attributes will be returned.
        * @return {Object.<string, *>}
        */
            }, {
                key: 'getAttrs',
                value: function getAttrs(opt_names) {
                    var attrsMap = {};
                    var names = opt_names || this.getAttrNames();

                    for (var i = 0; i < names.length; i++) {
                        attrsMap[names[i]] = this[names[i]];
                    }

                    return attrsMap;
                }

                /**
        * Returns an array with all attribute names.
        * @return {Array.<string>}
        */
            }, {
                key: 'getAttrNames',
                value: function getAttrNames() {
                    return Object.keys(this.attrsInfo_);
                }

                /**
        * Gets the value of the specified attribute. This is passed as that attribute's
        * getter to the `Object.defineProperty` call inside the `addAttr` method.
        * @param {string} name The name of the attribute.
        * @return {*}
        * @protected
        */
            }, {
                key: 'getAttrValue_',
                value: function getAttrValue_(name) {
                    this.initAttr_(name);

                    return this.attrsInfo_[name].value;
                }

                /**
        * Informs of changes to an attributes value through an event. Won't trigger
        * the event if the value hasn't changed or if it's being initialized.
        * @param {string} name The name of the attribute.
        * @param {*} prevVal The previous value of the attribute.
        * @protected
        */
            }, {
                key: 'informChange_',
                value: function informChange_(name, prevVal) {
                    if (this.shouldInformChange_(name, prevVal)) {
                        var data = {
                            attrName: name,
                            newVal: this[name],
                            prevVal: prevVal
                        };
                        this.emit(name + 'Changed', data);
                        this.scheduleBatchEvent_(data);
                    }
                }

                /**
        * Initializes the specified attribute, giving it a first value.
        * @param {string} name The name of the attribute.
        * @protected
        */
            }, {
                key: 'initAttr_',
                value: function initAttr_(name) {
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
                }

                /**
        * Merges an array of values for the ATTRS property into a single object.
        * @param {!Array} values The values to be merged.
        * @return {!Object} The merged value.
        * @static
        * @protected
        */
            }, {
                key: 'mergeInvalidAttrs_',

                /**
        * Merges the values of the `INVALID_ATTRS` static for the whole hierarchy of
        * the current instance.
        * @protected
        */
                value: function mergeInvalidAttrs_() {
                    _core['default'].mergeSuperClassesProperty(this.constructor, 'INVALID_ATTRS', function (values) {
                        return _array['default'].flatten(values).reduce(function (merged, val) {
                            if (val) {
                                merged[val] = true;
                            }
                            return merged;
                        }, {});
                    });
                }

                /**
        * Removes the requested attribute.
        * @param {string} name The name of the attribute.
        */
            }, {
                key: 'removeAttr',
                value: function removeAttr(name) {
                    this.attrsInfo_[name] = null;
                    delete this[name];
                }

                /**
        * Schedules an attribute change batch event to be emitted asynchronously.
        * @param {!Object} attrChangeData Information about an attribute's update.
        * @protected
        */
            }, {
                key: 'scheduleBatchEvent_',
                value: function scheduleBatchEvent_(attrChangeData) {
                    if (!this.scheduledBatchData_) {
                        _async['default'].nextTick(this.emitBatchEvent_, this);
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
                }

                /**
        * Sets the value of the requested attribute.
        * Note: this can and should be accomplished by setting the attribute as a regular property.
        * This should only be used in cases where a function is actually needed.
        * @param {string} name
        * @param {*} value
        * @return {*}
        */
            }, {
                key: 'set',
                value: function set(name, value) {
                    this[name] = value;
                }

                /**
        * Sets the value of all the specified attributes.
        * @param {!Object.<string,*>} values A map of attribute names to the values they
        *   should be set to.
        */
            }, {
                key: 'setAttrs',
                value: function setAttrs(values) {
                    var names = Object.keys(values);

                    for (var i = 0; i < names.length; i++) {
                        this[names[i]] = values[names[i]];
                    }
                }

                /**
        * Sets the value of the specified attribute. This is passed as that attribute's
        * setter to the `Object.defineProperty` call inside the `addAttr` method.
        * @param {string} name The name of the attribute.
        * @param {*} value The new value of the attribute.
        * @protected
        */
            }, {
                key: 'setAttrValue_',
                value: function setAttrValue_(name, value) {
                    if (!this.canSetAttribute(name) || !this.validateAttrValue_(name, value)) {
                        return;
                    }

                    var info = this.attrsInfo_[name];
                    if (info.initialValue === undefined && info.state === Attribute.States.UNINITIALIZED) {
                        info.state = Attribute.States.INITIALIZED;
                    }

                    var prevVal = this[name];
                    info.value = this.callSetter_(name, value);
                    info.written = true;
                    this.informChange_(name, prevVal);
                }

                /**
        * Sets the default value of the requested attribute.
        * @param {string} name The name of the attribute.
        * @return {*}
        */
            }, {
                key: 'setDefaultValue_',
                value: function setDefaultValue_(name) {
                    var config = this.attrsInfo_[name].config;

                    if (config.value !== undefined) {
                        this[name] = config.value;
                    } else {
                        this[name] = this.callFunction_(config.valueFn);
                    }
                }

                /**
        * Sets the initial value of the requested attribute.
        * @param {string} name The name of the attribute.
        * @return {*}
        */
            }, {
                key: 'setInitialValue_',
                value: function setInitialValue_(name) {
                    var info = this.attrsInfo_[name];
                    if (info.initialValue !== undefined) {
                        this[name] = info.initialValue;
                        info.initialValue = undefined;
                    }
                }

                /**
        * Checks if we should inform about an attributes update. Updates are ignored
        * during attribute initialization. Otherwise, updates to primitive values
        * are only informed when the new value is different from the previous
        * one. Updates to objects (which includes functions and arrays) are always
        * informed outside initialization though, since we can't be sure if all of
        * the internal data has stayed the same.
        * @param {string} name The name of the attribute.
        * @param {*} prevVal The previous value of the attribute.
        * @return {boolean}
        */
            }, {
                key: 'shouldInformChange_',
                value: function shouldInformChange_(name, prevVal) {
                    var info = this.attrsInfo_[name];
                    return info.state === Attribute.States.INITIALIZED && (_core['default'].isObject(prevVal) || prevVal !== this[name]);
                }

                /**
        * Validates the attribute's value, which includes calling the validator defined
        * in the attribute's configuration object, if there is one.
        * @param {string} name The name of the attribute.
        * @param {*} value The value to be validated.
        * @return {boolean} Flag indicating if value is valid or not.
        */
            }, {
                key: 'validateAttrValue_',
                value: function validateAttrValue_(name, value) {
                    var info = this.attrsInfo_[name];

                    return info.state === Attribute.States.INITIALIZING_DEFAULT || this.callValidator_(name, value);
                }
            }], [{
                key: 'mergeAttrs_',
                value: function mergeAttrs_(values) {
                    return _object['default'].mixin.apply(null, [{}].concat(values.reverse()));
                }

                /**
        * Merges the ATTRS static variable for the given constructor function.
        * @param  {!Function} ctor Constructor function.
        * @return {boolean} Returns true if merge happens, false otherwise.
        * @static
        */
            }, {
                key: 'mergeAttrsStatic',
                value: function mergeAttrsStatic(ctor) {
                    return _core['default'].mergeSuperClassesProperty(ctor, 'ATTRS', Attribute.mergeAttrs_);
                }
            }]);

            return Attribute;
        })(_EventEmitter2['default']);

        Attribute.INVALID_ATTRS = ['attrs'];

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

        module.exports = Attribute;
    }
);