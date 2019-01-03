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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.exception.ExportArticleTargetExtensionException;
import com.liferay.journal.util.ExportArticleHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=exportArticle"
	},
	service = MVCResourceCommand.class
)
public class ExportArticleMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			String targetExtension = ParamUtil.getString(
				resourceRequest, "targetExtension");

			PortletPreferences portletPreferences =
				resourceRequest.getPreferences();

			String porletResource = ParamUtil.getString(
				resourceRequest, "portletResource");

			if (Validator.isNotNull(porletResource)) {
				long plid = ParamUtil.getLong(resourceRequest, "plid");

				Layout layout = _layoutLocalService.fetchLayout(plid);

				if (layout != null) {
					portletPreferences =
						PortletPreferencesFactoryUtil.getExistingPortletSetup(
							layout, porletResource);
				}
			}

			String[] allowedExtensions = portletPreferences.getValues(
				"extensions", null);

			if (ArrayUtil.isNotEmpty(allowedExtensions) &&
				(allowedExtensions.length == 1)) {

				allowedExtensions = StringUtil.split(
					portletPreferences.getValue("extensions", null));
			}

			if (ArrayUtil.contains(allowedExtensions, targetExtension, true)) {
				_exportArticleHelper.sendFile(
					targetExtension, resourceRequest, resourceResponse);
			}
			else {
				throw new ExportArticleTargetExtensionException(
					"Target extension " + targetExtension + " is not allowed");
			}
		}
		catch (Exception e) {
			_log.error("Unable to export article", e);

			_portal.sendError(
				e, _portal.getHttpServletRequest(resourceRequest),
				_portal.getHttpServletResponse(resourceResponse));
		}
	}

	@Reference(unbind = "-")
	protected void setExportArticleHelper(
		ExportArticleHelper exportArticleHelper) {

		_exportArticleHelper = exportArticleHelper;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportArticleMVCResourceCommand.class);

	private ExportArticleHelper _exportArticleHelper;
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}