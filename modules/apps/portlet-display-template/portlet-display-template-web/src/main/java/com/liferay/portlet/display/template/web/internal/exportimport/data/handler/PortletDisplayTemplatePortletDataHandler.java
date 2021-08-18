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

package com.liferay.portlet.display.template.web.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistry;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Fernández
 */
@Component(
	enabled = false,
	property = "javax.portlet.name=" + PortletKeys.PORTLET_DISPLAY_TEMPLATE,
	service = PortletDataHandler.class
)
public class PortletDisplayTemplatePortletDataHandler
	extends BasePortletDataHandler {

	public static final String NAMESPACE = "portlet_display_template";

	public static final String SCHEMA_VERSION = "4.0.0";

	@Override
	public StagedModelType[] getDeletionSystemEventStagedModelTypes() {
		return getStagedModelTypes();
	}

	@Override
	public long getExportModelCount(ManifestSummary manifestSummary) {
		long totalModelCount = -1;

		for (StagedModelType stagedModelType : getStagedModelTypes()) {
			long modelCount = manifestSummary.getModelAdditionCount(
				stagedModelType);

			if (modelCount == -1) {
				continue;
			}

			if (totalModelCount == -1) {
				totalModelCount = modelCount;
			}
			else {
				totalModelCount += modelCount;
			}
		}

		return totalModelCount;
	}

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setExportControls(_getPortletDataHandlerControls());
		setStagingControls(getExportControls());
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		List<Long> classNameIds = _getClassNameIds(portletDataContext);

		if (!classNameIds.isEmpty()) {
			ActionableDynamicQuery actionableDynamicQuery =
				getDDMTemplateActionableDynamicQuery(
					portletDataContext, classNameIds.toArray(new Long[0]),
					new StagedModelType(
						_portal.getClassNameId(DDMTemplate.class),
						StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

			actionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Element ddmTemplatesElement =
			portletDataContext.getImportDataGroupElement(DDMTemplate.class);

		List<Element> ddmTemplateElements = ddmTemplatesElement.elements();

		List<Long> classNameIds = _getClassNameIds(portletDataContext);

		for (Element ddmTemplateElement : ddmTemplateElements) {
			long classNameId = _portal.getClassNameId(
				ddmTemplateElement.attributeValue("attached-class-name"));

			if (classNameIds.contains(classNameId)) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, ddmTemplateElement);
			}
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
				portletDataContext, getStagedModelTypes());

			return;
		}

		for (StagedModelType stagedModelType : getStagedModelTypes()) {
			ActionableDynamicQuery actionableDynamicQuery =
				getDDMTemplateActionableDynamicQuery(
					portletDataContext,
					new Long[] {stagedModelType.getReferrerClassNameId()},
					stagedModelType);

			actionableDynamicQuery.performCount();
		}
	}

	protected ActionableDynamicQuery getDDMTemplateActionableDynamicQuery(
		PortletDataContext portletDataContext, Long[] classNameIds,
		StagedModelType stagedModelType) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddmTemplateLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				dynamicQuery.add(classNameIdProperty.in(classNameIds));

				Property classPKProperty = PropertyFactoryUtil.forName(
					"classPK");

				dynamicQuery.add(classPKProperty.eq(0L));

				Property typeProperty = PropertyFactoryUtil.forName("type");

				dynamicQuery.add(
					typeProperty.eq(
						DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY));
			});

		exportActionableDynamicQuery.setStagedModelType(stagedModelType);

		return exportActionableDynamicQuery;
	}

	protected StagedModelType[] getStagedModelTypes() {
		if (_stagedModelTypes != null) {
			return _stagedModelTypes;
		}

		List<StagedModelType> stagedModelTypes = new ArrayList<>();

		long ddmTemplateClassNameId = _portal.getClassNameId(DDMTemplate.class);

		for (long classNameId : _templateHandlerRegistry.getClassNameIds()) {
			stagedModelTypes.add(
				new StagedModelType(ddmTemplateClassNameId, classNameId));
		}

		_stagedModelTypes = stagedModelTypes.toArray(new StagedModelType[0]);

		return _stagedModelTypes;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private List<Long> _getClassNameIds(PortletDataContext portletDataContext) {
		List<Long> classNameIds = new ArrayList<>();

		for (TemplateHandler templateHandler :
				_templateHandlerRegistry.getTemplateHandlers()) {

			ClassName className = _classNameLocalService.fetchClassName(
				templateHandler.getClassName());

			if (className == null) {
				continue;
			}

			if (portletDataContext.getBooleanParameter(
					NAMESPACE,
					templateHandler.getName(LocaleUtil.getSiteDefault()))) {

				classNameIds.add(
					_portal.getClassNameId(templateHandler.getClassName()));
			}
		}

		return classNameIds;
	}

	private PortletDataHandlerControl[] _getPortletDataHandlerControls() {
		List<PortletDataHandlerControl> portletDataHandlerControls =
			new ArrayList<>();

		portletDataHandlerControls.add(
			new PortletDataHandlerBoolean(
				NAMESPACE, "application-display-templates", true, true));

		for (TemplateHandler templateHandler :
				_templateHandlerRegistry.getTemplateHandlers()) {

			ClassName className = _classNameLocalService.fetchClassName(
				templateHandler.getClassName());

			if (className == null) {
				continue;
			}

			portletDataHandlerControls.add(
				new PortletDataHandlerBoolean(
					NAMESPACE,
					templateHandler.getName(LocaleUtil.getSiteDefault()), true,
					false, null, DDMTemplate.class.getName(),
					className.getValue()));
		}

		return portletDataHandlerControls.toArray(
			new PortletDataHandlerControl[0]);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private Portal _portal;

	private StagedModelType[] _stagedModelTypes;

	@Reference
	private Staging _staging;

	@Reference
	private TemplateHandlerRegistry _templateHandlerRegistry;

}