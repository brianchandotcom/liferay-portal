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

import com.liferay.frontend.taglib.internal.util.ServicesProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;
import com.liferay.taglib.util.AttributesTagSupport;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class StylesheetTag extends AttributesTagSupport {

	@Override
	public int doEndTag() throws JspException {
		Map<String, Bundle> bundleMap = ServicesProvider.getBundleMap();

		Bundle bundle = bundleMap.get(_bundle);

		if (bundle == null) {
			throw new JspException("Unable to find bundle " + _bundle);
		}

		AbsolutePortalURLBuilderFactory absolutePortalURLBuilderFactory =
			ServicesProvider.getAbsolutePortalURLBuilderFactory();

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		OutputData outputData = _getOutputData(httpServletRequest);

		StringBundler sb = new StringBundler(3);

		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
		sb.append(
			absolutePortalURLBuilder.forModule(
				bundle, _css
			).build());
		sb.append("\"></link>");

		outputData.addDataSB(_getOutputKey(), WebKeys.PAGE_TOP, sb);

		return EVAL_PAGE;
	}

	public void setBundle(String bundle) {
		_bundle = bundle;
	}

	public void setCss(String css) {
		_css = css;
	}

	private OutputData _getOutputData(ServletRequest servletRequest) {
		OutputData outputData = (OutputData)servletRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		if (outputData == null) {
			outputData = new OutputData();

			servletRequest.setAttribute(WebKeys.OUTPUT_DATA, outputData);
		}

		return outputData;
	}

	private String _getOutputKey() {
		return _bundle + StringPool.SLASH + _css;
	}

	private String _bundle;
	private String _css;

}