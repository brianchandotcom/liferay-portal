define(
    "frontend-js-metal-web@1.0.0/crystal-modal/src/Modal",
    ['exports', 'module', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/component/ComponentRegistry', 'metal/src/events/EventHandler', 'metal/src/soy/SoyComponent', 'crystal-modal/src/Modal.soy', 'metal-jquery-adapter/src/JQueryAdapter'],
    function (exports, module, _metalSrcCore, _metalSrcDomDom, _metalSrcComponentComponentRegistry, _metalSrcEventsEventHandler, _metalSrcSoySoyComponent, _crystalModalSrcModalSoy, _metalJqueryAdapterSrcJQueryAdapter) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _core = _interopRequireDefault(_metalSrcCore);

        var _dom = _interopRequireDefault(_metalSrcDomDom);

        var _ComponentRegistry = _interopRequireDefault(_metalSrcComponentComponentRegistry);

        var _EventHandler = _interopRequireDefault(_metalSrcEventsEventHandler);

        var _SoyComponent2 = _interopRequireDefault(_metalSrcSoySoyComponent);

        var _JQueryAdapter = _interopRequireDefault(_metalJqueryAdapterSrcJQueryAdapter);

        /**
      * Modal component.
      */

        var Modal = (function (_SoyComponent) {
            _inherits(Modal, _SoyComponent);

            /**
       * @inheritDoc
       */

            function Modal(opt_config) {
                _classCallCheck(this, Modal);

                _get(Object.getPrototypeOf(Modal.prototype), 'constructor', this).call(this, opt_config);
                this.eventHandler_ = new _EventHandler['default']();
            }

            /**
       * Default modal elementClasses.
       * @default modal
       * @type {string}
       * @static
       */

            /**
       * @inheritDoc
       */

            _createClass(Modal, [{
                key: 'attached',
                value: function attached() {
                    this.autoFocus_(this.autoFocus);
                }

                /**
        * Automatically focuses the element specified by the given selector.
        * @param {boolean|string} autoFocusSelector The selector, or false if no
        *   element should be automatically focused.
        * @protected
        */
            }, {
                key: 'autoFocus_',
                value: function autoFocus_(autoFocusSelector) {
                    if (this.inDocument && this.visible && autoFocusSelector) {
                        var element = this.element.querySelector(autoFocusSelector);
                        if (element) {
                            element.focus();
                        }
                    }
                }

                /**
        * @inheritDoc
        */
            }, {
                key: 'detached',
                value: function detached() {
                    _get(Object.getPrototypeOf(Modal.prototype), 'detached', this).call(this);
                    this.eventHandler_.removeAllListeners();
                }

                /**
        * @inheritDoc
        */
            }, {
                key: 'disposeInternal',
                value: function disposeInternal() {
                    _dom['default'].exitDocument(this.overlayElement);
                    this.unrestrictFocus_();
                    _get(Object.getPrototypeOf(Modal.prototype), 'disposeInternal', this).call(this);
                }

                /**
        * Handles a `focus` event on the document. If the focused element is
        * outside the modal and an overlay is being used, focuses the modal back.
        * @param {!Event} event
        * @protected
        */
            }, {
                key: 'handleDocumentFocus_',
                value: function handleDocumentFocus_(event) {
                    if (this.overlay && !this.element.contains(event.target)) {
                        this.autoFocus_('.modal-dialog');
                    }
                }

                /**
        * Handles document click in order to close the alert.
        * @param {!Event} event
        * @protected
        */
            }, {
                key: 'handleKeyup_',
                value: function handleKeyup_(event) {
                    if (event.keyCode === 27) {
                        this.hide();
                    }
                }

                /**
        * Hides the modal, setting its `visible` attribute to false.
        */
            }, {
                key: 'hide',
                value: function hide() {
                    this.visible = false;
                }

                /**
        * Restricts focus to the modal while it's visible.
        * @protected
        */
            }, {
                key: 'restrictFocus_',
                value: function restrictFocus_() {
                    this.restrictFocusHandle_ = _dom['default'].on(document, 'focus', this.handleDocumentFocus_.bind(this), true);
                }

                /**
        * Shifts the focus back to the last element that had been focused before the
        * modal was shown.
        * @protected
        */
            }, {
                key: 'shiftFocusBack_',
                value: function shiftFocusBack_() {
                    if (this.lastFocusedElement_) {
                        this.lastFocusedElement_.focus();
                        this.lastFocusedElement_ = null;
                    }
                }

                /**
        * Shows the modal, setting its `visible` attribute to true.
        */
            }, {
                key: 'show',
                value: function show() {
                    this.visible = true;
                }

                /**
        * Syncs the component according to the value of the `hideOnEscape` attribute.
        * @param {boolean} hideOnEscape
        */
            }, {
                key: 'syncHideOnEscape',
                value: function syncHideOnEscape(hideOnEscape) {
                    if (hideOnEscape) {
                        this.eventHandler_.add(_dom['default'].on(document, 'keyup', this.handleKeyup_.bind(this)));
                    } else {
                        this.eventHandler_.removeAllListeners();
                    }
                }

                /**
        * Syncs the component according to the value of the `overlay` attribute.
        * @param {boolean} overlay
        */
            }, {
                key: 'syncOverlay',
                value: function syncOverlay(overlay) {
                    var willShowOverlay = overlay && this.visible;
                    _dom['default'][willShowOverlay ? 'enterDocument' : 'exitDocument'](this.overlayElement);
                }

                /**
        * Syncs the component according to the value of the `visible` attribute.
        * @param {boolean} visible
        */
            }, {
                key: 'syncVisible',
                value: function syncVisible(visible) {
                    this.element.style.display = visible ? 'block' : '';
                    this.syncOverlay(this.overlay);
                    if (this.visible) {
                        this.lastFocusedElement_ = document.activeElement;
                        this.autoFocus_(this.autoFocus);
                        this.restrictFocus_();
                    } else {
                        this.unrestrictFocus_();
                        this.shiftFocusBack_();
                    }
                }

                /**
        * Removes the handler that restricts focus to elements inside the modal.
        * @protected
        */
            }, {
                key: 'unrestrictFocus_',
                value: function unrestrictFocus_() {
                    if (this.restrictFocusHandle_) {
                        this.restrictFocusHandle_.removeListener();
                    }
                }

                /**
        * Defines the default value for the `overlayElement` attribute.
        * @protected
        */
            }, {
                key: 'valueOverlayElementFn_',
                value: function valueOverlayElementFn_() {
                    return _dom['default'].buildFragment('<div class="modal-backdrop fade in"></div>').firstChild;
                }
            }]);

            return Modal;
        })(_SoyComponent2['default']);

        Modal.ELEMENT_CLASSES = 'modal';

        Modal.ATTRS = {
            /**
       * A selector for the element that should be automatically focused when the modal
       * becomes visible, or `false` if no auto focus should happen. Defaults to the
       * modal's close button.
       * @type {boolean|string}
       */
            autoFocus: {
                validator: function validator(val) {
                    return val === false || _core['default'].isString(val);
                },
                value: '.close'
            },

            /**
       * Content to be placed inside modal body.
       * @type {string|SanitizedHtml}
       */
            body: {},

            /**
       * Content to be placed inside modal footer.
       * @type {string|SanitizedHtml}
       */
            footer: {},

            /**
       * Content to be placed inside modal header.
       * @type {string|SanitizedHtml}
       */
            header: {},

            /**
       * Whether modal should hide on esc.
       * @type {boolean}
       * @default true
       */
            hideOnEscape: {
                validator: _core['default'].isBoolean,
                value: true
            },

            /**
       * Whether overlay should be visible when modal is visible.
       * @type {boolean}
       * @default true
       */
            overlay: {
                validator: _core['default'].isBoolean,
                value: true
            },

            /**
       * Element to be used as overlay.
       * @type {Element}
       */
            overlayElement: {
                initOnly: true,
                valueFn: 'valueOverlayElementFn_'
            },

            /**
       * The ARIA role to be used for this modal.
       * @type {string}
       * @default 'dialog'
       */
            role: {
                validator: _core['default'].isString,
                value: 'dialog'
            }
        };

        _ComponentRegistry['default'].register('Modal', Modal);

        module.exports = Modal;
        _JQueryAdapter['default'].register('modal', Modal);
    }
);