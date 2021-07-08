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

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

function getExperienceUsedPortletIds({body, dispatch}) {
	const {segmentsExperienceId} = body;

	return serviceFetch(
		config.getExperienceUsedPortletsURL,
		{
			body: {
				segmentsExperienceId,
			},
		},
		dispatch
	);
}

export default {

	/**
	 * Asks backend to create a new experience
	 * @param {object} options
	 * @param {object} options.body
	 * @param {string} options.body.name Name for the new experience
	 * @param {string} options.body.segmentsEntryId Id of the segment for the Experience
	 * @param {function} options.dispatch
	 */
	createExperience({body, dispatch}) {
		const {name, segmentsEntryId} = body;

		const payload = {
			active: true,
			name,
			segmentsEntryId,
		};

		return serviceFetch(
			config.addSegmentsExperienceURL,
			{body: payload},
			dispatch
		);
	},

	/**
	 * Asks backend to duplicate an experience
	 * @param {object} options
	 * @param {object} options.body
	 * @param {string} options.body.segmentsExperienceId Id of the experience to be duplicated
	 * @param {function} options.dispatch
	 */
	duplicateExperience({body, dispatch}) {
		const {segmentsExperienceId} = body;

		const payload = {
			segmentsExperienceId,
		};

		return serviceFetch(
			config.duplicateSegmentsExperienceURL,
			{body: payload},
			dispatch
		);
	},

	/**
	 * Asks backend to remove an experience
	 * @param {object} options
	 * @param {object} options.body
	 * @param {string} options.body.segmentsExperienceId Id of the experience to be deleted
	 * @param {function} options.dispatch
	 */
	removeExperience({body, dispatch}) {
		const {segmentsExperienceId} = body;

		const payload = {
			segmentsExperienceId,
		};

		return serviceFetch(
			config.deleteSegmentsExperienceURL,
			{body: payload},
			dispatch
		);
	},

	selectExperience({body, dispatch}) {
		const {segmentsExperienceId} = body;

		return getExperienceUsedPortletIds({
			body: {segmentsExperienceId},
			dispatch,
		});
	},

	/**
	 * Asks backend to update an experience name and audience
	 * @param {object} options
	 * @param {object} options.body
	 * @param {string} options.body.name Experience New name for the experience
	 * @param {string} options.body.segmentsEntryId New audience for the experience
	 * @param {string} options.body.segmentsExperienceId Id of the experience to be updated
	 * @param {function} options.dispatch
	 */
	updateExperience({body, dispatch}) {
		return serviceFetch(
			config.updateSegmentsExperienceURL,
			{body},
			dispatch
		);
	},

	/**
	 * Asks backend to update an experience priority
	 * @param {object} options
	 * @param {object} options.body
	 * @param {number} options.body.newPriority Priority to update the experience
	 * @param {string} options.body.segmentsExperienceId Id of the experience to be updated
	 * @param {function} options.dispatch
	 */
	updateExperiencePriority({body, dispatch}) {
		const {newPriority, segmentsExperienceId} = body;

		const payload = {
			newPriority,
			segmentsExperienceId,
		};

		return serviceFetch(
			config.updateSegmentsExperiencePriorityURL,
			{body: payload},
			dispatch
		);
	},
};
