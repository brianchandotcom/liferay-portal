/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.persistence.DDLRecordSetActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 */
public class DDLPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "ddl";

	public DDLPortletDataHandler() {
		setAlwaysExportable(true);
		setDataLocalized(true);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				DDLPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		DDLRecordSetLocalServiceUtil.deleteRecordSets(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.dynamicdatalist",
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		ActionableDynamicQuery actionableDynamicQuery =
			new DDLRecordSetActionableDynamicQuery() {

				@Override
				protected void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

				@Override
				protected void performAction(Object object)
					throws PortalException {

					DDLRecordSet recordSet = (DDLRecordSet)object;

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, recordSet);
				}

		};

		actionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.dynamicdatalist",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element recordSetsElement =
			portletDataContext.getImportDataGroupElement(DDLRecordSet.class);

		List<Element> recordSetElements = recordSetsElement.elements();

		for (Element recordSetElement : recordSetElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, recordSetElement);
		}

		return portletPreferences;
	}

}