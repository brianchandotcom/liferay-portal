define("frontend-js-metal-web@1.0.6/metal-rating/src/Rating", ['exports', 'metal/src/metal', 'metal-component/src/all/component', 'metal-soy/src/Soy', './Rating.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _component, _Soy, _Rating, _JQueryAdapter) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _metal2 = _interopRequireDefault(_metal);

    var _component2 = _interopRequireDefault(_component);

    var _Soy2 = _interopRequireDefault(_Soy);

    var _Rating2 = _interopRequireDefault(_Rating);

    var _JQueryAdapter2 = _interopRequireDefault(_JQueryAdapter);

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

    var Rating = function (_Component) {
        _inherits(Rating, _Component);

        function Rating() {
            _classCallCheck(this, Rating);

            return _possibleConstructorReturn(this, _Component.apply(this, arguments));
        }

        Rating.prototype.attached = function attached() {
            this.delegate('mouseover', '.rating-item', this.handleMouseOverEvent.bind(this));
            this.delegate('click', '.rating-item', this.handleClickEvent.bind(this));
            this.on('mouseleave', this.handleMouseLeaveEvent.bind(this));
        };

        Rating.prototype.created = function created() {
            this.ratingClicked_ = this.value;
        };

        Rating.prototype.handleClickEvent = function handleClickEvent(event) {
            if (!this.disabled) {
                var index = parseInt(event.delegateTarget.getAttribute('data-index'), 10);

                if (index === this.ratingClicked_ && this.canReset) {
                    this.reset();
                } else {
                    this.value = index;
                }

                this.ratingClicked_ = this.value;
            }
        };

        Rating.prototype.handleMouseLeaveEvent = function handleMouseLeaveEvent() {
            this.setPreviousRate_();
        };

        Rating.prototype.handleMouseOverEvent = function handleMouseOverEvent(event) {
            if (!this.disabled) {
                var index = Number.parseInt(event.delegateTarget.getAttribute('data-index'), 10);

                this.value = index;
            }
        };

        Rating.prototype.reset = function reset() {
            this.value = -1;
            this.ratingClicked_ = -1;
        };

        Rating.prototype.setPreviousRate_ = function setPreviousRate_() {
            this.value = this.ratingClicked_;
        };

        return Rating;
    }(_component2.default);

    Rating.STATE = {

        /**
         * Flag indicating if this component can be reset or not
         * @type {boolean}
         * @default true
         */
        canReset: {
            value: true,
            validator: _metal2.default.isBoolean
        },

        /**
         * Optional CSS classes to be added to the inner rating element.
         * @type {string}
         */
        cssClasses: {
            value: {
                off: 'glyphicon glyphicon-star-empty',
                on: 'glyphicon glyphicon-star'
            }
        },

        /**
         * Block or unblock rating functionality.
         * @type {?boolean}
         * @default false
         */
        disabled: {
            value: false,
            validator: _metal2.default.isBoolean
        },

        /**
         * Name of the hidden input. It can be used to send
         * current option value as a form data.
         *
         * @attribute inputHiddenName
         * @type {string}
         * @default 'rate'
         */
        inputHiddenName: {
            value: 'rate',
            validator: _metal2.default.isString
        },

        /**
         * Label to be displayed with the Rating elements.
         *
         * @attribute label
         * @type {string}
         * @default ''
         */
        label: {
            value: '',
            validator: _metal2.default.isString
        },

        /**
         * List of rate options.
         * @type {array}
         */
        options: {
            validator: Array.isArray,
            value: [{
                value: 1,
                title: ''
            }, {
                value: 2,
                title: ''
            }, {
                value: 3,
                title: ''
            }, {
                value: 4,
                title: ''
            }, {
                value: 5,
                title: ''
            }]
        },

        /**
         * Rating current index value.
         * @type {?number}
         * @default null
         */
        value: {
            validator: _metal2.default.isNumber,
            value: -1
        }
    };
    _Soy2.default.register(Rating, _Rating2.default);

    exports.default = Rating;
    _JQueryAdapter2.default.register('rating', Rating);
});
//# sourceMappingURL=Rating.js.map