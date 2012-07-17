/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 */
public class DDLDisplayPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	public String[] getDataPortletPreferences() {
		return new String[] {
			"recordSetId", "detailDDMTemplateId", "listDDMTemplateId"};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isDataLocalized() {
		return _DATA_LOCALIZED;
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletPreferences.setValue("recordSetId", StringPool.BLANK);
		portletPreferences.setValue("detailDDMTemplateId", StringPool.BLANK);
		portletPreferences.setValue("listDDMTemplateId", StringPool.BLANK);
		portletPreferences.setValue("editable", Boolean.TRUE.toString());
		portletPreferences.setValue("spreadsheet", Boolean.FALSE.toString());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.dynamicdatalist",
			portletDataContext.getScopeGroupId());

		long recordSetId = GetterUtil.getLong(
			portletPreferences.getValue("recordSetId", null), 0);

		if (recordSetId == 0) {
			if (_log.isDebugEnabled()) {
				_log.debug("No record set found for " + portletId);
			}

			return StringPool.BLANK;
		}

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("record-set-data");

		DDLRecordSet recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(
			recordSetId);

		DDLPortletDataHandler ddlPortletDataHandler =
			DDLPortletDataHandlerUtil.getDDLPortletDataHandler();

		ddlPortletDataHandler.exportRecordSet(
			portletDataContext, rootElement, recordSet);

		return document.formattedString();
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

		if (Validator.isNull(data)) {
			return null;
		}

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element recordSetElement = rootElement.element("record-set");

		if (recordSetElement != null) {
			DDLPortletDataHandler ddlPortletDataHandler =
				DDLPortletDataHandlerUtil.getDDLPortletDataHandler();

			ddlPortletDataHandler.importRecordSet(
				portletDataContext, recordSetElement);
		}

		long importedRecordSetId = GetterUtil.getLong(
			portletPreferences.getValue("recordSetId", null));
		long importedDetailDDMTemplateId = GetterUtil.getLong(
			portletPreferences.getValue("detailDDMTemplateId", null));
		long importedListDDMTemplateId = GetterUtil.getLong(
			portletPreferences.getValue("listDDMTemplateId", null));

		Map<Long, Long> recordSetIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDLRecordSet.class);

		long recordSetId = MapUtil.getLong(
			recordSetIds, importedRecordSetId, importedRecordSetId);

		Map<Long, Long> templateIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class);

		long detailDDMTemplateId = MapUtil.getLong(
			templateIds, importedDetailDDMTemplateId,
			importedDetailDDMTemplateId);

		long listDDMTemplateId = MapUtil.getLong(
			templateIds, importedListDDMTemplateId, importedListDDMTemplateId);

		portletPreferences.setValue("recordSetId", String.valueOf(recordSetId));
		portletPreferences.setValue(
			"detailDDMTemplateId", String.valueOf(detailDDMTemplateId));
		portletPreferences.setValue(
			"listDDMTemplateId", String.valueOf(listDDMTemplateId));

		return portletPreferences;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final boolean _DATA_LOCALIZED = true;

	private static Log _log = LogFactoryUtil.getLog(
		DDLDisplayPortletDataHandlerImpl.class);

}