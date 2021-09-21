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

import {
	PagesVisitor,
	generateName,
	useConfig,
	useFormState,
} from 'data-engine-js-components-web';
import {useMemo} from 'react';

const getPredefinedValues = ({locale, localizedValue, options}) => {
	const predefinedValue = localizedValue[locale];

	if (!Array.isArray(predefinedValue)) {
		return predefinedValue;
	}

	const optionValues = new Set(options.map(({value}) => value));

	return predefinedValue.filter((value) => optionValues.has(value));
};

const filterSettingsContext = ({
	defaultLanguageId,
	disabledProperties,
	disabledTabs,
	editingLanguageId,
	settingsContext: {pages, ...otherSettings},
	unimplementedProperties,
	visibleProperties,
}) => {
	const hiddenProperties = new Set([
		...unimplementedProperties,
		...disabledProperties,
	]);
	const hiddenTabs = new Set(disabledTabs);
	const visiblePages = pages.filter(({title}) => !hiddenTabs.has(title));
	const visitor = new PagesVisitor(visiblePages);
	const updatedPages = visitor.mapFields(
		({
			fieldName,
			instanceId,
			locale,
			localizedValue,
			name,
			options,
			portletNamespace,
			repeatedIndex,
		}) => {
			const getName = () =>
				generateName(name, {
					editingLanguageId,
					fieldName,
					instanceId,
					portletNamespace,
					repeatedIndex,
				});
			const updatedProperties = {
				defaultLanguageId,
				editingLanguageId,
			};

			if (hiddenProperties.has(fieldName)) {
				updatedProperties.name = getName();
				updatedProperties.visibilityExpression = 'FALSE';
				updatedProperties.visible = false;

				return updatedProperties;
			}

			if (visibleProperties.includes(fieldName)) {
				updatedProperties.visibilityExpression = 'TRUE';
				updatedProperties.visible = true;
			}

			switch (fieldName) {
				case 'dataSourceType': {
					updatedProperties.name = getName();
					updatedProperties.predefinedValue = '["manual"]';

					if (!name.includes('form_web')) {
						updatedProperties.readOnly = true;
						updatedProperties.visibilityExpression = 'FALSE';
						updatedProperties.visible = false;
					}
					break;
				}
				case 'ddmDataProviderInstanceId':
				case 'ddmDataProviderInstanceOutput': {
					if (!name.includes('form_web')) {
						updatedProperties.visibilityExpression = 'FALSE';
						updatedProperties.visible = false;
					}
					break;
				}
				case 'localizable': {
					updatedProperties.showAsSwitcher = true;
					break;
				}
				case 'name': {
					updatedProperties.readOnly = true;
					break;
				}
				case 'predefinedValue': {
					updatedProperties.localizedValue = {
						...localizedValue,
						[locale]: getPredefinedValues({
							locale,
							localizedValue,
							options,
						}),
					};
					updatedProperties.name = getName();
					break;
				}
				case 'repeatable': {
					if (!name.includes('form_web')) {
						updatedProperties.name = getName();
						updatedProperties.showMaximumRepetitionsInfo = false;
					}
					break;
				}
				default: {
					updatedProperties.name = getName();
				}
			}

			return updatedProperties;
		},
		true
	);

	return {
		...otherSettings,
		pages: updatedPages,
	};
};

export const useSettingsContextFilter = (settingsContext) => {
	const {defaultLanguageId, editingLanguageId} = useFormState();

	const {
		disabledProperties,
		disabledTabs,
		unimplementedProperties,
		visibleProperties,
	} = useConfig();

	const filteredSettingsContext = useMemo(
		() =>
			filterSettingsContext({
				defaultLanguageId,
				disabledProperties,
				disabledTabs,
				editingLanguageId,
				settingsContext,
				unimplementedProperties,
				visibleProperties,
			}),
		[
			defaultLanguageId,
			disabledProperties,
			disabledTabs,
			editingLanguageId,
			settingsContext,
			unimplementedProperties,
			visibleProperties,
		]
	);

	return filteredSettingsContext;
};
