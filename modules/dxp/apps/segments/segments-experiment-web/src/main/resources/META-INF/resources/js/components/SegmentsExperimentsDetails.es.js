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
import {SegmentsExperimentType} from '../types.es';
import {indexToPercentageString} from '../util/percentages.es';
import {STATUS_DRAFT} from '../util/statuses.es';

function SegmentsExperimentsDetails({segmentsExperiment}) {
	const {
		confidenceLevel,
		goal,
		segmentsEntryName,
		status
	} = segmentsExperiment;

	return (
		<>
			<h4 className="mb-3 mt-4 sheet-subtitle">
				{Liferay.Language.get('details')}
			</h4>

			<dl>
				<div className="d-flex">
					<dt>{Liferay.Language.get('segment') + ':'} </dt>
					<dd className="text-secondary ml-2">{segmentsEntryName}</dd>
				</div>

				<div className="d-flex">
					<dt>{Liferay.Language.get('goal') + ':'} </dt>
					<dd className="text-secondary ml-2">{goal.label}</dd>
				</div>

				{status.value !== STATUS_DRAFT && (
					<div className="d-flex">
						<dt>
							{Liferay.Language.get('confidence-level') + ':'}{' '}
						</dt>
						<dd className="text-secondary ml-2">
							{indexToPercentageString(confidenceLevel)}
						</dd>
					</div>
				)}
			</dl>
		</>
	);
}

SegmentsExperimentsDetails.propTypes = {
	segmentsExperiment: SegmentsExperimentType
};

export default SegmentsExperimentsDetails;
