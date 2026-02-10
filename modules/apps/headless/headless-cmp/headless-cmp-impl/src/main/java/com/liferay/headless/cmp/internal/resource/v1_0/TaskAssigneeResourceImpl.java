/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cmp.internal.resource.v1_0;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.headless.cmp.dto.v1_0.TaskAssignee;
import com.liferay.headless.cmp.resource.v1_0.TaskAssigneeResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.site.cms.site.initializer.util.CMSUserUtil;

import jakarta.ws.rs.BadRequestException;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Carolina Barbosa
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/task-assignee.properties",
	scope = ServiceScope.PROTOTYPE, service = TaskAssigneeResource.class
)
public class TaskAssigneeResourceImpl extends BaseTaskAssigneeResourceImpl {

	@Override
	public Page<TaskAssignee> getTaskAssigneesPage(String search, String type) {
		List<TaskAssignee> taskAssignees = new ArrayList<>();

		if (Validator.isNull(type)) {
			taskAssignees.addAll(_getRoles(search));
			taskAssignees.addAll(_getUsers(search));
		}
		else if (StringUtil.equalsIgnoreCase("Role", type)) {
			taskAssignees.addAll(_getRoles(search));
		}
		else if (StringUtil.equalsIgnoreCase("User", type)) {
			taskAssignees.addAll(_getUsers(search));
		}
		else {
			throw new BadRequestException("Invalid type: " + type);
		}

		return Page.of(taskAssignees);
	}

	private List<TaskAssignee> _getRoles(String search) {
		return transform(
			_roleService.search(
				CompanyThreadLocal.getCompanyId(), search,
				new Integer[] {RoleConstants.TYPE_DEPOT}, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
			role -> {
				if (StringUtil.equals(
						DepotRolesConstants.ASSET_LIBRARY_CONNECTED_SITE_MEMBER,
						role.getName())) {

					return null;
				}

				return new TaskAssignee() {
					{
						setExternalReferenceCode(
							role::getExternalReferenceCode);
						setId(role::getRoleId);
						setName(role::getName);
						setType(
							() -> StringUtil.extractLast(
								Role.class.getName(), StringPool.PERIOD));
					}
				};
			});
	}

	private List<TaskAssignee> _getUsers(String search) {
		return transform(
			CMSUserUtil.getUsers(search),
			user -> new TaskAssignee() {
				{
					setExternalReferenceCode(user::getExternalReferenceCode);
					setId(user::getUserId);
					setName(user::getFullName);
					setPortrait(
						() -> {
							if (user.getPortraitId() == 0) {
								return null;
							}

							return user.getPortraitURL(
								new ThemeDisplay() {
									{
										setPathImage(_portal.getPathImage());
									}
								});
						});
					setType(
						() -> StringUtil.extractLast(
							User.class.getName(), StringPool.PERIOD));
				}
			});
	}

	@Reference
	private Portal _portal;

	@Reference
	private RoleService _roleService;

}