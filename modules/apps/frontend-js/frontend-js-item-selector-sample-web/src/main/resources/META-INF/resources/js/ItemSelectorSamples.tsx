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
	cmsFileViews,
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

type CMSFile = {
	id: number;
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
	locator: {
		id: 'id',
		label: 'name',
		value: 'id',
	},
	type: Liferay.Language.get('space'),
	views: assetLibraryViews,
};

const documentsItemSelectorConfig = {
	apiURL: `${location.origin}/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/documents`,
	locator: {
		id: 'id',
		label: 'fileName',
		value: 'id',
	},
	type: Liferay.Language.get('documents'),
	views: documentViews,
};

const userAccountsItemSelectorConfig = {
	apiURL: `${location.origin}/o/headless-admin-user/v1.0/user-accounts`,
	locator: {
		id: 'id',
		label: 'givenName',
		value: 'id',
	},
	type: Liferay.Language.get('user'),
	views: userViews,
};

const cmsFileItemSelectorConfig = {
	apiURL: `${location.origin}/o/search/v1.0/search?${[
		'emptySearch=true',
		'nestedFields=embedded,file.thumbnailURL',
		"filter=(cmsKind eq 'object') and (cmsSection eq 'files') and (status in (0, 2, 3))",
	].join('&')}`,
	locator: {
		id: 'embedded.id',
		label: 'embedded.title',
		value: 'embedded.id',
	},
	type: Liferay.Language.get('file'),
	views: cmsFileViews,
};

function getRandomId(): string {
	return Math.random().toString(36).substring(2, 9);
}

export default function ItemSelectorSamples() {
	const [documents, setDocuments] = useState<Document[]>([]);
	const [space, setSpace] = useState<Space>();

	const [documentsItemSelectorModal, setDocumentsItemSelectorModal] =
		useState<Document[]>([]);
	const [spacesItemSelectorModal, setSpacesItemSelectorModal] = useState<
		Space[]
	>([]);
	const [usersItemSelectorModal, setUsersItemSelectorModal] = useState<
		User[]
	>([]);
	const [cmsFile, setCMSFile] = useState<CMSFile | null>(null);
	const [usersMultiselect, setUsersMultiselect] = useState<
		User[]
	>([]);
	const [user2, setUser2] = useState<User | null>();
	const [space3, setSpace3] = useState<Space | null>();
	const [spacesMultiSelect, setSpacesMultiSelect] = useState<Space[]>([]);
	const [usersMultiSelect, setUsersMultiSelect] = useState<User[]>([]);

	const {
		observer: documentItemSelectorObserver,
		onOpenChange: documentItemSelectorOpenChange,
		open: documentItemSelectorOpen,
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
	const {
		observer: cmsFileItemSelectorObserver,
		onOpenChange: cmsFileItemSelectorOpenChange,
		open: cmsFileItemSelectorOpen,
	} = useModal();
	const {
		observer: user2ItemSelectorModalObserver,
		onOpenChange: user2ItemSelectorModalOpenChange,
		open: user2ItemSelectorModalOpen,
	} = useModal();
	const {
		observer: space2ItemSelectorModalObserver,
		onOpenChange: space2ItemSelectorModalOpenChange,
		open: space2ItemSelectorModalOpen,
	} = useModal();
	const {
		observer: userMultiSelectItemSelectorModalObserver,
		onOpenChange: userMultiSelectItemSelectorModalOpenChange,
		open: userMultiSelectItemSelectorModalOpen,
	} = useModal();
	const {
		observer: spacesMultiSelectItemSelectorModalObserver,
		onOpenChange: spacesMultiSelectItemSelectorModalOpenChange,
		open: spacesMultiSelectItemSelectorModalOpen,
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
							onItemsChange={(items: Space[]) => {
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

			<SampleContainer label="Item selector pops up modal on button click autocomplete (Users)">
				<ItemSelector
					apiURL={userAccountsItemSelectorConfig.apiURL}
					itemSelectorModalProps={{
						fdsProps: {
							...FDS_DEFAULT_PROPS,
							apiURL: userAccountsItemSelectorConfig.apiURL,
							id: `itemSelectorModal-users-${getRandomId()}`,
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.USER_ACCOUNTS,
							}),
						},
						items: user2 ? [user2] : [],
						observer: user2ItemSelectorModalObserver,
						onItemsChange: (items: User[]) => setUser2(items[0]),
						onOpenChange: user2ItemSelectorModalOpenChange,
						open: user2ItemSelectorModalOpen,
						type: userAccountsItemSelectorConfig.type,
					}}
					locator={{
						id: 'id',
						label: 'name',
						value: 'id',
					}}
					placeholder="Select an User"
				>
					{(item) => (
						<ItemSelector.Item key={item.id} textValue={item.name}>
							{item.name}
						</ItemSelector.Item>
					)}
				</ItemSelector>
			</SampleContainer>

			<SampleContainer label="Multiple Select Item selector pops up modal on button click (Users)">
				<ItemSelector
					apiURL={userAccountsItemSelectorConfig.apiURL}
					itemSelectorModalProps={{
						fdsProps: {
							...FDS_DEFAULT_PROPS,
							apiURL: userAccountsItemSelectorConfig.apiURL,
							id: `itemSelectorModal-documents-${getRandomId()}`,
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.USER_ACCOUNTS,
							}),
						},
						items: usersMultiSelect,
						observer: userMultiSelectItemSelectorModalObserver,
						onItemsChange: (items: User[]) => {
							setUsersMultiSelect([
								...usersMultiSelect,
								...items,
							]);
						},
						onOpenChange:
							userMultiSelectItemSelectorModalOpenChange,
						open: userMultiSelectItemSelectorModalOpen,
						type: userAccountsItemSelectorConfig.type,
					}}
					locator={{
						id: 'id',
						label: 'name',
						value: 'id',
					}}
					multiSelect
					placeholder="Select an User"
				>
					{(item) => (
						<ItemSelector.Item key={item.id} textValue={item.name}>
							{item.name}
						</ItemSelector.Item>
					)}
				</ItemSelector>
			</SampleContainer>

			<SampleContainer label="Item selector pops up modal on button click autocomplete (Spaces)">
				<ItemSelector
					apiURL={`${location.origin}/o/headless-asset-library/v1.0/asset-libraries`}
					itemSelectorModalProps={{
						fdsProps: {
							...FDS_DEFAULT_PROPS,
							apiURL: `${location.origin}/o/headless-asset-library/v1.0/asset-libraries`,
							id: `itemSelectorModal-spaces-${getRandomId()}`,
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.ASSET_LIBRARIES,
							}),
						},
						items: space3 ? [space3] : [],
						observer: space2ItemSelectorModalObserver,
						onItemsChange: (items: Space[]) => setSpace3(items[0]),
						onOpenChange: space2ItemSelectorModalOpenChange,
						open: space2ItemSelectorModalOpen,
						type: assetLibrariesItemSelectorConfig.type,
					}}
					locator={{
						id: 'id',
						label: 'name',
						value: 'id',
					}}
					placeholder="Select an Space"
				>
					{(item) => (
						<ItemSelector.Item key={item.id} textValue={item.name}>
							{item.name}
						</ItemSelector.Item>
					)}
				</ItemSelector>
			</SampleContainer>

			<SampleContainer label="Multiple Select Item selector pops up modal on button click (Spaces)">
				<ItemSelector
					apiURL={`${location.origin}/o/headless-asset-library/v1.0/asset-libraries`}
					itemSelectorModalProps={{
						fdsProps: {
							...FDS_DEFAULT_PROPS,
							apiURL: `${location.origin}/o/headless-asset-library/v1.0/asset-libraries`,
							id: `itemSelectorModal-spaces-${getRandomId()}`,
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.ASSET_LIBRARIES,
							}),
						},
						items: spacesMultiSelect,
						observer: spacesMultiSelectItemSelectorModalObserver,
						onItemsChange: (items: Space[]) => {
							setSpacesMultiSelect([
								...spacesMultiSelect,
								...items,
							]);
						},
						onOpenChange:
							spacesMultiSelectItemSelectorModalOpenChange,
						open: spacesMultiSelectItemSelectorModalOpen,
						type: assetLibrariesItemSelectorConfig.type,
					}}
					locator={{
						id: 'id',
						label: 'name',
						value: 'id',
					}}
					multiSelect
					placeholder="Select an Space"
				>
					{(item) => (
						<ItemSelector.Item key={item.id} textValue={item.name}>
							{item.name}
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
					onItemsChange={(items: Document[]) => {
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
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.DOCUMENTS,
							}),
						},
						items: documentsItemSelectorModal,
						locator: documentsItemSelectorConfig.locator,
						multiSelect: true,
						observer: documentItemSelectorObserver,
						onItemsChange: (items: Document[]) => {
							setDocumentsItemSelectorModal(items);
						},
						onOpenChange: documentItemSelectorOpenChange,
						open: documentItemSelectorOpen,
						type: documentsItemSelectorConfig.type,
					}}
				/>

				<ItemSelectorModal<Space>
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
						items: spacesItemSelectorModal,
						locator: assetLibrariesItemSelectorConfig.locator,
						observer: spaceItemSelectorObserver,
						onItemsChange: (items: Space[]) => {
							setSpacesItemSelectorModal(items);
						},
						onOpenChange: spaceItemSelectorOpenChange,
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
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.USER_ACCOUNTS,
							}),
						},
						items: usersItemSelectorModal,
						locator: userAccountsItemSelectorConfig.locator,
						observer: userItemSelectorObserver,
						onItemsChange: (items: User[]) => {
							setUsersItemSelectorModal(items);
						},
						onOpenChange: userItemSelectorOpenChange,
						open: userItemSelectorOpen,
						type: userAccountsItemSelectorConfig.type,
					}}
				/>

				<ItemSelectorModal<CMSFile>
					{...{
						fdsProps: {
							...FDS_DEFAULT_PROPS,
							apiURL: cmsFileItemSelectorConfig.apiURL,
							filters: [
								{
									apiURL: '/o/headless-asset-library/v1.0/asset-libraries',
									entityFieldType: 'collection',
									id: 'groupIds',
									itemKey: 'siteId',
									itemLabel: 'name',
									label: Liferay.Language.get('space'),
									multiple: true,
									type: 'selection',
								},
							],

							id: `itemSelectorModal-cms-files-${getRandomId()}`,
							views: getDefaultItemSelectorModalViews({
								viewsConfig:
									EItemSelectorModalViewsConfig.CMS_FILES,
							}),
						},
						items: cmsFile ? [cmsFile] : [],
						locator: cmsFileItemSelectorConfig.locator,
						observer: cmsFileItemSelectorObserver,
						onItemsChange: (items: CMSFile[]) => {
							setCMSFile(items[0]);
						},
						onOpenChange: cmsFileItemSelectorOpenChange,
						open: cmsFileItemSelectorOpen,
						type: cmsFileItemSelectorConfig.type,
					}}
				/>

				<ClayButton.Group className="mb-3" spaced>
					<ClayButton
						displayType="primary"
						onClick={() => {
							documentItemSelectorOpenChange(true);
						}}
					>
						Select Documents
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

					<ClayButton
						displayType="primary"
						onClick={() => {
							cmsFileItemSelectorOpenChange(true);
						}}
					>
						Select CMS File
					</ClayButton>
				</ClayButton.Group>

				{!!documentsItemSelectorModal.length &&
					documentsItemSelectorModal.map((document) => (
						<ClayAlert
							displayType="info"
							key={document.id}
							symbol="document"
							title={document.fileName}
						/>
					))}

				{!!spacesItemSelectorModal.length && (
					<ClayAlert
						displayType="info"
						symbol="nodes"
						title={spacesItemSelectorModal[0].name}
					/>
				)}

				{!!usersItemSelectorModal.length && (
					<ClayAlert
						displayType="info"
						symbol="user"
						title={usersItemSelectorModal[0].name}
					/>
				)}

				{cmsFile && (
					<ClayAlert
						displayType="info"
						symbol="file-template"
						title={cmsFile.title}
					/>
				)}
			</SampleContainer>
		</>
	);
}
