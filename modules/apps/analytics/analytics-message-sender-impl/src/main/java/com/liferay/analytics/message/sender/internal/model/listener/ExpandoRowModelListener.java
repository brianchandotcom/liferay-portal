/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			User user = userLocalService.fetchUser(expandoRow.getClassPK());

			if (!isUserActive(user)) {
				return true;
			}

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