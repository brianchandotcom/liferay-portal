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

import {ClayVerticalNav} from '@clayui/nav';
import React, {useState} from 'react';

import WorkspaceConnection from './WorkspaceConnection';

const NAV_KEYS = {
	ATTRIBUTES: 'attributes',
	PEOPLE: 'people',
	PROPERTIES: 'properties',
	WORKSPACE_CONNECTION: 'workspaceConnection',
};

const DefaultPage: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const [activeNavKey, setActiveNavKey] = useState(
		NAV_KEYS.WORKSPACE_CONNECTION
	);

	return (
		<div className="d-flex">
			<div>
				<p className="text-uppercase">
					{Liferay.Language.get('instance-scope')}
				</p>

				<ClayVerticalNav
					items={[
						{
							active:
								activeNavKey === NAV_KEYS.WORKSPACE_CONNECTION,
							label: Liferay.Language.get('workspace-connection'),
							onClick: () =>
								setActiveNavKey(NAV_KEYS.WORKSPACE_CONNECTION),
						},
						{
							active: activeNavKey === NAV_KEYS.PROPERTIES,
							label: Liferay.Language.get('properties'),
							onClick: () => setActiveNavKey(NAV_KEYS.PROPERTIES),
						},
						{
							active: activeNavKey === NAV_KEYS.PEOPLE,
							label: Liferay.Language.get('people'),
							onClick: () => setActiveNavKey(NAV_KEYS.PEOPLE),
						},
						{
							active: activeNavKey === NAV_KEYS.ATTRIBUTES,
							label: Liferay.Language.get('attributes'),
							onClick: () => setActiveNavKey(NAV_KEYS.ATTRIBUTES),
						},
					]}
					large={false}
				/>
			</div>

			{activeNavKey === NAV_KEYS.WORKSPACE_CONNECTION && (
				<WorkspaceConnection />
			)}

			{activeNavKey === NAV_KEYS.PROPERTIES && <p>properties</p>}

			{activeNavKey === NAV_KEYS.PEOPLE && <p>people</p>}

			{activeNavKey === NAV_KEYS.ATTRIBUTES && <p>attributes</p>}
		</div>
	);
};

export default DefaultPage;
