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

AUI.add(
	'liferay-logo-selector',
	A => {
		var Lang = A.Lang;

		var DELETE_LOGO = 'DELETE_LOGO';

		var MAP_DELETE_LOGO = {
			src: DELETE_LOGO
		};

		var LogoSelector = A.Component.create({
			ATTRS: {
				defaultLogoURL: {
					value: ''
				},

				editLogoFn: {
					setter(value) {
						var fn = function() {};

						if (Lang.isFunction(window[value])) {
							fn = window[value] || fn;
						}

						return fn;
					},
					validator: A.Lang.isString,
					value: ''
				},

				editLogoURL: {
					value: ''
				},

				logoDisplaySelector: {
					value: ''
				},

				logoURL: {
					value: ''
				},

				portletNamespace: {
					value: ''
				},

				randomNamespace: {
					value: ''
				}
			},

			BIND_UI_ATTRS: ['logoURL'],

			NAME: 'logoselector',

			prototype: {
				_changeLogo(url, fileEntryId) {
					var instance = this;

					instance.set('logoURL', url);

					if (fileEntryId) {
						instance._fileEntryIdInput.val(fileEntryId);
					}
				},

				_onDeleteLogoClick() {
					var instance = this;

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
					var instance = this;

					var editLogoURL = instance.get('editLogoURL');

					Liferay.Util.openWindow({
						cache: false,
						dialog: {
							destroyOnHide: true
						},
						dialogIframe: {
							bodyCssClass: 'dialog-with-footer'
						},
						id: instance._portletNamespace + 'changeLogo',
						title: Liferay.Language.get('upload-image'),
						uri: editLogoURL
					});

					event.preventDefault();
				},

				_uiSetLogoURL(value, src) {
					var instance = this;

					var logoURL = value;

					var logoDisplaySelector = instance.get(
						'logoDisplaySelector'
					);

					var deleteLogo = src == DELETE_LOGO;

					instance._avatar.attr('src', logoURL);

					if (logoDisplaySelector) {
						var logoDisplay = A.one(logoDisplaySelector);

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
						deleteLogo ? 'disabled' : ''
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
					var instance = this;

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
					var instance = this;

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
					var instance = this;

					var portletNamespace = instance._portletNamespace;
					var randomNamespace = instance._randomNamespace;

					var contentBox = instance.get('contentBox');

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
				}
			}
		});

		Liferay.LogoSelector = LogoSelector;
	},
	'',
	{
		requires: ['aui-base', 'liferay-util-window']
	}
);
