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

export function itemPathRenderer({
	itemData,
}: FDSItem<APIApplicationEndpointItem>) {
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
