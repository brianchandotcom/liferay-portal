import React from 'react';
import {Colors} from 'shared/util/charts';
import {CurrentUsage} from './CurrentUsage';
import {STATUS_DISPLAY_MAP} from 'shared/util/subscriptions';
import {sub} from 'shared/util/lang';
import {UsageMetric} from './UsageMetric';

export const PageViewsSession = ({currentPlan}) => {
	const {count, limit, status} = currentPlan.metrics.get('pageViews');

	return (
		<UsageMetric
			description={Liferay.Language.get(
				'non-unique-visits-to-any-of-the-pages-synced-to-analytics-cloud'
			)}
			title={Liferay.Language.get('page-views')}
		>
			<CurrentUsage
				count={count}
				items={{
					itemA: {
						color: Colors[STATUS_DISPLAY_MAP[status]],
						label: Liferay.Language.get('page-views'),
						value: count
					}
				}}
				legendText={sub(
					Liferay.Language.get('x-page-views-remaining'),
					[(limit - count).toLocaleString()]
				)}
				limit={limit}
			/>
		</UsageMetric>
	);
};
