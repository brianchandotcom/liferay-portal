/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.engine.constants;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum ReservedParameterNames {

	ALLOW_MISSPELLINGS("allow_misspellings"),
	COMMERCE_ACCOUNT_GROUP_IDS("commerce_account_group_ids"),
	COMMERCE_CHANNEL_GROUP_ID("commerce_channel_group_id"),
	COMPANY_ID("company_id"), CT_COLLECTION_ID("ct_collection_id"),
	CURRENT_DATE("current_date"), CURRENT_DAY_OF_MONTH("current_day_of_month"),
	CURRENT_DAY_OF_WEEK("current_day_of_week"),
	CURRENT_DAY_OF_YEAR("current_day_of_year"), CURRENT_HOUR("current_hour"),
	CURRENT_YEAR("current_year"),
	EXCLUDED_SEARCH_REQUEST_BODY_CONTRIBUTORS(
		"excluded_search_request_body_contributors"),
	EXPLAIN("explain"), INCLUDE_RESPONSE_STRING("include_response_string"),
	IP_ADDRESS("ip_address"), LANGUAGE("language"), LANGUAGE_ID("language_id"),
	LAYOUT_LOCALE_NAME("layout_locale_name"), PLID("plid"),
	SCOPE_GROUP_ID("scope_group_id"), SHOWING_INSTEAD_OF("showing_instead_of"),
	STAGING_GROUP("is_staging_group"), TIME_OF_DAY("time_of_day"),
	TIMEZONE_ID("timezone_id"), TIMEZONE_LOCALE_NAME("timezone_locale_name"),
	USER_AGE("user_age"), USER_BIRTHDAY("user_birthday"),
	USER_CREATE_DATE("user_create_date"),
	USER_EMAIL_DOMAIN("user_email_domain"), USER_FIRST_NAME("user_first_name"),
	USER_FULL_NAME("user_full_name"), USER_GROUP_IDS("user_group_ids"),
	USER_ID("user_id"), USER_IS_FEMALE("user_is_female"),
	USER_IS_GENDER_X("user_is_gender_x"), USER_IS_MALE("user_is_male"),
	USER_IS_SIGNED_IN("user_is_signed_in"), USER_JOB_TITLE("user_job_title"),
	USER_LANGUAGE_ID("user_language_id"), USER_LAST_NAME("user_last_name"),
	USER_ROLE_IDS("user_role_ids"),
	USER_SEGMENT_ENTRY_IDS("user_segment_entry_ids"),
	USER_SEGMENT_ENTRY_LOCALE_NAMES("user_segment_locale_names");

	public static final ReservedParameterNames findByJsonKey(String jsonKey) {
		Stream<ReservedParameterNames>
			reservedParameterConfigurationKeysStream = Arrays.stream(
				ReservedParameterNames.values());

		return reservedParameterConfigurationKeysStream.filter(
			value -> value._key.equals(jsonKey)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getKey() {
		return _key;
	}

	private ReservedParameterNames(String key) {
		_key = key;
	}

	private final String _key;

}