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
	'liferay-notification',
	A => {
		var Notification = A.Component.create({
			EXTENDS: Liferay.Alert,

			NAME: 'liferaynotification',

			prototype: {
				_getAlertsContainer(targetNode) {
					var instance = this;

					targetNode = targetNode || A.one('body');

					var alertsContainer = instance._alertsContainer;

					if (!alertsContainer) {
						var rootNode =
							targetNode || instance.get('rootNode') || A;

						alertsContainer =
							(targetNode &&
								targetNode.one(
									'.lfr-notification-container'
								)) ||
							rootNode.one('.lfr-notification-container');

						if (!alertsContainer) {
							alertsContainer = A.Node.create(
								instance.TPL_ALERTS_CONTAINER
							);

							targetNode.prepend(alertsContainer);
						}

						instance._alertsContainer = alertsContainer;
					}

					return alertsContainer;
				},

				TPL_ALERT_NODE: '<div class="lfr-notification-wrapper"></div>',

				TPL_ALERTS_CONTAINER:
					'<div class="lfr-notification-container"></div>'
			}
		});

		Liferay.Notification = Notification;
	},
	'',
	{
		requires: ['liferay-alert']
	}
);
