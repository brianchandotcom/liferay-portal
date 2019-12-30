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

import React from 'react';

import serviceFetch from '../../app/services/serviceFetch';

export const APIContext = React.createContext({});

// TODO grab urls from displayContext
const EDIT_SEGMENTS_EXPERIENCE_URL =
	'/segments.segmentsexperience/update-segments-experience';

const UPDATE_SEGMENTS_EXPERIENCE_PRIORITY_URL =
	'/segments.segmentsexperience/update-segments-experience-priority';

export default function API({
	addSegmentsExperienceURL,
	classNameId,
	classPK,
	portletNamespace,
	removeSegmentsExperienceURL: _removeSegmentsExperienceURL
}) {
	function createExperience({name, segmentsEntryId}) {
		const body = {
			active: true,
			classNameId,
			classPK,
			name,
			segmentsEntryId
		};

		return serviceFetch({portletNamespace}, addSegmentsExperienceURL, body);
	}

	function removeExperience(
		segmentsExperienceId,
		_fragmentEntryLinkIds = []
	) {
		// TODO actual call to server
		return new Promise(resolve => {
			setTimeout(() => resolve(), 1000);
		});
	}

	function updateExperiencePriority({newPriority, segmentsExperienceId}) {
		return new Promise((resolve, reject) => {
			Liferay.Service(UPDATE_SEGMENTS_EXPERIENCE_PRIORITY_URL, {
				newPriority,
				segmentsExperienceId
			})
				.then(resolve)
				.catch(reject);
		});
	}

	function updateExperience({
		active,
		nameMap,
		segmentsEntryId,
		segmentsExperienceId
	}) {
		return new Promise((resolve, reject) => {
			Liferay.Service(EDIT_SEGMENTS_EXPERIENCE_URL, {
				active,
				nameMap,
				segmentsEntryId,
				segmentsExperienceId
			})
				.then(resolve)
				.catch(reject);
		});
	}

	return {
		createExperience,
		removeExperience,
		updateExperience,
		updateExperiencePriority
	};
}
