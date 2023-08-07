/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text, TreeView} from '@clayui/core';
import Icon from '@clayui/icon';
import {
	CustomVerticalBar,
	ManagementToolbarSearch,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React, {useState} from 'react';
import {useStore, useZoomPanHelper} from 'react-flow-renderer';

import './LeftSidebar.scss';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {LeftSidebarDefinitionItemType, LeftSidebarItemType} from '../types';

const TYPES_TO_SYMBOLS = {
	objectDefinition: 'catalog',
	objectFolder: 'folder',
};

export default function LeftSidebar() {
	const [query, setQuery] = useState('');
	const [{leftSidebarItems}, dispatch] = useFolderContext();
	const {setCenter} = useZoomPanHelper();
	const store = useStore();

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
				<ClayButton>
					{Liferay.Language.get('create-new-object')}
				</ClayButton>

				<ManagementToolbarSearch
					query={query}
					setQuery={(searchTerm) => setQuery(searchTerm)}
				/>

				<TreeView<LeftSidebarItemType | LeftSidebarDefinitionItemType>
					items={leftSidebarItems}
					nestedKey="objectDefinitions"
					onSelect={(item) => {
						if (item.type === 'objectDefinition') {
							const {edges, nodes} = store.getState();

							dispatch({
								payload: {
									edges,
									nodes,
									selectedObjectDefinitionName: (item as LeftSidebarDefinitionItemType)
										.definitionName,
								},
								type: TYPES.SET_SELECTED_NODE,
							});

							const selectedNode = nodes.find(
								(definitionNode) =>
									definitionNode.data.name ===
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
								setCenter(x, y, 1.5);
							}
						}
					}}
					showExpanderOnHover={false}
				>
					{(item: LeftSidebarItemType) => (
						<TreeView.Item>
							<TreeView.ItemStack>
								<Icon symbol={TYPES_TO_SYMBOLS[item.type]} />

								<Text weight="semi-bold">{item.name}</Text>
							</TreeView.ItemStack>

							<TreeView.Group items={item.objectDefinitions}>
								{({name, selected, type}) => (
									<TreeView.Item
										active={selected}
										className={classNames({
											'lfr-objects__model-builder-left-sidebar-item': selected,
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
			</div>
		</CustomVerticalBar>
	);
}
