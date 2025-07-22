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
import {
	EItemSelectorModalViewsConfig,
	ItemSelector,
	ItemSelectorModal,
	assetLibraryViews,
	documentViews,
	getDefaultItemSelectorModalViews,
	userViews,
} from 'frontend-js-item-selector-web';
import React, {useState} from 'react';

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
	id: string;
	title: string;
};

type Space = {
	id: number;
	name: string;
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
	type: Liferay.Language.get('space'),
	views: assetLibraryViews,
};

const documentsItemSelectorConfig = {
	apiURL: `${location.origin}/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/documents`,
	itemNameLocator: 'fileName',
	type: Liferay.Language.get('file'),
	views: documentViews,
};

const userAccountsItemSelectorConfig = {
	apiURL: `${location.origin}/o/headless-admin-user/v1.0/user-accounts`,
	itemNameLocator: (item: any) => {
		return (
			item.givenName +
			' (' +
			item.roleBriefs?.map((role: any) => role.name).join(',') +
			')'
		);
	},
	type: Liferay.Language.get('user'),
	views: userViews,
};

function getRandomId(): string {
	return Math.random().toString(36).substring(2, 9);
}

export default function ItemSelectorSamples() {
	const [documents, setDocuments] = useState<Document[]>([]);
	const [space, setSpace] = useState<Space>();

	const [space2, setSpace2] = useState(null);
	const [document, setDocument] = useState(null);
	const [user, setUser] = useState(null);

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

	function onAssetSelection(asset: any) {
		setAsset(asset);
	}

	function onFileSelection(file: any) {
		setFile(file);
	}

	function onUserSelection(user: any) {
		setUser(user);
	}

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
				<ItemSelectorModal
					{...{
						fdsProps: {
							...FDS_DEFAULT_PROPS,
							apiURL: documentsItemSelectorConfig.apiURL,
							id: `itemSelectorModal-documents-${getRandomId()}`,
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.DOCUMENTS_AND_MEDIA,
							}),
						},
						itemNameLocator:
							documentsItemSelectorConfig.itemNameLocator,
						observer: fileItemSelectorObserver,
						onOpenChange: fileItemSelectorOpenChange,
						onSelection: onFileSelection,
						open: fileItemSelectorOpen,
						type: documentsItemSelectorConfig.type,
					}}
				/>

				<ItemSelectorModal
					{...{
						fdsProps: {
							...FDS_DEFAULT_PROPS,
							apiURL: assetLibrariesItemSelectorConfig.apiURL,
							id: `itemSelectorModal-assets-${getRandomId()}`,
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.ASSET_LIBRARIES,
							}),
						},
						itemNameLocator:
							assetLibrariesItemSelectorConfig.itemNameLocator,
						observer: spaceItemSelectorObserver,
						onOpenChange: spaceItemSelectorOpenChange,
						onSelection: onAssetSelection,
						open: spaceItemSelectorOpen,
						type: assetLibrariesItemSelectorConfig.type,
					}}
				/>

				<ItemSelectorModal
					{...{
						fdsProps: {
							...FDS_DEFAULT_PROPS,
							apiURL: userAccountsItemSelectorConfig.apiURL,
							id: `itemSelectorModal-users-${getRandomId()}`,
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.USER_ACCOUNTS,
							}),
						},
						itemNameLocator:
							userAccountsItemSelectorConfig.itemNameLocator,
						observer: userItemSelectorObserver,
						onOpenChange: userItemSelectorOpenChange,
						onSelection: onUserSelection,
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
