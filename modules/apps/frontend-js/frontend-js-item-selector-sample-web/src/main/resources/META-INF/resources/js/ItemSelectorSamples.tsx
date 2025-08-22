/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayList from '@clayui/list';
import {useModal} from '@clayui/modal';
import ClaySticker from '@clayui/sticker';
import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';
import {ItemSelector, ItemSelectorModal} from 'frontend-js-item-selector-web';
import React, {useState} from 'react';

import {
	assetLibraryViews,
	documentViews,
	userViews,
} from './utils/defaultViews';
import {
	EItemSelectorModalViewsConfig,
	getDefaultItemSelectorModalViews,
} from './utils/getDefaultItemSelectorModalViews';

import type {DisplayType} from '@clayui/sticker';

function SampleContainer({
	children,
	label,
}: {
	children: React.ReactNode;
	label: string;
}) {
	return (
		<div className="mt-4">
			<h2>{label}</h2>

			{children}
		</div>
	);
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

type Space = {
	id: number;
	name: string;
};

type User = {
	givenName: string;
	id: number;
	name: string;
	roleBriefs?: {
		name: string;
	}[];
};

const FDS_DEFAULT_PROPS: Partial<IFrontendDataSetProps> = {
	pagination: {
		deltas: [{label: 20}, {label: 40}, {label: 60}],
		initialDelta: 20,
	},
	selectionType: 'single',
};

const assetLibrariesItemSelectorConfig = {
	apiURL: `${location.origin}/o/headless-asset-library/v1.0/asset-libraries`,
	itemNameLocator: 'name',
	itemValueLocator: 'id',
	type: Liferay.Language.get('space'),
	views: assetLibraryViews,
};

const documentsItemSelectorConfig = {
	apiURL: `${location.origin}/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/documents`,
	itemNameLocator: 'fileName',
	itemValueLocator: 'id',
	type: Liferay.Language.get('document'),
	views: documentViews,
};

const userAccountsItemSelectorConfig = {
	apiURL: `${location.origin}/o/headless-admin-user/v1.0/user-accounts`,
	itemNameLocator: (item: User) =>
		item.givenName +
		' (' +
		item.roleBriefs?.map((role) => role.name).join(', ') +
		')',
	itemValueLocator: 'id',
	type: Liferay.Language.get('user'),
	views: userViews,
};

function getRandomId(): string {
	return Math.random().toString(36).substring(2, 9);
}

export default function ItemSelectorSamples() {
	const [documents, setDocuments] = useState<Document[]>([]);
	const [space, setSpace] = useState<Space>();

	const [space2, setSpace2] = useState<Space | null>();
	const [document, setDocument] = useState<Document | null>();
	const [user, setUser] = useState<User | null>();

	const {
		observer: fileItemSelectorObserver,
		onOpenChange: fileItemSelectorOpenChange,
		open: fileItemSelectorOpen,
	} = useModal();
	const {
		observer: spaceItemSelectorObserver,
		onOpenChange: spaceItemSelectorOpenChange,
		open: spaceItemSelectorOpen,
	} = useModal();
	const {
		observer: userItemSelectorObserver,
		onOpenChange: userItemSelectorOpenChange,
		open: userItemSelectorOpen,
	} = useModal();

	return (
		<>
			<SampleContainer label="Single Select (Documents) - Paginated Items">
				<ItemSelector<Document>
					apiURL={`${location.origin}/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/documents`}
				>
					{(item) => (
						<ItemSelector.Item key={item.id} textValue={item.title}>
							{item.title}
						</ItemSelector.Item>
					)}
				</ItemSelector>
			</SampleContainer>

			<SampleContainer label="Single Select (Spaces) - Controlled Component">
				<ClayInput.Group>
					<ClayInput.GroupItem prepend shrink>
						<ClayInput.GroupText>
							{space && (
								<ClaySticker
									displayType={
										`outline-${space.id % 10}` as DisplayType
									}
									size="sm"
								>
									{space.name.slice(0, 1)}
								</ClaySticker>
							)}
						</ClayInput.GroupText>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append>
						<ItemSelector<Space>
							apiURL={`${location.origin}/o/headless-asset-library/v1.0/asset-libraries`}
							as={ClayInput}
							items={space ? [space] : []}
							onItemsChange={(items: Array<Space>) => {
								if (items.length) {
									setSpace(items[0]);
								}
								else {
									setSpace(undefined);
								}
							}}
						>
							{(item: Space) => (
								<ItemSelector.Item
									key={item.id}
									textValue={item.name}
								>
									<span className="inline-item inline-item-before">
										<ClaySticker
											displayType={
												`outline-${item.id % 10}` as DisplayType
											}
											size="sm"
										>
											{item.name.slice(0, 1)}
										</ClaySticker>
									</span>

									<span className="inline-item inline-item-after">
										{item.name}
									</span>
								</ItemSelector.Item>
							)}
						</ItemSelector>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</SampleContainer>

			<SampleContainer label="Single Select (Users)">
				<ItemSelector
					apiURL={`${location.origin}/o/headless-admin-user/v1.0/user-accounts`}
					locator={{
						id: 'id',
						label: 'name',
						value: 'id',
					}}
				>
					{(item) => (
						<ItemSelector.Item key={item.id} textValue={item.name}>
							{item.name}
						</ItemSelector.Item>
					)}
				</ItemSelector>
			</SampleContainer>

			<SampleContainer label="Multiple Select (Documents) - Paginated Items">
				<ItemSelector<Document>
					apiURL={`${location.origin}/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/documents`}
					multiSelect
				>
					{(item) => (
						<ItemSelector.Item key={item.id} textValue={item.title}>
							{item.title}
						</ItemSelector.Item>
					)}
				</ItemSelector>
			</SampleContainer>

			<SampleContainer label="Multiple Select (Documents) - Custom Selected Items List">
				<ItemSelector<Document>
					apiURL={`${location.origin}/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/documents`}
					displaySelectedItems={false}
					items={documents}
					multiSelect
					onItemsChange={(items: Array<Document>) => {
						setDocuments(items);
					}}
					placeholder="Select a Document"
				>
					{(item) => (
						<ItemSelector.Item key={item.id} textValue={item.title}>
							{item.title}
						</ItemSelector.Item>
					)}
				</ItemSelector>

				{!!documents.length && (
					<ClayList className="mt-3">
						{documents.map((document) => (
							<ClayList.Item flex key={document.id}>
								{document.encodingFormat.includes('image') && (
									<ClayList.ItemField>
										<ClaySticker className="mr-1" size="xl">
											<ClaySticker.Image
												alt={document.title}
												src={document.contentUrl}
											/>
										</ClaySticker>
									</ClayList.ItemField>
								)}

								<ClayList.ItemField expand>
									<ClayList.ItemTitle>
										{document.title}
									</ClayList.ItemTitle>

									<ClayList.ItemText>
										Creator: {document.creator.name}
									</ClayList.ItemText>
								</ClayList.ItemField>

								<ClayList.ItemField>
									<ClayList.QuickActionMenu>
										<ClayList.QuickActionMenu.Item
											aria-label="Delete"
											onClick={() =>
												setDocuments((documents) =>
													documents.filter(
														(item) =>
															item.id !==
															document.id
													)
												)
											}
											symbol="trash"
											title="Delete"
										/>
									</ClayList.QuickActionMenu>
								</ClayList.ItemField>
							</ClayList.Item>
						))}
					</ClayList>
				)}
			</SampleContainer>

			<SampleContainer label="Item Selector Modal">
				<ItemSelectorModal<Document>
					{...{
						fdsProps: {
							...FDS_DEFAULT_PROPS,
							apiURL: documentsItemSelectorConfig.apiURL,
							id: `itemSelectorModal-documents-${getRandomId()}`,
							selectedItemsKey: 'id',
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.DOCUMENTS,
							}),
						},
						itemNameLocator:
							documentsItemSelectorConfig.itemNameLocator,
						itemValueLocator:
							documentsItemSelectorConfig.itemValueLocator,
						items: document ? [document] : [],
						observer: fileItemSelectorObserver,
						onOpenChange: fileItemSelectorOpenChange,
						onSelectedItemsChange: (items: Document[]) => {
							setDocument(items[0]);
						},
						open: fileItemSelectorOpen,
						type: documentsItemSelectorConfig.type,
					}}
				/>

				<ItemSelectorModal<Space>
					{...{
						fdsProps: {
							...FDS_DEFAULT_PROPS,
							apiURL: assetLibrariesItemSelectorConfig.apiURL,
							id: `itemSelectorModal-assets-${getRandomId()}`,
							selectedItemsKey: 'id',
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.ASSET_LIBRARIES,
							}),
						},
						itemNameLocator:
							assetLibrariesItemSelectorConfig.itemNameLocator,
						itemValueLocator:
							assetLibrariesItemSelectorConfig.itemValueLocator,
						items: space2 ? [space2] : [],
						observer: spaceItemSelectorObserver,
						onOpenChange: spaceItemSelectorOpenChange,
						onSelectedItemsChange: (items: Space[]) => {
							setSpace2(items[0]);
						},
						open: spaceItemSelectorOpen,
						type: assetLibrariesItemSelectorConfig.type,
					}}
				/>

				<ItemSelectorModal<User>
					{...{
						fdsProps: {
							...FDS_DEFAULT_PROPS,
							apiURL: userAccountsItemSelectorConfig.apiURL,
							id: `itemSelectorModal-users-${getRandomId()}`,
							selectedItemsKey: 'id',
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.USER_ACCOUNTS,
							}),
						},
						itemNameLocator:
							userAccountsItemSelectorConfig.itemNameLocator,
						itemValueLocator:
							userAccountsItemSelectorConfig.itemValueLocator,
						items: user ? [user] : [],
						observer: userItemSelectorObserver,
						onOpenChange: userItemSelectorOpenChange,
						onSelectedItemsChange: (items: User[]) => {
							setUser(items[0]);
						},
						open: userItemSelectorOpen,
						type: userAccountsItemSelectorConfig.type,
					}}
				/>

				<ClayButton.Group className="mb-3" spaced>
					<ClayButton
						displayType="primary"
						onClick={() => {
							fileItemSelectorOpenChange(true);
						}}
					>
						Select Document
					</ClayButton>

					<ClayButton
						displayType="primary"
						onClick={() => {
							spaceItemSelectorOpenChange(true);
						}}
					>
						Select Space
					</ClayButton>

					<ClayButton
						displayType="primary"
						onClick={() => {
							userItemSelectorOpenChange(true);
						}}
					>
						Select User
					</ClayButton>
				</ClayButton.Group>

				{space2 && (
					<ClayAlert
						displayType="info"
						symbol="nodes"
						title={space2.name}
					/>
				)}

				{document && (
					<ClayAlert
						displayType="info"
						symbol="document"
						title={document.fileName}
					/>
				)}

				{user && (
					<ClayAlert
						displayType="info"
						symbol="user"
						title={user.name}
					/>
				)}
			</SampleContainer>
		</>
	);
}
