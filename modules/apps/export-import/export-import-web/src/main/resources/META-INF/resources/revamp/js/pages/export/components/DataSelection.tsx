/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

import SectionHeader from '../../../components/SectionHeader';
import {DateFilterValues} from '../../../components/date_filter';
import CheckboxSheet from '../../../components/forms/CheckboxSheet';
import {
	FormikFieldContentSelector,
	FormikFieldDateFilter,
} from '../../../components/forms/formik';
import {PreviewPortletDataHandlerSection} from '../../../types/portletDataHandler';
import {PageTreeModalConfiguration} from './PageTreeModal';

const LABEL_ID = 'dataSelection-label';

export default function DataSelection({
	commentsAndRatingsEnabled = false,
	deletionCount = 0,
	itemsCount,
	loading = false,
	lookAndFeelEnabled = false,
	onApplyFilter,
	pageTreeModalConfiguration,
	sections,
}: {
	commentsAndRatingsEnabled?: boolean;
	deletionCount?: number;
	itemsCount?: number;
	loading?: boolean;
	lookAndFeelEnabled?: boolean;
	onApplyFilter: (filterValues: DateFilterValues) => void;
	pageTreeModalConfiguration: PageTreeModalConfiguration;
	sections: PreviewPortletDataHandlerSection[];
}) {
	return (
		<>
			<SectionHeader
				className="mt-4"
				id={LABEL_ID}
				subtitle={Liferay.Language.get(
					'select-and-filter-the-data-you-want-to-include-in-your-export'
				)}
				title={Liferay.Language.get('data-selection')}
			/>

			<ClayLayout.Sheet>
				<FormikFieldDateFilter
					itemsCount={itemsCount}
					name="dateFilter"
					onApplyFilter={onApplyFilter}
				/>
			</ClayLayout.Sheet>

			{deletionCount > 0 && (
				<CheckboxSheet
					description={Liferay.Language.get('deletions-help-export')}
					label={Liferay.Language.get('export-individual-deletions')}
					name="deletions"
					title={Liferay.Language.get('deletions')}
				/>
			)}

			<CheckboxSheet
				description={Liferay.Language.get(
					'export-import-permissions-help'
				)}
				label={Liferay.Language.get('export-permissions')}
				name="permissions"
				title={Liferay.Language.get('permissions')}
			/>

			<div className="sr-only" role="status">
				{loading
					? Liferay.Language.get('loading')
					: Liferay.Language.get('loaded')}
			</div>

			<div aria-busy={loading} data-testid="data-selection-section">
				{loading ? (
					<ClayLoadingIndicator className="mb-9 mt-8" />
				) : (
					<FormikFieldContentSelector
						aria-labelledby={LABEL_ID}
						commentsAndRatingsEnabled={commentsAndRatingsEnabled}
						lookAndFeelEnabled={lookAndFeelEnabled}
						name="contentSelection"
						pageTreeModalConfiguration={pageTreeModalConfiguration}
						sections={sections}
					/>
				)}
			</div>
		</>
	);
}
