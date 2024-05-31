/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as path from 'path';

const dependenciesFolder = path.join(__dirname, '..', 'dependencies');

export const products = {
	free_cloud: {
		categories: ['Analytics and Optimization'],
		cloudCompatible: true,
		compatibleOfferings: ['Self-Hosted', 'Self-Managed', 'Fully-Managed'],
		description: 'My free cloud app',
		logo: path.join(dependenciesFolder, 'marketplace-test-icon.png'),
		name: 'Free Cloud App',
		resourceRequirements: {
			cpus: 0,
			ram: 0,
		},
		tags: ['Business Use'],
		version: {
			notes: 'Lorem Ipsum...',
			version: '1.0.0',
		},
		zipFiles: [path.join(dependenciesFolder, 'marketplace-test-app.zip')],
	},
	paid_cloud: {
		categories: ['Customer Data Management'],
		cloudCompatible: true,
		compatibleOfferings: ['Self-Hosted', 'Self-Managed', 'Fully-Managed'],
		description: 'My paid cloud app',
		logo: path.join(dependenciesFolder, 'marketplace-test-icon.png'),
		name: 'Paid Cloud App',
		resourceRequirements: {
			cpus: 0,
			ram: 0,
		},
		tags: ['Client Extension Type'],
		version: {
			notes: 'Lorem Ipsum...',
			version: '1.0.0',
		},
		zipFiles: [path.join(dependenciesFolder, 'marketplace-test-app.zip')],
	},
} as const;
