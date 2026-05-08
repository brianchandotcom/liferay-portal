/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {FormikFieldContentSelector} from '../../../components/forms/formik';
import {ImportPreview} from '../../../types/exportImportPreview';
import FileSummary from './FileSummary';

export default function DataSelectionStep({
	importPreview,
}: {
	importPreview?: ImportPreview;
}) {
	if (!importPreview) {
		return null;
	}

	return (
		<>
			<FileSummary importPreview={importPreview} />

			<FormikFieldContentSelector
				name="contentSelection"
				sections={importPreview.portletDataHandlerSections}
			/>
		</>
	);
}
