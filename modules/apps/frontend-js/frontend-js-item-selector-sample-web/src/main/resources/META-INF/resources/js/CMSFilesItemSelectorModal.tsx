/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {
	EConfigInURLBehavior,
	IInlineNotificationComponent,
	IView,
	TSort,
} from '@liferay/frontend-data-set-web';
import {
	CMSFileUploaderComponent,
	IItemSelectorModalProps,
	ItemSelectorModal,
	getCMSItemSelectorFilters,
	getCMSItemSelectorGroupedFilters,
} from '@liferay/frontend-js-item-selector-web';
import {useBrowserTabVisibility} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';
import {v4 as uuidv4} from 'uuid';

const OBJECT_ENTRY_FOLDER_CLASS_NAME =
	'com.liferay.object.model.ObjectEntryFolder';

const ROOT_URL = `${window.location.origin}${Liferay.ThemeDisplay.getPathContext()}/o/search/v1.0/search`;

const BASE_SEARCH_PARAMS = {
	currentURL: '/web/cms/files',
	emptySearch: 'true',
	nestedFields: 'description,embedded,file.thumbnailURL',
};

const CMS_ROOT_FILES_URL = `${ROOT_URL}?${new URLSearchParams({
	...BASE_SEARCH_PARAMS,
	filter: "cmsRoot eq true and cmsSection eq 'files' and rootDescendantNode eq false and status in (0, 2, 3)",
}).toString()}`;

function getCMSChildFolderURL(folderId: string) {
	return `${ROOT_URL}?${new URLSearchParams({
		...BASE_SEARCH_PARAMS,
		filter: `folderId eq ${folderId}`,
	}).toString()}`;
}

async function checkNewCMSFiles(lastRequestTime: string) {
	const response = await fetch(
		`${CMS_ROOT_FILES_URL} and dateCreated gt ${lastRequestTime}`
	);

	if (!response.ok) {
		return {totalCount: 0};
	}

	return (await response.json()) as {totalCount: number};
}

type CMSFile = {
	id: number;
	title: string;
};

function CMSFilesItemSelectorModal({
	fdsProps,
	open,
	...otherProps
}: Omit<
	IItemSelectorModalProps<CMSFile>,
	'itemTypeLabel' | 'fdsProps' | 'apiURL'
> & {
	fdsProps?: IItemSelectorModalProps<CMSFile>['fdsProps'];
}) {
	const [folderStructure, setFolderStructure] = useState<
		{folderId: string; folderName: string}[]
	>([]);
	const [newItemsCount, setNewItemsCount] = useState(0);
	const [showInlineNotification, setShowInlineNotification] = useState(false);
	const [url, setURL] = useState(CMS_ROOT_FILES_URL);

	const isBrowserTabVisible = useBrowserTabVisibility();
	const lastRequestTimeRef = React.useRef(new Date().toISOString());

	useEffect(() => {
		if (isBrowserTabVisible && open) {
			checkNewCMSFiles(lastRequestTimeRef.current).then((response) => {
				if (response.totalCount > 0) {
					setNewItemsCount(response.totalCount);
					setShowInlineNotification(true);

					lastRequestTimeRef.current = new Date().toISOString();
				}
			});
		}
	}, [isBrowserTabVisible, open]);

	function onChildFolderClick({
		folderId,
		folderName,
	}: {
		folderId: string;
		folderName: string;
	}) {
		setFolderStructure((prevStructure) => [
			...prevStructure,
			{folderId, folderName},
		]);

		setURL(getCMSChildFolderURL(folderId));
	}

	const NewItemsNotificationComponent = ({
		context,
	}: {
		context: IInlineNotificationComponent['context'];
	}) => {
		if (!showInlineNotification) {
			return null;
		}

		return (
			<ClayAlert
				displayType="info"
				onClose={() => setShowInlineNotification(false)}
				title={Liferay.Language.get('info')}
				variant="stripe"
			>
				{Liferay.Util.sub(
					Liferay.Language.get(
						'x-new-items-are-not-visible-in-this-view'
					),
					[newItemsCount]
				)}

				<ClayButton.Group className="pl-3" spaced>
					<ClayButton
						displayType="info"
						onClick={() => {
							const updatedSorts: TSort[] = (context?.sorts || [])
								.filter((sort) => sort.key !== 'dateCreated')
								.map((sort) => {
									sort.active = false;

									return sort;
								});

							updatedSorts.push({
								active: true,
								direction: 'desc',
								key: 'dateCreated',
								label: Liferay.Language.get('by-creation-date'),
							});

							context && context.onClearResultsBar();
							context && context.forceSortsUpdate(updatedSorts);

							setShowInlineNotification(false);
						}}
						size="sm"
					>
						{Liferay.Language.get('reload')}
					</ClayButton>

					<ClayButton
						alert
						onClick={() => setShowInlineNotification(false)}
						size="sm"
					>
						{Liferay.Language.get('dismiss')}
					</ClayButton>
				</ClayButton.Group>
			</ClayAlert>
		);
	};

	return (
		<ItemSelectorModal
			{...otherProps}
			apiURL={url}
			breadcrumbs={
				folderStructure.length
					? [
							{
								label: Liferay.Language.get('default'),
								onClick: () => {
									setURL(CMS_ROOT_FILES_URL);
									setFolderStructure([]);
								},
							},
							...folderStructure.map(
								({folderId, folderName}, index) => ({
									label: folderName,
									onClick: () => {
										setFolderStructure(
											(prevFolderStructure) =>
												prevFolderStructure.slice(
													0,
													index + 1
												)
										);

										setURL(getCMSChildFolderURL(folderId));
									},
								})
							),
						]
					: undefined
			}
			fdsProps={{
				configInURLBehavior: EConfigInURLBehavior.OFF,
				...fdsProps,
				customRenderers: {
					tableCell: [
						{
							component: ({itemData, value}) => {
								const {embedded, entryClassName} = itemData;

								return entryClassName ===
									OBJECT_ENTRY_FOLDER_CLASS_NAME ? (
									<ClayButton
										className="c-p-0"
										displayType="link"
										onClick={() => {
											onChildFolderClick({
												folderId: embedded.id,
												folderName: embedded.title,
											});
										}}
									>
										{value}
									</ClayButton>
								) : (
									value
								);
							},
							name: 'cmsFilesTitleCellRenderer',
							type: 'internal',
						},
					],
				},
				filters: getCMSItemSelectorFilters(
					Liferay.ThemeDisplay.getSiteGroupId()
				),
				groupedFilters: getCMSItemSelectorGroupedFilters(),
				id: `itemSelectorModal-cms-${uuidv4()}`,
				inlineNotificationComponent: NewItemsNotificationComponent,
				pagination: {
					deltas: [{label: 20}, {label: 40}, {label: 60}],
					initialDelta: 20,
				},
				views: [
					{
						contentRenderer: 'cards',
						label: Liferay.Language.get('cards'),
						name: 'cards',
						schema: {
							description: 'embedded.description',
							image: 'embedded.file.thumbnailURL',
							title: 'embedded.title',
						},
						setItemComponentProps: ({
							item,
							props,
						}: {
							item: any;
							props: any;
						}) => {
							if (
								item.entryClassName ===
								OBJECT_ENTRY_FOLDER_CLASS_NAME
							) {
								return {
									...props,
									onClick: () => {
										onChildFolderClick({
											folderId: item.embedded.id,
											folderName: item.embedded.title,
										});
									},
									onSelectChange: null,
									symbol: 'folder',
								};
							}

							const stickerProps = {
								className: 'file-icon-color-5',
								displayType: 'unstyled',
							};

							if (
								item.embedded.file &&
								!item.embedded.file.mimeType.startsWith('image')
							) {
								return {
									...props,
									imgProps: null,
									stickerProps,
								};
							}

							return {
								...props,
								stickerProps,
							};
						},
						thumbnail: 'cards2',
					},
					{
						contentRenderer: 'table',
						label: Liferay.Language.get('table'),
						name: 'table',
						schema: {
							fields: [
								{
									contentRenderer:
										'cmsFilesTitleCellRenderer',
									fieldName: 'embedded.title',
									label: Liferay.Language.get('title'),
									sortable: false,
								},
								{
									fieldName: 'embedded.description',
									label: Liferay.Language.get('description'),
									sortable: false,
								},
								{
									fieldName: 'embedded.file.name',
									label: Liferay.Language.get('file-name'),
									sortable: false,
								},
								{
									fieldName: 'embedded.file.mimeType',
									label: Liferay.Language.get('type'),
									sortable: false,
								},
							],
						},
						thumbnail: 'table',
					},
				] as IView[],
			}}
			filesUploaderComponent={CMSFileUploaderComponent}
			itemTypeLabel={Liferay.Language.get('files')}
			locator={{
				id: 'embedded.id',
				label: 'embedded.title',
				value: 'embedded.id',
			}}
			open={open}
		/>
	);
}

export default CMSFilesItemSelectorModal;
