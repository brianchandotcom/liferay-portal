/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-staging',
	() => {
		const StagingBar = {
			init(config) {
				const instance = this;

				const namespace = config.namespace;

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
