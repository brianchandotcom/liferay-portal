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
	A => {
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
					fireOnce: true
				});

				Liferay.after('initStagingBar', () => {
					var body = A.getBody();

					if (body.hasClass('has-staging-bar')) {
						var stagingLevel3 = A.one(
							'.staging-bar-level-3-message'
						);

						body.addClass(
							stagingLevel3 === null
								? 'staging-ready'
								: 'staging-ready-level-3'
						);
					}
				});

				Liferay.fire('initStagingBar', config);
			}
		};

		Liferay.StagingBar = StagingBar;
	},
	'',
	{
		requires: ['aui-io-plugin-deprecated', 'aui-modal']
	}
);
