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
	title: string;
	value: ESteps;
};

const STEPS: TStep[] = [
	{
		Component: ConnectStep,
		title: Liferay.Language.get('connect'),
		value: ESteps.ConnectAC,
	},
	{
		Component: PropertyStep,
		title: Liferay.Language.get('property'),
		value: ESteps.Property,
	},
	{
		Component: PeopleStep,
		title: Liferay.Language.get('people'),
		value: ESteps.People,
	},
	{
		Component: PeopleDataStep,
		title: Liferay.Language.get('people-data'),
		value: ESteps.PeopleData,
	},
];

const WizardPage: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const [value, setValue] = useState<number>(ESteps.ConnectAC);

	return (
		<div className="sheet-lg">
			<ClayMultiStepNav indicatorLabel="top">
				{STEPS.map(({title, value: curValue}, index) => (
					<ClayMultiStepNav.Item
						active={curValue === value}
						complete={value > curValue}
						expand={index + 1 !== STEPS.length}
						key={curValue}
					>
						{index < STEPS.length - 1 && (
							<ClayMultiStepNav.Divider />
						)}

						<ClayMultiStepNav.Indicator
							complete={value > curValue && curValue !== value}
							label={1 + index}
							onClick={() => setValue(curValue)}
							subTitle={title}
						/>
					</ClayMultiStepNav.Item>
				))}
			</ClayMultiStepNav>

			{STEPS.map(({Component, value: curValue}) => (
				<div key={curValue}>
					{curValue === value && (
						<Component onChangeStep={setValue} />
					)}
				</div>
			))}
		</div>
	);
};

export default WizardPage;
