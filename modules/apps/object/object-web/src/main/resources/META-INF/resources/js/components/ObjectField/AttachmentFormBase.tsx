/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {SingleSelect, Toggle} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {normalizeFieldSettings} from '../../utils/fieldSettings';

import './ObjectFieldFormBase.scss';

interface IAttachmentFormBaseProps {
	disabled?: boolean;
	error?: string;
	hasDepotEntry?: boolean;
	objectDefinitionName: string;
	objectFieldSettings: ObjectFieldSetting[];
	onSubmit?: (values?: Partial<ObjectField>) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

export function AttachmentFormBase({
	disabled,
	error,
	hasDepotEntry,
	objectDefinitionName,
	objectFieldSettings,
	onSubmit,
	setValues,
	values,
}: IAttachmentFormBaseProps) {
	const [spaces, setSpaces] = useState<Space[]>([]);

	const attachmentSources = [
		{
			description: Liferay.Language.get(
				'files-can-be-stored-in-an-object-entry-or-in-a-specific-folder-in-documents-and-media'
			),
			label:
				Liferay.Language.get('upload-directly-from-users-computer') +
				` (${Liferay.Language.get('documents-and-media')})`,
			value: 'userComputerToDocumentsAndMedia',
		},
		...(Liferay.FeatureFlags['LPD-74813'] && hasDepotEntry
			? [
					{
						label:
							Liferay.Language.get(
								'upload-directly-from-users-computer'
							) + ` (${Liferay.Language.get('cms-files')})`,
						value: 'userComputerToDepotFiles',
					},
					{
						label: Liferay.Language.get(
							'upload-or-select-from-cms-files'
						),
						value: 'depotFiles',
					},
				]
			: []),
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

	const settings = normalizeFieldSettings(objectFieldSettings);

	const isUserComputerDMUpload =
		settings.fileSource === 'userComputerToDocumentsAndMedia';

	const isUserComputerDepotUpload =
		settings.fileSource === 'userComputerToDepotFiles';

	const allowsLibraryStorage =
		Liferay.FeatureFlags['LPD-74813'] &&
		(isUserComputerDMUpload || isUserComputerDepotUpload);

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

	const toggleShowFilesInLibrary = (value: boolean) => {
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
			if (settings.fileSource === 'userComputerToDocumentsAndMedia') {
				updatedSettings.push({
					name: 'storageLibraryPath',
					value: `/${objectDefinitionName}`,
				});
			}
			else if (settings.fileSource === 'userComputerToDepotFiles') {
				updatedSettings.push(
					{
						name: 'storageLibraryPath',
						value: `/${objectDefinitionName}`,
					},
					{
						name: 'storageDepot',
						value: String(spaces[0].externalReferenceCode),
					}
				);
			}
		}

		setValues({objectFieldSettings: updatedSettings});
	};

	useEffect(() => {
		if (Liferay.FeatureFlags['LPD-74813'] && hasDepotEntry) {
			fetch(
				`/o/headless-asset-library/v1.0/asset-libraries?filter=type eq 'Space'`
			)
				.then((response) => response.json())
				.then((data) => setSpaces(data?.items ?? []));
		}
	}, [hasDepotEntry]);

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

			{allowsLibraryStorage && (
				<ClayForm.Group className="lfr-objects__object-field-form-base-container">
					<Toggle
						disabled={disabled}
						label={Liferay.Language.get(
							'show-uploaded-files-in-library'
						)}
						name="showFilesInLibrary"
						onBlur={(event) => {
							event.stopPropagation();

							if (onSubmit) {
								onSubmit();
							}
						}}
						onToggle={toggleShowFilesInLibrary}
						toggled={!!settings.showFilesInLibrary}
						tooltipAlign="top"
					/>
				</ClayForm.Group>
			)}
		</>
	);
}
