/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {ClayVerticalNav} from '@clayui/nav';
import React, {useState} from 'react';

import {IPages} from '../../utils/types';
import AttributesPage from './AttributesPage';
import ConnectPage from './ConnectPage';
import PeoplePage from './PeoplePage';
import PropertiesPage from './PropertiesPage';

export interface IGenericPageProps {
	title: string;
}

enum EPages {
	Attributes = 'ATTRIBUTES',
	People = 'PEOPLE',
	Properties = 'PROPERTIES',
	WorkspaceConnection = 'WORKSPACE_CONNECTION',
}

const PAGES: IPages<IGenericPageProps, EPages>[] = [
	{
		Component: ConnectPage,
		key: EPages.WorkspaceConnection,
		title: Liferay.Language.get('workspace-connection'),
	},
	{
		Component: PropertiesPage,
		key: EPages.Properties,
		title: Liferay.Language.get('properties'),
	},
	{
		Component: PeoplePage,
		key: EPages.People,
		title: Liferay.Language.get('people'),
	},
	{
		Component: AttributesPage,
		key: EPages.Attributes,
		title: Liferay.Language.get('attributes'),
	},
];

const DefaultPage: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const [activePage, setactivePage] = useState(EPages.WorkspaceConnection);

	return (
		<ClayLayout.ContainerFluid>
			<ClayLayout.Row>
				<ClayLayout.Col size={3}>
					<ClayVerticalNav
						items={PAGES.map(({key, title: label}) => {
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
					{PAGES.map(({Component, key, title}) => (
						<div key={key}>
							{activePage === key && <Component title={title} />}
						</div>
					))}
				</ClayLayout.Col>
			</ClayLayout.Row>
		</ClayLayout.ContainerFluid>
	);
};

export default DefaultPage;
