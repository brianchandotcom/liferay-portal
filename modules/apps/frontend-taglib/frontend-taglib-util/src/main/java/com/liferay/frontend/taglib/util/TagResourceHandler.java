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

package com.liferay.frontend.taglib.util;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.util.internal.NPMResolverRef;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.net.URL;

import java.util.Dictionary;
import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Iván Zaera Avellón
 */
public class TagResourceHandler {

	public TagResourceHandler(Class<?> tagClass, TagAccessor tagAccessor) {
		_tagAccessor = tagAccessor;

		_bundle = FrameworkUtil.getBundle(tagClass);
		_log = LogFactoryUtil.getLog(tagClass);

		Dictionary<String, String> headers = _bundle.getHeaders(
			StringPool.BLANK);

		_webContextPath = headers.get("Web-ContextPath");
	}

	public void outputBundleStyleSheet(String bundleCssPath) {
		StringBundler sb = new StringBundler(6);

		sb.append("<link data-senna-track=\"temporary\" href=\"");
		sb.append(PortalUtil.getPathModule());
		sb.append(_webContextPath);
		sb.append(StringPool.SLASH);
		sb.append(bundleCssPath);
		sb.append("\" rel=\"stylesheet\">");

		outputResource(Position.TOP, sb.toString());
	}

	public void outputNPMResource(String npmResourcePath) {
		try (NPMResolverRef npmResolverRef = new NPMResolverRef(_tagAccessor)) {
			NPMResolver npmResolver = npmResolverRef.getNPMResolver();

			String resourcePath = npmResolver.resolveModuleName(
				npmResourcePath);

			URL url = _bundle.getEntry(
				"META-INF/resources/node_modules/" + resourcePath);

			outputResource(Position.BOTTOM, StringUtil.read(url.openStream()));
		}
		catch (Exception exception) {
			_log.error(
				"Unable to output NPM resource " + npmResourcePath, exception);
		}
	}

	public void outputNPMStyleSheet(String npmCssPath) {
		try (NPMResolverRef npmResolverRef = new NPMResolverRef(_tagAccessor)) {
			NPMResolver npmResolver = npmResolverRef.getNPMResolver();

			String cssPath = npmResolver.resolveModuleName(npmCssPath);

			StringBundler sb = new StringBundler(6);

			sb.append("<link href=\"");
			sb.append(PortalUtil.getPathModule());
			sb.append(_webContextPath);
			sb.append("/node_modules/");
			sb.append(cssPath);
			sb.append("\" rel=\"stylesheet\">");

			outputResource(Position.TOP, sb.toString());
		}
		catch (Exception exception) {
			_log.error(
				"Unable to output NPM style sheet " + npmCssPath, exception);
		}
	}

	public void outputResource(Position position, String html) {
		HttpServletRequest httpServletRequest = _getRequest();

		boolean xPjax = GetterUtil.getBoolean(
			httpServletRequest.getHeader("X-PJAX"));

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay.isIsolated() || themeDisplay.isLifecycleResource() ||
			themeDisplay.isStateExclusive() || xPjax) {

			try {
				PageContext pageContext = _tagAccessor.getPageContext();

				JspWriter jspWriter = pageContext.getOut();

				jspWriter.write(html);
			}
			catch (IOException ioException) {
				_log.error("Unable to output resource", ioException);
			}
		}
		else {
			OutputData outputData = _getOutputData();

			Long key = _nextKey.getAndIncrement();

			outputData.setDataSB(
				key.toString(), _webKeysEnumMap.get(position),
				new StringBundler(html));
		}
	}

	public enum Position {

		BOTTOM, TOP

	}

	private OutputData _getOutputData() {
		HttpServletRequest httpServletRequest = _getRequest();

		OutputData outputData = (OutputData)httpServletRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		if (outputData == null) {
			outputData = new OutputData();

			httpServletRequest.setAttribute(WebKeys.OUTPUT_DATA, outputData);
		}

		return outputData;
	}

	private HttpServletRequest _getRequest() {
		return _tagAccessor.getRequest();
	}

	private ThemeDisplay _getThemeDisplay() {
		ServletRequest servletRequest = _getRequest();

		return (ThemeDisplay)servletRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	private static final EnumMap<Position, String> _webKeysEnumMap =
		new EnumMap<Position, String>(Position.class) {
			{
				put(Position.BOTTOM, WebKeys.PAGE_BODY_BOTTOM);
				put(Position.TOP, WebKeys.PAGE_TOP);
			}
		};

	private final Bundle _bundle;
	private final Log _log;
	private final AtomicLong _nextKey = new AtomicLong();
	private final TagAccessor _tagAccessor;
	private final String _webContextPath;

}