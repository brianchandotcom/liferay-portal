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

import React, {useEffect, useState} from 'react';
import {useStepWizard} from '../../../hooks/useStepWizard';
import {AVAILABLE_STEPS} from '../../../utils/constants';
import {getLoadedContentFlag} from '../../../utils/util';

import {FormBasicBusinessInformation} from './Basics/BusinessInformation';
import {FormBasicBusinessType} from './Basics/BusinessType';
import {FormBasicProductQuote} from './Basics/ProductQuote';
import {FormBusiness} from './Business';
import {FormEmployees} from './Employees';
import {FormProperty} from './Property';

const compare = (a, b) => {
	return a.section === b.section && a.subsection === b.subsection;
};

export function Forms({form}) {
	const {selectedStep, setSection} = useStepWizard();
	const [loaded, setLoaded] = useState(false);
	const [loadedSections, setLoadedSections] = useState(false);
	const {backToEdit} = getLoadedContentFlag();

	useEffect(() => {
		if (!loaded && form) {
			setLoaded(true);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [form]);

	useEffect(() => {
		if (backToEdit) {
			loadSections();
		}
		else {
			setLoadedSections(true);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [loaded]);

	const loadSections = () => {
		const sectionFormKeys = Object.keys(form);

		const stepName = sectionFormKeys[
			sectionFormKeys.length - 1
		]?.toLowerCase();

		switch (stepName) {
			case 'basics':
				const stepBasicName = Object.keys(form?.basics)[
					Object.keys(form?.basics).length - 1
				]?.toLowerCase();

				if (stepBasicName === 'businessInformation') {
					setSection(AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION);
				}
				else if (stepBasicName === 'business-type') {
					setSection(AVAILABLE_STEPS.BASICS_BUSINESS_TYPE);
				}
				else {
					setSection(AVAILABLE_STEPS.BASICS_PRODUCT_QUOTE);
				}
				break;
			case 'business':
				setSection(AVAILABLE_STEPS.BUSINESS);
				break;
			case 'employees':
				setSection(AVAILABLE_STEPS.EMPLOYEES);
				break;
			case 'property':
				setSection(AVAILABLE_STEPS.PROPERTY);
				break;
			default:
				break;
		}
		setLoadedSections(true);
	};

	if (!loaded || !loadedSections) {
		return null;
	}

	if (compare(selectedStep, AVAILABLE_STEPS.BASICS_BUSINESS_TYPE)) {
		return <FormBasicBusinessType form={form} />;
	}

	if (compare(selectedStep, AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION)) {
		return <FormBasicBusinessInformation form={form} />;
	}

	if (compare(selectedStep, AVAILABLE_STEPS.BASICS_PRODUCT_QUOTE)) {
		return <FormBasicProductQuote form={form} />;
	}

	if (compare(selectedStep, AVAILABLE_STEPS.BUSINESS)) {
		return <FormBusiness form={form} />;
	}

	if (compare(selectedStep, AVAILABLE_STEPS.EMPLOYEES)) {
		return <FormEmployees form={form} />;
	}

	if (compare(selectedStep, AVAILABLE_STEPS.PROPERTY)) {
		return <FormProperty form={form} />;
	}

	return <div></div>;
}
