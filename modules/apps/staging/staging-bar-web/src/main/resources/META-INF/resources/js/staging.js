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
	'liferay-staging',
	() => {
		var StagingBar = {
			init(config) {
				var instance = this;

				var namespace = config.namespace;

				instance.markAsReadyForPublicationURL =
					config.markAsReadyForPublicationURL;

				instance.layoutRevisionStatusURL =
					config.layoutRevisionStatusURL;

				instance._namespace = namespace;

				instance.viewHistoryURL = config.viewHistoryURL;

				Liferay.publish({
					fireOnce: true,
				});

				Liferay.after('initStagingBar', () => {
					const body = document.body;

					if (body.classList.contains('has-staging-bar')) {
						const stagingLevel3 = document.querySelector(
							'.staging-bar-level-3-message'
						);

						if (!stagingLevel3) {
							body.classList.add('staging-ready');
						}
						else {
							body.classList.add('staging-ready-level-3');
						}
					}
				});

				Liferay.fire('initStagingBar', config);
			},
		};

		Liferay.StagingBar = StagingBar;
	},
	'',
	{
		requires: ['aui-io-plugin-deprecated', 'aui-modal'],
	}
);
