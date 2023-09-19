/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Text, TreeView} from '@clayui/core';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import Icon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {
	API,
	getLocalizableLabel,
	stringIncludesQuery,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import {openToast, sub} from 'frontend-js-web';
import React, {useMemo} from 'react';
import {
	FlowElement,
	Node,
	isNode,
	useStore,
	useZoomPanHelper,
} from 'react-flow-renderer';

import './LeftSidebar.scss';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {LeftSidebarItem, LeftSidebarObjectDefinitionItem} from '../types';

const TYPES_TO_SYMBOLS = {
	linkedObjectDefinition: 'link',
	objectDefinition: 'catalog',
	objectFolder: 'folder',
};

export default function LeftSidebarTreeView({
	query,
	showActions,
}: {
	query: string;
	showActions?: boolean;
}) {
	const [
		{elements, leftSidebarItems, selectedObjectFolder},
		dispatch,
	] = useObjectFolderContext();
	const store = useStore();
	const {setCenter} = useZoomPanHelper();

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
		return leftSidebarItems.map((leftSidebarItem) => {
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

			return {
				...leftSidebarItem,
				leftSidebarObjectDefinitionItems: newLeftSidebarObjectDefinitionItems,
			};
		});
	}, [leftSidebarItems, query]);

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

		const currentObjectFolderObjectDefinitions = await API.getObjectDefinitions(
			`filter=objectFolderExternalReferenceCode eq '${currentObjectFolder?.externalReferenceCode}'`
		);

		let objectDefinitionToBeMoved = currentObjectFolderObjectDefinitions.find(
			(currentObjectFolderObjectDefinition) =>
				currentObjectFolderObjectDefinition.id === objectDefinitionId
		);

		if (objectDefinitionToBeMoved) {
			objectDefinitionToBeMoved = {
				...objectDefinitionToBeMoved,
				objectFolderExternalReferenceCode:
					selectedObjectFolder.externalReferenceCode,
			};

			try {
				const movedObjectDefinition = (await API.save({
					item: objectDefinitionToBeMoved,
					method: 'PATCH',
					returnValue: true,
					url: `/o/object-admin/v1.0/object-definitions/${objectDefinitionToBeMoved?.id}`,
				})) as ObjectDefinition;

				dispatch({
					payload: {
						newObjectDefinition: movedObjectDefinition,
						selectedObjectFolderName: selectedObjectFolder.name,
					},
					type: TYPES.ADD_OBJECT_DEFINITION_TO_OBJECT_FOLDER,
				});

				const objectDefinitionNodeToBeMoved = elements.find(
					(element) =>
						isNode(element) &&
						element.id === objectDefinitionToBeMoved!.id.toString()
				) as FlowElement<ObjectDefinitionNodeData>;

				if (
					!objectDefinitionNodeToBeMoved.data?.linkedObjectDefinition
				) {
					dispatch({
						payload: {
							currentObjectFolderName: currentObjectFolder!.name,
							deletedObjectDefinitionName:
								movedObjectDefinition.name,
						},
						type: TYPES.DELETE_OBJECT_DEFINITION,
					});
				}

				openToast({
					message: sub(
						Liferay.Language.get('x-was-moved-successfully'),
						`<strong>${getLocalizableLabel(
							movedObjectDefinition.defaultLanguageId,
							movedObjectDefinition.label
						)}</strong>`
					),
					type: 'success',
				});
			}
			catch (error) {}
		}
	};

	const onClickGoToFolder = (selectedObjectFolderName: string) => {
		dispatch({
			payload: {
				objectFolderName: selectedObjectFolderName,
			},
			type: TYPES.SET_OBJECT_FOLDER_NAME,
		});
	};

	const leftSidebarOtherObjectFoldersItems = filteredLeftSidebarItems.filter(
		(filteredLeftSidebarItem) =>
			filteredLeftSidebarItem.objectFolderName !==
			selectedObjectFolder.name
	);

	const leftSidebarSelectedObjectFolderItem = filteredLeftSidebarItems.find(
		(filteredLeftSidebarItem) =>
			filteredLeftSidebarItem.objectFolderName ===
			selectedObjectFolder.name
	) as LeftSidebarItem;

	const linkedObjectDefinitions = leftSidebarSelectedObjectFolderItem.leftSidebarObjectDefinitionItems?.filter(
		(leftSidebarObjectDefinitionItem) =>
			leftSidebarObjectDefinitionItem.type === 'linkedObjectDefinition'
	);

	const newLeftSidebarOtherObjectFolderItems = leftSidebarOtherObjectFoldersItems.map(
		(leftSidebarObjectFolderItem) => {
			const newLeftSidebarObjectDefinitionItems = leftSidebarObjectFolderItem.leftSidebarObjectDefinitionItems?.map(
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
				...leftSidebarObjectFolderItem,
				leftSidebarObjectDefinitionItems: newLeftSidebarObjectDefinitionItems,
			};
		}
	);

	return (
		<TreeView<LeftSidebarItem | LeftSidebarObjectDefinitionItem>
			items={
				showActions
					? newLeftSidebarOtherObjectFolderItems
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
										TYPES_TO_SYMBOLS[leftSidebarItem.type]
									}
								/>

								<Text weight="semi-bold">
									{leftSidebarItem.name}
								</Text>

								{leftSidebarItem.objectFolderName !==
									selectedObjectFolder.name && (
									<ClayTooltipProvider>
										<div className="lfr-objects__model-builder-left-sidebar-go-to-folder-button">
											<ClayButton
												data-tooltip-align="bottom"
												displayType={null}
												onClick={() =>
													onClickGoToFolder(
														leftSidebarItem.objectFolderName
													)
												}
												title={Liferay.Language.get(
													'go-to-folder'
												)}
											>
												<Icon
													className="text-5"
													symbol="arrow-right-full"
												/>
											</ClayButton>
										</div>
									</ClayTooltipProvider>
								)}
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
											type: TYPES.BULK_CHANGE_NODE_VIEW,
										})
								)}
						</div>
					</TreeView.ItemStack>

					<TreeView.Group
						items={leftSidebarItem.leftSidebarObjectDefinitionItems}
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
										type === 'linkedObjectDefinition' ? (
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
															displayType={null}
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
}
