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
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributorRegistry;
import com.liferay.segments.exception.NoSuchEntryException;
import com.liferay.segments.exception.SegmentsEntryCriteriaException;
import com.liferay.segments.exception.SegmentsEntryKeyException;
import com.liferay.segments.exception.SegmentsEntryNameException;
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
 * @author Eduardo García
 */
@Component(
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS,
		"mvc.command.name=/segments/update_segments_entry"
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

		Map<Locale, String> nameMap = _localization.getLocalizationMap(
			actionRequest, "name");

		String segmentsEntryKey = ParamUtil.getString(
			actionRequest, "segmentsEntryKey");

		Map<Locale, String> descriptionMap = _localization.getLocalizationMap(
			actionRequest, "description");
		boolean active = ParamUtil.getBoolean(actionRequest, "active", true);
		String type = ParamUtil.getString(actionRequest, "type");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			SegmentsEntry.class.getName(), actionRequest);

		try {
			SegmentsEntry segmentsEntry = null;

			Criteria criteria = ActionUtil.getCriteria(
				actionRequest,
				_segmentsCriteriaContributorRegistry.
					getSegmentsCriteriaContributors(type));

			boolean dynamic = ParamUtil.getBoolean(
				actionRequest, "dynamic", true);

			_validateCriteria(criteria, dynamic);

			if (segmentsEntryId <= 0) {
				long groupId = ParamUtil.getLong(actionRequest, "groupId");

				if (groupId > 0) {
					serviceContext.setScopeGroupId(groupId);
				}

				segmentsEntry = _segmentsEntryService.addSegmentsEntry(
					segmentsEntryKey, nameMap, descriptionMap, active,
					CriteriaSerializer.serialize(criteria), type,
					serviceContext);
			}
			else {
				segmentsEntry = _segmentsEntryService.updateSegmentsEntry(
					segmentsEntryId, segmentsEntryKey, nameMap, descriptionMap,
					active, CriteriaSerializer.serialize(criteria),
					serviceContext);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				redirect = HttpComponentsUtil.setParameter(
					redirect, "segmentsEntryId",
					segmentsEntry.getSegmentsEntryId());
			}

			boolean saveAndContinue = ParamUtil.get(
				actionRequest, "saveAndContinue", false);

			if (saveAndContinue) {
				redirect = _getSaveAndContinueRedirect(
					actionRequest, segmentsEntry, redirect);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof SegmentsEntryCriteriaException ||
					 exception instanceof SegmentsEntryKeyException ||
					 exception instanceof SegmentsEntryNameException) {

				SessionErrors.add(
					actionRequest, exception.getClass(), exception);

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "/segments/edit_segments_entry");
			}
			else {
				throw exception;
			}
		}
	}

	private String _getSaveAndContinueRedirect(
			ActionRequest actionRequest, SegmentsEntry segmentsEntry,
			String redirect)
		throws Exception {

		PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, portletConfig.getPortletName(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/segments/edit_segments_entry");
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

	private void _validateCriteria(Criteria criteria, boolean dynamic)
		throws SegmentsEntryCriteriaException {

		if (dynamic && MapUtil.isEmpty(criteria.getCriteria())) {
			throw new SegmentsEntryCriteriaException();
		}
	}

	@Reference
	private Localization _localization;

	@Reference
	private SegmentsCriteriaContributorRegistry
		_segmentsCriteriaContributorRegistry;

	@Reference
	private SegmentsEntryService _segmentsEntryService;

}