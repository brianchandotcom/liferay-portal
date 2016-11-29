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

package com.liferay.dynamic.data.lists.form.web.internal.portlet.action;

import com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException;
import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Pedro Queiroz
 */

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
		"mvc.command.name=copyRecordSet"
	},
	service = MVCActionCommand.class
)
public class CopyRecordSetMVCActionCommand extends BaseMVCActionCommand {

	public Map<Locale, String> getRecordSetNameCopy(long recordSetId)
		throws Exception {
		DDLRecordSet ddlRecordSet = _ddlRecordSetService
			.getRecordSet(recordSetId);
		Locale siteDefaultLocale = LocaleUtil.getSiteDefault();

		Map<Locale, String> nameMapCopy = new HashMap<>();

		String nameCopy =
			LanguageUtil.format(getResourceBundle(siteDefaultLocale),
				"copy-of-x", ddlRecordSet.getName(siteDefaultLocale, Boolean.TRUE));

		nameMapCopy.put(siteDefaultLocale, nameCopy);

		return nameMapCopy;
	}

	protected ResourceBundle getResourceBundle(Locale locale) {

		Class<?> clazz = getClass();

		return ResourceBundleUtil.getBundle("content.Language", locale, clazz);
	}

	protected void copyRecordSet(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long recordSetId = ParamUtil.getLong(actionRequest, "recordSetId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecord.class.getName(), actionRequest);

		serviceContext.setAddGroupPermissions(Boolean.TRUE);
		serviceContext.setAddGuestPermissions(Boolean.TRUE);

		_ddlRecordSetService.copyRecordSet(groupId, recordSetId,
			getRecordSetNameCopy(recordSetId), serviceContext);

	}

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			copyRecordSet(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchRecordSetException) {

				SessionErrors.add(actionRequest, e.getClass());

				PortletSession portletSession =
					actionRequest.getPortletSession();

				PortletContext portletContext =
					portletSession.getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/error.jsp");

				portletRequestDispatcher.include(actionRequest, actionResponse);
			}
			else {
				throw e;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {

		_ddlRecordSetService = ddlRecordSetService;
	}

	private DDLRecordSetService _ddlRecordSetService;
}