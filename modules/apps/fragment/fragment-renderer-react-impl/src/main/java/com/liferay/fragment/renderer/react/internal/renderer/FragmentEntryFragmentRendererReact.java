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

package com.liferay.fragment.renderer.react.internal.renderer;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 * @author Eudaldo Alonso
 */
@Component(service = FragmentRenderer.class)
public class FragmentEntryFragmentRendererReact implements FragmentRenderer {

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
		return FragmentRendererConstants.
			FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY_REACT;
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

			FragmentEntryLink fragmentEntryLink = _getFragmentEntryLink(
				fragmentRendererContext);

			JSONObject configurationJSONObject =
				JSONFactoryUtil.createJSONObject();

			if (Validator.isNotNull(fragmentEntryLink.getConfiguration())) {
				configurationJSONObject =
					_fragmentEntryConfigurationParser.
						getConfigurationJSONObject(
							fragmentEntryLink.getConfiguration(),
							fragmentEntryLink.getEditableValues());
			}

			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				"configuration", configurationJSONObject
			).build();

			printWriter.write(
				_renderFragmentEntry(
					fragmentEntryLink, data, httpServletRequest));
		}
		catch (PortalException portalException) {
			throw new IOException(portalException);
		}
	}

	@Activate
	protected void activate() {
		_jsPackage = _npmResolver.getJSPackage();
	}

	private FragmentEntry _getContributedFragmentEntry(
		FragmentEntryLink fragmentEntryLink) {

		Map<String, FragmentEntry> fragmentCollectionContributorEntries =
			_fragmentCollectionContributorTracker.getFragmentEntries();

		return fragmentCollectionContributorEntries.get(
			fragmentEntryLink.getRendererKey());
	}

	private FragmentEntryLink _getFragmentEntryLink(
		FragmentRendererContext fragmentRendererContext) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		FragmentEntry fragmentEntry = _getContributedFragmentEntry(
			fragmentEntryLink);

		if (fragmentEntry != null) {
			fragmentEntryLink.setCss(fragmentEntry.getCss());
			fragmentEntryLink.setHtml(fragmentEntry.getHtml());
			fragmentEntryLink.setJs(fragmentEntry.getJs());
		}

		return fragmentEntryLink;
	}

	private String _renderFragmentEntry(
			FragmentEntryLink fragmentEntryLink, Map<String, Object> data,
			HttpServletRequest httpServletRequest)
		throws IOException {

		StringBundler sb = new StringBundler(9);

		sb.append("<div id=\"");

		StringBundler fragmentIdSB = new StringBundler(4);

		fragmentIdSB.append("fragment-");
		fragmentIdSB.append(fragmentEntryLink.getFragmentEntryId());
		fragmentIdSB.append("-");
		fragmentIdSB.append(fragmentEntryLink.getNamespace());

		sb.append(fragmentIdSB.toString());

		sb.append("\" >");
		sb.append(fragmentEntryLink.getHtml());

		Writer writer = new CharArrayWriter();

		_reactRenderer.renderReact(
			new ComponentDescriptor(
				ModuleNameUtil.getModuleResolvedId(
					_jsPackage,
					"fragmentEntryLink/" +
						fragmentEntryLink.getFragmentEntryLinkId()),
				"fragment" + fragmentEntryLink.getFragmentEntryLinkId(),
				Collections.emptyList(), true),
			data, httpServletRequest, writer);

		sb.append(writer.toString());

		sb.append("</div>");

		if (Validator.isNotNull(fragmentEntryLink.getCss())) {
			String outputKey = fragmentEntryLink.getFragmentEntryId() + "_CSS";

			OutputData outputData = (OutputData)httpServletRequest.getAttribute(
				WebKeys.OUTPUT_DATA);

			boolean cssLoaded = false;

			if (outputData != null) {
				Set<String> outputKeys = outputData.getOutputKeys();

				cssLoaded = outputKeys.contains(outputKey);

				StringBundler cssSB = outputData.getDataSB(
					outputKey, StringPool.BLANK);

				if (cssSB != null) {
					cssLoaded = Objects.equals(
						cssSB.toString(), fragmentEntryLink.getCss());
				}
			}
			else {
				outputData = new OutputData();
			}

			if (!cssLoaded) {
				sb.append("<style>");
				sb.append(fragmentEntryLink.getCss());
				sb.append("</style>");

				outputData.addOutputKey(outputKey);

				outputData.setDataSB(
					outputKey, StringPool.BLANK,
					new StringBundler(fragmentEntryLink.getCss()));

				httpServletRequest.setAttribute(
					WebKeys.OUTPUT_DATA, outputData);
			}
		}

		return sb.toString();
	}

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	private JSPackage _jsPackage;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private ReactRenderer _reactRenderer;

}