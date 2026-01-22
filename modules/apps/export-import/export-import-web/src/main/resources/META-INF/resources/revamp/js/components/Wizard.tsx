/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';
import React, {useState} from 'react';

import Footer from './Footer';

export interface IGenericStepProps {
	backURL?: string;
	description?: string;
	exportURL?: string | undefined;
	nextFn?: () => void | undefined;
	previousFn?: () => void | undefined;
	title?: string;
}

export interface IWizardStepProps extends IGenericStepProps {
	children: React.ReactElement;
}

export function WizardStep({children}: IWizardStepProps) {
	return children;
}

export function Wizard({
	backURL,
	children,
}: {
	backURL: string;
	children: React.ReactNode;
}) {
	const [stepNumber, setStepNumber] = useState(0);
	const steps = React.Children.toArray(children);
	const totalSteps = React.Children.count(children);

	const currentStep = steps[stepNumber] as React.ReactElement;
	const {description, title} = currentStep.props;

	return (
		<>
			<ClayMultiStepNav center className="mx-9" indicatorLabel="top">
				{React.Children.map(children, (step, index) => {
					if (!React.isValidElement(step)) {
						return null;
					}

					const {title} = step.props;

					return (
						<ClayMultiStepNav.Item
							active={index === stepNumber}
							key={index}
							state={stepNumber > index ? 'complete' : undefined}
						>
							{index < totalSteps - 1 && (
								<ClayMultiStepNav.Divider />
							)}

							<ClayMultiStepNav.Indicator
								label={1 + index}
								subTitle={title}
							/>
						</ClayMultiStepNav.Item>
					);
				})}
			</ClayMultiStepNav>

			<header className="mb-1 sheet-header">
				<div className="mb-1 sheet-title">{title}</div>

				{description && (
					<p className="sheet-text text-secondary">{description}</p>
				)}
			</header>

			{steps[stepNumber]}

			<Footer
				backURL={backURL}
				exportURL={
					stepNumber === totalSteps - 1 ? 'exportURL' : undefined
				}
				nextFn={
					stepNumber < totalSteps - 1
						? () => setStepNumber(stepNumber + 1)
						: undefined
				}
				previousFn={
					stepNumber > 0
						? () => setStepNumber(stepNumber - 1)
						: undefined
				}
			/>
		</>
	);
}
