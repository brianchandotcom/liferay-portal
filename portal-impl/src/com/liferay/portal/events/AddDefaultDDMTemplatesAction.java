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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplate;
import com.liferay.util.ContentUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Juan Fernández
 */
public class AddDefaultDDMTemplatesAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void addDDMTemplates(
			long userId, long groupId, ServiceContext serviceContext)
		throws Exception {

		List<TemplateHandler> templateHandlers =
			TemplateHandlerRegistryUtil.getTemplateHandlers();

		for (TemplateHandler templateHandler : templateHandlers) {
			long classNameId = PortalUtil.getClassNameId(
				templateHandler.getClassName());

			List<Element> templateElements =
				templateHandler.getDefaultTemplateElements();

			for (Element templateElement : templateElements) {
				String templateKey = templateElement.elementText(
					"template-key");

				DDMTemplate ddmTemplate =
					DDMTemplateLocalServiceUtil.fetchTemplate(
						groupId, classNameId, templateKey);

				if (ddmTemplate != null) {
					continue;
				}

				Map<Locale, String> nameMap = getLocalizationMap(
					groupId, templateElement.elementText("name"));
				Map<Locale, String> descriptionMap = getLocalizationMap(
					groupId, templateElement.elementText("description"));
				String language = templateElement.elementText("language");
				String scriptFileName = templateElement.elementText(
					"script-file");

				Class<?> clazz = templateHandler.getClass();

				ClassLoader classLoader = clazz.getClassLoader();

				String script = ContentUtil.get(classLoader, scriptFileName);

				boolean cacheable = GetterUtil.getBoolean(
					templateElement.elementText("cacheable"));

				DDMTemplateLocalServiceUtil.addTemplate(
					userId, groupId, classNameId, 0,
					PortalUtil.getClassNameId(PortletDisplayTemplate.class),
					templateKey, nameMap, descriptionMap,
					DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null, language,
					script, cacheable, false, null, null, serviceContext);
			}
		}
	}

	protected void doRun(long companyId) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		Group group = GroupLocalServiceUtil.getCompanyGroup(companyId);

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		addDDMTemplates(defaultUserId, group.getGroupId(), serviceContext);
	}

	protected Map<Locale, String> getLocalizationMap(long groupId, String key) {
		Map<Locale, String> map = new HashMap<>();

		Locale[] locales = LanguageUtil.getAvailableLocales(groupId);

		for (Locale locale : locales) {
			map.put(locale, LanguageUtil.get(locale, key));
		}

		return map;
	}

}