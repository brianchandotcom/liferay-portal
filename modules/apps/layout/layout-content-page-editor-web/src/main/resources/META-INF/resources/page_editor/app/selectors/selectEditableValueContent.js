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

import {SEGMENTS_EXPERIENCE_ID_PREFIX} from '../config/constants/segmentsExperienceIdPrefix';
import {config} from '../config/index';
import selectEditableValue from './selectEditableValue';
import selectPrefixedSegmentsExperienceId from './selectPrefixedSegmentsExperienceId';

export default function selectEditableValueContent(
	state,
	fragmentEntryLinkId,
	editableId,
	processorType
) {
	const {languageId} = state;
	const defaultPrefixedSegmentsExperienceId = `${SEGMENTS_EXPERIENCE_ID_PREFIX}${config.defaultSegmentsExperienceId}`;
	const prefixedSegmentsExperienceId = selectPrefixedSegmentsExperienceId(
		state
	);

	const data = selectEditableValue(
		state,
		fragmentEntryLinkId,
		editableId,
		processorType
	);

	let content = data;

	if (content[prefixedSegmentsExperienceId]) {
		content = content[prefixedSegmentsExperienceId];
	}
	else if (content[defaultPrefixedSegmentsExperienceId]) {
		content = content[defaultPrefixedSegmentsExperienceId];
	}

	if (content[languageId]) {
		content = content[languageId];
	}
	else if (content[config.defaultLanguageId]) {
		content = content[config.defaultLanguageId];
	}

	if (typeof content !== 'string') {
		content = data.defaultValue;
	}

	return content;
}
