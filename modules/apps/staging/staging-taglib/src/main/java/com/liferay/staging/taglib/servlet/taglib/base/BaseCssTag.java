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

package com.liferay.staging.taglib.servlet.taglib.base;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Peter Borkuti
 */
public abstract class BaseCssTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		_outputStylesheetLink();

		return super.doStartTag();
	}

	public abstract String getTagNameForCssPath();

	private OutputData _getOutputData() {
		ServletRequest servletRequest = getRequest();

		OutputData outputData = (OutputData)servletRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		if (outputData == null) {
			outputData = new OutputData();

			servletRequest.setAttribute(WebKeys.OUTPUT_DATA, outputData);
		}

		return outputData;
	}

	private void _outputStylesheetLink() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)getRequest().getAttribute(WebKeys.THEME_DISPLAY);

		boolean xPjax = GetterUtil.getBoolean(request.getHeader("X-PJAX"));

		StringBundler sb = new StringBundler(6);

		sb.append("<link data-senna-track=\"temporary\" href=\"");
		sb.append(PortalUtil.getPathModule());
		sb.append("/staging-taglib/");
		sb.append(getTagNameForCssPath());
		sb.append("/css/main.css");
		sb.append("\" rel=\"stylesheet\">");

		if (themeDisplay.isIsolated() || themeDisplay.isLifecycleResource() ||
			themeDisplay.isStateExclusive() || xPjax) {

			try {
				JspWriter jspWriter = pageContext.getOut();

				jspWriter.write(sb.toString());
			}
			catch (IOException ioe) {
				_log.error("Unable to output style sheet link", ioe);
			}
		}
		else {
			OutputData outputData = _getOutputData();

			outputData.setDataSB(
				_OUTPUT_CSS_KEY + getTagNameForCssPath(), WebKeys.PAGE_TOP, sb);
		}
	}

	private static final String _OUTPUT_CSS_KEY =
		BaseCssTag.class.getName() + "_CSS_";

	private static final Log _log = LogFactoryUtil.getLog(BaseCssTag.class);

}