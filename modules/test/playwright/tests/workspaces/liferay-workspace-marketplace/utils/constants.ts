/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as path from 'path';

import {PublishSolution} from '../types';

const dependenciesFolder = path.join(__dirname, '..', 'dependencies');

export const MARKETPLACE_CHANNEL = 'Marketplace Channel';

export const ORDER_ITEMS = {
	DECIMAL_QUANTITY: 1,
	QUANTITY: 1,
	UNIT_PRICE: 1,
};

export const products = {
	cloud_free: {
		categories: ['Analytics and Optimization'],
		cloudCompatible: true,
		compatibleOfferings: ['Liferay SaaS'],
		description: 'My free cloud app',
		dxpVersions: ['7.3'],
		logo: path.join(dependenciesFolder, 'marketplace-icon.png'),
		name: 'Cloud App - Free',
		priceModel: 'free',
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
		compatibleOfferings: ['Liferay SaaS'],
		description: 'My paid cloud app',
		dxpVersions: ['7.3'],
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
		support: {
			publisherWebsiteUrl: 'www.liferay.com',
			supportEmail: 'test@liferay.com',
			supportPhone: '+00 00 000000000',
		},
		tags: ['Client Extension Type'],
		version: {
			notes: 'Lorem Ipsum...',
			version: '1.0.0',
		},
		zipFiles: [path.join(dependenciesFolder, 'folder.marketplace.zip')],
	},
	dxp_free: {
		categories: ['Analytics and Optimization'],
		cloudCompatible: false,
		compatibleOfferings: ['Liferay Self-Hosted', 'Liferay PaaS'],
		description: 'My free Dxp app',
		dxpVersions: ['7.3'],
		logo: path.join(dependenciesFolder, 'marketplace-icon.png'),
		name: 'DXP App - Free',
		priceModel: 'free',
		tags: ['Business Use'],
		version: {
			notes: 'Lorem Ipsum...',
			version: '1.0.0',
		},
		zipFiles: [path.join(dependenciesFolder, 'folder.marketplace.zip')],
	},
	dxp_paid: {
		categories: ['Customer Data Management'],
		cloudCompatible: false,
		compatibleOfferings: ['Liferay Self-Hosted', 'Liferay PaaS'],
		description: 'My paid cloud app',
		dxpVersions: ['7.3'],
		logo: path.join(dependenciesFolder, 'marketplace-icon.png'),
		name: 'DXP App - Paid',
		price: {
			developer: 100,
			standard: 100,
		},
		priceModel: 'paid',
		resourceRequirements: {
			cpus: 0,
			ram: 0,
		},
		support: {
			publisherWebsiteUrl: 'www.liferay.com',
			supportEmail: 'test@liferay.com',
			supportPhone: '+00 00 000000000',
		},
		tags: ['Client Extension Type'],
		version: {
			notes: 'Lorem Ipsum...',
			version: '1.0.0',
		},
		zipFiles: [path.join(dependenciesFolder, 'folder.marketplace.zip')],
	},
} as const;

export const solutions: {
	[key: string]: PublishSolution;
} = {
	solution_1: {
		companyProfile: {
			description: 'Company Description',
			email: 'test@liferay.com',
			phone: '1111111111',
			website: 'https://liferay.com',
		},
		contactUs: {
			email: 'test@liferay.com',
		},
		details: {
			'text-block': {
				description: 'Text Block Description',
				title: 'Text Block Title',
			},
			'text-images': {
				description: 'Text Image Block Description',
				title: 'Text Image Block Title',
			},
		},
		header: {
			description: 'Solution Header Description',
			title: 'Solution Header Title',
		},
		profile: {
			description: 'Solution Test Description',
			name: 'Solution Test Name',
		},
	},
};

export const SOLUTION_PUBLISHER_ROLE = 'Solution Publisher';

export enum PAYMENT_STATUS {
	AUTHORIZED = '2',
	CANCELLED = '8',
	COMPLETED = '0',
	FAILED = '4',
	PENDING = '1',
}

export enum PRODUCT_WORKFLOW_STATUS_CODE {
	APPROVED = 0,
	PENDING = 1,
	DRAFT = 2,
}

export enum ORDER_WORKFLOW_STATUS_CODE {
	CANCELLED = '8',
	COMPLETED = '0',
	ON_HOLD = '20',
	PENDING = '1',
	PROCESSING = '10',
}

export enum ORDER_TYPES {
	DXPAPP = 'DXPAPP',
	CLOUDAPP = 'CLOUDAPP',
	SOLUTIONS7 = 'SOLUTIONS7',
	SOLUTIONS30 = 'SOLUTIONS30',
}
