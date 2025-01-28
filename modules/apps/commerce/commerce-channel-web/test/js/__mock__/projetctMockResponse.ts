/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const projectsMockResponse = {
	environments: [
		{
			isExtensionEnvironment: true,
			projectId: 'extlxcv1e6-ext37110510',
		},
		{
			isExtensionEnvironment: false,
			projectId: 'extlxcv1e6-ext37574',
		},
		{
			isExtensionEnvironment: true,
			projectId: 'extlxcv1e6-ext37154881',
		},
		{
			isExtensionEnvironment: true,
			projectId: 'extlxcv1e6-ext37148631',
		},
	],
	rootProjectId: 'extlxcv1e6',
	rootProjectPlanUsage: {
		cpu: {
			free: 297,
			limit: 300,
			used: 3,
		},
		instance: {
			free: 292,
			limit: 300,
			used: 8,
		},
		memory: {
			free: 304876,
			limit: 307200,
			used: 2324,
		},
	},
};

export default projectsMockResponse;
