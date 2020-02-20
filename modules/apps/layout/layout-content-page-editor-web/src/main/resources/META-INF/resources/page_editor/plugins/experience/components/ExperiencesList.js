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

import PropTypes from 'prop-types';
import React from 'react';

import {useDispatch} from '../../../app/store/index';
import selectExperience from '../thunks/selectExperience';
import {ExperienceType} from '../types';
import ExperienceItem from './ExperienceItem';

const ExperiencesList = ({
	activeExperienceId,
	defaultExperienceId,
	experiences,
	hasUpdatePermissions,
	onDeleteExperience,
	onEditExperience,
	onPriorityDecrease,
	onPriorityIncrease
}) => {
	const dispatch = useDispatch();

	const handleExperienceSelection = id => dispatch(selectExperience(id));

	return (
		<ul className="list-unstyled mt-4" role="list">
			{experiences.map((experience, i) => {
				const active =
					experience.segmentsExperienceId === activeExperienceId;
				const lockedDecreasePriority = experiences.length - 2 === i;
				const lockedIncreasePriority = i === 0;

				const editable =
					hasUpdatePermissions &&
					experience.segmentsExperienceId !== defaultExperienceId &&
					!experience.hasLockedSegmentsExperiment;

				return (
					<ExperienceItem
						active={active}
						editable={editable}
						experience={experience}
						key={experience.segmentsExperienceId}
						lockedDecreasePriority={lockedDecreasePriority}
						lockedIncreasePriority={lockedIncreasePriority}
						onDeleteExperience={onDeleteExperience}
						onEditExperience={onEditExperience}
						onPriorityDecrease={onPriorityDecrease}
						onPriorityIncrease={onPriorityIncrease}
						onSelect={handleExperienceSelection}
					/>
				);
			})}
		</ul>
	);
};

ExperiencesList.propTypes = {
	activeExperienceId: PropTypes.string.isRequired,
	defaultExperienceId: PropTypes.string.isRequired,
	experiences: PropTypes.arrayOf(PropTypes.shape(ExperienceType)).isRequired,
	hasUpdatePermissions: PropTypes.bool.isRequired,
	onDeleteExperience: PropTypes.func.isRequired,
	onEditExperience: PropTypes.func.isRequired,
	onPriorityDecrease: PropTypes.func.isRequired,
	onPriorityIncrease: PropTypes.func.isRequired
};

export default ExperiencesList;
