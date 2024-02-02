import MetricBar from 'shared/components/MetricBar';
import React from 'react';
import Sticker from 'shared/components/Sticker';
import {
	ADD_ONS,
	DEFAULT_ADDONS,
	getPlanLabel,
	getPropIcon,
	getPropLabel,
	INDIVIDUALS,
	PAGEVIEWS,
	PLAN_TYPES,
	STATUS_DISPLAY_MAP
} from 'shared/util/subscriptions';
import {CUSTOM_DATE_FORMAT, formatDateToTimeZone} from 'shared/util/date';
import {get} from 'lodash';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {toRounded} from 'shared/util/numbers';

const METRIC_DEFINITION_MAP = {
	[INDIVIDUALS]: Liferay.Language.get(
		'contacts-with-a-known-email-address-that-are-synced-to-analytics-cloud'
	),
	[PAGEVIEWS]: Liferay.Language.get(
		'non-unique-visits-to-any-of-the-pages-synced-to-analytics-cloud'
	)
};

export const UsageMetric = ({
	className,
	currentPlan: {addOns, lastAnniversaryDate, metrics, name, startDate},
	metricType,
	planType,
	timeZoneId
}) => {
	const {count, limit, status} = metrics.get(metricType);
	const percent = limit > 0 ? count / limit : 0;

	const addOnQuantity = addOns.getIn([metricType, 'quantity'], 0);

	const addOnPlan = get(
		ADD_ONS,
		[metricType, planType],
		DEFAULT_ADDONS[metricType]
	);

	const addOnLimit = addOnPlan.limits[metricType];

	const actualPlanLimit = limit - addOnLimit * addOnQuantity;

	const planLabel = `${getPlanLabel(
		name
	)} ${actualPlanLimit.toLocaleString()}`;

	const addOnQuantityLabel = `+ ${addOnLimit.toLocaleString()} ${Liferay.Language.get(
		'add-on'
	)} (${addOnQuantity}x)`;

	return (
		<div className={className}>
			<h3 className='text-secondary'>{getPropLabel(metricType)}</h3>

			<div className='d-flex align-items-center'>
				<Sticker display='dark' symbol={getPropIcon(metricType)} />

				<div className='m-2'>
					<span className='mr-2'>
						{sub(
							Liferay.Language.get('x-of-x'),
							[
								<h2 className='m-0 d-inline' key={count}>
									{count.toLocaleString()}
								</h2>,
								<Text key={limit} size={3} weight='semi-bold'>
									{limit.toLocaleString()}
								</Text>
							],
							false
						)}
					</span>

					<Text color='secondary' size={3} weight='semi-bold'>
						{sub(Liferay.Language.get('x-percent-since-x'), [
							toRounded(percent * 100),
							formatDateToTimeZone(
								PLAN_TYPES[name] === 'basic'
									? startDate
									: lastAnniversaryDate,
								CUSTOM_DATE_FORMAT,
								timeZoneId
							)
						])}
					</Text>

					<div>
						<Text color='muted' size={3} weight='semi-bold'>
							{`${planLabel} ${addOnQuantityLabel}`}
						</Text>
					</div>
				</div>
			</div>

			<MetricBar display={STATUS_DISPLAY_MAP[status]} percent={percent} />

			<div className='metric-definition text-secondary'>
				{METRIC_DEFINITION_MAP[metricType]}
			</div>
		</div>
	);
};
