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

package com.liferay.portal.kernel.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.render.PortletRenderParts;
import com.liferay.portal.kernel.portlet.render.PortletRenderUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.portlet.MimeResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 * @deprecated As of Cavanaugh (7.4.x), replaced with {@link PortletRenderUtil}
 */
@Deprecated
public class PortletPathsUtil {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced with {@link PortletRenderUtil#getPortletRenderParts(HttpServletRequest, String, Portlet)}
	 */
	@Deprecated
	public static Map<String, Object> getPortletPaths(
		HttpServletRequest httpServletRequest, String portletHTML,
		Portlet portlet) {

		PortletRenderParts portletRenderParts =
			PortletRenderUtil.getPortletRenderParts(
				httpServletRequest, portletHTML, portlet);

		return HashMapBuilder.<String, Object>put(
			"footerCssPaths", portletRenderParts.getFooterCssPaths()
		).put(
			"footerJavaScriptPaths",
			portletRenderParts.getFooterJavaScriptPaths()
		).put(
			"headerCssPaths", portletRenderParts.getHeaderCssPaths()
		).put(
			"headerJavaScriptPaths",
			portletRenderParts.getHeaderJavaScriptPaths()
		).put(
			"markupHeadElements",
			() -> {
				List<String> markupHeadElements =
					(List<String>)httpServletRequest.getAttribute(
						MimeResponse.MARKUP_HEAD_ELEMENT);

				if (markupHeadElements != null) {
					return StringUtil.merge(
						markupHeadElements, StringPool.BLANK);
				}

				return null;
			}
		).put(
			"portletHTML", portletRenderParts.getPortletHTML()
		).put(
			"refresh", portletRenderParts.isRefresh()
		).build();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced with {@link PortletRenderUtil#writeFooterPaths(HttpServletResponse, PortletRenderParts)}
	 */
	@Deprecated
	public static void writeFooterPaths(
			HttpServletResponse httpServletResponse, Map<String, Object> paths)
		throws IOException {

		PortletRenderUtil.writeFooterPaths(
			httpServletResponse,
			new PortletRenderParts(
				(Collection<String>)paths.get("footerCssPaths"),
				(Collection<String>)paths.get("footerJavaScriptPaths"),
				(Collection<String>)paths.get("headerCssPaths"),
				(Collection<String>)paths.get("headerJavaScriptPaths"),
				(String)paths.get("portletHTML"),
				(Boolean)paths.get("refresh")));
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced with {@link PortletRenderUtil#writeHeaderPaths(HttpServletResponse, PortletRenderParts)}
	 */
	@Deprecated
	public static void writeHeaderPaths(
			HttpServletResponse httpServletResponse, Map<String, Object> paths)
		throws IOException {

		PortletRenderUtil.writeHeaderPaths(
			httpServletResponse,
			new PortletRenderParts(
				(Collection<String>)paths.get("footerCssPaths"),
				(Collection<String>)paths.get("footerJavaScriptPaths"),
				(Collection<String>)paths.get("headerCssPaths"),
				(Collection<String>)paths.get("headerJavaScriptPaths"),
				(String)paths.get("portletHTML"),
				(Boolean)paths.get("refresh")));
	}

}