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

package com.liferay.commerce.machine.learning.forecast.alert.internal.security.permission.resource;

import com.liferay.commerce.machine.learning.forecast.alert.constants.CommerceMLForecastAlertConstants;
import com.liferay.commerce.machine.learning.forecast.alert.model.CommerceMLForecastAlertEntry;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(
	immediate = true,
	property = "resource.name=" + CommerceMLForecastAlertConstants.RESOURCE_NAME,
	service = PortletResourcePermission.class
)
public class CommerceMLForecastAlertPortletResourcePermission
	implements PortletResourcePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, group, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceMLForecastAlertEntry.class.getName(),
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceMLForecastAlertEntry.class.getName(),
				actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, Group group, String actionId) {

		if (group == null) {
			return false;
		}

		return _contains(permissionChecker, group.getGroupId(), actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		return _contains(permissionChecker, groupId, actionId);
	}

	@Override
	public String getResourceName() {
		return CommerceMLForecastAlertConstants.RESOURCE_NAME;
	}

	private boolean _contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		if (permissionChecker.isCompanyAdmin() ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		return permissionChecker.hasPermission(
			groupId, CommerceMLForecastAlertConstants.RESOURCE_NAME, 0,
			actionId);
	}

}