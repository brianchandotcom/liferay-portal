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

package com.liferay.content.dashboard.document.library.internal.item.action;

import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mikel Lorza
 */
public class RevertFileVersionContentDashboardItemVersionAction
	implements ContentDashboardItemVersionAction {

	public RevertFileVersionContentDashboardItemVersionAction(
		FileVersion fileVersion, HttpServletRequest httpServletRequest,
		Language language,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		_fileVersion = fileVersion;
		_httpServletRequest = httpServletRequest;
		_language = language;
		_requestBackedPortletURLFactory = requestBackedPortletURLFactory;
	}

	@Override
	public String getIcon() {
		return "revert";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "revert");
	}

	@Override
	public String getName() {
		return "revert";
	}

	@Override
	public String getURL() {
		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(
				(PortletResponse)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE));

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		return PortletURLBuilder.create(
			_requestBackedPortletURLFactory.createActionURL(
				DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)
		).setActionName(
			"/document_library/edit_file_entry"
		).setCMD(
			Constants.REVERT
		).setRedirect(
			portletURL
		).setBackURL(
			portletURL.toString()
		).setParameter(
			"fileEntryId", _fileVersion.getFileEntryId()
		).setParameter(
			"version", _fileVersion.getVersion()
		).buildString();
	}

	private final FileVersion _fileVersion;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final RequestBackedPortletURLFactory
		_requestBackedPortletURLFactory;

}