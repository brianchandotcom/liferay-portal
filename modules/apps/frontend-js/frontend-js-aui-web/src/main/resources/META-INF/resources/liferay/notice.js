/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `Liferay.Util.openToast`.
 */
AUI.add(
	'liferay-notice',
	(A) => {
		const ADOM = A.DOM;
		const ANode = A.Node;
		const Do = A.Do;
		const Lang = A.Lang;

		const CSS_ALERTS = 'has-alerts';

		const STR_CLICK = 'click';

		const STR_EMPTY = '';

		const STR_HIDE = 'hide';

		const STR_PX = 'px';

		const STR_SHOW = 'show';

		/**
		 * @deprecated As of Wilberforce (7.0.x)
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
		 * type {String}: One of 'warning' or 'notice'. If not set, default notice type will be 'notice'
		 * useAnimation {boolean}: To animate show/hide of the notice, defaults to true. If useAnimation is set to true, but there is no timeout, 5000 will be used as timeout.
		 *
		 * Callbacks
		 * onClose {function}: Called when the toolbar is closed.
		 */

		/**
		 * @deprecated As of Athanasius (7.3.x), replaced by `Liferay.Util.openToast`.
		 * @module liferay-notice
		 */

		const Notice = function (options) {
			const instance = this;

			options = options || {};

			instance._closeText = options.closeText;
			instance._node = options.node;
			instance._noticeType = options.type || 'notice';
			instance._noticeClass = 'alert-notice';
			instance._onClose = options.onClose;
			instance._useCloseButton = true;

			if (options.useAnimation) {
				instance._noticeClass += ' popup-alert-notice';

				if (!Lang.isNumber(options.timeout)) {
					options.timeout = 5000;
				}
			}

			instance._animationConfig = options.animationConfig || {
				duration: 2,
				easing: 'ease-out',
				top: '50px',
			};

			instance._useAnimation = options.useAnimation;

			instance._timeout = options.timeout;

			// eslint-disable-next-line @liferay/aui/no-get-body
			instance._body = A.getBody();

			instance._useToggleButton = false;
			instance._hideText = STR_EMPTY;
			instance._showText = STR_EMPTY;

			if (options.toggleText !== false) {
				instance.toggleText = A.mix(options.toggleText, {
					hide: null,
					show: null,
				});

				instance._useToggleButton = true;
			}

			if (instance._noticeType === 'warning') {
				instance._noticeClass = 'alert-danger popup-alert-warning';
			}

			if (options.noticeClass) {
				instance._noticeClass += ' ' + options.noticeClass;
			}

			instance._content = options.content || STR_EMPTY;

			instance._createHTML();

			return instance._notice;
		};

		Notice.prototype = {
			_addCloseButton(notice) {
				const instance = this;

				let closeButton;

				if (instance._closeText !== false) {
					instance._closeText =
						instance._closeText || Liferay.Language.get('close');
				}
				else {
					instance._useCloseButton = false;
					instance._closeText = STR_EMPTY;
				}

				if (instance._useCloseButton) {
					const html =
						'<button class="btn btn-secondary c-ml-1 submit">' +
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

			_addToggleButton(notice) {
				const instance = this;

				if (instance._useToggleButton) {
					instance._hideText =
						instance._toggleText.hide ||
						Liferay.Language.get('hide');
					instance._showText =
						instance._toggleText.show ||
						Liferay.Language.get('show');

					const toggleButton = ANode.create(
						'<a class="toggle-button" href="javascript:void(0);"><span>' +
							instance._hideText +
							'</span></a>'
					);
					const toggleSpan = toggleButton.one('span');

					let visible = 0;

					const hideText = instance._hideText;
					const showText = instance._showText;

					toggleButton.on(STR_CLICK, () => {
						let text = showText;

						if (visible === 0) {
							text = hideText;

							visible = 1;
						}
						else {
							visible = 0;
						}

						notice.toggle();
						toggleSpan.text(text);
					});

					notice.append(toggleButton);
				}
			},

			_afterNoticeShow() {
				const instance = this;

				instance._preventHide();

				const notice = instance._notice;

				if (instance._useAnimation) {
					const animationConfig = instance._animationConfig;

					let left = animationConfig.left;
					let top = animationConfig.top;

					if (!left) {
						const noticeRegion = ADOM.region(
							ANode.getDOMNode(notice)
						);

						left = ADOM.winWidth() / 2 - noticeRegion.width / 2;

						top = -noticeRegion.height;

						animationConfig.left = left + STR_PX;
					}

					notice.setXY([left, top]);

					notice.transition(instance._animationConfig, () => {
						instance._hideHandle = A.later(
							instance._timeout,
							notice,
							STR_HIDE
						);
					});
				}
				else if (instance._timeout > -1) {
					instance._hideHandle = A.later(
						instance._timeout,
						notice,
						STR_HIDE
					);
				}

				Liferay.fire('noticeShow', {
					notice: instance,
					useAnimation: instance._useAnimation,
				});
			},

			_beforeNoticeHide() {
				const instance = this;

				let returnVal;

				if (instance._useAnimation) {
					// eslint-disable-next-line @liferay/aui/no-merge
					const animationConfig = A.merge(instance._animationConfig, {
						top: -instance._notice.get('offsetHeight') + STR_PX,
					});

					instance._notice.transition(animationConfig, () => {
						instance._notice.toggle(false);
					});

					returnVal = new Do.Halt(null);
				}

				Liferay.fire('noticeHide', {
					notice: instance,
					useAnimation: instance._useAnimation,
				});

				return returnVal;
			},

			_beforeNoticeShow() {
				const instance = this;

				instance._notice.toggle(true);
			},

			_createHTML() {
				const instance = this;

				const content = instance._content;
				const node = A.one(instance._node);

				const notice =
					node ||
					ANode.create(
						'<div class="alert alert-warning" dynamic="true"></div>'
					);

				if (content) {
					notice.html(content);
				}

				instance._noticeClass.split(' ').forEach((item) => {
					notice.addClass(item);
				});

				instance._addCloseButton(notice);
				instance._addToggleButton(notice);

				if (!node || (node && !node.inDoc())) {
					instance._body.prepend(notice);
				}

				instance._body.addClass(CSS_ALERTS);

				Do.before(
					instance._beforeNoticeHide,
					notice,
					STR_HIDE,
					instance
				);

				Do.before(
					instance._beforeNoticeShow,
					notice,
					STR_SHOW,
					instance
				);

				Do.after(instance._afterNoticeShow, notice, STR_SHOW, instance);

				instance._notice = notice;
			},

			_preventHide() {
				const instance = this;

				if (instance._hideHandle) {
					instance._hideHandle.cancel();

					instance._hideHandle = null;
				}
			},

			close() {
				const instance = this;

				const notice = instance._notice;

				notice.hide();

				instance._body.removeClass(CSS_ALERTS);

				if (instance._onClose) {
					instance._onClose();
				}
			},

			setClosing() {
				const instance = this;

				const alerts = A.all(
					'.popup-alert-notice, .popup-alert-warning'
				);

				if (alerts.size()) {
					instance._useCloseButton = true;

					if (!instance._body) {
						// eslint-disable-next-line @liferay/aui/no-get-body
						instance._body = A.getBody();
					}

					instance._body.addClass(CSS_ALERTS);

					alerts.each(instance._addCloseButton, instance);
				}
			},
		};

		Liferay.Notice = Notice;
	},
	'',
	{
		requires: ['aui-base'],
	}
);
