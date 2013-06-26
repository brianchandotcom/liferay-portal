AUI.add(
	'liferay-notice',
	function(A) {
		var ADOM = A.DOM;
		var ANode = A.Node;
		var Do = A.Do;
		var Lang = A.Lang;

		var CSS_ALERTS = 'has-alerts';

		var STR_CLICK = 'click';

		var STR_EMPTY = '';

		var STR_HIDE = 'hide';

		var STR_PX = 'px';

		var STR_SHOW = 'show';

		/**
		 * @deprecated
		 *
		 * OPTIONS
		 *
		 * Required
		 * content {string}: The content of the toolbar.
		 *
		 * Optional
		 * animationConfig {Object}: The Transition config, defaults to {easing: 'ease-out', duration: 2, top: '50px'}. If 'left' property is not specified, it will be automatically calculated.
		 * closeText {string}: Use for the "close" button. Set to false to not have a close button. If set to false but in the provided markup (via content property) there is an element with class "close", a click listener on this element will be added. As result, the notice will be closed.
		 * noticeClass {string}: A class to add to the notice toolbar.
		 * timeout {Number}: The timeout in milliseconds, after it the notice will be automatically closed. Set it to -1, or do not add this property to disable this functionality.
		 * toggleText {object}: The text to use for the "hide" and "show" button. Set to false to not have a hide button.
		 * noticeType {String}: One of 'warning' or 'notice'. If not set, default notice type will be 'notice'
		 * useAnimation {boolean}: To animate show/hide of the notice, defaults to true. If useAnimation is set to true, but there is no timeout, 5000 will be used as timeout.
		 *
		 * Callbacks
		 * onClose {function}: Called when the toolbar is closed.
		 */

		var Notice = function(options) {
			var instance = this;

			options = options || {};

			instance._closeText = options.closeText;
			instance._node = options.node;
			instance._noticeType = options.type || 'notice';
			instance._noticeClass = 'alert-block popup-alert-notice';
			instance._onClose = options.onClose;
			instance._useCloseButton = true;

			if (options.useAnimation && !Lang.isNumber(options.timeout)) {
				options.timeout = 5000;
			}

			instance._animationConfig = options.animationConfig || {
				duration: 2,
				easing: 'ease-out',
				top: '50px'
			};

			instance._useAnimation = options.useAnimation;

			instance._timeout = options.timeout;

			instance._body = A.getBody();

			instance._useToggleButton = false;
			instance._hideText = STR_EMPTY;
			instance._showText = STR_EMPTY;

			if (options.toggleText !== false) {
				instance.toggleText = A.mix(
					options.toggleText,
					{
						hide: null,
						show: null
					}
				);

				instance._useToggleButton = true;
			}

			if (instance._noticeType == 'warning') {
				instance._noticeClass = 'alert-error popup-alert-warning';
			}

			if (options.noticeClass) {
				instance._noticeClass += ' ' + options.noticeClass;
			}

			instance._content = options.content || STR_EMPTY;

			instance._createHTML();

			return instance._notice;
		};

		Notice.prototype = {
			close: function() {
				var instance = this;

				var notice = instance._notice;

				notice.hide();

				instance._body.removeClass(CSS_ALERTS);

				if (instance._onClose) {
					instance._onClose();
				}
			},

			setClosing: function() {
				var instance = this;

				var alerts = A.all('.popup-alert-notice, .popup-alert-warning');

				if (alerts.size()) {
					instance._useCloseButton = true;

					if (!instance._body) {
						instance._body = A.getBody();
					}

					instance._body.addClass(CSS_ALERTS);

					alerts.each(instance._addCloseButton, instance);
				}
			},

			_afterNoticeShow: function(event) {
				var instance = this;

				if (instance._useAnimation) {
					var noticeLeft;

					var noticeRegion;

					if (!instance._animationConfig.left) {
						var viewportWidth = ADOM.winWidth();

						noticeRegion = ADOM.region(ANode.getDOMNode(instance._notice));

						noticeLeft = (viewportWidth / 2) - (noticeRegion.width / 2);

						instance._animationConfig.left = noticeLeft + STR_PX;
					}

					instance._notice.setXY([noticeLeft, -noticeRegion.height]);

					instance._notice.transition(
						instance._animationConfig,
						function() {
							A.later(instance._timeout, instance._notice, STR_HIDE);
						}
					);
				}
				else {
					A.later(instance._timeout, instance._notice, STR_HIDE);
				}
			},

			_beforeNoticeHide: function(event) {
				var instance = this;

				var returnVal;

				if (instance._useAnimation) {
					var animationConfig = A.merge(
						instance._animationConfig,
						{
							top: -instance._notice.get('offsetHeight') + STR_PX
						}
					);

					instance._notice.transition(animationConfig);

					returnVal = new Do.Halt(null);
				}

				return returnVal;
			},

			_createHTML: function() {
				var instance = this;

				var content = instance._content;
				var node = A.one(instance._node);

				var notice = node || ANode.create('<div class="alert" dynamic="true"></div>');

				if (content) {
					notice.html(content);
				}

				notice.addClass(instance._noticeClass);

				instance._addCloseButton(notice);
				instance._addToggleButton(notice);

				if (!node || (node && !node.inDoc())) {
					instance._body.append(notice);
				}

				instance._body.addClass(CSS_ALERTS);

				if (instance._timeout > 0) {
					Do.before(instance._beforeNoticeHide, notice, STR_HIDE, instance);

					Do.after(instance._afterNoticeShow, notice, STR_SHOW, instance);
				}

				instance._notice = notice;
			},

			_addCloseButton: function(notice) {
				var instance = this;

				var closeButton;

				if (instance._closeText !== false) {
					instance._closeText = instance._closeText || Liferay.Language.get('close');
				}
				else {
					instance._useCloseButton = false;
					instance._closeText = STR_EMPTY;
				}

				if (instance._useCloseButton) {
					var html =  '<button class="btn submit popup-alert-close">' +
									instance._closeText +
								'</button>';

					closeButton = notice.append(html);
				}
				else {
					closeButton = notice.one('.close');
				}

				if (closeButton) {
					closeButton.on(STR_CLICK, instance.close, instance);
				}
			},

			_addToggleButton: function(notice) {
				var instance = this;

				if (instance._useToggleButton) {
					instance._hideText = instance._toggleText.hide || Liferay.Language.get(STR_HIDE);
					instance._showText = instance._toggleText.show || Liferay.Language.get(STR_SHOW);

					var toggleButton = ANode.create('<a class="toggle-button" href="javascript:;"><span>' + instance._hideText + '</span></a>');
					var toggleSpan = toggleButton.one('span');
					var height = 0;

					var visible = 0;

					var showText = instance._showText;
					var hideText = instance._hideText;

					toggleButton.on(
						STR_CLICK,
						function(event) {
							var text = showText;

							if (visible == 0) {
								text = hideText;

								visible = 1;
							}
							else {
								visible = 0;
							}

							notice.toggle();
							toggleSpan.text(text);
						}
					);

					notice.append(toggleButton);
				}
			}
		};

		Liferay.Notice = Notice;
	},
	'',
	{
		requires: ['aui-base']
	}
);