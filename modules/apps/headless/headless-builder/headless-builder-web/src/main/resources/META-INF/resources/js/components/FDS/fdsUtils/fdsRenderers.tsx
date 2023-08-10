/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

import StatusLabel from '../../StatusLabel';
import {wrapStringInForwardSlashes} from '../../utils/string';

export function itemMethodRenderer({
	itemData,
}: {
	itemData: {httpMethod: {name: string}};
}) {
	return <ClayLabel displayType="info">{itemData.httpMethod.name}</ClayLabel>;
}

export function itemPathRenderer({itemData}: FDSItem<APIEndpointItem>) {
	const path = wrapStringInForwardSlashes(itemData.path);

	return (
		<ClayTooltipProvider>
			<div className="table-list-title">
				<ClayLink
					data-senna-off
					data-tooltip-align="top"
					decoration="none"
					href="#"
					title={path}
				>
					{path}
				</ClayLink>
			</div>
		</ClayTooltipProvider>
	);
}

export function itemStatusRenderer({itemData}: FDSItem<APIApplicationItem>) {
	return <StatusLabel statusKey={itemData.applicationStatus?.key} />;
}

export function itemURLRenderer({itemData}: FDSItem<APIApplicationItem>) {
	return wrapStringInForwardSlashes(itemData.baseURL);
}
