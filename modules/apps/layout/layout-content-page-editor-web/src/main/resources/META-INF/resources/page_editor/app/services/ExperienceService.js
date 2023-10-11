/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

function getExperienceData({body}) {
	const {loadFragmentEntryLinks, segmentsExperienceId} = body;

	return serviceFetch(config.getExperienceDataURL, {
		body: {
			loadFragmentEntryLinks,
			segmentsExperienceId,
		},
	});
}

export default {

	/**
	 * Asks backend to create a new experience
	 * @param {object} options
	 * @param {object} options.body
	 * @param {string} options.body.name Name for the new experience
	 * @param {string} options.body.segmentsEntryId Id of the segment for the Experience
	 */
	createExperience({body}) {
		const {name, segmentsEntryId} = body;

		const payload = {
			active: true,
			name,
			segmentsEntryId,
		};

		return serviceFetch(config.addSegmentsExperienceURL, {body: payload});
	},

	/**
	 * Asks backend to duplicate an experience
	 * @param {object} options
	 * @param {object} options.body
	 * @param {string} options.body.segmentsExperienceId Id of the experience to be duplicated
	 */
	duplicateExperience({body}) {
		const {segmentsExperienceId} = body;

		const payload = {
			segmentsExperienceId,
		};

		return serviceFetch(config.duplicateSegmentsExperienceURL, {
			body: payload,
		});
	},

	/**
	 * Asks backend to remove an experience
	 * @param {object} options
	 * @param {object} options.body
	 * @param {string} options.body.segmentsExperienceId Id of the experience to be deleted
	 */
	removeExperience({body}) {
		const {segmentsExperienceId} = body;

		const payload = {
			segmentsExperienceId,
		};

		return serviceFetch(config.deleteSegmentsExperienceURL, {
			body: payload,
		});
	},

	selectExperience({body}) {
		const {loadFragmentEntryLinks, segmentsExperienceId} = body;

		return getExperienceData({
			body: {loadFragmentEntryLinks, segmentsExperienceId},
		});
	},

	/**
	 * Asks backend to update an experience name and audience
	 * @param {object} options
	 * @param {object} options.body
	 * @param {string} options.body.name Experience New name for the experience
	 * @param {string} options.body.segmentsEntryId New audience for the experience
	 * @param {string} options.body.segmentsExperienceId Id of the experience to be updated
	 */
	updateExperience({body}) {
		return serviceFetch(config.updateSegmentsExperienceURL, {body});
	},

	/**
	 * Asks backend to update an experience priority
	 * @param {object} options
	 * @param {object} options.body
	 * @param {number} options.body.newPriority Priority to update the experience
	 * @param {string} options.body.segmentsExperienceId Id of the experience to be updated
	 * @param {function} options.dispatch
	 */
	updateExperiencePriority({body}) {
		const {newPriority, segmentsExperienceId} = body;

		const payload = {
			newPriority,
			segmentsExperienceId,
		};

		return serviceFetch(config.updateSegmentsExperiencePriorityURL, {
			body: payload,
		});
	},
};
