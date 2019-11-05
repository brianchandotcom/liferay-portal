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

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.util.FragmentEntryConfigUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
	public String getCollectionKey() {
		return StringPool.BLANK;
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		return fragmentEntryLink.getConfiguration();
	}

	@Override
	public String getKey() {
		return FragmentRendererConstants.FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY;
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		return false;
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

	private String _renderFragmentEntry(
		long fragmentEntryId, String css, String html, String js,
		String configuration, String namespace) {

		StringBundler sb = new StringBundler(16);

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
			boolean cssLoaded = false;

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			OutputData outputData = null;

			String outputKey = fragmentEntryId + "_CSS";

			if (serviceContext != null) {
				outputData = (OutputData)serviceContext.getAttribute(
					WebKeys.OUTPUT_DATA);

				if (outputData == null) {
					outputData = new OutputData();
				}

				Set<String> outputKeys = outputData.getOutputKeys();

				StringBundler cssSB = outputData.getDataSB(
					outputKey, StringPool.BLANK);

				cssLoaded = outputKeys.contains(outputKey);

				if (cssSB != null) {
					cssLoaded = Objects.equals(cssSB.toString(), css);
				}
			}

			if (!cssLoaded) {
				sb.append("<style>");
				sb.append(css);
				sb.append("</style>");

				if (outputData != null) {
					outputData.addOutputKey(outputKey);

					outputData.setDataSB(
						outputKey, StringPool.BLANK, new StringBundler(css));

					serviceContext.setAttribute(
						WebKeys.OUTPUT_DATA, outputData);
				}
			}
		}

		if (Validator.isNotNull(js)) {
			sb.append("<script>(function() {");
			sb.append("var fragmentElement = document.querySelector('#");
			sb.append(fragmentIdSB.toString());
			sb.append("'); var configuration = ");
			sb.append(configuration);
			sb.append(";");
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

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					httpServletRequest, httpServletResponse,
					fragmentRendererContext.getMode(),
					fragmentRendererContext.getLocale());

		Optional<Map<String, Object>> fieldValuesOptional =
			fragmentRendererContext.getFieldValuesOptional();

		defaultFragmentEntryProcessorContext.setFieldValues(
			fieldValuesOptional.orElse(null));

		defaultFragmentEntryProcessorContext.setPreviewClassPK(
			fragmentRendererContext.getPreviewClassPK());
		defaultFragmentEntryProcessorContext.setPreviewType(
			fragmentRendererContext.getPreviewType());
		defaultFragmentEntryProcessorContext.setSegmentsExperienceIds(
			fragmentRendererContext.getSegmentsExperienceIds());

		String css =
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkCSS(
				fragmentEntryLink, defaultFragmentEntryProcessorContext);

		String html =
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, defaultFragmentEntryProcessorContext);

		html = _writePortletPaths(
			fragmentEntryLink, html, httpServletRequest, httpServletResponse);

		JSONObject configurationJSONObject =
			FragmentEntryConfigUtil.getConfigurationJSONObject(
				fragmentEntryLink.getConfiguration(),
				fragmentEntryLink.getEditableValues(),
				fragmentRendererContext.getSegmentsExperienceIds());

		return _renderFragmentEntry(
			fragmentEntryLink.getFragmentEntryId(), css, html,
			fragmentEntryLink.getJs(), configurationJSONObject.toString(),
			fragmentEntryLink.getNamespace());
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
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private PortletRegistry _portletRegistry;

}