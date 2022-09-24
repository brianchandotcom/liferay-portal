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

const MultiSteps = ({steps}: any) => {
	return (
		<ClayMultiStepNav className="container mx-10">
			{steps?.map((step: any, index: number) => (
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
						label={1 + index}
					/>
				</ClayMultiStepNav.Item>
			))}
		</ClayMultiStepNav>
	);
};

export default MultiSteps;
