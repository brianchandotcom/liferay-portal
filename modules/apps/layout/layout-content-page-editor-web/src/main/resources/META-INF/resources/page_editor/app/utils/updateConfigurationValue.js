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

import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {config} from '../config/index';
import updateFragmentConfiguration from '../thunks/updateFragmentConfiguration';

export default function updateConfigurationValue({
	configuration,
	dispatch,
	fragmentEntryLink,
	languageId,
	name,
	value,
}) {
	const configurationValues =
		fragmentEntryLink.editableValues?.[
			FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
		] ?? {};

	const localizable =
		configuration?.fieldSets?.some((fieldSet) =>
			fieldSet.fields.some(
				(field) => field.name === name && field.localizable
			)
		) ?? false;

	const currentValue = configurationValues[name];

	const nextConfigurationValues = {
		...configurationValues,
		[name]: localizable
			? {
					...(typeof currentValue === 'object'
						? currentValue
						: {[config.defaultLanguageId]: currentValue}),
					[languageId]: value,
			  }
			: value,
	};

	dispatch(
		updateFragmentConfiguration({
			configurationValues: nextConfigurationValues,
			fragmentEntryLink,
			languageId,
		})
	);
}
