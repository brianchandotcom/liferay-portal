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

package com.liferay.segments.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.exception.NoSuchEntryException;
import com.liferay.segments.exception.SegmentsEntryCriteriaException;
import com.liferay.segments.exception.SegmentsEntryKeyException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryService;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS,
		"mvc.command.name=updateSegmentsEntry"
	},
	service = MVCActionCommand.class
)
public class UpdateSegmentsEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long segmentsEntryId = ParamUtil.getLong(
			actionRequest, "segmentsEntryId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");

		String name = nameMap.get(_portal.getLocale(actionRequest));

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		boolean active = ParamUtil.getBoolean(actionRequest, "active", true);
		String criteria = ParamUtil.getString(actionRequest, "criteria");
		boolean dynamic = ParamUtil.getBoolean(actionRequest, "dynamic", true);
		String key = ParamUtil.getString(actionRequest, "key", name);
		String type = ParamUtil.getString(actionRequest, "type");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			SegmentsEntry.class.getName(), actionRequest);

		try {
			SegmentsEntry segmentsEntry = null;

			validateCriteria(criteria, dynamic);

			if (segmentsEntryId <= 0) {
				segmentsEntry = _segmentsEntryService.addSegmentsEntry(
					nameMap, descriptionMap, active, criteria, key, type,
					serviceContext);
			}
			else {
				segmentsEntry = _segmentsEntryService.updateSegmentsEntry(
					segmentsEntryId, nameMap, descriptionMap, active, criteria,
					key, serviceContext);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			boolean saveAndContinue = ParamUtil.get(
				actionRequest, "saveAndContinue", false);

			if (saveAndContinue) {
				redirect = getSaveAndContinueRedirect(
					actionRequest, segmentsEntry, redirect);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof SegmentsEntryCriteriaException ||
					 e instanceof SegmentsEntryKeyException) {

				SessionErrors.add(actionRequest, e.getClass(), e);

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editSegmentsEntry");
			}
			else {
				throw e;
			}
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, SegmentsEntry segmentsEntry,
			String redirect)
		throws Exception {

		PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, portletConfig.getPortletName(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "editSegmentsEntry");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter(
			"groupId", String.valueOf(segmentsEntry.getGroupId()), false);
		portletURL.setParameter(
			"segmentsEntryId",
			String.valueOf(segmentsEntry.getSegmentsEntryId()), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	protected void validateCriteria(String criteria, boolean dynamic)
		throws SegmentsEntryCriteriaException {

		if (dynamic && Validator.isNull(criteria)) {
			throw new SegmentsEntryCriteriaException();
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryService _segmentsEntryService;

}