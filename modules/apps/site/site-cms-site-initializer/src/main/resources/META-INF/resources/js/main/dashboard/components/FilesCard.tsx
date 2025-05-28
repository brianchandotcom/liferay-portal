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

export function FilesCard() {
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
								href: generateUrl('/files').toString(),
								label: Liferay.Language.get('view-all-files'),
								value: 'view-all-files',
							},
						]}
					/>
				</>
			}
			description={Liferay.Language.get(
				'this-metric-calculates-the-total-amount-of-files-created-in-your-spaces'
			)}
			title={Liferay.Language.get('files')}
		>
			<ContentAndFilesCard
				endpointURL="/o/analytics-cms-rest/v1.0/file-overview"
				rangeSelector={rangeSelector}
				title={(totalCount) =>
					sub(
						totalCount === 1
							? Liferay.Language.get('x-new-file')
							: Liferay.Language.get('x-new-files'),
						[totalCount]
					)
				}
			/>
		</BaseCard>
	);
}
