/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';
import React, {ReactElement, useState} from 'react';

import Footer from './Footer';

interface WizardStep {
	actionButton?: React.ReactElement;
	children: React.ReactNode;
	description: string;
	onSubmit?: () => void;
	title: string;
}

export function WizardStep({children}: WizardStep) {
	return <>{children}</>;
}

export function Wizard({
	backURL,
	children,
}: {
	backURL: string;
	children: React.ReactElement<WizardStep> | React.ReactElement<WizardStep>[];
}) {
	const [stepNumber, setStepNumber] = useState(0);

	const steps = React.Children.toArray(
		children
	) as ReactElement<WizardStep>[];

	const totalSteps = steps.length;

	const step = steps[stepNumber] as React.ReactElement<WizardStep>;
	const {actionButton, description, onSubmit, title} = step.props;

	const next = () => {
		if (stepNumber < totalSteps - 1) {
			setStepNumber((stepNumber) => stepNumber + 1);
		}
	};

	const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		onSubmit?.();
		next();
	};

	return (
		<form onSubmit={handleSubmit}>
			<ClayMultiStepNav center className="c-mx-lg-9" indicatorLabel="top">
				{steps.map((step, index) => {
					const {title: multiStepTitle} = step.props;

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
								subTitle={multiStepTitle}
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
				actionButton={actionButton}
				backURL={backURL}
				onPrevious={
					stepNumber > 0
						? () => setStepNumber(stepNumber - 1)
						: undefined
				}
			/>
		</form>
	);
}
