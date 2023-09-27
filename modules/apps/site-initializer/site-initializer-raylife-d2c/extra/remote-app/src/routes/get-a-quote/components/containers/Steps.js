/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

export function Steps() {
	const {
		selectedStep: {section},
		setSection,
	} = useStepWizard();
	const {
		state: {
			dimensions: {
				device: {isMobile},
			},
			percentage,
		},
	} = useContext(AppContext);

	return (
		<StepList>
			<StepItem
				isMobile={isMobile}
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
				isMobile={isMobile}
				onClick={() => setSection(AVAILABLE_STEPS.BUSINESS)}
				percentage={percentage[AVAILABLE_STEPS.BUSINESS.section]}
				selected={section === AVAILABLE_STEPS.BUSINESS.section}
			>
				Business
			</StepItem>

			<StepItem
				isMobile={isMobile}
				onClick={() => setSection(AVAILABLE_STEPS.EMPLOYEES)}
				percentage={percentage[AVAILABLE_STEPS.EMPLOYEES.section]}
				selected={section === AVAILABLE_STEPS.EMPLOYEES.section}
			>
				Employees
			</StepItem>

			<StepItem
				isMobile={isMobile}
				onClick={() => setSection(AVAILABLE_STEPS.PROPERTY)}
				percentage={percentage[AVAILABLE_STEPS.PROPERTY.section]}
				selected={section === AVAILABLE_STEPS.PROPERTY.section}
			>
				Property
			</StepItem>
		</StepList>
	);
}
