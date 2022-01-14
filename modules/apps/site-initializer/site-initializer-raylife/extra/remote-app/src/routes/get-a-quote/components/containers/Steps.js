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

import React, {useContext} from 'react';
import {StepItem} from '../../../../common/components/fragments/Step/Item';
import {StepList} from '../../../../common/components/fragments/Step/List';
import {
	STORAGE_KEYS,
	Storage,
} from '../../../../common/services/liferay/storage';
import {AppContext} from '../../context/AppContextProvider';
import {useStepWizard} from '../../hooks/useStepWizard';
import {AVAILABLE_STEPS} from '../../utils/constants';

export function Steps({isMobileDevice}) {
	const {
		selectedStep: {section},
		setSection,
	} = useStepWizard();
	const {
		state: {percentage},
	} = useContext(AppContext);

	return (
		<StepList>
			<StepItem
				isMobileDevice={isMobileDevice}
				onClick={() => {
					Storage.setItem(STORAGE_KEYS.BASIC_STEP_CLICKED, true);
					setSection(AVAILABLE_STEPS.BASICS_PRODUCT_QUOTE);
				}}
				percentage={
					percentage[AVAILABLE_STEPS.BASICS_PRODUCT_QUOTE.section]
				}
				selected={
					section === AVAILABLE_STEPS.BASICS_PRODUCT_QUOTE.section
				}
			>
				Basics
			</StepItem>

			<StepItem
				isMobileDevice={isMobileDevice}
				onClick={() => setSection(AVAILABLE_STEPS.BUSINESS)}
				percentage={percentage[AVAILABLE_STEPS.BUSINESS.section]}
				selected={section === AVAILABLE_STEPS.BUSINESS.section}
			>
				Business
			</StepItem>

			<StepItem
				isMobileDevice={isMobileDevice}
				onClick={() => setSection(AVAILABLE_STEPS.EMPLOYEES)}
				percentage={percentage[AVAILABLE_STEPS.EMPLOYEES.section]}
				selected={section === AVAILABLE_STEPS.EMPLOYEES.section}
			>
				Employees
			</StepItem>

			<StepItem
				isMobileDevice={isMobileDevice}
				onClick={() => setSection(AVAILABLE_STEPS.PROPERTY)}
				percentage={percentage[AVAILABLE_STEPS.PROPERTY.section]}
				selected={section === AVAILABLE_STEPS.PROPERTY.section}
			>
				Property
			</StepItem>
		</StepList>
	);
}
