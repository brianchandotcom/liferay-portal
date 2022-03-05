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
	BUSINESS_ANUAL_GROSS_REVENUE:
		'What is your estimated annual gross revenue for the next 12 months?',
	BUSINESS_EMAIL: 'Business Email',
	BUSINESS_FEDERAL_EMPLOYER_IDENTIFICATION_NUMBER:
		'Does your business have a Federal Employer Identification Number (FEIN)?',
	BUSINESS_WEBSITE: 'Business Website (optional)',
	BUSINESS_YEAR_OPERATION: 'Does your business operate year round?',
	CITY: 'City',
	DIVING_BOARDS: 'Are there diving boards or slides?',
	DO_YOU_HAVE_RAYLIFE_POLICY: 'Do you have a Raylife Auto policy?',
	DO_YOU_OWN_THE_BUILDING_AT: 'Do you own the building at ',
	DO_YOU_SELL_PRODUCTS_UNDER_OWN_BRAND:
		'Do you sell products under your own brand or label?',
	DO_YOU_STORE_PERSONALITY_IDENTIFIABLE:
		'Do you store personally identifiable information about your customers?',
	EMPLOYEES_AMOUNT: 'How many full or part time employees do you have?',
	EMPLOYEES_ANNUAL_PAYROLL:
		'What do you anticipate your annual payroll will be for all employees over the next 12 months?',
	FEDERAL_EMPLOYER_IDENTIFICATION_NUMBER:
		'Federal Employer Identification Number (FEIN)',
	FIRST_NAME: 'First Name',
	HOW_MANY_SQUARE_FEET_OF_THE_BUILDING:
		'How many square feet of the building does your business occupy?',
	HOW_MANY_STORIES_IS_THIS_BUILDING: 'How many stories is this building?',
	HOW_MANY_TOTAL_SQUARE_FEET_IS_THE_BUILDING:
		'How many total square feet is the building?',
	LAST_NAME: 'Last Name',
	LEGAL_ENTITY: 'Legal Entity',
	OWNERS_ANNUAL_PAYROLL:
		'What do you anticipate your annual payroll will be for all owner(s) over the next 12 months?',
	PERCENT_OF_SALES_FROM_MERCHANDISE:
		'Percent of sales from used merchandise?',
	PHONE: 'Phone',
	PHYSICAL_ADDRESS: 'Physical Address',
	PHYSICAL_BUSINESS_ADDRESS: 'Physical Business Address',
	PREMISES: 'Premises',
	PRIMARY_LOCATION:
		'Is this the primary location where you conduct business?',
	STATE: 'State',
	SWIMMING_POOL: 'Are there swimming pool(s) on the premises?',
	WHAT_PERCENTAGE_OF_OVERALL_INVOLVE_DELIVERY:
		'What percentage of overall sales involve delivery?',
	WHAT_YEAR_WAS_THE_BUILDING_CONSTRUCTED:
		'What year was the building constructed?',
	YEAR_BUSINESS_STARTED: 'What year did you start your business?',
	YEAR_OF_INDUSTRY_EXPERIENCE: 'Years of industry experience?',
	YOUR_NAME: 'Your Name',
	ZIP: 'ZIP',
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
				hideContinueButton: true,
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
		mobileSubSections: [
			{
				active: true,
				hideContinueButton: true,
				hideInputLabel: false,
				title:
					SUBSECTION_KEYS.BUSINESS_FEDERAL_EMPLOYER_IDENTIFICATION_NUMBER,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.YEAR_BUSINESS_STARTED,
			},
			{
				active: false,
				hideContinueButton: true,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.BUSINESS_YEAR_OPERATION,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.EMPLOYEES_AMOUNT,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.BUSINESS_ANUAL_GROSS_REVENUE,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.OWNERS_ANNUAL_PAYROLL,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.EMPLOYEES_ANNUAL_PAYROLL,
			},
		],
		section: 'employees',
		subsection: '',
		title: 'Tell us about your employees!',
	},
	PROPERTY: {
		Component: FormProperty,
		active: false,
		id: 'PROPERTY',
		index: 5,
		mobileSubSections: [
			{
				active: true,
				hideContinueButton: true,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.DO_YOU_OWN_THE_BUILDING_AT,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.HOW_MANY_STORIES_IS_THIS_BUILDING,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.HOW_MANY_SQUARE_FEET_OF_THE_BUILDING,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title:
					SUBSECTION_KEYS.HOW_MANY_TOTAL_SQUARE_FEET_IS_THE_BUILDING,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.WHAT_YEAR_WAS_THE_BUILDING_CONSTRUCTED,
			},
			{
				active: false,
				hideContinueButton: true,
				hideInputLabel: true,
				title: SUBSECTION_KEYS.PRIMARY_LOCATION,
			},
			{
				active: false,
				hideContinueButton: false,
				hideInputLabel: false,
				title: SUBSECTION_KEYS.PREMISES,
			},
		],
		section: 'property',
		subsection: '',
		title: 'More about',
	},
};

export const STEP_ORDERED = Object.values(AVAILABLE_STEPS).sort(
	(stepA, stepB) => stepA.index - stepB.index
);
