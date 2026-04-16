/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React, {useState} from 'react';

import {getValidateLarFileEndpoint} from '../../../common/utils/getValidateLarFileEndpoint';
import {FormikFieldFileSelector} from '../../../components/forms/formik/FormikFieldFileSelector';
import {useWizard} from '../NewImport';

export default function FileSelectionStep() {
	const [progress, setProgress] = useState<number>();
	const {isCompanyGroup} = useWizard();

	const handleUpload = (file: File, signal?: AbortSignal) =>
		getValidateLarFileEndpoint({
			file,
			isCompanyGroup,
			onProgress: setProgress,
			signal,
		});

	return (
		<>
			<ClayLayout.Sheet>
				<ClayLayout.SheetHeader className="mb-1">
					<div className="mb-2 sheet-title">
						{Liferay.Language.get('import-details')}
					</div>
				</ClayLayout.SheetHeader>
			</ClayLayout.Sheet>

			<ClayLayout.Sheet>
				<ClayLayout.SheetHeader className="mb-1">
					<div className="mb-2 sheet-title" id="fileSelector-label">
						{Liferay.Language.get('file-upload')}
					</div>

					<div
						className="sheet-text text-3"
						id="fileSelector-description"
					>
						{Liferay.Language.get(
							'select-and-upload-your-prepared-file'
						)}
					</div>
				</ClayLayout.SheetHeader>

				<FormikFieldFileSelector
					aria-describedby="fileSelector-description"
					aria-labelledby="fileSelector-label"
					name="fileSelector"
					progress={progress}
					uploadRequest={handleUpload}
					validExtensions=".lar"
				/>
			</ClayLayout.Sheet>
		</>
	);
}
