/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Text, TreeView} from '@clayui/core';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import Icon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {API, getLocalizableLabel} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import {openToast, sub} from 'frontend-js-web';
import React from 'react';
import {Node, useStoreState, useZoomPanHelper} from 'react-flow-renderer';

import {getUpdatedModelBuilderStructurePayload} from '../../ViewObjectDefinitions/objectDefinitionUtil';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {LeftSidebarItem, LeftSidebarObjectDefinitionItem} from '../types';

import './LeftSidebar.scss';

const TYPES_TO_SYMBOLS = {
	linkedObjectDefinition: 'link',
	objectDefinition: 'catalog',
	objectFolder: 'folder',
};

export default function LeftSidebarTreeView({
	expandedKeys,
	leftSidebarOtherObjectFoldersItems,
	leftSidebarSelectedObjectFolderItem,
	setExpandedKeys,
	showActions,
}: {
	expandedKeys: Set<React.Key>;
	leftSidebarOtherObjectFoldersItems: LeftSidebarItem[];
	leftSidebarSelectedObjectFolderItem: LeftSidebarItem;
	setExpandedKeys: React.Dispatch<React.SetStateAction<Set<React.Key>>>;
	showActions?: boolean;
}) {
	const [{selectedObjectFolder}, dispatch] = useObjectFolderContext();
	const {setCenter} = useZoomPanHelper();

	const {edges, nodes} = useStoreState((state) => state);

	const changeObjectDefinitionNodeViewButton = (
		hiddenObjectDefinitionNode: boolean,
		dispatch: Function
	) => (
		<div className="lfr-objects__model-builder-left-sidebar-show-folders-button">
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
		</div>
	);

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
				(await API.save({
					item: objectDefinitionToBeMoved,
					method: 'PATCH',
					returnValue: true,
					url: `/o/object-admin/v1.0/object-definitions/${objectDefinitionToBeMoved?.id}`,
				})) as ObjectDefinition;

				setTimeout(async () => {
					const payload = await getUpdatedModelBuilderStructurePayload(
						selectedObjectFolder.name
					);

					dispatch({
						payload,
						type: TYPES.UPDATE_MODEL_BUILDER_STRUCTURE,
					});
				}, 200);

				openToast({
					message: sub(
						Liferay.Language.get('x-was-moved-successfully'),
						`<strong>${Liferay.Util.escapeHTML(
							getLocalizableLabel(
								objectDefinitionToBeMoved.defaultLanguageId,
								objectDefinitionToBeMoved.label
							)
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
			expandedKeys={expandedKeys}
			items={
				showActions
					? newLeftSidebarOtherObjectFolderItems
					: [leftSidebarSelectedObjectFolderItem]
			}
			nestedKey="leftSidebarObjectDefinitionItems"
			onExpandedChange={setExpandedKeys}
			onSelect={(item) => {
				if (
					!showActions &&
					selectedObjectFolder.objectFolderItems?.find(
						(objectFolderItem) =>
							objectFolderItem.objectDefinitionExternalReferenceCode ===
							(item as LeftSidebarObjectDefinitionItem)
								.externalReferenceCode
					)
				) {
					dispatch({
						payload: {
							objectDefinitionNodes: nodes,
							objectRelationshipEdges: edges,
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
								<div>
									<Icon
										symbol={
											TYPES_TO_SYMBOLS[
												leftSidebarItem.type
											]
										}
									/>
								</div>

								<div className="lfr-objects__model-builder-left-sidebar-current-object-folder-content-title">
									<Text weight="semi-bold">
										{leftSidebarItem.name}
									</Text>
								</div>

								{leftSidebarItem.objectFolderName !==
									selectedObjectFolder.name && (
									<ClayTooltipProvider>
										<div className="lfr-objects__model-builder-left-sidebar-go-to-folder-button">
											<ClayButton
												aria-label={Liferay.Language.get(
													'go-to-folder'
												)}
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
												objectDefinitionNodes: nodes,
												objectRelationshipEdges: edges,
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
							<div className="lfr-objects__model-builder-left-sidebar-current-object-folder-group">
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
																aria-label={Liferay.Language.get(
																	'actions'
																)}
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
															objectDefinitionNodes: nodes,
															objectRelationshipEdges: edges,
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

									<span
										className="text-truncate"
										title={label}
									>
										{label}
									</span>
								</TreeView.Item>
							</div>
						)}
					</TreeView.Group>
				</TreeView.Item>
			)}
		</TreeView>
	);
}
