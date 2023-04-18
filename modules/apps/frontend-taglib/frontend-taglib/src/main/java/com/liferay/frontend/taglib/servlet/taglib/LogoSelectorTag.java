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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Sandro Chinea
 */
public class LogoSelectorTag extends IncludeTag {

	public int getAspectRatio() {
		return _aspectRatio;
	}

	public String getCurrentLogoURL() {
		return _currentLogoURL;
	}

	public String getDefaultLogoURL() {
		return _defaultLogoURL;
	}

	public String getTempImageFileName() {
		return _tempImageFileName;
	}

	public boolean isDefaultLogo() {
		return _defaultLogo;
	}

	public boolean isPreserveRatio() {
		return _preserveRatio;
	}

	public void setAspectRatio(int aspectRatio) {
		_aspectRatio = aspectRatio;
	}

	public void setCurrentLogoURL(String currentLogoURL) {
		_currentLogoURL = currentLogoURL;
	}

	public void setDefaultLogo(boolean defaultLogo) {
		_defaultLogo = defaultLogo;
	}

	public void setDefaultLogoURL(String defaultLogoURL) {
		_defaultLogoURL = defaultLogoURL;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setPreserveRatio(boolean preserveRatio) {
		_preserveRatio = preserveRatio;
	}

	public void setTempImageFileName(String tempImageFileName) {
		_tempImageFileName = tempImageFileName;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_aspectRatio = 0;
		_currentLogoURL = null;
		_defaultLogo = false;
		_defaultLogoURL = null;
		_preserveRatio = false;
		_tempImageFileName = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-frontend:logo-selector:currentLogoURL", _currentLogoURL);
		httpServletRequest.setAttribute(
			"liferay-frontend:logo-selector:defaultLogoURL", _defaultLogoURL);
		httpServletRequest.setAttribute(
			"liferay-frontend:logo-selector:imageURL",
			_getImageURL(httpServletRequest));

		String randomKey = PortalUtil.generateRandomKey(
			httpServletRequest, "taglib_ui_logo_selector");

		String randomNamespace = randomKey + StringPool.UNDERLINE;

		httpServletRequest.setAttribute(
			"liferay-frontend:logo-selector:randomNamespace", randomNamespace);

		httpServletRequest.setAttribute(
			"liferay-frontend:logo-selector:uploadImageURL",
			_getUploadImageURL(httpServletRequest, randomNamespace));
	}

	private String _getImageURL(HttpServletRequest httpServletRequest) {
		boolean deleteLogo = ParamUtil.getBoolean(
			httpServletRequest, "deleteLogo");

		if (deleteLogo) {
			return getDefaultLogoURL();
		}

		long fileEntryId = ParamUtil.getLong(httpServletRequest, "fileEntryId");

		if (fileEntryId > 0) {
			PortletResponse portletResponse =
				(PortletResponse)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			return ResourceURLBuilder.createResourceURL(
				PortalUtil.getLiferayPortletResponse(portletResponse),
				PortletKeys.IMAGE_UPLOADER
			).setMVCResourceCommandName(
				"/image_uploader/upload_image"
			).setCMD(
				Constants.GET_TEMP
			).setParameter(
				"tempImageFileName", getTempImageFileName()
			).buildString();
		}

		return getCurrentLogoURL();
	}

	private String _getUploadImageURL(
		HttpServletRequest httpServletRequest, String randomNamespace) {

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortletURLBuilder.createRenderURL(
			PortalUtil.getLiferayPortletResponse(portletResponse),
			PortletKeys.IMAGE_UPLOADER
		).setMVCRenderCommandName(
			"/image_uploader/upload_image"
		).setParameter(
			"aspectRatio", getAspectRatio()
		).setParameter(
			"currentLogoURL", getCurrentLogoURL()
		).setParameter(
			"preserveRatio", isPreserveRatio()
		).setParameter(
			"randomNamespace", randomNamespace
		).setParameter(
			"tempImageFileName", getTempImageFileName()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private static final String _PAGE = "/logo_selector/page.jsp";

	private int _aspectRatio;
	private String _currentLogoURL;
	private boolean _defaultLogo;
	private String _defaultLogoURL;
	private boolean _preserveRatio;
	private String _tempImageFileName;

}