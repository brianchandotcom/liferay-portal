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
import {ViewObjectDefinitionsModals} from '../../ViewObjectDefinitions/ViewObjectDefinitions';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {LeftSidebarDefinitionItemType, LeftSidebarItemType} from '../types';

const TYPES_TO_SYMBOLS = {
	objectDefinition: 'catalog',
	objectFolder: 'folder',
	objectLink: 'link',
};

interface LeftSidebarProps {
	selectedFolderName: string;
	setShowModal: (
		value: React.SetStateAction<ViewObjectDefinitionsModals>
	) => void;
}

export default function LeftSidebar({
	selectedFolderName,
	setShowModal,
}: LeftSidebarProps) {
	const [query, setQuery] = useState('');
	const [{leftSidebarItems, selectedFolder}, dispatch] = useFolderContext();
	const {setCenter} = useZoomPanHelper();
	const store = useStore();

	const changeNodeViewButton = (hiddenNode: boolean, dispatch: Function) => (
		<ClayButtonWithIcon
			aria-label={
				hiddenNode
					? Liferay.Language.get('hidden')
					: Liferay.Language.get('show')
			}
			displayType="unstyled"
			onClick={(event) => {
				event.stopPropagation();
				dispatch();
			}}
			symbol={hiddenNode ? 'hidden' : 'view'}
		/>
	);

	const filteredItems = useMemo(() => {
		return leftSidebarItems.map((sidebarItem) => {
			if (!sidebarItem.objectDefinitions) {
				return sidebarItem;
			}

			const newObjectDefinitions = sidebarItem.objectDefinitions.filter(
				(objectDefinition) =>
					stringIncludesQuery(objectDefinition.name, query)
			);

			return {
				...sidebarItem,
				objectDefinitions: newObjectDefinitions,
			};
		});
	}, [query, leftSidebarItems]);

	const handleMove = async ({
		definitionId,
		folderName,
	}: {
		definitionId: number;
		folderName: string;
	}) => {
		const folderResponse = await API.getAllFolders();

		const currentFolder = folderResponse.find(
			(folder) => folder.name === folderName
		);

		const folderDefinitions = await API.getObjectDefinitions(
			`filter=objectFolderExternalReferenceCode eq '${currentFolder?.externalReferenceCode}'`
		);

		const objectDefinition = folderDefinitions.find(
			(definition) => definition.id === definitionId
		) as ObjectDefinitionNodeData;

		if (objectDefinition) {
			const movedObjectDefinition: ObjectDefinitionNodeData = {
				...objectDefinition,
				objectFolderExternalReferenceCode:
					selectedFolder.externalReferenceCode,
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
						selectedFolderName,
					},
					type: TYPES.ADD_NEW_NODE_TO_FOLDER,
				});

				if (!objectDefinition.linkedDefinition) {
					dispatch({
						payload: {
							currentFolderName: currentFolder!.name,
							deletedNodeName: newObjectDefinition.name,
						},
						type: TYPES.DELETE_FOLDER_NODE,
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
		const otherFolders = filteredItems.filter(
			(item) => item.folderName !== selectedFolderName
		);

		const selectedFolder = filteredItems.find(
			(item) => item.folderName === selectedFolderName
		) as LeftSidebarItemType;

		const linkedDefinitions = selectedFolder.objectDefinitions?.filter(
			(definition) => definition.type === 'objectLink'
		);

		const newOtherFolders = otherFolders.map((folder) => {
			const definitions = folder.objectDefinitions?.map((definition) => {
				const objectLinked = linkedDefinitions?.find(
					(linkedDefinition) =>
						linkedDefinition.definitionId ===
						definition.definitionId
				);
				if (objectLinked) {
					return {
						...definition,
						linked: true,
					};
				}

				return definition;
			});

			return {
				...folder,
				objectDefinitions: definitions,
			};
		});

		return (
			<TreeView<LeftSidebarItemType | LeftSidebarDefinitionItemType>
				items={showActions ? newOtherFolders : [selectedFolder]}
				nestedKey="objectDefinitions"
				onSelect={(item) => {
					if (
						selectedFolder.objectDefinitions?.find(
							(definition) =>
								definition.definitionId ===
								(item as LeftSidebarDefinitionItemType)
									.definitionId
						)
					) {
						const {edges, nodes} = store.getState();

						dispatch({
							payload: {
								edges,
								nodes,
								selectedObjectDefinitionId: (item as LeftSidebarDefinitionItemType).definitionId.toString(),
							},
							type: TYPES.SET_SELECTED_NODE,
						});

						const selectedNode = (nodes as Node<
							ObjectDefinitionNodeData
						>[]).find(
							(definitionNode) =>
								definitionNode.data?.name ===
								(item as LeftSidebarDefinitionItemType)
									.definitionName
						);

						if (selectedNode) {
							const x =
								selectedNode.__rf.position.x +
								selectedNode.__rf.width / 2;
							const y =
								selectedNode.__rf.position.y +
								selectedNode.__rf.height / 2;
							setCenter(x, y, 1.2);
						}
					}
				}}
				showExpanderOnHover={false}
			>
				{(item: LeftSidebarItemType) => (
					<TreeView.Item>
						<TreeView.ItemStack>
							<div className="lfr-objects__model-builder-left-sidebar-current-folder-container">
								<div className="lfr-objects__model-builder-left-sidebar-current-folder-content">
									<Icon
										symbol={TYPES_TO_SYMBOLS[item.type]}
									/>

									<Text weight="semi-bold">{item.name}</Text>
								</div>

								{!showActions &&
									changeNodeViewButton(
										item.hiddenFolderNodes,
										() =>
											dispatch({
												payload: {
													hiddenFolderNodes:
														item.hiddenFolderNodes,
													leftSidebarItem: item,
												},
												type:
													TYPES.BULK_CHANGE_NODE_VIEW,
											})
									)}
							</div>
						</TreeView.ItemStack>

						<TreeView.Group items={item.objectDefinitions}>
							{({
								definitionId,
								definitionName,
								hiddenNode,
								linked,
								name,
								selected,
								type,
							}) => (
								<TreeView.Item
									actions={
										showActions ? (
											type === 'objectLink' ? (
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
																		definitionId,
																		folderName:
																			item.folderName,
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
											changeNodeViewButton(
												hiddenNode,
												() =>
													dispatch({
														payload: {
															definitionId,
															definitionName,
															hiddenNode,
															leftSidebarItem: item,
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

									{name}
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
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								addObjectDefinition: true,
							})
						)
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
