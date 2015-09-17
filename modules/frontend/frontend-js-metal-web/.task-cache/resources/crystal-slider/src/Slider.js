define(
    "frontend-js-metal-web@1.0.0/crystal-slider/src/Slider",
    ['exports', 'module', 'metal/src/core', 'metal/src/component/ComponentRegistry', 'metal-drag-drop/src/Drag', 'metal-position/src/Position', 'metal/src/soy/SoyComponent', 'crystal-slider/src/Slider.soy', 'metal-jquery-adapter/src/JQueryAdapter'],
    function (exports, module, _metalSrcCore, _metalSrcComponentComponentRegistry, _metalDragDropSrcDrag, _metalPositionSrcPosition, _metalSrcSoySoyComponent, _crystalSliderSrcSliderSoy, _metalJqueryAdapterSrcJQueryAdapter) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _core = _interopRequireDefault(_metalSrcCore);

        var _ComponentRegistry = _interopRequireDefault(_metalSrcComponentComponentRegistry);

        var _Drag = _interopRequireDefault(_metalDragDropSrcDrag);

        var _Position = _interopRequireDefault(_metalPositionSrcPosition);

        var _SoyComponent2 = _interopRequireDefault(_metalSrcSoySoyComponent);

        var _JQueryAdapter = _interopRequireDefault(_metalJqueryAdapterSrcJQueryAdapter);

        /**
      * Slider component.
      */

        var Slider = (function (_SoyComponent) {
            _inherits(Slider, _SoyComponent);

            /**
       * @inheritDoc
       */

            function Slider(opt_config) {
                _classCallCheck(this, Slider);

                _get(Object.getPrototypeOf(Slider.prototype), 'constructor', this).call(this, opt_config);

                /**
        * Map of different slider DOM elements. Used as a cache to prevent unnecessary dom lookups
        * on succesive queries.
        * @type {Map}
        * @protected
        */
                this.elements_ = new Map();
            }

            /**
       * @inheritDoc
       */

            _createClass(Slider, [{
                key: 'attached',
                value: function attached() {
                    /**
         * Manages dragging the rail handle to update the slider value.
         * @type {Drag}
         * @protected
         */
                    this.drag_ = new _Drag['default']({
                        constrain: this.getElement_('.rail'),
                        handles: this.getElement_('.handle'),
                        sources: this.getElement_('.rail-handle')
                    });

                    /**
         * Position and dimensions of the slider element.
         * @type {DOMRect}
         * @protected
         */
                    this.elementRegion_ = _Position['default'].getRegion(this.element);

                    this.attachDragEvents_();
                }

                /**
        * Attaches the drag events to handle value updates when dragging the rail handle.
        * protected
        */
            }, {
                key: 'attachDragEvents_',
                value: function attachDragEvents_() {
                    this.drag_.on(_Drag['default'].Events.DRAG, this.updateValueFromDragData_.bind(this));
                    this.drag_.on(_Drag['default'].Events.END, this.updateValueFromDragData_.bind(this));
                }

                /**
        * @inheritDoc
        */
            }, {
                key: 'disposeInternal',
                value: function disposeInternal() {
                    _get(Object.getPrototypeOf(Slider.prototype), 'disposeInternal', this).call(this);

                    this.drag_.dispose();
                    this.elements_ = null;
                    this.elementRegion_ = null;
                }

                /**
        * Returns a DOM element inside the slider component based on a selector query.
        * @param {string} query Query selector matching the desired element inside the Slider.
        * @return {Element} The slider element, or null if none was found.
        * @protected
        */
            }, {
                key: 'getElement_',
                value: function getElement_(query) {
                    var element = this.elements_.get(query);

                    if (!element) {
                        element = this.element.querySelector(query);

                        this.elements_.set(query, element);
                    }

                    return element;
                }

                /**
        * Handles mouse down actions on the slider rail and updates the slider value accordingly.
        * @param {!Event} event
        * @protected
        */
            }, {
                key: 'onRailMouseDown_',
                value: function onRailMouseDown_(event) {
                    if (event.target === this.getElement_('.rail') || event.target === this.getElement_('.rail-active')) {
                        this.updateValue_(event.offsetX, 0);
                    }
                }

                /**
        * Synchronizes the slider UI with the max attribute.
        * @param {number} newVal The new value of the attribute.
        */
            }, {
                key: 'syncMax',
                value: function syncMax(newVal) {
                    if (newVal < this.value) {
                        this.value = newVal;
                    } else {
                        this.updateHandlePosition_();
                    }
                }

                /**
        * Synchronizes the slider UI with the min attribute.
        * @param {number} newVal The new value of the attribute.
        */
            }, {
                key: 'syncMin',
                value: function syncMin(newVal) {
                    if (newVal > this.value) {
                        this.value = newVal;
                    } else {
                        this.updateHandlePosition_();
                    }
                }

                /**
        * Synchronizes the slider UI with the value attribute.
        * @param {number} newVal The new value of the attribute.
        */
            }, {
                key: 'syncValue',
                value: function syncValue() {
                    this.updateHandlePosition_();
                }

                /**
        * Updates the handle position and active region to reflect the current slider value.
        * @protected
        */
            }, {
                key: 'updateHandlePosition_',
                value: function updateHandlePosition_() {
                    var positionValue = 100 * (this.value - this.min) / (this.max - this.min) + '%';

                    if (!(this.drag_ && this.drag_.isDragging())) {
                        this.getElement_('.rail-handle').style.left = positionValue;
                    }

                    this.getElement_('.rail-active').style.width = positionValue;
                }

                /**
        * Updates the slider value based on the UI state of the handle element.
        * @param {number} handlePosition Position of the handle in px.
        * @param {number} offset Offset to be added to normalize relative inputs.
        * @protected
        */
            }, {
                key: 'updateValue_',
                value: function updateValue_(handlePosition, offset) {
                    this.value = Math.round(offset + handlePosition / this.elementRegion_.width * (this.max - this.min));
                }

                /**
        * Handles Drag events from the rail handle and updates the slider value accordingly.
        * @param {!Object} data
        * @protected
        */
            }, {
                key: 'updateValueFromDragData_',
                value: function updateValueFromDragData_(data) {
                    this.updateValue_(data.relativeX, this.min);
                }
            }]);

            return Slider;
        })(_SoyComponent2['default']);

        Slider.ATTRS = {
            /**
       * Name of the hidden input field that holds the slider value. Useful when slider is embedded
       * inside a form so it can automatically send its value.
       * @type {string}
       */
            inputName: {
                validator: _core['default'].isString
            },

            /**
       * Defines the maximum value handled by the slider.
       * @type {number}
       * @default 100
       */
            max: {
                value: 100
            },

            /**
       * Defines the minimum value handled by the slider.
       * @type {number}
       * @default 0
       */
            min: {
                value: 0
            },

            /**
       * Defines the currently selected value on the slider.
       * @type {number}
       * @default 50
       */
            value: {
                validator: function validator(val) {
                    return _core['default'].isNumber(val) && this.min <= val && val <= this.max;
                },
                value: 80
            }
        };

        /**
      * Default slider elementClasses.
      * @default slider
      * @type {string}
      * @static
      */
        Slider.ELEMENT_CLASSES = 'slider';

        _ComponentRegistry['default'].register('Slider', Slider);

        module.exports = Slider;
        _JQueryAdapter['default'].register('slider', Slider);
    }
);