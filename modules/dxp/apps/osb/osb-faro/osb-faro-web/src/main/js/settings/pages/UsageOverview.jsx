import AddOnsList from '../components/usage-overview/AddOnsList';
import Alert from 'shared/components/Alert';
import BasePage from 'settings/components/BasePage';
import Card from 'shared/components/Card';
import PlansList from '../components/usage-overview/PlansList';
import React from 'react';
import UsageMetric from '../components/usage-overview/UsageMetric';
import {compose, withCurrentUser, withProject} from 'shared/hoc';
import {
	formatPlanData,
	getPlanAddOns,
	getPropLabel,
	INDIVIDUALS,
	PAGEVIEWS,
	PLAN_TYPES,
	PLANS,
	STATUS_DISPLAY_MAP
} from 'shared/util/subscriptions';
import {Project, User} from 'shared/util/records';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';
import {sortBy} from 'lodash';
import {sub} from 'shared/util/lang';
import {SubscriptionStatuses} from 'shared/util/constants';

const PLAN_LEVEL_MAP = {
	[PLANS.basic.name]: 0,
	[PLANS.business.name]: 1,
	[PLANS.enterprise.name]: 2
};

const getPlans = ({name}) =>
	sortBy(PLANS, plan => PLAN_LEVEL_MAP[plan.name])
		.filter(plan => PLAN_LEVEL_MAP[plan.name] >= PLAN_LEVEL_MAP[name])
		.reverse();

const getAlertContent = (alert, currentUser) => {
	const admin = currentUser.isAdmin();

	switch (alert.statusCode) {
		case SubscriptionStatuses.Approaching:
			return {
				message: sub(
					admin
						? Liferay.Language.get(
								'the-current-plan-can-only-support-another-x-x.-please-contact-a-sales-representative-to-upgrade-the-plan'
						  )
						: Liferay.Language.get(
								'the-current-plan-can-only-support-another-x-x.-please-contact-your-site-administrator-to-upgrade-the-plan'
						  ),
					[
						<b key={alert.type}>
							{(alert.limit - alert.current).toLocaleString()}
						</b>,
						getPropLabel(alert.type).toLowerCase()
					],
					false
				),
				title: Liferay.Language.get('usage-limit-approaching')
			};
		case SubscriptionStatuses.Over:
			return {
				message: sub(
					admin
						? Liferay.Language.get(
								'the-current-plans-x-limit-has-been-exceeded.-please-contact-a-sales-representative-at-the-earliest-convenience'
						  )
						: Liferay.Language.get(
								'the-current-plans-x-limit-has-been-exceeded.-please-contact-your-site-administrator-at-the-earliest-convenience'
						  ),
					[
						<b key={alert.type}>
							{getPropLabel(alert.type).toLowerCase()}
						</b>
					],
					false
				),
				title: Liferay.Language.get('usage-limit-exceeded')
			};
		default:
			return '';
	}
};

export class UsageOverview extends React.Component {
	static propTypes = {
		currentUser: PropTypes.instanceOf(User).isRequired,
		groupId: PropTypes.string.isRequired,
		project: PropTypes.instanceOf(Project).isRequired
	};

	renderAlerts() {
		const {
			currentUser,
			project: {faroSubscription}
		} = this.props;

		const {metrics} = formatPlanData(faroSubscription);

		const individuals = metrics.get('individuals');
		const pageViews = metrics.get('pageViews');

		const noAlerts =
			individuals.status === SubscriptionStatuses.Ok &&
			pageViews.status === SubscriptionStatuses.Ok;

		const alerts = [
			{
				current: individuals.count,
				limit: individuals.limit,
				statusCode: individuals.status,
				type: INDIVIDUALS
			},
			{
				current: pageViews.count,
				limit: pageViews.limit,
				statusCode: pageViews.status,
				type: PAGEVIEWS
			}
		].filter(alert => alert.statusCode > SubscriptionStatuses.Ok);

		return noAlerts ? null : (
			<div>
				{alerts.map(alert => {
					const alertContent = getAlertContent(alert, currentUser);

					return (
						<Alert
							iconSymbol='exclamation-full'
							key={alert.type}
							title={alertContent.title}
							type={STATUS_DISPLAY_MAP[alert.statusCode]}
						>
							{alertContent.message}
						</Alert>
					);
				})}
			</div>
		);
	}

	render() {
		const {
			currentUser,
			groupId,
			project: {faroSubscription, timeZone}
		} = this.props;

		const currentPlan = formatPlanData(faroSubscription);
		const timeZoneId = timeZone.get('timeZoneId');

		const showAddonPanels =
			PLAN_LEVEL_MAP[currentPlan.name] >=
			PLAN_LEVEL_MAP[PLANS.business.name];

		const planType =
			PLAN_TYPES[currentPlan.name] || PLAN_TYPES[PLANS.basic.name];

		const availableAddOns = !!getPlanAddOns(planType).filter(Boolean)
			.length;

		return (
			<BasePage
				backURL={toRoute(Routes.SETTINGS_ADD_DATA_SOURCE, {
					groupId
				})}
				className={`usage-overview-page-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
				groupId={groupId}
				key='UsageOverview'
				pageTitle={Liferay.Language.get('current-usage-limits')}
			>
				<div className='row'>
					<div className='col-xl-8'>
						<div className='text-secondary'>
							<p>
								<b>
									{Liferay.Language.get(
										'plans-are-limited-by-the-total-amount-of-individuals-and-page-views'
									)}
								</b>
							</p>

							<p>
								{Liferay.Language.get(
									'when-either-limit-is-exceeded-the-current-plan-will-either-have-to-be-upgraded-or-add-ons-will-have-to-be-purchased-to-accommodate-the-overage'
								)}
							</p>
						</div>

						{this.renderAlerts()}

						<Card>
							<Card.Body>
								<UsageMetric
									currentPlan={currentPlan}
									metricType={INDIVIDUALS}
									planType={planType}
									timeZoneId={timeZoneId}
								/>

								<UsageMetric
									currentPlan={currentPlan}
									metricType={PAGEVIEWS}
									planType={planType}
									timeZoneId={timeZoneId}
								/>
							</Card.Body>
						</Card>
					</div>
					<div className='col-xl-4 plans'>
						{availableAddOns && (
							<>
								<div>
									<h4>{Liferay.Language.get('plans')}</h4>

									<div className='text-secondary'>
										<p>
											{Liferay.Language.get(
												'balance-pricing-and-value.-higher-tiers-offer-more-flexibility-through-cheaper-add-ons-and-higher-limits'
											)}
										</p>
									</div>

									<PlansList
										currentPlanName={currentPlan.name}
										plans={getPlans(currentPlan)}
									/>
								</div>

								<div>
									<h4>{Liferay.Language.get('add-ons')}</h4>

									<div className='text-secondary'>
										<p>
											{Liferay.Language.get(
												'tailor-limits-to-business-needs.-incrementally-increase-individual-or-page-view-limits-as-needed-without-committing-to-a-new-plan'
											)}
										</p>
									</div>

									{!showAddonPanels && (
										<div className='text-secondary'>
											<strong>
												<p>
													{currentUser.isAdmin()
														? Liferay.Language.get(
																'add-ons-are-not-available-on-the-basic-plan.-to-increase-usage-limits-please-contact-a-sales-representative'
														  )
														: Liferay.Language.get(
																'add-ons-are-not-available-on-the-basic-plan.-to-increase-usage-limits-please-contact-your-site-administrator'
														  )}
												</p>
											</strong>
										</div>
									)}

									<AddOnsList
										active={showAddonPanels}
										currentPlan={currentPlan}
										planType={planType}
									/>
								</div>
							</>
						)}
					</div>
				</div>
			</BasePage>
		);
	}
}

export default compose(withCurrentUser, withProject)(UsageOverview);
