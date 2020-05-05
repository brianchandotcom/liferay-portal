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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.BaseEntityModelListener;
import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	immediate = true, service = {EntityModelListener.class, ModelListener.class}
)
public class ExpandoRowModelListener
	extends BaseEntityModelListener<ExpandoRow> {

	@Override
	public List<String> getAttributeNames() {
		return Collections.singletonList("modifiedDate");
	}

	@Override
	protected ExpandoRow getModel(long id) throws Exception {
		return _expandoRowLocalService.getExpandoRow(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "classPK";
	}

	@Override
	protected boolean isExcluded(ExpandoRow expandoRow) {
		if (isCustomField(
				Organization.class.getName(), expandoRow.getTableId())) {

			return false;
		}

		if (isCustomField(User.class.getName(), expandoRow.getTableId())) {
			User user = userLocalService.fetchUser(expandoRow.getClassPK());

			return isUserExcluded(user);
		}

		return true;
	}

	@Override
	protected JSONObject serialize(
		BaseModel<?> baseModel, List<String> includeAttributeNames) {

		ExpandoRow expandoRow = (ExpandoRow)baseModel;

		if (isCustomField(
				Organization.class.getName(), expandoRow.getTableId())) {

			Organization organization =
				_organizationLocalService.fetchOrganization(
					expandoRow.getClassPK());

			if (organization != null) {
				JSONObject jsonObject = super.serialize(
					organization, getOrganizationAttributeNames());

				jsonObject.remove(getPrimaryKeyName());

				return jsonObject.put(
					"organizationId", organization.getPrimaryKeyObj());
			}
		}
		else if (isCustomField(User.class.getName(), expandoRow.getTableId())) {
			User user = userLocalService.fetchUser(expandoRow.getClassPK());

			if (user != null) {
				JSONObject jsonObject = super.serialize(
					user, getUserAttributeNames());

				jsonObject.remove(getPrimaryKeyName());

				return jsonObject.put("userId", user.getPrimaryKeyObj());
			}
		}

		return JSONFactoryUtil.createJSONObject();
	}

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}