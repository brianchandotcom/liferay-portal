/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
