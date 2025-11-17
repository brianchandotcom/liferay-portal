/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Alert from '@clayui/alert';
import {useModal} from '@clayui/modal';
import {IFrontendDataSetProps, IView} from '@liferay/frontend-data-set-web';
import {ItemSelectorModal} from '@liferay/frontend-js-item-selector-web';
import {openModal, openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import ApiHelper, {RequestResult} from '../../common/services/ApiHelper';
import FolderService from '../../common/services/FolderService';
import {AssetLibrary} from '../../common/types/AssetLibrary';
import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../../common/utils/constants';
import {displayErrorToast} from '../../common/utils/toastUtil';
import DuplicatedAssetFolderNamesModalContent, {
	Option,
} from './DuplicatedAssetFolderNamesModalContent';

export type TFolderItemSelectorModalContent = {
	action: Action;
	assetLibraries: AssetLibrary[];
	itemData: ItemData;
	loadData: () => {};
	objectEntryFolderExternalReferenceCode: string | undefined;
};

export type Action = 'copy' | 'move';

type Folder = {
	id: number;
	title: string;
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

const getSpaceFoldersURL = (scopeId: number) => {
	return `${window.location.origin}/o/headless-object/v1.0/scopes/${scopeId}/object-entry-folders`;
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
	error: any,
	folder: Folder,
	itemData: ItemData,
	message: string
) => {
	if (error) {
		displayErrorToast(error);
	}
	else {
		displaySuccessToast(
			message,
			`${Liferay.Util.escapeHTML(itemData.embedded.title)}`,
			`<strong>${Liferay.Util.escapeHTML(folder.title)}</strong>`
		);
	}
};

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

	promise.then(({error}: {error: any}) => {
		if (!error) {
			loadData();
		}

		displayToast(error, folder, itemData, SUCCESS_MESSAGES[action]);
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
	).then(({error}: {error: any}) => {
		if (!error) {
			loadData();
		}

		displayToast(error, folder, itemData, SUCCESS_MESSAGES[action]);
	});
}

function openDuplicatedAssetFolderNamesModal(
	action: Action,
	itemData: ItemData,
	onContinueClick: (operation: Option) => void
) {
	openModal({
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
	assetLibraries,
	itemData,
	loadData,
	objectEntryFolderExternalReferenceCode,
}: TFolderItemSelectorModalContent) {
	const [selectedItemType, setSelectedItemType] = useState<
		'folder' | 'space'
	>(objectEntryFolderExternalReferenceCode ? 'folder' : 'space');
	const [url, setURL] = useState<string>(
		objectEntryFolderExternalReferenceCode
			? getSpaceFoldersURL(itemData.embedded.scopeId)
			: SPACES_URL
	);
	const [schemaKey, setSchemaKey] = useState(0);

	const {observer, onOpenChange, open} = useModal();

	function onSpaceClick({scopeId}: {scopeId: number}) {
		setSchemaKey((prev) => prev + 1);
		setSelectedItemType('folder');
		setURL(getSpaceFoldersURL(scopeId));
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
					onSpaceClick({
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

	const handleOnItemsChange = (folder: Folder) => {
		if (itemData.entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME) {
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
											setSchemaKey((prev) => prev + 1);
											setSelectedItemType('space');
											setURL(SPACES_URL);
										},
									},
								]
					}
					fdsProps={{
						...FDS_DEFAULT_PROPS,
						id: `itemSelectorModal-users-${itemData.id}`,
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
									id: 'id',
									label: 'title',
									value: 'id',
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
					onItemsChange={(items: Folder[]) => {
						if (items.length) {
							handleOnItemsChange(items[0]);
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
