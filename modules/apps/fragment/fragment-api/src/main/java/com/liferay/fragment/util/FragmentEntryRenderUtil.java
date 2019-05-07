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

package com.liferay.fragment.util;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Pablo Molina
 */
public class FragmentEntryRenderUtil {

	public static PortletRegistry getPortletRegistry() {
		return _portletRegistryServiceTracler.getService();
	}

	public static FragmentEntryProcessorRegistry getService() {
		return _serviceTracker.getService();
	}

	public static String renderFragmentEntry(FragmentEntry fragmentEntry) {
		return renderFragmentEntry(
			fragmentEntry.getFragmentEntryId(), 0, fragmentEntry.getCss(),
			fragmentEntry.getHtml(), fragmentEntry.getJs());
	}

	public static String renderFragmentEntry(
		long fragmentEntryId, long fragmentEntryInstanceId, String css,
		String html, String js) {

		StringBundler sb = new StringBundler(14);

		sb.append("<div id=\"");

		StringBundler fragmentIdSB = new StringBundler(4);

		fragmentIdSB.append("fragment-");
		fragmentIdSB.append(fragmentEntryId);
		fragmentIdSB.append("-");
		fragmentIdSB.append(fragmentEntryInstanceId);

		sb.append(fragmentIdSB.toString());

		sb.append("\" >");
		sb.append(html);
		sb.append("</div>");

		if (Validator.isNotNull(css)) {
			sb.append("<style>");
			sb.append(css);
			sb.append("</style>");
		}

		if (Validator.isNotNull(js)) {
			sb.append("<script>(function() {");
			sb.append("var fragmentElement = document.querySelector('#");
			sb.append(fragmentIdSB.toString());
			sb.append("');");
			sb.append(js);
			sb.append(";}());</script>");
		}

		return sb.toString();
	}

	public static String renderFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		return renderFragmentEntryLink(
			fragmentEntryLink, FragmentEntryLinkConstants.EDIT,
			httpServletRequest, httpServletResponse);
	}

	public static String renderFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink, String mode,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		return renderFragmentEntryLink(
			fragmentEntryLink, mode, new HashMap<>(), httpServletRequest,
			httpServletResponse);
	}

	public static String renderFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink, String mode,
			Map<String, Object> parameterMap,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		return renderFragmentEntryLink(
			fragmentEntryLink, mode, parameterMap,
			LocaleUtil.getMostRelevantLocale(), httpServletRequest,
			httpServletResponse);
	}

	public static String renderFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink, String mode,
			Map<String, Object> parameterMap, Locale locale,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		return renderFragmentEntryLink(
			fragmentEntryLink, mode, parameterMap, locale, new long[0],
			httpServletRequest, httpServletResponse);
	}

	public static String renderFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink, String mode,
			Map<String, Object> parameterMap, Locale locale,
			long[] segmentsExperienceIds, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		FragmentRendererController fragmentRendererController =
			_getFragmentRendererController();

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		defaultFragmentRendererContext.setFieldValues(parameterMap);
		defaultFragmentRendererContext.setLocale(locale);
		defaultFragmentRendererContext.setMode(mode);
		defaultFragmentRendererContext.setSegmentsExperienceIds(
			segmentsExperienceIds);

		return fragmentRendererController.render(
			defaultFragmentRendererContext, httpServletRequest,
			httpServletResponse);
	}

	private static FragmentRendererController _getFragmentRendererController() {
		return _fragmentRendererControllerServiceTracker.getService();
	}

	private static final ServiceTracker
		<FragmentRendererController, FragmentRendererController>
			_fragmentRendererControllerServiceTracker =
				ServiceTrackerFactory.open(
					FrameworkUtil.getBundle(FragmentEntryRenderUtil.class),
					FragmentRendererController.class);
	private static final ServiceTracker<PortletRegistry, PortletRegistry>
		_portletRegistryServiceTracler = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(FragmentEntryRenderUtil.class),
			PortletRegistry.class);
	private static final ServiceTracker
		<FragmentEntryProcessorRegistry, FragmentEntryProcessorRegistry>
			_serviceTracker = ServiceTrackerFactory.open(
				FrameworkUtil.getBundle(FragmentEntryRenderUtil.class),
				FragmentEntryProcessorRegistry.class);

}