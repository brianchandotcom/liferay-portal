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

package com.liferay.commerce.notification.web.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.notification.exception.CommerceNotificationTemplateNameException;
import com.liferay.commerce.notification.exception.CommerceNotificationTemplateTypeException;
import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateService;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateUserSegmentRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceNotificationTemplate"
	},
	service = MVCActionCommand.class
)
public class EditCommerceNotificationTemplateMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceNotificationTemplates(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceNotificationTemplateIds = null;

		long commerceNotificationTemplateId = ParamUtil.getLong(
			actionRequest, "commerceNotificationTemplateId");

		if (commerceNotificationTemplateId > 0) {
			deleteCommerceNotificationTemplateIds =
				new long[] {commerceNotificationTemplateId};
		}
		else {
			deleteCommerceNotificationTemplateIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceNotificationTemplateIds"),
				0L);
		}

		for (long deleteCommerceNotificationTemplateId :
				deleteCommerceNotificationTemplateIds) {

			_commerceNotificationTemplateService.
				deleteCommerceNotificationTemplate(
					deleteCommerceNotificationTemplateId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceNotificationTemplates(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommerceNotificationTemplate(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchNotificationTemplateException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommerceNotificationTemplateNameException ||
					 e instanceof CommerceNotificationTemplateTypeException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCommerceNotificationTemplate");
			}
			else {
				throw e;
			}
		}
	}

	protected CommerceNotificationTemplate updateCommerceNotificationTemplate(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceNotificationTemplateId = ParamUtil.getLong(
			actionRequest, "commerceNotificationTemplateId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String cc = ParamUtil.getString(actionRequest, "cc");
		String ccn = ParamUtil.getString(actionRequest, "ccn");
		String type = ParamUtil.getString(actionRequest, "type");
		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		Map<Locale, String> subjectMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "subject");
		Map<Locale, String> bodyMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "body");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceNotificationTemplate.class.getName(), actionRequest);

		CommerceNotificationTemplate commerceNotificationTemplate = null;

		if (commerceNotificationTemplateId <= 0) {
			commerceNotificationTemplate =
				_commerceNotificationTemplateService.
					addCommerceNotificationTemplate(
						name, description, cc, ccn, type, enabled, subjectMap,
						bodyMap, serviceContext);
		}
		else {
			commerceNotificationTemplate =
				_commerceNotificationTemplateService.
					updateCommerceNotificationTemplate(
						commerceNotificationTemplateId, name, description, cc,
						ccn, type, enabled, subjectMap, bodyMap,
						serviceContext);
		}

		if (commerceNotificationTemplate != null) {
			updateCommercePriceListUserSegmentEntryRels(
				actionRequest, commerceNotificationTemplate);
		}

		return commerceNotificationTemplate;
	}

	protected void updateCommercePriceListUserSegmentEntryRels(
			ActionRequest actionRequest,
			CommerceNotificationTemplate commerceNotificationTemplate)
		throws PortalException {

		long[] addCommerceUserSegmentEntryIds = ParamUtil.getLongValues(
			actionRequest, "addCommerceUserSegmentEntryIds");
		long[] deleteCommerceNotificationTemplateUserSegmentRelIds =
			ParamUtil.getLongValues(
				actionRequest,
				"deleteCommerceNotificationTemplateUserSegmentRelIds");

		if (deleteCommerceNotificationTemplateUserSegmentRelIds.length > 0) {
			for (long deleteCommerceNotificationTemplateUserSegmentRelId :
					deleteCommerceNotificationTemplateUserSegmentRelIds) {

				_commerceNotificationTemplateUserSegmentRelService.
					deleteCommerceNotificationTemplateUserSegmentRel(
						deleteCommerceNotificationTemplateUserSegmentRelId);
			}
		}

		if (addCommerceUserSegmentEntryIds.length > 0) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceNotificationTemplateUserSegmentRel.class.getName(),
				actionRequest);

			for (long addCommerceUserSegmentEntryId :
					addCommerceUserSegmentEntryIds) {

				CommerceNotificationTemplateUserSegmentRel
					commerceNotificationTemplateUserSegmentRel =
						_commerceNotificationTemplateUserSegmentRelService.
							fetchCommerceNotificationTemplateUserSegmentRel(
								commerceNotificationTemplate.
									getCommerceNotificationTemplateId(),
								addCommerceUserSegmentEntryId);

				if (commerceNotificationTemplateUserSegmentRel == null) {
					_commerceNotificationTemplateUserSegmentRelService.
						addCommerceNotificationTemplateUserSegmentRel(
							commerceNotificationTemplate.
								getCommerceNotificationTemplateId(),
							addCommerceUserSegmentEntryId, serviceContext);
				}
			}
		}
	}

	@Reference
	private CommerceNotificationTemplateService
		_commerceNotificationTemplateService;

	@Reference
	private CommerceNotificationTemplateUserSegmentRelService
		_commerceNotificationTemplateUserSegmentRelService;

}