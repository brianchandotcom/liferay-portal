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

(function (A) {
	var CLICK_EVENTS = {};
	var Util = Liferay.Util;

	A.use('attribute', 'oop', (A) => {
		A.augment(Liferay, A.Attribute, true);
	});

	Liferay.provide(
		Liferay,
		'delegateClick',
		(id, fn) => {
			var element = A.config.doc.getElementById(id);

			if (!element || element.id != id) {
				return;
			}

			var guid = A.one(element).addClass('lfr-delegate-click').guid();

			CLICK_EVENTS[guid] = fn;

			if (!Liferay._baseDelegateHandle) {
				Liferay._baseDelegateHandle = A.getBody().delegate(
					'click',
					Liferay._baseDelegate,
					'.lfr-delegate-click'
				);
			}
		},
		['aui-base']
	);

	Liferay._baseDelegate = function (event) {
		var id = event.currentTarget.attr('id');

		var fn = CLICK_EVENTS[id];

		if (fn) {
			fn.apply(this, arguments);
		}
	};

	Liferay._CLICK_EVENTS = CLICK_EVENTS;

	Liferay.provide(
		window,
		'submitForm',
		(form, action, singleSubmit, validate) => {
			if (!Util._submitLocked) {
				if (form.jquery) {
					form = form[0];
				}

				Liferay.fire('submitForm', {
					action,
					form: A.one(form),
					singleSubmit,
					validate: validate !== false,
				});
			}
		},
		['aui-base', 'aui-form-validator', 'aui-url', 'liferay-form']
	);

	Liferay.publish('submitForm', {
		defaultFn(event) {
			var form = event.form;

			var hasErrors = false;

			if (event.validate) {
				var liferayForm = Liferay.Form.get(form.attr('id'));

				if (liferayForm) {
					var validator = liferayForm.formValidator;

					if (A.instanceOf(validator, A.FormValidator)) {
						validator.validate();

						hasErrors = validator.hasErrors();

						if (hasErrors) {
							validator.focusInvalidField();
						}
					}
				}
			}

			if (!hasErrors) {
				var action = event.action || form.getAttribute('action');

				var singleSubmit = event.singleSubmit;

				var inputs = form.all(
					'button[type=submit], input[type=button], input[type=image], input[type=reset], input[type=submit]'
				);

				Util.disableFormButtons(inputs, form);

				if (singleSubmit === false) {
					Util._submitLocked = A.later(
						1000,
						Util,
						Util.enableFormButtons,
						[inputs, form]
					);
				}
				else {
					Util._submitLocked = true;
				}

				var baseURL;
				var queryString;
				var searchParamsIndex = action.indexOf('?');

				if (searchParamsIndex === -1) {
					baseURL = action;
					queryString = '';
				}
				else {
					baseURL = action.slice(0, searchParamsIndex);
					queryString = action.slice(searchParamsIndex + 1);
				}

				var searchParams = new URLSearchParams(queryString);

				var authToken = searchParams.get('p_auth') || '';

				if (authToken.includes('#')) {
					authToken = authToken.substring(0, authToken.indexOf('#'));
				}

				if (authToken) {
					form.append(
						'<input name="p_auth" type="hidden" value="' +
							authToken +
							'" />'
					);

					searchParams.delete('p_auth');

					action = baseURL + '?' + searchParams.toString();
				}

				form.attr('action', action);

				Util.submitForm(form);

				form.attr('target', '');

				Util._submitLocked = null;
			}
		},
	});

	Liferay.after('closeWindow', (event) => {
		var id = event.id;

		var dialog = Util.getTop().Liferay.Util.Window.getById(id);

		if (dialog && dialog.iframe) {
			var dialogWindow = dialog.iframe.node.get('contentWindow').getDOM();

			var openingWindow = dialogWindow.Liferay.Util.getOpener();
			var redirect = event.redirect;

			if (redirect) {
				openingWindow.Liferay.Util.navigate(redirect);
			}
			else {
				var refresh = event.refresh;

				if (refresh && openingWindow) {
					var data;

					if (!event.portletAjaxable) {
						data = {
							portletAjaxable: false,
						};
					}

					openingWindow.Liferay.Portlet.refresh(
						'#p_p_id_' + refresh + '_',
						data
					);
				}
			}

			dialog.hide();
		}
	});
})(AUI());
