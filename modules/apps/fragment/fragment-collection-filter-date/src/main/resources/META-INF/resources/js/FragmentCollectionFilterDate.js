/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
