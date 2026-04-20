/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import DateFilter, {DateFilterValues} from '../../../components/date_filter';
import {FormikFieldContentSelector} from '../../../components/forms/formik';
import {PortletDataHandlerSection} from '../../../utils/mockPortletDataHandlerSections';

export default function DataSelectionStep({
	itemsCount = 0,
	onApplyFilter,
	sections,
}: {
	itemsCount?: number;
	onApplyFilter?: (filterValues: DateFilterValues) => void;
	sections: PortletDataHandlerSection[];
}) {
	return (
		<>
			<ClayLayout.Sheet>
				<DateFilter
					itemsCount={itemsCount}
					onApplyFilter={onApplyFilter}
				/>
			</ClayLayout.Sheet>

			<FormikFieldContentSelector
				name="contentSelection"
				sections={sections}
			/>
		</>
	);
}
