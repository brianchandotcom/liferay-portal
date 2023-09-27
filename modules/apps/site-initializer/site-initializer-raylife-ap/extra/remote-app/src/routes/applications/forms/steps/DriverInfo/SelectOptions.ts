/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type millitaryAffiliationOptionsType = {
	className: string;
	label: string;
};

export const millitaryAffiliationOptions: millitaryAffiliationOptionsType[] = [
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'Choose an option',
	},
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'None',
	},
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'US Air Force',
	},
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'US Coast Guard',
	},
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'US Marine Corps',
	},
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'US Military',
	},
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'US Navy',
	},
];

type accidentCitationOptionsType = {
	className: string;
	label: string;
};

export const accidentCitationOptions: accidentCitationOptionsType[] = [
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'Choose an option',
	},

	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'Accident - Not at fault',
	},
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'Accident - At fault',
	},
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'Accident - Car rolled over',
	},
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'Citation - Driving under the influence',
	},
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'Citation - Failing to Yield',
	},
	{
		className: ' text-paragraph-sm text-brand-primary text-center',
		label: 'US Citation - Speeding',
	},
];

type genderOptionsType = {
	label: string;
	value: string;
};

export const genderOptions: genderOptionsType[] = [
	{
		label: '',
		value: '',
	},
	{
		label: 'Choose an option',
		value: 'Choose an option',
	},
	{
		label: 'Male',
		value: 'Male',
	},
	{
		label: 'Female',
		value: 'Female',
	},
];

type maritalStatusOptionsType = {
	label: string;
	value: string;
};

export const maritalStatusOptions: maritalStatusOptionsType[] = [
	{
		label: '',
		value: '',
	},
	{
		label: 'Choose an option',
		value: 'Choose an option',
	},
	{
		label: 'Married',
		value: 'Married',
	},
	{
		label: 'Separated',
		value: 'Separated',
	},
	{
		label: 'Single',
		value: 'Single',
	},
	{
		label: 'Widowed',
		value: 'Widowed',
	},
];

type relationToContactOptionsType = {
	label: string;
	value: string;
};

export const relationToContactOptions: relationToContactOptionsType[] = [
	{
		label: '',
		value: '',
	},
	{
		label: 'Choose an option',
		value: 'Choose an option',
	},
	{
		label: 'Self',
		value: 'Self',
	},
	{
		label: 'Spouse',
		value: 'Spouse',
	},
	{
		label: 'Dependent',
		value: 'Dependent',
	},
	{
		label: 'Relative',
		value: 'Relative',
	},
];

type occupationOptionsType = {
	label: string;
	value: string;
};

export const occupationOptions: occupationOptionsType[] = [
	{
		label: '',
		value: '',
	},
	{
		label: 'Choose an option',
		value: 'Choose an option',
	},
	{
		label: 'Accountant',
		value: 'Accountant',
	},
	{
		label: 'Dentist',
		value: 'Dentist',
	},
	{
		label: 'Engineer',
		value: 'Engineer',
	},
	{
		label: 'Government',
		value: 'Government',
	},
	{
		label: 'Other',
		value: 'Other',
	},
];

type highestEducationType = {
	label: string;
	value: string;
};

export const highestEducationOptions: highestEducationType[] = [
	{
		label: '',
		value: '',
	},
	{
		label: 'Choose an option',
		value: 'Choose an option',
	},
	{
		label: 'High School',
		value: 'High School',
	},
	{
		label: 'College',
		value: 'College',
	},
	{
		label: 'Masters',
		value: 'Masters',
	},
	{
		label: 'PHD',
		value: 'PHD',
	},
];

type governmentAffiliationType = {
	label: string;
	value: string;
};

export const governmentAffiliationOptions: governmentAffiliationType[] = [
	{
		label: '',
		value: '',
	},
	{
		label: 'Choose an option',
		value: 'Choose an option',
	},
	{
		label: 'ABC Sheriff Association',
		value: 'ABC Sheriff Association',
	},
	{
		label: 'FBI Academy Associates',
		value: 'FBI Academy Associates',
	},
	{
		label: 'State Firefighters Association',
		value: 'State Firefighters Association',
	},
];
