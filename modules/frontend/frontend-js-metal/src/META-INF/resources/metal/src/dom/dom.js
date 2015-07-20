define(['exports', 'module', 'metal/src/core', 'metal/src/object/object', 'metal/src/events/DomEventHandle'], function (exports, module, _metalSrcCore, _metalSrcObjectObject, _metalSrcEventsDomEventHandle) {
	'use strict';

	var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

	var _core = _interopRequireDefault(_metalSrcCore);

	var _object = _interopRequireDefault(_metalSrcObjectObject);

	var _DomEventHandle = _interopRequireDefault(_metalSrcEventsDomEventHandle);

	var dom = (function () {
		function dom() {
			_classCallCheck(this, dom);
		}

		_createClass(dom, null, [{
			key: 'addClasses',

			/**
    * Adds the requested CSS classes to an element.
    * @param {!Element} element The element to add CSS classes to.
    * @param {string} classes CSS classes to add.
    */
			value: function addClasses(element, classes) {
				if (!_core['default'].isObject(element) || !_core['default'].isString(classes)) {
					return;
				}

				if ('classList' in element) {
					dom.addClassesWithNative_(element, classes);
				} else {
					dom.addClassesWithoutNative_(element, classes);
				}
			}
		}, {
			key: 'addClassesWithNative_',

			/**
    * Adds the requested CSS classes to an element using classList.
    * @param {!Element} element The element to add CSS classes to.
    * @param {string} classes CSS classes to add.
    * @protected
    */
			value: function addClassesWithNative_(element, classes) {
				classes.split(' ').forEach(function (className) {
					element.classList.add(className);
				});
			}
		}, {
			key: 'addClassesWithoutNative_',

			/**
    * Adds the requested CSS classes to an element without using classList.
    * @param {!Element} element The element to add CSS classes to.
    * @param {string} classes CSS classes to add.
    * @protected
    */
			value: function addClassesWithoutNative_(element, classes) {
				var elementClassName = ' ' + element.className + ' ';
				var classesToAppend = '';

				classes = classes.split(' ');

				for (var i = 0; i < classes.length; i++) {
					var className = classes[i];

					if (elementClassName.indexOf(' ' + className + ' ') === -1) {
						classesToAppend += ' ' + className;
					}
				}

				if (classesToAppend) {
					element.className = element.className + classesToAppend;
				}
			}
		}, {
			key: 'append',

			/**
    * Appends a child node with text or other nodes to a parent node. If
    * child is a HTML string it will be automatically converted to a document
    * fragment before appending it to the parent.
    * @param {!Element} parent The node to append nodes to.
    * @param {!(Element|NodeList|string)} child The thing to append to the parent.
    * @return {!Element} The appended child.
    */
			value: function append(parent, child) {
				if (_core['default'].isString(child)) {
					child = dom.buildFragment(child);
				}
				if (child instanceof NodeList) {
					var childArr = Array.prototype.slice.call(child);
					for (var i = 0; i < childArr.length; i++) {
						parent.appendChild(childArr[i]);
					}
				} else {
					parent.appendChild(child);
				}
				return child;
			}
		}, {
			key: 'buildFragment',

			/**
    * Helper for converting a HTML string into a document fragment.
    * @param {string} htmlString The HTML string to convert.
    * @return {!Element} The resulting document fragment.
    */
			value: function buildFragment(htmlString) {
				var tempDiv = document.createElement('div');
				tempDiv.innerHTML = '<br>' + htmlString;
				tempDiv.removeChild(tempDiv.firstChild);

				var fragment = document.createDocumentFragment();
				while (tempDiv.firstChild) {
					fragment.appendChild(tempDiv.firstChild);
				}
				return fragment;
			}
		}, {
			key: 'delegate',

			/**
    * Listens to the specified event on the given DOM element, but only calls the
    * callback with the event when it triggered by elements that match the given
    * selector.
    * @param {!Element} element The container DOM element to listen to the event on.
    * @param {string} eventName The name of the event to listen to.
    * @param {string} selector The selector that matches the child elements that
    *   the event should be triggered for.
    * @param {!function(!Object)} callback Function to be called when the event is
    *   triggered. It will receive the normalized event object.
    * @return {!DomEventHandle} Can be used to remove the listener.
    */
			value: function delegate(element, eventName, selector, callback) {
				var customConfig = dom.customEvents[eventName];
				if (customConfig && customConfig.delegate) {
					eventName = customConfig.originalEvent;
					callback = customConfig.handler.bind(customConfig, callback);
				}
				return dom.on(element, eventName, dom.handleDelegateEvent_.bind(null, selector, callback));
			}
		}, {
			key: 'enterDocument',

			/**
    * Inserts node in document as last element.
    * @param {Element} node Element to remove children from.
    */
			value: function enterDocument(node) {
				dom.append(document.body, node);
			}
		}, {
			key: 'exitDocument',

			/**
    * Removes node from document.
    * @param {Element} node Element to remove children from.
    */
			value: function exitDocument(node) {
				if (node.parentNode) {
					node.parentNode.removeChild(node);
				}
			}
		}, {
			key: 'handleDelegateEvent_',

			/**
    * This is called when an event is triggered by a delegate listener (see
    * `dom.delegate` for more details).
    * @param {string} selector The selector or element that matches the child
    *   elements that the event should be triggered for.
    * @param {!function(!Object)} callback Function to be called when the event
    *   is triggered. It will receive the normalized event object.
    * @param {!Event} event The event payload.
    * @return {boolean} False if at least one of the triggered callbacks returns
    *   false, or true otherwise.
    */
			value: function handleDelegateEvent_(selector, callback, event) {
				dom.normalizeDelegateEvent_(event);

				var currentElement = event.target;
				var returnValue = true;

				while (currentElement && !event.stopped) {
					if (_core['default'].isString(selector) && dom.match(currentElement, selector)) {
						event.delegateTarget = currentElement;
						returnValue &= callback(event);
					}
					currentElement = currentElement.parentNode;
				}

				return returnValue;
			}
		}, {
			key: 'hasClass',

			/**
    * Checks if the given element has the requested css class.
    * @param {!Element} element
    * @param {string} className
    * @return {boolean}
    */
			value: function hasClass(element, className) {
				if ('classList' in element) {
					return dom.hasClassWithNative_(element, className);
				} else {
					return dom.hasClassWithoutNative_(element, className);
				}
			}
		}, {
			key: 'hasClassWithNative_',

			/**
    * Checks if the given element has the requested css class using classList.
    * @param {!Element} element
    * @param {string} className
    * @return {boolean}
    * @protected
    */
			value: function hasClassWithNative_(element, className) {
				return element.classList.contains(className);
			}
		}, {
			key: 'hasClassWithoutNative_',

			/**
    * Checks if the given element has the requested css class without using classList.
    * @param {!Element} element
    * @param {string} className
    * @return {boolean}
    * @protected
    */
			value: function hasClassWithoutNative_(element, className) {
				return (' ' + element.className + ' ').indexOf(' ' + className + ' ') >= 0;
			}
		}, {
			key: 'isEmpty',

			/**
    * Checks if the given element is empty or not.
    * @param {!Element} element
    * @return {boolean}
    */
			value: function isEmpty(element) {
				return element.childNodes.length === 0;
			}
		}, {
			key: 'match',

			/**
    * Check if an element matches a given selector.
    * @param {Element} element
    * @param {string} selector
    * @return {boolean}
    */
			value: function match(element, selector) {
				if (!element || element.nodeType !== 1) {
					return false;
				}

				var p = Element.prototype;
				var m = p.matches || p.webkitMatchesSelector || p.mozMatchesSelector || p.msMatchesSelector || p.oMatchesSelector;
				if (m) {
					return m.call(element, selector);
				}

				return dom.matchFallback_(element, selector);
			}
		}, {
			key: 'matchFallback_',

			/**
    * Check if an element matches a given selector, using an internal implementation
    * instead of calling existing javascript functions.
    * @param {Element} element
    * @param {string} selector
    * @return {boolean}
    * @protected
    */
			value: function matchFallback_(element, selector) {
				var nodes = document.querySelectorAll(selector, element.parentNode);
				for (var i = 0; i < nodes.length; ++i) {
					if (nodes[i] === element) {
						return true;
					}
				}
				return false;
			}
		}, {
			key: 'normalizeDelegateEvent_',

			/**
    * Normalizes the event payload for delegate listeners.
    * @param {!Event} event
    */
			value: function normalizeDelegateEvent_(event) {
				event.stopPropagation = dom.stopPropagation_;
				event.stopImmediatePropagation = dom.stopImmediatePropagation_;
			}
		}, {
			key: 'on',

			/**
    * Listens to the specified event on the given DOM element. This function normalizes
    * DOM event payloads and functions so they'll work the same way on all supported
    * browsers.
    * @param {!Element} element The DOM element to listen to the event on.
    * @param {string} eventName The name of the event to listen to.
    * @param {!function(!Object)} callback Function to be called when the event is
    *   triggered. It will receive the normalized event object.
    * @return {!DomEventHandle} Can be used to remove the listener.
    */
			value: function on(element, eventName, callback) {
				var customConfig = dom.customEvents[eventName];
				if (customConfig && customConfig.event) {
					eventName = customConfig.originalEvent;
					callback = customConfig.handler.bind(customConfig, callback);
				}
				element.addEventListener(eventName, callback);
				return new _DomEventHandle['default'](element, eventName, callback);
			}
		}, {
			key: 'once',

			/**
    * Listens to the specified event on the given DOM element once. This
    * function normalizes DOM event payloads and functions so they'll work the
    * same way on all supported browsers.
    * @param {!Element} element The DOM element to listen to the event on.
    * @param {string} eventName The name of the event to listen to.
    * @param {!function(!Object)} callback Function to be called when the event
    *   is triggered. It will receive the normalized event object.
    * @return {!DomEventHandle} Can be used to remove the listener.
    */
			value: function once(element, eventName, callback) {
				var domEventHandle = this.on(element, eventName, function () {
					domEventHandle.removeListener();
					return callback.apply(this, arguments);
				});
				return domEventHandle;
			}
		}, {
			key: 'registerCustomEvent',

			/**
    * Registers a custom event.
    * @param {string} eventName The name of the custom event.
    * @param {!Object} customConfig An object with information about how the event
    *   should be handled.
    */
			value: function registerCustomEvent(eventName, customConfig) {
				dom.customEvents[eventName] = customConfig;
			}
		}, {
			key: 'removeChildren',

			/**
    * Removes all the child nodes on a DOM node.
    * @param {Element} node Element to remove children from.
    */
			value: function removeChildren(node) {
				var child;
				while (child = node.firstChild) {
					node.removeChild(child);
				}
			}
		}, {
			key: 'removeClasses',

			/**
    * Removes the requested CSS classes from an element.
    * @param {!Element} element The element to remove CSS classes from.
    * @param {string} classes CSS classes to remove.
    */
			value: function removeClasses(element, classes) {
				if (!_core['default'].isObject(element) || !_core['default'].isString(classes)) {
					return;
				}

				if ('classList' in element) {
					dom.removeClassesWithNative_(element, classes);
				} else {
					dom.removeClassesWithoutNative_(element, classes);
				}
			}
		}, {
			key: 'removeClassesWithNative_',

			/**
    * Removes the requested CSS classes from an element using classList.
    * @param {!Element} element The element to remove CSS classes from.
    * @param {string} classes CSS classes to remove.
    * @protected
    */
			value: function removeClassesWithNative_(element, classes) {
				classes.split(' ').forEach(function (className) {
					element.classList.remove(className);
				});
			}
		}, {
			key: 'removeClassesWithoutNative_',

			/**
    * Removes the requested CSS classes from an element without using classList.
    * @param {!Element} element The element to remove CSS classes from.
    * @param {string} classes CSS classes to remove.
    * @protected
    */
			value: function removeClassesWithoutNative_(element, classes) {
				var elementClassName = ' ' + element.className + ' ';

				classes = classes.split(' ');

				for (var i = 0; i < classes.length; i++) {
					elementClassName = elementClassName.replace(' ' + classes[i] + ' ', ' ');
				}

				element.className = elementClassName.trim();
			}
		}, {
			key: 'replace',

			/**
    * Replaces the first element with the second.
    * @param {Element} element1
    * @param {Element} element2
    */
			value: function replace(element1, element2) {
				if (element1 && element2 && element1 !== element2) {
					element1.parentNode.insertBefore(element2, element1);
					element1.parentNode.removeChild(element1);
				}
			}
		}, {
			key: 'stopImmediatePropagation_',

			/**
    * The function that replaces `stopImmediatePropagation_` for events.
    * @protected
    */
			value: function stopImmediatePropagation_() {
				this.stopped = true;
				Event.prototype.stopImmediatePropagation.call(this);
			}
		}, {
			key: 'stopPropagation_',

			/**
    * The function that replaces `stopPropagation` for events.
    * @protected
    */
			value: function stopPropagation_() {
				this.stopped = true;
				Event.prototype.stopPropagation.call(this);
			}
		}, {
			key: 'supportsEvent',

			/**
    * Checks if the given element supports the given event type.
    * @param {!Element|string} element The DOM element or element tag name to check.
    * @param {string} eventName The name of the event to check.
    * @return {boolean}
    */
			value: function supportsEvent(element, eventName) {
				if (dom.customEvents[eventName]) {
					return true;
				}

				if (_core['default'].isString(element)) {
					if (!elementsByTag[element]) {
						elementsByTag[element] = document.createElement(element);
					}
					element = elementsByTag[element];
				}
				return 'on' + eventName in element;
			}
		}, {
			key: 'toElement',

			/**
    * Converts the given argument to a DOM element. Strings are assumed to
    * be selectors, and so a matched element will be returned. If the arg
    * is already a DOM element it will be the return value.
    * @param {string|Element|Document} selectorOrElement
    * @return {Element} The converted element, or null if none was found.
    */
			value: function toElement(selectorOrElement) {
				if (_core['default'].isElement(selectorOrElement) || _core['default'].isDocument(selectorOrElement)) {
					return selectorOrElement;
				} else if (_core['default'].isString(selectorOrElement)) {
					if (selectorOrElement[0] === '#' && selectorOrElement.indexOf(' ') === -1) {
						return document.getElementById(selectorOrElement.substr(1));
					} else {
						return document.querySelector(selectorOrElement);
					}
				} else {
					return null;
				}
			}
		}, {
			key: 'toggleClasses',

			/**
    * Adds or removes one or more classes from an element. If any of the classes
    * is present, it will be removed from the element, or added otherwise.
    * @param {!Element} element The element which classes will be toggled.
    * @param {string} classes The classes which have to added or removed from the element.
    */
			value: function toggleClasses(element, classes) {
				if (!_core['default'].isObject(element) || !_core['default'].isString(classes)) {
					return;
				}

				if ('classList' in element) {
					dom.toggleClassesWithNative_(element, classes);
				} else {
					dom.toggleClassesWithoutNative_(element, classes);
				}
			}
		}, {
			key: 'toggleClassesWithNative_',

			/**
    * Adds or removes one or more classes from an element using classList.
    * If any of the classes is present, it will be removed from the element,
    * or added otherwise.
    * @param {!Element} element The element which classes will be toggled.
    * @param {string} classes The classes which have to added or removed from the element.
    */
			value: function toggleClassesWithNative_(element, classes) {
				classes.split(' ').forEach(function (className) {
					element.classList.toggle(className);
				});
			}
		}, {
			key: 'toggleClassesWithoutNative_',

			/**
    * Adds or removes one or more classes from an element without using classList.
    * If any of the classes is present, it will be removed from the element,
    * or added otherwise.
    * @param {!Element} element The element which classes will be toggled.
    * @param {string} classes The classes which have to added or removed from the element.
    */
			value: function toggleClassesWithoutNative_(element, classes) {
				var elementClassName = ' ' + element.className + ' ';

				classes = classes.split(' ');

				for (var i = 0; i < classes.length; i++) {
					var className = ' ' + classes[i] + ' ';
					var classIndex = elementClassName.indexOf(className);

					if (classIndex === -1) {
						elementClassName = elementClassName + classes[i] + ' ';
					} else {
						elementClassName = elementClassName.substring(0, classIndex) + ' ' + elementClassName.substring(classIndex + className.length);
					}
				}

				element.className = elementClassName.trim();
			}
		}, {
			key: 'triggerEvent',

			/**
    * Triggers the specified event on the given element.
    * NOTE: This should mostly be used for testing, not on real code.
    * @param {!Element} element The node that should trigger the event.
    * @param {string} eventName The name of the event to be triggred.
    * @param {Object=} opt_eventObj An object with data that should be on the
    *   triggered event's payload.
    */
			value: function triggerEvent(element, eventName, opt_eventObj) {
				var eventObj = document.createEvent('HTMLEvents');
				eventObj.initEvent(eventName, true, true);
				_object['default'].mixin(eventObj, opt_eventObj);
				element.dispatchEvent(eventObj);
			}
		}]);

		return dom;
	})();

	var elementsByTag = {};
	dom.customEvents = {};

	module.exports = dom;
});