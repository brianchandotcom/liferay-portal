define(
    "frontend-js-metal-web@1.0.0/crystal-tooltip/src/Tooltip",
    ['exports', 'module', 'metal/src/core', 'metal/src/dom/dom', 'metal-position/src/Align', 'metal/src/component/ComponentRegistry', 'metal/src/events/EventHandler', 'metal/src/soy/SoyComponent', 'metal/src/dom/events', 'crystal-tooltip/src/Tooltip.soy', 'metal-jquery-adapter/src/JQueryAdapter'],
    function (exports, module, _metalSrcCore, _metalSrcDomDom, _metalPositionSrcAlign, _metalSrcComponentComponentRegistry, _metalSrcEventsEventHandler, _metalSrcSoySoyComponent, _metalSrcDomEvents, _crystalTooltipSrcTooltipSoy, _metalJqueryAdapterSrcJQueryAdapter) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _core = _interopRequireDefault(_metalSrcCore);

        var _dom = _interopRequireDefault(_metalSrcDomDom);

        var _Align = _interopRequireDefault(_metalPositionSrcAlign);

        var _ComponentRegistry = _interopRequireDefault(_metalSrcComponentComponentRegistry);

        var _EventHandler = _interopRequireDefault(_metalSrcEventsEventHandler);

        var _SoyComponent2 = _interopRequireDefault(_metalSrcSoySoyComponent);

        var _JQueryAdapter = _interopRequireDefault(_metalJqueryAdapterSrcJQueryAdapter);

        /**
      * Tooltip component.
      */

        var Tooltip = (function (_SoyComponent) {
            _inherits(Tooltip, _SoyComponent);

            /**
       * @inheritDoc
       */

            function Tooltip(opt_config) {
                _classCallCheck(this, Tooltip);

                _get(Object.getPrototypeOf(Tooltip.prototype), 'constructor', this).call(this, opt_config);

                this.eventHandler_ = new _EventHandler['default']();
            }

            /**
       * @inheritDoc
       * @see `Align` class.
       * @static
       */

            /**
       * @inheritDoc
       */

            _createClass(Tooltip, [{
                key: 'attached',
                value: function attached() {
                    this.align();
                    this.syncTriggerEvents(this.triggerEvents);
                }

                /**
        * @inheritDoc
        */
            }, {
                key: 'detached',
                value: function detached() {
                    this.eventHandler_.removeAllListeners();
                }

                /**
        * Aligns the tooltip with the best region around alignElement. The best
        * region is defined by clockwise rotation starting from the specified
        * `position`. The element is always aligned in the middle of alignElement
        * axis.
        * @param {Element=} opt_alignElement Optional element to align with.
        */
            }, {
                key: 'align',
                value: function align(opt_alignElement) {
                    this.syncAlignElement(opt_alignElement || this.alignElement);
                }

                /**
        * @param {!function()} fn
        * @param {number} delay
        * @private
        */
            }, {
                key: 'callAsync_',
                value: function callAsync_(fn, delay) {
                    clearTimeout(this.delay_);
                    this.delay_ = setTimeout(fn.bind(this), delay);
                }

                /**
        * Handles hide event triggered by `events`.
        * @param {!Event} event
        * @protected
        */
            }, {
                key: 'handleHide',
                value: function handleHide(event) {
                    var _this = this;

                    var delegateTarget = event.delegateTarget;
                    var interactingWithDifferentTarget = delegateTarget && delegateTarget !== this.alignElement;
                    this.callAsync_(function () {
                        if (this.locked_) {
                            return;
                        }
                        if (interactingWithDifferentTarget) {
                            this.alignElement = delegateTarget;
                        } else {
                            this.visible = false;
                            _get(Object.getPrototypeOf(Tooltip.prototype), 'syncVisible', _this).call(_this, false);
                        }
                    }, this.delay[1]);
                }

                /**
        * Handles show event triggered by `events`.
        * @param {!Event} event
        * @protected
        */
            }, {
                key: 'handleShow',
                value: function handleShow(event) {
                    var delegateTarget = event.delegateTarget;
                    _get(Object.getPrototypeOf(Tooltip.prototype), 'syncVisible', this).call(this, true);
                    this.callAsync_(function () {
                        this.alignElement = delegateTarget;
                        this.visible = true;
                    }, this.delay[0]);
                }

                /**
        * Handles toggle event triggered by `events`.
        * @param {!Event} event
        * @protected
        */
            }, {
                key: 'handleToggle',
                value: function handleToggle(event) {
                    if (this.visible) {
                        this.handleHide(event);
                    } else {
                        this.handleShow(event);
                    }
                }

                /**
        * Locks tooltip visibility.
        * @param {!Event} event
        */
            }, {
                key: 'lock',
                value: function lock() {
                    this.locked_ = true;
                }

                /**
        * Unlocks tooltip visibility.
        * @param {!Event} event
        */
            }, {
                key: 'unlock',
                value: function unlock(event) {
                    this.locked_ = false;
                    this.handleHide(event);
                }

                /**
        * Attribute synchronization logic for `alignElement` attribute.
        * @param {Element} alignElement
        * @param {Element} prevAlignElement
        */
            }, {
                key: 'syncAlignElement',
                value: function syncAlignElement(alignElement, prevAlignElement) {
                    if (prevAlignElement) {
                        alignElement.removeAttribute('aria-describedby');
                    }
                    if (alignElement) {
                        if (this.visible) {
                            alignElement.setAttribute('aria-describedby', this.id);
                        } else {
                            alignElement.removeAttribute('aria-describedby');
                        }
                        if (this.inDocument) {
                            Tooltip.Align.align(this.element, alignElement, this.position);
                        }
                    }
                }

                /**
        * Attribute synchronization logic for `position` attribute.
        * @param {Align.Top|Align.Right|Align.Bottom|Align.Left} position
        */
            }, {
                key: 'syncPosition',
                value: function syncPosition(position) {
                    this.updatePositionCSS(position);
                    this.syncAlignElement(this.alignElement);
                }

                /**
        * Attribute synchronization logic for `selector` attribute.
        */
            }, {
                key: 'syncSelector',
                value: function syncSelector() {
                    this.syncTriggerEvents(this.triggerEvents);
                }

                /**
        * Attribute synchronization logic for `triggerEvents` attribute.
        * @param {!Array<string>} triggerEvents
        */
            }, {
                key: 'syncTriggerEvents',
                value: function syncTriggerEvents(triggerEvents) {
                    if (!this.inDocument) {
                        return;
                    }
                    this.eventHandler_.removeAllListeners();
                    var selector = this.selector;
                    if (!selector) {
                        return;
                    }

                    this.eventHandler_.add(this.on('mouseenter', this.lock), this.on('mouseleave', this.unlock));

                    if (triggerEvents[0] === triggerEvents[1]) {
                        this.eventHandler_.add(_dom['default'].delegate(document, triggerEvents[0], selector, this.handleToggle.bind(this)));
                    } else {
                        this.eventHandler_.add(_dom['default'].delegate(document, triggerEvents[0], selector, this.handleShow.bind(this)), _dom['default'].delegate(document, triggerEvents[1], selector, this.handleHide.bind(this)));
                    }
                }

                /**
        * Attribute synchronization logic for `visible` attribute.
        * Updates the element's opacity value according to it's visibility, and realigns
        * the tooltip as needed.
        * @param {boolean} visible
        */
            }, {
                key: 'syncVisible',
                value: function syncVisible(visible) {
                    this.element.style.opacity = visible ? 1 : '';
                    this.align();
                }

                /**
        * Updates the css class for the current position.
        * @param {number} position
        */
            }, {
                key: 'updatePositionCSS',
                value: function updatePositionCSS(position) {
                    _dom['default'].removeClasses(this.element, Tooltip.PositionClasses.join(' '));
                    _dom['default'].addClasses(this.element, Tooltip.PositionClasses[position]);
                }
            }]);

            return Tooltip;
        })(_SoyComponent2['default']);

        Tooltip.Align = _Align['default'];

        /**
      * Default tooltip elementClasses.
      * @default tooltip
      * @type {string}
      * @static
      */
        Tooltip.ELEMENT_CLASSES = 'tooltip';

        /**
      * Tooltip attrbutes definition.
      * @type {!Object}
      * @static
      */
        Tooltip.ATTRS = {
            /**
       * Element to align tooltip with.
       * @type {Element}
       */
            alignElement: {
                setter: _dom['default'].toElement
            },

            /**
       * Delay showing and hiding the tooltip (ms).
       * @type {!Array<number>}
       * @default [ 500, 250 ]
       */
            delay: {
                validator: Array.isArray,
                value: [500, 250]
            },

            /**
       * Trigger events used to bind handlers to show and hide tooltip.
       * @type {!Array<string>}
       * @default ['mouseenter', 'mouseleave']
       */
            triggerEvents: {
                validator: Array.isArray,
                value: ['mouseenter', 'mouseleave']
            },

            /**
       * If a selector is provided, tooltip objects will be delegated to the
       * specified targets by setting the `alignElement`.
       * @type {?string}
       */
            selector: {
                validator: _core['default'].isString
            },

            /**
       * Content to be placed inside tooltip.
       * @type {string}
       */
            content: {},

            /**
       * The position to try alignment. If not possible the best position will be
       * found.
       * @type {Align.Top|Align.Right|Align.Bottom|Align.Left}
       * @default Align.Bottom
       */
            position: {
                validator: Tooltip.Align.isValidPosition,
                value: Tooltip.Align.Bottom
            }
        };

        /**
      * CSS classes used for each align position.
      * @type {!Array}
      * @static
      */
        Tooltip.PositionClasses = ['top', 'right', 'bottom', 'left'];

        _ComponentRegistry['default'].register('Tooltip', Tooltip);

        module.exports = Tooltip;
        _JQueryAdapter['default'].register('tooltip', Tooltip);
    }
);