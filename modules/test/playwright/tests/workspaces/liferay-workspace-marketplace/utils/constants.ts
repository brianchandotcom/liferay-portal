/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as path from 'path';

const dependenciesFolder = path.join(__dirname, '..', 'dependencies');

export const products = {
	cloud_free: {
		categories: ['Analytics and Optimization'],
		cloudCompatible: true,
		compatibleOfferings: ['Self-Hosted', 'Self-Managed', 'Fully-Managed'],
		description: 'My free cloud app',
		dxpVersions: ['Liferay Portal 7.4 GA109'],
		logo: path.join(dependenciesFolder, 'marketplace-icon.png'),
		name: 'Cloud App - Free',
		resourceRequirements: {
			cpus: 0,
			ram: 0,
		},
		tags: ['Business Use'],
		version: {
			notes: 'Lorem Ipsum...',
			version: '1.0.0',
		},
		zipFiles: [path.join(dependenciesFolder, 'folder.marketplace.zip')],
	},
	cloud_paid: {
		categories: ['Customer Data Management'],
		cloudCompatible: true,
		compatibleOfferings: ['Self-Hosted', 'Self-Managed', 'Fully-Managed'],
		description: 'My paid cloud app',
		dxpVersions: ['Liferay Portal 7.4 GA109'],
		logo: path.join(dependenciesFolder, 'marketplace-icon.png'),
		name: 'Cloud App - Paid',
		price: {
			developer: 100,
			standard: 100,
		},
		priceModel: 'paid',
		resourceRequirements: {
			cpus: 0,
			ram: 0,
		},
		tags: ['Client Extension Type'],
		version: {
			notes: 'Lorem Ipsum...',
			version: '1.0.0',
		},
		zipFiles: [path.join(dependenciesFolder, 'folder.marketplace.zip')],
	},
	dxp_free: {
		categories: ['Customer Data Management'],
		cloudCompatible: false,
		compatibleOfferings: ['Self-Hosted'],
		description: 'My free dxp app',
		dxpVersions: ['Liferay Portal 7.4 GA110', 'Liferay Portal 7.4 GA109'],
		logo: path.join(dependenciesFolder, 'marketplace-icon.png'),
		name: 'DXP App - Free',
		tags: ['Client Extension Type'],
		version: {
			notes: 'Lorem Ipsum...',
			version: '1.0.0',
		},
		zipFiles: [path.join(dependenciesFolder, 'folder.marketplace.zip')],
	},
} as const;
