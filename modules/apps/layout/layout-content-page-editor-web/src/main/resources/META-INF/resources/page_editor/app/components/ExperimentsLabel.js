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
import PropTypes from 'prop-types';
import React from 'react';

const STATUS_COMPLETED = 2;
const STATUS_DRAFT = 0;
const STATUS_FINISHED_NO_WINNER = 4;
const STATUS_FINISHED_WINNER = 3;
const STATUS_PAUSED = 5;
const STATUS_RUNNING = 1;
const STATUS_SCHEDULED = 7;
const STATUS_TERMINATED = 6;

const STATUS_TO_TYPE = {
	[STATUS_COMPLETED]: 'success',
	[STATUS_DRAFT]: 'secondary',
	[STATUS_FINISHED_NO_WINNER]: 'secondary',
	[STATUS_FINISHED_WINNER]: 'success',
	[STATUS_PAUSED]: 'warning',
	[STATUS_RUNNING]: 'primary',
	[STATUS_SCHEDULED]: 'warning',
	[STATUS_TERMINATED]: 'danger'
};

export default function ExperimentsLabel({label, value}) {
	const displayType = STATUS_TO_TYPE[value];

	return (
		<>
			<span className="mr-1 text-secondary">
				{Liferay.Language.get('ab-test')}
			</span>

			<ClayLabel displayType={displayType}>{label}</ClayLabel>
		</>
	);
}

ExperimentsLabel.propTypes = {
	label: PropTypes.string.isRequired,
	value: PropTypes.string.isRequired
};
