/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const asahConfig = {
	environment: {
		apiToken: process.env.OSB_ASAH_API_TOKEN
			? process.env.OSB_ASAH_API_TOKEN
			: '',
		backendUrl: process.env.OSB_ASAH_BACKEND_URL
			? process.env.OSB_ASAH_BACKEND_URL
			: 'http://osbasahbackend:8080',
		batchCuratordUrl: process.env.OSB_ASAH_BATCH_CURATOR_URL
			? process.env.OSB_ASAH_BATCH_CURATOR_URL
			: 'http://osbasahbatchcurator:8080',
		projectId: process.env.OSB_ASAH_PROJECT_ID
			? process.env.OSB_ASAH_PROJECT_ID
			: 'osbasah',
	},
};

export {asahConfig};
