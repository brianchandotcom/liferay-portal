define(['exports', 'metal-events/src/events'], function (exports, _events) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _defineProperty(obj, key, value) {
		if (key in obj) {
			Object.defineProperty(obj, key, {
				value: value,
				enumerable: true,
				configurable: true,
				writable: true
			});
		} else {
			obj[key] = value;
		}

		return obj;
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

	var ComponentRenderer = function (_EventEmitter) {
		_inherits(ComponentRenderer, _EventEmitter);

		/**
   * Constructor function for `ComponentRenderer`.
   * @param {!Component} component The component that this renderer is
   *     responsible for.
   */

		function ComponentRenderer(component) {
			_classCallCheck(this, ComponentRenderer);

			var _this = _possibleConstructorReturn(this, _EventEmitter.call(this));

			_this.component_ = component;

			_this.componentRendererEvents_ = new _events.EventHandler();
			_this.componentRendererEvents_.add(_this.component_.once('render', _this.render.bind(_this)));
			_this.on('rendered', _this.handleRendered_);

			if (_this.component_.constructor.SYNC_UPDATES_MERGED) {
				_this.componentRendererEvents_.add(_this.component_.on('stateKeyChanged', _this.handleComponentRendererStateKeyChanged_.bind(_this)));
			} else {
				_this.componentRendererEvents_.add(_this.component_.on('stateChanged', _this.handleComponentRendererStateChanged_.bind(_this)));
			}
			return _this;
		}

		/**
   * @inheritDoc
   */


		ComponentRenderer.prototype.disposeInternal = function disposeInternal() {
			this.componentRendererEvents_.removeAllListeners();
			this.componentRendererEvents_ = null;
		};

		ComponentRenderer.prototype.handleComponentRendererStateChanged_ = function handleComponentRendererStateChanged_(changes) {
			if (this.shouldRerender_(changes)) {
				this.update(changes);
			}
		};

		ComponentRenderer.prototype.handleComponentRendererStateKeyChanged_ = function handleComponentRendererStateKeyChanged_(data) {
			var changes = {
				changes: _defineProperty({}, data.key, data)
			};
			if (this.shouldRerender_(changes)) {
				this.update(changes);
			}
		};

		ComponentRenderer.prototype.handleRendered_ = function handleRendered_() {
			this.isRendered_ = true;
		};

		ComponentRenderer.prototype.hasChangedBesidesElement_ = function hasChangedBesidesElement_(changes) {
			var count = Object.keys(changes).length;
			if (changes.hasOwnProperty('element')) {
				count--;
			}
			return count > 0;
		};

		ComponentRenderer.prototype.render = function render() {
			if (!this.component_.element) {
				this.component_.element = document.createElement('div');
			}
			this.emit('rendered', !this.isRendered_);
		};

		ComponentRenderer.prototype.shouldRerender_ = function shouldRerender_(changes) {
			return this.isRendered_ && !this.skipUpdates_ && this.hasChangedBesidesElement_(changes.changes);
		};

		ComponentRenderer.prototype.startSkipUpdates = function startSkipUpdates() {
			this.skipUpdates_ = true;
		};

		ComponentRenderer.prototype.stopSkipUpdates = function stopSkipUpdates() {
			this.skipUpdates_ = false;
		};

		ComponentRenderer.prototype.update = function update() {};

		return ComponentRenderer;
	}(_events.EventEmitter);

	exports.default = ComponentRenderer;
});
//# sourceMappingURL=ComponentRenderer.js.map