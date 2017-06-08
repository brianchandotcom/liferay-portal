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

package com.liferay.social.office.internal.listeners;

import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 * @author Sergio González
 */
@Component(immediate = true, service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterUpdate(Group group) throws ModelListenerException {
		try {
			setSocialOfficeEnabled(group);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected void setSocialOfficeEnabled(Group group) throws Exception {
		boolean socialOfficeEnabled = false;

		String customJspServletContextName = GetterUtil.getString(
			group.getTypeSettingsProperty("customJspServletContextName"));

		if (customJspServletContextName.equals("so-hook")) {
			socialOfficeEnabled = true;
		}

		expandoValueLocalService.addValue(
			group.getCompanyId(), Group.class.getName(),
			ExpandoTableConstants.DEFAULT_TABLE_NAME, "socialOfficeEnabled",
			group.getGroupId(), socialOfficeEnabled);
	}

	@Reference
	protected ExpandoValueLocalService expandoValueLocalService;

}