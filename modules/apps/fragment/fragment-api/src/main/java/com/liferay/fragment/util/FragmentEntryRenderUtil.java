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
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			FragmentEntryLink fragmentEntryLink, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException {

		return renderFragmentEntryLink(
			fragmentEntryLink, FragmentEntryLinkConstants.EDIT, request,
			response);
	}

	public static String renderFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink, String mode,
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		return renderFragmentEntryLink(
			fragmentEntryLink, mode, new HashMap<>(), request, response);
	}

	public static String renderFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink, String mode,
			Map<String, Object> parameterMap, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException {

		return renderFragmentEntryLink(
			fragmentEntryLink, mode, parameterMap,
			LocaleUtil.getMostRelevantLocale(), request, response);
	}

	public static String renderFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink, String mode,
			Map<String, Object> parameterMap, Locale locale,
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		return renderFragmentEntryLink(
			fragmentEntryLink, mode, parameterMap, locale, new long[0], request,
			response);
	}

	public static String renderFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink, String mode,
			Map<String, Object> parameterMap, Locale locale, long[] segmentsIds,
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry =
			getService();

		String css = fragmentEntryProcessorRegistry.processFragmentEntryLinkCSS(
			fragmentEntryLink, mode, locale);

		String html =
			fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, mode, locale, segmentsIds);

		if ((request != null) && Validator.isNotNull(html)) {
			html = _processTemplate(html, parameterMap, request, response);
		}

		if (Validator.isNotNull(css)) {
			css = _processTemplate(css, parameterMap, request, response);
		}

		html = _writePortletPaths(fragmentEntryLink, html, request, response);

		return renderFragmentEntry(
			fragmentEntryLink.getFragmentEntryId(),
			fragmentEntryLink.getPosition(), css, html,
			fragmentEntryLink.getJs());
	}

	private static String _processTemplate(
			String html, Map<String, Object> parameterMap,
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		html = "[#ftl]\n" + html;

		TemplateResource templateResource = new StringTemplateResource(
			"template_id", html);

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, templateResource, false);

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(
				TemplateConstants.LANG_TYPE_FTL);

		templateManager.addTaglibSupport(template, request, response);
		templateManager.addTaglibTheme(
			template, "taglibLiferay", request, response);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.put(TemplateConstants.WRITER, unsyncStringWriter);

		if (MapUtil.isNotEmpty(parameterMap)) {
			template.putAll(parameterMap);
		}

		template.prepare(request);

		template.processTemplate(unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	private static String _writePortletPaths(
			FragmentEntryLink fragmentEntryLink, String html,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		PortletRegistry portletRegistry = getPortletRegistry();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		HttpServletResponse pipingHttpServletResponse =
			new PipingServletResponse(httpServletResponse, unsyncStringWriter);

		portletRegistry.writePortletPaths(
			fragmentEntryLink, httpServletRequest, pipingHttpServletResponse);

		unsyncStringWriter.append(html);

		return unsyncStringWriter.toString();
	}

	private static final ServiceTracker<PortletRegistry, PortletRegistry>
		_portletRegistryServiceTracler = ServiceTrackerFactory.open(
			PortletRegistry.class);
	private static final ServiceTracker
		<FragmentEntryProcessorRegistry, FragmentEntryProcessorRegistry>
			_serviceTracker = ServiceTrackerFactory.open(
				FragmentEntryProcessorRegistry.class);

}