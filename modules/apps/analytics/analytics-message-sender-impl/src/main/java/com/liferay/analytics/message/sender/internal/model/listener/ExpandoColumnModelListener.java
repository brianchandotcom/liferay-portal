/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.BaseEntityModelListener;
import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = {EntityModelListener.class, ModelListener.class})
public class ExpandoColumnModelListener
	extends BaseEntityModelListener<ExpandoColumn> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return _attributeNames;
	}

	@Override
	public void onBeforeUpdate(
			ExpandoColumn originalExpandoColumn, ExpandoColumn expandoColumn)
		throws ModelListenerException {

		if (!analyticsConfigurationRegistry.isActive()) {
			return;
		}

		ExpandoColumn oldExpandoColumn =
			_expandoColumnLocalService.fetchExpandoColumn(
				expandoColumn.getColumnId());

		if (Objects.equals(
				oldExpandoColumn.getName(), expandoColumn.getName()) &&
			Objects.equals(
				oldExpandoColumn.getType(), expandoColumn.getType())) {

			return;
		}

		addAnalyticsMessage(
			"update", getAttributeNames(expandoColumn.getCompanyId()),
			expandoColumn);
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			_expandoColumnLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property tableIdProperty = PropertyFactoryUtil.forName(
					"tableId");

				dynamicQuery.add(
					tableIdProperty.in(
						_getTableDynamicQuery(
							classNameLocalService.getClassNameId(
								Organization.class.getName()),
							ExpandoTableConstants.DEFAULT_TABLE_NAME)));
			});

		return actionableDynamicQuery;
	}

	@Override
	protected ExpandoColumn getModel(long id) throws Exception {
		return _expandoColumnLocalService.getColumn(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "name";
	}

	@Override
	protected boolean isExcluded(ExpandoColumn expandoColumn) {
		if (isCustomField(
				Organization.class.getName(), expandoColumn.getTableId())) {

			return false;
		}

		if (isCustomField(User.class.getName(), expandoColumn.getTableId())) {
			AnalyticsConfiguration analyticsConfiguration =
				analyticsConfigurationRegistry.getAnalyticsConfiguration(
					expandoColumn.getCompanyId());

			if (ArrayUtil.isEmpty(
					analyticsConfiguration.syncedUserFieldNames())) {

				return true;
			}

			for (String syncedUserFieldName :
					analyticsConfiguration.syncedUserFieldNames()) {

				if (Objects.equals(
						expandoColumn.getName(), syncedUserFieldName)) {

					return false;
				}
			}

			return true;
		}

		return true;
	}

	@Override
	protected JSONObject serialize(
		BaseModel<?> baseModel, List<String> includeAttributeNames) {

		ExpandoColumn expandoColumn = (ExpandoColumn)baseModel;

		String className = User.class.getName();

		if (isCustomField(
				Organization.class.getName(), expandoColumn.getTableId())) {

			className = Organization.class.getName();
		}

		String dataType = ExpandoColumnConstants.getDataType(
			expandoColumn.getType());

		if (Validator.isBlank(dataType)) {
			dataType = ExpandoColumnConstants.DATA_TYPE_TEXT;
		}

		return JSONUtil.put(
			"className", className
		).put(
			"companyId", expandoColumn.getCompanyId()
		).put(
			"dataType", dataType
		).put(
			"displayType",
			ExpandoColumnConstants.getDefaultDisplayTypeProperty(
				expandoColumn.getType(),
				expandoColumn.getTypeSettingsProperties())
		).put(
			"name", expandoColumn.getName() + "-" + dataType
		).put(
			"typeLabel",
			ExpandoColumnConstants.getTypeLabel(expandoColumn.getType())
		);
	}

	private DynamicQuery _getTableDynamicQuery(
		long organizationClassNameId, String name) {

		DynamicQuery dynamicQuery = expandoTableLocalService.dynamicQuery();

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		dynamicQuery.add(classNameIdProperty.eq(organizationClassNameId));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(nameProperty.eq(name));

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tableId"));

		return dynamicQuery;
	}

	private static final List<String> _attributeNames = Arrays.asList(
		"className", "companyId", "dataType", "displayType", "name",
		"typeLabel");

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

}