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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.kernel.service.TrashEntryService;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=editProductDefinition"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return super.processAction(actionRequest, actionResponse);
	}

	protected void deleteCPDefinitions(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long[] deleteCPDefinitionIds = null;

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		if (cpDefinitionId > 0) {
			deleteCPDefinitionIds = new long[] {cpDefinitionId};
		}
		else {
			deleteCPDefinitionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPDefinitionIds"),
				0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteCPDefinitionId : deleteCPDefinitionIds) {
			if (moveToTrash) {
				CPDefinition cpDefinition =
					_cpDefinitionService.moveCPDefinitionToTrash(
						deleteCPDefinitionId);

				trashedModels.add(cpDefinition);
			}
			else {
				_cpDefinitionService.deleteCPDefinition(deleteCPDefinitionId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			SessionMessages.add(
				actionRequest,
				_portal.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		CPDefinition cpDefinition = null;

		int workflowAction = ParamUtil.getInteger(
			actionRequest, "workflowAction",
			WorkflowConstants.ACTION_SAVE_DRAFT);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCPDefinitions(actionRequest, false);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				cpDefinition = updateCPDefinition(actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, cpDefinition);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteCPDefinitions(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreTrashEntries(actionRequest);
			}

			if ((cpDefinition != null) &&
				(workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT)) {

				String redirect = getSaveAndContinueRedirect(
					actionRequest, cpDefinition);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPDefinitionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				throw e;
			}
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, CPDefinition cpDefinition)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, themeDisplay.getScopeGroup(),
			CPDefinition.class.getName(), PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));

		return portletURL.toString();
	}

	protected void restoreTrashEntries(ActionRequest actionRequest)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			_trashEntryService.restoreEntry(restoreTrashEntryId);
		}
	}

	protected CPDefinition updateCPDefinition(ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "titleMapAsXML");
		Map<Locale, String> shortDescriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "shortDescriptionMapAsXML");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "descriptionMapAsXML");
		Map<Locale, String> urlTitleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "urlTitleMapAsXML");
		String productTypeName = ParamUtil.getString(
			actionRequest, "productTypeName");
		String baseSKU = ParamUtil.getString(actionRequest, "baseSKU");
		String layoutUuid = ParamUtil.getString(actionRequest, "layoutUuid");

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinition.class.getName(), actionRequest);

		CPDefinition cpDefinition = null;

		if (cpDefinitionId <= 0) {

			// Add commerce product definition

			cpDefinition = _cpDefinitionService.addCPDefinition(
				baseSKU, titleMap, shortDescriptionMap, descriptionMap,
				urlTitleMap, layoutUuid, productTypeName, null,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);
		}
		else {

			// Update commerce product definition

			cpDefinition = _cpDefinitionService.updateCPDefinition(
				cpDefinitionId, baseSKU, titleMap, shortDescriptionMap,
				descriptionMap, urlTitleMap, layoutUuid, null, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
		}

		return cpDefinition;
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private Portal _portal;

	@Reference
	private TrashEntryService _trashEntryService;

}