/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

const _statusToLabelDisplayType = (status) => STATUS_TO_TYPE[status];

/**
 * This component simlpy maps a `value` to an associated `displayType` for Experiment statuses
 */
const ExperimentLabel = ({label, value}) => {
	const displayType = _statusToLabelDisplayType(value);

	return <ClayLabel displayType={displayType}>{label}</ClayLabel>;
};

ExperimentLabel.propTypes = ExperimentStatusType;

export default ExperimentLabel;
