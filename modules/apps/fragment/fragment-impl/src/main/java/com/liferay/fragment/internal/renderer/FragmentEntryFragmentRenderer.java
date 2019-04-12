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

package com.liferay.fragment.internal.renderer;

import com.liferay.asset.info.display.contributor.util.ContentAccessorUtil;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 * @author Pablo Molina
 */
@Component(service = FragmentRenderer.class)
public class FragmentEntryFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey(
		FragmentRendererContext fragmentRendererContext) {

		try {
			FragmentEntry fragmentEntry = _getFragmentEntry(
				fragmentRendererContext);

			FragmentCollection fragmentCollection =
				_fragmentCollectionLocalService.getFragmentCollection(
					fragmentEntry.getFragmentCollectionId());

			return fragmentCollection.getFragmentCollectionKey();
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Override
	public String getKey() {
		return FragmentRendererConstants.FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY;
	}

	@Override
	public String getLabel(FragmentRendererContext fragmentRendererContext) {
		try {
			FragmentEntry fragmentEntry = _getFragmentEntry(
				fragmentRendererContext);

			return fragmentEntry.getName();
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write(
				_renderFragmentEntryLink(
					fragmentRendererContext, httpServletRequest,
					httpServletResponse));
		}
		catch (PortalException pe) {
			throw new IOException(pe);
		}
	}

	private FragmentEntry _getFragmentEntry(
			FragmentRendererContext fragmentRendererContext)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		return _fragmentEntryLocalService.getFragmentEntry(
			fragmentEntryLink.getFragmentEntryId());
	}

	private String _processTemplate(
			String html, FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource("template_id", "[#ftl]\n" + html),
			false);

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(
				TemplateConstants.LANG_TYPE_FTL);

		templateManager.addTaglibSupport(
			template, httpServletRequest, httpServletResponse);
		templateManager.addTaglibTheme(
			template, "taglibLiferay", httpServletRequest, httpServletResponse);

		template.put(TemplateConstants.WRITER, unsyncStringWriter);
		template.put("contentAccessorUtil", ContentAccessorUtil.getInstance());

		Optional<Map<String, Object>> fieldValuesOptional =
			fragmentRendererContext.getFieldValuesOptional();

		if (fieldValuesOptional.isPresent() &&
			MapUtil.isNotEmpty(fieldValuesOptional.get())) {

			template.putAll(fieldValuesOptional.get());
		}

		template.prepare(httpServletRequest);

		template.processTemplate(unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	private String _renderFragmentEntry(
		long fragmentEntryId, String namespace, String css, String html,
		String js) {

		StringBundler sb = new StringBundler(14);

		sb.append("<div id=\"");

		StringBundler fragmentIdSB = new StringBundler(4);

		fragmentIdSB.append("fragment-");
		fragmentIdSB.append(fragmentEntryId);
		fragmentIdSB.append("-");
		fragmentIdSB.append(namespace);

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

	private String _renderFragmentEntryLink(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		String css =
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkCSS(
				fragmentRendererContext.getFragmentEntryLink(),
				fragmentRendererContext.getMode(),
				fragmentRendererContext.getLocale(),
				fragmentRendererContext.getSegmentsExperienceIds());

		if ((httpServletRequest != null) && (httpServletResponse != null) &&
			Validator.isNotNull(css)) {

			css = _processTemplate(
				css, fragmentRendererContext, httpServletRequest,
				httpServletResponse);
		}

		String html =
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentRendererContext.getFragmentEntryLink(),
				fragmentRendererContext.getMode(),
				fragmentRendererContext.getLocale(),
				fragmentRendererContext.getSegmentsExperienceIds());

		if ((httpServletRequest != null) && (httpServletResponse != null) &&
			Validator.isNotNull(html)) {

			html = _processTemplate(
				html, fragmentRendererContext, httpServletRequest,
				httpServletResponse);
		}

		html = _writePortletPaths(
			fragmentRendererContext.getFragmentEntryLink(), html,
			httpServletRequest, httpServletResponse);

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		return _renderFragmentEntry(
			fragmentEntryLink.getFragmentEntryId(),
			fragmentEntryLink.getNamespace(), css, html,
			fragmentEntryLink.getJs());
	}

	private String _writePortletPaths(
			FragmentEntryLink fragmentEntryLink, String html,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		_portletRegistry.writePortletPaths(
			fragmentEntryLink, httpServletRequest,
			new PipingServletResponse(httpServletResponse, unsyncStringWriter));

		unsyncStringWriter.append(html);

		return unsyncStringWriter.toString();
	}

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private PortletRegistry _portletRegistry;

}