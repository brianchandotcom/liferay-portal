/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import FDSItemTitle from '../components/FDSItemTitle';

import type {IItemsActions} from '@liferay/frontend-data-set-web';

interface IProps {
	actions: IItemsActions[];
	itemData: {active?: boolean; description?: string} | null | undefined;
	itemId: unknown;
	value: unknown;
}

const ChatbotItemTitle = ({actions, itemData, itemId, value}: IProps) => {
	const statusLabel = itemData?.active
		? Liferay.Language.get('running')
		: Liferay.Language.get('stopped');

	const statusDisplayType = itemData?.active
		? 'label-success'
		: 'label-secondary';

	return (
		<div className="align-items-start d-flex flex-column">
			<FDSItemTitle
				actions={actions}
				itemData={itemData}
				itemId={itemId}
				value={value}
			/>

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
