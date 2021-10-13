import React, {useEffect, useState} from 'react';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';
import {useStepWizard} from '~/routes/get-a-quote/hooks/useStepWizard';
import {AVAILABLE_STEPS} from '~/routes/get-a-quote/utils/constants';

import {FormBasicBusinessInformation} from './Basics/BusinessInformation';
import {FormBasicBusinessType} from './Basics/BusinessType';
import {FormBasicProductQuote} from './Basics/ProductQuote';
import {FormBusiness} from './Business';
import {FormEmployees} from './Employees';
import {FormProperty} from './Property';

const compare = (a, b) => {
	return a.section === b.section && a.subsection === b.subsection;
};

export const Forms = ({form}) => {
	const {selectedStep, setSection} = useStepWizard();
	const [loaded, setLoaded] = useState(false);
	const [loadedSections, setLoadedSections] = useState(false);

	useEffect(() => {
		if (!loaded && form) {
			setLoaded(true);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [form]);

	useEffect(() => {
		if (
			loaded &&
			Storage.itemExist(STORAGE_KEYS.BACK_TO_EDIT) &&
			JSON.parse(Storage.getItem(STORAGE_KEYS.BACK_TO_EDIT))
		) {
			loadSections();
		} else {
			setLoadedSections(true);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [loaded]);

	const loadSections = () => {
		const stepName = Object.keys(form)[
			Object.keys(form).length - 1
		].toLowerCase();
		switch (stepName) {
			case 'basics':
				const stepBasicName = Object.keys(form?.basics)[
					Object.keys(form?.basics).length - 1
				].toLowerCase();

				if (stepBasicName === 'businessInformation') {
					setSection(AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION);
				} else if (stepBasicName === 'business-type') {
					setSection(AVAILABLE_STEPS.BASICS_BUSINESS_TYPE);
				} else {
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
};
