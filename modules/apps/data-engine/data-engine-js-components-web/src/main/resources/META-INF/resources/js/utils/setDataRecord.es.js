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

import extractDataRecordValueKey from '../utils/extractDataRecordValueKey.es';

export default (
	{
		locale,
		localizable,
		localizedValue = {
			[locale]: '',
		},
		localizedValueEdited,
		name,
		transient,
		value = '',
		visible,
	},
	dataRecordValues,
	languageId,
	preserveValue
) => {
	const dataRecordValueKey = extractDataRecordValueKey(name);

	if (transient) {
		dataRecordValues[dataRecordValueKey] = '';

		return;
	}

	let _value = value;

	if (typeof value !== 'string') {
		_value = JSON.stringify(value);
	}

	if (!visible) {
		_value = '';
	}

	if (localizable) {
		const edited =
			!!localizedValue?.[languageId] ||
			(localizedValueEdited && localizedValueEdited[languageId]);

		let availableLanguageIds;

		Object.keys(localizedValue)
			.filter(
				(languageId) =>
					!localizedValueEdited?.[languageId] &&
					(localizedValue[languageId] === '' ||
						localizedValue[languageId] === '[]' ||
						localizedValue[languageId] === '{}')
			)
			.forEach((languageId) => {
				delete localizedValue[languageId];
			});

		if (localizedValue) {
			availableLanguageIds = Object.keys(localizedValue);
		}
		else {
			availableLanguageIds = [];
		}

		if (!availableLanguageIds.includes(languageId)) {
			availableLanguageIds.push(languageId);
		}

		dataRecordValues[dataRecordValueKey] = {...localizedValue};

		if (edited) {
			dataRecordValues[dataRecordValueKey] = {
				...localizedValue,
				[languageId]: _value,
			};
		}
		else if (preserveValue) {
			dataRecordValues[dataRecordValueKey] = {
				...localizedValue,
				[languageId]: value,
			};
		}
	}
	else {
		dataRecordValues[dataRecordValueKey] = _value;
	}
};
