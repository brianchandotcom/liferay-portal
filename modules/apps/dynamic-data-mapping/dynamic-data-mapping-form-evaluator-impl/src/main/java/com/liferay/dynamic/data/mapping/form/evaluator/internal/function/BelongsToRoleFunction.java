/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

/**
 * @author Leonardo Barros
 */
public class BelongsToRoleFunction
	implements DDMExpressionFunction.Function1<String[], Boolean>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "belongsTo";

	public BelongsToRoleFunction(
		RoleLocalService roleLocalService,
		UserGroupRoleLocalService userGroupRoleLocalService,
		UserLocalService userLocalService) {

		this.roleLocalService = roleLocalService;
		this.userGroupRoleLocalService = userGroupRoleLocalService;
		this.userLocalService = userLocalService;
	}

	@Override
	public Boolean apply(String[] roles) {
		if (_ddmExpressionParameterAccessor == null) {
			return false;
		}

		try {
			boolean belongsTo;

			long companyId = _ddmExpressionParameterAccessor.getCompanyId();
			long groupId = _ddmExpressionParameterAccessor.getGroupId();
			long userId = _ddmExpressionParameterAccessor.getUserId();

			for (String roleName : roles) {
				Role role = roleLocalService.fetchRole(companyId, roleName);

				if (role == null) {
					continue;
				}

				if (userId == 0) {
					if (roleName.equals("Guest")) {
						return true;
					}

					continue;
				}

				if (role.getType() == RoleConstants.TYPE_REGULAR) {
					belongsTo = userLocalService.hasRoleUser(
						companyId, roleName, userId, true);
				}
				else {
					belongsTo = userGroupRoleLocalService.hasUserGroupRole(
						userId, groupId, roleName, true);
				}

				if (belongsTo) {
					return true;
				}
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionParameterAccessor(
		DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

		_ddmExpressionParameterAccessor = ddmExpressionParameterAccessor;
	}

	protected RoleLocalService roleLocalService;
	protected UserGroupRoleLocalService userGroupRoleLocalService;
	protected UserLocalService userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BelongsToRoleFunction.class);

	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;

}