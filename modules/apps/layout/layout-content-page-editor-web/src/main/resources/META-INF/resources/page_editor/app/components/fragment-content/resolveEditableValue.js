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

import {config} from '../../config/index';
import InfoItemService from '../../services/InfoItemService';

export default function(
	editableValues,
	editableId,
	processorType,
	languageId,
	prefixedSegmentsExperienceId,
	getFieldValue = InfoItemService.getAssetFieldValue
) {
	const editableValue = editableValues[processorType][editableId];

	let valuePromise;

	if (editableIsMappedToInfoItem(editableValue)) {
		valuePromise = getFieldValue({
			classNameId: editableValue.classNameId,
			classPK: editableValue.classPK,
			collectionFieldId: editableValue.collectionFieldId,
			fieldId: editableValue.fieldId,
			languageId,
		}).catch(() => {
			return selectEditableValueContent(
				editableValue,
				languageId,
				prefixedSegmentsExperienceId
			);
		});
	}
	else {
		valuePromise = Promise.resolve(
			selectEditableValueContent(
				editableValue,
				languageId,
				prefixedSegmentsExperienceId
			)
		);
	}

	let configPromise;

	if (editableIsMappedToInfoItem(editableValue.config)) {
		configPromise = getFieldValue({
			classNameId: editableValue.config.classNameId,
			classPK: editableValue.config.classPK,
			collectionFieldId: editableValue.config.collectionFieldId,
			fieldId: editableValue.config.fieldId,
			languageId,
		})
			.then(href => {
				return {...editableValue.config, href};
			})
			.catch(() => {
				return {...editableValue.config};
			});
	}
	else {
		configPromise = Promise.resolve(editableValue.config);
	}

	return Promise.all([valuePromise, configPromise]);
}

function selectEditableValueContent(
	editableValue,
	languageId,
	prefixedSegmentsExperienceId
) {
	let content = editableValue;

	if (content[prefixedSegmentsExperienceId]) {
		content = content[prefixedSegmentsExperienceId];
	}

	if (content[languageId]) {
		content = content[languageId];
	}
	else if (content[config.defaultLanguageId]) {
		content = content[config.defaultLanguageId];
	}

	if (typeof content !== 'string') {
		content = editableValue.defaultValue;
	}

	return content;
}

function editableIsMappedToInfoItem(editableValue) {
	return (
		editableValue &&
		((editableValue.classNameId &&
			editableValue.classPK &&
			editableValue.fieldId) ||
			editableValue.collectionFieldId)
	);
}
