/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.web.admin.lar;

import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsolePortletKeys;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.DefinitionLocalService;
import com.liferay.portal.reports.engine.console.service.SourceLocalService;
import com.liferay.portal.reports.engine.console.service.permission.AdminResourcePermissionChecker;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ReportsEngineConsolePortletKeys.DISPLAY_REPORTS,
		"javax.portlet.name=" + ReportsEngineConsolePortletKeys.REPORTS_ADMIN
	},
	service = PortletDataHandler.class
)
public class AdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "reports";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.SITE);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(Definition.class),
			new StagedModelType(Source.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "definitions", true, false,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "sources", true, false, null,
						Source.class.getName())
				},
				Definition.class.getName()));
		setPublishToLiveByDefault(true);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				AdminPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_definitionLocalService.deleteDefinitions(
			portletDataContext.getScopeGroupId());

		_sourceLocalService.deleteSources(portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(
			AdminResourcePermissionChecker.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "sources")) {
			ActionableDynamicQuery sourceActionableDynamicQuery =
				_sourceLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			sourceActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "definitions")) {
			ActionableDynamicQuery definitionActionableDynamicQuery =
				_definitionLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			definitionActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(
			AdminResourcePermissionChecker.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "sources")) {
			Element sourcesElement =
				portletDataContext.getImportDataGroupElement(Source.class);

			List<Element> sourceElements = sourcesElement.elements();

			for (Element sourceElement : sourceElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, sourceElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "definitions")) {
			Element definitionsElement =
				portletDataContext.getImportDataGroupElement(Definition.class);

			List<Element> definitionElements = definitionsElement.elements();

			for (Element definitionElement : definitionElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, definitionElement);
			}
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery sourceActionableDynamicQuery =
			_sourceLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		sourceActionableDynamicQuery.performCount();

		ActionableDynamicQuery definitionActionableDynamicQuery =
			_definitionLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		definitionActionableDynamicQuery.performCount();
	}

	@Reference
	private DefinitionLocalService _definitionLocalService;

	@Reference
	private SourceLocalService _sourceLocalService;

}