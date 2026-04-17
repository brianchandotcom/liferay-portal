/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClaySticker, {DisplayType} from '@clayui/sticker';
import {
	FieldBase,
	MultipleFileUploader,
	UploadRequestCallback,
} from 'frontend-js-components-web';
import {getFileAsBase64} from 'frontend-js-web';
import React, {useId, useState} from 'react';

import ItemSelector from '../item_selector/ItemSelector';
import {FilesUploaderComponent} from '../item_selector/ItemSelectorModal';

type AssetLibrary = {
	externalReferenceCode: string;
	id: number;
	name: string;
	settings?: {logoColor?: string};
	siteId: number;
};

interface ISpaceInputProps extends React.InputHTMLAttributes<HTMLInputElement> {
	selectedLogoColor?: string;
	selectedName?: string;
	value?: string;
}

const SpaceInput = React.forwardRef<HTMLInputElement, ISpaceInputProps>(
	({selectedLogoColor, selectedName, value, ...otherProps}, ref) => {
		const showSticker = !!selectedName && !!value?.length;

		return (
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						className="form-control-select"
						insetBefore={showSticker}
						ref={ref}
						type="text"
						value={value}
						{...otherProps}
					/>

					{showSticker && (
						<ClayInput.GroupInsetItem before>
							<ClaySticker
								displayType={selectedLogoColor as DisplayType}
								size="sm"
							>
								{selectedName.charAt(0).toUpperCase()}
							</ClaySticker>
						</ClayInput.GroupInsetItem>
					)}
				</ClayInput.GroupItem>
			</ClayInput.Group>
		);
	}
);

const ASSET_LIBRARIES_API_URL = `${location.origin}/o/headless-asset-library/v1.0/asset-libraries?filter=type eq 'Space'`;

const CMSFileUploaderComponent: FilesUploaderComponent = function ({
	allowedExtensions,
	files,
	groupId,
	maxFileSize,
	onCloseUploadView,
}) {
	const [assetLibrary, setAssetLibrary] = useState<
		AssetLibrary | undefined
	>();
	const [groupIdError, setGroupIdError] = useState(false);

	const groupIdInputId = useId();

	const resolvedGroupId = groupId ?? assetLibrary?.siteId;

	const formValidation = groupId
		? undefined
		: async () => {
				const error = !resolvedGroupId || resolvedGroupId <= 0;

				setGroupIdError(error);

				return !error;
			};

	const uploadRequest: UploadRequestCallback = async ({fileData}) => {
		if (!resolvedGroupId) {
			throw new Error('spaceId is not defined');
		}

		const fileBase64 = await getFileAsBase64(fileData.file);

		const response = await Liferay.Util.fetch(
			`/o/cms/basic-documents/scopes/${resolvedGroupId}`,
			{
				body: JSON.stringify({
					file: {
						fileBase64,
						name: fileData.name,
					},
					objectEntryFolderExternalReferenceCode: 'L_FILES',
					title: fileData.name,
				}),
				headers: {
					'Accept': 'application/json',
					'Accept-Language':
						Liferay.ThemeDisplay.getBCP47LanguageId(),
					'Content-Type': 'application/json',
				},
				method: 'POST',
			}
		);

		return await response.json();
	};

	const onUploadComplete = ({
		failedFiles,
	}: {
		failedFiles: string[];
		successFiles: string[];
	}) => {
		if (!failedFiles.length) {
			onCloseUploadView();
		}
	};

	return (
		<MultipleFileUploader
			filesToUpload={files}
			formValidation={formValidation}
			maxFileSize={maxFileSize}
			onModalClose={onCloseUploadView}
			onUploadComplete={onUploadComplete}
			scopeSelectorElement={
				groupId ? undefined : (
					<div className="mt-4">
						<FieldBase
							errorMessage={
								groupIdError
									? Liferay.Language.get(
											'this-field-is-required'
										)
									: undefined
							}
							helpMessage={Liferay.Language.get(
								'select-the-space-to-upload-the-file'
							)}
							id={groupIdInputId}
							label={Liferay.Language.get('space')}
							required
						>
							<ItemSelector<AssetLibrary>
								apiURL={ASSET_LIBRARIES_API_URL}
								as={SpaceInput}
								id={groupIdInputId}
								items={assetLibrary ? [assetLibrary] : []}
								locator={{
									id: 'id',
									label: 'name',
									value: 'externalReferenceCode',
								}}
								onItemsChange={(items) => {
									setGroupIdError(false);
									setAssetLibrary(items[0]);
								}}
								onKeyDown={(
									event: React.KeyboardEvent<HTMLInputElement>
								) => {
									if (event.key === 'Enter') {
										event.preventDefault();
									}
								}}
								selectedLogoColor={
									assetLibrary?.settings?.logoColor
								}
								selectedName={assetLibrary?.name}
							>
								{(item) => (
									<ItemSelector.Item
										key={item.externalReferenceCode}
										textValue={item.name}
									>
										<div className="align-items-center c-gap-2 d-flex">
											<ClaySticker
												displayType={
													item.settings
														?.logoColor as DisplayType
												}
												size="sm"
											>
												{item.name
													.charAt(0)
													.toUpperCase()}
											</ClaySticker>

											<span>{item.name}</span>
										</div>
									</ItemSelector.Item>
								)}
							</ItemSelector>
						</FieldBase>
					</div>
				)
			}
			uploadRequest={uploadRequest}
			validExtensions={allowedExtensions}
		/>
	);
};

export default CMSFileUploaderComponent;
