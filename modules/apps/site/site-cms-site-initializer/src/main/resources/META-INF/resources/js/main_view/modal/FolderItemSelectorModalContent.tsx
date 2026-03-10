/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Alert from '@clayui/alert';
import {useModal} from '@clayui/modal';
import {IFrontendDataSetProps, IView} from '@liferay/frontend-data-set-web';
import {ItemSelectorModal} from '@liferay/frontend-js-item-selector-web';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import ApiHelper, {RequestResult} from '../../common/services/ApiHelper';
import FolderService from '../../common/services/FolderService';
import {AssetLibrary} from '../../common/types/AssetLibrary';
import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../../common/utils/constants';
import {openCMSModal} from '../../common/utils/openCMSModal';
import {displayErrorToast} from '../../common/utils/toastUtil';
import {triggerAssetBulkAction} from '../props_transformer/actions/triggerAssetBulkAction';
import DuplicatedAssetFolderNamesModalContent, {
	Option,
} from './DuplicatedAssetFolderNamesModalContent';

export type TFolderItemSelectorModalContent = {
	action: Action;
	apiURL?: string;
	assetLibraries: AssetLibrary[];
	dataSetId?: string;
	isBulk?: boolean;
	itemData: ItemData;
	loadData: () => {};
	objectEntryFolderExternalReferenceCode: string | undefined;
	rootObjectEntryFolderExternalReferenceCode: string;
	selectedData?: any;
};

export type Action = 'copy' | 'move';

type Folder = {
	id: number;
	title: string;
};

type Space = {
	name: string;
	scopeId: number;
};

const SPACES_URL = `${window.location.origin}/o/headless-asset-library/v1.0/asset-libraries?filter=type eq 'Space'`;

const SUCCESS_MESSAGES = {
	copy: Liferay.Language.get('x-was-successfully-copied-to-x'),
	move: Liferay.Language.get('x-was-successfully-moved-to-x'),
};

const FDS_DEFAULT_PROPS: Partial<IFrontendDataSetProps> = {
	pagination: {
		deltas: [{label: 20}, {label: 40}, {label: 60}],
		initialDelta: 20,
	},
	selectionType: 'single',
};

const getSpaceFoldersURL = (cmsSection: string, scopeId: number) => {
	return `${window.location.origin}/o/search/v1.0/search?emptySearch=true&entryClassNames=${OBJECT_ENTRY_FOLDER_CLASS_NAME}&filter=(cmsSection eq '${cmsSection}' or title eq '${cmsSection}') and (status in (0, 2, 3))&nestedFields=description,embedded,file.thumbnailURL&scope=${scopeId}`;
};

const displayInfoToast = (
	action: Action,
	folder: Folder,
	itemData: ItemData
) => {
	openToast({
		message: sub(
			action === 'copy'
				? Liferay.Language.get('copying-x-to-x')
				: Liferay.Language.get('moving-x-to-x'),
			`${Liferay.Util.escapeHTML(itemData.embedded.title)}`,
			`<strong>${Liferay.Util.escapeHTML(folder.title)}</strong>`
		),
		type: 'info',
	});
};

const displaySuccessToast = (message: string, ...args: string[]) => {
	openToast({
		message: sub(message, args),
		type: 'success',
	});
};

const displayToast = (
	action: Action,
	error: any,
	folder: Folder,
	itemData: ItemData,
	message: string
) => {
	if (error) {
		let errorMessage = error;

		if (error?.status === 'BAD_REQUEST') {
			errorMessage = sub(
				action === 'copy'
					? Liferay.Language.get(
							'x-could-not-be-copied.-please-ensure-the-structure-it-is-using-exists-in-the-destination-space'
						)
					: Liferay.Language.get(
							'x-could-not-be-moved.-please-ensure-the-structure-it-is-using-exists-in-the-destination-space'
						),
				itemData.title
			);
		}

		displayErrorToast(errorMessage);
	}
	else {
		displaySuccessToast(
			message,
			`${Liferay.Util.escapeHTML(itemData.embedded.title)}`,
			`<strong>${Liferay.Util.escapeHTML(folder.title)}</strong>`
		);
	}
};

function executeBulkCopyAction({
	apiURL,
	dataSetId,
	folder,
	onClose,
	selectedData,
	targetName,
}: {
	apiURL: string | undefined;
	dataSetId?: string;
	folder: Folder;
	onClose: () => void;
	selectedData: any;
	targetName?: string;
}) {
	triggerAssetBulkAction({
		additionalData: {
			targetName: targetName ?? folder.title,
		},
		apiURL,
		dataSetId,
		keyValues: {
			objectEntryFolderId: folder.id,
		},
		onCreateSuccess: onClose,
		selectedData,
		type: 'CopyObjectBulkSelectionAction',
	});
}

function executeFolderAction(
	action: Action,
	folder: Folder,
	itemData: ItemData,
	loadData: () => {},
	replace = false
) {
	displayInfoToast(action, folder, itemData);

	let promise: Promise<RequestResult<unknown>>;

	if (action === 'copy') {
		promise = replace
			? FolderService.copyReplaceFolder(itemData.embedded.id, folder.id)
			: FolderService.copyFolder(itemData.embedded.id, folder.id);
	}
	else {
		promise = replace
			? FolderService.moveReplaceFolder(itemData.embedded.id, folder.id)
			: FolderService.moveFolder(itemData.embedded.id, folder.id);
	}

	promise.then((result: any) => {
		if (!result.error) {
			loadData();
		}

		displayToast(
			action,
			result.error,
			folder,
			itemData,
			SUCCESS_MESSAGES[action]
		);
	});
}

function executeAssetAction(
	action: Action,
	folder: Folder,
	itemData: ItemData,
	loadData: () => {},
	replace = false
) {
	displayInfoToast(action, folder, itemData);

	ApiHelper.post<any>(
		itemData.actions[`${action}${replace ? '-replace' : ''}`].href.replace(
			'{objectEntryFolderId}',
			String(folder.id)
		)
	).then((result: any) => {
		if (!result.error) {
			loadData();
		}

		displayToast(
			action,
			result.error,
			folder,
			itemData,
			SUCCESS_MESSAGES[action]
		);
	});
}

function openDuplicatedAssetFolderNamesModal(
	action: Action,
	itemData: ItemData,
	onContinueClick: (operation: Option) => void
) {
	openCMSModal({
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			DuplicatedAssetFolderNamesModalContent({
				action,
				closeModal,
				itemData,
				onContinueClick,
			}),
		size: 'md',
	});
}

function FolderItemSelectorModalContent({
	action,
	apiURL,
	assetLibraries,
	dataSetId,
	isBulk = false,
	itemData,
	loadData,
	objectEntryFolderExternalReferenceCode,
	rootObjectEntryFolderExternalReferenceCode,
	selectedData,
}: TFolderItemSelectorModalContent) {
	const [selectedItemType, setSelectedItemType] = useState<
		'folder' | 'space'
	>(objectEntryFolderExternalReferenceCode ? 'folder' : 'space');

	const objectFolderExternalReferenceCode =
		itemData.entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME
			? ''
			: itemData.embedded.systemProperties?.objectDefinitionBrief
					?.objectFolderExternalReferenceCode;

	const cmsSection =
		objectFolderExternalReferenceCode === 'L_CMS_CONTENT_STRUCTURES' ||
		rootObjectEntryFolderExternalReferenceCode === 'L_CONTENTS'
			? 'contents'
			: 'files';
	const [url, setURL] = useState<string>(
		objectEntryFolderExternalReferenceCode
			? getSpaceFoldersURL(cmsSection, itemData.embedded.scopeId)
			: SPACES_URL
	);
	const [schemaKey, setSchemaKey] = useState(0);
	const [currentSpace, setCurrentSpace] = useState<Space | undefined>();

	const {observer, onOpenChange, open} = useModal();

	function handleSpaceClick(space: Space) {
		setCurrentSpace(space);
		setSchemaKey((prev) => prev + 1);
		setSelectedItemType('folder');
		setURL(getSpaceFoldersURL(cmsSection, space.scopeId));
	}

	const setItemComponentProps = ({item, props}: {item: any; props: any}) => {
		if (item.type === 'Space') {
			const assetLibrary = assetLibraries.find(
				(assetLibrary) =>
					assetLibrary.externalReferenceCode ===
					item.externalReferenceCode
			);

			return {
				...props,
				onClick: () => {
					handleSpaceClick({
						name: assetLibrary!.name,
						scopeId: assetLibrary!.groupId,
					});
				},
				onSelectChange: null,
			};
		}

		return {
			...props,
			symbol: 'folder',
		};
	};

	const handleOnItemsChange = (folder: Folder, targetName?: string) => {
		if (isBulk) {
			executeBulkCopyAction({
				apiURL,
				dataSetId,
				folder,
				onClose: () => onOpenChange(false),
				selectedData,
				targetName,
			});
		}
		else if (itemData.entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME) {
			FolderService.searchFolder(
				itemData.embedded.scopeId,
				itemData.title,
				folder.id
			).then(({data, error}: any) => {
				if (error) {
					displayErrorToast(error);
				}
				else {
					if (data?.items.length > 0) {
						openDuplicatedAssetFolderNamesModal(
							action,
							itemData,
							(operation: Option) => {
								executeFolderAction(
									action,
									folder,
									itemData,
									loadData,
									operation === 'replace'
								);
							}
						);
					}
					else {
						executeFolderAction(action, folder, itemData, loadData);
					}
				}
			});
		}
		else {
			ApiHelper.get(
				`${itemData.actions['get-by-scope'].href}?filter=title eq '${itemData.title}' and folderId eq ${folder.id}`
			).then(({data, error}: any) => {
				if (error) {
					displayErrorToast(error);
				}
				else {
					if (data?.items.length > 0) {
						openDuplicatedAssetFolderNamesModal(
							action,
							itemData,
							(operation: Option) => {
								executeAssetAction(
									action,
									folder,
									itemData,
									loadData,
									operation === 'replace'
								);
							}
						);
					}
					else {
						executeAssetAction(action, folder, itemData, loadData);
					}
				}
			});
		}
	};

	useEffect(() => {
		onOpenChange(true);
	}, [onOpenChange]);

	return (
		<>
			{open && (
				<ItemSelectorModal<Folder>
					apiURL={url}
					breadcrumbs={
						objectEntryFolderExternalReferenceCode
							? undefined
							: [
									{
										label: Liferay.Language.get('spaces'),
										onClick: () => {
											setCurrentSpace(undefined);
											setSchemaKey((prev) => prev + 1);
											setSelectedItemType('space');
											setURL(SPACES_URL);
										},
									},
									...(currentSpace
										? [
												{
													label: currentSpace.name,
													onClick: () => {
														handleSpaceClick(
															currentSpace
														);
													},
												},
											]
										: []),
								]
					}
					breadcrumbsLabel={false}
					fdsProps={{
						...FDS_DEFAULT_PROPS,
						id: `itemSelectorModal-users-${selectedItemType === 'folder' ? itemData.embedded.id : itemData.id}`,
						views: [
							{
								contentRenderer: 'cards',
								label: Liferay.Language.get('cards'),
								name: 'cards',
								schema:
									selectedItemType === 'folder'
										? {
												description: 'description',
												title: 'title',
											}
										: {
												description: 'description',
												title: 'name',
											},
								setItemComponentProps,
								thumbnail: 'cards2',
							},
							{
								contentRenderer: 'table',
								label: Liferay.Language.get('table'),
								name: 'table',
								schema: {
									fields: [
										selectedItemType === 'folder'
											? {
													fieldName: 'title',
													label: Liferay.Language.get(
														'title'
													),
													sortable: false,
												}
											: {
													fieldName: 'name',
													label: Liferay.Language.get(
														'title'
													),
													sortable: false,
												},
										{
											fieldName: 'description',
											label: Liferay.Language.get(
												'description'
											),
											sortable: false,
										},
									],
								},
								setItemComponentProps,
								thumbnail: 'table',
							},
						] as IView[],
					}}
					items={[]}
					key={schemaKey}
					locator={
						selectedItemType === 'folder'
							? {
									id: 'embedded.id',
									label: 'title',
									value: 'embedded.id',
								}
							: {
									id: 'id',
									label: 'name',
									value: 'id',
								}
					}
					message={
						<Alert
							className="alert-dismissible alert-fluid p-3"
							displayType="warning"
							title="Warning"
						>
							{action === 'copy'
								? Liferay.Language.get(
										'only-categories-and-tags-also-available-in-the-destination-will-be-copied'
									)
								: Liferay.Language.get(
										'only-categories-and-tags-also-available-in-the-destination-will-be-retained'
									)}
						</Alert>
					}
					observer={observer}
					onItemsChange={(items: any[]) => {
						if (items.length) {
							const item = items[0];
							const isFolder = selectedItemType === 'folder';

							let name = item.title;

							if (isFolder) {
								const isRootFolder =
									!item.embedded.parentObjectEntryFolderId ||
									item.embedded.parentObjectEntryFolderId ===
										null;

								if (isRootFolder) {
									name = currentSpace?.name || item.title;
								}
							}

							handleOnItemsChange(
								{
									id: isFolder ? item.embedded.id : item.id,
									title: name,
								},
								name
							);
						}
					}}
					onOpenChange={onOpenChange}
					open={open}
					title={
						action === 'copy'
							? sub(
									Liferay.Language.get('copy-x-to'),
									itemData.title
								)
							: sub(
									Liferay.Language.get('move-x-to'),
									itemData.title
								)
					}
				/>
			)}
		</>
	);
}

export default FolderItemSelectorModalContent;
