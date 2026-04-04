/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getFDSInternalRenderer} from '@liferay/frontend-data-set-web';
import React from 'react';

import type {IItemsActions} from '@liferay/frontend-data-set-web';

const ActionLinkRenderer = getFDSInternalRenderer('actionLink')?.component;

const ChatbotItemTitle = ({
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
	const statusLabel = itemData?.active
		? Liferay.Language.get('running')
		: Liferay.Language.get('stopped');

	const statusDisplayType = itemData?.active
		? 'label-success'
		: 'label-secondary';

	return (
		<div className="align-items-start d-flex flex-column">
			{ActionLinkRenderer ? (
				<ActionLinkRenderer
					actions={actions}
					itemData={itemData}
					itemId={itemId}
					options={{actionId: 'view'}}
					value={value}
				/>
			) : (
				<>{value}</>
			)}

			{itemData?.description && (
				<p className="list-group-subtext">{itemData.description}</p>
			)}

			<span className={`label ${statusDisplayType}`}>
				<span className="label-item label-item-expand">
					{statusLabel}
				</span>
			</span>
		</div>
	);
};

export default ChatbotItemTitle;
