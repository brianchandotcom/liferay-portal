define(['exports', 'module', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/component/ComponentRegistry', 'metal/src/soy/SoyComponent', 'crystal-modal/src/Modal.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, module, _metalSrcCore, _metalSrcDomDom, _metalSrcComponentComponentRegistry, _metalSrcSoySoyComponent, _crystalModalSrcModalSoy, _metalJqueryAdapterSrcJQueryAdapter) {
	'use strict';

	var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

	var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

	function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) subClass.__proto__ = superClass; }

	var _core = _interopRequireDefault(_metalSrcCore);

	var _dom = _interopRequireDefault(_metalSrcDomDom);

	var _ComponentRegistry = _interopRequireDefault(_metalSrcComponentComponentRegistry);

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
		}

		_createClass(Modal, [{
			key: 'disposeInternal',

			/**
    * @inheritDoc
    */
			value: function disposeInternal() {
				_dom['default'].exitDocument(this.overlayElement);
				_get(Object.getPrototypeOf(Modal.prototype), 'disposeInternal', this).call(this);
			}
		}, {
			key: 'hide',

			/**
    * Hides the modal, setting its `visible` attribute to false.
    */
			value: function hide() {
				this.visible = false;
			}
		}, {
			key: 'show',

			/**
    * Shows the modal, setting its `visible` attribute to true.
    */
			value: function show() {
				this.visible = true;
			}
		}, {
			key: 'syncOverlay',

			/**
    * @inheritDoc
    */
			value: function syncOverlay(overlay) {
				var willShowOverlay = overlay && this.visible;
				_dom['default'][willShowOverlay ? 'enterDocument' : 'exitDocument'](this.overlayElement);
			}
		}, {
			key: 'syncVisible',

			/**
    * @inheritDoc
    */
			value: function syncVisible(visible) {
				this.element.style.display = visible ? 'block' : '';
				this.syncOverlay(this.overlay);
			}
		}, {
			key: 'valueOverlayElementFn_',

			/**
    * @inheritDoc
    */
			value: function valueOverlayElementFn_() {
				return _dom['default'].buildFragment('<div class="modal-backdrop fade in"></div>').firstChild;
			}
		}]);

		return Modal;
	})(_SoyComponent2['default']);

	/**
  * Default modal elementClasses.
  * @default modal
  * @type {String}
  * @static
  */
	Modal.ELEMENT_CLASSES = 'modal';

	Modal.ATTRS = {
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
		}
	};

	_ComponentRegistry['default'].register('Modal', Modal);

	module.exports = Modal;
	_JQueryAdapter['default'].register('modal', Modal);
});