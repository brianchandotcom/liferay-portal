/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import {ViewDashboardContext} from '../ViewDashboardContext';
import {buildQueryString} from '../utils/buildQueryString';
import {fetchMetrics} from '../utils/fetchMetrics';
import {ActionsDropdown} from './ActionsDropdown';
import {BaseCard} from './BaseCard';
import {ContentAndFilesCard, TrendClassification} from './ContentAndFilesCard';
import {RangeSelectors, RangeSelectorsDropdown} from './RangeSelectorsDropdown';

interface IContent {
	categoriesCount: number;
	tagsCount: number;
	totalCount: number;
	trend: {
		classification: TrendClassification;
		percentage: number;
	};
	vocabulariesCount: number;
}

const CONTENT_PATH = '/o/analytics-cms-rest/v1.0/overview/content';

export function ContentCard() {
	const {
		filters: {languageId, spaceId},
	} = useContext(ViewDashboardContext);

	// TODO: Remove this exception after implementing links
	// eslint-disable-next-line @typescript-eslint/no-unused-vars
	const [action, setAction] = useState('');
	const [rangeSelector, setRangeSelector] = useState(
		RangeSelectors.Last7Days
	);
	const [metrics, setMetrics] = useState<IContent>();

	const queryParams = buildQueryString({
		languageId,
		rangeKey: rangeSelector,
		spaceId,
	});

	useEffect(() => {
		const endpoint = `${CONTENT_PATH}${queryParams}`;
		fetchMetrics(endpoint).then((payload) => setMetrics(payload));
	}, [queryParams]);

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
								icon: 'catalog',
								label: Liferay.Language.get('view-new-content'),
								value: 'viewNewContent',
							},
						]}
						onChange={setAction}
					/>
				</>
			}
			description={Liferay.Language.get(
				'this-metric-calculates-the-total-amount-of-content-assets-created-in-your-spaces'
			)}
			title={Liferay.Language.get('content')}
		>
			<ContentAndFilesCard
				categories={metrics?.categoriesCount ?? 0}
				tags={metrics?.tagsCount ?? 0}
				title={sub(Liferay.Language.get('x-new-content-items'), [
					metrics?.totalCount ?? 0,
				])}
				trend={{
					classification:
						metrics?.trend.classification ??
						TrendClassification.Neutral,
					percentage: metrics?.trend.percentage ?? 0,
				}}
				vocabularies={metrics?.vocabulariesCount ?? 0}
			/>
		</BaseCard>
	);
}
