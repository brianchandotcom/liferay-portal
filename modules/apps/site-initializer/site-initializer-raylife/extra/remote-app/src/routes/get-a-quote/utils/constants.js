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

import {FormBasicBusinessInformation} from '../components/containers/Forms/Basics/BusinessInformation';
import {FormBasicBusinessType} from '../components/containers/Forms/Basics/BusinessType';
import {FormBasicProductQuote} from '../components/containers/Forms/Basics/ProductQuote';
import {FormBusiness} from '../components/containers/Forms/Business';
import {FormEmployees} from '../components/containers/Forms/Employees';
import {FormProperty} from '../components/containers/Forms/Property';

export const TOTAL_OF_FIELD = {
	BASICS: 10,
	EMPLOYEES: 7,
	PROPERTY: 6,
};

export const SUBSECTION_KEYS = {
	BUSINESS_EMAIL: 'Business Email',
	BUSINESS_WEBSITE: 'Business Website',
	DO_YOU_HAVE_RAYLIFE_POLICY: 'Do you have a Raylife Auto policy?',
	DO_YOU_SELL_PRODUCTS_UNDER_OWN_BRAND:
		'Do you sell products under your own brand or label?',
	DO_YOU_STORE_PERSONALITY_IDENTIFIABLE:
		'Do you store personally identifiable information about your customers?',
	LEGAL_ENTITY: 'Legal Entity',
	PERCENT_OF_SALES_FROM_MERCHANDISE:
		'Percent of sales from used merchandise?',
	PHONE: 'Phone',
	PHYSICAL_ADDRESS: 'Physical Address',
	WHAT_PERCENTAGE_OF_OVERALL_INVOLVE_DELIVERY:
		'What percentage of overall sales involve delivery?',
	YEAR_OF_INDUSTRY_EXPERIENCE: 'Years of industry experience?',
	YOUR_NAME: 'Your Name',
};

export const AVAILABLE_STEPS = {
	BASICS_BUSINESS_INFORMATION: {
		Component: FormBasicBusinessInformation,
		active: false,
		id: 'BASICS_BUSINESS_INFORMATION',
		index: 2,
		mobileSubSections: [
			{
				active: true,
				hideInputLabel: false,
				title: SUBSECTION_KEYS.YOUR_NAME,
			},
			{
				active: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.BUSINESS_EMAIL,
			},
			{
				active: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.PHONE,
			},
			{
				active: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.BUSINESS_WEBSITE,
			},
			{
				active: false,
				hideInputLabel: false,
				title: SUBSECTION_KEYS.PHYSICAL_ADDRESS,
			},
		],
		section: 'basics',
		subsection: 'businessInformation',
		title: 'Just the business basics!',
	},
	BASICS_BUSINESS_TYPE: {
		Component: FormBasicBusinessType,
		active: false,
		id: 'BASICS_BUSINESS_TYPE',
		index: 1,
		section: 'basics',
		subsection: 'business-type',
		title: 'Select a primary industry.',
	},
	BASICS_PRODUCT_QUOTE: {
		Component: FormBasicProductQuote,
		active: true,
		id: 'BASICS_PRODUCT_QUOTE',
		index: 0,
		section: 'basics',
		subsection: 'product-quote',
		title: 'Welcome! Select a product.',
	},
	BUSINESS: {
		Component: FormBusiness,
		active: true,
		id: 'BUSINESS',
		index: 3,
		mobileSubSections: [
			{
				active: true,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.YEAR_OF_INDUSTRY_EXPERIENCE,
			},
			{
				active: false,
				hideContinueButton: true,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.DO_YOU_STORE_PERSONALITY_IDENTIFIABLE,
			},
			{
				active: false,
				hideContinueButton: true,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.DO_YOU_HAVE_RAYLIFE_POLICY,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.LEGAL_ENTITY,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.PERCENT_OF_SALES_FROM_MERCHANDISE,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.DO_YOU_SELL_PRODUCTS_UNDER_OWN_BRAND,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title:
					SUBSECTION_KEYS.WHAT_PERCENTAGE_OF_OVERALL_INVOLVE_DELIVERY,
			},
		],
		section: 'business',
		subsection: '',
		title: "Let's get to know your business!",
	},
	EMPLOYEES: {
		Component: FormEmployees,
		active: false,
		id: 'EMPLOYEES',
		index: 4,
		section: 'employees',
		subsection: '',
		title: 'Tell us about your employees!',
	},
	PROPERTY: {
		Component: FormProperty,
		active: false,
		id: 'PROPERTY',
		index: 5,
		section: 'property',
		subsection: '',
		title: 'More about',
	},
};

export const STEP_ORDERED = Object.values(AVAILABLE_STEPS).sort(
	(stepA, stepB) => stepA.index - stepB.index
);
