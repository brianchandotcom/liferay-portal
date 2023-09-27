/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import {useStepWizard} from '../../../hooks/useStepWizard';
import {AVAILABLE_STEPS} from '../../../utils/constants';
import {getLoadedContentFlag} from '../../../utils/util';

export function Forms({form, formActionContext: {isMobileDevice}}) {
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
			return loadSections();
		}

		setLoadedSections(true);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [loaded]);

	// eslint-disable-next-line no-unused-vars
	const loadSections = () => {
		const sectionFormKeys = Object.keys(form).filter(
			(section) => section !== 'raylife-form-input'
		);

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

	const Component = selectedStep?.Component || (() => <></>);

	return <Component form={form} isMobile={isMobileDevice} />;
}
