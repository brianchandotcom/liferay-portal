/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import React, {useState} from 'react';

export default function PaginationBar({
	activeDelta,
	activePage,
	componentId: _componentId,
	cssClass,
	deltas,
	disabledPages,
	ellipsisBuffer,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	showDeltasDropDown,
	totalItems,
	...otherProps
}) {
	const [initialActiveDelta, setInitialActiveDelta] = useState(
		activeDelta ?? deltas[0].label
	);
	const [initialActivePage, setInitialActivePage] = useState(activePage ?? 1);

	const initialEllipsisBuffer = ellipsisBuffer ?? 2;

	return (
		<ClayPaginationBarWithBasicItems
			activeDelta={initialActiveDelta}
			activePage={initialActivePage}
			className={cssClass}
			deltas={deltas}
			disabledPages={disabledPages ?? []}
			ellipsisBuffer={initialEllipsisBuffer}
			onDeltaChange={setInitialActiveDelta}
			onPageChange={setInitialActivePage}
			showDeltasDropDown={showDeltasDropDown}
			totalItems={totalItems}
			{...otherProps}
		/>
	);
}
