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

import React, {useState, useContext} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {ReviewExperimentModal} from './ReviewExperimentModal.es';
import {
	STATUS_COMPLETED,
	STATUS_DRAFT,
	STATUS_FINISHED_NO_WINNER,
	STATUS_FINISHED_WINNER,
	STATUS_PAUSED,
	STATUS_RUNNING,
	STATUS_TERMINATED
} from '../util/statuses.es';
import SegmentsExperimentsContext from '../context.es';
import {updateSegmentsExperiment, updateVariants} from '../state/actions.es';
import {StateContext, DispatchContext} from './../state/context.es';

function _experimentReady(experiment, variants) {
	if (variants.length <= 1) return false;
	if (experiment.goal.value === 'click' && !experiment.goal.target)
		return false;
	return true;
}

function SegmentsExperimentsActions({onEditSegmentsExperimentStatus}) {
	const {variants, experiment, viewExperimentURL} = useContext(StateContext);
	const dispatch = useContext(DispatchContext);

	const [reviewModalVisible, setReviewModalVisible] = useState(false);
	const {APIService} = useContext(SegmentsExperimentsContext);

	const readyToRun = _experimentReady(experiment, variants);

	return (
		<>
			{experiment.status.value === STATUS_DRAFT && (
				<ClayButton
					className="w-100"
					disabled={!readyToRun}
					onClick={() => setReviewModalVisible(true)}
				>
					{Liferay.Language.get('review-and-run-test')}
				</ClayButton>
			)}

			{experiment.status.value === STATUS_RUNNING && (
				<ClayButton
					className="w-100"
					displayType="secondary"
					onClick={() => {
						const confirmed = confirm(
							Liferay.Language.get(
								'are-you-sure-you-want-to-terminate-this-test'
							)
						);

						if (confirmed)
							onEditSegmentsExperimentStatus(
								experiment,
								STATUS_TERMINATED
							);
					}}
				>
					{Liferay.Language.get('terminate-test')}
				</ClayButton>
			)}

			{experiment.status.value === STATUS_PAUSED && (
				<>
					<ClayButton
						className="w-100"
						onClick={() =>
							onEditSegmentsExperimentStatus(
								experiment,
								STATUS_RUNNING
							)
						}
					>
						{Liferay.Language.get('restart-test')}
					</ClayButton>
				</>
			)}

			{experiment.status.value === STATUS_FINISHED_WINNER && (
				<>
					<ClayButton
						className="w-100"
						displayType="secondary"
						onClick={_handleDiscardExperiment}
					>
						{Liferay.Language.get('discard-test')}
					</ClayButton>
				</>
			)}

			{experiment.status.value === STATUS_FINISHED_NO_WINNER && (
				<ClayButton
					className="w-100"
					displayType="primary"
					onClick={_handleDiscardExperiment}
				>
					{Liferay.Language.get('discard-test')}
				</ClayButton>
			)}

			{reviewModalVisible && (
				<ReviewExperimentModal
					onRun={_handleRunExperiment}
					setVisible={setReviewModalVisible}
					variants={variants}
					visible={reviewModalVisible}
				/>
			)}
			{viewExperimentURL && (
				<ClayLink
					className="btn btn-secondary btn-sm w-100 mt-3"
					displayType="secondary"
					href={viewExperimentURL}
					target="_blank"
				>
					{Liferay.Language.get('view-data-in-analytics-cloud')}
					<ClayIcon className="ml-2" symbol="shortcut" />
				</ClayLink>
			)}
		</>
	);

	function _handleRunExperiment({splitVariantsMap, confidenceLevel}) {
		const body = {
			confidenceLevel,
			segmentsExperimentId: experiment.segmentsExperimentId,
			segmentsExperimentRels: JSON.stringify(splitVariantsMap),
			status: STATUS_RUNNING
		};

		return APIService.runExperiment(body).then(function(response) {
			const {segmentsExperiment} = response;
			const updatedVariants = variants.map(variant => ({
				...variant,
				split: splitVariantsMap[variant.segmentsExperimentRelId]
			}));

			dispatch(updateSegmentsExperiment(segmentsExperiment));
			dispatch(updateVariants(updatedVariants));
		});
	}

	function _handleDiscardExperiment() {
		const body = {
			segmentsExperimentId: experiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId: experiment.segmentsExperienceId
		};

		APIService.publishExperience(body).then(({segmentsExperiment}) => {
			dispatch(updateSegmentsExperiment(segmentsExperiment));
		});
	}
}

SegmentsExperimentsActions.propTypes = {
	onEditSegmentsExperimentStatus: PropTypes.func.isRequired
};

export default SegmentsExperimentsActions;
