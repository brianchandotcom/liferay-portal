/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {dateUtils, sub} from 'frontend-js-web';
import React from 'react';

import formatActionURL from '../../../common/utils/formatActionURL';

interface ActionItem {
	data: {id: string};
	href?: string;
}

const formatDate = (date: string) => {
	return dateUtils.format(new Date(date), 'P p');
};

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
		<div className="d-flex">
			<ClayIcon className="m-3" symbol="document-default" />

			<div className="">
				<div className="table-list-title">
					<ClayLink
						className="text-decoration-underline"
						data-senna-off
						href={formattedHref}
					>
						<div>{value}</div>
					</ClayLink>
				</div>

				<div className="text-2 text-muted">
					{sub(
						Liferay.Language.get('modified-at-x-by-x'),
						formatDate(itemData.dateModified),
						itemData.embedded.creator.name
					)}
				</div>
			</div>
		</div>
	);
}
