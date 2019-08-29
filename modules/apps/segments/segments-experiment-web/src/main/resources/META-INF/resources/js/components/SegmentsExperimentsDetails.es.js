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

function SegmentsExperimentsDetails({segmentsExperiment}) {
	return (
		<>
			<h4 className="mb-3 mt-4 sheet-subtitle">
				{Liferay.Language.get('details')}
			</h4>

			<div className="mb-2">
				<b>{Liferay.Language.get('segment')}: </b>
				{segmentsExperiment.segmentsEntryName}
			</div>
			<div>
				<b>{Liferay.Language.get('goal')}: </b>
				{segmentsExperiment.goal.label}
			</div>
			<div>
				<b>{Liferay.Language.get('confidence-level')}: </b>
				{segmentsExperiment.confidenceLevel * 100 + '%'}
			</div>
		</>
	);
}

SegmentsExperimentsDetails.propTypes = {
	segmentsExperiment: SegmentsExperimentType
};

export default SegmentsExperimentsDetails;
