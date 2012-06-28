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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.util.ContentUtil;

import java.util.HashMap;
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
			long userId, long groupId, String templateKey, String name,
			String description, String fileName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
			groupId, templateKey);

		if (ddmTemplate != null) {
			return;
		}

		String script = ContentUtil.get(
			"com/liferay/portal/events/dependencies/" + fileName);

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		Locale locale = LocaleUtil.getDefault();

		nameMap.put(locale, LanguageUtil.get(locale, name));

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(locale, LanguageUtil.get(locale, description));

		DDMTemplateLocalServiceUtil.addTemplate(
			userId, groupId, PortalUtil.getClassNameId(AssetEntry.class), 0,
			templateKey, nameMap, descriptionMap, "list", null, "vm", script,
			serviceContext);
	}

	protected void addDDMTemplates(
			long userId, long groupId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		addDDMTemplate(
			userId, groupId, DDMTemplateConstants.TEMPLATE_KEY_CUSTOM_ABSTRACTS,
			"asset-publisher-custom-abstracts",
			"asset-publisher-custom-abstracts-description",
			"asset-publisher-custom-abstracts.vm", serviceContext);

		addDDMTemplate(
			userId, groupId,
			DDMTemplateConstants.TEMPLATE_KEY_CUSTOM_TITLE_LIST,
			"asset-publisher-custom-title-list",
			"asset-publisher-custom-title-list-description",
			"asset-publisher-custom-title-list.vm", serviceContext);
	}

	protected void doRun(long companyId) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		Group group = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.GUEST);

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		addDDMTemplates(defaultUserId, group.getGroupId(), serviceContext);
	}

}