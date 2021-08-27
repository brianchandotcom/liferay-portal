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

import ClayDatePicker from '@clayui/date-picker';
import {
	getCollectionFilterValue,
	setCollectionFilterValue,
} from '@liferay/fragment-renderer-collection-filter-impl';
import React from 'react';

export default function FragmentCollectionFilterDate({
	date,
	fragmentEntryLinkId,
	isDisabled,
}) {
	const value = getCollectionFilterValue(date, fragmentEntryLinkId);

	return (
		<ClayDatePicker
			disabled={isDisabled}
			onValueChange={(value) =>
				setCollectionFilterValue(date, fragmentEntryLinkId, value)
			}
			placeholder="YYYY-MM-DD"
			value={value}
		/>
	);
}
