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

import {
	SELECT_SEGMENTS_EXPERIENCE,
	UPDATE_SEGMENTS_EXPERIENCE_PRIORITY,
	DELETE_SEGMENTS_EXPERIENCE
} from '../../../actions/actions.es';
import useDispatch from '../../../store/hooks/useDispatch.es';
import ExperienceItem from './ExperienceItem.es';
import {ExperienceType} from './types.es';

const DEFAULT_EXPERIENCE_ID = '0';

const ExperiencesList = ({
	activeExperienceId,
	experiences,
	hasUpdatePermissions,
	onEditExperience
}) => {
	const dispatch = useDispatch();

	const _selectExperience = id => {
		dispatch({
			segmentsExperienceId: id,
			type: SELECT_SEGMENTS_EXPERIENCE
		});
	};

	const _decreasePriority = (id, priority) => {
		dispatch({
			direction: 'down',
			priority,
			segmentsExperienceId: id,
			type: UPDATE_SEGMENTS_EXPERIENCE_PRIORITY
		});
	};

	const _increasePriority = (id, priority) => {
		dispatch({
			direction: 'up',
			priority,
			segmentsExperienceId: id,
			type: UPDATE_SEGMENTS_EXPERIENCE_PRIORITY
		});
	};

	const _deleteExperience = id => {
		dispatch({
			segmentsExperienceId: id,
			type: DELETE_SEGMENTS_EXPERIENCE
		});
	};

	return (
		<ul className="list-unstyled mt-4">
			{experiences.map((experience, i) => {
				const active =
					experience.segmentsExperienceId === activeExperienceId;
				const lockedDecreasePriority = experiences.length - 2 === i;
				const lockedIncreasePriority = i === 0;

				const editable =
					hasUpdatePermissions &&
					experience.segmentsExperienceId !== DEFAULT_EXPERIENCE_ID &&
					!experience.hasLockedSegmentsExperiment;

				return (
					<ExperienceItem
						active={active}
						editable={editable}
						experience={experience}
						key={experience.segmentsExperienceId}
						lockedDecreasePriority={lockedDecreasePriority}
						lockedIncreasePriority={lockedIncreasePriority}
						onDeleteExperience={_deleteExperience}
						onEditExperience={onEditExperience}
						onPriorityDecrease={_decreasePriority}
						onPriorityIncrease={_increasePriority}
						onSelect={_selectExperience}
					/>
				);
			})}
		</ul>
	);
};

ExperiencesList.propTypes = {
	activeExperienceId: PropTypes.string.isRequired,
	experiences: PropTypes.arrayOf(PropTypes.shape(ExperienceType)).isRequired,
	hasUpdatePermissions: PropTypes.bool.isRequired,
	onEditExperience: PropTypes.func.isRequired
};

export default ExperiencesList;
