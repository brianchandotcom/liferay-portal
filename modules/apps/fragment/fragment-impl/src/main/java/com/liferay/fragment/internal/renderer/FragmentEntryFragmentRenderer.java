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

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.input.template.parser.FragmentEntryInputTemplateNodeContextHelper;
import com.liferay.fragment.input.template.parser.InputTemplateNode;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.form.InfoForm;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;
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
		catch (PortalException portalException) {
			throw new IOException(portalException);
		}
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
			fragmentEntryLink.setType(fragmentEntry.getType());
		}

		return fragmentEntryLink;
	}

	private String _getFragmentEntryName(FragmentEntryLink fragmentEntryLink) {
		FragmentEntry fragmentEntry = null;

		if (Validator.isNotNull(fragmentEntryLink.getRendererKey())) {
			fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					fragmentEntryLink.getRendererKey());
		}

		if (fragmentEntry == null) {
			fragmentEntry = _fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());
		}

		if (fragmentEntry == null) {
			return StringPool.BLANK;
		}

		return fragmentEntry.getName();
	}

	private JSONObject _getInputJSONObject(
		FragmentEntryLink fragmentEntryLink,
		HttpServletRequest httpServletRequest,
		Optional<InfoForm> infoFormOptional, Locale locale) {

		FragmentEntryInputTemplateNodeContextHelper
			fragmentEntryInputTemplateNodeContextHelper =
				new FragmentEntryInputTemplateNodeContextHelper(
					_getFragmentEntryName(fragmentEntryLink),
					_dlAppLocalService, _fragmentEntryConfigurationParser,
					_itemSelector);

		InputTemplateNode inputTemplateNode =
			fragmentEntryInputTemplateNodeContextHelper.toInputTemplateNode(
				fragmentEntryLink, httpServletRequest, infoFormOptional,
				locale);

		return inputTemplateNode.toJSONObject();
	}

	private boolean _isCacheable(
		FragmentEntryLink fragmentEntryLink,
		FragmentRendererContext fragmentRendererContext) {

		if (fragmentEntryLink.isTypeInput() ||
			!Objects.equals(
				fragmentRendererContext.getMode(),
				FragmentEntryLinkConstants.VIEW) ||
			(fragmentRendererContext.getPreviewClassPK() > 0) ||
			!fragmentRendererContext.isUseCachedContent()) {

			return false;
		}

		if (fragmentEntryLink.getPlid() > 0) {
			Layout layout = _layoutLocalService.fetchLayout(
				fragmentEntryLink.getPlid());

			if (layout.isDraftLayout()) {
				return false;
			}
		}

		FragmentEntry fragmentEntry = null;

		if (Validator.isNotNull(fragmentEntryLink.getRendererKey())) {
			fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					fragmentEntryLink.getRendererKey());

			if (fragmentEntry == null) {
				return false;
			}
		}

		if (fragmentEntry == null) {
			fragmentEntry = _fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());
		}

		if (fragmentEntry == null) {
			return fragmentEntryLink.isCacheable();
		}

		return fragmentEntry.isCacheable();
	}

	private String _renderFragmentEntry(
		String configuration, String css,
		FragmentRendererContext fragmentRendererContext, String html,
		HttpServletRequest httpServletRequest) {

		StringBundler sb = new StringBundler(25);

		sb.append("<div id=\"");

		sb.append(fragmentRendererContext.getFragmentElementId());

		sb.append("\" >");
		sb.append(html);
		sb.append("</div>");

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		if (Validator.isNotNull(css)) {
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
					cssLoaded = Objects.equals(cssSB.toString(), css);
				}
			}
			else {
				outputData = new OutputData();
			}

			if (!cssLoaded ||
				Objects.equals(
					fragmentRendererContext.getMode(),
					FragmentEntryLinkConstants.EDIT)) {

				sb.append("<style>");
				sb.append(css);
				sb.append("</style>");

				outputData.addOutputKey(outputKey);

				outputData.setDataSB(
					outputKey, StringPool.BLANK, new StringBundler(css));

				httpServletRequest.setAttribute(
					WebKeys.OUTPUT_DATA, outputData);
			}
		}

		if (Validator.isNotNull(fragmentEntryLink.getJs())) {
			sb.append("<script>(function() {");
			sb.append("const configuration = ");
			sb.append(configuration);
			sb.append("; const fragmentElement = document.querySelector('#");
			sb.append(fragmentRendererContext.getFragmentElementId());
			sb.append("'); const fragmentEntryLinkNamespace = '");
			sb.append(fragmentEntryLink.getNamespace());
			sb.append("'; const fragmentNamespace = '");
			sb.append(fragmentEntryLink.getNamespace());
			sb.append("'");

			if (fragmentEntryLink.isTypeInput()) {
				sb.append("; const input = ");
				sb.append(
					JSONUtil.toString(
						_getInputJSONObject(
							fragmentEntryLink, httpServletRequest,
							fragmentRendererContext.getInfoFormOptional(),
							fragmentRendererContext.getLocale())));
			}

			sb.append("; const layoutMode = '");
			sb.append(
				HtmlUtil.escapeJS(
					ParamUtil.getString(
						_portal.getOriginalServletRequest(httpServletRequest),
						"p_l_mode", Constants.VIEW)));
			sb.append("';");
			sb.append(fragmentEntryLink.getJs());
			sb.append(";}());</script>");
		}

		return sb.toString();
	}

	private String _renderFragmentEntryLink(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		PortalCache<String, String> portalCache =
			(PortalCache<String, String>)_multiVMPool.getPortalCache(
				FragmentEntryLink.class.getName());

		FragmentEntryLink fragmentEntryLink = _getFragmentEntryLink(
			fragmentRendererContext);

		StringBundler cacheKeySB = new StringBundler(5);

		cacheKeySB.append(fragmentEntryLink.getFragmentEntryLinkId());
		cacheKeySB.append(StringPool.DASH);
		cacheKeySB.append(fragmentRendererContext.getLocale());
		cacheKeySB.append(StringPool.DASH);
		cacheKeySB.append(fragmentEntryLink.getSegmentsExperienceId());

		String content = StringPool.BLANK;

		if (_isCacheable(fragmentEntryLink, fragmentRendererContext)) {
			content = portalCache.get(cacheKeySB.toString());

			if (Validator.isNotNull(content)) {
				return content;
			}
		}

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					httpServletRequest, httpServletResponse,
					fragmentRendererContext.getMode(),
					fragmentRendererContext.getLocale());

		Optional<Object> displayObjectOptional =
			fragmentRendererContext.getDisplayObjectOptional();

		defaultFragmentEntryProcessorContext.setDisplayObject(
			displayObjectOptional.orElse(null));

		defaultFragmentEntryProcessorContext.setFragmentElementId(
			fragmentRendererContext.getFragmentElementId());

		Optional<InfoForm> infoFormOptional =
			fragmentRendererContext.getInfoFormOptional();

		defaultFragmentEntryProcessorContext.setInfoForm(
			infoFormOptional.orElse(null));

		defaultFragmentEntryProcessorContext.setPreviewClassNameId(
			fragmentRendererContext.getPreviewClassNameId());
		defaultFragmentEntryProcessorContext.setPreviewClassPK(
			fragmentRendererContext.getPreviewClassPK());
		defaultFragmentEntryProcessorContext.setPreviewType(
			fragmentRendererContext.getPreviewType());
		defaultFragmentEntryProcessorContext.setPreviewVersion(
			fragmentRendererContext.getPreviewVersion());
		defaultFragmentEntryProcessorContext.setSegmentsEntryIds(
			fragmentRendererContext.getSegmentsEntryIds());

		String css = StringPool.BLANK;

		if (Validator.isNotNull(fragmentEntryLink.getCss())) {
			css = _fragmentEntryProcessorRegistry.processFragmentEntryLinkCSS(
				fragmentEntryLink, defaultFragmentEntryProcessorContext);
		}

		String html = StringPool.BLANK;

		if (Validator.isNotNull(fragmentEntryLink.getHtml()) ||
			Validator.isNotNull(fragmentEntryLink.getEditableValues())) {

			html = _fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, defaultFragmentEntryProcessorContext);
		}

		if (Objects.equals(
				defaultFragmentEntryProcessorContext.getMode(),
				FragmentEntryLinkConstants.EDIT)) {

			html = _writePortletPaths(
				fragmentEntryLink, html, httpServletRequest,
				httpServletResponse);
		}

		JSONObject configurationJSONObject = _jsonFactory.createJSONObject();

		if (Validator.isNotNull(fragmentEntryLink.getConfiguration())) {
			configurationJSONObject =
				_fragmentEntryConfigurationParser.getConfigurationJSONObject(
					fragmentEntryLink.getConfiguration(),
					fragmentEntryLink.getEditableValues(),
					fragmentRendererContext.getLocale());
		}

		content = _renderFragmentEntry(
			configurationJSONObject.toString(), css, fragmentRendererContext,
			html, httpServletRequest);

		if (_isCacheable(fragmentEntryLink, fragmentRendererContext)) {
			portalCache.put(cacheKeySB.toString(), content);
		}

		return content;
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
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private MultiVMPool _multiVMPool;

	@Reference
	private Portal _portal;

	@Reference
	private PortletRegistry _portletRegistry;

}