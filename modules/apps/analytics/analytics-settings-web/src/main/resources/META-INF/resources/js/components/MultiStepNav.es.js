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

import Sheet from './Sheet.es';

const MultiStepNav = ({steps}) => {
	const [value] = useState(0);

	const isActive = (index) => value === index;

	const isComplete = (index) => value > index;

	return (
		<>
			<ClayMultiStepNav>
				{steps.map(({title}, i) => (
					<ClayMultiStepNav.Item
						active={isActive(i)}
						complete={isComplete(i)}
						expand={i + 1 !== steps.length}
						key={i}
					>
						<ClayMultiStepNav.Title>{title}</ClayMultiStepNav.Title>

						<ClayMultiStepNav.Divider />

						<ClayMultiStepNav.Indicator
							complete={isComplete(i)}
							label={1 + i}
						/>
					</ClayMultiStepNav.Item>
				))}
			</ClayMultiStepNav>

			{steps[value] && (
				<Sheet
					description={steps[value].description}
					title={steps[value].title}
				>
					<Sheet.Content>{steps[value].content}</Sheet.Content>

					<Sheet.Footer>{steps[value].footer}</Sheet.Footer>
				</Sheet>
			)}
		</>
	);
};

export default MultiStepNav;
