/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getFDSInternalRenderer} from '@liferay/frontend-data-set-web';
import React from 'react';

import type {IItemsActions} from '@liferay/frontend-data-set-web';

const ActionLinkRenderer = getFDSInternalRenderer('actionLink')?.component;

const AgentDefinitionItemTitle = ({
	actions,
	itemData,
	itemId,
	value,
}: {
	actions: IItemsActions[];
	itemData: any;
	itemId: any;
	value: unknown;
}) => {
	return ActionLinkRenderer ? (
		<ActionLinkRenderer
			actions={actions}
			itemData={itemData}
			itemId={itemId}
			options={{actionId: 'view'}}
			value={value}
		/>
	) : (
		<>{value}</>
	);
};

export default AgentDefinitionItemTitle;
