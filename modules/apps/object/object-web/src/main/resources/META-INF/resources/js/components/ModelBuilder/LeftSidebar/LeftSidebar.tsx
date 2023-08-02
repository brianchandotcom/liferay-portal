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
import React, {useState} from 'react';

import './LeftSidebar.scss';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';

const TYPES_TO_SYMBOLS = {
	objectDefinition: 'catalog',
	objectFolder: 'folder',
};

export default function LeftSidebar() {
	const [query, setQuery] = useState('');
	const [{leftSidebarItems}] = useFolderContext();

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

				<TreeView
					items={leftSidebarItems}
					nestedKey="objectDefinitions"
					showExpanderOnHover={false}
				>
					{(item) => (
						<TreeView.Item>
							<TreeView.ItemStack>
								<Icon symbol={TYPES_TO_SYMBOLS[item.type]} />

								<Text weight="semi-bold">{item.name}</Text>
							</TreeView.ItemStack>

							<TreeView.Group items={item.objectDefinitions}>
								{({name, type}) => (
									<TreeView.Item>
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
