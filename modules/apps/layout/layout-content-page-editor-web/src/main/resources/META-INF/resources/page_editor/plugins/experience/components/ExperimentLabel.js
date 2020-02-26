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

import ClayLabel from '@clayui/label';
import React from 'react';

import {
	STATUS_COMPLETED,
	STATUS_DRAFT,
	STATUS_FINISHED_NO_WINNER,
	STATUS_FINISHED_WINNER,
	STATUS_PAUSED,
	STATUS_RUNNING,
	STATUS_SCHEDULED,
	STATUS_TERMINATED,
} from '../statuses';
import {ExperimentStatusType} from '../types';

const STATUS_TO_TYPE = {
	[STATUS_COMPLETED]: 'success',
	[STATUS_DRAFT]: 'secondary',
	[STATUS_FINISHED_NO_WINNER]: 'secondary',
	[STATUS_FINISHED_WINNER]: 'success',
	[STATUS_PAUSED]: 'warning',
	[STATUS_RUNNING]: 'primary',
	[STATUS_SCHEDULED]: 'warning',
	[STATUS_TERMINATED]: 'danger',
};

const _statusToLabelDisplayType = status => STATUS_TO_TYPE[status];

/**
 * This component simlpy maps a `value` to an associated `displayType` for Experiment statuses
 */
const ExperimentLabel = ({label, value}) => {
	const displayType = _statusToLabelDisplayType(value);

	return <ClayLabel displayType={displayType}>{label}</ClayLabel>;
};

ExperimentLabel.propTypes = ExperimentStatusType;

export default ExperimentLabel;
