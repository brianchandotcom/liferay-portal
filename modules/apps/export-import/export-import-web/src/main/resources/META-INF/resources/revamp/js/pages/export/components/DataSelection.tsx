/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

import DateFilter, {DateFilterValues} from '../../../components/date_filter';
import {FormikFieldContentSelector} from '../../../components/forms/formik';
import {PortletDataHandlerSection} from '../../../types/portletDataHandlerSection';

const LABEL_ID = 'dataSelection-label';

export default function DataSelection({
	itemsCount,
	loading = false,
	onApplyFilter,
	sections,
}: {
	itemsCount?: number;
	loading?: boolean;
	onApplyFilter?: (filterValues: DateFilterValues) => void;
	sections: PortletDataHandlerSection[];
}) {
	return (
		<>
			<header className="mb-1 mt-5 sheet-header">
				<div className="mb-1 sheet-title" id={LABEL_ID}>
					{Liferay.Language.get('data-selection')}
				</div>

				<p className="sheet-text text-secondary">
					{Liferay.Language.get(
						'select-and-filter-the-data-you-want-to-include-in-your-export'
					)}
				</p>
			</header>

			<ClayLayout.Sheet>
				<DateFilter
					itemsCount={itemsCount}
					onApplyFilter={onApplyFilter}
				/>
			</ClayLayout.Sheet>

			<div data-testid="data-selection-section">
				{loading ? (
					<ClayLoadingIndicator className="mb-9 mt-8" />
				) : (
					<FormikFieldContentSelector
						aria-labelledby={LABEL_ID}
						name="contentSelection"
						sections={sections}
					/>
				)}
			</div>
		</>
	);
}
