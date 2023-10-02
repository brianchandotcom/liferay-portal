/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.analytics.entity.model;

import com.liferay.analytics.message.sender.internal.util.AnalyticsModelUtil;
import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "analytics.entity.model.type=expandoRow",
	service = AnalyticsEntityModel.class
)
public class ExpandoRowAnalyticsEntityModel
	extends BaseAnalyticsEntityModel<ExpandoRow> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return Collections.singletonList("modifiedDate");
	}

	@Override
	public ExpandoRow getModel(long id) throws Exception {
		return _expandoRowLocalService.getExpandoRow(id);
	}

	@Override
	public boolean isExcluded(ExpandoRow expandoRow) {
		if (AnalyticsModelUtil.isCustomField(
				classNameLocalService.getClassNameId(
					Organization.class.getName()),
				expandoTableLocalService.fetchExpandoTable(
					expandoRow.getTableId()))) {

			return false;
		}

		if (AnalyticsModelUtil.isCustomField(
				classNameLocalService.getClassNameId(User.class.getName()),
				expandoTableLocalService.fetchExpandoTable(
					expandoRow.getTableId()))) {

			User user = userLocalService.fetchUser(expandoRow.getClassPK());

			if (!AnalyticsModelUtil.isUserActive(user)) {
				return true;
			}

			return AnalyticsModelUtil.isUserExcluded(
				analyticsConfigurationRegistry.getAnalyticsConfiguration(
					user.getCompanyId()),
				user);
		}

		return true;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "classPK";
	}

	@Override
	protected JSONObject serialize(
		BaseModel<?> baseModel, List<String> includeAttributeNames) {

		ExpandoRow expandoRow = (ExpandoRow)baseModel;

		if (AnalyticsModelUtil.isCustomField(
				classNameLocalService.getClassNameId(
					Organization.class.getName()),
				expandoTableLocalService.fetchExpandoTable(
					expandoRow.getTableId()))) {

			Organization organization =
				_organizationLocalService.fetchOrganization(
					expandoRow.getClassPK());

			if (organization != null) {
				JSONObject jsonObject = super.serialize(
					organization, _organizationAttributeNames);

				jsonObject.remove(getPrimaryKeyName());

				return jsonObject.put(
					"organizationId", organization.getPrimaryKeyObj());
			}
		}
		else if (AnalyticsModelUtil.isCustomField(
					classNameLocalService.getClassNameId(User.class.getName()),
					expandoTableLocalService.fetchExpandoTable(
						expandoRow.getTableId()))) {

			User user = userLocalService.fetchUser(expandoRow.getClassPK());

			if (user != null) {
				JSONObject jsonObject = super.serialize(
					user,
					AnalyticsModelUtil.getUserAttributeNames(
						analyticsConfigurationRegistry.
							getAnalyticsConfiguration(user.getCompanyId())));

				jsonObject.remove(getPrimaryKeyName());

				return jsonObject.put("userId", user.getPrimaryKeyObj());
			}
		}

		return _jsonFactory.createJSONObject();
	}

	private static final List<String> _organizationAttributeNames =
		Arrays.asList(
			"expando", "modifiedDate", "name", "parentOrganizationId",
			"treePath", "type");

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}