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

import React, {useContext, useMemo} from 'react';

import {ConfigContext} from '../../../app/config/index';
import {StoreContext} from '../../../app/store/index';
import API, {APIContext} from '../API';
import ExperienceSelector from './ExperienceSelector';

// TODO: show how to colocate CSS with plugins (may use loaders)
export default function ExperienceToolbarSection({selectId}) {
	const {availableSegmentsExperiences, segmentsExperienceId} = useContext(
		StoreContext
	);
	const {
		addSegmentsExperienceURL,
		availableSegmentsEntries,
		classNameId,
		classPK,
		defaultSegmentsEntryId,
		editSegmentsEntryURL,
		portletNamespace
	} = useContext(ConfigContext);

	const experiences = useMemo(
		() =>
			Object.values(availableSegmentsExperiences)
				.sort((a, b) => b.priority - a.priority)
				.map(experience => {
					const segmentsEntryName =
						availableSegmentsEntries[experience.segmentsEntryId]
							.name;

					return {
						...experience,
						segmentsEntryName
					};
				}),
		[availableSegmentsExperiences, availableSegmentsEntries]
	);
	const segments = useMemo(() => Object.values(availableSegmentsEntries), [
		availableSegmentsEntries
	]).filter(segment => segment.segmentsEntryId !== defaultSegmentsEntryId);

	// TODO get endpoints URL from the display context
	const APIService = useMemo(() => {
		return API({
			addSegmentsExperienceURL,
			classNameId,
			classPK,
			editSegmentsExperiencePriorityURL: '/',
			editSegmentsExperienceURL: '/',
			portletNamespace,
			removeSegmentsExperienceURL: '/'
		});
	}, [addSegmentsExperienceURL, classNameId, classPK, portletNamespace]);

	const selectedExperience =
		availableSegmentsExperiences[segmentsExperienceId];

	return (
		<APIContext.Provider value={APIService}>
			<div className="mr-2 page-editor-toolbar-experience">
				<label className="mr-2" htmlFor={selectId}>
					{Liferay.Language.get('experience')}
				</label>

				<ExperienceSelector
					editSegmentsEntryURL={editSegmentsEntryURL}
					experiences={experiences}
					segments={segments}
					selectedExperience={selectedExperience}
					selectId={selectId}
				/>
			</div>
		</APIContext.Provider>
	);
}
