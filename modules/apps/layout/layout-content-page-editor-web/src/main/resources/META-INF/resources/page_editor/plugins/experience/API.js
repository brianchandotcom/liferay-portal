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

export default function API({
	addSegmentsExperienceURL,
	classNameId,
	classPK,
	deleteSegmentsExperienceURL,
	portletNamespace,
	updateSegmentsExperiencePriorityURL,
	updateSegmentsExperienceURL
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
		const body = {
			_fragmentEntryLinkIds,
			segmentsExperienceId
		};

		return serviceFetch(
			{portletNamespace},
			deleteSegmentsExperienceURL,
			body
		);
	}

	function updateExperiencePriority({newPriority, segmentsExperienceId}) {
		const body = {
			newPriority,
			segmentsExperienceId
		};

		return serviceFetch(
			{portletNamespace},
			updateSegmentsExperiencePriorityURL,
			body
		);
	}

	function updateExperience({
		active,
		name,
		segmentsEntryId,
		segmentsExperienceId
	}) {
		const body = {
			active,
			name,
			segmentsEntryId,
			segmentsExperienceId
		};

		return serviceFetch(
			{portletNamespace},
			updateSegmentsExperienceURL,
			body
		);
	}

	return {
		createExperience,
		removeExperience,
		updateExperience,
		updateExperiencePriority
	};
}
