/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDS_INTERNAL_CELL_RENDERERS} from '@liferay/frontend-data-set-web';
import {CurrencyUtils} from 'commerce-frontend-js';
import {navigate} from 'frontend-js-web';
import React from 'react';

const ActionLinkRenderer =
	FDS_INTERNAL_CELL_RENDERERS.find(({name}) => name === 'actionLink')
		?.component ?? null;

export function wipeCurrencyAndNavigate({cartId, orderDetailURL}) {
	CurrencyUtils.resetCommerceCurrency();

	navigate(`${orderDetailURL}${cartId}`);
}

const PendingOrderIdDataRenderer = ({orderDetailURL, ...props}) => {
	return (
		<div
			onClick={(event) => {
				event.preventDefault();

				wipeCurrencyAndNavigate({
					cartId: props.itemId,
					orderDetailURL,
				});
			}}
		>
			<ActionLinkRenderer {...props} />
		</div>
	);
};

export default PendingOrderIdDataRenderer;
