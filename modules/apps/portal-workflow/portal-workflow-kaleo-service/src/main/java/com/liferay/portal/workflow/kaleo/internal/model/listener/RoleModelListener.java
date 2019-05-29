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

package com.liferay.portal.workflow.kaleo.internal.model.listener;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author István András Dézsi
 */
@Component(immediate = true, service = ModelListener.class)
public class RoleModelListener extends BaseModelListener<Role> {

	@Override
	public void onBeforeRemove(Role role) throws ModelListenerException {
		try {
			_deleteKaleoTaskAssignmentByRole(role.getRoleId());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference(unbind = "-")
	protected void setKaleoTaskAssignmentLocalService(
		KaleoTaskAssignmentLocalService kaleoTaskAssignmentLocalService) {

		_kaleoTaskAssignmentLocalService = kaleoTaskAssignmentLocalService;
	}

	private void _deleteKaleoTaskAssignmentByRole(final long roleId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_kaleoTaskAssignmentLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property assigneeClassNameProperty =
					PropertyFactoryUtil.forName("assigneeClassName");

				dynamicQuery.add(
					assigneeClassNameProperty.like(Role.class.getName()));

				Property assigneeClassPKProperty = PropertyFactoryUtil.forName(
					"assigneeClassPK");

				dynamicQuery.add(assigneeClassPKProperty.eq(roleId));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(KaleoTaskAssignment kaleoTaskAssignment) ->
				_kaleoTaskAssignmentLocalService.deleteKaleoTaskAssignment(
					kaleoTaskAssignment));

		actionableDynamicQuery.performActions();
	}

	private KaleoTaskAssignmentLocalService _kaleoTaskAssignmentLocalService;

}