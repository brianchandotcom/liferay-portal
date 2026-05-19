/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import {Wizard, WizardStep} from '../../components/Wizard';
import {postImportProcess} from '../../services/postImportProcess';
import {ImportPreview} from '../../types/exportImportPreview';
import {toRequestPortletDataHandlers} from '../../utils/toRequestPortletDataHandlers';
import DataSelectionStep from './steps/DataSelectionStep';
import FileSelectionStep from './steps/FileSelectionStep';
import SettingsStep, {SETTINGS_STEP_INITIAL_VALUES} from './steps/SettingsStep';

export function NewImport({
	backURL,
	importPreviewAPIURL,
	importProcessAPIURL,
}: {
	backURL: string;
	importPreviewAPIURL: string;
	importProcessAPIURL: string;
}) {
	const [importPreview, setImportPreview] = useState<
		ImportPreview | undefined
	>();

	return (
		<Wizard backURL={backURL}>
			<WizardStep
				description={Liferay.Language.get(
					'name-your-import-process-and-upload-your-file'
				)}
				initialValues={{
					fileSelector: undefined,
					name: '',
				}}
				isStepValid={(values) =>
					values.fileSelector instanceof File &&
					!!values.name.trim() &&
					!!importPreview
				}
				title={Liferay.Language.get('setup')}
			>
				<FileSelectionStep
					importPreviewAPIURL={importPreviewAPIURL}
					setImportPreview={setImportPreview}
				/>
			</WizardStep>

			<WizardStep
				description={Liferay.Language.get(
					'select-the-data-from-your-file-that-you-would-like-to-import'
				)}
				initialValues={{
					contentSelection: undefined,
					deletions: false,
					importPermissions: false,
				}}
				isStepValid={(values) => !!values.contentSelection}
				title={Liferay.Language.get('data-selection')}
			>
				<DataSelectionStep importPreview={importPreview} />
			</WizardStep>

			<WizardStep
				actionButton={
					<ClayButton type="submit">
						<span className="inline-item inline-item-before">
							<ClayIcon className="mr-1" symbol="import" />
						</span>

						{Liferay.Language.get('import')}
					</ClayButton>
				}
				description={Liferay.Language.get(
					'set-up-your-import-configuration'
				)}
				initialValues={SETTINGS_STEP_INITIAL_VALUES}
				onSubmit={async (values) => {
					if (!importPreview) {
						return;
					}

					const result = await postImportProcess({
						importRequest: {
							fileEntryId: importPreview.fileEntryId,
							requestPortletDataHandlers:
								toRequestPortletDataHandlers(
									importPreview.previewPortletDataHandlerSections ??
										[],
									values.contentSelection
								),
						},
						url: importProcessAPIURL,
					});

					if (result.error) {
						Liferay.Util.openToast({
							message: result.error,
							type: 'danger',
						});

						return;
					}

					window.location.href = backURL;
				}}
				title={Liferay.Language.get('settings')}
			>
				<SettingsStep />
			</WizardStep>
		</Wizard>
	);
}
