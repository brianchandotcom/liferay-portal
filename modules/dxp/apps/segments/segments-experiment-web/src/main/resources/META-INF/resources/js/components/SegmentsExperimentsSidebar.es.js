/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal, {useModal} from '@clayui/modal';
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useReducer} from 'react';

import SegmentsExperimentsContext from '../context.es';
import {
	addSegmentsExperiment,
	addVariant,
	archiveExperiment,
	closeCreationModal,
	closeDeletionModal,
	closeEditionModal,
	deleteArchivedExperiment,
	editSegmentsExperiment,
	openCreationModal,
	openDeletionModal,
	openEditionModal,
	reviewAndRunExperiment,
	reviewClickTargetElement,
	updateSegmentsExperimentStatus,
	updateSegmentsExperimentTarget,
} from '../state/actions.es';
import {
	DispatchContext,
	StateContext,
	getInitialState,
} from '../state/context.es';
import {reducer} from '../state/reducer.es';
import {
	SegmentsExperimentGoal,
	SegmentsExperimentType,
	SegmentsVariantType,
} from '../types.es';
import {
	getSegmentsExperimentAction,
	navigateToExperience,
} from '../util/navigation.es';
import {
	STATUS_COMPLETED,
	STATUS_DRAFT,
	STATUS_TERMINATED,
} from '../util/statuses.es';
import {openErrorToast, openSuccessToast} from '../util/toasts.es';
import {DeleteModal} from './DeleteModal.es';
import SegmentsExperiments from './SegmentsExperiments.es';
import SegmentsExperimentsModal from './SegmentsExperimentsModal.es';
import UnsupportedSegmentsExperiments from './UnsupportedSegmentsExperiments.es';

function SegmentsExperimentsSidebar({
	initialExperimentHistory,
	initialGoals,
	initialSegmentsExperiment,
	initialSegmentsVariants,
	initialSelectedSegmentsExperienceId = '0',
	winnerSegmentsVariantId,
}) {
	const {APIService, page} = useContext(SegmentsExperimentsContext);
	const [state, dispatch] = useReducer(
		reducer,
		{
			initialExperimentHistory,
			initialSegmentsExperiment,
			initialSegmentsVariants,
			initialSelectedSegmentsExperienceId,
			winnerSegmentsVariantId,
		},
		getInitialState
	);

	const {
		createExperimentModal,
		deleteExperimentModal,
		editExperimentModal,
		experiment,
	} = state;

	const {
		observer: creationModalObserver,
		onClose: onCreationModalClose,
	} = useModal({
		onClose: () => dispatch(closeCreationModal()),
	});
	const {
		observer: editionModalObserver,
		onClose: onEditionModalClose,
	} = useModal({
		onClose: () => dispatch(closeEditionModal()),
	});
	const {
		observer: deletionModalObserver,
		onClose: onDeletionModalClose,
	} = useModal({
		onClose: () => dispatch(closeDeletionModal()),
	});

	useEffect(() => {
		const segmentsExperimentAction = getSegmentsExperimentAction();

		if (
			!segmentsExperimentAction ||
			!experiment ||
			experiment.status.value !== STATUS_DRAFT
		) {
			return;
		}

		if (segmentsExperimentAction === 'reviewAndRun') {
			dispatch(reviewAndRunExperiment());
		}
		else if (segmentsExperimentAction === 'delete') {
			dispatch(openDeletionModal());
		}
	}, [dispatch, experiment]);

	return page.type === 'content' ? (
		<DispatchContext.Provider value={dispatch}>
			<StateContext.Provider value={state}>
				<div className="pb-3 px-3">
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
						onTargetChange={_handleTargetChange}
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

					{deleteExperimentModal.active && (
						<DeleteModal
							modalObserver={deletionModalObserver}
							onCancel={onDeletionModalClose}
							onDelete={() => {
								_handleDeleteSegmentsExperiment(
									experiment.segmentsExperimentId
								);

								onDeletionModalClose();
							}}
							title={Liferay.Language.get('delete-test')}
						>
							<p className="font-weight-bold text-secondary">
								{Liferay.Language.get(
									'are-you-sure-you-want-to-delete-this'
								)}
							</p>

							<p className="text-secondary">
								{Liferay.Language.get(
									'you-will-lose-all-data-relate-to-it.-you-will-not-be-able-to-undo-this-operation'
								)}
							</p>
						</DeleteModal>
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
			segmentsExperimentId: experimentId,
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
			.catch((_error) => {
				openErrorToast();
			});
	}

	function _handleExperimentCreation(experimentData) {
		const {
			description,
			goal,
			goalTarget,
			name,
			segmentsExperienceId,
		} = experimentData;

		const body = {
			description,
			goal,
			goalTarget,
			name,
			plid: page.plid,
			segmentsExperienceId,
		};

		return APIService.createExperiment(body)
			.then(function _successCallback(objectResponse) {
				const {
					segmentsExperiment,
					segmentsExperimentRel,
				} = objectResponse;

				const {
					confidenceLevel,
					description,
					detailsURL,
					editable,
					goal,
					name,
					segmentsEntryName,
					segmentsExperienceId,
					segmentsExperimentId,
					status,
				} = segmentsExperiment;

				openSuccessToast();

				dispatch(addVariant(segmentsExperimentRel));

				dispatch(closeCreationModal());

				dispatch(
					addSegmentsExperiment({
						confidenceLevel,
						description,
						detailsURL,
						editable,
						goal,
						name,
						segmentsEntryName,
						segmentsExperienceId,
						segmentsExperimentId,
						status,
					})
				);
			})
			.catch(function _errorCallback() {
				dispatch(
					openCreationModal({
						description,
						error: Liferay.Language.get('create-test-error'),
						name,
						segmentsExperienceId,
					})
				);
			});
	}

	function _handleEditSegmentExperimentStatus(experimentData, status) {
		const body = {
			segmentsExperimentId: experimentData.segmentsExperimentId,
			status,
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
							status,
						})
					);
				}
				else {
					dispatch(
						updateSegmentsExperimentStatus({
							editable,
							status,
						})
					);
				}
			})
			.catch(function _errorCallback() {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
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
			segmentsExperimentId,
		} = experimentData;

		const body = {
			description,
			goal,
			goalTarget,
			name,
			segmentsExperimentId,
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
					status,
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
						status,
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
						status: experimentData.status,
					})
				);
			});
	}

	function _handleTargetChange(selector) {
		const body = {
			description: experiment.description,
			goal: experiment.goal.value,
			goalTarget: selector && `#${selector}`,
			name: experiment.name,
			segmentsExperimentId: experiment.segmentsExperimentId,
		};

		APIService.editExperiment(body)
			.then(() => {
				openSuccessToast();

				dispatch(
					updateSegmentsExperimentTarget({
						goal: {...experiment.goal, target: selector},
					})
				);
				dispatch(reviewClickTargetElement());
			})
			.catch((_error) => {
				openErrorToast();
			});
	}
}

SegmentsExperimentsSidebar.propTypes = {
	initialExperimentHistory: PropTypes.arrayOf(SegmentsExperimentType)
		.isRequired,
	initialGoals: PropTypes.arrayOf(SegmentsExperimentGoal),
	initialSegmentsExperiment: SegmentsExperimentType,
	initialSegmentsVariants: PropTypes.arrayOf(SegmentsVariantType).isRequired,
	initialSelectedSegmentsExperienceId: PropTypes.string,
	winnerSegmentsVariantId: PropTypes.string,
};

export default SegmentsExperimentsSidebar;
