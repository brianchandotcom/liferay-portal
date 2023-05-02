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

package com.liferay.analytics.settings.internal.model.listener;

import com.liferay.analytics.batch.exportimport.model.listener.BaseAnalyticsDXPEntityModelListener;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(service = ModelListener.class)
public class ExpandoColumnModelListener
	extends BaseAnalyticsDXPEntityModelListener<ExpandoColumn> {

	@Override
	public void onAfterRemove(ExpandoColumn expandoColumn) {
		if (!FeatureFlagManagerUtil.isEnabled("LRAC-10757")) {
			try {
				AnalyticsConfiguration analyticsConfiguration =
					_analyticsSettingsManager.getAnalyticsConfiguration(
						expandoColumn.getCompanyId());

				String[] syncedUserFieldNames1 =
					analyticsConfiguration.syncedUserFieldNames();

				if (syncedUserFieldNames1.length == 0) {
					return;
				}

				String[] syncedUserFieldNames2 =
					new String[syncedUserFieldNames1.length - 1];

				int i = 0;

				for (String syncedUserFieldName : syncedUserFieldNames1) {
					if (!syncedUserFieldName.equals(expandoColumn.getName())) {
						syncedUserFieldNames2[i] = syncedUserFieldName;
						i++;
					}
				}

				_analyticsSettingsManager.updateCompanyConfiguration(
					expandoColumn.getCompanyId(),
					new HashMapBuilder<>().<String, Object>put(
						"previousSyncedUserFieldNames", syncedUserFieldNames1
					).<String, Serializable>put(
						"syncedUserFieldNames", syncedUserFieldNames2
					).build());
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
		else {
			super.onAfterRemove(expandoColumn);
		}
	}

	@Override
	protected boolean isTracked(ExpandoColumn expandoColumn) {
		if (_isCustomField(
				Organization.class.getName(), expandoColumn.getTableId())) {

			return true;
		}

		if (_isCustomField(User.class.getName(), expandoColumn.getTableId())) {
			AnalyticsConfiguration analyticsConfiguration =
				analyticsConfigurationRegistry.getAnalyticsConfiguration(
					expandoColumn.getCompanyId());

			if (ArrayUtil.isEmpty(
					analyticsConfiguration.syncedUserFieldNames())) {

				return false;
			}

			for (String syncedUserFieldName :
					analyticsConfiguration.syncedUserFieldNames()) {

				if (Objects.equals(
						expandoColumn.getName(), syncedUserFieldName)) {

					return true;
				}
			}

			return false;
		}

		return false;
	}

	private boolean _isCustomField(String className, long tableId) {
		long classNameId = _classNameLocalService.getClassNameId(className);

		try {
			ExpandoTable expandoTable = _expandoTableLocalService.getTable(
				tableId);

			if (Objects.equals(
					ExpandoTableConstants.DEFAULT_TABLE_NAME,
					expandoTable.getName()) &&
				(expandoTable.getClassNameId() == classNameId)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get expando table " + tableId, exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoColumnModelListener.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

}