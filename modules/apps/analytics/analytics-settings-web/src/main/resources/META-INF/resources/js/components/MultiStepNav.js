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
import React from 'react';

import Sheet from './Sheet';

const MultiStepNav = ({steps}) => {
	const currentStep = 0;

	return (
		<>
			<ClayMultiStepNav>
				{steps.map(({title}, index) => (
					<ClayMultiStepNav.Item
						active={currentStep === index}
						complete={currentStep > index}
						expand={index + 1 !== steps.length}
						key={index}
					>
						<ClayMultiStepNav.Title>{title}</ClayMultiStepNav.Title>

						<ClayMultiStepNav.Divider />

						<ClayMultiStepNav.Indicator
							complete={currentStep > index}
							label={1 + index}
						/>
					</ClayMultiStepNav.Item>
				))}
			</ClayMultiStepNav>

			{steps[currentStep] && (
				<Sheet
					description={steps[currentStep].description}
					title={steps[currentStep].title}
				>
					<Sheet.Content>{steps[currentStep].content}</Sheet.Content>

					<Sheet.Footer>{steps[currentStep].footer}</Sheet.Footer>
				</Sheet>
			)}
		</>
	);
};

export default MultiStepNav;
