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

package com.liferay.portlet.blogs.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsStatsUserLocalServiceUtil;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryExportActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Juan Fernández
 * @author Zsolt Berentey
 */
public class BlogsPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "blogs";

	public BlogsPortletDataHandler() {
		setAlwaysExportable(true);
		setExportControls(
			new PortletDataHandlerBoolean(NAMESPACE, "entries"),
			new PortletDataHandlerBoolean(NAMESPACE, "embedded-assets"));
		setExportMetadataControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "blog-entries", true,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(NAMESPACE, "categories"),
					new PortletDataHandlerBoolean(NAMESPACE, "comments"),
					new PortletDataHandlerBoolean(NAMESPACE, "ratings"),
					new PortletDataHandlerBoolean(NAMESPACE, "tags")
				}));
		setImportMetadataControls(getExportMetadataControls());
		setPublishToLiveByDefault(PropsValues.BLOGS_PUBLISH_TO_LIVE_BY_DEFAULT);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				BlogsPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		BlogsEntryLocalServiceUtil.deleteEntries(
			portletDataContext.getScopeGroupId());

		BlogsStatsUserLocalServiceUtil.deleteStatsUserByGroupId(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			BlogsPermission.RESOURCE_NAME,
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "entries")) {
			ActionableDynamicQuery actionableDynamicQuery =
				new BlogsEntryExportActionableDynamicQuery(portletDataContext);

			actionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			BlogsPermission.RESOURCE_NAME,
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		if (portletDataContext.getBooleanParameter(NAMESPACE, "entries")) {
			Element entriesElement =
				portletDataContext.getImportDataGroupElement(BlogsEntry.class);

			List<Element> entryElements = entriesElement.elements();

			for (Element entryElement : entryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, entryElement);
			}
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		ActionableDynamicQuery actionableDynamicQuery =
			new BlogsEntryExportActionableDynamicQuery(portletDataContext);

		manifestSummary.addModelCount(
			BlogsEntry.class, actionableDynamicQuery.performCount());
	}

}