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
