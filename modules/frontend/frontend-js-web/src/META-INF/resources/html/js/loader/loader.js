/*!
 * @overview es6-promise - a tiny implementation of Promises/A+.
 * @copyright Copyright (c) 2014 Yehuda Katz, Tom Dale, Stefan Penner and contributors (Conversion to ES6 API by Jake Archibald)
 * @license   Licensed under MIT license
 *            See https://raw.githubusercontent.com/jakearchibald/es6-promise/master/LICENSE
 * @version   2.0.0
 */

(function () {
    function r(a, b) {
        n[l] = a;
        n[l + 1] = b;
        l += 2;
        2 === l && A()
    }
    function s(a) {
        return "function" === typeof a
    }
    function F() {
        return function () {
            process.nextTick(t)
        }
    }
    function G() {
        var a = 0,
            b = new B(t),
            c = document.createTextNode("");
        b.observe(c, {
            characterData: !0
        });
        return function () {
            c.data = a = ++a % 2
        }
    }
    function H() {
        var a = new MessageChannel;
        a.port1.onmessage = t;
        return function () {
            a.port2.postMessage(0)
        }
    }
    function I() {
        return function () {
            setTimeout(t, 1)
        }
    }
    function t() {
        for (var a = 0; a < l; a += 2)(0, n[a])(n[a + 1]), n[a] = void 0, n[a + 1] = void 0;
        l = 0
    }
    function p() {}
    function J(a, b, c, d) {
        try {
            a.call(b, c, d)
        } catch (e) {
            return e
        }
    }
    function K(a, b, c) {
        r(function (a) {
            var e = !1,
                f = J(c, b, function (c) {
                    e || (e = !0, b !== c ? q(a, c) : m(a, c))
                }, function (b) {
                    e || (e = !0, g(a, b))
                });
            !e && f && (e = !0, g(a, f))
        }, a)
    }
    function L(a, b) {
        1 === b.a ? m(a, b.b) : 2 === a.a ? g(a, b.b) : u(b, void 0, function (b) {
            q(a, b)
        }, function (b) {
            g(a, b)
        })
    }
    function q(a, b) {
        if (a === b) g(a, new TypeError("You cannot resolve a promise with itself"));
        else if ("function" === typeof b || "object" === typeof b && null !== b) if (b.constructor === a.constructor) L(a, b);
        else {
            var c;
            try {
                c = b.then
            } catch (d) {
                v.error = d, c = v
            }
            c === v ? g(a, v.error) : void 0 === c ? m(a, b) : s(c) ? K(a, b, c) : m(a, b)
        } else m(a, b)
    }
    function M(a) {
        a.f && a.f(a.b);
        x(a)
    }
    function m(a, b) {
        void 0 === a.a && (a.b = b, a.a = 1, 0 !== a.e.length && r(x, a))
    }
    function g(a, b) {
        void 0 === a.a && (a.a = 2, a.b = b, r(M, a))
    }
    function u(a, b, c, d) {
        var e = a.e,
            f = e.length;
        a.f = null;
        e[f] = b;
        e[f + 1] = c;
        e[f + 2] = d;
        0 === f && a.a && r(x, a)
    }
    function x(a) {
        var b = a.e,
            c = a.a;
        if (0 !== b.length) {
            for (var d, e, f = a.b, g = 0; g < b.length; g += 3) d = b[g], e = b[g + c], d ? C(c, d, e, f) : e(f);
            a.e.length = 0
        }
    }
    function D() {
        this.error =
        null
    }
    function C(a, b, c, d) {
        var e = s(c),
            f, k, h, l;
        if (e) {
            try {
                f = c(d)
            } catch (n) {
                y.error = n, f = y
            }
            f === y ? (l = !0, k = f.error, f = null) : h = !0;
            if (b === f) {
                g(b, new TypeError("A promises callback cannot return that same promise."));
                return
            }
        } else f = d, h = !0;
        void 0 === b.a && (e && h ? q(b, f) : l ? g(b, k) : 1 === a ? m(b, f) : 2 === a && g(b, f))
    }
    function N(a, b) {
        try {
            b(function (b) {
                q(a, b)
            }, function (b) {
                g(a, b)
            })
        } catch (c) {
            g(a, c)
        }
    }
    function k(a, b, c, d) {
        this.n = a;
        this.c = new a(p, d);
        this.i = c;
        this.o(b) ? (this.m = b, this.d = this.length = b.length, this.l(), 0 === this.length ? m(this.c, this.b) : (this.length = this.length || 0, this.k(), 0 === this.d && m(this.c, this.b))) : g(this.c, this.p())
    }
    function h(a) {
        O++;
        this.b = this.a = void 0;
        this.e = [];
        if (p !== a) {
            if (!s(a)) throw new TypeError("You must pass a resolver function as the first argument to the promise constructor");
            if (!(this instanceof h)) throw new TypeError("Failed to construct 'Promise': Please use the 'new' operator, this object constructor cannot be called as a function.");
            N(this, a)
        }
    }
    var E = Array.isArray ? Array.isArray : function (a) {
        return "[object Array]" === Object.prototype.toString.call(a)
    },
        l = 0,
        w = "undefined" !== typeof window ? window : {},
        B = w.MutationObserver || w.WebKitMutationObserver,
        w = "undefined" !== typeof Uint8ClampedArray && "undefined" !== typeof importScripts && "undefined" !== typeof MessageChannel,
        n = Array(1E3),
        A;
    A = "undefined" !== typeof process && "[object process]" === {}.toString.call(process) ? F() : B ? G() : w ? H() : I();
    var v = new D,
        y = new D;
    k.prototype.o = function (a) {
        return E(a)
    };
    k.prototype.p = function () {
        return Error("Array Methods must be provided an Array")
    };
    k.prototype.l =

    function () {
        this.b = Array(this.length)
    };
    k.prototype.k = function () {
        for (var a = this.length, b = this.c, c = this.m, d = 0; void 0 === b.a && d < a; d++) this.j(c[d], d)
    };
    k.prototype.j = function (a, b) {
        var c = this.n;
        "object" === typeof a && null !== a ? a.constructor === c && void 0 !== a.a ? (a.f = null, this.g(a.a, b, a.b)) : this.q(c.resolve(a), b) : (this.d--, this.b[b] = this.h(a))
    };
    k.prototype.g = function (a, b, c) {
        var d = this.c;
        void 0 === d.a && (this.d--, this.i && 2 === a ? g(d, c) : this.b[b] = this.h(c));
        0 === this.d && m(d, this.b)
    };
    k.prototype.h = function (a) {
        return a
    };
    k.prototype.q = function (a, b) {
        var c = this;
        u(a, void 0, function (a) {
            c.g(1, b, a)
        }, function (a) {
            c.g(2, b, a)
        })
    };
    var O = 0;
    h.all = function (a, b) {
        return (new k(this, a, !0, b)).c
    };
    h.race = function (a, b) {
        function c(a) {
            q(e, a)
        }
        function d(a) {
            g(e, a)
        }
        var e = new this(p, b);
        if (!E(a)) return (g(e, new TypeError("You must pass an array to race.")), e);
        for (var f = a.length, h = 0; void 0 === e.a && h < f; h++) u(this.resolve(a[h]), void 0, c, d);
        return e
    };
    h.resolve = function (a, b) {
        if (a && "object" === typeof a && a.constructor === this) return a;
        var c = new this(p, b);
        q(c, a);
        return c
    };
    h.reject = function (a, b) {
        var c = new this(p, b);
        g(c, a);
        return c
    };
    h.prototype = {
        constructor: h,
        then: function (a, b) {
            var c = this.a;
            if (1 === c && !a || 2 === c && !b) return this;
            var d = new this.constructor(p),
                e = this.b;
            if (c) {
                var f = arguments[c - 1];
                r(function () {
                    C(c, d, f, e)
                })
            } else u(this, d, a, b);
            return d
        },
        "catch": function (a) {
            return this.then(null, a)
        }
    };
    var z = {
        Promise: h,
        polyfill: function () {
            var a;
            a = "undefined" !== typeof global ? global : "undefined" !== typeof window && window.document ? window : self;
            "Promise" in a && "resolve" in a.Promise && "reject" in a.Promise && "all" in a.Promise && "race" in a.Promise &&
            function () {
                var b;
                new a.Promise(function (a) {
                    b = a
                });
                return s(b)
            }() || (a.Promise = h)
        }
    };
    "function" === typeof define && define.amd ? define(function () {
        return z
    }) : "undefined" !== typeof module && module.exports ? module.exports = z : "undefined" !== typeof this && (this.ES6Promise = z)
}).call(this);

(function() {
	var global = {};

	global.__CONFIG__ = window.__CONFIG__;

	(function (global, factory) {
    'use strict';

    var built = factory(global);

    /* istanbul ignore else */
    if (typeof module === 'object' && module) {
        module.exports = built;
    }

    /* istanbul ignore next */
    if (typeof define === 'function' && define.amd) {
        define(factory);
    }

    global.EventEmitter = built;
}(typeof global !== 'undefined' ? global : /* istanbul ignore next */ this, function (global) {

    'use strict';

/**
 * Creates an instance of EventEmitter class.
 *
 * @constructor
 */

function EventEmitter() {
    this._events = {};
}

EventEmitter.prototype = {
    constructor: EventEmitter,

    /**
     * Adds event listener to an event.
     *
     * @param {string} event The name of the event.
     * @param {Function} callback Callback method to be invoked when event is being emitted.
     */
    on: function (event, callback) {
        var listeners = this._events[event] = this._events[event] || [];

        listeners.push(callback);
    },

    /**
     * Removes an event from the list of event listeners to some event.
     *
     * @param {string} event The name of the event.
     * @param {function} callback Callback method to be removed from the list of listeners.
     */
    off: function (event, callback) {
        var listeners = this._events[event];

        if (listeners) {
            var callbackIndex = listeners.indexOf(callback);

            if (callbackIndex > -1) {
                listeners.splice(callbackIndex, 1);
            } else {
                console.warn('Off: callback was not removed: ' + callback.toString());
            }
        } else {
            console.warn('Off: there are no listeners for event: ' + event);
        }
    },

    /**
     * Emits an event. The function calls all registered listeners in the order they have been added. The provided args
     * param will be passed to each listener of the event.
     *
     * @param {string} event The name of the event.
     * @param {object} args Object, which will be passed to the listener as only argument.
     */
    emit: function (event, args) {
        var listeners = this._events[event];

        if (listeners) {
            // Slicing is needed to prevent the following situation:
            // A listener is being invoked. During its execution, it may
            // remove itself from the list. In this case, for loop will
            // be damaged, since i will be out of sync.
            listeners = listeners.slice(0);

            for (var i = 0; i < listeners.length; i++) {
                var listener = listeners[i];

                listener.call(listener, args);
            }
        } else {
            console.warn('No listeners for event: ' + event);
        }
    }
};

    return EventEmitter;
}));
(function (global, factory) {
    'use strict';

    var built = factory(global);

    /* istanbul ignore else */
    if (typeof module === 'object' && module) {
        module.exports = built;
    }

    /* istanbul ignore next */
    if (typeof define === 'function' && define.amd) {
        define(factory);
    }

    global.ConfigParser = built;
}(typeof global !== 'undefined' ? global : /* istanbul ignore next */ this, function (global) {

    'use strict';

var hasOwnProperty = Object.prototype.hasOwnProperty;

/**
 * Creates an instance of ConfigurationParser class.
 *
 * @constructor
 * @param {object=} - The configuration object to be parsed.
 */

function ConfigParser(config) {
    this._config = {};
    this._modules = {};
    this._conditionalModules = {};

    this._parseConfig(config);
}

ConfigParser.prototype = {
    constructor: ConfigParser,

    /**
     * Adds a module to the configuration.
     *
     * @param {object} module The module which should be added to the configuration. Should have the following
     *     properties:
     *     <ul>
     *         <strong>Obligatory properties</strong>:
     *         <li>name (String) The name of the module</li>
     *         <li>dependencies (Array) The modules from which it depends</li>
     *     </ul>
     *
     *     <strong>Optional parameters:</strong>
     *     The same as those which config parameter of {@link Loader#define} method accepts.
     */
    addModule: function (module) {
        // Module might be added via configuration or when it arrives from the server.
        // If it arrives from the server, it will have already a definition. In this case,
        // we will overwrite the existing properties with those, provided from the module definition.
        // Otherwise, we will just add it to the map.
        var moduleDefinition = this._modules[module.name];

        if (moduleDefinition) {
            for (var key in module) {
                if (hasOwnProperty.call(module, key)) {
                    moduleDefinition[key] = module[key];
                }
            }
        } else {
            this._modules[module.name] = module;
        }

        this._registerConditionalModule(module);
    },

    /**
     * Returns the current configuration.
     *
     * @return {object} The current configuration.
     */
    getConfig: function () {
        return this._config;
    },

    /**
     * Returns map with all currently registered conditional modules and their triggers.
     *
     * @return {object} Map with all currently registered conditional modules.
     */
    getConditionalModules: function () {
        return this._conditionalModules;
    },

    /**
     * Returns map with all currently registered modules.
     *
     * @return {object} Map with all currently registered modules.
     */
    getModules: function () {
        return this._modules;
    },

    /**
     * Maps module names to their aliases. Example:
     * __CONFIG__.maps = {
     *      liferay: 'liferay@1.0.0'
     * }
     *
     * When someone does require('liferay/html/js/ac.es',...),
     * if the module 'liferay/html/js/ac.es' is not defined,
     * then a corresponding alias will be searched. If found, the name will be replaced,
     * so it will look like user did require('liferay@1.0.0/html/js/ac.es',...).
     *
     * @protected
     * @param {array|string} module The module which have to be mapped or array of modules.
     * @return {array|string} The mapped module or array of mapped modules.
     */
    mapModule: function(module) {
        var modules;

        if (Array.isArray(module)) {
            modules = module;
        } else {
            modules = [module];
        }

        for (var i = 0; i < modules.length; i++) {
            var tmpModule = modules[i];

            for (var alias in this._config.maps) {
                /* istanbul ignore else */
                if (hasOwnProperty.call(this._config.maps, alias)) {
                    if (tmpModule === alias || tmpModule.indexOf(alias + '/') === 0) {
                        tmpModule = this._config.maps[alias] + tmpModule.substring(alias.length);
                        modules[i] = tmpModule;

                        break;
                    }
                }
            }
        }

        return Array.isArray(module) ? modules : modules[0];
    },

    /**
     * Parses configuration object.
     *
     * @protected
     * @param {object} config Configuration object to be parsed.
     * @return {object} The created configuration
     */
    _parseConfig: function (config) {
        for (var key in config) { /* istanbul ignore else */
            if (hasOwnProperty.call(config, key)) {
                if (key === 'modules') {
                    this._parseModules(config[key]);
                } else {
                    this._config[key] = config[key];
                }
            }
        }

        return this._config;
    },

    /**
     * Parses a provided modules configuration.
     *
     * @protected
     * @param {object} modules Map of modules to be parsed.
     * @return {object} Map of parsed modules.
     */
    _parseModules: function (modules) {
        for (var key in modules) { /* istanbul ignore else */
            if (hasOwnProperty.call(modules, key)) {
                var module = modules[key];

                module.name = key;

                this.addModule(module);
            }
        }

        return this._modules;
    },

    /**
     * Registers conditional module to the configuration.
     *
     * @protected
     * @param {object} module Module object
     */
    _registerConditionalModule: function (module) {
        // Create HashMap of all modules, which have conditional modules, as an Array.
        if (module.condition) {
            var existingModules = this._conditionalModules[module.condition.trigger];

            if (!existingModules) {
                this._conditionalModules[module.condition.trigger] = existingModules = [];
            }

            existingModules.push(module.name);
        }
    }
};

    return ConfigParser;
}));
(function (global, factory) {
    'use strict';

    var built = factory(global);

    /* istanbul ignore else */
    if (typeof module === 'object' && module) {
        module.exports = built;
    }

    /* istanbul ignore next */
    if (typeof define === 'function' && define.amd) {
        define(factory);
    }

    global.DependencyBuilder = built;
}(typeof global !== 'undefined' ? global : /* istanbul ignore next */ this, function (global) {

    'use strict';

var hasOwnProperty = Object.prototype.hasOwnProperty;

/**
 * Creates an instance of DependencyBuilder class.
 *
 * @constructor
 * @param {object} - instance of {@link ConfigParser} object.
 */

function DependencyBuilder(configParser) {
    this._configParser = configParser;

    this._result = [];
}

DependencyBuilder.prototype = {
    constructor: DependencyBuilder,

    /**
     * Resolves modules dependencies.
     *
     * @param {array} modules List of modules which dependencies should be resolved.
     * @return {array} List of module names, representing module dependencies. Module name itself is being returned too.
     */
    resolveDependencies: function (modules) {
        // Copy the passed modules to a resolving modules queue.
        // Modules may be added there during the process of resolving.
        this._queue = modules.slice(0);

        var result;

        try {
            this._resolveDependencies();

            // Reorder the modules list so the modules without dependencies will
            // be moved upfront
            result = this._result.reverse().slice(0);
        }
        finally {
            this._cleanup();
        }

        return result;
    },

    /**
     * Clears the used resources during the process of resolving dependencies.
     *
     * @protected
     */
    _cleanup: function () {
        var modules = this._configParser.getModules();

        // Set to false all temporary markers which were set during the process of
        // dependencies resolving.
        for (var key in modules) { /* istanbul ignore else */
            if (hasOwnProperty.call(modules, key)) {
                var module = modules[key];

                module.conditionalMark = false;
                module.mark = false;
                module.tmpMark = false;
            }
        }

        this._queue.length = 0;
        this._result.length = 0;
    },

    /**
     * Processes conditional modules. If a module has conditional module as dependency, this module will be added to
     * the list of modules, which dependencies should be resolved.
     *
     * @protected
     * @param {object} module Module, which will be checked for conditional modules as dependencies.
     */
    _processConditionalModules: function (module) {
        var conditionalModules = this._configParser.getConditionalModules()[module.name];

        // If the current module has conditional modules as dependencies,
        // add them to the list (queue) of modules, which have to be resolved.
        if (conditionalModules && !module.conditionalMark) {
            var modules = this._configParser.getModules();

            for (var i = 0; i < conditionalModules.length; i++) {
                var conditionalModule = modules[conditionalModules[i]];

                if (this._queue.indexOf(conditionalModule.name) === -1 && this._testConditionalModule(conditionalModule.condition.test)) {

                    this._queue.push(conditionalModule.name);
                }
            }

            module.conditionalMark = true;
        }
    },

    /**
     * Processes all modules in the {@link DependencyBuilder#_queue} and resolves their dependencies. The function
     * implements standard
     * [topological sorting based on depth-first search]{@link http://en.wikipedia.org/wiki/Topological_sorting}.
     *
     * @protected
     */
    _resolveDependencies: function () {
        // Process all modules in the queue.
        // Note: modules may be added to the queue during the process of evaluating.
        var modules = this._configParser.getModules();

        for (var i = 0; i < this._queue.length; i++) {
            var module = modules[this._queue[i]];

            if (!module.mark) {
                this._visit(module);
            }
        }
    },

    /**
     * Executes the test function of an conditional module and adds it to the list of module dependencies if the
     * function returns true.
     *
     * @param {function|string} testFunction The function which have to be executed. May be Function object or string.
     * @return {boolean} The result of the execution of the test function.
     */
    _testConditionalModule: function (testFunction) {
        if (typeof testFunction === 'function') {
            return testFunction();
        } else {
            return eval('false || ' + testFunction)();
        }
    },

    /**
     * Visits a module during the process of resolving dependencies. The function will throw exception in case of
     * circular dependencies among modules.
     *
     * @protected
     * @param {object} module The module which have to be visited.
     */
    _visit: function (module) {
        // Directed Acyclic Graph is supported only, throw exception if there are circular dependencies.
        if (module.tmpMark) {
            throw new Error('Error processing module: ' + module.name + '. ' + 'The provided configuration is not Directed Acyclic Graph.');
        }

        // Check if this module has conditional modules and add them to the queue if so.
        this._processConditionalModules(module);

        if (!module.mark) {
            module.tmpMark = true;

            var modules = this._configParser.getModules();

            for (var i = 0; i < module.dependencies.length; i++) {
                var dependencyName = module.dependencies[i];

                if (dependencyName === 'exports' || dependencyName === 'module') {
                    continue;
                }

                // Map the modules to their aliases
                dependencyName = this._configParser.mapModule(dependencyName);

                var moduleDependency = modules[dependencyName];

                if (!moduleDependency) {
                    throw new Error('Cannot resolve module: ' + module.name + ' ' + 'due to not yet registered or wrongly specified dependency: ' + dependencyName);
                }

                this._visit(moduleDependency, modules);
            }

            module.mark = true;

            module.tmpMark = false;

            this._result.unshift(module.name);
        }
    },

    /**
     * @property {array} _queue List of modules, which dependencies should be resolved. Initially, it is copy of
     * the array of modules, passed for resolving; during the process more modules may be added to the queue. For
     * example, these might be conditional modules.
     *
     * @protected
     * @memberof! DependencyBuilder#
     * @default []
     */
    _queue: []
};

    return DependencyBuilder;
}));
(function (global, factory) {
    'use strict';

    var built = factory(global);

    /* istanbul ignore else */
    if (typeof module === 'object' && module) {
        module.exports = built;
    }

    /* istanbul ignore next */
    if (typeof define === 'function' && define.amd) {
        define(factory);
    }

    global.URLBuilder = built;
}(typeof global !== 'undefined' ? global : /* istanbul ignore next */ this, function (global) {

    'use strict';

// External protocols regex, supports:
// "http", "https", "//" and "www."
var REGEX_EXTERNAL_PROTOCOLS = /^https?:\/\/|\/\/|www\./;

/**
 * Creates an instance of URLBuilder class.
 *
 * @constructor
 * @param {object} - instance of {@link ConfigParser} object.
 */

function URLBuilder(configParser) {
    this._configParser = configParser;
}

URLBuilder.prototype = {
    constructor: URLBuilder,

    /**
     * Returns a list of URLs from provided list of modules.
     *
     * @param {array} modules List of modules for which URLs should be created.
     * @return {array} List of URLs.
     */
    build: function (modules) {
        var buffer = [];
        var result = [];

        var registeredModules = this._configParser.getModules();
        var config = this._configParser.getConfig();

        var basePath = config.basePath;

        /* istanbul ignore else */
        if (basePath.charAt(basePath.length - 1) !== '/') {
            basePath += '/';
        }

        for (var i = 0; i < modules.length; i++) {
            var module = registeredModules[modules[i]];

            // If module has fullPath, individual URL have to be created.
            if (module.fullPath) {
                result.push(module.fullPath);

            } else {
                var path = this._getModulePath(module);

                // If the URL starts with external protocol, individual URL shall be created.
                if (REGEX_EXTERNAL_PROTOCOLS.test(path)) {
                    result.push(path);

                    // If combine is disabled, create individual URL based on config URL and module path.
                } else if (!config.combine) {
                    result.push(config.url + basePath + path);

                } else {
                    // If combine is true and module does not have full path, it will be collected
                    // in a buffer to be loaded among with other modules from combo loader.
                    buffer.push(path);
                }
            }

            module.requested = true;
        }

        // Add to the result all modules, which have to be combined.
        if (buffer.length) {
            result.push(config.url + basePath + buffer.join('&' + basePath));

            buffer.length = 0;
        }

        return result;
    },

    /**
     * Returns the path for a module. If module has property path, it will be returned directly. Otherwise,
     * the name of module will be used and extension .js will be added to module name if omitted.
     *
     * @protected
     * @param {object} module The module which path should be returned.
     * @return {string} Module path.
     */
    _getModulePath: function (module) {
        var path = module.path || module.name;

        var paths = this._configParser.getConfig().paths;

        for (var key in paths) {
            /* istanbul ignore else */
            if (Object.prototype.hasOwnProperty.call(paths, key)) {
                if (path === key || path.indexOf(key + '/') === 0) {
                    path = paths[key] + path.substring(key.length);
                }
            }
        }

        if (!REGEX_EXTERNAL_PROTOCOLS.test(path) && path.indexOf('.js') !== path.length - 3) {
            path += '.js';
        }

        return path;
    }
};

    return URLBuilder;
}));
(function (global, factory) {
    'use strict';

    var built = factory(global);

    /* istanbul ignore else */
    if (typeof module === 'object' && module) {
        module.exports = built;
    }

    /* istanbul ignore next */
    if (typeof define === 'function' && define.amd) {
        define(factory);
    }

    /* jshint newcap:false */
    global.Loader = new built();
    global.require = global.Loader.require.bind(global.Loader);
    global.define = global.Loader.define.bind(global.Loader);
}(typeof global !== 'undefined' ? global : /* istanbul ignore next */ this, function (global) {

    'use strict';

/**
 * Creates an instance of Loader class.
 *
 * @namespace Loader
 * @extends EventEmitter
 * @constructor
 */

function Loader(config) {
    Loader.superclass.constructor.apply(this, arguments);

    this._config = config || global.__CONFIG__;

    this._modulesMap = {};
}

extend(Loader, global.EventEmitter, {
    /**
     * Defines a module in the system and fires {@link Loader#event:moduleRegister} event with the registered module as param.
     *
     * @memberof! Loader#
     * @param {string} name The name of the module.
     * @param {array} dependencies List of module dependencies.
     * @param {function} implementation The implementation of the module.
     * @param {object=} config Object configuration:
     * <ul>
     *         <strong>Optional properties</strong>:
     *         <li>path (String) - Explicitly set path of the module. If omitted, module name will be used as path</li>
     *         <li>condition (Object) Object which represents if the module should be added automatically after another
     *             module.
     *         It should have the following properties:</li>
     *             <ul>
     *                 <li>trigger - the module, which should trigger the loading of the current module</li>
     *                 <li>test - function, which should return true if module should be loaded</li>
     *         </ul>
     *     </ul>
     * @return {Object} The constructed module.
     */
    define: function(name, dependencies, implementation, config) {
        // Create new module by merging the provided config with the passed name,
        // dependencies and the implementation.
        var module = config || {};

        name = this._getConfigParser().mapModule(name);

        module.name = name;
        module.dependencies = dependencies;
        module.pendingImplementation = implementation;

        var configParser = this._getConfigParser();

        configParser.addModule(module);

        if (!this._modulesMap[module.name]) {
            this._modulesMap[module.name] = true;
        }

        this.emit('moduleRegister', module);
    },

    /**
     * Returns list of currently registered conditional modules.
     *
     * @memberof! Loader#
     * @return {array} List of currently registered conditional modules.
     */
    getConditionalModules: function() {
        return this._getConfigParser().getConditionalModules();
    },

    /**
     * Returns list of currently registered modules.
     *
     * @memberof! Loader#
     * @return {array} List of currently registered modules.
     */
    getModules: function() {
        return this._getConfigParser().getModules();
    },

    /**
     * Requires list of modules. If a module is not yet registered, it will be ignored and its implementation
     * in the provided success callback will be left undefined.<br>
     *
     * @memberof! Loader#
     * @param {array|string[]} modules Modules can be specified as an array of strings or provided as
     *     multiple string parameters.
     * @param {function} success Callback, which will be invoked in case of success. The provided parameters will
     *     be implementations of all required modules.
     * @param {function} failure Callback, which will be invoked in case of failure. One parameter with
     *     information about the error will be provided.
     */
    require: function() {
        var self = this;

        var failureCallback;
        var modules;
        var successCallback;

        // Modules can be specified by as an array, or just as parameters to the function
        // We do not slice or leak arguments to not cause V8 performance penalties
        // TODO: This could be extracted as an inline function (hint)
        var isArgsArray = Array.isArray ? Array.isArray(arguments[0]) : /* istanbul ignore next */
            Object.prototype.toString.call(arguments[0]) === '[object Array]';

        if (isArgsArray) {
            modules = arguments[0];
            successCallback = typeof arguments[1] === 'function' ? arguments[1] : null;
            failureCallback = typeof arguments[2] === 'function' ? arguments[2] : null;

        } else {
            modules = [];

            for (var i = 0; i < arguments.length; ++i) {
                if (typeof arguments[i] === 'string') {
                    modules[i] = arguments[i];

                    /* istanbul ignore else */
                } else if (typeof arguments[i] === 'function') {
                    successCallback = arguments[i];
                    failureCallback = typeof arguments[++i] === 'function' ? arguments[i] : /* istanbul ignore next */ null;

                    break;
                }
            }
        }

        modules = this._getConfigParser().mapModule(modules);

        // Resolve the dependencies of the specified modules by the user
        // then load their JS scripts
        self._resolveDependencies(modules).then(function(dependencies) {
            return self._loadModules(dependencies);
        }).then(function(loadedModules) {
            var moduleImplementations = self._getModuleImplementations(modules);

            /* istanbul ignore else */
            if (successCallback) {
                successCallback.apply(successCallback, moduleImplementations);
            }
        }, function(error) { /* istanbul ignore else */
            if (failureCallback) {
                failureCallback.call(failureCallback, error);

            }
        });
    },

    /**
     * Creates Promise for module. It will be resolved as soon as module is being loaded from server.
     *
     * @memberof! Loader#
     * @protected
     * @param {string} moduleName The name of module for which Promise should be created.
     * @return {Promise} Promise, which will be resolved as soon as the requested module is being loaded.
     */
    _createModulePromise: function(moduleName) {
        var self = this;

        return new Promise(function(resolve, reject) {
            var onModuleRegister = function(registeredModule) {
                if (registeredModule.name === moduleName) {
                    self.off('moduleRegister', onModuleRegister);

                    // Overwrite the promise entry in modules map with simple true value.
                    // Hopefully GC will remove this promise from the memory.
                    self._modulesMap[moduleName] = true;

                    resolve(moduleName);
                }
            };

            self.on('moduleRegister', onModuleRegister);
        });
    },

    /**
     * Returns instance of {@link ConfigParser} class currently used.
     *
     * @memberof! Loader#
     * @protected
     * @return {ConfigParser} Instance of {@link ConfigParser} class.
     */
    _getConfigParser: function() { /* istanbul ignore else */
        if (!this._configParser) {
            this._configParser = new global.ConfigParser(this._config);
        }

        return this._configParser;
    },

    /**
     * Returns instance of {@link DependencyBuilder} class currently used.
     *
     * @memberof! Loader#
     * @protected
     * @return {DependencyBuilder} Instance of {@link DependencyBuilder} class.
     */
    _getDependencyBuilder: function() {
        if (!this._dependencyBuilder) {
            this._dependencyBuilder = new global.DependencyBuilder(this._getConfigParser());
        }

        return this._dependencyBuilder;
    },

    /**
     * Retrieves module implementations to an array.
     *
     * @memberof! Loader#
     * @protected
     * @param {array} requiredModules Lit of modules, which implementations will be added to an array.
     * @return {array} List of modules implementations.
     */
    _getModuleImplementations: function(requiredModules) {
        var moduleImplementations = [];

        var modules = this._getConfigParser().getModules();

        for (var i = 0; i < requiredModules.length; i++) {
            var requiredModule = modules[requiredModules[i]];

            moduleImplementations.push(requiredModule ? requiredModule.implementation : undefined);
        }

        return moduleImplementations;
    },

    /**
     * Returns instance of {@link URLBuilder} class currently used.
     *
     * @memberof! Loader#
     * @protected
     * @return {URLBuilder} Instance of {@link URLBuilder} class.
     */
    _getURLBuilder: function() { /* istanbul ignore else */
        if (!this._urlBuilder) {
            this._urlBuilder = new global.URLBuilder(this._getConfigParser());
        }

        return this._urlBuilder;
    },

    /**
     * Filters a list of modules and returns only these which have been not yet requested for delivery via network.
     *
     * @memberof! Loader#
     * @protected
     * @param {array} modules List of modules which which will be filtered.
     * @return {array} List of modules not yet requested for delivery via network.
     */
    _filterNotRequestedModules: function(modules) {
        var missingModules = [];

        var registeredModules = this._getConfigParser().getModules();

        for (var i = 0; i < modules.length; i++) {
            var registeredModule = registeredModules[modules[i]];

            // Get all modules which are not yet requested from the server.
            // We exclude "exports" and "module" modules, which are part of AMD spec.
            if ((registeredModule !== 'exports' && registeredModule !== 'module') && (!registeredModule || !registeredModule.requested)) {
                missingModules.push(modules[i]);
            }
        }

        return missingModules;
    },

    /**
     * Loads list of modules.
     *
     * @memberof! Loader#
     * @protected
     * @param {array} modules List of modules to be loaded.
     * @return {Promise} Promise, which will be resolved as soon as all module a being loaded.
     */
    _loadModules: function(moduleNames) {
        var self = this;

        return new Promise(function(resolve, reject) {
            // First, detect any still unloaded modules
            var notRequestedModules = self._filterNotRequestedModules(moduleNames);

            if (notRequestedModules.length) {
                // If there are unloaded modules, construct their URLs
                var urls = self._getURLBuilder().build(notRequestedModules);

                var pendingScripts = [];

                // Create promises for each of the scripts, which should be loaded
                for (var i = 0; i < urls.length; i++) {
                    pendingScripts.push(self._loadScript(urls[i]));
                }

                // Wait for resolving all script Promises
                // As soon as that happens, wait for each module to define itself
                Promise.all(pendingScripts).then(function(loadedScripts) {
                    return self._waitForModules(moduleNames);
                })
                // As soon as all scripts were loaded and all dependencies have been resolved,
                // resolve the main Promise
                .then(function(modules) {
                    resolve(modules);
                })
                // If any script fails to load or other error happens,
                // reject the main Promise
                .catch(function(error) {
                    reject(error);
                });
            } else {
                // If there are no any missing modules, just wait for modules dependencies
                // to be resolved and then resolve the main promise
                self._waitForModules(moduleNames).then(function(modules) {
                    resolve(modules);
                })
                // If some error happens, for example if some module implementation
                // throws error, reject the main Promise
                .
                catch(function(error) {
                    reject(error);
                });
            }
        });
    },

    /**
     * Loads a &ltscript&gt element on the page.
     *
     * @memberof! Loader#
     * @protected
     * @param {string} url The src of the script.
     * @return {Promise} Promise which will be resolved as soon as the script is being loaded.
     */
    _loadScript: function(url) {
        return new Promise(function(resolve, reject) {
            var script = document.createElement('script');

            script.src = url;

            // On ready state change is needed for IE < 9, not sure if that is needed anymore,
            // it depends which browsers will we support at the end
            script.onload = script.onreadystatechange = function() { /* istanbul ignore else */
                if (!this.readyState || /* istanbul ignore next */ this.readyState === 'complete' || /* istanbul ignore next */ this.readyState === 'load') {

                    script.onload = script.onreadystatechange = null;

                    resolve(script);
                }
            };

            // If some script fails to load, reject the main Promise
            script.onerror = function() {
                document.body.removeChild(script);

                reject(script);
            };

            document.body.appendChild(script);
        });
    },

    /**
     * Resolves modules dependencies.
     *
     * @memberof! Loader#
     * @protected
     * @param {array} modules List of modules which dependencies should be resolved.
     * @return {Promise} Promise which will be resolved as soon as all dependencies are being resolved.
     */
    _resolveDependencies: function(modules) {
        var self = this;

        return new Promise(function(resolve, reject) {
            try {
                var registeredModules = self._getConfigParser().getModules();
                var finalModules = [];

                // Ignore wrongly specified byt the user (misspelled) modules
                for (var i = 0; i < modules.length; i++) {
                    if (registeredModules[modules[i]]) {
                        finalModules.push(modules[i]);
                    }
                }

                var dependencies = self._getDependencyBuilder().resolveDependencies(finalModules);

                resolve(dependencies);
            } catch (error) {
                reject(error);
            }
        });
    },

    /**
     * Invokes the implementation method of list of modules passing the implementations of its dependencies.
     *
     * @memberof! Loader#
     * @protected
     * @param {array} modules List of modules to which implementation should be set.
     */
    _setModuleImplementation: function(modules) {
        var registeredModules = this._getConfigParser().getModules();

        for (var i = 0; i < modules.length; i++) {
            var module = modules[i];

            if (module.implementation) {
                continue;
            }

            var dependencyImplementations = [];

            // Leave exports implementation undefined by default
            var exportsImpl;

            for (var j = 0; j < module.dependencies.length; j++) {
                var dependency = module.dependencies[j];

                // If the current dependency of this module is 'exports',
                // create an empty object and pass it as implementation of
                // 'exports' module
                if (dependency === 'exports') {
                    exportsImpl = {};

                    dependencyImplementations.push(exportsImpl);
                } else if (dependency === 'module') {
                    exportsImpl = {exports: {}};

                    dependencyImplementations.push(exportsImpl);
                } else {
                    // otherwise set as value the implementation of the
                    // registered module

                    dependency = this._getConfigParser().mapModule(dependency);

                    var dependencyModule = registeredModules[dependency];

                    var impl = dependencyModule.implementation;

                    dependencyImplementations.push(impl);
                }
            }

            var result = module.pendingImplementation.apply(module.pendingImplementation, dependencyImplementations);

            // Store as implementation either the returned value from the function's invocation,
            // or one of these:
            // 1. If the passed object has 'exports' property (in case of 'module' dependency), get this one.
            // 2. Otherwise, get the passed object itself (in case of 'exports' dependency)
            //
            // The final implementation of this module may be undefined if there is no
            // returned value, or the object does not have 'exports' or 'module' dependency.
            if (result) {
                module.implementation = result;
            } else if (exportsImpl) {
                module.implementation = exportsImpl.exports || exportsImpl;
            }
        }
    },

    /**
     * Resolves a Promise as soon as all module dependencies are being resolved or it has implementation already.
     *
     * @memberof! Loader#
     * @protected
     * @param {object} module The module for which this function should wait.
     * @return {Promise}
     */
    _waitForModule: function(moduleName) {
        var self = this;

        // Check if there is already a promise for this module.
        // If there is not - create one and store it to module promises map.
        var modulePromise = self._modulesMap[moduleName];

        if (!modulePromise) {
            modulePromise = self._createModulePromise(moduleName);

            self._modulesMap[moduleName] = modulePromise;
        }

        return modulePromise;
    },

    /**
     * Resolves a Promise as soon as all dependencies of all provided modules are being resolved and modules have
     * implementations.
     *
     * @memberof! Loader#
     * @protected
     * @param {array} modules List of modules for which implementations this function should wait.
     * @return {Promise}
     */
    _waitForModules: function(moduleNames) {
        var self = this;

        return new Promise(function(resolve, reject) {
            var modulesPromises = [];

            for (var i = 0; i < moduleNames.length; i++) {
                modulesPromises.push(self._waitForModule(moduleNames[i]));
            }

            Promise.all(modulesPromises).then(function(uselessPromises) {
                var registeredModules = self._getConfigParser().getModules();

                var definedModules = [];

                for (var i = 0; i < moduleNames.length; i++) {
                    definedModules.push(registeredModules[moduleNames[i]]);
                }

                self._setModuleImplementation(definedModules);

                resolve(definedModules);
            });
        });
    }

    /**
     * Indicates that a module has been registered.
     *
     * @event Loader#moduleRegister
     * @param {object} module - The registered module.
     */
});

// Utilities methods


function extend(r, s, px) { /* istanbul ignore if else */
    if (!s || !r) {
        throw ('extend failed, verify dependencies');
    }

    var sp = s.prototype,
        rp = Object.create(sp);
    r.prototype = rp;

    rp.constructor = r;
    r.superclass = sp;

    /* istanbul ignore if else */
    // assign constructor property
    if (s != Object && sp.constructor == Object.prototype.constructor) {
        sp.constructor = s;
    }

    /* istanbul ignore else */
    // add prototype overrides
    if (px) {
        mix(rp, px);
    }

    return r;
}

function mix(destination, source) {
    var hasOwnProperty = Object.prototype.hasOwnProperty;

    for (var k in source) { /* istanbul ignore else */
        if (hasOwnProperty.call(source, k)) {
            destination[k] = source[k];
        }
    }

    return destination;
}

    return Loader;
}));

	window.Loader = global.Loader;
    window.require = global.require;
    window.define = global.define;
}());