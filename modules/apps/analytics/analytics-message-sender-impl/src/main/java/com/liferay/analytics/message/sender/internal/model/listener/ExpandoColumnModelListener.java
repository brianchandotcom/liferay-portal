/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;

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

	@Reference(target = "(analytics.entity.model.type=expandoColumn)")
	private AnalyticsEntityModel<ExpandoColumn>
		_expandoColumnAnalyticsEntityModel;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

}