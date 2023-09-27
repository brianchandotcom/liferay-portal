/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<ClayLayout.ContentRow
			className="experience-selector"
			verticalAlign="center"
		>
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
					sizing="sm"
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
