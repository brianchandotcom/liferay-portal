/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';

type MultiStepsProps = {
	classes?: string;
	steps: StepTypes[];
};

type StepTypes = {
	active: boolean;
	complete: boolean;
	show: boolean;
	title: string;
};

const MultiSteps: React.FC<MultiStepsProps> = ({classes, steps}) => {
	return (
		<ClayMultiStepNav className={`container mx-10 ${classes}`}>
			{steps?.map((step: StepTypes, index: number) => {
				if (step.show) {
					return (
						<ClayMultiStepNav.Item
							active={step.active}
							complete={step.complete}
							expand={index + 1 !== steps.length}
							key={index}
						>
							<ClayMultiStepNav.Title>
								{step.title}
							</ClayMultiStepNav.Title>

							{index + 1 !== steps.length ? (
								<ClayMultiStepNav.Divider />
							) : (
								''
							)}

							<ClayMultiStepNav.Indicator
								complete={step.complete}
								label={index + 1}
							/>
						</ClayMultiStepNav.Item>
					);
				}
			})}
		</ClayMultiStepNav>
	);
};

export default MultiSteps;
