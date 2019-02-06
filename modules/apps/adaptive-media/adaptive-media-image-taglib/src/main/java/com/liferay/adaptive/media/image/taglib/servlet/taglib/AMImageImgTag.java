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

package com.liferay.adaptive.media.image.taglib.servlet.taglib;

import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.AttributesTagSupport;

import java.io.IOException;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTag;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Adolfo Pérez
 */
public class AMImageImgTag extends AttributesTagSupport implements BodyTag {

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(_getHTMLTag());

			return EVAL_PAGE;
		}
		catch (IOException | PortalException e) {
			throw new JspException(e);
		}
	}

	public void setFileVersion(FileVersion fileVersion) {
		_fileVersion = fileVersion;
	}

	private String _getFallbackTag() throws PortalException {
		Map<String, Object> dynamicAttributes = getDynamicAttributes();

		StringBundler sb = new StringBundler(
			4 + (4 * dynamicAttributes.size()));

		sb.append("<img ");

		for (Map.Entry<String, Object> entry : dynamicAttributes.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=\"");

			Object value = entry.getValue();

			sb.append(HtmlUtil.escapeAttribute(value.toString()));

			sb.append("\" ");
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String downloadURL = DLURLHelperUtil.getPreviewURL(
			_fileVersion.getFileEntry(), _fileVersion, themeDisplay,
			StringPool.BLANK);

		sb.append("src=\"");
		sb.append(downloadURL);
		sb.append("\" />");

		return sb.toString();
	}

	private String _getHTMLTag() throws PortalException {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<AMImageHTMLTagFactory> serviceReference =
			bundleContext.getServiceReference(AMImageHTMLTagFactory.class);

		if (serviceReference == null) {
			return _getFallbackTag();
		}

		try {
			AMImageHTMLTagFactory amImageHTMLTagFactory =
				bundleContext.getService(serviceReference);

			if (amImageHTMLTagFactory == null) {
				return _getFallbackTag();
			}

			return amImageHTMLTagFactory.create(
				_getFallbackTag(), _fileVersion.getFileEntry());
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	private FileVersion _fileVersion;

}