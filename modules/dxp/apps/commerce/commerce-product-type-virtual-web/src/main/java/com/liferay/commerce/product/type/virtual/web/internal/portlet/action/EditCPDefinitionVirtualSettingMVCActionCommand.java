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

package com.liferay.commerce.product.type.virtual.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingFileEntryIdException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingSampleFileEntryIdException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingSampleUrlException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingTermsOfUseRequiredException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingUrlException;
import com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.struts.StrutsActionPortletURL;
import com.liferay.portlet.PortletResponseImpl;
import com.liferay.portlet.PortletURLImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=editProductDefinitionVirtualSetting"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionVirtualSettingMVCActionCommand
	extends BaseMVCActionCommand {

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURLImpl portletURL = new StrutsActionPortletURL(
			(PortletResponseImpl)actionResponse, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		long cpDefinitionId = ParamUtil.getLong(actionRequest, "cpDefinitionId");

		portletURL.setParameter("mvcRenderCommandName", "editProductDefinitionVirtualSetting");
		portletURL.setParameter("cpDefinitionId", String.valueOf(cpDefinitionId));
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = getSaveAndContinueRedirect(actionRequest, actionResponse);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCPDefinitionVirtualSetting(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof CPDefinitionVirtualSettingFileEntryIdException ||
				e instanceof
					CPDefinitionVirtualSettingSampleFileEntryIdException ||
				e instanceof CPDefinitionVirtualSettingSampleUrlException ||
				e instanceof
					CPDefinitionVirtualSettingTermsOfUseRequiredException ||
				e instanceof CPDefinitionVirtualSettingUrlException ||
				e instanceof NoSuchCPDefinitionVirtualSettingException ||
				e instanceof PrincipalException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcRenderCommandName", "editProductDefinitionVirtualSetting");

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
			ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionVirtualSettingId = ParamUtil.getLong(
			actionRequest, "cpDefinitionVirtualSettingId");

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		boolean useUrl = ParamUtil.getBoolean(
			actionRequest, "useUrl");
		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");
		String url = ParamUtil.getString(actionRequest, "url");
		String activationStatus = ParamUtil.getString(
			actionRequest, "activationStatus");
		long numberOfDays = ParamUtil.getLong(actionRequest, "numberOfDays");
		int maxUsages = ParamUtil.getInteger(actionRequest, "maxUsages");
		boolean useSample = ParamUtil.getBoolean(actionRequest, "useSample");
		boolean useSampleUrl = ParamUtil.getBoolean(
			actionRequest, "useSampleUrl");
		long sampleFileEntryId = ParamUtil.getLong(
			actionRequest, "sampleFileEntryId");
		String sampleUrl = ParamUtil.getString(actionRequest, "sampleUrl");
		boolean termsOfUseRequired = ParamUtil.getBoolean(
			actionRequest, "termsOfUseRequired");
		Map<Locale, String> termsOfUseContentMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "termsOfUseContent");
		long termsOfUseJournalArticleResourcePK = ParamUtil.getLong(
			actionRequest, "termsOfUseJournalArticleResourcePK");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinitionVirtualSetting.class.getName(), actionRequest);

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting = null;

		if (cpDefinitionVirtualSettingId <= 0) {

			// Add commerce product definition virtual setting

			cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingService.
					addCPDefinitionVirtualSetting(
						cpDefinitionId, useUrl, fileEntryId, url,
						activationStatus, numberOfDays, maxUsages, useSample,
						useSampleUrl, sampleFileEntryId, sampleUrl,
						termsOfUseRequired, termsOfUseContentMap,
						termsOfUseJournalArticleResourcePK, serviceContext);
		}
		else {

			// Update commerce product definition virtual setting

			cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingService.
					updateCPDefinitionVirtualSetting(
						cpDefinitionVirtualSettingId, useUrl, fileEntryId,
						url, activationStatus, numberOfDays, maxUsages, useSample,
						useSampleUrl, sampleFileEntryId, sampleUrl,
						termsOfUseRequired, termsOfUseContentMap,
						termsOfUseJournalArticleResourcePK, serviceContext);
		}

		return cpDefinitionVirtualSetting;
	}

	@Reference
	private CPDefinitionVirtualSettingService
		_cpDefinitionVirtualSettingService;

}