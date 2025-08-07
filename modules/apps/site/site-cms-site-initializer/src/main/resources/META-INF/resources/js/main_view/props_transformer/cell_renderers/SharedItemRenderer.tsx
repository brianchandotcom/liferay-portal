/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {sub} from 'frontend-js-web';
import React, {useMemo} from 'react';

import formatActionURL from '../../../common/utils/formatActionURL';

const OBJECT_ENTRY_FOLDER_CLASSNAME =
	'com.liferay.object.model.ObjectEntryFolder';

interface ActionItem {
	data: {id: string};
	href?: string;
}

export default function SharedItemRenderer({
	actions,
	itemData,
	options,
	value,
}: {
	actions: ActionItem[];
	itemData: any;
	options: {actionId: string};
	value: string;
}) {
	const {fileTypeIcon, fileTypeIconColor, shareable, siteName} = itemData;

	const linkHref = useMemo(() => {
		const {actionId} = options;

		if (!actions.length || !actionId) {
			return null;
		}

		const isFolder = itemData?.className === OBJECT_ENTRY_FOLDER_CLASSNAME;
		const isUpdate = itemData?.actionIds?.includes('UPDATE');

		const resolvedActionId = isFolder
			? `${actionId}Folder`
			: isUpdate
				? `${actionId}Edit`
				: actionId;

		const selectedAction = actions.find(
			({data}) => data?.id === resolvedActionId
		);

		if (!selectedAction?.href) {
			return null;
		}

		return formatActionURL(itemData, selectedAction.href);
	}, [actions, itemData, options]);

	return (
		<span className="align-items-center c-gap-2 d-flex table-list-title">
			{itemData.fileTypeIcon && itemData.fileTypeIconColor && (
				<span className={fileTypeIconColor}>
					<ClayIcon aria-hidden="true" symbol={fileTypeIcon} />
				</span>
			)}

			{linkHref ? (
				<ClayLink aria-label={value} data-senna-off href={linkHref}>
					{value}
				</ClayLink>
			) : (
				<span>{value}</span>
			)}

			{shareable && (
				<ClayTooltipProvider>
					<span
						data-tooltip-align="top"
						title={sub(
							Liferay.Language.get('shared-from-x'),
							siteName
						)}
					>
						<ClayIcon className="text-secondary" symbol="users" />
					</span>
				</ClayTooltipProvider>
			)}
		</span>
	);
}
