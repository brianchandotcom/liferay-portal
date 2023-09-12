/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	openTerminateModal,
	reviewAndRunExperiment,
	runExperiment,
	updateSegmentsExperimentStatus,
} from '../state/actions.es';
import {
	STATUS_COMPLETED,
	STATUS_DRAFT,
	STATUS_FINISHED_NO_WINNER,
	STATUS_FINISHED_WINNER,
	STATUS_PAUSED,
	STATUS_RUNNING,
	STATUS_TERMINATED,
} from '../util/statuses.es';
import {DispatchContext, StateContext} from './../state/context.es';
import {ReviewExperimentModal} from './ReviewExperimentModal.es';

function SegmentsExperimentsActions({
	onCreateSegmentsExperiment,
	onEditSegmentsExperimentStatus,
}) {
	const {
		experiment,
		reviewExperimentModal,
		variants,
		viewExperimentDetailsURL,
	} = useContext(StateContext);
	const dispatch = useContext(DispatchContext);

	const {observer, onClose} = useModal({
		onClose: () => dispatch(closeReviewAndRunExperiment()),
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
					onClick={() => dispatch(openTerminateModal())}
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

			{experiment.status.value === STATUS_TERMINATED && (
				<ClayButton
					className="w-100"
					displayType="primary"
					onClick={onCreateSegmentsExperiment}
				>
					{Liferay.Language.get('create-new-test')}
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

			{viewExperimentDetailsURL && (
				<ClayLink
					className="btn btn-secondary btn-sm mt-3 w-100"
					displayType="secondary"
					href={viewExperimentDetailsURL}
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
			status: STATUS_RUNNING,
		};

		return APIService.runExperiment(body).then((response) => {
			const {segmentsExperiment} = response;

			dispatch(
				runExperiment({
					experiment: segmentsExperiment,
					splitVariantsMap,
				})
			);
		});
	}

	function _handleDiscardExperiment() {
		const body = {
			segmentsExperimentId: experiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId: experiment.segmentsExperienceId,
		};

		APIService.publishExperience(body).then(({segmentsExperiment}) => {
			dispatch(updateSegmentsExperimentStatus(segmentsExperiment));
		});
	}
}

SegmentsExperimentsActions.propTypes = {
	onCreateSegmentsExperiment: PropTypes.func.isRequired,
	onEditSegmentsExperimentStatus: PropTypes.func.isRequired,
};

export default SegmentsExperimentsActions;
