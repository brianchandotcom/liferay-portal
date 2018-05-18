/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.user.segment.internal.criterion;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionType;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.user.segment.criterion.type.key=" + CommerceUserSegmentCriterionConstants.TYPE_USER_GROUP,
		"commerce.user.segment.criterion.type.order:Integer=30"
	},
	service = CommerceUserSegmentCriterionType.class
)
public class UserGroupCommerceUserSegmentCriterionTypeImpl
	extends BaseCommerceUserSegmentCriterionType {

	@Override
	public String getKey() {
		return CommerceUserSegmentCriterionConstants.TYPE_USER_GROUP;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "user-groups");
	}

	@Override
	public String getPreview(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion, int length) {

		if (length <= 0) {
			return StringPool.BLANK;
		}

		List<String> userGroupNames = new ArrayList<>();

		String[] userGroupIds = StringUtil.split(
			commerceUserSegmentCriterion.getTypeSettings());

		for (String userGroupId : userGroupIds) {
			UserGroup userGroup = _userGroupLocalService.fetchUserGroup(
				GetterUtil.getLong(userGroupId));

			if (userGroup != null) {
				userGroupNames.add(userGroup.getName());
			}
		}

		String preview = StringUtil.merge(
			userGroupNames, StringPool.COMMA_AND_SPACE);

		return StringUtil.shorten(preview, length, StringPool.TRIPLE_PERIOD);
	}

	@Override
	protected long[] getUserClassPKs(User user) throws PortalException {
		return user.getUserGroupIds();
	}

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}