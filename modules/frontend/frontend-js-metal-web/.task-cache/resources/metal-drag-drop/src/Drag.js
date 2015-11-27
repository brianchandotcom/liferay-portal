'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/metal-drag-drop/src/Drag", ['exports', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/object/object', 'metal/src/attribute/Attribute', 'metal-drag-drop/src/helpers/DragAutoScroll', 'metal-drag-drop/src/helpers/DragScrollDelta', 'metal-drag-drop/src/helpers/DragShim', 'metal/src/events/EventHandler', 'metal-position/src/Position'], function (exports, _core, _dom, _object, _Attribute2, _DragAutoScroll, _DragScrollDelta, _DragShim, _EventHandler, _Position) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _object2 = _interopRequireDefault(_object);

	var _Attribute3 = _interopRequireDefault(_Attribute2);

	var _DragAutoScroll2 = _interopRequireDefault(_DragAutoScroll);

	var _DragScrollDelta2 = _interopRequireDefault(_DragScrollDelta);

	var _DragShim2 = _interopRequireDefault(_DragShim);

	var _EventHandler2 = _interopRequireDefault(_EventHandler);

	var _Position2 = _interopRequireDefault(_Position);

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

	var Drag = (function (_Attribute) {
		_inherits(Drag, _Attribute);

		function Drag(opt_config) {
			_classCallCheck(this, Drag);

			var _this = _possibleConstructorReturn(this, _Attribute.call(this, opt_config));

			_this.activeDragPlaceholder_ = null;
			_this.activeDragSource_ = null;
			_this.distanceDragged_ = 0;
			_this.dragging_ = false;
			_this.dragHandler_ = new _EventHandler2.default();
			_this.dragScrollDelta_ = new _DragScrollDelta2.default();
			_this.mousePos_ = null;
			_this.mouseSourceDelta_ = null;
			_this.sourceHandler_ = new _EventHandler2.default();
			_this.sourceRegion_ = null;
			_this.sourceRelativePos_ = null;

			_this.attachSourceEvents_();

			_this.on(Drag.Events.DRAG, _this.defaultDragFn_, true);

			_this.on(Drag.Events.END, _this.defaultEndFn_, true);

			_this.on('sourcesChanged', _this.handleSourcesChanged_.bind(_this));

			_this.dragScrollDelta_.on('scrollDelta', _this.handleScrollDelta_.bind(_this));

			_dom2.default.on(document, 'keydown', _this.handleKeyDown_.bind(_this));

			return _this;
		}

		Drag.prototype.attachSourceEvents_ = function attachSourceEvents_() {
			var toAttach = {
				keydown: this.handleSourceKeyDown_.bind(this),
				mousedown: this.handleDragStartEvent_.bind(this),
				touchstart: this.handleDragStartEvent_.bind(this)
			};
			var eventTypes = Object.keys(toAttach);

			for (var i = 0; i < eventTypes.length; i++) {
				var listenerFn = toAttach[eventTypes[i]];

				if (_core2.default.isString(this.sources)) {
					this.sourceHandler_.add(_dom2.default.delegate(this.container, eventTypes[i], this.sources, listenerFn));
				} else {
					this.sourceHandler_.add(_dom2.default.on(this.sources, eventTypes[i], listenerFn));
				}
			}
		};

		Drag.prototype.buildEventObject_ = function buildEventObject_() {
			return {
				placeholder: this.activeDragPlaceholder_,
				source: this.activeDragSource_,
				relativeX: this.sourceRelativePos_.x,
				relativeY: this.sourceRelativePos_.y,
				x: this.sourceRegion_.left,
				y: this.sourceRegion_.top
			};
		};

		Drag.prototype.calculateInitialPosition_ = function calculateInitialPosition_(event) {
			this.sourceRegion_ = _object2.default.mixin({}, _Position2.default.getRegion(this.activeDragSource_, true));
			this.sourceRelativePos_ = {
				x: this.activeDragSource_.offsetLeft,
				y: this.activeDragSource_.offsetTop
			};

			if (_core2.default.isDef(event.clientX)) {
				this.mousePos_ = {
					x: event.clientX,
					y: event.clientY
				};
				this.mouseSourceDelta_ = {
					x: this.sourceRegion_.left - this.mousePos_.x,
					y: this.sourceRegion_.top - this.mousePos_.y
				};
			}
		};

		Drag.prototype.canStartDrag_ = function canStartDrag_(event) {
			return !this.disabled && (!_core2.default.isDef(event.button) || event.button === 0) && !this.isDragging() && this.isWithinHandle_(event.target);
		};

		Drag.prototype.cleanUpAfterDragging_ = function cleanUpAfterDragging_() {
			if (this.activeDragPlaceholder_) {
				this.activeDragPlaceholder_.setAttribute('aria-grabbed', 'false');

				_dom2.default.removeClasses(this.activeDragPlaceholder_, this.draggingClass);

				if (this.dragPlaceholder === Drag.Placeholder.CLONE) {
					_dom2.default.exitDocument(this.activeDragPlaceholder_);
				}
			}

			this.activeDragPlaceholder_ = null;
			this.activeDragSource_ = null;
			this.sourceRegion_ = null;
			this.sourceRelativePos_ = null;
			this.mousePos_ = null;
			this.mouseSourceDelta_ = null;
			this.dragging_ = false;
			this.dragHandler_.removeAllListeners();
		};

		Drag.prototype.cloneActiveDrag_ = function cloneActiveDrag_() {
			var placeholder = this.activeDragSource_.cloneNode(true);
			placeholder.style.position = 'absolute';
			placeholder.style.left = this.sourceRelativePos_.x + 'px';
			placeholder.style.top = this.sourceRelativePos_.y + 'px';

			_dom2.default.append(this.activeDragSource_.parentNode, placeholder);

			return placeholder;
		};

		Drag.prototype.constrain_ = function constrain_(region) {
			this.constrainToAxis_(region);
			this.constrainToSteps_(region);
			this.constrainToRegion_(region);
		};

		Drag.prototype.constrainToAxis_ = function constrainToAxis_(region) {
			if (this.axis === 'x') {
				region.top = this.sourceRegion_.top;
				region.bottom = this.sourceRegion_.bottom;
			} else if (this.axis === 'y') {
				region.left = this.sourceRegion_.left;
				region.right = this.sourceRegion_.right;
			}
		};

		Drag.prototype.constrainToRegion_ = function constrainToRegion_(region) {
			var constrain = this.constrain;

			if (constrain) {
				if (_core2.default.isElement(constrain)) {
					constrain = _Position2.default.getRegion(constrain, true);
				}

				if (region.left < constrain.left) {
					region.left = constrain.left;
				} else if (region.right > constrain.right) {
					region.left -= region.right - constrain.right;
				}

				if (region.top < constrain.top) {
					region.top = constrain.top;
				} else if (region.bottom > constrain.bottom) {
					region.top -= region.bottom - constrain.bottom;
				}

				region.right = region.left + region.width;
				region.bottom = region.top + region.height;
			}
		};

		Drag.prototype.constrainToSteps_ = function constrainToSteps_(region) {
			var deltaX = region.left - this.sourceRegion_.left;
			var deltaY = region.top - this.sourceRegion_.top;
			region.left -= deltaX % this.steps.x;
			region.right = region.left + region.width;
			region.top -= deltaY % this.steps.y;
			region.bottom = region.top + region.height;
		};

		Drag.prototype.createActiveDragPlaceholder_ = function createActiveDragPlaceholder_() {
			var dragPlaceholder = this.dragPlaceholder;

			if (dragPlaceholder === Drag.Placeholder.CLONE) {
				this.activeDragPlaceholder_ = this.cloneActiveDrag_();
			} else if (_core2.default.isElement(dragPlaceholder)) {
				this.activeDragPlaceholder_ = dragPlaceholder;
			} else {
				this.activeDragPlaceholder_ = this.activeDragSource_;
			}
		};

		Drag.prototype.defaultDragFn_ = function defaultDragFn_() {
			this.moveToPosition_(this.activeDragPlaceholder_);
		};

		Drag.prototype.defaultEndFn_ = function defaultEndFn_() {
			this.moveToPosition_(this.activeDragSource_);
		};

		Drag.prototype.disposeInternal = function disposeInternal() {
			this.cleanUpAfterDragging_();
			this.dragHandler_ = null;
			this.dragScrollDelta_.dispose();
			this.dragScrollDelta_ = null;
			this.sourceHandler_.removeAllListeners();
			this.sourceHandler_ = null;

			_Attribute.prototype.disposeInternal.call(this);
		};

		Drag.prototype.getActiveDrag = function getActiveDrag() {
			return this.activeDragSource_;
		};

		Drag.prototype.handleDragEndEvent_ = function handleDragEndEvent_() {
			if (this.autoScroll) {
				this.autoScroll.stop();
			}

			this.dragScrollDelta_.stop();

			_DragShim2.default.hideDocShim();

			this.emit(Drag.Events.END, this.buildEventObject_());
			this.cleanUpAfterDragging_();
		};

		Drag.prototype.handleDragMoveEvent_ = function handleDragMoveEvent_(event) {
			var position = event.targetTouches ? event.targetTouches[0] : event;
			var distanceX = position.clientX - this.mousePos_.x;
			var distanceY = position.clientY - this.mousePos_.y;
			this.mousePos_.x = position.clientX;
			this.mousePos_.y = position.clientY;

			if (!this.isDragging() && !this.hasReachedMinimumDistance_(distanceX, distanceY)) {
				return;
			}

			if (!this.isDragging()) {
				this.startDragging_();
				this.dragScrollDelta_.start(this.activeDragPlaceholder_, this.scrollContainers);
			}

			if (this.autoScroll) {
				this.autoScroll.scroll(this.scrollContainers, this.mousePos_.x, this.mousePos_.y);
			}

			this.updatePositionFromMouse();
		};

		Drag.prototype.handleDragStartEvent_ = function handleDragStartEvent_(event) {
			this.activeDragSource_ = event.delegateTarget || event.currentTarget;

			if (this.canStartDrag_(event)) {
				this.calculateInitialPosition_(event.targetTouches ? event.targetTouches[0] : event);
				event.preventDefault();

				if (event.type === 'keydown') {
					this.startDragging_();
				} else {
					this.dragHandler_.add.apply(this.dragHandler_, _DragShim2.default.attachDocListeners(this.useShim, {
						mousemove: this.handleDragMoveEvent_.bind(this),
						touchmove: this.handleDragMoveEvent_.bind(this),
						mouseup: this.handleDragEndEvent_.bind(this),
						touchend: this.handleDragEndEvent_.bind(this)
					}));
					this.distanceDragged_ = 0;
				}
			}
		};

		Drag.prototype.handleKeyDown_ = function handleKeyDown_(event) {
			if (event.keyCode === 27 && this.isDragging()) {
				this.handleDragEndEvent_();
			}
		};

		Drag.prototype.handleScrollDelta_ = function handleScrollDelta_(event) {
			this.mouseSourceDelta_.x += event.deltaX;
			this.mouseSourceDelta_.y += event.deltaY;
			this.updatePositionFromMouse();
		};

		Drag.prototype.handleSourceKeyDown_ = function handleSourceKeyDown_(event) {
			if (this.isDragging()) {
				var currentTarget = event.delegateTarget || event.currentTarget;

				if (currentTarget !== this.activeDragSource_) {
					return;
				}

				if (event.keyCode >= 37 && event.keyCode <= 40) {
					var deltaX = 0;
					var deltaY = 0;
					var speedX = this.keyboardSpeed >= this.steps.x ? this.keyboardSpeed : this.steps.x;
					var speedY = this.keyboardSpeed >= this.steps.y ? this.keyboardSpeed : this.steps.y;

					if (event.keyCode === 37) {
						deltaX -= speedX;
					} else if (event.keyCode === 38) {
						deltaY -= speedY;
					} else if (event.keyCode === 39) {
						deltaX += speedX;
					} else {
						deltaY += speedY;
					}

					this.updatePositionFromDelta(deltaX, deltaY);
					event.preventDefault();
				} else if (event.keyCode === 13 || event.keyCode === 32 || event.keyCode === 27) {
					this.handleDragEndEvent_();
				}
			} else if (event.keyCode === 13 || event.keyCode === 32) {
				this.handleDragStartEvent_(event);
			}
		};

		Drag.prototype.handleSourcesChanged_ = function handleSourcesChanged_() {
			this.sourceHandler_.removeAllListeners();
			this.attachSourceEvents_();
		};

		Drag.prototype.hasReachedMinimumDistance_ = function hasReachedMinimumDistance_(distanceX, distanceY) {
			this.distanceDragged_ += Math.abs(distanceX) + Math.abs(distanceY);
			return this.distanceDragged_ >= this.minimumDragDistance;
		};

		Drag.prototype.isDragging = function isDragging() {
			return this.dragging_;
		};

		Drag.prototype.isWithinHandle_ = function isWithinHandle_(element) {
			var handles = this.handles;

			if (!handles) {
				return true;
			} else if (_core2.default.isString(handles)) {
				return _dom2.default.match(element, handles + ', ' + handles + ' *');
			} else {
				return _dom2.default.contains(handles, element);
			}
		};

		Drag.prototype.moveToPosition_ = function moveToPosition_(element) {
			element.style.left = this.sourceRelativePos_.x + 'px';
			element.style.top = this.sourceRelativePos_.y + 'px';
		};

		Drag.prototype.setterAutoScrollFn_ = function setterAutoScrollFn_(val) {
			if (val !== false) {
				return new _DragAutoScroll2.default(val);
			}
		};

		Drag.prototype.setterConstrainFn = function setterConstrainFn(val) {
			if (_core2.default.isString(val)) {
				val = _dom2.default.toElement(val);
			}

			return val;
		};

		Drag.prototype.setterScrollContainersFn_ = function setterScrollContainersFn_(scrollContainers) {
			var elements = this.toElements_(scrollContainers);
			elements.push(document);
			return elements;
		};

		Drag.prototype.startDragging_ = function startDragging_() {
			this.dragging_ = true;
			this.createActiveDragPlaceholder_();

			_dom2.default.addClasses(this.activeDragPlaceholder_, this.draggingClass);

			this.activeDragPlaceholder_.setAttribute('aria-grabbed', 'true');
		};

		Drag.prototype.toElements_ = function toElements_(elementOrSelector) {
			if (_core2.default.isString(elementOrSelector)) {
				var matched = this.container.querySelectorAll(elementOrSelector);
				return Array.prototype.slice.call(matched, 0);
			} else if (elementOrSelector) {
				return [elementOrSelector];
			} else {
				return [];
			}
		};

		Drag.prototype.updatePosition = function updatePosition(newRegion) {
			this.constrain_(newRegion);
			var deltaX = newRegion.left - this.sourceRegion_.left;
			var deltaY = newRegion.top - this.sourceRegion_.top;

			if (deltaX !== 0 || deltaY !== 0) {
				this.sourceRegion_ = newRegion;
				this.sourceRelativePos_.x += deltaX;
				this.sourceRelativePos_.y += deltaY;
				this.emit(Drag.Events.DRAG, this.buildEventObject_());
			}
		};

		Drag.prototype.updatePositionFromDelta = function updatePositionFromDelta(deltaX, deltaY) {
			var newRegion = _object2.default.mixin({}, this.sourceRegion_);

			newRegion.left += deltaX;
			newRegion.right += deltaX;
			newRegion.top += deltaY;
			newRegion.bottom += deltaY;
			this.updatePosition(newRegion);
		};

		Drag.prototype.updatePositionFromMouse = function updatePositionFromMouse() {
			var newRegion = {
				height: this.sourceRegion_.height,
				left: this.mousePos_.x + this.mouseSourceDelta_.x,
				top: this.mousePos_.y + this.mouseSourceDelta_.y,
				width: this.sourceRegion_.width
			};
			newRegion.right = newRegion.left + newRegion.width;
			newRegion.bottom = newRegion.top + newRegion.height;
			this.updatePosition(newRegion);
		};

		Drag.prototype.validateElementOrString_ = function validateElementOrString_(val) {
			return _core2.default.isString(val) || _core2.default.isElement(val);
		};

		Drag.prototype.validatorConstrainFn = function validatorConstrainFn(val) {
			return _core2.default.isString(val) || _core2.default.isObject(val);
		};

		return Drag;
	})(_Attribute3.default);

	Drag.prototype.registerMetalComponent && Drag.prototype.registerMetalComponent(Drag, 'Drag')
	Drag.ATTRS = {
		autoScroll: {
			setter: 'setterAutoScrollFn_',
			value: false,
			writeOnce: true
		},
		axis: {
			validator: _core2.default.isString
		},
		constrain: {
			setter: 'setterConstrainFn',
			validator: 'validatorConstrainFn'
		},
		container: {
			setter: _dom2.default.toElement,
			validator: 'validateElementOrString_',
			value: document
		},
		disabled: {
			validator: _core2.default.isBoolean,
			value: false
		},
		draggingClass: {
			validator: _core2.default.isString,
			value: 'dragging'
		},
		dragPlaceholder: {
			validator: 'validateElementOrString_'
		},
		handles: {
			validator: 'validateElementOrString_'
		},
		keyboardSpeed: {
			validator: _core2.default.isNumber,
			value: 10
		},
		minimumDragDistance: {
			validator: _core2.default.isNumber,
			value: 5,
			writeOnce: true
		},
		scrollContainers: {
			setter: 'setterScrollContainersFn_',
			validator: 'validateElementOrString_'
		},
		sources: {
			validator: 'validateElementOrString_'
		},
		steps: {
			validator: _core2.default.isObject,
			valueFn: function valueFn() {
				return {
					x: 1,
					y: 1
				};
			}
		},
		useShim: {
			value: true
		}
	};
	Drag.Events = {
		DRAG: 'drag',
		END: 'end'
	};
	Drag.Placeholder = {
		CLONE: 'clone'
	};
	exports.default = Drag;
});
//# sourceMappingURL=Drag.js.map