/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-widget-zindex',
	(A) => {
		const STR_HOST = 'host';

		const WidgetZIndex = A.Component.create({
			EXTENDS: A.Plugin.Base,

			NAME: 'widgetzindex',

			NS: 'zindex',

			prototype: {
				_setHostZIndex() {
					const instance = this;

					instance
						.get(STR_HOST)
						.set('zIndex', ++Liferay.zIndex.WINDOW);
				},

				initializer() {
					const instance = this;

					const host = instance.get(STR_HOST);

					if (!host.get('rendered') && host.get('visible')) {
						instance._setHostZIndex();
					}

					instance.onHostEvent('visibleChange', (event) => {
						if (event.newVal) {
							instance._setHostZIndex();
						}
					});
				},
			},
		});

		Liferay.WidgetZIndex = WidgetZIndex;
	},
	'',
	{
		requires: ['aui-modal', 'plugin'],
	}
);
