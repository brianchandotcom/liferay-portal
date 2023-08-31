/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = ModelListener.class)
public class ExpandoRowModelListener extends BaseModelListener<ExpandoRow> {

	@Override
	protected AnalyticsEntityModel<ExpandoRow> getAnalyticsEntityModel() {
		return _expandoRowAnalyticsEntityModel;
	}

	@Reference(target = "(analytics.entity.model.type=expandoRow)")
	private AnalyticsEntityModel<ExpandoRow> _expandoRowAnalyticsEntityModel;

}