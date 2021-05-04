/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';

import {SegmentsExperimentType} from '../types.es';
import {indexToPercentageString} from '../util/percentages.es';
import {STATUS_DRAFT} from '../util/statuses.es';

function SegmentsExperimentsDetails({segmentsExperiment}) {
	const {
		confidenceLevel,
		description,
		goal,
		segmentsEntryName,
		status,
	} = segmentsExperiment;

	return (
		<>
			<h4 className="mb-3 mt-4 sheet-subtitle">
				{Liferay.Language.get('details')}
			</h4>

			<dl>
				{description && (
					<div className="c-my-2">
						<dt className="d-inline-block">
							{Liferay.Language.get('description') + ':'}{' '}
						</dt>
						<dd className="d-inline inline-item-after text-secondary">
							{description}
						</dd>
					</div>
				)}

				<div className="c-my-2">
					<dt className="d-inline-block">
						{Liferay.Language.get('segment') + ':'}{' '}
					</dt>
					<dd className="d-inline inline-item-after text-secondary">
						{segmentsEntryName}
					</dd>
				</div>

				<div className="c-my-2">
					<dt className="d-inline-block">
						{Liferay.Language.get('goal') + ':'}{' '}
					</dt>
					<dd className="d-inline inline-item-after text-secondary">
						{goal.label}
					</dd>
				</div>

				{status.value !== STATUS_DRAFT && (
					<div className="c-my-2">
						<dt className="d-inline-block">
							{Liferay.Language.get('confidence-level') + ':'}{' '}
						</dt>
						<dd className="d-inline inline-item-after text-secondary">
							{indexToPercentageString(confidenceLevel)}
						</dd>
					</div>
				)}
			</dl>
		</>
	);
}

SegmentsExperimentsDetails.propTypes = {
	segmentsExperiment: SegmentsExperimentType,
};

export default SegmentsExperimentsDetails;
