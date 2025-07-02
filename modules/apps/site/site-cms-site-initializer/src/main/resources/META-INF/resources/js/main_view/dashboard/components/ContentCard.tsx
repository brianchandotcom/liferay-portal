/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {generateUrl} from '../utils/urls';
import {ActionsDropdown} from './ActionsDropdown';
import {BaseCard} from './BaseCard';
import {ContentAndFilesCard} from './ContentAndFilesCard';
import {
	RangeSelector,
	RangeSelectors,
	RangeSelectorsDropdown,
} from './RangeSelectorsDropdown';

export function ContentCard() {
	const [rangeSelector, setRangeSelector] = useState<RangeSelector>({
		rangeEnd: '',
		rangeKey: RangeSelectors.Last7Days,
		rangeStart: '',
	});

	return (
		<BaseCard
			Preferences={
				<>
					<RangeSelectorsDropdown
						activeRangeSelector={rangeSelector}
						className="mr-3"
						onChange={setRangeSelector}
					/>

					<ActionsDropdown
						items={[
							{
								href: generateUrl('/contents').toString(),
								label: Liferay.Language.get('view-all-content'),
								value: 'view-all-content',
							},
						]}
					/>
				</>
			}
			description={Liferay.Language.get(
				'this-metric-calculates-the-total-amount-of-content-assets-created-in-your-spaces'
			)}
			title={Liferay.Language.get('content')}
		>
			<ContentAndFilesCard
				endpointURL="/o/analytics-cms-rest/v1.0/content-overview"
				rangeSelector={rangeSelector}
				title={(totalCount) =>
					sub(
						totalCount === 1
							? Liferay.Language.get('x-new-content-item')
							: Liferay.Language.get('x-new-content-items'),
						[totalCount]
					)
				}
			/>
		</BaseCard>
	);
}
