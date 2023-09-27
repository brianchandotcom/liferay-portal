/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-logo-selector',
	(A) => {
		const Lang = A.Lang;

		const DELETE_LOGO = 'DELETE_LOGO';

		const MAP_DELETE_LOGO = {
			src: DELETE_LOGO,
		};

		const LogoSelector = A.Component.create({
			ATTRS: {
				defaultLogo: {
					value: false,
				},

				defaultLogoURL: {
					value: '',
				},

				editLogoFn: {
					setter(value) {
						let fn = function () {};

						if (Lang.isFunction(window[value])) {
							fn = window[value] || fn;
						}

						return fn;
					},
					validator: A.Lang.isString,
					value: '',
				},

				editLogoURL: {
					value: '',
				},

				logoDisplaySelector: {
					value: '',
				},

				logoURL: {
					value: '',
				},

				portletNamespace: {
					value: '',
				},

				randomNamespace: {
					value: '',
				},
			},

			BIND_UI_ATTRS: ['logoURL'],

			NAME: 'logoselector',

			prototype: {
				_changeLogo(url, fileEntryId) {
					const instance = this;

					url = Liferay.Util.addParams('t=' + Date.now(), url);

					instance.set('logoURL', url);

					if (fileEntryId) {
						instance._fileEntryIdInput.val(fileEntryId);
					}
				},

				_onDeleteLogoClick() {
					const instance = this;

					instance.set(
						'logoURL',
						instance.get('defaultLogoURL'),
						MAP_DELETE_LOGO
					);

					if (instance._emptyResultMessage) {
						instance._emptyResultMessage.show();
					}
				},

				_openEditLogoWindow(event) {
					const instance = this;

					const editLogoURL = instance.get('editLogoURL');

					Liferay.Util.openWindow({
						cache: false,
						dialog: {
							destroyOnHide: true,
						},
						dialogIframe: {
							bodyCssClass: 'dialog-with-footer',
						},
						id: instance._portletNamespace + 'changeLogo',
						title: Liferay.Language.get('upload-image'),
						uri: editLogoURL,
					});

					event.preventDefault();
				},

				_uiSetLogoURL(value, src) {
					const instance = this;

					const logoURL = value;

					const logoDisplaySelector = instance.get(
						'logoDisplaySelector'
					);

					const deleteLogo = src === DELETE_LOGO;

					const defaultLogo = instance.get('defaultLogo');

					instance._avatar.attr('src', logoURL);

					if (logoDisplaySelector) {
						const logoDisplay = A.one(logoDisplaySelector);

						if (logoDisplay) {
							logoDisplay.attr('src', logoURL);
						}
					}

					instance
						.get('editLogoFn')
						.apply(instance, [logoURL, deleteLogo]);

					instance._deleteLogoInput.val(deleteLogo);
					instance._deleteLogoButton.attr(
						'disabled',
						deleteLogo || defaultLogo ? 'disabled' : ''
					);
					instance._deleteLogoButton.toggleClass(
						'disabled',
						deleteLogo
					);

					if (instance._emptyResultMessage) {
						instance._emptyResultMessage.hide();
					}
				},

				bindUI() {
					const instance = this;

					instance
						.get('contentBox')
						.delegate(
							'click',
							instance._openEditLogoWindow,
							'.edit-logo',
							instance
						);
					instance
						.get('contentBox')
						.delegate(
							'click',
							instance._onDeleteLogoClick,
							'.delete-logo',
							instance
						);
				},

				initializer() {
					const instance = this;

					instance._portletNamespace = instance.get(
						'portletNamespace'
					);
					instance._randomNamespace = instance.get('randomNamespace');

					window[instance._randomNamespace + 'changeLogo'] = A.bind(
						'_changeLogo',
						instance
					);
				},

				renderUI() {
					const instance = this;

					const portletNamespace = instance._portletNamespace;
					const randomNamespace = instance._randomNamespace;

					const contentBox = instance.get('contentBox');

					instance._avatar = contentBox.one(
						'#' + randomNamespace + 'avatar'
					);
					instance._deleteLogoButton = contentBox.one('.delete-logo');
					instance._deleteLogoInput = contentBox.one(
						'#' + portletNamespace + 'deleteLogo'
					);
					instance._emptyResultMessage = contentBox.one(
						'#' + randomNamespace + 'emptyResultMessage'
					);
					instance._fileEntryIdInput = contentBox.one(
						'#' + portletNamespace + 'fileEntryId'
					);
				},
			},
		});

		Liferay.LogoSelector = LogoSelector;
	},
	'',
	{
		requires: ['aui-base', 'liferay-util-window'],
	}
);
