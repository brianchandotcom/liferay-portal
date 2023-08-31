/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.util;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Joao Victor Alves
 */
public class AnalyticsModelUtil {

	public static List<String> getUserAttributeNames(
		AnalyticsConfiguration analyticsConfiguration) {

		if (ArrayUtil.isEmpty(analyticsConfiguration.syncedUserFieldNames())) {
			return _userAttributeNames;
		}

		List<String> attributeNames = new ArrayList<>();

		attributeNames.add("expando");
		attributeNames.add("memberships");

		for (String name : _userAttributeNames) {
			if (ArrayUtil.contains(
					analyticsConfiguration.syncedUserFieldNames(), name)) {

				attributeNames.add(name);
			}
		}

		return attributeNames;
	}

	public static boolean isCustomField(
		long classNameId, ExpandoTable expandoTable) {

		if ((expandoTable != null) &&
			Objects.equals(
				ExpandoTableConstants.DEFAULT_TABLE_NAME,
				expandoTable.getName()) &&
			(expandoTable.getClassNameId() == classNameId)) {

			return true;
		}

		return false;
	}

	public static boolean isUserActive(User user) {
		if ((user == null) ||
			Objects.equals(
				user.getStatus(), WorkflowConstants.STATUS_INACTIVE)) {

			return false;
		}

		return true;
	}

	public static boolean isUserExcluded(
		AnalyticsConfiguration analyticsConfiguration, User user) {

		if ((user == null) ||
			Objects.equals(
				user.getScreenName(),
				AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN) ||
			Objects.equals(
				user.getStatus(), WorkflowConstants.STATUS_INACTIVE)) {

			return true;
		}

		if (analyticsConfiguration.syncAllContacts()) {
			return false;
		}

		long[] organizationIds = null;

		try {
			organizationIds = user.getOrganizationIds();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return true;
		}

		for (long organizationId : organizationIds) {
			if (ArrayUtil.contains(
					analyticsConfiguration.syncedOrganizationIds(),
					String.valueOf(organizationId))) {

				return false;
			}
		}

		for (long userGroupId : user.getUserGroupIds()) {
			if (ArrayUtil.contains(
					analyticsConfiguration.syncedUserGroupIds(),
					String.valueOf(userGroupId))) {

				return false;
			}
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsModelUtil.class);

	private static final List<String> _userAttributeNames = Arrays.asList(
		"agreedToTermsOfUse", "comments", "companyId", "contactId",
		"createDate", "emailAddress", "emailAddressVerified", "expando",
		"externalReferenceCode", "facebookId", "firstName", "googleUserId",
		"greeting", "jobTitle", "languageId", "lastName", "ldapServerId",
		"memberships", "middleName", "modifiedDate", "openId", "portraitId",
		"screenName", "status", "timeZoneId", "uuid");

}