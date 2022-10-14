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
import com.liferay.portal.kernel.json.JSONFactory;
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
@Component(service = {EntityModelListener.class, ModelListener.class})
public class ExpandoRowModelListener
	extends BaseEntityModelListener<ExpandoRow> {

	@Override
	public List<String> getAttributeNames(long companyId) {
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
			return isUserExcluded(
				userLocalService.fetchUser(expandoRow.getClassPK()));
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
					user, getUserAttributeNames(user.getCompanyId()));

				jsonObject.remove(getPrimaryKeyName());

				return jsonObject.put("userId", user.getPrimaryKeyObj());
			}
		}

		return _jsonFactory.createJSONObject();
	}

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}