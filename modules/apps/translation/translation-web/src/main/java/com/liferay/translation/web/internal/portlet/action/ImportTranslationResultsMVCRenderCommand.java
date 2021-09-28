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

package com.liferay.translation.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.web.internal.display.context.ImportTranslationResultsDisplayContext;

import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = {
		"javax.portlet.name=" + TranslationPortletKeys.TRANSLATION,
		"mvc.command.name=/translation/import_translation_results"
	},
	service = MVCRenderCommand.class
)
public class ImportTranslationResultsMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		long classNameId = ParamUtil.getLong(renderRequest, "classNameId");
		long classPK = ParamUtil.getLong(renderRequest, "classPK");
		long groupId = ParamUtil.getLong(renderRequest, "groupId");
		String fileName = ParamUtil.getString(renderRequest, "fileName");
		String title = ParamUtil.getString(renderRequest, "title");

		HttpServletRequest httpServletRequest =
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(renderRequest));

		HttpSession httpSession = httpServletRequest.getSession();

		Map<String, String> failureEntries =
			(Map<String, String>)httpSession.getAttribute("failureEntries");

		List<String> successEntries = (List<String>)httpSession.getAttribute(
			"successEntries");

		renderRequest.setAttribute(
			ImportTranslationResultsDisplayContext.class.getName(),
			new ImportTranslationResultsDisplayContext(
				classNameId, classPK, groupId, failureEntries, fileName,
				_portal.getHttpServletRequest(renderRequest),
				_portal.getLiferayPortletResponse(renderResponse),
				successEntries, title));

		httpSession.removeAttribute("failureEntries");
		httpSession.removeAttribute("successEntries");

		return "/import_translation_results.jsp";
	}

	@Reference
	private Portal _portal;

}