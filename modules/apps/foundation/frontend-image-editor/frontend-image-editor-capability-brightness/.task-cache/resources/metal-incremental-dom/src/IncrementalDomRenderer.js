define(['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-component/src/all/component', './IncrementalDomAop', './children/IncrementalDomChildren', './cleanup/IncrementalDomUnusedComponents', './utils/IncrementalDomUtils', './incremental-dom'], function (exports, _metal, _dom, _component, _IncrementalDomAop, _IncrementalDomChildren, _IncrementalDomUnusedComponents, _IncrementalDomUtils) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	var _IncrementalDomAop2 = _interopRequireDefault(_IncrementalDomAop);

	var _IncrementalDomChildren2 = _interopRequireDefault(_IncrementalDomChildren);

	var _IncrementalDomUnusedComponents2 = _interopRequireDefault(_IncrementalDomUnusedComponents);

	var _IncrementalDomUtils2 = _interopRequireDefault(_IncrementalDomUtils);

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
			_this.setConfig_(comp, comp.getInitialConfig());
			_this.changes_ = {};
			comp.on('attached', _this.handleAttached_.bind(_this));

			if (!_this.component_.constructor.SYNC_UPDATES_MERGED) {
				// If the component is being updated synchronously we'll just reuse the
				// `handleComponentRendererStateKeyChanged_` function from
				// `ComponentRenderer`.
				comp.on('stateKeyChanged', _this.handleStateKeyChanged_.bind(_this));
			}

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
   * Attaches inline listeners found on the first component render, since those
   * may come from existing elements on the page that already have
   * data-on[eventname] attributes set to its final value. This won't trigger
   * `handleInterceptedAttributesCall_`, so we need manual work to guarantee
   * that projects using progressive enhancement like this will still work.
   * @param {!Element} node
   * @param {!Array} args
   * @protected
   */


		IncrementalDomRenderer.prototype.attachDecoratedListeners_ = function attachDecoratedListeners_(node, args) {
			if (!this.component_.wasRendered) {
				var attrs = (args[2] || []).concat(args.slice(3));
				for (var i = 0; i < attrs.length; i += 2) {
					var eventName = this.getEventFromListenerAttr_(attrs[i]);
					if (eventName && !node[eventName + '__handle__']) {
						this.attachEvent_(node, attrs[i], eventName, attrs[i + 1]);
					}
				}
			}
		};

		IncrementalDomRenderer.prototype.attachEvent_ = function attachEvent_(element, key, eventName, fn) {
			var handleKey = eventName + '__handle__';
			if (element[handleKey]) {
				element[handleKey].removeListener();
				element[handleKey] = null;
			}

			element[key] = fn;
			if (fn) {
				if (_metal.core.isString(fn)) {
					if (key[0] === 'd') {
						// Allow data-on[eventkey] listeners to stay in the dom, as they
						// won't cause conflicts.
						element.setAttribute(key, fn);
					}
					fn = this.component_.getListenerFn(fn);
				}
				element[handleKey] = _dom2.default.delegate(document, eventName, element, fn);
			} else {
				element.removeAttribute(key);
			}
		};

		IncrementalDomRenderer.prototype.buildChildren_ = function buildChildren_(children) {
			return children.length === 0 ? emptyChildren_ : children;
		};

		IncrementalDomRenderer.prototype.buildRef = function buildRef(tag) {
			var ctor = _metal.core.isString(tag) ? _component.ComponentRegistry.getConstructor(tag) : tag;
			var prefix = this.currentPrefix_ + _metal.core.getUid(ctor, true);
			var count = this.generatedRefCount_[prefix] || 0;
			this.generatedRefCount_[prefix] = count + 1;
			return prefix + 'sub' + count;
		};

		IncrementalDomRenderer.getComponentBeingRendered = function getComponentBeingRendered() {
			return renderingComponents_[renderingComponents_.length - 1];
		};

		IncrementalDomRenderer.prototype.getSubComponent_ = function getSubComponent_(tagOrCtor, config) {
			var ConstructorFn = tagOrCtor;
			if (_metal.core.isString(ConstructorFn)) {
				ConstructorFn = _component.ComponentRegistry.getConstructor(tagOrCtor);
			}

			var comp = this.component_.components[config.ref];
			if (comp && comp.constructor !== ConstructorFn) {
				comp = null;
			}

			if (!comp) {
				comp = new ConstructorFn(config, false);
				this.component_.addSubComponent(config.ref, comp);
			}

			if (comp.wasRendered) {
				this.setConfig_(comp, config);
				comp.getRenderer().startSkipUpdates();
				comp.setState(config);
				comp.getRenderer().stopSkipUpdates();
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
			if (renderingComponents_.length === 0) {
				_IncrementalDomUnusedComponents2.default.disposeUnused();
			}
		};

		IncrementalDomRenderer.prototype.handleAttached_ = function handleAttached_(data) {
			this.attachData_ = data;
		};

		IncrementalDomRenderer.prototype.handleInterceptedAttributesCall_ = function handleInterceptedAttributesCall_(originalFn, element, name, value) {
			var eventName = this.getEventFromListenerAttr_(name);
			if (eventName) {
				this.attachEvent_(element, name, eventName, value);
				return;
			}

			if (name === 'checked') {
				// This is a temporary fix to account for incremental dom setting
				// "checked" as an attribute only, which can cause bugs since that won't
				// necessarily check/uncheck the element it's set on. See
				// https://github.com/google/incremental-dom/issues/198 for more details.
				value = _metal.core.isDefAndNotNull(value) && value !== false;
			}

			if (name === 'value' && element.value !== value) {
				// This is a temporary fix to account for incremental dom setting
				// "value" as an attribute only, which can cause bugs since that won't
				// necessarily update the input's content it's set on. See
				// https://github.com/google/incremental-dom/issues/239 for more details.
				// We only do this if the new value is different though, as otherwise the
				// browser will automatically move the typing cursor to the end of the
				// field.
				element[name] = value;
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

			config.children = this.buildChildren_(tree.config.children);
			this.componentToRender_ = null;
			this.currentPrefix_ = this.prevPrefix_;
			this.prevPrefix_ = null;
			this.renderFromTag_(tag, config);
		};

		IncrementalDomRenderer.prototype.handleChildRender_ = function handleChildRender_(node) {
			if (node.tag && _IncrementalDomUtils2.default.isComponentTag(node.tag)) {
				node.config.children = this.buildChildren_(node.config.children);
				this.renderFromTag_(node.tag, node.config);
				return true;
			}
		};

		IncrementalDomRenderer.prototype.handleComponentRendererStateKeyChanged_ = function handleComponentRendererStateKeyChanged_(data) {
			this.handleStateKeyChanged_(data);
			_ComponentRenderer.prototype.handleComponentRendererStateKeyChanged_.call(this, data);
		};

		IncrementalDomRenderer.prototype.handleInterceptedOpenCall_ = function handleInterceptedOpenCall_(originalFn, tag) {
			if (_IncrementalDomUtils2.default.isComponentTag(tag)) {
				return this.handleSubComponentCall_.apply(this, arguments);
			} else {
				return this.handleRegularCall_.apply(this, arguments);
			}
		};

		IncrementalDomRenderer.prototype.handleRegularCall_ = function handleRegularCall_(originalFn) {
			var currComp = IncrementalDomRenderer.getComponentBeingRendered();
			var currRenderer = currComp.getRenderer();

			for (var _len = arguments.length, args = Array(_len > 1 ? _len - 1 : 0), _key = 1; _key < _len; _key++) {
				args[_key - 1] = arguments[_key];
			}

			if (!currRenderer.rootElementReached_ && currComp.config.key) {
				args[1] = currComp.config.key;
			}

			var node = originalFn.apply(null, args);
			this.attachDecoratedListeners_(node, args);
			this.updateElementIfNotReached_(node);
			return node;
		};

		IncrementalDomRenderer.prototype.handleStateKeyChanged_ = function handleStateKeyChanged_(data) {
			this.changes_[data.key] = data;
		};

		IncrementalDomRenderer.prototype.handleSubComponentCall_ = function handleSubComponentCall_(originalFn) {
			for (var _len2 = arguments.length, args = Array(_len2 > 1 ? _len2 - 1 : 0), _key2 = 1; _key2 < _len2; _key2++) {
				args[_key2 - 1] = arguments[_key2];
			}

			var config = _IncrementalDomUtils2.default.buildConfigFromCall(args);
			config.ref = _metal.core.isDefAndNotNull(config.ref) ? config.ref : this.buildRef(args[0]);
			this.componentToRender_ = {
				config: config,
				tag: args[0]
			};

			this.prevPrefix_ = this.currentPrefix_;
			this.currentPrefix_ = config.ref;
			this.generatedRefCount_[this.currentPrefix_] = 0;
			_IncrementalDomChildren2.default.capture(this, this.handleChildrenCaptured_);
		};

		IncrementalDomRenderer.prototype.intercept_ = function intercept_() {
			_IncrementalDomAop2.default.startInterception({
				attributes: this.handleInterceptedAttributesCall_,
				elementOpen: this.handleInterceptedOpenCall_
			});
		};

		IncrementalDomRenderer.isIncDomNode = function isIncDomNode(node) {
			return !!node[_IncrementalDomChildren2.default.CHILD_OWNER];
		};

		IncrementalDomRenderer.prototype.getEventFromListenerAttr_ = function getEventFromListenerAttr_(attr) {
			var matches = IncrementalDomRenderer.LISTENER_REGEX.exec(attr);
			var eventName = matches ? matches[1] ? matches[1] : matches[2] : null;
			return eventName ? eventName.toLowerCase() : null;
		};

		IncrementalDomRenderer.prototype.getParent = function getParent() {
			return this.parent_;
		};

		IncrementalDomRenderer.prototype.getOwner = function getOwner() {
			return this.owner_;
		};

		IncrementalDomRenderer.render = function render(fnOrCtor, opt_dataOrElement, opt_parent) {
			if (!_component.Component.isComponentCtor(fnOrCtor)) {
				var fn = fnOrCtor;

				var TempComponent = function (_Component) {
					_inherits(TempComponent, _Component);

					function TempComponent() {
						_classCallCheck(this, TempComponent);

						return _possibleConstructorReturn(this, _Component.apply(this, arguments));
					}

					TempComponent.prototype.created = function created() {
						if (IncrementalDomRenderer.getComponentBeingRendered()) {
							this.getRenderer().updateContext_(this);
						}
					};

					TempComponent.prototype.render = function render() {
						fn(this.config);
					};

					return TempComponent;
				}(_component.Component);

				TempComponent.RENDERER = IncrementalDomRenderer;
				fnOrCtor = TempComponent;
			}
			return _component.Component.render(fnOrCtor, opt_dataOrElement, opt_parent);
		};

		IncrementalDomRenderer.prototype.render = function render() {
			this.patch();
		};

		IncrementalDomRenderer.renderChild = function renderChild(child) {
			child[_IncrementalDomChildren2.default.CHILD_OWNER].renderChild(child);
		};

		IncrementalDomRenderer.prototype.renderChild = function renderChild(child) {
			this.intercept_();
			_IncrementalDomChildren2.default.render(child, this.handleChildRender_);
			_IncrementalDomAop2.default.stopInterception();
		};

		IncrementalDomRenderer.prototype.renderFromTag_ = function renderFromTag_(tag, config) {
			if (_metal.core.isString(tag) || tag.prototype.getRenderer) {
				var comp = this.renderSubComponent_(tag, config);
				this.updateElementIfNotReached_(comp.element);
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
			if (this.component_.wasRendered && !this.shouldUpdate(this.changes_) && IncrementalDOM.currentPointer() === this.component_.element) {
				if (this.component_.element) {
					IncrementalDOM.skipNode();
				}
				return;
			}
			this.renderInsidePatchDontSkip_();
		};

		IncrementalDomRenderer.prototype.renderInsidePatchDontSkip_ = function renderInsidePatchDontSkip_() {
			IncrementalDomRenderer.startedRenderingComponent(this.component_);
			this.changes_ = {};
			this.rootElementReached_ = false;
			_IncrementalDomUnusedComponents2.default.schedule(this.childComponents_ || []);
			this.childComponents_ = [];
			this.generatedRefCount_ = {};
			this.listenersToAttach_ = [];
			this.currentPrefix_ = '';
			this.intercept_();
			this.renderIncDom();
			_IncrementalDomAop2.default.stopInterception();
			if (!this.rootElementReached_) {
				this.component_.element = null;
			} else {
				this.component_.addElementClasses();
			}
			this.emit('rendered', !this.isRendered_);
			IncrementalDomRenderer.finishedRenderingComponent();
		};

		IncrementalDomRenderer.prototype.renderSubComponent_ = function renderSubComponent_(tagOrCtor, config) {
			var comp = this.getSubComponent_(tagOrCtor, config);
			this.updateContext_(comp);
			var renderer = comp.getRenderer();
			if (renderer instanceof IncrementalDomRenderer) {
				var parentComp = IncrementalDomRenderer.getComponentBeingRendered();
				parentComp.getRenderer().childComponents_.push(comp);
				renderer.parent_ = parentComp;
				renderer.owner_ = this.component_;
				renderer.renderInsidePatch();
			} else {
				console.warn('IncrementalDomRenderer doesn\'t support rendering sub components ' + 'that don\'t use IncrementalDomRenderer as well, like:', comp);
			}
			if (!comp.wasRendered) {
				comp.renderAsSubComponent();
			}
			return comp;
		};

		IncrementalDomRenderer.prototype.setConfig_ = function setConfig_(comp, config) {
			var prevConfig = comp.config;
			comp.config = config;
			if (_metal.core.isFunction(comp.configChanged)) {
				comp.configChanged(config, prevConfig || {});
			}
			comp.emit('configChanged', {
				prevVal: prevConfig,
				newVal: config
			});
		};

		IncrementalDomRenderer.prototype.shouldUpdate = function shouldUpdate(changes) {
			if (this.component_.shouldUpdate) {
				return this.component_.shouldUpdate(changes);
			}
			return true;
		};

		IncrementalDomRenderer.startedRenderingComponent = function startedRenderingComponent(comp) {
			renderingComponents_.push(comp);
		};

		IncrementalDomRenderer.prototype.patch = function patch() {
			if (!this.component_.element && this.parent_) {
				// If the component has no content but was rendered from another component,
				// we'll need to patch this parent to make sure that any new content will
				// be added in the right place.
				this.parent_.getRenderer().patch();
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
			if (this.hasChangedBesidesElement_(this.changes_) && this.shouldUpdate(this.changes_)) {
				this.patch();
			}
		};

		IncrementalDomRenderer.prototype.updateElementIfNotReached_ = function updateElementIfNotReached_(node) {
			var currComp = IncrementalDomRenderer.getComponentBeingRendered();
			var currRenderer = currComp.getRenderer();
			if (!currRenderer.rootElementReached_) {
				currRenderer.rootElementReached_ = true;
				if (currComp.element !== node) {
					currComp.element = node;
				}
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
	var emptyChildren_ = [];

	IncrementalDomRenderer.LISTENER_REGEX = /^(?:on([A-Z]\w+))|(?:data-on(\w+))$/;

	exports.default = IncrementalDomRenderer;
});
//# sourceMappingURL=IncrementalDomRenderer.js.map