/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import {
	FormikFieldCheckbox,
	FormikFieldContentSelector,
} from '../../../components/forms/formik';
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

			{importPreview.deletionCount > 0 && (
				<ClayLayout.Sheet className="mt-4">
					<ClayLayout.SheetHeader className="mb-1">
						<div className="mb-2 sheet-title">
							{Liferay.Language.get('deletions')}
						</div>
					</ClayLayout.SheetHeader>

					<FormikFieldCheckbox
						description={Liferay.Language.get('deletions-help')}
						label={Liferay.Language.get(
							'replicate-selected-deletions'
						)}
						name="replicateDeletions"
					/>
				</ClayLayout.Sheet>
			)}

			<ClayLayout.Sheet className="mt-4">
				<ClayLayout.SheetHeader className="mb-1">
					<div className="mb-2 sheet-title">
						{Liferay.Language.get('permissions')}
					</div>
				</ClayLayout.SheetHeader>

				<FormikFieldCheckbox
					description={Liferay.Language.get(
						'export-import-permissions-help'
					)}
					label={Liferay.Language.get('import-permissions')}
					name="importPermissions"
				/>
			</ClayLayout.Sheet>

			<FormikFieldContentSelector
				name="contentSelection"
				sections={importPreview.portletDataHandlerSections}
			/>
		</>
	);
}
