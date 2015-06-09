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

package com.liferay.portlet.exportimport.messaging;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil;

import java.util.List;

/**
 * @author Levente Hudák
 */
public class DraftExportImportConfigurationMessageListener
	extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws PortalException {
		if (PropsValues.
				STAGING_DRAFT_EXPORT_IMPORT_CONFIGURATION_CLEAN_UP_COUNT ==
					-1) {

			return;
		}

		DynamicQuery dynamicQuery =
			ExportImportConfigurationLocalServiceUtil.dynamicQuery();

		Property property = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(property.eq(WorkflowConstants.STATUS_DRAFT));

		Order order = OrderFactoryUtil.asc("createDate");

		dynamicQuery.addOrder(order);

		dynamicQuery.setLimit(
			QueryUtil.ALL_POS,
			PropsValues.
				STAGING_DRAFT_EXPORT_IMPORT_CONFIGURATION_CLEAN_UP_COUNT);

		List<ExportImportConfiguration> exportImportConfigurations =
			ExportImportConfigurationLocalServiceUtil.dynamicQuery(
				dynamicQuery);

		for (ExportImportConfiguration exportImportConfiguration :
				exportImportConfigurations) {

			ExportImportConfigurationLocalServiceUtil.
				deleteExportImportConfiguration(exportImportConfiguration);
		}
	}

}