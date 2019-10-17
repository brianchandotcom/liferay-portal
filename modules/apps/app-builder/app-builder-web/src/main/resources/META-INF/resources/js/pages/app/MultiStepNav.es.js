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

export default ({currentStep}) => {
	const steps = ['1', '2', '3'];
	const isComplete = index =>
		index !== steps.length - 1 && currentStep > index;

	return (
		<ClayMultiStepNav>
			{steps.map((label, index) => (
				<ClayMultiStepNav.Item
					active={currentStep === index}
					complete={isComplete(index)}
					expand={index < steps.length - 1}
					key={index}
				>
					<ClayMultiStepNav.Divider />

					<ClayMultiStepNav.Indicator
						complete={isComplete(index)}
						label={label}
					/>
				</ClayMultiStepNav.Item>
			))}
		</ClayMultiStepNav>
	);
};
