/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayLayout from '@clayui/layout';
import {ClayVerticalNav} from '@clayui/nav';
import React, {useState} from 'react';

import WorkspaceConnection from './WorkspaceConnection';

enum EPages {
	Attributes = 'attributes',
	People = 'people',
	Properties = 'properties',
	WorkspaceConnection = 'workspace-connection',
}

const PAGES = [
	{
		Component: () => <WorkspaceConnection />,
		key: EPages.WorkspaceConnection,
		label: Liferay.Language.get('workspace-connection'),
	},
	{
		Component: () => <div>properties</div>,
		key: EPages.Properties,
		label: Liferay.Language.get('properties'),
	},
	{
		Component: () => <div>people</div>,
		key: EPages.People,
		label: Liferay.Language.get('people'),
	},
	{
		Component: () => <div>attributes</div>,
		key: EPages.Attributes,
		label: Liferay.Language.get('attributes'),
	},
];

const DefaultPage: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const [activePage, setactivePage] = useState(EPages.WorkspaceConnection);

	return (
		<ClayLayout.ContainerFluid>
			<ClayLayout.Row>
				<ClayLayout.Col size={3}>
					<ClayVerticalNav
						items={PAGES.map(({key, label}) => {
							return {
								active: activePage === key,
								label,
								onClick: () => setactivePage(key),
							};
						})}
						large={false}
					/>
				</ClayLayout.Col>

				<ClayLayout.Col size={9}>
					{PAGES.map(
						({Component, key}) =>
							activePage === key && <Component />
					)}
				</ClayLayout.Col>
			</ClayLayout.Row>
		</ClayLayout.ContainerFluid>
	);
};

export default DefaultPage;
