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

import React from 'react';
import PropTypes from 'prop-types';
import ClayLabel from '@clayui/label';
import {SegmentsExperimentGoal, SegmentsExperimentType} from '../types.es';

function SegmentsExperimentsDetails({initialGoals, segmentsExperiment}) {
	return (
		<>
			<ClayLabel
				displayType={_statusToType(segmentsExperiment.status.value)}
			>
				{segmentsExperiment.status.label}
			</ClayLabel>

			<div className="mt-2 mb-4">
				<p>
					<b>{Liferay.Language.get('segment')}: </b>
					{segmentsExperiment.segmentsEntryName}
				</p>
				<p>
					<b>{Liferay.Language.get('goal')}: </b>
					{_handleGoalLabel()}
				</p>
			</div>
		</>
	);

	function _handleGoalLabel() {
		const goal = initialGoals.find(
			goal => segmentsExperiment.goal == goal.value
		);

		return goal.label;
	}
}

const _statusToType = status => STATUS_TO_TYPE[status];

const STATUS_TO_TYPE = {
	0: 'secondary',
	1: 'primary',
	2: 'success',
	3: 'success',
	4: 'danger',
	5: 'danger',
	6: 'danger',
	7: 'warning'
};

SegmentsExperimentsDetails.propTypes = {
	initialGoals: PropTypes.arrayOf(SegmentsExperimentGoal),
	segmentsExperiment: SegmentsExperimentType
};

export default SegmentsExperimentsDetails;
