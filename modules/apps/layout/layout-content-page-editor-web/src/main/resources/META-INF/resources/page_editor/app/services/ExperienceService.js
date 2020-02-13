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

import serviceFetch from './serviceFetch';

function getExperienceUsedPortletIds({body, config, dispatch}) {
	const {segmentsExperienceId} = body;
	const {getExperienceUsedPortletsURL} = config;

	return serviceFetch(
		config,
		getExperienceUsedPortletsURL,
		{
			body: {
				segmentsExperienceId
			}
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
	 * @param {object} options.config
	 * @param {string} options.config.addSegmentsExperienceURL Url of the backend service
	 */
	createExperience({body, config, dispatch}) {
		const {name, segmentsEntryId} = body;
		const {addSegmentsExperienceURL} = config;

		const payload = {
			active: true,
			name,
			segmentsEntryId
		};

		return serviceFetch(
			config,
			addSegmentsExperienceURL,
			{body: payload},
			dispatch
		);
	},

	/**
	 * Asks backend to remove an experience
	 * @param {object} options
	 * @param {object} options.body
	 * @param {number[]} options.body.fragmentEntryLinkIds List of fragment entry ids unique to the  experience to delete
	 * @param {string} options.body.segmentsExperienceId Id of the experience to be deleted
	 * @param {object} options.config
	 * @param {string} options.config.deleteSegmentsExperienceURL Url of the backend service
	 */
	removeExperience({body, config, dispatch}) {
		const {fragmentEntryLinkIds, segmentsExperienceId} = body;
		const {deleteSegmentsExperienceURL} = config;

		const payload = {
			deleteSegmentsExperience: true,
			fragmentEntryLinkIds: JSON.stringify(fragmentEntryLinkIds),
			segmentsExperienceId
		};

		return serviceFetch(
			config,
			deleteSegmentsExperienceURL,
			{body: payload},
			dispatch
		);
	},

	selectExperience({body, config, dispatch}) {
		const {segmentsExperienceId} = body;

		return getExperienceUsedPortletIds({
			body: {segmentsExperienceId},
			config,
			dispatch
		});
	},

	/**
	 * Asks backend to update an experience name and audience
	 * @param {object} options
	 * @param {object} options.body
	 * @param {string} options.body.name Experience New name for the experience
	 * @param {string} options.body.segmentsEntryId New audience for the experience
	 * @param {string} options.body.segmentsExperienceId Id of the experience to be updated
	 * @param {object} options.config
	 * @param {string} options.config.updateSegmentsExperienceURL Url of the backend service
	 */
	updateExperience({body, config, dispatch}) {
		const {updateSegmentsExperienceURL} = config;

		return serviceFetch(
			config,
			updateSegmentsExperienceURL,
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
	 * @param {object} options.config
	 * @param {string} options.config.updateSegmentsExperiencePriorityURL Url of the backend service
	 */
	updateExperiencePriority({body, config, dispatch}) {
		const {newPriority, segmentsExperienceId} = body;
		const {updateSegmentsExperiencePriorityURL} = config;

		const payload = {
			newPriority,
			segmentsExperienceId
		};

		return serviceFetch(
			config,
			updateSegmentsExperiencePriorityURL,
			{body: payload},
			dispatch
		);
	}
};
