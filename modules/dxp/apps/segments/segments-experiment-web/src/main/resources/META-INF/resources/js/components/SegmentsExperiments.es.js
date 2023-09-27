/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import SegmentsExperimentsContext from '../context.es';
import {openDeletionModal} from '../state/actions.es';
import {DispatchContext, StateContext} from '../state/context.es';
import {NO_EXPERIMENT_ILLUSTRATION_FILE_NAME} from '../util/contants.es';
import {
	STATUS_DRAFT,
	STATUS_FINISHED_WINNER,
	STATUS_RUNNING,
	STATUS_TERMINATED,
	statusToLabelDisplayType,
} from '../util/statuses.es';
import ClickGoalPicker from './ClickGoalPicker/ClickGoalPicker.es';
import SegmentsExperimentsActions from './SegmentsExperimentsActions.es';
import SegmentsExperimentsDetails from './SegmentsExperimentsDetails.es';
import Variants from './Variants/Variants.es';

function SegmentsExperiments({
	onCreateSegmentsExperiment,
	onEditSegmentsExperiment,
	onEditSegmentsExperimentStatus,
	onTargetChange,
}) {
	const {experiment} = useContext(StateContext);
	const dispatch = useContext(DispatchContext);

	const goalTarget = experiment?.goal?.target?.replace('#', '');
	const isGoalTargetInDOM = document.getElementById(goalTarget);

	// If the target has been removed from the page we must reset it

	if (goalTarget && !isGoalTargetInDOM) {
		onTargetChange('');
	}

	return (
		<Experiments
			experiment={experiment}
			goalTarget={goalTarget}
			onCreateSegmentsExperiment={onCreateSegmentsExperiment}
			onDeleteSegmentsExperiment={() => {
				dispatch(openDeletionModal());
			}}
			onEditSegmentsExperiment={onEditSegmentsExperiment}
			onEditSegmentsExperimentStatus={onEditSegmentsExperimentStatus}
			onTargetChange={onTargetChange}
		/>
	);
}

function Experiments({
	experiment,
	goalTarget,
	onCreateSegmentsExperiment,
	onDeleteSegmentsExperiment,
	onEditSegmentsExperiment,
	onEditSegmentsExperimentStatus,
	onTargetChange,
}) {
	const [dropdown, setDropdown] = useState(false);
	const [dimissAlert, setDimissAlert] = useState(true);
	const {imagesPath} = useContext(SegmentsExperimentsContext);
	const noExperimentIllustration = `${imagesPath}${NO_EXPERIMENT_ILLUSTRATION_FILE_NAME}`;
	const {selectedExperienceId, variants} = useContext(StateContext);
	const winnerVariant = variants.find((variant) => variant.winner === true);

	return (
		<>
			{experiment && (
				<>
					<div className="align-items-center d-flex justify-content-between">
						<h4 className="mb-0 text-dark text-truncate">
							{experiment.name}
						</h4>

						{experiment.editable && (
							<ClayDropDown
								active={dropdown}
								data-testid="segments-experiments-drop-down"
								onActiveChange={setDropdown}
								trigger={
									<ClayButton
										aria-label={Liferay.Language.get(
											'show-actions'
										)}
										borderless
										className="btn-monospaced"
										displayType="secondary"
									>
										<ClayIcon symbol="ellipsis-v" />
									</ClayButton>
								}
							>
								<ClayDropDown.ItemList>
									<ClayDropDown.Item
										onClick={onEditSegmentsExperiment}
									>
										<ClayIcon
											className="c-mr-3 text-4"
											symbol="pencil"
										/>

										{Liferay.Language.get('edit')}
									</ClayDropDown.Item>

									<ClayDropDown.Item
										onClick={onDeleteSegmentsExperiment}
									>
										<ClayIcon
											className="c-mr-3 text-4"
											symbol="trash"
										/>

										{Liferay.Language.get('delete')}
									</ClayDropDown.Item>
								</ClayDropDown.ItemList>
							</ClayDropDown>
						)}

						{!experiment.editable &&
							experiment.status.value !== STATUS_RUNNING && (
								<ClayButton
									className="text-secondary"
									data-testid="delete-variant"
									displayType={null}
									monospaced
									onClick={onDeleteSegmentsExperiment}
								>
									<ClayIcon symbol="trash" />
								</ClayButton>
							)}
					</div>

					<ClayLabel
						displayType={statusToLabelDisplayType(
							experiment.status.value
						)}
					>
						{experiment.status.label}
					</ClayLabel>

					{experiment.status.value === STATUS_TERMINATED &&
						dimissAlert && (
							<ClayAlert
								className="mt-3"
								displayType="warning"
								onClose={() => setDimissAlert(false)}
								title={Liferay.Language.get('alert')}
							>
								{Liferay.Language.get(
									'the-test-has-not-gathered-sufficient-data-to-confidently-determine-a-winner.-however,-variants-can-still-be-published'
								)}
							</ClayAlert>
						)}

					{experiment.status.value === STATUS_FINISHED_WINNER && (
						<ClayAlert className="mt-3" displayType="success">
							<div
								className="d-inline"
								dangerouslySetInnerHTML={{
									__html: sub(
										Liferay.Language.get(
											'x-is-the-winner-variant'
										),
										'<strong>',
										winnerVariant.name,
										'</strong>'
									),
								}}
							/>
						</ClayAlert>
					)}

					<SegmentsExperimentsDetails
						segmentsExperiment={experiment}
					/>

					{experiment.goal.value === 'click' && (
						<ClickGoalPicker
							allowEdit={experiment.status.value === STATUS_DRAFT}
							onSelectClickGoalTarget={(selector) => {
								onTargetChange(selector);
							}}
							target={goalTarget}
						/>
					)}

					<Variants
						selectedSegmentsExperienceId={selectedExperienceId}
					/>

					<SegmentsExperimentsActions
						onCreateSegmentsExperiment={onCreateSegmentsExperiment}
						onEditSegmentsExperimentStatus={
							onEditSegmentsExperimentStatus
						}
					/>
				</>
			)}

			{!experiment && (
				<div className="segments-experiments-empty-state text-center">
					<img
						alt={Liferay.Language.get('create-test-help-message')}
						className="mb-3 mt-4 segments-experiments-empty-state__image"
						height="185"
						src={noExperimentIllustration}
						width="185"
					/>

					<h4 className="text-dark">
						{Liferay.Language.get(
							'no-active-tests-were-found-for-the-selected-experience'
						)}
					</h4>

					<p>{Liferay.Language.get('create-test-help-message')}</p>

					<ClayButton
						displayType="secondary"
						onClick={() =>
							onCreateSegmentsExperiment(selectedExperienceId)
						}
					>
						{Liferay.Language.get('create-test')}
					</ClayButton>
				</div>
			)}
		</>
	);
}

SegmentsExperiments.propTypes = {
	onCreateSegmentsExperiment: PropTypes.func.isRequired,
	onDeleteSegmentsExperiment: PropTypes.func.isRequired,
	onEditSegmentsExperiment: PropTypes.func.isRequired,
	onEditSegmentsExperimentStatus: PropTypes.func.isRequired,
	onTargetChange: PropTypes.func.isRequired,
};

export default SegmentsExperiments;
