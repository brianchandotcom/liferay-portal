/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayTabs from '@clayui/tabs';
import {openConfirmModal, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import SegmentsExperimentsContext from '../context.es';
import {archiveExperiment, openDeletionModal} from '../state/actions.es';
import {DispatchContext, StateContext} from '../state/context.es';
import {NO_EXPERIMENT_ILLUSTRATION_FILE_NAME} from '../util/contants.es';
import {navigateToExperience} from '../util/navigation.es';
import {
	STATUS_COMPLETED,
	STATUS_DRAFT,
	STATUS_FINISHED_WINNER,
	statusToLabelDisplayType,
} from '../util/statuses.es';
import {openErrorToast, openSuccessToast} from '../util/toasts.es';
import ClickGoalPicker from './ClickGoalPicker/ClickGoalPicker.es';
import ExperimentsHistory from './ExperimentsHistory.es';
import SegmentsExperimentsActions from './SegmentsExperimentsActions.es';
import SegmentsExperimentsDetails from './SegmentsExperimentsDetails.es';
import Variants from './Variants/Variants.es';

const TABS_STATES = {
	ACTIVE: 0,
	HISTORY: 1,
};

function SegmentsExperiments({
	onCreateSegmentsExperiment,
	onDeleteSegmentsExperiment,
	onEditSegmentsExperiment,
	onEditSegmentsExperimentStatus,
	onTargetChange,
}) {
	const [activeTab, setActiveTab] = useState(TABS_STATES.ACTIVE);
	const {experiment, experimentHistory} = useContext(StateContext);
	const dispatch = useContext(DispatchContext);

	const goalTarget = experiment?.goal?.target?.replace('#', '');
	const isGoalTargetInDOM = document.getElementById(goalTarget);

	// If the target has been removed from the page we must reset it

	if (goalTarget && !isGoalTargetInDOM) {
		onTargetChange('');
	}

	if (Liferay.FeatureFlags['LRAC-14055']) {
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

	return (
		<>
			<ClayTabs justified={true}>
				<ClayTabs.Item
					active={activeTab === TABS_STATES.ACTIVE}
					className="c-pt-1"
					onClick={() => setActiveTab(TABS_STATES.ACTIVE)}
				>
					{Liferay.Language.get('active-test')}
				</ClayTabs.Item>

				<ClayTabs.Item
					active={activeTab === TABS_STATES.HISTORY}
					className="c-pt-1"
					onClick={() => setActiveTab(TABS_STATES.HISTORY)}
				>
					{Liferay.Language.get('history[record]')}

					{' (' + experimentHistory.length + ')'}
				</ClayTabs.Item>
			</ClayTabs>

			<ClayTabs.Content
				activeIndex={activeTab}
				className="pt-3"
				fade={false}
			>
				<ClayTabs.TabPane>
					<Experiments
						experiment={experiment}
						goalTarget={goalTarget}
						onCreateSegmentsExperiment={onCreateSegmentsExperiment}
						onDeleteSegmentsExperiment={() => {
							dispatch(openDeletionModal());
						}}
						onEditSegmentsExperiment={onEditSegmentsExperiment}
						onEditSegmentsExperimentStatus={
							onEditSegmentsExperimentStatus
						}
						onTargetChange={onTargetChange}
					/>
				</ClayTabs.TabPane>

				<ClayTabs.TabPane>
					<ExperimentsHistory
						experimentHistory={experimentHistory}
						onDeleteSegmentsExperiment={onDeleteSegmentsExperiment}
					/>
				</ClayTabs.TabPane>
			</ClayTabs.Content>
		</>
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
	const {APIService, imagesPath} = useContext(SegmentsExperimentsContext);
	const dispatch = useContext(DispatchContext);
	const noExperimentIllustration = `${imagesPath}${NO_EXPERIMENT_ILLUSTRATION_FILE_NAME}`;
	const {selectedExperienceId, variants} = useContext(StateContext);
	const winnerVariant = variants.find((variant) => variant.winner === true);

	function _handleEditExperiment() {
		onEditSegmentsExperiment();
	}

	function _handlePublishVariant(experienceId) {
		const body = {
			segmentsExperimentId: experiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId: experienceId,
		};

		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-publish-this-variant'
			),
			onConfirm: (isConfimed) => {
				if (isConfimed) {
					APIService.publishExperience(body)
						.then(({segmentsExperiment}) => {
							openSuccessToast();

							dispatch(
								archiveExperiment({
									status: segmentsExperiment.status,
								})
							);
							navigateToExperience(experienceId);
						})
						.catch((_error) => {
							openErrorToast();
						});
				}
			},
		});
	}

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
										onClick={_handleEditExperiment}
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
					</div>

					<ClayLabel
						displayType={statusToLabelDisplayType(
							experiment.status.value
						)}
					>
						{experiment.status.label}
					</ClayLabel>

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

							<ClayAlert.Footer>
								<ClayButton.Group>
									<ClayButton
										alert
										onClick={() =>
											_handlePublishVariant(
												winnerVariant.segmentsExperienceId
											)
										}
									>
										{Liferay.Language.get('publish-winner')}
									</ClayButton>
								</ClayButton.Group>
							</ClayAlert.Footer>
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
						onVariantPublish={_handlePublishVariant}
						selectedSegmentsExperienceId={selectedExperienceId}
					/>

					<SegmentsExperimentsActions
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
