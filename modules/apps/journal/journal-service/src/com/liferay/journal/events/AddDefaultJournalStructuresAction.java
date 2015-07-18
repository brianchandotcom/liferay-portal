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

package com.liferay.journal.events;

import com.liferay.journal.configuration.JournalServiceConfigurationValues;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormJSONDeserializer;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormLayoutJSONDeserializer;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalService;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalService;
import com.liferay.portlet.dynamicdatamapping.util.DefaultDDMStructureUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true)
public class AddDefaultJournalStructuresAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	@Activate
	protected void activate() throws ActionException {
		Long companyId = CompanyThreadLocal.getCompanyId();

		try {
			List<Company> companies = _companyLocalService.getCompanies();

			for (Company company : companies) {
				CompanyThreadLocal.setCompanyId(company.getCompanyId());

				run(new String[] {String.valueOf(company.getCompanyId())});
			}
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyId);
		}
	}

	protected ServiceContext createServiceContext(long groupId, long userId) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		return serviceContext;
	}

	protected void doRun(long companyId) throws Exception {
		String defaultLanguageId = UpgradeProcessUtil.getDefaultLanguageId(
			companyId);

		List<Element> structureElements =
			DefaultDDMStructureUtil.getDDMStructureElements(
				PortalClassLoaderUtil.getClassLoader(), _FILE_NAME,
				LocaleUtil.fromLanguageId(defaultLanguageId));

		for (Element structureElement : structureElements) {
			processStructureElement(companyId, structureElement);
		}
	}

	protected void processStructureElement(
			long companyId, Element structureElement)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(companyId);

		long userId = _userLocalService.getDefaultUserId(companyId);

		String name = structureElement.elementText("name");
		String description = structureElement.elementText("description");

		Map<Locale, String> nameMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		for (Locale curLocale :
				LanguageUtil.getAvailableLocales(group.getGroupId())) {

			nameMap.put(curLocale, LanguageUtil.get(curLocale, name));
			descriptionMap.put(
				curLocale, LanguageUtil.get(curLocale, description));
		}

		Element structureElementDefinitionElement = structureElement.element(
			"definition");

		DDMForm ddmForm = _ddmFormJSONDeserializer.deserialize(
			structureElementDefinitionElement.getTextTrim());

		Element structureElementLayoutElement = structureElement.element(
			"layout");

		DDMFormLayout ddmFormLayout =
			_ddmFormLayoutJSONDeserializer.deserialize(
				structureElementLayoutElement.getTextTrim());

		ServiceContext serviceContext = createServiceContext(
			group.getGroupId(), userId);

		DDMStructure ddmStructure = _ddmStructureLocalService.addStructure(
			userId, group.getGroupId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			PortalUtil.getClassNameId(JournalArticle.class), name, nameMap,
			descriptionMap, ddmForm, ddmFormLayout,
			JournalServiceConfigurationValues.JOURNAL_ARTICLE_STORAGE_TYPE,
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);

		Element templateElement = structureElement.element("template");

		if (templateElement == null) {
			return;
		}

		String templateFileName = templateElement.elementText("file-name");

		String script = StringUtil.read(
			PortalClassLoaderUtil.getClassLoader(),
			FileUtil.getPath(_FILE_NAME) + StringPool.SLASH + templateFileName);

		boolean cacheable = GetterUtil.getBoolean(
			templateElement.elementText("cacheable"));

		_ddmTemplateLocalService.addTemplate(
			userId, group.getGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class),
			ddmStructure.getStructureId(), ddmStructure.getClassNameId(), null,
			nameMap, null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
			DDMTemplateConstants.TEMPLATE_MODE_CREATE,
			TemplateConstants.LANG_TYPE_FTL, script, cacheable, false,
			StringPool.BLANK, null, serviceContext);
	}

	@Reference
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference
	protected void setDDMFormJSONDeserializer(
		DDMFormJSONDeserializer ddmFormJSONDeserializer) {

		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
	}

	@Reference
	protected void setDDMFormLayoutJSONDeserializer(
		DDMFormLayoutJSONDeserializer ddmFormLayoutJSONDeserializer) {

		_ddmFormLayoutJSONDeserializer = ddmFormLayoutJSONDeserializer;
	}

	@Reference
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	@Reference
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(target = "(original.bean=true)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	@Reference
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final String _FILE_NAME =
		"com/liferay/portal/upgrade/v7_0_0/dependencies/" +
			"basic-web-content-structure.xml";

	private CompanyLocalService _companyLocalService;
	private DDMFormJSONDeserializer _ddmFormJSONDeserializer;
	private DDMFormLayoutJSONDeserializer _ddmFormLayoutJSONDeserializer;
	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMTemplateLocalService _ddmTemplateLocalService;
	private GroupLocalService _groupLocalService;
	private UserLocalService _userLocalService;

}