import Constants, {SubscriptionStatuses} from 'shared/util/constants';
import {fromJS, List, Map} from 'immutable';
import {isNil} from 'lodash';
import {Metric, Plan} from 'shared/util/records';

const {subscriptionPlans} = Constants;

export const INDIVIDUALS = 'individuals';

export const PAGEVIEWS = 'pageViews';

export const PLAN_TYPES = {
	['Liferay Analytics Cloud Basic']: 'basic',
	['Liferay Analytics Cloud Business']: 'business',
	['Liferay Analytics Cloud Business Contacts']: INDIVIDUALS,
	['Liferay Analytics Cloud Business Tracked Pages']: PAGEVIEWS,
	['Liferay Analytics Cloud Enterprise']: 'enterprise',
	['Liferay Analytics Cloud Enterprise Contacts']: INDIVIDUALS,
	['Liferay Analytics Cloud Enterprise Tracked Pages']: PAGEVIEWS,
	['LXC - CSP - Custom User Tier']: 'lxcCspCustomUserTier',
	['LXC - CSP - Custom User Tier - Extra User']:
		'lxcCspCustomUserTierExtraUser',
	['LXC - CSP - Up to 100 Users']: 'lxcCspUpTo100Users',
	['LXC - CSP - Up to 100 Users - Extra User']: 'lxcCspUpTo100UsersExtraUser',
	['LXC - CSP - Up to 10K Users']: 'lxcCspUpTo10kUsers',
	['LXC - CSP - Up to 10K Users - Extra User']: 'lxcCspUpTo10kUsersExtraUser',
	['LXC - CSP - Up to 1K Users']: 'lxcCspUpTo1kUsers',
	['LXC - CSP - Up to 1K Users - Extra User']: 'lxcCspUpTo1kUsersExtraUser',
	['LXC - CSP - Up to 20K Users']: 'lxcCspUpTo20kUsers',
	['LXC - CSP - Up to 20K Users - Extra User']: 'lxcCspUpTo20kUsersExtraUser',
	['LXC - CSP - Up to 500 Users']: 'lxcCspUpTo500Users',
	['LXC - CSP - Up to 500 Users - Extra User']: 'lxcCspUpTo500UsersExtraUser',
	['LXC - CSP - Up to 5K Users']: 'lxcCspUpTo5kUsers',
	['LXC - CSP - Up to 5K Users - Extra User']: 'lxcCspUpTo5kUsersExtraUser',
	['LXC Subscription - Engage Site']: 'lxcSubscriptionEngageSite',
	['LXC Subscription - Support Site']: 'lxcSubscriptionSupportSite',
	['LXC Subscription - Transact Site']: 'lxcSubscriptionTransactSite'
};

function formatSubscriptions(allPlans) {
	const addOns = {
		[INDIVIDUALS]: {},
		['lxcCspUpTo100UsersExtraUser']: {},
		['lxcCspUpTo10kUsersExtraUser']: {},
		['lxcCspUpTo1kUsers']: {},
		['lxcCspUpTo1kUsersExtraUser']: {},
		['lxcCspUpTo20kUsers']: {},
		['lxcCspUpTo20kUsersExtraUser']: {},
		['lxcCspUpTo500UsersExtraUser']: {},
		['lxcCspUpTo5kUsersExtraUser']: {},
		['lxcSubscriptionEngageSite']: {},
		['lxcSubscriptionSupportSite']: {},
		['lxcSubscriptionTransactSite']: {},
		[PAGEVIEWS]: {}
	};

	const plans = {};

	const hasKeyProperty = key =>
		Object.prototype.hasOwnProperty.call(allPlans, key);

	for (const key in allPlans) {
		if (hasKeyProperty(key)) {
			const {
				baseSubscriptionPlan,
				individualsLimit,
				name,
				pageViewsLimit,
				price
			} = allPlans[key];

			const planType = PLAN_TYPES[key];

			const formattedPlan = {
				baseSubscriptionPlan,
				limits: {
					[INDIVIDUALS]: individualsLimit,
					[PAGEVIEWS]: pageViewsLimit
				},
				name,
				price
			};

			const parentPlanType = PLAN_TYPES[baseSubscriptionPlan];

			if (baseSubscriptionPlan) {
				addOns[planType][parentPlanType] = formattedPlan;
			} else {
				plans[planType] = formattedPlan;
			}
		}
	}

	return {addOns, plans};
}

const {addOns, plans} = formatSubscriptions(subscriptionPlans);

export {addOns as ADD_ONS};

export {plans as PLANS};

export const STATUS_DISPLAY_MAP = {
	[SubscriptionStatuses.Ok]: 'primary',
	[SubscriptionStatuses.Approaching]: 'warning',
	[SubscriptionStatuses.Over]: 'danger'
};

export const DEFAULT_ADDONS = {
	[INDIVIDUALS]: addOns[INDIVIDUALS].business,
	[PAGEVIEWS]: addOns[PAGEVIEWS].business
};

export function getPlanAddOns(planType) {
	return planType === 'basic'
		? [DEFAULT_ADDONS[INDIVIDUALS], DEFAULT_ADDONS[PAGEVIEWS]]
		: [addOns[INDIVIDUALS][planType], addOns[PAGEVIEWS][planType]];
}

export function getPlanLabel(name) {
	switch (name) {
		case plans.basic.name:
			return Liferay.Language.get('basic-plan');

		case plans.business.name:
			return Liferay.Language.get('business-plan');

		case plans.enterprise.name:
			return Liferay.Language.get('enterprise-plan');

		case plans.lxcCspCustomUserTier.name:
			return Liferay.Language.get('lxc-csp-custom-user-tier');

		case plans.lxcCspUpTo100Users.name:
			return Liferay.Language.get('lxc-csp-up-to-100-user');

		case plans.lxcCspUpTo500Users.name:
			return Liferay.Language.get('lxc-csp-up-to-500-users');

		case plans.lxcCspUpTo1kUsers.name:
			return Liferay.Language.get('lxc-csp-up-to-1k-users');

		case plans.lxcCspUpTo5kUsers.name:
			return Liferay.Language.get('lxc-csp-up-to-5k-users');

		case plans.lxcCspUpTo10kUsers.name:
			return Liferay.Language.get('lxc-csp-up-to-10k-users');

		case plans.lxcCspUpTo20kUsers.name:
			return Liferay.Language.get('lxc-csp-up-to-20k-users');

		case plans.lxcSubscriptionEngageSite.name:
			return Liferay.Language.get('lxc-subscription-engage-site');

		case plans.lxcSubscriptionSupportSite.name:
			return Liferay.Language.get('lxc-subscription-support-site');

		case plans.lxcSubscriptionTransactSite.name:
			return Liferay.Language.get('lxc-subscription-transact-site');

		default:
			return '';
	}
}

export function getPropIcon(name) {
	switch (name) {
		case INDIVIDUALS:
			return 'ac-individual';
		case PAGEVIEWS:
			return 'faro-page-views';
		default:
			return '';
	}
}

export function getPropLabel(name) {
	switch (name) {
		case INDIVIDUALS:
		case `${INDIVIDUALS}Limit`:
			return Liferay.Language.get('individuals');

		case PAGEVIEWS:
		case `${PAGEVIEWS}Limit`:
			return Liferay.Language.get('page-views');

		case plans.basic.name:
			return Liferay.Language.get('basic');

		case plans.business.name:
			return Liferay.Language.get('business');

		case plans.enterprise.name:
			return Liferay.Language.get('enterprise');

		case plans.lxcCspCustomUserTier.name:
			return Liferay.Language.get('lxc-csp-custom-user-tier');

		case plans.lxcCspCustomUserTierExtraUser.name:
			return Liferay.Language.get('lxc-csp-custom-user-tier-extra-user');

		case plans.lxcCspUpTo100Users.name:
			return Liferay.Language.get('lxc-csp-up-to-100-user');

		case plans.lxcCspUpTo500Users.name:
			return Liferay.Language.get('lxc-csp-up-to-500-users');

		case plans.lxcCspUpTo1kUsers.name:
			return Liferay.Language.get('lxc-csp-up-to-1k-users');

		case plans.lxcCspUpTo5kUsers.name:
			return Liferay.Language.get('lxc-csp-up-to-5k-users');

		case plans.lxcCspUpTo10kUsers.name:
			return Liferay.Language.get('lxc-csp-up-to-10k-users');

		case plans.lxcCspUpTo20kUsers.name:
			return Liferay.Language.get('lxc-csp-up-to-20k-users');

		case plans.lxcSubscriptionEngageSite.name:
			return Liferay.Language.get('lxc-subscription-engage-site');

		case plans.lxcSubscriptionSupportSite.name:
			return Liferay.Language.get('lxc-subscription-support-site');

		case plans.lxcSubscriptionTransactSite.name:
			return Liferay.Language.get('lxc-subscription-transact-site');

		default:
			return '';
	}
}

export function formatPlanData(subscriptionIMap) {
	if (isNil(subscriptionIMap)) {
		subscriptionIMap = new Map();
	}

	return new Plan(
		fromJS({
			addOns: {
				...subscriptionIMap
					.get('addOns', new List())
					.reduce((acc, addOn) => {
						acc[PLAN_TYPES[addOn.get('name')]] = addOn;
						return acc;
					}, {})
			},
			endDate: subscriptionIMap.get('endDate'),
			lastAnniversaryDate: subscriptionIMap.get('lastAnniversaryDate'),
			metrics: {
				individuals: new Metric({
					count: subscriptionIMap.get(
						'individualsCountSinceLastAnniversary',
						0
					),
					limit: subscriptionIMap.get('individualsLimit', 0),
					status: subscriptionIMap.get(
						'individualsStatus',
						SubscriptionStatuses.Ok
					)
				}),
				pageViews: new Metric({
					count: subscriptionIMap.get(
						'pageViewsCountSinceLastAnniversary',
						0
					),
					limit: subscriptionIMap.get('pageViewsLimit', 0),
					status: subscriptionIMap.get(
						'pageViewsStatus',
						SubscriptionStatuses.Ok
					)
				})
			},
			name: subscriptionIMap.get('name'),
			startDate: subscriptionIMap.get('startDate')
		})
	);
}
