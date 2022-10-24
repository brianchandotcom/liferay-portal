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

package com.liferay.layout.admin.web.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.exportimport.portlet.data.handler.helper.PortletDataHandlerHelper;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
	service = PortletDataHandler.class
)
public class GroupPagesPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "page-templates";

	public static final String SCHEMA_VERSION = "4.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Override
	public String getServiceName() {
		return LayoutPageTemplateConstants.SERVICE_NAME;
	}

	@Override
	public boolean isConfigurationEnabled() {
		return false;
	}

	@Override
	public boolean isStaged() {
		return true;
	}

	@Override
	public boolean validateSchemaVersion(String schemaVersion) {
		return _portletDataHandlerHelper.validateSchemaVersion(
			schemaVersion, getSchemaVersion());
	}

	@Activate
	protected void activate() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(LayoutPageTemplateCollection.class),
			new StagedModelType(LayoutPageTemplateEntry.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "page-template-sets", true, true, null,
				LayoutPageTemplateCollection.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "page-templates", true, false, null,
				LayoutPageTemplateEntry.class.getName(),
				StagedModelType.REFERRER_CLASS_NAME_ALL));
		setPublishToLiveByDefault(true);
		setStagingControls(getExportControls());
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				GroupPagesPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (!portletDataContext.getBooleanParameter(
				NAMESPACE, "page-templates")) {

			return getExportDataRootElementString(rootElement);
		}

		portletDataContext.addPortletPermissions(
			LayoutPageTemplateConstants.RESOURCE_NAME);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery
			layoutPageTemplateCollectionExportActionableDynamicQuery =
				_layoutPageTemplateCollectionStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

		layoutPageTemplateCollectionExportActionableDynamicQuery.
			performActions();

		ActionableDynamicQuery layoutPageTemplateEntryActionableDynamicQuery =
			_layoutPageTemplateEntryStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		layoutPageTemplateEntryActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		if (!portletDataContext.getBooleanParameter(
				NAMESPACE, "page-templates")) {

			return null;
		}

		portletDataContext.importPortletPermissions(
			LayoutPageTemplateConstants.RESOURCE_NAME);

		Element layoutPageTemplateCollectionsElement =
			portletDataContext.getImportDataGroupElement(
				LayoutPageTemplateCollection.class);

		List<Element> layoutPageTemplateCollectionElements =
			layoutPageTemplateCollectionsElement.elements();

		for (Element layoutPageTemplateCollectionElement :
				layoutPageTemplateCollectionElements) {

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutPageTemplateCollectionElement);
		}

		Element fragmentEntriesElement =
			portletDataContext.getImportDataGroupElement(
				LayoutPageTemplateEntry.class);

		List<Element> layoutPageTemplateEntryElements =
			fragmentEntriesElement.elements();

		for (Element layoutPageTemplateEntryElement :
				layoutPageTemplateEntryElements) {

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				(LayoutPageTemplateEntry)portletDataContext.getZipEntryAsObject(
					layoutPageTemplateEntryElement.attributeValue("path"));

			boolean privateLayout = portletDataContext.isPrivateLayout();

			if (layoutPageTemplateEntry.getType() ==
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC) {

				portletDataContext.setPrivateLayout(true);
			}

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutPageTemplateEntryElement);

			portletDataContext.setPrivateLayout(privateLayout);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		if (ExportImportDateUtil.isRangeFromLastPublishDate(
				portletDataContext)) {

			_staging.populateLastPublishDateCounts(
				portletDataContext,
				new StagedModelType[] {
					new StagedModelType(
						LayoutPageTemplateCollection.class.getName()),
					new StagedModelType(LayoutPageTemplateEntry.class.getName())
				});

			return;
		}

		ActionableDynamicQuery
			layoutPageTemplateCollectionExportActionableDynamicQuery =
				_layoutPageTemplateCollectionStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

		layoutPageTemplateCollectionExportActionableDynamicQuery.performCount();

		ActionableDynamicQuery
			layoutPageTemplateEntryExportActionableDynamicQuery =
				_layoutPageTemplateEntryStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

		layoutPageTemplateEntryExportActionableDynamicQuery.performCount();
	}

	@Reference(
		target = "(model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateCollection)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutPageTemplateCollection>
		_layoutPageTemplateCollectionStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateEntry)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutPageTemplateEntry>
		_layoutPageTemplateEntryStagedModelRepository;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private PortletDataHandlerHelper _portletDataHandlerHelper;

	@Reference
	private Staging _staging;

}