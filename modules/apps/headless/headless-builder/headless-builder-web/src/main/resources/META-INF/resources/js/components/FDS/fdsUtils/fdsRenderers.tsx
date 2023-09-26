/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {Dispatch, SetStateAction} from 'react';

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
	fdsItem,
	setMainEndpointNav,
}: {
	fdsItem: FDSItem<APIEndpointItem>;
	setMainEndpointNav: Dispatch<SetStateAction<MainNav>>;
}) {
	const path = wrapStringInForwardSlashes(fdsItem.itemData.path);

	return (
		<ClayTooltipProvider>
			<div className="table-list-title">
				<ClayLink
					data-senna-off
					data-tooltip-align="top"
					decoration="none"
					href="#"
					onClick={() =>
						setMainEndpointNav({edit: fdsItem.itemData.id})
					}
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
