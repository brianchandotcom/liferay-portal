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

type primaryUsageOptionsType = {
	name: string;
};

export const primaryUsageOptions: primaryUsageOptionsType[] = [
	{
		name: '',
	},
	{
		name: 'Choose an option',
	},
	{
		name: 'Commuting',
	},
	{
		name: 'Business',
	},
	{
		name: 'Pleasure',
	},
];

type ownershipOptionsType = {
	name: string;
};

export const ownershipOptions: ownershipOptionsType[] = [
	{
		name: '',
	},
	{
		name: 'Choose an option',
	},
	{
		name: 'Own',
	},
	{
		name: 'Loan',
	},
	{
		name: 'Lease',
	},
];

type modelOptionsType = {
	name: string;
};

type makeOptionsType = {
	checked: boolean;
	model: modelOptionsType[];
	name: string;
};

export const makeOptions: makeOptionsType[] = [
	{
		checked: true,
		model: [],
		name: '',
	},
	{
		checked: false,
		model: [],
		name: 'Choose an option',
	},
	{
		checked: false,
		model: [
			{name: ''},
			{name: 'Choose an option'},
			{name: 'Edge Se'},
			{name: 'F-150 super 250 xl'},
			{name: 'Focus s sedan'},
			{name: 'Mustang Gt Fastback'},
		],
		name: 'ford',
	},
	{
		checked: false,
		model: [
			{name: ''},
			{name: 'Choose an option'},
			{name: 'Corolla le'},
			{name: 'Camry Hybrid xle'},
			{name: 'Highlander se'},
			{name: 'Tundra Sr5'},
		],
		name: 'toyota',
	},
	{
		checked: false,
		model: [
			{name: ''},
			{name: 'Choose an option'},
			{name: 's90 r-design'},
			{name: 'v90 cross country'},
			{name: 'xc90 incription'},
		],
		name: 'volvo',
	},
];

type yearsOptionsType = {
	className: string;
	label: string;
};

export const yearsOptions: yearsOptionsType[] = [
	{
		className:
			'font-weight-bolder text-paragraph-sm text-brand-primary text-uppercase text-center',
		label: 'Choose an option',
	},
	{
		className:
			'font-weight-bolder text-paragraph-sm text-brand-primary text-uppercase text-center',
		label: '2019',
	},

	{
		className:
			'font-weight-bolder text-paragraph-sm text-brand-primary text-uppercase text-center',
		label: '2018',
	},
	{
		className:
			'font-weight-bolder text-paragraph-sm text-brand-primary text-uppercase text-center',
		label: '2017',
	},
	{
		className:
			'font-weight-bolder text-paragraph-sm text-brand-primary text-uppercase text-center',
		label: '2016',
	},
	{
		className:
			'font-weight-bolder text-paragraph-sm text-brand-primary text-uppercase text-center',
		label: '2015',
	},
	{
		className:
			'font-weight-bolder text-paragraph-sm text-brand-primary text-uppercase text-center',
		label: '2014',
	},
	{
		className:
			'font-weight-bolder text-paragraph-sm text-brand-primary text-uppercase text-center',
		label: '2013',
	},
];
