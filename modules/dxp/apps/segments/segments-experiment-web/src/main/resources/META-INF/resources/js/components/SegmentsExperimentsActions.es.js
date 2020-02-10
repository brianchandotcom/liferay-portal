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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import SegmentsExperimentsContext from '../context.es';
import {
	closeReviewAndRunExperiment,
	reviewAndRunExperiment,
	runExperiment,
	updateSegmentsExperimentStatus
} from '../state/actions.es';
import {
	STATUS_COMPLETED,
	STATUS_DRAFT,
	STATUS_FINISHED_NO_WINNER,
	STATUS_FINISHED_WINNER,
	STATUS_PAUSED,
	STATUS_RUNNING,
	STATUS_TERMINATED
} from '../util/statuses.es';
import {DispatchContext, StateContext} from './../state/context.es';
import {ReviewExperimentModal} from './ReviewExperimentModal.es';

function SegmentsExperimentsActions({onEditSegmentsExperimentStatus}) {
	const {
		experiment,
		reviewExperimentModal,
		variants,
		viewExperimentURL
	} = useContext(StateContext);
	const dispatch = useContext(DispatchContext);

	const {observer, onClose} = useModal({
		onClose: () => dispatch(closeReviewAndRunExperiment())
	});
	const {APIService} = useContext(SegmentsExperimentsContext);

	return (
		<>
			{experiment.status.value === STATUS_DRAFT && (
				<ClayButton
					className="w-100"
					onClick={() => dispatch(reviewAndRunExperiment())}
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

						if (confirmed) {
							onEditSegmentsExperimentStatus(
								experiment,
								STATUS_TERMINATED
							);
						}
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

			{reviewExperimentModal.active && (
				<ReviewExperimentModal
					modalObserver={observer}
					onModalClose={onClose}
					onRun={_handleRunExperiment}
					variants={variants}
				/>
			)}
			{viewExperimentURL && (
				<ClayLink
					className="btn btn-secondary btn-sm mt-3 w-100"
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

	function _handleRunExperiment({confidenceLevel, splitVariantsMap}) {
		const body = {
			confidenceLevel,
			segmentsExperimentId: experiment.segmentsExperimentId,
			segmentsExperimentRels: JSON.stringify(splitVariantsMap),
			status: STATUS_RUNNING
		};

		return APIService.runExperiment(body).then(response => {
			const {segmentsExperiment} = response;

			dispatch(
				runExperiment({
					experiment: segmentsExperiment,
					splitVariantsMap
				})
			);
		});
	}

	function _handleDiscardExperiment() {
		const body = {
			segmentsExperimentId: experiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId: experiment.segmentsExperienceId
		};

		APIService.publishExperience(body).then(({segmentsExperiment}) => {
			dispatch(updateSegmentsExperimentStatus(segmentsExperiment));
		});
	}
}

SegmentsExperimentsActions.propTypes = {
	onEditSegmentsExperimentStatus: PropTypes.func.isRequired
};

export default SegmentsExperimentsActions;
