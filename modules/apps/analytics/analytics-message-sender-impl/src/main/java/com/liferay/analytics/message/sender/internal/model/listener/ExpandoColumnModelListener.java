/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.internal.util.AnalyticsModelUtil;
import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = ModelListener.class)
public class ExpandoColumnModelListener
	extends BaseModelListener<ExpandoColumn> {

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

		_expandoColumnAnalyticsEntityModel.addAnalyticsMessage(
			"update",
			_expandoColumnAnalyticsEntityModel.getAttributeNames(
				expandoColumn.getCompanyId()),
			expandoColumn);
	}

	@Override
	protected AnalyticsEntityModel<ExpandoColumn> getAnalyticsEntityModel() {
		return _expandoColumnAnalyticsEntityModel;
	}

	@Override
	protected ExpandoColumn getModel(long id) throws Exception {
		return _expandoColumnLocalService.getColumn(id);
	}

	@Override
	protected boolean isExcluded(ExpandoColumn expandoColumn) {
		if (AnalyticsModelUtil.isCustomField(
				classNameLocalService.getClassNameId(
					Organization.class.getName()),
				expandoTableLocalService.fetchExpandoTable(
					expandoColumn.getTableId()))) {

			return false;
		}

		if (AnalyticsModelUtil.isCustomField(
				classNameLocalService.getClassNameId(User.class.getName()),
				expandoTableLocalService.fetchExpandoTable(
					expandoColumn.getTableId()))) {

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

	@Reference(target = "(analytics.entity.model.type=expandoColumn)")
	private AnalyticsEntityModel<ExpandoColumn>
		_expandoColumnAnalyticsEntityModel;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

}