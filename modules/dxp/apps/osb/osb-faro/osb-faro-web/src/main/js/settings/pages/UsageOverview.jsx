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
	INDIVIDUALS,
	PAGEVIEWS,
	PLAN_TYPES,
	PLANS
} from 'shared/util/subscriptions';
import {Project, User} from 'shared/util/records';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';
import {sortBy} from 'lodash';
import {SubscriptionStatuses} from 'shared/util/constants';

const PLAN_LEVEL_MAP = {
	[PLANS.basic.name]: 0,
	[PLANS.business.name]: 1,
	[PLANS.enterprise.name]: 2
};

const LXC_PLAN_LEVEL_MAP = {
	[PLANS.lxcPro.name]: 0,
	[PLANS.lxcBusiness.name]: 1,
	[PLANS.lxcEnterprise.name]: 2
};

function isLxcPlan(name) {
	if (
		name === PLANS.lxcBusiness.name ||
		name === PLANS.lxcEnterprise.name ||
		name === PLANS.lxcPro.name
	) {
		return true;
	}

	return false;
}

const filterPlans = (currentPlanName, nextPlanName) => {
	if (isLxcPlan(currentPlanName)) {
		return (
			LXC_PLAN_LEVEL_MAP[nextPlanName] >=
			LXC_PLAN_LEVEL_MAP[currentPlanName]
		);
	}

	return PLAN_LEVEL_MAP[nextPlanName] >= PLAN_LEVEL_MAP[currentPlanName];
};

const getPlans = ({name}) =>
	sortBy(PLANS, plan =>
		isLxcPlan(name)
			? LXC_PLAN_LEVEL_MAP[plan.name]
			: PLAN_LEVEL_MAP[plan.name]
	)
		.filter(plan => filterPlans(name, plan.name))
		.reverse();

const getAlertContent = (alertStatusCode, currentUser) => {
	const admin = currentUser.isAdmin();

	switch (alertStatusCode) {
		case SubscriptionStatuses.Approaching:
			return {
				message: admin
					? Liferay.Language.get(
							'usage-limit-is-approaching.-please-contact-your-sales-representative-at-the-earliest-convenience'
					  )
					: Liferay.Language.get(
							'usage-limit-is-approaching.-please-contact-your-workspace-administrator-at-the-earliest-convenience'
					  ),
				title: Liferay.Language.get('alert')
			};
		case SubscriptionStatuses.Over:
			return {
				message: admin
					? Liferay.Language.get(
							'usage-limit-exceeded.-please-contact-your-sales-representative-to-upgrade-the-plan'
					  )
					: Liferay.Language.get(
							'usage-limit-exceeded.-please-contact-your-workspace-administrator-to-upgrade-the-plan'
					  ),
				title: Liferay.Language.get('alert')
			};
		default:
			return null;
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

		let alertStatusCode = individuals.status;

		if (individuals.status !== pageViews.status) {
			if (individuals.status !== SubscriptionStatuses.Ok) {
				alertStatusCode = individuals.status;
			}

			if (
				individuals.status === SubscriptionStatuses.Ok ||
				pageViews.status === SubscriptionStatuses.Over
			) {
				alertStatusCode = pageViews.status;
			}
		}

		const alertContent = getAlertContent(alertStatusCode, currentUser);

		return alertContent ? (
			<div>
				<Alert
					iconSymbol='exclamation-full'
					title={alertContent.title}
					type='warning'
				>
					{alertContent.message}
				</Alert>
			</div>
		) : null;
	}

	render() {
		const {
			currentUser,
			groupId,
			project: {faroSubscription, timeZone}
		} = this.props;

		const currentPlan = formatPlanData(faroSubscription);
		const timeZoneId = timeZone.get('timeZoneId');

		const showAddonPanels = filterPlans(
			PLANS.business.name,
			currentPlan.name
		);

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

									<PlansList
										currentPlanName={currentPlan.name}
										plans={getPlans(currentPlan)}
										workspaceBirthday={
											currentPlan.startDate
										}
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
