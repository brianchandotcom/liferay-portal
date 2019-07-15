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

import {CREATE_SEGMENTS_EXPERIMENT} from '../actions/actions.es';

const CREATE_SEGMENTS_EXPERIMENT_URL =
	'/segments.segmentsexperiment/add-segments-experiment';

/**
 * Reducer to create an experiment
 *
 * @export
 * @param {State} state
 * @param {object} action
 * @param {string} action.type
 * @param {object} action.payload
 * @param {string} action.payload.name
 * @param {string} action.payload.description
 * @returns {Promise}
 */
export function createSegmentsExperimentsReducer(state, action) {
	return new Promise((resolve, reject) => {
		if (action.type === CREATE_SEGMENTS_EXPERIMENT) {
			const {name, description} = action.payload;
			const {classPK, classNameId} = state;
			const segmentsExperienceId =
				state.segmentsExperienceId || state.defaultSegmentsExperienceId;

			_createExperiment({
				name,
				description,
				segmentsExperienceId,
				classPK,
				classNameId
			})
				.then(experiment => {
					resolve({
						...state,
						availableSegmentsExperiments: [
							experiment,
							...state.availableSegmentsExperiments
						]
					});
				})
				.catch(error => {
					reject(error);
				});
		} else {
			resolve(state);
		}
	});
}

/**
 * Aks the backend to create an experiment
 *
 * @param {object} experimentData
 * @param {string} experimentData.name
 * @param {string} experimentData.description
 * @param {string} experimentData.segmentsExperienceId
 * @param {string} experimentData.classPK
 * @param {string} experimentData.classNameId
 * @returns {Promise}
 */
function _createExperiment({
	name,
	description,
	segmentsExperienceId,
	classPK,
	classNameId
}) {
	return new Promise(function(resolve, reject) {
		Liferay.Service(
			CREATE_SEGMENTS_EXPERIMENT_URL,
			{
				segmentsExperienceId,
				name,
				description,
				classPK,
				classNameId
			},
			function _successCallback(obj) {
				const {name, description, segmentsExperienceId, status} = obj;
				resolve({
					name,
					description,
					segmentsExperienceId,
					status
				});
			},
			function _errorCallback(error) {
				reject(error);
			}
		);
	});
}
