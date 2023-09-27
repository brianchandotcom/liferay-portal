/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

function getExperienceData({body, dispatch}) {
	const {loadFragmentEntryLinks, segmentsExperienceId} = body;

	return serviceFetch(
		config.getExperienceDataURL,
		{
			body: {
				loadFragmentEntryLinks,
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
		const {loadFragmentEntryLinks, segmentsExperienceId} = body;

		return getExperienceData({
			body: {loadFragmentEntryLinks, segmentsExperienceId},
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
