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
import React, {useState} from 'react';

import ConnectStep from './ConnectStep';
import PeopleDataStep from './PeopleDataStep';
import PeopleStep from './PeopleStep';
import PropertyStep from './PropertyStep';

export enum ESteps {
	ConnectAC = 0,
	Property = 1,
	People = 2,
	PeopleData = 3,
}

export interface TGenericComponent {
	onChangeStep: (step: ESteps) => void;
}

type TStep = {
	Component: React.FC<TGenericComponent>;
	available: boolean;
	title: string;
	value: ESteps;
};

const STEPS: TStep[] = [
	{
		Component: ConnectStep,
		available: true,
		title: Liferay.Language.get('connect'),
		value: ESteps.ConnectAC,
	},
	{
		Component: PropertyStep,
		available: false,
		title: Liferay.Language.get('property'),
		value: ESteps.Property,
	},
	{
		Component: PeopleStep,
		available: false,
		title: Liferay.Language.get('people'),
		value: ESteps.People,
	},
	{
		Component: PeopleDataStep,
		available: false,
		title: Liferay.Language.get('people-data'),
		value: ESteps.PeopleData,
	},
];

const WizardPage: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const [value, setValue] = useState<number>(ESteps.ConnectAC);
	const [steps, setSteps] = useState<TStep[]>(STEPS);

	return (
		<div className="sheet-lg">
			<ClayMultiStepNav indicatorLabel="top">
				{steps.map(({available, title, value: nextValue}, index) => {
					const completed = value > nextValue && nextValue !== value;

					return (
						<ClayMultiStepNav.Item
							active={nextValue === value}
							complete={value > nextValue}
							expand={index + 1 !== STEPS.length}
							key={nextValue}
						>
							{index < STEPS.length - 1 && (
								<ClayMultiStepNav.Divider />
							)}

							<ClayMultiStepNav.Indicator
								complete={completed}
								label={1 + index}
								onClick={
									available
										? () => setValue(nextValue)
										: () => {}
								}
								subTitle={title}
							/>
						</ClayMultiStepNav.Item>
					);
				})}
			</ClayMultiStepNav>

			{STEPS.map(({Component, value: curValue}) => (
				<div key={curValue}>
					{curValue === value && (
						<Component
							onChangeStep={(nextValue) => {
								const updatedSteps = steps.map((step) => {
									if (nextValue === step.value) {
										return {
											...step,
											available: true,
										};
									}

									return step;
								});

								setSteps(updatedSteps);
								setValue(nextValue);
							}}
						/>
					)}
				</div>
			))}
		</div>
	);
};

export default WizardPage;
