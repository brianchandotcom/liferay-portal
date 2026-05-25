/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import React from 'react';

import FDSItemTitle from '../components/FDSItemTitle';

import type {IItemsActions} from '@liferay/frontend-data-set-web';

interface IProps {
	actions: IItemsActions[];
	itemData: {description?: string; system?: boolean} | null | undefined;
	itemId: unknown;
	value: unknown;
}

const InstructionDefinitionItemTitle = ({
	actions,
	itemData,
	itemId,
	value,
}: IProps) => {
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

			{itemData?.system && (
				<ClayLabel className="mt-1" displayType="info" inverse>
					{Liferay.Language.get('provided-by-liferay')}
				</ClayLabel>
			)}
		</div>
	);
};

export default InstructionDefinitionItemTitle;
