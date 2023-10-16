/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayPanel from '@clayui/panel';
import {
	CustomVerticalBar,
	ManagementToolbarSearch,
	stringIncludesQuery,
} from '@liferay/object-js-components-web';
import React, {useMemo, useState} from 'react';

import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {LeftSidebarItem} from '../types';
import {LeftSidebarEmptySearch} from './LeftSidebarEmptySearch';
import LeftSidebarTreeView from './LeftSidebarTreeView';

interface LeftSidebarProps {
	setShowModal: (value: React.SetStateAction<ModelBuilderModals>) => void;
}

export default function LeftSidebar({setShowModal}: LeftSidebarProps) {
	const [expandedKeys, setExpandedKeys] = useState<Set<React.Key>>(
		new Set(['uncategorized'])
	);
	const [query, setQuery] = useState('');
	const [
		{isLoadingObjectFolder, leftSidebarItems, selectedObjectFolder},
	] = useObjectFolderContext();

	const filteredLeftSidebarItems = useMemo(() => {
		const keys = [] as string[];

		const newLeftSidebarItems = leftSidebarItems.map((leftSidebarItem) => {
			if (!leftSidebarItem.leftSidebarObjectDefinitionItems) {
				return leftSidebarItem;
			}

			const newLeftSidebarObjectDefinitionItems = leftSidebarItem.leftSidebarObjectDefinitionItems.filter(
				(leftSidebarObjectDefinitionItem) =>
					stringIncludesQuery(
						leftSidebarObjectDefinitionItem.label,
						query
					)
			);

			keys.push(leftSidebarItem.name);

			return {
				...leftSidebarItem,
				id: leftSidebarItem.name,
				leftSidebarObjectDefinitionItems: newLeftSidebarObjectDefinitionItems,
			};
		});

		const selectedObjectFolderKey = keys.find(
			(key) => key === selectedObjectFolder.name
		) as string;

		setExpandedKeys(new Set([selectedObjectFolderKey]));

		return newLeftSidebarItems;
	}, [leftSidebarItems, query, selectedObjectFolder]);

	const leftSidebarOtherObjectFoldersItems = filteredLeftSidebarItems.filter(
		(filteredLeftSidebarItem) =>
			filteredLeftSidebarItem.objectFolderName !==
			selectedObjectFolder.name
	);

	leftSidebarOtherObjectFoldersItems.sort((a, b) =>
		a.objectFolderName > b.objectFolderName
			? 1
			: b.objectFolderName > a.objectFolderName
			? -1
			: 0
	);

	const leftSidebarSelectedObjectFolderItem = filteredLeftSidebarItems.find(
		(filteredLeftSidebarItem) =>
			filteredLeftSidebarItem.objectFolderName ===
			selectedObjectFolder.name
	) as LeftSidebarItem;

	return (
		<CustomVerticalBar
			defaultActive="objectsModelBuilderLeftSidebar"
			panelWidth={300}
			position="left"
			resize={false}
			triggerSideBarAnimation={true}
			verticalBarItems={[
				{
					title: 'objectsModelBuilderLeftSidebar',
				},
			]}
		>
			<div className="lfr-objects__model-builder-left-sidebar">
				<ClayButton
					aria-labelledby={Liferay.Language.get('create-new-object')}
					className="lfr-objects__model-builder-left-sidebar-body-create-new-object-button"
					onClick={() =>
						setShowModal((previousState: ModelBuilderModals) => ({
							...previousState,
							addObjectDefinition: true,
						}))
					}
					size="sm"
				>
					{Liferay.Language.get('create-new-object')}
				</ClayButton>

				<ManagementToolbarSearch
					query={query}
					setQuery={(searchTerm) => setQuery(searchTerm)}
				/>

				{!isLoadingObjectFolder ? (
					<>
						{!leftSidebarOtherObjectFoldersItems.length &&
						leftSidebarSelectedObjectFolderItem
							?.leftSidebarObjectDefinitionItems?.length === 0 &&
						query ? (
							<LeftSidebarEmptySearch />
						) : (
							!!leftSidebarItems.length && (
								<>
									<LeftSidebarTreeView
										expandedKeys={expandedKeys}
										leftSidebarOtherObjectFoldersItems={
											leftSidebarOtherObjectFoldersItems
										}
										leftSidebarSelectedObjectFolderItem={
											leftSidebarSelectedObjectFolderItem
										}
										setExpandedKeys={setExpandedKeys}
									/>

									<ClayPanel
										className="lfr-objects__model-builder-left-sidebar-body-panel"
										collapsable
										defaultExpanded
										displayTitle={Liferay.Language.get(
											'other-folders'
										)}
										displayType="unstyled"
										showCollapseIcon={true}
									>
										<ClayPanel.Body>
											<LeftSidebarTreeView
												expandedKeys={expandedKeys}
												leftSidebarOtherObjectFoldersItems={
													leftSidebarOtherObjectFoldersItems
												}
												leftSidebarSelectedObjectFolderItem={
													leftSidebarSelectedObjectFolderItem
												}
												setExpandedKeys={
													setExpandedKeys
												}
												showActions
											/>
										</ClayPanel.Body>
									</ClayPanel>
								</>
							)
						)}
					</>
				) : (
					<div className="lfr-objects__model-builder-left-sidebar-loading">
						<span
							aria-hidden="true"
							className="loading-animation loading-animation-secondary loading-animation-sm"
						></span>
					</div>
				)}
			</div>
		</CustomVerticalBar>
	);
}
