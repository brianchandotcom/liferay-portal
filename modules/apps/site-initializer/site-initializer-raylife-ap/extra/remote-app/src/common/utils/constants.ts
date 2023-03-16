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

export type ConstantListType = {[keys: string]: string};

type ConstantsType = {
	APPLICATION_STATUS: {[keys: string]: {INDEX: number; NAME: string}};
	CLAIM_STATUS: {[keys: string]: {INDEX: number; NAME: string}};
	DEVICES: ConstantListType;
	MONTHS_ABREVIATIONS: string[];
	REPAIR_ACTIONS_OPTIONS: ConstantListType[];
	US_STATES: ConstantListType[];
};

export const CONSTANTS: ConstantsType = {
	APPLICATION_STATUS: {
		approved: {INDEX: 8, NAME: 'approved'},
		bound: {INDEX: 6, NAME: 'bound'},
		inInvestigation: {INDEX: 7, NAME: 'InInvestigation'},
		incomplete: {INDEX: 1, NAME: 'incomplete'},
		open: {INDEX: 0, NAME: 'open'},
		quoted: {INDEX: 2, NAME: 'quoted'},
		rejected: {INDEX: 5, NAME: 'rejected'},
		reviewed: {INDEX: 4, NAME: 'reviewed'},
		underwriting: {INDEX: 3, NAME: 'underwriting'},
	},

	CLAIM_STATUS: {
		approved: {INDEX: 3, NAME: 'approved'},
		claimSubmitted: {INDEX: 0, NAME: 'claimSubmitted'},
		declined: {INDEX: 7, NAME: 'declined'},
		inEstimation: {INDEX: 2, NAME: 'inEstimation'},
		inInvestigation: {INDEX: 1, NAME: 'inInvestigation'},
		pendingSettlement: {INDEX: 5, NAME: 'pendingSettlement'},
		repair: {INDEX: 4, NAME: 'repair'},
		settled: {INDEX: 6, NAME: 'settled'},
	},

	DEVICES: {
		DESKTOP: 'DESKTOP',
		PHONE: 'PHONE',
		TABLET: 'TABLET',
	},

	MONTHS_ABREVIATIONS: [
		'Jan',
		'Feb',
		'Mar',
		'Apr',
		'May',
		'Jun',
		'Jul',
		'Aug',
		'Sep',
		'Oct',
		'Nov',
		'Dec',
	],

	REPAIR_ACTIONS_OPTIONS: [
		{
			city: 'Los Angeles, CA 90005',
			name: 'Lyons Auto Pro Plus',
			phone: '(310) 987-4567',
			street: '675 Main Street',
		},
		{
			city: 'Los Angeles, CA 90010',
			name: 'Diamond Auto Repair',
			phone: '(310) 458-3492',
			street: '1432 Wilshire Blvd.',
		},
		{
			city: 'Los Angeles, CA 90027',
			name: 'Hyperion Automotive Center',
			phone: '(323) 665-7864',
			street: '2938 Hyperion Ave',
		},
	],

	US_STATES: [
		{
			label: '',
			value: '',
		},
		{
			label: 'CHOOSE AN OPTION',
			value: 'CHOOSE AN OPTION',
		},
		{
			label: 'CA',
			value: 'CA',
		},
		{
			label: 'NV',
			value: 'NV',
		},
		{
			label: 'NY',
			value: 'NY',
		},
	],
};

export const productAutoERC = 'RAYAP-001';
