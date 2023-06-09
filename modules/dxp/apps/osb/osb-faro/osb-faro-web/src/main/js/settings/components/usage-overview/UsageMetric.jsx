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
	PLANS,
	STATUS_DISPLAY_MAP
} from 'shared/util/subscriptions';
import {formatDateToTimeZone} from 'shared/util/date';
import {get} from 'lodash';
import {Plan} from 'shared/util/records';
import {PropTypes} from 'prop-types';
import {sub} from 'shared/util/lang';
import {toRounded} from 'shared/util/numbers';

const METRIC_DEFINITION_MAP = {
	[INDIVIDUALS]: Liferay.Language.get(
		'contacts-with-a-known-email-address-that-are-synced-to-analytics-cloud'
	),
	[PAGEVIEWS]: Liferay.Language.get(
		'non-unique-visits-to-any-of-the-pages-synced-to-analytics-cloud'
	)
};

export default class UsageMetric extends React.Component {
	static propTypes = {
		currentPlan: PropTypes.instanceOf(Plan),
		metricType: PropTypes.oneOf([INDIVIDUALS, PAGEVIEWS]),
		planType: PropTypes.oneOf([
			PLAN_TYPES[PLANS.basic.name],
			PLAN_TYPES[PLANS.business.name],
			PLAN_TYPES[PLANS.enterprise.name]
		]),
		timeZoneId: PropTypes.string
	};

	getUsageMetricDetails() {
		const {
			currentPlan: {addOns: planAddOns, metrics, name},
			metricType,
			planType
		} = this.props;

		const limit = metrics.getIn([metricType, 'limit']);

		const addOnQuantity = planAddOns.getIn([metricType, 'quantity'], 0);

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

		return `${planLabel} ${addOnQuantityLabel}`;
	}

	render() {
		const {
			currentPlan: {lastAnniversaryDate, metrics},
			metricType,
			timeZoneId
		} = this.props;

		const {count, limit, status} = metrics.get(metricType);

		const percent = limit > 0 ? count / limit : 0;

		return (
			<div
				className={`overview-usage-metric-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				<h3 className='metric-name'>{getPropLabel(metricType)}</h3>

				<div className='metric-breakdown'>
					<Sticker display='dark' symbol={getPropIcon(metricType)} />

					<div className='metric-breakdown-content'>
						<div>
							<span>
								{sub(
									Liferay.Language.get('x-of-x'),
									[
										<span
											className='metric-current'
											key={count}
										>
											{count.toLocaleString()}
										</span>,
										<span
											className='metric-limit'
											key={limit}
										>
											{limit.toLocaleString()}
										</span>
									],
									false
								)}
							</span>

							<span className='text-secondary usage-since-label semibold'>
								{sub(
									Liferay.Language.get('x-percent-since-x'),
									[
										toRounded(percent * 100),
										formatDateToTimeZone(
											lastAnniversaryDate,
											'MMMM D, YYYY',
											timeZoneId
										)
									]
								)}
							</span>
						</div>

						<div className='text-muted small semibold'>
							{this.getUsageMetricDetails()}
						</div>
					</div>
				</div>

				<MetricBar
					display={STATUS_DISPLAY_MAP[status]}
					percent={percent}
				/>

				<div className='metric-definition text-secondary'>
					{METRIC_DEFINITION_MAP[metricType]}
				</div>
			</div>
		);
	}
}
