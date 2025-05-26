/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';

import formatActionURL from '../../util/formatActionURL';

const OBJECT_ENTRY_FOLDER_CLASSNAME =
	'com.liferay.object.model.ObjectEntryFolder';

interface ActionItem {
	data: {id: string};
	href?: string;
}

export default function SimpleActionLinkRenderer({
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
	if (!actions || !actions.length || !options?.actionId) {
		return value ? <> {value} </> : null;
	}

	const actionId =
		itemData?.entryClassName === OBJECT_ENTRY_FOLDER_CLASSNAME
			? options?.actionId + 'Folder'
			: options?.actionId;

	const currentAction = actions.find(
		(action) => action.data?.id === actionId
	);

	if (!currentAction) {
		return null;
	}

	const formattedHref =
		currentAction.href && formatActionURL(itemData, currentAction.href);

	return (
		<div className="table-list-title">
			<ClayLink data-senna-off href={formattedHref}>
				{value}
			</ClayLink>
		</div>
	);
}
