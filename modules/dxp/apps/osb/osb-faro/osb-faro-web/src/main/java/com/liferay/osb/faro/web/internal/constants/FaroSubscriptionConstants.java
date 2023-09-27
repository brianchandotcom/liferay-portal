/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.constants;

import com.liferay.osb.faro.provisioning.client.constants.ProductConstants;
import com.liferay.osb.faro.web.internal.subscription.FaroSubscriptionPlan;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class FaroSubscriptionConstants {

	public static final double LIMIT_APPROACHING_THRESHOLD = .8;

	public static final int STATUS_LIMIT_APPROACHING = 1;

	public static final int STATUS_LIMIT_OVER = 2;

	public static final int STATUS_OK = 0;

	public static FaroSubscriptionPlan getFaroSubscriptionPlan(String name) {
		return _faroSubscriptionPlans.get(name);
	}

	public static FaroSubscriptionPlan getFaroSubscriptionPlanByProductEntryId(
		String productEntryId) {

		return getFaroSubscriptionPlan(
			ProductConstants.getProductName(productEntryId));
	}

	public static Map<String, FaroSubscriptionPlan> getFaroSubscriptionPlans() {
		return _faroSubscriptionPlans;
	}

	public static Map<String, Integer> getStatuses() {
		return _statuses;
	}

	private static final Map<String, FaroSubscriptionPlan>
		_faroSubscriptionPlans =
			HashMapBuilder.<String, FaroSubscriptionPlan>put(
				ProductConstants.BASIC_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null, ProductConstants.BASIC_PRODUCT_NAME, 1000, 300000, 0)
			).put(
				ProductConstants.BUSINESS_CONTACTS_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					ProductConstants.BUSINESS_PRODUCT_NAME,
					ProductConstants.BUSINESS_CONTACTS_PRODUCT_NAME, 5000, 0,
					1500)
			).put(
				ProductConstants.BUSINESS_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null, ProductConstants.BUSINESS_PRODUCT_NAME, 10000,
					5000000, 7500)
			).put(
				ProductConstants.BUSINESS_TRACKED_PAGES_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					ProductConstants.BUSINESS_PRODUCT_NAME,
					ProductConstants.BUSINESS_TRACKED_PAGES_PRODUCT_NAME, 0,
					5000000, 750)
			).put(
				ProductConstants.ENTERPRISE_CONTACTS_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					ProductConstants.ENTERPRISE_PRODUCT_NAME,
					ProductConstants.ENTERPRISE_CONTACTS_PRODUCT_NAME, 5000, 0,
					500)
			).put(
				ProductConstants.ENTERPRISE_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null, ProductConstants.ENTERPRISE_PRODUCT_NAME, 100000,
					60000000, 20000)
			).put(
				ProductConstants.ENTERPRISE_TRACKED_PAGES_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					ProductConstants.ENTERPRISE_PRODUCT_NAME,
					ProductConstants.ENTERPRISE_TRACKED_PAGES_PRODUCT_NAME, 0,
					5000000, 250)
			).put(
				ProductConstants.LXC_CSP_1K_USERS_EXTRA_USER_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					ProductConstants.LXC_CSP_1K_USERS_PRODUCT_NAME,
					ProductConstants.LXC_CSP_1K_USERS_EXTRA_USER_PRODUCT_NAME,
					1000, 0, 0)
			).put(
				ProductConstants.LXC_CSP_1K_USERS_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null, ProductConstants.LXC_CSP_1K_USERS_PRODUCT_NAME, 1000,
					-1, 0)
			).put(
				ProductConstants.LXC_CSP_5K_USERS_EXTRA_USER_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					ProductConstants.LXC_CSP_5K_USERS_PRODUCT_NAME,
					ProductConstants.LXC_CSP_5K_USERS_EXTRA_USER_PRODUCT_NAME,
					5000, 0, 0)
			).put(
				ProductConstants.LXC_CSP_5K_USERS_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null, ProductConstants.LXC_CSP_5K_USERS_PRODUCT_NAME, 5000,
					-1, 0)
			).put(
				ProductConstants.LXC_CSP_10K_USERS_EXTRA_USER_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					ProductConstants.LXC_CSP_10K_USERS_PRODUCT_NAME,
					ProductConstants.LXC_CSP_10K_USERS_EXTRA_USER_PRODUCT_NAME,
					10000, 0, 0)
			).put(
				ProductConstants.LXC_CSP_10K_USERS_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null, ProductConstants.LXC_CSP_10K_USERS_PRODUCT_NAME,
					10000, -1, 0)
			).put(
				ProductConstants.LXC_CSP_20K_USERS_EXTRA_USER_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					ProductConstants.LXC_CSP_20K_USERS_PRODUCT_NAME,
					ProductConstants.LXC_CSP_20K_USERS_EXTRA_USER_PRODUCT_NAME,
					20000, 0, 0)
			).put(
				ProductConstants.LXC_CSP_20K_USERS_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null, ProductConstants.LXC_CSP_20K_USERS_PRODUCT_NAME,
					20000, -1, 0)
			).put(
				ProductConstants.LXC_CSP_100_USERS_EXTRA_USER_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					ProductConstants.LXC_CSP_100_USERS_PRODUCT_NAME,
					ProductConstants.LXC_CSP_100_USERS_EXTRA_USER_PRODUCT_NAME,
					100, 0, 0)
			).put(
				ProductConstants.LXC_CSP_100_USERS_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null, ProductConstants.LXC_CSP_100_USERS_PRODUCT_NAME, 100,
					-1, 0)
			).put(
				ProductConstants.LXC_CSP_500_USERS_EXTRA_USER_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					ProductConstants.LXC_CSP_500_USERS_PRODUCT_NAME,
					ProductConstants.LXC_CSP_500_USERS_EXTRA_USER_PRODUCT_NAME,
					500, 0, 0)
			).put(
				ProductConstants.LXC_CSP_500_USERS_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null, ProductConstants.LXC_CSP_500_USERS_PRODUCT_NAME, 500,
					-1, 0)
			).put(
				ProductConstants.LXC_CSP_CUSTOM_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null, ProductConstants.LXC_CSP_CUSTOM_PRODUCT_NAME, -1, -1,
					0)
			).put(
				ProductConstants.LXC_SUBSCRIPTION_ENGAGE_SITE_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null,
					ProductConstants.LXC_SUBSCRIPTION_ENGAGE_SITE_PRODUCT_NAME,
					-1, -1, 0)
			).put(
				ProductConstants.LXC_SUBSCRIPTION_SUPPORT_SITE_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null,
					ProductConstants.LXC_SUBSCRIPTION_SUPPORT_SITE_PRODUCT_NAME,
					-1, -1, 0)
			).put(
				ProductConstants.LXC_SUBSCRIPTION_TRANSACT_SITE_PRODUCT_NAME,
				new FaroSubscriptionPlan(
					null,
					ProductConstants.
						LXC_SUBSCRIPTION_TRANSACT_SITE_PRODUCT_NAME,
					-1, -1, 0)
			).build();
	private static final Map<String, Integer> _statuses = HashMapBuilder.put(
		"approaching", STATUS_LIMIT_APPROACHING
	).put(
		"ok", STATUS_OK
	).put(
		"over", STATUS_LIMIT_OVER
	).build();

}