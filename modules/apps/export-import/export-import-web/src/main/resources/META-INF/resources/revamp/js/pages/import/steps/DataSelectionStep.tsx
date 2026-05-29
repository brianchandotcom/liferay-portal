/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import CheckboxSheet from '../../../components/forms/CheckboxSheet';
import {FormikFieldContentSelector} from '../../../components/forms/formik';
import {ImportPreview} from '../../../types/exportImportPreview';
import FileSummary from './FileSummary';

export default function DataSelectionStep({
	commentsAndRatingsEnabled = false,
	importPreview,
	lookAndFeelEnabled = false,
}: {
	commentsAndRatingsEnabled?: boolean;
	importPreview?: ImportPreview;
	lookAndFeelEnabled?: boolean;
}) {
	if (!importPreview) {
		return null;
	}

	return (
		<>
			<FileSummary importPreview={importPreview} />

			{importPreview.deletionCount > 0 && (
				<CheckboxSheet
					description={Liferay.Language.get('deletions-help')}
					label={Liferay.Language.get('replicate-selected-deletions')}
					name="deletions"
					title={Liferay.Language.get('deletions')}
				/>
			)}

			<CheckboxSheet
				description={Liferay.Language.get(
					'export-import-permissions-help'
				)}
				label={Liferay.Language.get('import-permissions')}
				name="permissions"
				title={Liferay.Language.get('permissions')}
			/>

			<FormikFieldContentSelector
				commentsAndRatingsEnabled={commentsAndRatingsEnabled}
				commentsAndRatingsSubtitle={Liferay.Language.get(
					'for-each-of-the-selected-content-types,-import-their'
				)}
				lookAndFeelEnabled={lookAndFeelEnabled}
				name="contentSelection"
				sections={importPreview.previewPortletDataHandlerSections}
			/>
		</>
	);
}
