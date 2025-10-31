/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import {IFrontendDataSetProps, IView} from '@liferay/frontend-data-set-web';
import {ItemSelectorModal} from '@liferay/frontend-js-item-selector-web';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import ApiHelper, {RequestResult} from '../../common/services/ApiHelper';
import FolderService from '../../common/services/FolderService';
import {AssetLibrary} from '../../common/types/AssetLibrary';
import {displayErrorToast} from '../../common/utils/toastUtil';

export type TFolderItemSelectorModalContent = {
	action: Action;
	assetLibraries: AssetLibrary[];
	itemData: ItemData;
	objectEntryFolderExternalReferenceCode: string | undefined;
};

type Action = 'copy' | 'move';

type Folder = {
	id: number;
	title: string;
};

const SPACES_URL = `${window.location.origin}/o/headless-asset-library/v1.0/asset-libraries?filter=type eq 'Space'`;

const SUCCESS_MESSAGE_KEYS = {
	copy: 'x-was-successfully-copied-to-x',
	move: 'x-was-successfully-moved-to-x',
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
			Liferay.Language.get(
				action === 'copy' ? 'copying-x-to-x' : 'moving-x-to-x'
			),
			`${Liferay.Util.escapeHTML(itemData.embedded.title)}`,
			`<strong>${Liferay.Util.escapeHTML(folder.title)}</strong>`
		),
		type: 'info',
	});
};

const displaySuccessToast = (messageKey: string, ...args: string[]) => {
	openToast({
		message: sub(Liferay.Language.get(messageKey), args),
		type: 'success',
	});
};

const displayToast = (
	error: any,
	folder: Folder,
	itemData: ItemData,
	messageKey: string
) => {
	if (error) {
		displayErrorToast(error);
	}
	else {
		displaySuccessToast(
			messageKey,
			`${Liferay.Util.escapeHTML(itemData.embedded.title)}`,
			`<strong>${Liferay.Util.escapeHTML(folder.title)}</strong>`
		);
	}
};

function FolderItemSelectorModalContent({
	action,
	assetLibraries,
	itemData,
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

	const {observer, onOpenChange, open} = useModal();

	function onSpaceClick({scopeId}: {scopeId: number}) {
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
		displayInfoToast(action, folder, itemData);

		if (
			itemData.entryClassName ===
			'com.liferay.object.model.ObjectEntryFolder'
		) {
			let promise: Promise<RequestResult<unknown>>;

			if (action === 'copy') {
				promise = FolderService.copyFolder(
					itemData.embedded.id,
					folder.id
				);
			}
			else {
				promise = FolderService.moveFolder(
					itemData.embedded.id,
					folder.id
				);
			}

			promise.then(({error}: {error: any}) => {
				displayToast(
					error,
					folder,
					itemData,
					SUCCESS_MESSAGE_KEYS[action]
				);
			});
		}
		else {
			ApiHelper.post<any>(
				itemData.actions[action].href.replace(
					'{objectEntryFolderId}',
					String(folder.id)
				)
			).then(({error}: {error: any}) => {
				displayToast(
					error,
					folder,
					itemData,
					SUCCESS_MESSAGE_KEYS[action]
				);
			});
		}
	};

	useEffect(() => {
		onOpenChange(true);
	}, [onOpenChange]);

	return (
		<>
			{open && (
				<ItemSelectorModal
					apiURL={url}
					breadcrumbs={
						objectEntryFolderExternalReferenceCode
							? undefined
							: [
									{
										label: Liferay.Language.get('spaces'),
										onClick: () => {
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
					itemTypeLabel={Liferay.Language.get('folders')}
					items={[]}
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
					observer={observer}
					onItemsChange={(items: Folder[]) => {
						if (items.length) {
							handleOnItemsChange(items[0]);
						}
					}}
					onOpenChange={onOpenChange}
					open={open}
				/>
			)}
		</>
	);
}

export default FolderItemSelectorModalContent;
