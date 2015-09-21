define(
    "frontend-js-metal-web@1.0.0/metal-drag-drop/src/Drag",
    ['exports', 'module', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/object/object', 'metal/src/attribute/Attribute', 'metal-drag-drop/src/helpers/DragAutoScroll', 'metal-drag-drop/src/helpers/DragScrollDelta', 'metal-drag-drop/src/helpers/DragShim', 'metal/src/events/EventHandler', 'metal-position/src/Position'],
    function (exports, module, _metalSrcCore, _metalSrcDomDom, _metalSrcObjectObject, _metalSrcAttributeAttribute, _metalDragDropSrcHelpersDragAutoScroll, _metalDragDropSrcHelpersDragScrollDelta, _metalDragDropSrcHelpersDragShim, _metalSrcEventsEventHandler, _metalPositionSrcPosition) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _core = _interopRequireDefault(_metalSrcCore);

        var _dom = _interopRequireDefault(_metalSrcDomDom);

        var _object = _interopRequireDefault(_metalSrcObjectObject);

        var _Attribute2 = _interopRequireDefault(_metalSrcAttributeAttribute);

        var _DragAutoScroll = _interopRequireDefault(_metalDragDropSrcHelpersDragAutoScroll);

        var _DragScrollDelta = _interopRequireDefault(_metalDragDropSrcHelpersDragScrollDelta);

        var _DragShim = _interopRequireDefault(_metalDragDropSrcHelpersDragShim);

        var _EventHandler = _interopRequireDefault(_metalSrcEventsEventHandler);

        var _Position = _interopRequireDefault(_metalPositionSrcPosition);

        /**
      * Responsible for making elements draggable. Handles all the logic
      * for dragging elements. Dropping is handled by `DragDrop`.
      * @extends {Attribute}
      */

        var Drag = (function (_Attribute) {
            _inherits(Drag, _Attribute);

            /**
       * @inheritDoc
       */

            function Drag(opt_config) {
                _classCallCheck(this, Drag);

                _get(Object.getPrototypeOf(Drag.prototype), 'constructor', this).call(this, opt_config);

                /**
        * The drag placeholder that is active at the moment.
        * @type {Element}
        * @protected
        */
                this.activeDragPlaceholder_ = null;

                /**
        * The drag source that is active at the moment.
        * @type {Element}
        * @protected
        */
                this.activeDragSource_ = null;

                /**
        * The current x position of the mouse (or null if not dragging).
        * @type {?number}
        * @protected
        */
                this.currentMouseX_ = null;

                /**
        * The current y position of the mouse (or null if not dragging).
        * @type {?number}
        * @protected
        */
                this.currentMouseY_ = null;

                /**
        * The current region values of the element being dragged, relative to
        * the document (or null if not dragging).
        * @type {Object}
        * @protected
        */
                this.currentSourceRegion_ = null;

                /**
        * The current x position of the element being dragged relative to its
        * `offsetParent`, or to the viewport if there's no `offsetParent`
        * (or null if not dragging).
        * @type {?number}
        * @protected
        */
                this.currentSourceRelativeX_ = null;

                /**
        * The current y position of the element being dragged relative to its
        * `offsetParent`, or to the viewport if there's no `offsetParent`
        * (or null if not dragging).
        * @type {?number}
        * @protected
        */
                this.currentSourceRelativeY_ = null;

                /**
        * The distance that has been dragged.
        * @type {number}
        * @protected
        */
                this.distanceDragged_ = 0;

                /**
        * Flag indicating if one of the sources are being dragged.
        * @type {boolean}
        * @protected
        */
                this.dragging_ = false;

                /**
        * The `EventHandler` instance that holds events that keep track of the drag action.
        * @type {!EventHandler}
        * @protected
        */
                this.dragHandler_ = new _EventHandler['default']();

                /**
        * `DragScrollDelta` instance.
        * @type {!DragScrollDelta}
        * @protected
        */
                this.dragScrollDelta_ = new _DragScrollDelta['default']();

                /**
        * The `EventHandler` instance that holds events for the source (or sources).
        * @type {!EventHandler}
        * @protected
        */
                this.sourceHandler_ = new _EventHandler['default']();

                this.attachSourceEvents_();
                this.on(Drag.Events.DRAG, this.defaultDragFn_, true);
                this.on(Drag.Events.END, this.defaultEndFn_, true);
                this.on('sourcesChanged', this.handleSourcesChanged_.bind(this));
                this.dragScrollDelta_.on('scrollDelta', this.handleScrollDelta_.bind(this));
                _dom['default'].on(document, 'keydown', this.handleKeyDown_.bind(this));
            }

            /**
       * Attributes definition.
       * @type {!Object}
       * @static
       */

            /**
       * Attaches the necessary events to the source (or sources).
       * @protected
       */

            _createClass(Drag, [{
                key: 'attachSourceEvents_',
                value: function attachSourceEvents_() {
                    var toAttach = {
                        keydown: this.handleSourceKeyDown_.bind(this),
                        mousedown: this.handleDragStartEvent_.bind(this),
                        touchstart: this.handleDragStartEvent_.bind(this)
                    };
                    var eventTypes = Object.keys(toAttach);
                    for (var i = 0; i < eventTypes.length; i++) {
                        var listenerFn = toAttach[eventTypes[i]];
                        if (_core['default'].isString(this.sources)) {
                            this.sourceHandler_.add(_dom['default'].delegate(this.container, eventTypes[i], this.sources, listenerFn));
                        } else {
                            this.sourceHandler_.add(_dom['default'].on(this.sources, eventTypes[i], listenerFn));
                        }
                    }
                }

                /**
        * Builds the object with data to be passed to a drag event.
        * @return {!Object}
        * @protected
        */
            }, {
                key: 'buildEventObject_',
                value: function buildEventObject_() {
                    return {
                        placeholder: this.activeDragPlaceholder_,
                        source: this.activeDragSource_,
                        relativeX: this.currentSourceRelativeX_,
                        relativeY: this.currentSourceRelativeY_,
                        x: this.currentSourceRegion_.left,
                        y: this.currentSourceRegion_.top
                    };
                }

                /**
        * Calculates the initial positions for the drag action.
        * @param {!Event} event
        * @protected
        */
            }, {
                key: 'calculateInitialPosition_',
                value: function calculateInitialPosition_(event) {
                    this.currentMouseX_ = event.clientX;
                    this.currentMouseY_ = event.clientY;
                    this.currentSourceRegion_ = _object['default'].mixin({}, _Position['default'].getRegion(this.activeDragSource_, true));
                    this.currentSourceRelativeX_ = this.activeDragSource_.offsetLeft;
                    this.currentSourceRelativeY_ = this.activeDragSource_.offsetTop;
                }

                /**
        * Checks if the given event can start a drag operation.
        * @param {!Event} event
        * @return {boolean}
        * @protected
        */
            }, {
                key: 'canStartDrag_',
                value: function canStartDrag_(event) {
                    return !this.disabled && (!_core['default'].isDef(event.button) || event.button === 0) && !this.isDragging() && this.isWithinHandle_(event.target);
                }

                /**
        * Resets all variables to their initial values and detaches drag listeners.
        * @protected
        */
            }, {
                key: 'cleanUpAfterDragging_',
                value: function cleanUpAfterDragging_() {
                    if (this.activeDragPlaceholder_) {
                        this.activeDragPlaceholder_.setAttribute('aria-grabbed', 'false');
                        _dom['default'].removeClasses(this.activeDragPlaceholder_, this.draggingClass);
                        if (this.dragPlaceholder === Drag.Placeholder.CLONE) {
                            _dom['default'].exitDocument(this.activeDragPlaceholder_);
                        }
                    }
                    this.activeDragPlaceholder_ = null;
                    this.activeDragSource_ = null;
                    this.currentSourceRegion_ = null;
                    this.currentSourceRelativeX_ = null;
                    this.currentSourceRelativeY_ = null;
                    this.currentMouseX_ = null;
                    this.currentMouseY_ = null;
                    this.dragging_ = false;
                    this.dragHandler_.removeAllListeners();
                }

                /**
        * Clones the active drag source and adds the clone to the document.
        * @return {!Element}
        * @protected
        */
            }, {
                key: 'cloneActiveDrag_',
                value: function cloneActiveDrag_() {
                    var placeholder = this.activeDragSource_.cloneNode(true);
                    placeholder.style.position = 'absolute';
                    placeholder.style.left = this.currentSourceRelativeX_ + 'px';
                    placeholder.style.top = this.currentSourceRelativeY_ + 'px';
                    _dom['default'].append(this.activeDragSource_.parentNode, placeholder);
                    return placeholder;
                }

                /**
        * Constrains the given delta between the min/max values defined by the
        * constrain region.
        * @param {number} delta
        * @param {string} minKey The key for the min value in the region object.
        * @param {string} maxKey The key for the max value in the region object.
        * @return {number} The constrained delta.
        * @protected
        */
            }, {
                key: 'constrain_',
                value: function constrain_(delta, minKey, maxKey) {
                    var constrain = this.constrain;
                    if (constrain) {
                        if (_core['default'].isElement(constrain)) {
                            constrain = _Position['default'].getRegion(constrain, true);
                        }
                        delta = Math.max(delta, constrain[minKey] - this.currentSourceRegion_[minKey]);
                        delta = Math.min(delta, constrain[maxKey] - this.currentSourceRegion_[maxKey]);
                    }
                    return delta;
                }

                /**
        * Creates the active drag placeholder, unless it already exists.
        * @protected
        */
            }, {
                key: 'createActiveDragPlaceholder_',
                value: function createActiveDragPlaceholder_() {
                    var dragPlaceholder = this.dragPlaceholder;
                    if (dragPlaceholder === Drag.Placeholder.CLONE) {
                        this.activeDragPlaceholder_ = this.cloneActiveDrag_();
                    } else if (_core['default'].isElement(dragPlaceholder)) {
                        this.activeDragPlaceholder_ = dragPlaceholder;
                    } else {
                        this.activeDragPlaceholder_ = this.activeDragSource_;
                    }
                }

                /**
        * The default behavior for the `Drag.Events.DRAG` event. Can be prevented
        * by calling the `preventDefault` function on the event's facade. Moves
        * the placeholder to the new calculated source position.
        * @protected
        */
            }, {
                key: 'defaultDragFn_',
                value: function defaultDragFn_() {
                    this.moveToPosition_(this.activeDragPlaceholder_);
                }

                /**
        * The default behavior for the `Drag.Events.END` event. Can be prevented
        * by calling the `preventDefault` function on the event's facade. Moves
        * the source element to the final calculated position.
        * @protected
        */
            }, {
                key: 'defaultEndFn_',
                value: function defaultEndFn_() {
                    this.moveToPosition_(this.activeDragSource_);
                }

                /**
        * @inheritDoc
        */
            }, {
                key: 'disposeInternal',
                value: function disposeInternal() {
                    this.cleanUpAfterDragging_();
                    this.dragHandler_ = null;
                    this.dragScrollDelta_.dispose();
                    this.dragScrollDelta_ = null;
                    this.sourceHandler_.removeAllListeners();
                    this.sourceHandler_ = null;
                    _get(Object.getPrototypeOf(Drag.prototype), 'disposeInternal', this).call(this);
                }

                /**
        * Gets the active drag source.
        * @return {Element}
        */
            }, {
                key: 'getActiveDrag',
                value: function getActiveDrag() {
                    return this.activeDragSource_;
                }

                /**
        * Handles events that can end a drag action, like "mouseup" and "touchend".
        * Triggered when the mouse drag action ends.
        * @protected
        */
            }, {
                key: 'handleDragEndEvent_',
                value: function handleDragEndEvent_() {
                    if (this.autoScroll) {
                        this.autoScroll.stop();
                    }
                    this.dragScrollDelta_.stop();
                    _DragShim['default'].hideDocShim();
                    this.emit(Drag.Events.END, this.buildEventObject_());
                    this.cleanUpAfterDragging_();
                }

                /**
        * Handles events that can move a draggable element, like "mousemove" and "touchmove".
        * Tracks the movement on the screen to update the drag action.
        * @param {!Event} event
        * @protected
        */
            }, {
                key: 'handleDragMoveEvent_',
                value: function handleDragMoveEvent_(event) {
                    var position = event.targetTouches ? event.targetTouches[0] : event;
                    var distanceX = position.clientX - this.currentMouseX_;
                    var distanceY = position.clientY - this.currentMouseY_;
                    this.currentMouseX_ = position.clientX;
                    this.currentMouseY_ = position.clientY;
                    if (!this.isDragging() && !this.hasReachedMinimumDistance_(distanceX, distanceY)) {
                        return;
                    }

                    if (!this.isDragging()) {
                        this.startDragging_();
                        this.dragScrollDelta_.start(this.activeDragPlaceholder_, this.scrollContainers);
                    }
                    if (this.autoScroll) {
                        this.autoScroll.scroll(this.scrollContainers, this.currentMouseX_, this.currentMouseY_);
                    }
                    this.updatePosition(distanceX, distanceY);
                }

                /**
        * Handles events that can start a drag action, like "mousedown" and "touchstart".
        * When this is triggered and the sources were not already being dragged, more
        * listeners will be attached to keep track of the drag action.
        * @param {!Event} event
        * @protected
        */
            }, {
                key: 'handleDragStartEvent_',
                value: function handleDragStartEvent_(event) {
                    this.activeDragSource_ = event.delegateTarget || event.currentTarget;

                    if (this.canStartDrag_(event)) {
                        this.calculateInitialPosition_(event.targetTouches ? event.targetTouches[0] : event);
                        event.preventDefault();
                        if (event.type === 'keydown') {
                            this.startDragging_();
                        } else {
                            this.dragHandler_.add.apply(this.dragHandler_, _DragShim['default'].attachDocListeners(this.useShim, {
                                mousemove: this.handleDragMoveEvent_.bind(this),
                                touchmove: this.handleDragMoveEvent_.bind(this),
                                mouseup: this.handleDragEndEvent_.bind(this),
                                touchend: this.handleDragEndEvent_.bind(this)
                            }));
                            this.distanceDragged_ = 0;
                        }
                    }
                }

                /**
        * Handles a `keydown` event on the document. Ends the drag if ESC was the pressed key.
        * @param {!Event} event
        * @protected
        */
            }, {
                key: 'handleKeyDown_',
                value: function handleKeyDown_(event) {
                    if (event.keyCode === 27 && this.isDragging()) {
                        this.handleDragEndEvent_();
                    }
                }

                /**
        * Handles a "scrollDelta" event. Updates the position data for the source,
        * as well as the placeholder's position on the screen when "move" is set to true.
        * @param {!Object} event [description]
        * @protected
        */
            }, {
                key: 'handleScrollDelta_',
                value: function handleScrollDelta_(event) {
                    this.updatePosition(event.deltaX, event.deltaY);
                }

                /**
        * Handles a `keydown` event from `KeyboardDrag`. Does the appropriate drag action
        * for the pressed key.
        * @param {!Object} event
        * @protected
        */
            }, {
                key: 'handleSourceKeyDown_',
                value: function handleSourceKeyDown_(event) {
                    if (this.isDragging()) {
                        var currentTarget = event.delegateTarget || event.currentTarget;
                        if (currentTarget !== this.activeDragSource_) {
                            return;
                        }
                        if (event.keyCode >= 37 && event.keyCode <= 40) {
                            // Arrow keys during drag move the source.
                            var deltaX = 0;
                            var deltaY = 0;
                            if (event.keyCode === 37) {
                                deltaX -= this.keyboardSpeed;
                            } else if (event.keyCode === 38) {
                                deltaY -= this.keyboardSpeed;
                            } else if (event.keyCode === 39) {
                                deltaX += this.keyboardSpeed;
                            } else {
                                deltaY += this.keyboardSpeed;
                            }
                            this.updatePosition(deltaX, deltaY);
                            event.preventDefault();
                        } else if (event.keyCode === 13 || event.keyCode === 32 || event.keyCode === 27) {
                            // Enter, space or esc during drag will end it.
                            this.handleDragEndEvent_();
                        }
                    } else if (event.keyCode === 13 || event.keyCode === 32) {
                        // Enter or space will start the drag action.
                        this.handleDragStartEvent_(event);
                    }
                }

                /**
        * Triggers when the `sources` attribute changes. Detaches events attached to the
        * previous sources and attaches them to the new value instead.
        * @protected
        */
            }, {
                key: 'handleSourcesChanged_',
                value: function handleSourcesChanged_() {
                    this.sourceHandler_.removeAllListeners();
                    this.attachSourceEvents_();
                }

                /**
        * Checks if the minimum distance for dragging has been reached after
        * adding the given values.
        * @param {number} distanceX
        * @param {number} distanceY
        * @return {boolean}
        * @protected
        */
            }, {
                key: 'hasReachedMinimumDistance_',
                value: function hasReachedMinimumDistance_(distanceX, distanceY) {
                    this.distanceDragged_ += Math.abs(distanceX) + Math.abs(distanceY);
                    return this.distanceDragged_ >= this.minimumDragDistance;
                }

                /**
        * Checks if one of the sources are being dragged.
        * @return {boolean}
        */
            }, {
                key: 'isDragging',
                value: function isDragging() {
                    return this.dragging_;
                }

                /**
        * Checks if the given element is within a valid handle.
        * @param {!Element} element
        * @protected
        */
            }, {
                key: 'isWithinHandle_',
                value: function isWithinHandle_(element) {
                    var handles = this.handles;
                    if (!handles) {
                        return true;
                    } else if (_core['default'].isString(handles)) {
                        return _dom['default'].match(element, handles + ', ' + handles + ' *');
                    } else {
                        return _dom['default'].contains(handles, element);
                    }
                }

                /**
        * Moves the given element to the current source coordinates.
        * @param {!Element} element
        * @protected
        */
            }, {
                key: 'moveToPosition_',
                value: function moveToPosition_(element) {
                    element.style.left = this.currentSourceRelativeX_ + 'px';
                    element.style.top = this.currentSourceRelativeY_ + 'px';
                }

                /**
        * Setter for the `autoScroll` attribute.
        * @param {*} val
        * @return {!DragAutoScroll}
        */
            }, {
                key: 'setterAutoScrollFn_',
                value: function setterAutoScrollFn_(val) {
                    if (val !== false) {
                        return new _DragAutoScroll['default'](val);
                    }
                }

                /**
        * Setter for the `constrain` attribute.
        * @param {!Element|Object|string} val
        * @return {!Element|Object}
        * @protected
        */
            }, {
                key: 'setterConstrainFn',
                value: function setterConstrainFn(val) {
                    if (_core['default'].isString(val)) {
                        val = _dom['default'].toElement(val);
                    }
                    return val;
                }

                /**
        * Sets the `scrollContainers` attribute.
        * @param {Element|string} scrollContainers
        * @return {!Array<!Element>}
        * @protected
        */
            }, {
                key: 'setterScrollContainersFn_',
                value: function setterScrollContainersFn_(scrollContainers) {
                    var elements = this.toElements_(scrollContainers);
                    elements.push(document);
                    return elements;
                }

                /**
        * Starts dragging the selected source.
        * @protected
        */
            }, {
                key: 'startDragging_',
                value: function startDragging_() {
                    this.dragging_ = true;
                    this.createActiveDragPlaceholder_();
                    _dom['default'].addClasses(this.activeDragPlaceholder_, this.draggingClass);
                    this.activeDragPlaceholder_.setAttribute('aria-grabbed', 'true');
                }

                /**
        * Converts the given element or selector into an array of elements.
        * @param {Element|string} elementOrSelector
        * @return {!Array<!Element>}
        * @protected
        */
            }, {
                key: 'toElements_',
                value: function toElements_(elementOrSelector) {
                    if (_core['default'].isString(elementOrSelector)) {
                        var matched = this.container.querySelectorAll(elementOrSelector);
                        return Array.prototype.slice.call(matched, 0);
                    } else if (elementOrSelector) {
                        return [elementOrSelector];
                    } else {
                        return [];
                    }
                }

                /**
        * Updates the dragged element's position, moving its placeholder if `move`
        * is set to true.
        * @param {number} deltaX
        * @param {number} deltaY
        */
            }, {
                key: 'updatePosition',
                value: function updatePosition(deltaX, deltaY) {
                    if (this.axis === 'x') {
                        deltaY = 0;
                    } else if (this.axis === 'y') {
                        deltaX = 0;
                    }
                    deltaX = this.constrain_(deltaX, 'left', 'right');
                    deltaY = this.constrain_(deltaY, 'top', 'bottom');

                    if (deltaX !== 0 || deltaY !== 0) {
                        this.currentSourceRegion_.left += deltaX;
                        this.currentSourceRegion_.right += deltaX;
                        this.currentSourceRegion_.top += deltaY;
                        this.currentSourceRegion_.bottom += deltaY;

                        this.currentSourceRelativeX_ += deltaX;
                        this.currentSourceRelativeY_ += deltaY;
                        this.emit(Drag.Events.DRAG, this.buildEventObject_());
                    }
                }

                /**
        * Validates the given value, making sure that it's either an element or a string.
        * @param {*} val
        * @return {boolean}
        * @protected
        */
            }, {
                key: 'validateElementOrString_',
                value: function validateElementOrString_(val) {
                    return _core['default'].isString(val) || _core['default'].isElement(val);
                }

                /**
        * Validates the value of the `constrain` attribute.
        * @param {*} val
        * @return {boolean}
        * @protected
        */
            }, {
                key: 'validatorConstrainFn',
                value: function validatorConstrainFn(val) {
                    return _core['default'].isString(val) || _core['default'].isObject(val);
                }
            }]);

            return Drag;
        })(_Attribute2['default']);

        Drag.ATTRS = {
            /**
       * Configuration object for the `DragAutoScroll` instance that will be used for
       * automatically scrolling the elements in `scrollContainers` during drag when
       * the mouse is near their boundaries. If set to `false`, auto scrolling will be
       * disabled (default).
       * @type {!Object|boolean}
       * @default false
       */
            autoScroll: {
                setter: 'setterAutoScrollFn_',
                value: false,
                writeOnce: true
            },

            /**
       * The axis that allows dragging. Can be set to just x, just y or both (default).
       * @type {string}
       */
            axis: {
                validator: _core['default'].isString
            },

            /**
       * Object with the boundaries, that the dragged element should not leave
       * while being dragged. If not set, the element is free to be dragged
       * to anywhere on the page. Can be either already an object with the
       * boundaries relative to the document, or an element to use the boundaries
       * from, or even a selector for finding that element.
       * @type {!Element|Object|string}
       */
            constrain: {
                setter: 'setterConstrainFn',
                validator: 'validatorConstrainFn'
            },

            /**
       * An element that contains all sources, targets and scroll containers. This
       * will be used when delegate events are attached or when looking for elements
       * by selector. Defaults to `document`.
       * @type {!Element|string}
       * @default document
       */
            container: {
                setter: _dom['default'].toElement,
                validator: 'validateElementOrString_',
                value: document
            },

            /**
       * Flag indicating if drag operations are disabled. When set to true, it
       * dragging won't work.
       * @type {boolean}
       * @default false
       */
            disabled: {
                validator: _core['default'].isBoolean,
                value: false
            },

            /**
       * The CSS class that should be added to the node being dragged.
       * @type {string}
       * @default 'dragging'
       */
            draggingClass: {
                validator: _core['default'].isString,
                value: 'dragging'
            },

            /**
       * The placeholder element that should be moved during drag. Can be either
       * an element or the "clone" string, indicating that a clone of the source
       * being dragged should be used. If nothing is set, the original source element
       * will be used.
       * @type {Element|?string}
       */
            dragPlaceholder: {
                validator: 'validateElementOrString_'
            },

            /**
       * Elements inside the source that should be the drag handles. Can be
       * either a single element or a selector for multiple elements.
       * @type {Element|?string}
       */
            handles: {
                validator: 'validateElementOrString_'
            },

            /**
       * The number of pixels that the source should move when dragged via
       * the keyboard controls.
       * @default 10
       */
            keyboardSpeed: {
                validator: _core['default'].isNumber,
                value: 10
            },

            /**
       * The minimum distance, in pixels, that the mouse needs to move before
       * the action is considered a drag.
       * @type {number}
       * @default 5
       */
            minimumDragDistance: {
                validator: _core['default'].isNumber,
                value: 5,
                writeOnce: true
            },

            /**
       * Elements with scroll, besides the document, that contain any of the given
       * sources. Can be either a single element or a selector for multiple elements.
       * @type {Element|string}
       */
            scrollContainers: {
                setter: 'setterScrollContainersFn_',
                validator: 'validateElementOrString_'
            },

            /**
       * Elements that should be draggable. Can be either a single element
       * or a selector for multiple elements.
       * @type {!Element|string}
       */
            sources: {
                validator: 'validateElementOrString_'
            },

            /**
       * Flag indicating if a shim should be used for capturing document events.
       * This is important for allowing dragging nodes over iframes. If false,
       * events will be listened in the document itself instead.
       * @type {boolean}
       * @default true
       */
            useShim: {
                value: true
            }
        };

        /**
      * Holds the names of events that can be emitted by `Drag`.
      * @type {!Object}
      * @static
      */
        Drag.Events = {
            DRAG: 'drag',
            END: 'end'
        };

        /**
      * Holds the values that can be passed to the `dragPlaceholder` attribute.
      * @type {!Object}
      * @static
      */
        Drag.Placeholder = {
            CLONE: 'clone'
        };

        module.exports = Drag;
    }
);