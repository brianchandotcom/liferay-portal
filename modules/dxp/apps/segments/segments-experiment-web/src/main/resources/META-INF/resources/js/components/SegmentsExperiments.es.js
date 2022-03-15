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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayTabs from '@clayui/tabs';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import SegmentsExperimentsContext from '../context.es';
import {archiveExperiment} from '../state/actions.es';
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
	const [dropdown, setDropdown] = useState(false);
	const [activeTab, setActiveTab] = useState(TABS_STATES.ACTIVE);
	const {
		experiment,
		experimentHistory,
		selectedExperienceId,
		variants,
	} = useContext(StateContext);
	const {APIService, imagesPath} = useContext(SegmentsExperimentsContext);
	const dispatch = useContext(DispatchContext);

	const noExperimentIllustration = `${imagesPath}${NO_EXPERIMENT_ILLUSTRATION_FILE_NAME}`;
	const winnerVariant = variants.find((variant) => variant.winner === true);
	const goalTarget = experiment?.goal?.target?.replace('#', '');
	const isGoalTargetInDOM = document.getElementById(goalTarget);

	// If the target has been removed from the page we must reset it

	if (goalTarget && !isGoalTargetInDOM) {
		onTargetChange('');
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
												{Liferay.Language.get('edit')}
											</ClayDropDown.Item>

											<ClayDropDown.Item
												onClick={
													_handleDeleteActiveExperiment
												}
											>
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

							{experiment.status.value ===
								STATUS_FINISHED_WINNER && (
								<ClayAlert
									className="mt-3"
									displayType="success"
								>
									<div
										className="d-inline"
										dangerouslySetInnerHTML={{
											__html: Liferay.Util.sub(
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
												{Liferay.Language.get(
													'publish-winner'
												)}
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
									allowEdit={
										experiment.status.value === STATUS_DRAFT
									}
									onSelectClickGoalTarget={(selector) => {
										onTargetChange(selector);
									}}
									target={goalTarget}
								/>
							)}

							<Variants
								onVariantPublish={_handlePublishVariant}
								selectedSegmentsExperienceId={
									selectedExperienceId
								}
							/>

							<SegmentsExperimentsActions
								onEditSegmentsExperimentStatus={
									onEditSegmentsExperimentStatus
								}
							/>
						</>
					)}

					{!experiment && (
						<div className="text-center">
							<img
								alt=""
								className="my-3"
								src={noExperimentIllustration}
								width="120px"
							/>

							<h4 className="text-dark">
								{Liferay.Language.get(
									'no-active-tests-were-found-for-the-selected-experience'
								)}
							</h4>

							<p>
								{Liferay.Language.get(
									'create-test-help-message'
								)}
							</p>

							<ClayButton
								displayType="secondary"
								onClick={() =>
									onCreateSegmentsExperiment(
										selectedExperienceId
									)
								}
							>
								{Liferay.Language.get('create-test')}
							</ClayButton>
						</div>
					)}
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

	function _handleDeleteActiveExperiment() {
		const confirmed = confirm(
			Liferay.Language.get('are-you-sure-you-want-to-delete-this')
		);

		if (confirmed) {
			return onDeleteSegmentsExperiment(experiment.segmentsExperimentId);
		}
	}

	function _handleEditExperiment() {
		onEditSegmentsExperiment();
	}

	function _handlePublishVariant(experienceId) {
		const body = {
			segmentsExperimentId: experiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId: experienceId,
		};

		const confirmed = confirm(
			Liferay.Language.get(
				'are-you-sure-you-want-to-publish-this-variant'
			)
		);

		if (confirmed) {
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
	}
}

SegmentsExperiments.propTypes = {
	onCreateSegmentsExperiment: PropTypes.func.isRequired,
	onDeleteSegmentsExperiment: PropTypes.func.isRequired,
	onEditSegmentsExperiment: PropTypes.func.isRequired,
	onEditSegmentsExperimentStatus: PropTypes.func.isRequired,
	onTargetChange: PropTypes.func.isRequired,
};

export default SegmentsExperiments;
