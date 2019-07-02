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

package com.liferay.trash.taglib.servlet.taglib;

import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.taglib.internal.servlet.ServletContextUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * Creates a message confirming items were moved to the Recycle Bin, and
 * presents an option to restore them.
 *
 * @author Julio Camarero
 */
public class UndoTag extends IncludeTag {

	@Override
	public int doStartTag() {
		if (_getData() == null) {
			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getPortletURL() {
		return _portletURL;
	}

	public String getRedirect() {
		return _redirect;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL.toString();
	}

	public void setPortletURL(String portletURL) {
		_portletURL = portletURL;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_portletURL = null;
		_redirect = null;
	}

	@Override
	protected String getPage() {
		if (ListUtil.isEmpty(_getTrashedModels())) {
			return null;
		}

		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<TrashedModel> trashedModels = _getTrashedModels();

		List<Long> restoreTrashEntryIds = new ArrayList<>();
		List<String> titles = new ArrayList<>();

		for (TrashedModel trashedModel : trashedModels) {
			try {
				TrashEntry trashEntry = trashedModel.getTrashEntry();

				TrashHandler trashHandler = trashedModel.getTrashHandler();

				TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
					trashedModel.getTrashEntryClassPK());

				restoreTrashEntryIds.add(trashEntry.getEntryId());
				titles.add(trashRenderer.getTitle(themeDisplay.getLocale()));
			}
			catch (Exception e) {
			}
		}

		httpServletRequest.setAttribute("liferay-trash:undo:cmd", _getCmd());
		httpServletRequest.setAttribute(
			"liferay-trash:undo:portletURL", _portletURL);
		httpServletRequest.setAttribute(
			"liferay-trash:undo:redirect", _redirect);
		httpServletRequest.setAttribute(
			"liferay-trash:undo:restoreTrashEntryIds", restoreTrashEntryIds);
		httpServletRequest.setAttribute("liferay-trash:undo:titles", titles);
		httpServletRequest.setAttribute(
			"liferay-trash:undo:trashedEntriesCount",
			restoreTrashEntryIds.size());
	}

	private String _getCmd() {
		Map<String, Object> data = _getData();

		if (data == null) {
			return Constants.MOVE_TO_TRASH;
		}

		return MapUtil.getString(data, Constants.CMD);
	}

	private Map<String, Object> _getData() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String key =
			portletDisplay.getId() +
				SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA;

		if (!SessionMessages.contains(portletRequest, key)) {
			return null;
		}

		return (HashMap<String, Object>)SessionMessages.get(
			portletRequest, key);
	}

	private List<TrashedModel> _getTrashedModels() {
		Map<String, Object> data = _getData();

		if (data == null) {
			return Collections.emptyList();
		}

		List<TrashedModel> trashedModels = (List<TrashedModel>)data.get(
			"trashedModels");

		if (ListUtil.isEmpty(trashedModels)) {
			return Collections.emptyList();
		}

		return trashedModels;
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/undo/page.jsp";

	private String _portletURL;
	private String _redirect;

}