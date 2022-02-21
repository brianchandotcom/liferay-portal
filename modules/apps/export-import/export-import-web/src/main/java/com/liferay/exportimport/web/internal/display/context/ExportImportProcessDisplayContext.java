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

package com.liferay.exportimport.web.internal.display.context;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mariano Álvaro Sáiz
 */
public class ExportImportProcessDisplayContext {

	public ExportImportProcessDisplayContext(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		_httpServletRequest = httpServletRequest;

		_portletRequest = (PortletRequest)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	public BackgroundTask getBackgroundTask() throws PortalException {
		if (_backgroundTaskId != null) {
			return _backgroundTask;
		}

		_backgroundTaskId = ParamUtil.getLong(
			_httpServletRequest, "backgroundTaskId");

		_backgroundTask = null;

		if (_backgroundTaskId > 0) {
			_backgroundTask = BackgroundTaskManagerUtil.getBackgroundTask(
				_backgroundTaskId);
		}

		return _backgroundTask;
	}

	public SearchContainer<BackgroundTask> getSearchContainer()
		throws Exception {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_portletRequest, _getIteratorURL(), null,
			"no-processes-were-found");

		BackgroundTask backgroundTask = getBackgroundTask();

		List<BackgroundTask> backgroundTasks = new ArrayList<>();

		if (backgroundTask != null) {
			backgroundTasks.add(backgroundTask);
		}

		_searchContainer.setResultsAndTotal(
			() -> backgroundTasks, backgroundTasks.size());

		return _searchContainer;
	}

	private PortletURL _getIteratorURL() {
		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		MimeResponse mimeResponse = (MimeResponse)portletResponse;

		return mimeResponse.createRenderURL();
	}

	private BackgroundTask _backgroundTask;
	private Long _backgroundTaskId;
	private final HttpServletRequest _httpServletRequest;
	private final PortletRequest _portletRequest;
	private SearchContainer<BackgroundTask> _searchContainer;

}