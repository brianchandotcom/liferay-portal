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
