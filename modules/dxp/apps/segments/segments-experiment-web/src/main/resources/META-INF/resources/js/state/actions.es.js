/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function addSegmentsExperiment(payload) {
	return {
		payload,
		type: 'ADD_EXPERIMENT',
	};
}

export function addVariant(payload) {
	return {
		payload,
		type: 'ADD_VARIANT',
	};
}

export function closeCreationModal() {
	return {
		type: 'CREATE_EXPERIMENT_FINISH',
	};
}

export function closeEditionModal() {
	return {
		type: 'EDIT_EXPERIMENT_FINISH',
	};
}

export function closeReviewAndRunExperiment() {
	return {
		type: 'REVIEW_AND_RUN_EXPERIMENT_FINISH',
	};
}

export function editSegmentsExperiment(payload) {
	return {
		payload,
		type: 'EDIT_EXPERIMENT',
	};
}

export function openCreationModal(payload) {
	return {
		payload,
		type: 'CREATE_EXPERIMENT_START',
	};
}

export function closeDeletionModal() {
	return {
		payload: {active: false},
		type: 'DELETE_EXPERIMENT',
	};
}

export function closePublishModal() {
	return {
		payload: {active: false, experience: null},
		type: 'PUBLISH_EXPERIMENT',
	};
}

export function openPublishModal(experience) {
	return {
		payload: {active: true, experience},
		type: 'PUBLISH_EXPERIMENT',
	};
}

export function openDeletionModal() {
	return {
		payload: {active: true},
		type: 'DELETE_EXPERIMENT',
	};
}

export function closeTerminateModal() {
	return {
		payload: {active: false},
		type: 'TERMINATE_EXPERIMENT',
	};
}

export function openTerminateModal() {
	return {
		payload: {active: true},
		type: 'TERMINATE_EXPERIMENT',
	};
}

export function openEditionModal(payload) {
	return {
		payload,
		type: 'EDIT_EXPERIMENT_START',
	};
}

export function reviewAndRunExperiment() {
	return {
		type: 'REVIEW_AND_RUN_EXPERIMENT',
	};
}

export function reviewVariants() {
	return {
		type: 'REVIEW_VARIANTS',
	};
}

export function reviewClickTargetElement() {
	return {
		type: 'REVIEW_CLICK_TARGET_ELEMENT',
	};
}

export function runExperiment({experiment, splitVariantsMap}) {
	return {
		payload: {
			experiment,
			splitVariantsMap,
		},
		type: 'RUN_EXPERIMENT',
	};
}

export function updateSegmentsExperimentTarget(payload) {
	return {
		payload,
		type: 'UPDATE_SEGMENTS_EXPERIMENT_TARGET',
	};
}

export function updateSegmentsExperimentStatus(payload) {
	return {
		payload,
		type: 'UPDATE_EXPERIMENT_STATUS',
	};
}

export function updateVariant(payload) {
	return {
		payload,
		type: 'UPDATE_VARIANT',
	};
}

export function updateVariants(payload) {
	return {
		payload,
		type: 'UPDATE_VARIANTS',
	};
}
