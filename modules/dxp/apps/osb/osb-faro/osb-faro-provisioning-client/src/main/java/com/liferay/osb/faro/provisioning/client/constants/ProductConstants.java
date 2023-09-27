/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.provisioning.client.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Matthew Kong
 */
public class ProductConstants {

	public static final String BASIC_PRODUCT_ENTRY_ID = "KOR-36421";

	public static final String BASIC_PRODUCT_NAME =
		"Liferay Analytics Cloud Basic";

	public static final String BUSINESS_CONTACTS_PRODUCT_ENTRY_ID = "KOR-36431";

	public static final String BUSINESS_CONTACTS_PRODUCT_NAME =
		"Liferay Analytics Cloud Business Contacts";

	public static final String BUSINESS_PRODUCT_ENTRY_ID = "KOR-36425";

	public static final String BUSINESS_PRODUCT_NAME =
		"Liferay Analytics Cloud Business";

	public static final String BUSINESS_TRACKED_PAGES_PRODUCT_ENTRY_ID =
		"KOR-36434";

	public static final String BUSINESS_TRACKED_PAGES_PRODUCT_NAME =
		"Liferay Analytics Cloud Business Tracked Pages";

	public static final String ENTERPRISE_CONTACTS_PRODUCT_ENTRY_ID =
		"KOR-36437";

	public static final String ENTERPRISE_CONTACTS_PRODUCT_NAME =
		"Liferay Analytics Cloud Enterprise Contacts";

	public static final String ENTERPRISE_PRODUCT_ENTRY_ID = "KOR-36428";

	public static final String ENTERPRISE_PRODUCT_NAME =
		"Liferay Analytics Cloud Enterprise";

	public static final String ENTERPRISE_TRACKED_PAGES_PRODUCT_ENTRY_ID =
		"KOR-36440";

	public static final String ENTERPRISE_TRACKED_PAGES_PRODUCT_NAME =
		"Liferay Analytics Cloud Enterprise Tracked Pages";

	public static final String LXC_CSP_1K_USERS_ENTRY_ID = "KOR-4891509";

	public static final String LXC_CSP_1K_USERS_EXTRA_USER_ENTRY_ID =
		"KOR-4891653";

	public static final String LXC_CSP_1K_USERS_EXTRA_USER_PRODUCT_NAME =
		"LXC - CSP - Up to 1K Users - Extra User";

	public static final String LXC_CSP_1K_USERS_PRODUCT_NAME =
		"LXC - CSP - Up to 1K Users";

	public static final String LXC_CSP_5K_USERS_ENTRY_ID = "KOR-4891533";

	public static final String LXC_CSP_5K_USERS_EXTRA_USER_ENTRY_ID =
		"KOR-4891677";

	public static final String LXC_CSP_5K_USERS_EXTRA_USER_PRODUCT_NAME =
		"LXC - CSP - Up to 5K Users - Extra User";

	public static final String LXC_CSP_5K_USERS_PRODUCT_NAME =
		"LXC - CSP - Up to 5K Users";

	public static final String LXC_CSP_10K_USERS_ENTRY_ID = "KOR-4891557";

	public static final String LXC_CSP_10K_USERS_EXTRA_USER_ENTRY_ID =
		"KOR-4891701";

	public static final String LXC_CSP_10K_USERS_EXTRA_USER_PRODUCT_NAME =
		"LXC - CSP - Up to 10K Users - Extra User";

	public static final String LXC_CSP_10K_USERS_PRODUCT_NAME =
		"LXC - CSP - Up to 10K Users";

	public static final String LXC_CSP_20K_USERS_ENTRY_ID = "KOR-4891581";

	public static final String LXC_CSP_20K_USERS_EXTRA_USER_ENTRY_ID =
		"KOR-4891725";

	public static final String LXC_CSP_20K_USERS_EXTRA_USER_PRODUCT_NAME =
		"LXC - CSP - Up to 20K Users - Extra User";

	public static final String LXC_CSP_20K_USERS_PRODUCT_NAME =
		"LXC - CSP - Up to 20K Users";

	public static final String LXC_CSP_100_USERS_ENTRY_ID = "KOR-4891461";

	public static final String LXC_CSP_100_USERS_EXTRA_USER_ENTRY_ID =
		"KOR-4891605";

	public static final String LXC_CSP_100_USERS_EXTRA_USER_PRODUCT_NAME =
		"LXC - CSP - Up to 100 Users - Extra User";

	public static final String LXC_CSP_100_USERS_PRODUCT_NAME =
		"LXC - CSP - Up to 100 Users";

	public static final String LXC_CSP_500_USERS_ENTRY_ID = "KOR-4891485";

	public static final String LXC_CSP_500_USERS_EXTRA_USER_ENTRY_ID =
		"KOR-4891629";

	public static final String LXC_CSP_500_USERS_EXTRA_USER_PRODUCT_NAME =
		"LXC - CSP - Up to 500 Users - Extra User";

	public static final String LXC_CSP_500_USERS_PRODUCT_NAME =
		"LXC - CSP - Up to 500 Users";

	public static final String LXC_CSP_CUSTOM_ENTRY_ID = "KOR-4891749";

	public static final String LXC_CSP_CUSTOM_EXTRA_USER_ENTRY_ID =
		"KOR-4891773";

	public static final String LXC_CSP_CUSTOM_EXTRA_USER_PRODUCT_NAME =
		"LXC - CSP - Custom User Tier - Extra User";

	public static final String LXC_CSP_CUSTOM_PRODUCT_NAME =
		"LXC - CSP - Custom User Tier";

	public static final String LXC_SUBSCRIPTION_ENGAGE_SITE_ENTRY_ID =
		"KOR-4891133";

	public static final String LXC_SUBSCRIPTION_ENGAGE_SITE_PRODUCT_NAME =
		"LXC Subscription - Engage Site";

	public static final String LXC_SUBSCRIPTION_SUPPORT_SITE_ENTRY_ID =
		"KOR-4891161";

	public static final String LXC_SUBSCRIPTION_SUPPORT_SITE_PRODUCT_NAME =
		"LXC Subscription - Support Site";

	public static final String LXC_SUBSCRIPTION_TRANSACT_SITE_ENTRY_ID =
		"KOR-4891189";

	public static final String LXC_SUBSCRIPTION_TRANSACT_SITE_PRODUCT_NAME =
		"LXC Subscription - Transact Site";

	public static final int OSB_OFFERING_ENTRY_STATUS_ACTIVE = 1;

	public static List<String> getBaseProductEntryIds() {
		return _baseProductEntryIds;
	}

	public static String[] getProductEntryIds() {
		Set<String> keys = _productNames.keySet();

		return keys.toArray(new String[0]);
	}

	public static String getProductName(String productEntryId) {
		return _productNames.get(productEntryId);
	}

	private static final List<String> _baseProductEntryIds = Arrays.asList(
		BASIC_PRODUCT_ENTRY_ID, BUSINESS_PRODUCT_ENTRY_ID,
		ENTERPRISE_PRODUCT_ENTRY_ID, LXC_CSP_100_USERS_ENTRY_ID,
		LXC_CSP_10K_USERS_ENTRY_ID, LXC_CSP_1K_USERS_ENTRY_ID,
		LXC_CSP_20K_USERS_ENTRY_ID, LXC_CSP_500_USERS_ENTRY_ID,
		LXC_CSP_5K_USERS_ENTRY_ID, LXC_CSP_CUSTOM_ENTRY_ID,
		LXC_SUBSCRIPTION_ENGAGE_SITE_ENTRY_ID,
		LXC_SUBSCRIPTION_SUPPORT_SITE_ENTRY_ID,
		LXC_SUBSCRIPTION_TRANSACT_SITE_ENTRY_ID);
	private static final Map<String, String> _productNames = HashMapBuilder.put(
		BASIC_PRODUCT_ENTRY_ID, BASIC_PRODUCT_NAME
	).put(
		BUSINESS_CONTACTS_PRODUCT_ENTRY_ID, BUSINESS_CONTACTS_PRODUCT_NAME
	).put(
		BUSINESS_PRODUCT_ENTRY_ID, BUSINESS_PRODUCT_NAME
	).put(
		BUSINESS_TRACKED_PAGES_PRODUCT_ENTRY_ID,
		BUSINESS_TRACKED_PAGES_PRODUCT_NAME
	).put(
		ENTERPRISE_CONTACTS_PRODUCT_ENTRY_ID, ENTERPRISE_CONTACTS_PRODUCT_NAME
	).put(
		ENTERPRISE_PRODUCT_ENTRY_ID, ENTERPRISE_PRODUCT_NAME
	).put(
		ENTERPRISE_TRACKED_PAGES_PRODUCT_ENTRY_ID,
		ENTERPRISE_TRACKED_PAGES_PRODUCT_NAME
	).put(
		LXC_CSP_1K_USERS_ENTRY_ID, LXC_CSP_1K_USERS_PRODUCT_NAME
	).put(
		LXC_CSP_1K_USERS_EXTRA_USER_ENTRY_ID,
		LXC_CSP_1K_USERS_EXTRA_USER_PRODUCT_NAME
	).put(
		LXC_CSP_5K_USERS_ENTRY_ID, LXC_CSP_5K_USERS_PRODUCT_NAME
	).put(
		LXC_CSP_5K_USERS_EXTRA_USER_ENTRY_ID,
		LXC_CSP_5K_USERS_EXTRA_USER_PRODUCT_NAME
	).put(
		LXC_CSP_10K_USERS_ENTRY_ID, LXC_CSP_10K_USERS_PRODUCT_NAME
	).put(
		LXC_CSP_10K_USERS_EXTRA_USER_ENTRY_ID,
		LXC_CSP_10K_USERS_EXTRA_USER_PRODUCT_NAME
	).put(
		LXC_CSP_20K_USERS_ENTRY_ID, LXC_CSP_20K_USERS_PRODUCT_NAME
	).put(
		LXC_CSP_20K_USERS_EXTRA_USER_ENTRY_ID,
		LXC_CSP_20K_USERS_EXTRA_USER_PRODUCT_NAME
	).put(
		LXC_CSP_100_USERS_ENTRY_ID, LXC_CSP_100_USERS_PRODUCT_NAME
	).put(
		LXC_CSP_100_USERS_EXTRA_USER_ENTRY_ID,
		LXC_CSP_100_USERS_EXTRA_USER_PRODUCT_NAME
	).put(
		LXC_CSP_500_USERS_ENTRY_ID, LXC_CSP_500_USERS_PRODUCT_NAME
	).put(
		LXC_CSP_500_USERS_EXTRA_USER_ENTRY_ID,
		LXC_CSP_500_USERS_EXTRA_USER_PRODUCT_NAME
	).put(
		LXC_CSP_CUSTOM_ENTRY_ID, LXC_CSP_CUSTOM_PRODUCT_NAME
	).put(
		LXC_CSP_CUSTOM_EXTRA_USER_ENTRY_ID,
		LXC_CSP_CUSTOM_EXTRA_USER_PRODUCT_NAME
	).put(
		LXC_SUBSCRIPTION_ENGAGE_SITE_ENTRY_ID,
		LXC_SUBSCRIPTION_ENGAGE_SITE_PRODUCT_NAME
	).put(
		LXC_SUBSCRIPTION_SUPPORT_SITE_ENTRY_ID,
		LXC_SUBSCRIPTION_SUPPORT_SITE_PRODUCT_NAME
	).put(
		LXC_SUBSCRIPTION_TRANSACT_SITE_ENTRY_ID,
		LXC_SUBSCRIPTION_TRANSACT_SITE_PRODUCT_NAME
	).build();

}