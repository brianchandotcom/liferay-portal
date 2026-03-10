/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import DateFilter from '../../../components/DateFilter';

export default function DataSelectionStep({
	onApplyFilter,
}: {
	onApplyFilter?: (filterValues: any) => void;
}) {
	return (
		<>
			<ClayLayout.Sheet>
				<DateFilter onApplyFilter={onApplyFilter} />
			</ClayLayout.Sheet>

			<ClayLayout.Sheet>
				{Liferay.Language.get('portlets')}
			</ClayLayout.Sheet>
		</>
	);
}
