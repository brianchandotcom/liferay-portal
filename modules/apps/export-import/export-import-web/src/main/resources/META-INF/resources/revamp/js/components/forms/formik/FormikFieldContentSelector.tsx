/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useFormikContext} from 'formik';
import React from 'react';

import {PortletDataHandlerSection} from '../../../utils/mockPortletDataHandlerSections';
import ContentSelector, {
	ContentSelection,
} from '../content_selector/ContentSelector';

interface FormikFieldContentSelectorProps {
	name: string;
	sections: PortletDataHandlerSection[];
}

export function FormikFieldContentSelector({
	name,
	sections,
}: FormikFieldContentSelectorProps) {
	const {setFieldValue, values} =
		useFormikContext<Record<string, ContentSelection | undefined>>();

	const value = values[name];

	return (
		<ContentSelector
			onChange={(newValue) => setFieldValue(name, newValue)}
			sections={sections}
			value={value}
		/>
	);
}
