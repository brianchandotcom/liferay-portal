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

package com.liferay.saml.opensaml.integration.internal.field.expression.handler;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.saml.opensaml.integration.field.expression.handler.UserFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.processor.context.UserProcessorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	property = {
		"display.index:Integer=200", "prefix=membership",
		"processing.index:Integer=200"
	},
	service = UserFieldExpressionHandler.class
)
public class MembershipsUserFieldExpressionHandler
	implements UserFieldExpressionHandler {

	@Override
	public void bindProcessorContext(
		UserProcessorContext userProcessorContext) {

		List<Long> userGroupIds = new ArrayList<>();

		UserProcessorContext.UserBind<User> bind = userProcessorContext.bind(
			_processingIndex,
			(currentUser, newUser, serviceContext) -> {
				_userGroupLocalService.setUserUserGroups(
					newUser.getUserId(),
					ArrayUtil.toArray(userGroupIds.toArray(new Long[0])));

				return newUser;
			});

		bind.mapStringArray(
			"userGroups",
			(user, values) -> {
				if (values == null) {
					return;
				}

				for (String value : values) {
					UserGroup userGroup = _userGroupLocalService.fetchUserGroup(
						user.getCompanyId(), value);

					if (userGroup != null) {
						userGroupIds.add(userGroup.getUserGroupId());
					}
					else if (_log.isWarnEnabled()) {
						_log.warn("Ignored unknown user group: " + value);
					}
				}
			});
	}

	@Override
	public User getLdapUser(
			long companyId, String userIdentifier,
			String userIdentifierExpression)
		throws Exception {

		return null;
	}

	@Override
	public String getSectionLabel(Locale locale) {
		return ResourceBundleUtil.getString(
			ResourceBundleUtil.getBundle(
				locale, DefaultUserFieldExpressionHandler.class),
			"user-memberships");
	}

	@Override
	public User getUser(
			long companyId, String userIdentifier,
			String userIdentifierExpression)
		throws PortalException {

		return null;
	}

	@Override
	public List<String> getValidFieldExpressions() {
		return _validFieldExpressions;
	}

	@Override
	public boolean isSupportedForUserMatching(String userIdentifier) {
		return false;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_processingIndex = GetterUtil.getInteger(
			properties.get("processing.index"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MembershipsUserFieldExpressionHandler.class);

	private int _processingIndex;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	private final List<String> _validFieldExpressions =
		Collections.unmodifiableList(Arrays.asList("userGroups"));

}