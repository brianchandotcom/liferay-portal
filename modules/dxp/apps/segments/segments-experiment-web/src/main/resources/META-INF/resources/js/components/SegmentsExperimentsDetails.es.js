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
