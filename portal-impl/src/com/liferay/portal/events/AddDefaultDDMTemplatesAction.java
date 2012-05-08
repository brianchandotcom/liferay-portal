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

	protected void doRun(long companyId) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		Group group = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.GUEST);

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		addDefaultDDMTemplates(
			defaultUserId, group.getGroupId(), serviceContext);
	}

	protected void addDefaultDDMTemplates(
			long userId, long groupId, ServiceContext serviceContext)
		throws SystemException, PortalException {

		// Custom Abstracts

		addDDMTemplate(
        	groupId, userId, DDMTemplateConstants.TEMPLATE_KEY_CUSTOM_ABSTRACTS,
        	"custom-abstracts", "custom-abstracts-description",
        	"asset-publisher-custom-abstracts.vm", serviceContext);

		// Custom Title List

    	addDDMTemplate(
			groupId, userId,
			DDMTemplateConstants.TEMPLATE_KEY_CUSTOM_TITLE_LIST,
			"custom-title-list", "custom-title-list-description",
			"asset-publisher-custom-title-list.vm", serviceContext);
	}

	protected void addDDMTemplate(
			long groupId, long userId, String ddmTemplateKey, String name,
			String description, String fileName, ServiceContext serviceContext)
		throws SystemException, PortalException {

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
			groupId, ddmTemplateKey);

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

		long classNameId = PortalUtil.getClassNameId(AssetEntry.class);
		long classPK = groupId;

		DDMTemplateLocalServiceUtil.addTemplate(
			userId, groupId, classNameId, classPK, ddmTemplateKey, nameMap,
			descriptionMap, "list", null, "vm", script, serviceContext);
	}

}