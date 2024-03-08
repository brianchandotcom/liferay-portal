import moment from 'moment';
import React from 'react';
import {Colors} from 'shared/util/charts';
import {CurrentUsage} from './CurrentUsage';
import {CUSTOM_DATE_FORMAT, formatDateToTimeZone} from 'shared/util/date';
import {STATUS_DISPLAY_MAP} from 'shared/util/subscriptions';
import {sub} from 'shared/util/lang';
import {UsageMetric} from './UsageMetric';
import {useTimeZone} from 'shared/hooks/useTimeZone';

export const PageViewsSession = ({currentPlan}) => {
	const {timeZoneId} = useTimeZone();
	const {count, limit, status} = currentPlan.metrics.get('pageViews');
	const available = limit - count;

	return (
		<UsageMetric
			description={
				sub(
					Liferay.Language.get(
						'total-page-views-have-been-tracked-by-analytics-cloud-since-x'
					),
					[
						formatDateToTimeZone(
							moment(currentPlan.startDate),
							CUSTOM_DATE_FORMAT,
							timeZoneId
						)
					]
				) as string
			}
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
					Liferay.Language.get('x-page-views-are-available'),
					[(available > 0 ? available : 0).toLocaleString()]
				)}
				limit={limit}
				percentageText={percentage =>
					Number(percentage) === 1
						? Liferay.Language.get('1-page-view-was-used')
						: Liferay.Language.get('x-page-views-were-used')
				}
			/>
		</UsageMetric>
	);
};
