/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import {IView} from '@liferay/frontend-data-set-web';
import React, {useState} from 'react';
import {v4 as uuidv4} from 'uuid';

import ItemSelectorModal, {IItemSelectorModalProps} from './itemSelectorModal';

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
	filter: "cmsRoot eq true and cmsSection eq 'files' and status in (0, 2, 3)",
}).toString()}`;

function getCMSChildFolderURL(folderId: string) {
	return `${ROOT_URL}?${new URLSearchParams({
		...BASE_SEARCH_PARAMS,
		filter: `folderId eq ${folderId}`,
	}).toString()}`;
}

type Document = {
	contentUrl: string;
	creator: {
		name: string;
	};
	encodingFormat: string;
	fileName: string;
	id: string;
	title: string;
};

function CMSFilesItemSelectorModal({
	fdsProps,
	...otherProps
}: Omit<IItemSelectorModalProps<Document>, 'fdsProps' | 'type'> & {
	fdsProps?: IItemSelectorModalProps<Document>['fdsProps'];
}) {
	const [folderStructure, setFolderStructure] = useState<
		{folderId: string; folderName: string}[]
	>([]);
	const [url, setURL] = useState(CMS_ROOT_FILES_URL);

	return (
		<ItemSelectorModal
			{...otherProps}
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
								({folderId, folderName}) => ({
									href: '#',
									label: folderName,
									onClick: () => {
										setFolderStructure(
											(prevFolderStructure) =>
												prevFolderStructure.slice(
													0,
													prevFolderStructure.findIndex(
														({folderId: id}) =>
															folderId === id
													) + 1
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
				...fdsProps,
				apiURL: url,
				customRenderers: {
					tableCell: [
						{
							component: ({itemData, value}) => {
								const {embedded} = itemData;

								return (
									<div className="align-items-center d-flex table-list-title">
										<ClayLink
											href="#"
											onClick={() => {
												const folderId = embedded.id;
												const folderName =
													embedded.title;

												setFolderStructure(
													(prevStructure) => [
														...prevStructure,
														{folderId, folderName},
													]
												);

												setURL(
													getCMSChildFolderURL(
														folderId
													)
												);
											}}
										>
											{value}
										</ClayLink>
									</div>
								);
							},
							name: 'cmsFilesTitleCellRenderer',
							type: 'internal',
						},
					],
				},
				id: `itemSelectorModal-documents-${uuidv4()}`,
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
									interactive: true,
									onClick: () => {
										const folderId = item.embedded.id;
										const folderName = item.embedded.title;

										setFolderStructure((prevStructure) => [
											...prevStructure,
											{folderId, folderName},
										]);

										setURL(getCMSChildFolderURL(folderId));
									},
									onSelectChange: null,
									symbol: 'folder',
								};
							}

							const stickerProps = {
								stickerProps: {
									className: 'file-icon-color-5',
									displayType: 'unstyled',
								},
							};

							if (
								!item.embedded.file.mimeType.startsWith('image')
							) {
								return {
									...props,
									imgProps: null,
									...stickerProps,
								};
							}

							return {
								...props,
								...stickerProps,
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
			locator={{
				id: 'embedded.id',
				label: 'embedded.title',
				value: 'embedded.id',
			}}
			type={Liferay.Language.get('document')}
		/>
	);
}

export default CMSFilesItemSelectorModal;
