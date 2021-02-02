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
import isMapped from '../../utils/editable-value/isMapped';
import isMappedToCollection from '../../utils/editable-value/isMappedToCollection';
import isMappedToInfoItem from '../../utils/editable-value/isMappedToInfoItem';

export default function resolveEditableValue(
	editableValue,
	languageId,
	getFieldValue = null
) {
	let valuePromise;

	if (isMapped(editableValue)) {
		if (getFieldValue) {
			valuePromise = getFieldValue({
				...editableValue,
				languageId,
			}).catch(() => resolveRawEditableValue(editableValue, languageId));
		}
		else if (isMappedToInfoItem()) {
			valuePromise =  InfoItemService.getInfoItemFieldValue({
				...editableValue,
				languageId,
			}).catch(() => resolveRawEditableValue(editableValue, languageId));
		}
		else {
			valuePromise = resolveRawEditableValue(editableValue, languageId);
		}
	}
	else {
		valuePromise = resolveRawEditableValue(editableValue, languageId);
	}

	let configPromise;

	const editableConfig = editableValue.config
		? editableValue.config[languageId] ||
		  editableValue.config[config.defaultLanguageId] ||
		  editableValue.config
		: editableValue.config;

	if (
		isMappedToInfoItem(editableConfig) ||
		isMappedToCollection(editableConfig)
	) {
		configPromise = getFieldValue({
			...editableConfig,
			languageId,
		})
			.then((href) => {
				return {...editableConfig, href};
			})
			.catch(() => {
				return {...editableConfig};
			});
	}
	else {
		configPromise = Promise.resolve(editableConfig);
	}

	return Promise.all([valuePromise, configPromise]);
}

function resolveRawEditableValue(editableValue, languageId) {
	let content = editableValue;

	if (content[languageId]) {
		content = content[languageId];
	}
	else if (content[config.defaultLanguageId]) {
		content = content[config.defaultLanguageId];
	}

	if (content == null || content.defaultValue) {
		content = editableValue.defaultValue;
	}

	return Promise.resolve(content);
}
