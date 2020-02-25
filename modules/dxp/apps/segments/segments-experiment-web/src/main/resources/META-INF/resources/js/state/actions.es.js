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

export const addSegmentsExperiment = payload => ({
	payload,
	type: 'ADD_EXPERIMENT',
});

export const addVariant = payload => ({
	payload,
	type: 'ADD_VARIANT',
});

export const archiveExperiment = payload => ({
	payload,
	type: 'ARCHIVE_EXPERIMENT',
});

export const closeCreationModal = () => ({
	type: 'CREATE_EXPERIMENT_FINISH',
});

export const closeEditionModal = () => ({
	type: 'EDIT_EXPERIMENT_FINISH',
});

export const closeReviewAndRunExperiment = () => ({
	type: 'REVIEW_AND_RUN_EXPERIMENT_FINISH',
});

export const deleteArchivedExperiment = experimentId => ({
	payload: {
		experimentId,
	},
	type: 'DELETE_ARCHIVED_EXPERIMENT',
});

export const editSegmentsExperiment = payload => ({
	payload,
	type: 'EDIT_EXPERIMENT',
});

export const openCreationModal = payload => ({
	payload,
	type: 'CREATE_EXPERIMENT_START',
});

export const openEditionModal = payload => ({
	payload,
	type: 'EDIT_EXPERIMENT_START',
});

export const reviewAndRunExperiment = () => ({
	type: 'REVIEW_AND_RUN_EXPERIMENT',
});

export const reviewVariants = () => ({
	type: 'REVIEW_VARIANTS',
});

export const reviewClickTargetElement = () => ({
	type: 'REVIEW_CLICK_TARGET_ELEMENT',
});

export const runExperiment = ({experiment, splitVariantsMap}) => ({
	payload: {
		experiment,
		splitVariantsMap,
	},
	type: 'RUN_EXPERIMENT',
});

export const updateSegmentsExperimentTarget = payload => ({
	payload,
	type: 'UPDATE_SEGMENTS_EXPERIMENT_TARGET',
});

export const updateSegmentsExperimentStatus = payload => ({
	payload,
	type: 'UPDATE_EXPERIMENT_STATUS',
});

export const updateVariant = payload => ({
	payload,
	type: 'UPDATE_VARIANT',
});

export const updateVariants = payload => ({
	payload,
	type: 'UPDATE_VARIANTS',
});
