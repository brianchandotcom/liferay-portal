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

import React, {useContext, useReducer, createContext} from 'react';
import PropTypes from 'prop-types';
import SegmentsExperiments from './SegmentsExperiments.es';
import SegmentsExperimentsModal from './SegmentsExperimentsModal.es';
import {
	SegmentsExperienceType,
	SegmentsExperimentGoal,
	SegmentsExperimentType,
	SegmentsVariantType
} from '../types.es';
import SegmentsExperimentsContext from '../context.es';
import UnsupportedSegmentsExperiments from './UnsupportedSegmentsExperiments.es';
import {navigateToExperience} from '../util/navigation.es';
import {
	STATUS_RUNNING,
	STATUS_COMPLETED,
	STATUS_TERMINATED
} from '../util/statuses.es';
import {reducer} from '../util/reducer.es';
import {
	closeCreationModal,
	closeEditionModal,
	openEditionModal,
	openCreationModal,
	addSegmentsExperiment,
	updateSegmentsExperiment,
	addVariant,
	updateVariants
} from '../util/actions.es';

const DEFAULT_STATE = {
	createExperimentModal: {active: false},
	editExperimentModal: {active: false},
	experiences: [],
	experiment: null,
	selectedExperienceId: null,
	variants: [],
	winnerVariant: null
};

function getInitialState(state) {
	return {
		...DEFAULT_STATE,
		...state
	};
}

export const DispatchContext = createContext();
export const StateContext = createContext(DEFAULT_STATE);

function SegmentsExperimentsSidebar({
	initialGoals,
	initialSegmentsExperiences,
	initialSegmentsVariants,
	initialSegmentsExperiment,
	initialSelectedSegmentsExperienceId = '0',
	winnerSegmentsVariantId
}) {
	const {APIService, page} = useContext(SegmentsExperimentsContext);
	const [state, dispatch] = useReducer(
		reducer,
		{
			experiment: initialSegmentsExperiment,
			selectedExperienceId: initialSelectedSegmentsExperienceId,
			variants: initialSegmentsVariants.map(initialVariant => {
				if (
					winnerSegmentsVariantId ===
					initialVariant.segmentsExperienceId
				)
					return {...initialVariant, winner: true};
				return initialVariant;
			}),
			winnerVariant: winnerSegmentsVariantId
		},
		getInitialState
	);

	const {
		createExperimentModal,
		editExperimentModal,
		experiment,
		variants
	} = state;

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
						onExperimentDiscard={_handleExperimentDiscard}
						onRunExperiment={_handleRunExperiment}
						onSelectSegmentsExperienceChange={
							_handleSelectSegmentsExperience
						}
						onTargetChange={_handleTargetChange}
						onWinnerExperiencePublishing={
							_handleWinnerExperiencePublishing
						}
						segmentsExperiences={initialSegmentsExperiences}
						segmentsExperiment={experiment}
						selectedSegmentsExperienceId={
							initialSelectedSegmentsExperienceId
						}
					/>
					{createExperimentModal.active && (
						<SegmentsExperimentsModal
							active={createExperimentModal.active}
							description={createExperimentModal.description}
							error={createExperimentModal.error}
							goals={initialGoals}
							handleClose={_handleModalClose}
							name={createExperimentModal.name}
							onSave={_handleExperimentCreation}
							segmentsExperienceId={
								createExperimentModal.segmentsExperienceId
							}
							title={Liferay.Language.get('create-new-test')}
						/>
					)}
					{editExperimentModal.active && (
						<SegmentsExperimentsModal
							active={editExperimentModal.active}
							description={editExperimentModal.description}
							error={editExperimentModal.error}
							goal={editExperimentModal.goal}
							goals={initialGoals}
							handleClose={_handleEditModalClose}
							name={editExperimentModal.name}
							onSave={_handleExperimentEdition}
							segmentsExperienceId={
								editExperimentModal.segmentsExperienceId
							}
							segmentsExperimentId={
								editExperimentModal.segmentsExperimentId
							}
							title={Liferay.Language.get('edit-test')}
						/>
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

	function _handleModalClose() {
		dispatch(closeCreationModal());
	}

	function _handleEditModalClose() {
		dispatch(closeEditionModal());
	}

	function _handleDeleteSegmentsExperiment() {
		const body = {
			segmentsExperimentId: experiment.segmentsExperimentId
		};

		APIService.deleteExperiment(body).then(() => {
			navigateToExperience(initialSelectedSegmentsExperienceId);
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

		APIService.createExperiment(body)
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

	function _handleEditSegmentExperimentStatus(experimentData, status) {
		const body = {
			segmentsExperimentId: experimentData.segmentsExperimentId,
			status
		};

		APIService.editExperimentStatus(body)
			.then(function _successCallback(objectResponse) {
				const {editable, status} = objectResponse.segmentsExperiment;

				dispatch(
					updateSegmentsExperiment({
						editable,
						status
					})
				);
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

		APIService.editExperiment(body)
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
					updateSegmentsExperiment({
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

	function _handleExperimentDiscard() {
		const body = {
			segmentsExperimentId: experiment.segmentsExperimentId,
			status: STATUS_TERMINATED
		};

		APIService.discardExperiement(body).then(({segmentsExperiment}) => {
			segmentsExperiment(segmentsExperiment);
		});
	}

	function _handleTargetChange(selector) {
		const body = {
			description: experiment.description,
			goal: experiment.goal.value,
			goalTarget: selector,
			name: experiment.name,
			segmentsExperimentId: experiment.segmentsExperimentId
		};

		APIService.editExperiment(body).then(() => {
			dispatch(
				updateSegmentsExperiment({
					goal: {...experiment.goal, target: selector}
				})
			);
		});
	}

	function _handleWinnerExperiencePublishing() {
		const body = {
			segmentsExperienceId: winnerSegmentsVariantId,
			segmentsExperimentId: experiment.segmentsExperimentId,
			status: STATUS_COMPLETED
		};

		APIService.publishExperience(body).then(({segmentsExperiment}) => {
			dispatch(updateSegmentsExperiment(segmentsExperiment));
		});
	}
}

SegmentsExperimentsSidebar.propTypes = {
	initialGoals: PropTypes.arrayOf(SegmentsExperimentGoal),
	initialSegmentsExperiences: PropTypes.arrayOf(SegmentsExperienceType),
	initialSegmentsExperiment: SegmentsExperimentType,
	initialSegmentsVariants: PropTypes.arrayOf(SegmentsVariantType).isRequired,
	initialSelectedSegmentsExperienceId: PropTypes.string,
	winnerSegmentsVariantId: PropTypes.string
};

export default SegmentsExperimentsSidebar;
