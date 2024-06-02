/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Header = {
	description: string;
	title: string;
};

type CompanyProfile = {
	description: string;
	email: string;
	phone: string;
	website: string;
};

type ContactUs = {
	email: string;
};

export type PublishProductPayload = {
	categories: string[];
	cloudCompatible: boolean;
	compatibleOfferings: string[];
	description: string;
	dxpVersions: string[];
	logo: string;
	name: string;
	price?: {
		developer?: number;
		standard?: number;
	};
	priceModel: 'free' | 'paid';
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

type PublishSolution = {
	companyProfile: CompanyProfile;
	contactUs: ContactUs;
	details: SolutionDetails;
	header: Header;
	profile: SolutionProfile;
};

type SolutionDetails = {
	'text-block': {
		description: string;
		title: string;
	};
	'text-images': {
		description: string;
		title: string;
	};
};

type SolutionProfile = {
	description: string;
	name: string;
};

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

export const PUBLISH_SOLUTION: PublishSolution = {
	companyProfile: {
		description: 'Company Description',
		email: 'test@liferay.com',
		phone: '1111111111',
		website: 'liferay.com',
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
};
