/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';
import React, {useState} from 'react';

import {IWizardStepProps} from '../types';

export function WizardStep({children, ...rest}: IWizardStepProps) {
	return React.cloneElement(children, {...rest});
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

	const next = () =>
		setStepNumber(Math.min(stepNumber + 1, steps.length - 1));
	const previous = () => setStepNumber(Math.max(stepNumber - 1, 0));

	return (
		<>
			<ClayMultiStepNav center className="mx-9" indicatorLabel="top">
				{(steps as React.ReactElement[]).map((step, index) => {
					const {title} = step.props;

					return (
						<ClayMultiStepNav.Item
							active={index === stepNumber}
							expand={index + 1 !== steps.length}
							key={index}
							state={stepNumber > index ? 'complete' : undefined}
						>
							{index < steps.length - 1 && (
								<ClayMultiStepNav.Divider />
							)}

							<ClayMultiStepNav.Indicator
								label={1 + index}
								onClick={() => setStepNumber(index)}
								subTitle={title}
							/>
						</ClayMultiStepNav.Item>
					);
				})}
			</ClayMultiStepNav>

			{steps[stepNumber] &&
				React.cloneElement(
					steps[stepNumber] as React.ReactElement<any>,
					{
						backURL,
						nextFn:
							stepNumber < steps.length - 1 ? next : undefined,
						previousFn: stepNumber > 0 ? previous : undefined,
					}
				)}
		</>
	);
}
