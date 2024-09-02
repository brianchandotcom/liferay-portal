/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode, useContext, useEffect, useState} from 'react';

import {FormLayoutDataItem} from '../../types/layout_data/FormLayoutDataItem';
import getLayoutDataItemUniqueClassName from '../utils/getLayoutDataItemUniqueClassName';

const FormStepContext = React.createContext<{
	activeStep: number;
}>({
	activeStep: 0,
});

function FormStepContextProvider({
	children,
	form,
}: {
	children: ReactNode;
	form: FormLayoutDataItem;
}) {
	const [activeStep, setActiveStep] = useState<number>(0);

	useEffect(() => {
		const onStepChange = ({
			emitter,
			step,
		}: {
			emitter: HTMLElement;
			step: number | 'next' | 'previous';
		}) => {
			const formElement = document.querySelector(
				`.${getLayoutDataItemUniqueClassName(form.itemId)}`
			);

			// Return if the emitter is not in this form

			if (!formElement?.contains(emitter)) {
				return;
			}

			const nextActiveStep = getNextActiveStep(form, activeStep, step);

			if (nextActiveStep !== activeStep) {
				setActiveStep(nextActiveStep);
			}
		};

		Liferay.on('formFragment:changeStep', onStepChange);

		return () =>
			Liferay.detach(
				'formFragment:changeStep',
				onStepChange as () => void
			);
	}, [activeStep, form]);

	return (
		<FormStepContext.Provider
			value={{
				activeStep,
			}}
		>
			{children}
		</FormStepContext.Provider>
	);
}

function useActiveStep() {
	return useContext(FormStepContext).activeStep;
}

function getNextActiveStep(
	form: FormLayoutDataItem,
	activeStep: number,
	eventStep: number | 'next' | 'previous'
): number {
	const numberOfSteps = form.config.numberOfSteps;

	if (eventStep === 'next') {
		if (activeStep + 1 < numberOfSteps) {
			return activeStep + 1;
		}
	}
	else if (eventStep === 'previous') {
		if (activeStep !== 0) {
			return activeStep - 1;
		}
	}
	else {
		return eventStep;
	}

	return activeStep;
}

export {FormStepContextProvider, useActiveStep};
