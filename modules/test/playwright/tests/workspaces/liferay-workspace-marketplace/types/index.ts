/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type Steps =
	| 'build'
	| 'create'
	| 'licensing'
	| 'pricing'
	| 'profile'
	| 'storefront'
	| 'submit'
	| 'support'
	| 'version';

export type PublishProductPayload = {
	categories: string[];
	cloudCompatible: boolean;
	compatibleOfferings: string[];
	description: string;
	logo: string;
	name: string;
	resourceRequirements: {
		cpus: number;
		ram: number;
	};
	tags: string[];
	version: {
		notes: string;
		version: string;
	};
	zipFiles: string[];
};

export const PUBLISH_SOLUTION = {
	header: {
		description: 'Solution Header Description',
		title: 'Solution Header Title',
	},
	profile: {
		description: 'Solution Test Description',
		name: 'Solution Test Name',
	},
};
