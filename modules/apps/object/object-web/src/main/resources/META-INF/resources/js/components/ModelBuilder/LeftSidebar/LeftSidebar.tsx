/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Text, TreeView} from '@clayui/core';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import Icon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import {
	API,
	CustomVerticalBar,
	ManagementToolbarSearch,
	getLocalizableLabel,
	stringIncludesQuery,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import {openToast, sub} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';
import {Node, useStore, useZoomPanHelper} from 'react-flow-renderer';

import './LeftSidebar.scss';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {LeftSidebarItem, LeftSidebarObjectDefinitionItem} from '../types';

const TYPES_TO_SYMBOLS = {
	linkedObjectDefinition: 'link',
	objectDefinition: 'catalog',
	objectFolder: 'folder',
};

interface LeftSidebarProps {
	selectedObjectFolderName: string;
	setShowModal: (value: React.SetStateAction<ModelBuilderModals>) => void;
}

export default function LeftSidebar({
	selectedObjectFolderName,
	setShowModal,
}: LeftSidebarProps) {
	const [query, setQuery] = useState('');
	const [
		{leftSidebarItems, selectedObjectFolder},
		dispatch,
	] = useObjectFolderContext();
	const {setCenter} = useZoomPanHelper();
	const store = useStore();

	const changeObjectDefinitionNodeViewButton = (
		hiddenObjectDefinitionNode: boolean,
		dispatch: Function
	) => (
		<ClayButtonWithIcon
			aria-label={
				hiddenObjectDefinitionNode
					? Liferay.Language.get('hidden')
					: Liferay.Language.get('show')
			}
			displayType="unstyled"
			onClick={(event) => {
				event.stopPropagation();
				dispatch();
			}}
			symbol={hiddenObjectDefinitionNode ? 'hidden' : 'view'}
		/>
	);

	const filteredLeftSidebarItems = useMemo(() => {
		return leftSidebarItems.map((sidebarItem) => {
			if (!sidebarItem.leftSidebarObjectDefinitionItems) {
				return sidebarItem;
			}

			const newObjectDefinitions = sidebarItem.leftSidebarObjectDefinitionItems.filter(
				(leftSidebarObjectDefinitionItem) =>
					stringIncludesQuery(
						leftSidebarObjectDefinitionItem.label,
						query
					)
			);

			return {
				...sidebarItem,
				leftSidebarObjectDefinitionItems: newObjectDefinitions,
			};
		});
	}, [query, leftSidebarItems]);

	const handleMove = async ({
		objectDefinitionId,
		objectFolderName,
	}: {
		objectDefinitionId: number;
		objectFolderName: string;
	}) => {
		const objectFolders = await API.getAllObjectFolders();

		const currentObjectFolder = objectFolders.find(
			(objectFolder) => objectFolder.name === objectFolderName
		);

		const objectDefinitionsFilteredByObjectFolder = await API.getObjectDefinitions(
			`filter=objectFolderExternalReferenceCode eq '${currentObjectFolder?.externalReferenceCode}'`
		);

		const objectDefinition = objectDefinitionsFilteredByObjectFolder.find(
			(objectDefinition) => objectDefinition.id === objectDefinitionId
		) as ObjectDefinitionNodeData;

		if (objectDefinition) {
			const movedObjectDefinition: ObjectDefinitionNodeData = {
				...objectDefinition,
				objectFolderExternalReferenceCode:
					selectedObjectFolder.externalReferenceCode,
			};

			try {
				const newObjectDefinition = (await API.save({
					item: movedObjectDefinition,
					method: 'PATCH',
					returnValue: true,
					url: `/o/object-admin/v1.0/object-definitions/${objectDefinition?.id}`,
				})) as ObjectDefinition;

				dispatch({
					payload: {
						newObjectDefinition,
						selectedObjectFolderName,
					},
					type: TYPES.ADD_OBJECT_DEFINITION_TO_OBJECT_FOLDER,
				});

				if (!objectDefinition.linkedObjectDefinition) {
					dispatch({
						payload: {
							currentObjectFolderName: currentObjectFolder!.name,
							deletedObjectDefinitionName:
								newObjectDefinition.name,
						},
						type: TYPES.DELETE_OBJECT_DEFINITION,
					});
				}

				openToast({
					message: sub(
						Liferay.Language.get('x-was-moved-successfully'),
						`<strong>${getLocalizableLabel(
							objectDefinition.defaultLanguageId,
							movedObjectDefinition.label
						)}</strong>`
					),
					type: 'success',
				});
			}
			catch (error) {}
		}
	};

	const TreeViewComponent = ({showActions}: {showActions?: boolean}) => {
		const leftSidebarOtherObjectFoldersItems = filteredLeftSidebarItems.filter(
			(leftSidebarItem) =>
				leftSidebarItem.objectFolderName !== selectedObjectFolderName
		);

		const leftSidebarSelectedObjectFolderItem = filteredLeftSidebarItems.find(
			(leftSidebarItem) =>
				leftSidebarItem.objectFolderName === selectedObjectFolderName
		) as LeftSidebarItem;

		const linkedObjectDefinitions = leftSidebarSelectedObjectFolderItem.leftSidebarObjectDefinitionItems?.filter(
			(leftSidebarObjectDefinitionItem) =>
				leftSidebarObjectDefinitionItem.type ===
				'linkedObjectDefinition'
		);

		const newOtherObjectFolders = leftSidebarOtherObjectFoldersItems.map(
			(leftSidebarOtherObjectFoldersItem) => {
				const objectDefinitions = leftSidebarOtherObjectFoldersItem.leftSidebarObjectDefinitionItems?.map(
					(leftSidebarObjectDefinitionItem) => {
						const linkedObjectDefinition = linkedObjectDefinitions?.find(
							(linkedObjectDefinition) =>
								linkedObjectDefinition.id ===
								leftSidebarObjectDefinitionItem.id
						);
						if (linkedObjectDefinition) {
							return {
								...leftSidebarObjectDefinitionItem,
								linked: true,
							};
						}

						return leftSidebarObjectDefinitionItem;
					}
				);

				return {
					...leftSidebarOtherObjectFoldersItem,
					objectDefinitions,
				};
			}
		);

		return (
			<TreeView<LeftSidebarItem | LeftSidebarObjectDefinitionItem>
				items={
					showActions
						? newOtherObjectFolders
						: [leftSidebarSelectedObjectFolderItem]
				}
				nestedKey="objectDefinitions"
				onSelect={(item) => {
					if (
						selectedObjectFolder.objectDefinitions?.find(
							(objectDefinition) =>
								objectDefinition.id ===
								(item as LeftSidebarObjectDefinitionItem).id
						)
					) {
						const {edges, nodes} = store.getState();

						dispatch({
							payload: {
								edges,
								nodes,
								selectedObjectDefinitionId: (item as LeftSidebarObjectDefinitionItem).id.toString(),
							},
							type: TYPES.SET_SELECTED_OBJECT_DEFINITION_NODE,
						});

						const selectedObjectDefinitionNode = (nodes as Node<
							ObjectDefinitionNodeData
						>[]).find(
							(objectDefinitionNode) =>
								objectDefinitionNode.data?.name ===
								(item as LeftSidebarObjectDefinitionItem).name
						);

						if (selectedObjectDefinitionNode) {
							const x =
								selectedObjectDefinitionNode.__rf.position.x +
								selectedObjectDefinitionNode.__rf.width / 2;
							const y =
								selectedObjectDefinitionNode.__rf.position.y +
								selectedObjectDefinitionNode.__rf.height / 2;
							setCenter(x, y, 1.2);
						}
					}
				}}
				showExpanderOnHover={false}
			>
				{(leftSidebarItem: LeftSidebarItem) => (
					<TreeView.Item>
						<TreeView.ItemStack>
							<div className="lfr-objects__model-builder-left-sidebar-current-object-folder-container">
								<div className="lfr-objects__model-builder-left-sidebar-current-object-folder-content">
									<Icon
										symbol={
											TYPES_TO_SYMBOLS[
												leftSidebarItem.type
											]
										}
									/>

									<Text weight="semi-bold">
										{leftSidebarItem.name}
									</Text>
								</div>

								{!showActions &&
									changeObjectDefinitionNodeViewButton(
										leftSidebarItem.hiddenObjectFolderObjectDefinitionNodes,
										() =>
											dispatch({
												payload: {
													hiddenObjectFolderObjectDefinitionNodes:
														leftSidebarItem.hiddenObjectFolderObjectDefinitionNodes,
													leftSidebarItem,
												},
												type:
													TYPES.BULK_CHANGE_NODE_VIEW,
											})
									)}
							</div>
						</TreeView.ItemStack>

						<TreeView.Group
							items={
								leftSidebarItem.leftSidebarObjectDefinitionItems
							}
						>
							{({
								hiddenObjectDefinitionNode,
								id,
								label,
								linked,
								name,
								selected,
								type,
							}) => (
								<TreeView.Item
									actions={
										showActions ? (
											type ===
											'linkedObjectDefinition' ? (
												<></>
											) : (
												<>
													<ClayDropDownWithItems
														items={[
															{
																label: Liferay.Language.get(
																	'move-to-current-folder'
																),
																onClick: () =>
																	handleMove({
																		objectDefinitionId: id,
																		objectFolderName:
																			leftSidebarItem.objectFolderName,
																	}),
																symbolLeft:
																	'move-folder',
															},
														]}
														trigger={
															<ClayButton
																displayType={
																	null
																}
																monospaced
															>
																<Icon symbol="ellipsis-v" />
															</ClayButton>
														}
													/>
												</>
											)
										) : (
											changeObjectDefinitionNodeViewButton(
												hiddenObjectDefinitionNode,
												() =>
													dispatch({
														payload: {
															hiddenObjectDefinitionNode,
															objectDefinitionId: id,
															objectDefinitionName: name,
															selectedSidebarItem: leftSidebarItem,
														},
														type:
															TYPES.CHANGE_NODE_VIEW,
													})
											)
										)
									}
									active={selected}
									className={classNames({
										'lfr-objects__model-builder-left-sidebar-item': selected,
										'lfr-objects__model-builder-left-sidebar-item-linked': linked,
									})}
								>
									<Icon symbol={TYPES_TO_SYMBOLS[type]} />

									{label}
								</TreeView.Item>
							)}
						</TreeView.Group>
					</TreeView.Item>
				)}
			</TreeView>
		);
	};

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
					className="lfr-objects__model-builder-left-sidebar-body-create-new-object-button"
					onClick={() =>
						setShowModal((previousState: ModelBuilderModals) => ({
							...previousState,
							addObjectDefinition: true,
						}))
					}
				>
					{Liferay.Language.get('create-new-object')}
				</ClayButton>

				<ManagementToolbarSearch
					query={query}
					setQuery={(searchTerm) => setQuery(searchTerm)}
				/>

				{!!leftSidebarItems.length && (
					<>
						<TreeViewComponent></TreeViewComponent>
						<ClayPanel
							className="lfr-objects__model-builder-left-sidebar-body-panel"
							collapsable
							defaultExpanded
							displayTitle={Liferay.Language.get('other-folders')}
							displayType="unstyled"
							showCollapseIcon={true}
						>
							<ClayPanel.Body>
								<TreeViewComponent
									showActions
								></TreeViewComponent>
							</ClayPanel.Body>
						</ClayPanel>
					</>
				)}
			</div>
		</CustomVerticalBar>
	);
}
