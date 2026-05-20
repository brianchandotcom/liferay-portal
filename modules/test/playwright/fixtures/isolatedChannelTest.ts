/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../helpers/ApiHelpers';
import getRandomString from '../utils/getRandomString';
import {backendPageTest} from './backendPageTest';

type AnalyticsChannel = {
	id: string;
	name: string;
};

type AnalyticsProject = {
	groupId: string;
	name: string;
};

const isolatedChannelTest = backendPageTest.extend<{
	analyticsChannel: AnalyticsChannel;
	project: AnalyticsProject;
}>({
	analyticsChannel: [
		async ({backendPage, project}, use) => {
			const apiHelpers = new ApiHelpers(backendPage);

			let analyticsChannel: AnalyticsChannel;

			try {
				analyticsChannel =
					await apiHelpers.jsonWebServicesOSBFaro.createChannel(
						'My Property - ' + getRandomString(),
						project.groupId
					);

				await use(analyticsChannel);
			}
			finally {
				if (analyticsChannel?.id) {
					await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
						`[${analyticsChannel.id}]`,
						project.groupId
					);
				}
			}
		},
		{auto: true},
	],
	project: async ({backendPage}, use) => {
		const apiHelpers = new ApiHelpers(backendPage);

		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await use(project);
	},
});

export {isolatedChannelTest};
