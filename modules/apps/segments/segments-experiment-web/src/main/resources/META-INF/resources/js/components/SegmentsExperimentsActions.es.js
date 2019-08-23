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
import ClayButton from '@clayui/button';
import {InitialSegmentsVariantType, SegmentsExperimentType} from '../types.es';

const STATUS_DRAFT = 0;
const STATUS_PAUSED = 5;
const STATUS_RUNNING = 1;
const STATUS_TERMINATED = 6;

function SegmentsExperimentsActions({
	onEditSegmentsExperimentStatus,
	segmentsExperiment,
	variants
}) {
	return (
		<>
			{segmentsExperiment.status.value === STATUS_DRAFT && (
				<ClayButton
					className="w-100"
					disabled={variants.length <= 1}
					onClick={() =>
						onEditSegmentsExperimentStatus(
							segmentsExperiment,
							STATUS_RUNNING
						)
					}
				>
					{Liferay.Language.get('review-and-run-test')}
				</ClayButton>
			)}

			{segmentsExperiment.status.value === STATUS_RUNNING && (
				<>
					<ClayButton className="w-100 mb-3" disabled>
						{Liferay.Language.get('view-data-in-analytics-cloud')}
					</ClayButton>
					<ClayButton
						className="w-100 mb-3"
						displayType="secondary"
						onClick={() =>
							onEditSegmentsExperimentStatus(
								segmentsExperiment,
								STATUS_PAUSED
							)
						}
					>
						{Liferay.Language.get('pause-test')}
					</ClayButton>
					<ClayButton
						className="w-100"
						displayType="secondary"
						onClick={() =>
							onEditSegmentsExperimentStatus(
								segmentsExperiment,
								STATUS_TERMINATED
							)
						}
					>
						{Liferay.Language.get('terminate-test')}
					</ClayButton>
				</>
			)}

			{segmentsExperiment.status.value === STATUS_PAUSED && (
				<>
					<ClayButton
						className="w-100"
						onClick={() =>
							onEditSegmentsExperimentStatus(
								segmentsExperiment,
								STATUS_RUNNING
							)
						}
					>
						{Liferay.Language.get('restart-test')}
					</ClayButton>
				</>
			)}
		</>
	);
}

SegmentsExperimentsActions.propTypes = {
	onEditSegmentsExperimentStatus: PropTypes.func.isRequired,
	segmentsExperiment: SegmentsExperimentType,
	variants: PropTypes.arrayOf(InitialSegmentsVariantType)
};

export default SegmentsExperimentsActions;
