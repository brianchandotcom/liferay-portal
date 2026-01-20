/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';
import React, {useState} from 'react';

export interface IGenericStepProps {
	backURL?: string;
	exportURL?: string;
	nextStep?: ESteps;
	onChangeStep: (step: ESteps) => void;
	previousStep?: ESteps;
}

export interface IPages<T, K> {
	Component: React.FC<{children?: React.ReactNode | undefined} & T>;
	key: K;
	title: string;
}

export enum ESteps {
	Setup = 0,
	DataSelection = 1,
	Settings = 2,
}

export interface IStepProps<T, K> extends IPages<T, K> {
	available: boolean;
}

export function Wizard({
	backURL,
	externalSteps,
}: {
	backURL: string;
	externalSteps: IStepProps<IGenericStepProps, ESteps>[];
}) {
	const [step, setStep] = useState<ESteps>(ESteps.Setup);
	const [steps, setSteps] = useState(externalSteps);

	return (
		<>
			<ClayMultiStepNav center className="w-60" indicatorLabel="top">
				{steps.map(({available, key: nextStep, title}, index) => {
					const completed = step > nextStep && nextStep !== step;

					return (
						<ClayMultiStepNav.Item
							active={nextStep === step}
							complete={step > nextStep}
							data-testid={
								nextStep === step && 'multi-step-item-active'
							}
							expand={index + 1 !== externalSteps.length}
							key={nextStep}
						>
							{index < externalSteps.length - 1 && (
								<ClayMultiStepNav.Divider />
							)}

							<ClayMultiStepNav.Indicator
								complete={completed}
								label={1 + index}
								onClick={() => available && setStep(nextStep)}
								subTitle={title}
							/>
						</ClayMultiStepNav.Item>
					);
				})}
			</ClayMultiStepNav>

			{steps.map(({Component, key: currentStep}) => (
				<div key={currentStep}>
					{currentStep === step && (
						<Component
							backURL={backURL}
							onChangeStep={(nextStep) => {
								const updatedSteps = steps.map((step) => {
									if (nextStep === step.key) {
										return {
											...step,
											available: true,
										};
									}

									return step;
								});

								setSteps(updatedSteps);
								setStep(nextStep);
							}}
						/>
					)}
				</div>
			))}
		</>
	);
}
