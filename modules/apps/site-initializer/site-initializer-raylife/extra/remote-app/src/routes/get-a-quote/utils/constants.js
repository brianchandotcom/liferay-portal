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

export const AVAILABLE_STEPS = {
	BASICS_BUSINESS_INFORMATION: {
		Component: FormBasicBusinessInformation,
		index: 2,
		section: 'basics',
		subsection: 'businessInformation',
		title: 'Just the business basics!',
	},
	BASICS_BUSINESS_TYPE: {
		Component: FormBasicBusinessType,
		index: 1,
		section: 'basics',
		subsection: 'business-type',
		title: 'Select a primary industry.',
	},
	BASICS_PRODUCT_QUOTE: {
		Component: FormBasicProductQuote,
		index: 0,
		section: 'basics',
		subsection: 'product-quote',
		title: 'Welcome! Select a product.',
	},
	BUSINESS: {
		Component: FormBusiness,
		index: 3,
		section: 'business',
		subsection: '',
		title: "Let's get to know your business!",
	},
	EMPLOYEES: {
		Component: FormEmployees,
		index: 4,
		section: 'employees',
		subsection: '',
		title: 'Tell us about your employees!',
	},
	PROPERTY: {
		Component: FormProperty,
		index: 5,
		section: 'property',
		subsection: '',
		title: 'More about',
	},
};

export const STEP_ORDERED = Object.values(AVAILABLE_STEPS).sort(
	(stepA, stepB) => stepA.index - stepB.index
);
