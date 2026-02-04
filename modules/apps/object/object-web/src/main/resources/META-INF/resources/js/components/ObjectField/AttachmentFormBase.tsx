/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {SingleSelect, Toggle} from '@liferay/object-js-components-web';
import React from 'react';

import {normalizeFieldSettings} from '../../utils/fieldSettings';

import './ObjectFieldFormBase.scss';

interface IAttachmentFormBaseProps {
	disabled?: boolean;
	error?: string;
	objectDefinitionName: string;
	objectFieldSettings: ObjectFieldSetting[];
	onSubmit?: (values?: Partial<ObjectField>) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

const attachmentSources = [
	{
		description: Liferay.Language.get(
			'files-can-be-stored-in-an-object-entry-or-in-a-specific-folder-in-documents-and-media'
		),
		label: Liferay.Language.get('upload-directly-from-users-computer'),
		value: 'userComputerToDocumentsAndMedia',
	},
	{
		description: Liferay.Language.get(
			'users-can-upload-or-select-existing-files-from-documents-and-media'
		),
		label: Liferay.Language.get(
			'upload-or-select-from-documents-and-media-item-selector'
		),
		value: 'documentsAndMedia',
	},
];

export function AttachmentFormBase({
	disabled,
	error,
	objectDefinitionName,
	objectFieldSettings,
	onSubmit,
	setValues,
	values,
}: IAttachmentFormBaseProps) {
	const settings = normalizeFieldSettings(objectFieldSettings);

	const attachmentSource = attachmentSources.find(
		({value}) => value === settings.fileSource
	);

	const handleAttachmentSourceChange = (value: string) => {
		const fileSource: ObjectFieldSetting = {name: 'fileSource', value};

		const updatedSettings = objectFieldSettings.filter(
			(setting) =>
				setting.name !== 'fileSource' &&
				setting.name !== 'showFilesInLibrary' &&
				setting.name !== 'storageLibraryPath'
		);

		updatedSettings.push(fileSource);

		if (value === 'userComputerToDocumentsAndMedia') {
			updatedSettings.push({
				name: 'showFilesInLibrary',
				value: false,
			});
		}

		setValues({objectFieldSettings: updatedSettings});

		if (onSubmit) {
			onSubmit({
				...values,
				objectFieldSettings: updatedSettings,
			});
		}
	};

	const toggleShowFiles = (value: boolean) => {
		const updatedSettings = objectFieldSettings.filter(
			(setting) =>
				setting.name !== 'showFilesInLibrary' &&
				setting.name !== 'storageLibraryPath'
		);

		updatedSettings.push({
			name: 'showFilesInLibrary',
			value,
		});

		if (value) {
			updatedSettings.push({
				name: 'storageLibraryPath',
				value: `/${objectDefinitionName}`,
			});
		}

		setValues({objectFieldSettings: updatedSettings});
	};

	return (
		<>
			<SingleSelect
				disabled={disabled}
				error={error}
				items={attachmentSources}
				label={Liferay.Language.get('request-files')}
				onSelectionChange={(value) =>
					handleAttachmentSourceChange(value as string)
				}
				required
				selectedKey={attachmentSource?.value}
			/>

			{settings.fileSource === 'userComputerToDocumentsAndMedia' && (
				<ClayForm.Group className="lfr-objects__object-field-form-base-container">
					<Toggle
						disabled={disabled}
						label={Liferay.Language.get(
							'show-files-in-documents-and-media'
						)}
						name="showFilesInLibrary"
						onBlur={(event) => {
							event.stopPropagation();

							if (onSubmit) {
								onSubmit();
							}
						}}
						onToggle={toggleShowFiles}
						toggled={!!settings.showFilesInLibrary}
						tooltip={Liferay.Language.get(
							'when-activated-users-can-define-a-folder-within-documents-and-media-to-display-the-files-leave-it-unchecked-for-files-to-be-stored-individually-per-entry'
						)}
						tooltipAlign="top"
					/>
				</ClayForm.Group>
			)}
		</>
	);
}
