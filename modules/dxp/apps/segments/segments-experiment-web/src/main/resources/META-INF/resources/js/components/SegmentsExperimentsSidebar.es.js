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

import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useContext, useReducer} from 'react';

import SegmentsExperimentsContext from '../context.es';
import {
	addSegmentsExperiment,
	addVariant,
	archiveExperiment,
	closeCreationModal,
	closeEditionModal,
	deleteArchivedExperiment,
	editSegmentsExperiment,
	openCreationModal,
	openEditionModal,
	reviewClickTargetElement,
	updateSegmentsExperimentStatus,
	updateSegmentsExperimentTarget
} from '../state/actions.es';
import {
	DispatchContext,
	StateContext,
	getInitialState
} from '../state/context.es';
import {reducer} from '../state/reducer.es';
import {
	SegmentsExperienceType,
	SegmentsExperimentGoal,
	SegmentsExperimentType,
	SegmentsVariantType
} from '../types.es';
import {navigateToExperience} from '../util/navigation.es';
import {STATUS_COMPLETED, STATUS_TERMINATED} from '../util/statuses.es';
import {openErrorToast, openSuccessToast} from '../util/toasts.es';
import SegmentsExperiments from './SegmentsExperiments.es';
import SegmentsExperimentsModal from './SegmentsExperimentsModal.es';
import UnsupportedSegmentsExperiments from './UnsupportedSegmentsExperiments.es';

function SegmentsExperimentsSidebar({
	initialExperimentHistory,
	initialGoals,
	initialSegmentsExperiences,
	initialSegmentsExperiment,
	initialSegmentsVariants,
	initialSelectedSegmentsExperienceId = '0',
	viewSegmentsExperimentDetailsURL,
	winnerSegmentsVariantId
}) {
	const {APIService, page} = useContext(SegmentsExperimentsContext);
	const [state, dispatch] = useReducer(
		reducer,
		{
			initialExperimentHistory,
			initialSegmentsExperiment,
			initialSegmentsVariants,
			initialSelectedSegmentsExperienceId,
			viewSegmentsExperimentDetailsURL,
			winnerSegmentsVariantId
		},
		getInitialState
	);

	const {createExperimentModal, editExperimentModal, experiment} = state;

	const {
		observer: creationModalObserver,
		onClose: onCreationModalClose
	} = useModal({
		onClose: () => dispatch(closeCreationModal())
	});
	const {
		observer: editionModalObserver,
		onClose: onEditionModalClose
	} = useModal({
		onClose: () => dispatch(closeEditionModal())
	});

	return page.type === 'content' ? (
		<DispatchContext.Provider value={dispatch}>
			<StateContext.Provider value={state}>
				<div className="p-3">
					<SegmentsExperiments
						onCreateSegmentsExperiment={
							_handleCreateSegmentsExperiment
						}
						onDeleteSegmentsExperiment={
							_handleDeleteSegmentsExperiment
						}
						onEditSegmentsExperiment={_handleEditSegmentsExperiment}
						onEditSegmentsExperimentStatus={
							_handleEditSegmentExperimentStatus
						}
						onSelectSegmentsExperienceChange={
							_handleSelectSegmentsExperience
						}
						onTargetChange={_handleTargetChange}
						segmentsExperiences={initialSegmentsExperiences}
					/>
					{createExperimentModal.active && (
						<ClayModal observer={creationModalObserver} size="lg">
							<SegmentsExperimentsModal
								description={createExperimentModal.description}
								error={createExperimentModal.error}
								goals={initialGoals}
								name={createExperimentModal.name}
								onClose={onCreationModalClose}
								onSave={_handleExperimentCreation}
								segmentsExperienceId={
									createExperimentModal.segmentsExperienceId
								}
								title={Liferay.Language.get('create-new-test')}
							/>
						</ClayModal>
					)}
					{editExperimentModal.active && (
						<ClayModal observer={editionModalObserver} size="lg">
							<SegmentsExperimentsModal
								description={editExperimentModal.description}
								error={editExperimentModal.error}
								goal={editExperimentModal.goal}
								goals={initialGoals}
								name={editExperimentModal.name}
								onClose={onEditionModalClose}
								onSave={_handleExperimentEdition}
								segmentsExperienceId={
									editExperimentModal.segmentsExperienceId
								}
								segmentsExperimentId={
									editExperimentModal.segmentsExperimentId
								}
								title={Liferay.Language.get('edit-test')}
							/>
						</ClayModal>
					)}
				</div>
			</StateContext.Provider>
		</DispatchContext.Provider>
	) : (
		<UnsupportedSegmentsExperiments />
	);

	function _handleCreateSegmentsExperiment(_experienceId) {
		dispatch(openCreationModal());
	}

	function _handleDeleteSegmentsExperiment(experimentId) {
		const body = {
			segmentsExperimentId: experimentId
		};

		APIService.deleteExperiment(body)
			.then(() => {
				openSuccessToast();

				if (
					experiment &&
					experiment.segmentsExperimentId === experimentId
				) {
					navigateToExperience(experiment.segmentsExperienceId);
				}
				else {
					dispatch(deleteArchivedExperiment(experimentId));
				}
			})
			.catch(_error => {
				openErrorToast();
			});
	}

	function _handleExperimentCreation(experimentData) {
		const {
			description,
			goal,
			goalTarget,
			name,
			segmentsExperienceId
		} = experimentData;

		const body = {
			classNameId: page.classNameId,
			classPK: page.classPK,
			description,
			goal,
			goalTarget,
			name,
			segmentsExperienceId
		};

		return APIService.createExperiment(body)
			.then(function _successCallback(objectResponse) {
				const {
					segmentsExperiment,
					segmentsExperimentRel
				} = objectResponse;

				const {
					confidenceLevel,
					description,
					editable,
					goal,
					name,
					segmentsEntryName,
					segmentsExperienceId,
					segmentsExperimentId,
					status
				} = segmentsExperiment;

				openSuccessToast();

				dispatch(addVariant(segmentsExperimentRel));

				dispatch(closeCreationModal());

				dispatch(
					addSegmentsExperiment({
						confidenceLevel,
						description,
						editable,
						goal,
						name,
						segmentsEntryName,
						segmentsExperienceId,
						segmentsExperimentId,
						status
					})
				);
			})
			.catch(function _errorCallback() {
				dispatch(
					openCreationModal({
						description,
						error: Liferay.Language.get('create-test-error'),
						name,
						segmentsExperienceId
					})
				);
			});
	}

	function _handleEditSegmentExperimentStatus(experimentData, status) {
		const body = {
			segmentsExperimentId: experimentData.segmentsExperimentId,
			status
		};

		return APIService.editExperimentStatus(body)
			.then(function _successCallback(objectResponse) {
				const {editable, status} = objectResponse.segmentsExperiment;

				if (
					status.value === STATUS_TERMINATED ||
					status.value === STATUS_COMPLETED
				) {
					dispatch(
						archiveExperiment({
							status
						})
					);
				}
				else {
					dispatch(
						updateSegmentsExperimentStatus({
							editable,
							status
						})
					);
				}
			})
			.catch(function _errorCallback() {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					title: Liferay.Language.get('error'),
					type: 'danger'
				});
			});
	}

	function _handleEditSegmentsExperiment() {
		dispatch(openEditionModal());
	}

	function _handleExperimentEdition(experimentData) {
		const {
			description,
			goal,
			goalTarget,
			name,
			segmentsExperimentId
		} = experimentData;

		const body = {
			description,
			goal,
			goalTarget,
			name,
			segmentsExperimentId
		};

		return APIService.editExperiment(body)
			.then(function _successCallback(objectResponse) {
				const {
					confidenceLevel,
					description,
					editable,
					goal,
					name,
					segmentsEntryName,
					segmentsExperienceId,
					segmentsExperimentId,
					status
				} = objectResponse.segmentsExperiment;

				dispatch(closeEditionModal());

				dispatch(
					editSegmentsExperiment({
						confidenceLevel,
						description,
						editable,
						goal,
						name,
						segmentsEntryName,
						segmentsExperienceId,
						segmentsExperimentId,
						status
					})
				);
			})
			.catch(function _errorCallback() {
				dispatch(
					openEditionModal({
						description: experimentData.description,
						editable: experimentData.editable,
						error: Liferay.Language.get('edit-test-error'),
						name: experimentData.name,
						segmentsEntryName: experimentData.segmentsEntryName,
						segmentsExperienceId:
							experimentData.segmentsExperienceId,
						segmentsExperimentId:
							experimentData.segmentsExperimentId,
						status: experimentData.status
					})
				);
			});
	}

	function _handleSelectSegmentsExperience(segmentsExperienceId) {
		navigateToExperience(segmentsExperienceId);
	}

	function _handleTargetChange(selector) {
		const body = {
			description: experiment.description,
			goal: experiment.goal.value,
			goalTarget: selector,
			name: experiment.name,
			segmentsExperimentId: experiment.segmentsExperimentId
		};

		APIService.editExperiment(body)
			.then(() => {
				openSuccessToast();

				dispatch(
					updateSegmentsExperimentTarget({
						goal: {...experiment.goal, target: selector}
					})
				);
				dispatch(reviewClickTargetElement());
			})
			.catch(_error => {
				openErrorToast();
			});
	}
}

SegmentsExperimentsSidebar.propTypes = {
	initialExperimentHistory: PropTypes.arrayOf(SegmentsExperimentType)
		.isRequired,
	initialGoals: PropTypes.arrayOf(SegmentsExperimentGoal),
	initialSegmentsExperiences: PropTypes.arrayOf(SegmentsExperienceType),
	initialSegmentsExperiment: SegmentsExperimentType,
	initialSegmentsVariants: PropTypes.arrayOf(SegmentsVariantType).isRequired,
	initialSelectedSegmentsExperienceId: PropTypes.string,
	winnerSegmentsVariantId: PropTypes.string
};

export default SegmentsExperimentsSidebar;
