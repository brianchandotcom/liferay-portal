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

package com.liferay.commerce.user.segment.internal.criterion;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionType;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
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
		"commerce.user.segment.criterion.type.key=" + CommerceUserSegmentCriterionConstants.TYPE_ROLE,
		"commerce.user.segment.criterion.type.order:Integer=40"
	},
	service = CommerceUserSegmentCriterionType.class
)
public class RoleCommerceUserSegmentCriterionTypeImpl
	extends BaseCommerceUserSegmentCriterionType {

	@Override
	public String getKey() {
		return CommerceUserSegmentCriterionConstants.TYPE_ROLE;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "roles");
	}

	@Override
	public String getPreview(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion, int length) {

		if (length <= 0) {
			return StringPool.BLANK;
		}

		List<String> roleNames = new ArrayList<>();

		String[] roleIds = StringUtil.split(
			commerceUserSegmentCriterion.getTypeSettings());

		for (String roleId : roleIds) {
			Role role = _roleLocalService.fetchRole(GetterUtil.getLong(roleId));

			if (role != null) {
				roleNames.add(role.getName());
			}
		}

		String preview = StringUtil.merge(
			roleNames, StringPool.COMMA_AND_SPACE);

		return StringUtil.shorten(preview, length, StringPool.TRIPLE_PERIOD);
	}

	@Override
	protected long[] getUserClassPKs(User user) throws PortalException {
		if (user.isDefaultUser()) {
			Role role = _roleLocalService.getRole(
				user.getCompanyId(), RoleConstants.GUEST);

			return new long[] {role.getRoleId()};
		}

		return user.getRoleIds();
	}

	@Reference
	private RoleLocalService _roleLocalService;

}