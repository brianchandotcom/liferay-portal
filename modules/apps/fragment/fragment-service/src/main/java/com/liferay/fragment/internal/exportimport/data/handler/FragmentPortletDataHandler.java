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

package com.liferay.fragment.internal.exportimport.data.handler;

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
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
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
	immediate = true,
	property = "javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
	service = PortletDataHandler.class
)
public class FragmentPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "fragments";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Override
	public String getServiceName() {
		return FragmentConstants.SERVICE_NAME;
	}

	@Override
	public boolean isConfigurationEnabled() {
		return false;
	}

	@Override
	public boolean validateSchemaVersion(String schemaVersion) {
		return _portletDataHandlerHelper.validateSchemaVersion(
			schemaVersion, getSchemaVersion());
	}

	@Activate
	protected void activate() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(FragmentCollection.class),
			new StagedModelType(FragmentEntry.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "entries", true, false, null,
				FragmentEntry.class.getName()));
		setPublishToLiveByDefault(true);
		setStagingControls(getExportControls());
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				FragmentPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_fragmentEntryStagedModelRepository.deleteStagedModels(
			portletDataContext);
		_fragmentCollectionStagedModelRepository.deleteStagedModels(
			portletDataContext);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "entries")) {
			return getExportDataRootElementString(rootElement);
		}

		portletDataContext.addPortletPermissions(
			FragmentConstants.RESOURCE_NAME);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery fragmentCollectionExportActionableDynamicQuery =
			_fragmentCollectionStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		fragmentCollectionExportActionableDynamicQuery.performActions();

		ActionableDynamicQuery fragmentEntryActionableDynamicQuery =
			_fragmentEntryStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		fragmentEntryActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "entries")) {
			return null;
		}

		portletDataContext.importPortletPermissions(
			FragmentConstants.RESOURCE_NAME);

		Element fragmentCollectionsElement =
			portletDataContext.getImportDataGroupElement(
				FragmentCollection.class);

		List<Element> fragmentCollectionElements =
			fragmentCollectionsElement.elements();

		for (Element fragmentCollectionElement : fragmentCollectionElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, fragmentCollectionElement);
		}

		Element fragmentEntriesElement =
			portletDataContext.getImportDataGroupElement(FragmentEntry.class);

		List<Element> fragmentEntryElements = fragmentEntriesElement.elements();

		for (Element fragmentEntryElement : fragmentEntryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, fragmentEntryElement);
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
					new StagedModelType(FragmentCollection.class.getName()),
					new StagedModelType(FragmentEntry.class.getName())
				});

			return;
		}

		ActionableDynamicQuery fragmentCollectionExportActionableDynamicQuery =
			_fragmentCollectionStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		fragmentCollectionExportActionableDynamicQuery.performCount();

		ActionableDynamicQuery fragmentEntryExportActionableDynamicQuery =
			_fragmentEntryStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		fragmentEntryExportActionableDynamicQuery.performCount();
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(
		target = "(model.class.name=com.liferay.fragment.model.FragmentCollection)",
		unbind = "-"
	)
	private StagedModelRepository<FragmentCollection>
		_fragmentCollectionStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.fragment.model.FragmentEntry)",
		unbind = "-"
	)
	private StagedModelRepository<FragmentEntry>
		_fragmentEntryStagedModelRepository;

	@Reference
	private PortletDataHandlerHelper _portletDataHandlerHelper;

	@Reference
	private Staging _staging;

}