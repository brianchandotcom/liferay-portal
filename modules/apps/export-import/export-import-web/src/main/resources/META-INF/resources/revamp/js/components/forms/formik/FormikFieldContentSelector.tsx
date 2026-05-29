/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useField, useFormikContext} from 'formik';
import React from 'react';

import {PageTreeModalConfiguration} from '../../../pages/export/components/PageTreeModal';
import {PreviewPortletDataHandlerSection} from '../../../types/portletDataHandler';
import ContentSelector, {
	ContentSelection,
} from '../content_selector/ContentSelector';

interface FormikFieldContentSelectorProps {
	'aria-labelledby'?: string;
	'commentsAndRatingsEnabled'?: boolean;
	'commentsAndRatingsSubtitle'?: string;
	'lookAndFeelEnabled'?: boolean;
	'name': string;
	'pageTreeModalConfiguration'?: PageTreeModalConfiguration;
	'sections': PreviewPortletDataHandlerSection[];
}

export function FormikFieldContentSelector({
	'aria-labelledby': ariaLabelledby,
	commentsAndRatingsEnabled = false,
	commentsAndRatingsSubtitle,
	lookAndFeelEnabled = false,
	name,
	pageTreeModalConfiguration,
	sections,
}: FormikFieldContentSelectorProps) {
	const [field, meta, helpers] = useField<ContentSelection | undefined>(name);
	const [{value: deletions}] = useField<boolean | undefined>('deletions');
	const {setFieldTouched} = useFormikContext();

	return (
		<ContentSelector
			aria-labelledby={ariaLabelledby}
			commentsAndRatingsEnabled={commentsAndRatingsEnabled}
			commentsAndRatingsSubtitle={commentsAndRatingsSubtitle}
			errorMessage={meta.touched && meta.error ? meta.error : undefined}
			lookAndFeelEnabled={lookAndFeelEnabled}
			name={name}
			onChange={(newValue) => {
				helpers.setValue(newValue);
				setFieldTouched(name, true, false);
			}}
			pageTreeModalConfiguration={pageTreeModalConfiguration}
			sections={sections}
			showDeletions={!!deletions}
			value={field.value}
		/>
	);
}
