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

package com.liferay.frontend.icons.web.internal.util;

import com.liferay.frontend.icons.web.internal.model.FrontendIconsResource;
import com.liferay.frontend.icons.web.internal.model.FrontendIconsResourcePack;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Víctor Galán
 */
public class SVGUtil {

	public static List<FrontendIconsResource> getFrontendIconsResources(
		String svgContent) {

		List<FrontendIconsResource> frontendIconsResources = new ArrayList<>();

		try {
			Document document = SAXReaderUtil.read(svgContent);

			Element rootElement = document.getRootElement();

			List<Element> symbolElements = rootElement.elements("symbol");

			if (ListUtil.isEmpty(symbolElements)) {
				return Collections.singletonList(
					new FrontendIconsResource(
						_getInnerSVG(rootElement),
						rootElement.attributeValue("id"),
						rootElement.attributeValue("viewBox")));
			}

			for (Element symbolElement : symbolElements) {
				frontendIconsResources.add(
					new FrontendIconsResource(
						_getInnerSVG(symbolElement),
						symbolElement.attributeValue("id"),
						symbolElement.attributeValue("viewBox")));
			}
		}
		catch (DocumentException documentException) {
			_log.error(documentException);
		}

		return frontendIconsResources;
	}

	public static String getSVGSpritemap(
		FrontendIconsResourcePack frontendIconsResourcePack) {

		StringBundler sb = new StringBundler();

		for (FrontendIconsResource frontendIconsResource :
				frontendIconsResourcePack.getFrontendIconsResources()) {

			sb.append(frontendIconsResource.asSymbol());
		}

		return StringUtil.replace(
			_SPRITEMAP_TMPL, "[$CONTENT$]", sb.toString());
	}

	private static String _getInnerSVG(Element rootElement) {
		List<Element> elements = rootElement.elements();

		StringBundler sb = new StringBundler(elements.size());

		for (Element element : elements) {
			sb.append(element.asXML());
		}

		return sb.toString();
	}

	private static final String _SPRITEMAP_TMPL = StringUtil.read(
		SVGUtil.class,
		"/com/liferay/frontend/icon/admin/web/internal/util/dependencies" +
			"/spritemap.svg");

	private static final Log _log = LogFactoryUtil.getLog(SVGUtil.class);

}