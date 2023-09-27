/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The Icon Component.
 *
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-icon
 */

AUI.add(
	'liferay-icon',
	(A) => {
		const _ICON_REGISTRY = {};

		const Icon = {
			_forcePost(event) {
				if (!Liferay.SPA || !Liferay.SPA.app) {
					Liferay.Util.forcePost(event.currentTarget);

					event.preventDefault();
				}
			},

			_getConfig(event) {
				return _ICON_REGISTRY[event.currentTarget.attr('id')];
			},

			_handleDocClick(event) {
				const instance = this;

				const config = instance._getConfig(event);

				if (config) {
					event.preventDefault();

					if (config.useDialog) {
						instance._useDialog(event);
					}
					else {
						instance._forcePost(event);
					}
				}
			},

			_handleDocMouseOut(event) {
				const instance = this;

				const config = instance._getConfig(event);

				if (config && config.srcHover) {
					instance._onMouseHover(event, config.src);
				}
			},

			_handleDocMouseOver(event) {
				const instance = this;

				const config = instance._getConfig(event);

				if (config && config.srcHover) {
					instance._onMouseHover(event, config.srcHover);
				}
			},

			_onMouseHover(event, src) {
				const image = event.currentTarget.one('img');

				if (image) {
					image.attr('src', src);
				}
			},

			_useDialog(event) {
				Liferay.Util.openInDialog(event, {
					dialog: {
						destroyOnHide: true,
					},
					dialogIframe: {
						bodyCssClass: 'cadmin dialog-with-footer',
					},
				});
			},

			register(config) {
				const instance = this;

				const doc = A.one(A.config.doc);

				_ICON_REGISTRY[config.id] = config;

				if (!instance._docClickHandler) {
					instance._docClickHandler = doc.delegate(
						'click',
						instance._handleDocClick,
						'.lfr-icon-item',
						instance
					);
				}

				if (!instance._docHoverHandler) {
					instance._docHoverHandler = doc.delegate(
						'hover',
						instance._handleDocMouseOver,
						instance._handleDocMouseOut,
						'.lfr-icon-item',
						instance
					);
				}

				Liferay.once('screenLoad', () => {
					delete _ICON_REGISTRY[config.id];
				});
			},
		};

		Liferay.Icon = Icon;
	},
	'',
	{
		requires: ['aui-base', 'liferay-util-window'],
	}
);
