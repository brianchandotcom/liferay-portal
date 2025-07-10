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
import {TView} from '@liferay/frontend-data-set-web';
import {
	ItemSelector,
	ItemSelectorModal,
	assetLibraryViews,
	documentsAndMediaViews,
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

export interface IItemSelectorConfiguration {
	apiURL: string;
	itemSelectionCallbackFn?: string;
	selectedItemDescriptionKey: string;
	type: string;
	views: TView[];
}

const docsAndMediaItemSelectorConfig: IItemSelectorConfiguration = {
	apiURL: `${location.origin}/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/documents`,
	itemSelectionCallbackFn: 'setDocuments',
	selectedItemDescriptionKey: 'fileName',
	type: Liferay.Language.get('file'),
	views: documentsAndMediaViews,
};
const assetsItemSelectorConfig = {
	apiURL: `${location.origin}/o/headless-asset-library/v1.0/asset-libraries`,
	selectedItemDescriptionKey: 'name',
	type: Liferay.Language.get('asset'),
	views: assetLibraryViews,
};
const usersItemSelectorConfig = {
	apiURL: `${location.origin}/o/headless-admin-user/v1.0/user-accounts`,
	selectedItemDescriptionKey: 'givenName',
	type: Liferay.Language.get('user'),
	views: userViews,
};

export default function ItemSelectorSamples() {
	const [documents, setDocuments] = useState<Document[]>([]);
	const [space, setSpace] = useState<Space>();

	const [asset, setAsset] = useState(null);
	const [file, setFile] = useState(null);
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

	function onFileSelection(file: any) {
		setFile(file);
	}

	function onSpaceSelection(asset: any) {
		setAsset(asset);
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
						apiURL: docsAndMediaItemSelectorConfig.apiURL,
						observer: fileItemSelectorObserver,
						onItemSelectorSave: onFileSelection,
						onOpenChange: fileItemSelectorOpenChange,
						open: fileItemSelectorOpen,
						selectedItemDescriptionKey:
							docsAndMediaItemSelectorConfig.selectedItemDescriptionKey,
						type: docsAndMediaItemSelectorConfig.type,
					}}
				/>

				<ItemSelectorModal
					{...{
						apiURL: assetsItemSelectorConfig.apiURL,
						observer: spaceItemSelectorObserver,
						onItemSelectorSave: onSpaceSelection,
						onOpenChange: spaceItemSelectorOpenChange,
						open: spaceItemSelectorOpen,
						selectedItemDescriptionKey:
							assetsItemSelectorConfig.selectedItemDescriptionKey,
						type: assetsItemSelectorConfig.type,
						viewsConfig: assetLibraryViews,
					}}
				/>

				<ItemSelectorModal
					{...{
						apiURL: usersItemSelectorConfig.apiURL,
						observer: userItemSelectorObserver,
						onItemSelectorSave: onUserSelection,
						onOpenChange: userItemSelectorOpenChange,
						open: userItemSelectorOpen,
						selectedItemDescriptionKey:
							usersItemSelectorConfig.selectedItemDescriptionKey,
						type: usersItemSelectorConfig.type,
						viewsConfig: 'users',
					}}
				/>

				<ClayButton.Group spaced>
					<ClayButton
						displayType="primary"
						onClick={() => {
							fileItemSelectorOpenChange(true);
						}}
					>
						Select File
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

				{asset && (
					<ClayAlert
						displayType="info"
						symbol="nodes"
						title={asset['name']}
					/>
				)}

				{file && (
					<ClayAlert
						displayType="info"
						symbol="document"
						title={file['fileName']}
					/>
				)}

				{user && (
					<ClayAlert
						displayType="info"
						symbol="user"
						title={user['givenName']}
					/>
				)}
			</SampleContainer>
		</>
	);
}
