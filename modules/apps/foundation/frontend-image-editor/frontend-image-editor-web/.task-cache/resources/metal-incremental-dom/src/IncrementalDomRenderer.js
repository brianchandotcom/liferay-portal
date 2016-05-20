define(['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-component/src/all/component', './IncrementalDomAop', './children/IncrementalDomChildren', './incremental-dom'], function (exports, _metal, _dom, _component, _IncrementalDomAop, _IncrementalDomChildren) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	var _IncrementalDomAop2 = _interopRequireDefault(_IncrementalDomAop);

	var _IncrementalDomChildren2 = _interopRequireDefault(_IncrementalDomChildren);

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

	var IncrementalDomRenderer = function (_ComponentRenderer) {
		_inherits(IncrementalDomRenderer, _ComponentRenderer);

		/**
   * @inheritDoc
   */

		function IncrementalDomRenderer(comp) {
			_classCallCheck(this, IncrementalDomRenderer);

			var _this = _possibleConstructorReturn(this, _ComponentRenderer.call(this, comp));

			comp.context = {};
			_this.changes_ = {};
			_this.eventsCollector_ = new _component.EventsCollector(comp);
			_this.lastElementCreationCall_ = [];
			comp.on('stateKeyChanged', _this.handleStateKeyChanged_.bind(_this));
			comp.on('attached', _this.handleAttached_.bind(_this));
			comp.on('detached', _this.handleDetached_.bind(_this));

			// Binds functions that will be used many times, to avoid creating new
			// functions each time.
			_this.handleInterceptedAttributesCall_ = _this.handleInterceptedAttributesCall_.bind(_this);
			_this.handleInterceptedOpenCall_ = _this.handleInterceptedOpenCall_.bind(_this);
			_this.handleChildrenCaptured_ = _this.handleChildrenCaptured_.bind(_this);
			_this.handleChildRender_ = _this.handleChildRender_.bind(_this);
			_this.renderInsidePatchDontSkip_ = _this.renderInsidePatchDontSkip_.bind(_this);
			return _this;
		}

		/**
   * Adds all inline listener attributes included in the given config.
   * @param {!Array} listeners
   * @protected
   */


		IncrementalDomRenderer.prototype.addInlineListeners_ = function addInlineListeners_(listeners) {
			for (var i = 0; i < listeners.length; i += 2) {
				var name = listeners[i];
				var fn = listeners[i + 1];
				if (this.isListenerAttr_(name) && _metal.core.isString(fn)) {
					this.listenersToAttach_.push({
						eventName: name.substr(7),
						fn: fn
					});
				}
			}
		};

		IncrementalDomRenderer.prototype.attachInlineListeners_ = function attachInlineListeners_() {
			this.eventsCollector_.startCollecting();
			for (var i = 0; i < this.listenersToAttach_.length; i++) {
				var listener = this.listenersToAttach_[i];
				this.eventsCollector_.attachListener(listener.eventName, listener.fn);
			}
		};

		IncrementalDomRenderer.prototype.buildChildrenFn_ = function buildChildrenFn_(children) {
			var _this2 = this;

			if (children.length === 0) {
				return emptyChildrenFn_;
			}
			var prefix = this.buildKey_();
			var fn = function fn() {
				var prevPrefix = _this2.currentPrefix_;
				_this2.generatedKeyCount_[prefix] = 0;
				_this2.currentPrefix_ = prefix;
				_this2.intercept_();
				_IncrementalDomChildren2.default.render(fn, _this2.handleChildRender_);
				_IncrementalDomAop2.default.stopInterception();
				_this2.currentPrefix_ = prevPrefix;
			};
			fn.children = children;
			return fn;
		};

		IncrementalDomRenderer.prototype.buildConfigFromCall_ = function buildConfigFromCall_(args) {
			var config = {
				key: args[1]
			};
			var attrsArr = (args[2] || []).concat(args.slice(3));
			for (var i = 0; i < attrsArr.length; i += 2) {
				config[attrsArr[i]] = attrsArr[i + 1];
			}
			return config;
		};

		IncrementalDomRenderer.prototype.buildKey_ = function buildKey_() {
			var count = this.generatedKeyCount_[this.currentPrefix_] || 0;
			this.generatedKeyCount_[this.currentPrefix_] = count + 1;
			return this.currentPrefix_ + 'sub' + count;
		};

		IncrementalDomRenderer.prototype.disposeUnusedSubComponents_ = function disposeUnusedSubComponents_() {
			var keys = Object.keys(this.component_.components);
			var unused = [];
			for (var i = 0; i < keys.length; i++) {
				if (!this.subComponentsFound_[keys[i]]) {
					unused.push(keys[i]);
				}
			}
			this.component_.disposeSubComponents(unused);
		};

		IncrementalDomRenderer.getComponentBeingRendered = function getComponentBeingRendered() {
			return renderingComponents_[renderingComponents_.length - 1];
		};

		IncrementalDomRenderer.prototype.getSubComponent_ = function getSubComponent_(key, tagOrCtor, config) {
			var comp = this.component_.addSubComponent(key, tagOrCtor, config);
			if (comp.wasRendered) {
				comp.setState(config);
			}
			return comp;
		};

		IncrementalDomRenderer.prototype.guaranteeParent_ = function guaranteeParent_() {
			var element = this.component_.element;
			if (!element || !element.parentNode) {
				var parent = document.createElement('div');
				if (element) {
					_dom2.default.append(parent, element);
				}
				return parent;
			}
		};

		IncrementalDomRenderer.finishedRenderingComponent = function finishedRenderingComponent() {
			renderingComponents_.pop();
		};

		IncrementalDomRenderer.prototype.handleAttached_ = function handleAttached_(data) {
			this.attachData_ = data;
		};

		IncrementalDomRenderer.prototype.handleDetached_ = function handleDetached_() {
			this.eventsCollector_.detachAllListeners();
		};

		IncrementalDomRenderer.prototype.handleInterceptedAttributesCall_ = function handleInterceptedAttributesCall_(originalFn, element, name, value) {
			if (this.isListenerAttr_(name)) {
				var eventName = name.substr(7);
				if (_metal.core.isFunction(element[name])) {
					element.removeEventListener(eventName, element[name]);
				}
				if (_metal.core.isFunction(value)) {
					_dom2.default.on(element, eventName, value);
				}
			}

			if (name === 'checked') {
				// This is a temporary fix to account for incremental dom setting
				// "checked" as an attribute only, which can cause bugs since that won't
				// necessarily check/uncheck the element it's set on. See
				// https://github.com/google/incremental-dom/issues/198 for more details.
				value = _metal.core.isDefAndNotNull(value) && value !== false;
			}

			if (_metal.core.isBoolean(value)) {
				// Incremental dom sets boolean values as string data attributes, which
				// is counter intuitive. This changes the behavior to use the actual
				// boolean value.
				element[name] = value;
				if (value) {
					element.setAttribute(name, '');
				} else {
					element.removeAttribute(name);
				}
			} else {
				originalFn(element, name, value);
			}
		};

		IncrementalDomRenderer.prototype.handleChildrenCaptured_ = function handleChildrenCaptured_(tree) {
			var _componentToRender_ = this.componentToRender_;
			var config = _componentToRender_.config;
			var tag = _componentToRender_.tag;

			config.children = this.buildChildrenFn_(tree.children);
			this.componentToRender_ = null;
			this.renderFromTag_(tag, config);
		};

		IncrementalDomRenderer.prototype.handleChildRender_ = function handleChildRender_(node) {
			if (node.args && !node.isText && this.isComponentTag_(node.args[0])) {
				var config = this.buildConfigFromCall_(node.args);
				config.children = this.buildChildrenFn_(node.children);
				this.renderFromTag_(node.args[0], config);
				return true;
			}
		};

		IncrementalDomRenderer.prototype.handleInterceptedOpenCall_ = function handleInterceptedOpenCall_(originalFn, tag) {
			if (this.isComponentTag_(tag)) {
				return this.handleSubComponentCall_.apply(this, arguments);
			} else {
				return this.handleRegularCall_.apply(this, arguments);
			}
		};

		IncrementalDomRenderer.prototype.handleRegularCall_ = function handleRegularCall_(originalFn, tag, key, statics) {
			var attrsArr = _metal.array.slice(arguments, 4);
			this.addInlineListeners_((statics || []).concat(attrsArr));
			var args = _metal.array.slice(arguments, 1);

			var currComp = IncrementalDomRenderer.getComponentBeingRendered();
			var currRenderer = currComp.getRenderer();
			if (!currRenderer.rootElementReached_ && currComp.config.key) {
				args[1] = currComp.config.key;
			}

			var node = originalFn.apply(null, args);
			this.updateElementIfNotReached_(node, args);
			return node;
		};

		IncrementalDomRenderer.prototype.handleStateKeyChanged_ = function handleStateKeyChanged_(data) {
			this.changes_[data.key] = data;
		};

		IncrementalDomRenderer.prototype.handleSubComponentCall_ = function handleSubComponentCall_(originalFn) {
			for (var _len = arguments.length, args = Array(_len > 1 ? _len - 1 : 0), _key = 1; _key < _len; _key++) {
				args[_key - 1] = arguments[_key];
			}

			this.componentToRender_ = {
				config: this.buildConfigFromCall_(args),
				tag: args[0]
			};
			_IncrementalDomChildren2.default.capture(this.handleChildrenCaptured_);
		};

		IncrementalDomRenderer.prototype.hasChangedBesidesElement_ = function hasChangedBesidesElement_() {
			var count = Object.keys(this.changes_).length;
			if (this.changes_.hasOwnProperty('element')) {
				count--;
			}
			return count > 0;
		};

		IncrementalDomRenderer.prototype.intercept_ = function intercept_() {
			_IncrementalDomAop2.default.startInterception({
				attributes: this.handleInterceptedAttributesCall_,
				elementOpen: this.handleInterceptedOpenCall_
			});
		};

		IncrementalDomRenderer.prototype.isComponentTag_ = function isComponentTag_(tag) {
			return !_metal.core.isString(tag) || tag[0] === tag[0].toUpperCase();
		};

		IncrementalDomRenderer.prototype.isListenerAttr_ = function isListenerAttr_(attr) {
			return attr.substr(0, 7) === 'data-on';
		};

		IncrementalDomRenderer.prototype.render = function render() {
			this.patch();
		};

		IncrementalDomRenderer.prototype.renderFromTag_ = function renderFromTag_(tag, config) {
			if (_metal.core.isString(tag) || tag.prototype.getRenderer) {
				var comp = this.renderSubComponent_(tag, config);
				this.updateElementIfNotReached_(comp);
				return comp.element;
			} else {
				return tag(config);
			}
		};

		IncrementalDomRenderer.prototype.renderIncDom = function renderIncDom() {
			if (this.component_.render) {
				this.component_.render();
			} else {
				IncrementalDOM.elementVoid('div');
			}
		};

		IncrementalDomRenderer.prototype.renderInsidePatch = function renderInsidePatch() {
			if (this.component_.wasRendered && !this.shouldUpdate(this.changes_)) {
				this.skipRerender_();
				return;
			}
			this.renderInsidePatchDontSkip_();
		};

		IncrementalDomRenderer.prototype.renderInsidePatchDontSkip_ = function renderInsidePatchDontSkip_() {
			IncrementalDomRenderer.startedRenderingComponent(this.component_);
			this.changes_ = {};
			this.rootElementReached_ = false;
			this.subComponentsFound_ = {};
			this.generatedKeyCount_ = {};
			this.listenersToAttach_ = [];
			this.currentPrefix_ = '';
			this.intercept_();
			this.renderIncDom();
			_IncrementalDomAop2.default.stopInterception();
			this.attachInlineListeners_();
			IncrementalDomRenderer.finishedRenderingComponent();
			if (!this.rootElementReached_) {
				this.component_.element = null;
			}
			this.emit('rendered', !this.component_.wasRendered);
		};

		IncrementalDomRenderer.prototype.renderSubComponent_ = function renderSubComponent_(tagOrCtor, config) {
			var key = config.key || this.buildKey_();
			var comp = this.getSubComponent_(key, tagOrCtor, config);
			this.updateContext_(comp);
			var renderer = comp.getRenderer();
			if (renderer instanceof IncrementalDomRenderer) {
				renderer.lastParentComponent_ = IncrementalDomRenderer.getComponentBeingRendered();
				renderer.renderInsidePatch();
			} else {
				console.warn('IncrementalDomRenderer doesn\'t support rendering sub components ' + 'that don\'t use IncrementalDomRenderer as well, like:', comp);
			}
			if (!comp.wasRendered) {
				comp.renderAsSubComponent();
			}
			this.subComponentsFound_[key] = true;
			return comp;
		};

		IncrementalDomRenderer.prototype.shouldUpdate = function shouldUpdate(changes) {
			if (this.component_.shouldUpdate) {
				return this.component_.shouldUpdate(changes);
			}
			return true;
		};

		IncrementalDomRenderer.prototype.skipRerender_ = function skipRerender_() {
			if (this.lastElementCreationCall_.length > 0) {
				IncrementalDOM.elementOpen.apply(null, this.lastElementCreationCall_);
				IncrementalDOM.skip();
				IncrementalDOM.elementClose(this.lastElementCreationCall_[0]);
			}
		};

		IncrementalDomRenderer.startedRenderingComponent = function startedRenderingComponent(comp) {
			renderingComponents_.push(comp);
		};

		IncrementalDomRenderer.prototype.patch = function patch() {
			if (!this.component_.element && this.lastParentComponent_) {
				// If the component has no content but was rendered from another component,
				// we'll need to patch this parent to make sure that any new content will
				// be added in the right place.
				this.lastParentComponent_.getRenderer().patch();
				return;
			}

			var tempParent = this.guaranteeParent_();
			if (tempParent) {
				IncrementalDOM.patch(tempParent, this.renderInsidePatchDontSkip_);
				_dom2.default.exitDocument(this.component_.element);
				if (this.component_.element && this.component_.inDocument) {
					this.component_.renderElement_(this.attachData_.parent, this.attachData_.sibling);
				}
			} else {
				var element = this.component_.element;
				IncrementalDOM.patchOuter(element, this.renderInsidePatchDontSkip_);
				if (!this.component_.element) {
					_dom2.default.exitDocument(element);
				}
			}
		};

		IncrementalDomRenderer.prototype.update = function update() {
			if (this.hasChangedBesidesElement_() && this.shouldUpdate(this.changes_)) {
				this.patch();
				this.eventsCollector_.detachUnusedListeners();
				this.disposeUnusedSubComponents_();
			}
		};

		IncrementalDomRenderer.prototype.updateElementIfNotReached_ = function updateElementIfNotReached_(nodeOrComponent, opt_args) {
			var currComp = IncrementalDomRenderer.getComponentBeingRendered();
			var currRenderer = currComp.getRenderer();
			if (!currRenderer.rootElementReached_) {
				currRenderer.rootElementReached_ = true;

				var node = nodeOrComponent;
				var args = opt_args;

				if (nodeOrComponent instanceof _component.Component) {
					var renderer = nodeOrComponent.getRenderer();
					args = renderer instanceof IncrementalDomRenderer ? renderer.lastElementCreationCall_ : [];
					node = nodeOrComponent.element;
				}

				if (currComp.element !== node) {
					currComp.element = node;
				}
				currRenderer.lastElementCreationCall_ = args;
			}
		};

		IncrementalDomRenderer.prototype.updateContext_ = function updateContext_(comp) {
			var context = comp.context;
			var parent = IncrementalDomRenderer.getComponentBeingRendered();
			var childContext = parent.getChildContext ? parent.getChildContext() : {};
			_metal.object.mixin(context, parent.context, childContext);
			comp.context = context;
		};

		return IncrementalDomRenderer;
	}(_component.ComponentRenderer);

	var renderingComponents_ = [];
	function emptyChildrenFn_() {}
	emptyChildrenFn_.calls = [];

	exports.default = IncrementalDomRenderer;
});
//# sourceMappingURL=IncrementalDomRenderer.js.map