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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateHandler;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
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

	protected void addDDMTemplate(
			long userId, long groupId, long classNameId, String templateKey,
			String name, String description, String scriptFile, String language,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
			groupId, templateKey);

		if (ddmTemplate != null) {
			return;
		}

		String script = ContentUtil.get(scriptFile);

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		Locale locale = LocaleUtil.getDefault();

		nameMap.put(locale, LanguageUtil.get(locale, name));

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(locale, LanguageUtil.get(locale, description));

		DDMTemplateLocalServiceUtil.addTemplate(
			userId, groupId, classNameId, 0, templateKey, nameMap,
			descriptionMap, "list", null, language, script, serviceContext);
	}

	protected void addDDMTemplates(
			long userId, long groupId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<PortletDisplayTemplateHandler> portletDisplayTemplateHandlers =
			PortletDisplayTemplateHandlerRegistryUtil
				.getPortletDisplayTemplateHandlers();

		for (PortletDisplayTemplateHandler portletDisplayTemplateHandler :
			portletDisplayTemplateHandlers) {

			try {
				long classNameId = PortalUtil.getClassNameId(
					portletDisplayTemplateHandler.getClassName());

				List<Element> templateElements =
					portletDisplayTemplateHandler.getDefaultTemplates();

				for (Element templateElement : templateElements) {
					String templateKey = templateElement.elementText(
						"template-key");

					DDMTemplate ddmTemplate =
						DDMTemplateLocalServiceUtil.fetchTemplate(
							groupId, templateKey);

					if (ddmTemplate != null) {
						continue;
					}

					String name = templateElement.elementText("name");

					String description = templateElement.elementText(
						"description");

					String language = templateElement.elementText("language");

					String scriptFile = templateElement.elementText(
						"script-file");

					addDDMTemplate(
						userId, groupId, classNameId, templateKey, name,
						description, scriptFile, language, serviceContext);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to install default portlet display templates " +
							"for " + portletDisplayTemplateHandler.getName(
								LocaleUtil.getDefault()));
				}
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

	private static Log _log = LogFactoryUtil.getLog(
		AddDefaultDDMTemplatesAction.class);

}