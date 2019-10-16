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

export function reducer(state, action) {
	switch (action.type) {
		case 'ADD_EXPERIMENT':
			return {
				...state,
				experiment: action.payload
			};

		case 'ADD_VARIANT':
			return {
				...state,
				variants: [...state.variants, action.payload]
			};

		case 'ARCHIVE_EXPERIMENT':
			return {
				...state,
				experiment: null,
				experimentHistory: [
					{...state.experiment, status: action.payload.status},
					...state.experimentHistory
				],
				variants: [],
				viewExperimentURL: undefined
			};

		case 'CREATE_EXPERIMENT_FINISH':
			return {
				...state,
				createExperimentModal: {
					active: false
				}
			};

		case 'CREATE_EXPERIMENT_START':
			return _createExperimentStart(state, action.payload);

		case 'DELETE_ARCHIVED_EXPERIMENT':
			return {
				...state,
				experimentHistory: state.experimentHistory.filter(
					experiment => {
						return (
							experiment.segmentsExperimentId !==
							action.payload.experimentId
						);
					}
				)
			};

		case 'EDIT_EXPERIMENT_FINISH':
			return {
				...state,
				editExperimentModal: {
					active: false
				}
			};

		case 'EDIT_EXPERIMENT_START':
			return _editExperienceStart(state, action.payload);

		case 'UPDATE_EXPERIMENT':
			return _updateExperiment(state, action.payload);

		case 'UPDATE_VARIANT':
			return {
				...state,
				variants: state.variants.map(variant => {
					if (
						action.payload.variantId ===
						variant.segmentsExperimentRelId
					) {
						return {
							...variant,
							...action.payload.changes
						};
					}
					return variant;
				})
			};

		case 'UPDATE_VARIANTS':
			return {
				...state,
				variants: action.payload
			};

		default:
			return state;
	}
}

function _createExperimentStart(state, experimentModalState = {}) {
	const {selectedExperienceId} = state;
	const {
		description,
		error,
		name,
		segmentsExperienceId
	} = experimentModalState;

	return {
		...state,
		createExperimentModal: {
			active: true,
			description,
			error,
			name,
			segmentsExperienceId: segmentsExperienceId
				? segmentsExperienceId
				: selectedExperienceId
		}
	};
}

function _editExperienceStart(state, experiementModalState = {}) {
	const {experiment} = state;

	const {
		description,
		editable,
		error,
		goal,
		name,
		segmentsEntryName,
		segmentsExperienceId,
		segmentsExperimentId,
		status
	} = experiementModalState;

	return {
		...state,
		editExperimentModal: {
			active: true,
			description: description ? description : experiment.description,
			editable: editable ? editable : experiment.editable,
			error: error ? error : experiment.error,
			goal: goal ? goal : experiment.goal,
			name: name ? name : experiment.name,
			segmentsEntryName: segmentsEntryName
				? segmentsEntryName
				: experiment.segmentsEntryName,
			segmentsExperienceId: segmentsExperienceId
				? segmentsExperienceId
				: experiment.segmentsExperienceId,
			segmentsExperimentId: segmentsExperimentId
				? segmentsExperimentId
				: experiment.segmentsExperimentId,
			status: status ? status : experiment.status
		}
	};
}

function _updateExperiment(state, updatedValues) {
	return {
		...state,
		experiment: {...state.experiment, ...updatedValues}
	};
}
