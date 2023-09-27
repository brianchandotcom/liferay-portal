/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function (A) {
	const CLICK_EVENTS = {};
	const Util = Liferay.Util;

	A.use('attribute', 'oop', (A) => {
		A.augment(Liferay, A.Attribute, true);
	});

	Liferay.provide(
		Liferay,
		'delegateClick',
		(id, fn) => {
			const element = A.config.doc.getElementById(id);

			if (!element || element.id !== id) {
				return;
			}

			// eslint-disable-next-line @liferay/aui/no-one
			const guid = A.one(element).addClass('lfr-delegate-click').guid();

			CLICK_EVENTS[guid] = fn;

			if (!Liferay._baseDelegateHandle) {
				// eslint-disable-next-line @liferay/aui/no-get-body
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
		const id = event.currentTarget.attr('id');

		const fn = CLICK_EVENTS[id];

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
					// eslint-disable-next-line @liferay/aui/no-one
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
			const form = event.form;

			let hasErrors = false;

			if (event.validate) {
				const liferayForm = Liferay.Form.get(form.attr('id'));

				if (liferayForm) {
					const validator = liferayForm.formValidator;

					if (A.instanceOf(validator, A.FormValidator)) {
						validator.validate();

						hasErrors = validator.hasErrors();

						if (hasErrors) {
							validator.focusInvalidField();
						}
					}
				}
			}

			function enableFormButtons(inputs) {
				Util._submitLocked = null;

				Util.toggleDisabled(inputs, false);
			}

			if (!hasErrors) {
				let action = event.action || form.getAttribute('action');

				const singleSubmit = event.singleSubmit;

				const inputs = form.all(
					'button[type=submit], input[type=button], input[type=image], input[type=reset], input[type=submit]'
				);

				const inputsArray = Array.from(inputs._nodes);

				if (inputsArray.length) {
					inputsArray.map((input) => {
						input.disabled = true;
						input.style.opacity = 0.5;
					});
				}

				if (singleSubmit === false) {
					Util._submitLocked = A.later(
						1000,
						Util,
						enableFormButtons,
						[inputs, form]
					);
				}
				else {
					Util._submitLocked = true;
				}

				let baseURL;
				let queryString;
				const searchParamsIndex = action.indexOf('?');

				if (searchParamsIndex === -1) {
					baseURL = action;
					queryString = '';
				}
				else {
					baseURL = action.slice(0, searchParamsIndex);
					queryString = action.slice(searchParamsIndex + 1);
				}

				const searchParams = new URLSearchParams(queryString);

				let authToken = searchParams.get('p_auth') || '';

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
		const id = event.id;

		const dialog = Util.getTop().Liferay.Util.Window.getById(id);

		if (dialog && dialog.iframe) {
			const dialogWindow = dialog.iframe.node
				.get('contentWindow')
				.getDOM();

			const openingWindow = dialogWindow.Liferay.Util.getOpener();
			const redirect = event.redirect;

			if (redirect) {
				openingWindow.Liferay.Util.navigate(redirect);
			}
			else {
				const refresh = event.refresh;

				if (refresh && openingWindow) {
					let data;

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
