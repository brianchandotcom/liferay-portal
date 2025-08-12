/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React from 'react';

import formatActionURL from '../../../common/utils/formatActionURL';

interface ActionItem {
	data: {id: string};
	href?: string;
}

export default function AssetRenderer({
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
	const {actionId} = options;

	if (!actions.length || !actionId) {
		return value ? <>{value}</> : null;
	}

	const selectedAction = actions.find(({data}) => data?.id === actionId);

	if (!selectedAction?.href) {
		return value ? <>{value}</> : null;
	}

	const formattedHref = formatActionURL(itemData, selectedAction.href);

	return (
		<div className="table-list-title">
			<ClayIcon className="ml-3 mr-3" symbol="document-default" />

			<ClayLink
				className="text-decoration-underline"
				data-senna-off
				href={formattedHref}
			>
				{value}
			</ClayLink>
		</div>
	);
}
