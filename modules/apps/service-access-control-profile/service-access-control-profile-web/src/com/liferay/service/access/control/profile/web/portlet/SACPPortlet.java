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

package com.liferay.service.access.control.profile.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.service.access.control.profile.service.SACPEntryService;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	property = {
		"com.liferay.portlet.control-panel-entry-category=configuration",
		"com.liferay.portlet.control-panel-entry-weight=11",
		"com.liferay.portlet.css-class-wrapper=service-access-control-profile-portlet",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=Service Access Control Profile",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=Service Access Control Profile",
		"javax.portlet.info.short-title=Service Access Control Profile",
		"javax.portlet.info.title=Service Access Control Profile",
		"javax.portlet.init-param.clear-request-parameters=true",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SACPPortlet extends MVCPortlet {

	public void deleteSACPEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long sacpEntryId = ParamUtil.getLong(actionRequest, "sacpEntryId");

		_sacpEntryService.deleteSACPEntry(sacpEntryId);
	}

	public void updateSACPEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long sacpEntryId = ParamUtil.getLong(actionRequest, "sacpEntryId");
		String name = ParamUtil.getString(actionRequest, "name");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		String allowedServices = ParamUtil.getString(
			actionRequest, "allowedServices");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (sacpEntryId > 0) {
			_sacpEntryService.updateSACPEntry(
				sacpEntryId, allowedServices, name, titleMap, serviceContext);
		}
		else {
			_sacpEntryService.addSACPEntry(
				serviceContext.getCompanyId(), allowedServices, name, titleMap,
				serviceContext);
		}
	}

	@Reference(unbind = "-")
	protected void setSACPEntryService(SACPEntryService sacpEntryService) {
		_sacpEntryService = sacpEntryService;
	}

	private SACPEntryService _sacpEntryService;

}