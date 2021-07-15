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

import {ClaySelectWithOption} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

const ExperienceSelector = ({label, options}) => {
	const selectorId = 'experience-select';

	return (
		<ClayLayout.ContentRow verticalAlign="center">
			<ClayLayout.ContentCol>
				<label className="mr-2" htmlFor={selectorId}>
					{label}
				</label>
			</ClayLayout.ContentCol>
			<ClayLayout.ContentCol>
				<ClaySelectWithOption id={selectorId} options={options} />
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
};

export default ExperienceSelector;
