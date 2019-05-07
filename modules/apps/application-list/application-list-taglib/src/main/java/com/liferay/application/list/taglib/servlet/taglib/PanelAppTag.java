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

package com.liferay.application.list.taglib.servlet.taglib;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Adolfo Pérez
 */
public class PanelAppTag extends BasePanelTag {

	@Override
	public int doEndTag() throws JspException {
		if (_panelApp != null) {
			request.setAttribute(ApplicationListWebKeys.PANEL_APP, _panelApp);

			try {
				boolean include = _panelApp.include(
					request,
					PipingServletResponse.createPipingServletResponse(
						pageContext));

				if (include) {
					doClearTag();

					return EVAL_PAGE;
				}
			}
			catch (IOException ioe) {
				_log.error("Unable to include panel app", ioe);
			}
		}

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	public Map<String, Object> getData() {
		return _data;
	}

	public String getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	public PanelApp getPanelApp() {
		return _panelApp;
	}

	public String getUrl() {
		return _url;
	}

	public Boolean isActive() {
		return _active;
	}

	public void setActive(Boolean active) {
		_active = active;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setPanelApp(PanelApp panelApp) {
		_panelApp = panelApp;
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_active = null;
		_data = null;
		_id = null;
		_label = null;
		_panelApp = null;
		_url = null;
	}

	@Override
	protected String getPage() {
		return "/panel_app/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		boolean active = false;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (_active != null) {
			active = _active.booleanValue();
		}
		else {
			active = Objects.equals(
				themeDisplay.getPpid(), _panelApp.getPortletId());
		}

		httpServletRequest.setAttribute(
			"liferay-application-list:panel-app:active", active);

		if (_data == null) {
			_data = new HashMap<>();
		}

		if (Validator.isNull(_label) && (_panelApp != null)) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), _panelApp.getPortletId());

			_label = PortalUtil.getPortletTitle(
				portlet, servletContext, themeDisplay.getLocale());

			if (!_data.containsKey("qa-id")) {
				_data.put("qa-id", "app");
			}
		}

		if (!_data.containsKey("title")) {
			_data.put("title", _label);
		}

		httpServletRequest.setAttribute(
			"liferay-application-list:panel-app:data", _data);

		if (Validator.isNull(_id)) {
			_id = "portlet_" + _panelApp.getPortletId();
		}

		httpServletRequest.setAttribute(
			"liferay-application-list:panel-app:id", _id);

		httpServletRequest.setAttribute(
			"liferay-application-list:panel-app:label", _label);

		int notificationsCount = 0;

		if (_panelApp != null) {
			notificationsCount = _panelApp.getNotificationsCount(
				themeDisplay.getUser());
		}

		httpServletRequest.setAttribute(
			"liferay-application-list:panel-app:notificationsCount",
			notificationsCount);

		httpServletRequest.setAttribute(
			"liferay-application-list:panel-app:panelApp", _panelApp);

		if (Validator.isNull(_url) && (_panelApp != null)) {
			PortletURL portletURL = null;

			try {
				portletURL = _panelApp.getPortletURL(httpServletRequest);
			}
			catch (PortalException pe) {
				_log.error("Unable to get portlet URL", pe);
			}

			_url = portletURL.toString();
		}

		httpServletRequest.setAttribute(
			"liferay-application-list:panel-app:url", _url);
	}

	private static final Log _log = LogFactoryUtil.getLog(PanelAppTag.class);

	private Boolean _active;
	private Map<String, Object> _data;
	private String _id;
	private String _label;
	private PanelApp _panelApp;
	private String _url;

}