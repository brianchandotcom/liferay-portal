import BasePage from 'settings/components/BasePage';
import Card from 'shared/components/Card';
import ClayAlert from '@clayui/alert';
import ClayLayout from '@clayui/layout';
import moment from 'moment';
import React, {useState} from 'react';
import {AlertTypes} from 'shared/components/Alert';
import {compose, withCurrentUser, withProject} from 'shared/hoc';
import {CUSTOM_DATE_FORMAT, formatDateToTimeZone} from 'shared/util/date';
import {
	formatPlanData,
	INDIVIDUALS,
	isBasicPlan,
	PAGEVIEWS,
	PLAN_TYPES,
	PLANS
} from 'shared/util/subscriptions';
import {sub} from 'shared/util/lang';
import {SubscriptionDetails} from 'settings/components/usage-overview/SubscriptionDetails';
import {SubscriptionStatuses} from 'shared/util/constants';
import {Text} from '@clayui/core';
import {UsageMetric} from '../components/usage-overview/UsageMetric';

const subscriptionStatuses = admin => ({
	[SubscriptionStatuses.Approaching]: {
		message: admin
			? Liferay.Language.get(
					'usage-limit-is-approaching.-please-contact-your-sales-representative-at-the-earliest-convenience'
			  )
			: Liferay.Language.get(
					'usage-limit-is-approaching.-please-contact-your-workspace-administrator-at-the-earliest-convenience'
			  ),
		title: Liferay.Language.get('alert')
	},
	[SubscriptionStatuses.Over]: {
		message: admin
			? Liferay.Language.get(
					'usage-limit-exceeded.-please-contact-your-sales-representative-to-upgrade-the-plan'
			  )
			: Liferay.Language.get(
					'usage-limit-exceeded.-please-contact-your-workspace-administrator-to-upgrade-the-plan'
			  ),
		title: Liferay.Language.get('alert')
	}
});

const getAlertStatusCode = currentPlan => {
	const individuals = currentPlan.metrics.get('individuals');
	const pageViews = currentPlan.metrics.get('pageViews');

	if (individuals.status !== pageViews.status) {
		if (individuals.status !== SubscriptionStatuses.Ok) {
			return individuals.status;
		}

		if (
			individuals.status === SubscriptionStatuses.Ok ||
			pageViews.status === SubscriptionStatuses.Over
		) {
			return pageViews.status;
		}
	}

	return null;
};

export const UsageOverview = ({currentUser, groupId, project}) => {
	const [showAlert, setShowAlert] = useState(true);

	const admin = currentUser.isAdmin();
	const currentPlan = formatPlanData(project.faroSubscription);
	const timeZoneId = project.timeZone.get('timeZoneId');
	const planType =
		PLAN_TYPES[currentPlan.name] || PLAN_TYPES[PLANS.basic.name];

	let pageActions = [];

	if (currentUser.isAdmin()) {
		pageActions = [
			{
				displayType: 'primary',
				href: 'https://support.liferay.com/',
				icon: {
					symbol: 'shortcut'
				},
				label: Liferay.Language.get('manage-subscriptions'),
				target: '_blank'
			}
		];
	}

	const alertContent = subscriptionStatuses(admin)[
		getAlertStatusCode(currentPlan)
	];

	return (
		<BasePage
			groupId={groupId}
			key='UsageOverview'
			pageActions={pageActions}
			pageDescription={Liferay.Language.get(
				'plans-are-limited-by-the-total-amount-of-individuals-and-page-views'
			)}
			pageTitle={Liferay.Language.get('subscription-&-usage')}
		>
			{showAlert && alertContent && (
				<ClayLayout.Row>
					<ClayLayout.Col xl={12}>
						<ClayAlert
							displayType={AlertTypes.Warning}
							onClose={() => setShowAlert(false)}
							title={alertContent.title}
						>
							{alertContent.message}
						</ClayAlert>
					</ClayLayout.Col>
				</ClayLayout.Row>
			)}

			<ClayLayout.Row>
				<ClayLayout.Col xl={8}>
					<Card>
						<Card.Header>
							<Card.Title>
								{Liferay.Language.get('usage-limits')}
							</Card.Title>
						</Card.Header>

						<Card.Body>
							<p>
								<Text color='secondary' size={3}>
									{Liferay.Language.get(
										'when-either-limit-is-exceeded-the-current-plan-will-either-have-to-be-upgraded-or-add-ons-will-have-to-be-purchased-to-accommodate-the-overage'
									)}
								</Text>
							</p>

							{!isBasicPlan(currentPlan) && (
								<p data-testid='next-anniversary-date'>
									<Text color='secondary' size={3}>
										{sub(
											Liferay.Language.get(
												'plan-usage-resets-on-x'
											),
											[
												<b key='DATE'>
													{formatDateToTimeZone(
														moment(
															currentPlan.lastAnniversaryDate
														).add(1, 'year'),
														CUSTOM_DATE_FORMAT,
														timeZoneId
													)}
												</b>
											],
											false
										)}
									</Text>
								</p>
							)}

							<UsageMetric
								className='my-4'
								currentPlan={currentPlan}
								metricType={INDIVIDUALS}
								planType={planType}
								timeZoneId={timeZoneId}
							/>

							<UsageMetric
								className='mt-4'
								currentPlan={currentPlan}
								metricType={PAGEVIEWS}
								planType={planType}
								timeZoneId={timeZoneId}
							/>
						</Card.Body>
					</Card>
				</ClayLayout.Col>

				<ClayLayout.Col xl={4}>
					<SubscriptionDetails
						currentPlan={currentPlan}
						planType={planType}
					/>
				</ClayLayout.Col>
			</ClayLayout.Row>
		</BasePage>
	);
};

export default compose(withCurrentUser, withProject)(UsageOverview);
