/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useField, useFormikContext} from 'formik';
import React, {useEffect} from 'react';

import {PageTreeModalConfiguration} from '../../../pages/export/components/PageTreeModal';
import {ExportImportProcess} from '../../../types/exportImportProcess';
import {PreviewPortletDataHandlerSection} from '../../../types/portletDataHandler';
import {getFullDataSelection} from '../../../utils/contentSelection';
import ContentSelector, {
	ContentSelection,
} from '../content_selector/ContentSelector';

interface FormikFieldContentSelectorProps {
	'aria-labelledby'?: string;
	'commentsAndRatingsEnabled'?: boolean;
	'lookAndFeelEnabled'?: boolean;
	'name': string;
	'pageTreeModalConfiguration'?: PageTreeModalConfiguration;
	'process'?: ExportImportProcess;
	'sections': PreviewPortletDataHandlerSection[];
}

export function FormikFieldContentSelector({
	'aria-labelledby': ariaLabelledby,
	commentsAndRatingsEnabled = false,
	lookAndFeelEnabled = false,
	name,
	pageTreeModalConfiguration,
	process = 'export',
	sections,
}: FormikFieldContentSelectorProps) {
	const [field, meta, helpers] = useField<ContentSelection | undefined>(name);
	const [{value: deletions}] = useField<boolean | undefined>('deletions');
	const {setFieldTouched, setFieldValue} = useFormikContext();

	const showDeletions = !!deletions;

	const shouldSeed =
		!!sections.length && field.value === undefined && !meta.touched;

	const seededContentSelection = shouldSeed
		? getFullDataSelection(sections, {
				commentsAndRatingsEnabled,
				lookAndFeelEnabled,
				showDeletions,
			})
		: undefined;

	useEffect(() => {
		if (seededContentSelection) {
			setFieldValue(name, seededContentSelection);
		}
	}, [name, seededContentSelection, setFieldValue]);

	return (
		<ContentSelector
			aria-labelledby={ariaLabelledby}
			commentsAndRatingsEnabled={commentsAndRatingsEnabled}
			errorMessage={meta.touched && meta.error ? meta.error : undefined}
			lookAndFeelEnabled={lookAndFeelEnabled}
			name={name}
			onChange={(newValue) => {
				helpers.setValue(newValue);
				setFieldTouched(name, true, false);
			}}
			pageTreeModalConfiguration={pageTreeModalConfiguration}
			process={process}
			sections={sections}
			showDeletions={showDeletions}
			value={field.value ?? seededContentSelection}
		/>
	);
}
