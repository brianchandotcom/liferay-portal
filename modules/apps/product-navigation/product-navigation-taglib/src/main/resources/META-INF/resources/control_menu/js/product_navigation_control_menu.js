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
	'liferay-product-navigation-control-menu',
	A => {
		var ControlMenu = {
			init(containerId) {
				var instance = this;

				var controlMenu = A.one(containerId);

				if (controlMenu) {
					var namespace = controlMenu.attr('data-namespace');

					instance._namespace = namespace;

					Liferay.Util.toggleControls(controlMenu);

					var eventHandle = controlMenu.on(
						['focus', 'mousemove', 'touchstart'],
						() => {
							Liferay.fire('initLayout');

							eventHandle.detach();
						}
					);
				}
			}
		};

		Liferay.ControlMenu = ControlMenu;
	},
	'',
	{
		requires: [
			'aui-node',
			'aui-overlay-mask-deprecated',
			'event-move',
			'event-touch',
			'liferay-menu-toggle'
		]
	}
);
