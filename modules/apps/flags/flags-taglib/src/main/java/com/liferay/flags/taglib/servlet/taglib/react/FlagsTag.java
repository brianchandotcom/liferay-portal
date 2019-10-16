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

package com.liferay.flags.taglib.servlet.taglib.react;

import com.liferay.flags.taglib.internal.servlet.ServletContextUtil;
import com.liferay.flags.taglib.servlet.taglib.util.FlagsTagUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Ambrín Chaudhary
 * @author Jorge González
 */
public class FlagsTag extends IncludeTag {

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public String getContentTitle() {
		return _contentTitle;
	}

	public String getContentURL() {
		return _contentURL;
	}

	public String getElementClasses() {
		return _elementClasses;
	}

	public String getMessage() {
		return _message;
	}

	public long getReportedUserId() {
		return _reportedUserId;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public boolean isLabel() {
		return _label;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setContentTitle(String contentTitle) {
		_contentTitle = contentTitle;
	}

	public void setContentURL(String contentURL) {
		_contentURL = contentURL;
	}

	public void setElementClasses(String elementClasses) {
		_elementClasses = elementClasses;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setLabel(boolean label) {
		_label = label;
	}

	public void setMessage(String message) {
		_message = message;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setReportedUserId(long reportedUserId) {
		_reportedUserId = reportedUserId;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_classPK = 0;
		_contentTitle = null;
		_contentURL = null;
		_elementClasses = null;
		_enabled = true;
		_label = true;
		_message = null;
		_reportedUserId = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		try {
			String message = _getMessage();

			httpServletRequest.setAttribute(
				"liferay-flags:flags:data", _getData(message));

			httpServletRequest.setAttribute(
				"liferay-flags:flags:elementClasses", _getElementClasses());
			httpServletRequest.setAttribute(
				"liferay-flags:flags:message", message);
			httpServletRequest.setAttribute(
				"liferay-flags:flags:onlyIcon", !_label);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private Map<String, Object> _getData(String message)
		throws PortalException {

		Map<String, Object> data = new HashMap<>();

		Map<String, Object> context = new HashMap<>();

		context.put(
			"namespace", PortalUtil.getPortletNamespace(PortletKeys.FLAGS));

		data.put("context", context);

		Map<String, Object> props = new HashMap<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		props.put("baseData", _getDataJSONObject(themeDisplay));

		Company company = themeDisplay.getCompany();

		props.put("companyName", company.getName());

		props.put("disabled", !_enabled);
		props.put("forceLogin", !FlagsTagUtil.isFlagsEnabled(themeDisplay));

		if (Validator.isNotNull(message)) {
			props.put("message", message);
		}

		props.put("onlyIcon", !_label);
		props.put(
			"pathTermsOfUse",
			PortalUtil.getPathMain() + "/portal/terms_of_use");
		props.put(
			"reasons",
			FlagsTagUtil.getReasons(themeDisplay.getCompanyId(), request));
		props.put("signedIn", themeDisplay.isSignedIn());
		props.put("uri", FlagsTagUtil.getURI(request));

		data.put("props", props);

		return data;
	}

	private JSONObject _getDataJSONObject(ThemeDisplay themeDisplay) {
		String namespace = PortalUtil.getPortletNamespace(PortletKeys.FLAGS);

		String contentURL = _contentURL;

		if (Validator.isNull(contentURL)) {
			contentURL = FlagsTagUtil.getCurrentURL(request);
		}

		JSONObject dataJSONObject = JSONUtil.put(
			namespace + "className", _className
		).put(
			namespace + "classPK", _classPK
		).put(
			namespace + "contentTitle", _contentTitle
		).put(
			namespace + "contentURL", contentURL
		).put(
			namespace + "reportedUserId", _reportedUserId
		);

		if (themeDisplay.isSignedIn()) {
			User user = themeDisplay.getUser();

			dataJSONObject.put(
				namespace + "reporterEmailAddress", user.getEmailAddress());
		}

		return dataJSONObject;
	}

	private String _getElementClasses() {
		return _elementClasses;
	}

	private String _getMessage() {
		ResourceBundle resourceBundle = new AggregateResourceBundle(
			(ResourceBundle)pageContext.getAttribute("resourceBundle"),
			ResourceBundleUtil.getBundle(
				PortalUtil.getLocale(request), "com.liferay.flags.taglib"));

		if (Validator.isNotNull(_message)) {
			return LanguageUtil.get(resourceBundle, _message);
		}

		return LanguageUtil.get(resourceBundle, "report");
	}

	private static final String _PAGE = "/flags/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(FlagsTag.class);

	private String _className;
	private long _classPK;
	private String _contentTitle;
	private String _contentURL;
	private String _elementClasses;
	private boolean _enabled = true;
	private boolean _label = true;
	private String _message;
	private long _reportedUserId;

}