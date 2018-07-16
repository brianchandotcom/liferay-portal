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

package com.liferay.blogs.web.internal.exportimport.data.handler;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.util.PropsValues;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Juan Fernández
 * @author Zsolt Berentey
 * @author Gergely Mathe
 */
@Component(
	property = "javax.portlet.name=" + BlogsPortletKeys.BLOGS,
	service = PortletDataHandler.class
)
public class BlogsPortletDataHandler extends BasePortletDataHandler {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             BlogsAdminPortletDataHandler#NAMESPACE}
	 */
	@Deprecated
	public static final String NAMESPACE = "blogs";

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             BlogsAdminPortletDataHandler#SCHEMA_VERSION}
	 */
	@Deprecated
	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getNamespace() {
		return _blogsAdminPortletDataHandler.getNamespace();
	}

	@Override
	public String getSchemaVersion() {
		return _blogsAdminPortletDataHandler.getSchemaVersion();
	}

	@Override
	public String getServiceName() {
		return _blogsAdminPortletDataHandler.getServiceName();
	}

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(BlogsEntry.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				getNamespace(), "entries", true, false,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						getNamespace(), "referenced-content")
				},
				BlogsEntry.class.getName()));
		setPublishToLiveByDefault(PropsValues.BLOGS_PUBLISH_TO_LIVE_BY_DEFAULT);
		setStagingControls(getExportControls());
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return _blogsAdminPortletDataHandler.doDeleteData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return _blogsAdminPortletDataHandler.doExportData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		return _blogsAdminPortletDataHandler.doImportData(
			portletDataContext, portletId, portletPreferences, data);
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		_blogsAdminPortletDataHandler.doPrepareManifestSummary(
			portletDataContext, portletPreferences);
	}

	@Reference
	private BlogsAdminPortletDataHandler _blogsAdminPortletDataHandler;

}