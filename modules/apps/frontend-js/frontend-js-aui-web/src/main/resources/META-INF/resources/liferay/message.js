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
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
AUI.add(
	'liferay-message',
	(A) => {
		const EVENT_DATA_DISMISS_ALL = {
			categoryVisible: false,
		};

		const NAME = 'liferaymessage';

		const REGEX_CSS_TYPE = A.DOM._getRegExp(
			'\\blfr-message-(alert|error|help|info|success)\\b',
			'g'
		);

		const TPL_HIDE_NOTICES =
			'<button aria-label="' +
			Liferay.Language.get('close') +
			'" type="button" class="close">&#x00D7;</button>';

		const Message = A.Component.create({
			ATTRS: {
				closeButton: {
					valueFn() {
						return A.Node.create(TPL_HIDE_NOTICES);
					},
				},

				dismissible: {
					value: true,
				},

				hideAllNotices: {
					valueFn() {
						const instance = this;

						return A.Node.create(
							'<a href="javascript:void(0);"><small>' +
								instance.get('strings.dismissAll') ||
								Liferay.Language.get(
									'disable-this-note-for-all-portlets'
								) + '</small></a>'
						);
					},
				},

				persistenceCategory: {
					value: '',
				},

				persistent: {
					value: true,
				},

				trigger: {
					setter: A.one,
				},

				type: {
					value: 'info',
				},
			},

			CSS_PREFIX: 'lfr-message',

			HTML_PARSER: {
				closeButton: '.close',
				hideAllNotices: '.btn-link',
			},

			NAME,

			UI_ATTRS: ['dismissible', 'persistent', 'type'],

			prototype: {
				_afterVisibleChange(event) {
					const instance = this;

					const messageVisible = event.newVal;

					instance._contentBox.toggle(messageVisible);

					instance.get('trigger').toggle(!messageVisible);

					if (instance.get('persistent')) {
						const sessionData = {};

						if (themeDisplay.isImpersonated()) {
							sessionData.doAsUserId = themeDisplay.getDoAsUserIdEncoded();
						}

						if (event.categoryVisible === false) {
							sessionData[
								instance.get('persistenceCategory')
							] = true;
						}

						sessionData[instance.get('id')] = messageVisible;

						Object.entries(sessionData).forEach((key, value) => {
							Liferay.Util.Session.set(key, value);
						});
					}
				},

				_onCloseButtonClick() {
					const instance = this;

					instance.hide();
				},

				_onHideAllClick() {
					const instance = this;

					instance.set('visible', false, EVENT_DATA_DISMISS_ALL);
				},

				_onTriggerClick() {
					const instance = this;

					instance.show();
				},

				_uiSetDismissible(value) {
					const instance = this;

					instance._boundingBox.toggleClass(
						instance._cssDismissible,
						value
					);
				},

				_uiSetPersistent(value) {
					const instance = this;

					instance._boundingBox.toggleClass(
						instance._cssPersistent,
						value
					);
				},

				_uiSetType(value) {
					const instance = this;

					const contentBox = instance._contentBox;

					let cssClass = contentBox
						.attr('class')
						.replace(REGEX_CSS_TYPE, '');

					cssClass += ' ' + instance.getClassName(value);

					contentBox.attr('class', cssClass);
				},

				bindUI() {
					const instance = this;

					if (instance._dismissible) {
						instance.after(
							'visibleChange',
							instance._afterVisibleChange
						);

						const closeButton = instance._closeButton;

						if (closeButton) {
							closeButton.on(
								'click',
								instance._onCloseButtonClick,
								instance
							);
						}

						const trigger = instance._trigger;

						if (trigger) {
							trigger.on(
								'click',
								instance._onTriggerClick,
								instance
							);
						}

						const hideAllNotices = instance._hideAllNotices;

						if (hideAllNotices) {
							hideAllNotices.on(
								'click',
								instance._onHideAllClick,
								instance
							);
						}
					}
				},

				initializer() {
					const instance = this;

					instance._boundingBox = instance.get('boundingBox');
					instance._contentBox = instance.get('contentBox');

					instance._cssDismissible = instance.getClassName(
						'dismissible'
					);
					instance._cssPersistent = instance.getClassName(
						'persistent'
					);
				},

				renderUI() {
					const instance = this;

					const dismissible = instance.get('dismissible');

					if (dismissible) {
						const trigger = instance.get('trigger');

						instance._trigger = trigger;

						const closeButton = instance.get('closeButton');

						if (instance.get('persistenceCategory')) {
							const hideAllNotices = instance.get(
								'hideAllNotices'
							);

							instance._contentBox.append(hideAllNotices);

							instance._contentBox.addClass('dismiss-all-notes');

							instance._hideAllNotices = hideAllNotices;
						}

						instance._closeButton = closeButton;

						instance._contentBox.prepend(closeButton);
					}

					instance._dismissible = dismissible;
				},
			},
		});

		Liferay.Message = Message;
	},
	'',
	{
		requires: ['aui-base'],
	}
);
