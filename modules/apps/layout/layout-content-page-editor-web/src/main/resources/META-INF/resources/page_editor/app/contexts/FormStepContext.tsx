/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode, useContext, useEffect, useState} from 'react';

import {FormLayoutDataItem} from '../../types/layout_data/FormLayoutDataItem';
import {useGlobalContext} from '../contexts/GlobalContext';
import {useActivationOrigin, useActiveItemIds} from '../js-index';
import {getFormStepIndex} from '../utils/getFormStepIndex';
import getLayoutDataItemTopperUniqueClassName from '../utils/getLayoutDataItemTopperUniqueClassName';
import getLayoutDataItemUniqueClassName from '../utils/getLayoutDataItemUniqueClassName';
import {useSelectorRef} from './StoreContext';

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

	const activationOrigin = useActivationOrigin();
	const activeItemIds = useActiveItemIds();

	const globalContext = useGlobalContext();
	const layoutDataRef = useSelectorRef((state) => state.layoutData);

	useEffect(() => {
		const onStepChange = ({
			emitter,
			step,
		}: {
			emitter: HTMLElement;
			step: number | 'next' | 'previous';
		}) => {
			const formElement = globalContext.document.querySelector(
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

		(globalContext.window as any).Liferay.on(
			'formFragment:changeStep',
			onStepChange
		);

		return () =>
			(globalContext.window as any).Liferay.detach(
				'formFragment:changeStep',
				onStepChange as () => void
			);
	}, [activeStep, form, globalContext.document, globalContext.window]);

	useEffect(() => {
		if (activationOrigin === 'sidebar') {
			const itemId = activeItemIds[activeItemIds.length - 1];
			const item = layoutDataRef.current?.items[itemId];

			if (!item) {
				return;
			}

			const formStepIndex = getFormStepIndex(item, layoutDataRef.current);

			if (formStepIndex !== null && formStepIndex !== activeStep) {
				const element = globalContext.document.querySelector(
					`.${getLayoutDataItemTopperUniqueClassName(itemId)}`
				);

				Liferay.fire('formFragment:changeStep', {
					emitter: element,
					step: formStepIndex,
				});

				setActiveStep(formStepIndex);
			}
		}
	}, [
		activationOrigin,
		activeItemIds,
		activeStep,
		form,
		globalContext.document,
		layoutDataRef,
	]);

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
