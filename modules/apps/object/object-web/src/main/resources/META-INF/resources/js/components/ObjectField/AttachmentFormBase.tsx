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
	objectFieldSettings: ObjectFieldSetting[];
	objectName: string;
	setValues: (values: Partial<ObjectField>) => void;
}

const attachmentSources = [
	{
		description: Liferay.Language.get(
			'files-can-be-stored-in-an-object-entry-or-in-a-specific-folder-in-documents-and-media'
		),
		label: Liferay.Language.get('upload-directly-from-users-computer'),
		value: 'userComputer',
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
	objectFieldSettings,
	objectName,
	setValues,
}: IAttachmentFormBaseProps) {
	const settings = normalizeFieldSettings(objectFieldSettings);

	const attachmentSource = attachmentSources.find(
		({value}) => value === settings.fileSource
	);

	const handleAttachmentSourceChange = ({value}: {value: string}) => {
		const fileSource: ObjectFieldSetting = {name: 'fileSource', value};

		const updatedSettings = objectFieldSettings.filter(
			(setting) =>
				setting.name !== 'fileSource' &&
				setting.name !== 'showFilesInDocumentsAndMedia' &&
				setting.name !== 'storageDLFolderPath'
		);

		updatedSettings.push(fileSource);

		if (value === 'userComputer') {
			updatedSettings.push({
				name: 'showFilesInDocumentsAndMedia',
				value: false,
			});
		}

		setValues({objectFieldSettings: updatedSettings});
	};

	const toggleShowFiles = (value: boolean) => {
		const updatedSettings = objectFieldSettings.filter(
			(setting) =>
				setting.name !== 'showFilesInDocumentsAndMedia' &&
				setting.name !== 'storageDLFolderPath'
		);

		updatedSettings.push({
			name: 'showFilesInDocumentsAndMedia',
			value,
		});

		if (value) {
			updatedSettings.push({
				name: 'storageDLFolderPath',
				value: `/${objectName}`,
			});
		}

		setValues({objectFieldSettings: updatedSettings});
	};

	return (
		<>
			<SingleSelect
				disabled={disabled}
				error={error}
				label={Liferay.Language.get('request-files')}
				onChange={handleAttachmentSourceChange}
				options={attachmentSources}
				required
				value={attachmentSource?.label}
			/>

			{settings.fileSource === 'userComputer' && (
				<ClayForm.Group className="lfr-objects__object-field-form-base-container">
					<Toggle
						disabled={disabled}
						label={Liferay.Language.get(
							'show-files-in-documents-and-media'
						)}
						name="showFilesInDocumentsAndMedia"
						onToggle={toggleShowFiles}
						toggled={!!settings.showFilesInDocumentsAndMedia}
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
