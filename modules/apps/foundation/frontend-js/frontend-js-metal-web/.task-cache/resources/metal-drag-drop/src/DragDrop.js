define("frontend-js-metal-web@1.0.6/metal-drag-drop/src/DragDrop", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', './Drag', 'metal-position/src/all/position', 'metal-events/src/events'], function (exports, _metal, _dom, _Drag2, _position) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	var _Drag3 = _interopRequireDefault(_Drag2);

	var _position2 = _interopRequireDefault(_position);

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

	var DragDrop = function (_Drag) {
		_inherits(DragDrop, _Drag);

		/**
   * @inheritDoc
   */

		function DragDrop(opt_config) {
			_classCallCheck(this, DragDrop);

			var _this = _possibleConstructorReturn(this, _Drag.call(this, opt_config));

			/**
    * The currently active targets, that is, the ones that the dragged source is over.
    * @type {!Array<!Element>}
    * @protected
    */
			_this.activeTargets_ = [];
			return _this;
		}

		/**
   * Adds a target to this `DragDrop` instance.
   * @param {!Element} target
   */


		DragDrop.prototype.addTarget = function addTarget(target) {
			this.targets.push(target);
			this.targets = this.targets;
		};

		DragDrop.prototype.buildEventObject_ = function buildEventObject_() {
			var obj = _Drag.prototype.buildEventObject_.call(this);
			obj.target = this.activeTargets_[0];
			obj.allActiveTargets = this.activeTargets_;
			return obj;
		};

		DragDrop.prototype.cleanUpAfterDragging_ = function cleanUpAfterDragging_() {
			_Drag.prototype.cleanUpAfterDragging_.call(this);
			this.targets.forEach(function (target) {
				return target.removeAttribute('aria-dropeffect');
			});
			if (this.activeTargets_.length) {
				_dom2.default.removeClasses(this.activeTargets_[0], this.targetOverClass);
			}
			this.activeTargets_ = [];
		};

		DragDrop.prototype.findAllActiveTargets_ = function findAllActiveTargets_() {
			var activeTargets = [];
			var mainRegion;
			var sourceRegion = this.getSourceRegion_();
			var targets = this.targets;
			targets.forEach(function (target, index) {
				var region = _position2.default.getRegion(target);
				if (targets[index] !== this.activeDragPlaceholder_ && _position2.default.intersectRegion(region, sourceRegion)) {
					if (!mainRegion || _position2.default.insideRegion(mainRegion, region)) {
						activeTargets = [targets[index]].concat(activeTargets);
						mainRegion = region;
					} else {
						activeTargets.push(targets[index]);
					}
				}
			}.bind(this));
			return activeTargets;
		};

		DragDrop.prototype.getSourceRegion_ = function getSourceRegion_() {
			if (_metal.core.isDefAndNotNull(this.mousePos_)) {
				var x = this.mousePos_.x;
				var y = this.mousePos_.y;
				return _position2.default.makeRegion(y, 0, x, x, y, 0);
			} else {
				// We need to remove the scroll data from the region, since the other regions we'll
				// be comparing to won't take that information into account.
				var region = _metal.object.mixin({}, this.sourceRegion_);
				region.left -= document.body.scrollLeft;
				region.right -= document.body.scrollLeft;
				region.top -= document.body.scrollTop;
				region.bottom -= document.body.scrollTop;
				return region;
			}
		};

		DragDrop.prototype.removeTarget = function removeTarget(target) {
			_metal.array.remove(this.targets, target);
			this.targets = this.targets;
		};

		DragDrop.prototype.startDragging_ = function startDragging_() {
			var _this2 = this;

			if (this.ariaDropEffect) {
				this.targets.forEach(function (target) {
					return target.setAttribute('aria-dropeffect', _this2.ariaDropEffect);
				});
			}
			_Drag.prototype.startDragging_.call(this);
		};

		DragDrop.prototype.updatePosition = function updatePosition(deltaX, deltaY) {
			_Drag.prototype.updatePosition.call(this, deltaX, deltaY);

			var newTargets = this.findAllActiveTargets_();
			if (newTargets[0] !== this.activeTargets_[0]) {
				if (this.activeTargets_[0]) {
					_dom2.default.removeClasses(this.activeTargets_[0], this.targetOverClass);
					this.emit(DragDrop.Events.TARGET_LEAVE, this.buildEventObject_());
				}

				this.activeTargets_ = newTargets;
				if (this.activeTargets_[0]) {
					_dom2.default.addClasses(this.activeTargets_[0], this.targetOverClass);
					this.emit(DragDrop.Events.TARGET_ENTER, this.buildEventObject_());
				}
			}
		};

		return DragDrop;
	}(_Drag3.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	DragDrop.STATE = {
		/**
   * The "aria-dropeffect" value to be set for all targets. If not set,
   * this html attribute will have to be set manually on the targets.
   * @type {string}
   */
		ariaDropEffect: {
			validator: _metal.core.isString
		},

		/**
   * The CSS class that should be added to drop targets when a source
   * is being dragged over them.
   * @type {string}
   * @default 'dropOver'
   */
		targetOverClass: {
			validator: _metal.core.isString,
			value: 'targetOver'
		},

		/**
   * Elements that the sources can be dropped on. Can be either a single
   * element or a selector for multiple elements.
   * @type {!Element|string}
   */
		targets: {
			setter: 'toElements_',
			validator: 'validateElementOrString_'
		}
	};

	/**
  * Holds the names of events that can be emitted by `DragDrop`.
  * @type {!Object}
  * @static
  */
	DragDrop.Events = {
		DRAG: 'drag',
		END: 'end',
		TARGET_ENTER: 'targetEnter',
		TARGET_LEAVE: 'targetLeave'
	};

	exports.default = DragDrop;
});
//# sourceMappingURL=DragDrop.js.map