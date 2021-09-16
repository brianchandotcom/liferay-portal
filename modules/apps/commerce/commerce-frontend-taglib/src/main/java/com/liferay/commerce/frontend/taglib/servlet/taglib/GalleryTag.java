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

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.content.util.CPMedia;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.portlet.PortletResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Fabio Mastrorilli
 */
public class GalleryTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			_images = _cpContentHelper.getImages(_cpDefinitionId, themeDisplay);
			_viewCPAttachmentURL = _getViewCPAttachmentURL();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return super.doStartTag();
	}

	public long getCPDefinitionId() {
		return _cpDefinitionId;
	}

	public String getNamespace() {
		return _namespace;
	}

	public void setCPDefinitionId(long cpDefinitionId) {
		_cpDefinitionId = cpDefinitionId;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());

		_cpContentHelper = ServletContextUtil.getCPContentHelper();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cpContentHelper = null;
		_cpDefinitionId = 0;
		_images = null;
		_namespace = StringPool.BLANK;
		_viewCPAttachmentURL = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-commerce:gallery:images", _images);
		httpServletRequest.setAttribute(
			"liferay-commerce:gallery:namespace", _namespace);
		httpServletRequest.setAttribute(
			"liferay-commerce:gallery:viewCPAttachmentURL",
			_viewCPAttachmentURL);
	}

	private String _getViewCPAttachmentURL() {
		PortletResponse portletResponse =
			(PortletResponse)getRequest().getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(portletResponse);

		ResourceURL resourceURL = liferayPortletResponse.createResourceURL();

		resourceURL.setParameter(
			"cpDefinitionId", String.valueOf(_cpDefinitionId));
		resourceURL.setResourceID("/cp_content_web/view_cp_attachments");

		return resourceURL.toString();
	}

	private static final String _PAGE = "/gallery/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(GalleryTag.class);

	private CPContentHelper _cpContentHelper;
	private long _cpDefinitionId;
	private List<CPMedia> _images;
	private String _namespace = StringPool.BLANK;
	private String _viewCPAttachmentURL = StringPool.BLANK;

}