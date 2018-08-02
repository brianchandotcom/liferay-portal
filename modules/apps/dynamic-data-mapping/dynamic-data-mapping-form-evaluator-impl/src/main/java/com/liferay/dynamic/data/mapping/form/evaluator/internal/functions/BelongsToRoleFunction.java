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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.evaluator.function.name=belongsTo",
	service = DDMExpressionFunction.class
)
public class BelongsToRoleFunction
	implements DDMExpressionFunction.Function1<String[], Boolean>,
			   DDMExpressionParameterAccessorAware {

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
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return false;
	}

	@Override
	public void setDDMExpressionParameterAccessor(
		DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

		_ddmExpressionParameterAccessor = ddmExpressionParameterAccessor;
	}

	@Reference
	protected RoleLocalService roleLocalService;

	@Reference
	protected UserGroupRoleLocalService userGroupRoleLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BelongsToRoleFunction.class);

	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;

}