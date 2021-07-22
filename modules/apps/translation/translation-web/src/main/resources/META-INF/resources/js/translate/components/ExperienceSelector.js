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
import PropTypes from 'prop-types';
import React from 'react';

const ExperienceSelector = ({
	confirmChangesBeforeReload,
	label,
	options,
	value = '',
}) => {
	const selectorId = 'experience-selector';

	const changePage = (event) => {
		confirmChangesBeforeReload({segmentsExperienceId: event.target.value});
	};

	return (
		<ClayLayout.ContentRow verticalAlign="center">
			<ClayLayout.ContentCol>
				<label className="mr-2" htmlFor={selectorId}>
					{label}
				</label>
			</ClayLayout.ContentCol>
			<ClayLayout.ContentCol>
				<ClaySelectWithOption
					id={selectorId}
					onChange={changePage}
					options={options}
					value={value}
				/>
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
};

ExperienceSelector.propTypes = {
	confirmChangesBeforeReload: PropTypes.func.isRequired,
	label: PropTypes.string.isRequired,
	options: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string.isRequired,
			value: PropTypes.string.isRequired,
		})
	).isRequired,
	value: PropTypes.string,
};

export default ExperienceSelector;
